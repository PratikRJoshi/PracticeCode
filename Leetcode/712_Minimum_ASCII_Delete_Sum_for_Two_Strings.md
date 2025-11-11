# Minimum ASCII Delete Sum for Two Strings

## Problem Description

**Problem Link:** [Minimum ASCII Delete Sum for Two Strings](https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/)

Given two strings `s1` and `s2`, return *the lowest **ASCII** sum of deleted characters to make two strings equal*.

**Example 1:**
```
Input: s1 = "sea", s2 = "eat"
Output: 231
Explanation: Deleting "s" from "sea" adds 115 to the sum.
Deleting "t" from "eat" adds 116 to the sum.
At the end, both strings are equal, and 115 + 116 = 231 is the minimum sum possible.
```

**Example 2:**
```
Input: s1 = "delete", s2 = "leet"
Output: 403
Explanation: Deleting "dee" from "delete" to turn the string into "let",
adds 100[d]+101[e]+101[e] = 302 to the sum. Deleting "e" from "leet" adds 101[e] to the sum.
At the end, both strings equal to "let", and 115+116 = 231 is the minimum sum possible.
```

**Constraints:**
- `1 <= s1.length, s2.length <= 1000`
- `s1` and `s2` consist of lowercase English letters.

## Intuition/Main Idea

This is similar to **Edit Distance** but with ASCII costs. We want to find the minimum ASCII sum of characters to delete to make both strings equal.

**Core Algorithm:**
1. Find the **Longest Common Subsequence (LCS)** of the two strings.
2. Delete all characters not in the LCS.
3. The answer is `totalASCII(s1) + totalASCII(s2) - 2 * ASCII(LCS)`.

**Alternative DP Approach:**
1. Use DP where `dp[i][j]` = minimum ASCII delete sum for `s1[0..i-1]` and `s2[0..j-1]`.
2. If `s1[i-1] == s2[j-1]`, no deletion needed: `dp[i][j] = dp[i-1][j-1]`.
3. Otherwise, delete from one string: `dp[i][j] = min(s1[i-1] + dp[i-1][j], s2[j-1] + dp[i][j-1])`.

**Why DP works:** The problem has overlapping subproblems. We can build the solution bottom-up by considering prefixes of both strings.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| DP table | 2D DP array - Line 6 |
| Base cases | First row/column - Lines 8-13 |
| Characters match | Match case - Line 17 |
| Characters don't match | Delete case - Line 19 |
| Return result | Return statement - Line 21 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int minimumDeleteSum(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        Integer[][] memo = new Integer[m + 1][n + 1];
        return minDeleteSum(s1, s2, m, n, memo);
    }
    
    private int minDeleteSum(String s1, String s2, int i, int j, Integer[][] memo) {
        // Base case: both strings exhausted
        if (i == 0 && j == 0) {
            return 0;
        }
        
        // Base case: s1 exhausted, delete all remaining in s2
        if (i == 0) {
            int sum = 0;
            for (int k = 0; k < j; k++) {
                sum += s2.charAt(k);
            }
            return sum;
        }
        
        // Base case: s2 exhausted, delete all remaining in s1
        if (j == 0) {
            int sum = 0;
            for (int k = 0; k < i; k++) {
                sum += s1.charAt(k);
            }
            return sum;
        }
        
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        int result;
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            // Characters match, no deletion needed
            result = minDeleteSum(s1, s2, i - 1, j - 1, memo);
        } else {
            // Delete from s1 or s2, choose minimum
            int deleteFromS1 = s1.charAt(i - 1) + minDeleteSum(s1, s2, i - 1, j, memo);
            int deleteFromS2 = s2.charAt(j - 1) + minDeleteSum(s1, s2, i, j - 1, memo);
            result = Math.min(deleteFromS1, deleteFromS2);
        }
        
        memo[i][j] = result;
        return result;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int minimumDeleteSum(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        
        // DP: dp[i][j] = minimum ASCII delete sum for s1[0..i-1] and s2[0..j-1]
        int[][] dp = new int[m + 1][n + 1];
        
        // Base case: delete all characters from s1 (empty s1)
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] + s1.charAt(i - 1);
        }
        
        // Base case: delete all characters from s2 (empty s2)
        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] + s2.charAt(j - 1);
        }
        
        // Fill DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // Characters match, no deletion needed
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Delete from s1 or s2, choose minimum
                    dp[i][j] = Math.min(
                        s1.charAt(i - 1) + dp[i - 1][j],
                        s2.charAt(j - 1) + dp[i][j - 1]
                    );
                }
            }
        }
        
        return dp[m][n];
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Cases (Lines 8-13):** 
   - **First Row:** Delete all characters from `s1` to match empty `s2`.
   - **First Column:** Delete all characters from `s2` to match empty `s1`.

2. **DP Transition (Lines 15-21):**
   - **Match Case (Line 17):** If characters match, no deletion needed: `dp[i][j] = dp[i-1][j-1]`.
   - **No Match Case (Line 19):** Delete from either string:
     - Delete from `s1`: `s1[i-1] + dp[i-1][j]`
     - Delete from `s2`: `s2[j-1] + dp[i][j-1]`
     - Take the minimum.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the minimum ASCII delete sum for `s1[0..i-1]` and `s2[0..j-1]`?"
- **Why this works:** To make `s1[0..i]` and `s2[0..j]` equal:
  - If `s1[i] == s2[j]`, no deletion needed, use `dp[i-1][j-1]`.
  - Otherwise, delete from one string and use the optimal solution for the remaining.
- **Overlapping subproblems:** Multiple prefixes share the same optimal subproblems.

**Example walkthrough for `s1 = "sea", s2 = "eat"`:**
- dp[0][0] = 0
- dp[1][0] = 115 (delete 's')
- dp[0][1] = 101 (delete 'e')
- dp[1][1] = s1[0]=='s', s2[0]=='e' → min(115+dp[0][1], 101+dp[1][0]) = min(216, 216) = 216
- Continue...
- Result: 231 ✓

## Complexity Analysis

- **Time Complexity:** $O(m \times n)$ where $m$ and $n$ are the lengths of `s1` and `s2`. We fill a 2D DP table.

- **Space Complexity:** $O(m \times n)$ for the DP table. Can be optimized to $O(\min(m, n))$ using 1D array.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **712. Minimum ASCII Delete Sum for Two Strings** (this problem) - Edit distance with costs
2. **583. Delete Operation for Two Strings** - Minimum deletions
3. **72. Edit Distance** - Edit distance
4. **1143. Longest Common Subsequence** - LCS
5. **115. Distinct Subsequences** - Count subsequences
6. **97. Interleaving String** - String interleaving
7. **10. Regular Expression Matching** - Pattern matching
8. **44. Wildcard Matching** - Wildcard matching

