# 1008. Construct Binary Search Tree from Preorder Traversal

[LeetCode Link](https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/)

## Problem Description
Given an array of integers `preorder`, which represents the preorder traversal of a binary search tree (BST), construct the BST and return its root.

A preorder traversal visits nodes in this order:

- root
- left subtree
- right subtree

### Examples

#### Example 1
- Input: `preorder = [8,5,1,7,10,12]`
- Output: `[8,5,10,1,7,null,12]`

#### Example 2
- Input: `preorder = [1,3]`
- Output: `[1,null,3]`

---

## Intuition/Main Idea
Preorder traversal always visits the root first. For a BST:

- All values in the left subtree must be within a valid range `< root.val`
- All values in the right subtree must be within a valid range `> root.val`

In preorder, once we start building a subtree, the values belonging to that subtree appear as a **contiguous segment** in the array.

So we can build the BST in one pass using:

- A shared pointer `index` that always points to the next unused preorder value.
- A helper `buildTree(preorder, min, max)` that constructs the subtree whose node values must lie within `[min, max]`.

Core idea:

- If `preorder[index]` is not within `[min, max]`, then the current subtree is done (return `null`).
- Otherwise, consume that value as the root, then:
  - build the left subtree with range `[min, root.val]`
  - build the right subtree with range `[root.val, max]`

Because `index` only moves forward and every value is consumed once, the solution is O(n).

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Preorder gives root before its subtrees | Use `preorder[index]` as the root as soon as it fits the range |
| Must respect BST ordering | Enforce valid range with `(min, max)` bounds in `buildTree` |
| Build tree efficiently | Use global `index` so each preorder value is consumed exactly once |
| Return the constructed root | `bstFromPreorder` returns `buildTree(preorder, MIN, MAX)` |

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
    int index = 0;
    public TreeNode bstFromPreorder(int[] preorder) {
        if(preorder == null || preorder.length == 0) {
            return null;
        }

        return buildTree(preorder, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private TreeNode buildTree(int[] preorder, int min, int max) {
        if(index == preorder.length || preorder[index] < min || preorder[index] > max) {
            return null;
        }

        TreeNode node = new TreeNode(preorder[index++]);
        node.left = buildTree(preorder, min, node.val);
        node.right = buildTree(preorder, node.val, max);

        return node;
    }
}
```

### Learning Pattern
- “Construct tree from traversal” problems often work by consuming the traversal array with a pointer.
- For BST preorder specifically:
  - recursion + min/max bounds decides whether the next value belongs to the current subtree
  - each preorder value is consumed exactly once

---

## Complexity Analysis
- Time Complexity: $O(n)$
  - each value is processed once
- Space Complexity: $O(h)$
  - recursion stack where `h` is the height of the BST (worst-case $O(n)$)

---

## Tree Problems

### Why or why not a helper function is required
- A helper is required because we need extra parameters (`min`, `max`) to enforce valid BST ranges while building subtrees.

### Why or why not a global variable is required
- A global variable (`index`) is a clean way to ensure each recursive call consumes preorder in sequence without slicing arrays.

### What all is calculated at the current level or node of the tree
- Decide whether `preorder[index]` can be the root of the current subtree by checking if it lies within `[min, max]`.
- If it can, consume it (`index++`), create the node, and recursively construct:
  - left subtree within `[min, node.val]`
  - right subtree within `[node.val, max]`

### What is returned to the parent from the current level of the tree
- The root node of the constructed subtree (or `null` if the next preorder value does not fit within `[min, max]`).

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- We must create the current node first (consume the preorder value).
- Then build left subtree before right subtree because preorder visits left subtree nodes first.

---

## Similar Problems
- [105. Construct Binary Tree from Preorder and Inorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) (tree construction from traversals)
- [701. Insert into a Binary Search Tree](https://leetcode.com/problems/insert-into-a-binary-search-tree/) (BST property)
- [98. Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/) (bounds idea)
