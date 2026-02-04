### 253. Meeting Rooms II
### Problem Link: [Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/)

### Problem Description
Given an array of meeting time intervals `intervals` where `intervals[i] = [starti, endi]`, return the minimum number of conference rooms required.

#### Example 1
- Input: `intervals = [[0,30],[5,10],[15,20]]`
- Output: `2`
- Explanation: We need 2 rooms because `[0,30]` overlaps with `[5,10]` and `[15,20]`.

#### Example 2
- Input: `intervals = [[7,10],[2,4]]`
- Output: `1`
- Explanation: These meetings do not overlap, so 1 room is enough.

### Intuition/Main Idea
We need the maximum number of overlapping meetings at any moment. A simple way to compute this is to separate all start times and end times. Each start time means one more room is needed; each end time means one room is freed. By sorting both lists and walking through them in chronological order, we can keep a running count of active meetings and track the maximum value reached.

If the next meeting starts before the earliest current meeting ends, we must allocate a new room. Otherwise, a meeting has ended and its room becomes available. Using a strict `<` comparison allows back-to-back meetings to reuse the same room.

### Code Mapping
| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @Handle empty input | Early return when `intervals == null || intervals.length == 0` |
| @Separate start and end times | Fill `startTimes` and `endTimes` arrays from intervals |
| @Process events in order | Sort both arrays, then scan with `startPointer` and `endPointer` |
| @Increase rooms on overlap | `if (startTimes[startPointer] < endTimes[endPointer]) activeRooms++;` |
| @Reuse room when meeting ends | `else { activeRooms--; endPointer++; }` |
| @Return maximum rooms needed | Track `maxRoomsNeeded` and return it |
| @Alternative: compute rooms by tracking earliest ending meeting | `minMeetingRoomsWithPriorityQueue(...)` using `PriorityQueue<Integer>` |

### Final Java Code & Learning Pattern (Full Content)
#### Sort start / end
```java
import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        int meetingCount = intervals.length;
        int[] startTimes = new int[meetingCount];
        int[] endTimes = new int[meetingCount];

        for (int meetingIndex = 0; meetingIndex < meetingCount; meetingIndex++) {
            startTimes[meetingIndex] = intervals[meetingIndex][0];
            endTimes[meetingIndex] = intervals[meetingIndex][1];
        }

        Arrays.sort(startTimes);
        Arrays.sort(endTimes);

        int startPointer = 0;
        int endPointer = 0;
        int activeRooms = 0;
        int maxRoomsNeeded = 0;

        while (startPointer < meetingCount) {
            if (startTimes[startPointer] < endTimes[endPointer]) {
                // A meeting starts before the earliest one ends -> need a new room.
                activeRooms++;
                startPointer++;
            } else {
                // A meeting ended, so its room can be reused.
                activeRooms--;
                endPointer++;
            }

            maxRoomsNeeded = Math.max(maxRoomsNeeded, activeRooms);
        }

        return maxRoomsNeeded;
    }
```

#### Priority Queue
```java
    public int minMeetingRoomsWithPriorityQueue(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        // Sort by start time so we process meetings chronologically.
        Arrays.sort(intervals, (first, second) -> Integer.compare(first[0], second[0]));

        // Min-heap stores end times of meetings currently occupying rooms.
        PriorityQueue<Integer> earliestEndingMeetingEndTimes = new PriorityQueue<>();

        for (int meetingIndex = 0; meetingIndex < intervals.length; meetingIndex++) {
            int currentMeetingStart = intervals[meetingIndex][0];
            int currentMeetingEnd = intervals[meetingIndex][1];

            // If the earliest-ending meeting ends before (or exactly at) this start,
            // we can reuse that room.
            if (!earliestEndingMeetingEndTimes.isEmpty()
                    && earliestEndingMeetingEndTimes.peek() <= currentMeetingStart) {
                earliestEndingMeetingEndTimes.poll();
            }

            // Allocate a room for the current meeting (new room or reused room).
            earliestEndingMeetingEndTimes.add(currentMeetingEnd);
        }

        // The heap size is the number of rooms that were needed simultaneously.
        return earliestEndingMeetingEndTimes.size();
    }
}
```

**Learning Pattern:**
- When a problem asks for maximum overlap, convert intervals into start/end events.
- Sort starts and ends separately, then merge them with two pointers.
- Increment on a start event and decrement on an end event; the maximum counter is the answer.
- Equivalent alternative: sort by start time and use a min-heap of end times to reuse rooms greedily.

### Complexity Analysis
- **Time Complexity**: $O(n \log n)$ due to sorting the start and end arrays.
- **Space Complexity**: $O(n)$ for the two arrays.

### Similar Problems
- [Meeting Rooms](https://leetcode.com/problems/meeting-rooms/)
- [Merge Intervals](https://leetcode.com/problems/merge-intervals/)
- [Insert Interval](https://leetcode.com/problems/insert-interval/)
- [Employee Free Time](https://leetcode.com/problems/employee-free-time/)
