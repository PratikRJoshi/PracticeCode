# Out of Boundary Paths

## Problem Description

**Problem Link:** [Out of Boundary Paths](https://leetcode.com/problems/out-of-boundary-paths/)

There is an `m x n` grid with a ball. The ball is initially at the position `[startRow, startColumn]`. You are allowed to move the ball to one of the four adjacent cells in the grid (possibly out of the grid crossing the grid boundary). You can apply **at most** `maxMove` moves.

Given the five integers `m`, `n`, `maxMove`, `startRow`, `startColumn`, return *the number of paths to move the ball out of the grid boundary*. Since the answer can be very large, return it **modulo** $10^9 + 7$.

**Example 1:**

```
Input: m = 2, n = 2, maxMove = 2, startRow = 0, startColumn = 0
Output: 6
```

**Example 2:**

```
Input: m = 1, n = 1, maxMove = 1, startRow = 0, startColumn = 0
Output: 4
```

**Constraints:**
- `1 <= m, n <= 50`
- `0 <= maxMove <= 50`
- `0 <= startRow < m`
- `0 <= startColumn < n`

## Intuition/Main Idea

This is a **dynamic programming** problem with memoization. We need to count paths that go out of bounds within `maxMove` moves.

**Core Algorithm:**
1. Use DP where `dp[moves][i][j]` = number of paths to go out of bounds from `(i, j)` with `moves` moves remaining.
2. Base case: if out of bounds, return 1.
3. Base case: if no moves left, return 0.
4. Recurrence: sum paths from all four directions.

**Why DP works:** The problem has overlapping subproblems - counting paths from a position with given moves is needed multiple times. We can memoize results to avoid recomputation.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Memoization | Memo array - Line 6 |
| Base case: out of bounds | Boundary check - Lines 10-12 |
| Base case: no moves | Moves check - Lines 14-16 |
| Try all directions | Direction loop - Lines 18-21 |
| Sum paths | Path summation - Line 20 |
| Return result | Return statement - Line 23 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    private static final int MOD = 1000000007;
    private int[][][] memo;
    
    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        memo = new int[m][n][maxMove + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k <= maxMove; k++) {
                    memo[i][j][k] = -1;
                }
            }
        }
        return dfs(m, n, maxMove, startRow, startColumn);
    }
    
    private int dfs(int m, int n, int moves, int i, int j) {
        // Base case: out of bounds
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return 1;
        }
        
        // Base case: no moves left
        if (moves == 0) {
            return 0;
        }
        
        if (memo[i][j][moves] != -1) {
            return memo[i][j][moves];
        }
        
        // Try all four directions
        long paths = 0;
        paths = (paths + dfs(m, n, moves - 1, i - 1, j)) % MOD;
        paths = (paths + dfs(m, n, moves - 1, i + 1, j)) % MOD;
        paths = (paths + dfs(m, n, moves - 1, i, j - 1)) % MOD;
        paths = (paths + dfs(m, n, moves - 1, i, j + 1)) % MOD;
        
        memo[i][j][moves] = (int) paths;
        return (int) paths;
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Cases:**
   - **Out of Bounds (Lines 10-12):** If we're out of bounds, we've found a valid path, return 1.
   - **No Moves (Lines 14-16):** If no moves left and still in bounds, no valid path, return 0.

2. **Try Directions (Lines 18-21):** Try moving in all four directions (up, down, left, right) and sum the paths.

**Intuition behind generating subproblems:**
- **Subproblem:** "How many paths to go out of bounds from `(i, j)` with `moves` moves remaining?"
- **Why this works:** To go out of bounds from `(i, j)` with `moves` moves, we can move in any direction and then go out of bounds from the new position with `moves-1` moves.
- **Overlapping subproblems:** Multiple positions and move counts may share the same optimal subproblems.

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive, recursive. Time: O(m×n×maxMove), Space: O(m×n×maxMove).
- **Bottom-up (iterative):** More efficient, no recursion. Time: O(m×n×maxMove), Space: O(m×n×maxMove) or O(m×n) optimized.
- **When bottom-up is better:** Better for this problem due to no recursion overhead and space optimization possible.

## Complexity Analysis

- **Time Complexity:** $O(m \times n \times maxMove)$ where we process each state once.

- **Space Complexity:** $O(m \times n \times maxMove)$ for memoization. Can be optimized to $O(m \times n)$ using 2D DP.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **576. Out of Boundary Paths** (this problem) - DP with boundaries
2. **688. Knight Probability in Chessboard** - DP with probability
3. **62. Unique Paths** - Grid DP
4. **63. Unique Paths II** - Grid DP with obstacles
5. **64. Minimum Path Sum** - Grid DP with costs
6. **935. Knight Dialer** - DP with moves
7. **1220. Count Vowels Permutation** - DP with transitions
8. **935. Knight Dialer** - DP with knight moves

