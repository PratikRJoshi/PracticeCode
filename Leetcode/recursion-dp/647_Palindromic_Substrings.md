# Palindromic Substrings

## Problem Description

**Problem Link:** [Palindromic Substrings](https://leetcode.com/problems/palindromic-substrings/)

Given a string `s`, return the number of palindromic substrings in it.

## Intuition/Main Idea

A substring is palindrome if outer chars match and inner substring is also palindrome.

### Subproblem definition
`isPalindrome(left, right)` = whether `s[left..right]` is palindrome.

### State transition
`isPalindrome(left, right)` is true if:
- `s[left] == s[right]`, and
- `(right - left <= 2 || isPalindrome(left + 1, right - 1))`

Count all `(left, right)` pairs that are palindrome.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Count all palindromic substrings | double loop over all start/end pairs |
| Validate palindrome efficiently | memoized palindrome check |
| Single chars are palindromes | base condition `left >= right` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int countSubstrings(String s) {
        int n = s.length();
        // Size n x n because states are substring boundaries [left][right].
        Boolean[][] memo = new Boolean[n][n];

        int count = 0;
        for (int left = 0; left < n; left++) {
            for (int right = left; right < n; right++) {
                if (isPalindrome(left, right, s, memo)) {
                    count++;
                }
            }
        }

        return count;
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

Bottom-up is often simpler for palindrome substring DP.

```java
class Solution {
    public int countSubstrings(String s) {
        int n = s.length();

        // Size n x n because dp[left][right] represents substring s[left..right].
        boolean[][] dp = new boolean[n][n];
        int count = 0;

        for (int right = 0; right < n; right++) {
            for (int left = 0; left <= right; left++) {
                if (s.charAt(left) == s.charAt(right)
                        && (right - left <= 2 || dp[left + 1][right - 1])) {
                    dp[left][right] = true;
                    count++;
                }
            }
        }

        return count;
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n^2)$
- **Space Complexity:** $O(n^2)$

## Similar Problems

1. [5. Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)
2. [516. Longest Palindromic Subsequence](https://leetcode.com/problems/longest-palindromic-subsequence/)
