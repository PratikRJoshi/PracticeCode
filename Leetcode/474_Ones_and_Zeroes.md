# Ones and Zeroes

## Problem Description

**Problem Link:** [Ones and Zeroes](https://leetcode.com/problems/ones-and-zeroes/)

You are given an array of binary strings `strs` and two integers `m` and `n`.

Return *the size of the largest subset of* `strs` *such that there are **at most*** `m` *`0`'s and* `n` *`1`'s in the subset*.

A set `x` is a **subset** of a set `y` if all elements of `x` are also elements of `y`.

**Example 1:**
```
Input: strs = ["10","0001","111001","1","0"], m = 5, n = 3
Output: 4
Explanation: The largest subset with at most 5 0's and 3 1's is {"10", "0001", "1", "0"}, so the answer is 4.
```

**Constraints:**
- `1 <= strs.length <= 600`
- `1 <= strs[i].length <= 100`
- `strs[i]` consists only of digits `'0'` and `'1'`.
- `1 <= m, n <= 100`

## Intuition/Main Idea

This is a 2D knapsack problem. We need to maximize number of strings while respecting 0 and 1 limits.

**Core Algorithm:**
- Use 2D DP: `dp[i][j]` = max strings using at most i zeros and j ones
- For each string, count its zeros and ones
- Update DP: either include string or not

**Why 2D DP:** We have two constraints (zeros and ones), so we need 2D state space.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Maximize subset size | 2D DP - Lines 8-30 |
| Count zeros and ones | Character counting - Lines 12-18 |
| Update DP | Include/exclude - Lines 22-26 |

## Final Java Code & Learning Pattern (Full Content)

### Bottom-Up Approach

```java
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        // dp[i][j] = maximum number of strings using at most i zeros and j ones
        // Size: (m+1) x (n+1) to handle limits
        int[][] dp = new int[m + 1][n + 1];
        
        for (String str : strs) {
            // Count zeros and ones in current string
            int zeros = 0, ones = 0;
            for (char c : str.toCharArray()) {
                if (c == '0') zeros++;
                else ones++;
            }
            
            // Update DP from bottom-right to top-left
            // This ensures we don't use same string twice
            for (int i = m; i >= zeros; i--) {
                for (int j = n; j >= ones; j--) {
                    // Either include current string or not
                    dp[i][j] = Math.max(dp[i][j], 
                        dp[i - zeros][j - ones] + 1);
                }
            }
        }
        
        return dp[m][n];
    }
}
```

### Top-Down Approach (Memoization)

```java
class Solution {
    private Integer[][][] memo;
    
    public int findMaxForm(String[] strs, int m, int n) {
        memo = new Integer[strs.length][m + 1][n + 1];
        return helper(strs, 0, m, n);
    }
    
    private int helper(String[] strs, int index, int zeros, int ones) {
        if (index == strs.length || (zeros == 0 && ones == 0)) {
            return 0;
        }
        
        if (memo[index][zeros][ones] != null) {
            return memo[index][zeros][ones];
        }
        
        // Count zeros and ones in current string
        int strZeros = 0, strOnes = 0;
        for (char c : strs[index].toCharArray()) {
            if (c == '0') strZeros++;
            else strOnes++;
        }
        
        // Option 1: Skip current string
        int skip = helper(strs, index + 1, zeros, ones);
        
        // Option 2: Include current string (if possible)
        int include = 0;
        if (strZeros <= zeros && strOnes <= ones) {
            include = 1 + helper(strs, index + 1, zeros - strZeros, ones - strOnes);
        }
        
        memo[index][zeros][ones] = Math.max(skip, include);
        return memo[index][zeros][ones];
    }
}
```

## Dynamic Programming Analysis

**Intuition behind generating subproblems:**
- Subproblem: Maximum strings using at most i zeros and j ones
- For each string, decide to include or exclude
- If include, subtract its zeros and ones from budget

**Top-down / Memoized version:**
- Recursive with memoization
- Tracks index, remaining zeros, remaining ones
- More intuitive but may have stack overflow for large inputs

**Bottom-up version:**
- Iterative, fills DP table
- More space efficient, avoids recursion overhead
- Iterate backwards to avoid using same string twice

**DP array size allocation:**
- Bottom-up: `(m+1) x (n+1)` - track max strings for each (zeros, ones) budget
- Top-down: `strs.length x (m+1) x (n+1)` - memoize all states
- +1 for base cases (0 zeros/ones)

## Complexity Analysis

**Time Complexity:** 
- Bottom-up: $O(strs.length \times m \times n)$
- Top-down: $O(strs.length \times m \times n)$ with memoization

**Space Complexity:**
- Bottom-up: $O(m \times n)$
- Top-down: $O(strs.length \times m \times n)$ for memo

## Similar Problems

- [Coin Change](https://leetcode.com/problems/coin-change/) - Similar knapsack pattern
- [Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/) - Subset sum problem
- [Target Sum](https://leetcode.com/problems/target-sum/) - Similar DP with constraints

