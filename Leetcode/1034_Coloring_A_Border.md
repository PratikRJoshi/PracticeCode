### 1034. Coloring A Border
Problem: https://leetcode.com/problems/coloring-a-border/

---

#### Flood-fill pattern reference
```java
class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if(image == null || image.length == 0){
            return new int[][]{};
        }

        floodFill(image, sr, sc, image[sr][sc], color);

        return image;
    }

    private static void floodFill(int[][] image, int x, int y, int oldColor, int newColor) {
        if(x < 0 || y < 0 || x >= image.length || y >= image[0].length 
            || image[x][y] != oldColor 
            || image[x][y] == newColor) {
            return;
        }

        oldColor = image[x][y];
        image[x][y] = newColor;
        floodFill(image, x + 1, y, oldColor, newColor);
        floodFill(image, x - 1, y, oldColor, newColor);
        floodFill(image, x, y + 1, oldColor, newColor);
        floodFill(image, x, y - 1, oldColor, newColor);

    }
}
```

---

#### Requirement → Code mapping

| Tag | Requirement | Where implemented |
|-----|-------------|-------------------|
| R1 | Capture original color of the component | `int originalColor = grid[row][col]; // [R1]` |
| R2 | DFS traversal restricted to component cells & mark visited | `visited[x][y] = true; // [R2]` and recursive DFS |
| R3 | Detect border when a cell has <4 same-color neighbors | `if (sameColorNeighbors < 4) { // [R3]` |
| R4 | Collect border cells for later recoloring | `borders.add(new int[]{x, y}); // [R4]` |
| R5 | Paint all collected border cells with the new color | `grid[cell[0]][cell[1]] = color; // [R5]` |

#### Border detection (`sameNeighbor`)
`sameNeighbor` counts, for the current cell, **how many of its 4 orthogonal neighbors are (a) inside the grid and (b) have the `originalColor`.**

* **sameNeighbor == 4  ➜  interior cell**  – surrounded on every side by identical-color cells, so it stays as-is.
* **sameNeighbor < 4  ➜  border cell**  – at least one side touches the grid edge or a different color, so we push the cell to `borders` for later recoloring.

This check is the code realization of requirement **R3**.

---

#### Modified solution (same pattern)

```java
import java.util.*;

class Solution {
    private static final int[][] DIRS = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    
    public int[][] colorBorder(int[][] grid, int row, int col, int color) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n]; // [R2]
        List<int[]> borders = new ArrayList<>();
        int originalColor = grid[row][col]; // [R1]
        
        dfs(grid, row, col, originalColor, visited, borders);
        
        for (int[] cell : borders) {
            grid[cell[0]][cell[1]] = color; // [R5]
        }
        return grid;
    }
    
    private void dfs(int[][] grid, int x, int y, int originalColor,
                     boolean[][] visited, List<int[]> borders) {
        visited[x][y] = true; // [R2]
        int m = grid.length, n = grid[0].length;
        int sameColorNeighbors = 0;
        
        for (int[] d : DIRS) {
            int nx = x + d[0], ny = y + d[1];
            if (nx >= 0 && ny >= 0 && nx < m && ny < n && grid[nx][ny] == originalColor) {
                sameColorNeighbors++;
                if (!visited[nx][ny]) {
                    dfs(grid, nx, ny, originalColor, visited, borders);
                }
            }
        }
        if (sameColorNeighbors < 4) { // [R3]
            borders.add(new int[]{x, y}); // [R4]
        }
    }
}
```

---

#### Why not recolor during DFS traversal?

When solving LC 1034 we must **decide whether a cell is a border _before_ we change any colors**.

* **Border criterion depends on neighbors still having the original color.**  Repainting early mutates `grid[x][y]`, so later DFS calls think this cell is a *different* color and stop, leaving parts of the component unexplored.
* **Neighbor-count logic becomes wrong.**  If a cell has <4 neighbors with the original color *after* some of them were repainted, interior cells may be mis-classified as border (or vice-versa).
* **Classic flood-fill can paint immediately** because every cell will ultimately get the new color, so early mutation is harmless.  In border-coloring only the border changes, so we must:
  1. Traverse the entire component while leaving all colors intact.
  2. Collect cells that satisfy the border rule.
  3. Repaint that collected list in a final pass.

This two-phase approach guarantees correct traversal and accurate border detection.

---

#### Complexity analysis
* **Time**: `O(m × n)` – every cell is visited at most once.
* **Space**: `O(m × n)` – recursion stack + `visited` + `borders`.

---

> The solution preserves the flood-fill recursion structure while adding a border-detection step, making it easy to see how the original pattern adapts to this problem.
