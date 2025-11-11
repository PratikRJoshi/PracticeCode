# Minimum Score Triangulation of Polygon

## Problem Description

**Problem Link:** [Minimum Score Triangulation of Polygon](https://leetcode.com/problems/minimum-score-triangulation-of-polygon/)

You have a convex `n`-sided polygon where each vertex is labeled with a value. You are given an integer array `values` where `values[i]` is the value of the `i`th vertex (i.e., **clockwise order**).

You want to triangulate the polygon into `n - 2` triangles. For each triangle, the value of that triangle is the product of the values of its vertices, and the total score of the triangulation is the sum of these values over all `n - 2` triangles in the triangulation.

Return *the smallest possible total score that you can achieve with some triangulation of the polygon*.

**Example 1:**

```
Input: values = [1,2,3]
Output: 6
Explanation: The polygon is already triangulated, and the score of the only triangle is 1*2*3 = 6.
```

**Example 2:**

```
Input: values = [3,7,4,5]
Output: 144
Explanation: There are two triangulations, with respective scores of 3*7*5 + 4*5*7 = 245, or 3*4*5 + 3*4*7 = 144.
The minimum score is 144.
```

**Constraints:**
- `n == values.length`
- `3 <= n <= 50`
- `1 <= values[i] <= 100`

## Intuition/Main Idea

This is an **interval DP** problem similar to Matrix Chain Multiplication. We need to find the optimal way to triangulate a polygon.

**Core Algorithm:**
1. Use DP where `dp[i][j]` = minimum score to triangulate polygon from vertex `i` to `j`.
2. For a polygon from `i` to `j`, try each vertex `k` between `i` and `j` as the third vertex of a triangle.
3. `dp[i][j] = min(values[i] * values[k] * values[j] + dp[i][k] + dp[k][j])` for all `k` in `(i, j)`.

**Why interval DP works:** To triangulate polygon `[i..j]`, we choose a vertex `k` to form a triangle `(i, k, j)`, then triangulate the remaining polygons `[i..k]` and `[k..j]`. This creates overlapping subproblems.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| DP for interval scores | 2D DP array - Line 6 |
| Process by length | Length loop - Line 8 |
| Try each middle vertex | Middle loop - Line 11 |
| Calculate triangle score | Score calculation - Line 14 |
| Combine subproblems | DP transition - Line 15 |
| Return result | Return statement - Line 18 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        Integer[][] memo = new Integer[n][n];
        return minScore(values, 0, n - 1, memo);
    }
    
    private int minScore(int[] values, int i, int j, Integer[][] memo) {
        // Base case: less than 3 vertices, no triangle possible
        if (j - i < 2) {
            return 0;
        }
        
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        int min = Integer.MAX_VALUE;
        // Try each vertex k between i and j as the third vertex of triangle (i, k, j)
        for (int k = i + 1; k < j; k++) {
            int score = values[i] * values[k] * values[j] +
                       minScore(values, i, k, memo) +
                       minScore(values, k, j, memo);
            min = Math.min(min, score);
        }
        
        memo[i][j] = min;
        return min;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        
        // DP: dp[i][j] = minimum score to triangulate polygon from vertex i to j
        int[][] dp = new int[n][n];
        
        // Process intervals by length (from smallest to largest)
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                
                dp[i][j] = Integer.MAX_VALUE;
                // Try each vertex k between i and j as third vertex of triangle (i, k, j)
                for (int k = i + 1; k < j; k++) {
                    int score = values[i] * values[k] * values[j] +
                               dp[i][k] + dp[k][j];
                    dp[i][j] = Math.min(dp[i][j], score);
                }
            }
        }
        
        return dp[0][n - 1];
    }
}
```

**Explanation of Key Code Sections:**

1. **Process by Length (Line 8):** Process intervals from length 3 (triangle) to `n` (full polygon). This ensures subproblems are computed before they're needed.

2. **Try Middle Vertex (Lines 11-16):** For polygon `[i..j]`, try each vertex `k` between `i` and `j`:
   - Form triangle `(i, k, j)` with score `values[i] * values[k] * values[j]`.
   - Add scores from triangulating `[i..k]` and `[k..j]`.
   - Take the minimum.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the minimum score to triangulate polygon from vertex `i` to `j`?"
- **Why this works:** To triangulate `[i..j]`, we choose a vertex `k` to form triangle `(i, k, j)`, then optimally triangulate the remaining polygons.
- **Overlapping subproblems:** Multiple triangulations may share the same subpolygons.

**Example walkthrough for `values = [3,7,4,5]`:**
- dp[0][2] = 3*7*4 = 84 (triangle only)
- dp[1][3] = 7*4*5 = 140 (triangle only)
- dp[0][3]: Try k=1 → 3*7*5 + dp[0][1] + dp[1][3] = 105 + 0 + 140 = 245
- Try k=2 → 3*4*5 + dp[0][2] + dp[2][3] = 60 + 84 + 0 = 144
- Result: min(245, 144) = 144 ✓

## Complexity Analysis

- **Time Complexity:** $O(n^3)$ where $n$ is the number of vertices. We process $O(n^2)$ intervals and try $O(n)$ middle vertices for each.

- **Space Complexity:** $O(n^2)$ for the DP table.

## Similar Problems

Problems that can be solved using similar interval DP patterns:

1. **1039. Minimum Score Triangulation of Polygon** (this problem) - Interval DP
2. **312. Burst Balloons** - Interval DP
3. **1130. Minimum Cost Tree From Leaf Values** - Interval DP
4. **1547. Minimum Cost to Cut a Stick** - Interval DP
5. **1000. Minimum Cost to Merge Stones** - Interval DP with constraints
6. **516. Longest Palindromic Subsequence** - Interval DP
7. **664. Strange Printer** - Interval DP
8. **87. Scramble String** - Interval DP variant

