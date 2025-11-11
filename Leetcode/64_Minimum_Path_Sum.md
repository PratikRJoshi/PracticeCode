# Minimum Path Sum

## Problem Description

**Problem Link:** [Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/)

Given a `m x n` `grid` filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path.

**Note:** You can only move either down or right at any point in time.

**Example 1:**

```
Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
Output: 7
Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.
```

**Example 2:**

```
Input: grid = [[1,2,3],[4,5,6]]
Output: 12
```

**Constraints:**
- `m == grid.length`
- `n == grid[i].length`
- `1 <= m, n <= 200`
- `0 <= grid[i][j] <= 100`

## Intuition/Main Idea

This is a classic **2D dynamic programming** problem. We need to find the minimum path sum from top-left to bottom-right.

**Core Algorithm:**
1. Use DP where `dp[i][j]` = minimum path sum to reach `(i, j)`.
2. We can only come from `(i-1, j)` or `(i, j-1)`.
3. `dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1])`.
4. Handle boundary cases (first row and first column).

**Why DP works:** The problem has overlapping subproblems - the minimum path to `(i, j)` is needed to compute paths to `(i+1, j)` and `(i, j+1)`. We can build the solution bottom-up.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Initialize first row | First row loop - Lines 8-10 |
| Initialize first column | First column loop - Lines 12-14 |
| DP transition | Nested loops - Lines 16-19 |
| Choose minimum path | Math.min - Line 18 |
| Return result | Return statement - Line 20 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        Integer[][] memo = new Integer[m][n];
        return minSum(grid, m - 1, n - 1, memo);
    }
    
    private int minSum(int[][] grid, int i, int j, Integer[][] memo) {
        if (i == 0 && j == 0) {
            return grid[0][0];
        }
        
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE;
        }
        
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        int result = grid[i][j] + Math.min(
            minSum(grid, i - 1, j, memo),
            minSum(grid, i, j - 1, memo)
        );
        
        memo[i][j] = result;
        return result;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        // DP: dp[i][j] = minimum path sum to reach (i, j)
        int[][] dp = new int[m][n];
        
        // Base case: starting position
        dp[0][0] = grid[0][0];
        
        // Initialize first row: can only come from left
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }
        
        // Initialize first column: can only come from top
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        
        // Fill DP table
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // Can come from top or left, choose minimum
                dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        
        return dp[m - 1][n - 1];
    }
}
```

**Space-Optimized Version:**

```java
class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        // Use 1D array: only need previous row
        int[] dp = new int[n];
        dp[0] = grid[0][0];
        
        // Initialize first row
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j - 1] + grid[0][j];
        }
        
        // Process remaining rows
        for (int i = 1; i < m; i++) {
            // First column: can only come from top
            dp[0] += grid[i][0];
            
            // Remaining columns
            for (int j = 1; j < n; j++) {
                // dp[j] = from top, dp[j-1] = from left
                dp[j] = grid[i][j] + Math.min(dp[j], dp[j - 1]);
            }
        }
        
        return dp[n - 1];
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Line 7):** `dp[0][0] = grid[0][0]` - starting position.

2. **First Row (Lines 9-11):** Can only come from left, so accumulate horizontally.

3. **First Column (Lines 13-15):** Can only come from top, so accumulate vertically.

4. **DP Transition (Lines 17-20):** For each cell `(i, j)`:
   - Can come from `(i-1, j)` (top) or `(i, j-1)` (left).
   - Take minimum and add current cell value.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the minimum path sum to reach `(i, j)`?"
- **Why this works:** To reach `(i, j)`, we must come from either `(i-1, j)` or `(i, j-1)`. The optimal path uses the minimum of these two options.
- **Overlapping subproblems:** Multiple paths may share the same subproblems.

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive, recursive. Time: O(m×n), Space: O(m×n) for memo + stack.
- **Bottom-up (iterative):** More efficient, no recursion. Time: O(m×n), Space: O(m×n) or O(n) optimized.
- **When bottom-up is better:** Better for this problem due to no recursion overhead and space optimization possible.

## Complexity Analysis

- **Time Complexity:** $O(m \times n)$ where $m$ and $n$ are the grid dimensions. We visit each cell once.

- **Space Complexity:** 
  - Bottom-up with 2D: $O(m \times n)$.
  - Bottom-up optimized: $O(n)$ using 1D array.

## Similar Problems

Problems that can be solved using similar 2D DP patterns:

1. **64. Minimum Path Sum** (this problem) - 2D DP path sum
2. **62. Unique Paths** - Count paths
3. **63. Unique Paths II** - With obstacles
4. **120. Triangle** - Triangle path sum
5. **931. Minimum Falling Path Sum** - Falling path
6. **174. Dungeon Game** - Reverse DP
7. **221. Maximal Square** - Square DP
8. **85. Maximal Rectangle** - Rectangle DP

