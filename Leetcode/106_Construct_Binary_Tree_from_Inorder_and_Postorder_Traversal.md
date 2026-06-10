# 106. Construct Binary Tree from Inorder and Postorder Traversal

## Problem Description

[Construct Binary Tree from Inorder and Postorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/)

Given two integer arrays `inorder` and `postorder` where `inorder` is the inorder traversal of a binary tree and `postorder` is the postorder traversal of the same tree, construct and return the binary tree.

### Example 1

Input: `inorder = [9,3,15,20,7]`, `postorder = [9,15,7,20,3]`

Output: `[3,9,20,null,null,15,7]`

### Example 2

Input: `inorder = [-1]`, `postorder = [-1]`

Output: `[-1]`

### Constraints

- `1 <= inorder.length <= 3000`, `postorder.length == inorder.length`.
- All values are unique and appear in both arrays.

## Intuition / Main Idea

This is the mirror of "preorder + inorder" (LC 105). In **postorder** (`left, right, root`), the **last** element is the root. Inorder (`left, root, right`) tells us how many nodes are in the left vs right subtree.

### Build the intuition step by step (explicit index windows)

This version mirrors the LC 105 (preorder + inorder) solution, passing explicit index windows for **both** arrays instead of a moving global pointer.

1. The **last** element of the current `postorder` window (`postorder[postEnd]`) is the subtree's root.
2. Find that root inside `inorder` (linear scan). `inorderLength = rootInInorder - inStart` = the number of nodes in the left subtree — the ruler that slices the postorder window.
3. **Postorder slicing**: left subtree = `[postStart, postStart + inorderLength - 1]`; right subtree = `[postStart + inorderLength, postEnd - 1]` (the trailing `-1` drops the root that sits at the end).
4. **Inorder slicing**: left = `[inStart, rootInInorder - 1]`; right = `[rootInInorder + 1, inEnd]`.
5. Build left then right (natural order — no global pointer to force right-first).

### Why this works

`inorderLength` keeps both arrays' windows in lockstep: each subtree has the same node count in inorder and postorder. Since all values are unique, the root split is unambiguous, so the reconstruction is exact.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Root is the last postorder element | `new TreeNode(postorder[postEnd])` |
| Locate root in inorder | linear scan setting `rootInInorder` |
| Left/right subtree sizes match across arrays | `inorderLength = rootInInorder - inStart` |
| Slice postorder windows | left `[postStart, postStart+inorderLength-1]`, right `[postStart+inorderLength, postEnd-1]` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Recursive tree construction / divide and conquer (explicit index windows)]
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
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder.length != postorder.length) {
            return null;
        }
        return buildTree(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    private TreeNode buildTree(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd) {
        if (inStart > inEnd || postStart > postEnd) {
            return null;
        }

        TreeNode root = new TreeNode(postorder[postEnd]); // postorder: root is LAST
        int rootInInorder = 0;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == root.val) {
                rootInInorder = i;
                break;
            }
        }

        int inorderLength = rootInInorder - inStart; // size of the left subtree

        root.left = buildTree(inorder, inStart, rootInInorder - 1, postorder, postStart, postStart + inorderLength - 1);
        root.right = buildTree(inorder, rootInInorder + 1, inEnd, postorder, postStart + inorderLength, postEnd - 1);

        return root;
    }
}
```

### Why each part exists

- **`postorder[postEnd]`** — in postorder (`left, right, root`) the root is the last element of the current window.
- **Explicit index windows for both arrays** — no global mutable pointer; each call is self-contained, which makes the left/right order irrelevant (build left then right naturally).
- **`inorderLength`** — bridges the two arrays so their windows always describe the same set of nodes.
- **`postEnd - 1` on the right slice** — excludes the root, which sits at the very end of the postorder window.

## Tree Problem Notes

- **Helper function?** Yes — the recursion needs the four window bounds as parameters. The public `buildTree` just kicks off with the full ranges.
- **Global variable?** No — this version is stateless across calls; all context is passed via the index windows (contrast with the moving-pointer variant, which needs a persistent `postIdx`).
- **Computed at each node:** take the last postorder element as root, locate it in inorder, compute `inorderLength`, then recurse on the sliced windows.
- **Returned to parent:** the constructed subtree root, wired as the parent's `left`/`right`.
- **Recursion order:** left or right first doesn't matter here, since each call's window is explicit (unlike the back-to-front pointer variant, which forces right-before-left).

## Complexity Analysis

- **Time Complexity:** $O(n^2)$ worst case — each of the `n` nodes does an `O(n)` linear scan to find the root in inorder (degrades on skewed trees). A `HashMap<value, index>` for root lookup restores `O(n)`.
- **Space Complexity:** $O(n)$ — recursion stack (`O(h)`: `O(\log n)` balanced, `O(n)` skewed). The HashMap variant adds `O(n)`.

## Similar Problems

1. [LeetCode 105. Construct Binary Tree from Preorder and Inorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) — the preorder mirror (root first, build left before right).
2. [LeetCode 889. Construct Binary Tree from Preorder and Postorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/) — same divide-and-conquer idea, ambiguous trees.
3. [LeetCode 1008. Construct Binary Search Tree from Preorder Traversal](https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/) — BST-specific reconstruction.
