# Minimum Cost Tree From Leaf Values

## Problem Description

**Problem Link:** [Minimum Cost Tree From Leaf Values](https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/)

Given an array `arr` of positive integers, consider all binary trees such that:

- Each node has either `0` or `2` children.
- The values of `arr` correspond to the values of each **leaf** in an in-order traversal of the tree.
- The value of each non-leaf node is equal to the product of the largest leaf value in its left and right subtrees, respectively.

Among all possible binary trees, return *the smallest possible sum of the values of each non-leaf node*. It is guaranteed that the answer fits in a **32-bit** signed integer (i.e., it is less than $2^{31}$).

**Example 1:**

```
Input: arr = [6,2,4]
Output: 32
Explanation: There are two possible trees:
- First: non-leaf node sum = 6*2 + 6*4 = 12 + 24 = 36
- Second: non-leaf node sum = 2*4 + 6*4 = 8 + 24 = 32
The second tree has a smaller sum of 32.
```

**Example 2:**

```
Input: arr = [4,11]
Output: 44
```

**Constraints:**
- `2 <= arr.length <= 40`
- `1 <= arr[i] <= 15`
- It is guaranteed that the answer fits in a **32-bit** signed integer (i.e., it is less than $2^{31}$).

## Intuition/Main Idea

This is an **interval DP** problem. We need to build a binary tree from leaves with minimum cost.

**Core Algorithm:**
1. Use DP where `dp[i][j]` = minimum cost to build tree from leaves `arr[i..j]`.
2. For interval `[i..j]`, try each split point `k` where `i <= k < j`.
3. `dp[i][j] = min(max(arr[i..k]) * max(arr[k+1..j]) + dp[i][k] + dp[k+1][j])`.

**Why interval DP works:** To build a tree from `arr[i..j]`, we split at some point `k`, build left subtree from `arr[i..k]` and right subtree from `arr[k+1..j]`. The root value is `max(left) * max(right)`. This creates overlapping subproblems.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Precompute max values | Max array - Lines 7-11 |
| DP for interval cost | 2D DP array - Line 13 |
| Process by length | Length loop - Line 15 |
| Try each split | Split loop - Line 18 |
| Calculate root value | Max product - Line 20 |
| Combine subproblems | DP transition - Line 21 |
| Return result | Return statement - Line 25 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int mctFromLeafValues(int[] arr) {
        int n = arr.length;
        Integer[][] memo = new Integer[n][n];
        return minCost(arr, 0, n - 1, memo);
    }
    
    private int minCost(int[] arr, int i, int j, Integer[][] memo) {
        // Base case: single leaf, no cost
        if (i == j) {
            return 0;
        }
        
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        int min = Integer.MAX_VALUE;
        // Try each split point k
        for (int k = i; k < j; k++) {
            int leftMax = getMax(arr, i, k);
            int rightMax = getMax(arr, k + 1, j);
            int cost = leftMax * rightMax +
                      minCost(arr, i, k, memo) +
                      minCost(arr, k + 1, j, memo);
            min = Math.min(min, cost);
        }
        
        memo[i][j] = min;
        return min;
    }
    
    private int getMax(int[] arr, int i, int j) {
        int max = 0;
        for (int k = i; k <= j; k++) {
            max = Math.max(max, arr[k]);
        }
        return max;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int mctFromLeafValues(int[] arr) {
        int n = arr.length;
        
        // Precompute max values for all intervals
        int[][] maxVal = new int[n][n];
        for (int i = 0; i < n; i++) {
            maxVal[i][i] = arr[i];
            for (int j = i + 1; j < n; j++) {
                maxVal[i][j] = Math.max(maxVal[i][j - 1], arr[j]);
            }
        }
        
        // DP: dp[i][j] = minimum cost to build tree from arr[i..j]
        int[][] dp = new int[n][n];
        
        // Process intervals by length
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                
                // Try each split point k
                for (int k = i; k < j; k++) {
                    int cost = maxVal[i][k] * maxVal[k + 1][j] +
                              dp[i][k] + dp[k + 1][j];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        
        return dp[0][n - 1];
    }
}
```

**Explanation of Key Code Sections:**

1. **Precompute Max Values (Lines 7-11):** Precompute `maxVal[i][j]` = maximum value in `arr[i..j]` to avoid recomputation.

2. **Process by Length (Line 15):** Process intervals from length 2 to `n`.

3. **Try Splits (Lines 18-22):** For each interval `[i..j]`, try each split `k`:
   - Left subtree: `arr[i..k]` with max `maxVal[i][k]`
   - Right subtree: `arr[k+1..j]` with max `maxVal[k+1][j]`
   - Root value: `maxVal[i][k] * maxVal[k+1][j]`
   - Total cost: root value + left cost + right cost

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the minimum cost to build a tree from `arr[i..j]`?"
- **Why this works:** To build a tree from `arr[i..j]`, we split at some point `k`, build left and right subtrees optimally, and combine them with cost `max(left) * max(right)`.
- **Overlapping subproblems:** Multiple trees may share the same subtrees.

**Example walkthrough for `arr = [6,2,4]`:**
- maxVal: [6,6,6], [2,4], [4]
- dp[0][1]: split at 0 → 6*2 + 0 + 0 = 12
- dp[1][2]: split at 1 → 2*4 + 0 + 0 = 8
- dp[0][2]: split at 0 → 6*4 + dp[0][0] + dp[1][2] = 24 + 0 + 8 = 32
- split at 1 → 6*4 + dp[0][1] + dp[2][2] = 24 + 12 + 0 = 36
- Result: min(32, 36) = 32 ✓

## Complexity Analysis

- **Time Complexity:** $O(n^3)$ where $n$ is the array length. We process $O(n^2)$ intervals and try $O(n)$ splits for each.

- **Space Complexity:** $O(n^2)$ for the DP table and max value table.

## Similar Problems

Problems that can be solved using similar interval DP patterns:

1. **1130. Minimum Cost Tree From Leaf Values** (this problem) - Interval DP
2. **1039. Minimum Score Triangulation of Polygon** - Interval DP
3. **312. Burst Balloons** - Interval DP
4. **1547. Minimum Cost to Cut a Stick** - Interval DP
5. **1000. Minimum Cost to Merge Stones** - Interval DP
6. **516. Longest Palindromic Subsequence** - Interval DP
7. **664. Strange Printer** - Interval DP
8. **87. Scramble String** - Interval DP variant

