# Edit Distance

## Problem Description

**Problem Link:** [Edit Distance](https://leetcode.com/problems/edit-distance/)

Given `word1` and `word2`, return minimum operations to convert `word1` to `word2`.
Allowed operations: insert, delete, replace.

## Intuition/Main Idea

This extends LCS-style 2D DP.

### Subproblem definition
`dist(i, j)` = minimum edits to convert suffix `word1[i..]` into `word2[j..]`.

### State transition
If chars match:
- `dist(i, j) = dist(i+1, j+1)`

Else consider 3 operations:
1. Insert: `1 + dist(i, j+1)`
2. Delete: `1 + dist(i+1, j)`
3. Replace: `1 + dist(i+1, j+1)`

Take minimum.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Insert/delete/replace operations | three transition branches |
| Min operations required | `Math.min(...)` over choices |
| End-of-string base cases | remaining length when one string exhausted |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int minDistance(String word1, String word2) {
        // Size len1 x len2 because states are suffix starts i,j inside both strings.
        Integer[][] memo = new Integer[word1.length()][word2.length()];
        return dfs(0, 0, word1, word2, memo);
    }

    private int dfs(int i, int j, String a, String b, Integer[][] memo) {
        if (i == a.length()) {
            return b.length() - j;
        }
        if (j == b.length()) {
            return a.length() - i;
        }
        if (memo[i][j] != null) {
            return memo[i][j];
        }

        if (a.charAt(i) == b.charAt(j)) {
            memo[i][j] = dfs(i + 1, j + 1, a, b, memo);
        } else {
            int insertCost = 1 + dfs(i, j + 1, a, b, memo);
            int deleteCost = 1 + dfs(i + 1, j, a, b, memo);
            int replaceCost = 1 + dfs(i + 1, j + 1, a, b, memo);
            memo[i][j] = Math.min(insertCost, Math.min(deleteCost, replaceCost));
        }

        return memo[i][j];
    }
}
```

### Bottom-Up Version

Bottom-up is often preferred in interviews because transitions are explicit in table form.

```java
class Solution {
    public int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        // Size (len1+1) x (len2+1) to include empty suffix states.
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][len2] = len1 - i;
        }
        for (int j = 0; j <= len2; j++) {
            dp[len1][j] = len2 - j;
        }

        for (int i = len1 - 1; i >= 0; i--) {
            for (int j = len2 - 1; j >= 0; j--) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    dp[i][j] = dp[i + 1][j + 1];
                } else {
                    int insertCost = 1 + dp[i][j + 1];
                    int deleteCost = 1 + dp[i + 1][j];
                    int replaceCost = 1 + dp[i + 1][j + 1];
                    dp[i][j] = Math.min(insertCost, Math.min(deleteCost, replaceCost));
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

1. [1143. Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/)
2. [583. Delete Operation for Two Strings](https://leetcode.com/problems/delete-operation-for-two-strings/)
