# House Robber III

## Problem Description

**Problem Link:** [House Robber III](https://leetcode.com/problems/house-robber-iii/)

Given root of binary tree where each node has money, find maximum amount you can rob such that directly-linked nodes cannot both be robbed.

## Intuition/Main Idea

This combines tree recursion with include/exclude DP.

For each node, compute two values:
- `robThis`: best if we rob current node (children must be skipped)
- `skipThis`: best if we skip current node (children can be robbed or skipped)

### Why this works

Each node decision depends only on children states, so postorder DFS naturally builds answers bottom-up on tree.

### Tree-specific reasoning
- **Helper function required?** Yes, helper returns both states for each node.
- **Global variable required?** No, root state gives final answer.
- **What is calculated at current node?** `robThis` and `skipThis`.
- **What is returned to parent?** pair `[robThis, skipThis]`.
- **Children before current node or vice versa?** Children first (postorder), because current computation needs child states.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Adjacent nodes cannot both be robbed | `robThis = node.val + leftSkip + rightSkip` |
| Maximize total amount | `skipThis = max(leftRob,leftSkip) + max(rightRob,rightSkip)` |
| Return best plan overall | `max(rootRob, rootSkip)` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Tree DP - Include/Exclude]
class Solution {
    public int rob(TreeNode root) {
        int[] result = dfs(root);
        return Math.max(result[0], result[1]);
    }

    // Returns [robThis, skipThis]
    private int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[] {0, 0};
        }

        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        int robThis = node.val + left[1] + right[1];
        int skipThis = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        return new int[] {robThis, skipThis};
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n)$
- **Space Complexity:** $O(h)$ recursion stack

## Similar Problems

1. [198. House Robber](https://leetcode.com/problems/house-robber/)
2. [213. House Robber II](https://leetcode.com/problems/house-robber-ii/)
