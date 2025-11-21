# Employee Free Time

## Problem Description

**Problem Link:** [Employee Free Time](https://leetcode.com/problems/employee-free-time/)

We are given a list `schedule` of employees, which represents the working time for each employee.

Each employee has a list of non-overlapping `Intervals`, and these intervals are in sorted order.

Return the list of finite intervals representing **common, positive-length free time** for *all* employees, also in sorted order.

(Even though we are representing `Intervals` in the form `[x, y]`, the objects inside are `Intervals`, not lists or arrays. For example, `schedule[0][0].start = 1`, `schedule[0][0].end = 2`, and `schedule[0][0][0]` is not defined.)

Also, we wouldn't include intervals like [5, 5] in our answer, as they have zero length.

**Example 1:**
```
Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
Output: [[3,4]]
Explanation: There are a total of three employees, and all common free time intervals would be [-inf, 1], [3, 4], [10, inf].
We only care about intervals with finite length, so we return [[3,4]].
```

**Constraints:**
- `1 <= schedule.length , schedule[i].length <= 50`
- `0 <= schedule[i].start < schedule[i].end <= 10^8`

## Intuition/Main Idea

We need to find time intervals when ALL employees are free. This means finding gaps in the union of all working intervals.

**Core Algorithm:**
- Merge all intervals from all employees
- Sort merged intervals
- Find gaps between consecutive intervals
- Gaps represent free time for all employees

**Why merge intervals:** After merging, gaps between intervals represent times when no employee is working, i.e., free time for all.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Merge all intervals | Collect and sort - Lines 8-15 |
| Find gaps | Gap detection - Lines 17-22 |
| Return free time | Result building - Lines 17-22 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        // Collect all intervals from all employees
        List<Interval> allIntervals = new ArrayList<>();
        for (List<Interval> employeeSchedule : schedule) {
            allIntervals.addAll(employeeSchedule);
        }
        
        // Sort intervals by start time
        allIntervals.sort((a, b) -> a.start - b.start);
        
        // Merge overlapping intervals
        List<Interval> merged = new ArrayList<>();
        merged.add(allIntervals.get(0));
        
        for (int i = 1; i < allIntervals.size(); i++) {
            Interval current = allIntervals.get(i);
            Interval last = merged.get(merged.size() - 1);
            
            // If current overlaps with last, merge them
            if (current.start <= last.end) {
                last.end = Math.max(last.end, current.end);
            } else {
                // No overlap, add current interval
                merged.add(current);
            }
        }
        
        // Find gaps between merged intervals (free time)
        List<Interval> freeTime = new ArrayList<>();
        for (int i = 0; i < merged.size() - 1; i++) {
            // Gap between merged[i].end and merged[i+1].start
            int start = merged.get(i).end;
            int end = merged.get(i + 1).start;
            
            if (start < end) { // Positive length gap
                freeTime.add(new Interval(start, end));
            }
        }
        
        return freeTime;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n \log n)$ where $n$ is total number of intervals. Sorting dominates.

**Space Complexity:** $O(n)$ for merged intervals and result.

## Similar Problems

- [Merge Intervals](https://leetcode.com/problems/merge-intervals/) - Merge overlapping intervals
- [Non-overlapping Intervals](https://leetcode.com/problems/non-overlapping-intervals/) - Remove overlapping intervals
- [Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/) - Count overlapping intervals

