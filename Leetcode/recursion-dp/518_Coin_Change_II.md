# Coin Change II

## Problem Description

**Problem Link:** [Coin Change II](https://leetcode.com/problems/coin-change-ii/)

Given coin denominations and amount, return number of combinations that make up amount. You have infinite copies of each coin.

## Intuition/Main Idea

Unbounded knapsack counting version.

### Subproblem definition
`ways(index, remaining)` = number of ways to make `remaining` using coins from `index` onward.

### State transition
At each index:
- Skip this coin: `ways(index + 1, remaining)`
- Take this coin (stay at same index because unlimited reuse): `ways(index, remaining - coins[index])`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Count combinations, not minimum coins | DP stores counts and sums them |
| Unlimited coin reuse | take branch keeps same index |
| Avoid permutation double-counting | process coins outer loop in bottom-up |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int change(int amount, int[] coins) {
        // Size coins.length x (amount+1): states are (index, remaining).
        Integer[][] memo = new Integer[coins.length][amount + 1];
        return dfs(0, amount, coins, memo);
    }

    private int dfs(int index, int remaining, int[] coins, Integer[][] memo) {
        if (remaining == 0) {
            return 1;
        }
        if (index == coins.length || remaining < 0) {
            return 0;
        }
        if (memo[index][remaining] != null) {
            return memo[index][remaining];
        }

        int skip = dfs(index + 1, remaining, coins, memo);
        int take = dfs(index, remaining - coins[index], coins, memo);

        memo[index][remaining] = skip + take;
        return memo[index][remaining];
    }
}
```

### Bottom-Up Version

Bottom-up with coins outer loop naturally counts combinations (not permutations).

```java
class Solution {
    public int change(int amount, int[] coins) {
        // Size amount+1 because dp[a] is ways to make amount a.
        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int coin : coins) {
            for (int currentAmount = coin; currentAmount <= amount; currentAmount++) {
                dp[currentAmount] += dp[currentAmount - coin];
            }
        }

        return dp[amount];
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(numberOfCoins \cdot amount)$
- **Space Complexity:** Top-down $O(numberOfCoins \cdot amount)$, bottom-up $O(amount)$

## Similar Problems

1. [322. Coin Change](https://leetcode.com/problems/coin-change/)
2. [494. Target Sum](https://leetcode.com/problems/target-sum/)
