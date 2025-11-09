### 994. Rotting Oranges
### Problem Link: [Rotting Oranges](https://leetcode.com/problems/rotting-oranges/)
### Intuition
This problem presents a grid where:
1. Each cell contains either an empty cell (0), a fresh orange (1), or a rotten orange (2)
2. Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten
3. We need to determine the minimum number of minutes until no fresh oranges are left, or return -1 if it's impossible

The key insight is to use a Breadth-First Search (BFS) approach, which naturally models the "spreading" process of rotting oranges in layers (or time steps). We start with all initially rotten oranges in the queue, and then process them level by level, marking fresh oranges as rotten and counting the minutes it takes.

### Java Reference Implementation (BFS)
```java
class Solution {
    public int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0;
        
        // Add all rotten oranges to the queue and count fresh oranges
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    freshCount++;
                }
            }
        }
        
        // If there are no fresh oranges, return 0
        if (freshCount == 0) {
            return 0;
        }
        
        // If there are fresh oranges but no rotten ones, it's impossible
        if (queue.isEmpty()) {
            return -1;
        }
        
        int minutes = 0;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right
        
        // BFS to rot oranges level by level (minute by minute)
        while (!queue.isEmpty() && freshCount > 0) {
            int size = queue.size();
            
            // Process all oranges at the current level (minute)
            for (int i = 0; i < size; i++) {
                int[] pos = queue.poll();
                
                // Check all four adjacent positions
                for (int[] dir : directions) {
                    int newRow = pos[0] + dir[0];
                    int newCol = pos[1] + dir[1];
                    
                    // If the position is valid and has a fresh orange
                    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] == 1) {
                        // Make the orange rotten
                        grid[newRow][newCol] = 2;
                        queue.offer(new int[]{newRow, newCol});
                        freshCount--;
                    }
                }
            }
            
            // Increment minutes after processing all oranges at the current level
            minutes++;
        }
        
        // If there are still fresh oranges left, it's impossible to rot all of them
        return freshCount == 0 ? minutes : -1;
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: Check for empty grid and cases with no fresh oranges
- **R1 (Identify initial state)**: Count fresh oranges and collect all rotten oranges
- **R2 (BFS approach)**: Use a queue to process oranges level by level, representing minutes
- **R3 (Track progress)**: Decrement fresh orange count as they rot
- **R4 (Check for impossibility)**: Return -1 if there are still fresh oranges after BFS completes

### Example Walkthrough
For the grid:
```
2 1 1
1 1 0
0 1 1
```

1. Initial state:
    - Rotten oranges: (0,0)
    - Fresh oranges: 7
    - Queue: [(0,0)]
    - Minutes: 0

2. Minute 1:
    - Process (0,0) → rot (0,1) and (1,0)
    - Fresh oranges: 5
    - Queue: [(0,1), (1,0)]
    - Minutes: 1

3. Minute 2:
    - Process (0,1) → rot (0,2) and (1,1)
    - Process (1,0) → rot (2,0) (but it's already empty)
    - Fresh oranges: 3
    - Queue: [(0,2), (1,1)]
    - Minutes: 2

4. Minute 3:
    - Process (0,2) → no new rots
    - Process (1,1) → rot (2,1)
    - Fresh oranges: 2
    - Queue: [(2,1)]
    - Minutes: 3

5. Minute 4:
    - Process (2,1) → rot (2,2)
    - Fresh oranges: 1
    - Queue: [(2,2)]
    - Minutes: 4

6. All oranges are rotten, return 4 minutes.

### Complexity
- **Time Complexity**: O(m × n) where m is the number of rows and n is the number of columns. We visit each cell at most once.
- **Space Complexity**: O(m × n) for the queue in the worst case where all oranges are rotten initially.

### Key Insights
1. **BFS vs DFS**: BFS is more suitable for this problem because it naturally models the time progression (level by level).
2. **Counting Fresh Oranges**: Keeping track of fresh oranges allows us to quickly determine if all oranges can be rotted.
3. **Queue Size Tracking**: Processing the queue in batches (based on size) helps us track the minutes accurately.
