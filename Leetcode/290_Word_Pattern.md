# Word Pattern

## Problem Description

**Problem Link:** [Word Pattern](https://leetcode.com/problems/word-pattern/)

Given a `pattern` and a string `s`, find if `s` follows the same pattern.

Here **follow** means a full match, such that there is a bijection between a letter in `pattern` and a **non-empty** word in `s`.

**Example 1:**
```
Input: pattern = "abba", s = "dog cat cat dog"
Output: true
```

**Example 2:**
```
Input: pattern = "abba", s = "dog cat cat fish"
Output: false
```

**Example 3:**
```
Input: pattern = "aaaa", s = "dog cat cat dog"
Output: false
```

**Constraints:**
- `1 <= pattern.length <= 300`
- `pattern` contains only lower-case English letters.
- `1 <= s.length <= 3000`
- `s` contains only lowercase English letters and spaces `' '`.
- `s` **does not contain** any leading or trailing spaces.
- All the words in `s` are separated by a **single space**.

## Intuition/Main Idea

This problem requires establishing a bijection (one-to-one mapping) between characters in the pattern and words in the string. We need to ensure:
1. Each character in pattern maps to exactly one word
2. Each word maps to exactly one character in pattern

**Core Algorithm:**
- Use two HashMaps: one for pattern → word mapping, another for word → pattern mapping
- Split the string into words
- Check if lengths match
- For each character-word pair, verify both mappings are consistent

**Why two maps:** We need bidirectional checking to ensure bijection. A single map checking pattern→word isn't enough because multiple characters could map to the same word, which violates the bijection requirement.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Split string into words | `split()` method - Line 6 |
| Check length match | Length comparison - Line 7 |
| Map pattern char to word | `charToWord` HashMap - Lines 9, 15-18 |
| Map word to pattern char | `wordToChar` HashMap - Lines 10, 19-22 |
| Verify bijection | Both map checks - Lines 15-22 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public boolean wordPattern(String pattern, String s) {
        // Split the string into words array
        // We split by single space as per constraints
        String[] words = s.split(" ");
        
        // If lengths don't match, pattern cannot match
        // This is the first check: pattern length must equal number of words
        if (pattern.length() != words.length) {
            return false;
        }
        
        // Two maps to ensure bijection (one-to-one mapping)
        // charToWord: maps each character in pattern to its corresponding word
        // wordToChar: maps each word to its corresponding character in pattern
        // We need both to ensure no two characters map to same word AND no two words map to same character
        Map<Character, String> charToWord = new HashMap<>();
        Map<String, Character> wordToChar = new HashMap<>();
        
        // Iterate through pattern and words simultaneously
        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            String word = words[i];
            
            // Check if character already has a mapping
            if (charToWord.containsKey(ch)) {
                // If mapped word doesn't match current word, pattern is violated
                // This ensures each character maps to exactly one word
                if (!charToWord.get(ch).equals(word)) {
                    return false;
                }
            } else {
                // New character, add mapping
                charToWord.put(ch, word);
            }
            
            // Check if word already has a mapping
            if (wordToChar.containsKey(word)) {
                // If mapped character doesn't match current character, pattern is violated
                // This ensures each word maps to exactly one character
                if (wordToChar.get(word) != ch) {
                    return false;
                }
            } else {
                // New word, add mapping
                wordToChar.put(word, ch);
            }
        }
        
        // If we've processed all characters without conflicts, pattern matches
        return true;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the pattern (or number of words). We iterate through the pattern once, and HashMap operations are $O(1)$ on average.

**Space Complexity:** $O(n)$ for storing the two HashMaps. In the worst case, all characters map to different words, requiring $O(n)$ space.

## Similar Problems

- [Isomorphic Strings](https://leetcode.com/problems/isomorphic-strings/) - Similar bijection problem with characters
- [Word Pattern II](https://leetcode.com/problems/word-pattern-ii/) - Harder variant with recursive backtracking
- [Group Anagrams](https://leetcode.com/problems/group-anagrams/) - Uses HashMap for grouping

