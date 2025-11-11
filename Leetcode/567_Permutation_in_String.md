# Permutation in String

## Problem Description

**Problem Link:** [Permutation in String](https://leetcode.com/problems/permutation-in-string/)

Given two strings `s1` and `s2`, return `true` *if `s2` contains a permutation of `s1`, or `false` otherwise*.

In other words, return `true` if one of `s1`'s permutations is the substring of `s2`.

**Example 1:**
```
Input: s1 = "ab", s2 = "eidbaooo"
Output: true
Explanation: s2 contains one permutation of s1 ("ba").
```

**Example 2:**
```
Input: s1 = "ab", s2 = "eidboaoo"
Output: false
```

**Constraints:**
- `1 <= s1.length, s2.length <= 10^4`
- `s1` and `s2` consist of lowercase English letters.

## Intuition/Main Idea

This is a **sliding window** problem with **frequency counting**. We need to check if any window in `s2` has the same character frequency as `s1`.

**Core Algorithm:**
1. Count frequency of each character in `s1`.
2. Use a sliding window of size `s1.length()` in `s2`.
3. Maintain frequency count of characters in the current window.
4. If frequencies match, return true.
5. Slide the window and update frequencies.

**Why sliding window works:** We need to check all contiguous substrings of length `s1.length()` in `s2`. A sliding window efficiently processes these substrings by adding one character and removing one character at a time.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count frequency of s1 | Frequency array - Lines 7-10 |
| Initialize sliding window | Window frequency - Lines 12-15 |
| Check if frequencies match | `matches` method - Lines 32-38 |
| Slide window and update | For loop - Lines 17-29 |
| Add new character | Frequency increment - Line 21 |
| Remove old character | Frequency decrement - Line 24 |
| Return result | Return statement - Line 30 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        
        if (len1 > len2) {
            return false;
        }
        
        // Frequency array for s1
        int[] count1 = new int[26];
        for (char c : s1.toCharArray()) {
            count1[c - 'a']++;
        }
        
        // Frequency array for sliding window in s2
        int[] count2 = new int[26];
        
        // Initialize window: first len1 characters
        for (int i = 0; i < len1; i++) {
            count2[s2.charAt(i) - 'a']++;
        }
        
        // Check if initial window matches
        if (matches(count1, count2)) {
            return true;
        }
        
        // Slide the window
        for (int i = len1; i < len2; i++) {
            // Add new character (right end of window)
            count2[s2.charAt(i) - 'a']++;
            
            // Remove old character (left end of window)
            count2[s2.charAt(i - len1) - 'a']--;
            
            // Check if current window matches
            if (matches(count1, count2)) {
                return true;
            }
        }
        
        return false;
    }
    
    // Check if two frequency arrays match
    private boolean matches(int[] count1, int[] count2) {
        for (int i = 0; i < 26; i++) {
            if (count1[i] != count2[i]) {
                return false;
            }
        }
        return true;
    }
}
```

**Explanation of Key Code Sections:**

1. **Edge Case (Lines 5-7):** If `s1` is longer than `s2`, it's impossible to find a permutation.

2. **Count s1 Frequency (Lines 9-12):** Create a frequency array for `s1`. This is our target pattern.

3. **Initialize Window (Lines 14-17):** Create frequency array for the first `len1` characters of `s2`. This is our initial sliding window.

4. **Check Initial Window (Lines 19-21):** Check if the initial window matches `s1`.

5. **Slide Window (Lines 23-29):** For each new position:
   - **Add Right Character (Line 24):** Include the new character at the right end.
   - **Remove Left Character (Line 27):** Exclude the character that's no longer in the window.
   - **Check Match (Lines 29-31):** Check if the updated window matches `s1`.

6. **Match Function (Lines 34-40):** Compare two frequency arrays element by element.

**Why sliding window is efficient:**
- **O(1) update:** Adding/removing one character takes constant time.
- **O(26) comparison:** Checking if frequencies match takes O(26) = O(1) time.
- **Overall:** O(n) time where n is length of s2.

**Optimization:** Instead of comparing entire arrays each time, we can track the number of matching characters:

```java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        
        if (len1 > len2) return false;
        
        int[] count = new int[26];
        for (char c : s1.toCharArray()) {
            count[c - 'a']++;
        }
        
        int matches = 0;
        // Initialize window
        for (int i = 0; i < len1; i++) {
            int idx = s2.charAt(i) - 'a';
            count[idx]--;
            if (count[idx] == 0) matches++;
            else if (count[idx] == -1) matches--;
        }
        
        if (matches == 26) return true;
        
        // Slide window
        for (int i = len1; i < len2; i++) {
            // Remove left
            int leftIdx = s2.charAt(i - len1) - 'a';
            count[leftIdx]++;
            if (count[leftIdx] == 0) matches++;
            else if (count[leftIdx] == 1) matches--;
            
            // Add right
            int rightIdx = s2.charAt(i) - 'a';
            count[rightIdx]--;
            if (count[rightIdx] == 0) matches++;
            else if (count[rightIdx] == -1) matches--;
            
            if (matches == 26) return true;
        }
        
        return false;
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the length of `s2`. We process each character at most twice (once when added, once when removed).

- **Space Complexity:** $O(1)$ as we only use fixed-size arrays (26 elements).

## Similar Problems

Problems that can be solved using similar sliding window patterns:

1. **567. Permutation in String** (this problem) - Sliding window with frequency
2. **438. Find All Anagrams in a String** - Same pattern, find all occurrences
3. **76. Minimum Window Substring** - Sliding window with frequency
4. **3. Longest Substring Without Repeating Characters** - Sliding window
5. **424. Longest Repeating Character Replacement** - Sliding window with replacement
6. **209. Minimum Size Subarray Sum** - Sliding window with sum
7. **239. Sliding Window Maximum** - Sliding window maximum
8. **30. Substring with Concatenation of All Words** - Sliding window variant
9. **159. Longest Substring with At Most Two Distinct Characters** - Sliding window
10. **340. Longest Substring with At Most K Distinct Characters** - Sliding window

