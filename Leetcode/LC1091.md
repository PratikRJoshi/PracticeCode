### 1091. Shortest Path in Binary Matrix
Problem: https://leetcode.com/problems/shortest-path-in-binary-matrix/

---

### Main Idea: Breadth-First Search (BFS)

This is a classic shortest path problem on a grid, which is a perfect use case for Breadth-First Search (BFS). BFS guarantees that we find the shortest path in an unweighted graph because it explores the graph layer by layer.

1.  **Start at the beginning:** We begin our search at the top-left cell `(0, 0)`.
2.  **Explore level by level:** We use a queue to explore all reachable cells at the current distance before moving on to the next distance level. This ensures we don't prematurely find a longer path.
3.  **Track path length:** We increment a counter for each level of the BFS. Since the path length is the number of cells, we start our count at 1.
4.  **Check for destination:** As soon as we reach the bottom-right cell `(n-1, n-1)`, we know we've found the shortest path, and we can return the current path length.
5.  **Avoid cycles:** We use a `visited` array to ensure we don't process the same cell multiple times.

---

### BFS Solution (Corrected)

```java
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] == 1 || grid[n - 1][n - 1] == 1) {
            return -1;
        }

        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[n][n];
        visited[0][0] = true;
        q.offer(new int[]{0, 0});

        // Start length at 1 to account for the starting cell
        int len = 0;
        int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        
        while (!q.isEmpty()) {
            int size = q.size();
            len++;
            for (int i = 0; i < size; i++) {
                int[] cell = q.poll();
                if (cell[0] == n - 1 && cell[1] == n - 1) {
                    return len;
                }

                for (int[] dir : dirs) {
                    int x = dir[0] + cell[0];
                    int y = dir[1] + cell[1];

                    if (x < 0 || y < 0 || x >= n || y >= n || grid[x][y] == 1 || visited[x][y]) {
                        continue;
                    }

                    visited[x][y] = true;
                    q.offer(new int[]{x, y});
                }
            }
        }

        return -1;
    }
}
```

---

### The Off-by-One Error: Why `len` Fails When Incremented Incorrectly

The problem defines path length as the **number of cells**. Your original code failed because of an off-by-one error:

*   **Initialization:** You started `len = 0`.
*   **First Level:** When processing the start cell `(0,0)`, `len` is still `0`. If `n=1`, you would incorrectly return `0` instead of `1`.
*   **Increment:** You incremented `len` *after* the `for` loop. This means that when you are checking cells at level `k`, `len` holds the value `k-1`.

**The Fix:** By initializing `len = 1`, you correctly account for the starting cell. The variable `len` now accurately represents the number of cells in the path for the level currently being processed.

---

### Complexity Analysis
*   **Time Complexity: `O(n^2)`**
    *   In the worst case, we visit every cell in the `n x n` grid exactly once.

*   **Space Complexity: `O(n^2)`**
    *   The `visited` array takes `O(n^2)` space.
    *   The `queue` can, in the worst case, hold a large portion of the cells (e.g., in a spiral or checkerboard pattern), also contributing `O(n^2)` space.