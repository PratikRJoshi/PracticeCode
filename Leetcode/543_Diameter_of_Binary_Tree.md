### 543. Diameter of Binary Tree
Problem: https://leetcode.com/problems/diameter-of-binary-tree/

The goal is to find the length of the longest path between any two nodes in a binary tree. This path might not pass through the root. The length is measured by the number of edges.

---

### Main Idea & Intuition

The key insight is that for any node in the tree, the longest path that passes *through that specific node* is the sum of the heights of its left and right subtrees. The diameter of the entire tree is simply the maximum of these path lengths calculated for *every node*.

Let's think about a single node `N`:
- A path going through `N` must connect a node in its left subtree to a node in its right subtree.
- To make this path as long as possible, it should start at the deepest node in the left subtree, go up to `N`, and then go down to the deepest node in the right subtree.
- The length of this path is `height(left subtree) + height(right subtree)`.

We can use a single Depth First Search (DFS) traversal to solve this efficiently. During the traversal, for each node, we will do two things:
1.  **Calculate its height:** The height of a node is `1 + max(height of left child, height of right child)`. We return this value up the recursion stack.
2.  **Update the diameter:** We calculate the potential diameter at the current node (`height(left) + height(right)`) and update a global maximum if this path is the longest one seen so far.

---

### Step-by-Step to the Final Code

Let's build the solution logically.

**Step 1: Define the main function and a global variable.**
We need a main function, `diameterOfBinaryTree`, that takes the `root` of the tree. We also need a variable, let's call it `maxDiameter`, to keep track of the longest path found so far. This variable will be updated by our recursive helper function.

```java
class Solution {
    private int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        // We will call our helper function here
        height(root);
        return maxDiameter;
    }
    
    // Helper function to be defined next
}
```

**Step 2: Create a recursive helper function for DFS.**
This function, let's call it `height(node)`, will be the core of our solution. It needs to do two jobs: calculate the height of the subtree rooted at `node`, and update `maxDiameter`.

**Step 3: Define the base case for the recursion.**
If our `height` function receives a `null` node, it means we've reached the end of a branch. The height of a null tree is 0.

```java
private int height(TreeNode node) {
    if (node == null) {
        return 0;
    }
    // ... more logic to come
}
```

**Step 4: Add the recursive calls.**
For any non-null node, we need to find the heights of its left and right children. We do this by calling `height` on them recursively.

```java
private int height(TreeNode node) {
    if (node == null) {
        return 0;
    }
    // Recursively find the height of left and right subtrees
    int leftHeight = height(node.left);
    int rightHeight = height(node.right);
    // ...
}
```

**Step 5: Update the global diameter.**
Now that we have the heights of the left and right subtrees (`leftHeight` and `rightHeight`), we can calculate the length of the longest path that passes through the *current node*. This is simply `leftHeight + rightHeight`. We compare this value with our `maxDiameter` and update it if we've found a new longest path.

```java
private int height(TreeNode node) {
    if (node == null) {
        return 0;
    }
    int leftHeight = height(node.left);
    int rightHeight = height(node.right);

    // Update the max diameter found so far
    maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);
    
    // ...
}
```

**Step 6: Return the height of the current node.**
Finally, the `height` function must fulfill its primary duty: returning the height of the subtree rooted at the current `node`. The height of a node is 1 (for the node itself) plus the height of its tallest child subtree.

```java
private int height(TreeNode node) {
    if (node == null) {
        return 0;
    }
    int leftHeight = height(node.left);
    int rightHeight = height(node.right);

    maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);

    // Return the height of this subtree to the caller
    return 1 + Math.max(leftHeight, rightHeight);
}
```

**Final Code:**

Putting it all together, we get the complete solution.

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
    private int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        height(root);
        return maxDiameter;
    }

    private int height(TreeNode node) {
        // Base Case: An empty tree has a height of 0.
        if (node == null) {
            return 0;
        }

        // Recursively calculate the height of the left and right subtrees.
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        // The diameter at this node is the sum of the heights of its children.
        // Update the global maximum if this path is longer.
        maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);

        // Return the height of the tree rooted at this node.
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
```

---

### Complexity Analysis

*   **Time Complexity: O(N)**
    *   We visit each node in the tree exactly once during our DFS traversal. `N` is the number of nodes in the tree.

*   **Space Complexity: O(H)**
    *   The space complexity is determined by the depth of the recursion stack. In the worst case (a skewed tree, like a linked list), the height `H` of the tree can be `N`. In the best case (a balanced tree), the height is `log(N)`. Therefore, the space complexity is `O(H)`.

---
