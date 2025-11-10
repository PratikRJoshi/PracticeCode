### 1448. Count Good Nodes in Binary Tree
### Problem Link: [Count Good Nodes in Binary Tree](https://leetcode.com/problems/count-good-nodes-in-binary-tree/)

### Intuition/Main Idea
This problem asks us to count the number of "good" nodes in a binary tree. A node is considered "good" if the path from the root to the node does not contain any node with a value greater than the current node's value.

The key insight is to use a depth-first search (DFS) traversal while keeping track of the maximum value seen so far along the path from the root. As we traverse down the tree, we pass along the maximum value seen in the path. For each node, we check if its value is greater than or equal to this maximum. If it is, it's a "good" node and we increment our counter.

This approach works because:
1. We need to examine every node in the tree exactly once
2. For each node, we only need to know the maximum value in the path from the root to that node
3. We can efficiently track this maximum value by updating it as we traverse down the tree

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count good nodes | `int goodNodes(TreeNode root)` |
| Track maximum value in path | `dfs(root, Integer.MIN_VALUE)` |
| Check if current node is good | `if (node.val >= maxSoFar)` |
| Update count of good nodes | `int count = node.val >= maxSoFar ? 1 : 0;` |
| Update maximum value for children | `int newMax = Math.max(maxSoFar, node.val);` |

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
        
        // Check if current node is good (value >= maximum seen so far)
        int count = node.val >= maxSoFar ? 1 : 0;
        
        // Update maximum value for the path
        int newMax = Math.max(maxSoFar, node.val);
        
        // Recursively count good nodes in left and right subtrees
        // and add them to the current count
        count += dfs(node.left, newMax);
        count += dfs(node.right, newMax);
        
        return count;
    }
}
```

### Alternative Implementation (Using a Global Variable)

```java
// [Pattern: DFS with Global Counter]
class Solution {
    private int goodCount = 0;
    
    public int goodNodes(TreeNode root) {
        dfs(root, Integer.MIN_VALUE);
        return goodCount;
    }
    
    private void dfs(TreeNode node, int maxSoFar) {
        if (node == null) {
            return;
        }
        
        // If current node is good, increment counter
        if (node.val >= maxSoFar) {
            goodCount++;
        }
        
        // Update maximum and continue DFS
        int newMax = Math.max(maxSoFar, node.val);
        dfs(node.left, newMax);
        dfs(node.right, newMax);
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(n)$ where n is the number of nodes in the binary tree. We visit each node exactly once.
- **Space Complexity**: $O(h)$ where h is the height of the tree. This is the space used by the recursion stack. In the worst case (skewed tree), this could be $O(n)$, but for a balanced tree, it would be $O(\log n)$.

### Tree Problems Explanation
- **Helper Function**: A helper function is required to carry the additional state (maximum value seen so far) during the traversal. This allows us to determine if each node is "good" based on its path from the root.

- **Global Variable**: In the first implementation, no global variable is needed as we directly return and accumulate the count through recursive calls. In the alternative implementation, we use a global variable `goodCount` to track the total number of good nodes, which simplifies the recursive function's return type to void.

- **Current Level Calculation**: At each node, we determine if it's a good node by comparing its value with the maximum value seen so far in the path from the root. This is a local decision made at each node based on the path information passed down from its ancestors.

- **Return Value**: In the first implementation, the DFS function returns the count of good nodes in the subtree rooted at the current node, including the current node if it's good. This allows us to accumulate counts as we return up the recursion stack. In the alternative implementation, we don't return anything from the helper function since we're using a global counter.

### Similar Problems
1. **LeetCode 938: Range Sum of BST** - Traverse a BST and sum values in a given range.
2. **LeetCode 1302: Deepest Leaves Sum** - Find the sum of values of the deepest leaves.
3. **LeetCode 1315: Sum of Nodes with Even-Valued Grandparent** - Similar tree traversal with condition checking.
4. **LeetCode 1379: Find a Corresponding Node of a Binary Tree in a Clone of That Tree** - Traverse two trees simultaneously.
5. **LeetCode 863: All Nodes Distance K in Binary Tree** - Find all nodes at distance K from a target node.
6. **LeetCode 236: Lowest Common Ancestor of a Binary Tree** - Find the lowest common ancestor of two nodes.
7. **LeetCode 543: Diameter of Binary Tree** - Find the length of the longest path in a tree.
8. **LeetCode 124: Binary Tree Maximum Path Sum** - Find the maximum path sum in a binary tree.
