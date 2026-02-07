# Add and Search Word - Data structure design

## Problem Description

**Problem Link:** [Add and Search Word - Data structure design](https://leetcode.com/problems/add-and-search-word-data-structure-design/)

Design a data structure that supports adding new words and finding if a string matches any previously added string.

Implement the `WordDictionary` class:

- `WordDictionary()` Initializes the object.
- `void addWord(word)` Adds `word` to the data structure, it can be matched later.
- `bool search(word)` Returns `true` if there is any string in the data structure that matches `word` or `false` otherwise. `word` may contain dots `'.'` where dots can be matched with any letter.

**Example 1:**
```
Input
["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
Output
[null,null,null,null,false,true,true,true]

Explanation
WordDictionary wordDictionary = new WordDictionary();
wordDictionary.addWord("bad");
wordDictionary.addWord("dad");
wordDictionary.addWord("mad");
wordDictionary.search("pad"); // return False
wordDictionary.search("bad"); // return True
wordDictionary.search(".ad"); // return True
wordDictionary.search("b.."); // return True
```

**Constraints:**
- `1 <= word.length <= 25`
- `word` in `addWord` consists of lowercase English letters.
- `word` in `search` consist of `'.'` or lowercase English letters.
- There will be at most `10^4` calls in total to `addWord` and `search`.

## Intuition/Main Idea

This is similar to Trie, but search needs to handle wildcard '.' that matches any character.

**Core Algorithm:**
- Use Trie data structure to store words
- `addWord`: Insert word into Trie normally
- `search`: Use DFS to search, when encountering '.', try all possible children

**Why Trie:** Efficient prefix matching and word storage. The wildcard '.' requires exploring multiple paths, which DFS handles naturally.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Add word | Trie insert - Lines 11-22 |
| Search with wildcard | DFS search - Lines 24-44 |
| Handle '.' character | Try all children - Lines 35-40 |

## Final Java Code & Learning Pattern (Full Content)

```java
class WordDictionary {
    // Trie node class
    class TrieNode {
        TrieNode[] children;
        boolean isWord;
        
        TrieNode() {
            children = new TrieNode[26]; // 26 lowercase letters
            isWord = false;
        }
    }
    
    private TrieNode root;
    
    public WordDictionary() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode node = root;
        
        // Insert word into Trie
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            
            // Create child node if it doesn't exist
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            
            // Move to child node
            node = node.children[index];
        }
        
        // Mark end of word
        node.isWord = true;
    }
    
    public boolean search(String word) {
        // Use DFS to search with wildcard support
        return searchHelper(word, 0, root);
    }
    
    private boolean searchHelper(String word, int index, TrieNode node) {
        // Mentor mental model:
        // - `node`  = where you are in the trie right now
        // - `index` = which character of `word` you are trying to match

        // Base case: we have consumed the entire search string/pattern.
        // This is a valid match ONLY if this trie node represents the end of a word.
        if (index == word.length()) {
            return node.isWord;
        }

        // Current character we need to match.
        char ch = word.charAt(index);

        // Case 1: '.' is a wildcard that can match ANY single character.
        if (ch == '.') {
            // So we try every possible child from the current node.
            // If any child path can match the rest of the pattern, we're done.
            for (TrieNode child : node.children) {
                // Only recurse if the child exists.
                if (child != null && searchHelper(word, index + 1, child)) {
                    return true;
                }
            }
            // None of the children worked => no match from this node.
            return false;
        } else {
            // Case 2: normal letter.
            // We must follow exactly one edge for this letter.
            int charIndex = ch - 'a';

            // If that edge doesn't exist, this path is impossible.
            if (node.children[charIndex] == null) {
                return false;
            }

            // Otherwise, move down one level and match the next character.
            return searchHelper(word, index + 1, node.children[charIndex]);
        }
    }
}
```

## Complexity Analysis

**Time Complexity:**
- `addWord`: $O(m)$ where $m$ is word length
- `search`: $O(26^m)$ in worst case (all '.'), but typically much better

**Space Complexity:** $O(ALPHABET_SIZE * N * M)$ where $N$ is number of words and $M$ is average length.

## Similar Problems

- [Implement Trie (Prefix Tree)](https://leetcode.com/problems/implement-trie-prefix-tree/) - Basic Trie implementation
- [Word Search II](https://leetcode.com/problems/word-search-ii/) - Trie + DFS on board
- [Design Add and Search Words Data Structure](https://leetcode.com/problems/design-add-and-search-words-data-structure/) - Same problem

