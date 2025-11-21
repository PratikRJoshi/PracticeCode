# Binary Tree Level Order Traversal

## Problem Description

**Problem Link:** [Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/)

Given the `root` of a binary tree, return *the level order traversal of its nodes' values*. (i.e., from left to right, level by level).

**Example 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: [[3],[9,20],[15,7]]
```

**Example 2:**
```
Input: root = [1]
Output: [[1]]
```

**Example 3:**
```
Input: root = []
Output: []
```

**Constraints:**
- The number of nodes in the tree is in the range `[0, 2000]`.
- `-1000 <= Node.val <= 1000`

## Intuition/Main Idea

Level order traversal requires processing nodes level by level, which naturally fits a BFS (Breadth-First Search) approach using a queue.

**Core Algorithm:**
- Use a queue to store nodes at each level
- Process all nodes at current level before moving to next
- For each level, collect all node values
- Add children of current level nodes to queue for next level

**Why BFS/Queue:** Level order means we process nodes at the same depth together. A queue's FIFO property ensures we process nodes in the correct order (left to right, level by level).

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Level order traversal | BFS with queue - Lines 7-25 |
| Process level by level | Level size tracking - Lines 11-12 |
| Collect values per level | Level list - Lines 13, 19 |
| Add children to queue | Queue operations - Lines 21-24 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        // Queue for BFS traversal
        // We use LinkedList as Queue implementation
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        // Process each level
        while (!queue.isEmpty()) {
            // Get the number of nodes at current level
            // This ensures we process all nodes at same level together
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                
                // Add children to queue for next level
                // Left child first to maintain left-to-right order
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            // Add current level to result
            result.add(level);
        }
        
        return result;
    }
}
```

## Tree Problem Analysis

**Why or why not a helper function is required:**
- No helper function needed. The main function handles BFS traversal directly.

**Why or why not a global variable is required:**
- No global variable needed. We pass the result list and build it during traversal.

**What all is calculated at the current level or node of the tree:**
- At each level: Collect all node values at that level into a list.

**What is returned to the parent from the current level of the tree:**
- Not applicable here. We're building a list of lists, not returning values up the tree.

**How to decide if a recursive call to children needs to be made before current node calculation or vice versa:**
- This is iterative BFS, not recursive. We process nodes level by level using a queue. We add children to queue after processing current node.

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the number of nodes. We visit each node exactly once.

**Space Complexity:** $O(n)$ for the queue. In worst case (complete binary tree), the last level has $n/2$ nodes.

## Similar Problems

- [Binary Tree Zigzag Level Order Traversal](https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/) - Same but reverse every other level
- [Binary Tree Right Side View](https://leetcode.com/problems/binary-tree-right-side-view/) - Return last element of each level
- [Average of Levels in Binary Tree](https://leetcode.com/problems/average-of-levels-in-binary-tree/) - Calculate average per level

