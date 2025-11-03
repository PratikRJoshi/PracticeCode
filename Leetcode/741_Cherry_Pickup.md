### 741. Cherry Pickup
Problem: https://leetcode.com/problems/cherry-pickup/

---

Below is a compact mapping from **problem requirements → code** followed by a top-down Java solution that stays *as close as possible* to the prior DFS+memo pattern you used for Word Break.


## Problem → State mapping (how rules translate to code)

**Problem rules (short):**
- Grid `n x n`. Cells are `0` (empty), `1` (cherry), `-1` (thorn / blocked).
- Start at `(0,0)` → go to `(n-1,n-1)` moving only **right or down**.
- Then return from `(n-1,n-1)` → `(0,0)` moving only **left or up**.
- A cherry can be picked once; when picked its cell becomes `0`.
- If no valid path exists, result = `0`.

**Key idea (common trick):**
- Instead of modeling forward+back separately, model **two people walking simultaneously** from `(0,0)` to `(n-1,n-1)` (both only move right/down).  
  - Equivalent because reverse trip is just another forward trip in time-synced fashion.
- Let person A be at `(r1,c1)`, person B at `(r2,c2)` after `t` steps where `t = r1 + c1 = r2 + c2`. So `c2 = t - r2 = r1 + c1 - r2`.
- State can be represented as `(r1, c1, r2)` — `c2` is computed from the others.

**State meaning in code:**
- `dfs(r1, c1, r2)` returns maximum cherries collectable from these positions onward (both moving to end), or a sentinel for invalid.
- Invalid / blocked states return a very negative value (so they won't be chosen).
- When both persons stand on the same cell, only count that cherry once.

**Transitions (moves):**
- Each person can move either `down` or `right`. That yields 4 combinations of next states:
  - A down, B down: `(r1+1,c1) , (r2+1,c2)`
  - A down, B right: `(r1+1,c1) , (r2,c2+1)` — note we pass `r2` but compute `c2` each call
  - A right, B down: `(r1,c1+1) , (r2+1,c2)`
  - A right, B right: `(r1,c1+1) , (r2,c2+1)`

**Base cases:**
- If any index out-of-bound or cell value `-1` → invalid.
- If `r1 == n-1 && c1 == n-1` (both reached end at same time) → return grid value at end (either 0 or 1).

**Memoization:**
- Use `Integer[][][] memo = new Integer[n][n][n]` with `null` => uncomputed.
- Key indexing: `memo[r1][c1][r2]`.

---

## Full Java top-down code (keeps the DFS+memo pattern)

```java
class Solution {
    // sentinel to mark impossible paths (use a large negative number)
    private static final int NEG_INF = Integer.MIN_VALUE / 4;

    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        // memo[r1][c1][r2] -> best cherries from state (r1,c1) and (r2,c2) where c2 = r1+c1-r2
        Integer[][][] memo = new Integer[n][n][n];
        int ans = dfs(0, 0, 0, grid, memo);
        return Math.max(0, ans); // if ans negative => no valid path => return 0
    }

    private int dfs(int r1, int c1, int r2, int[][] grid, Integer[][][] memo) {
        int n = grid.length;
        int c2 = r1 + c1 - r2; // derived column for person B

        // bounds / thorn check
        if (r1 < 0 || c1 < 0 || r2 < 0 || c2 < 0 ||
            r1 >= n || c1 >= n || r2 >= n || c2 >= n) {
            return NEG_INF;
        }
        if (grid[r1][c1] == -1 || grid[r2][c2] == -1) {
            return NEG_INF;
        }

        // If both reached bottom-right
        if (r1 == n - 1 && c1 == n - 1) {
            return grid[r1][c1]; // both are on same last cell; return its cherry (0 or 1)
        }

        if (memo[r1][c1][r2] != null) {
            return memo[r1][c1][r2];
        }

        // cherries picked at current step (avoid double count when same cell)
        int cherries = 0;
        if (r1 == r2 && c1 == c2) {
            cherries = grid[r1][c1];
        } else {
            cherries = grid[r1][c1] + grid[r2][c2];
        }

        // explore all 4 combinations of moves
        int bestNext = NEG_INF;
        // A down, B down
        bestNext = Math.max(bestNext, dfs(r1 + 1, c1, r2 + 1, grid, memo));
        // A down, B right
        bestNext = Math.max(bestNext, dfs(r1 + 1, c1, r2, grid, memo));
        // A right, B down
        bestNext = Math.max(bestNext, dfs(r1, c1 + 1, r2 + 1, grid, memo));
        // A right, B right
        bestNext = Math.max(bestNext, dfs(r1, c1 + 1, r2, grid, memo));

        int result = NEG_INF;
        if (bestNext != NEG_INF) {
            result = cherries + bestNext;
        }

        memo[r1][c1][r2] = result;
        return result;
    }
}
```

---

## Mapping of problem requirements → specific lines in code

- **Grid bounds & blocked cells** → early checks:
  ```java
  if (r1 < 0 || ... || c2 >= n) return NEG_INF;
  if (grid[r1][c1] == -1 || grid[r2][c2] == -1) return NEG_INF;
  ```
  These lines implement the rule *"-1 means the cell is blocked"*.

- **Simultaneous walk & c2 derivation** → `int c2 = r1 + c1 - r2;`  
  That enforces the time-synchronization `t = r1 + c1 = r2 + c2`.

- **Counting cherries and avoiding double count**:
  ```java
  if (r1 == r2 && c1 == c2) cherries = grid[r1][c1];
  else cherries = grid[r1][c1] + grid[r2][c2];
  ```
  Implements *"when both pass same cell, pick cherry once"*.

- **Transitions (right/down choices for both persons)** → the four `dfs(...)` calls:
  ```java
  bestNext = Math.max(bestNext, dfs(r1 + 1, c1, r2 + 1, grid, memo)); // down, down
  bestNext = Math.max(bestNext, dfs(r1 + 1, c1, r2, grid, memo));     // down, right
  bestNext = Math.max(bestNext, dfs(r1, c1 + 1, r2 + 1, grid, memo)); // right, down
  bestNext = Math.max(bestNext, dfs(r1, c1 + 1, r2, grid, memo));     // right, right
  ```

- **End condition and returning final answer**:
  ```java
  if (r1 == n - 1 && c1 == n - 1) return grid[r1][c1];
  ...
  int ans = dfs(0,0,0,grid,memo);
  return Math.max(0, ans);
  ```
  These implement *"reach (n-1,n-1)"* and *"if no valid path return 0"*.

- **Memoization** → `memo[r1][c1][r2]` stores computed results, preventing exponential recomputation.

---

## Complexity

- **Time Complexity:** `O(n^3)`  
  - There are `O(n^3)` distinct states `(r1, c1, r2)` because `r1, c1, r2` each range over `n` values (but actually `c1` is bound by `r1+c1 <= 2n`, yet complexity is cubic).  
  - Each state tries 4 next moves (constant), so overall `O(n^3)`.

- **Space Complexity:** `O(n^3)` for memoization array `memo[n][n][n]`, plus recursion stack `O(n)` depth.

---

## Notes / Practical tips

- We used `Integer[][][] memo` and a `NEG_INF` sentinel via `Integer` storage pattern: `null` => uncomputed, stored value may be negative sentinel for impossible paths. This mirrors the null-check style used in earlier top-down examples.
- Using `NEG_INF = Integer.MIN_VALUE / 4` avoids overflow when adding cherries.
- The final `Math.max(0, ans)` enforces the rule that if there's no valid forward+return path, answer should be `0`.

- BFS is theoretically possible for Cherry Pickup, but impractical because:
  - State space is huge: (r1, c1, r2, c2, grid state) → O(n^4 * 2^(n^2))
  - Memory usage explodes due to all frontier states
  - Many duplicate states require heavy pruning

- DFS + memoization is preferred because:
    - Memoization efficiently prunes repeated states using (r1, c1, r2)
  - No need to track entire grid, only positions and cherries collected
  - Time complexity O(n^3) vs exponential for naive BFS

---