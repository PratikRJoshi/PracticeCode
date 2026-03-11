# Coin Change

## Problem Description

**Problem Link:** [Coin Change](https://leetcode.com/problems/coin-change/)

Given coin denominations and an amount, return minimum number of coins needed to make that amount. Return `-1` if impossible.

## Intuition/Main Idea

Unbounded Knapsack: each coin can be used multiple times.

### Subproblem definition
`minCoins(rem)` = minimum coins needed to make amount `rem`.

### State transition
For each coin:
`minCoins(rem) = min(minCoins(rem), 1 + minCoins(rem - coin))`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Reuse same coin unlimited times | transition keeps same coin set for `rem - coin` |
| Find minimum number of coins | `Math.min(...)` recurrence |
| Return -1 if impossible | sentinel large value check at end |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        // Size amount+1 because remaining amount states are 0..amount.
        Integer[] memo = new Integer[amount + 1];
        int answer = dfs(amount, coins, memo);
        return answer >= Integer.MAX_VALUE / 2 ? -1 : answer;
    }

    private int dfs(int remaining, int[] coins, Integer[] memo) {
        if (remaining == 0) {
            return 0;
        }
        if (remaining < 0) {
            return Integer.MAX_VALUE / 2;
        }
        if (memo[remaining] != null) {
            return memo[remaining];
        }

        int best = Integer.MAX_VALUE / 2;
        for (int coin : coins) {
            best = Math.min(best, 1 + dfs(remaining - coin, coins, memo));
        }

        memo[remaining] = best;
        return memo[remaining];
    }
}
```

### Bottom-Up Version

Bottom-up avoids recursion and is the standard robust implementation.

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        // Size amount+1 because dp[a] represents min coins for amount a.
        int[] dp = new int[amount + 1];

        for (int a = 1; a <= amount; a++) {
            dp[a] = Integer.MAX_VALUE / 2;
        }

        for (int a = 1; a <= amount; a++) {
            for (int coin : coins) {
                if (coin <= a) {
                    dp[a] = Math.min(dp[a], 1 + dp[a - coin]);
                }
            }
        }

        return dp[amount] >= Integer.MAX_VALUE / 2 ? -1 : dp[amount];
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(amount \cdot numberOfCoins)$
- **Space Complexity:** $O(amount)$

## Similar Problems

1. [518. Coin Change II](https://leetcode.com/problems/coin-change-ii/)
2. [279. Perfect Squares](https://leetcode.com/problems/perfect-squares/)
