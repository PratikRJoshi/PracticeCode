# Interleaving String

## Problem Description

**Problem Link:** [Interleaving String](https://leetcode.com/problems/interleaving-string/)

Given strings `s1`, `s2`, and `s3`, find whether `s3` is formed by an **interleaving** of `s1` and `s2`.

An **interleaving** of two strings `s` and `t` is a configuration where `s` and `t` are divided into `n` and `m` non-empty substrings respectively, such that:

- `s = s1 + s2 + ... + sn`
- `t = t1 + t2 + ... + tm`
- `|n - m| <= 1`
- The **interleaving** is `s1 + t1 + s2 + t2 + ...` or `t1 + s1 + t2 + s2 + ...`

**Note:** `a + b` is the concatenation of strings `a` and `b`.

**Example 1:**

```
Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
Output: true
Explanation: One possible interleaving: s1 = "aa" + "bc" + "c", s2 = "dbbc" + "a", s3 = "aa" + "dbbc" + "bc" + "a" + "c"
```

**Example 2:**

```
Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
Output: false
```

**Example 3:**

```
Input: s1 = "", s2 = "", s3 = ""
Output: true
```

**Constraints:**
- `0 <= s1.length, s2.length <= 100`
- `0 <= s3.length <= 200`
- `s1`, `s2`, and `s3` consist of lowercase English letters.

## Intuition/Main Idea

This is a **dynamic programming** problem. We need to check if `s3` can be formed by interleaving `s1` and `s2`.

**Core Algorithm:**
1. Use DP where `dp[i][j]` represents whether `s3[0..i+j-1]` can be formed by interleaving `s1[0..i-1]` and `s2[0..j-1]`.
2. For each position, `s3[i+j-1]` must match either `s1[i-1]` or `s2[j-1]`.
3. Recurrence: `dp[i][j] = (s1[i-1] == s3[i+j-1] && dp[i-1][j]) || (s2[j-1] == s3[i+j-1] && dp[i][j-1])`

**Why DP works:** The problem has overlapping subproblems - checking if `s3[k:]` can be formed from `s1[i:]` and `s2[j:]` might be needed multiple times. We can build the solution bottom-up.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Base case: empty strings | Base case initialization - Lines 7-9 |
| DP table | 2D DP array - Line 6 |
| Check s1 match | First condition - Lines 13-14 |
| Check s2 match | Second condition - Lines 15-16 |
| Combine conditions | OR operation - Line 12 |
| Return final result | Return statement - Line 19 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int len1 = s1.length();
        int len2 = s2.length();
        int len3 = s3.length();
        
        if (len1 + len2 != len3) {
            return false;
        }
        
        Boolean[][] memo = new Boolean[len1 + 1][len2 + 1];
        return dp(s1, s2, s3, len1, len2, memo);
    }
    
    private boolean dp(String s1, String s2, String s3, int i, int j, Boolean[][] memo) {
        // Base case: both strings exhausted
        if (i == 0 && j == 0) {
            return true;
        }
        
        // Check memoization
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        boolean result = false;
        
        // Check if we can use character from s1
        if (i > 0 && s1.charAt(i - 1) == s3.charAt(i + j - 1)) {
            result = dp(s1, s2, s3, i - 1, j, memo);
        }
        
        // Check if we can use character from s2 (if s1 didn't work)
        if (!result && j > 0 && s2.charAt(j - 1) == s3.charAt(i + j - 1)) {
            result = dp(s1, s2, s3, i, j - 1, memo);
        }
        
        memo[i][j] = result;
        return result;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int len1 = s1.length();
        int len2 = s2.length();
        int len3 = s3.length();
        
        // Length check
        if (len1 + len2 != len3) {
            return false;
        }
        
        // DP: dp[i][j] = can s3[0..i+j-1] be formed by interleaving s1[0..i-1] and s2[0..j-1]
        boolean[][] dp = new boolean[len1 + 1][len2 + 1];
        
        // Base case: empty strings
        dp[0][0] = true;
        
        // Fill first row: using only s2
        for (int j = 1; j <= len2; j++) {
            dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
        }
        
        // Fill first column: using only s1
        for (int i = 1; i <= len1; i++) {
            dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
        }
        
        // Fill DP table
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                // Current character in s3
                char c3 = s3.charAt(i + j - 1);
                
                // Option 1: Use character from s1
                boolean fromS1 = dp[i - 1][j] && s1.charAt(i - 1) == c3;
                
                // Option 2: Use character from s2
                boolean fromS2 = dp[i][j - 1] && s2.charAt(j - 1) == c3;
                
                dp[i][j] = fromS1 || fromS2;
            }
        }
        
        return dp[len1][len2];
    }
}
```

**Explanation of Key Code Sections:**

1. **Length Check (Lines 6-8):** If `len1 + len2 != len3`, it's impossible to form `s3`.

2. **Base Case (Line 11):** `dp[0][0] = true` because empty strings can form an empty string.

3. **Initialize First Row (Lines 13-15):** Fill first row using only `s2`. `dp[0][j]` is true if `s2[0..j-1]` matches `s3[0..j-1]`.

4. **Initialize First Column (Lines 17-19):** Fill first column using only `s1`. `dp[i][0]` is true if `s1[0..i-1]` matches `s3[0..i-1]`.

5. **Fill DP Table (Lines 21-31):** For each `(i, j)`:
   - **Current Character (Line 23):** The character in `s3` at position `i+j-1`.
   - **From s1 (Line 26):** Can we form `s3[0..i+j-1]` by using `s1[0..i-1]` and `s2[0..j-1]`, where the last character comes from `s1`?
   - **From s2 (Line 29):** Same but last character from `s2`.
   - **Combine (Line 31):** Either option works.

**Intuition behind generating subproblems:**
- **Subproblem:** "Can `s3[0..k]` be formed by interleaving `s1[0..i]` and `s2[0..j]`?"
- **Why this works:** To form `s3[0..k]`, the last character must come from either `s1[i]` or `s2[j]`. If it comes from `s1[i]`, we need to form `s3[0..k-1]` from `s1[0..i-1]` and `s2[0..j]`.
- **Overlapping subproblems:** Multiple paths may check the same subproblem.

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive, recursive. Time: O(m×n), Space: O(m×n) for memo + stack.
- **Bottom-up (iterative):** More efficient, no recursion. Time: O(m×n), Space: O(m×n), can be optimized to O(n).
- **When bottom-up is better:** Better for this problem due to no recursion overhead and potential space optimization.

## Complexity Analysis

- **Time Complexity:** $O(m \times n)$ where $m$ and $n$ are the lengths of `s1` and `s2`. We fill a 2D DP table.

- **Space Complexity:** $O(m \times n)$ for the DP table. Can be optimized to $O(n)$ using 1D array.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **97. Interleaving String** (this problem) - DP for string interleaving
2. **72. Edit Distance** - DP for string transformation
3. **115. Distinct Subsequences** - DP for subsequence counting
4. **1143. Longest Common Subsequence** - DP for LCS
5. **10. Regular Expression Matching** - DP with wildcards
6. **44. Wildcard Matching** - DP with wildcards
7. **583. Delete Operation for Two Strings** - DP for deletion
8. **712. Minimum ASCII Delete Sum for Two Strings** - DP with costs
9. **1312. Minimum Insertion Steps to Make a String Palindrome** - DP for palindrome
10. **516. Longest Palindromic Subsequence** - DP for palindrome

