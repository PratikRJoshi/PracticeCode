# 3885. Design Event Manager

**Link:** [https://leetcode.com/problems/design-event-manager/](https://leetcode.com/problems/design-event-manager/)

**Difficulty:** Medium

---

## Problem Description

You are given an initial list of events, where each event has a unique `eventId` and a `priority`.

Implement the `EventManager` class:

- `EventManager(int[][] events)` — Initializes the manager with the given events, where `events[i] = [eventId_i, priority_i]`.
- `void updatePriority(int eventId, int newPriority)` — Updates the priority of the active event with id `eventId` to `newPriority`.
- `int pollHighest()` — Removes and returns the `eventId` of the active event with the **highest priority**. If multiple active events have the same priority, return the **smallest `eventId`** among them. If there are no active events, return `-1`.

An event is called **active** if it has not been removed by `pollHighest()`.

### Examples

**Example 1:**
```
Input:
["EventManager", "pollHighest", "updatePriority", "pollHighest", "pollHighest"]
[[[[5, 7], [2, 7], [9, 4]]], [], [9, 7], [], []]

Output: [null, 2, null, 5, 9]

Explanation:
EventManager eventManager = new EventManager([[5,7], [2,7], [9,4]]);
eventManager.pollHighest();       // events 5 and 2 both have priority 7, return smaller id → 2
eventManager.updatePriority(9, 7); // event 9 now has priority 7
eventManager.pollHighest();       // remaining highest-priority events: 5 and 9 → return 5
eventManager.pollHighest();       // only event 9 remains → return 9
```

**Example 2:**
```
Input:
["EventManager", "pollHighest", "pollHighest", "pollHighest"]
[[[[4, 1], [7, 2]]], [], [], []]

Output: [null, 7, 4, -1]

Explanation:
EventManager eventManager = new EventManager([[4,1], [7,2]]);
eventManager.pollHighest(); // event 7 has higher priority → return 7
eventManager.pollHighest(); // only event 4 remains → return 4
eventManager.pollHighest(); // no events left → return -1
```

### Constraints

- `1 <= events.length <= 10^5`
- `events[i] = [eventId, priority]`
- `1 <= eventId <= 10^9`
- `1 <= priority <= 10^9`
- All values of `eventId` in `events` are unique.
- `1 <= newPriority <= 10^9`
- For every call to `updatePriority`, `eventId` refers to an active event.
- At most `10^5` calls in total will be made to `updatePriority` and `pollHighest`.

---

## Intuition / Main Idea

### The Core Question

We need to always quickly answer: *"Which active event has the highest priority, and among ties, which has the smallest id?"*

And we need to be able to **update** a priority mid-stream.

### Building the Intuition Step by Step

**Step 1: What if there were no updates?**

If priorities were fixed, we'd just put everything in a max-heap keyed by `(-priority, eventId)` (negated so Java's min-heap acts like a max-heap). Each `pollHighest()` would just pop the top. Simple.

**Step 2: What breaks with updates?**

A standard heap doesn't support efficient removal of arbitrary elements. If event 9's priority changes from 4 → 7, we can't find its old `(-4, 9)` entry in the heap to remove it. We'd have to scan the whole heap: O(n).

**Step 3: We need a data structure that supports O(log n) removal of arbitrary elements.**

That's a **TreeSet** (`java.util.TreeSet`). A `TreeSet` is a self-balancing BST (Red-Black Tree). It supports:
- `add(element)` — O(log n)
- `remove(element)` — O(log n), finds by value
- `first()` / `pollFirst()` — O(log n), the "min" element

**Step 4: What do we store in the TreeSet?**

We want the *highest* priority to come first. `TreeSet` is a min-structure by default. So we store `(-priority, eventId)` as a 2-element int array. The TreeSet's comparator sorts:
1. First by `-priority` ascending (so highest priority = most negative = first)
2. Then by `eventId` ascending (smallest id breaks ties)

**Step 5: How does update work?**

When priority changes, we:
1. Look up the old priority from a `HashMap<eventId → priority>`
2. Remove `(-oldPriority, eventId)` from the TreeSet — O(log n)
3. Add `(-newPriority, eventId)` — O(log n)
4. Update the HashMap

**Why this intuition works:**

The TreeSet maintains a globally sorted order of `(negated-priority, eventId)` at all times. Any structural change (add, remove, update) costs O(log n). The "highest priority, smallest id" event is always the first element. There's no lazy deletion, no stale entries — the set is always clean.

---

## Code Mapping

| Problem Requirement | Java Code Section |
|---|---|
| Always retrieve highest priority, smallest id on tie | `TreeSet` comparator: sort by `a[0] - b[0]` (negated priority), then `a[1] - b[1]` (eventId) |
| Update priority of an active event efficiently | `sl.remove({-old, id})` then `sl.add({-new, id})` — O(log n) each |
| Return -1 when no active events | `if (sl.isEmpty()) return -1` |
| Remove event permanently after poll | `sl.pollFirst()` removes from set; `d.remove(eventId)` removes from map |
| Track current priority per event for O(1) lookup during update | `HashMap<Integer, Integer> priorityMap` |

---

## Final Java Code

```java
import java.util.*;

class EventManager {

    // Sorted set stores int[] {-priority, eventId}
    // Sorted ascending by -priority (so highest priority comes first),
    // then ascending by eventId (so smallest id breaks ties).
    private TreeSet<int[]> sortedActiveEvents;

    // Maps eventId → current priority, needed to look up old priority during updates
    // without which we can't find and remove the old entry from the TreeSet.
    private Map<Integer, Integer> eventIdToPriority;

    public EventManager(int[][] events) {

        // Comparator: first compare by -priority (ascending = highest priority first),
        // then by eventId (ascending = smallest id first on tie).
        sortedActiveEvents = new TreeSet<>((firstEvent, secondEvent) -> {
            if (firstEvent[0] != secondEvent[0]) {
                // Both values are negated priorities, so smaller value = higher actual priority
                return firstEvent[0] - secondEvent[0];
            }
            // Same priority: prefer smaller eventId
            return firstEvent[1] - secondEvent[1];
        });

        eventIdToPriority = new HashMap<>();

        for (int[] event : events) {
            int eventId = event[0];
            int priority = event[1];

            // Store as (-priority, eventId) so min-ordered TreeSet acts like a max-priority structure
            sortedActiveEvents.add(new int[]{-priority, eventId});
            eventIdToPriority.put(eventId, priority);
        }
    }

    public void updatePriority(int eventId, int newPriority) {

        // Step 1: Retrieve the old priority to locate the existing TreeSet entry
        int oldPriority = eventIdToPriority.get(eventId);

        // Step 2: Remove the stale entry from the TreeSet using the old priority
        // TreeSet.remove() uses the comparator to find the exact element — O(log n)
        sortedActiveEvents.remove(new int[]{-oldPriority, eventId});

        // Step 3: Insert the updated entry with the new priority
        sortedActiveEvents.add(new int[]{-newPriority, eventId});

        // Step 4: Reflect the new priority in our lookup map
        eventIdToPriority.put(eventId, newPriority);
    }

    public int pollHighest() {

        // No active events remain
        if (sortedActiveEvents.isEmpty()) {
            return -1;
        }

        // pollFirst() removes and returns the smallest element per comparator:
        // = highest priority event, smallest id on tie
        int[] highestPriorityEvent = sortedActiveEvents.pollFirst();
        int eventId = highestPriorityEvent[1];

        // Clean up the priority map so this eventId is no longer considered active
        eventIdToPriority.remove(eventId);

        return eventId;
    }
}

/**
 * Your EventManager object will be instantiated and called as such:
 * EventManager obj = new EventManager(events);
 * obj.updatePriority(eventId, newPriority);
 * int param_2 = obj.pollHighest();
 */
```

---

## Complexity Analysis

**Time Complexity:**
- **Constructor:** $O(n \log n)$ — each of the $n$ initial events is inserted into the TreeSet, which costs $O(\log n)$ per insertion.
- **`updatePriority`:** $O(\log n)$ — one TreeSet `remove` + one `add`, each $O(\log n)$. HashMap operations are $O(1)$.
- **`pollHighest`:** $O(\log n)$ — `pollFirst()` on a TreeSet is $O(\log n)$. HashMap `remove` is $O(1)$.

**Space Complexity:**
- $O(n)$ — the TreeSet and HashMap together store one entry per active event.

---

## Similar Problems

- [**LC 295 — Find Median from Data Stream**](https://leetcode.com/problems/find-median-from-data-stream/) — same pattern of maintaining a dynamically ordered structure that supports O(log n) insertions and queries on extremes.
- [**LC 355 — Design Twitter**](https://leetcode.com/problems/design-twitter/) — design problem requiring a priority queue over dynamic data with per-user filtering.
- [**LC 1942 — The Number of the Smallest Unoccupied Chair**](https://leetcode.com/problems/the-number-of-the-smallest-unoccupied-chair/) — TreeSet used to efficiently track and reclaim the minimum available element.
- [**LC 218 — The Skyline Problem**](https://leetcode.com/problems/the-skyline-problem/) — TreeSet used to maintain a dynamic set of active heights with O(log n) add/remove/max.
