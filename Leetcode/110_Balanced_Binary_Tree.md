### 110. Balanced Binary Tree
Problem: https://leetcode.com/problems/balanced-binary-tree/

---

### Main Idea & Intuition

The problem asks us to determine if a binary tree is "height-balanced." This means that for *every single node* in the tree, the height of its left subtree and the height of its right subtree differ by at most 1.

This problem is a perfect example of a **post-order traversal** where the result from the children determines the result for the parent.

**Core Logic:**
To check if a node is balanced, we first need to know the heights of its left and right subtrees. This is a classic bottom-up approach. We can write a helper function that does two things:
1.  Calculates the height of a subtree.
2.  Checks if that subtree is balanced.

We can combine these two goals elegantly. Our helper function will return the height of a subtree *if it is balanced*. If it's unbalanced, we'll return a special flag value (like `-1`).

When a parent node gets the results from its children:
- If either child returned `-1`, the parent knows an imbalance exists below it. It doesn't need to do any more work and can immediately return `-1` to its own parent.
- If both children return valid heights, the parent checks if *it* is balanced by comparing the two heights. If `abs(leftHeight - rightHeight) > 1`, it's unbalanced and returns `-1`. 
- Otherwise, it is balanced, and it returns its own height: `1 + max(leftHeight, rightHeight)`. 

This avoids using a global variable by encoding the "isBalanced" status directly into the return value of our height-calculating function.

---

### Step-by-Step to the Final Code

**Step 1: Define the main function.**
The main function `isBalanced` will call our recursive helper and check the final result. If the helper returns `-1`, the tree is unbalanced; otherwise, it's balanced.

```java
class Solution {
    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }
    
    // Helper function to be defined next
}
```

**Step 2: Create the recursive helper `checkHeight`.**
This function will return the height of the tree rooted at `node` if it's balanced, or `-1` if it's not.

**Step 3: Define the base case.**
An empty tree (`node == null`) is balanced and has a height of 0.

```java
private int checkHeight(TreeNode node) {
    if (node == null) {
        return 0;
    }
    // ... more logic
}
```

**Step 4: Add recursive calls and check for imbalance from children.**
Recursively call `checkHeight` on the left and right children. After each call, check if the returned value is `-1`. If it is, an imbalance was found below, so we immediately stop and propagate the `-1` upwards.

```java
private int checkHeight(TreeNode node) {
    if (node == null) {
        return 0;
    }

    int leftHeight = checkHeight(node.left);
    if (leftHeight == -1) {
        return -1; // Propagate imbalance
    }

    int rightHeight = checkHeight(node.right);
    if (rightHeight == -1) {
        return -1; // Propagate imbalance
    }
    // ...
}
```

**Step 5: Check the balance at the current node.**
If the children are balanced, we now check the current node. If the absolute difference in their heights is greater than 1, the current node is unbalanced. Return `-1`.

```java
private int checkHeight(TreeNode node) {
    // ... (recursive calls from Step 4)

    if (Math.abs(leftHeight - rightHeight) > 1) {
        return -1; // Current node is unbalanced
    }
    // ...
}
```

**Step 6: Return the height if balanced.**
If the current node is balanced, its height is `1 + max(leftHeight, rightHeight)`. Return this value.

```java
private int checkHeight(TreeNode node) {
    // ... (checks from Step 4 & 5)

    // Return height if balanced
    return 1 + Math.max(leftHeight, rightHeight);
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
    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    // Returns the height of the tree if it's balanced, otherwise returns -1.
    private int checkHeight(TreeNode node) {
        // Base case: An empty tree is balanced and has height 0.
        if (node == null) {
            return 0;
        }

        // Recursively get the height of the left subtree.
        // If it's unbalanced, propagate the -1 up.
        int leftHeight = checkHeight(node.left);
        if (leftHeight == -1) {
            return -1;
        }

        // Recursively get the height of the right subtree.
        // If it's unbalanced, propagate the -1 up.
        int rightHeight = checkHeight(node.right);
        if (rightHeight == -1) {
            return -1;
        }

        // Check if the current node is balanced.
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        // If balanced, return its height.
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
```

---

### Complexity Analysis

*   **Time Complexity: O(N)**
    *   We visit each node exactly once in a post-order traversal. `N` is the number of nodes.

*   **Space Complexity: O(H)**
    *   The space complexity is determined by the recursion stack depth, which is the height of the tree, `H`. In the worst case (a skewed tree), this is `O(N)`. In a balanced tree, it's `O(log N)`.
