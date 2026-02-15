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

This is a **tree dynamic programming** problem.

At every house (tree node), you have exactly two choices:

- Rob this house.
  - Then you are not allowed to rob its direct children.
- Skip this house.
  - Then you are free to rob (or skip) its children.

The “tree DP” part comes from the fact that the best answer for a node depends on the best answers of its subtrees.

### DP subproblem definition

Let `best(node)` be the maximum money we can rob from the subtree rooted at `node`.

Then:

- If we **rob** `node`, we must **skip** both children, but we can consider the grandchildren.
- If we **skip** `node`, we can independently take the best from left and right children.

This gives a natural **top-down (memoized)** solution:

- `best(node) = max(
    node.val + best(node.left.left) + best(node.left.right) + best(node.right.left) + best(node.right.right),
    best(node.left) + best(node.right)
  )`

However, an even cleaner formulation is a **bottom-up** DP where for each node we compute two values:

- `robCurrent`   = best if we rob this node
- `skipCurrent`  = best if we skip this node

Recurrence (post-order):

- `robCurrent  = node.val + left.skipCurrent + right.skipCurrent`
- `skipCurrent = max(left.robCurrent, left.skipCurrent) + max(right.robCurrent, right.skipCurrent)`

### Tree problem requirements (how to reason about recursion)

- **Why a helper function is required**
  - We need to compute multiple values for each node (e.g., rob/skip), which a helper can return as a pair.
- **Why a global variable is not required**
  - Each node’s answer can be computed from its children and returned upward; no shared state is needed.
- **What is calculated at the current node**
  - Either:
    - `best(node)` in the memoized approach, or
    - `(robCurrent, skipCurrent)` in the bottom-up approach.
- **What is returned to the parent**
  - Memoized: `best(node)`.
  - Bottom-up: the pair `(robCurrent, skipCurrent)`.
- **Order of recursion (children first vs current first)**
  - Bottom-up requires **post-order** (compute children first), because current node depends on children values.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Max money without robbing parent-child on same night | `best(node)` = max(rob current, skip current) (top-down) (lines 12-33) |
| Efficiently avoid recomputing subtree answers | Memoization map keyed by `TreeNode` (lines 12-33) |
| Bottom-up tree DP alternative (rob/skip per node) | Helper returns `int[]{robCurrent, skipCurrent}` (lines 35-64) |
| Base case for null children | Return `{0, 0}` for null node (line 38) |

## Final Java Code & Learning Pattern (Full Content)

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int rob(TreeNode root) {
        // DP (top-down/memoized): best amount from this subtree.
        Map<TreeNode, Integer> memo = new HashMap<>();
        return robMemo(root, memo);
    }

    // Top-down definition:
    // robMemo(node) returns the best amount we can rob from the subtree rooted at node.
    private int robMemo(TreeNode node, Map<TreeNode, Integer> memo) {
        if (node == null) {
            return 0;
        }

        if (memo.containsKey(node)) {
            return memo.get(node);
        }

        // Option 1: Rob current node.
        // Then we must skip its children, so we jump to grandchildren.
        int moneyIfRobCurrent = node.val;
        if (node.left != null) {
            moneyIfRobCurrent += robMemo(node.left.left, memo);
            moneyIfRobCurrent += robMemo(node.left.right, memo);
        }
        if (node.right != null) {
            moneyIfRobCurrent += robMemo(node.right.left, memo);
            moneyIfRobCurrent += robMemo(node.right.right, memo);
        }

        // Option 2: Skip current node.
        // Then we are free to take the best from its children.
        int moneyIfSkipCurrent = robMemo(node.left, memo) + robMemo(node.right, memo);

        int best = Math.max(moneyIfRobCurrent, moneyIfSkipCurrent);
        memo.put(node, best);
        return best;
    }

    // Bottom-up alternative:
    // For each node, return:
    // - result[0] = best if we rob current node
    // - result[1] = best if we skip current node
    private int[] robBottomUp(TreeNode node) {
        if (node == null) {
            return new int[] {0, 0};
        }

        int[] left = robBottomUp(node.left);
        int[] right = robBottomUp(node.right);

        int robCurrent = node.val + left[1] + right[1];
        int skipCurrent = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        return new int[] {robCurrent, skipCurrent};
    }
}
```

### Dynamic Programming notes (top-down vs bottom-up)

- **Top-down / memoized**
  - Subproblem: `robMemo(node)` = best amount for subtree rooted at `node`.
  - State transition: choose `max(rob current + grandchildren, skip current + children)`.
  - Memoization avoids recomputing the same subtree results.

- **Bottom-up**
  - Subproblem: for each node return `(robCurrent, skipCurrent)`.
  - Transition is purely from children to parent (post-order).
  - This is often simpler and avoids a hash map.

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the number of nodes. We visit each node once.

- **Space Complexity:** $O(h)$ where $h$ is the height of the tree for recursion stack.

## Similar Problems

- [House Robber](https://leetcode.com/problems/house-robber/) - 1D DP version
- [House Robber II](https://leetcode.com/problems/house-robber-ii/) - circular constraint
- [Delete and Earn](https://leetcode.com/problems/delete-and-earn/) - similar “pick vs skip” DP idea
- [Binary Tree Maximum Path Sum](https://leetcode.com/problems/binary-tree-maximum-path-sum/) - tree DP with post-order aggregation
- [Binary Tree Cameras](https://leetcode.com/problems/binary-tree-cameras/) - tree DP with state per node

