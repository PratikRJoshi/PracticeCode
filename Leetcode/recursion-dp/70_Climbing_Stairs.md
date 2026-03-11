# Climbing Stairs

## Problem Description

**Problem Link:** [Climbing Stairs](https://leetcode.com/problems/climbing-stairs/)

You are climbing `n` steps. Each time you can climb `1` or `2` steps. Return the number of distinct ways.

## Intuition/Main Idea

This is Fibonacci pattern.

### Step-by-step intuition
- To reach step `i`, last move came from `i-1` (1-step jump) or `i-2` (2-step jump).
- So ways to reach `i` = ways(`i-1`) + ways(`i-2`).

### Subproblem definition
`ways(i)` = number of ways to reach step `i`.

### State transition
`ways(i) = ways(i - 1) + ways(i - 2)`.

Base:
- `ways(0)=1` (one empty way)
- `ways(1)=1`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| 1-step or 2-step move | recurrence using `i-1` and `i-2` |
| Count total distinct ways | return `dp[n]` |
| Handle small `n` | base checks for `n <= 1` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int climbStairs(int n) {
        // Size n+1 because step indices range from 0..n.
        Integer[] memo = new Integer[n + 1];
        return dfs(n, memo);
    }

    private int dfs(int step, Integer[] memo) {
        if (step <= 1) {
            return 1;
        }
        if (memo[step] != null) {
            return memo[step];
        }

        memo[step] = dfs(step - 1, memo) + dfs(step - 2, memo);
        return memo[step];
    }
}
```

### Bottom-Up Version

Bottom-up is often cleaner and avoids recursion overhead.

```java
class Solution {
    public int climbStairs(int n) {
        if (n <= 1) {
            return 1;
        }

        // Size n+1 because we compute ways for each step index 0..n.
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int step = 2; step <= n; step++) {
            dp[step] = dp[step - 1] + dp[step - 2];
        }

        return dp[n];
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n)$
- **Space Complexity:** $O(n)$

## Similar Problems

1. [509. Fibonacci Number](https://leetcode.com/problems/fibonacci-number/)
2. [746. Min Cost Climbing Stairs](https://leetcode.com/problems/min-cost-climbing-stairs/)
