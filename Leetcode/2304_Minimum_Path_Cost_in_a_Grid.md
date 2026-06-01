# 2304. Minimum Path Cost in a Grid

## Problem Description

[Minimum Path Cost in a Grid](https://leetcode.com/problems/minimum-path-cost-in-a-grid/)

You are given a 0-indexed `m x n` integer matrix `grid` of **distinct** integers from `0` to `m * n - 1`. You can move from a cell `(i, j)` to **any** cell `(i + 1, k)` in the next row. The cost of moving from value `v = grid[i][j]` into column `k` of the next row is `moveCost[v][k]` (a `(m*n) x n` array).

The cost of a path is the sum of the values of the cells visited **plus** the move costs. Return the **minimum** cost of a path that starts at **any** cell in the first row and ends at **any** cell in the last row.

### Example 1

Input: `grid = [[5,3],[4,0],[2,1]]`, `moveCost = [[9,8],[1,5],[10,12],[18,6],[2,4],[14,3]]`

Output: `17`

Explanation: Best path `5 -> 0 -> 1`: cells `5 + 0 + 1 = 6`, moves `moveCost[5][1] + moveCost[0][0] = 3 + ... = 11`; total `17`.

### Example 2

Input: `grid = [[5,1,2],[4,0,3]]`, `moveCost = ...`

Output: `6`

### Constraints

- `2 <= m, n <= 50`, `grid` holds distinct values `0..m*n-1`.
- `moveCost.length == m * n`, `moveCost[i].length == n`, `1 <= moveCost[i][j] <= 100`.

## Intuition / Main Idea

This is a layered DP: choose one cell per row, paying a value cost at each cell and a move cost between rows. Unlike "minimum path sum", you can jump to **any** column in the next row, and the move cost is indexed by the **cell's value**, not its position.

### Build the intuition step by step

1. **`moveCost` indexing is the trap.** From a cell holding value `v`, moving to column `k` costs `moveCost[v][k]`. Since the grid holds distinct values `0..m*n-1`, each value selects a unique row of `moveCost`.
2. Define `dp[i][j]` = min cost of any path that starts in row 0 and ends exactly at `(i, j)`, including `grid[i][j]`.
3. To end at `(i, j)`, you came from some `(i-1, k)`. The cost contributed is `dp[i-1][k] + moveCost[grid[i-1][k]][j]`. Take the minimum over all `k`, then add `grid[i][j]`.
4. Base case: `dp[0][j] = grid[0][j]` (no move into the first row).
5. Answer: the minimum over the **entire last row**, since the path may end anywhere.

### Why this works

Each target cell considers every possible predecessor column and keeps the cheapest. Because the move cost depends on `k`, it must live **inside** the `min` together with `dp[i-1][k]` — minimizing `dp[i-1][k]` alone and adding an unrelated move cost is wrong.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Move to any column in next row | inner loop over `k` from `0..n-1` |
| `moveCost` indexed by cell value | `moveCost[grid[i-1][k]][j]` |
| Min path ending at `(i,j)` | `current[j] = grid[i][j] + min_k(prev[k] + moveCost[grid[i-1][k]][j])` |
| Path may start/end anywhere in first/last row | base `prev[j] = grid[0][j]`; answer = `min(prev)` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Grid DP with value-indexed transition cost; O(n) rolling array]
class Solution {
    public int minPathCost(int[][] grid, int[][] moveCost) {
        int m = grid.length, n = grid[0].length;

        int[] prev = new int[n];
        for (int j = 0; j < n; j++) {
            prev[j] = grid[0][j];               // base: first row, no move cost
        }

        for (int i = 1; i < m; i++) {
            int[] current = new int[n];
            for (int j = 0; j < n; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 0; k < n; k++) {
                    // move cost depends on k -> must be inside the min
                    min = Math.min(min, prev[k] + moveCost[grid[i - 1][k]][j]);
                }
                current[j] = grid[i][j] + min;
            }
            prev = current;
        }

        int answer = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            answer = Math.min(answer, prev[j]); // path can end at any column
        }
        return answer;
    }
}
```

### Why each part exists

- **`prev`/`current` rolling arrays** — only the previous row is needed, so `O(n)` space suffices. The `grid` **cannot** be reused in place, because the transition needs the original `grid[i-1][k]` value for the `moveCost` lookup.
- **`min` over `k`** with the move cost inside — captures the cheapest predecessor *including* its transition cost.
- **Final `min(prev)`** — the optimal path may finish at any last-row column.

## Dynamic Programming Notes

- **Subproblem:** `dp[i][j]` = min cost from row 0 to cell `(i, j)`.
- **State transition (plain words):** "best total to be standing on `(i,j)` = its own value plus the cheapest way to have been on some cell in the row above and step here."
- **Top-down variant:** define `solve(i, j)` = min cost from `(i,j)` down to any last-row cell; base `i == m-1` -> `grid[i][j]` (whole last row, not just the corner); recurrence `grid[i][j] + min_k(moveCost[grid[i][j]][k] + solve(i+1, k))`; answer = `min_j solve(0, j)`. Memo size `m x n` because there is one subproblem per cell.
- **Bottom-up wins** here on constant factors and avoids recursion-stack overhead; both are `O(m·n²)`.

## Complexity Analysis

- **Time Complexity:** $O(m \cdot n^2)$ — each of `m·n` cells scans `n` predecessor columns.
- **Space Complexity:** $O(n)$ with the rolling array (`O(m·n)` for a full 2D table).

## Similar Problems

1. [LeetCode 64. Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/) — grid DP, but moves restricted to right/down.
2. [LeetCode 1937. Maximum Number of Points with Cost](https://leetcode.com/problems/maximum-number-of-points-with-cost/) — same "any column in next row" shape; needs prefix/suffix-max to beat `O(n²)`.
3. [LeetCode 931. Minimum Falling Path Sum](https://leetcode.com/problems/minimum-falling-path-sum/) — adjacent-column moves only.
