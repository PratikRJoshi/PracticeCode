### LeetCode 630: Course Schedule III
Problem: https://leetcode.com/problems/course-schedule-iii/description/

## Problem Statement

There are `n` different online courses numbered from `1` to `n`. You are given an array `courses` where `courses[i] = [durationi, lastDayi]` indicates that the `i`th course has a duration of `durationi` and must be finished on or before `lastDayi`.

You will start on the `1`st day and you cannot take two or more courses simultaneously.

Return the maximum number of courses that you can take.

---

## Intuition and Main Idea

This problem is different from the other "Course Schedule" problems. It's not about graph topology (prerequisites), but about optimal scheduling under constraints. This points towards a **greedy algorithm**.

Let's think about how to make a greedy choice. What's the most restrictive constraint? The `lastDay`. A course with an early deadline is harder to fit in than one with a late deadline. This gives us our first key insight:

**Insight 1: Sort courses by their deadlines (`lastDayi`).**
By processing courses in increasing order of their deadlines, we prioritize the ones that will expire soonest.

Now, we iterate through the sorted courses and decide whether to take each one. Let's maintain a running total of the time we've spent so far, `totalTime`.

For each course, we try to take it:
1.  Add its `duration` to `totalTime`.
2.  If `totalTime` is still within the course's `lastDay`, great! We've successfully added a new course.
3.  What if `totalTime` exceeds the `lastDay`? We've scheduled too much time. We must drop a course we've previously committed to. To maximize our chances of fitting more courses later, which one should we drop? The one that took the longest time. Dropping the longest course frees up the most time, giving us the best opportunity to fit the current (and future) courses.

**Insight 2: Use a Max Priority Queue to track durations.**
If we need to drop a course, we should drop the one with the longest duration among those we've already taken. A max priority queue is the perfect data structure to keep track of the durations of the courses we've taken and quickly find the maximum.

---

## Algorithm: Greedy with a Priority Queue

1.  **Sort:** Sort the `courses` array based on the `lastDay` (the second element) in ascending order.

2.  **Initialize:**
    *   Initialize `totalTime = 0` to track the cumulative duration of courses taken.
    *   Initialize a `Max Priority Queue` to store the durations of the courses we decide to take.

3.  **Iterate and Schedule:**
    *   Loop through each `course` in the sorted array.
    *   **Attempt to take the course:**
        *   Add the course's `duration` to `totalTime`.
        *   Push the course's `duration` into the max priority queue.
    *   **Check for conflict:**
        *   If `totalTime` now exceeds the course's `lastDay`, we have a conflict.
        *   To resolve it, we must drop the longest course taken so far. Poll the max element from the priority queue (let's call it `longestDuration`).
        *   Subtract `longestDuration` from `totalTime`.

4.  **Conclusion:**
    *   After iterating through all the courses, the number of courses we were able to take is simply the number of elements remaining in the priority queue.
    *   Return the size of the priority queue.

---

## Java Implementation

```java
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Collections;

class Solution {
    public int scheduleCourse(int[][] courses) {
        // 1. Sort courses by their deadlines
        Arrays.sort(courses, (a, b) -> a[1] - b[1]);

        // 2. Initialize
        // Max Priority Queue to store durations of courses taken
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        int totalTime = 0;

        // 3. Iterate and Schedule
        for (int[] course : courses) {
            int duration = course[0];
            int lastDay = course[1];

            // Attempt to take the current course
            totalTime += duration;
            pq.offer(duration);

            // Check for conflict: If we exceed the deadline
            if (totalTime > lastDay) {
                // Drop the longest course taken so far
                int longestDuration = pq.poll();
                totalTime -= longestDuration;
            }
        }

        // 4. The size of the PQ is the number of courses we could take
        return pq.size();
    }
}
```

---

## Complexity Analysis

Let `n` be the number of courses.

*   **Time Complexity: O(n log n)**
    *   **Sorting:** The initial sort of the `courses` array takes **O(n log n)** time. This is the dominant part of the algorithm.
    *   **Iteration:** We iterate through each of the `n` courses once.
    *   **Priority Queue Operations:** In each iteration, we perform an `offer` (add) and potentially a `poll` (remove). Both operations on a priority queue take O(log k) time, where `k` is the number of elements in the queue. Since `k <= n`, each operation is at most O(log n).
    *   The loop contributes O(n log n) in total.
    *   Overall complexity is O(n log n) + O(n log n) = **O(n log n)**.

*   **Space Complexity: O(n)**
    *   The space complexity is determined by the priority queue. In the worst case, we might be able to take all `n` courses, so the priority queue would store `n` durations. This requires **O(n)** space.
