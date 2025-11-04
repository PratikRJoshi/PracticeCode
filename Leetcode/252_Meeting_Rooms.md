### 252. Meeting Rooms
### Problem Link: [Meeting Rooms](https://leetcode.com/problems/meeting-rooms/)
### Intuition
This problem asks us to determine if a person can attend all meetings. Given an array of meeting time intervals where each interval consists of a start and end time, we need to check if any of the meetings overlap.

The key insight is to sort the intervals by their start times and then check if any meeting's start time is earlier than the previous meeting's end time. If such a case exists, it means the meetings overlap and the person cannot attend all of them.

### Java Reference Implementation
```java
class Solution {
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) { // [R0] Handle edge cases
            return true; // No meetings or just one meeting, can attend all
        }
        
        // [R1] Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        // [R2] Check for any overlapping meetings
        for (int i = 1; i < intervals.length; i++) {
            // [R3] If current meeting starts before previous meeting ends, there's an overlap
            if (intervals[i][0] < intervals[i-1][1]) {
                return false; // Cannot attend all meetings
            }
        }
        
        return true; // [R4] No overlaps found, can attend all meetings
    }
}
```

### Understanding the Algorithm and Boundary Checks

1. **Sorting by Start Time:**
   - We sort the intervals by their start times to process them in chronological order
   - This allows us to easily compare adjacent meetings for potential overlaps

2. **Overlap Check (`intervals[i][0] < intervals[i-1][1]`):**
   - We check if the current meeting's start time is earlier than the previous meeting's end time
   - If so, the meetings overlap and the person cannot attend both
   - We use `<` (not `<=`) because:
     - If meeting 1 ends at time t and meeting 2 starts at time t, they don't overlap
     - The person can finish the first meeting and immediately start the second one

3. **Edge Cases:**
   - Empty array: No meetings, so the person can attend all (trivially true)
   - Single meeting: Only one meeting, so no possibility of overlap (also true)

4. **Loop Boundary (`i = 1` to `intervals.length`):**
   - We start from the second meeting (index 1) since we're comparing with the previous meeting
   - This avoids an out-of-bounds error when accessing `intervals[i-1]`

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (intervals == null || intervals.length <= 1) { return true; }` - Return true for empty arrays or single meetings
- **R1 (Sort intervals)**: `Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));` - Sort by start time
- **R2 (Check overlaps)**: `for (int i = 1; i < intervals.length; i++)` - Iterate through sorted intervals
- **R3 (Detect overlap)**: `if (intervals[i][0] < intervals[i-1][1])` - Check if current meeting starts before previous ends
- **R4 (Return result)**: `return true;` - If no overlaps found, can attend all meetings

### Complexity Analysis
- **Time Complexity**: O(n log n)
  - Sorting the intervals takes O(n log n) time, where n is the number of meetings
  - The subsequent linear scan through the sorted intervals takes O(n) time
  - Overall, the time complexity is dominated by the sorting step: O(n log n)

- **Space Complexity**: O(1) or O(n)
  - If we're allowed to sort the input array in-place, the space complexity is O(1)
  - If we need to create a copy of the input array for sorting, the space complexity is O(n)
  - The sorting algorithm itself might use O(log n) space for the recursion stack

### Related Problems
- **Meeting Rooms II** (Problem 253): Find the minimum number of conference rooms required
- **Merge Intervals** (Problem 56): Merge all overlapping intervals
- **Insert Interval** (Problem 57): Insert a new interval into a set of non-overlapping intervals
