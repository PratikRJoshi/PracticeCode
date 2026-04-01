# Salesforce Jobs Equilibrium

## 1) Problem Description

Given array `duration[]`, one operation reduces any chosen job by `1`.
If a job reaches `0`, it is removed.

Goal: after operations, all remaining (positive) durations are equal.
Find minimum operations.

## 2) Intuition/Main Idea

Choose a target final duration `t` (possibly all removed, i.e. `t=0`).

For one job with original value `d`:
- if `d >= t`, cost is `d - t`
- if `d < t`, cannot increase, so must remove job completely: cost `d`

Total cost for target `t`:

```text
sum(d < t ? d : d - t)
```

Best `t` is among `0` and values present in `duration`.

### Why this intuition works

- Only decrements are allowed.
- Final positive values must be identical, so single target `t` defines every job action.
- Piecewise-linear cost changes only at existing durations.

### How to derive it step by step

1. Sort durations.
2. Build prefix sum for quick sums of small values.
3. For each candidate target `t = sorted[i]`:
   - prefix part (`< t`) gets removed
   - suffix part (`>= t`) reduced down to `t`
4. Also compare with `t = 0` (remove all).

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @OnlyDecrementAllowed | cost formula uses only reductions/removal |
| @AllActiveJobsEqual | iterate single target `t` values |
| @LargeInputUpTo1e5 | sort + prefix sums for `O(n log n)` |
| @MinimumTotalOperations | track global minimum over all targets |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static long getMinimumOperations(List<Integer> duration) {
        int n = duration.size();
        List<Integer> sorted = new ArrayList<>(duration);
        Collections.sort(sorted);

        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + sorted.get(i);
        }

        long totalSum = prefix[n];
        long answer = totalSum; // target t = 0 (remove all jobs)

        for (int i = 0; i < n; i++) {
            if (i > 0 && sorted.get(i).equals(sorted.get(i - 1))) {
                continue;
            }

            long target = sorted.get(i);
            long removeSmallerPart = prefix[i];
            long suffixSum = totalSum - prefix[i];
            long reduceLargerPart = suffixSum - target * (n - i);

            answer = Math.min(answer, removeSmallerPart + reduceLargerPart);
        }

        return answer;
    }
}
```

Learning Pattern:
- When final state is "all values become one target" with one-direction operations, convert to target-scan optimization.
- Prefix sums make repeated cost computation efficient.

## 5) Complexity Analysis

- Time Complexity: $O(n \log n)$
- Space Complexity: $O(n)$

## Similar Problems

- [LeetCode 462: Minimum Moves to Equal Array Elements II](https://leetcode.com/problems/minimum-moves-to-equal-array-elements-ii/) (target-based cost minimization)
- [LeetCode 453: Minimum Moves to Equal Array Elements](https://leetcode.com/problems/minimum-moves-to-equal-array-elements/) (equalization by operations)