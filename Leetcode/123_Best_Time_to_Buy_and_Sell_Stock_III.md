## 123. Best Time to Buy and Sell Stock III (At Most Two Transactions)
Problem: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/

**Problem rules:**
- Maximum **2 transactions**
- Only one stock can be held at a time

**States:**
- `i` → day index
- `holding` → true if holding
- `txn` → remaining transactions

**Transitions:**
- Holding: sell today (txn-1) or hold
- Not holding: buy today or skip

**Code:**
```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if(n == 0){
            return 0;
        }

        // < price , hold, remaining transactions >
        // Why 3? Because the problem allows at most 2 transactions, but we also need to represent 0 remaining transactions. So valid transaction states are 0, 1, 2. That’s 3 possible values.
        Integer[][][] memo = new Integer[n][2][3];

        return dfs(prices, 0, false, 2, memo);
    }

    private int dfs(int[] prices, int index, boolean hold, int remainingTxn, Integer[][][] memo){
        if(index == prices.length || remainingTxn == 0){
            return 0;
        }
        if(memo[index][hold ? 1 : 0][remainingTxn] != null){
            return memo[index][hold ? 1 : 0][remainingTxn];
        }

        int profit = 0;
        if(hold) { // if currently holding
            // either sell 
            int sell = prices[index] + dfs(prices, index + 1, false, remainingTxn - 1, memo);
            // or do nothing
            int skip = dfs(prices, index + 1, true, remainingTxn, memo);
            profit = Math.max(sell, skip);
        } else {
            // either buy
            int buy = -prices[index] + dfs(prices, index + 1, true, remainingTxn, memo); // buying doesn't complete 1 txn
            // or do nothing
            int skip = dfs(prices, index + 1, false, remainingTxn, memo);
            profit = Math.max(buy, skip);
        }

        memo[index][hold ? 1 : 0][remainingTxn] = profit;
        return profit;
    }
}
// Time Complexity: O(n * 2 * (2+1))
// Space Complexity: O(n * 2 * (2+1))
```
