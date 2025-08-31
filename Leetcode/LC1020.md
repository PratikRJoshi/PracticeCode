### 1020. Number of Enclaves
Problem: https://leetcode.com/problems/number-of-enclaves/

---

Below is a compact mapping from **problem requirements → code** followed by a DFS Java solution that mirrors the two-phase mark-and-finalize pattern you used in `LC130`.

## Problem → State mapping (how rules translate to code)

**Problem rules (short):**
- Grid `m × n` with `0 = sea`, `1 = land`.
- A **move** can go to a 4-adjacent land cell or **walk off the grid** (any edge).
- Count **land cells that can *never* reach the edge** by any sequence of moves. These are the *enclaves*.

**Key idea (same as surrounded-regions):**
- Instead of searching for enclaves directly, first **mark all land cells that can reach the border** as *safe*.
- Remaining unmarked land cells are the answer.

**State definition (DFS):**
- `dfs(r,c)` → marks cell `(r,c)` and all connected land cells as safe (changes value `1 → 2`).
- The grid itself acts as the visited/memo structure; no extra set.

**Base cases:**
- `(r,c)` out of bounds or `grid[r][c] != 1` → stop.

**Transition:**
- Recurse into four neighbours `(r±1,c)` and `(r,c±1)`.

---

## Full Java code (DFS marking)

```java
class Solution {
    private int m, n;

    public int numEnclaves(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        m = grid.length;
        n = grid[0].length;

        // 1️⃣ Mark border-reachable land as safe (value 2)
        for (int r = 0; r < m; r++) {
            dfs(grid, r, 0);
            dfs(grid, r, n - 1);
        }
        for (int c = 0; c < n; c++) {
            dfs(grid, 0, c);
            dfs(grid, m - 1, c);
        }

        // 2️⃣ Count remaining land cells (value 1) = enclaves
        int enclaves = 0;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) enclaves++;
            }
        }
        return enclaves;
    }

    // DFS marks land reachable from (r,c) as safe (2)
    private void dfs(int[][] g, int r, int c) {
        if (r < 0 || c < 0 || r >= m || c >= n || g[r][c] != 1) return;
        g[r][c] = 2; // mark safe
        dfs(g, r + 1, c);
        dfs(g, r - 1, c);
        dfs(g, r, c + 1);
        dfs(g, r, c - 1);
    }
}
```

---

## Mapping of requirements → specific lines in code

* **Border-reachable land stays safe** → outer loops lines 10-17 call `dfs` on every border cell.
* **DFS expansion** → `dfs(...)` lines 24-29 explore 4-direction neighbours, identical in structure to `LC130`.
* **Enclave counting** → double loop lines 19-23 increments `enclaves` for each land cell still valued `1`.
* **Visited tracking** → in-place marking `1 → 2` inside DFS prevents revisiting and doubles as memoization.

---

## Complexity

* **Time:** `O(m · n)` — every cell visited at most once.
* **Space:** `O(m · n)` worst-case recursion stack (or `O(m+n)` with iterative stack).

---

## Notes / Pattern reuse

- Identical two-phase scheme: **mark border-connected**, then **process interior**.
- Sentinel value (`2`) serves like `'S'` in `LC130` — no extra arrays needed.
- Converting DFS to BFS queue is trivial if stack depth is a concern.

---
