# Maximum Depth of Binary Tree

## Problem Description

**Problem Link:** [Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/)

Given the root of a binary tree, return its maximum depth.

## Intuition/Main Idea

Branching recursion:
- Depth of empty tree is `0`.
- Depth of a node is `1 + max(depth(left), depth(right))`.

This works because maximum depth must come from one of the two subtrees.

### Tree-specific reasoning
- **Helper function required?** No. `maxDepth` itself is enough.
- **Global variable required?** No. each call returns its subtree depth.
- **What is calculated at current node?** `1 + max(leftDepth, rightDepth)`.
- **What is returned to parent?** current subtree max depth.
- **Children before current node or vice versa?** Children first, because current node needs their depths.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Handle empty tree | `if (root == null) return 0;` |
| Explore both branches | recursive calls on `root.left` and `root.right` |
| Return max depth | `1 + Math.max(leftDepth, rightDepth)` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Tree DFS Recursion]
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);

        return 1 + Math.max(leftDepth, rightDepth);
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n)$
- **Space Complexity:** $O(h)$ recursion stack (`h` = tree height)

## Similar Problems

1. [111. Minimum Depth of Binary Tree](https://leetcode.com/problems/minimum-depth-of-binary-tree/)
2. [543. Diameter of Binary Tree](https://leetcode.com/problems/diameter-of-binary-tree/)
