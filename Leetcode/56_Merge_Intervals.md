### 56. Merge Intervals
### Problem Link: [Merge Intervals](https://leetcode.com/problems/merge-intervals/)
### Intuition
This problem asks us to merge all overlapping intervals in a collection of intervals. Two intervals overlap if one interval's start point falls within another interval.

The key insight is to first sort the intervals by their start times. Once sorted, we can iterate through the intervals and merge overlapping ones. If the current interval overlaps with the last interval in our result list, we merge them by updating the end time of the last interval. Otherwise, we add the current interval to the result list.

### Java Reference Implementation
```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        List<int[]> result = new ArrayList<>();
        int[] currentInterval = intervals[0];
        result.add(currentInterval);
        
        for (int[] interval : intervals) {
            // If current interval overlaps with the last interval in result
            if (interval[0] <= currentInterval[1]) {
                // Merge the intervals by updating the end time
                currentInterval[1] = Math.max(currentInterval[1], interval[1]);
            } else {
                // No overlap, add the current interval to result
                currentInterval = interval;
                result.add(currentInterval);
            }
        }
        
        return result.toArray(new int[result.size()][]);
    }
}
```

### Requirement → Code Mapping
- **R0 (Sort intervals)**: `Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));` - Sort intervals by start time
- **R1 (Detect overlap)**: `if (interval[0] <= currentInterval[1])` - Check if the current interval overlaps with the last interval in result
- **R2 (Merge intervals)**: `currentInterval[1] = Math.max(currentInterval[1], interval[1]);` - Update the end time to merge overlapping intervals
- **R3 (Add non-overlapping intervals)**: `result.add(currentInterval);` - Add intervals that don't overlap with the previous one
- **R4 (Handle edge cases)**: Check if the input is null or contains only one interval

### Example Walkthrough
For the intervals `[[1,3], [2,6], [8,10], [15,18]]`:

1. Sort intervals (already sorted by start time)
2. Add `[1,3]` to result
3. Process `[2,6]`:
   - Overlaps with `[1,3]` (2 <= 3)
   - Merge to `[1,6]`
4. Process `[8,10]`:
   - No overlap with `[1,6]` (8 > 6)
   - Add `[8,10]` to result
5. Process `[15,18]`:
   - No overlap with `[8,10]` (15 > 10)
   - Add `[15,18]` to result
6. Final result: `[[1,6], [8,10], [15,18]]`

### Complexity Analysis
- **Time Complexity**: O(n log n) - Dominated by the sorting step
- **Space Complexity**: O(n) - For storing the result
