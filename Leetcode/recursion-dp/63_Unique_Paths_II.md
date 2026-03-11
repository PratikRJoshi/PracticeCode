# Unique Paths II

## Problem Description

**Problem Link:** [Unique Paths II](https://leetcode.com/problems/unique-paths-ii/)

Same as Unique Paths, but some cells are blocked (`1`). You cannot step on blocked cells. Return number of unique paths.

## Intuition/Main Idea

Same DP as Unique Paths + obstacle check.

### Subproblem definition
`paths(row, col)` = number of valid paths from `(row, col)` to destination.

### State transition
If current cell is obstacle: `0`.
Else:
`paths(row, col) = paths(row + 1, col) + paths(row, col + 1)`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Blocked cell cannot be used | `if (grid[row][col] == 1) return 0;` |
| Move right/down only | sum down and right states |
| Return total valid paths | `return dp[0][0];` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int rows = obstacleGrid.length;
        int cols = obstacleGrid[0].length;

        // Size rows x cols because state is each valid in-grid cell.
        Integer[][] memo = new Integer[rows][cols];
        return dfs(0, 0, obstacleGrid, memo);
    }

    private int dfs(int row, int col, int[][] grid, Integer[][] memo) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (row >= rows || col >= cols || grid[row][col] == 1) {
            return 0;
        }
        if (row == rows - 1 && col == cols - 1) {
            return 1;
        }
        if (memo[row][col] != null) {
            return memo[row][col];
        }

        memo[row][col] = dfs(row + 1, col, grid, memo) + dfs(row, col + 1, grid, memo);
        return memo[row][col];
    }
}
```

### Bottom-Up Version

Bottom-up is iterative and naturally handles boundary checks.

```java
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int rows = obstacleGrid.length;
        int cols = obstacleGrid[0].length;

        // Size rows x cols to track ways from each cell to destination.
        int[][] dp = new int[rows][cols];

        for (int row = rows - 1; row >= 0; row--) {
            for (int col = cols - 1; col >= 0; col--) {
                if (obstacleGrid[row][col] == 1) {
                    dp[row][col] = 0;
                } else if (row == rows - 1 && col == cols - 1) {
                    dp[row][col] = 1;
                } else {
                    int down = (row + 1 < rows) ? dp[row + 1][col] : 0;
                    int right = (col + 1 < cols) ? dp[row][col + 1] : 0;
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

1. [62. Unique Paths](https://leetcode.com/problems/unique-paths/)
2. [64. Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/)
