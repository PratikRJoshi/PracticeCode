### 998. Maximum Binary Tree II
### Problem Link: [Maximum Binary Tree II](https://leetcode.com/problems/maximum-binary-tree-ii/)
### Intuition
This problem is a follow-up to the "Maximum Binary Tree" problem. In the original problem, we constructed a maximum binary tree from an array. Now, we're given a maximum binary tree constructed from an array A, and we need to insert a new value into the tree as if it were appended to the original array.

The key insight is to understand how the maximum binary tree is constructed:
1. The root is the maximum element in the array
2. The left subtree is the maximum tree constructed from elements to the left of the maximum
3. The right subtree is the maximum tree constructed from elements to the right of the maximum

When we append a new value to the array, there are two cases:
1. If the new value is greater than the root, it becomes the new root with the old root as its left child
2. If the new value is less than the root, it should be inserted into the right subtree

### Java Reference Implementation
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
    public TreeNode insertIntoMaxTree(TreeNode root, int val) {
        // [R0] If the tree is empty, create a new node with the value
        if (root == null) {
            return new TreeNode(val);
        }
        
        // [R1] If the new value is greater than the root, it becomes the new root
        if (val > root.val) {
            TreeNode newRoot = new TreeNode(val);
            newRoot.left = root;
            return newRoot;
        }
        
        // [R2] Otherwise, insert the value into the right subtree
        root.right = insertIntoMaxTree(root.right, val);
        
        return root; // [R3] Return the root of the updated tree
    }
}
```

### Alternative Implementation (Iterative)
```java
class Solution {
    public TreeNode insertIntoMaxTree(TreeNode root, int val) {
        // If the tree is empty or the new value is greater than the root
        if (root == null || val > root.val) {
            TreeNode newRoot = new TreeNode(val);
            newRoot.left = root;
            return newRoot;
        }
        
        // Find the correct position to insert the new value
        TreeNode current = root;
        while (current.right != null && current.right.val > val) {
            current = current.right;
        }
        
        // Insert the new node
        TreeNode newNode = new TreeNode(val);
        newNode.left = current.right;
        current.right = newNode;
        
        return root;
    }
}
```

### Understanding the Algorithm and Tree Construction

1. **Maximum Binary Tree Properties:**
   - The root is the maximum element in the array
   - The left subtree contains elements to the left of the maximum
   - The right subtree contains elements to the right of the maximum

2. **Insertion Logic:**
   - When we append a value to the array, it can only affect the rightmost path of the tree
   - If the new value is greater than any node on this path, it becomes the parent of that node
   - Otherwise, it is inserted as a leaf node in the rightmost path

3. **Recursive Approach:**
   - If the tree is empty, create a new node with the value
   - If the value is greater than the root, create a new root with the old root as its left child
   - Otherwise, recursively insert the value into the right subtree

4. **Iterative Approach:**
   - If the value is greater than the root, create a new root
   - Otherwise, traverse the right path until we find a node whose right child is null or has a value less than the new value
   - Insert the new node at this position

5. **Why Only the Right Subtree:**
   - Since the new value is appended to the end of the array, it can only be in the right subtree of the original elements
   - It cannot be in the left subtree of any existing node (except when it becomes the new root)

### Requirement → Code Mapping
- **R0 (Empty tree)**: `if (root == null) { return new TreeNode(val); }` - Handle the base case
- **R1 (Greater than root)**: If the new value is greater than the root, create a new root
- **R2 (Insert into right subtree)**: Recursively insert the value into the right subtree
- **R3 (Return updated tree)**: Return the root of the updated tree

### Complexity Analysis
- **Time Complexity**: O(h)
  - h is the height of the tree
  - In the worst case, we need to traverse the entire right path of the tree
  - For a balanced tree, this is O(log n), but for a skewed tree, it could be O(n)

- **Space Complexity**:
  - Recursive approach: O(h) for the recursion stack
  - Iterative approach: O(1) as we only use a constant amount of extra space

### Related Problems
- **Maximum Binary Tree** (Problem 654): The original problem of constructing a maximum binary tree
- **Insert into a Binary Search Tree** (Problem 701): Similar insertion logic but for BSTs
- **Construct Binary Tree from Preorder and Inorder Traversal** (Problem 105): Another tree construction problem
