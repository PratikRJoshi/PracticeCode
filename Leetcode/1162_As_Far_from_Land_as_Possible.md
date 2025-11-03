### 1162. As Far from Land as Possible
Problem: https://leetcode.com/problems/as-far-from-land-as-possible/

### Main Idea: Multi-Source BFS

Instead of starting a search from every water cell (0) to find the nearest land (1), we can flip the problem around. This is a classic shortest-path-on-a-grid problem, perfect for Breadth-First Search (BFS).

1.  **Start from all land cells at once.** Think of this as a multi-source BFS where every land cell is a starting point.
2.  **Expand in waves.** The BFS will explore the grid in layers. The first layer is all water cells at distance 1 from land, the second layer is all water cells at distance 2, and so on.
3.  **The last cell reached is the answer.** The final water cell visited by this expanding wave is guaranteed to be the one farthest from any land. The number of layers (or the distance) it took to reach it is our answer.
4.  **Handle edge cases.** If there is no land or no water, it's impossible to find a distance, so we return -1.

### Requirement → Code Mapping

| Tag | Requirement | Where Implemented |
|---|---|---|
| R1 | Identify all initial sources (land cells) | `if (grid[r][c] == 1) queue.offer(...)` |
| R2 | Perform a level-by-level search (BFS) | The `while (!queue.isEmpty())` loop structure |
| R3 | Keep track of distance with each level | `distance++` after each level is processed |
| R4 | Explore valid neighbors (in-bounds, unvisited water) | Inner `for (int[] dir : DIRS)` loop with checks |
| R5 | Mark visited cells to prevent cycles | `grid[nr][nc] = 2;` (or use a separate `visited` grid) |
| R6 | Handle edge cases (all land or all water) | `if (queue.isEmpty() || queue.size() == n * n)` |

### Multi-Source BFS Solution

```java
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    private static final int[][] DIRS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int maxDistance(int[][] grid) {
        int n = grid.length;
        Queue<int[]> queue = new LinkedList<>();

        // R1: Add all land cells to the queue as starting points
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    queue.offer(new int[]{r, c});
                }
            }
        }

        // R6: Handle edge cases where the grid is all land or all water
        if (queue.isEmpty() || queue.size() == n * n) {
            return -1;
        }

        int distance = -1;

        // R2: Perform level-order BFS
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                int[] cell = queue.poll();
                int r = cell[0];
                int c = cell[1];

                // R4: Explore neighbors
                for (int[] dir : DIRS) {
                    int nr = r + dir[0];
                    int nc = c + dir[1];

                    if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] == 0) {
                        // R5: Mark as visited and add to queue
                        grid[nr][nc] = 2; // Mark as visited
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
            // R3: Increment distance after processing a full level
            distance++;
        }

        return distance;
    }
}
```

### Complexity Analysis
*   **Time Complexity: `O(n^2)`**
    *   We visit every cell in the `n x n` grid at most once.

*   **Space Complexity: `O(n^2)`**
    *   In the worst case, the queue could hold all the cells of the grid (e.g., a checkerboard pattern), leading to `O(n^2)` space.
