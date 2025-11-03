### 130. Surrounded Regions
Problem: https://leetcode.com/problems/surrounded-regions/

---

Below is a compact mapping from **problem requirements → code** followed by a DFS Java solution that keeps the same explanatory layout used in `LC871`, `LC1277`, etc.

## Problem → State mapping (how rules translate to code)

**Problem rules (short):**
- Board is `m × n` grid of `'X'` and `'O'`.
- Any `'O'` **on a border**, or connected (4-dir) to a border `'O'`, must **stay `'O'`**.
- All other `'O'` cells are **captured → turned to `'X'`**.

**Key idea:**
- Instead of searching for surrounded regions directly, mark all `'O'` **reachable from the border** as *safe*.
- After marking, flip every other `'O'` to `'X'`.

**State definition (DFS):**
- `dfs(r,c)` → marks cell `(r,c)` and all connected `'O'`s as safe (changes them to `'S'`).
- No memo table required; we mutate the board and rely on in-place marking to avoid repeats (pattern similar to visited-check in earlier problems).

**Base cases:**
- If out of bounds or cell not `'O'` → return.

**Transition:**
- Recurse on four neighbours `(r±1,c)` and `(r,c±1)`.

---

## Full Java code (DFS marking)

```java
class Solution {
    private int m, n;

    public void solve(char[][] board) {
        if (board == null || board.length == 0) return;
        m = board.length;
        n = board[0].length;

        // 1️⃣ Mark border-connected regions as safe ('S')
        for (int r = 0; r < m; r++) {
            dfs(board, r, 0);
            dfs(board, r, n - 1);
        }
        for (int c = 0; c < n; c++) {
            dfs(board, 0, c);
            dfs(board, m - 1, c);
        }

        // 2️⃣ Flip the cells
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (board[r][c] == 'O') board[r][c] = 'X'; // captured
                else if (board[r][c] == 'S') board[r][c] = 'O'; // restore
            }
        }
    }

    // DFS marks reachable 'O' cells from (r,c) as 'S'
    private void dfs(char[][] b, int r, int c) {
        if (r < 0 || c < 0 || r >= m || c >= n || b[r][c] != 'O') return;
        b[r][c] = 'S';
        dfs(b, r + 1, c);
        dfs(b, r - 1, c);
        dfs(b, r, c + 1);
        dfs(b, r, c - 1);
    }
}
```

---

## Mapping of requirements → specific lines in code

* **Border cells must stay `'O'`** → outer loops lines 9-17 call `dfs` on every border cell.
* **DFS expansion** → `dfs(...)` lines 23-29 explore 4-dir neighbours, mirroring grid recursion in earlier problems.
* **Capture rule** → inner double loop lines 18-22 flips leftover `'O'` to `'X'`.
* **Restoration** → same loop turns temporary `'S'` back to `'O'`.
* **Visited tracking** → replacing `'O'` with `'S'` inside `dfs` acts like a memo to avoid revisits (no extra memory).

---

## Complexity

* **Time:** `O(m · n)` — each cell visited at most once.
* **Space:** `O(m · n)` worst-case recursion stack (or `O(m+n)` if tail-recursion optimized; iterative stack/queue can limit to `O(min(m,n))`).

---

## Notes / Pattern reuse

- Still a two-phase approach like earlier greedy solution: **mark** then **finalize**.
- In-place marking (`'S'`) is analogous to using a sentinel / memo array.
- If stack depth is a concern, convert `dfs` to iterative BFS with a queue — logic unchanged.

---
