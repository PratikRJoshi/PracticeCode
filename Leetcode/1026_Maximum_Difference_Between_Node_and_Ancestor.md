# 1026. Maximum Difference Between Node and Ancestor

[LeetCode Link](https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/)

## Problem Description
Given the `root` of a binary tree, find the maximum value `v` for which there exist different nodes `a` and `b` such that:

- `a` is an ancestor of `b`
- `v = |a.val - b.val|`

Return the maximum such value.

### Examples

#### Example 1
- Input: `root = [8,3,10,1,6,null,14,null,null,4,7,13]`
- Output: `7`
- Explanation: The maximum difference is between ancestor `8` and descendant `1`.

#### Example 2
- Input: `root = [1,null,2,null,0,3]`
- Output: `3`

---

## Intuition/Main Idea
For any node, the best ancestor to maximize `|ancestor.val - node.val|` will be either:

- the **minimum value** on the path from root to this node, or
- the **maximum value** on the path from root to this node.

So while doing a DFS from root to leaves, we carry two pieces of information:

- `minValueOnPath`: the minimum node value seen so far on the current root-to-node path
- `maxValueOnPath`: the maximum node value seen so far on the current root-to-node path

At each node we can compute the best difference using these extremes:

- `max(|node.val - minValueOnPath|, |node.val - maxValueOnPath|)`

Then we update the path extremes and continue to children.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| `a` must be an ancestor of `b` | Carry `minValueOnPath` / `maxValueOnPath` down recursion to represent ancestors |
| Maximize absolute difference | Update `maxDiff` using `maxValueOnPath - minValueOnPath` while traversing |
| Return a single maximum value | Return global `maxDiff` after DFS completes |

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
    int maxDiff = Integer.MIN_VALUE;

    public int maxAncestorDiff(TreeNode root) {
        dfs(root, root.val, root.val);
        return maxDiff;
    }

    private void dfs(TreeNode root, int minValueOnPath, int maxValueOnPath) {
        if (root == null) {
            return;
        }

        minValueOnPath = Math.min(minValueOnPath, root.val);
        maxValueOnPath = Math.max(maxValueOnPath, root.val);

        maxDiff = Math.max(maxDiff, maxValueOnPath - minValueOnPath);

        dfs(root.left, minValueOnPath, maxValueOnPath);
        dfs(root.right, minValueOnPath, maxValueOnPath);
    }
}
```

### Learning Pattern
- For any “ancestor vs descendant” max difference, track extreme values (min/max) along the root-to-node path.
- The only ancestors that matter for maximizing absolute difference are the path min and path max.

---

## Complexity Analysis
- Time Complexity: $O(n)$
  - each node is visited once
- Space Complexity: $O(h)$
  - recursion stack where `h` is tree height

---

## Tree Problems

### Why or why not a helper function is required
- A helper is useful because we need to carry extra state (`minValueOnPath`, `maxValueOnPath`) that is not part of the original function signature.

### Why or why not a global variable is required
- A global variable is convenient here because we do not need each subtree to “return” an answer to its parent.
- We simply update `maxDiff` during traversal and read it once traversal is complete.

### What all is calculated at the current level or node of the tree
- Updated min/max values for the current root-to-node path.
- `maxValueOnPath - minValueOnPath` which is the best possible ancestor difference for the current node.

### What is returned to the parent from the current level of the tree
- Nothing is returned because we use a global accumulator (`maxDiff`).

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- Update path extremes and `maxDiff` using the current node first.
- Then recurse to children with updated path extremes.

---

## Similar Problems
- [1161. Maximum Level Sum of a Binary Tree](https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/) (tree traversal + aggregation)
- [543. Diameter of Binary Tree](https://leetcode.com/problems/diameter-of-binary-tree/) (DFS with path-based aggregation)
- [104. Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/) (DFS basics)
