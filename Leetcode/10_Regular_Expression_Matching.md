# Regular Expression Matching

## Problem Description

**Problem Link:** [Regular Expression Matching](https://leetcode.com/problems/regular-expression-matching/)

Given an input string `s` and a pattern `p`, implement regular expression matching with support for `'.'` and `'*'` where:

- `'.'` Matches any single character.
- `'*'` Matches zero or more of the preceding element.

The matching should cover the **entire** input string (not partial).

**Example 1:**
```
Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
```

**Example 2:**
```
Input: s = "aa", p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
```

**Example 3:**
```
Input: s = "ab", p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".
```

**Constraints:**
- `1 <= s.length <= 20`
- `1 <= p.length <= 20`
- `s` contains only lowercase English letters.
- `p` contains only lowercase English letters, `'.'`, and `'*'`.
- It is guaranteed that for each appearance of the character `'*'`, there will be a previous valid character to match.

## Intuition/Main Idea

### Building the Intuition Incrementally

**Step 1: Plain Matching Without Any Special Characters**

First, imagine matching without `'.'` or `'*'`. Given `s = "abc"` and `p = "abc"`:
- Compare `s[i]` and `p[j]` at each step.
- If equal, advance both pointers: the problem reduces to matching the rest of `s` against the rest of `p`.
- If both strings are exhausted simultaneously, it is a match.

This gives the fundamental recursive skeleton:
```
dp(sIndex, pIndex) = (s[sIndex] == p[pIndex]) AND dp(sIndex+1, pIndex+1)
```

**Step 2: Adding `'.'` (Wildcard for Any Single Character)**

`'.'` matches any single character. The only change is in the comparison:
```
currentCharsMatch = (s[sIndex] == p[pIndex]) OR (p[pIndex] == '.')
```
Everything else (advancing both pointers) stays the same.

**Step 3: Adding `'*'` — The Critical Complexity**

`'*'` is never standalone — it always appears with the character before it (e.g., `a*` or `.*`). The pair `p[j]p[j+1]` where `p[j+1] == '*'` means "zero or more of `p[j]`".

When we encounter `p[pIndex+1] == '*'`, we have two decisions for the unit `p[pIndex]*`:
- **Zero occurrences**: Ignore the entire `p[pIndex]*` unit. Advance the pattern by 2.
  → `dp(sIndex, pIndex + 2)`
- **One or more occurrences**: Only valid if `s[sIndex]` matches `p[pIndex]`. Consume `s[sIndex]` but **stay at `pIndex`** in the pattern (because `'*'` might still fire again for the next character of `s`).
  → `firstMatch AND dp(sIndex + 1, pIndex)`

**Why "stay at `pIndex`" instead of advancing to `pIndex + 2`?**

Because `'*'` means one *or more*. After matching one character, there might be more of the same to match. Staying at `pIndex` lets the `'*'` fire again on the next character of `s`. When no more characters match, the "zero occurrences" branch will skip the unit.

**Step 4: Why DP? (Overlapping Subproblems)**

Consider `s = "aaa"`, `p = "a*"`. Without memoization, `dp(2, 0)` is reached via multiple paths in more complex patterns. Memoizing each `(sIndex, pIndex)` pair eliminates redundant recomputation, reducing complexity from exponential to $O(m \times n)$.

**Why the Intuition Works — The Core Principle:**

`dp(sIndex, pIndex)` only depends on:
1. `s[sIndex]` and `p[pIndex]` (current characters).
2. Whether `p[pIndex+1] == '*'` (decides which branch to take).
3. Results of strictly smaller subproblems (indices always move forward).

Future characters do not affect the current decision. This is **optimal substructure** — the hallmark of DP.

### Subproblem Definition

`dp(sIndex, pIndex)` = does `s[sIndex..m-1]` match `p[pIndex..n-1]`?

where `m = s.length()` and `n = p.length()`.

### State Transitions

1. **Base case**: `pIndex == n` → return `sIndex == m`
   *(Pattern exhausted: valid only if string is also exhausted)*

2. **Current character match**: `firstMatch = (sIndex < m) AND (s[sIndex] == p[pIndex] OR p[pIndex] == '.')`

3. **If `p[pIndex+1] == '*'`** (star unit):
   - `dp(sIndex, pIndex + 2)` ← zero occurrences: skip `p[pIndex]*` entirely
   - OR `firstMatch AND dp(sIndex + 1, pIndex)` ← one occurrence, stay at `pIndex` to allow more

4. **Else** (regular character):
   - `firstMatch AND dp(sIndex + 1, pIndex + 1)` ← advance both pointers

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Pattern exhausted base case | `pIndex == p.length()` check — Top-down: base case; Bottom-up: `dp[0][0] = true` |
| Patterns matching empty string (`a*`, `a*b*`) | Init loop — Bottom-up: `dp[0][pIndex] = dp[0][pIndex-2]` when `p.charAt(pIndex-1)=='*'` |
| Match any single character with `'.'` | `firstMatch` computation using `p.charAt(pIndex) == '.'` |
| Zero occurrences with `'*'` | `dp(sIndex, pIndex + 2)` — top-down; `dp[sIndex][pIndex-2]` — bottom-up |
| One or more occurrences with `'*'` | `firstMatch && dp(sIndex + 1, pIndex)` — top-down; `dp[sIndex-1][pIndex]` — bottom-up |
| Exact character matching (no `'*'`) | `firstMatch && dp(sIndex+1, pIndex+1)` — top-down; `dp[sIndex-1][pIndex-1]` — bottom-up |
| Memoization to avoid recomputation | `memo[sIndex][pIndex]` cache check and store |

## Final Java Code & Learning Pattern

### Intuition Behind Generating Subproblems (Top-Down Approach)

**Subproblem Definition:**
`dp(sIndex, pIndex)` = does `s[sIndex..m-1]` match `p[pIndex..n-1]`?

**Why process from 0th index to last index:**
We start at `(0, 0)` and recurse forward. At each step, `sIndex` either advances by 1 or stays the same, and `pIndex` either advances by 1 or 2. Indices never go backward, so every recursive call operates on a strictly smaller suffix. This guarantees termination.

**Why `memo` size is `(m+1) × (n+1)`:**
- `sIndex` ranges from `0` to `m` inclusive — `m` is used in the base case check `sIndex == m`
- `pIndex` ranges from `0` to `n` inclusive — `n` is used in the base case check `pIndex == n`
- Total distinct states: `(m+1) × (n+1)`

### Top-Down / Memoized Version

```java
class Solution {
    public boolean isMatch(String s, String p) {
        // memo[sIndex][pIndex] = does s[sIndex..] match p[pIndex..]?
        // Size (m+1) x (n+1): sIndex ranges 0..m, pIndex ranges 0..n (both inclusive for base cases)
        Boolean[][] memo = new Boolean[s.length() + 1][p.length() + 1];
        return dp(0, 0, s, p, memo);
    }

    private boolean dp(int sIndex, int pIndex, String s, String p, Boolean[][] memo) {
        // Return cached result if already computed for this subproblem
        if (memo[sIndex][pIndex] != null) {
            return memo[sIndex][pIndex];
        }

        boolean result;

        // Base case: pattern fully consumed
        // dp(sIndex, n): does s[sIndex..] match empty pattern?
        // Only true if string is also fully consumed (no leftover unmatched characters in s)
        if (pIndex == p.length()) {
            return sIndex == s.length();
        }

        // Check if current characters can match
        // firstMatch is false if sIndex == s.length() (s exhausted but p still has characters)
        // This handles both exact character match and '.' wildcard
        boolean firstMatch = (sIndex < s.length()) &&
                             (p.charAt(pIndex) == s.charAt(sIndex) || p.charAt(pIndex) == '.');

        // State transition: check if a '*' follows the current pattern character
        if (pIndex + 1 < p.length() && p.charAt(pIndex + 1) == '*') {
            // 'x*' unit found — two choices:
            // Choice 1 (zero occurrences): skip the entire 'x*' unit, advance pattern by 2
            //   → subproblem: dp(sIndex, pIndex + 2)
            // Choice 2 (one or more occurrences): s[sIndex] must match p[pIndex],
            //   consume s[sIndex], stay at pIndex so '*' can fire again on next character
            //   → subproblem: firstMatch AND dp(sIndex + 1, pIndex)
            result = dp(sIndex, pIndex + 2, s, p, memo) ||
                     (firstMatch && dp(sIndex + 1, pIndex, s, p, memo));
        } else {
            // No '*' follows: regular character or '.' — both must match, advance both pointers
            // State transition: dp(sIndex, pIndex) = firstMatch AND dp(sIndex+1, pIndex+1)
            result = firstMatch && dp(sIndex + 1, pIndex + 1, s, p, memo);
        }

        // Cache result before returning
        memo[sIndex][pIndex] = result;
        return result;
    }
}
```

### Bottom-Up Version

**When is bottom-up better than top-down here?**
Both approaches share the same $O(m \times n)$ time complexity. Bottom-up can be faster in practice due to:
- **No function call overhead**: Java method calls have stack frame cost; bottom-up avoids this entirely.
- **Better memory locality**: Sequential array access is cache-friendly vs. scattered recursive calls.
- **No stack overflow risk**: Even though `m, n ≤ 20` here, bottom-up is the safer habit for large inputs.

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int stringLength = s.length();
        int patternLength = p.length();

        // dp[sIndex][pIndex] = does s[0..sIndex-1] match p[0..pIndex-1]?
        // Size (stringLength+1) x (patternLength+1):
        // - sIndex=0 represents the empty prefix of s
        // - pIndex=0 represents the empty prefix of p
        // - We need indices 0..stringLength and 0..patternLength inclusive
        boolean[][] dp = new boolean[stringLength + 1][patternLength + 1];

        // Base case: empty string matches empty pattern
        dp[0][0] = true;

        // Base case: empty string s matched against patterns like "a*", "a*b*", ".*"
        // These patterns can match empty string by using zero occurrences of their char units
        for (int pIndex = 1; pIndex <= patternLength; pIndex++) {
            if (p.charAt(pIndex - 1) == '*') {
                // Use zero occurrences of the preceding char: skip back 2 positions
                // State transition: dp[0][pIndex] = dp[0][pIndex - 2]
                dp[0][pIndex] = dp[0][pIndex - 2];
            }
        }

        // Fill dp table bottom-up (smaller substrings first, building up to full strings)
        for (int sIndex = 1; sIndex <= stringLength; sIndex++) {
            for (int pIndex = 1; pIndex <= patternLength; pIndex++) {
                char patternChar = p.charAt(pIndex - 1);
                char stringChar = s.charAt(sIndex - 1);

                if (patternChar == '*') {
                    // '*' modifies the character immediately before it: p.charAt(pIndex - 2)
                    // Choice 1 (zero occurrences): skip the 'x*' unit entirely
                    // State transition: dp[sIndex][pIndex] = dp[sIndex][pIndex - 2]
                    dp[sIndex][pIndex] = dp[sIndex][pIndex - 2];

                    // Choice 2 (one or more occurrences): s[sIndex-1] must match the char before '*'
                    // State transition: dp[sIndex][pIndex] |= dp[sIndex-1][pIndex]
                    char charBeforeStar = p.charAt(pIndex - 2);
                    if (charBeforeStar == '.' || charBeforeStar == stringChar) {
                        dp[sIndex][pIndex] = dp[sIndex][pIndex] || dp[sIndex - 1][pIndex];
                    }
                } else if (patternChar == '.' || patternChar == stringChar) {
                    // Current characters match (exact or '.' wildcard)
                    // State transition: dp[sIndex][pIndex] = dp[sIndex-1][pIndex-1]
                    dp[sIndex][pIndex] = dp[sIndex - 1][pIndex - 1];
                }
                // else: characters do not match → dp[sIndex][pIndex] stays false
            }
        }

        return dp[stringLength][patternLength];
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(m \times n)$ where $m$ is the length of string `s` and $n$ is the length of pattern `p`. Each of the $m \times n$ subproblems is computed exactly once.

- **Space Complexity:** $O(m \times n)$ for the DP table or memoization array.

## Similar Problems

1. [44. Wildcard Matching](https://leetcode.com/problems/wildcard-matching/) - Similar pattern matching with different wildcard rules
2. [97. Interleaving String](https://leetcode.com/problems/interleaving-string/) - Another string pattern matching problem using DP
3. [72. Edit Distance](https://leetcode.com/problems/edit-distance/) - DP for string transformation
4. [115. Distinct Subsequences](https://leetcode.com/problems/distinct-subsequences/) - DP for subsequence counting
5. [1143. Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/) - Classic DP string problem
6. [583. Delete Operation for Two Strings](https://leetcode.com/problems/delete-operation-for-two-strings/) - DP for string operations
7. [712. Minimum ASCII Delete Sum for Two Strings](https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/) - DP with costs
8. [1312. Minimum Insertion Steps to Make a String Palindrome](https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/) - DP for palindrome construction
9. [516. Longest Palindromic Subsequence](https://leetcode.com/problems/longest-palindromic-subsequence/) - DP for palindrome finding
10. [22. Generate Parentheses](https://leetcode.com/problems/generate-parentheses/) - While also a string-building problem, this uses backtracking rather than 2D DP matching; not truly similar in approach
