### 236. Lowest Common Ancestor of a Binary Tree
Problem: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/

---

### Main Idea & Intuition

This is a classic tree problem that can be solved elegantly using a **post-order traversal**. The goal is to find the lowest (deepest) node in the tree that has two given nodes, `p` and `q`, as descendants.

**Core Logic:**
We can design a recursive function that traverses the tree and tells its parent what it found in its subtree. The function will return one of three things:
1.  `p`: if it found node `p`.
2.  `q`: if it found node `q`.
3.  `null`: if it found neither `p` nor `q`.

The magic happens when a parent node receives the results from its left and right children.

-   If the `left` child returns a non-null node and the `right` child also returns a non-null node, it means `p` was found in one subtree and `q` was found in the other. Therefore, the **current node** must be the Lowest Common Ancestor (LCA). It's the first node from the bottom up that has `p` and `q` in separate branches.
-   If only one child returns a non-null node (e.g., `left` returns `p` and `right` returns `null`), it means the LCA must be in the branch that found something. So, the parent just bubbles that result up.
-   If the current node itself is `p` or `q`, it has found one of the targets and should return itself to its parent.

This is a bottom-up approach because a node makes its decision only after hearing back from its children.

---

### Applying General Tree Intuitions

This problem is a perfect illustration of a bottom-up, post-order traversal.

-   **Global Variable vs. Return Value**: No global variable is needed. The function's return value is powerful enough to solve the problem. It returns a `TreeNode` if `p` or `q` is found in its subtree, and `null` otherwise. The parent node uses these return values to make its decision.

-   **Pre-order vs. Post-order**: This is a classic **post-order (bottom-up)** approach. A node cannot decide if it is the LCA until it has received the results from its left and right children. It needs to know if `p` was found on one side and `q` on the other. This "wait and see" approach is the definition of post-order processing.

-   **Helper Function**: Not needed. The function signature `lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q)` is perfectly self-sufficient. The recursive calls use the exact same signature, so there's no need for an extra layer.

---

### Step-by-Step to the Final Code

**Step 1: Define the recursive function `lowestCommonAncestor`.**
This function will take the current `node`, `p`, and `q` as input.

**Step 2: Define the base cases.**
- If the current `node` is `null`, we've reached the end of a branch without finding anything. Return `null`.
- If the current `node` is either `p` or `q`, we have found one of our targets. We don't need to search any deeper in this branch. Return the `node` itself.

```java
public TreeNode lowestCommonAncestor(TreeNode node, TreeNode p, TreeNode q) {
    if (node == null || node == p || node == q) {
        return node;
    }
    // ... more logic
}
```

**Step 3: Add recursive calls (Post-order traversal).**
Recursively call the function on the left and right children. This will search the entire subtrees before we process the current node.

```java
public TreeNode lowestCommonAncestor(TreeNode node, TreeNode p, TreeNode q) {
    // ... base cases

    TreeNode leftResult = lowestCommonAncestor(node.left, p, q);
    TreeNode rightResult = lowestCommonAncestor(node.right, p, q);
    // ...
}
```

**Step 4: Process the results from the children.**
This is the core logic of the algorithm.
- If `leftResult` and `rightResult` are both non-null, it means `p` and `q` were found in different subtrees. The current `node` is the LCA.
- If only `leftResult` is non-null, it means both `p` and `q` are in the left subtree. The `leftResult` is the LCA, so we bubble it up.
- If only `rightResult` is non-null, the logic is the same for the right side.

```java
public TreeNode lowestCommonAncestor(TreeNode node, TreeNode p, TreeNode q) {
    // ... recursive calls

    if (leftResult != null && rightResult != null) {
        return node; // This is the LCA
    }

    // Otherwise, bubble up whichever result is not null
    return leftResult != null ? leftResult : rightResult;
}
```

**Final Code:**

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Base Case 1: If the tree is empty, or we found p or q, return the node.
        // If root is p or q, it is the highest possible ancestor for itself.
        if (root == null || root == p || root == q) {
            return root;
        }

        // Post-order traversal: search in left and right subtrees first.
        TreeNode leftResult = lowestCommonAncestor(root.left, p, q);
        TreeNode rightResult = lowestCommonAncestor(root.right, p, q);

        // Process results from children:
        // Case 1: p and q were found in different subtrees (left and right).
        // This means the current 'root' is their first common ancestor.
        if (leftResult != null && rightResult != null) {
            return root;
        }

        // Case 2: p and q are in the same subtree.
        // The non-null result from a child is the LCA. Bubble it up.
        // If both are null, this branch doesn't contain p or q, so we return null.
        return leftResult != null ? leftResult : rightResult;
    }
}
```

---

### Complexity Analysis

*   **Time Complexity: O(N)**
    *   We visit each node in the tree at most once. `N` is the number of nodes.

*   **Space Complexity: O(H)**
    *   The space complexity is determined by the recursion stack depth, which is the height of the tree, `H`. In the worst case (a skewed tree), this is `O(N)`. In a balanced tree, it's `O(log N)`.
