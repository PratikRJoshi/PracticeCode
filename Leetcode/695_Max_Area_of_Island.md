### 695. Max Area of Island
### Problem Link: [Max Area of Island](https://leetcode.com/problems/max-area-of-island/)

### Intuition/Main Idea
This problem asks us to find the maximum area of an island in a grid. An island is a group of connected 1's (land cells) that are adjacent horizontally or vertically. The key insight is to use Depth-First Search (DFS) or Breadth-First Search (BFS) to explore each island and calculate its area. We need to:
1. Iterate through each cell in the grid
2. When we find a land cell (value 1), perform DFS/BFS to explore the entire island
3. Count the number of cells in the island and update the maximum area
4. Mark visited cells to avoid counting them multiple times

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Find maximum area of island | `int maxArea = 0; ... maxArea = Math.max(maxArea, currentArea);` |
| Explore island using DFS | `private int dfs(int[][] grid, int i, int j)` |
| Mark visited cells | `grid[i][j] = 0;` |
| Check valid cell boundaries | `if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == 0)` |
| Count connected land cells | `1 + dfs(grid, i+1, j) + dfs(grid, i-1, j) + dfs(grid, i, j+1) + dfs(grid, i, j-1)` |

### Final Java Code & Learning Pattern

```java
// [Pattern: Depth-First Search on Grid]
class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        int maxArea = 0;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    // Found a new island, explore it using DFS
                    int currentArea = dfs(grid, i, j);
                    maxArea = Math.max(maxArea, currentArea);
                }
            }
        }
        
        return maxArea;
    }
    
    private int dfs(int[][] grid, int i, int j) {
        // Check if we're out of bounds or if this is not a land cell
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == 0) {
            return 0;
        }
        
        // Mark this cell as visited by changing it to 0
        grid[i][j] = 0;
        
        // Explore in all four directions and count connected land cells
        return 1 + dfs(grid, i+1, j)   // down
                 + dfs(grid, i-1, j)   // up
                 + dfs(grid, i, j+1)   // right
                 + dfs(grid, i, j-1);  // left
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(m \times n)$ where m is the number of rows and n is the number of columns in the grid. In the worst case, we visit each cell once.
- **Space Complexity**: $O(m \times n)$ for the recursion stack in the worst case (if the entire grid is one island). In practice, the space complexity is limited by the size of the largest island.

### Tree Problems Explanation
- **Helper Function**: A helper function is required for the DFS traversal to keep the main function clean and to encapsulate the recursive exploration logic.
- **Global Variable**: No global variable is needed as we can directly return the area from the DFS function and keep track of the maximum in the main function.
- **Current Level Calculation**: At each cell, we calculate if it's part of an island (value 1) and mark it as visited.
- **Return Value**: The DFS function returns the area of the island starting at the current cell, which is 1 (for the current cell) plus the areas from exploring in all four directions.

### Similar Problems
1. **Number of Islands (LeetCode 200)**: Count the number of islands in a grid.
2. **Island Perimeter (LeetCode 463)**: Calculate the perimeter of an island.
3. **Surrounded Regions (LeetCode 130)**: Capture regions surrounded by 'X'.
4. **Number of Closed Islands (LeetCode 1254)**: Count islands that are completely surrounded by water.
5. **Number of Distinct Islands (LeetCode 694)**: Count distinct island shapes.
6. **Flood Fill (LeetCode 733)**: Similar DFS approach to update connected cells.
7. **Pacific Atlantic Water Flow (LeetCode 417)**: Determine cells where water can flow to both oceans.
8. **Word Search (LeetCode 79)**: Find if a word exists in a grid using DFS.
