# Symmetric Tree

## Problem Description

**Problem Link:** [Symmetric Tree](https://leetcode.com/problems/symmetric-tree/)

Given the `root` of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).

**Example 1:**
```
Input: root = [1,2,2,3,4,4,3]
Output: true
```

**Example 2:**
```
Input: root = [1,2,2,null,3,null,3]
Output: false
```

**Constraints:**
- The number of nodes in the tree is in the range `[1, 1000]`.
- `-100 <= Node.val <= 100`

## Intuition/Main Idea

A tree is symmetric if the left subtree is a mirror of the right subtree. We need to compare nodes in a mirrored way.

**Core Algorithm:**
- Compare root's left and right subtrees
- Two nodes are symmetric if:
  - Their values are equal
  - Left's left subtree mirrors right's right subtree
  - Left's right subtree mirrors right's left subtree

**Why recursive comparison:** We need to check if subtrees mirror each other. This naturally requires comparing corresponding nodes recursively.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Check if tree is symmetric | Main function - Lines 4-7 |
| Compare mirrored nodes | Helper function - Lines 9-20 |
| Handle null cases | Null checks - Lines 11-14 |
| Recursive comparison | Recursive calls - Line 18 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        // A tree is symmetric if left and right subtrees mirror each other
        // Handle null root case
        if (root == null) {
            return true;
        }
        
        // Compare left and right subtrees
        return isMirror(root.left, root.right);
    }
    
    // Helper function to check if two subtrees mirror each other
    private boolean isMirror(TreeNode left, TreeNode right) {
        // Both null: symmetric
        if (left == null && right == null) {
            return true;
        }
        
        // One null, one not: not symmetric
        if (left == null || right == null) {
            return false;
        }
        
        // Values must match
        // And subtrees must mirror: left.left mirrors right.right, left.right mirrors right.left
        return (left.val == right.val) 
            && isMirror(left.left, right.right) 
            && isMirror(left.right, right.left);
    }
}
```

## Tree Problem Analysis

**Why or why not a helper function is required:**
- Helper function is required to compare two subtrees. The main function only handles the root case.

**Why or why not a global variable is required:**
- No global variable needed. We pass subtrees as parameters and return boolean.

**What all is calculated at the current level or node of the tree:**
- At current comparison: Check if two nodes are null, if values match.

**What is returned to the parent from the current level of the tree:**
- Returns boolean: true if subtrees mirror each other, false otherwise.

**How to decide if a recursive call to children needs to be made before current node calculation or vice versa:**
- Current node check (null and value) happens first (lines 11-16), then recursive calls (line 18). We validate current nodes before checking children.

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the number of nodes. We visit each node once.

**Space Complexity:** $O(h)$ where $h$ is the height of the tree for recursion stack. In worst case (skewed), $O(n)$.

## Similar Problems

- [Same Tree](https://leetcode.com/problems/same-tree/) - Check if trees are identical
- [Invert Binary Tree](https://leetcode.com/problems/invert-binary-tree/) - Mirror the tree
- [Subtree of Another Tree](https://leetcode.com/problems/subtree-of-another-tree/) - Check subtree relationship

