### 199. Binary Tree Right Side View
Problem: https://leetcode.com/problems/binary-tree-right-side-view/

---

### Main Idea & Intuition

The goal is to collect the values of the rightmost node at each level of the tree. This can be solved with either Breadth-First Search (BFS) or Depth-First Search (DFS).

The DFS approach is particularly elegant. We perform a pre-order traversal. The key idea is to traverse the right subtree *before* the left subtree. We also pass down the current `level` of the tree.

As we traverse, we keep a list of the results. When we visit a node, we check if its level is equal to the current size of our result list. If it is, it means we are visiting this level for the very first time. Since we are prioritizing the right side, the first node we encounter at any new level is guaranteed to be the rightmost one. We add it to our list and continue.

---

### Applying General Tree Intuitions

-   **Global Variable vs. Return Value**: We don't need a global variable, but we do need to pass a `List<Integer>` by reference to our helper function to collect the results. The helper function itself can return `void` because its job is to modify this list as a side effect.

-   **Pre-order vs. Post-order**: This is a classic **pre-order (top-down)** approach. We process the current node (checking if it should be added to the list) *before* we make the recursive calls to its children. The decision is made at the node itself based on the `level` information passed down from its parent.

-   **Helper Function**: Yes, a helper function is necessary. The main function `rightSideView(TreeNode root)` must return a `List<Integer>`. Our recursive function needs to carry extra state—the current `level`. Therefore, we need a helper with a signature like `dfs(TreeNode node, int level, List<Integer> result)`.

--- 

### Code Solution (DFS)

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
import java.util.ArrayList;
import java.util.List;

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

        // If this is the first time we are visiting this level, it must be the rightmost node.
        if (level == result.size()) {
            result.add(node.val);
        }

        // Recurse on the right side first to ensure we process the rightmost nodes first.
        dfs(node.right, level + 1, result);
        dfs(node.left, level + 1, result);
    }
}
```

---

### Complexity Analysis

*   **Time Complexity: O(N)**
    *   We visit every node in the tree exactly once. `N` is the number of nodes.

*   **Space Complexity: O(H)**
    *   The space complexity is determined by the depth of the recursion stack, which is the height of the tree, `H`. In the worst case (a skewed tree), this is `O(N)`. In a balanced tree, it's `O(log N)`.
