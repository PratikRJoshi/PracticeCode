### 1277. Count Square Submatrices with All Ones
Problem: https://leetcode.com/problems/count-square-submatrices-with-all-ones/

---

Below is a compact mapping from **problem requirements → code** followed by a top-down DFS + memo Java solution that mirrors the same pattern you used in `LC741`, `LC123`, and `LC122`.

## Problem → State mapping (how rules translate to code)

**Problem rules (short):**
- Matrix `m × n` of `0`/`1`.
- A **square sub-matrix of all ones** is any contiguous square whose cells are all `1`.
- Count **all** such squares (sizes `1×1`, `2×2`, …).

**Key idea (top-down trick):**
- For each cell `(r,c)` that contains a `1`, the **largest square** with `(r,c)` as its **top-left corner** depends only on its three neighbors
  - `down   = dfs(r+1, c)`
  - `right  = dfs(r,   c+1)`
  - `diag   = dfs(r+1, c+1)`
- The size is `1 + min(down, right, diag)` (classic dynamic-programming relation for squares of ones).
- Let `dfs(r,c)` return that largest size.  Then the answer is the **sum** of `dfs(r,c)` over all cells.

**State definition in code:**
- `dfs(r, c)` → largest square length whose top-left is `(r,c)`.
- Memo table `Integer[][] memo = new Integer[m][n]` as in prior patterns.  `null` means uncomputed.

**Base cases:**
- If `(r,c)` is **out of bounds** → return `0` (no square).
- If `grid[r][c] == 0` → return `0`.
- Cells on the **last row or last column** cannot expand further, so `dfs(r,c) = grid[r][c]` (either `0` or `1`).

**Transition (same spirit as 741):**
```text
size = 1 + min( dfs(r+1,c), dfs(r,c+1), dfs(r+1,c+1) )
```
(works only if current cell is `1`; otherwise `0`).

---

## Full Java code (DFS + memo, cherry-pickup style)

```java
class Solution {
    public int countSquares(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        Integer[][] memo = new Integer[m][n];

        int total = 0;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                total += dfs(r, c, matrix, memo);  // accumulate sizes
            }
        }
        return total;
    }

    private int dfs(int r, int c, int[][] A, Integer[][] memo) {
        int m = A.length, n = A[0].length;
        // out of bounds ⇒ 0
        if (r >= m || c >= n) return 0;

        if (memo[r][c] != null) return memo[r][c];

        if (A[r][c] == 0) {
            memo[r][c] = 0;
            return 0;
        }

        // If on last row/col, largest square is 1 (itself)
        if (r == m - 1 || c == n - 1) {
            memo[r][c] = 1;
            return 1;
        }

        int down  = dfs(r + 1, c,     A, memo);
        int right = dfs(r,     c + 1, A, memo);
        int diag  = dfs(r + 1, c + 1, A, memo);

        int size = 1 + Math.min(down, Math.min(right, diag));
        memo[r][c] = size;
        return size;
    }
}
```

---

## Mapping of requirements → specific lines in code

* **Cell out of bounds / zeros** → early returns in `dfs` lines 9-16.
* **Recurrence `size = 1 + min(...)`** → lines 23-27 compute neighbors then line 29 sets final size.
* **Memoization** → `memo[r][c]` check & store (lines 6, 10, 28-30) exactly like `memo[r1][c1][r2]` in `LC741`.
* **Aggregating answer** → outer double loop in `countSquares` lines 3-8 parallels the wrapper in `LC741.cherryPickup` that calls `dfs` on the start state.

---

## Complexity

* **Time:** Each state `(r,c)` visited once, work `O(1)` ⇒ `O(m·n)`.
* **Space:** Memo table `O(m·n)` plus recursion depth `O(m+n)`.

---

## Notes / Pattern reuse

- Pattern identical to earlier problems:
  * **State** chosen so next states depend on *constant-size* neighborhood.
  * **DFS + memo** avoids recomputation and keeps code short.
- You could also write iterative DP, but this top-down version deliberately mirrors the style of `LC741` / Stock problems for practice.
- No negative sentinel needed here because squares are always non-negative; `0` suffices for invalid states.

---
