# Maximal Square

## Problem Description

**Problem Link:** [Maximal Square](https://leetcode.com/problems/maximal-square/)

Given an `m x n` binary `matrix` filled with `0`'s and `1`'s, *find the largest square containing only `1`'s and return its area*.

**Example 1:**

```
Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
Output: 4
```

**Example 2:**

```
Input: matrix = [["0","1"],["1","0"]]
Output: 1
```

**Example 3:**

```
Input: matrix = [["0"]]
Output: 0
```

**Constraints:**
- `m == matrix.length`
- `n == matrix[i].length`
- `1 <= m, n <= 300`
- `matrix[i][j]` is `'0'` or `'1'`.

## Intuition/Main Idea

This is a **2D dynamic programming** problem. We need to find the largest square of 1's.

**Core Algorithm:**
1. Use DP where `dp[i][j]` = side length of largest square ending at `(i, j)`.
2. If `matrix[i][j] == '1'`, `dp[i][j] = 1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])`.
3. If `matrix[i][j] == '0'`, `dp[i][j] = 0`.
4. Track the maximum side length and return its square.

**Why this works:** For a square ending at `(i, j)` to have side length `k`, the squares ending at `(i-1, j)`, `(i, j-1)`, and `(i-1, j-1)` must all have side length at least `k-1`. Taking the minimum ensures we form a valid square.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| DP for square side | DP array - Line 6 |
| Process each cell | Nested loops - Lines 8-16 |
| Check if cell is '1' | Character check - Line 10 |
| Calculate square side | Min calculation - Line 11 |
| Track maximum side | Max tracking - Line 13 |
| Return area | Return statement - Line 18 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        Integer[][] memo = new Integer[m][n];
        int maxSide = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maxSide = Math.max(maxSide, maxSquare(matrix, i, j, memo));
            }
        }
        
        return maxSide * maxSide;
    }
    
    private int maxSquare(char[][] matrix, int i, int j, Integer[][] memo) {
        if (i < 0 || j < 0 || matrix[i][j] == '0') {
            return 0;
        }
        
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        int result = 1 + Math.min(
            Math.min(maxSquare(matrix, i - 1, j, memo),
                    maxSquare(matrix, i, j - 1, memo)),
            maxSquare(matrix, i - 1, j - 1, memo)
        );
        
        memo[i][j] = result;
        return result;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        // DP: dp[i][j] = side length of largest square ending at (i, j)
        int[][] dp = new int[m][n];
        int maxSide = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        // First row or column: square side is 1
                        dp[i][j] = 1;
                    } else {
                        // Take minimum of three neighbors + 1
                        dp[i][j] = 1 + Math.min(
                            Math.min(dp[i - 1][j], dp[i][j - 1]),
                            dp[i - 1][j - 1]
                        );
                    }
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        
        return maxSide * maxSide;
    }
}
```

**Space-Optimized Version:**

```java
class Solution {
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        int[] dp = new int[n];
        int maxSide = 0;
        int prev = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int temp = dp[j];
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[j] = 1;
                    } else {
                        dp[j] = 1 + Math.min(Math.min(dp[j], dp[j - 1]), prev);
                    }
                    maxSide = Math.max(maxSide, dp[j]);
                } else {
                    dp[j] = 0;
                }
                prev = temp;
            }
        }
        
        return maxSide * maxSide;
    }
}
```

**Explanation of Key Code Sections:**

1. **DP Array (Line 6):** `dp[i][j]` represents the side length of the largest square ending at `(i, j)`.

2. **Process Each Cell (Lines 8-16):** 
   - **Check '1' (Line 10):** Only process cells with '1'.
   - **Boundary (Lines 11-13):** First row or column: square side is 1.
   - **Transition (Lines 14-16):** For other cells, take minimum of three neighbors and add 1.

3. **Track Maximum (Line 13):** Keep track of the maximum side length.

4. **Return Area (Line 18):** Return `maxSide²` (area of square).

**Why min of three neighbors:**
- For a square of side `k` ending at `(i, j)`, we need:
  - Square ending at `(i-1, j)` with side ≥ `k-1`
  - Square ending at `(i, j-1)` with side ≥ `k-1`
  - Square ending at `(i-1, j-1)` with side ≥ `k-1`
- Taking minimum ensures all three conditions are satisfied.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the side length of the largest square ending at `(i, j)`?"
- **Why this works:** To form a square ending at `(i, j)`, we need squares ending at the three adjacent positions to form a valid square.
- **Overlapping subproblems:** Multiple squares may share the same subproblems.

**Example walkthrough:**
- For matrix with 2x2 square of 1's:
- dp[1][1] = 1 + min(dp[0][1], dp[1][0], dp[0][0]) = 1 + min(1,1,1) = 2
- Area = 2² = 4 ✓

## Complexity Analysis

- **Time Complexity:** $O(m \times n)$ where $m$ and $n$ are the grid dimensions. We visit each cell once.

- **Space Complexity:** 
  - Bottom-up with 2D: $O(m \times n)$.
  - Bottom-up optimized: $O(n)$ using 1D array.

## Similar Problems

Problems that can be solved using similar 2D DP patterns:

1. **221. Maximal Square** (this problem) - Square DP
2. **85. Maximal Rectangle** - Rectangle DP
3. **1277. Count Square Submatrices with All Ones** - Count squares
4. **64. Minimum Path Sum** - Path sum DP
5. **62. Unique Paths** - Count paths
6. **120. Triangle** - Triangle path
7. **931. Minimum Falling Path Sum** - Falling path
8. **174. Dungeon Game** - Reverse DP

