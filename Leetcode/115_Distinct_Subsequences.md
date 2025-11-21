# Distinct Subsequences

## Problem Description

**Problem Link:** [Distinct Subsequences](https://leetcode.com/problems/distinct-subsequences/)

Given two strings `s` and `t`, return *the number of distinct subsequences of `s` which equals `t`*.

The test cases are generated so that the answer fits on a **32-bit signed integer**.

**Example 1:**
```
Input: s = "rabbbit", t = "rabbit"
Output: 3
Explanation:
As shown below, there are 3 ways you can generate "rabbit" from "rabbbit".
rabbbit
rabbbit
rabbbit
```

**Example 2:**
```
Input: s = "babgbag", t = "bag"
Output: 5
Explanation:
As shown below, there are 5 ways you can generate "bag" from "babgbag".
babgbag
babgbag
babgbag
babgbag
babgbag
```

**Constraints:**
- `1 <= s.length, t.length <= 1000`
- `s` and `t` consist of English letters.

## Intuition/Main Idea

This is a **dynamic programming** problem for counting subsequences. We need to count how many distinct ways we can form string `t` as a subsequence of string `s`.

**Core Insight:**
- For each character in `s`, we have a choice: **use it** or **skip it** to form `t`
- If `s[i]` matches `t[j]`, we can either:
  - **Use it**: Match `s[i]` with `t[j]` and continue matching the rest
  - **Skip it**: Don't use `s[i]` and try to match `t[j]` with later characters in `s`
- If `s[i]` doesn't match `t[j]`, we **must skip** `s[i]`

**Why DP works:**
- **Overlapping subproblems**: The same subproblem (forming `t[j:]` from `s[i:]`) appears multiple times in the recursion tree
- **Optimal substructure**: The solution to a larger problem depends on solutions to smaller subproblems
- We can memoize results to avoid recalculating the same subproblems

**Key Subproblem Definition:**
- `dp(i, j)` = number of ways to form `t[j:]` using `s[i:]`
- We process from left to right (0th index to last index) in both strings

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count distinct subsequences | DP function return value - Top-down: Line 78, Bottom-up: Line 142 |
| Handle empty string `t` (base case) | Base case check - Top-down: Line 78-80, Bottom-up: Line 123-125 |
| Handle empty string `s` (base case) | Base case check - Top-down: Line 83-85 |
| Characters match - use or skip | Match case logic - Top-down: Line 95-99, Bottom-up: Line 131-134 |
| Characters don't match - must skip | No match case logic - Top-down: Line 100-102, Bottom-up: Line 135-137 |
| Memoization to avoid recomputation | Memo array check and store - Top-down: Lines 88-90, 105-106 |
| DP table initialization | DP array allocation - Top-down: Line 72, Bottom-up: Line 120 |

## Final Java Code & Learning Pattern

### Intuition Behind Generating Subproblems (Top-Down Approach)

**Subproblem Definition:**
- `dp(i, j)` = number of ways to form `t[j..n-1]` (remaining part of `t` starting from index `j`) using `s[i..m-1]` (remaining part of `s` starting from index `i`)

**Why this subproblem works:**
1. **Natural progression**: We process both strings from left to right (0th index to last index)
2. **Decision at each step**: For each position `i` in `s` and `j` in `t`, we decide:
   - If `s[i] == t[j]`: We can use `s[i]` to match `t[j]` OR skip `s[i]`
   - If `s[i] != t[j]`: We must skip `s[i]`
3. **Base cases**:
   - If `j == n` (we've matched all of `t`): return 1 (found one valid subsequence)
   - If `i == m` but `j < n` (we've exhausted `s` but not `t`): return 0 (cannot form `t`)

**Recursive relationship:**
- If `s[i] == t[j]`:
  - **Use it**: `dp(i+1, j+1)` - match current characters and move both pointers
  - **Skip it**: `dp(i+1, j)` - skip current character in `s`, keep `t` pointer same
  - Total: `dp(i+1, j+1) + dp(i+1, j)`
- If `s[i] != t[j]`:
  - **Must skip**: `dp(i+1, j)` - skip current character in `s`

**Why memo array size is `(m+1) × (n+1)`:**
- We need indices from `0` to `m` for string `s` (total `m+1` positions)
- We need indices from `0` to `n` for string `t` (total `n+1` positions)
- The `+1` accounts for the base case where we've exhausted one or both strings
- `memo[i][j]` stores the result for `dp(i, j)` where `i` can be `0..m` and `j` can be `0..n`

### Top-Down / Memoized Version

```java
class Solution {
    public int numDistinct(String s, String t) {
        int m = s.length();
        int n = t.length();
        
        // Memo array: memo[i][j] stores result for dp(i, j)
        // Size: (m+1) × (n+1) because i ranges from 0 to m, j ranges from 0 to n
        // The +1 accounts for base cases where we've exhausted one or both strings
        Integer[][] memo = new Integer[m + 1][n + 1];
        
        // Start from beginning of both strings (0th index)
        return dp(s, t, 0, 0, memo);
    }
    
    // dp(i, j) = number of ways to form t[j..n-1] using s[i..m-1]
    // We process from left to right (0th index to last index)
    private int dp(String s, String t, int i, int j, Integer[][] memo) {
        // Base case: we've matched all characters in t (j reached end of t)
        // This means we found one valid subsequence
        if (j == t.length()) {
            return 1;
        }
        
        // Base case: we've exhausted s but haven't matched all of t
        // Cannot form t, so return 0
        if (i == s.length()) {
            return 0;
        }
        
        // Check if we've already computed this subproblem
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        int result = 0;
        
        // If current characters match
        if (s.charAt(i) == t.charAt(j)) {
            // Option 1: Use current character from s to match current character in t
            // Move both pointers forward: dp(i+1, j+1)
            result += dp(s, t, i + 1, j + 1, memo);
            
            // Option 2: Skip current character from s
            // Keep t pointer same, move s pointer: dp(i+1, j)
            result += dp(s, t, i + 1, j, memo);
        } else {
            // Characters don't match, we must skip current character in s
            // Keep t pointer same, move s pointer: dp(i+1, j)
            result = dp(s, t, i + 1, j, memo);
        }
        
        // Store result in memo before returning
        memo[i][j] = result;
        return result;
    }
}
```

**Key Points:**
- **Processing direction**: We process from 0th index to last index (left to right) in both strings
- **Memoization**: We store results to avoid recalculating the same subproblems
- **Two choices when match**: Use the character OR skip it (both contribute to the count)
- **One choice when no match**: Must skip (only one way forward)

### Bottom-Up Version

```java
class Solution {
    public int numDistinct(String s, String t) {
        int m = s.length();
        int n = t.length();
        
        // DP table: dp[i][j] = number of ways to form t[0..j-1] using s[0..i-1]
        // Size: (m+1) × (n+1) because:
        // - dp[i][j] represents using first i characters of s and first j characters of t
        // - i ranges from 0 to m (0 means no characters from s, m means all characters)
        // - j ranges from 0 to n (0 means no characters from t, n means all characters)
        // - The +1 accounts for the base case where we use 0 characters
        int[][] dp = new int[m + 1][n + 1];
        
        // Base case: dp[i][0] = 1 for all i
        // Meaning: empty string t can be formed in exactly 1 way (by using no characters from s)
        // This is true for any prefix of s
        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }
        
        // Fill DP table bottom-up
        // i represents how many characters from s we've used (0 to m)
        // j represents how many characters from t we've matched (0 to n)
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // If current characters match (s[i-1] and t[j-1] because we're using 1-indexed in DP)
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    // Option 1: Use s[i-1] to match t[j-1]
                    // Number of ways = dp[i-1][j-1] (ways to form t[0..j-2] using s[0..i-2])
                    // Option 2: Skip s[i-1]
                    // Number of ways = dp[i-1][j] (ways to form t[0..j-1] using s[0..i-2])
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    // Characters don't match, must skip s[i-1]
                    // Number of ways = dp[i-1][j] (ways to form t[0..j-1] using s[0..i-2])
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        
        // Return number of ways to form entire t using entire s
        return dp[m][n];
    }
}
```

**When Bottom-Up is Better Than Top-Down:**

1. **No recursion overhead**: Bottom-up avoids function call stack overhead
2. **Better cache locality**: Iterating through array sequentially is more cache-friendly
3. **Predictable memory access**: Sequential access pattern is easier for CPU to optimize
4. **No stack overflow risk**: For very large inputs, bottom-up avoids potential stack overflow from deep recursion
5. **Easier space optimization**: Can easily optimize to 1D array since we only need previous row

**Space-Optimized Bottom-Up Version:**

```java
class Solution {
    public int numDistinct(String s, String t) {
        int m = s.length();
        int n = t.length();
        
        // Space optimization: We only need the previous row, so use 1D array
        // dp[j] represents number of ways to form t[0..j-1] using current prefix of s
        // Size: n+1 because j ranges from 0 to n
        int[] dp = new int[n + 1];
        
        // Base case: empty string t can be formed in 1 way
        dp[0] = 1;
        
        // Process each character in s
        for (int i = 1; i <= m; i++) {
            // Process from right to left to avoid overwriting values we still need
            // We need dp[j-1] from previous iteration, so we update from right to left
            for (int j = n; j >= 1; j--) {
                // If characters match
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    // dp[j] = dp[j] (skip) + dp[j-1] (use)
                    // dp[j-1] is from previous iteration (before we update it)
                    dp[j] += dp[j - 1];
                }
                // If no match, dp[j] stays the same (skip current character)
            }
        }
        
        return dp[n];
    }
}
```

**Why we iterate right to left in space-optimized version:**
- When we update `dp[j]`, we need `dp[j-1]` from the **previous iteration** (before we updated it)
- If we iterate left to right, `dp[j-1]` would already be updated, giving us wrong value
- By iterating right to left, `dp[j-1]` still has the value from previous iteration

## Complexity Analysis

- **Time Complexity:** $O(m \times n)$ where $m$ is the length of `s` and $n$ is the length of `t`. 
  - Top-down: Each subproblem `(i, j)` is computed at most once, and there are $m \times n$ subproblems
  - Bottom-up: We fill a 2D DP table of size $(m+1) \times (n+1)$

- **Space Complexity:** 
  - Top-down: $O(m \times n)$ for the memoization array, plus $O(m + n)$ for recursion stack
  - Bottom-up: $O(m \times n)$ for the DP table
  - Space-optimized bottom-up: $O(n)$ using a 1D array

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **115. Distinct Subsequences** (this problem) - DP for subsequence counting
2. **392. Is Subsequence** - Check if subsequence exists (simpler version)
3. **1143. Longest Common Subsequence** - DP for LCS (similar structure)
4. **72. Edit Distance** - DP for string transformation (similar decision-making)
5. **97. Interleaving String** - DP for string interleaving (similar matching logic)
6. **583. Delete Operation for Two Strings** - DP for deletion (similar structure)
7. **712. Minimum ASCII Delete Sum for Two Strings** - DP with costs (similar pattern)
8. **1092. Shortest Common Supersequence** - DP for supersequence (related problem)
9. **1312. Minimum Insertion Steps to Make a String Palindrome** - DP for palindrome
10. **516. Longest Palindromic Subsequence** - DP for palindrome (similar structure)
