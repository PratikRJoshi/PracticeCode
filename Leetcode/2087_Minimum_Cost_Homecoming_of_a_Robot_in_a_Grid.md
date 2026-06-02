# 2087. Minimum Cost Homecoming of a Robot in a Grid

## Problem Description

[Minimum Cost Homecoming of a Robot in a Grid](https://leetcode.com/problems/minimum-cost-homecoming-of-a-robot-in-a-grid/)

There is an `m x n` grid. A robot starts at `startPos = [startRow, startCol]` and wants to reach `homePos = [homeRow, homeCol]`. The robot moves one cell at a time (up/down/left/right). You are given `rowCosts` (length `m`) and `colCosts` (length `n`):

- Moving **into** any cell in row `r` costs `rowCosts[r]`.
- Moving **into** any cell in column `c` costs `colCosts[c]`.

Return the **minimum total cost** for the robot to return home.

### Example 1

Input: `startPos = [1,0]`, `homePos = [2,3]`, `rowCosts = [5,4,3]`, `colCosts = [8,2,6,7]`

Output: `18`

Explanation: Move down into row 2 (`+3`), then right into columns 1, 2, 3 (`2 + 6 + 7 = 15`). Total `3 + 15 = 18`.

### Example 2

Input: `startPos = [0,0]`, `homePos = [0,0]`, `rowCosts = [5]`, `colCosts = [26]`

Output: `0`

### Constraints

- `1 <= m, n <= 10^5`, `0 <= rowCosts[r], colCosts[c] <= 10^4`.
- `rowCosts.length == m`, `colCosts.length == n`.

## Intuition / Main Idea

This *looks* like a top-down grid DP (recurse over robot positions, minimize cost), but it is actually **greedy**. The recursive exploration reveals why: there are no real choices that create reusable subproblems.

### Build the intuition step by step (and why it's greedy, not DP)

1. **Entering a row/column has a fixed cost**, independent of the other coordinate: row `r` always costs `rowCosts[r]`.
2. **All costs are non-negative.** So any detour or backtrack only *adds* cost — the robot never benefits from moving away from home.
3. **The path is forced.** To get from `startRow` to `homeRow`, the robot must pass through (enter) **every row in between**, exactly once on a direct path. It cannot skip a row or substitute a "cheaper" one. Same for columns.
4. Therefore the minimum cost is a **fixed sum**: `rowCosts` of every row from start (exclusive) to home (inclusive), plus `colCosts` of every column likewise.

### The meta-lesson

When a problem tempts you toward DP, ask: **"Is there a choice that produces overlapping subproblems?"** Here the answer is no — the set of rows and columns crossed is determined entirely by start and home. No choice ⇒ no DP ⇒ greedy direct sum.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Sum row-entry costs toward home | `for (r = start+step; r != home+step; r += step) rowSum += rowCosts[r];` |
| Handle either direction (up/down, left/right) | `int rowStep = homePos[0] > startPos[0] ? 1 : -1;` |
| Same row or same column (no move) | loop condition false immediately ⇒ zero iterations |
| Total cost | `return rowSum + colSum;` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Greedy direct traversal — forced path, non-negative costs]
class Solution {
    public int minCost(int[] startPos, int[] homePos, int[] rowCosts, int[] colCosts) {
        int rowSum = 0, colSum = 0;
        int rowStep = homePos[0] > startPos[0] ? 1 : -1;
        int colStep = homePos[1] > startPos[1] ? 1 : -1;

        // Enter every row from start (exclusive) to home (inclusive).
        for (int r = startPos[0] + rowStep; r != homePos[0] + rowStep; r += rowStep) {
            rowSum += rowCosts[r];
        }
        // Enter every column from start (exclusive) to home (inclusive).
        for (int c = startPos[1] + colStep; c != homePos[1] + colStep; c += colStep) {
            colSum += colCosts[c];
        }

        return rowSum + colSum;
    }
}
```

### Why each part exists

- **`rowStep` / `colStep`** — pick the travel direction so one loop handles both "home above/below" and "home left/right".
- **Loop from `start + step` to `home + step`** — `start + step` skips the start cell (no cost to begin there); the `!= home + step` bound *includes* the home row/column (you pay to enter it).
- **Same-coordinate edge case is free** — if `start == home` in a dimension, the loop's start equals its (excluded) end after one step, so it runs zero times. No crash, no spurious cost.

## Complexity Analysis

- **Time Complexity:** $O(m + n)$ — one pass over the rows between start/home, one over the columns.
- **Space Complexity:** $O(1)$.

## Similar Problems

1. [LeetCode 64. Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/) — a grid path problem that *does* need DP (choices at each cell).
2. [LeetCode 1727. Largest Submatrix With Rearrangements](https://leetcode.com/problems/largest-submatrix-with-rearrangements/) — greedy after a transform.
3. [LeetCode 1029. Two City Scheduling](https://leetcode.com/problems/two-city-scheduling/) — greedy by sorting on a cost differential.
