# [226. Invert Binary Tree](https://leetcode.com/problems/invert-binary-tree/)

Given the `root` of a binary tree, invert the tree, and return its root.

**Example 1:**

![Example 1](https://assets.leetcode.com/uploads/2021/03/14/invert1-tree.jpg)

```
Input: root = [4,2,7,1,3,6,9]
Output: [4,7,2,9,6,3,1]
```

**Example 2:**

![Example 2](https://assets.leetcode.com/uploads/2021/03/14/invert2-tree.jpg)

```
Input: root = [2,1,3]
Output: [2,3,1]
```

**Example 3:**

```
Input: root = []
Output: []
```

**Constraints:**

- The number of nodes in the tree is in the range `[0, 100]`.
- `-100 <= Node.val <= 100`

## Intuition/Main Idea:

The problem asks us to invert a binary tree, which means swapping every left child with its corresponding right child. This is a classic recursive problem where we can:

1. Invert the left subtree
2. Invert the right subtree
3. Swap the left and right children of the current node

The base case is when we reach a null node, in which case we simply return null.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Invert the tree | `TreeNode temp = root.left; root.left = root.right; root.right = temp;` |
| Return its root | `return root;` |
| Handle empty tree | `if (root == null) return null;` |

## Final Java Code & Learning Pattern:

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
    public TreeNode invertTree(TreeNode root) {
        // Base case: if the node is null, return null
        if (root == null) {
            return null;
        }
        
        // Recursively invert the left and right subtrees
        TreeNode invertedLeft = invertTree(root.left);
        TreeNode invertedRight = invertTree(root.right);
        
        // Swap the left and right children
        root.left = invertedRight;
        root.right = invertedLeft;
        
        // Return the inverted root
        return root;
    }
}
```

This solution uses a recursive approach to invert the binary tree:

1. If the current node is null (base case), we return null.
2. We recursively invert the left subtree and store the result.
3. We recursively invert the right subtree and store the result.
4. We swap the left and right children of the current node.
5. We return the current node (which now has its subtrees inverted).

The recursion naturally handles the traversal of the entire tree, ensuring that every node's children are swapped.

## Tree Problem Explanations:

### Why or why not a helper function is required:
A helper function is not required for this problem because:
- The main function signature already provides what we need (taking a root node and returning the inverted tree).
- The operation is straightforward and can be done directly in the main function.
- We don't need to maintain any additional state or parameters during the recursion.

### Why or why not a global variable is required:
A global variable is not required because:
- We don't need to track any cumulative state across recursive calls.
- Each recursive call is independent and only needs to swap its own children.
- The result is built up naturally through the recursion without needing shared state.

### What is calculated at the current level or node of the tree:
At each node, we:
1. Recursively get the inverted left subtree.
2. Recursively get the inverted right subtree.
3. Swap the left and right children of the current node.

### What is returned to the parent from the current level of the tree:
Each recursive call returns the root of the inverted subtree. This allows the parent to correctly attach the inverted subtrees.

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa:
In this problem, we need to make recursive calls to the children before swapping them (pre-order traversal) because:
- We need the fully inverted subtrees before we can attach them to the current node.
- The swapping operation depends on having the inverted subtrees ready.

## Alternative Implementation (Iterative):

We can also solve this problem iteratively using a queue or stack:

```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            
            // Swap the children
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;
            
            // Add children to the queue
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
        
        return root;
    }
}
```

This iterative solution uses a level-order traversal (BFS) to visit each node and swap its children.

## Complexity Analysis:

- **Time Complexity**: $O(n)$ where n is the number of nodes in the tree. We visit each node exactly once.
- **Space Complexity**: 
  - Recursive: $O(h)$ where h is the height of the tree. This is the space used by the recursion stack. In the worst case (skewed tree), this could be $O(n)$.
  - Iterative: $O(w)$ where w is the maximum width of the tree. In the worst case, this could be $O(n/2)$ which simplifies to $O(n)$.

## Similar Problems:

1. [100. Same Tree](https://leetcode.com/problems/same-tree/)
2. [101. Symmetric Tree](https://leetcode.com/problems/symmetric-tree/)
3. [104. Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/)
4. [110. Balanced Binary Tree](https://leetcode.com/problems/balanced-binary-tree/)
5. [543. Diameter of Binary Tree](https://leetcode.com/problems/diameter-of-binary-tree/)
