# Binary Tree Zigzag Level Order Traversal

## Problem Description

**Problem Link:** [Binary Tree Zigzag Level Order Traversal](https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/)

Given the `root` of a binary tree, return *the zigzag level order traversal of its nodes' values*. (i.e., from left to right, then right to left for the next level and alternate between).

**Example 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: [[3],[20,9],[15,7]]
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

Similar to level order traversal, but we reverse the order of nodes at every other level. We can use BFS with a flag to track whether to reverse.

**Core Algorithm:**
- Use BFS to traverse level by level
- Track current level number (0-indexed)
- For even levels (0, 2, 4...), add nodes left to right
- For odd levels (1, 3, 5...), reverse the level list before adding

**Why BFS with reversal:** We need level-by-level processing. Instead of changing traversal order, we can reverse the list for odd levels, which is simpler.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Level order traversal | BFS with queue - Lines 8-28 |
| Zigzag pattern | Level tracking and reversal - Lines 12, 25-27 |
| Reverse odd levels | Collections.reverse - Line 26 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true; // Track direction
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // Add node value to level list
                // We always add left-to-right, then reverse if needed
                level.add(node.val);
                
                // Add children to queue (always left then right)
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            // Reverse level list for odd levels (right-to-left)
            // Level 0: left-to-right (no reverse)
            // Level 1: right-to-left (reverse)
            // Level 2: left-to-right (no reverse)
            // etc.
            if (!leftToRight) {
                Collections.reverse(level);
            }
            
            result.add(level);
            leftToRight = !leftToRight; // Toggle direction
        }
        
        return result;
    }
}
```

## Tree Problem Analysis

**Why or why not a helper function is required:**
- No helper function needed. Main function handles traversal.

**Why or why not a global variable is required:**
- No global variable. We use a local boolean flag to track direction.

**What all is calculated at the current level or node of the tree:**
- At each level: Collect node values, then reverse if it's an odd level.

**What is returned to the parent from the current level of the tree:**
- Not applicable. We build result list level by level.

**How to decide if a recursive call to children needs to be made before current node calculation or vice versa:**
- Iterative BFS. We process current level, then add children to queue for next level.

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the number of nodes. We visit each node once. Reversing takes $O(k)$ per level where $k$ is level size, but total is still $O(n)$.

**Space Complexity:** $O(n)$ for the queue and result list.

## Similar Problems

- [Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) - Same without reversal
- [Binary Tree Right Side View](https://leetcode.com/problems/binary-tree-right-side-view/) - Last element of each level
- [Spiral Matrix](https://leetcode.com/problems/spiral-matrix/) - Similar zigzag pattern in 2D array

