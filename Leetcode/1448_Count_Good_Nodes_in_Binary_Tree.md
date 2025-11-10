### 1448. Count Good Nodes in Binary Tree
### Problem Link: [Count Good Nodes in Binary Tree](https://leetcode.com/problems/count-good-nodes-in-binary-tree/)

### Intuition/Main Idea
This problem asks us to count the number of "good" nodes in a binary tree. A node is considered "good" if the path from the root to the node does not contain any node with a value greater than the current node's value.

The key insight is to use a depth-first search (DFS) traversal while keeping track of the maximum value seen so far along the path from the root. For each node, we check if its value is greater than or equal to the maximum value seen so far. If it is, it's a "good" node. Then we recursively check its children, updating the maximum value seen.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count good nodes | `int goodNodes(TreeNode root)` |
| Track maximum value in path | `dfs(root, Integer.MIN_VALUE)` |
| Check if current node is good | `if (node.val >= maxSoFar)` |
| Update count of good nodes | `count = 1 + dfs(node.left, Math.max(maxSoFar, node.val)) + dfs(node.right, Math.max(maxSoFar, node.val))` |

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
// [Pattern: DFS with Path Maximum Tracking]
class Solution {
    public int goodNodes(TreeNode root) {
        // Start DFS from root with minimum possible value as initial maximum
        return dfs(root, Integer.MIN_VALUE);
    }
    
    private int dfs(TreeNode node, int maxSoFar) {
        // Base case: empty node
        if (node == null) {
            return 0;
        }
        
        int count = 0;
        // Check if current node is good (value >= maximum seen so far)
        if (node.val >= maxSoFar) {
            count = 1; // Current node is good
        }
        
        // Update maximum value for the path
        int newMax = Math.max(maxSoFar, node.val);
        
        // Recursively count good nodes in left and right subtrees
        count += dfs(node.left, newMax);
        count += dfs(node.right, newMax);
        
        return count;
    }
}
```

### Alternative Implementation (Cleaner)

```java
// [Pattern: DFS with Path Maximum Tracking]
class Solution {
    public int goodNodes(TreeNode root) {
        return dfs(root, Integer.MIN_VALUE);
    }
    
    private int dfs(TreeNode node, int maxSoFar) {
        if (node == null) {
            return 0;
        }
        
        // Check if current node is good and count recursively
        int count = node.val >= maxSoFar ? 1 : 0;
        
        // Update maximum and continue DFS
        int newMax = Math.max(maxSoFar, node.val);
        count += dfs(node.left, newMax);
        count += dfs(node.right, newMax);
        
        return count;
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(n)$ where n is the number of nodes in the binary tree. We visit each node exactly once.
- **Space Complexity**: $O(h)$ where h is the height of the tree. This is the space used by the recursion stack. In the worst case (skewed tree), this could be $O(n)$, but for a balanced tree, it would be $O(\log n)$.

### Tree Problems Explanation
- **Helper Function**: A helper function is required to carry the additional state (maximum value seen so far) during the traversal. The main function initializes the DFS with the minimum possible value.
- **Global Variable**: No global variable is needed as we can directly return and accumulate the count of good nodes through the recursive calls.
- **Current Level Calculation**: At each node, we determine if it's a good node by comparing its value with the maximum value seen so far in the path from the root.
- **Return Value**: The DFS function returns the count of good nodes in the subtree rooted at the current node, including the current node if it's good.

### Similar Problems
1. **LeetCode 938: Range Sum of BST** - Traverse a BST and sum values in a given range.
2. **LeetCode 1302: Deepest Leaves Sum** - Find the sum of values of the deepest leaves.
3. **LeetCode 1315: Sum of Nodes with Even-Valued Grandparent** - Similar tree traversal with condition checking.
4. **LeetCode 1379: Find a Corresponding Node of a Binary Tree in a Clone of That Tree** - Traverse two trees simultaneously.
5. **LeetCode 863: All Nodes Distance K in Binary Tree** - Find all nodes at distance K from a target node.
6. **LeetCode 236: Lowest Common Ancestor of a Binary Tree** - Find the lowest common ancestor of two nodes.
7. **LeetCode 543: Diameter of Binary Tree** - Find the length of the longest path in a tree.
8. **LeetCode 124: Binary Tree Maximum Path Sum** - Find the maximum path sum in a binary tree.
