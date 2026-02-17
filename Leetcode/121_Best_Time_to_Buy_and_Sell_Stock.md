# Best Time to Buy and Sell Stock

## Problem Description

**Problem Link:** [Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)

You are given an array `prices` where `prices[i]` is the price of a given stock on the `i`-th day.

You want to maximize your profit by choosing a **single day** to buy one stock and choosing a **different day in the future** to sell that stock.

Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return `0`.

**Example 1:**
```
Input: prices = [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6 - 1 = 5.
```

**Example 2:**
```
Input: prices = [7,6,4,3,1]
Output: 0
Explanation: No transactions are done because the price is always decreasing.
```

## Intuition/Main Idea

### Build the intuition (mentor-style)

You are allowed **one buy** and **one sell** (buy must happen before sell). So for any day `i` as a potential sell day, the best buy day must be the **cheapest price seen before day `i`**.

That suggests a one-pass strategy:

1. As you scan days from left to right, keep track of the **minimum price so far**.
2. If you sell today, your profit would be:
   - `prices[i] - minPriceSoFar`
3. Keep the maximum profit seen.

### Why the intuition works

For a fixed sell day `i`, the profit is `prices[i] - prices[buyDay]`.

To maximize that, you want `prices[buyDay]` to be as small as possible among all days `< i`.

So the “best buy before today” is exactly `minPriceSoFar`, and checking every day as a sell candidate guarantees we consider the optimal transaction.

### Connecting to the `hold` / `notHold` DP pattern

Even though this problem is typically solved greedily, it matches a simple 2-state DP view:

- `hold` = best profit if we are holding one stock after processing day `i`.
- `notHold` = best profit if we are not holding any stock after processing day `i`.

Because we are allowed **only one transaction**, the only way to enter `hold` is by buying once from profit `0`:

- `hold = max(hold, -prices[i])`

And we can sell from `hold` to update `notHold`:

- `notHold = max(notHold, hold + prices[i])`

The greedy `minPriceSoFar` solution is essentially the same idea, just simplified.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Only one transaction (buy once, sell once later) | Maintain `minPriceSoFar` and consider each day as a sell day once (lines 13-29) |
| Buy must happen before sell | `minPriceSoFar` updated before profit check for future days (loop order) (lines 19-27) |
| Return `0` if no profit possible | Initialize `maxProfit = 0` and only improve it when positive profits exist (lines 16-27) |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int maxProfit(int[] prices) {
        // Edge case: if there are fewer than 2 days, we can't complete a buy+sell.
        if (prices == null || prices.length < 2) {
            return 0;
        }

        // minPriceSoFar tracks the cheapest price we've seen up to the current day.
        // Reason: if we sell today, the best buy day must be the minimum price before today.
        int minPriceSoFar = prices[0];

        // maxProfit is initialized to 0 because we are allowed to do nothing.
        int maxProfit = 0;

        // Loop condition reasoning:
        // - start from day 1 because day 0 can only be a buy day, not a sell day.
        for (int dayIndex = 1; dayIndex < prices.length; dayIndex++) {
            int todaysPrice = prices[dayIndex];

            // If we sell today, profit uses the cheapest price we've seen before today.
            int profitIfSellToday = todaysPrice - minPriceSoFar;
            maxProfit = Math.max(maxProfit, profitIfSellToday);

            // Update the minimum price after processing today's selling option.
            // This ensures minPriceSoFar always represents the best possible buy price
            // for any future selling day.
            minPriceSoFar = Math.min(minPriceSoFar, todaysPrice);
        }

        return maxProfit;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$

- One pass through the array.

**Space Complexity:** $O(1)$

- Only constant extra variables.

## Similar Problems

- [Best Time to Buy and Sell Stock II](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/) - multiple transactions allowed
- [Best Time to Buy and Sell Stock III](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/) - at most two transactions (DP)
- [Best Time to Buy and Sell Stock with Cooldown](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/) - DP with cooldown state
