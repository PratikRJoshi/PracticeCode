# 95. Unique Binary Search Trees II

[LeetCode Link](https://leetcode.com/problems/unique-binary-search-trees-ii/)

## Problem Description

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

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Generate all structurally unique BSTs | `generateTrees(start, end)` returns list of all trees for a range |
| Each value `i` can be a root | Loop `for (int i = start; i <= end; i++)` |
| Left subtree uses values `< i` | Recursively generate left trees from `[start..i-1]` |
| Right subtree uses values `> i` | Recursively generate right trees from `[i+1..end]` |
| Combine all left/right pairs | Nested loops over `leftTrees` and `rightTrees` |

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

## Tree Problems

### Why or why not a helper function is required
- A helper is required because the recursive subproblem is “generate all BSTs for a value range `[start..end]`”, not just `[1..n]`.

### Why or why not a global variable is required
- A global variable is not required because each recursion returns its list of trees and the parent combines them. No shared accumulation is needed.

### What all is calculated at the current level or node of the tree
- For a chosen root value `i`, we compute:
  - all possible left subtrees from `[start..i-1]`
  - all possible right subtrees from `[i+1..end]`
  - then combine every `(left, right)` pair under a new `TreeNode(i)`.

### What is returned to the parent from the current level of the tree
- The list of all unique BST roots that can be formed using exactly the values in `[start..end]`.

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- You must generate children lists first because the current level’s work is the Cartesian product of `(leftSubtree, rightSubtree)` options.
- So: pick root `i` -> recurse to get left options and right options -> then create roots and attach.

## Complexity Analysis

- **Time Complexity:** $O(4^n / n^{3/2})$ - Catalan number complexity for generating all BSTs.

- **Space Complexity:** $O(4^n / n^{3/2})$ for storing all BSTs.

## Similar Problems

Problems that can be solved using similar tree generation patterns:

1. [95. Unique Binary Search Trees II](https://leetcode.com/problems/unique-binary-search-trees-ii/) (this problem)
2. [96. Unique Binary Search Trees](https://leetcode.com/problems/unique-binary-search-trees/) (count BSTs / Catalan DP)
3. [894. All Possible Full Binary Trees](https://leetcode.com/problems/all-possible-full-binary-trees/) (generate all tree shapes)
4. [241. Different Ways to Add Parentheses](https://leetcode.com/problems/different-ways-to-add-parentheses/) (generate all structures)
5. [22. Generate Parentheses](https://leetcode.com/problems/generate-parentheses/) (generate all valid structures)
6. [17. Letter Combinations of a Phone Number](https://leetcode.com/problems/letter-combinations-of-a-phone-number/) (generate combinations)
7. [77. Combinations](https://leetcode.com/problems/combinations/) (generate combinations)
8. [46. Permutations](https://leetcode.com/problems/permutations/) (generate permutations)

