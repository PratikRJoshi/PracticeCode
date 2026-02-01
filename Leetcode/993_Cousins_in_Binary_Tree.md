# 993. Cousins in Binary Tree

[LeetCode Link](https://leetcode.com/problems/cousins-in-binary-tree/)

## Problem Description
Given the `root` of a binary tree with unique values and two integers `x` and `y`, return `true` if the nodes corresponding to `x` and `y` are **cousins**.

Two nodes are cousins if:

- they are at the same depth
- they have different parents

### Examples

#### Example 1
- Input: `root = [1,2,3,4]`, `x = 4`, `y = 3`
- Output: `false`
- Explanation: `4` is at depth 3 and `3` is at depth 2.

#### Example 2
- Input: `root = [1,2,3,null,4,null,5]`, `x = 5`, `y = 4`
- Output: `true`
- Explanation: `4` and `5` are at the same depth and have different parents.

#### Example 3
- Input: `root = [1,2,3,null,4]`, `x = 2`, `y = 3`
- Output: `false`
- Explanation: They share the same parent (root).

---

## Intuition/Main Idea
Cousins are defined by a **level condition** (same depth) and a **parent condition** (different parent).

A BFS level-order traversal is a direct fit because:

- it processes nodes level by level (depth is implicit)
- we can track the parent of each child when we enqueue it

At each level:

1. Scan all nodes at that level.
2. Record the parent of `x` if we see it.
3. Record the parent of `y` if we see it.
4. After finishing the level:
   - if we found both `x` and `y`, they are cousins iff their parents are different
   - if we found only one of them, they cannot be cousins (different depths) -> return false

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Same depth | Process one BFS level at a time using `levelSize` |
| Different parents | Track `parentOfX` / `parentOfY` while scanning a level |
| Return `true` only if both appear in the same level | After each level, check found status and return accordingly |

---

## Final Java Code & Learning Pattern (Full Content)
```java
import java.util.*;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    private static class NodeWithParent {
        TreeNode node;
        TreeNode parent;

        NodeWithParent(TreeNode node, TreeNode parent) {
            this.node = node;
            this.parent = parent;
        }
    }

    public boolean isCousins(TreeNode root, int x, int y) {
        Queue<NodeWithParent> queue = new ArrayDeque<>();
        queue.offer(new NodeWithParent(root, null));

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            TreeNode parentOfX = null;
            TreeNode parentOfY = null;

            for (int i = 0; i < levelSize; i++) {
                NodeWithParent current = queue.poll();
                TreeNode currentNode = current.node;
                TreeNode currentParent = current.parent;

                if (currentNode.val == x) {
                    parentOfX = currentParent;
                }
                if (currentNode.val == y) {
                    parentOfY = currentParent;
                }

                if (currentNode.left != null) {
                    queue.offer(new NodeWithParent(currentNode.left, currentNode));
                }
                if (currentNode.right != null) {
                    queue.offer(new NodeWithParent(currentNode.right, currentNode));
                }
            }

            if (parentOfX != null && parentOfY != null) {
                return parentOfX != parentOfY;
            }

            if (parentOfX != null || parentOfY != null) {
                return false;
            }
        }

        return false;
    }
}
```

### Learning Pattern
- For “same level + different parent” tree questions:
  - BFS by levels gives depth for free
  - store parent information while enqueueing children
  - decide after each level (don’t wait until the end)

---

## Complexity Analysis
- Time Complexity: $O(n)$
  - each node is visited once
- Space Complexity: $O(w)$
  - where `w` is the maximum width of the tree (queue size)

---

## Tree Problems

### Why or why not a helper function is required
- Not required because BFS already organizes computation by depth.

### Why or why not a global variable is required
- Not required because the decision can be made while scanning each level.

### What all is calculated at the current level or node of the tree
- For the current BFS level:
  - `parentOfX` and `parentOfY` (if found)
  - enqueue children along with their parent

### What is returned to the parent from the current level of the tree
- Nothing is returned upward because we are not using recursion.

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- We avoid recursion entirely.
- If you used DFS instead, you would explicitly track depth and parent (more bookkeeping), while BFS naturally groups by depth.

---

## Similar Problems
- [1161. Maximum Level Sum of a Binary Tree](https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/) (BFS per level)
- [515. Find Largest Value in Each Tree Row](https://leetcode.com/problems/find-largest-value-in-each-tree-row/) (aggregate per level)
- [102. Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) (level-order traversal)
