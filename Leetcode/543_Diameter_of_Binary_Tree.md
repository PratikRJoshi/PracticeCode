### 543. Diameter of Binary Tree
### Problem Link: [Diameter of Binary Tree](https://leetcode.com/problems/diameter-of-binary-tree/)

### Intuition/Main Idea
The diameter of a binary tree is the length of the longest path between any two nodes in the tree. This path may or may not pass through the root. The key insight is that for any node in the tree, the longest path that passes through it is the sum of the heights of its left and right subtrees.

To solve this problem efficiently, we can use a depth-first search (DFS) approach where we calculate the height of each subtree while simultaneously keeping track of the maximum diameter found so far. For each node, we:
1. Calculate the height of its left subtree
2. Calculate the height of its right subtree
3. Update the maximum diameter if the path through this node (left height + right height) is longer than any previously found
4. Return the height of the current node (1 + max(left height, right height))

This approach allows us to find the diameter in a single tree traversal.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Track maximum diameter | `private int maxDiameter = 0;` |
| Calculate height of subtrees | `int leftHeight = height(node.left); int rightHeight = height(node.right);` |
| Update diameter at each node | `maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);` |
| Return height of current subtree | `return 1 + Math.max(leftHeight, rightHeight);` |

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
// [Pattern: DFS with Height Calculation]
class Solution {
    private int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        height(root);
        return maxDiameter;
    }

    private int height(TreeNode node) {
        // Base Case: An empty tree has a height of 0
        if (node == null) {
            return 0;
        }

        // Recursively calculate the height of the left and right subtrees
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        // The diameter at this node is the sum of the heights of its children
        // Update the global maximum if this path is longer
        maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);

        // Return the height of the tree rooted at this node
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
```

### Alternative Implementation (Without Global Variable)

```java
// [Pattern: DFS with Return Pair]
class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
        return diameterHeight(root).diameter;
    }
    
    // Helper class to store both diameter and height
    private class DiameterHeight {
        int diameter;
        int height;
        
        DiameterHeight(int diameter, int height) {
            this.diameter = diameter;
            this.height = height;
        }
    }
    
    private DiameterHeight diameterHeight(TreeNode node) {
        if (node == null) {
            return new DiameterHeight(0, 0);
        }
        
        // Get values from left and right subtrees
        DiameterHeight left = diameterHeight(node.left);
        DiameterHeight right = diameterHeight(node.right);
        
        // Calculate height of current node
        int height = 1 + Math.max(left.height, right.height);
        
        // Calculate diameter passing through current node
        int pathThroughNode = left.height + right.height;
        
        // The diameter is the maximum of:
        // 1. Diameter of left subtree
        // 2. Diameter of right subtree
        // 3. Path through current node
        int diameter = Math.max(pathThroughNode, Math.max(left.diameter, right.diameter));
        
        return new DiameterHeight(diameter, height);
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(n)$ where n is the number of nodes in the binary tree. We visit each node exactly once.
- **Space Complexity**: $O(h)$ where h is the height of the tree. This is the space used by the recursion stack. In the worst case (a skewed tree), this could be $O(n)$, but for a balanced tree, it would be $O(\log n)$.

### Tree Problems Explanation
- **Helper Function**: A helper function is required to calculate the height of each subtree while simultaneously tracking the maximum diameter. This dual-purpose function allows us to solve the problem in a single tree traversal.

- **Global Variable**: In the first implementation, we use a global variable `maxDiameter` to keep track of the maximum diameter found so far. This simplifies the recursive function by allowing it to focus on returning the height. In the alternative implementation, we avoid using a global variable by returning both the height and diameter in a custom object.

- **Current Level Calculation**: At each node, we calculate two things:
  1. The potential diameter passing through this node (sum of left and right subtree heights)
  2. The height of the subtree rooted at this node (1 + max of left and right subtree heights)

- **Return Value**: The helper function returns the height of the subtree rooted at the current node. This value is used by parent nodes to calculate their own heights and potential diameters.

### Similar Problems
1. **LeetCode 124: Binary Tree Maximum Path Sum** - Similar concept but with node values instead of edges.
2. **LeetCode 687: Longest Univalue Path** - Find the longest path where nodes have the same value.
3. **LeetCode 1522: Diameter of N-Ary Tree** - Extension to N-ary trees.
4. **LeetCode 1245: Tree Diameter** - Finding diameter in an undirected graph.
5. **LeetCode 1372: Longest ZigZag Path in a Binary Tree** - Find longest zigzag path.
6. **LeetCode 337: House Robber III** - Different problem but uses similar DFS with return values pattern.
7. **LeetCode 104: Maximum Depth of Binary Tree** - Simpler version focusing only on height.
8. **LeetCode 110: Balanced Binary Tree** - Check if height difference between subtrees is at most 1.
