# Regular Expression Matching

## Problem Description

**Problem Link:** [Regular Expression Matching](https://leetcode.com/problems/regular-expression-matching/)

Given an input string `s` and a pattern `p`, implement regular expression matching with support for `'.'` and `'*'` where:

- `'.'` Matches any single character.
- `'*'` Matches zero or more of the preceding element.

The matching should cover the **entire** input string (not partial).

**Example 1:**
```
Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
```

**Example 2:**
```
Input: s = "aa", p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
```

**Example 3:**
```
Input: s = "ab", p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".
```

**Constraints:**
- `1 <= s.length <= 20`
- `1 <= p.length <= 20`
- `s` contains only lowercase English letters.
- `p` contains only lowercase English letters, `'.'`, and `'*'`.
- It is guaranteed that for each appearance of the character `'*'`, there will be a previous valid character to match.

## Intuition/Main Idea

This is a classic **dynamic programming** problem. The key challenge is handling the `'*'` wildcard which can match zero or more of the preceding character.

**Core Algorithm:**
1. Use DP where `dp[i][j]` represents whether `s[0..i-1]` matches `p[0..j-1]`.
2. Handle three cases:
   - If `p[j-1] == '*'`: The preceding character can be repeated 0 or more times.
   - If `p[j-1] == '.'`: Matches any single character.
   - Otherwise: Exact character match.

**Why DP works:** The problem has overlapping subproblems - matching `s[i:]` with `p[j:]` might be needed multiple times. We can build the solution bottom-up or top-down with memoization.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Base case: empty string matches empty pattern | `dp[0][0] = true` - Line 8 |
| Handle patterns with '*' | Star handling - Lines 19-25 |
| Handle '.' wildcard | Dot handling - Line 27 |
| Handle exact character match | Character match - Line 29 |
| Return final result | Return statement - Line 32 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        Boolean[][] memo = new Boolean[m + 1][n + 1];
        return dp(s, p, m, n, memo);
    }
    
    private boolean dp(String s, String p, int i, int j, Boolean[][] memo) {
        // Base case: both strings exhausted
        if (i == 0 && j == 0) {
            return true;
        }
        
        // Base case: pattern exhausted but string not
        if (j == 0) {
            return false;
        }
        
        // Base case: string exhausted
        if (i == 0) {
            // Only matches if remaining pattern is like "x*y*z*" (all stars)
            return p.charAt(j - 1) == '*' && dp(s, p, i, j - 2, memo);
        }
        
        // Check memoization
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        boolean result = false;
        
        // Case 1: Current pattern character is '*'
        if (p.charAt(j - 1) == '*') {
            // Option 1: Match zero characters (skip the pattern "x*")
            result = dp(s, p, i, j - 2, memo);
            
            // Option 2: Match one or more characters
            // Check if preceding character matches current string character
            if (!result && (p.charAt(j - 2) == '.' || p.charAt(j - 2) == s.charAt(i - 1))) {
                result = dp(s, p, i - 1, j, memo);
            }
        }
        // Case 2: Current pattern character is '.'
        else if (p.charAt(j - 1) == '.') {
            result = dp(s, p, i - 1, j - 1, memo);
        }
        // Case 3: Exact character match
        else {
            result = (s.charAt(i - 1) == p.charAt(j - 1)) && dp(s, p, i - 1, j - 1, memo);
        }
        
        memo[i][j] = result;
        return result;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        
        // DP: dp[i][j] = does s[0..i-1] match p[0..j-1]
        boolean[][] dp = new boolean[m + 1][n + 1];
        
        // Base case: empty string matches empty pattern
        dp[0][0] = true;
        
        // Handle patterns like "a*b*c*" matching empty string
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }
        
        // Fill DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // Case 1: Current pattern character is '*'
                if (p.charAt(j - 1) == '*') {
                    // Option 1: Match zero characters (skip "x*")
                    dp[i][j] = dp[i][j - 2];
                    
                    // Option 2: Match one or more characters
                    // Check if preceding character matches current string character
                    if (!dp[i][j] && (p.charAt(j - 2) == '.' || p.charAt(j - 2) == s.charAt(i - 1))) {
                        dp[i][j] = dp[i - 1][j];
                    }
                }
                // Case 2: Current pattern character is '.'
                else if (p.charAt(j - 1) == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                // Case 3: Exact character match
                else {
                    dp[i][j] = (s.charAt(i - 1) == p.charAt(j - 1)) && dp[i - 1][j - 1];
                }
            }
        }
        
        return dp[m][n];
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case Initialization (Line 8):** `dp[0][0] = true` because an empty string matches an empty pattern.

2. **Empty String Pattern Matching (Lines 10-13):** Handle patterns like `"a*b*c*"` that can match an empty string. If pattern ends with `'*'`, it can match zero characters.

3. **Main DP Loop (Lines 15-35):** For each position `(i, j)`:
   - **Star Case (Lines 17-25):** If pattern has `'*'`:
     - Option 1: Match zero characters - skip the `"x*"` pattern: `dp[i][j-2]`
     - Option 2: Match one or more - if preceding char matches, continue matching: `dp[i-1][j]`
   - **Dot Case (Line 27):** `'.'` matches any character, so `dp[i][j] = dp[i-1][j-1]`
   - **Exact Match (Line 29):** Characters must match exactly: `dp[i][j] = (s[i-1] == p[j-1]) && dp[i-1][j-1]`

**Intuition behind generating subproblems:**
- **Subproblem:** "Does `s[0..i-1]` match `p[0..j-1]`?"
- **Why this works:** To match `s[0..i]` with `p[0..j]`, we need to check:
  - If `p[j] == '*'`: Can we match `s[0..i]` with `p[0..j-2]` (zero match) OR `s[0..i-1]` with `p[0..j]` (one+ match)?
  - Otherwise: Do `s[i]` and `p[j]` match, and does `s[0..i-1]` match `p[0..j-1]`?
- **Overlapping subproblems:** Multiple paths may check the same subproblem.

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive, recursive. Time: O(m×n), Space: O(m×n) for memo + stack.
- **Bottom-up (iterative):** More efficient, no recursion. Time: O(m×n), Space: O(m×n).
- **When bottom-up is better:** Better for this problem due to no recursion overhead and better cache locality.

## Complexity Analysis

- **Time Complexity:** $O(m \times n)$ where $m$ is the length of `s` and $n$ is the length of `p`. We fill a 2D DP table.

- **Space Complexity:** $O(m \times n)$ for the DP table. Can be optimized to $O(n)$ using 1D array.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **10. Regular Expression Matching** (this problem) - DP with wildcards
2. **44. Wildcard Matching** - Similar with different wildcard rules
3. **72. Edit Distance** - DP for string transformation
4. **97. Interleaving String** - DP for string combination
5. **115. Distinct Subsequences** - DP for subsequence counting
6. **1143. Longest Common Subsequence** - DP for LCS
7. **583. Delete Operation for Two Strings** - DP for string deletion
8. **712. Minimum ASCII Delete Sum for Two Strings** - DP with costs
9. **1312. Minimum Insertion Steps to Make a String Palindrome** - DP for palindrome
10. **516. Longest Palindromic Subsequence** - DP for palindrome

