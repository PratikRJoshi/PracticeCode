### 518. Coin Change 2
### Problem Link: [Coin Change 2](https://leetcode.com/problems/coin-change-2/)
### Intuition
This problem asks us to count the number of different ways to make up a given amount using a set of coin denominations. Unlike the original Coin Change problem (322) which asks for the minimum number of coins, this problem focuses on counting all possible combinations.

The key insight is to use dynamic programming with a slightly different approach. We need to build up the solution incrementally, but instead of finding the minimum, we're counting the total number of ways.

We can formulate the recurrence relation as:
`dp[amount] += dp[amount - coin]` for each coin in coins

This means: the number of ways to make up `amount` is the sum of the number of ways to make up `amount - coin` for each usable coin.

### Java Reference Implementation
```java
class Solution {
    public int change(int amount, int[] coins) {
        // [R0] Initialize dp array: dp[i] represents the number of ways to make amount i
        int[] dp = new int[amount + 1];
        
        // [R1] Base case: there is 1 way to make amount 0 (by using no coins)
        dp[0] = 1;
        
        // [R2] For each coin, update the dp array
        for (int coin : coins) {
            // [R3] For each amount from coin value to target amount
            for (int currentAmount = coin; currentAmount <= amount; currentAmount++) {
                // [R4] Add the number of ways to make up (currentAmount - coin)
                dp[currentAmount] += dp[currentAmount - coin];
            }
        }
        
        return dp[amount]; // [R5] Return the total number of ways
    }
}
```

### Alternative Implementation (Recursive with Memoization)
```java
class Solution {
    public int change(int amount, int[] coins) {
        // Create a memoization table
        Integer[][] memo = new Integer[coins.length][amount + 1];
        
        // Start the recursion from the first coin and the target amount
        return changeHelper(coins, 0, amount, memo);
    }
    
    private int changeHelper(int[] coins, int index, int amount, Integer[][] memo) {
        // Base case: we've reached the target amount
        if (amount == 0) {
            return 1;
        }
        
        // Base case: we've used all coins or the amount is negative
        if (index >= coins.length || amount < 0) {
            return 0;
        }
        
        // If we've already computed this state, return the cached result
        if (memo[index][amount] != null) {
            return memo[index][amount];
        }
        
        // Two options: include the current coin or skip it
        int includeCoin = changeHelper(coins, index, amount - coins[index], memo);
        int skipCoin = changeHelper(coins, index + 1, amount, memo);
        
        // Cache and return the result
        memo[index][amount] = includeCoin + skipCoin;
        return memo[index][amount];
    }
}
```

### Understanding the Algorithm and Dynamic Programming

1. **State Definition:**
   - In the bottom-up approach, `dp[i]` represents the number of ways to make amount `i`
   - The base case is `dp[0] = 1` because there is exactly one way to make amount 0 (by using no coins)

2. **Order of Loops:**
   - Unlike the Coin Change problem (322), we iterate through coins in the outer loop and amounts in the inner loop
   - This is crucial because it ensures that we count each combination only once
   - If we reversed the loops, we would count permutations instead of combinations

3. **Recurrence Relation:**
   - For each coin and each amount from coin value to target amount:
     - `dp[amount] += dp[amount - coin]`
     - This adds the number of ways to make up `amount - coin` to the current count

4. **Avoiding Duplicates:**
   - By processing one coin at a time and updating all possible amounts, we ensure that combinations with the same coins in different orders are counted only once
   - For example, with coins [1,2] and amount 3, we count [1,1,1] and [1,2] as distinct combinations, but not [1,2] and [2,1] as separate

5. **Recursive Approach:**
   - In the top-down approach, we have two choices at each step:
     - Include the current coin (if possible) and stay at the same index (to allow reuse)
     - Skip the current coin and move to the next one
   - We memoize the results to avoid redundant calculations

### Requirement → Code Mapping
- **R0 (Initialize dp array)**: `int[] dp = new int[amount + 1];` - Create array to store the number of ways
- **R1 (Base case)**: `dp[0] = 1;` - There is 1 way to make amount 0
- **R2 (Process each coin)**: Iterate through each coin denomination
- **R3 (Update for each amount)**: For each amount from coin value to target amount
- **R4 (Apply recurrence relation)**: `dp[currentAmount] += dp[currentAmount - coin];` - Add the number of ways
- **R5 (Return result)**: `return dp[amount];` - Return the total number of ways

### Complexity Analysis
- **Time Complexity**: O(amount × n)
  - We have two nested loops: one iterating through n coins and one iterating through amounts (from 1 to `amount`)
  - For each coin, we update values for amounts from the coin value to the target amount
  - Overall: O(amount × n)

- **Space Complexity**: O(amount)
  - We use a dp array of size `amount + 1`
  - The space complexity is therefore O(amount)
  - The recursive approach uses O(n × amount) space for the memoization table

### Related Problems
- **Coin Change** (Problem 322): Find the minimum number of coins to make up an amount
- **Combination Sum IV** (Problem 377): Similar but counts permutations instead of combinations
- **Target Sum** (Problem 494): Assign + or - signs to elements to achieve a target sum

### Comparison with Coin Change (Problem 322)

#### Problem Objective Difference
- **Coin Change (322)**: Find the *minimum number* of coins needed to make up an amount
- **Coin Change 2 (518)**: Count the *total number of ways* to make up an amount

#### Key Implementation Differences

1. **DP State Meaning**:
   - **322**: `dp[i]` = minimum number of coins to make amount i
   - **518**: `dp[i]` = number of different ways to make amount i

2. **Initialization**:
   - **322**: `dp[0] = 0`, others initialized to `amount + 1` (representing "infinity")
   - **518**: `dp[0] = 1` (one way to make zero - use no coins), others initialized to `0`

3. **Loop Order** (critical difference):
   - **322**: 
     ```java
     for (int amount = 1; amount <= target; amount++) {
         for (int coin : coins) {
             // Update dp[amount]
         }
     }
     ```
   - **518**: 
     ```java
     for (int coin : coins) {
         for (int amount = coin; amount <= target; amount++) {
             // Update dp[amount]
         }
     }
     ```
   This difference prevents counting the same combination in different orders in problem 518.

4. **Recurrence Relation**:
   - **322**: `dp[amount] = Math.min(dp[amount], dp[amount - coin] + 1)`
   - **518**: `dp[amount] += dp[amount - coin]`

5. **Result Interpretation**:
   - **322**: Return `-1` if no solution exists (`dp[amount] > amount`)
   - **518**: Always return `dp[amount]` (could be 0 if no ways exist)

These differences reflect the fundamental distinction between finding a minimum value (optimization problem) and counting all possibilities (combinatorial problem).
