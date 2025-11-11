# Scramble String

## Problem Description

**Problem Link:** [Scramble String](https://leetcode.com/problems/scramble-string/)

We can scramble a string `s` to get a string `t` using the following algorithm:

1. If the length of the string is 1, stop.
2. If the length of the string is > 1, do the following:
   - Split the string into two non-empty substrings at a random index, i.e., if the string is `s`, split it into `x` and `y` where `s = x + y`.
   - **Randomly** decide to swap the two substrings or to keep them in the same order, i.e., after this step, `s` may become `s = x + y` or `s = y + x`.
   - Apply step 1 recursively on each of the two substrings `x` and `y`.

Given two strings `s1` and `s2` of **the same length**, return `true` *if `s2` is a scrambled string of `s1`, otherwise return `false`*.

**Example 1:**

```
Input: s1 = "great", s2 = "rgeat"
Output: true
Explanation: One possible scenario applied on s1 is:
"great" --> "gr/eat" --> "gr" + "eat" --> "rgeat"
```

**Example 2:**

```
Input: s1 = "abcde", s2 = "caebd"
Output: false
```

**Example 3:**

```
Input: s1 = "a", s2 = "a"
Output: true
```

**Constraints:**
- `s1.length == s2.length`
- `1 <= s1.length <= 30`
- `s1` and `s2` consist of lowercase English letters.

## Intuition/Main Idea

This is an **interval DP** problem with memoization. We need to check if `s2` can be obtained by scrambling `s1`.

**Core Algorithm:**
1. Use DP where `dp[i][j][len]` = can `s1[i..i+len-1]` be scrambled to `s2[j..j+len-1]`.
2. For each length and starting positions, try all possible split points.
3. Check two cases: same order and swapped order.

**Why DP works:** The problem has overlapping subproblems - checking if substrings can be scrambled is needed multiple times. We can memoize results to avoid recomputation.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Memoization map | HashMap - Line 5 |
| Base case: length 1 | Length check - Lines 9-11 |
| Check character frequency | Frequency check - Lines 13-18 |
| Try all splits | For loop - Line 21 |
| Check same order | Same order check - Lines 23-25 |
| Check swapped order | Swapped order check - Lines 26-28 |
| Return result | Return statement - Line 31 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
import java.util.*;

class Solution {
    public boolean isScramble(String s1, String s2) {
        Map<String, Boolean> memo = new HashMap<>();
        return isScramble(s1, s2, memo);
    }
    
    private boolean isScramble(String s1, String s2, Map<String, Boolean> memo) {
        // Base case: same string
        if (s1.equals(s2)) {
            return true;
        }
        
        // Base case: different lengths
        if (s1.length() != s2.length()) {
            return false;
        }
        
        String key = s1 + "#" + s2;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        int n = s1.length();
        
        // Check character frequency (pruning)
        int[] count = new int[26];
        for (int i = 0; i < n; i++) {
            count[s1.charAt(i) - 'a']++;
            count[s2.charAt(i) - 'a']--;
        }
        for (int c : count) {
            if (c != 0) {
                memo.put(key, false);
                return false;
            }
        }
        
        // Try all possible split points
        for (int i = 1; i < n; i++) {
            // Case 1: Same order (no swap)
            if (isScramble(s1.substring(0, i), s2.substring(0, i), memo) &&
                isScramble(s1.substring(i), s2.substring(i), memo)) {
                memo.put(key, true);
                return true;
            }
            
            // Case 2: Swapped order
            if (isScramble(s1.substring(0, i), s2.substring(n - i), memo) &&
                isScramble(s1.substring(i), s2.substring(0, n - i), memo)) {
                memo.put(key, true);
                return true;
            }
        }
        
        memo.put(key, false);
        return false;
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Cases (Lines 9-15):** 
   - If strings are equal, return true.
   - If lengths differ, return false.

2. **Memoization (Lines 17-20):** Use a key combining both strings to cache results.

3. **Character Frequency Check (Lines 22-29):** Pruning optimization - if character frequencies don't match, they can't be scrambled.

4. **Try Splits (Lines 31-42):** For each split point `i`:
   - **Same Order (Lines 33-36):** Check if `s1[0..i-1]` scrambles to `s2[0..i-1]` and `s1[i..]` scrambles to `s2[i..]`.
   - **Swapped Order (Lines 38-41):** Check if `s1[0..i-1]` scrambles to `s2[n-i..]` and `s1[i..]` scrambles to `s2[0..n-i-1]`.

**Intuition behind generating subproblems:**
- **Subproblem:** "Can `s1` be scrambled to `s2`?"
- **Why this works:** To scramble `s1` to `s2`, we split at some point and either keep or swap the parts, then recursively scramble each part.
- **Overlapping subproblems:** Multiple scrambles may check the same substring pairs.

**Example walkthrough for `s1 = "great", s2 = "rgeat"`:**
- Split at 2: "gr" + "eat"
- Swapped: "eat" + "gr" = "eatgr" ≠ "rgeat"
- Try other splits...
- Actually: "great" → split at 2 → "gr"/"eat" → swap → "eat"/"gr" → "eat" + "gr" = "eatgr"
- Need: "rgeat" = "rg" + "eat"
- So: "gr" → "rg" (scrambled), "eat" → "eat" (same)
- Check recursively...

## Complexity Analysis

- **Time Complexity:** $O(n^4)$ in worst case due to memoization, but pruning significantly reduces this.

- **Space Complexity:** $O(n^3)$ for memoization in worst case.

## Similar Problems

Problems that can be solved using similar interval DP patterns:

1. **87. Scramble String** (this problem) - Interval DP with memoization
2. **312. Burst Balloons** - Interval DP
3. **1039. Minimum Score Triangulation of Polygon** - Interval DP
4. **1130. Minimum Cost Tree From Leaf Values** - Interval DP
5. **1547. Minimum Cost to Cut a Stick** - Interval DP
6. **516. Longest Palindromic Subsequence** - Interval DP
7. **664. Strange Printer** - Interval DP
8. **131. Palindrome Partitioning** - Interval DP variant

