# House Robber III

## Problem Description

**Problem Link:** [House Robber III](https://leetcode.com/problems/house-robber-iii/)

The thief has found himself a new place for his thievery again. There is only one entrance to this area, called `root`.

Besides the `root`, each house has one and only one parent house. After a tour, the smart thief realized that all houses in this place form a binary tree. It will automatically contact the police if **two directly-linked houses were broken into on the same night**.

Given the `root` of the binary tree, return *the maximum amount of money the thief can rob **without alerting the police***.

**Example 1:**

```
Input: root = [3,2,3,null,3,null,1]
Output: 7
Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
```

**Example 2:**

```
Input: root = [3,4,5,1,3,null,1]
Output: 9
Explanation: Maximum amount of money the thief can rob = 4 + 5 = 9.
```

**Constraints:**
- The number of nodes in the tree is in the range `[1, 10^4]`.
- `0 <= Node.val <= 10^4`

## Intuition/Main Idea

This is a **tree DP** problem. We need to find the maximum money we can rob without robbing two directly connected nodes.

**Core Algorithm:**
1. For each node, we have two choices:
   - Rob this node: can't rob children, can rob grandchildren.
   - Don't rob this node: can rob children.
2. Use DP where we return `[rob, notRob]` for each subtree.
3. `rob = node.val + left[notRob] + right[notRob]`
4. `notRob = max(left[rob], left[notRob]) + max(right[rob], right[notRob])`

**Why tree DP works:** The problem has overlapping subproblems - calculating maximum money for subtrees is needed multiple times. We can memoize results.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| DP for rob/notRob | Return array - Line 6 |
| Base case: null | Null check - Lines 8-11 |
| Recurse on children | Recursive calls - Lines 13-14 |
| Calculate rob value | Rob calculation - Line 17 |
| Calculate notRob value | NotRob calculation - Line 18 |
| Return result | Return statement - Line 19 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int rob(TreeNode root) {
        int[] result = robHelper(root);
        return Math.max(result[0], result[1]);
    }
    
    private int[] robHelper(TreeNode node) {
        // [rob, notRob]
        if (node == null) {
            return new int[]{0, 0};
        }
        
        int[] left = robHelper(node.left);
        int[] right = robHelper(node.right);
        
        // Rob current node: can't rob children
        int rob = node.val + left[1] + right[1];
        
        // Don't rob current node: can rob children (choose max for each)
        int notRob = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        
        return new int[]{rob, notRob};
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Lines 8-11):** If node is null, return `[0, 0]` (no money to rob).

2. **Recurse on Children (Lines 13-14):** Get results for left and right subtrees.

3. **Rob Current Node (Line 17):** If we rob current node, we can't rob children, so add `left[1] + right[1]`.

4. **Don't Rob Current Node (Line 18):** If we don't rob current node, we can rob children. For each child, choose the maximum of robbing or not robbing.

**Why helper function is required:**
- We need to return two values (rob and notRob) for each node.
- The helper function allows us to return an array with both values.

**Why global variable is not required:**
- We calculate values bottom-up and return them, so no global state needed.

**What is calculated at current node:**
- Maximum money if we rob this node.
- Maximum money if we don't rob this node.

**What is returned to parent:**
- Array `[rob, notRob]` for the current subtree.

**Recursive call order:**
- Post-order traversal: calculate children's values first, then calculate current node's values.
- This ensures we have all necessary information before making decisions.

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the number of nodes. We visit each node once.

- **Space Complexity:** $O(h)$ where $h$ is the height of the tree for recursion stack.

## Similar Problems

Problems that can be solved using similar tree DP patterns:

1. **337. House Robber III** (this problem) - Tree DP with constraints
2. **198. House Robber** - Array DP
3. **213. House Robber II** - Circular array DP
4. **740. Delete and Earn** - Similar DP pattern
5. **124. Binary Tree Maximum Path Sum** - Tree DP
6. **543. Diameter of Binary Tree** - Tree DP
7. **968. Binary Tree Cameras** - Tree DP with cameras
8. **979. Distribute Coins in Binary Tree** - Tree DP with coins

