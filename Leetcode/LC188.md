### 188. Best Time to Buy and Sell Stock IV (At Most K Transactions)
Problem: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/

**Problem rules:**
- Maximum **K transactions**
- Only one stock can be held at a time

**States:**
- `i` → day index
- `holding` → true if holding
- `txn` → remaining transactions


**Transitions:** same as [LC123.md])(LC123.md)

**Code:**
```java
class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if(n == 0){
            return 0;
        }

        // < prices, hold, txn >
        Integer[][][] memo = new Integer[n][2][k + 1];
        return dfs(prices, 0, false, k, memo);
    }

    private int dfs(int[] prices, int index, boolean hold, int remainingTxn, Integer[][][] memo){
        if(index == prices.length|| remainingTxn == 0){
            return 0;
        }
        if(memo[index][hold ? 1 : 0][remainingTxn] != null){
            return memo[index][hold ? 1 : 0][remainingTxn];
        }

        int profit = 0;

        if(hold){ // if currently holding
            // either sell
            int sell = prices[index] + dfs(prices, index + 1, false, remainingTxn - 1, memo);
            // or do nothing
            int skip = dfs(prices, index + 1, true, remainingTxn, memo);
            profit = Math.max(sell, skip);
        } else { // it not holding
            // either buy
            int buy = -prices[index] + dfs(prices, index + 1, true, remainingTxn, memo);
            // or do nothing
            int skip = dfs(prices, index + 1, false, remainingTxn, memo);
            profit = Math.max(buy, skip);
        }

        memo[index][hold ? 1 : 0][remainingTxn] = profit;
        return profit;
    }
}
// Time Complexity: O(n * 2 * (k+1))
// Space Complexity: O(n * 2 * (k+1))
```