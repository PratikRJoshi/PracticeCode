# Dungeon Game

## Problem Description

**Problem Link:** [Dungeon Game](https://leetcode.com/problems/dungeon-game/)

The demons had captured the princess and imprisoned her in **the bottom-right corner of a dungeon**. The dungeon consists of `m x n` rooms laid out in a 2D grid. Our valiant knight initially positioned in **the top-left corner** must battle his way through `dungeon` to rescue the princess.

The knight has an initial health point represented by a positive integer. If at any point his health point drops to 0 or below, he dies immediately.

Some of the rooms are guarded by demons (represented by negative integers), and others contain health orbs (represented by positive integers).

To reach the princess as quickly as possible, the knight decides to move only **rightward or downward** in each step.

Return *the knight's minimum initial health so that he can rescue the princess*.

**Note** that any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room where the princess is imprisoned.

**Example 1:**

```
Input: dungeon = [[-2,-3,3],[-5,-10,1],[10,30,-5]]
Output: 7
Explanation: The initial health of the knight must be at least 7 if he follows the optimal path: RIGHT-> RIGHT -> DOWN -> DOWN.
```

**Example 2:**

```
Input: dungeon = [[0]]
Output: 1
```

**Constraints:**
- `m == dungeon.length`
- `n == dungeon[i].length`
- `1 <= m, n <= 200`
- `-1000 <= dungeon[i][j] <= 1000`

## Intuition/Main Idea

This problem requires **reverse DP** - we work backwards from the princess to the knight. The key insight is that we need to ensure the knight has at least 1 health at each cell.

**Core Algorithm:**
1. Start from the bottom-right (princess).
2. Use DP where `dp[i][j]` = minimum health needed at `(i, j)` to reach the princess.
3. Work backwards: `dp[i][j] = max(1, min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j])`.
4. The answer is `dp[0][0]`.

**Why reverse DP works:** We know we need at least 1 health at the princess. Working backwards, we can determine the minimum health needed at each cell to ensure we never drop below 1.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Initialize bottom-right | Base case - Lines 8-9 |
| Initialize last row | Backward iteration - Lines 11-13 |
| Initialize last column | Backward iteration - Lines 15-17 |
| Reverse DP transition | Nested loops - Lines 19-22 |
| Calculate minimum health | Health calculation - Line 21 |
| Return result | Return statement - Line 24 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        Integer[][] memo = new Integer[m][n];
        return minHealth(dungeon, 0, 0, memo);
    }
    
    private int minHealth(int[][] dungeon, int i, int j, Integer[][] memo) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        
        // Base case: reached princess
        if (i == m - 1 && j == n - 1) {
            return Math.max(1, 1 - dungeon[i][j]);
        }
        
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        int min = Integer.MAX_VALUE;
        
        // Try going right
        if (j + 1 < n) {
            int rightHealth = minHealth(dungeon, i, j + 1, memo);
            min = Math.min(min, Math.max(1, rightHealth - dungeon[i][j]));
        }
        
        // Try going down
        if (i + 1 < m) {
            int downHealth = minHealth(dungeon, i + 1, j, memo);
            min = Math.min(min, Math.max(1, downHealth - dungeon[i][j]));
        }
        
        memo[i][j] = min;
        return min;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        
        // DP: dp[i][j] = minimum health needed at (i,j) to reach princess
        int[][] dp = new int[m][n];
        
        // Base case: bottom-right cell
        dp[m - 1][n - 1] = Math.max(1, 1 - dungeon[m - 1][n - 1]);
        
        // Initialize last row: can only go right
        for (int j = n - 2; j >= 0; j--) {
            dp[m - 1][j] = Math.max(1, dp[m - 1][j + 1] - dungeon[m - 1][j]);
        }
        
        // Initialize last column: can only go down
        for (int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = Math.max(1, dp[i + 1][n - 1] - dungeon[i][n - 1]);
        }
        
        // Fill DP table backwards
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                // Minimum health needed from next step
                int minNext = Math.min(dp[i + 1][j], dp[i][j + 1]);
                // Current health needed = max(1, minNext - current cell value)
                dp[i][j] = Math.max(1, minNext - dungeon[i][j]);
            }
        }
        
        return dp[0][0];
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Lines 8-9):** At the princess cell, we need at least `max(1, 1 - dungeon[m-1][n-1])` health. If the cell has a demon, we need extra health.

2. **Last Row (Lines 11-13):** Can only go right, so health needed is based on right cell.

3. **Last Column (Lines 15-17):** Can only go down, so health needed is based on bottom cell.

4. **DP Transition (Lines 19-22):** For each cell:
   - **Min Next (Line 20):** Minimum health needed from the next step (right or down).
   - **Current Health (Line 21):** `max(1, minNext - dungeon[i][j])`. We need at least 1 health, and after this cell we need `minNext`.

**Why reverse DP:**
- **Forward DP doesn't work:** If we go forward, we don't know future threats, so we can't determine minimum health needed.
- **Reverse DP works:** We know we need 1 health at the end, and work backwards to determine what we need at each step.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the minimum health needed at `(i, j)` to reach the princess?"
- **Why this works:** To determine health at `(i, j)`, we need to know the minimum health needed after this cell. Working backwards, we can compute this.
- **Overlapping subproblems:** Multiple paths may share the same optimal subproblems.

**Example walkthrough for `dungeon = [[-2,-3,3],[-5,-10,1],[10,30,-5]]`:**
- dp[2][2] = max(1, 1-(-5)) = 6
- dp[2][1] = max(1, 6-30) = 1
- dp[2][0] = max(1, 1-10) = 1
- dp[1][2] = max(1, 6-1) = 5
- Continue backwards...
- dp[0][0] = 7 ✓

## Complexity Analysis

- **Time Complexity:** $O(m \times n)$ where $m$ and $n$ are the grid dimensions. We visit each cell once.

- **Space Complexity:** $O(m \times n)$ for the DP array. Can be optimized to $O(\min(m, n))$.

## Similar Problems

Problems that can be solved using similar reverse DP patterns:

1. **174. Dungeon Game** (this problem) - Reverse DP
2. **64. Minimum Path Sum** - Forward DP
3. **62. Unique Paths** - Count paths
4. **63. Unique Paths II** - With obstacles
5. **120. Triangle** - Triangle path
6. **931. Minimum Falling Path Sum** - Falling path
7. **221. Maximal Square** - Square DP
8. **85. Maximal Rectangle** - Rectangle DP

