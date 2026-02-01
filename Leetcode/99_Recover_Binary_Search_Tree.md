# 99. Recover Binary Search Tree

[LeetCode Link](https://leetcode.com/problems/recover-binary-search-tree/)

## Problem Description
You are given the `root` of a binary search tree (BST), where **exactly two nodes** of the tree were swapped by mistake.

Recover the tree without changing its structure.

### Examples

#### Example 1
- Input: `root = [1,3,null,null,2]`
- Output: `[3,1,null,null,2]`
- Explanation: `3` and `1` are swapped.

#### Example 2
- Input: `root = [3,1,4,null,null,2]`
- Output: `[2,1,4,null,null,3]`
- Explanation: `2` and `3` are swapped.

---

## Intuition/Main Idea
For a valid BST, an inorder traversal produces a strictly increasing sequence.

If two nodes are swapped, the inorder sequence will have **inversions** (a previous value is greater than a later value).

There are two cases:

- The swapped nodes are adjacent in inorder: one inversion.
- The swapped nodes are not adjacent: two inversions.

During inorder traversal, whenever we see `previous.val > current.val`, we found an inversion:

- On the first inversion, mark `first = previous` and `second = current`.
- On a second inversion, update `second = current`.

Finally, swap `first.val` and `second.val`.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| BST inorder should be sorted | Use inorder traversal with `previous` pointer |
| Detect the two swapped nodes | Find inversions where `previous.val > current.val` |
| Recover without changing structure | Swap only values: `first.val` and `second.val` |

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
    private TreeNode first;
    private TreeNode second;
    private TreeNode previous;

    public void recoverTree(TreeNode root) {
        first = null;
        second = null;
        previous = null;

        inorder(root);

        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }

    private void inorder(TreeNode node) {
        if (node == null) {
            return;
        }

        inorder(node.left);

        if (previous != null && previous.val > node.val) {
            if (first == null) {
                first = previous;
            }
            second = node;
        }
        previous = node;

        inorder(node.right);
    }
}
```

### Learning Pattern
- For BST problems, inorder traversal often converts the problem into an “array-like” property.
- If the inorder sequence must be sorted, any `prev > curr` indicates a violation.
- Two swapped nodes become 1 or 2 inversions; track the first bad `prev` and the last bad `curr`.

---

## Complexity Analysis
- Time Complexity: $O(n)$
  - each node is visited once
- Space Complexity: $O(h)$
  - recursion stack where `h` is the height of the tree

---

## Tree Problems

### Why or why not a helper function is required
- A helper function is required to perform inorder traversal recursively.

### Why or why not a global variable is required
- Global variables (`first`, `second`, `previous`) are convenient to share state across recursive calls.

### What all is calculated at the current level or node of the tree
- Detect an inversion by comparing `previous.val` with `node.val`.
- Update `first` and `second` based on whether this is the first or later inversion.

### What is returned to the parent from the current level of the tree
- Nothing is returned; traversal updates shared state and eventually we swap values.

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- In inorder traversal, we must process:
  - left subtree first
  - then current node
  - then right subtree

---

## Similar Problems
- [98. Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/) (BST inorder/bounds validation)
- [530. Minimum Absolute Difference in BST](https://leetcode.com/problems/minimum-absolute-difference-in-bst/) (inorder sorted property)
- [538. Convert BST to Greater Tree](https://leetcode.com/problems/convert-bst-to-greater-tree/) (reverse inorder)
