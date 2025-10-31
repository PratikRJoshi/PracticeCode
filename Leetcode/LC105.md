### 105. Construct Binary Tree from Preorder and Inorder Traversal

#### Problem Statement
[Construct Binary Tree from Preorder and Inorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)

---

### Main Idea & Intuition

The key to this problem lies in the properties of `preorder` and `inorder` traversals:

1.  **Preorder Traversal (`Root`, `Left`, `Right`)**: The first element in the preorder array is always the root of the current tree or subtree.
2.  **Inorder Traversal (`Left`, `Root`, `Right`)**: Once the root is identified, the inorder array clearly separates the nodes in the left subtree from those in the right subtree.

The algorithm works recursively:
1.  **Identify Root**: The first element of the current `preorder` segment is the `root`.
2.  **Find Root in Inorder**: Locate this `root` in the `inorder` segment. This is the pivot point.
3.  **Partition**: All elements to the left of the `root` in the `inorder` segment belong to the left subtree. All elements to the right belong to the right subtree.
4.  **Recurse**: The number of elements in the left partition (`leftSubtreeSize`) tells us how to slice the `preorder` array for the recursive calls. We use this information to build the left and right subtrees recursively.

To optimize finding the root's index in the `inorder` array, I'll use a `HashMap` for `O(1)` lookups.

### Code Implementation

```java
import java.util.HashMap;
import java.util.Map;

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
    private int preorderIndex;
    private Map<Integer, Integer> inorderIndexMap;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        preorderIndex = 0;
        // Build a hashmap to store value -> index relations
        inorderIndexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }

        return buildTreeHelper(preorder, 0, preorder.length - 1);
    }

    private TreeNode buildTreeHelper(int[] preorder, int left, int right) {
        // If there are no elements to construct the tree
        if (left > right) {
            return null;
        }

        // Select the preorder_index element as the root and increment it
        int rootValue = preorder[preorderIndex++];
        TreeNode root = new TreeNode(rootValue);

        // Build left and right subtree
        // Exclude inorderIndexMap.get(rootValue) element because it's the root
        root.left = buildTreeHelper(preorder, left, inorderIndexMap.get(rootValue) - 1);
        root.right = buildTreeHelper(preorder, inorderIndexMap.get(rootValue) + 1, right);
        
        return root;
    }
}
```

### Complexity Analysis
*   **Time Complexity**: `O(N)`, where `N` is the number of nodes. Building the `HashMap` takes `O(N)` time. The recursive helper function processes each node once, leading to an overall `O(N)` complexity.
*   **Space Complexity**: `O(N)`. The `inorderIndexMap` requires `O(N)` space. The recursion stack also requires space. In the average case of a balanced tree, it's `O(log N)`, but in the worst case of a skewed tree, it's `O(N)`.
