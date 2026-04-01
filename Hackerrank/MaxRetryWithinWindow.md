# Max Retry Within Window

## 1) Problem Description

Given unsorted request times `timestamp[]` and a window size `windowSize`, find the maximum number of requests that can fit in any inclusive interval:

```text
[t, t + windowSize]
```

for some start `t`.

Example:

```text
timestamp = [1, 3, 7, 5]
windowSize = 4
answer = 2
```

Constraints:
- `1 <= n <= 2 * 10^5`
- `1 <= timestamp[i] <= 10^9`
- `1 <= windowSize <= 10^9`

## 2) Intuition/Main Idea

Because timestamps are unsorted, first sort them.

Then use a sliding window `[left, right]` over sorted times such that:

```text
timestamp[right] - timestamp[left] <= windowSize
```

### Why this intuition works

- In sorted order, any valid interval corresponds to a contiguous segment.
- If segment violates window size, advancing `left` is the only way to fix it.
- For each `right`, the maintained `left` gives largest valid segment ending at `right`.

### How to derive it step by step

1. Sort timestamps.
2. Expand `right` one step at a time.
3. While difference exceeds `windowSize`, move `left` forward.
4. Track max segment length `right - left + 1`.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @UnsortedInput | `Collections.sort(sortedTimestamps)` |
| @InclusiveWindowConstraint | while loop checks `sorted[right] - sorted[left] > windowSize` |
| @MaximumRequestsInAnyWindow | `maximumRequests = Math.max(maximumRequests, right - left + 1)` |
| @LargeInputEfficiency | two pointers move monotonically (`O(n)` after sort) |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static int getMaxRetryWithinWindow(List<Integer> timestamp, int windowSize) {
        List<Integer> sortedTimestamps = new ArrayList<>(timestamp);
        Collections.sort(sortedTimestamps);

        int left = 0;
        int maximumRequests = 0;

        for (int right = 0; right < sortedTimestamps.size(); right++) {
            while ((long) sortedTimestamps.get(right) - sortedTimestamps.get(left) > windowSize) {
                left++;
            }

            maximumRequests = Math.max(maximumRequests, right - left + 1);
        }

        return maximumRequests;
    }
}
```

Learning Pattern:
- For "max items within value distance K", sort + sliding window is a default high-value pattern.
- Keep window valid by moving only the left pointer when violated.

## 5) Complexity Analysis

- Time Complexity: $O(n \log n)$ due to sorting
- Space Complexity: $O(n)$ for sorted copy

## Similar Problems

- [LeetCode 1838: Frequency of the Most Frequent Element](https://leetcode.com/problems/frequency-of-the-most-frequent-element/) (sorted window counting)
- [LeetCode 1004: Max Consecutive Ones III](https://leetcode.com/problems/max-consecutive-ones-iii/) (two-pointer window maintenance pattern)