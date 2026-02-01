# 530. Minimum Absolute Difference in BST

[LeetCode Link](https://leetcode.com/problems/minimum-absolute-difference-in-bst/)

## Problem Description
Given the `root` of a Binary Search Tree (BST), return the minimum absolute difference between the values of any two different nodes in the tree.

### Examples

#### Example 1
- Input: `root = [4,2,6,1,3]`
- Output: `1`

#### Example 2
- Input: `root = [1,0,48,null,null,12,49]`
- Output: `1`

---

## Intuition/Main Idea
In a BST, an inorder traversal visits node values in **sorted (increasing) order**.

If values are sorted, the minimum absolute difference between any two values must occur between **two adjacent values** in the sorted order.

So we:

- inorder traverse the tree
- keep `previousValue` (the last visited value)
- update `minDifference` using `currentValue - previousValue`

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| BST inorder is sorted | `inorder(node.left) -> visit node -> inorder(node.right)` |
| Min difference comes from adjacent inorder values | Update `minDifference` with `node.val - previousValue` |
| Track previous visited value | `previousValue` and `hasPrevious` |

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
    private long previousValue;
    private boolean hasPrevious;
    private int minDifference;

    public int getMinimumDifference(TreeNode root) {
        previousValue = 0;
        hasPrevious = false;
        minDifference = Integer.MAX_VALUE;

        inorder(root);
        return minDifference;
    }

    private void inorder(TreeNode node) {
        if (node == null) {
            return;
        }

        inorder(node.left);

        if (hasPrevious) {
            int diff = (int) (node.val - previousValue);
            minDifference = Math.min(minDifference, diff);
        }
        previousValue = node.val;
        hasPrevious = true;

        inorder(node.right);
    }
}
```

### Learning Pattern
- For BST problems that ask for minimum difference / closest values:
  - inorder traversal gives sorted order
  - compare only adjacent values

---

## Complexity Analysis
- Time Complexity: $O(n)$
  - visit each node once
- Space Complexity: $O(h)$
  - recursion stack

---

## Tree Problems

### Why or why not a helper function is required
- A helper function is required to perform inorder traversal while maintaining traversal state.

### Why or why not a global variable is required
- Global variables (`previousValue`, `hasPrevious`, `minDifference`) are convenient to share state across recursive calls.

### What all is calculated at the current level or node of the tree
- Compute the difference between the current node value and the previously visited inorder value.
- Update `minDifference`.

### What is returned to the parent from the current level of the tree
- Nothing is returned; the inorder traversal updates shared state.

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- Inorder requires left subtree first (to reach the smallest values), then current node, then right subtree.

---

## Similar Problems
- [98. Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/) (BST inorder/bounds property)
- [99. Recover Binary Search Tree](https://leetcode.com/problems/recover-binary-search-tree/) (inorder violations)
- [783. Minimum Distance Between BST Nodes](https://leetcode.com/problems/minimum-distance-between-bst-nodes/) (same idea as 530)
