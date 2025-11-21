# Same Tree

## Problem Description

**Problem Link:** [Same Tree](https://leetcode.com/problems/same-tree/)

Given the roots of two binary trees `p` and `q`, write a function to check if they are the same or not.

Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.

**Example 1:**
```
Input: p = [1,2,3], q = [1,2,3]
Output: true
```

**Example 2:**
```
Input: p = [1,2], q = [1,null,2]
Output: false
```

**Example 3:**
```
Input: p = [1,2,1], q = [1,1,2]
Output: false
```

**Constraints:**
- The number of nodes in both trees is in the range `[0, 100]`.
- `-10^4 <= Node.val <= 10^4`

## Intuition/Main Idea

We need to check if two trees are identical. This requires checking structure and values.

**Core Algorithm:**
- If both nodes are null, they're same (return true)
- If one is null and other isn't, they're different (return false)
- If values differ, they're different (return false)
- Recursively check left and right subtrees

**Why recursion:** Trees have recursive structure. Checking if trees are same requires checking if roots match and if both subtrees match, which is naturally recursive.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Check if trees are same | Recursive function - Lines 4-15 |
| Handle null cases | Null checks - Lines 5-8 |
| Compare node values | Value comparison - Line 10 |
| Check subtrees recursively | Recursive calls - Line 12 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // Base case: both nodes are null, they're the same
        if (p == null && q == null) {
            return true;
        }
        
        // If one is null and other isn't, they're different
        if (p == null || q == null) {
            return false;
        }
        
        // If values differ, trees are different
        if (p.val != q.val) {
            return false;
        }
        
        // Recursively check left and right subtrees
        // Both must be same for trees to be same
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```

## Tree Problem Analysis

**Why or why not a helper function is required:**
- No helper function needed here. The main function handles recursion directly.

**Why or why not a global variable is required:**
- No global variable needed. We pass trees as parameters and return boolean.

**What all is calculated at the current level or node of the tree:**
- At current node: Check if both nodes are null, if one is null, if values match.

**What is returned to the parent from the current level of the tree:**
- Returns boolean: true if subtree rooted at current node matches, false otherwise.

**How to decide if a recursive call to children needs to be made before current node calculation or vice versa:**
- Current node check (null and value) happens first, then recursive calls. We need to validate current node before checking children.

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the minimum number of nodes in the two trees. We visit each node once.

**Space Complexity:** $O(h)$ where $h$ is the height of the tree for recursion stack. In worst case (skewed tree), $O(n)$.

## Similar Problems

- [Symmetric Tree](https://leetcode.com/problems/symmetric-tree/) - Check if tree is symmetric
- [Subtree of Another Tree](https://leetcode.com/problems/subtree-of-another-tree/) - Check if one tree is subtree of another
- [Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/) - Similar recursive pattern

