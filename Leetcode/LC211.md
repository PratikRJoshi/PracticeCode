### 211. Add and Search Word - Data structure design
Problem: https://leetcode.com/problems/add-and-search-word-data-structure-design/description/

---

### Main Idea & Intuition

This problem requires us to design a data structure that supports two operations:
1. Adding a word
2. Searching for a word, where the search word may contain the wildcard character '.' which can match any letter

The key insight is to use a **Trie** (prefix tree) data structure, which is perfect for word storage and retrieval operations. The main challenge is handling the wildcard character during search, which requires a modified search algorithm that explores all possible paths when encountering a '.'.

#### Core Concept: Trie with Wildcard Search

1. **Trie Structure**: Each node represents a character and has links to child nodes for subsequent characters
2. **Word Marking**: Nodes that represent the end of a word are marked
3. **Regular Search**: Follow the path defined by each character in the word
4. **Wildcard Search**: When encountering a '.', explore all possible child paths

### Code Implementation with Detailed Comments

```java
class WordDictionary {
    // Define the Trie node structure
    private class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;
        
        public TrieNode() {
            children = new TrieNode[26]; // 26 lowercase English letters
            isEndOfWord = false;
        }
    }
    
    private TrieNode root;
    
    public WordDictionary() {
        root = new TrieNode();
    }
    
    /**
     * Adds a word to the data structure
     * Time Complexity: O(n) where n is the length of the word
     */
    public void addWord(String word) {
        TrieNode current = root;
        
        // Insert each character of the word into the Trie
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        
        // Mark the end of the word
        current.isEndOfWord = true;
    }
    
    /**
     * Returns true if the word is in the data structure.
     * A word could contain the dot character '.' to represent any one letter.
     * Time Complexity: Worst case O(26^m) where m is the length of the word
     * when the word is all dots
     */
    public boolean search(String word) {
        return searchHelper(word, 0, root);
    }
    
    /**
     * Recursive helper function to search for a word with wildcards
     * 
     * @param word    The word to search for
     * @param index   Current position in the word
     * @param node    Current node in the Trie
     * @return        True if the word exists
     */
    private boolean searchHelper(String word, int index, TrieNode node) {
        // Base case: we've processed all characters in the word
        if (index == word.length()) {
            return node.isEndOfWord;
        }
        
        char c = word.charAt(index);
        
        // Case 1: Current character is a wildcard '.'
        if (c == '.') {
            // Try all possible paths
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null && 
                    searchHelper(word, index + 1, node.children[i])) {
                    return true;
                }
            }
            // No valid path found
            return false;
        } 
        // Case 2: Regular character
        else {
            int childIndex = c - 'a';
            // If there's no path for this character, word doesn't exist
            if (node.children[childIndex] == null) {
                return false;
            }
            // Continue search with the next character
            return searchHelper(word, index + 1, node.children[childIndex]);
        }
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */