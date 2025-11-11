### 994. Rotting Oranges
### Problem Link: [Rotting Oranges](https://leetcode.com/problems/rotting-oranges/)

### Intuition/Main Idea
This problem asks us to find the minimum number of minutes until no fresh oranges are left in a grid. Each minute, any fresh orange adjacent to a rotten orange becomes rotten.

The key insight is to use a **Breadth-First Search (BFS)** approach, which is perfect for finding the shortest path or minimum time in a grid. We start by identifying all initially rotten oranges and enqueue them. Then, we process them level by level, where each level represents one minute of time.

For each rotten orange, we check its four adjacent cells (up, down, left, right). If there's a fresh orange, we make it rotten and add it to the queue for the next level. We continue this process until the queue is empty, meaning no more oranges can rot.

Finally, we check if any fresh oranges remain. If so, it's impossible to rot all oranges, and we return -1. Otherwise, we return the number of minutes elapsed (which is the number of levels processed in our BFS).

This approach ensures we find the minimum time because BFS processes all possibilities at the current time step before moving to the next time step.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count fresh oranges | `int freshCount = 0; for (int i = 0; i < rows; i++) { for (int j = 0; j < cols; j++) { if (grid[i][j] == 1) { freshCount++; } } }` |
| Initialize queue with rotten oranges | `if (grid[i][j] == 2) { queue.offer(new int[]{i, j}); }` |
| Process oranges level by level | `while (!queue.isEmpty() && freshCount > 0) { int size = queue.size(); for (int i = 0; i < size; i++) { /* process current level */ } minutes++; }` |
| Check four adjacent directions | `int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};` |
| Return -1 if impossible | `return freshCount > 0 ? -1 : minutes;` |

### Final Java Code & Learning Pattern

```java
// [Pattern: BFS for Minimum Time/Distance in Grid]
class Solution {
    public int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0;
        
        // Count fresh oranges and add rotten oranges to queue
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    freshCount++;
                } else if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        
        // If there are no fresh oranges, return 0
        if (freshCount == 0) {
            return 0;
        }
        
        // If there are fresh oranges but no rotten ones, impossible
        if (queue.isEmpty()) {
            return -1;
        }
        
        int minutes = 0;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right
        
        // BFS to rot oranges level by level (each level = 1 minute)
        while (!queue.isEmpty() && freshCount > 0) {
            int size = queue.size();
            
            // Process all rotten oranges at the current minute
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                
                // Check all four adjacent cells
                for (int[] dir : directions) {
                    int newRow = cell[0] + dir[0];
                    int newCol = cell[1] + dir[1];
                    
                    // Check if the adjacent cell is valid and has a fresh orange
                    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] == 1) {
                        // Make the orange rotten
                        grid[newRow][newCol] = 2;
                        queue.offer(new int[]{newRow, newCol});
                        freshCount--;
                    }
                }
            }
            
            // Increment time after processing all oranges at current minute
            minutes++;
        }
        
        // If there are still fresh oranges left, it's impossible to rot all
        return freshCount > 0 ? -1 : minutes;
    }
}
```

### Alternative Implementation (Using In-place Marking)

```java
// [Pattern: BFS with In-place Marking]
class Solution {
    public int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        int freshCount = 0;
        Queue<int[]> queue = new LinkedList<>();
        
        // First pass: count fresh oranges and add initial rotten oranges to queue
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    freshCount++;
                } else if (grid[i][j] == 2) {
                    // Use a special marker for initial rotten oranges
                    queue.offer(new int[]{i, j});
                }
            }
        }
        
        // If no fresh oranges, return 0
        if (freshCount == 0) {
            return 0;
        }
        
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int minutes = 0;
        
        // BFS
        while (!queue.isEmpty() && freshCount > 0) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                int[] pos = queue.poll();
                
                for (int[] dir : directions) {
                    int newRow = pos[0] + dir[0];
                    int newCol = pos[1] + dir[1];
                    
                    // Check if adjacent cell is valid and has a fresh orange
                    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] == 1) {
                        // Mark as rotten with the current time + 2
                        // (We add 2 to distinguish from original values 0, 1, 2)
                        grid[newRow][newCol] = minutes + 2 + 2;
                        queue.offer(new int[]{newRow, newCol});
                        freshCount--;
                    }
                }
            }
            
            minutes++;
        }
        
        return freshCount == 0 ? minutes : -1;
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(m \times n)$ where m is the number of rows and n is the number of columns in the grid. In the worst case, we might need to visit every cell in the grid once.
- **Space Complexity**: $O(m \times n)$ for the queue, which in the worst case might contain all cells in the grid.

### Similar Problems
1. **LeetCode 286: Walls and Gates** - Find the distance of each cell to the nearest gate.
2. **LeetCode 542: 01 Matrix** - Find the distance of the nearest 0 for each cell.
3. **LeetCode 1162: As Far from Land as Possible** - Find the maximum distance from land.
4. **LeetCode 1091: Shortest Path in Binary Matrix** - Find the shortest path from top-left to bottom-right.
5. **LeetCode 934: Shortest Bridge** - Find the shortest bridge connecting two islands.
6. **LeetCode 1293: Shortest Path in a Grid with Obstacles Elimination** - Find shortest path with obstacle removal.
7. **LeetCode 1197: Minimum Knight Moves** - Find the minimum number of moves for a knight to reach a position.
8. **LeetCode 752: Open the Lock** - Find the minimum number of moves to open a lock.
