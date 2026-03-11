# Longest Palindromic Substring

## Problem Description

**Problem Link:** [Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)

Given string `s`, return the longest palindromic substring.

## Intuition/Main Idea

Same palindrome-state idea as problem 647. Instead of counting all palindromes, keep the longest interval.

### Subproblem definition
`isPalindrome(left, right)` = whether `s[left..right]` is palindrome.

### State transition
`isPalindrome(left, right)` true iff:
- `s[left] == s[right]`
- and `(right - left <= 2 || isPalindrome(left + 1, right - 1))`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Return longest palindromic substring | update best start/length when palindrome found |
| Validate substrings efficiently | DP/memo palindrome relation |
| Handle all possible centers/ranges | nested loops over `left` and `right` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();
        // Size n x n for substring boundary states.
        Boolean[][] memo = new Boolean[n][n];

        int bestStart = 0;
        int bestLength = 1;

        for (int left = 0; left < n; left++) {
            for (int right = left; right < n; right++) {
                if (isPalindrome(left, right, s, memo)) {
                    int length = right - left + 1;
                    if (length > bestLength) {
                        bestLength = length;
                        bestStart = left;
                    }
                }
            }
        }

        return s.substring(bestStart, bestStart + bestLength);
    }

    private boolean isPalindrome(int left, int right, String s, Boolean[][] memo) {
        if (left >= right) {
            return true;
        }
        if (memo[left][right] != null) {
            return memo[left][right];
        }

        memo[left][right] = s.charAt(left) == s.charAt(right)
                && isPalindrome(left + 1, right - 1, s, memo);
        return memo[left][right];
    }
}
```

### Bottom-Up Version

Bottom-up is usually cleaner and avoids recursive checks.

```java
class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();

        // Size n x n because dp[left][right] represents substring s[left..right].
        boolean[][] dp = new boolean[n][n];

        int bestStart = 0;
        int bestLength = 1;

        for (int right = 0; right < n; right++) {
            for (int left = 0; left <= right; left++) {
                if (s.charAt(left) == s.charAt(right)
                        && (right - left <= 2 || dp[left + 1][right - 1])) {
                    dp[left][right] = true;

                    int length = right - left + 1;
                    if (length > bestLength) {
                        bestLength = length;
                        bestStart = left;
                    }
                }
            }
        }

        return s.substring(bestStart, bestStart + bestLength);
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n^2)$
- **Space Complexity:** $O(n^2)$

## Similar Problems

1. [647. Palindromic Substrings](https://leetcode.com/problems/palindromic-substrings/)
2. [131. Palindrome Partitioning](https://leetcode.com/problems/palindrome-partitioning/)
