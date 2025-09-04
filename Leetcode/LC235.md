### LC235. Lowest Common Ancestor of a Binary Search Tree
Problem: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/description/

### Main Idea & Intuition

This problem is a specialized version of finding the Lowest Common Ancestor (LCA), but for a Binary Search Tree (BST). The BST property is the key to a highly efficient solution. For any node `n`:
- All values in the left subtree are less than `n.val`.
- All values in the right subtree are greater than `n.val`.

This allows us to discard half of the tree at every step. At any given `node`, we can compare its value to the values of `p` and `q`:

1.  **If both `p.val` and `q.val` are smaller than `node.val`**: Both nodes must be in the left subtree. Therefore, the LCA must also be in the left subtree.
2.  **If both `p.val` and `q.val` are larger than `node.val`**: Both nodes must be in the right subtree. The LCA must also be in the right subtree.
3.  **If neither of the above is true**: This means the current `node` is the "split point." `p` and `q` are on opposite sides of the node, or one of them is the node itself. Thus, the current `node` is the LCA.

This leads to a very direct search that doesn't require visiting the entire tree.

### Applying General Tree Intuitions

-   **Global Variable vs. Return Value**: No global variable is needed. The function can directly return the LCA node it finds. The information we need is the same as the return value.
-   **Pre-order vs. Post-order**: This is a classic **pre-order (top-down)** approach. We evaluate the current node *first* to decide whether to go left, right, or to stop. This is unlike the general LCA problem (LC236), which requires a post-order traversal.
-   **Helper Function**: Not needed. The function signature is self-sufficient for recursion. An iterative approach is even cleaner and more common.

### Iterative Solution (Recommended)

This is the most efficient approach in terms of space.

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode node = root;
        while (node != null) {
            // If both p and q are smaller, LCA must be in the left.
            if (p.val < node.val && q.val < node.val) {
                node = node.left;
            // If both p and q are larger, LCA must be in the right.
            } else if (p.val > node.val && q.val > node.val) {
                node = node.right;
            // Otherwise, we've found the split point. This is the LCA.
            } else {
                return node;
            }
        }
        return null; // Should not be reached in a valid BST with p and q present
    }
}
```

### Recursive Solution

This solution is also elegant and directly follows the logic.

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Base case or split point found
        if (root == null) {
            return null;
        }

        // If both p and q are smaller than root, LCA is in the left subtree
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        
        // If both p and q are larger than root, LCA is in the right subtree
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        
        // Otherwise, this root is the split point and thus the LCA
        return root;
    }
}
```

### Complexity Analysis

*   **Time Complexity: O(H)**
    *   `H` is the height of the tree. In each step, we eliminate half of the remaining tree, so we traverse a single path from the root to the LCA. In a balanced BST, this is `O(log N)`. In the worst case (a skewed tree), this is `O(N)`.

*   **Space Complexity:**
    *   **Iterative Solution: O(1)**. We only use a few pointers, not the call stack.
    *   **Recursive Solution: O(H)**. The space is determined by the depth of the recursion stack, which is the height of the tree.
