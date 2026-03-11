# Maximum Product Subarray

## Problem Description

**Problem Link:** [Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/)

Given integer array `nums`, find contiguous subarray with largest product and return product.

## Intuition/Main Idea

This is Kadane-like, but sign flips matter.

At index `i`, we must track both:
- maximum product ending at `i`
- minimum product ending at `i`

Why minimum? A negative times a negative can become the next maximum.

### Subproblem definition
`maxEnd(i)` = max product of subarray ending at `i`
`minEnd(i)` = min product of subarray ending at `i`

### State transition
Using previous `(maxEnd, minEnd)`:
- candidates are `nums[i]`, `nums[i]*prevMax`, `nums[i]*prevMin`
- new max = max of candidates
- new min = min of candidates

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Contiguous subarray | transitions use only previous index state |
| Handle negative sign flips | track both `max` and `min` states |
| Return largest product overall | update `answer` with current max state |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int maxProduct(int[] nums) {
        int n = nums.length;
        // Sizes n because states maxEnd(i), minEnd(i) are defined for each index i.
        Integer[] maxMemo = new Integer[n];
        Integer[] minMemo = new Integer[n];

        int answer = Integer.MIN_VALUE;
        for (int index = 0; index < n; index++) {
            compute(index, nums, maxMemo, minMemo);
            answer = Math.max(answer, maxMemo[index]);
        }

        return answer;
    }

    private void compute(int index, int[] nums, Integer[] maxMemo, Integer[] minMemo) {
        if (maxMemo[index] != null) {
            return;
        }

        if (index == 0) {
            maxMemo[index] = nums[index];
            minMemo[index] = nums[index];
            return;
        }

        compute(index - 1, nums, maxMemo, minMemo);

        int prevMax = maxMemo[index - 1];
        int prevMin = minMemo[index - 1];
        int current = nums[index];

        int c1 = current;
        int c2 = current * prevMax;
        int c3 = current * prevMin;

        maxMemo[index] = Math.max(c1, Math.max(c2, c3));
        minMemo[index] = Math.min(c1, Math.min(c2, c3));
    }
}
```

### Bottom-Up Version

Bottom-up is cleaner and standard for interviews.

```java
class Solution {
    public int maxProduct(int[] nums) {
        int currentMax = nums[0];
        int currentMin = nums[0];
        int answer = nums[0];

        for (int index = 1; index < nums.length; index++) {
            int value = nums[index];

            int candidate1 = value;
            int candidate2 = value * currentMax;
            int candidate3 = value * currentMin;

            int nextMax = Math.max(candidate1, Math.max(candidate2, candidate3));
            int nextMin = Math.min(candidate1, Math.min(candidate2, candidate3));

            currentMax = nextMax;
            currentMin = nextMin;
            answer = Math.max(answer, currentMax);
        }

        return answer;
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n)$
- **Space Complexity:** Top-down $O(n)$, bottom-up $O(1)$

## Similar Problems

1. [53. Maximum Subarray](https://leetcode.com/problems/maximum-subarray/)
2. [1567. Maximum Length of Subarray With Positive Product](https://leetcode.com/problems/maximum-length-of-subarray-with-positive-product/)
