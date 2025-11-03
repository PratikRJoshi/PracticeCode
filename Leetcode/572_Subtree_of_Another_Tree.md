### 572. Subtree of Another Tree
Problem: https://leetcode.com/problems/subtree-of-another-tree/

---

### Main Idea & Intuition

The problem asks if one tree (`subRoot`) is an exact subtree of another (`root`). This can be broken down into two simpler questions:

1.  **Question 1: "Are two trees identical?"** We need a way to check if two given trees, say `t1` and `t2`, have the exact same structure and node values. We can do this with a simple recursive function, let's call it `isSameTree`. It checks if `t1.val == t2.val`, and then recursively confirms that `t1.left` is identical to `t2.left` AND `t1.right` is identical to `t2.right`.

2.  **Question 2: "How do I check every subtree in the main tree?"** We need to traverse the main tree (`root`) and, for every node we visit, we use our `isSameTree` function from Question 1 to see if the subtree starting at that node is identical to `subRoot`.

So, the overall logic for the main function `isSubtree(root, subRoot)` becomes:
- Is the tree starting at `root` identical to `subRoot`?
- **OR**, is `subRoot` a subtree of `root.left`?
- **OR**, is `subRoot` a subtree of `root.right`?

This structure naturally leads to a recursive solution.

---

### Applying General Tree Intuitions

-   **Global Variable vs. Return Value**: No global variable is needed. Both the main function and the helper will return a `boolean`, which is exactly the information required by their callers.

-   **Pre-order vs. Post-order**: The main `isSubtree` function uses a **pre-order traversal**. At each node in the main tree, it first performs its primary check (`isSameTree(root, subRoot)`) and then, if that fails, it makes the recursive calls on its children (`isSubtree(root.left, ...)`). This "check self, then recurse" pattern is pre-order.

-   **Helper Function**: Yes, a helper function (`isSameTree`) is essential. The main problem is "Is `subRoot` *somewhere* inside `root`?" while the helper answers the more specific question, "Are these two trees *identical* starting from their respective roots?" Since the recursive logic for these two questions is different, separating them into a main function and a helper is the cleanest approach.

---

### Code Solution

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
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) {
            return false;
        }
        // Check if the tree starting at the current root is the same as subRoot.
        if (isSameTree(root, subRoot)) {
            return true;
        }
        // If not, recursively check if subRoot is a subtree of the left or right child.
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    // Helper function to check if two trees are identical.
    private boolean isSameTree(TreeNode t1, TreeNode t2) {
        // If both nodes are null, they are identical.
        if (t1 == null && t2 == null) {
            return true;
        }
        // If one is null but the other isn't, they are not identical.
        if (t1 == null || t2 == null) {
            return false;
        }
        // If their values don't match, they are not identical.
        if (t1.val != t2.val) {
            return false;
        }
        // Recursively check if their left and right subtrees are also identical.
        return isSameTree(t1.left, t2.left) && isSameTree(t1.right, t2.right);
    }
}
```

---

### Complexity Analysis

Let `m` be the number of nodes in `root` and `n` be the number of nodes in `subRoot`.

*   **Time Complexity: O(m * n)**
    *   In the worst case, the `isSubtree` function traverses every node in `root` ( `m` nodes). For each of these `m` nodes, it calls `isSameTree`, which then traverses every node in `subRoot` (`n` nodes) in its own worst case. This leads to a multiplicative complexity.

*   **Space Complexity: O(H_root)**
    *   The space complexity is determined by the maximum depth of the recursion stack. The `isSubtree` call stack can go as deep as the height of the `root` tree (`H_root`). The `isSameTree` calls create their own stack, but this happens sequentially, so the overall maximum depth is dominated by the height of the larger tree.
