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

This problem requires matching a string against a pattern with special characters. The key insight is to use dynamic programming to break down the problem into smaller subproblems.

The main challenge is handling the `'*'` wildcard, which can match zero or more of the preceding character. This creates multiple possible matching paths that we need to explore.

For example, with pattern `"a*"`, we can match:
- Zero 'a's (empty string)
- One 'a' ("a")
- Multiple 'a's ("aa", "aaa", etc.)

We need to systematically check all these possibilities, which is where dynamic programming helps us avoid redundant work.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Handle empty string and pattern | Base case handling - Lines 9-17 |
| Match any single character with '.' | Dot character handling - Lines 36-38 |
| Match zero instances with '*' | Zero match handling - Lines 25-26 |
| Match multiple instances with '*' | Multiple match handling - Lines 29-31 |
| Exact character matching | Character comparison - Lines 40-42 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public boolean isMatch(String s, String p) {
        // Create memoization array to store results of subproblems
        // Size is [s.length()+1][p.length()+1] because:
        // - We need to handle empty string cases (i=0 or j=0)
        // - Indices i and j can go up to s.length() and p.length() respectively
        // - This allows us to represent all possible subproblems from (0,0) to (s.length(), p.length())
        Boolean[][] memo = new Boolean[s.length() + 1][p.length() + 1];
        return dp(0, 0, s, p, memo);
    }
    
    private boolean dp(int i, int j, String s, String p, Boolean[][] memo) {
        // If we've already computed this state, return the cached result
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        boolean result;
        
        // Base case: if pattern is exhausted
        if (j == p.length()) {
            // Success only if string is also exhausted
            // Otherwise, we have leftover characters in s that can't be matched
            return i == s.length();
        } else {
            // Check if current characters match
            boolean firstMatch = (i < s.length() && 
                                 (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.'));
            
            // If next character is '*', we have two options:
            if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                // Option 1: Skip the pattern with '*' (match zero instances)
                result = dp(i, j + 2, s, p, memo) || 
                         // Option 2: Match current character and try to match more
                         (firstMatch && dp(i + 1, j, s, p, memo));
            } else {
                // Regular matching: current characters must match and continue
                result = firstMatch && dp(i + 1, j + 1, s, p, memo);
            }
        }
        
        // Cache the result
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

        // dp[i][j] = does s[0...i-1] match p[0...j-1]
        // Size is [m+1][n+1] because:
        // - dp[0][0] represents empty string matching empty pattern
        // - We need positions for all characters plus the empty string case
        // - This allows us to build solutions from empty strings up to full strings
        boolean[][] dp = new boolean[m + 1][n + 1];
        boolean[][] dp = new boolean[m + 1][n + 1];
        
        // Base case: empty pattern matches empty string
        dp[0][0] = true;
        
        // Handle patterns that can match empty string (like a*, a*b*, etc.)
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];  // Skip the pattern with '*'
            }
        }
        
        // Fill the dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char pChar = p.charAt(j - 1);
                char sChar = s.charAt(i - 1);
                
                if (pChar == '*') {
                    // Skip the pattern with '*' (match zero instances)
                    dp[i][j] = dp[i][j - 2];
                    
                    // Check if preceding character matches current string character
                    char prevPattern = p.charAt(j - 2);
                    if (prevPattern == '.' || prevPattern == sChar) {
                        // Match one or more instances
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else if (pChar == '.' || pChar == sChar) {
                    // Current characters match
                    dp[i][j] = dp[i - 1][j - 1];
                }
                // else: characters don't match, dp[i][j] remains false
            }
        }
        
        return dp[m][n];
    }
}
```

## Complexity Analysis

- **Time Complexity**: $O(m \times n)$ where $m$ is the length of string `s` and $n` is the length of pattern `p`. We need to fill a table of size m×n.
  
- **Space Complexity**: $O(m \times n)$ for the DP table or memoization array.

## Similar Problems

1. [44. Wildcard Matching](https://leetcode.com/problems/wildcard-matching/) - Similar pattern matching with different wildcard rules
2. [97. Interleaving String](https://leetcode.com/problems/interleaving-string/) - Another string pattern matching problem using DP
3. [72. Edit Distance](https://leetcode.com/problems/edit-distance/) - DP for string transformation
4. [115. Distinct Subsequences](https://leetcode.com/problems/distinct-subsequences/) - DP for subsequence counting
5. [1143. Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/) - Classic DP string problem
6. [583. Delete Operation for Two Strings](https://leetcode.com/problems/delete-operation-for-two-strings/) - DP for string operations
7. [712. Minimum ASCII Delete Sum for Two Strings](https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/) - DP with costs
8. [1312. Minimum Insertion Steps to Make a String Palindrome](https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/) - DP for palindrome construction
9. [516. Longest Palindromic Subsequence](https://leetcode.com/problems/longest-palindromic-subsequence/) - DP for palindrome finding
10. [22. Generate Parentheses](https://leetcode.com/problems/generate-parentheses/) - Another pattern matching problem
