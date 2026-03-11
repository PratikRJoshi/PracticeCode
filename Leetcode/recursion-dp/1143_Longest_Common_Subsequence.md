# Longest Common Subsequence

## Problem Description

**Problem Link:** [Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/)

Given two strings `text1` and `text2`, return length of their longest common subsequence.

## Intuition/Main Idea

### Subproblem definition
`lcs(i, j)` = LCS length between suffixes `text1[i..]` and `text2[j..]`.

### State transition
- If chars match: `1 + lcs(i+1, j+1)`
- Else: `max(lcs(i+1, j), lcs(i, j+1))`

This works because first mismatch forces us to skip one side and try best option.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Compare two strings char by char | `text1.charAt(i)` vs `text2.charAt(j)` |
| Build optimal subsequence length | match adds 1, mismatch uses max |
| Handle end of either string | base case when `i==len1` or `j==len2` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        // Size len1 x len2 because valid i,j states are 0..len1-1 and 0..len2-1.
        Integer[][] memo = new Integer[text1.length()][text2.length()];
        return dfs(0, 0, text1, text2, memo);
    }

    private int dfs(int i, int j, String a, String b, Integer[][] memo) {
        if (i == a.length() || j == b.length()) {
            return 0;
        }
        if (memo[i][j] != null) {
            return memo[i][j];
        }

        if (a.charAt(i) == b.charAt(j)) {
            memo[i][j] = 1 + dfs(i + 1, j + 1, a, b, memo);
        } else {
            memo[i][j] = Math.max(dfs(i + 1, j, a, b, memo), dfs(i, j + 1, a, b, memo));
        }

        return memo[i][j];
    }
}
```

### Bottom-Up Version

Bottom-up avoids recursion and gives straightforward 2D table visualization.

```java
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();

        // Size (len1+1) x (len2+1) to include empty suffix states.
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = len1 - 1; i >= 0; i--) {
            for (int j = len2 - 1; j >= 0; j--) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i][j] = 1 + dp[i + 1][j + 1];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }

        return dp[0][0];
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(mn)$
- **Space Complexity:** $O(mn)$

## Similar Problems

1. [72. Edit Distance](https://leetcode.com/problems/edit-distance/)
2. [583. Delete Operation for Two Strings](https://leetcode.com/problems/delete-operation-for-two-strings/)
