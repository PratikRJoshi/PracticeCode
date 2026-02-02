### 212. Word Search II
Problem: https://leetcode.com/problems/word-search-ii/description/

---

### Main Idea & Intuition

This problem is an extension of Word Search (LC79), but instead of searching for a single word, we need to find all words from a given dictionary that exist in the grid. The key insight is to combine two techniques:

1. **Trie Data Structure**: To efficiently store and search for words from the dictionary
2. **Backtracking**: To explore all possible paths in the grid, similar to Word Search I

#### Core Concept: Trie + Backtracking

1. **Build a Trie**: Insert all dictionary words into a Trie for efficient prefix matching
2. **Backtracking Search**: Start from each cell and explore all possible paths
3. **Word Collection**: When we find a complete word in the Trie, add it to our result
4. **Optimization**: Remove found words from the Trie to avoid duplicates

#### Understanding the Trie Structure

A Trie (prefix tree) is perfect for this problem because:
- It allows us to check if a path we're exploring could potentially form a valid word
- We can terminate early if a prefix doesn't exist in our dictionary
- We can mark complete words in the Trie nodes

### Code Implementation with Detailed Comments

```java
class Solution {
    // Four possible movement directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
    // Result set to store found words
    private Set<String> result;
    
    // Trie node definition
    class TrieNode {
        TrieNode[] children;
        String word;  // Store complete word at leaf nodes
        
        public TrieNode() {
            children = new TrieNode[26];  // 26 lowercase English letters
            word = null;
        }
    }
    
    public List<String> findWords(char[][] board, String[] words) {
        // Build the Trie with all words
        TrieNode root = buildTrie(words);
        result = new HashSet<>();  // Use Set to avoid duplicates
        
        int rows = board.length;
        int cols = board[0].length;
        
        // Try each cell as a starting point
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Start backtracking from this cell
                backtrack(board, i, j, root);
            }
        }
        
        // Convert set to list for the final result
        return new ArrayList<>(result);
    }
    
    /**
     * Builds a Trie from the given array of words
     */
    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        
        for (String word : words) {
            TrieNode node = root;
            
            // Insert each character of the word into the Trie
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
            }
            
            // Store the complete word at the leaf node
            node.word = word;
        }
        
        return root;
    }
    
    /**
     * Backtracking function to explore all possible paths
     * 
     * @param board  The 2D grid of characters
     * @param row    Current row position
     * @param col    Current column position
     * @param node   Current Trie node
     */
    private void backtrack(char[][] board, int row, int col, TrieNode node) {
        // Base case 1: Out of bounds
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return;
        }
        
        // Get current character
        char c = board[row][col];
        
        // Base case 2: Cell already visited or character not in Trie
        if (c == '#' || node.children[c - 'a'] == null) {
            return;
        }
        
        // Move to the next node in Trie
        node = node.children[c - 'a'];
        
        // If we found a complete word, add it to results
        if (node.word != null) {
            result.add(node.word);
            // Optional optimization: remove found word to avoid duplicates
            node.word = null;
        }
        
        // Mark the current cell as visited
        board[row][col] = '#';
        
        // Try all four directions
        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            
            // Recursive call to explore this path
            backtrack(board, newRow, newCol, node);
        }
        
        // Backtrack: restore the original character
        board[row][col] = c;
    }
}
```

---

### Alternative Implementation (Trie node stores `char` + `isWord`)

```java
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    private Set<String> result;

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        char ch;
        boolean isWord;

        TrieNode(char ch) {
            this.ch = ch;
        }
    }

    public List<String> findWords(char[][] board, String[] words) {
        TrieNode root = buildTrie(words);
        result = new HashSet<>();

        int rows = board.length;
        int cols = board[0].length;

        StringBuilder currentPath = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                backtrack(board, row, col, root, currentPath);
            }
        }

        return new ArrayList<>(result);
    }

    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode('\0');
        for (String word : words) {
            TrieNode node = root;
            for (int index = 0; index < word.length(); index++) {
                char c = word.charAt(index);
                int childIndex = c - 'a';
                if (node.children[childIndex] == null) {
                    node.children[childIndex] = new TrieNode(c);
                }
                node = node.children[childIndex];
            }
            node.isWord = true;
        }
        return root;
    }

    private void backtrack(char[][] board, int row, int col, TrieNode node, StringBuilder currentPath) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return;
        }

        char boardChar = board[row][col];
        if (boardChar == '#') {
            return;
        }

        TrieNode nextNode = node.children[boardChar - 'a'];
        if (nextNode == null) {
            return;
        }

        currentPath.append(nextNode.ch);

        if (nextNode.isWord) {
            result.add(currentPath.toString());
            nextNode.isWord = false;
        }

        board[row][col] = '#';
        for (int[] direction : DIRECTIONS) {
            backtrack(board, row + direction[0], col + direction[1], nextNode, currentPath);
        }
        board[row][col] = boardChar;

        currentPath.deleteCharAt(currentPath.length() - 1);
    }
}
```
