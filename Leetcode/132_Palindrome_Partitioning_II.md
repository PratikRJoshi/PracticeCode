# Palindrome Partitioning II

## Problem Description

**Problem Link:** [Palindrome Partitioning II](https://leetcode.com/problems/palindrome-partitioning-ii/)

Given a string `s`, partition `s` such that every substring of the partition is a palindrome.

Return *the **minimum cuts** needed for a palindrome partitioning of `s`*.

**Example 1:**
```
Input: s = "aab"
Output: 1
Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.
```

**Example 2:**
```
Input: s = "a"
Output: 0
```

**Example 3:**
```
Input: s = "ab"
Output: 1
```

**Constraints:**
- `1 <= s.length <= 2000`
- `s` consists of lowercase English letters only.

## Intuition/Main Idea

This is a **dynamic programming** problem. We need to find the minimum number of cuts to partition the string into palindromes.

**Core Algorithm:**
1. Precompute which substrings are palindromes using DP.
2. Use DP where `cuts[i]` = minimum cuts needed for `s[0..i-1]`.
3. For each position `i`, try all possible palindrome endings at `i`.
4. `cuts[i] = min(cuts[j] + 1)` for all `j` where `s[j..i-1]` is a palindrome.

**Why DP works:** The problem has overlapping subproblems - finding minimum cuts for prefixes is needed multiple times. We can build the solution bottom-up.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Precompute palindromes | Palindrome DP - Lines 7-15 |
| DP for minimum cuts | Cuts array - Line 17 |
| Initialize cuts | Arrays.fill - Line 18 |
| Try all palindrome endings | Nested loops - Lines 20-25 |
| Update minimum cuts | Cuts update - Line 24 |
| Return result | Return statement - Line 26 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int minCut(String s) {
        int n = s.length();
        boolean[][] isPalindrome = precomputePalindromes(s);
        Integer[] memo = new Integer[n];
        return minCuts(s, 0, isPalindrome, memo);
    }
    
    private int minCuts(String s, int start, boolean[][] isPalindrome, Integer[] memo) {
        if (start == s.length()) {
            return -1;  // No cuts needed for empty string
        }
        
        if (memo[start] != null) {
            return memo[start];
        }
        
        int min = Integer.MAX_VALUE;
        for (int end = start; end < s.length(); end++) {
            if (isPalindrome[start][end]) {
                min = Math.min(min, 1 + minCuts(s, end + 1, isPalindrome, memo));
            }
        }
        
        memo[start] = min;
        return min;
    }
    
    private boolean[][] precomputePalindromes(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    dp[i][j] = true;
                } else if (j == i + 1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i + 1][j - 1];
                }
            }
        }
        
        return dp;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int minCut(String s) {
        int n = s.length();
        
        // Precompute palindromes: isPalindrome[i][j] = is s[i..j] a palindrome
        boolean[][] isPalindrome = new boolean[n][n];
        
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    isPalindrome[i][j] = true;
                } else if (j == i + 1) {
                    isPalindrome[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    isPalindrome[i][j] = (s.charAt(i) == s.charAt(j)) && isPalindrome[i + 1][j - 1];
                }
            }
        }
        
        // DP: cuts[i] = minimum cuts needed for s[0..i-1]
        int[] cuts = new int[n];
        Arrays.fill(cuts, Integer.MAX_VALUE);
        
        for (int i = 0; i < n; i++) {
            // If s[0..i] is a palindrome, no cuts needed
            if (isPalindrome[0][i]) {
                cuts[i] = 0;
            } else {
                // Try all possible palindrome endings at i
                for (int j = 0; j < i; j++) {
                    if (isPalindrome[j + 1][i]) {
                        cuts[i] = Math.min(cuts[i], cuts[j] + 1);
                    }
                }
            }
        }
        
        return cuts[n - 1];
    }
}
```

**Explanation of Key Code Sections:**

1. **Precompute Palindromes (Lines 7-15):** Use DP to precompute which substrings are palindromes. This avoids recomputing during the main DP.

2. **Cuts DP (Lines 17-25):** 
   - **Base Case (Lines 20-22):** If `s[0..i]` is a palindrome, no cuts needed: `cuts[i] = 0`.
   - **Transition (Lines 23-25):** For each position `j < i`, if `s[j+1..i]` is a palindrome, we can cut after `j`: `cuts[i] = min(cuts[i], cuts[j] + 1)`.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the minimum cuts needed for `s[0..i-1]`?"
- **Why this works:** To partition `s[0..i]`, we can end a palindrome at position `i`. If we end it at `j+1`, we need `cuts[j] + 1` total cuts.
- **Overlapping subproblems:** Multiple positions may check the same optimal cuts.

**Example walkthrough for `s = "aab"`:**
- isPalindrome: [0,0]=T, [0,1]=F, [0,2]=F, [1,1]=T, [1,2]=T, [2,2]=T
- cuts[0] = 0 (palindrome "a")
- cuts[1] = min(0+1) = 1 (cut after 0, "a"|"a")
- cuts[2] = min(0+1) = 1 (cut after 0, "aa"|"b")
- Result: 1 ✓

## Complexity Analysis

- **Time Complexity:** $O(n^2)$ where $n$ is the length of `s`. We precompute palindromes in $O(n^2)$ and compute cuts in $O(n^2)$.

- **Space Complexity:** $O(n^2)$ for the palindrome table and $O(n)$ for the cuts array.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **132. Palindrome Partitioning II** (this problem) - Minimum cuts DP
2. **131. Palindrome Partitioning** - Find all partitions (backtracking)
3. **647. Palindromic Substrings** - Count palindromes
4. **5. Longest Palindromic Substring** - Find longest palindrome
5. **516. Longest Palindromic Subsequence** - LCS variant
6. **1216. Valid Palindrome III** - Palindrome with deletions
7. **1312. Minimum Insertion Steps to Make a String Palindrome** - Minimum insertions

