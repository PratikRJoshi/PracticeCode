# Maximum Score Words Formed by Letters

## Problem Description

**Problem Link:** [Maximum Score Words Formed by Letters](https://leetcode.com/problems/maximum-score-words-formed-by-letters/)

Given a list of `words`, a list of single `letters` (might be repeating), and a `score` array of size `26` that maps each letter to a score.

Return *the maximum score of **any** valid set of words formed by using the given letters* (each letter can only be used **once**). No two letters `score` are the same.

**Example 1:**

```
Input: words = ["dog","cat","dad","good"], letters = ["a","a","c","d","d","d","g","o","o"], score = [1,0,9,5,0,0,3,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0]
Output: 23
Explanation:
Score  a=1, c=9, d=5, g=3, o=2
If we use the words "dad" and "good", we get a score of (5+1+5) + (3+2+2+5) = 23.
Which is the maximum score possible.
```

**Example 2:**

```
Input: words = ["xxxz","ax","bx","cx"], letters = ["z","a","b","c","x","x","x"], score = [1,2,3,4,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
Output: 27
Explanation:
We use the word "ax" (score = 1+5 = 6), "bx" (score = 2+5 = 7), and "cx" (score = 3+5 = 8).
Total score = 6+7+8 = 27.
```

**Constraints:**
- `1 <= words.length <= 14`
- `1 <= words[i].length <= 15`
- `1 <= letters.length <= 100`
- `letters[i].length == 1`
- `score.length == 26`
- `0 <= score[i] <= 10`
- `words[i]`, `letters[i]` contains only lower case English letters.

## Intuition/Main Idea

This is a **backtracking** problem. We need to select words that maximize score while respecting letter constraints.

**Core Algorithm:**
1. Count available letters.
2. For each word, check if it can be formed with available letters.
3. Try including or excluding each word.
4. When including, subtract letters used and add score.
5. Track maximum score.

**Why backtracking works:** We systematically try all combinations of words. When we finish trying, we backtrack to try other combinations.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count letters | Letter counting - Lines 7-10 |
| Backtrack function | Backtrack method - Lines 12-30 |
| Base case: all processed | Index check - Lines 14-16 |
| Try excluding word | Exclude branch - Line 19 |
| Check if word can be formed | CanForm check - Lines 22-28 |
| Try including word | Include branch - Lines 29-32 |
| Return result | Return statement - Line 34 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public int maxScoreWords(String[] words, char[] letters, int[] score) {
        // Count available letters
        int[] letterCount = new int[26];
        for (char ch : letters) {
            letterCount[ch - 'a']++;
        }
        
        return backtrack(words, 0, letterCount, score);
    }
    
    private int backtrack(String[] words, int index, int[] letterCount, int[] score) {
        // Base case: all words processed
        if (index == words.length) {
            return 0;
        }
        
        // Option 1: Skip current word
        int maxScore = backtrack(words, index + 1, letterCount, score);
        
        // Option 2: Try including current word
        String word = words[index];
        int[] tempCount = letterCount.clone();
        int wordScore = 0;
        boolean canForm = true;
        
        // Check if word can be formed
        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';
            if (tempCount[idx] == 0) {
                canForm = false;
                break;
            }
            tempCount[idx]--;
            wordScore += score[idx];
        }
        
        if (canForm) {
            maxScore = Math.max(maxScore, wordScore + backtrack(words, index + 1, tempCount, score));
        }
        
        return maxScore;
    }
}
```

**Explanation of Key Code Sections:**

1. **Count Letters (Lines 7-10):** Count available letters.

2. **Backtrack (Lines 12-30):**
   - **Base Case (Lines 14-16):** When all words processed, return 0.
   - **Skip Word (Line 19):** Try excluding current word.
   - **Try Include (Lines 21-29):** Check if word can be formed:
     - **Check Letters (Lines 24-28):** Ensure enough letters available.
     - **Calculate Score (Line 27):** Sum letter scores.
     - **Recurse (Line 29):** Include word and try next words.

**Why backtracking:**
- We need to explore all word combinations.
- After trying including/excluding, we backtrack to try others.
- This ensures we find the maximum score.

**Example walkthrough for `words = ["dog","cat","dad","good"], letters = ["a","a","c","d","d","d","g","o","o"]`:**
- Try excluding "dog": score=0 → try including "cat": score=9+1+... → continue
- Try including "dad" and "good": score=(5+1+5)+(3+2+2+5)=23 ✓

## Complexity Analysis

- **Time Complexity:** $O(2^n \times m)$ where $n$ is number of words and $m$ is average word length. We try $2^n$ combinations.

- **Space Complexity:** $O(26)$ for letter count and $O(n)$ for recursion stack.

## Similar Problems

Problems that can be solved using similar backtracking patterns:

1. **1255. Maximum Score Words Formed by Letters** (this problem) - Backtracking selection
2. **2305. Fair Distribution of Cookies** - Backtracking distribution
3. **698. Partition to K Equal Sum Subsets** - Backtracking partition
4. **1947. Maximum Compatibility Score Sum** - Backtracking assignment
5. **638. Shopping Offers** - Backtracking with memoization
6. **526. Beautiful Arrangement** - Backtracking placement
7. **980. Unique Paths III** - Backtracking paths
8. **1219. Path with Maximum Gold** - Backtracking with optimization

