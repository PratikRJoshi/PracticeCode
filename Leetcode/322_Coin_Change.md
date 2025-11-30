# 322. Coin Change

## Problem Description
[Coin Change](https://leetcode.com/problems/coin-change/)

You are given an integer array `coins` representing coins of different denominations and an integer `amount` representing a total amount of money.

Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return `-1`.

You may assume that you have an infinite number of each kind of coin.

**Example 1:**
```
Input: coins = [1,2,5], amount = 11
Output: 3
Explanation: 11 = 5 + 5 + 1
```

**Example 2:**
```
Input: coins = [2], amount = 3
Output: -1
```

**Example 3:**
```
Input: coins = [1], amount = 0
Output: 0
```

**Constraints:**
- `1 <= coins.length <= 12`
- `1 <= coins[i] <= 2^31 - 1`
- `0 <= amount <= 10^4`

## Intuition/Main Idea
This is a classic dynamic programming problem where we need to find the minimum number of coins required to make up a given amount. The key insight is to build solutions incrementally, starting from smaller amounts and using those solutions to solve for larger amounts.

For each amount from 0 to the target amount, we determine the minimum number of coins needed by considering all available coin denominations. For each coin, we check if using it would lead to a better (smaller) solution than what we've found so far.

The core recurrence relation is:
```
dp[amount] = min(dp[amount], dp[amount - coin] + 1) for each coin in coins
```

This means: the minimum number of coins to make up `amount` is the minimum of either the current value of `dp[amount]` or the minimum number of coins to make up `amount - coin` plus one more coin.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Handle edge cases (invalid inputs) | `if (amount < 0 || coins == null || coins.length == 0) { return -1; }` |
| Base case (amount = 0) | `if (amount == 0) { return 0; }` |
| Initialize DP array | `int[] dp = new int[amount + 1]; Arrays.fill(dp, amount + 1);` |
| Set base case in DP | `dp[0] = 0;` |
| Try each coin for each amount | Nested loops: `for (int currentAmount = 1; currentAmount <= amount; currentAmount++)` and `for (int coin : coins)` |
| Update minimum coins needed | `dp[currentAmount] = Math.min(dp[currentAmount], dp[currentAmount - coin] + 1);` |
| Return result with validation | `return dp[amount] > amount ? -1 : dp[amount];` |

## Final Java Code & Learning Pattern

```java
class Solution {
    /**
     * Finds the minimum number of coins needed to make up the given amount.
     * 
     * @param coins Array of coin denominations available
     * @param amount Target amount to make up
     * @return Minimum number of coins needed, or -1 if impossible
     */
    public int coinChange(int[] coins, int amount) {
        // Handle edge cases: negative amount, null or empty coins array
        if (amount < 0 || coins == null || coins.length == 0) {
            return -1;
        }
        
        // Base case: 0 amount requires 0 coins
        if (amount == 0) {
            return 0;
        }
        
        // Create DP array where dp[i] represents the minimum coins needed for amount i
        // Initialize with amount+1 (representing "infinity" - an amount larger than possible)
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        
        // Base case: 0 amount requires 0 coins
        dp[0] = 0;
        
        // Build up the dp array from 1 to amount (bottom-up approach)
        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            // For each amount, try all possible coins
            for (int coin : coins) {
                // Only use the coin if it's not larger than the current amount
                if (coin <= currentAmount) {
                    // The key DP state transition:
                    // dp[currentAmount] = min(dp[currentAmount], dp[currentAmount - coin] + 1)
                    // This means: either keep current minimum or use one coin + minimum for remaining amount
                    dp[currentAmount] = Math.min(dp[currentAmount], dp[currentAmount - coin] + 1);
                }
            }
        }
        
        // If dp[amount] is still amount+1, it means no solution was found
        // Otherwise, return the minimum coins needed
        return dp[amount] > amount ? -1 : dp[amount];
    }
}
```

### Top-down (Memoization) Approach

```java
class Solution {
    /**
     * Finds the minimum number of coins needed to make up the given amount.
     * This is the top-down approach with memoization.
     * 
     * @param coins Array of coin denominations available
     * @param amount Target amount to make up
     * @return Minimum number of coins needed, or -1 if impossible
     */
    public int coinChange(int[] coins, int amount) {
        // Handle edge cases: negative amount, null or empty coins array
        if (amount < 0 || coins == null || coins.length == 0) {
            return -1;
        }
        
        // Base case: 0 amount requires 0 coins
        if (amount == 0) {
            return 0;
        }
        
        // Initialize memoization array with -1 (representing "not calculated yet")
        // Size is amount+1 to include all values from 0 to amount
        int[] memo = new int[amount + 1];
        Arrays.fill(memo, -1);
        memo[0] = 0; // Base case: 0 amount requires 0 coins
        
        return coinChangeHelper(coins, amount, memo);
    }
    
    /**
     * Helper method for the recursive top-down approach.
     * 
     * @param coins Array of coin denominations
     * @param amount Current amount to make change for
     * @param memo Memoization array to avoid redundant calculations
     * @return Minimum coins needed for current amount
     */
    private int coinChangeHelper(int[] coins, int amount, int[] memo) {
        // If we've already calculated this amount, return the cached result
        if (memo[amount] != -1) {
            return memo[amount];
        }
        
        // Initialize with a value larger than any possible answer
        int minCoins = Integer.MAX_VALUE;
        
        // Try each coin denomination
        for (int coin : coins) {
            // Only use the coin if it's not larger than the current amount
            if (coin <= amount) {
                // Recursively find minimum coins for amount - coin
                int subproblem = coinChangeHelper(coins, amount - coin, memo);
                
                // If there's a valid solution for the subproblem
                if (subproblem != -1) {
                    // Update minimum if this path leads to a better solution
                    // The key state transition: min_coins(amount) = 1 + min_coins(amount - coin)
                    minCoins = Math.min(minCoins, subproblem + 1);
                }
            }
        }
        
        // Cache and return the result
        // If minCoins is still Integer.MAX_VALUE, no solution was found
        memo[amount] = (minCoins == Integer.MAX_VALUE) ? -1 : minCoins;
        return memo[amount];
    }
}
```

## Complexity Analysis

- **Time Complexity**: $O(amount \times n)$
  - Bottom-up approach: We have two nested loops - one iterating through amounts (1 to `amount`) and one iterating through coins (n coins)
  - Top-down approach: In the worst case, we might need to compute each amount from 1 to `amount`, and for each amount, we try all n coins
  
- **Space Complexity**: $O(amount)$
  - We use a dp/memo array of size `amount + 1`
  - The recursion call stack in the top-down approach could potentially add $O(amount)$ space in the worst case

## Dynamic Programming Specific Explanations

### Subproblem Definition
The subproblem for this dynamic programming solution is:
- `dp[i]` = minimum number of coins needed to make amount `i`

### State Transition
The state transition is:
- `dp[i] = min(dp[i], dp[i - coin] + 1)` for each coin in coins

This transition means: To make amount `i`, we can either:
1. Keep the current minimum number of coins for amount `i`
2. Use one coin of value `coin` plus the minimum number of coins needed for amount `i - coin`

### Top-down vs Bottom-up Approach
- **Top-down (Memoization)**: This approach starts from the target amount and recursively breaks it down into smaller subproblems. It's more intuitive because it follows the natural thought process of "How can I make this amount using these coins?"
  
- **Bottom-up (Tabulation)**: This approach builds the solution incrementally from the smallest subproblem (amount = 0) up to the target amount. It's generally more efficient because:
  1. It avoids the overhead of recursive function calls
  2. It processes each subproblem exactly once in a predetermined order
  3. It doesn't have the risk of stack overflow for large inputs

The bottom-up approach is particularly better for this problem when the amount is large and the number of coins is small, as it systematically builds solutions without redundant calculations.

### DP Array Size Explanation
- We allocate `dp[amount + 1]` or `memo[amount + 1]` because:
  1. We need to store solutions for all amounts from 0 to the target amount (inclusive)
  2. The 0-indexed array needs one extra position to accommodate the amount value itself

## Similar Problems

- [518. Coin Change 2](https://leetcode.com/problems/coin-change-2/) - Count the number of ways to make up an amount using given coins
- [279. Perfect Squares](https://leetcode.com/problems/perfect-squares/) - Find the minimum number of perfect squares that sum to n
- [983. Minimum Cost For Tickets](https://leetcode.com/problems/minimum-cost-for-tickets/) - Similar DP problem with different constraints
- [1049. Last Stone Weight II](https://leetcode.com/problems/last-stone-weight-ii/) - Can be solved using similar DP approach
- [416. Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/) - Similar DP pattern for subset problems
