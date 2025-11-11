# Unique Paths III

## Problem Description

**Problem Link:** [Unique Paths III](https://leetcode.com/problems/unique-paths-iii/)

You are given an `m x n` integer array `grid` where `grid[i][j]` could be:

- `1` representing the starting square. There is exactly one starting square.
- `2` representing the ending square. There is exactly one ending square.
- `0` representing empty squares we can walk over.
- `-1` representing obstacles that we cannot walk over.

Return *the number of 4-directional walks from the starting square to the ending square, that walk over every non-obstacle square exactly once*.

**Example 1:**

```
Input: grid = [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
Output: 2
Explanation: We have the following two paths:
1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)
```

**Example 2:**

```
Input: grid = [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
Output: 4
```

**Constraints:**
- `m == grid.length`
- `n == grid[i].length`
- `1 <= m, n <= 20`
- `1 <= m * n <= 20`
- `-1 <= grid[i][j] <= 2`
- There is exactly one starting cell and one ending cell.

## Intuition/Main Idea

This is a **backtracking** problem. We need to count paths that visit all empty cells exactly once.

**Core Algorithm:**
1. Count total empty cells (including start and end).
2. Start DFS from starting cell.
3. Try all four directions.
4. Mark cell as visited, recurse, then unmark.
5. When reaching end with all cells visited, increment count.

**Why backtracking works:** We explore all possible paths. When we finish exploring a path, we backtrack to try other paths. We only count paths that visit all cells.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count empty cells | Count calculation - Lines 7-15 |
| Find start | Start search - Lines 10-14 |
| DFS from start | DFS call - Line 16 |
| Try all directions | Direction loop - Lines 22-26 |
| Check validity | Validity check - Lines 19-21 |
| Mark visited | Mark operation - Line 28 |
| Recurse | Recursive call - Line 29 |
| Backtrack | Unmark operation - Line 30 |
| Count paths | Count increment - Lines 17-18 |
| Return result | Return statement - Line 32 |

## Final Java Code & Learning Pattern

```java
class Solution {
    private int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private int count = 0;
    
    public int uniquePathsIII(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int emptyCount = 0;
        int startRow = 0, startCol = 0;
        
        // Count empty cells and find start
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    emptyCount++;
                } else if (grid[i][j] == 1) {
                    startRow = i;
                    startCol = j;
                    emptyCount++; // Include start cell
                }
            }
        }
        
        dfs(grid, startRow, startCol, emptyCount);
        return count;
    }
    
    private void dfs(int[][] grid, int i, int j, int remaining) {
        // Base case: out of bounds or obstacle
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == -1) {
            return;
        }
        
        // Base case: reached end with all cells visited
        if (grid[i][j] == 2) {
            if (remaining == 0) {
                count++;
            }
            return;
        }
        
        // Mark as visited and try all directions
        grid[i][j] = -1; // Mark as visited
        remaining--;
        
        for (int[] dir : directions) {
            dfs(grid, i + dir[0], j + dir[1], remaining);
        }
        
        // Backtrack: restore cell
        grid[i][j] = 0;
        remaining++;
    }
}
```

**Explanation of Key Code Sections:**

1. **Count Empty Cells (Lines 7-15):** Count all cells we need to visit (0s and start).

2. **DFS (Lines 17-35):**
   - **Base Cases (Lines 19-28):** Return if out of bounds or obstacle. If reached end with all cells visited, increment count.
   - **Mark Visited (Line 29):** Mark cell as obstacle to prevent revisiting.
   - **Try Directions (Lines 31-33):** Try all four directions.
   - **Backtrack (Line 35):** Restore cell value.

**Why backtracking:**
- We need to explore all paths that visit all cells.
- After exploring a path, we backtrack to try others.
- This ensures we count all valid paths.

**Example walkthrough for `grid = [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]`:**
- emptyCount = 9 (including start)
- Start at (0,0): mark, remaining=8 → try directions
- Continue exploring paths...
- When reach (2,2) with remaining=0: count++ ✓
- Result: 2 ✓

## Complexity Analysis

- **Time Complexity:** $O(4^{m \times n})$ in worst case, but pruning significantly reduces this.

- **Space Complexity:** $O(m \times n)$ for recursion stack.

## Similar Problems

Problems that can be solved using similar backtracking patterns:

1. **980. Unique Paths III** (this problem) - Backtracking paths
2. **79. Word Search** - Backtracking search
3. **1219. Path with Maximum Gold** - Backtracking with optimization
4. **37. Sudoku Solver** - Backtracking puzzle
5. **51. N-Queens** - Backtracking placement
6. **526. Beautiful Arrangement** - Backtracking placement
7. **212. Word Search II** - Multiple word search
8. **130. Surrounded Regions** - DFS/BFS

