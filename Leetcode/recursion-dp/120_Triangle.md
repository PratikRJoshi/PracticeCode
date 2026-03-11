# Triangle

## Problem Description

**Problem Link:** [Triangle](https://leetcode.com/problems/triangle/)

Given a triangle array, return minimum path sum from top to bottom. At each step, you may move to index `j` or `j+1` in the next row.

## Intuition/Main Idea

Grid DP variation with triangular shape.

### Subproblem definition
`minCost(row, col)` = minimum sum from `triangle[row][col]` to bottom.

### State transition
`minCost(row, col) = triangle[row][col] + min(minCost(row+1,col), minCost(row+1,col+1))`

Base case: last row returns its own value.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Move to adjacent values next row | transitions to `(row+1,col)` and `(row+1,col+1)` |
| Find minimum path sum | `Math.min(leftChild, rightChild)` |
| Start from top | return state from `(0,0)` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
import java.util.List;

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        // Size n x n is safe because col index is always <= row and row < n.
        Integer[][] memo = new Integer[n][n];
        return dfs(0, 0, triangle, memo);
    }

    private int dfs(int row, int col, List<List<Integer>> triangle, Integer[][] memo) {
        if (row == triangle.size() - 1) {
            return triangle.get(row).get(col);
        }
        if (memo[row][col] != null) {
            return memo[row][col];
        }

        int leftChild = dfs(row + 1, col, triangle, memo);
        int rightChild = dfs(row + 1, col + 1, triangle, memo);

        memo[row][col] = triangle.get(row).get(col) + Math.min(leftChild, rightChild);
        return memo[row][col];
    }
}
```

### Bottom-Up Version

Bottom-up is often preferred here because we can reuse one array.

```java
import java.util.List;

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();

        // Size n because each row has at most n elements.
        int[] dp = new int[n];

        for (int col = 0; col < n; col++) {
            dp[col] = triangle.get(n - 1).get(col);
        }

        for (int row = n - 2; row >= 0; row--) {
            for (int col = 0; col <= row; col++) {
                dp[col] = triangle.get(row).get(col) + Math.min(dp[col], dp[col + 1]);
            }
        }

        return dp[0];
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n^2)$
- **Space Complexity:** Top-down $O(n^2)$, bottom-up optimized $O(n)$

## Similar Problems

1. [64. Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/)
2. [1200-series triangle-like grid DP variations]
