## 309. Best Time to Buy and Sell Stock with Cooldown
Problem: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/

All of these state transitions can be converted to **top-down memoization (recursive DP)**. Let’s carefully map it.

---

### Step 1: Define your states for recursion

For each day `i`, your state must include:

1. `i` → the day index.
2. `holding` → whether you are currently holding a stock (true/false).
3. `cooldown` → whether you are in cooldown (i.e., cannot buy today).

Function signature could be:

```java
int dfs(int i, boolean holding, boolean cooldown, int[] prices, int[][][] memo)
```

- `memo[i][holding ? 1 : 0][cooldown ? 1 : 0]` stores max profit from day `i` in that state.

---

### Step 2: Base case

```java
if(i >= prices.length) return 0; // no more days left
```

---

### Step 3: Transition rules as recursive calls

- **If holding**:
    - Sell today → move to cooldown tomorrow:

      ```java
      int sell = prices[i] + dfs(i + 1, false, true, prices, memo);
      ```
    - Do nothing → stay holding:

      ```java
      int stay = dfs(i + 1, true, false, prices, memo);
      ```
    - Return `max(sell, stay)`

- **If not holding**:
    - If in cooldown → cannot buy today, only wait:

      ```java
      int wait = dfs(i + 1, false, false, prices, memo);
      ```
    - If not in cooldown → can buy or wait:

      ```java
      int buy = -prices[i] + dfs(i + 1, true, false, prices, memo);
      int skip = dfs(i + 1, false, false, prices, memo);
      return max(buy, skip);
      ```

---

### Step 4: Memoization

Store results to avoid recomputation:

```java
if(memo[i][holding ? 1 : 0][cooldown ? 1 : 0] != -1) 
    return memo[i][holding ? 1 : 0][cooldown ? 1 : 0];
```

---

### Step 5: Top-level call

```java
int n = prices.length;
int[][][] memo = new int[n][2][2];
for(int i=0;i<n;i++)
    for(int j=0;j<2;j++)
        Arrays.fill(memo[i][j], -1);
        
return dfs(0, false, false, prices, memo);
```

---

### ✅ Summary

- **All the state transitions (`hold`, `notHold`, `cooldown`) can be directly modeled recursively.**
- Each state variable becomes a **parameter** in the recursive function.
- Memoization ensures **O(n * 2 * 2)** complexity instead of exponential recursion.

---

### Full Java code (Top-Down with Memoization)

```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if(prices == null || n == 0){
            return 0;
        }

        // 3 states to keep track of: day/index, buy/sell, cooldown/not-cooldown
        Integer[][][] memo = new Integer[n][2][2];

        return dfs(0, false, false, prices, memo);
    }

    private int dfs(int index, boolean hold, boolean cooldown, int[] prices, Integer[][][] memo){
        if(index >= prices.length){
            return 0;
        }

        if(memo[index][hold ? 1 : 0][cooldown ? 1 : 0] != null){
            return memo[index][hold ? 1 : 0][cooldown ? 1 : 0];
        }

        int profit = 0;

        if(hold){ // if currently holding
            // sell today and go to cooldown
            int sell = prices[index] + dfs(index + 1, false, true, prices, memo);
            // or do nothing and keep holding
            int stay = dfs(index + 1, true, false, prices, memo);
            profit = Math.max(sell, stay);
        } else {
            if(cooldown){ // if currently in cooldown
                // don't buy, just wait
                profit = dfs(index + 1, false, false, prices, memo);
            } else { // no cooldown, no hold, then buy today or skip today
                // buy
                int buy = -prices[index] + dfs(index + 1, true, false, prices, memo);
                // skip
                int skip = dfs(index + 1, false, false, prices, memo);
                profit = Math.max(buy, skip);
            }
        }

        memo[index][hold ? 1 : 0][cooldown ? 1 : 0] = profit;
        return profit;
    }
}
```

---

This mirrors the **iterative state-transition DP** version, just now in **recursive top-down form** with memoization.

You can see how each **problem rule** directly translates into **state variables** and **recursive choices**.
