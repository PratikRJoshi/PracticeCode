# Salesforce License Grouping

## 1) Problem Description

Given `licenseSlots[]`, choose number of groups `g >= 2`.
After adding slots, every `licenseSlots[i]` must be divisible by `g`.

For fixed `g`, minimum add for one element is:

```text
(g - (licenseSlots[i] % g)) % g
```

Return minimum total additions over valid `g`.

Constraints:
- `1 <= n <= 10^5`
- `1 <= licenseSlots[i] <= 500`

## 2) Intuition/Main Idea

Since values are small (`<= 500`), brute-force candidate group counts is feasible.

For each `g` in `[2, maxValue]`, compute total additions and keep minimum.

Why up to `maxValue` is enough:
- if `g > maxValue`, then each element needs at least `g - value` additions (large), which cannot beat small `g` candidates already checked.

### Why this intuition works

- For each chosen `g`, formula above is the exact minimum extra needed per slot.
- Independent per element, so total is sum.
- Global optimum is minimum among all candidate `g`.

### How to derive it step by step

1. Compute maximum slot value.
2. Loop `g = 2..maxValue`.
3. Sum required additions for each slot.
4. Track smallest sum.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @AtLeastTwoGroups | loop starts from `groups = 2` |
| @EachSlotDivisibleByGroups | per-slot add formula with modulo |
| @MinimumAdditionalSlots | global minimum over all candidate groups |
| @ConstraintSlotValueLe500 | bounded enumeration over group counts |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static int getMinimumSlots(List<Integer> licenseSlots) {
        int maxValue = 0;
        for (int value : licenseSlots) {
            maxValue = Math.max(maxValue, value);
        }

        long answer = Long.MAX_VALUE;

        for (int groups = 2; groups <= maxValue; groups++) {
            long additions = 0L;

            for (int value : licenseSlots) {
                additions += (groups - (value % groups)) % groups;
            }

            answer = Math.min(answer, additions);
        }

        return (int) answer;
    }
}
```

Learning Pattern:
- When one parameter range is small, enumerate it and compute exact cost.
- Modulo remainder often directly gives minimal increment to next multiple.

## 5) Complexity Analysis

- Let `n` be array size and `V = max(licenseSlots)`.
- Time Complexity: $O(nV)$, here `V <= 500` so practical.
- Space Complexity: $O(1)$ extra.

## Similar Problems

- [LeetCode 2344: Minimum Deletions to Make Array Divisible](https://leetcode.com/problems/minimum-deletions-to-make-array-divisible/) (divisibility target reasoning)
- [LeetCode 1590: Make Sum Divisible by P](https://leetcode.com/problems/make-sum-divisible-by-p/) (modulo-based adjustment logic)