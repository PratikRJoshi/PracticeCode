### 650. 2 Keys Keyboard
Problem: https://leetcode.com/problems/2-keys-keyboard/


Below is a top-down memoized Java solution for the problem:

> There is only one character 'A' on the screen of a notepad. You can `Copy All` (copy whole screen) or `Paste` (paste last copied chunk). Given integer `n`, return the minimum number of operations to get exactly `n` 'A's.

This solution follows the same **memoized recursion** idea you used before (like `wordBreak`): define a small state (here `k` = target number of 'A's), write a recursive relation, and memoize results.

---

## Key idea (short)
- Let `dp[k]` = minimum operations to get `k` A's starting from 1 A.
- If `d` is a divisor of `k` (2 ≤ d ≤ k-1), you can:
    - First produce `d` A's optimally (`dp[d]` ops), then `Copy All` once and `Paste` `(k/d - 1)` times → total `dp[d] + (k/d)` operations (because `1 + (k/d - 1) = k/d`).
    - Symmetrically, produce `k/d` A's first, then multiply by `d` → `dp[k/d] + d`.
- So:
  ```
  dp[k] = min_{d | k, 1<d<k} ( dp[d] + k/d,  dp[k/d] + d )
  ```
- Base: `dp[1] = 0`.

This yields a simple top-down recursion on `k` (not on screen/clipboard states), which is compact and efficient enough.

---

## Mapping problem requirements → code
| Requirement | How it's mapped to code |
|---|---|
| Start with one 'A' | base case `dp[1] = 0` |
| Allowed ops: Copy All (copy whole screen) and Paste (paste last copied) | When we scale `d` → `k` by repeating `Paste`, that cost is captured by `k/d` (1 copy + (k/d - 1) pastes → `k/d`) |
| Get exactly `n` | we compute `dfs(n, memo)` |
| Want minimum operations | `dp[k]` stores min operations; `Math.min(...)` picks best divisor strategy |
| Reuse sub-results | `Integer[] memo` caches computed `dp[k]` (null = not computed) |

---

## Full Java code (top-down memo) — keeps pattern similar to your prior memo-coded problems

```java
class Solution {
    public int minSteps(int n) {
        if (n <= 1) return 0;
        Integer[] memo = new Integer[n + 1];
        memo[1] = 0;
        return dfs(n, memo);
    }

    private int dfs(int k, Integer[] memo) {
        if (memo[k] != null) return memo[k];
        int res = k; // worst case: copy once and paste (k-1) times => cost = k

        // try all proper divisors d of k (2..sqrt(k)), and consider both factor orders
        for (int d = 2; d * d <= k; d++) {
            if (k % d == 0) {
                int other = k / d;
                // Option 1: build d, then multiply to k (cost = dp[d] + other)
                res = Math.min(res, dfs(d, memo) + other);
                // Option 2: build other, then multiply to k (cost = dp[other] + d)
                res = Math.min(res, dfs(other, memo) + d);
            }
        }

        memo[k] = res;
        return res;
    }
}
```

---

## Walkthrough example (n = 3)
- `dfs(3)` checks divisors: only 3 is prime, no proper divisor >1, so `res` remains `3` (copy once, paste twice).
- `minSteps(3)` → `3`, which matches example.

For `n = 4`:
- divisors: `2` and `4`. For `d=2`: `dfs(2) + 2`. `dfs(2)` = 2 (copy/paste) so `2+2=4`. But also `dfs(other=2)+2` same. However better strategy: get 2 (2 ops), copy and paste → additional 2 ops total 4. For `4` best is actually 4 (can't do better).
  For composite numbers with better factors the recursion picks the optimal factorization path.

---

## Time & Space Complexity

- **Time Complexity:** `O(n * sqrt(n))` in practice (each `k` is computed once and for each `k` we check divisors up to `sqrt(k)`), which can be loosely thought of as `O(n^{3/2})` in worst-case summed terms. Using memoization means each `dfs(k)` runs divisor loop once.
- **Space Complexity:** `O(n)` for the `memo` array plus recursion stack up to `O(n)` in worst case (if recursion depth grows), so overall `O(n)`.

---

## Notes & alternatives
- This `dp over target k` (factorization-based) is the standard efficient approach for this problem. It avoids modeling (screen, clipboard) explicit states and is both elegant and fast.
- Another correct (but slower) approach is to do BFS/DFS over `(screen, clipboard)` states; that state space can be larger and more cumbersome.
- The factorization recurrence naturally captures the minimal sequence structure: building a smaller block and repeating pastes is the only optimal pattern.

---
