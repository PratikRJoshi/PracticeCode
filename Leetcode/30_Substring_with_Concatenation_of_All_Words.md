# Substring with Concatenation of All Words

## Problem Description

**Problem Link:** [Substring with Concatenation of All Words](https://leetcode.com/problems/substring-with-concatenation-of-all-words/)

You are given a string `s` and an array of strings `words`. All the strings of `words` are of **the same length**.

A **concatenated substring** in `s` is a substring that contains all the strings in `words` concatenated in **any order**, and all characters of such substring should be contiguous.

Return *the starting indices of all the concatenated substrings in* `s`. You can return the answer in **any order**.

**Example 1:**
```
Input: s = "barfoothefoobarman", words = ["foo","bar"]
Output: [0,9]
Explanation: Since words.length == 2 and words[i].length == 3, the concatenated substring has to be of length 6.
The substring starting at 0 is "barfoo". It is the concatenation of ["bar","foo"] which is a permutation of words.
The substring starting at 9 is "foobar". It is the concatenation of ["foo","bar"] which is a permutation of words.
```

**Example 2:**
```
Input: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
Output: []
Explanation: Since words.length == 4 and words[i].length == 4, the concatenated substring has to be of length 16.
There is no substring of length 16 in s that is equal to the concatenation of any permutation of words.
```

**Constraints:**
- `1 <= s.length <= 10^4`
- `1 <= words.length <= 5000`
- `1 <= words[i].length <= 30`
- `s` and `words[i]` consist of lowercase English letters.

## Intuition/Main Idea

This is a sliding window problem with word matching. We need to find all starting positions where we can form a concatenation of all words.

**Core Algorithm:**
- Calculate total length needed: `words.length * words[0].length()`
- Use sliding window of this fixed length
- For each window, check if it contains all words exactly once
- Use HashMap to count word frequencies

**Why sliding window:** We need to check all possible substrings of fixed length. The sliding window efficiently moves through the string, and for each position, we verify if it matches all words.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find concatenated substrings | Sliding window - Lines 7-30 |
| Check all words present | Word frequency map - Lines 9-11, 20-28 |
| Handle word permutations | HashMap comparison - Lines 20-28 |
| Fixed window size | Window length calculation - Line 8 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || s.length() == 0 || words == null || words.length == 0) {
            return result;
        }
        
        // Calculate total length of concatenated substring
        int wordLen = words[0].length();
        int totalLen = words.length * wordLen;
        
        // Create frequency map of words
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        
        // Slide window through string
        // We check each possible starting position
        for (int i = 0; i <= s.length() - totalLen; i++) {
            // Extract substring of total length
            String window = s.substring(i, i + totalLen);
            
            // Check if this window contains all words
            Map<String, Integer> seen = new HashMap<>();
            boolean valid = true;
            
            // Check each word in the window
            for (int j = 0; j < words.length; j++) {
                // Extract word at position j
                String word = window.substring(j * wordLen, (j + 1) * wordLen);
                
                // Check if word is in our word list
                if (!wordCount.containsKey(word)) {
                    valid = false;
                    break;
                }
                
                // Count occurrences
                seen.put(word, seen.getOrDefault(word, 0) + 1);
                
                // Check if we've seen this word too many times
                if (seen.get(word) > wordCount.get(word)) {
                    valid = false;
                    break;
                }
            }
            
            // If window is valid (contains all words exactly), add starting index
            if (valid && seen.equals(wordCount)) {
                result.add(i);
            }
        }
        
        return result;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n \cdot m \cdot k)$ where $n$ is length of `s`, `m` is number of words, and `k` is length of each word. For each starting position, we check all words in the window.

**Space Complexity:** $O(m)$ for storing word frequency maps.

## Similar Problems

- [Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/) - Similar sliding window with character matching
- [Find All Anagrams in a String](https://leetcode.com/problems/find-all-anagrams-in-a-string/) - Similar pattern matching
- [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/) - Sliding window variant

