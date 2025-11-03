Perfect, let’s do this step by step 🤝

Your current pattern is **state transitions with `hold` and `notHold`**. For the “only one transaction” version of the stock problem (LeetCode 121), we don’t allow multiple buys/sells — just **one buy and one sell max**.

So we’ll still keep:
- `hold` → max profit if I currently hold a stock.
- `notHold` → max profit if I don’t hold any stock.

But this time, once you sell, you can’t buy again.

---

### Transition logic for single transaction
- `hold = Math.max(hold, -prices[i])`  
  → either I keep holding (yesterday’s `hold`) or I buy today (but since only one transaction is allowed, profit is `-prices[i]`).

- `notHold = Math.max(notHold, hold + prices[i])`  
  → either I keep not holding (yesterday’s `notHold`) or I sell today (yesterday’s `hold + prices[i]`).

---

### Code (minimal changes from your pattern)
```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;

        int hold = -prices[0];   // if we buy on day 0
        int notHold = 0;         // profit = 0 if we do nothing

        for (int i = 1; i < n; i++) {
            hold = Math.max(hold, -prices[i]);            // buy (only once)
            notHold = Math.max(notHold, hold + prices[i]); // sell
        }

        return notHold; // best profit after at most one transaction
    }
}
```

---

### Key difference vs. unlimited transactions version
- In unlimited transactions, `hold` depends on `notHold - prices[i]` (because you can buy after selling).
- In **single transaction**, `hold` just depends on `-prices[i]` (you can only buy once).

---

Want me to also show how this is basically the same as the **classic minPrice trick** (`minPrice` and `maxProfit`) so you can connect the two approaches?
