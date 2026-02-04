### 252. Meeting Rooms
### Problem Link: [Meeting Rooms](https://leetcode.com/problems/meeting-rooms/)

### Problem Description
Given an array of meeting time intervals `intervals` where `intervals[i] = [starti, endi]`, determine if a person could attend all meetings.

A person can attend all meetings if and only if **no two meetings overlap**.

#### Example 1
- Input: `intervals = [[0,30],[5,10],[15,20]]`
- Output: `false`
- Explanation: The meeting `[0,30]` overlaps with `[5,10]` and `[15,20]`.

#### Example 2
- Input: `intervals = [[7,10],[2,4]]`
- Output: `true`
- Explanation: These meetings do not overlap.

### Intuition/Main Idea
We only care about whether any two intervals overlap. The easiest way to detect overlap is to first sort all meetings by their start time. Once sorted, only adjacent meetings can conflict: if meeting `i` starts before meeting `i-1` ends, then those two overlap and the answer is immediately `false`.

Sorting turns the problem into a single linear scan: as we move from left to right, each current meeting only needs to be compared with the one that ends right before it in the sorted order. If no adjacent pair overlaps, then no pair overlaps at all, so the person can attend every meeting.

### Code Mapping
| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @Handle empty/single meeting cases | Early return when `intervals == null || intervals.length <= 1` |
| @Order meetings by start time | `Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));` |
| @Detect overlap between consecutive meetings | `if (intervals[i][0] < intervals[i - 1][1]) return false;` |
| @Return true when no overlaps exist | `return true;` after the scan |

### Final Java Code & Learning Pattern (Full Content)
```java
import java.util.Arrays;

class Solution {
    public boolean canAttendMeetings(int[][] intervals) {
        // If there are 0 or 1 meetings, overlap is impossible.
        if (intervals == null || intervals.length <= 1) {
            return true;
        }

        // Sort by start time so only adjacent meetings can overlap.
        Arrays.sort(intervals, (first, second) -> Integer.compare(first[0], second[0]));

        // Scan adjacent meetings to detect any overlap.
        for (int meetingIndex = 1; meetingIndex < intervals.length; meetingIndex++) {
            int currentStart = intervals[meetingIndex][0];
            int previousEnd = intervals[meetingIndex - 1][1];

            // If current meeting starts before previous ends, overlap exists.
            if (currentStart < previousEnd) {
                return false;
            }
        }

        // No overlaps were found.
        return true;
    }
}
```

**Learning Pattern:**
- When overlap depends on ordering, sort by the key that makes conflicts adjacent (here: start time).
- After sorting, reduce the problem to a single pass comparing consecutive elements.
- Use strict `<` for overlap because end time equal to next start time is allowed.

### Complexity Analysis
- **Time Complexity**: $O(n \log n)$ due to sorting, followed by a linear scan.
- **Space Complexity**: $O(1)$ extra space if sorting in place (or $O(n)$ depending on sorting implementation).

### Similar Problems
- [Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/)
- [Merge Intervals](https://leetcode.com/problems/merge-intervals/)
- [Insert Interval](https://leetcode.com/problems/insert-interval/)
