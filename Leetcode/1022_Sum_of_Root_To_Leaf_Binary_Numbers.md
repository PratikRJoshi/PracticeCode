# 1022. Sum of Root To Leaf Binary Numbers

[LeetCode Link](https://leetcode.com/problems/sum-of-root-to-leaf-binary-numbers/)

## Problem Description
You are given the `root` of a binary tree where each node has a value `0` or `1`.

Each root-to-leaf path represents a binary number starting with the most significant bit.

Return the sum of these numbers.

### Examples

#### Example 1
- Input: `root = [1,0,1,0,1,0,1]`
- Output: `22`
- Explanation: The paths are `100`, `101`, `110`, `111`, which sum to `4 + 5 + 6 + 7 = 22`.

#### Example 2
- Input: `root = [0]`
- Output: `0`

---

## Intuition/Main Idea
As we go down the tree, we are building a binary number bit by bit.

If `currentValue` is the number formed from root to the parent, then adding a bit `b` at the child means:

- `nextValue = currentValue * 2 + b`

When we reach a leaf, that `currentValue` is one full root-to-leaf number, so we add it to the answer.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Build binary number along path | `nextValue = currentValue * 2 + node.val` |
| Sum only at leaves | `if (node.left == null && node.right == null)` |
| Include all root-to-leaf paths | DFS explores both left and right |

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
    public int sumRootToLeaf(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode node, int currentValue) {
        if (node == null) {
            return 0;
        }

        int nextValue = currentValue * 2 + node.val;

        if (node.left == null && node.right == null) {
            return nextValue;
        }

        return dfs(node.left, nextValue) + dfs(node.right, nextValue);
    }
}
```

### Learning Pattern
- For “root-to-leaf path forms a number”:
  - carry the partial number as you traverse
  - update with a simple transition (for binary: `*2 + bit`)
  - at leaves, return/add the formed number

---

## Complexity Analysis
- Time Complexity: $O(n)$
  - each node visited once
- Space Complexity: $O(h)$
  - recursion stack

---

## Tree Problems

### Why or why not a helper function is required
- A helper function is required to carry the running path number (`currentValue`) while traversing.

### Why or why not a global variable is required
- No global variable is required because each subtree returns the sum of its root-to-leaf numbers.

### What all is calculated at the current level or node of the tree
- Compute `nextValue` by shifting left (`*2`) and adding the current bit.
- If this node is a leaf, that `nextValue` is one completed number.

### What is returned to the parent from the current level of the tree
- The sum of all root-to-leaf numbers in the subtree rooted at this node.

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- Current node calculation must happen first to compute `nextValue`.
- Then recurse into children using `nextValue`.

---

## Similar Problems
- [129. Sum Root to Leaf Numbers](https://leetcode.com/problems/sum-root-to-leaf-numbers/) (same pattern, base-10 digits)
- [1448. Count Good Nodes in Binary Tree](https://leetcode.com/problems/count-good-nodes-in-binary-tree/) (carry path state)
