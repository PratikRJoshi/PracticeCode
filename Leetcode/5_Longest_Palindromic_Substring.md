# Longest Palindromic Substring

## Problem Description

**Problem Link:** [Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)

Given a string `s`, return *the longest palindromic substring* in `s`.

**Example 1:**
```
Input: s = "babad"
Output: "bab"
Explanation: "aba" is also a valid answer.
```

**Example 2:**
```
Input: s = "cbbd"
Output: "bb"
```

**Constraints:**
- `1 <= s.length <= 1000`
- `s` consist of only digits and English letters.

## Intuition/Main Idea

We need to find the longest palindromic substring. There are two approaches:

**Approach 1: Expand Around Centers**
- For each possible center, expand outward to find longest palindrome
- Handle odd-length (center at char) and even-length (center between chars)

**Approach 2: Dynamic Programming**
- `dp[i][j]` = true if substring s[i...j] is palindrome
- Base case: single char is palindrome
- Recurrence: `dp[i][j] = (s[i] == s[j]) && dp[i+1][j-1]`

**Why DP:** We can build solution from smaller subproblems. If we know s[i+1...j-1] is palindrome, we can check if s[i...j] is palindrome by comparing s[i] and s[j].

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find longest palindrome | DP table - Lines 8-30 |
| Check palindrome | DP recurrence - Line 20 |
| Track longest | Length and start tracking - Lines 22-25 |

## Final Java Code & Learning Pattern (Full Content)

### Bottom-Up Approach

```java
class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();
        if (n == 0) return "";
        
        // dp[i][j] = true if substring s[i...j] is palindrome
        // Size: n x n to cover all possible substrings
        boolean[][] dp = new boolean[n][n];
        
        int start = 0;
        int maxLen = 1;
        
        // Base case: every single character is a palindrome
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        
        // Check for palindromes of length 2
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLen = 2;
            }
        }
        
        // Check for palindromes of length 3 and more
        // We iterate by length, starting from length 3
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                
                // s[i...j] is palindrome if:
                // 1. s[i] == s[j] (ends match)
                // 2. s[i+1...j-1] is palindrome (inner substring is palindrome)
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    start = i;
                    maxLen = len;
                }
            }
        }
        
        return s.substring(start, start + maxLen);
    }
}
```

### Top-Down Approach (Memoization)

```java
class Solution {
    private Boolean[][] memo;
    
    public String longestPalindrome(String s) {
        int n = s.length();
        memo = new Boolean[n][n];
        
        int start = 0;
        int maxLen = 1;
        
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isPalindrome(s, i, j) && j - i + 1 > maxLen) {
                    start = i;
                    maxLen = j - i + 1;
                }
            }
        }
        
        return s.substring(start, start + maxLen);
    }
    
    private boolean isPalindrome(String s, int i, int j) {
        if (i >= j) return true;
        
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        memo[i][j] = (s.charAt(i) == s.charAt(j)) && isPalindrome(s, i + 1, j - 1);
        return memo[i][j];
    }
}
```

## Dynamic Programming Analysis

**Intuition behind generating subproblems:**
- Subproblem: Is substring s[i...j] a palindrome?
- If we know s[i+1...j-1] is palindrome, we only need to check if s[i] == s[j]
- Build from smaller substrings to larger ones

**Top-down / Memoized version:**
- Uses recursion with memoization
- Checks if substring is palindrome recursively
- Memoizes results to avoid recomputation

**Bottom-up version:**
- Iterates by substring length
- Fills DP table from smaller to larger lengths
- More efficient as it avoids recursion overhead

**DP array size allocation:**
- Size: `n x n` where n is string length
- Reason: Need to check all possible substrings s[i...j] where 0 <= i <= j < n
- This covers all possible palindromic substrings

## Complexity Analysis

**Time Complexity:** 
- Bottom-up: $O(n^2)$ where $n$ is string length. We check all substrings.
- Top-down: $O(n^2)$ with memoization.

**Space Complexity:** $O(n^2)$ for the DP table/memo.

## Similar Problems

- [Palindromic Substrings](https://leetcode.com/problems/palindromic-substrings/) - Count all palindromic substrings
- [Longest Palindromic Subsequence](https://leetcode.com/problems/longest-palindromic-subsequence/) - Subsequence variant
- [Valid Palindrome](https://leetcode.com/problems/valid-palindrome/) - Check if string is palindrome

