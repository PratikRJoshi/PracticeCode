# 1448. Count Good Nodes in Binary Tree

[LeetCode Link](https://leetcode.com/problems/count-good-nodes-in-binary-tree/)

## Problem Description
You are given a binary tree `root`.

A node `X` in the tree is named **good** if in the path from the root to `X` there are no nodes with a value greater than `X`.

Return the number of **good** nodes in the binary tree.

### Examples

#### Example 1
- Input: `root = [3,1,4,3,null,1,5]`
- Output: `4`

#### Example 2
- Input: `root = [3,3,null,4,2]`
- Output: `3`

#### Example 3
- Input: `root = [1]`
- Output: `1`

---

## Intuition/Main Idea
A node is “good” if its value is at least the maximum value seen so far on the path from the root.

So during DFS, we carry one piece of path state:

- `maxSoFar`: maximum value on the current root-to-node path

At each node:

- If `node.val >= maxSoFar`, this node contributes `+1`.
- Update the path maximum to `max(node.val, maxSoFar)` and continue to children.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Node is good if no ancestor is greater | `int isGood = node.val >= maxSoFar ? 1 : 0;` |
| Need to consider the entire root-to-node path | Pass `maxSoFar` down the recursion |
| Count good nodes in the full tree | `goodNodes(root)` returns `dfs(root, MIN_VALUE)` |

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
    public int goodNodes(TreeNode root) {
        return dfs(root, Integer.MIN_VALUE);
    }

    private int dfs(TreeNode node, int maxSoFar) {
        if (node == null) {
            return 0;
        }

        int isGood = node.val >= maxSoFar ? 1 : 0;
        int updatedMax = Math.max(maxSoFar, node.val);

        int leftGood = dfs(node.left, updatedMax);
        int rightGood = dfs(node.right, updatedMax);

        return isGood + leftGood + rightGood;
    }
}
```

### Learning Pattern
- When the condition for a node depends on “best/worst value seen on the path”, carry that value as a DFS parameter.
- Make the recursion return the answer for the subtree, then combine: `selfContribution + left + right`.

---

## Complexity Analysis
- Time Complexity: $O(n)$
  - each node is visited once
- Space Complexity: $O(h)$
  - recursion stack where `h` is the height of the tree

---

## Tree Problems

### Why or why not a helper function is required
- A helper function is required because we must carry extra path state (`maxSoFar`) while traversing down the tree.

### Why or why not a global variable is required
- No global variable is required because each subtree can return its count, and the parent combines results.

### What all is calculated at the current level or node of the tree
- Whether the current node is good: `node.val >= maxSoFar`.
- The updated path maximum for children: `updatedMax = max(maxSoFar, node.val)`.

### What is returned to the parent from the current level of the tree
- The number of good nodes in the subtree rooted at the current node.

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- Current node calculation must happen first because the children depend on the updated path maximum.
- After that, recurse to children and sum up results.

---

## Similar Problems
- [1026. Maximum Difference Between Node and Ancestor](https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/) (track min/max along path)
- [112. Path Sum](https://leetcode.com/problems/path-sum/) (carry path state down DFS)
- [129. Sum Root to Leaf Numbers](https://leetcode.com/problems/sum-root-to-leaf-numbers/) (carry accumulated path value)
