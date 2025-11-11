# Path with Maximum Gold

## Problem Description

**Problem Link:** [Path with Maximum Gold](https://leetcode.com/problems/path-with-maximum-gold/)

In a gold mine `grid` of size `m x n`, each cell in this mine has an integer representing the amount of gold in that cell, `0` if it is empty.

Return the maximum amount of gold you can collect under the conditions:

- Every time you are located in a cell you will collect all the gold in that cell.
- From your position, you can walk one step to the left, right, up, or down.
- You can't visit the same cell more than once.
- Never visit a cell with `0` gold.
- You can start and stop collecting gold from **any** position in the grid that has some gold.

**Example 1:**

```
Input: grid = [[0,6,0],[5,8,7],[0,9,0]]
Output: 24
Explanation:
[[0,6,0],
 [5,8,7],
 [0,9,0]]
Path to get the maximum gold, 9 -> 8 -> 7.
```

**Example 2:**

```
Input: grid = [[1,0,7],[2,0,6],[3,4,5],[0,3,0],[9,0,20]]
Output: 28
Explanation:
[[1,0,7],
 [2,0,6],
 [3,4,5],
 [0,3,0],
 [9,0,20]]
Path to get the maximum gold, 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7.
```

**Constraints:**
- `m == grid.length`
- `n == grid[i].length`
- `1 <= m, n <= 15`
- `0 <= grid[i][j] <= 100`
- There are at most **25** cells containing gold.

## Intuition/Main Idea

This is a **backtracking** problem. We need to find the maximum gold path starting from any cell.

**Core Algorithm:**
1. For each cell with gold, start DFS.
2. Try all four directions (up, down, left, right).
3. Mark cell as visited, collect gold, recurse.
4. Backtrack by unmarking and subtracting gold.
5. Track maximum gold collected.

**Why backtracking works:** We explore all possible paths from each starting cell. When we finish exploring a path, we backtrack to try other paths.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Try each starting cell | Nested loops - Lines 6-10 |
| DFS from cell | DFS call - Line 9 |
| Try all directions | Direction loop - Lines 15-20 |
| Mark visited | Mark operation - Line 13 |
| Collect gold | Gold collection - Line 14 |
| Recurse | Recursive call - Line 19 |
| Backtrack | Unmark operation - Line 20 |
| Track maximum | Max tracking - Line 11 |
| Return result | Return statement - Line 12 |

## Final Java Code & Learning Pattern

```java
class Solution {
    private int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    public int getMaximumGold(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int maxGold = 0;
        
        // Try each cell as starting point
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0) {
                    maxGold = Math.max(maxGold, dfs(grid, i, j));
                }
            }
        }
        
        return maxGold;
    }
    
    private int dfs(int[][] grid, int i, int j) {
        // Base case: out of bounds or no gold
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == 0) {
            return 0;
        }
        
        // Collect gold and mark as visited
        int gold = grid[i][j];
        grid[i][j] = 0; // Mark as visited
        
        int maxPath = 0;
        // Try all four directions
        for (int[] dir : directions) {
            maxPath = Math.max(maxPath, dfs(grid, i + dir[0], j + dir[1]));
        }
        
        // Backtrack: restore gold
        grid[i][j] = gold;
        
        return gold + maxPath;
    }
}
```

**Explanation of Key Code Sections:**

1. **Try Starting Cells (Lines 6-10):** For each cell with gold, start DFS.

2. **DFS (Lines 13-28):** 
   - **Base Case (Lines 14-16):** Return 0 if out of bounds or no gold.
   - **Collect Gold (Line 18):** Get gold value.
   - **Mark Visited (Line 19):** Set cell to 0 to prevent revisiting.
   - **Try Directions (Lines 21-23):** Try all four directions and take maximum.
   - **Backtrack (Line 25):** Restore gold value.

3. **Return Maximum (Line 27):** Return current gold plus maximum from directions.

**Why backtracking:**
- We need to explore all paths from each starting cell.
- After exploring a path, we backtrack to try other paths.
- This ensures we find the maximum gold path.

**Example walkthrough for `grid = [[0,6,0],[5,8,7],[0,9,0]]`:**
- Start at (0,1): gold=6 → try directions → max from (1,1)=8 → path: 6+8+7=21
- Start at (1,0): gold=5 → try directions → max from (1,1)=8 → path: 5+8+7=20
- Start at (1,1): gold=8 → try directions → max from (1,2)=7, (0,1)=6 → path: 8+7+6=21
- Start at (1,2): gold=7 → try directions → max from (1,1)=8 → path: 7+8+6=21
- Start at (2,1): gold=9 → try directions → max from (1,1)=8 → path: 9+8+7=24 ✓

## Complexity Analysis

- **Time Complexity:** $O(m \times n \times 4^k)$ where $k$ is the maximum path length. We try each cell as start and explore paths.

- **Space Complexity:** $O(k)$ for recursion stack where $k$ is the path length.

## Similar Problems

Problems that can be solved using similar backtracking patterns:

1. **1219. Path with Maximum Gold** (this problem) - Backtracking with optimization
2. **79. Word Search** - Backtracking search
3. **980. Unique Paths III** - Backtracking paths
4. **37. Sudoku Solver** - Backtracking puzzle
5. **51. N-Queens** - Backtracking placement
6. **526. Beautiful Arrangement** - Backtracking placement
7. **212. Word Search II** - Multiple word search
8. **130. Surrounded Regions** - DFS/BFS

