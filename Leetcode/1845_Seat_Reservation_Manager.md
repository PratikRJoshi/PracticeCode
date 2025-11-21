# Seat Reservation Manager

## Problem Description

**Problem Link:** [Seat Reservation Manager](https://leetcode.com/problems/seat-reservation-manager/)

Design a system that manages the reservation state of `n` seats that are numbered from `1` to `n`.

Implement the `SeatManager` class:

- `SeatManager(int n)` Initializes a `SeatManager` object that will manage `n` seats numbered from `1` to `n`. All seats are initially available.
- `int reserve()` Fetches the **smallest-numbered** unreserved seat, reserves it, and returns its number.
- `void unreserve(int seatNumber)` Unreserves the seat with the given `seatNumber`.

**Example 1:**
```
Input
["SeatManager", "reserve", "reserve", "unreserve", "reserve", "reserve", "reserve", "reserve", "unreserve"]
[[5], [], [], [2], [], [], [], [], [5]]
Output
[null, 1, 2, null, 2, 3, 4, 5, null]

Explanation
SeatManager seatManager = new SeatManager(5); // Initializes a SeatManager with 5 seats.
seatManager.reserve();    // All seats are available, so return the lowest numbered seat, which is 1.
seatManager.reserve();    // The available seats are [2,3,4,5], so return the lowest, which is 2.
seatManager.unreserve(2); // Unreserve seat 2, so now the available seats are [2,3,4,5].
seatManager.reserve();    // The available seats are [2,3,4,5], so return the lowest, which is 2.
seatManager.reserve();    // The available seats are [3,4,5], so return the lowest, which is 3.
seatManager.reserve();    // The available seats are [4,5], so return the lowest, which is 4.
seatManager.reserve();    // The only available seat is seat 5, so return 5.
seatManager.unreserve(5); // Unreserve seat 5, so now the available seats are [5].
```

**Constraints:**
- `1 <= n <= 10^5`
- `1 <= seatNumber <= n`
- For each call to `reserve`, it is guaranteed that there will be at least one unreserved seat.
- For each call to `unreserve`, `seatNumber` is guaranteed to have been reserved.
- At most `10^5` calls will be made in total to `reserve` and `unreserve`.

## Intuition/Main Idea

We need to efficiently find the smallest available seat number. A min-heap is perfect for this.

**Core Algorithm:**
- Use a min-heap to store available seat numbers
- Initialize heap with all seats 1 to n
- `reserve()`: Pop from heap (smallest available)
- `unreserve()`: Push seat back to heap

**Why min-heap:** We need to quickly get the smallest available seat. A min-heap provides O(log n) operations for both reserve and unreserve.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Initialize n seats | Constructor - Lines 5-9 |
| Reserve smallest seat | Heap poll - Lines 12-14 |
| Unreserve seat | Heap offer - Lines 17-19 |

## Final Java Code & Learning Pattern (Full Content)

```java
class SeatManager {
    // Min-heap to store available seat numbers
    // Smallest seat number is always at the top
    private PriorityQueue<Integer> availableSeats;
    
    public SeatManager(int n) {
        // Initialize heap with all seats from 1 to n
        availableSeats = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            availableSeats.offer(i);
        }
    }
    
    public int reserve() {
        // Get and remove the smallest available seat number
        return availableSeats.poll();
    }
    
    public void unreserve(int seatNumber) {
        // Add the seat back to available seats
        availableSeats.offer(seatNumber);
    }
}
```

## Complexity Analysis

**Time Complexity:**
- Constructor: $O(n \log n)$ to insert n seats
- `reserve()`: $O(\log n)$ for heap poll
- `unreserve()`: $O(\log n)$ for heap offer

**Space Complexity:** $O(n)$ for the heap.

## Similar Problems

- [Smallest Number in Infinite Set](https://leetcode.com/problems/smallest-number-in-infinite-set/) - Similar heap-based design
- [Design Twitter](https://leetcode.com/problems/design-twitter/) - Design problem with data structures
- [Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/) - Heap-based design

