### 200. Number of Islands
Problem: https://leetcode.com/problems/number-of-islands/

{{ ... }}

---

Below is a compact mapping from **problem requirements → code** followed by a DFS Java solution that reuses the flood-fill pattern from `LC1254`, `LC1020`, etc.

## Problem → State mapping (how rules translate to code)

**Problem rules (short):**
- Grid `m × n` with `'1' = land`, `'0' = water`.
- An **island** is a maximal 4-directionally connected group of `1`s.
- Return the **total number** of islands.

**Key idea:**
- Traverse every cell; whenever an unvisited land cell (`1`) is found, **run DFS to sink** (flip to `0`) all connected land.  
  Each DFS call therefore accounts for exactly **one** island.

**State definition (DFS):**
- `dfs(r,c)` → recursively flips connected `1`s to `0`. The grid itself acts as the visited set.

**Base case:**
- Out of bounds or `grid[r][c] != '1'` → stop.

**Transition:**
- Recurse on four neighbours `(r±1,c)` and `(r,c±1)`.

---

## Full Java code (DFS counting)

```java
class Solution {
    private int m, n;

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        m = grid.length;
        n = grid[0].length;

        int islands = 0;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == '1') {
                    islands++;
                    dfs(grid, r, c); // sink this island
                }
            }
        }
        return islands;
    }

    // DFS sinks connected land '1' → '0'
    private void dfs(char[][] g, int r, int c) {
        if (r < 0 || c < 0 || r >= m || c >= n || g[r][c] != '1') return;
        g[r][c] = '0';
        dfs(g, r + 1, c);
        dfs(g, r - 1, c);
        dfs(g, r, c + 1);
        dfs(g, r, c - 1);
    }
}
```

---

## Mapping of requirements → specific lines in code

* **Identify each island once** → nested loops lines 10-16 check every cell; on seeing `'1'` we increment `islands` and call `dfs` to erase that island.
* **Visited tracking** → `dfs` sets cell to `'0'` (line 20) ensuring no land is double-counted.
* **4-direction connectivity** → recursive calls lines 21-24.
* **Return total** → line 14 returns after full traversal.

---

## Complexity

* **Time:** `O(m · n)` — each cell visited at most once.
* **Space:** Recursion stack `O(m · n)` worst-case (all land). Use iterative stack/queue to limit if necessary.

---

## How is this different from `LC1254` (Closed Islands)?

| Aspect | LC200 – Number of Islands | LC1254 – Closed Islands |
|--------|---------------------------|-------------------------|
| **What is counted** | *Every* connected land region | Only land regions **not touching border** |
| **Pre-processing** | None; directly count | First erase border-reachable land, then count |
| **Grid values** | `'1' = land`, `'0' = water` | `0 = land`, `1 = water` (opposite convention) |
| **DFS sink value** | Flip `'1' → '0'` | Flip `0 → 1` |
| **Loops range for counting** | Whole grid | Interior cells only (borders already handled) |

Despite these differences, the core **flood-fill** template is identical: pick a start cell, DFS/BFS the 4-neighbourhood, and mark visited cells in place.

---
