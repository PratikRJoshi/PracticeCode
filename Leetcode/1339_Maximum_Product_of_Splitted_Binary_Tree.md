# Maximum Product of Splitted Binary Tree

## Problem Description

**Problem Link:** [Maximum Product of Splitted Binary Tree](https://leetcode.com/problems/maximum-product-of-splitted-binary-tree/)

Given the `root` of a binary tree, split the binary tree into two subtrees by removing one edge such that the product of the sums of the subtrees is maximized.

Return *the maximum product of the sums of the two subtrees*. Since the answer may be too large, return it **modulo** $10^9 + 7$.

**Note** that you need to maximize the answer before taking the mod and not after taking it.

**Example 1:**

```
Input: root = [1,2,3,4,5,6]
Output: 110
Explanation: Remove the red edge and get 2 binary trees with sum 11 and 10. Their product is 110 (11*10)
```

**Example 2:**

```
Input: root = [1,null,2,3,4,null,null,5,6]
Output: 90
Explanation: Remove the red edge and get 2 binary trees with sum 15 and 6. Their product is 90 (15*6)
```

**Constraints:**
- The number of nodes in the tree is in the range `[2, 5 * 10^4]`.
- `1 <= Node.val <= 10^4`

## Intuition/Main Idea

This is a **tree DP** problem. We need to find the edge to remove that maximizes the product of subtree sums.

**Core Algorithm:**
1. Calculate total sum of the tree.
2. For each edge (parent-child connection), calculate the sum of the subtree below.
3. The product is `subtreeSum * (totalSum - subtreeSum)`.
4. Find the maximum product.

**Why this works:** When we remove an edge, we split the tree into two parts. The sum of one part is the subtree sum, and the other is `totalSum - subtreeSum`. We want to maximize their product.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Calculate total sum | First DFS - Lines 7-8 |
| Find maximum product | Second DFS - Lines 10-18 |
| Calculate subtree sum | DFS return - Line 15 |
| Calculate product | Product calculation - Line 16 |
| Track maximum | Max tracking - Line 17 |
| Return result | Return statement - Line 19 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    private long totalSum = 0;
    private long maxProduct = 0;
    private static final int MOD = 1000000007;
    
    public int maxProduct(TreeNode root) {
        // First pass: calculate total sum
        totalSum = getSum(root);
        
        // Second pass: find maximum product
        findMaxProduct(root);
        
        return (int) (maxProduct % MOD);
    }
    
    private long getSum(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return node.val + getSum(node.left) + getSum(node.right);
    }
    
    private long findMaxProduct(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        // Calculate subtree sum
        long subtreeSum = node.val + 
                         findMaxProduct(node.left) + 
                         findMaxProduct(node.right);
        
        // Calculate product if we remove edge above this node
        long product = subtreeSum * (totalSum - subtreeSum);
        maxProduct = Math.max(maxProduct, product);
        
        return subtreeSum;
    }
}
```

**Explanation of Key Code Sections:**

1. **Calculate Total Sum (Lines 7-8):** First DFS pass to calculate the total sum of all nodes.

2. **Find Maximum Product (Lines 10-18):** Second DFS pass:
   - **Subtree Sum (Line 15):** Calculate sum of subtree rooted at current node.
   - **Product (Line 16):** If we remove the edge above this node, product is `subtreeSum * (totalSum - subtreeSum)`.
   - **Track Maximum (Line 17):** Keep track of the maximum product.

**Why helper function is required:**
- We need two passes: one to calculate total sum, another to find maximum product.
- The helper function `findMaxProduct` does both: calculates subtree sum and updates maximum product.

**Why global variable is required:**
- `maxProduct` needs to be updated during traversal and accessed across recursive calls.
- `totalSum` is calculated once and used in all product calculations.

**What is calculated at current node:**
- Subtree sum: sum of all nodes in subtree rooted at current node.
- Product: `subtreeSum * (totalSum - subtreeSum)` if edge above is removed.

**What is returned to parent:**
- Subtree sum: so parent can calculate its own subtree sum.

**Recursive call order:**
- Post-order traversal: calculate children's subtree sums first, then calculate current node's subtree sum.
- This ensures we have all necessary information before calculating the product.

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the number of nodes. We traverse the tree twice.

- **Space Complexity:** $O(h)$ where $h$ is the height of the tree for recursion stack.

## Similar Problems

Problems that can be solved using similar tree DP patterns:

1. **1339. Maximum Product of Splitted Binary Tree** (this problem) - Tree DP with product
2. **124. Binary Tree Maximum Path Sum** - Tree DP with path sum
3. **543. Diameter of Binary Tree** - Tree DP with diameter
4. **337. House Robber III** - Tree DP with constraints
5. **968. Binary Tree Cameras** - Tree DP with cameras
6. **979. Distribute Coins in Binary Tree** - Tree DP with coins
7. **1120. Maximum Average Subtree** - Tree DP with average
8. **1123. Lowest Common Ancestor of Deepest Leaves** - Tree DP with LCA

