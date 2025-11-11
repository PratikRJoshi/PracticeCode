# Minimum Interval to Include Each Query

## Problem Description

**Problem Link:** [Minimum Interval to Include Each Query](https://leetcode.com/problems/minimum-interval-to-include-each-query/)

You are given a 2D integer array `intervals`, where `intervals[i] = [lefti, righti]` describes the `i`th interval starting at `lefti` and ending at `righti` (inclusive). The size of an interval is defined as the number of integers it contains, or more formally `righti - lefti + 1`.

You are also given an integer array `queries`. The answer to the `j`th query is the size of the smallest interval such that `lefti <= queries[j] <= righti`. If no such interval exists, the answer is `-1`.

Return *an array `answer` of size `queries.length` where `answer[j]` is the answer to the `j`th query*.

**Example 1:**

```
Input: intervals = [[1,4],[2,4],[3,6],[4,4]], queries = [2,3,4,5]
Output: [3,3,1,4]
Explanation: The queries are processed as follows:
- Query = 2: The interval [2,4] is the smallest interval containing 2. The answer is 4 - 2 + 1 = 3.
- Query = 3: The interval [2,4] is the smallest interval containing 3. The answer is 4 - 2 + 1 = 3.
- Query = 4: The interval [4,4] is the smallest interval containing 4. The answer is 4 - 4 + 1 = 1.
- Query = 5: The interval [3,6] is the smallest interval containing 5. The answer is 6 - 3 + 1 = 4.
```

**Example 2:**

```
Input: intervals = [[2,3],[2,5],[1,8],[20,25]], queries = [2,19,5,22]
Output: [2,-1,4,6]
Explanation: The queries are processed as follows:
- Query = 2: The interval [2,3] is the smallest interval containing 2. The answer is 3 - 2 + 1 = 2.
- Query = 19: None of the intervals contain 19. The answer is -1.
- Query = 5: The interval [2,5] is the smallest interval containing 5. The answer is 5 - 2 + 1 = 4.
- Query = 22: The interval [20,25] is the smallest interval containing 22. The answer is 25 - 20 + 1 = 6.
```

**Constraints:**
- `1 <= intervals.length <= 10^5`
- `1 <= queries.length <= 10^5`
- `intervals[i].length == 2`
- `1 <= lefti <= righti <= 10^7`
- `1 <= queries[j] <= 10^7`

## Intuition/Main Idea

This problem requires finding the smallest interval that contains each query point. We can use a **sweep line algorithm** with a **priority queue**.

**Key Insight:** 
1. Sort intervals by start position.
2. Sort queries with their original indices.
3. For each query, add all intervals that start <= query to a min-heap (ordered by interval size).
4. Remove intervals that end < query (they don't contain the query).
5. The top of the heap is the smallest interval containing the query.

**Core Algorithm:**
1. Sort intervals by start position.
2. Create query-index pairs and sort by query value.
3. Use a min-heap to store intervals (ordered by size: right - left + 1).
4. Sweep through queries: add valid intervals, remove invalid ones, get minimum.

**Why this works:** By processing queries in order and maintaining a heap of valid intervals, we efficiently find the minimum size interval for each query.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Sort intervals by start | Sorting - Line 8 |
| Create query-index pairs | Query array creation - Lines 10-13 |
| Sort queries | Query sorting - Line 14 |
| Min-heap for intervals | PriorityQueue - Line 16 |
| Sweep through queries | For loop - Line 18 |
| Add intervals starting <= query | While loop - Lines 20-23 |
| Remove intervals ending < query | While loop - Lines 25-27 |
| Get minimum interval size | Heap peek - Line 29 |
| Store result | Result array - Line 31 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    public int[] minInterval(int[][] intervals, int[] queries) {
        int n = intervals.length;
        int m = queries.length;
        
        // Sort intervals by start position
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        
        // Create query-index pairs and sort by query value
        int[][] queryPairs = new int[m][2];
        for (int i = 0; i < m; i++) {
            queryPairs[i] = new int[]{queries[i], i};
        }
        Arrays.sort(queryPairs, (a, b) -> a[0] - b[0]);
        
        // Min-heap: [size, end] - ordered by interval size
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        
        int[] result = new int[m];
        int intervalIndex = 0;
        
        // Process queries in sorted order
        for (int[] queryPair : queryPairs) {
            int query = queryPair[0];
            int originalIndex = queryPair[1];
            
            // Add all intervals that start <= query
            while (intervalIndex < n && intervals[intervalIndex][0] <= query) {
                int[] interval = intervals[intervalIndex];
                int size = interval[1] - interval[0] + 1;
                pq.offer(new int[]{size, interval[1]});
                intervalIndex++;
            }
            
            // Remove intervals that end < query (don't contain query)
            while (!pq.isEmpty() && pq.peek()[1] < query) {
                pq.poll();
            }
            
            // Get minimum size interval that contains query
            result[originalIndex] = pq.isEmpty() ? -1 : pq.peek()[0];
        }
        
        return result;
    }
}
```

**Explanation of Key Code Sections:**

1. **Sort Intervals (Line 8):** We sort intervals by their start position. This allows us to process them in order as we sweep through queries.

2. **Create Query Pairs (Lines 10-13):** We pair each query with its original index so we can place the answer in the correct position later.

3. **Sort Queries (Line 14):** We sort queries by their value. This enables the sweep line approach - we process queries in order and maintain valid intervals.

4. **Min-Heap Setup (Line 16):** We use a priority queue that orders intervals by size (smallest first). Each entry stores `[size, end]` where size = `right - left + 1`.

5. **Sweep Line Algorithm (Lines 18-31):**
   - **Add Valid Intervals (Lines 20-23):** For the current query, add all intervals that start <= query. These intervals might contain the query.
   - **Remove Invalid Intervals (Lines 25-27):** Remove intervals that end < query. These intervals don't contain the query.
   - **Get Minimum (Line 29):** The top of the heap is the smallest interval containing the query (if heap is not empty).

6. **Store Result (Line 31):** Store the answer at the original index of the query.

**Why sweep line works:**
- **Monotonicity:** As queries increase, intervals that were valid remain valid (if they haven't been removed).
- **Efficiency:** We process each interval and query once, giving O(n log n + m log m) time.
- **Correctness:** By maintaining a heap of valid intervals ordered by size, we always get the minimum.

**Example walkthrough:**
- Intervals: [[1,4],[2,4],[3,6],[4,4]], sorted: same
- Queries: [2,3,4,5], sorted: [2,3,4,5]
- Query 2: Add [1,4], [2,4] → Heap: [[3,4],[3,4]] → Min: 3
- Query 3: Add [3,6] → Heap: [[3,4],[3,4],[4,6]] → Min: 3
- Query 4: Add [4,4], remove [1,4] (end < 4) → Heap: [[3,4],[4,6],[1,4]] → Min: 1
- Query 5: Remove [2,4] (end < 5) → Heap: [[4,6]] → Min: 4

## Complexity Analysis

- **Time Complexity:** $O(n \log n + m \log m)$ where $n$ is the number of intervals and $m$ is the number of queries. Sorting takes $O(n \log n + m \log m)$, and processing takes $O(n + m)$ with heap operations.

- **Space Complexity:** $O(n + m)$ for the heap, query pairs, and result array.

## Similar Problems

Problems that can be solved using similar sweep line and priority queue patterns:

1. **1851. Minimum Interval to Include Each Query** (this problem) - Sweep line with heap
2. **56. Merge Intervals** - Interval merging
3. **57. Insert Interval** - Interval insertion
4. **253. Meeting Rooms II** - Interval scheduling with heap
5. **435. Non-overlapping Intervals** - Interval selection
6. **452. Minimum Number of Arrows to Burst Balloons** - Interval covering
7. **1288. Remove Covered Intervals** - Interval coverage
8. **759. Employee Free Time** - Interval merging
9. **1229. Meeting Scheduler** - Interval intersection
10. **218. The Skyline Problem** - Sweep line with heap

