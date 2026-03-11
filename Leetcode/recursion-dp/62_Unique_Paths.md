# Unique Paths

## Problem Description

**Problem Link:** [Unique Paths](https://leetcode.com/problems/unique-paths/)

A robot starts at top-left of an `m x n` grid and can move only right or down. Return number of unique paths to bottom-right.

## Intuition/Main Idea

### Subproblem definition
`paths(row, col)` = number of ways to reach bottom-right from `(row, col)`.

### State transition
From each cell, you can go:
- Down: `(row + 1, col)`
- Right: `(row, col + 1)`

So:
`paths(row, col) = paths(row + 1, col) + paths(row, col + 1)`

Base:
- destination cell has 1 path
- out of bounds has 0 paths

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Move only right/down | recurrence with `(r+1,c)` and `(r,c+1)` |
| Count all paths | add both branch counts |
| Destination base case | return `1` at bottom-right |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int uniquePaths(int m, int n) {
        // Size m x n because valid states are all in-grid coordinates.
        Integer[][] memo = new Integer[m][n];
        return dfs(0, 0, m, n, memo);
    }

    private int dfs(int row, int col, int m, int n, Integer[][] memo) {
        if (row >= m || col >= n) {
            return 0;
        }
        if (row == m - 1 && col == n - 1) {
            return 1;
        }
        if (memo[row][col] != null) {
            return memo[row][col];
        }

        memo[row][col] = dfs(row + 1, col, m, n, memo) + dfs(row, col + 1, m, n, memo);
        return memo[row][col];
    }
}
```

### Bottom-Up Version

Bottom-up avoids recursion overhead and directly fills the matrix.

```java
class Solution {
    public int uniquePaths(int m, int n) {
        // Size m x n to represent each grid cell state.
        int[][] dp = new int[m][n];

        for (int row = m - 1; row >= 0; row--) {
            for (int col = n - 1; col >= 0; col--) {
                if (row == m - 1 && col == n - 1) {
                    dp[row][col] = 1;
                } else {
                    int down = (row + 1 < m) ? dp[row + 1][col] : 0;
                    int right = (col + 1 < n) ? dp[row][col + 1] : 0;
                    dp[row][col] = down + right;
                }
            }
        }

        return dp[0][0];
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(mn)$
- **Space Complexity:** $O(mn)$

## Similar Problems

1. [63. Unique Paths II](https://leetcode.com/problems/unique-paths-ii/)
2. [64. Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/)
