# 98. Validate Binary Search Tree

[LeetCode Link](https://leetcode.com/problems/validate-binary-search-tree/)

## Problem Description
Given the `root` of a binary tree, determine if it is a valid binary search tree (BST).

A valid BST is defined as follows:

- The left subtree of a node contains only nodes with keys **less than** the node's key.
- The right subtree of a node contains only nodes with keys **greater than** the node's key.
- Both the left and right subtrees must also be binary search trees.

### Examples

#### Example 1
- Input: `root = [2,1,3]`
- Output: `true`

#### Example 2
- Input: `root = [5,1,4,null,null,3,6]`
- Output: `false`
- Explanation: The node with value `3` is in the right subtree of `5` but is less than `5`.

---

## Intuition/Main Idea
The key is that a BST condition is not only about a node vs its direct parent.

For every node, its value must fall within the valid range imposed by **all ancestors**:

- left child must be in `(..., parentVal)`
- right child must be in `(parentVal, ...)`

So we do DFS while carrying a valid `(lowerBound, upperBound)` range.

If any node violates the range, the tree is not a BST.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| All left subtree values must be strictly less | DFS passes `upperBound = node.val` to left child |
| All right subtree values must be strictly greater | DFS passes `lowerBound = node.val` to right child |
| Must respect constraints from all ancestors | Maintain `(lowerBound, upperBound)` through recursion |
| Empty tree/subtree is valid | Base case `if (node == null) return true;` |

---

## Final Java Code & Learning Pattern (Full Content)
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
        return dfs(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean dfs(TreeNode node, long lowerBound, long upperBound) {
        if (node == null) {
            return true;
        }

        if (node.val <= lowerBound || node.val >= upperBound) {
            return false;
        }

        return dfs(node.left, lowerBound, node.val) && dfs(node.right, node.val, upperBound);
    }
}
```

### Learning Pattern
- When a constraint must hold against **all ancestors**, carry it as bounds/state in the recursion.
- Use `long` bounds to avoid overflow at `Integer.MIN_VALUE` / `Integer.MAX_VALUE`.

---

## Complexity Analysis
- Time Complexity: $O(n)$
  - each node is visited once
- Space Complexity: $O(h)$
  - recursion stack where `h` is the height of the tree

---

## Tree Problems

### Why or why not a helper function is required
- A helper is required to carry additional state (`lowerBound`, `upperBound`) while traversing.

### Why or why not a global variable is required
- No global variable is required because each call returns whether its subtree is valid and the parent combines results with `&&`.

### What all is calculated at the current level or node of the tree
- Validate `node.val` is within `(lowerBound, upperBound)`.
- Compute the new bounds for the children.

### What is returned to the parent from the current level of the tree
- A boolean: whether the subtree rooted at this node is a valid BST.

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- The current node must be validated first, because if it violates bounds we can stop early.
- If it’s valid, then validate left and right subtrees with updated bounds.

---

## Similar Problems
- [1008. Construct Binary Search Tree from Preorder Traversal](https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/) (BST bounds)
- [530. Minimum Absolute Difference in BST](https://leetcode.com/problems/minimum-absolute-difference-in-bst/) (inorder property)
- [538. Convert BST to Greater Tree](https://leetcode.com/problems/convert-bst-to-greater-tree/) (reverse inorder)
