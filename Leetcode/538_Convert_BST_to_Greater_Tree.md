# 538. Convert BST to Greater Tree

[LeetCode Link](https://leetcode.com/problems/convert-bst-to-greater-tree/)

## Problem Description
Given the `root` of a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original BST is changed to the original key plus the sum of all keys greater than the original key in the BST.

### Examples

#### Example 1
- Input: `root = [4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]`
- Output: `[30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]`

#### Example 2
- Input: `root = [0,null,1]`
- Output: `[1,null,1]`

---

## Intuition/Main Idea
In a BST, inorder traversal is increasing. To accumulate the sum of all **greater** values, we want to visit nodes from **largest to smallest**.

That is exactly a **reverse inorder traversal**:

- visit right subtree
- then node
- then left subtree

Maintain a running sum `runningSum`:

- when we visit a node, add its value to `runningSum`
- replace `node.val` with `runningSum`

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Need sum of all greater keys | Traverse from largest to smallest (reverse inorder) |
| Update each node in-place | `runningSum += node.val; node.val = runningSum;` |
| Preserve tree structure | Only mutate values, no pointer changes |

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
    private int runningSum;

    public TreeNode convertBST(TreeNode root) {
        runningSum = 0;
        reverseInorder(root);
        return root;
    }

    private void reverseInorder(TreeNode node) {
        if (node == null) {
            return;
        }

        reverseInorder(node.right);

        runningSum += node.val;
        node.val = runningSum;

        reverseInorder(node.left);
    }
}
```

### Learning Pattern
- If a BST question needs “greater elements” information:
  - traverse in reverse inorder (right -> node -> left)
  - carry a running sum/aggregate

---

## Complexity Analysis
- Time Complexity: $O(n)$
  - visit each node once
- Space Complexity: $O(h)$
  - recursion stack

---

## Tree Problems

### Why or why not a helper function is required
- A helper function is required to perform reverse inorder traversal recursively.

### Why or why not a global variable is required
- A global variable (`runningSum`) is convenient to share the accumulated sum across recursive calls.

### What all is calculated at the current level or node of the tree
- Update `runningSum` by adding the current node value.
- Overwrite `node.val` with the updated `runningSum`.

### What is returned to the parent from the current level of the tree
- Nothing is returned; the traversal mutates nodes in-place.

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- Must process the right subtree first so `runningSum` already includes all greater values before updating the current node.

---

## Similar Problems
- [530. Minimum Absolute Difference in BST](https://leetcode.com/problems/minimum-absolute-difference-in-bst/) (inorder property)
- [1038. Binary Search Tree to Greater Sum Tree](https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/) (same transformation)
- [98. Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/) (BST constraints)
