# Path Sum II

## Problem Description

**Problem Link:** [Path Sum II](https://leetcode.com/problems/path-sum-ii/)

Given the `root` of a binary tree and an integer `targetSum`, return *all **root-to-leaf** paths where the sum of the node values in the path equals* `targetSum`.

**Example 1:**
```
Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
Output: [[5,4,11,2],[5,8,4,5]]
Explanation: There are two paths whose sum equals 22:
5 + 4 + 11 + 2 = 22
5 + 8 + 4 + 5 = 22
```

**Example 2:**
```
Input: root = [1,2,3], targetSum = 5
Output: []
```

**Example 3:**
```
Input: root = [1,2], targetSum = 0
Output: []
```

**Constraints:**
- The number of nodes in the tree is in the range `[0, 5000]`.
- `-1000 <= Node.val <= 1000`
- `-1000 <= targetSum <= 1000`

## Intuition/Main Idea

We need to find all root-to-leaf paths that sum to targetSum. This is a backtracking problem.

**Core Algorithm:**
- Use DFS with backtracking
- Track current path and current sum
- When we reach a leaf node, check if sum equals targetSum
- If yes, add path to result
- Backtrack by removing current node from path

**Why backtracking:** We need to explore all paths. Backtracking allows us to build paths incrementally and undo choices when exploring different branches.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find all root-to-leaf paths | DFS traversal - Lines 6-30 |
| Check path sum equals target | Sum check at leaf - Lines 18-21 |
| Collect valid paths | Add to result - Line 20 |
| Backtrack | Remove from path - Line 28 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentPath = new ArrayList<>();
        
        // Start DFS traversal from root
        dfs(root, targetSum, 0, currentPath, result);
        
        return result;
    }
    
    private void dfs(TreeNode node, int targetSum, int currentSum, 
                     List<Integer> currentPath, List<List<Integer>> result) {
        // Base case: null node
        if (node == null) {
            return;
        }
        
        // Add current node to path and update sum
        currentPath.add(node.val);
        currentSum += node.val;
        
        // Check if we're at a leaf node
        if (node.left == null && node.right == null) {
            // If sum equals target, add current path to result
            if (currentSum == targetSum) {
                result.add(new ArrayList<>(currentPath)); // Create copy
            }
        } else {
            // Continue DFS on children
            dfs(node.left, targetSum, currentSum, currentPath, result);
            dfs(node.right, targetSum, currentSum, currentPath, result);
        }
        
        // Backtrack: remove current node from path
        // This allows us to explore other paths
        currentPath.remove(currentPath.size() - 1);
    }
}
```

## Tree Problem Analysis

**Why or why not a helper function is required:**
- Helper function is required to pass current path and sum, and to perform backtracking.

**Why or why not a global variable is required:**
- No global variable. We pass result list and current path as parameters.

**What all is calculated at the current level or node of the tree:**
- At current node: Add node to path, update sum, check if leaf and sum matches target.

**What is returned to the parent from the current level of the tree:**
- Nothing returned. We modify result list directly when valid path found.

**How to decide if a recursive call to children needs to be made before current node calculation or vice versa:**
- Current node processing (add to path, update sum) happens first (lines 15-16), then recursive calls (lines 25-26), then backtrack (line 28). We need node in path before exploring children, and remove it after.

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the number of nodes. We visit each node once. Creating path copies takes $O(h)$ per leaf, but total is still $O(n)$.

**Space Complexity:** $O(h)$ for recursion stack and path list, where $h$ is height. In worst case, $O(n)$.

## Similar Problems

- [Path Sum](https://leetcode.com/problems/path-sum/) - Check if any path exists (boolean)
- [Path Sum III](https://leetcode.com/problems/path-sum-iii/) - Count paths (not just root-to-leaf)
- [Binary Tree Paths](https://leetcode.com/problems/binary-tree-paths/) - Return all root-to-leaf paths as strings
- [Sum Root to Leaf Numbers](https://leetcode.com/problems/sum-root-to-leaf-numbers/) - Sum all root-to-leaf numbers

