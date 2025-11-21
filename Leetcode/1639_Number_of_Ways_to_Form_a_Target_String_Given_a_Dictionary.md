# Number of Ways to Form a Target String Given a Dictionary

## Problem Description

**Problem Link:** [Number of Ways to Form a Target String Given a Dictionary](https://leetcode.com/problems/number-of-ways-to-form-a-target-string-given-a-dictionary/)

You are given a list of strings of the same length `words` and a string `target`.

Your task is to form `target` using the given `words` under the following rules:

- You should use as many words as you want.
- From each word, you can pick one character at any position.
- Once you pick a character from a word at position `i`, you cannot pick a character from the same word at any position `j` where `j < i`.

Return *the number of ways to form* `target` *from* `words`. Since the answer may be too large, return it **modulo** `10^9 + 7`.

**Example 1:**
```
Input: words = ["acca","bbbb","caca"], target = "aba"
Output: 6
Explanation: There are 6 ways to form target.
"aba" -> index 0 from "acca", index 1 from "bbbb", index 2 from "caca"
"aba" -> index 0 from "acca", index 1 from "caca", index 2 from "bbbb"
"aba" -> index 0 from "caca", index 1 from "bbbb", index 2 from "caca"
"aba" -> index 0 from "caca", index 1 from "caca", index 2 from "bbbb"
"aba" -> index 0 from "bbbb", index 1 from "acca", index 2 from "caca"
"aba" -> index 0 from "bbbb", index 1 from "caca", index 2 from "acca"
```

**Constraints:**
- `1 <= words.length <= 1000`
- `1 <= words[i].length <= 1000`
- All strings in `words` have the same length.
- `1 <= target.length <= 1000`
- `words[i]` and `target` contain only lowercase English letters.

## Intuition/Main Idea

We need to count ways to form target by picking characters from words, with the constraint that we pick characters in order.

**Core Algorithm:**
- Count frequency of each character at each position across all words
- Use DP: `dp[i][j]` = ways to form target[0...i] using positions 0...j
- For each position, we can either use it or skip it

**Why DP:** We need to count combinations. DP tracks number of ways to form target prefix using word positions.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Count ways to form target | DP - Lines 10-30 |
| Count character frequencies | Frequency counting - Lines 12-18 |
| Use or skip positions | DP recurrence - Lines 22-25 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int numWays(String[] words, String target) {
        int mod = 1000000007;
        int wordLen = words[0].length();
        int targetLen = target.length();
        
        // Count frequency of each character at each position
        // freq[pos][char] = count of char at position pos across all words
        int[][] freq = new int[wordLen][26];
        for (String word : words) {
            for (int i = 0; i < wordLen; i++) {
                freq[i][word.charAt(i) - 'a']++;
            }
        }
        
        // dp[i][j] = ways to form target[0...i] using positions 0...j
        // Size: (targetLen+1) x (wordLen+1) for base cases
        long[][] dp = new long[targetLen + 1][wordLen + 1];
        
        // Base case: empty target can be formed in 1 way
        for (int j = 0; j <= wordLen; j++) {
            dp[0][j] = 1;
        }
        
        for (int i = 1; i <= targetLen; i++) {
            for (int j = 1; j <= wordLen; j++) {
                char targetChar = target.charAt(i - 1);
                int charIndex = targetChar - 'a';
                
                // Option 1: Don't use position j-1
                dp[i][j] = dp[i][j - 1];
                
                // Option 2: Use position j-1 if it has targetChar
                if (freq[j - 1][charIndex] > 0) {
                    dp[i][j] = (dp[i][j] + 
                        dp[i - 1][j - 1] * freq[j - 1][charIndex]) % mod;
                }
            }
        }
        
        return (int) dp[targetLen][wordLen];
    }
}
```

## Dynamic Programming Analysis

**Intuition behind generating subproblems:**
- Subproblem: How many ways to form target[0...i] using positions 0...j?
- We can either use position j or skip it
- If we use it, multiply by frequency of target[i] at position j

**DP array size allocation:**
- Size: `(targetLen+1) x (wordLen+1)`
- Reason: Need to handle empty target/positions and track all combinations
- +1 for base cases

## Complexity Analysis

**Time Complexity:** $O(wordLen \times targetLen + wordLen \times words.length)$ for frequency counting and DP.

**Space Complexity:** $O(wordLen \times targetLen)$ for DP table.

## Similar Problems

- [Distinct Subsequences](https://leetcode.com/problems/distinct-subsequences/) - Count subsequences
- [Interleaving String](https://leetcode.com/problems/interleaving-string/) - Form string from two strings
- [Edit Distance](https://leetcode.com/problems/edit-distance/) - Similar DP pattern

