# 3885. Design Event Manager

**Link:** [https://leetcode.com/problems/design-event-manager/](https://leetcode.com/problems/design-event-manager/)

**Difficulty:** Medium

---

## Problem Description

You are given an initial list of events. Each event has:
- a unique `eventId`
- a `priority`

Design an `EventManager` with these operations:

- `EventManager(int[][] events)`  
  Initialize the manager using `events[i] = [eventId_i, priority_i]`.
- `void updatePriority(int eventId, int newPriority)`  
  Update the priority of an active event.
- `int pollHighest()`  
  Remove and return the active event with:
  1. highest priority
  2. if tied, smallest `eventId`  
  Return `-1` if no active event exists.

An event is active until removed by `pollHighest()`.

### Example 1

```text
Input:
["EventManager", "pollHighest", "updatePriority", "pollHighest", "pollHighest"]
[[[[5, 7], [2, 7], [9, 4]]], [], [9, 7], [], []]

Output:
[null, 2, null, 5, 9]
```

Why:
- Initial highest priority is 7 for event 5 and 2 -> tie breaks by smaller id -> return 2
- Update event 9 to priority 7
- Remaining highest priority is now event 5 and 9 -> return 5 first
- Next return 9

### Example 2

```text
Input:
["EventManager", "pollHighest", "pollHighest", "pollHighest"]
[[[[4, 1], [7, 2]]], [], [], []]

Output:
[null, 7, 4, -1]
```

### Constraints

- `1 <= events.length <= 10^5`
- `events[i] = [eventId, priority]`
- `1 <= eventId <= 10^9`
- `1 <= priority <= 10^9`
- All initial `eventId` values are unique.
- `1 <= newPriority <= 10^9`
- `updatePriority` is always called on an active event.
- Total calls to `updatePriority` and `pollHighest` are at most `10^5`.

---

## Intuition/Main Idea

### What are we optimizing for?

Every time we call `pollHighest()`, we need the best event quickly:
1. maximum priority
2. smallest id among ties

And we also need to support frequent priority updates.

### Build intuition incrementally

**Step 1: If there were no updates**  
A max-heap would be enough: top gives the highest priority event immediately.

**Step 2: Why updates make it tricky**  
If event `9` changes from priority `4` to `7`, the old heap entry is now stale.  
Java `PriorityQueue` cannot remove an arbitrary non-top entry in `O(log n)`; direct removal can degrade to `O(n)`.

**Step 3: Combine two structures**

Use:
- `HashMap<Integer, Integer> eventToPriority` -> current valid priority of each active event
- `PriorityQueue<int[]> maxHeap` -> candidate events ordered by:
  - higher priority first
  - smaller id first for ties

Each heap entry is `[priority, eventId]`.

**Step 4: Lazy deletion (key idea)**

When priority updates:
- do **not** search and delete old heap entry
- just push new `[newPriority, eventId]` into heap
- update hash map

During `pollHighest()`:
- keep removing heap top while it is stale:
  - event already removed from map, or
  - heap priority does not match current map priority
- first valid top is the true answer

### Why this intuition works

The hash map is the source of truth for active/current state.  
The priority queue is a fast candidate generator.  
Even if stale entries exist, they are filtered exactly when they reach the top.  
Each stale entry is removed once, so total extra work is amortized, keeping operations efficient.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| `@Use HashMap` for active latest priorities | `Map<Integer, Integer> eventToPriority` and updates in constructor / `updatePriority` / `pollHighest` |
| `@Use PriorityQueue` instead of TreeSet | `PriorityQueue<int[]> maxHeap` with custom comparator |
| Highest priority, then smallest id | Comparator: `second[0] - first[0]`, then `first[1] - second[1]` |
| Efficient priority updates | `eventToPriority.put(eventId, newPriority)` + `maxHeap.offer(new int[]{newPriority, eventId})` |
| Remove stale heap entries safely | `discardStaleTopEntries()` before polling answer |
| Return `-1` when no active events | Check `eventToPriority.isEmpty()` (or heap exhausted after cleanup) |

---

## Final Java Code & Learning Pattern (Full Content)

```java
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class EventManager {

    // Source of truth:
    // active eventId -> latest priority
    private final Map<Integer, Integer> eventToPriority;

    // Candidate ordering structure:
    // [priority, eventId], sorted so top = highest priority,
    // and for ties, smallest eventId.
    private final PriorityQueue<int[]> maxHeap;

    public EventManager(int[][] events) {
        eventToPriority = new HashMap<>();

        maxHeap = new PriorityQueue<>((first, second) -> {
            if (first[0] != second[0]) {
                // Higher priority should come first.
                return Integer.compare(second[0], first[0]);
            }
            // For equal priority, smaller id should come first.
            return Integer.compare(first[1], second[1]);
        });

        for (int[] event : events) {
            int eventId = event[0];
            int priority = event[1];

            eventToPriority.put(eventId, priority);
            maxHeap.offer(new int[]{priority, eventId});
        }
    }

    public void updatePriority(int eventId, int newPriority) {
        // Update source of truth to latest priority.
        eventToPriority.put(eventId, newPriority);

        // Lazy deletion pattern:
        // keep old entries in heap; push newest state.
        maxHeap.offer(new int[]{newPriority, eventId});
    }

    public int pollHighest() {
        // Quick fail when no active events remain.
        if (eventToPriority.isEmpty()) {
            return -1;
        }

        discardStaleTopEntries();

        // Defensive check in case heap got exhausted after cleanup.
        if (maxHeap.isEmpty()) {
            return -1;
        }

        int[] top = maxHeap.poll();
        int eventId = top[1];

        // Mark event as inactive after returning it.
        eventToPriority.remove(eventId);

        return eventId;
    }

    private void discardStaleTopEntries() {
        while (!maxHeap.isEmpty()) {
            int[] top = maxHeap.peek();
            int priorityFromHeap = top[0];
            int eventId = top[1];

            Integer currentPriority = eventToPriority.get(eventId);

            // Stale if event already removed,
            // or this entry does not reflect latest priority.
            if (currentPriority == null || currentPriority != priorityFromHeap) {
                maxHeap.poll();
                continue;
            }

            // Top is valid and current.
            break;
        }
    }
}

/**
 * Your EventManager object will be instantiated and called as such:
 * EventManager obj = new EventManager(events);
 * obj.updatePriority(eventId, newPriority);
 * int param_2 = obj.pollHighest();
 */
```

### Learning Pattern

This is a classic **HashMap + Heap with lazy deletion** pattern:
- Use a map for latest valid state.
- Use a heap for fast best-candidate retrieval.
- Never pay expensive random removal from heap.
- Clean stale entries only when they reach heap top.

This pattern is reusable in many design and streaming problems where updates are frequent.

---

## Complexity Analysis

- **Constructor:** `O(n log n)` for inserting all events into heap.
- **updatePriority:** `O(log n)` due to one heap insertion.
- **pollHighest:** amortized `O(log n)` (each stale entry is popped at most once overall).
- **Space:** `O(n + u)` where `u` is number of updates that have introduced stale heap entries (commonly reported as `O(n + operations)` worst-case).

---

## Similar Problems

- [LC 355 - Design Twitter](https://leetcode.com/problems/design-twitter/)  
  Uses heap-based ranking with recency/priority style ordering.
- [LC 621 - Task Scheduler](https://leetcode.com/problems/task-scheduler/)  
  Priority queue to repeatedly select highest-urgency work.
- [LC 703 - Kth Largest Element in a Stream](https://leetcode.com/problems/kth-largest-element-in-a-stream/)  
  Streaming design with heap maintenance.
- [LC 1675 - Minimize Deviation in Array](https://leetcode.com/problems/minimize-deviation-in-array/)  
  Uses ordered retrieval and repeated updates from priority structure.
