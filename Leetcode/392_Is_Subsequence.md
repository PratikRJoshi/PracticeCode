# Is Subsequence

## Problem Description

**Problem Link:** [Is Subsequence](https://leetcode.com/problems/is-subsequence/)

Given two strings `s` and `t`, return `true` if `s` is a **subsequence** of `t`, or `false` otherwise.

A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (i.e., `"ace"` is a subsequence of `"abcde"` while `"aec"` is not).

**Example 1:**
```
Input: s = "abc", t = "ahbgdc"
Output: true
```

**Example 2:**
```
Input: s = "axc", t = "ahbgdc"
Output: false
```

**Constraints:**
- `0 <= s.length <= 100`
- `0 <= t.length <= 10^4`
- `s` and `t` consist only of lowercase English letters.

## Intuition/Main Idea

This is a classic two-pointer problem. We need to check if all characters of `s` appear in `t` in the same order.

**Core Algorithm:**
- Use two pointers: one for `s` (subsequence), one for `t` (main string)
- Traverse `t` with one pointer
- When we find a character in `t` that matches current character in `s`, advance `s` pointer
- If we finish traversing `s`, it's a subsequence

**Why two pointers:** We need to match characters in order. The two-pointer approach allows us to efficiently check if characters of `s` appear in `t` in sequence without needing to backtrack.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Check if s is subsequence of t | Two-pointer traversal - Lines 6-14 |
| Match characters in order | Pointer advancement - Lines 10-11 |
| Track progress in s | `sIndex` pointer - Lines 7, 11 |
| Traverse t | Loop through t - Line 6 |
| Verify all s matched | Final check - Line 15 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public boolean isSubsequence(String s, String t) {
        // Two-pointer approach
        // sIndex tracks position in subsequence string s
        // We'll traverse t and match characters of s in order
        int sIndex = 0;
        
        // Traverse through the main string t
        // We need to find all characters of s in t in the same order
        for (int i = 0; i < t.length(); i++) {
            // If we've matched all characters of s, we can return early
            // This optimization helps when s is much shorter than t
            if (sIndex == s.length()) {
                return true;
            }
            
            // If current character in t matches current character in s
            // Advance the pointer in s to look for next character
            if (t.charAt(i) == s.charAt(sIndex)) {
                sIndex++;
            }
            // If no match, continue searching in t
            // We don't advance sIndex, keeping it at current position
        }
        
        // After traversing t, check if we matched all characters of s
        // sIndex equals s.length() means we found all characters in order
        return sIndex == s.length();
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of `t`. We traverse `t` once, and each character comparison is $O(1)$.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space for the pointer variable.

## Similar Problems

- [Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/) - DP variant finding longest subsequence
- [Merge Sorted Array](https://leetcode.com/problems/merge-sorted-array/) - Uses similar two-pointer technique
- [Two Sum II](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) - Two-pointer approach on sorted array

