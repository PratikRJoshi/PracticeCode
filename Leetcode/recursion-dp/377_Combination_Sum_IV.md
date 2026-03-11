# Combination Sum IV

## Problem Description

**Problem Link:** [Combination Sum IV](https://leetcode.com/problems/combination-sum-iv/)

Given distinct integers `nums` and `target`, return number of possible combinations that add to `target`.

Difference from Coin Change II: order matters (`[1,2]` and `[2,1]` are different).

## Intuition/Main Idea

Unbounded knapsack counting with permutation order.

### Subproblem definition
`ways(rem)` = number of ordered sequences summing to `rem`.

### State transition
For each `num` in `nums`:
- if `num <= rem`, append `num` after a sequence for `rem-num`
- `ways(rem) += ways(rem - num)`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Reuse numbers unlimited times | transition from `rem - num` repeatedly |
| Count ordered sequences | bottom-up loop order: amount outer, nums inner |
| Return total sequences for target | `return dp[target]` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int combinationSum4(int[] nums, int target) {
        // Size target+1 because remaining states are 0..target.
        Integer[] memo = new Integer[target + 1];
        return dfs(target, nums, memo);
    }

    private int dfs(int remaining, int[] nums, Integer[] memo) {
        if (remaining == 0) {
            return 1;
        }
        if (memo[remaining] != null) {
            return memo[remaining];
        }

        long ways = 0;
        for (int num : nums) {
            if (num <= remaining) {
                ways += dfs(remaining - num, nums, memo);
            }
        }

        memo[remaining] = (int) ways;
        return memo[remaining];
    }
}
```

### Bottom-Up Version

Bottom-up is often faster and directly shows the permutation counting order.

```java
class Solution {
    public int combinationSum4(int[] nums, int target) {
        // Size target+1 because dp[sum] is number of ordered ways to form 'sum'.
        long[] dp = new long[target + 1];
        dp[0] = 1;

        for (int sum = 1; sum <= target; sum++) {
            for (int num : nums) {
                if (num <= sum) {
                    dp[sum] += dp[sum - num];
                }
            }
        }

        return (int) dp[target];
    }
}
```

## Complexity Analysis

Let `n = nums.length`.

- **Time Complexity:** $O(n \cdot target)$
- **Space Complexity:** $O(target)$

## Similar Problems

1. [518. Coin Change II](https://leetcode.com/problems/coin-change-ii/) - combinations where order does not matter.
2. [322. Coin Change](https://leetcode.com/problems/coin-change/) - minimum coins variant.
