# Distinct Subsequences

## Problem Description

**Problem Link:** [Distinct Subsequences](https://leetcode.com/problems/distinct-subsequences/)

Given strings `s` and `t`, return number of distinct subsequences of `s` which equal `t`.

## Intuition/Main Idea

2D DP counting pattern.

### Subproblem definition
`count(i, j)` = number of ways `t[j..]` can be formed from `s[i..]`.

### State transition
If `s[i] == t[j]`:
- use this char: `count(i+1, j+1)`
- skip this char: `count(i+1, j)`

Else:
- only skip: `count(i+1, j)`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Count all valid subsequences | DP stores counts, not booleans |
| Match target exactly | base `j == t.length()` returns 1 |
| Ignore extra chars in source | skip transition `i+1` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int numDistinct(String s, String t) {
        // Size s.length() x t.length() because state is (i,j) inside both strings.
        Integer[][] memo = new Integer[s.length()][t.length()];
        return dfs(0, 0, s, t, memo);
    }

    private int dfs(int i, int j, String s, String t, Integer[][] memo) {
        if (j == t.length()) {
            return 1;
        }
        if (i == s.length()) {
            return 0;
        }
        if (memo[i][j] != null) {
            return memo[i][j];
        }

        int ways = dfs(i + 1, j, s, t, memo);
        if (s.charAt(i) == t.charAt(j)) {
            ways += dfs(i + 1, j + 1, s, t, memo);
        }

        memo[i][j] = ways;
        return memo[i][j];
    }
}
```

### Bottom-Up Version

Bottom-up is robust and avoids recursion overhead.

```java
class Solution {
    public int numDistinct(String s, String t) {
        int m = s.length();
        int n = t.length();

        // Size (m+1) x (n+1) to include empty suffix states.
        long[][] dp = new long[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][n] = 1;
        }

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                dp[i][j] = dp[i + 1][j];
                if (s.charAt(i) == t.charAt(j)) {
                    dp[i][j] += dp[i + 1][j + 1];
                }
            }
        }

        return (int) dp[0][0];
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(mn)$
- **Space Complexity:** $O(mn)$

## Similar Problems

1. [1143. Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/)
2. [97. Interleaving String](https://leetcode.com/problems/interleaving-string/)
