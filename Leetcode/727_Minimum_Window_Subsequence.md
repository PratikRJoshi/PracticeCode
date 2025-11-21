# Minimum Window Subsequence

## Problem Description

**Problem Link:** [Minimum Window Subsequence](https://leetcode.com/problems/minimum-window-subsequence/)

Given strings `s1` and `s2`, return *the **minimum (contiguous) substring** part of* `s1`*, so that* `s2` *is a subsequence of the part*.

If there is no such window in `s1` that covers all characters in `s2`, return the empty string `""`. If there are multiple such minimum-length windows, return the one with the **left-most starting index**.

**Example 1:**
```
Input: s1 = "abcdebdde", s2 = "bde"
Output: "bcde"
Explanation: "bcde" is the answer because it occurs before "bdde" which has the same length.
```

**Constraints:**
- `1 <= s1.length <= 2 * 10^4`
- `1 <= s2.length <= 100`
- `s1` and `s2` consist of lowercase English letters.

## Intuition/Main Idea

We need to find the shortest substring of s1 that contains s2 as a subsequence.

**Core Algorithm:**
- Use DP: `dp[i][j]` = start index of minimum window ending at s1[i] that contains s2[0...j]
- If `s1[i] == s2[j]`, we can extend the subsequence
- Track minimum window length

**Why DP:** We need to track subsequence matching and find minimum window. DP helps track the best starting position for each state.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find minimum window | DP table - Lines 8-30 |
| Match subsequence | Character matching - Lines 15-20 |
| Track window start | Start index tracking - Lines 12, 19 |

## Final Java Code & Learning Pattern (Full Content)

### Bottom-Up Approach

```java
class Solution {
    public String minWindow(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        
        // dp[i][j] = start index of minimum window in s1[0...i] 
        // that contains s2[0...j] as subsequence
        // Size: (m+1) x (n+1) to handle empty strings
        int[][] dp = new int[m + 1][n + 1];
        
        // Initialize: for empty s2, window starts at current position
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        
        // Initialize: for empty s1, no valid window
        for (int j = 1; j <= n; j++) {
            dp[0][j] = -1;
        }
        
        int minLen = Integer.MAX_VALUE;
        int start = -1;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // Characters match: extend subsequence
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Characters don't match: continue from previous position
                    dp[i][j] = dp[i - 1][j];
                }
            }
            
            // If we've matched entire s2, check if this is minimum window
            if (dp[i][n] != -1) {
                int len = i - dp[i][n];
                if (len < minLen) {
                    minLen = len;
                    start = dp[i][n];
                }
            }
        }
        
        return start == -1 ? "" : s1.substring(start, start + minLen);
    }
}
```

## Dynamic Programming Analysis

**Intuition behind generating subproblems:**
- Subproblem: What's the start index of minimum window in s1[0...i] containing s2[0...j]?
- If characters match, we extend the subsequence
- If not, we continue from previous position

**DP array size allocation:**
- Size: `(m+1) x (n+1)` where m = s1.length, n = s2.length
- Reason: Need to handle empty strings and track start indices for all positions
- +1 for base cases (empty strings)

## Complexity Analysis

**Time Complexity:** $O(m \times n)$ where $m$ is s1 length and $n$ is s2 length.

**Space Complexity:** $O(m \times n)$ for DP table. Can be optimized to $O(n)$.

## Similar Problems

- [Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/) - Similar window problem
- [Is Subsequence](https://leetcode.com/problems/is-subsequence/) - Check subsequence
- [Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/) - Similar DP pattern

