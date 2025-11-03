### 111. Minimum Depth of Binary Tree
Problem: https://leetcode.com/problems/minimum-depth-of-binary-tree/

---

### Main Idea & Intuition

The *minimum depth* is the number of nodes along the shortest path from the root node down to the **nearest leaf** node (a node with no children).

There are two common approaches:
1.  **Breadth-First Search (BFS)** – Top-down level order traversal. The first time we encounter a leaf, we know we are at the minimum depth.
2.  **Depth-First Search (DFS)** – Post-order recursion that returns the minimum depth of the left/right subtrees and picks the smaller one, taking care with `null` children.

The BFS solution is usually preferred because it can stop early once it finds the first leaf, yielding `O(minDepth)` early-exit behaviour.

---

### Applying General Tree Intuitions (from `Intuitions.md`)

| Question from Intuitions.md                                   | Answer for this problem |
| -------------------------------------------------------------- | ----------------------- |
| **Global variable vs. Return value?**                         | No global variable is needed. The BFS loop keeps a running level counter; the DFS version returns an `int` depth to its parent. |
| **Pre-order vs. Post-order?**                                  | • BFS: neither; it’s level-order.<br>• DFS: **Post-order (bottom-up)** because the depth of a node is `1 + min(depth(left), depth(right))`, which requires children’s results first. |
| **Helper function needed?**                                   | BFS: no helper.<br>DFS: we can call the main function recursively; no extra parameters needed, so a helper is optional. |

---

### Step-by-Step Code Build-Up (BFS version)

**Step 1 – Edge case**: if the tree is empty (`root == null`) return `0`.

**Step 2 – Initialize queue**: add the root to a queue and set `depth = 1`.

**Step 3 – Level order traversal**: while the queue isn’t empty, iterate over the current level size.
* Pop a node.
* If it is a **leaf** (`left == null && right == null`), return the current `depth`.
* Otherwise push any non-null children onto the queue.

**Step 4 – After finishing a level**, increment `depth` and continue.

---

### Final Code (BFS – early exit)

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
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int depth = 1;

        while (!q.isEmpty()) {
            int levelSize = q.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = q.poll();
                // Check if it's a leaf
                if (node.left == null && node.right == null) {
                    return depth;
                }
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            depth++;
        }
        return depth; // Should not reach here
    }
}
```

### Alternative DFS (post-order) in one-liner style
```java
class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        // If one child is missing, we cannot take min(0, x); must take the non-zero path.
        if (left == 0 || right == 0) {
            return 1 + left + right; // one of them is zero
        }
        return 1 + Math.min(left, right);
    }
}
```

---

### Complexity Analysis

| Approach | Time | Space |
| -------- | ---- | ----- |
| BFS | `O(N)` – every node visited at most once (early exit may be earlier). | `O(W)` – queue holds at most the width of the tree (worst-case `O(N)`). |
| DFS | `O(N)` – visits all nodes. | `O(H)` – recursion stack; `H` is height (worst-case `O(N)`, avg `O(log N)`). |

---

### Summary

`minDepth` showcases two patterns from `Intuitions.md`:
1. When the return value (the depth) is exactly what the parent needs, **no global variable** is required.
2. A choice between **top-down BFS** (early exit) and **bottom-up DFS** (post-order) depending on which is clearer or more efficient for early termination.