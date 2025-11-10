### 199. Binary Tree Right Side View
### Problem Link: [Binary Tree Right Side View](https://leetcode.com/problems/binary-tree-right-side-view/)

### Intuition/Main Idea
This problem asks us to find the nodes visible from the right side of a binary tree, which means we need to find the rightmost node at each level of the tree. There are two main approaches to solve this:

1. **DFS (Depth-First Search)**: Traverse the tree in a modified pre-order traversal (root → right → left) and keep track of the level. The first node we encounter at each level will be the rightmost node at that level.

2. **BFS (Breadth-First Search)**: Traverse the tree level by level and add the last node of each level to the result.

The DFS approach is particularly elegant because by visiting the right subtree before the left subtree, we ensure that the first node we encounter at each new level is the rightmost one.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Find rightmost node at each level | `if (level == result.size()) { result.add(node.val); }` |
| Traverse right subtree first | `dfs(node.right, level + 1, result); dfs(node.left, level + 1, result);` |
| Track the current level | `dfs(TreeNode node, int level, List<Integer> result)` |
| Return the right side view | `List<Integer> result = new ArrayList<>(); dfs(root, 0, result); return result;` |

### Final Java Code & Learning Pattern

```java
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
// [Pattern: Modified Pre-order DFS Traversal (Root → Right → Left)]
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }
    
    private void dfs(TreeNode node, int level, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        // If this is the first time we're visiting this level,
        // the current node is the rightmost node at this level
        if (level == result.size()) {
            result.add(node.val);
        }
        
        // Visit right subtree first, then left subtree
        // This ensures we encounter the rightmost node at each level first
        dfs(node.right, level + 1, result);
        dfs(node.left, level + 1, result);
    }
}
```

### Alternative Implementation (BFS Approach)

```java
// [Pattern: Level Order Traversal (BFS)]
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // If this is the last node in the current level, add it to the result
                if (i == levelSize - 1) {
                    result.add(node.val);
                }
                
                // Add child nodes to the queue for the next level
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        
        return result;
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(n)$ where n is the number of nodes in the binary tree. We visit each node exactly once.
- **Space Complexity**: $O(h)$ where h is the height of the tree. This is the space used by the recursion stack in the DFS approach. In the worst case (skewed tree), this could be $O(n)$, but for a balanced tree, it would be $O(\log n)$. For the BFS approach, the space complexity is $O(w)$ where w is the maximum width of the tree, which could be up to $O(n/2)$ in the worst case.

### Tree Problems Explanation
- **Helper Function**: A helper function is required for the DFS approach to keep track of the current level during traversal. This allows us to determine if we're seeing a level for the first time.
- **Global Variable**: No global variable is needed as we pass the result list by reference to the helper function, which modifies it directly.
- **Current Level Calculation**: At each node, we check if the current level equals the size of our result list. If it does, it means this is the first node we're encountering at this level (and since we're traversing right to left, it's the rightmost node).
- **Return Value**: The helper function doesn't need to return anything (void) because it directly modifies the result list. The main function returns this list as the final answer.

### Similar Problems
1. **LeetCode 102: Binary Tree Level Order Traversal** - Similar level-based traversal but returns all nodes at each level.
2. **LeetCode 107: Binary Tree Level Order Traversal II** - Returns levels in bottom-up order.
3. **LeetCode 103: Binary Tree Zigzag Level Order Traversal** - Traverses levels in alternating directions.
4. **LeetCode 116/117: Populating Next Right Pointers in Each Node I/II** - Connects nodes at the same level.
5. **LeetCode 515: Find Largest Value in Each Tree Row** - Finds the maximum value at each level.
6. **LeetCode 429: N-ary Tree Level Order Traversal** - Level order traversal for n-ary trees.
7. **LeetCode 513: Find Bottom Left Tree Value** - Finds the leftmost value in the last row (mirror of this problem).
8. **LeetCode 637: Average of Levels in Binary Tree** - Calculates the average value at each level.
