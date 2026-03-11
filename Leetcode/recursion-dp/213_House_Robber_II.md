# House Robber II

## Problem Description

**Problem Link:** [House Robber II](https://leetcode.com/problems/house-robber-ii/)

Same as House Robber, but houses form a circle. First and last are adjacent.

## Intuition/Main Idea

Circular adjacency means we cannot take both index `0` and index `n-1`.

So split into two linear cases:
1. Rob from `[0 .. n-2]`
2. Rob from `[1 .. n-1]`

Answer is max of the two case results.

### Subproblem definition
For linear range `[start..end]`, `dp(i)` = max money from index `i` to `end`.

### State transition
Same as House Robber:
`dp(i) = max(dp(i+1), nums[i] + dp(i+2))`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Circular adjacency constraint | two runs: `robLinear(0,n-2)` and `robLinear(1,n-1)` |
| Reuse House Robber logic | helper `robLinear` with same recurrence |
| Edge case with single house | `if (nums.length == 1) return nums[0];` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }

        return Math.max(robRange(nums, 0, n - 2), robRange(nums, 1, n - 1));
    }

    private int robRange(int[] nums, int start, int end) {
        // Size nums.length so index can be used directly in memo.
        Integer[] memo = new Integer[nums.length];
        return dfs(start, end, nums, memo);
    }

    private int dfs(int index, int end, int[] nums, Integer[] memo) {
        if (index > end) {
            return 0;
        }
        if (memo[index] != null) {
            return memo[index];
        }

        int skip = dfs(index + 1, end, nums, memo);
        int rob = nums[index] + dfs(index + 2, end, nums, memo);
        memo[index] = Math.max(skip, rob);
        return memo[index];
    }
}
```

### Bottom-Up Version

Bottom-up with two linear scans is cleaner and avoids recursive depth.

```java
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }

        return Math.max(robLinear(nums, 0, n - 2), robLinear(nums, 1, n - 1));
    }

    private int robLinear(int[] nums, int start, int end) {
        int next = 0;
        int nextNext = 0;

        for (int index = end; index >= start; index--) {
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

1. [198. House Robber](https://leetcode.com/problems/house-robber/)
2. [337. House Robber III](https://leetcode.com/problems/house-robber-iii/)
