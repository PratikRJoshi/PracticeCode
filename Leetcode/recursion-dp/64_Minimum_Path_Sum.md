# Minimum Path Sum

## Problem Description

**Problem Link:** [Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/)

Given `m x n` grid with non-negative numbers, move only right or down from top-left to bottom-right. Return minimum path sum.

## Intuition/Main Idea

This is Unique Paths style, but replace counting with minimization.

### Subproblem definition
`minCost(row, col)` = minimum path sum from `(row, col)` to destination.

### State transition
`minCost(row, col) = grid[row][col] + min(minCost(row+1,col), minCost(row,col+1))`

Base:
- destination cost is its own value.
- out-of-bounds treated as infinity.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Move right/down only | transitions from down and right |
| Choose cheapest route | `Math.min(down, right)` |
| Include current cell cost | `grid[row][col] + ...` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int minPathSum(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Size rows x cols because each cell is a DP state.
        Integer[][] memo = new Integer[rows][cols];
        return dfs(0, 0, grid, memo);
    }

    private int dfs(int row, int col, int[][] grid, Integer[][] memo) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (row >= rows || col >= cols) {
            return Integer.MAX_VALUE / 2;
        }
        if (row == rows - 1 && col == cols - 1) {
            return grid[row][col];
        }
        if (memo[row][col] != null) {
            return memo[row][col];
        }

        int down = dfs(row + 1, col, grid, memo);
        int right = dfs(row, col + 1, grid, memo);
        memo[row][col] = grid[row][col] + Math.min(down, right);
        return memo[row][col];
    }
}
```

### Bottom-Up Version

Bottom-up is often simpler for grid DP and cache-friendly.

```java
class Solution {
    public int minPathSum(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Size rows x cols for min cost from each cell to destination.
        int[][] dp = new int[rows][cols];

        for (int row = rows - 1; row >= 0; row--) {
            for (int col = cols - 1; col >= 0; col--) {
                if (row == rows - 1 && col == cols - 1) {
                    dp[row][col] = grid[row][col];
                } else {
                    int down = (row + 1 < rows) ? dp[row + 1][col] : Integer.MAX_VALUE / 2;
                    int right = (col + 1 < cols) ? dp[row][col + 1] : Integer.MAX_VALUE / 2;
                    dp[row][col] = grid[row][col] + Math.min(down, right);
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

1. [62. Unique Paths](https://leetcode.com/problems/unique-paths/)
2. [120. Triangle](https://leetcode.com/problems/triangle/)
