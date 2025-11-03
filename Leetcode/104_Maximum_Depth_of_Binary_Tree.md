### 104. Maximum Depth of Binary Tree

#### Problem Statement
[Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/)

---

### Main Idea & Intuition

The goal is to find the longest path from the root node down to the farthest leaf node. This can be solved elegantly using recursion in a "bottom-up" manner.

The depth of a binary tree is defined recursively:
*   If a node is `null`, its depth is `0`.
*   Otherwise, its depth is `1` (for the node itself) plus the maximum of the depths of its left and right subtrees.

We recursively calculate the depth of the left and right subtrees and then take the maximum, adding one for the current level.

### Code Implementation

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
    public int maxDepth(TreeNode root) {
        // Base case: if the node is null, its depth is 0.
        if (root == null) {
            return 0;
        }
        
        // Recursively find the depth of the left and right subtrees.
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        // The depth of the tree is 1 + the max of the depths of the subtrees.
        return 1 + Math.max(leftDepth, rightDepth);
    }
}
```

### Complexity Analysis
*   **Time Complexity**: `O(N)`, where `N` is the number of nodes. We visit each node exactly once.
*   **Space Complexity**: `O(H)`, where `H` is the height of the tree, for the recursion stack. In the worst case of a skewed tree, this is `O(N)`. In the best case of a completely balanced tree, it is `O(log N)`.