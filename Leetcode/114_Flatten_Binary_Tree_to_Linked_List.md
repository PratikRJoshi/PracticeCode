# Flatten Binary Tree to Linked List

## Problem Description

**Problem Link:** [Flatten Binary Tree to Linked List](https://leetcode.com/problems/flatten-binary-tree-to-linked-list/)

Given the `root` of a binary tree, flatten the tree into a "linked list":

- The "linked list" should use the same `TreeNode` class where the `right` child pointer points to the next node in the list and the `left` child pointer is always `null`.
- The "linked list" should be in the same order as a **pre-order traversal** of the binary tree.

**Example 1:**
```
Input: root = [1,2,5,3,4,null,6]
Output: [1,null,2,null,3,null,4,null,5,null,6]
```

**Example 2:**
```
Input: root = []
Output: []
```

**Example 3:**
```
Input: root = [0]
Output: [0]
```

**Constraints:**
- The number of nodes in the tree is in the range `[0, 2000]`.
- `-100 <= Node.val <= 100`

## Intuition/Main Idea

We need to flatten the tree in pre-order (root, left, right) into a linked list using right pointers.

**Core Algorithm:**
- Do a post-order traversal (process children before parent)
- For each node:
  - Flatten left subtree, get its tail
  - Flatten right subtree, get its tail
  - Connect: root.right = flattened left, tail of left.right = flattened right
  - Set root.left = null

**Why post-order:** We need to process children first to get their flattened forms, then connect them to the parent.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Flatten tree to linked list | Recursive helper - Lines 6-30 |
| Pre-order order | Connect left before right - Lines 18-24 |
| Use right pointers | Right pointer updates - Lines 19, 22 |
| Set left to null | Left pointer null - Line 25 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public void flatten(TreeNode root) {
        // Call helper function to flatten and get tail
        flattenHelper(root);
    }
    
    // Helper function that flattens subtree and returns tail node
    private TreeNode flattenHelper(TreeNode root) {
        // Base case: null node
        if (root == null) {
            return null;
        }
        
        // Base case: leaf node
        if (root.left == null && root.right == null) {
            return root;
        }
        
        // Recursively flatten left and right subtrees
        TreeNode leftTail = flattenHelper(root.left);
        TreeNode rightTail = flattenHelper(root.right);
        
        // If left subtree exists, connect it to root's right
        if (root.left != null) {
            // Save original right subtree
            TreeNode rightSubtree = root.right;
            
            // Connect flattened left subtree to root's right
            root.right = root.left;
            root.left = null; // Set left to null
            
            // Connect original right subtree to tail of left subtree
            if (leftTail != null) {
                leftTail.right = rightSubtree;
            }
        }
        
        // Return the tail of the flattened subtree
        // If right subtree exists, return its tail; otherwise return left tail or root
        return rightTail != null ? rightTail : (leftTail != null ? leftTail : root);
    }
}
```

## Tree Problem Analysis

**Why or why not a helper function is required:**
- Helper function is required to return the tail node of the flattened subtree, which is needed to connect subtrees.

**Why or why not a global variable is required:**
- No global variable needed. We return tail nodes from recursive calls.

**What all is calculated at the current level or node of the tree:**
- At current node: Flatten left and right subtrees, connect them in pre-order, set left to null.

**What is returned to the parent from the current level of the tree:**
- Returns the tail node of the flattened subtree rooted at current node.

**How to decide if a recursive call to children needs to be made before current node calculation or vice versa:**
- Recursive calls happen first (lines 16-17) to get flattened subtrees and their tails. Then we connect them (lines 19-24). We need children processed before connecting.

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the number of nodes. We visit each node once.

**Space Complexity:** $O(h)$ where $h$ is the height for recursion stack. In worst case (skewed), $O(n)$.

## Similar Problems

- [Binary Tree Preorder Traversal](https://leetcode.com/problems/binary-tree-preorder-traversal/) - Pre-order traversal
- [Binary Tree Inorder Traversal](https://leetcode.com/problems/binary-tree-inorder-traversal/) - In-order traversal
- [Populating Next Right Pointers](https://leetcode.com/problems/populating-next-right-pointers-in-each-node/) - Similar linking pattern

