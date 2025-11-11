### 110. Balanced Binary Tree
### Problem Link: [Balanced Binary Tree](https://leetcode.com/problems/balanced-binary-tree/)

### Intuition/Main Idea
A balanced binary tree is defined as a tree where the height difference between the left and right subtrees of every node is at most 1. The key insight is to use a **post-order traversal** approach where we check the balance condition from the bottom up.

Instead of calculating the height of each subtree multiple times (which would be inefficient), we can combine the height calculation and balance checking in a single recursive function. This function returns the height of a subtree if it's balanced, or a special value (like -1) if it's unbalanced.

The elegance of this approach is that once we detect an imbalance anywhere in the tree, we can immediately propagate this information up the recursion stack without doing any further unnecessary calculations. This makes the algorithm both efficient and clean.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Check if tree is balanced | `return checkHeight(root) != -1;` |
| Calculate height of subtrees | `int leftHeight = checkHeight(node.left); int rightHeight = checkHeight(node.right);` |
| Check balance condition | `if (Math.abs(leftHeight - rightHeight) > 1) { return -1; }` |
| Propagate imbalance | `if (leftHeight == -1 || rightHeight == -1) { return -1; }` |
| Return height if balanced | `return 1 + Math.max(leftHeight, rightHeight);` |

### Final Java Code & Learning Pattern

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
// [Pattern: Post-order Traversal with Height Calculation]
class Solution {
    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    // Returns the height of the tree if it's balanced, otherwise returns -1
    private int checkHeight(TreeNode node) {
        // Base case: An empty tree is balanced and has height 0
        if (node == null) {
            return 0;
        }

        // Recursively get the height of the left subtree
        int leftHeight = checkHeight(node.left);
        if (leftHeight == -1) {
            return -1;  // Left subtree is unbalanced
        }

        // Recursively get the height of the right subtree
        int rightHeight = checkHeight(node.right);
        if (rightHeight == -1) {
            return -1;  // Right subtree is unbalanced
        }

        // Check if the current node is balanced
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;  // Current node is unbalanced
        }

        // If balanced, return its height
        return 1 + Math.max(leftHeight, rightHeight);
    }
}

```

### Alternative Implementation (Two-Pass Approach)

```java
// [Pattern: Separate Height Calculation and Balance Check]
class Solution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        // Check if left and right subtrees are balanced
        if (!isBalanced(root.left) || !isBalanced(root.right)) {
            return false;
        }
        
        // Check if current node is balanced
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        
        return Math.abs(leftHeight - rightHeight) <= 1;
    }
    
    private int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
}

```

### Complexity Analysis
- **Time Complexity**: $O(n)$ for the optimized one-pass solution, where n is the number of nodes in the binary tree. We visit each node exactly once.
- **Space Complexity**: $O(h)$ where h is the height of the tree. This is the space used by the recursion stack. In the worst case (a skewed tree), this could be $O(n)$, but for a balanced tree, it would be $O(\log n)$.

For the two-pass approach:
- **Time Complexity**: $O(n^2)$ in the worst case, as for each node, we might recalculate heights of its subtrees multiple times.
- **Space Complexity**: $O(h)$ same as the one-pass solution.

### Tree Problems Explanation
- **Helper Function**: A helper function is required to combine height calculation and balance checking. This allows us to efficiently propagate the balance status up the tree while calculating heights.

- **Global Variable**: No global variable is needed in this solution. Instead, we encode the "balanced" status directly in the return value of our recursive function. This makes the code cleaner and avoids potential issues with shared state.

- **Current Level Calculation**: At each node, we calculate:
  1. Whether the left and right subtrees are balanced
  2. The heights of the left and right subtrees
  3. Whether the current node is balanced based on the height difference

- **Return Value**: The helper function returns:
  - The height of the subtree if it's balanced
  - -1 if the subtree is unbalanced
  This dual-purpose return value efficiently combines height calculation and balance checking.

### Similar Problems
1. **LeetCode 104: Maximum Depth of Binary Tree** - Calculate the height of a binary tree.
2. **LeetCode 543: Diameter of Binary Tree** - Find the longest path between any two nodes in a tree.
3. **LeetCode 124: Binary Tree Maximum Path Sum** - Find the path with the maximum sum in a binary tree.
4. **LeetCode 1120: Maximum Average Subtree** - Find the subtree with the maximum average value.
5. **LeetCode 366: Find Leaves of Binary Tree** - Remove leaves of a tree layer by layer.
6. **LeetCode 563: Binary Tree Tilt** - Calculate the tilt of a binary tree.
7. **LeetCode 1448: Count Good Nodes in Binary Tree** - Count nodes that are greater than all nodes in the path from root.
8. **LeetCode 1372: Longest ZigZag Path in a Binary Tree** - Find the longest zigzag path in a binary tree.
