### 714. Best Time to Buy and Sell Stock with Transaction Fee
Problem: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/

**Problem rules:**
- Maximum **1 transaction**
- Only one stock can be held at a time
- Transaction fee is paid when selling

**States:**
- `i` → day index
- `holding` → true if holding

**Transitions:**
- Holding: sell today → profit + prices[i] - fee, or hold
- Not holding: buy today → -prices[i], or skip

**Code:**
```java
class Solution {
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        if(n == 0){
            return 0;
        }

        // < prices, hold >
        Integer[][] memo = new Integer[n][2];
        return dfs(prices, 0, false, fee, memo);
    }

    private int dfs(int[] prices, int index, boolean hold, int fee, Integer[][] memo){
        if(index == prices.length){
            return 0;
        }

        if(memo[index][hold ? 1 : 0] != null){
            return memo[index][hold ? 1: 0];
        }

        int profit = 0;
        if(hold) { // if currently holding
            // either sell
            int sell = prices[index] - fee + dfs(prices, index + 1, false, fee, memo);
            // or skip
            int skip = dfs(prices, index + 1, true, fee, memo);
            profit = Math.max(sell, skip);
        } else { // if not holding
            // either buy
            int buy = -prices[index] + dfs(prices, index + 1, true, fee, memo);
            // or do nothing
            int skip = dfs(prices, index + 1, false, fee, memo);
            profit = Math.max(buy, skip);
        }

        memo[index][hold ? 1 : 0] = profit;
        return profit;
    }
}
// Time Complexity: O(n * 2)
// Space Complexity: O(n * 2)
```