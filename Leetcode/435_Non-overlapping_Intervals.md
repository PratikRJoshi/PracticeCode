### 435. Non-overlapping Intervals
### Problem Link: [Non-overlapping Intervals](https://leetcode.com/problems/non-overlapping-intervals/)
### Intuition
This problem asks us to find the minimum number of intervals to remove to make the remaining intervals non-overlapping. This is a classic interval scheduling problem.

The key insight is to use a greedy approach: sort the intervals by their end times and then iterate through them, keeping track of the end time of the last included interval. If the current interval overlaps with the last included interval, we remove it. Otherwise, we include it and update our end time.

Sorting by end time is crucial because it allows us to fit in as many intervals as possible. By choosing intervals that end earlier, we leave more room for future intervals.

### Java Reference Implementation
```java
class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        int count = 0;
        int end = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            // If current interval overlaps with the last included interval
            if (intervals[i][0] < end) {
                // Keeping the interval that ends earlier
                // Remove the interval that ends later
                count++;
                end = Math.min(end, intervals[i][1]);
            } else {
                // No overlap, update end time
                end = intervals[i][1];
            }
        }
        
        return count;
    }
}
```

### Understanding the Algorithm and Boundary Checks

1. **Greedy Approach with End Time Sorting:**
   - We sort intervals by their end times
   - This allows us to fit in as many intervals as possible
   - By choosing intervals that end earlier, we leave more room for future intervals

2. **Overlap Check (`intervals[i][0] < end`):**
   - If the start time of the current interval is less than the end time of the last included interval, they overlap
   - In this case, we remove the current interval (increment count)
   - Otherwise, we include the current interval and update our end time

3. **Why Sort by End Time vs. Start Time:**
   - Sorting by end time is more efficient for this problem
   - When we encounter an overlap, we always remove the current interval
   - When sorting by start time, we need to decide which interval to remove (the one with the later end time)

4. **Edge Cases:**
   - Empty array: Return 0 (no intervals to remove)
   - Single interval: Return 0 (a single interval cannot overlap with itself)

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (intervals == null || intervals.length == 0) { return 0; }` - Return 0 for empty arrays
- **R1 (Sort intervals)**: `Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));` - Sort by end time
- **R2 (Initialize count)**: `int count = 0;` - Count of intervals to remove
- **R3 (Initialize end time)**: `int end = intervals[0][1];` - End time of the first interval
- **R4 (Iterate through intervals)**: Process each interval in sorted order
- **R5 (Check for overlap)**: `if (intervals[i][0] < end)` - Check if current interval overlaps with last included interval
- **R6 (Remove interval)**: `count++;` - Increment count when we need to remove an interval
- **R7 (Update end time)**: `end = intervals[i][1];` - Update end time when including an interval
- **R8 (Return result)**: `return count;` - Return the minimum number of intervals to remove

### Complexity Analysis
- **Time Complexity**: O(n log n)
  - Sorting the intervals takes O(n log n) time
  - The subsequent linear scan through the sorted intervals takes O(n) time
  - Overall: O(n log n)

- **Space Complexity**: O(1) or O(n)
  - If we're allowed to sort the input array in-place, the space complexity is O(1)
  - If we need to create a copy of the input array for sorting, the space complexity is O(n)
  - The sorting algorithm itself might use O(log n) space for the recursion stack

### Related Problems
- **Merge Intervals** (Problem 56): Merge all overlapping intervals
- **Insert Interval** (Problem 57): Insert a new interval into a set of non-overlapping intervals
- **Meeting Rooms II** (Problem 253): Find the minimum number of conference rooms required
