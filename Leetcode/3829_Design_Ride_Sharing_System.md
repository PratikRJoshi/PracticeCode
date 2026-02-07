# Design Ride Sharing System

## Problem Description

**Problem Link:** [Design Ride Sharing System](https://leetcode.com/problems/design-ride-sharing-system/)

A ride sharing system manages ride requests from riders and availability from drivers.

Riders request rides, and drivers become available over time. The system should match riders and drivers in the order they arrive.

Implement the `RideSharingSystem` class:

- `RideSharingSystem()` Initializes the system.
- `void addRider(int riderId)` Adds a new rider with the given `riderId`.
- `void addDriver(int driverId)` Adds a new driver with the given `driverId`.
- `int[] matchDriverWithRider()` Matches the earliest available driver with the earliest waiting rider and removes both of them from the system.
  - Returns `[driverId, riderId]` if a match is made.
  - If no match is available, returns `[-1, -1]`.
- `void cancelRider(int riderId)` Cancels the ride request of the rider with the given `riderId` if the rider exists and has not yet been matched.

**Example 1:**
```
Input:
["RideSharingSystem", "addRider", "addDriver", "addRider", "matchDriverWithRider", "addDriver", "cancelRider", "matchDriverWithRider", "matchDriverWithRider"]
[[], [3], [2], [1], [], [5], [3], [], []]
Output:
[null, null, null, null, [2, 3], null, null, [5, 1], [-1, -1]]
```

**Example 2:**
```
Input:
["RideSharingSystem", "addRider", "addDriver", "addDriver", "matchDriverWithRider", "addRider", "cancelRider", "matchDriverWithRider"]
[[], [8], [8], [6], [], [2], [2], []]
Output:
[null, null, null, null, [8, 8], null, null, [-1, -1]]
```

**Constraints:**
- `1 <= riderId, driverId <= 1000`
- Each `riderId` is unique among riders and is added at most once.
- Each `driverId` is unique among drivers and is added at most once.
- At most `1000` calls will be made in total.

## Intuition/Main Idea

This is a classic **FIFO matching** problem:

- Riders arrive over time and wait in a queue.
- Drivers arrive over time and wait in a queue.
- A match always pairs:
  - earliest waiting driver
  - with earliest waiting rider

So we need:

- A queue of drivers (simple FIFO)
- A queue of riders (FIFO), but with a twist:
  - Riders can be cancelled while they are waiting.

Because cancellations can occur for riders who might be somewhere in the middle of the queue, we use a common technique:

- Keep riders in a queue as usual.
- Also keep a boolean array `isRiderWaiting[riderId]`.
  - When a rider is added: set `true`.
  - When a rider is canceled or matched: set `false`.
- When matching, we “clean up” the front of the rider queue:
  - While the front rider is no longer waiting (`false`), pop and discard it.

This avoids expensive removals from the middle of the queue.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Match earliest driver with earliest rider | Two FIFO queues + cleanup before matching (lines 34-59) |
| `cancelRider(riderId)` cancels only if still waiting | `isRiderWaiting[riderId]` flag toggled to false (lines 61-67) |
| Return `[-1, -1]` if no match | Check cleaned rider queue and driver queue emptiness (lines 44-58) |
| Constraints: ids up to 1000 | Use fixed-size boolean array of length 1001 (lines 16-22) |

## Final Java Code & Learning Pattern (Full Content)

```java
import java.util.ArrayDeque;
import java.util.Queue;

class RideSharingSystem {
    private final Queue<Integer> waitingRiders;
    private final Queue<Integer> availableDrivers;

    // riderId is in [1..1000], so we can store waiting status in an array.
    // This makes cancel O(1).
    private final boolean[] isRiderWaiting;

    public RideSharingSystem() {
        this.waitingRiders = new ArrayDeque<>();
        this.availableDrivers = new ArrayDeque<>();
        this.isRiderWaiting = new boolean[1001];
    }

    public void addRider(int riderId) {
        // Rider joins the queue.
        waitingRiders.offer(riderId);
        isRiderWaiting[riderId] = true;
    }

    public void addDriver(int driverId) {
        // Driver becomes available and joins the queue.
        availableDrivers.offer(driverId);
    }

    public int[] matchDriverWithRider() {
        // First, remove any riders from the front who are no longer waiting
        // (they were canceled or already matched earlier).
        while (!waitingRiders.isEmpty() && !isRiderWaiting[waitingRiders.peek()]) {
            waitingRiders.poll();
        }

        // If either side is empty, no match can be made.
        if (availableDrivers.isEmpty() || waitingRiders.isEmpty()) {
            return new int[] {-1, -1};
        }

        // Match earliest driver with earliest rider.
        int driverId = availableDrivers.poll();
        int riderId = waitingRiders.poll();

        // Rider is no longer waiting because they are now matched.
        isRiderWaiting[riderId] = false;

        return new int[] {driverId, riderId};
    }

    public void cancelRider(int riderId) {
        // Only affects riders who are still waiting.
        // If they were never added or already matched, this will already be false.
        isRiderWaiting[riderId] = false;
    }
}
```

## Complexity Analysis

**Time Complexity:**
- `addRider`: $O(1)$
- `addDriver`: $O(1)$
- `cancelRider`: $O(1)$
- `matchDriverWithRider`: Amortized $O(1)$
  - Each canceled rider is removed from the queue at most once during cleanup.

**Space Complexity:** $O(R + D)$ where `R` is riders waiting in the queue and `D` is drivers waiting in the queue (plus `O(1)` fixed boolean array of size 1001).

## Similar Problems

- [Design Underground System](https://leetcode.com/problems/design-underground-system/) - Queue/map based system design
- [Design Twitter](https://leetcode.com/problems/design-twitter/) - Data structure + ordering constraints
- [Design Hit Counter](https://leetcode.com/problems/design-hit-counter/) - FIFO queue + cleanup pattern
