# House Robber

## Problem Description

**Problem Link:** [House Robber](https://leetcode.com/problems/house-robber/)

Given an integer array `nums`, each value is money in a house. Adjacent houses cannot both be robbed. Return maximum amount.

## Intuition/Main Idea

Include/Exclude pattern.

### Subproblem definition
`maxProfit(i)` = maximum money we can rob from subarray starting at index `i`.

### State transition
At index `i`:
- Skip house `i`: `maxProfit(i + 1)`
- Rob house `i`: `nums[i] + maxProfit(i + 2)`

Take maximum:
`maxProfit(i) = max(maxProfit(i+1), nums[i] + maxProfit(i+2))`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| No adjacent houses together | rob branch uses `i + 2` |
| Maximize total money | `Math.max(skip, rob)` |
| Reuse solved states | `memo[index]` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int rob(int[] nums) {
        // Size n because valid states are start indices 0..n-1.
        Integer[] memo = new Integer[nums.length];
        return dfs(0, nums, memo);
    }

    private int dfs(int index, int[] nums, Integer[] memo) {
        if (index >= nums.length) {
            return 0;
        }
        if (memo[index] != null) {
            return memo[index];
        }

        int skip = dfs(index + 1, nums, memo);
        int rob = nums[index] + dfs(index + 2, nums, memo);

        memo[index] = Math.max(skip, rob);
        return memo[index];
    }
}
```

### Bottom-Up Version

Bottom-up avoids recursion stack and can be space-optimized.

```java
class Solution {
    public int rob(int[] nums) {
        int next = 0;      // dp[i + 1]
        int nextNext = 0;  // dp[i + 2]

        for (int index = nums.length - 1; index >= 0; index--) {
            int current = Math.max(next, nums[index] + nextNext);
            nextNext = next;
            next = current;
        }

        return next;
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n)$
- **Space Complexity:** Top-down $O(n)$, bottom-up optimized $O(1)$

## Similar Problems

1. [213. House Robber II](https://leetcode.com/problems/house-robber-ii/)
2. [740. Delete and Earn](https://leetcode.com/problems/delete-and-earn/)
