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

### Build the intuition step by step

1. The last unused element of `postorder` is the current subtree's root.
2. Find that root inside `inorder`. Everything to its left forms the left subtree; everything to its right forms the right subtree.
3. Because postorder ends with the root and is structured `... left ... right ... root`, when we consume postorder **from the back** we must build the **right subtree before the left**.
4. Recurse, narrowing the inorder window each time.

### Why this works

A moving pointer `postIdx` (starting at the end of postorder) always points at the next root to place. Pairing it with the inorder split uniquely reconstructs the tree, since all values are distinct. A HashMap from value → inorder index gives `O(1)` root location.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Root is the last postorder element | `int rootVal = postorder[postIdx--];` |
| Split into left/right via inorder | `int mid = inorderIndex.get(rootVal);` |
| Right subtree before left | `root.right = build(...); root.left = build(...);` |
| O(1) root lookup | `Map<Integer,Integer> inorderIndex` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Recursive tree construction / divide and conquer (postorder variant)]
import java.util.HashMap;
import java.util.Map;

class Solution {
    private Map<Integer, Integer> inorderIndex;
    private int postIdx;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        inorderIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndex.put(inorder[i], i);
        }
        postIdx = postorder.length - 1;
        return build(postorder, 0, inorder.length - 1);
    }

    private TreeNode build(int[] postorder, int inStart, int inEnd) {
        if (inStart > inEnd) {
            return null;
        }
        int rootVal = postorder[postIdx--];
        TreeNode root = new TreeNode(rootVal);
        int mid = inorderIndex.get(rootVal);

        // Postorder consumed from the back => build RIGHT before LEFT.
        root.right = build(postorder, mid + 1, inEnd);
        root.left = build(postorder, inStart, mid - 1);
        return root;
    }
}
```

### Why each part exists

- **`inorderIndex` map** — turns the "find root in inorder" step from `O(n)` into `O(1)`.
- **`postIdx` as instance state** — a single global pointer walking postorder backward; every recursive call takes the next root.
- **Right-before-left ordering** — mandatory because we read postorder from the end (`...left, right, root`).

## Tree Problem Notes

- **Helper function?** Yes — the recursion needs extra parameters (the current inorder window). The public `buildTree` only sets up state.
- **Global variable?** Yes — `postIdx` must persist and decrement across all calls; a local would reset. `inorderIndex` is shared read-only state.
- **Computed at each node:** pick the root value, locate it in inorder, then recurse.
- **Returned to parent:** the constructed subtree root, which the parent wires as its `left`/`right`.
- **Recursion order:** right child **before** left, dictated by consuming postorder back-to-front.

## Complexity Analysis

- **Time Complexity:** $O(n)$ — each node created once, `O(1)` lookups.
- **Space Complexity:** $O(n)$ — map plus recursion stack (`O(h)` stack, `O(n)` map).

## Similar Problems

1. [LeetCode 105. Construct Binary Tree from Preorder and Inorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) — the preorder mirror (root first, build left before right).
2. [LeetCode 889. Construct Binary Tree from Preorder and Postorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/) — same divide-and-conquer idea, ambiguous trees.
3. [LeetCode 1008. Construct Binary Search Tree from Preorder Traversal](https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/) — BST-specific reconstruction.
