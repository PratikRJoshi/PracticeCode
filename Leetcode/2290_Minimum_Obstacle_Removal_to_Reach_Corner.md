### 2290. Minimum Obstacle Removal to Reach Corner
Problem: https://leetcode.com/problems/minimum-obstacle-removal-to-reach-corner/

---

### Main Idea: 0-1 Breadth-First Search (BFS)

This problem asks for the path from `(0,0)` to `(m-1, n-1)` with the minimum cost, where the cost is the number of obstacles removed. This is a shortest path problem on a weighted grid. Since the cost of moving to any cell is either 0 (empty) or 1 (obstacle), we can use a highly efficient algorithm called **0-1 BFS**, which is an optimization of Dijkstra's algorithm.

1.  **Model:** The grid is a graph where each cell is a node. The cost to travel to a cell is the value of that cell (0 or 1).
2.  **Algorithm Choice:** A standard BFS fails because edge weights are not uniform. Dijkstra's works, but is slightly overkill. 0-1 BFS is perfect for graphs with only two edge weights (especially 0 and 1).
3.  **0-1 BFS Strategy:** We use a **deque** (double-ended queue) to explore paths.
    *   We keep a `dist` array to store the minimum obstacles removed to reach each cell.
    *   When exploring a neighbor:
        *   If it's a **0-cost move** (to an empty cell), add it to the **front** of the deque. This prioritizes free moves.
        *   If it's a **1-cost move** (to an obstacle), add it to the **back** of the deque. These are explored later.
    *   This process keeps the deque sorted by cost without the `log(n)` overhead of a priority queue.

---

### Requirement → Code Mapping

| Tag | Requirement | Where Implemented |
|---|---|---|
| R1 | Track min obstacles to reach each cell | `int[][] dist = new int[m][n];` |
| R2 | Use a deque for the 0-1 BFS | `Deque<int[]> deque = new LinkedList<>();` |
| R3 | Handle 0-cost moves by adding to the front | `deque.offerFirst(new int[]{nr, nc});` |
| R4 | Handle 1-cost moves by adding to the back | `deque.offerLast(new int[]{nr, nc});` |
| R5 | Prune paths that are not better | `if (newDist >= dist[nr][nc]) continue;` |

---

### 0-1 BFS Solution

```java
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

class Solution {
    public int minimumObstacles(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] DIRS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // R1: dist[r][c] stores the min obstacles to reach cell (r, c)
        int[][] dist = new int[m][n];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = 0;

        // R2: Deque for 0-1 BFS. Stores {row, col}
        Deque<int[]> deque = new LinkedList<>();
        deque.offer(new int[]{0, 0});

        while (!deque.isEmpty()) {
            int[] current = deque.poll();
            int r = current[0];
            int c = current[1];

            for (int[] dir : DIRS) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                    int newDist = dist[r][c] + grid[nr][nc];

                    // R5: If we found a path that's not better, skip it
                    if (newDist < dist[nr][nc]) {
                        dist[nr][nc] = newDist;
                        if (grid[nr][nc] == 0) {
                            // R3: 0-cost move, add to front
                            deque.offerFirst(new int[]{nr, nc});
                        } else {
                            // R4: 1-cost move, add to back
                            deque.offerLast(new int[]{nr, nc});
                        }
                    }
                }
            }
        }

        return dist[m - 1][n - 1];
    }
}
```

---

### Complexity Analysis
*   **Time Complexity: `O(m * n)`**
    *   Each cell is added to and removed from the deque at most once. All operations inside the loop are constant time.

*   **Space Complexity: `O(m * n)`**
    *   The `dist` array and the `deque` can store up to `m * n` elements in the worst case.