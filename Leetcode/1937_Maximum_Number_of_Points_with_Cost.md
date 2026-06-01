# 1937. Maximum Number of Points with Cost

## Problem Description

[Maximum Number of Points with Cost](https://leetcode.com/problems/maximum-number-of-points-with-cost/)

You are given an `m x n` integer matrix `points`. To maximize points you pick **one cell in each row**. Picking cell `(r, c)` adds `points[r][c]` to your score. However, for adjacent rows `r` and `r + 1`, if you pick columns `c1` and `c2`, you **lose** `abs(c1 - c2)` points.

Return the **maximum** number of points achievable.

### Example 1

Input: `points = [[1,2,3],[1,5,1],[3,1,1]]`

Output: `9`

Explanation: Pick `(0,2)=3`, `(1,1)=5`, `(2,0)=3`. Penalty `|2-1| + |1-0| = 2`. Total `3+5+3-2 = 9`.

### Example 2

Input: `points = [[1,5],[2,3],[4,2]]`

Output: `11`

### Constraints

- `1 <= m, n <= 10^5`, `1 <= m * n <= 10^5`.
- `0 <= points[r][c] <= 10^5`.

## Intuition / Main Idea

Layered DP again, but the transition cost is `abs(columnDiff)`. A naive transition is `O(n)` per cell → `O(m·n²)` total, which is too slow because `n` alone can reach `10^5`. The fix: **split the absolute value** and precompute prefix/suffix maxima.

### Build the intuition step by step

1. `dp[i][j]` = best total for rows `0..i` ending at column `j`. Base: `dp[0][j] = points[0][j]`.
2. Naive transition: `dp[i][j] = points[i][j] + max_k(dp[i-1][k] - abs(k - j))`.
3. Split `abs(k - j)`:
   - **`k ≤ j`:** term = `(dp[i-1][k] + k) - j`. The `j` is constant for fixed `j`, so maximize `dp[i-1][k] + k` over `k ≤ j` — a **left-to-right prefix max**.
   - **`k ≥ j`:** term = `(dp[i-1][k] - k) + j`. Maximize `dp[i-1][k] - k` over `k ≥ j` — a **right-to-left suffix max**.
4. Then `dp[i][j] = points[i][j] + max(left[j] - j, right[j] + j)`, computed in `O(1)` per cell.

### Why this works

Absolute value is a max of two linear pieces. Separating the cases removes the `abs`, and within each case the `j`-dependent part factors out of the max — leaving a running maximum that any prefix/suffix sweep computes in linear time.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Score sum minus `abs` column penalties | `current[j] = points[i][j] + max(left[j]-j, right[j]+j)` |
| `k ≤ j` case (prefix max of `dp+k`) | `left[j] = max(left[j-1], prev[j] + j)` |
| `k ≥ j` case (suffix max of `dp-k`) | `right[j] = max(right[j+1], prev[j] - j)` |
| Large scores | return type `long` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Grid DP + prefix/suffix-max to collapse an abs-difference transition]
class Solution {
    public long maxPoints(int[][] points) {
        int m = points.length, n = points[0].length;

        long[] prev = new long[n];
        for (int j = 0; j < n; j++) {
            prev[j] = points[0][j];
        }

        for (int i = 1; i < m; i++) {
            long[] current = new long[n];
            long[] left = new long[n];
            long[] right = new long[n];

            left[0] = prev[0];                      // prev[0] + 0
            for (int j = 1; j < n; j++) {
                left[j] = Math.max(left[j - 1], prev[j] + j);
            }

            right[n - 1] = prev[n - 1] - (n - 1);
            for (int j = n - 2; j >= 0; j--) {
                right[j] = Math.max(right[j + 1], prev[j] - j);
            }

            for (int j = 0; j < n; j++) {
                current[j] = points[i][j] + Math.max(left[j] - j, right[j] + j);
            }
            prev = current;
        }

        long max = Long.MIN_VALUE;
        for (int j = 0; j < n; j++) {
            max = Math.max(max, prev[j]);
        }
        return max;
    }
}
```

### Why each part exists

- **`left` (prefix max of `prev[j] + j`)** — answers the `k ≤ j` case in `O(1)`.
- **`right` (suffix max of `prev[j] - j`)** — answers the `k ≥ j` case; must be built right-to-left in its own pass before `current` is filled.
- **`left[j] - j` and `right[j] + j`** — re-attach the `j` term that was factored out.
- **`long`** — scores can reach `~10^{10}`.
- **No `m==1`/`n==1` special case** — the final `max(prev)` naturally returns the single-row max; a single column has zero penalty.

## Dynamic Programming Notes

- **Subproblem:** `dp[i][j]` = best total for rows `0..i` ending at column `j`.
- **State transition (plain words):** "best ending here = my value plus the best previous row choice, discounted by how far I had to move."
- **Why bottom-up:** the prefix/suffix-max trick is most natural iteratively; a top-down memo would still need the same per-row sweeps to avoid `O(n²)`.
- **Array sizing:** `prev`/`current`/`left`/`right` are all length `n` (one per column); only the previous row matters, so `O(n)` space.

## Complexity Analysis

- **Time Complexity:** $O(m \cdot n)$ — three linear passes per row.
- **Space Complexity:** $O(n)$ — rolling arrays.

## Similar Problems

1. [LeetCode 2304. Minimum Path Cost in a Grid](https://leetcode.com/problems/minimum-path-cost-in-a-grid/) — same "any column in next row" structure (without the `abs` optimization opportunity).
2. [LeetCode 1014. Best Sightseeing Pair](https://leetcode.com/problems/best-sightseeing-pair/) — 1D cousin: split a two-index expression and track a running best.
3. [LeetCode 931. Minimum Falling Path Sum](https://leetcode.com/problems/minimum-falling-path-sum/) — layered grid DP with limited column moves.
