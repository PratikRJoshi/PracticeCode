# Minimum Falling Path Sum

## Problem Description

**Problem Link:** [Minimum Falling Path Sum](https://leetcode.com/problems/minimum-falling-path-sum/)

Given an `n x n` array of integers `matrix`, return *the **minimum sum** of any **falling path** through `matrix`*.

A **falling path** starts at any element in the first row and chooses the element in the next row that is either directly below or diagonally left/right. Specifically, the next element from position `(row, col)` will be `(row + 1, col - 1)`, `(row + 1, col)`, or `(row + 1, col + 1)`.

**Example 1:**

```
Input: matrix = [[2,1,3],[6,5,4],[7,8,9]]
Output: 13
Explanation: There are two falling paths with a minimum sum of 13:
[2,5,6] or [2,4,7]
```

**Example 2:**

```
Input: matrix = [[-19,57],[-40,-5]]
Output: -59
Explanation: The falling path with a minimum sum is [-19,-40].
```

**Constraints:**
- `n == matrix.length == matrix[i].length`
- `1 <= n <= 100`
- `-100 <= matrix[i][j] <= 100`

## Intuition/Main Idea

This is a **2D dynamic programming** problem. We need to find the minimum falling path sum from top to bottom.

**Core Algorithm:**
1. Use DP where `dp[i][j]` = minimum falling path sum to reach `(i, j)`.
2. We can come from `(i-1, j-1)`, `(i-1, j)`, or `(i-1, j+1)`.
3. `dp[i][j] = matrix[i][j] + min(dp[i-1][j-1], dp[i-1][j], dp[i-1][j+1])`.
4. Handle boundary cases (first and last columns).

**Why DP works:** The problem has overlapping subproblems - the minimum path to a cell is needed to compute paths to cells below it. We can build the solution bottom-up.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Base case: first row | First row copy - Lines 7-9 |
| Process each row | Outer loop - Line 11 |
| Handle first column | First column - Line 13 |
| Handle last column | Last column - Line 15 |
| Handle middle columns | Middle columns - Line 17 |
| Return minimum | Return statement - Line 22 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        Integer[][] memo = new Integer[n][n];
        int min = Integer.MAX_VALUE;
        
        for (int j = 0; j < n; j++) {
            min = Math.min(min, minPath(matrix, n - 1, j, memo));
        }
        
        return min;
    }
    
    private int minPath(int[][] matrix, int i, int j, Integer[][] memo) {
        if (i < 0 || j < 0 || j >= matrix.length) {
            return Integer.MAX_VALUE;
        }
        
        if (i == 0) {
            return matrix[0][j];
        }
        
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        int result = matrix[i][j] + Math.min(
            Math.min(minPath(matrix, i - 1, j - 1, memo),
                    minPath(matrix, i - 1, j, memo)),
            minPath(matrix, i - 1, j + 1, memo)
        );
        
        memo[i][j] = result;
        return result;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        
        // DP: dp[i][j] = minimum falling path sum to reach (i, j)
        int[][] dp = new int[n][n];
        
        // Base case: first row
        for (int j = 0; j < n; j++) {
            dp[0][j] = matrix[0][j];
        }
        
        // Process each row
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Can come from (i-1, j-1), (i-1, j), or (i-1, j+1)
                int minPrev = dp[i - 1][j];
                
                if (j > 0) {
                    minPrev = Math.min(minPrev, dp[i - 1][j - 1]);
                }
                if (j < n - 1) {
                    minPrev = Math.min(minPrev, dp[i - 1][j + 1]);
                }
                
                dp[i][j] = matrix[i][j] + minPrev;
            }
        }
        
        // Find minimum in last row
        int min = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            min = Math.min(min, dp[n - 1][j]);
        }
        
        return min;
    }
}
```

**Space-Optimized Version:**

```java
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        
        // Use only previous row
        int[] dp = new int[n];
        System.arraycopy(matrix[0], 0, dp, 0, n);
        
        for (int i = 1; i < n; i++) {
            int[] next = new int[n];
            for (int j = 0; j < n; j++) {
                int minPrev = dp[j];
                if (j > 0) minPrev = Math.min(minPrev, dp[j - 1]);
                if (j < n - 1) minPrev = Math.min(minPrev, dp[j + 1]);
                next[j] = matrix[i][j] + minPrev;
            }
            dp = next;
        }
        
        int min = Integer.MAX_VALUE;
        for (int val : dp) {
            min = Math.min(min, val);
        }
        
        return min;
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Lines 7-9):** First row values are the starting points: `dp[0][j] = matrix[0][j]`.

2. **Process Each Row (Lines 11-20):** For each row `i` and column `j`:
   - **Find Minimum Previous (Lines 13-18):** Check all three possible previous positions:
     - `(i-1, j-1)` if `j > 0`
     - `(i-1, j)` always available
     - `(i-1, j+1)` if `j < n-1`
   - **Update DP (Line 19):** `dp[i][j] = matrix[i][j] + minPrev`.

3. **Find Minimum (Lines 22-25):** The answer is the minimum value in the last row.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the minimum falling path sum to reach `(i, j)`?"
- **Why this works:** To reach `(i, j)`, we must come from one of three positions above. The optimal path uses the minimum of these three options.
- **Overlapping subproblems:** Multiple paths may share the same subproblems.

**Example walkthrough for `matrix = [[2,1,3],[6,5,4],[7,8,9]]`:**
- dp[0] = [2,1,3]
- dp[1][0] = 6 + min(2,1) = 7
- dp[1][1] = 5 + min(2,1,3) = 6
- dp[1][2] = 4 + min(1,3) = 5
- dp[2][0] = 7 + min(7,6) = 13
- dp[2][1] = 8 + min(7,6,5) = 13
- dp[2][2] = 9 + min(6,5) = 14
- Result: min(13,13,14) = 13 ✓

## Complexity Analysis

- **Time Complexity:** $O(n^2)$ where $n$ is the grid size. We visit each cell once.

- **Space Complexity:** 
  - Bottom-up with 2D: $O(n^2)$.
  - Bottom-up optimized: $O(n)$ using 1D array.

## Similar Problems

Problems that can be solved using similar 2D DP patterns:

1. **931. Minimum Falling Path Sum** (this problem) - Falling path DP
2. **120. Triangle** - Triangle path
3. **64. Minimum Path Sum** - Grid path sum
4. **62. Unique Paths** - Count paths
5. **63. Unique Paths II** - With obstacles
6. **174. Dungeon Game** - Reverse DP
7. **221. Maximal Square** - Square DP
8. **85. Maximal Rectangle** - Rectangle DP

