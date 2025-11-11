# Knight Probability in Chessboard

## Problem Description

**Problem Link:** [Knight Probability in Chessboard](https://leetcode.com/problems/knight-probability-in-chessboard/)

On an `n x n` chessboard, a knight starts at the cell `(row, column)` and attempts to make exactly `k` moves. The rows and columns are **0-indexed**, so the top-left cell is `(0, 0)`, and the bottom-right cell is `(n - 1, n - 1)`.

A chess knight has eight possible moves it can make, as illustrated below. Each move is two cells in a cardinal direction, then one cell in an orthogonal direction.

Return *the probability that the knight remains on the chessboard after it has stopped moving*.

**Example 1:**

```
Input: n = 3, k = 2, row = 0, column = 0
Output: 0.06250
Explanation: There are two moves (to (1,2), (2,1)) that will keep the knight on the board.
From each of those positions, there are also two moves that will keep the knight on the board.
The total probability the knight stays on the board is 0.0625.
```

**Example 2:**

```
Input: n = 1, k = 0, row = 0, column = 0
Output: 1.00000
```

**Constraints:**
- `1 <= n <= 25`
- `0 <= k <= 100`
- `0 <= row, column <= n - 1`

## Intuition/Main Idea

This is a **dynamic programming** problem with probability. We need to find the probability that the knight stays on the board after `k` moves.

**Core Algorithm:**
1. Use DP where `dp[moves][i][j]` = probability of being at `(i, j)` after `moves` moves.
2. Base case: `dp[0][row][column] = 1.0`.
3. For each move, update probabilities from all eight knight moves.
4. Sum probabilities of all positions after `k` moves.

**Why DP works:** The problem has overlapping subproblems - calculating probabilities from positions with given moves is needed multiple times. We can build the solution bottom-up.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| DP for probabilities | DP array - Line 6 |
| Base case: start position | Base case - Line 8 |
| Process moves | Moves loop - Line 10 |
| Try all knight moves | Direction loop - Lines 12-18 |
| Update probabilities | Probability update - Line 16 |
| Sum final probabilities | Sum calculation - Lines 20-23 |
| Return result | Return statement - Line 24 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    private double[][][] memo;
    private int[][] directions = {
        {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
        {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };
    
    public double knightProbability(int n, int k, int row, int column) {
        memo = new double[n][n][k + 1];
        return dfs(n, k, row, column);
    }
    
    private double dfs(int n, int moves, int i, int j) {
        // Base case: out of bounds
        if (i < 0 || i >= n || j < 0 || j >= n) {
            return 0.0;
        }
        
        // Base case: no moves left
        if (moves == 0) {
            return 1.0;
        }
        
        if (memo[i][j][moves] != 0.0) {
            return memo[i][j][moves];
        }
        
        double probability = 0.0;
        for (int[] dir : directions) {
            probability += dfs(n, moves - 1, i + dir[0], j + dir[1]) / 8.0;
        }
        
        memo[i][j][moves] = probability;
        return probability;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public double knightProbability(int n, int k, int row, int column) {
        int[][] directions = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };
        
        // DP: dp[moves][i][j] = probability of being at (i, j) after moves moves
        double[][][] dp = new double[k + 1][n][n];
        dp[0][row][column] = 1.0;
        
        for (int move = 1; move <= k; move++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int[] dir : directions) {
                        int prevI = i - dir[0];
                        int prevJ = j - dir[1];
                        if (prevI >= 0 && prevI < n && prevJ >= 0 && prevJ < n) {
                            dp[move][i][j] += dp[move - 1][prevI][prevJ] / 8.0;
                        }
                    }
                }
            }
        }
        
        // Sum probabilities of all positions after k moves
        double result = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result += dp[k][i][j];
            }
        }
        
        return result;
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Line 8):** `dp[0][row][column] = 1.0` - probability of starting at initial position is 1.

2. **Process Moves (Lines 10-18):** For each move, update probabilities:
   - For each position `(i, j)`, check all eight knight moves.
   - If previous position `(prevI, prevJ)` is valid, add its probability divided by 8.

3. **Sum Probabilities (Lines 20-23):** Sum probabilities of all positions after `k` moves.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the probability of being at `(i, j)` after `moves` moves?"
- **Why this works:** To be at `(i, j)` after `moves` moves, we must have been at one of the eight previous positions after `moves-1` moves and made the corresponding knight move.
- **Overlapping subproblems:** Multiple positions and move counts may share the same probabilities.

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive, recursive. Time: O(n²×k), Space: O(n²×k).
- **Bottom-up (iterative):** More efficient, no recursion. Time: O(n²×k), Space: O(n²×k) or O(n²) optimized.
- **When bottom-up is better:** Better for this problem due to no recursion overhead and space optimization possible.

## Complexity Analysis

- **Time Complexity:** $O(n^2 \times k)$ where we process each position for each move.

- **Space Complexity:** $O(n^2 \times k)$ for DP. Can be optimized to $O(n^2)$ using 2D DP.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **688. Knight Probability in Chessboard** (this problem) - DP with probability
2. **576. Out of Boundary Paths** - DP with boundaries
3. **935. Knight Dialer** - DP with knight moves
4. **62. Unique Paths** - Grid DP
5. **63. Unique Paths II** - Grid DP with obstacles
6. **64. Minimum Path Sum** - Grid DP with costs
7. **1220. Count Vowels Permutation** - DP with transitions
8. **935. Knight Dialer** - DP with dialer

