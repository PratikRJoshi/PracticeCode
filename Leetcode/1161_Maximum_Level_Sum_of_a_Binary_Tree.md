# 1161. Maximum Level Sum of a Binary Tree

[LeetCode Link](https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/)

## Problem Description
Given the `root` of a binary tree, the **level sum** is defined as the sum of values of the nodes on a particular level.

Return the **smallest level** `x` such that the sum of the nodes on level `x` is maximal.

- The root is on level `1`.

### Examples

#### Example 1
- Input: `root = [1,7,0,7,-8,null,null]`
- Output: `2`
- Explanation:
  - Level 1 sum = `1`
  - Level 2 sum = `7 + 0 = 7`
  - Level 3 sum = `7 + (-8) = -1`
  - Maximum sum is at level 2.

#### Example 2
- Input: `root = [989,null,10250,98693,-89388,null,null,null,-32127]`
- Output: `2`

---

## Intuition/Main Idea
This is a direct **level-order traversal (BFS)** problem.

If we traverse the tree level by level, then for each level we can compute the sum of all nodes at that level.

We track:

- `currentLevel` as we BFS
- `currentLevelSum` for each level
- `bestLevelSum` and `bestLevelIndex`

If a level’s sum is greater than the best seen so far, we update the best.

To satisfy “smallest level with maximal sum”, we update only when we find a **strictly greater** sum (not `>=`).

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Compute sum of each tree level | BFS loop with `levelSize` and accumulate `currentLevelSum` |
| Return smallest level with maximal sum | Update answer only when `currentLevelSum > bestLevelSum` |
| Levels are 1-indexed | Initialize `currentLevel = 1` and increment after each BFS level |

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
    public int maxLevelSum(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        int bestLevelIndex = 1;
        long bestLevelSum = Long.MIN_VALUE;

        int currentLevel = 1;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            long currentLevelSum = 0;

            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();
                currentLevelSum += currentNode.val;

                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }

            if (currentLevelSum > bestLevelSum) {
                bestLevelSum = currentLevelSum;
                bestLevelIndex = currentLevel;
            }

            currentLevel++;
        }

        return bestLevelIndex;
    }
}
```

### Learning Pattern
- Any time you need “something per level” in a tree, BFS with `levelSize` is the cleanest tool.
- Track best-so-far as you process each level.
- For “smallest index with max value”, update only on a strictly greater improvement.

---

## Complexity Analysis
- Time Complexity: $O(n)$
  - every node is processed once
- Space Complexity: $O(w)$
  - where `w` is the maximum width of the tree (queue size)

---

## Tree Problems

### Why or why not a helper function is required
- Not required because BFS naturally processes the tree in levels without recursion.

### Why or why not a global variable is required
- Not required because we can compute and compare each level sum on the fly.

### What all is calculated at the current level or node of the tree
- At each level:
  - `currentLevelSum` = sum of node values in that level
  - enqueue children for the next level

### What is returned to the parent from the current level of the tree
- Nothing is returned upward because we are not using recursion.

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- We avoid recursion entirely.
- If you used DFS instead, you would need to track depth and accumulate sums per depth in a map/array, which is more bookkeeping than BFS here.

---

## Similar Problems
- [102. Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) (classic BFS per level)
- [515. Find Largest Value in Each Tree Row](https://leetcode.com/problems/find-largest-value-in-each-tree-row/) (compute value per level)
- [637. Average of Levels in Binary Tree](https://leetcode.com/problems/average-of-levels-in-binary-tree/) (aggregate per level)
