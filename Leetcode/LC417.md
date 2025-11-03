### 417. Pacific Atlantic Water Flow
Problem: https://leetcode.com/problems/pacific-atlantic-water-flow/

#### Problem Description

There is an `m x n` rectangular island bordered by the Pacific Ocean (left and top edges) and the Atlantic Ocean (right and bottom edges). The island has varying heights represented by a matrix `heights[r][c]`.

Rain water can flow from a cell to any adjacent cell (north, south, east, west) with a height less than or equal to the current cell's height. Water can also flow from cells adjacent to an ocean directly into that ocean.

The task is to find all cells where water can flow to both the Pacific and Atlantic oceans.

#### Example 1:
**Input:** heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]  
**Output:** [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]

#### Example 2:
**Input:** heights = [[2,1],[1,2]]  
**Output:** [[0,0],[0,1],[1,0],[1,1]]

---

### Main Idea & Intuition

The key insight is to use **reverse thinking**:

1. Instead of checking if water can flow from each cell to both oceans (which would be inefficient), we start from the ocean borders and work inward.
2. We perform two separate traversals:
    - One from the Pacific Ocean borders (top and left edges)
    - One from the Atlantic Ocean borders (bottom and right edges)
3. For each traversal, we mark cells that can be reached from that ocean.
4. The cells that can be reached from both oceans are our answer.

This approach works because water flowing from cell A to cell B means cell A's height is greater than or equal to cell B's height. When working backward, we can reach cell A from cell B if cell A's height is greater than or equal to cell B's height.

### Code Implementation with DFS

```java
class Solution {
    // Four possible movement directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return new ArrayList<>();
        }
        
        int rows = heights.length;
        int cols = heights[0].length;
        
        // Create two boolean matrices to track cells reachable from each ocean
        boolean[][] pacificReachable = new boolean[rows][cols];
        boolean[][] atlanticReachable = new boolean[rows][cols];
        
        // Start DFS from the borders
        
        // Pacific borders: top row and leftmost column
        for (int i = 0; i < rows; i++) {
            dfs(heights, pacificReachable, i, 0, rows, cols);           // Left border
            dfs(heights, atlanticReachable, i, cols - 1, rows, cols);   // Right border
        }
        
        for (int j = 0; j < cols; j++) {
            dfs(heights, pacificReachable, 0, j, rows, cols);           // Top border
            dfs(heights, atlanticReachable, rows - 1, j, rows, cols);   // Bottom border
        }
        
        // Find cells reachable from both oceans
        List<List<Integer>> result = new ArrayList<>();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (pacificReachable[i][j] && atlanticReachable[i][j]) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        
        return result;
    }
    
    /**
     * DFS to mark all cells reachable from the current cell
     */
    private void dfs(int[][] heights, boolean[][] reachable, int row, int col, int rows, int cols) {
        // Mark current cell as reachable
        reachable[row][col] = true;
        
        // Check all four adjacent cells
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            // Check if the new position is valid and not yet visited
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols 
                    && !reachable[newRow][newCol] 
                    && heights[newRow][newCol] >= heights[row][col]) {
                // Continue DFS from this new cell
                dfs(heights, reachable, newRow, newCol, rows, cols);
            }
        }
    }
}
```

### BFS Alternative Implementation

```java
class SolutionBFS {
    // Four possible movement directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return new ArrayList<>();
        }

        int rows = heights.length;
        int cols = heights[0].length;

        // Create two boolean matrices to track cells reachable from each ocean
        boolean[][] pacificReachable = new boolean[rows][cols];
        boolean[][] atlanticReachable = new boolean[rows][cols];

        // Queues for BFS
        Queue<int[]> pacificQueue = new LinkedList<>();
        Queue<int[]> atlanticQueue = new LinkedList<>();

        // Add Pacific and Atlantic border cells to respective queues
        for (int i = 0; i < rows; i++) {
            pacificQueue.offer(new int[]{i, 0});      // Left border (Pacific)
            atlanticQueue.offer(new int[]{i, cols-1}); // Right border (Atlantic)
            pacificReachable[i][0] = true;
            atlanticReachable[i][cols-1] = true;
        }

        for (int j = 0; j < cols; j++) {
            pacificQueue.offer(new int[]{0, j});      // Top border (Pacific)
            atlanticQueue.offer(new int[]{rows-1, j}); // Bottom border (Atlantic)
            pacificReachable[0][j] = true;
            atlanticReachable[rows-1][j] = true;
        }

        // BFS from Pacific borders
        bfs(heights, pacificQueue, pacificReachable, rows, cols);

        // BFS from Atlantic borders
        bfs(heights, atlanticQueue, atlanticReachable, rows, cols);

        // Find cells reachable from both oceans
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (pacificReachable[i][j] && atlanticReachable[i][j]) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }

        return result;
    }

    /**
     * BFS to mark all cells reachable from the starting cells in the queue
     */
    private void bfs(int[][] heights, Queue<int[]> queue, boolean[][] reachable, int rows, int cols) {
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];

            // Check all four adjacent cells
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                // Check if the new position is valid and not yet visited
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                        && !reachable[newRow][newCol]
                        && heights[newRow][newCol] >= heights[row][col]) {
                    // Mark as reachable and add to queue
                    reachable[newRow][newCol] = true;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
    }
}
```

### Complexity Analysis

* **Time Complexity**: `O(m * n)` where `m` is the number of rows and `n` is the number of columns
    * We visit each cell at most twice (once from Pacific and once from Atlantic)
    * Each DFS/BFS operation is O(1) per cell

* **Space Complexity**: `O(m * n)`
    * We use two boolean matrices of size `m * n`
    * The recursion stack or queue can grow to `O(m * n)` in the worst case

### Key Insights

1. **Reverse Thinking**: Working backward from the oceans is more efficient than checking each cell individually.

2. **Multiple Traversals**: Using two separate traversals allows us to independently track reachability from each ocean.

3. **DFS vs BFS**: Both approaches work well for this problem. DFS is often more concise, while BFS might be more intuitive for some.

4. **Edge Cases**: Remember to handle empty matrices and border cases properly.
