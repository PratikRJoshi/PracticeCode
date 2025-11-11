# Triangle

## Problem Description

**Problem Link:** [Triangle](https://leetcode.com/problems/triangle/)

Given a `triangle` array, return *the minimum path sum from top to bottom*.

For each step, you may move to an adjacent number of the row below. More formally, if you are on index `i` on the current row, you may move to either index `i` or index `i + 1` on the next row.

**Example 1:**

```
Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
Output: 11
Explanation: The triangle looks like:
   2
  3 4
 6 5 7
4 1 8 3
The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11 (underlined above).
```

**Example 2:**

```
Input: triangle = [[-10]]
Output: -10
```

**Constraints:**
- `1 <= triangle.length <= 200`
- `triangle[0].length == 1`
- `triangle[i].length == triangle[i - 1].length + 1`
- `-10^4 <= triangle[i][j] <= 10^4`

## Intuition/Main Idea

This is a **2D dynamic programming** problem. We need to find the minimum path sum from top to bottom of a triangle.

**Core Algorithm:**
1. Use DP where `dp[i][j]` = minimum path sum to reach `triangle[i][j]`.
2. We can come from `triangle[i-1][j-1]` or `triangle[i-1][j]`.
3. `dp[i][j] = triangle[i][j] + min(dp[i-1][j-1], dp[i-1][j])`.
4. Handle boundary cases (first and last elements of each row).

**Why DP works:** The problem has overlapping subproblems - the minimum path to a cell is needed to compute paths to cells below it. We can build the solution bottom-up or top-down.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| DP for path sum | DP array - Line 5 |
| Base case: top | dp[0][0] - Line 7 |
| Process each row | Outer loop - Line 9 |
| Handle first element | First element - Line 11 |
| Handle last element | Last element - Line 13 |
| Handle middle elements | Middle elements - Line 15 |
| Return minimum | Return statement - Line 19 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
import java.util.*;

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        Integer[][] memo = new Integer[n][n];
        return minPath(triangle, 0, 0, memo);
    }
    
    private int minPath(List<List<Integer>> triangle, int row, int col, Integer[][] memo) {
        if (row == triangle.size()) {
            return 0;
        }
        
        if (memo[row][col] != null) {
            return memo[row][col];
        }
        
        int current = triangle.get(row).get(col);
        int min = current + Math.min(
            minPath(triangle, row + 1, col, memo),
            minPath(triangle, row + 1, col + 1, memo)
        );
        
        memo[row][col] = min;
        return min;
    }
}
```

### Bottom-Up Version

```java
import java.util.*;

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        
        // DP: dp[i][j] = minimum path sum to reach triangle[i][j]
        int[][] dp = new int[n][n];
        
        // Base case: top element
        dp[0][0] = triangle.get(0).get(0);
        
        for (int i = 1; i < n; i++) {
            // First element: can only come from above
            dp[i][0] = triangle.get(i).get(0) + dp[i - 1][0];
            
            // Last element: can only come from above-left
            dp[i][i] = triangle.get(i).get(i) + dp[i - 1][i - 1];
            
            // Middle elements: can come from above or above-left
            for (int j = 1; j < i; j++) {
                dp[i][j] = triangle.get(i).get(j) + 
                           Math.min(dp[i - 1][j - 1], dp[i - 1][j]);
            }
        }
        
        // Find minimum in last row
        int min = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            min = Math.min(min, dp[n - 1][j]);
        }
        
        return min;
    }
}
```

**Space-Optimized Version:**

```java
import java.util.*;

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        
        // Use only previous row
        int[] dp = new int[n];
        dp[0] = triangle.get(0).get(0);
        
        for (int i = 1; i < n; i++) {
            // Process from right to left to avoid overwriting
            dp[i] = dp[i - 1] + triangle.get(i).get(i);  // Last element
            
            for (int j = i - 1; j > 0; j--) {
                dp[j] = triangle.get(i).get(j) + Math.min(dp[j - 1], dp[j]);
            }
            
            dp[0] += triangle.get(i).get(0);  // First element
        }
        
        // Find minimum
        int min = Integer.MAX_VALUE;
        for (int val : dp) {
            min = Math.min(min, val);
        }
        
        return min;
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Line 7):** `dp[0][0] = triangle[0][0]` - starting at the top.

2. **Process Each Row (Lines 9-18):** For each row `i`:
   - **First Element (Line 11):** Can only come from `dp[i-1][0]`.
   - **Last Element (Line 13):** Can only come from `dp[i-1][i-1]`.
   - **Middle Elements (Lines 15-17):** Can come from `dp[i-1][j-1]` or `dp[i-1][j]`, take minimum.

3. **Find Minimum (Lines 20-23):** The answer is the minimum value in the last row.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the minimum path sum to reach `triangle[i][j]`?"
- **Why this works:** To reach `triangle[i][j]`, we must come from either `triangle[i-1][j-1]` or `triangle[i-1][j]`. The optimal path uses the minimum of these two options.
- **Overlapping subproblems:** Multiple paths may share the same subproblems.

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive, recursive. Time: O(n²), Space: O(n²) for memo + stack.
- **Bottom-up (iterative):** More efficient, no recursion. Time: O(n²), Space: O(n²) or O(n) optimized.
- **When bottom-up is better:** Better for this problem due to no recursion overhead and space optimization possible.

## Complexity Analysis

- **Time Complexity:** $O(n^2)$ where $n$ is the number of rows. We process each cell once.

- **Space Complexity:** 
  - Bottom-up with 2D: $O(n^2)$.
  - Bottom-up optimized: $O(n)$ using 1D array.

## Similar Problems

Problems that can be solved using similar 2D DP patterns:

1. **120. Triangle** (this problem) - Triangle path sum
2. **64. Minimum Path Sum** - Grid path sum
3. **62. Unique Paths** - Count paths
4. **63. Unique Paths II** - With obstacles
5. **931. Minimum Falling Path Sum** - Falling path
6. **174. Dungeon Game** - Reverse DP
7. **221. Maximal Square** - Square DP
8. **85. Maximal Rectangle** - Rectangle DP

