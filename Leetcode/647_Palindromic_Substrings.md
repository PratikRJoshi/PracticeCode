# Palindromic Substrings

## Problem Description

**Problem Link:** [Palindromic Substrings](https://leetcode.com/problems/palindromic-substrings/)

Given a string `s`, return *the number of **palindromic substrings** in it*.

A string is a **palindrome** when it reads the same backward as forward.

A **substring** is a contiguous sequence of characters within the string.

**Example 1:**
```
Input: s = "abc"
Output: 3
Explanation: Three palindromic strings: "a", "b", "c".
```

**Example 2:**
```
Input: s = "aaa"
Output: 6
Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
```

**Constraints:**
- `1 <= s.length <= 1000`
- `s` consists of lowercase English letters.

## Intuition/Main Idea

We can count palindromic substrings by expanding around each possible center (odd and even length palindromes).

**Core Algorithm:**
1. For each position, expand around it as center for odd-length palindromes.
2. For each position, expand around it and next position for even-length palindromes.
3. Count all palindromes found.

**Alternative DP Approach:**
1. Use DP to check if each substring is a palindrome.
2. Count all palindromic substrings.

**Why expansion works:** Every palindrome has a center. By checking all possible centers and expanding, we find all palindromes without missing any.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count palindromes | Count variable - Line 5 |
| Expand around centers | For loop - Line 7 |
| Expand for odd length | expandAroundCenter call - Line 9 |
| Expand for even length | expandAroundCenter call - Line 10 |
| Return count | Return statement - Line 12 |

## Final Java Code & Learning Pattern

### Expansion Approach (Optimal)

```java
class Solution {
    public int countSubstrings(String s) {
        int count = 0;
        int n = s.length();
        
        for (int i = 0; i < n; i++) {
            // Count odd-length palindromes centered at i
            count += expandAroundCenter(s, i, i);
            
            // Count even-length palindromes centered between i and i+1
            count += expandAroundCenter(s, i, i + 1);
        }
        
        return count;
    }
    
    private int expandAroundCenter(String s, int left, int right) {
        int count = 0;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++;
            left--;
            right++;
        }
        return count;
    }
}
```

### DP Approach

```java
class Solution {
    public int countSubstrings(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int count = 0;
        
        // Check all substrings
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    // Single character is always palindrome
                    dp[i][j] = true;
                    count++;
                } else if (j == i + 1) {
                    // Two characters: check if equal
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = true;
                        count++;
                    }
                } else {
                    // More than two: check ends and middle
                    if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                        dp[i][j] = true;
                        count++;
                    }
                }
            }
        }
        
        return count;
    }
}
```

**Explanation of Key Code Sections:**

**Expansion Approach:**

1. **For Each Center (Line 7):** Check each position as a potential center.

2. **Odd-Length Palindromes (Line 9):** Expand around `(i, i)` - single character center.

3. **Even-Length Palindromes (Line 10):** Expand around `(i, i+1)` - center between two characters.

4. **Expand Function (Lines 15-21):** Expand outward while characters match, counting each palindrome found.

**DP Approach:**

1. **DP Table (Line 6):** `dp[i][j]` = is `s[i..j]` a palindrome.

2. **Check Substrings (Lines 10-25):** For each substring:
   - **Single char (Lines 12-14):** Always a palindrome.
   - **Two chars (Lines 15-19):** Check if equal.
   - **More chars (Lines 20-24):** Check ends and if middle is palindrome.

**Why expansion is better:**
- **Time:** O(n²) but with better constant factors.
- **Space:** O(1) vs O(n²) for DP.
- **Simpler:** More intuitive and easier to understand.

**Example walkthrough for `s = "aaa"`:**
- i=0: odd→"a","aaa" (2), even→"aa" (1) → 3
- i=1: odd→"a","aaa" (2), even→"aa" (1) → 3
- i=2: odd→"a" (1), even→0 → 1
- Total: 6 ✓

## Complexity Analysis

- **Time Complexity:** $O(n^2)$ where $n$ is the length of `s`. In worst case, we expand around each center up to $n/2$ positions.

- **Space Complexity:** 
  - Expansion: $O(1)$ extra space.
  - DP: $O(n^2)$ for the DP table.

## Similar Problems

Problems that can be solved using similar palindrome patterns:

1. **647. Palindromic Substrings** (this problem) - Count palindromes
2. **5. Longest Palindromic Substring** - Find longest palindrome
3. **516. Longest Palindromic Subsequence** - LCS variant
4. **131. Palindrome Partitioning** - Partition into palindromes
5. **132. Palindrome Partitioning II** - Minimum cuts
6. **1216. Valid Palindrome III** - Palindrome with deletions
7. **1312. Minimum Insertion Steps to Make a String Palindrome** - Minimum insertions
8. **125. Valid Palindrome** - Check if palindrome

