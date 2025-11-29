### 57. Insert Interval
### Problem Link: [Insert Interval](https://leetcode.com/problems/insert-interval/)
### Intuition
This problem asks us to insert a new interval into a sorted list of non-overlapping intervals and merge any overlapping intervals. It's an extension of the Merge Intervals problem (Problem 56).

The key insight is to handle three cases:
1. Intervals that come before the new interval (no overlap)
2. Intervals that overlap with the new interval (need to merge)
3. Intervals that come after the new interval (no overlap)

We can process the intervals in order, adding non-overlapping intervals directly to the result and merging overlapping ones with the new interval.

### Java Reference Implementation
```java
class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>(); // [R0] Initialize result list
        int index = 0;
        int n = intervals.length;
        
        // [R1] Add all intervals that come before the new interval
        while (index < n && intervals[index][1] < newInterval[0]) {
            result.add(intervals[index++]);
        }
        
        // [R2] Merge overlapping intervals with the new interval
        // Does the current existing interval start before (or at the exact moment) the new interval ends? If yes, merge
        while (index < n && intervals[index][0] <= newInterval[1]) {
            // Update the new interval to include the current overlapping interval
            newInterval[0] = Math.min(newInterval[0], intervals[index][0]); // [R3] Update start of merged interval
            newInterval[1] = Math.max(newInterval[1], intervals[index][1]); // [R3] Update end of merged interval
            index++;
        }
        
        // [R4] Add the merged interval
        result.add(newInterval);
        
        // [R5] Add all intervals that come after the new interval
        while (index < n) {
            result.add(intervals[index++]);
        }
        
        // [R6] Convert list to array and return
        return result.toArray(new int[result.size()][]);
    }
}
```

### Requirement → Code Mapping
- **R0 (Initialize result)**: `List<int[]> result = new ArrayList<>();` - Create a list to store the result
- **R1 (Add non-overlapping intervals before)**: First while loop adds intervals that come before the new interval
- **R2 (Merge overlapping intervals)**: Second while loop merges intervals that overlap with the new interval
- **R3 (Update merged interval)**: `newInterval[0] = Math.min(...); newInterval[1] = Math.max(...);` - Update the bounds of the merged interval
- **R4 (Add merged interval)**: `result.add(newInterval);` - Add the merged interval to the result
- **R5 (Add non-overlapping intervals after)**: Third while loop adds intervals that come after the new interval
- **R6 (Convert and return)**: `return result.toArray(new int[result.size()][]);` - Convert the list to an array and return

### Complexity Analysis
- **Time Complexity**: O(n) - We process each interval once
- **Space Complexity**: O(n) - We create a new array to store the result

### Relation to Other Problems
This problem is related to:
- **Merge Intervals** (Problem 56): Similar but focuses on merging existing intervals rather than inserting a new one
- **Range Module** (Problem 715): More complex version involving adding, removing, and querying ranges
