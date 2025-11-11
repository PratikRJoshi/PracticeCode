# Word Ladder

## Problem Description

**Problem Link:** [Word Ladder](https://leetcode.com/problems/word-ladder/)

A **transformation sequence** from word `beginWord` to word `endWord` using a dictionary `wordList` is a sequence of words `beginWord -> s1 -> s2 -> ... -> sk` such that:

- Every adjacent pair of words differs by a single letter.
- Every `si` for `1 <= i <= k` is in `wordList`. Note that `beginWord` is not required to be in `wordList`.
- `sk == endWord`

Given two words, `beginWord` and `endWord`, and a dictionary `wordList`, return *the **number of words** in the **shortest transformation sequence** from `beginWord` to `endWord`, or `0` if no such sequence exists*.

**Example 1:**
```
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: 5
Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> "cog", which is 5 words long.
```

**Example 2:**
```
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: 0
Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
```

**Constraints:**
- `1 <= beginWord.length <= 10`
- `endWord.length == beginWord.length`
- `1 <= wordList.length <= 5000`
- `wordList[i].length == beginWord.length`
- `beginWord`, `endWord`, and `wordList[i]` consist of lowercase English letters.
- `beginWord != endWord`
- All the words in `wordList` are **unique**.

## Intuition/Main Idea

This is a **shortest path** problem that can be solved using **BFS (Breadth-First Search)**. We treat words as nodes and edges connect words that differ by one letter.

**Core Algorithm:**
1. Use BFS starting from `beginWord`.
2. For each word, generate all words that differ by one letter.
3. If we reach `endWord`, return the level (number of words).
4. Use a visited set to avoid cycles.

**Why BFS works:** BFS explores level by level, so the first time we reach `endWord`, we've found the shortest path. This is because all paths of length `k` are explored before paths of length `k+1`.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Convert wordList to set | HashSet creation - Line 7 |
| BFS queue | Queue initialization - Line 9 |
| Track visited words | Visited set - Line 10 |
| Process level by level | Level-based BFS - Lines 12-32 |
| Generate neighbor words | Character replacement - Lines 20-28 |
| Check if reached endWord | End check - Line 18 |
| Return shortest path length | Return statement - Line 33 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // Convert wordList to set for O(1) lookup
        Set<String> wordSet = new HashSet<>(wordList);
        
        // Check if endWord exists in wordList
        if (!wordSet.contains(endWord)) {
            return 0;
        }
        
        // BFS queue: store word and its level
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        
        // Track visited words
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        
        int level = 1;  // Start at level 1 (beginWord)
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            // Process all words at current level
            for (int i = 0; i < size; i++) {
                String current = queue.poll();
                
                // Check if we reached endWord
                if (current.equals(endWord)) {
                    return level;
                }
                
                // Generate all words that differ by one character
                char[] chars = current.toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    char original = chars[j];
                    
                    // Try all possible characters
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                        
                        chars[j] = c;
                        String nextWord = new String(chars);
                        
                        // If word exists and not visited, add to queue
                        if (wordSet.contains(nextWord) && !visited.contains(nextWord)) {
                            visited.add(nextWord);
                            queue.offer(nextWord);
                        }
                    }
                    
                    // Restore original character
                    chars[j] = original;
                }
            }
            
            level++;  // Move to next level
        }
        
        return 0;  // No path found
    }
}
```

**Explanation of Key Code Sections:**

1. **Word Set (Line 7):** Convert `wordList` to a `HashSet` for O(1) lookup when checking if a word exists.

2. **End Word Check (Lines 9-11):** If `endWord` is not in `wordList`, return 0 immediately.

3. **BFS Setup (Lines 13-17):** Initialize queue with `beginWord` and mark it as visited. Start at level 1.

4. **Level-by-Level Processing (Lines 19-45):**
   - **Process Current Level (Lines 20-21):** Process all words at the current level before moving to the next.
   - **Check End (Lines 23-25):** If we've reached `endWord`, return the current level.
   - **Generate Neighbors (Lines 27-40):** For each position in the word, try replacing it with each letter 'a'-'z':
     - **Skip Same Character (Line 30):** Don't replace with the same character.
     - **Check Validity (Line 35):** If the new word exists in `wordSet` and hasn't been visited, add it to the queue.
   - **Restore Character (Line 39):** Restore the original character before trying the next position.
   - **Next Level (Line 43):** Increment level after processing all words at current level.

**Why BFS finds shortest path:**
- **Level order:** BFS explores all nodes at distance 1, then distance 2, etc.
- **First occurrence:** The first time we reach `endWord` is via the shortest path.
- **Complete exploration:** We explore all possibilities at each level before moving to the next.

**Optimization - Bidirectional BFS:**

```java
import java.util.*;

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return 0;
        
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        beginSet.add(beginWord);
        endSet.add(endWord);
        
        Set<String> visited = new HashSet<>();
        int level = 1;
        
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            // Always expand the smaller set
            if (beginSet.size() > endSet.size()) {
                Set<String> temp = beginSet;
                beginSet = endSet;
                endSet = temp;
            }
            
            Set<String> nextSet = new HashSet<>();
            for (String word : beginSet) {
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char original = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                        chars[i] = c;
                        String nextWord = new String(chars);
                        
                        if (endSet.contains(nextWord)) {
                            return level + 1;
                        }
                        
                        if (wordSet.contains(nextWord) && !visited.contains(nextWord)) {
                            visited.add(nextWord);
                            nextSet.add(nextWord);
                        }
                    }
                    chars[i] = original;
                }
            }
            beginSet = nextSet;
            level++;
        }
        
        return 0;
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(M \times N \times 26)$ where $M$ is the length of words and $N$ is the number of words in `wordList`. For each word, we try 26 characters at each position.

- **Space Complexity:** $O(N)$ for the queue, visited set, and word set.

## Similar Problems

Problems that can be solved using similar BFS patterns:

1. **127. Word Ladder** (this problem) - BFS shortest path
2. **126. Word Ladder II** - BFS with path reconstruction
3. **433. Minimum Genetic Mutation** - Similar BFS pattern
4. **752. Open the Lock** - BFS with state transitions
5. **1091. Shortest Path in Binary Matrix** - BFS on grid
6. **200. Number of Islands** - BFS/DFS on grid
7. **994. Rotting Oranges** - BFS from multiple sources
8. **542. 01 Matrix** - Multi-source BFS
9. **1162. As Far from Land as Possible** - Multi-source BFS
10. **1293. Shortest Path in a Grid with Obstacles Elimination** - BFS with state

