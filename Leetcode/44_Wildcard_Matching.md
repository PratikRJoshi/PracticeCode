# Wildcard Matching

## Problem Description

**Problem Link:** [Wildcard Matching](https://leetcode.com/problems/wildcard-matching/)

Given an input string `s` and a pattern `p`, implement wildcard pattern matching with support for:

- `'?'` → matches any single character.
- `'*'` → matches any sequence of characters (including the empty sequence).

The match must cover the **entire** input string (not partial).

**Example 1:**
```
Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the whole string "aa".
```

**Example 2:**
```
Input: s = "aa", p = "*"
Output: true
Explanation: '*' can match the full string "aa".
```

**Example 3:**
```
Input: s = "cb", p = "?a"
Output: false
Explanation: '?' matches 'c', but 'a' does not match 'b'.
```

**Constraints:**
- `0 <= s.length, p.length <= 2000`
- `s` contains only lowercase English letters.
- `p` contains only lowercase English letters, `'?'`, and `'*'`.

## Intuition/Main Idea

### Build the intuition step by step

1. Define a subproblem by indices: from where are we matching in `s` and `p`?
2. If current pattern char is normal letter or `'?'`, we can only move both pointers forward by one.
3. If current pattern char is `'*'`, we have two valid choices:
   - `'*'` matches empty sequence: move pattern pointer only.
   - `'*'` matches one or more chars: consume one char in `s`, keep `'*'` in pattern.
4. These same states repeat many times, so memoization/DP is natural.

### Why this intuition works

At every state `(sIndex, pIndex)`, the answer depends only on:
- current pattern character type (letter, `'?'`, or `'*'`)
- and smaller suffix states.

For `'*'`, the two choices above are complete (either it consumes nothing or at least one char), so we do not miss any valid match.

### Subproblem Definition (DP State)

`dp(sIndex, pIndex)` = whether suffix `s[sIndex..]` matches suffix `p[pIndex..]`.

### State Transitions

1. **Base case:** if pattern is exhausted (`pIndex == p.length()`), return whether string is also exhausted (`sIndex == s.length()`).
2. If `p[pIndex] == '*'`:
   - skip `'*'`: `dp(sIndex, pIndex + 1)`
   - use `'*'` to consume one character: `sIndex < s.length() && dp(sIndex + 1, pIndex)`
3. Else if current chars match (`p[pIndex] == '?'` or equal letters):
   - `dp(sIndex + 1, pIndex + 1)`
4. Else: `false`.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Match entire string, not partial | Base case `if (patternIndex == pattern.length()) return stringIndex == string.length();` |
| `'?'` matches any one character | `patternCharacter == '?'` check in both top-down and bottom-up |
| `'*'` matches empty sequence | Top-down: `dp(stringIndex, patternIndex + 1, ...)`; Bottom-up: `dp[i][j - 1]` |
| `'*'` matches one or more characters | Top-down: `dp(stringIndex + 1, patternIndex, ...)`; Bottom-up: `dp[i - 1][j]` |
| Avoid recomputation of repeated states | `memo[stringIndex][patternIndex]` cache |
| Handle empty string with leading stars in pattern | Bottom-up init: `dp[0][patternIndex] = dp[0][patternIndex - 1]` when `'*'` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public boolean isMatch(String string, String pattern) {
        // memo[stringIndex][patternIndex] stores dp(stringIndex, patternIndex)
        // Size is (m + 1) x (n + 1) because indices can reach m and n in base cases.
        Boolean[][] memo = new Boolean[string.length() + 1][pattern.length() + 1];
        return dp(0, 0, string, pattern, memo);
    }

    private boolean dp(int stringIndex, int patternIndex,
                       String string, String pattern,
                       Boolean[][] memo) {
        if (memo[stringIndex][patternIndex] != null) {
            return memo[stringIndex][patternIndex];
        }

        boolean answer;

        // Pattern finished: valid only when string is also fully consumed.
        if (patternIndex == pattern.length()) {
            answer = (stringIndex == string.length());
            memo[stringIndex][patternIndex] = answer;
            return answer;
        }

        char patternCharacter = pattern.charAt(patternIndex);

        if (patternCharacter == '*') {
            // Choice 1: '*' matches empty sequence -> move pattern pointer.
            boolean skipStar = dp(stringIndex, patternIndex + 1, string, pattern, memo);

            // Choice 2: '*' consumes one character -> move string pointer, keep '*'.
            boolean consumeOneCharacter =
                (stringIndex < string.length()) &&
                dp(stringIndex + 1, patternIndex, string, pattern, memo);

            answer = skipStar || consumeOneCharacter;
        } else {
            // Normal character or '?': current position must be matchable.
            boolean currentMatches =
                (stringIndex < string.length()) &&
                (patternCharacter == '?' || patternCharacter == string.charAt(stringIndex));

            answer = currentMatches && dp(stringIndex + 1, patternIndex + 1, string, pattern, memo);
        }

        memo[stringIndex][patternIndex] = answer;
        return answer;
    }
}
```

### Bottom-Up Version

**When can bottom-up be better here?**
- Same asymptotic complexity as top-down: $O(m \times n)$.
- Often slightly faster in Java due to no recursive call overhead.
- No recursion depth concerns.

```java
class Solution {
    public boolean isMatch(String string, String pattern) {
        int stringLength = string.length();
        int patternLength = pattern.length();

        // dp[i][j] = whether string prefix length i matches pattern prefix length j
        // Size (m+1) x (n+1) is required to represent empty prefixes at index 0.
        boolean[][] dp = new boolean[stringLength + 1][patternLength + 1];

        dp[0][0] = true;

        // Empty string vs pattern prefix: only possible when pattern is all '*'.
        for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
            if (pattern.charAt(patternIndex - 1) == '*') {
                dp[0][patternIndex] = dp[0][patternIndex - 1];
            }
        }

        for (int stringIndex = 1; stringIndex <= stringLength; stringIndex++) {
            for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
                char patternCharacter = pattern.charAt(patternIndex - 1);

                if (patternCharacter == '*') {
                    // '*' as empty sequence OR '*' consumes one more character.
                    dp[stringIndex][patternIndex] =
                        dp[stringIndex][patternIndex - 1] || dp[stringIndex - 1][patternIndex];
                } else if (patternCharacter == '?' ||
                           patternCharacter == string.charAt(stringIndex - 1)) {
                    // Current characters match; inherit diagonal state.
                    dp[stringIndex][patternIndex] = dp[stringIndex - 1][patternIndex - 1];
                }
            }
        }

        return dp[stringLength][patternLength];
    }
}
```

## Complexity Analysis

Let `m = s.length()` and `n = p.length()`.

- **Time Complexity:** $O(m \times n)$ for both memoized top-down and bottom-up DP.
- **Space Complexity:** $O(m \times n)$ for memo/DP table.

## Similar Problems

1. [10. Regular Expression Matching](https://leetcode.com/problems/regular-expression-matching/) - Similar DP-on-two-strings pattern matching, but `*` semantics differ (`x*` instead of standalone `*`).
2. [97. Interleaving String](https://leetcode.com/problems/interleaving-string/) - 2D DP over string indices.
3. [72. Edit Distance](https://leetcode.com/problems/edit-distance/) - Classic two-index DP on strings.
4. [115. Distinct Subsequences](https://leetcode.com/problems/distinct-subsequences/) - Another state-by-indices string DP.
