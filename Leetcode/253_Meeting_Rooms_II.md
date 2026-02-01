### 253. Meeting Rooms II
### Problem Link: [Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/)

### Problem Description
Given an array of meeting time intervals `intervals` where `intervals[i] = [starti, endi]`, return the minimum number of conference rooms required.

#### Example 1
- Input: `intervals = [[0,30],[5,10],[15,20]]`
- Output: `2`
- Explanation: We need 2 rooms because:
  - Room 1: `[0,30]`
  - Room 2: `[5,10]` then `[15,20]`

#### Example 2
- Input: `intervals = [[7,10],[2,4]]`
- Output: `1`
- Explanation: These meetings do not overlap, so 1 room is enough.

### Intuition
This problem asks us to find the minimum number of conference rooms required to hold all meetings. Given an array of meeting time intervals where each interval consists of a start and end time, we need to determine how many rooms we need to allocate so that all meetings can be held.

The key insight is to track the start and end times separately. We can visualize this as a timeline where each start time represents a request for a new room and each end time represents a room becoming available. By processing these events in chronological order, we can determine the maximum number of rooms needed at any point in time.

### Java Reference Implementation
```java
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) { // [R0] Handle edge cases
            return 0;
        }
        
        int n = intervals.length;
        
        // [R1] Extract start and end times into separate arrays
        int[] startTimes = new int[n];
        int[] endTimes = new int[n];
        
        for (int i = 0; i < n; i++) {
            startTimes[i] = intervals[i][0];
            endTimes[i] = intervals[i][1];
        }
        
        // [R2] Sort both arrays
        Arrays.sort(startTimes);
        Arrays.sort(endTimes);
        
        int roomsNeeded = 0; // [R3] Current number of rooms in use
        int maxRooms = 0;    // [R4] Maximum number of rooms needed at any point
        
        int startPtr = 0;
        int endPtr = 0;
        
        // [R5] Process events in chronological order
        while (startPtr < n) {
            // [R6] If the earliest start time is before or at the earliest end time
            if (startTimes[startPtr] < endTimes[endPtr]) {
                roomsNeeded++; // [R7] Need a new room
                startPtr++;    // [R8] Move to the next start time
            } else {
                roomsNeeded--; // [R9] A room becomes available
                endPtr++;      // [R10] Move to the next end time
            }
            
            maxRooms = Math.max(maxRooms, roomsNeeded); // [R11] Update maximum rooms needed
        }
        
        return maxRooms; // [R12] Return the maximum number of rooms needed
    }
}
```

### Alternative Implementation (Using Priority Queue)
```java
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Min heap to track the earliest ending meeting
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        // Add the end time of the first meeting
        minHeap.add(intervals[0][1]);
        
        // Process the rest of the meetings
        for (int i = 1; i < intervals.length; i++) {
            // If the current meeting starts after the earliest ending meeting
            if (intervals[i][0] >= minHeap.peek()) {
                // Remove the earliest ending meeting (room becomes available)
                minHeap.poll();
            }
            
            // Add the end time of the current meeting
            minHeap.add(intervals[i][1]);
        }
        
        // The size of the heap is the number of rooms needed
        return minHeap.size();
    }
}
```

### Understanding the Algorithm and Boundary Checks

1. **Chronological Processing of Events:**
   - We separate start and end times to process them in chronological order
   - Each start time represents a request for a new room
   - Each end time represents a room becoming available

2. **Comparison Logic (`startTimes[startPtr] < endTimes[endPtr]`):**
   - We use `<` (not `<=`) because:
     - If a meeting ends at the same time another starts, we can reuse the room
     - This is efficient room allocation - as soon as a meeting ends, the room is available

3. **Pointer Movement:**
   - We always move the pointer that corresponds to the earlier event
   - If start time is earlier, we increment `startPtr`
   - If end time is earlier or equal, we increment `endPtr`

4. **Room Counting:**
   - When we process a start time, we increment `roomsNeeded`
   - When we process an end time, we decrement `roomsNeeded`
   - The maximum value of `roomsNeeded` at any point is our answer

5. **Loop Termination:**
   - The loop continues until we've processed all start times
   - We don't need to check if `endPtr < n` because there will always be an end time for every start time

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (intervals == null || intervals.length == 0) { return 0; }` - Return 0 for empty arrays
- **R1 (Extract times)**: Extract start and end times into separate arrays
- **R2 (Sort times)**: Sort both arrays to process events chronologically
- **R3 (Track rooms)**: `int roomsNeeded = 0;` - Keep track of current rooms in use
- **R4 (Track maximum)**: `int maxRooms = 0;` - Keep track of maximum rooms needed
- **R5 (Process events)**: Process start and end times in chronological order
- **R6-R10 (Update counters)**: Update room count and pointers based on event type
- **R11 (Update maximum)**: `maxRooms = Math.max(maxRooms, roomsNeeded);` - Update maximum rooms needed
- **R12 (Return result)**: `return maxRooms;` - Return the minimum number of rooms required

### Complexity Analysis
- **Time Complexity**: O(n log n)
  - Sorting the start and end time arrays takes O(n log n) time
  - The subsequent linear scan through the sorted arrays takes O(n) time
  - Overall, the time complexity is dominated by the sorting step: O(n log n)

- **Space Complexity**: O(n)
  - We use two additional arrays of size n to store the start and end times
  - The space complexity is therefore O(n)

### Related Problems
- **Meeting Rooms** (Problem 252): Check if a person can attend all meetings
- **Merge Intervals** (Problem 56): Merge all overlapping intervals
- **Insert Interval** (Problem 57): Insert a new interval into a set of non-overlapping intervals
- **Employee Free Time** (Problem 759): Find the free time for all employees
