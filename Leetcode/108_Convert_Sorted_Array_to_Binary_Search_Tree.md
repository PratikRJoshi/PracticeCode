# Convert Sorted Array to Binary Search Tree

## Problem Description

**Problem Link:** [Convert Sorted Array to Binary Search Tree](https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/)

Given an integer array `nums` where the elements are sorted in **ascending order**, convert it to a **height-balanced** binary search tree.

**Example 1:**
```
Input: nums = [-10,-3,0,5,9]
Output: [0,-3,9,-10,null,5]
Explanation: [0,-3,9,-10,null,5] is also accepted:
```

**Example 2:**
```
Input: nums = [1,3]
Output: [3,1]
Explanation: [1,null,3] and [3,1] are both height-balanced BSTs.
```

**Constraints:**
- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` is sorted in a **strictly increasing** order.

## Intuition/Main Idea

To create a height-balanced BST from a sorted array, we should use the middle element as the root. This ensures balance.

**Core Algorithm:**
- Use divide and conquer approach
- Find middle element of current subarray
- Make it the root
- Recursively build left subtree from left half
- Recursively build right subtree from right half

**Why middle element:** Using the middle ensures the tree is balanced. Left and right subtrees will have roughly equal sizes, minimizing height.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Convert sorted array to BST | Recursive helper - Lines 6-20 |
| Height-balanced tree | Middle element as root - Line 12 |
| Build left subtree | Recursive call - Line 15 |
| Build right subtree | Recursive call - Line 16 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        // Call helper function with entire array range
        return buildBST(nums, 0, nums.length - 1);
    }
    
    // Helper function to build BST from subarray
    // left and right are inclusive indices
    private TreeNode buildBST(int[] nums, int left, int right) {
        // Base case: if left > right, no more elements
        if (left > right) {
            return null;
        }
        
        // Find middle element
        // This ensures height-balanced tree
        int mid = left + (right - left) / 2;
        
        // Create root node with middle element
        TreeNode root = new TreeNode(nums[mid]);
        
        // Recursively build left subtree from left half
        // Left half: [left, mid-1]
        root.left = buildBST(nums, left, mid - 1);
        
        // Recursively build right subtree from right half
        // Right half: [mid+1, right]
        root.right = buildBST(nums, mid + 1, right);
        
        return root;
    }
}
```

## Tree Problem Analysis

**Why or why not a helper function is required:**
- Helper function is required to pass array bounds (left, right indices) for the current subproblem.

**Why or why not a global variable is required:**
- No global variable needed. Array is passed as parameter.

**What all is calculated at the current level or node of the tree:**
- At current node: Create TreeNode with middle element value.

**What is returned to the parent from the current level of the tree:**
- Returns the root TreeNode of the subtree built from the current subarray.

**How to decide if a recursive call to children needs to be made before current node calculation or vice versa:**
- Current node creation happens first (line 12), then recursive calls to build children (lines 15-16). We need the root before building subtrees.

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the array. We visit each element once.

**Space Complexity:** $O(\log n)$ for recursion stack in balanced tree. In worst case (skewed), $O(n)$.

## Similar Problems

- [Convert Sorted List to Binary Search Tree](https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/) - Similar with linked list
- [Construct Binary Tree from Preorder and Inorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) - Build tree from traversals
- [Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/) - Validate BST property

