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

This version passes explicit `preStart/preEnd` and `inStart/inEnd` index windows for both arrays and finds the root in `inorder` with a linear scan. `inorderLength = rootInInorder - inStart` (the size of the left subtree) is the ruler that slices the preorder window: left subtree = `[preStart+1, preStart+inorderLength]`, right subtree = `[preStart+inorderLength+1, preEnd]`.

(Optimization: replacing the linear scan with a `HashMap<value, index>` lookup drops the time from `O(N^2)` worst case to `O(N)`.)

### Code Implementation

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
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length != inorder.length){
            return null;
        }

        return buildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length -1);
    }

    private TreeNode buildTree(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd){
        if(preStart > preEnd || inStart > inEnd){
            return null;
        }

        TreeNode root = new TreeNode(preorder[preStart]);
        int rootInInorder = 0;
        for(int i = 0; i < inorder.length; i++){
            if(inorder[i] == root.val){
                rootInInorder = i;
                break;
            }
        }

        int inorderLength = rootInInorder - inStart;

        root.left = buildTree(preorder, preStart + 1, preStart + inorderLength, inorder, inStart, rootInInorder - 1);
        root.right = buildTree(preorder, preStart + inorderLength + 1, preEnd, inorder, rootInInorder + 1, inEnd);

        return root;
    }
}
```

### Complexity Analysis
*   **Time Complexity**: `O(N^2)` worst case for this version — each of the `N` nodes does an `O(N)` linear scan to locate the root in `inorder` (degrades on skewed trees). With a `HashMap` for root lookups it becomes `O(N)`.
*   **Space Complexity**: `O(N)` — recursion stack (`O(log N)` balanced, `O(N)` skewed). The `HashMap` variant adds `O(N)` for the index map.
