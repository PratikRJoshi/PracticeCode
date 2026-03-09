### 57. Insert Interval
### Problem Link: [Insert Interval](https://leetcode.com/problems/insert-interval/)
### Intuition/Main Idea
The English requirement is:

> "Insert `newInterval` into sorted non-overlapping `intervals`, and merge if necessary."

This translates to three chronological phases:
1. Add all intervals fully before `newInterval`.
2. Merge all intervals that overlap with `newInterval`.
3. Add all remaining intervals after the merged block.

Because input intervals are already sorted and non-overlapping, each interval belongs to exactly one of these phases, and we can process in one left-to-right pass.

### English Requirement → Code Translation
| English Requirement | Code Translation |
| --- | --- |
| "Intervals before new interval are unchanged" | `while (index < n && intervals[index][1] < newInterval[0])` |
| "Overlapping intervals must be merged" | `while (index < n && intervals[index][0] <= newInterval[1])` |
| "Merged interval start is smallest start" | `newInterval[0] = Math.min(newInterval[0], intervals[index][0])` |
| "Merged interval end is largest end" | `newInterval[1] = Math.max(newInterval[1], intervals[index][1])` |
| "Intervals after merged block stay unchanged" | `while (index < n)` append remaining |

### Code Mapping
| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @Insert into sorted non-overlapping intervals | One linear scan with pointer `index` |
| @Keep non-overlapping left intervals | First `while` loop (`end < mergedStart`) |
| @Merge all overlapping intervals | Second `while` loop (`start <= mergedEnd`) |
| @Update merged boundaries correctly | `Math.min` for start and `Math.max` for end |
| @Preserve right-side intervals | Third `while` loop appending leftovers |
| @Return merged final list | `result.toArray(new int[result.size()][])` |

### Final Java Code & Learning Pattern (Full Content)
```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int index = 0;
        int n = intervals.length;

        // Loop 1 condition: index < n && intervals[index][1] < newInterval[0]
        // English meaning:
        // - index < n: there are still intervals to inspect.
        // - intervals[index][1] < mergedStart: current interval ends strictly before new interval starts,
        //   so there is no overlap and we can keep it as-is.
        while (index < n && intervals[index][1] < newInterval[0]) {
            result.add(intervals[index]);
            index++;
        }

        // Loop 2 (the one you asked about): index < n && intervals[index][0] <= newInterval[1]
        // How to derive this yourself from English:
        // 1) We want to keep merging "as long as current interval overlaps newInterval".
        // 2) Overlap test between [aStart, aEnd] and [bStart, bEnd] is:
        //      aStart <= bEnd && bStart <= aEnd
        // 3) Here, loop 1 already removed all intervals that end before newInterval starts,
        //    so bStart <= aEnd is already guaranteed for current index.
        // 4) So only one check remains necessary in loop 2:
        //      currentStart <= newIntervalEnd  -> intervals[index][0] <= newInterval[1]
        // Intuition sentence to remember:
        // "Has the next interval started before my merged interval has ended?"
        // If yes, they still touch/overlap -> keep merging.
        while (index < n && intervals[index][0] <= newInterval[1]) {
            // Expand newInterval to absorb current overlapping interval.
            // Start becomes minimum start, end becomes maximum end.
            newInterval[0] = Math.min(newInterval[0], intervals[index][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[index][1]);
            index++;
        }

        // After loop 2, newInterval itself is the final merged interval block.
        result.add(newInterval);

        // Loop 3 condition: index < n
        // English meaning: append every remaining interval; by now they are guaranteed to be
        // strictly after newInterval[1] and therefore non-overlapping with the merged block.
        while (index < n) {
            result.add(intervals[index]);
            index++;
        }

        return result.toArray(new int[result.size()][]);
    }
}
```

**Learning Pattern:**
- For sorted, non-overlapping interval insertions, split into **left / overlap / right** phases.
- Use boundary comparisons to decide phase transitions.
- Merge by expanding `[start, end]` using `min` and `max`.

### Complexity Analysis
- **Time Complexity:** $O(n)$ — each interval is visited once.
- **Space Complexity:** $O(n)$ — output list may contain all intervals plus the inserted one.

### Similar Problems
- [56. Merge Intervals](https://leetcode.com/problems/merge-intervals/)
- [252. Meeting Rooms](https://leetcode.com/problems/meeting-rooms/)
- [253. Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/)
- [759. Employee Free Time](https://leetcode.com/problems/employee-free-time/)
