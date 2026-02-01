# 623. Add One Row to Tree

[LeetCode Link](https://leetcode.com/problems/add-one-row-to-tree/)

## Problem Description
Given the `root` of a binary tree and two integers `val` and `depth`, add a row of nodes with value `val` at the given depth `depth`.

Notes:

- The root node is at depth `1`.
- If `depth == 1`, a new root node with value `val` is created, and the original tree becomes the left subtree of the new root.
- For `depth > 1`, for every node at depth `depth - 1`:
  - Insert a new left child with value `val` and attach the original left subtree as the left child of this new node.
  - Insert a new right child with value `val` and attach the original right subtree as the right child of this new node.

Return the root of the modified tree.

### Examples

#### Example 1
- Input: `root = [4,2,6,3,1,5]`, `val = 1`, `depth = 2`
- Output: `[4,1,1,2,null,null,6,3,1,5]`

#### Example 2
- Input: `root = [4,2,null,3,1]`, `val = 1`, `depth = 3`
- Output: `[4,2,null,1,1,3,null,null,1]`

---

## Intuition/Main Idea
We are asked to insert a row at a specific depth. The only nodes whose pointers change are the nodes at depth `depth - 1`.

So the plan is:

1. Handle the special case `depth == 1` by creating a new root.
2. Otherwise, traverse the tree until we reach depth `depth - 1`.
3. For each node at that level:
   - Save its original `left` and `right` subtrees.
   - Create two new nodes with value `val`.
   - Attach the saved left subtree under the new left node.
   - Attach the saved right subtree under the new right node.

A BFS (level-order traversal) is a very direct way to stop exactly at `depth - 1`.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| If `depth == 1`, new node becomes root | Special-case block that creates `newRoot` and attaches old root as `newRoot.left` |
| Modify children only for nodes at depth `depth - 1` | BFS loop that stops at `currentDepth == depth - 1` |
| Preserve original subtrees when inserting new nodes | Save `originalLeft` / `originalRight` before rewiring pointers |
| Insert new left and right nodes with value `val` | Create `newLeftNode` and `newRightNode` and reassign `currentNode.left/right` |

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
    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        if (depth == 1) {
            TreeNode newRoot = new TreeNode(val);
            newRoot.left = root;
            return newRoot;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        int currentDepth = 1;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            if (currentDepth == depth - 1) {
                // Now queue contains exactly the nodes at depth (depth - 1).
                for (int i = 0; i < levelSize; i++) {
                    TreeNode currentNode = queue.poll();

                    TreeNode originalLeft = currentNode.left;
                    TreeNode originalRight = currentNode.right;

                    TreeNode newLeftNode = new TreeNode(val);
                    TreeNode newRightNode = new TreeNode(val);

                    currentNode.left = newLeftNode;
                    currentNode.right = newRightNode;

                    newLeftNode.left = originalLeft;
                    newRightNode.right = originalRight;
                }

                return root;
            } else {
                for (int i = 0; i < levelSize; i++) {
                    TreeNode currentNode = queue.poll();

                    if (currentNode.left != null) {
                        queue.offer(currentNode.left);
                    }
                    if (currentNode.right != null) {
                        queue.offer(currentNode.right);
                    }
                }

                currentDepth++;
            }
        }

        return root;
    }
}
```

### Learning Pattern
- When a tree problem says “modify nodes at depth `D`”, think: find all nodes at depth `D - 1`.
- BFS is clean when you need exact levels and only one pass.
- Pointer rewiring rule:
  - store old children
  - create new children
  - attach old subtrees under the new nodes

---

## Complexity Analysis
- Time Complexity: $O(n)$
  - each node is visited at most once by BFS
- Space Complexity: $O(w)$
  - where `w` is the maximum width of the tree (queue size)

---

## Tree Problems

### Why or why not a helper function is required
- Not required here because BFS naturally processes levels iteratively, and the work at the target depth is a simple local pointer rewrite.

### Why or why not a global variable is required
- Not required because we can build the new row in-place once we reach the correct depth. No cross-recursion aggregation is needed.

### What all is calculated at the current level or node of the tree
- For nodes at depth `< depth - 1`: we only enqueue their children to reach the next level.
- For nodes at depth `depth - 1`: we create `newLeftNode` and `newRightNode`, then attach the saved original subtrees.

### What is returned to the parent from the current level of the tree
- Nothing is returned upward because we are not using recursion. We mutate pointers directly.

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- We avoid recursion entirely.
- If you did use DFS recursion, you would recurse down until you reach depth `depth - 1`, then perform the pointer rewrite at that node (work happens when you arrive at the parent level of the insertion).

---

## Similar Problems
- [102. Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) (BFS level processing)
- [513. Find Bottom Left Tree Value](https://leetcode.com/problems/find-bottom-left-tree-value/) (level order traversal at depths)
- [111. Minimum Depth of Binary Tree](https://leetcode.com/problems/minimum-depth-of-binary-tree/) (depth-based traversal)
