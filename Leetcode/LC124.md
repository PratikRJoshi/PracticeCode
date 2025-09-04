### 124. Binary Tree Maximum Path Sum
Problem: https://leetcode.com/problems/binary-tree-maximum-path-sum/

---

### Main Idea & Intuition

This problem is a more advanced version of the "Diameter of a Binary Tree" problem. The core idea is very similar: for each node, we want to calculate a value, and in the process, update a global maximum.

The key distinction is what we return versus what we update globally.

1.  **What to return (for the parent):** A parent node needs to know the **maximum path sum starting from its child and going straight down**. A path can only extend from a parent to *one* of its children's paths. It can't go down the left child, up to the parent, and then down the right child to continue upwards. So, the function must return `node.val + max(left_path, right_path)`. This is the value a parent can use.

2.  **What to update globally (for the final answer):** The true maximum path sum might be a "V" shape that goes through the current node, connecting its left and right subtrees. The value of this path is `node.val + left_path + right_path`. This "V-shaped" path cannot be extended upwards to the parent node, so it can't be the return value. However, it might be the overall maximum path sum. Therefore, we must check and update a global `maxSum` with this value at every node.

We will use a post-order traversal (DFS) because to calculate the path sums for a given node, we first need the results from its left and right children.

---

### Step-by-Step to the Final Code

**Step 1: Define the main function and a global variable.**
We need a main function `maxPathSum` and a class member variable, `maxSum`, initialized to a very small number to correctly handle negative node values.

```java
class Solution {
    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxPathDown(root);
        return maxSum;
    }
    
    // Helper function to be defined next
}
```

**Step 2: Create a recursive helper function.**
This function, `maxPathDown(node)`, will do two things:
- Return the maximum path sum starting at `node` and going straight down.
- Update the global `maxSum` as a side effect.

**Step 3: Define the base case.**
If the node is `null`, it contributes nothing to a path sum. So, we return 0.

```java
private int maxPathDown(TreeNode node) {
    if (node == null) {
        return 0;
    }
    // ... more logic
}
```

**Step 4: Add recursive calls and handle negative paths.**
Recursively call `maxPathDown` for the left and right children. An important edge case is that if a child's path sum is negative, we should not include it in our path. A path of `[node]` is better than `[node] -> [negative child path]`. So, we take `Math.max(0, ...)` to ignore negative contributions.

```java
private int maxPathDown(TreeNode node) {
    if (node == null) {
        return 0;
    }
    int leftPath = Math.max(0, maxPathDown(node.left));
    int rightPath = Math.max(0, maxPathDown(node.right));
    // ...
}
```

**Step 5: Update the global maximum sum.**
With the results from the children, we calculate the "V-shaped" path sum at the current node: `node.val + leftPath + rightPath`. We compare this to our global `maxSum` and update it if we've found a new maximum.

```java
private int maxPathDown(TreeNode node) {
    if (node == null) {
        return 0;
    }
    int leftPath = Math.max(0, maxPathDown(node.left));
    int rightPath = Math.max(0, maxPathDown(node.right));

    // Update global max with the "V-shaped" path sum
    maxSum = Math.max(maxSum, node.val + leftPath + rightPath);
    
    // ...
}
```

**Step 6: Return the maximum path going downwards.**
The function must return the value its parent can use: the maximum path sum starting from the current node and extending downwards through only *one* of its children. This is `node.val + Math.max(leftPath, rightPath)`.

```java
private int maxPathDown(TreeNode node) {
    if (node == null) {
        return 0;
    }
    int leftPath = Math.max(0, maxPathDown(node.left));
    int rightPath = Math.max(0, maxPathDown(node.right));

    maxSum = Math.max(maxSum, node.val + leftPath + rightPath);

    // Return the max path that can be extended upwards
    return node.val + Math.max(leftPath, rightPath);
}
```

**Final Code:**

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
    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxPathDown(root);
        return maxSum;
    }

    private int maxPathDown(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // Get max path from children, but ignore negative paths
        int leftPath = Math.max(0, maxPathDown(node.left));
        int rightPath = Math.max(0, maxPathDown(node.right));

        // Update the global maximum with the path that includes the current node as the "root" of the path
        maxSum = Math.max(maxSum, node.val + leftPath + rightPath);

        // Return the max path sum that can be extended by the parent node
        return node.val + Math.max(leftPath, rightPath);
    }
}
```

---

### Complexity Analysis

*   **Time Complexity: O(N)**
    *   We visit each node exactly once during our post-order traversal. `N` is the number of nodes.

*   **Space Complexity: O(H)**
    *   The space complexity is determined by the depth of the recursion stack. `H` is the height of the tree. In the worst case (a skewed tree), this is `O(N)`. In a balanced tree, it's `O(log N)`.
