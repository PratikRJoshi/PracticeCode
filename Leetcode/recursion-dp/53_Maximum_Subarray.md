# Maximum Subarray

## Problem Description

**Problem Link:** [Maximum Subarray](https://leetcode.com/problems/maximum-subarray/)

Given an integer array `nums`, find the contiguous subarray with the largest sum and return its sum.

## Intuition/Main Idea

Kadane pattern: at index `i`, decide:
1. start a new subarray at `i`, or
2. extend the best subarray ending at `i-1`.

### Subproblem definition
`bestEndingAt(i)` = maximum subarray sum that **must end** at index `i`.

### State transition
`bestEndingAt(i) = max(nums[i], nums[i] + bestEndingAt(i - 1))`

Global answer is `max(bestEndingAt(i))` over all `i`.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Subarray must be contiguous | transition only from `i-1` |
| Choose extend vs restart | `Math.max(nums[i], nums[i] + prev)` |
| Return largest overall sum | update global maximum across indices |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int maxSubArray(int[] nums) {
        // Size n because state is bestEndingAt(i) for each i in [0..n-1].
        Integer[] memo = new Integer[nums.length];
        int answer = Integer.MIN_VALUE;

        for (int index = 0; index < nums.length; index++) {
            answer = Math.max(answer, dfs(index, nums, memo));
        }

        return answer;
    }

    private int dfs(int index, int[] nums, Integer[] memo) {
        if (index == 0) {
            return nums[0];
        }
        if (memo[index] != null) {
            return memo[index];
        }

        int bestEndingAtPrevious = dfs(index - 1, nums, memo);
        memo[index] = Math.max(nums[index], nums[index] + bestEndingAtPrevious);
        return memo[index];
    }
}
```

### Bottom-Up Version (Kadane)

Bottom-up is preferred here because it is very concise and uses O(1) extra space.

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int currentBestEndingHere = nums[0];
        int globalBest = nums[0];

        for (int index = 1; index < nums.length; index++) {
            currentBestEndingHere = Math.max(nums[index], nums[index] + currentBestEndingHere);
            globalBest = Math.max(globalBest, currentBestEndingHere);
        }

        return globalBest;
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n)$
- **Space Complexity:** Top-down $O(n)$, bottom-up $O(1)$

## Similar Problems

1. [152. Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/)
2. [918. Maximum Sum Circular Subarray](https://leetcode.com/problems/maximum-sum-circular-subarray/)
