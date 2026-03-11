# Min Cost Climbing Stairs

## Problem Description

**Problem Link:** [Min Cost Climbing Stairs](https://leetcode.com/problems/min-cost-climbing-stairs/)

You are given `cost[i]`, cost to step on stair `i`. You can start at step `0` or `1`. Each move can climb 1 or 2 steps. Return minimum cost to reach the top (index `n`).

## Intuition/Main Idea

Same structure as Climbing Stairs, but use `min` instead of `+`.

### Subproblem definition
`minCost(i)` = minimum cost required to reach step `i` (where `i` can be top `n`).

### State transition
For `i >= 2`:
`minCost(i) = min(minCost(i-1) + cost[i-1], minCost(i-2) + cost[i-2])`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| 1-step or 2-step move | recurrence from `i-1` and `i-2` |
| Pay cost when stepping on stair | `+ cost[i - 1]` and `+ cost[i - 2]` |
| Start from index 0 or 1 | base `dp[0]=0`, `dp[1]=0` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        // Size n+1 because destination is virtual top at index n.
        Integer[] memo = new Integer[n + 1];
        return dfs(n, cost, memo);
    }

    private int dfs(int step, int[] cost, Integer[] memo) {
        if (step <= 1) {
            return 0;
        }
        if (memo[step] != null) {
            return memo[step];
        }

        int oneStepBefore = dfs(step - 1, cost, memo) + cost[step - 1];
        int twoStepsBefore = dfs(step - 2, cost, memo) + cost[step - 2];

        memo[step] = Math.min(oneStepBefore, twoStepsBefore);
        return memo[step];
    }
}
```

### Bottom-Up Version

Bottom-up avoids recursion depth and is straightforward table fill.

```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;

        // Size n+1 because we compute min cost up to the top node n.
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 0;

        for (int step = 2; step <= n; step++) {
            int oneStepBefore = dp[step - 1] + cost[step - 1];
            int twoStepsBefore = dp[step - 2] + cost[step - 2];
            dp[step] = Math.min(oneStepBefore, twoStepsBefore);
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
2. [64. Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/)
