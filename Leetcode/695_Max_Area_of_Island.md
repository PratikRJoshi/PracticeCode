### 695. Max Area of Island
### Problem Link: [Max Area of Island](https://leetcode.com/problems/max-area-of-island/)

### Intuition/Main Idea
This problem asks us to find the maximum area of an island in a grid. An island is a group of connected 1's (land cells) that are adjacent horizontally or vertically. The key insight is to use Depth-First Search (DFS) or Breadth-First Search (BFS) to explore each island and calculate its area.

When we find a land cell (value 1), we perform DFS to explore the entire island, counting the number of cells in it. We mark visited cells by changing their value to 0 (water) to avoid counting them multiple times. We keep track of the maximum area found so far and return it at the end.

The DFS approach is particularly elegant for this problem because it naturally explores all connected cells in an island before moving on to the next one. For each cell, we check if it's a land cell, and if so, we recursively explore its four adjacent cells (up, down, left, right).

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

### Alternative Implementation (BFS Approach)

```java
// [Pattern: Breadth-First Search on Grid]
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
                    // Found a new island, explore it using BFS
                    int currentArea = bfs(grid, i, j);
                    maxArea = Math.max(maxArea, currentArea);
                }
            }
        }
        
        return maxArea;
    }
    
    private int bfs(int[][] grid, int startI, int startJ) {
        int area = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startI, startJ});
        grid[startI][startJ] = 0; // Mark as visited
        
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // Down, Up, Right, Left
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            area++; // Count this cell
            
            // Explore all four adjacent cells
            for (int[] dir : directions) {
                int newI = cell[0] + dir[0];
                int newJ = cell[1] + dir[1];
                
                // Check if the adjacent cell is valid and is land
                if (newI >= 0 && newI < grid.length && newJ >= 0 && newJ < grid[0].length && grid[newI][newJ] == 1) {
                    grid[newI][newJ] = 0; // Mark as visited
                    queue.offer(new int[]{newI, newJ});
                }
            }
        }
        
        return area;
    }
}

```

### Complexity Analysis
- **Time Complexity**: $O(m \times n)$ where m is the number of rows and n is the number of columns in the grid. In the worst case, we visit each cell once.
- **Space Complexity**: $O(m \times n)$ for the recursion stack in the worst case (if the entire grid is one island). In practice, the space complexity is limited by the size of the largest island.

### Similar Problems
1. **LeetCode 200: Number of Islands** - Count the number of islands in a grid.
2. **LeetCode 463: Island Perimeter** - Calculate the perimeter of an island.
3. **LeetCode 130: Surrounded Regions** - Capture regions surrounded by 'X'.
4. **LeetCode 1254: Number of Closed Islands** - Count islands that are completely surrounded by water.
5. **LeetCode 694: Number of Distinct Islands** - Count distinct island shapes.
6. **LeetCode 733: Flood Fill** - Similar DFS approach to update connected cells.
7. **LeetCode 417: Pacific Atlantic Water Flow** - Determine cells where water can flow to both oceans.
8. **LeetCode 79: Word Search** - Find if a word exists in a grid using DFS.
