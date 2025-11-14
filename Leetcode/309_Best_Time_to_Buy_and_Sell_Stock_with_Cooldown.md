### 309. Best Time to Buy and Sell Stock with Cooldown
### Problem Link: [Best Time to Buy and Sell Stock with Cooldown](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)

You are given an array `prices` where `prices[i]` is the price of a given stock on the `ith` day.

Find the maximum profit you can achieve. You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times) with the following restrictions:

- After you sell your stock, you cannot buy stock on the next day (i.e., cooldown one day).
- Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

**Example 1:**
```
Input: prices = [1,2,3,0,2]
Output: 3
Explanation: transactions = [buy, sell, cooldown, buy, sell]
```

**Example 2:**
```
Input: prices = [1]
Output: 0
```

**Constraints:**
- `1 <= prices.length <= 5000`
- `0 <= prices[i] <= 1000`

### Intuition/Main Idea
This problem is an extension of the classic "buy and sell stock" problem with an additional constraint: after selling, you must wait one day before buying again (cooldown). The key insight is to model this as a state machine with three possible states for each day:

1. **Hold**: You are holding a stock on this day (either bought today or was holding from previous days)
2. **Not Hold**: You don't have any stock on this day (either sold today or didn't have any from previous days)
3. **Cooldown**: You just sold a stock yesterday and cannot buy today

The optimal solution uses dynamic programming to track the maximum profit achievable in each state. We can solve this using either a top-down approach (recursion with memoization) or a bottom-up approach (iterative DP).

For the top-down approach, we define a recursive function that explores all possible decisions at each day while keeping track of our current state (holding stock or not, in cooldown or not). We use memoization to avoid redundant calculations.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Handle multiple transactions | Recursive state transitions in `dfs` method |
| Enforce cooldown after selling | `int sell = prices[index] + dfs(index + 1, false, true, prices, memo);` |
| Track maximum profit | `profit = Math.max(sell, stay);` and `profit = Math.max(buy, skip);` |
| Handle base case | `if(index >= prices.length) { return 0; }` |
| Memoize results | `memo[index][hold ? 1 : 0][cooldown ? 1 : 0] = profit;` |

### Final Java Code & Learning Pattern

```java
// [Pattern: State Machine DP with Memoization]
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (prices == null || n == 0) {
            return 0;
        }

        // 3D memoization array to store results:
        // - First dimension: day/index (0 to n-1)
        // - Second dimension: holding stock or not (0 or 1)
        // - Third dimension: in cooldown or not (0 or 1)
        Integer[][][] memo = new Integer[n][2][2];

        // Start from day 0, not holding any stock, not in cooldown
        return dfs(0, false, false, prices, memo);
    }

    private int dfs(int index, boolean hold, boolean cooldown, int[] prices, Integer[][][] memo) {
        // Base case: reached the end of the array
        if (index >= prices.length) {
            return 0;
        }

        // Check if we've already computed this state
        if (memo[index][hold ? 1 : 0][cooldown ? 1 : 0] != null) {
            return memo[index][hold ? 1 : 0][cooldown ? 1 : 0];
        }

        int profit = 0;

        if (hold) { 
            // If currently holding stock, we have two options:
            
            // Option 1: Sell today and go to cooldown tomorrow
            int sell = prices[index] + dfs(index + 1, false, true, prices, memo);
            
            // Option 2: Do nothing and keep holding
            int stay = dfs(index + 1, true, false, prices, memo);
            
            // Choose the option that maximizes profit
            profit = Math.max(sell, stay);
        } else {
            if (cooldown) { 
                // If in cooldown, we can only wait (can't buy today)
                profit = dfs(index + 1, false, false, prices, memo);
            } else { 
                // Not holding and not in cooldown, we have two options:
                
                // Option 1: Buy stock today
                int buy = -prices[index] + dfs(index + 1, true, false, prices, memo);
                
                // Option 2: Skip today (don't buy)
                int skip = dfs(index + 1, false, false, prices, memo);
                
                // Choose the option that maximizes profit
                profit = Math.max(buy, skip);
            }
        }

        // Memoize the result before returning
        memo[index][hold ? 1 : 0][cooldown ? 1 : 0] = profit;
        return profit;
    }
}
```

### Alternative Implementation (Bottom-Up DP)

```java
// [Pattern: State Machine DP with Tabulation]
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        
        int n = prices.length;
        
        // Define three states:
        // hold[i]: Maximum profit if holding stock at the end of day i
        // notHold[i]: Maximum profit if not holding stock at the end of day i (not in cooldown)
        // cooldown[i]: Maximum profit if in cooldown at the end of day i (just sold)
        int[] hold = new int[n];
        int[] notHold = new int[n];
        int[] cooldown = new int[n];
        
        // Initialize states for day 0
        hold[0] = -prices[0];  // Buy stock on day 0
        notHold[0] = 0;        // Do nothing on day 0
        cooldown[0] = 0;       // Cannot be in cooldown on day 0
        
        for (int i = 1; i < n; i++) {
            // If holding stock on day i, either kept it from day i-1 or bought it on day i
            hold[i] = Math.max(hold[i-1], notHold[i-1] - prices[i]);
            
            // If not holding and not in cooldown, either stayed not holding or came out of cooldown
            notHold[i] = Math.max(notHold[i-1], cooldown[i-1]);
            
            // If in cooldown, must have sold on day i
            cooldown[i] = hold[i-1] + prices[i];
        }
        
        // Final state is either not holding or in cooldown (both mean we don't have stock)
        return Math.max(notHold[n-1], cooldown[n-1]);
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(n)$ where n is the length of the prices array. We process each day exactly once in both approaches.
- **Space Complexity**: 
  - Top-down: $O(n)$ for the memoization array (technically $O(n \times 2 \times 2)$ but the constants can be dropped).
  - Bottom-up: $O(n)$ for the three state arrays.

### Dynamic Programming Problems Explanation
- **Intuition behind subproblems**: The subproblems are defined by the day index and the current state (holding/not holding stock, in cooldown or not). Each subproblem represents the maximum profit achievable from that day forward given the current state.

- **Top-down / memoized version**: The provided solution uses top-down DP with memoization. We define a recursive function `dfs(index, hold, cooldown, prices, memo)` that returns the maximum profit starting from day `index` with the given state. The memoization array stores results to avoid redundant calculations.

- **Bottom-up version**: In the bottom-up approach, we use three arrays to track the maximum profit for each state at each day. We build these arrays iteratively from day 0 to day n-1. This approach avoids the overhead of recursive calls and is generally more efficient for this problem.

- **Time complexity comparison**: The bottom-up approach is typically faster in practice because:
  1. It avoids the overhead of recursive function calls
  2. It has better cache locality since it accesses memory sequentially
  3. It doesn't need to check if a state has been computed before (no `if (memo[...] != null)` checks)

### Similar Problems
1. **LeetCode 121: Best Time to Buy and Sell Stock** - Simpler version allowing only one transaction
2. **LeetCode 122: Best Time to Buy and Sell Stock II** - Multiple transactions without cooldown
3. **LeetCode 123: Best Time to Buy and Sell Stock III** - At most two transactions
4. **LeetCode 188: Best Time to Buy and Sell Stock IV** - At most k transactions
5. **LeetCode 714: Best Time to Buy and Sell Stock with Transaction Fee** - Multiple transactions with fee
6. **LeetCode 1014: Best Sightseeing Pair** - Similar DP state transition pattern
7. **LeetCode 198: House Robber** - Similar concept of "cooldown" (can't rob adjacent houses)
8. **LeetCode 213: House Robber II** - Houses in a circle, similar state transitions
