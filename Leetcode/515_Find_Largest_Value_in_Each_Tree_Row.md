# 515. Find Largest Value in Each Tree Row

[LeetCode Link](https://leetcode.com/problems/find-largest-value-in-each-tree-row/)

## Problem Description
Given the `root` of a binary tree, return an array of the largest value in each row (level) of the tree.

### Examples

#### Example 1
- Input: `root = [1,3,2,5,3,null,9]`
- Output: `[1,3,9]`

#### Example 2
- Input: `root = [1,2,3]`
- Output: `[1,3]`

---

## Intuition/Main Idea
Each “row” of a binary tree corresponds to a **BFS level**.

So we can do a level-order traversal using a queue:

- For each level, scan exactly `levelSize` nodes.
- While scanning, compute `max` over the node values in that level.
- After finishing the level, append `max` to the result.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Return the largest value per tree row | Track `max` while iterating through a level and add it to `result` |
| Process nodes level by level | Use `Queue<TreeNode>` and `levelSize = q.size()` |
| Handle empty tree | Return empty `result` when `root == null` |

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
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if(root == null){
            return result;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while(!q.isEmpty()){
            int levelSize = q.size();

            int max = Integer.MIN_VALUE;
            for(int i = 0; i <  levelSize; i++){
                TreeNode node = q.poll();
                max = Math.max(max, node.val);

                if(node.left != null){
                    q.offer(node.left);
                }
                if(node.right != null){
                    q.offer(node.right);
                }

                if(i == levelSize - 1){
                    result.add(max);
                }
            }
        }

        return result;
    }
}
```

### Learning Pattern
- For “compute something per level” problems:
  - Use BFS with `levelSize = queue.size()`.
  - Maintain a running aggregate for the level (here: `max`).
  - After scanning `levelSize` nodes, commit the aggregate to the answer.

---

## Complexity Analysis
- Time Complexity: $O(n)$
  - each node is visited once
- Space Complexity: $O(w)$
  - where `w` is the maximum width of the tree (queue size)

---

## Tree Problems

### Why or why not a helper function is required
- Not required because BFS already groups nodes by depth using `levelSize`.

### Why or why not a global variable is required
- Not required because we can compute and append the answer for each level within the BFS loop.

### What all is calculated at the current level or node of the tree
- For the current level:
  - track `max` across the `levelSize` nodes
- For each node:
  - update `max`
  - push its children into the queue for the next level

### What is returned to the parent from the current level of the tree
- Nothing is returned upward because the solution is iterative (no recursion).

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- We avoid recursion entirely.
- In BFS, you process the current node first, then enqueue children for later processing.

---

## Similar Problems
- [102. Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) (classic BFS levels)
- [1161. Maximum Level Sum of a Binary Tree](https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/) (aggregate per level)
- [637. Average of Levels in Binary Tree](https://leetcode.com/problems/average-of-levels-in-binary-tree/) (aggregate per level)
