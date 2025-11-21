# [208. Implement Trie (Prefix Tree)](https://leetcode.com/problems/implement-trie-prefix-tree/)

A [trie](https://en.wikipedia.org/wiki/Trie) (pronounced as "try") or **prefix tree** is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.

Implement the Trie class:

- `Trie()` Initializes the trie object.
- `void insert(String word)` Inserts the string `word` into the trie.
- `boolean search(String word)` Returns `true` if the string `word` is in the trie (i.e., was inserted before), and `false` otherwise.
- `boolean startsWith(String prefix)` Returns `true` if there is a previously inserted string `word` that has the prefix `prefix`, and `false` otherwise.

**Example 1:**

```
Input
["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
Output
[null, null, true, false, true, null, true]

Explanation
Trie trie = new Trie();
trie.insert("apple");
trie.search("apple");   // return True
trie.search("app");     // return False
trie.startsWith("app"); // return True
trie.insert("app");
trie.search("app");     // return True
```

**Constraints:**

- `1 <= word.length, prefix.length <= 2000`
- `word` and `prefix` consist only of lowercase English letters.
- At most `3 * 10^4` calls **in total** will be made to `insert`, `search`, and `startsWith`.

## Intuition/Main Idea:

A Trie (Prefix Tree) is a tree-like data structure that is used to store a dynamic set of strings, where the keys are usually strings. Unlike a binary search tree, no node in the tree stores the key associated with that node; instead, its position in the tree defines the key with which it is associated.

The basic idea is:
1. Each node in the trie represents a single character.
2. The path from the root to a node forms a prefix.
3. Each node has a boolean flag to indicate if it's the end of a word.
4. Each node has a collection of child nodes, one for each possible character.

For this implementation, since we're dealing only with lowercase English letters, each node will have up to 26 children.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Initialize the trie object | `public Trie() { root = new TrieNode(); }` |
| Insert a string into the trie | `public void insert(String word) { ... }` |
| Check if a string is in the trie | `public boolean search(String word) { ... }` |
| Check if a prefix exists in the trie | `public boolean startsWith(String prefix) { ... }` |

## Final Java Code & Learning Pattern:

```java
class Trie {
    private TrieNode root;
    
    // Define the TrieNode class
    private class TrieNode {
        private TrieNode[] children;
        private boolean isEndOfWord;
        
        public TrieNode() {
            children = new TrieNode[26]; // 26 lowercase English letters
            isEndOfWord = false;
        }
    }

    public Trie() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode current = root;
        
        for (char c : word.toCharArray()) {
            int index = c - 'a'; // Convert character to index (0-25)
            
            // If the child node doesn't exist, create a new one
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            
            // Move to the child node
            current = current.children[index];
        }
        
        // Mark the end of the word
        current.isEndOfWord = true;
    }
    
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        
        // Return true if the node exists and it's the end of a word
        return node != null && node.isEndOfWord;
    }
    
    public boolean startsWith(String prefix) {
        // Return true if the node exists (regardless of whether it's the end of a word)
        return searchPrefix(prefix) != null;
    }
    
    // Helper method to find the node that represents the end of the given string
    private TrieNode searchPrefix(String word) {
        TrieNode current = root;
        
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            
            // If the child node doesn't exist, the word/prefix is not in the trie
            if (current.children[index] == null) {
                return null;
            }
            
            // Move to the child node
            current = current.children[index];
        }
        
        return current;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
```

This solution implements a Trie data structure with the following components:

1. **TrieNode Class**:
   - Each node has an array of 26 children (one for each lowercase English letter).
   - Each node has a boolean flag `isEndOfWord` to indicate if it represents the end of a word.

2. **Trie Class**:
   - **Constructor**: Initializes the trie with an empty root node.
   - **insert(String word)**: Inserts a word into the trie by traversing the trie character by character, creating new nodes as needed, and marking the last node as the end of a word.
   - **search(String word)**: Searches for a word in the trie by traversing the trie character by character and checking if the last node is marked as the end of a word.
   - **startsWith(String prefix)**: Checks if there is any word in the trie that starts with the given prefix by traversing the trie character by character.
   - **searchPrefix(String word)**: A helper method that returns the node representing the end of the given string, or null if the string is not found in the trie.

The key insight is that we're using the structure of the trie itself to store the strings, rather than storing the strings explicitly. Each path from the root to a node with `isEndOfWord = true` represents a word in the trie.

## Alternative Implementation:

We could also use a HashMap instead of an array for the children, which would be more flexible if we needed to handle characters beyond lowercase English letters:

```java
class Trie {
    private TrieNode root;
    
    private class TrieNode {
        private Map<Character, TrieNode> children;
        private boolean isEndOfWord;
        
        public TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }

    public Trie() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode current = root;
        
        for (char c : word.toCharArray()) {
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
        }
        
        current.isEndOfWord = true;
    }
    
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEndOfWord;
    }
    
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }
    
    private TrieNode searchPrefix(String word) {
        TrieNode current = root;
        
        for (char c : word.toCharArray()) {
            current = current.children.get(c);
            if (current == null) {
                return null;
            }
        }
        
        return current;
    }
}
```

## Complexity Analysis:

- **Time Complexity**:
  - **insert(String word)**: O(m) where m is the length of the word.
  - **search(String word)**: O(m) where m is the length of the word.
  - **startsWith(String prefix)**: O(m) where m is the length of the prefix.

- **Space Complexity**: O(n * m) where n is the number of words and m is the average length of the words. In the worst case, if there are no common prefixes, we would need to store all characters of all words.

## Similar Problems:

1. [211. Design Add and Search Words Data Structure](https://leetcode.com/problems/design-add-and-search-words-data-structure/)
2. [212. Word Search II](https://leetcode.com/problems/word-search-ii/)
3. [421. Maximum XOR of Two Numbers in an Array](https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/)
4. [648. Replace Words](https://leetcode.com/problems/replace-words/)
5. [677. Map Sum Pairs](https://leetcode.com/problems/map-sum-pairs/)
