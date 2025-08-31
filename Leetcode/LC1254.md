### 1254. Number of Closed Islands
Problem: https://leetcode.com/problems/number-of-closed-islands/

---

Below is a compact mapping from **problem requirements → code** followed by a DFS Java solution that reuses the border-mark pattern from `LC1020` and `LC130`.

## Problem → State mapping (how rules translate to code)

**Problem rules (short):**
- Grid `m × n` with `0 = land`, `1 = water`.
- A **closed island** is a maximal 4-connected region of land (`0`s) that is **completely surrounded** by water on all four edges (top, bottom, left, right).  
  Any land cell that touches the border, or is connected to one that touches the border, is **not** closed.
- Return the **count** of such closed islands.

**Key idea (two-phase like previous problems):**
1. **Erase** every land cell reachable from the border — they cannot form closed islands.  
   Mark them as water (`0 → 1`).
2. **Count** the remaining land regions; each DFS that starts on a `0` now corresponds to one closed island.

**State definition (DFS):**
- `dfs(r,c)` → flood-fills from `(r,c)` converting land `0 → 1` (mutating grid). Acts as visited set.
- No extra memo/visited array needed.

**Base case:**
- If out of bounds or `grid[r][c] != 0` → return.

**Transition:**
- Recurse on four neighbours `(r±1,c)` and `(r,c±1)`.

---

## Full Java code (DFS border-mark then count)

```java
class Solution {
    private int m, n;

    public int closedIsland(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        m = grid.length;
        n = grid[0].length;

        // 1️⃣ Erase all land reachable from the border (cannot be closed)
        for (int r = 0; r < m; r++) {
            dfs(grid, r, 0);
            dfs(grid, r, n - 1);
        }
        for (int c = 0; c < n; c++) {
            dfs(grid, 0, c);
            dfs(grid, m - 1, c);
        }

        // 2️⃣ Count closed islands in the remaining grid
        int closed = 0;
        for (int r = 1; r < m - 1; r++) {
            for (int c = 1; c < n - 1; c++) {
                if (grid[r][c] == 0) {
                    closed++;
                    dfs(grid, r, c); // erase whole island
                }
            }
        }
        return closed;
    }

    // DFS converts connected land 0→1
    private void dfs(int[][] g, int r, int c) {
        if (r < 0 || c < 0 || r >= m || c >= n || g[r][c] != 0) return;
        g[r][c] = 1; // mark as water/visited
        dfs(g, r + 1, c);
        dfs(g, r - 1, c);
        dfs(g, r, c + 1);
        dfs(g, r, c - 1);
    }
}
```

---

## Mapping of requirements → specific lines in code

* **Border land is not closed** → outer loops lines 10-15 call `dfs` on all border cells to erase them.
* **DFS flood-fill & visited tracking** → `dfs` method lines 25-30 changes `0 → 1` to avoid revisits (similar to marking `2` in `LC1020`).
* **Counting each remaining land region** → nested loops lines 17-23; every time we encounter a `0`, we increment `closed` and erase that island so it’s not double-counted.
* **Return number of closed islands** → line 24.

---

## Complexity

* **Time:** `O(m · n)` — each cell visited at most once across all DFS calls.
* **Space:** Recursion stack `O(m · n)` worst-case (all land). Convert to iterative stack/queue to cap at `O(min(m,n))` if needed.

---

## Notes / Pattern reuse

- Exact same **mark-then-process** template as `LC1020` (enclaves) and `LC130` (surrounded regions).
- Sentinel value (`1`) doubles as visited flag, so no extra memory.
- Works for non-rectangular cases (`m ≠ n`) since indices are handled correctly.

---
