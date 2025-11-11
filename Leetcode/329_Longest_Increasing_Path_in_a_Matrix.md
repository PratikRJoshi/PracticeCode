# Longest Increasing Path in a Matrix

## Problem Description

**Problem Link:** [Longest Increasing Path in a Matrix](https://leetcode.com/problems/longest-increasing-path-in-a-matrix/)

Given an `m x n` integers `matrix`, return *the length of the longest **increasing path** in `matrix`*.

From each cell, you can either move in four directions: left, right, up, or down. You **may not** move **diagonally** or move **outside the boundary** (i.e., wrap-around is not allowed).

**Example 1:**

```
Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
Output: 4
Explanation: The longest increasing path is [1, 2, 6, 9].
```

**Example 2:**

```
Input: matrix = [[3,4,5],[3,2,6],[2,2,1]]
Output: 4
Explanation: The longest increasing path is [3, 4, 5, 6].
```

**Example 3:**

```
Input: matrix = [[1]]
Output: 1
```

**Constraints:**
- `m == matrix.length`
- `n == matrix[i].length`
- `1 <= m, n <= 200`
- `0 <= matrix[i][j] <= 2^31 - 1`

## Intuition/Main Idea

This problem combines **DFS (Depth-First Search)** with **memoization** (dynamic programming). The key insight is that once we compute the longest path starting from a cell, we can reuse that result.

**Core Algorithm:**
1. For each cell, use DFS to find the longest increasing path starting from that cell.
2. During DFS, explore all four directions (up, down, left, right).
3. Only move to cells with values greater than the current cell (increasing path).
4. Use memoization to cache results and avoid recomputation.

**Why DFS + Memoization works:** 
- The problem has overlapping subproblems: the longest path from cell `(i,j)` might be needed when computing paths from neighboring cells.
- Memoization ensures we compute each cell's result only once.
- DFS naturally explores all paths from a starting cell.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Track longest path from each cell | Memoization array - Line 6 |
| Process each cell as starting point | Nested loops - Lines 8-10 |
| DFS to find longest path | `dfs` method - Lines 14-35 |
| Check valid directions | Direction array - Line 16 |
| Validate cell boundaries | Boundary checks - Line 22 |
| Check increasing condition | Value comparison - Line 23 |
| Update maximum path length | `Math.max()` - Line 10 |
| Return final result | Return statement - Line 11 |

## Final Java Code & Learning Pattern

```java
class Solution {
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        // Memoization: memo[i][j] = longest path starting from cell (i, j)
        int[][] memo = new int[m][n];
        int maxPath = 0;
        
        // Try each cell as starting point
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maxPath = Math.max(maxPath, dfs(matrix, i, j, memo));
            }
        }
        
        return maxPath;
    }
    
    private int dfs(int[][] matrix, int i, int j, int[][] memo) {
        // If already computed, return cached result
        if (memo[i][j] != 0) {
            return memo[i][j];
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        int maxLength = 1;  // At least the cell itself
        
        // Explore all four directions
        for (int[] dir : DIRECTIONS) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            
            // Check if new cell is valid and has greater value
            if (newI >= 0 && newI < m && newJ >= 0 && newJ < n && 
                matrix[newI][newJ] > matrix[i][j]) {
                // Recursively find longest path from new cell
                int pathLength = 1 + dfs(matrix, newI, newJ, memo);
                maxLength = Math.max(maxLength, pathLength);
            }
        }
        
        // Cache and return result
        memo[i][j] = maxLength;
        return maxLength;
    }
}
```

**Explanation of Key Code Sections:**

1. **Direction Array (Line 2):** We define the four possible directions: right, down, left, up. This makes the code cleaner and easier to modify.

2. **Memoization Array (Line 6):** `memo[i][j]` stores the length of the longest increasing path starting from cell `(i, j)`. We initialize it to 0 to indicate "not computed yet."

3. **Try Each Starting Cell (Lines 8-10):** We iterate through every cell in the matrix and try it as a starting point. We keep track of the maximum path length found.

4. **DFS Method (Lines 14-35):** 
   - **Memo Check (Lines 16-18):** If we've already computed the result for this cell, return it immediately. This is crucial for efficiency.
   - **Base Case (Line 21):** The minimum path length is 1 (the cell itself).
   - **Explore Directions (Lines 23-30):** For each direction:
     - Calculate the new cell coordinates.
     - Check if the new cell is within bounds and has a greater value (increasing path).
     - Recursively compute the longest path from the new cell and add 1.
     - Update the maximum length.
   - **Cache Result (Lines 33-34):** Store the computed result and return it.

**Why memoization is essential:**
- Without memoization: We might compute the longest path from the same cell multiple times, leading to exponential time complexity.
- With memoization: Each cell's result is computed exactly once, giving us O(m × n) time complexity.

**Why DFS works:**
- DFS naturally explores all paths from a starting cell.
- The increasing condition (`matrix[newI][newJ] > matrix[i][j]`) ensures we don't revisit cells in a cycle.
- The memoization ensures we don't recompute the same subproblem.

**Example walkthrough for `matrix = [[9,9,4],[6,6,8],[2,1,1]]`:**
- Start at (2,1) with value 1:
  - Can go to (2,0)=2 → path length 2
  - Can go to (1,0)=6 → path length 3
  - Can go to (0,0)=9 → path length 4
  - memo[2][1] = 4
- Start at (2,0) with value 2:
  - Can go to (1,0)=6 → path length 3
  - Can go to (0,0)=9 → path length 4
  - memo[2][0] = 4 (uses memo[2][1] when exploring)
- Maximum path length = 4

## Complexity Analysis

- **Time Complexity:** $O(m \times n)$ where $m$ and $n$ are the dimensions of the matrix. Each cell is visited exactly once due to memoization, and each visit checks up to 4 neighbors.

- **Space Complexity:** $O(m \times n)$ for the memoization array plus $O(m \times n)$ for the recursion stack in the worst case (though typically much less due to the increasing constraint).

## Similar Problems

Problems that can be solved using similar DFS + memoization patterns:

1. **329. Longest Increasing Path in a Matrix** (this problem) - DFS + memoization on grid
2. **200. Number of Islands** - DFS on grid
3. **130. Surrounded Regions** - DFS on grid
4. **79. Word Search** - DFS with backtracking
5. **212. Word Search II** - DFS with Trie
6. **417. Pacific Atlantic Water Flow** - DFS from boundaries
7. **695. Max Area of Island** - DFS to count area
8. **463. Island Perimeter** - DFS to count perimeter
9. **733. Flood Fill** - DFS to fill region
10. **1034. Coloring A Border** - DFS to find border

