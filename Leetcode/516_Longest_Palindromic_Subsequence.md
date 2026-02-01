# 516. Longest Palindromic Subsequence

[LeetCode Link](https://leetcode.com/problems/longest-palindromic-subsequence/)

## Problem Description
Given a string `s`, find the length of the **longest palindromic subsequence** in `s`.

A **subsequence** is a sequence that can be derived from another sequence by deleting some or no characters without changing the order of the remaining characters.

### Examples

#### Example 1
- Input: `s = "bbbab"`
- Output: `4`
- Explanation: One longest palindromic subsequence is `"bbbb"`.

#### Example 2
- Input: `s = "cbbd"`
- Output: `2`
- Explanation: One longest palindromic subsequence is `"bb"`.

---

## Intuition/Main Idea
We want a palindrome, which is defined by matching characters at both ends.

This strongly suggests a 2-pointer interval DP:

- If `s[left] == s[right]`, we can use both ends and add `2` to the best inside: `dp(left+1, right-1)`.
- If they don’t match, the best palindrome must drop one end: max of `dp(left+1, right)` and `dp(left, right-1)`.

This is classic “DP over substrings” where the subproblem is defined on an interval `[left, right]`.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Subsequence (not substring) | Mismatch case uses `max(dp(left+1,right), dp(left,right-1))` (skipping characters) |
| Palindrome requires matching ends | Match case adds `2` when `s[left] == s[right]` |
| Return only the maximum length | Return `dp(0, n-1)` (or `dp[0][n-1]`) |

---

## Final Java Code & Learning Pattern (Full Content)

### Top-Down (Memoized) DP

**Subproblem definition:**

- `dp(left, right)` = length of the longest palindromic subsequence inside substring `s[left..right]`.

**State transition (in simple words):**

- If the two ends match, we “take both ends” and solve the smaller inside problem.
- If they don’t match, we “skip one end” and take the better of the two possibilities.

```java
import java.util.*;

class Solution {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();

        // memo[left][right] stores dp(left, right).
        // Size is n x n because left/right are 0..n-1.
        Integer[][] memo = new Integer[n][n];

        return dp(s, 0, n - 1, memo);
    }

    private int dp(String s, int left, int right, Integer[][] memo) {
        if (left > right) {
            return 0;
        }
        if (left == right) {
            return 1;
        }

        if (memo[left][right] != null) {
            return memo[left][right];
        }

        int best;
        if (s.charAt(left) == s.charAt(right)) {
            best = 2 + dp(s, left + 1, right - 1, memo);
        } else {
            int skipLeft = dp(s, left + 1, right, memo);
            int skipRight = dp(s, left, right - 1, memo);
            best = Math.max(skipLeft, skipRight);
        }

        memo[left][right] = best;
        return best;
    }
}
```

### Bottom-Up DP

Bottom-up uses the same state but fills it in an order where dependencies are already computed.

- `dp[left][right]` depends on:
  - `dp[left+1][right-1]`
  - `dp[left+1][right]`
  - `dp[left][right-1]`

So we should build from smaller intervals to larger intervals.

```java
import java.util.*;

class Solution {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();

        // dp[left][right] = LPS length in s[left..right]
        // Size n x n because left/right are 0..n-1.
        int[][] dp = new int[n][n];

        // Length-1 intervals are always palindromes of length 1.
        for (int index = 0; index < n; index++) {
            dp[index][index] = 1;
        }

        // Build for increasing interval lengths.
        for (int intervalLength = 2; intervalLength <= n; intervalLength++) {
            for (int left = 0; left + intervalLength - 1 < n; left++) {
                int right = left + intervalLength - 1;

                if (s.charAt(left) == s.charAt(right)) {
                    dp[left][right] = 2 + dp[left + 1][right - 1];
                } else {
                    dp[left][right] = Math.max(dp[left + 1][right], dp[left][right - 1]);
                }
            }
        }

        return dp[0][n - 1];
    }
}
```

### Learning Pattern
- When the answer depends on matching “ends”, define DP on an interval `[left, right]`.
- Match ends => take both + solve inside.
- Mismatch => subsequence means you can skip characters, so take max of skipping one side.

---

## Complexity Analysis
- Time Complexity: $O(n^2)$
  - There are $O(n^2)$ distinct intervals `(left, right)`.
- Space Complexity: $O(n^2)$
  - Memo table / DP table size is `n x n`.

---

## Dynamic Programming Problems

### Subproblem Definition
- `dp(left, right)` = longest palindromic subsequence length inside `s[left..right]`.

### State Transition (simple words)
- If `s[left] == s[right]`: include both ends and solve inside.
- Else: skip left or skip right, take the best.

### Why the Top-Down Approach is Natural
- The problem statement naturally asks: “what’s the best inside this interval?”
- Top-down recursion directly mirrors that question and memoization ensures each interval is solved once.

### Why Bottom-Up Can Be Better in Practice
- Same asymptotic time, but avoids recursion overhead and is iterative.
- Especially good when recursion depth might be large.

---

## Similar Problems
- [1143. Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/) (subsequence DP)
- [647. Palindromic Substrings](https://leetcode.com/problems/palindromic-substrings/) (DP over intervals, but substring not subsequence)
- [5. Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/) (palindrome by intervals)
