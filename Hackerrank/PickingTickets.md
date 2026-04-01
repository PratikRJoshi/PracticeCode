# Picking Tickets

## 1) Problem Description

Given array `tickets`, find the maximum size of a subsequence such that after sorting, every adjacent difference is either `0` or `1`.

This means chosen values can contain:
- only one number `x`, or
- two consecutive numbers `x` and `x + 1`.

Example:

```text
tickets = [8, 5, 4, 8, 4]
```

Best subsequence is `{4, 4, 5}` with size `3`.

## 2) Intuition/Main Idea

If values must stay within one consecutive pair, answer is:

```text
max over v of (count[v] + count[v+1])
```

### Why this intuition works

- Any valid sorted subsequence cannot include values with gap `>= 2`.
- So it can use at most two distinct values, and if two values exist they must be consecutive.
- Therefore every candidate is fully described by one base value `v` and counts of `v` and `v+1`.

### How to derive it step by step

1. Count frequency of each ticket value.
2. For each distinct value `v`, compute `count[v] + count[v+1]`.
3. Keep the maximum.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @UnbrokenRangeCondition | evaluate only `value` and `value + 1` pair |
| @MaximumSubsequenceSize | maintain `maximumSize` across all base values |
| @LargeInputHandling | frequency map with one pass + one distinct-values pass |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static int pickingTickets(List<Integer> tickets) {
        Map<Integer, Integer> frequencyByValue = new HashMap<>();
        for (int value : tickets) {
            frequencyByValue.merge(value, 1, Integer::sum);
        }

        int maximumSize = 0;

        for (int value : frequencyByValue.keySet()) {
            int current = frequencyByValue.getOrDefault(value, 0);
            int next = frequencyByValue.getOrDefault(value + 1, 0);
            maximumSize = Math.max(maximumSize, current + next);
        }

        return maximumSize;
    }
}
```

Learning Pattern:
- When a rule limits value spread to a narrow band, map values to frequency and evaluate local neighbors.
- Replace subsequence construction with counting combinations of allowed values.

## 5) Complexity Analysis

- Time Complexity: $O(n)$ average with hash map
- Space Complexity: $O(m)$ where `m` is distinct ticket values

## Similar Problems

- [LeetCode 594: Longest Harmonious Subsequence](https://leetcode.com/problems/longest-harmonious-subsequence/)
- [LeetCode 128: Longest Consecutive Sequence](https://leetcode.com/problems/longest-consecutive-sequence/) (consecutive value reasoning)