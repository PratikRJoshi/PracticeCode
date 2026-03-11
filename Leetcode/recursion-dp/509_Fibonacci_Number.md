# Fibonacci Number

## Problem Description

**Problem Link:** [Fibonacci Number](https://leetcode.com/problems/fibonacci-number/)

Given `n`, return `F(n)` where:
- `F(0) = 0`
- `F(1) = 1`
- `F(n) = F(n-1) + F(n-2)` for `n > 1`

## Intuition/Main Idea

### Step-by-step intuition
1. Pure recursion follows the definition directly.
2. But recursion repeats states (e.g., `F(3)` computed multiple times).
3. Memoization stores each `F(i)` once.
4. Bottom-up fills from base to target with no recursion overhead.

### Subproblem definition
`dp(i)` = Fibonacci value at index `i`.

### State transition
`dp(i) = dp(i - 1) + dp(i - 2)` for `i >= 2`.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Handle base cases | `if (n <= 1) return n;` |
| Reuse computed results | `memo[i]` cache check |
| Build iteratively | `for (int i = 2; i <= n; i++)` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int fib(int n) {
        // Size n+1 because valid states are indices 0..n inclusive.
        Integer[] memo = new Integer[n + 1];
        return dfs(n, memo);
    }

    private int dfs(int i, Integer[] memo) {
        if (i <= 1) {
            return i;
        }
        if (memo[i] != null) {
            return memo[i];
        }
        memo[i] = dfs(i - 1, memo) + dfs(i - 2, memo);
        return memo[i];
    }
}
```

### Bottom-Up Version

Bottom-up can be faster here due to no recursion stack or function-call overhead.

```java
class Solution {
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }

        // Size n+1 because we fill values from F(0) to F(n).
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n)$
- **Space Complexity:** $O(n)$

## Similar Problems

1. [70. Climbing Stairs](https://leetcode.com/problems/climbing-stairs/)
2. [746. Min Cost Climbing Stairs](https://leetcode.com/problems/min-cost-climbing-stairs/)
