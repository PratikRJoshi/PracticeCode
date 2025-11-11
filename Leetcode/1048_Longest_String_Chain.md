# Longest String Chain

## Problem Description

**Problem Link:** [Longest String Chain](https://leetcode.com/problems/longest-string-chain/)

You are given an array of `words` where each word consists of lowercase English letters.

`wordA` is a **predecessor** of `wordB` if and only if we can insert **exactly one letter** anywhere in `wordA` **without changing the order of the other characters** to make it equal to `wordB`.

- For example, `"abc"` is a predecessor of `"abac"`, while `"cba"` is not a predecessor of `"bcad"`.

A **word chain** is a sequence of words `[word1, word2, ..., wordk]` with `k >= 1`, where `word1` is a predecessor of `word2`, `word2` is a predecessor of `word3`, and so on. A single word is trivially a word chain with `k == 1`.

Return *the length of the **longest possible word chain** with words chosen from the given list of `words`*.

**Example 1:**
```
Input: words = ["a","b","ba","bca","bda","bdca"]
Output: 4
Explanation: One of the longest word chains is ["a","ba","bca","bdca"].
```

**Example 2:**
```
Input: words = ["xbc","pcxbcf","xb","cxbc","pcxbc"]
Output: 5
Explanation: All the words can be put in a word chain ["xb", "xbc", "cxbc", "pcxbc", "pcxbcf"].
```

**Constraints:**
- `1 <= words.length <= 1000`
- `1 <= words[i].length <= 16`
- `words[i]` only consists of lowercase English letters.

## Intuition/Main Idea

This is a **Longest Increasing Subsequence** variant. We need to find the longest chain where each word is a predecessor of the next.

**Core Algorithm:**
1. Sort words by length (shorter words come first).
2. Use DP where `dp[word]` = length of longest chain ending at `word`.
3. For each word, check all shorter words to see if they are predecessors.
4. If a shorter word is a predecessor, extend the chain.

**Why sorting by length works:** A predecessor must be shorter by exactly 1 character. By sorting, we ensure we process shorter words before longer ones, so when processing a word, all its potential predecessors have already been processed.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Sort by length | Custom comparator - Line 7 |
| DP map for chain length | HashMap - Line 9 |
| Check predecessors | For loop - Lines 11-18 |
| Check if predecessor | isPredecessor method - Lines 20-32 |
| Update chain length | DP update - Line 16 |
| Track maximum length | Max tracking - Line 17 |
| Return result | Return statement - Line 19 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
import java.util.*;

class Solution {
    public int longestStrChain(String[] words) {
        // Sort words by length
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        
        // DP: map word to length of longest chain ending at that word
        Map<String, Integer> dp = new HashMap<>();
        int maxChain = 1;
        
        for (String word : words) {
            int currentChain = 1;
            
            // Check all possible predecessors (remove one character)
            for (int i = 0; i < word.length(); i++) {
                String predecessor = word.substring(0, i) + word.substring(i + 1);
                if (dp.containsKey(predecessor)) {
                    currentChain = Math.max(currentChain, dp.get(predecessor) + 1);
                }
            }
            
            dp.put(word, currentChain);
            maxChain = Math.max(maxChain, currentChain);
        }
        
        return maxChain;
    }
}
```

**Alternative Approach (Check All Shorter Words):**

```java
import java.util.*;

class Solution {
    public int longestStrChain(String[] words) {
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        
        Map<String, Integer> dp = new HashMap<>();
        int maxChain = 1;
        
        for (String word : words) {
            int currentChain = 1;
            
            // Check all words that are one character shorter
            for (String prevWord : dp.keySet()) {
                if (isPredecessor(prevWord, word)) {
                    currentChain = Math.max(currentChain, dp.get(prevWord) + 1);
                }
            }
            
            dp.put(word, currentChain);
            maxChain = Math.max(maxChain, currentChain);
        }
        
        return maxChain;
    }
    
    private boolean isPredecessor(String shorter, String longer) {
        if (longer.length() != shorter.length() + 1) {
            return false;
        }
        
        int i = 0, j = 0;
        while (i < shorter.length() && j < longer.length()) {
            if (shorter.charAt(i) == longer.charAt(j)) {
                i++;
            }
            j++;
        }
        
        return i == shorter.length();
    }
}
```

**Explanation of Key Code Sections:**

1. **Sort by Length (Line 7):** Sort words by length so shorter words are processed first. This ensures all potential predecessors are already in the DP map.

2. **DP Map (Line 9):** Use a HashMap to store the longest chain length ending at each word.

3. **Check Predecessors (Lines 13-17):** For each word, generate all possible predecessors by removing one character:
   - **Generate Predecessor (Line 14):** Remove character at position `i`.
   - **Check DP (Line 15):** If predecessor exists in DP, extend the chain.
   - **Update (Line 16):** Take the maximum chain length.

4. **Track Maximum (Line 18):** Keep track of the overall maximum chain length.

**Why removing one character works:**
- A predecessor of `word` is a word that can become `word` by adding one character.
- Equivalently, `word` can become its predecessor by removing one character.
- By generating all possible removals, we find all predecessors.

**Example walkthrough for `words = ["a","b","ba","bca","bda","bdca"]`:**
- After sort: ["a","b","ba","bca","bda","bdca"]
- "a": chain=1
- "b": chain=1
- "ba": predecessors "a","b" → chain=max(1+1,1+1)=2
- "bca": predecessor "ba" → chain=2+1=3
- "bda": predecessor "ba" → chain=2+1=3
- "bdca": predecessors "bca","bda" → chain=max(3+1,3+1)=4
- Result: 4 ✓

## Complexity Analysis

- **Time Complexity:** $O(n \times L^2)$ where $n$ is the number of words and $L$ is the average word length. For each word, we generate up to $L$ predecessors and check them.

- **Space Complexity:** $O(n)$ for the DP map.

## Similar Problems

Problems that can be solved using similar LIS or chain DP patterns:

1. **1048. Longest String Chain** (this problem) - String chain LIS
2. **300. Longest Increasing Subsequence** - Classic LIS
3. **354. Russian Doll Envelopes** - 2D LIS
4. **368. Largest Divisible Subset** - Divisibility chain
5. **646. Maximum Length of Pair Chain** - Interval chain
6. **673. Number of Longest Increasing Subsequence** - Count LIS
7. **1027. Longest Arithmetic Sequence** - Arithmetic progression
8. **1218. Longest Arithmetic Subsequence of Given Difference** - Fixed difference

