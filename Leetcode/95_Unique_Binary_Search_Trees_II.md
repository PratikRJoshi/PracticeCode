# Unique Binary Search Trees II

## Problem Description

**Problem Link:** [Unique Binary Search Trees II](https://leetcode.com/problems/unique-binary-search-trees-ii/)

Given an integer `n`, return *all the structurally unique **BST's** (binary search trees), which has exactly `n` nodes of unique values from `1` to `n`*. Return the answer in **any order**.

**Example 1:**

```
Input: n = 3
Output: [[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]
```

**Example 2:**

```
Input: n = 1
Output: [[1]]
```

**Constraints:**
- `1 <= n <= 8`

## Intuition/Main Idea

This is a **recursive/backtracking** problem. We need to generate all unique BSTs.

**Core Algorithm:**
1. For each value `i` from `1` to `n`, use `i` as root.
2. Recursively generate all left subtrees from `[start..i-1]`.
3. Recursively generate all right subtrees from `[i+1..end]`.
4. Combine each left subtree with each right subtree and attach to root.

**Why recursion works:** To build a BST with values `[start..end]`, we choose a root and recursively build left and right subtrees. This naturally generates all possible BSTs.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Generate all BSTs | generateTrees method - Lines 5-6 |
| Try each root | Root loop - Line 11 |
| Generate left subtrees | Left recursion - Line 13 |
| Generate right subtrees | Right recursion - Line 14 |
| Combine subtrees | Combination loop - Lines 16-20 |
| Return result | Return statement - Line 22 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    public List<TreeNode> generateTrees(int n) {
        return generateTrees(1, n);
    }
    
    private List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> result = new ArrayList<>();
        
        // Base case: no nodes
        if (start > end) {
            result.add(null);
            return result;
        }
        
        // Try each value as root
        for (int i = start; i <= end; i++) {
            // Generate all left subtrees
            List<TreeNode> leftTrees = generateTrees(start, i - 1);
            
            // Generate all right subtrees
            List<TreeNode> rightTrees = generateTrees(i + 1, end);
            
            // Combine each left subtree with each right subtree
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    result.add(root);
                }
            }
        }
        
        return result;
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Lines 9-12):** If `start > end`, no nodes to build, return list with `null` (represents empty subtree).

2. **Try Each Root (Line 11):** For each value `i` in `[start..end]`, use it as root.

3. **Generate Subtrees (Lines 13-14):** 
   - Left subtrees: values `[start..i-1]`
   - Right subtrees: values `[i+1..end]`

4. **Combine (Lines 16-20):** For each left subtree and each right subtree, create a new root node and attach them.

**Why helper function is required:**
- We need to generate trees for ranges `[start..end]`, not just `[1..n]`.
- The helper function allows recursive generation for any range.

**Why global variable is not required:**
- We build trees bottom-up and return lists, so no global state needed.

**What is calculated at current level:**
- All possible BSTs for the range `[start..end]`.
- Each BST uses a different value as root.

**What is returned to parent:**
- List of all BSTs for the current range.

**Recursive call order:**
- Pre-order: choose root first, then recursively build left and right subtrees.
- This ensures we build trees from bottom to top.

## Complexity Analysis

- **Time Complexity:** $O(4^n / n^{3/2})$ - Catalan number complexity for generating all BSTs.

- **Space Complexity:** $O(4^n / n^{3/2})$ for storing all BSTs.

## Similar Problems

Problems that can be solved using similar tree generation patterns:

1. **95. Unique Binary Search Trees II** (this problem) - Generate all BSTs
2. **96. Unique Binary Search Trees** - Count BSTs (DP)
3. **894. All Possible Full Binary Trees** - Generate full binary trees
4. **241. Different Ways to Add Parentheses** - Generate expressions
5. **22. Generate Parentheses** - Generate parentheses
6. **17. Letter Combinations of a Phone Number** - Generate combinations
7. **77. Combinations** - Generate combinations
8. **46. Permutations** - Generate permutations

