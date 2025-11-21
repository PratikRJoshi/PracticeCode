# Maximum Width of Binary Tree

## Problem Description

**Problem Link:** [Maximum Width of Binary Tree](https://leetcode.com/problems/maximum-width-of-binary-tree/)

Given the `root` of a binary tree, return *the **maximum width** of the given tree*.

The **maximum width** of a tree is the maximum width among all levels.

The **width** of one level is defined as the length between the end-nodes (the leftmost and rightmost non-null nodes), where the null nodes between the end-nodes that would be present in a complete binary tree extending down to that level are also counted into the length calculation.

It is **guaranteed** that the answer will in the range of a **32-bit signed integer**.

**Example 1:**
```
Input: root = [1,3,2,5,3,null,9]
Output: 4
Explanation: The maximum width exists in the third level with length 4 (5,3,null,9).
```

**Example 2:**
```
Input: root = [1,3,2,5,null,null,9,6,null,7]
Output: 7
Explanation: The maximum width exists in the fourth level with length 7 (6,null,null,null,null,null,7).
```

**Constraints:**
- The number of nodes in the tree is in the range `[1, 3000]`.
- `-100 <= Node.val <= 100`

## Intuition/Main Idea

We need to find the maximum width across all levels. Width is the distance between leftmost and rightmost non-null nodes at a level.

**Core Algorithm:**
- Use BFS with level-order traversal
- Assign an index to each node (like in a heap: left child = 2*parent, right child = 2*parent+1)
- For each level, calculate width = rightmost_index - leftmost_index + 1
- Track maximum width across all levels

**Why indexing:** By assigning indices like in a complete binary tree, we can calculate the width even when there are null nodes between the end nodes.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Level order traversal | BFS with queue - Lines 8-30 |
| Assign indices to nodes | Pair class - Lines 5, 15-16 |
| Calculate level width | Width calculation - Lines 20-22 |
| Track maximum width | Max tracking - Line 23 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        // Queue stores pairs of (node, index)
        // Index follows heap indexing: left = 2*parent, right = 2*parent+1
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(root, 0));
        
        int maxWidth = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int leftmost = queue.peek().getValue(); // First node's index in this level
            int rightmost = leftmost;
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                Pair<TreeNode, Integer> pair = queue.poll();
                TreeNode node = pair.getKey();
                int index = pair.getValue();
                
                // Update rightmost index
                rightmost = index;
                
                // Add children with their indices
                if (node.left != null) {
                    queue.offer(new Pair<>(node.left, 2 * index));
                }
                if (node.right != null) {
                    queue.offer(new Pair<>(node.right, 2 * index + 1));
                }
            }
            
            // Calculate width for this level
            int width = rightmost - leftmost + 1;
            maxWidth = Math.max(maxWidth, width);
        }
        
        return maxWidth;
    }
}

// Helper class for Pair (or use Map.Entry)
class Pair<K, V> {
    private K key;
    private V value;
    
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public K getKey() { return key; }
    public V getValue() { return value; }
}
```

## Tree Problem Analysis

**Why or why not a helper function is required:**
- No helper function needed. Main function handles BFS traversal.

**Why or why not a global variable is required:**
- No global variable. We track maxWidth locally.

**What all is calculated at the current level or node of the tree:**
- At each level: Find leftmost and rightmost indices, calculate width.

**What is returned to the parent from the current level of the tree:**
- Not applicable. We track maximum width across levels.

**How to decide if a recursive call to children needs to be made before current node calculation or vice versa:**
- Iterative BFS. We process current level, calculate width, then add children to queue.

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the number of nodes. We visit each node once.

**Space Complexity:** $O(n)$ for the queue. In worst case, last level has $n/2$ nodes.

## Similar Problems

- [Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) - Similar BFS pattern
- [Binary Tree Right Side View](https://leetcode.com/problems/binary-tree-right-side-view/) - Track rightmost node per level
- [Count Complete Tree Nodes](https://leetcode.com/problems/count-complete-tree-nodes/) - Uses similar indexing concept

