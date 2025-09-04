### 112. Path Sum
Problem: https://leetcode.com/problems/path-sum/

---

### Main Idea & Intuition

This problem asks if there's a root-to-leaf path that sums up to a given `targetSum`. This is the classic example of a **top-down (pre-order)** recursive approach.

**Core Logic:**
The idea is to pass information from parents down to their children. As we traverse from the root downwards, we keep track of the remaining sum we need to find.

At each node, we do the following:
1.  Subtract the current node's value from the sum we are looking for.
2.  Check if the current node is a leaf.
3.  If it is a leaf, we check if the remaining sum is exactly zero. If it is, we've found a valid path.
4.  If it's not a leaf, we recursively call the function on its children, passing the *new, updated remaining sum* down to them.

**Intuition:** "I'll account for my own value, and then I'll ask my children to solve a slightly smaller version of the problem."

Because we only need to find one such path, if either the left or the right child finds a valid path, the result for the current node is `true`.

---

### Step-by-Step to the Final Code

**Step 1: Define the main recursive function.**
The function `hasPathSum` will take the current `node` and the `targetSum` we need to achieve from this point downwards.

**Step 2: Define the base case for an invalid path.**
If the current node is `null`, it cannot be part of a path. So, we return `false`.

```java
public boolean hasPathSum(TreeNode node, int targetSum) {
    if (node == null) {
        return false;
    }
    // ... more logic
}
```

**Step 3: Process the current node (Pre-order action).**
Subtract the current node's value from the `targetSum`.

```java
public boolean hasPathSum(TreeNode node, int targetSum) {
    if (node == null) {
        return false;
    }
    targetSum -= node.val;
    // ...
}
```

**Step 4: Check for the success condition (at a leaf).**
If the current node is a leaf (it has no left and no right child), we check if the remaining `targetSum` is zero. If it is, we've successfully found a path.

```java
public boolean hasPathSum(TreeNode node, int targetSum) {
    // ...
    targetSum -= node.val;

    if (node.left == null && node.right == null) {
        return targetSum == 0;
    }
    // ...
}
```

**Step 5: Make the recursive calls.**
If the current node is not a leaf, we continue the search in its children. We call `hasPathSum` on the left and right children, passing the updated `targetSum`. Since we only need one valid path, we use the `||` (OR) operator to combine the results.

```java
public boolean hasPathSum(TreeNode node, int targetSum) {
    // ...

    // Recurse on children with the updated sum
    return hasPathSum(node.left, targetSum) || hasPathSum(node.right, targetSum);
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
    public boolean hasPathSum(TreeNode root, int targetSum) {
        // Base case: if the tree is empty, there's no path.
        if (root == null) {
            return false;
        }

        // Subtract the current node's value from the target.
        targetSum -= root.val;

        // Check if it's a leaf node.
        if (root.left == null && root.right == null) {
            // If it's a leaf, the path is valid only if the remaining sum is zero.
            return targetSum == 0;
        }

        // If it's not a leaf, recurse on the children.
        // Return true if a valid path is found in either the left or right subtree.
        return hasPathSum(root.left, targetSum) || hasPathSum(root.right, targetSum);
    }
}
```

---

### Complexity Analysis

*   **Time Complexity: O(N)**
    *   In the worst case, we have to visit every node in the tree once. `N` is the number of nodes.

*   **Space Complexity: O(H)**
    *   The space complexity is determined by the recursion stack depth, which is the height of the tree, `H`. In the worst case (a skewed tree), this is `O(N)`. In a balanced tree, it's `O(log N)`.
