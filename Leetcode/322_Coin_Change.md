### 322. Coin Change
### Problem Link: [Coin Change](https://leetcode.com/problems/coin-change/)
### Intuition
This problem asks us to find the minimum number of coins needed to make up a given amount, given a set of coin denominations. This is a classic dynamic programming problem.

The key insight is to build up the solution incrementally. For each amount from 1 to the target amount, we determine the minimum number of coins needed by considering all possible coin denominations. For each coin, we check if using it would lead to a better solution than what we've found so far.

We can formulate the recurrence relation as:
`dp[amount] = min(dp[amount], dp[amount - coin] + 1)` for each coin in coins

This means: the minimum number of coins to make up `amount` is the minimum of either the current value of `dp[amount]` or the minimum number of coins to make up `amount - coin` plus one more coin.

### Java Reference Implementation
```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        if (amount < 0 || coins == null || coins.length == 0) { // [R0] Handle edge cases
            return -1;
        }
        
        if (amount == 0) { // [R1] Base case: 0 amount requires 0 coins
            return 0;
        }
        
        // [R2] Initialize dp array with amount + 1 (representing "infinity")
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0; // [R3] Base case: 0 amount requires 0 coins
        
        // [R4] Build up the dp array from 1 to amount
        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            // [R5] Try each coin denomination
            for (int coin : coins) {
                // [R6] Check if the coin can be used (not larger than current amount)
                if (coin <= currentAmount) {
                    // [R7] Update dp[currentAmount] if using this coin leads to a better solution
                    dp[currentAmount] = Math.min(dp[currentAmount], dp[currentAmount - coin] + 1);
                }
            }
        }
        
        // [R8] Check if a solution was found
        return dp[amount] > amount ? -1 : dp[amount];
    }
}
```

### Alternative Implementation (Top-down with Memoization)
```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        if (amount < 0 || coins == null || coins.length == 0) {
            return -1;
        }
        
        if (amount == 0) {
            return 0;
        }
        
        // Initialize memoization array with -1 (representing "not calculated yet")
        int[] memo = new int[amount + 1];
        Arrays.fill(memo, -1);
        memo[0] = 0;
        
        return coinChangeHelper(coins, amount, memo);
    }
    
    private int coinChangeHelper(int[] coins, int amount, int[] memo) {
        // If we've already calculated this amount, return the cached result
        if (memo[amount] != -1) {
            return memo[amount];
        }
        
        // Initialize with a value larger than any possible answer
        int minCoins = Integer.MAX_VALUE;
        
        // Try each coin denomination
        for (int coin : coins) {
            if (coin <= amount) {
                int subproblem = coinChangeHelper(coins, amount - coin, memo);
                
                // If there's a valid solution for the subproblem
                if (subproblem != -1) {
                    minCoins = Math.min(minCoins, subproblem + 1);
                }
            }
        }
        
        // Cache and return the result
        memo[amount] = (minCoins == Integer.MAX_VALUE) ? -1 : minCoins;
        return memo[amount];
    }
}
```

### Understanding the Algorithm and Boundary Checks

1. **Dynamic Programming Approach:**
   - We use a bottom-up approach, building solutions for smaller amounts first
   - `dp[i]` represents the minimum number of coins needed to make amount `i`
   - We initialize `dp[0] = 0` (base case: 0 amount requires 0 coins)
   - For all other amounts, we initialize with `amount + 1` (representing "infinity")

2. **Recurrence Relation:**
   - For each amount `i` and each coin `c`:
     - If `c <= i`, we can use this coin
     - We update `dp[i] = min(dp[i], dp[i-c] + 1)`
     - This means: either keep the current minimum or use one coin `c` plus the minimum coins needed for `i-c`

3. **Final Check:**
   - If `dp[amount] > amount`, it means no valid combination was found
   - This is because the maximum number of coins needed is `amount` (all 1-value coins)
   - In this case, we return -1

4. **Top-down Approach (Alternative):**
   - Uses recursion with memoization to avoid redundant calculations
   - Solves the problem by breaking it down into smaller subproblems
   - Caches results to avoid recomputing the same subproblems

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (amount < 0 || coins == null || coins.length == 0) { return -1; }` - Return -1 for invalid inputs
- **R1 (Base case)**: `if (amount == 0) { return 0; }` - 0 amount requires 0 coins
- **R2 (Initialize dp array)**: Create and initialize dp array with "infinity" values
- **R3 (Set base case)**: `dp[0] = 0;` - 0 amount requires 0 coins
- **R4 (Build dp array)**: Iterate from 1 to amount to build solutions incrementally
- **R5 (Try each coin)**: For each amount, try all possible coins
- **R6 (Check if coin is usable)**: `if (coin <= currentAmount)` - Ensure coin is not larger than current amount
- **R7 (Update dp value)**: `dp[currentAmount] = Math.min(dp[currentAmount], dp[currentAmount - coin] + 1);` - Update if better solution found
- **R8 (Check for solution)**: `return dp[amount] > amount ? -1 : dp[amount];` - Return -1 if no solution, otherwise return minimum coins

### Complexity Analysis
- **Time Complexity**: O(amount × n)
  - We have two nested loops: one iterating through amounts (1 to `amount`) and one iterating through coins (n coins)
  - For each amount, we consider all n coins
  - Overall: O(amount × n)

- **Space Complexity**: O(amount)
  - We use a dp array of size `amount + 1`
  - The space complexity is therefore O(amount)

### Related Problems
- **Coin Change 2** (Problem 518): Count the number of ways to make up an amount using given coins
- **Minimum Cost For Tickets** (Problem 983): Similar DP problem with different constraints
- **Perfect Squares** (Problem 279): Find the minimum number of perfect squares that sum to n
