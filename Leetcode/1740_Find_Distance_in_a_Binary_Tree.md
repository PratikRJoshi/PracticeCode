# Find Distance in a Binary Tree

## Problem Description

**Problem Link:** [Find Distance in a Binary Tree](https://leetcode.com/problems/find-distance-in-a-binary-tree/)

Given the `root` of a binary tree and two integers `p` and `q`, return the **distance** between the nodes of value `p` and value `q` in the tree.

The **distance** between two nodes is the number of edges on the path from one to the other.

**Example 1:**
```
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 0
Output: 3
Explanation: There are 3 edges between 5 and 0: 5-3-1-0.
```

**Example 2:**
```
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 7
Output: 2
Explanation: There are 2 edges between 5 and 7: 5-2-7.
```

**Example 3:**
```
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 5
Output: 0
Explanation: The distance between a node and itself is 0.
```

**Constraints:**
- The number of nodes in the tree is in the range `[1, 10^4]`.
- `0 <= Node.val <= 10^9`
- All `Node.val` are unique.
- `p` and `q` are values in the tree.

---

## Intuition/Main Idea

### Building the Intuition Incrementally

**Step 1 — Simplify the problem.**
The "distance between two nodes" is the number of edges on the unique path connecting them. In a tree, there is exactly one path between any two nodes.

**Step 2 — Think about the Lowest Common Ancestor (LCA).**
Every path between two nodes in a tree must pass through their **Lowest Common Ancestor (LCA)**. The path looks like:
```
p --> ... --> LCA --> ... --> q
```
So: `distance(p, q) = depth(p from LCA) + depth(q from LCA)`

**Step 3 — How to compute depth from LCA?**
If we know the LCA node, the depth of a target node from that anchor is a simple recursion: scan the subtree rooted at the LCA, and count edges until you find the target.

**Step 4 — How to find the LCA?**
The standard LCA algorithm:
- If `root == null`, return null.
- If `root.val == p || root.val == q`, this is the LCA (one is an ancestor of the other, or we found one side).
- Recurse into left and right subtrees.
- If both sides return non-null, the current node is the LCA.
- Otherwise, return whichever side is non-null.

**Why the intuition works:**
- The LCA is the shallowest node that "owns" both `p` and `q` in its subtree.
- Once we land on the LCA, we only need to count edges downward to each target separately — both sub-distances are independent.
- The total distance is the sum of those two independent depths.

**Step 5 — Edge case: p == q.**
When both values are the same, the LCA is that node itself, and both depths are 0, so the answer is 0. The algorithm handles this naturally.

### Helper Function Analysis (Tree Problem Section)

**Why a helper function is required:**
We need two separate recursive operations — finding the LCA and computing depth from a given root. Separating these into distinct helper functions (`findLCA` and `getDepth`) keeps each concern clean and independently correct.

**Why a global variable is NOT required:**
The result is computed as the sum of two return values (`getDepth(lca, p, 0) + getDepth(lca, q, 0)`). No shared mutable state is needed; all information flows through return values.

**What is calculated at the current node:**
- In `findLCA`: whether the current node equals `p` or `q`, or whether both subtrees have found a target (making the current node the LCA).
- In `getDepth`: whether the current node is the target; if so, return the accumulated depth; otherwise, relay whichever subtree found the target.

**What is returned to the parent:**
- `findLCA` returns the LCA node (or a partial result indicating one target was found in this subtree).
- `getDepth` returns the depth of the target from the subtree root (or `-1` if not found in this subtree).

**Whether to recurse into children before or after current node calculation:**
- In `findLCA`: recurse first into left and right children, then decide based on their combined results. This is **post-order** — we need both children's answers before we can determine if the current node is the LCA.
- In `getDepth`: check the current node first (base case), then recurse. This is **pre-order** check followed by recursion.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|--------------------------|-------------------------------------|
| Find LCA of p and q | `findLCA` method |
| Compute edge count from LCA to p | `getDepth(lca, p, 0)` |
| Compute edge count from LCA to q | `getDepth(lca, q, 0)` |
| Total distance = depth(p) + depth(q) from LCA | `return getDepth(lca, p, 0) + getDepth(lca, q, 0)` |
| Handle p == q edge case | LCA is the node itself; both depths return 0 |
| Null subtree (target not in this branch) | `getDepth` returns -1 when target not found |

---

## Final Java Code & Learning Pattern

```java
class Solution {

    public int findDistance(TreeNode root, int p, int q) {
        // Find the Lowest Common Ancestor — the "meeting point" of both paths
        TreeNode lowestCommonAncestor = findLCA(root, p, q);

        // Distance = edges from LCA down to p + edges from LCA down to q
        return getDepth(lowestCommonAncestor, p, 0) + getDepth(lowestCommonAncestor, q, 0);
    }

    // POST-ORDER: recurse into children first, then decide if current node is LCA
    private TreeNode findLCA(TreeNode currentNode, int targetP, int targetQ) {
        // Base case: empty subtree contributes nothing
        if (currentNode == null) {
            return null;
        }

        // If the current node is one of the targets, it is the LCA
        // (the other target, if it exists, must be in its subtree)
        if (currentNode.val == targetP || currentNode.val == targetQ) {
            return currentNode;
        }

        // Recurse into both subtrees to search for both targets
        TreeNode leftResult = findLCA(currentNode.left, targetP, targetQ);
        TreeNode rightResult = findLCA(currentNode.right, targetP, targetQ);

        // If both subtrees found a target node, the current node is the LCA
        if (leftResult != null && rightResult != null) {
            return currentNode;
        }

        // Otherwise, return whichever side found a target (or null if neither did)
        return leftResult != null ? leftResult : rightResult;
    }

    // Counts edges from the subtree root down to the node whose value == target
    // Returns -1 if target is not found in this subtree
    private int getDepth(TreeNode currentNode, int target, int currentDepth) {
        // Base case: empty subtree — target not here
        if (currentNode == null) {
            return -1;
        }

        // Found the target — return how many edges we travelled to get here
        if (currentNode.val == target) {
            return currentDepth;
        }

        // Search left subtree first; if found, bubble the depth up
        int leftDepth = getDepth(currentNode.left, target, currentDepth + 1);
        if (leftDepth != -1) {
            return leftDepth;
        }

        // Otherwise search the right subtree
        return getDepth(currentNode.right, target, currentDepth + 1);
    }
}
```

**Explanation of Key Code Sections:**

1. **`findLCA` — post-order reasoning:** We recurse into left and right children before making a decision at the current node. We need both children's results to know whether the current node is the LCA.

2. **Early return in `findLCA`:** If `currentNode.val == p || currentNode.val == q`, we immediately return `currentNode`. This works because even if the other target is in this node's subtree, the LCA is still this node (one is an ancestor of the other).

3. **`getDepth` — `currentDepth` accumulator:** We pass the running edge count as a parameter instead of incrementing a global. Each recursive call increments by 1 because one more edge is crossed.

4. **Return `-1` for "not found":** This sentinel value lets the caller cleanly distinguish "found" from "not in this subtree", without needing a boolean flag.

5. **Why search left before right in `getDepth`:** The tree structure does not bias towards either side; we pick left first as a convention. If the left result is `-1`, we try right.

---

## Complexity Analysis

- **Time Complexity:** $O(N)$ — `findLCA` visits every node once; `getDepth` is called twice but each traversal visits at most $O(N)$ nodes in the worst case.

- **Space Complexity:** $O(H)$ — where $H$ is the height of the tree (recursion stack). $O(\log N)$ for a balanced tree, $O(N)$ for a skewed tree.

---

## Similar Problems

Problems that can be solved using the same LCA + depth-counting pattern:

1. [236. Lowest Common Ancestor of a Binary Tree](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) — Core LCA algorithm used directly here.
2. [235. Lowest Common Ancestor of a Binary Search Tree](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/) — BST property makes LCA simpler (no full recursion needed).
3. [1123. Lowest Common Ancestor of Deepest Leaves](https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/) — LCA of a dynamically determined set of nodes.
4. [1026. Maximum Difference Between Node and Ancestor](https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/) — Propagate min/max from root down to leaves.
5. [543. Diameter of Binary Tree](https://leetcode.com/problems/diameter-of-binary-tree/) — Distance between two deepest leaves; same "left depth + right depth" structure at each node.
