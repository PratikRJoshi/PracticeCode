### 98. Validate Binary Search Tree
### Problem Link: [Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/)
### Intuition
A binary search tree (BST) has the property that all nodes in the left subtree of a node have values less than the node's value, and all nodes in the right subtree have values greater than the node's value. This property must hold recursively for all nodes. 

There are two main approaches to validate a BST:
1. **Range-based validation**: For each node, maintain a valid range of values. The left child must be less than the current node, and the right child must be greater than the current node.
2. **In-order traversal**: In a valid BST, an in-order traversal produces values in ascending order. We can check if each node's value is greater than the previous node's value.

### Java Reference Implementation (Range-based approach)
```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }
    
    private boolean validate(TreeNode node, Integer lower, Integer upper) {
        if (node == null) {
            return true;
        }
        
        // Check if the current node's value is within the valid range
        if ((lower != null && node.val <= lower) || (upper != null && node.val >= upper)) {
            return false;
        }
        
        // Recursively validate left and right subtrees
        // For left subtree: upper bound is current node's value
        // For right subtree: lower bound is current node's value
        return validate(node.left, lower, node.val) && validate(node.right, node.val, upper);
    }
}
```

### Alternative Implementation (In-order traversal)
```java
class Solution {
    private Integer prev = null;
    
    public boolean isValidBST(TreeNode root) {
        return inorder(root);
    }
    
    private boolean inorder(TreeNode node) {
        if (node == null) {
            return true;
        }
        
        // Check left subtree
        if (!inorder(node.left)) {
            return false;
        }
        
        // Check current node
        if (prev != null && node.val <= prev) {
            return false;
        }
        prev = node.val;
        
        // Check right subtree
        return inorder(node.right);
    }
}
```

### Requirement → Code Mapping
- **R0 (Base case)**: `if (node == null) { return true; }` - Empty tree is a valid BST
- **R1 (Range validation)**: `if ((lower != null && node.val <= lower) || (upper != null && node.val >= upper)) { return false; }` - Check if current node's value is within valid range
- **R2 (Recursive validation)**: `return validate(node.left, lower, node.val) && validate(node.right, node.val, upper);` - Validate left and right subtrees with updated ranges
- **R3 (In-order traversal check)**: `if (prev != null && node.val <= prev) { return false; }` - In the alternative approach, check if values are in ascending order

### Complexity
- **Time Complexity**: O(n) - We visit each node exactly once
- **Space Complexity**: O(h) - Where h is the height of the tree (recursion stack space)
