# 863. All Nodes Distance K in Binary Tree (Generalized: Target by Value, with Duplicates)

**LeetCode Link:** [https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/](https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/)

---

## Problem Description

You are given a binary tree with a root node, an integer `k` denoting the number of hops (distance), and a **string value** for a node that is present in the tree.

> **Generalization from LeetCode 863:** The target is given as a **string value** rather than a direct node reference. Furthermore, this value **can be a duplicate** — meaning multiple nodes in the tree may share the same value. Your task is to find **all nodes** in the tree that are **exactly `k` hops (edges) away** from **any** node whose value matches the given target string.

The distance between two nodes in a tree is defined as the minimum number of edges traversed to get from one node to the other:
- A node's immediate parent or children are at distance 1.
- Their parents/children are at distance 2, and so on.

---

### Examples

#### Example 1 — Standard case (unique target)

```
        3
       / \
      5   1
     / \ / \
    6  2 0  8
      / \
     7   4
```

- **Input:** root = [3,5,1,6,2,0,8,null,null,7,4], targetValue = "5", k = 2
- **Output:** [7, 4, 1]
- **Explanation:** Nodes 7 and 4 are 2 hops below node 5. Node 1 is 2 hops above (via 5→3→1). Node 3 is only 1 hop away.

---

#### Example 2 — Duplicate target values

```
        1
       / \
      2   3
     / \
    4   2
```

- **Input:** root = [1,2,3,4,2], targetValue = "2", k = 1
- **Output:** [1, 4, 2, 1, 4]  *(collected from both nodes with value "2")*
- **Explanation:** There are two nodes with value "2": the left child of root (depth 1) and the right child of node 2 (depth 2). Nodes 1 hop from the first "2" are: 1, 4, 2(right child). Nodes 1 hop from the second "2" are: parent node 2, and node 4. All of these are collected (duplicates in the result list are acceptable since the question asks all nodes k hops from *any* matching target).

---

#### Example 3 — k = 0

- **Input:** targetValue = "5", k = 0
- **Output:** [5]
- **Explanation:** The target node itself is 0 hops away.

---

## Intuition / Main Idea

### Step 1 — Why is this non-trivial?

In a standard binary tree, each node only stores references to its **children** (left, right). If the node at distance `k` is **above** the target (an ancestor) or **in a sibling subtree**, we cannot reach it by going downward only.

For example, in the tree above, node `1` is 2 hops from node `5` — but to get there we must go **upward** (5 → 3) and then **downward** (3 → 1). The tree's `TreeNode` structure gives us no direct way to go upward.

---

### Step 2 — Key Insight: Treat the tree as an undirected graph

If we **add parent pointers** (a mapping from each node → its parent), then we can move in **3 directions** from any node:
1. Left child
2. Right child
3. Parent

This effectively turns the binary tree into an **undirected graph**, and finding all nodes at distance `k` becomes a standard **BFS/DFS from a source node in a graph** — just stop at depth `k`.

---

### Step 3 — Building the intuition incrementally

**Q: How do I know which nodes are the "start" nodes?**
Since the target is a string value (not a node reference), we must first do a **full tree traversal** to collect all nodes whose `.val` equals `targetValue`. This handles the **duplicate** case naturally.

**Q: How do I prevent going back the way I came (cycles)?**
We track the "parent we came from" (`fromNode`) during the distance-DFS so we never revisit the node we just came from. Alternatively, a `visited` set (keyed by node reference) works too — and is better for the duplicate-value case since we can't key by value.

**Q: Why DFS over BFS for the distance traversal?**
Both work. DFS with a `remainingHops` counter is clean and concise. BFS with a level counter is equally valid. We use DFS here to match the spirit of the tree-problems rule section.

**Q: Why do we need parent pointers built before the distance DFS?**
Without parent pointers, starting from a target node, we can only descend. We'd miss all ancestors and sibling-subtree nodes. The parent map is the key that unlocks upward movement.

---

### Step 4 — Why the intuition works

After building the parent map:
- Every node now has **at most 3 neighbors**: left child, right child, parent.
- Starting from each target node, we do a DFS exploring all 3 directions.
- We decrement `remainingHops` at each step.
- When `remainingHops == 0`, we record the current node.
- We use a `visited` set (of node references, not values!) to avoid revisiting nodes — critical because multiple target nodes may trigger overlapping searches.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Tree node value is a **String** | `class TreeNode { String val; ... }` — `val` field is `String` |
| **Duplicate** target values → multiple starting nodes | `findTargetNodes(root, targetValue, targetNodes)` collects all matching `TreeNode` references |
| Build parent pointers for upward traversal | `buildParentMap(root, null, parentMap)` — maps each node to its parent |
| Find all nodes exactly k hops away from each target | `collectKHopsAway(node, fromNode, remainingHops, parentMap, visited, result)` |
| Avoid revisiting nodes across multiple target expansions | `Set<TreeNode> visited` — keyed by reference, shared across all target expansions |
| Return all values of nodes k hops away | `result` list populated when `remainingHops == 0` |

---

## Final Java Code & Learning Pattern

```java
import java.util.*;

public class AllNodesKHopsAway {

    // TreeNode definition with String value (generalized version)
    static class TreeNode {
        String val;
        TreeNode left, right;

        TreeNode(String val) {
            this.val = val;
        }
    }

    /**
     * LEARNING PATTERN: "Parent-Map + Multi-Source DFS"
     *
     * Step 1: Build a map of node -> parent for every node (enables upward traversal).
     * Step 2: Collect all target nodes (handles duplicate values).
     * Step 3: For each target node, DFS in all 3 directions (left, right, parent)
     *         decrementing the hop count, and collect nodes when hops reach 0.
     *         A shared `visited` set prevents revisiting nodes across multiple target
     *         node expansions.
     */
    public List<String> findNodesKHopsAway(TreeNode root, String targetValue, int k) {
        List<String> result = new ArrayList<>();

        if (root == null) return result;

        // -------------------------------------------------------------------
        // PHASE 1: Build parent map
        // We need to traverse upward from any node. Since TreeNode only has
        // child pointers, we pre-build a Map<node -> parent> using a DFS.
        // -------------------------------------------------------------------
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        buildParentMap(root, null, parentMap);

        // -------------------------------------------------------------------
        // PHASE 2: Collect all nodes whose value matches targetValue
        // Because duplicates are allowed, there can be multiple target nodes.
        // We store node references (not values) so each is treated independently.
        // -------------------------------------------------------------------
        List<TreeNode> targetNodes = new ArrayList<>();
        findTargetNodes(root, targetValue, targetNodes);

        // -------------------------------------------------------------------
        // PHASE 3: From each target node, explore all directions (left, right,
        // parent) for exactly k hops. A shared `visited` set ensures we do not
        // add the same node twice if it happens to be k hops from two different
        // target nodes. We key by node reference, not value, to correctly handle
        // nodes that happen to share a value string.
        // -------------------------------------------------------------------
        Set<TreeNode> visited = new HashSet<>();

        for (TreeNode targetNode : targetNodes) {
            collectKHopsAway(targetNode, null, k, parentMap, visited, result);
        }

        return result;
    }

    // -----------------------------------------------------------------------
    // Helper 1: Build parent map via pre-order DFS
    //
    // WHY a helper is needed: The main method coordinates phases; this keeps
    // concerns separated. We pass `parentNode` as we recurse so we can record
    // the parent of each visited node.
    //
    // WHY no global variable: We pass `parentMap` as a parameter, which is
    // cleaner and avoids class-level state for a method that could be called
    // concurrently or reused.
    //
    // WHAT is calculated at the current node:
    //   - Record parentMap[currentNode] = parentNode
    //
    // WHAT is returned to parent: void (side-effect: fills parentMap)
    //
    // RECURSIVE CALL ORDER: We record the parent BEFORE recursing into children
    // (pre-order), because by the time we recurse, we already know the parent.
    // -----------------------------------------------------------------------
    private void buildParentMap(TreeNode currentNode, TreeNode parentNode,
                                 Map<TreeNode, TreeNode> parentMap) {
        if (currentNode == null) return;

        // Record this node's parent (root's parent will be null, which is correct)
        parentMap.put(currentNode, parentNode);

        // Recurse into children — pass currentNode as their parent
        buildParentMap(currentNode.left, currentNode, parentMap);
        buildParentMap(currentNode.right, currentNode, parentMap);
    }

    // -----------------------------------------------------------------------
    // Helper 2: Collect all TreeNode references whose val == targetValue
    //
    // WHY a helper: Separation of concerns — this is purely a search step.
    // WHY no global variable: result list is passed in as a parameter.
    // WHAT is calculated: whether current node's value matches targetValue.
    // WHAT is returned: void (side-effect: fills targetNodes list).
    // RECURSIVE CALL ORDER: Check current node first (pre-order), then children.
    // -----------------------------------------------------------------------
    private void findTargetNodes(TreeNode currentNode, String targetValue,
                                  List<TreeNode> targetNodes) {
        if (currentNode == null) return;

        if (currentNode.val.equals(targetValue)) {
            targetNodes.add(currentNode);
        }

        findTargetNodes(currentNode.left, targetValue, targetNodes);
        findTargetNodes(currentNode.right, targetValue, targetNodes);
    }

    // -----------------------------------------------------------------------
    // Helper 3: DFS to collect nodes exactly `remainingHops` away from `currentNode`
    //
    // WHY a helper: This is the core distance-DFS logic; separating it allows
    // calling it once per target node in Phase 3.
    //
    // WHY no global variable: `result` and `visited` are passed in and shared
    // across calls within a single invocation of findNodesKHopsAway.
    //
    // WHAT is calculated at the current node:
    //   - If remainingHops == 0, this node is exactly k hops from the source;
    //     add its value to result.
    //   - Otherwise, propagate the DFS in all 3 directions.
    //
    // WHAT is returned to parent: void (side-effect: fills result).
    //
    // RECURSIVE CALL ORDER (post/pre doesn't matter here): We check the base
    // case (hops == 0) first, then recurse — because we want to record the
    // current node *before* going deeper (if hops == 0, we stop here).
    //
    // `fromNode` — the node we just came from — prevents revisiting:
    //   - When going down to left/right child: fromNode = currentNode (so child
    //     doesn't recurse back up to currentNode).
    //   - When going up to parent: fromNode = currentNode (so parent doesn't
    //     recurse back down to currentNode).
    //
    // WHY `visited` set (keyed by reference) is also needed:
    //   When multiple target nodes are processed sequentially, a node might be
    //   reachable from two different targets. The `fromNode` trick alone cannot
    //   prevent this across separate DFS calls. The `visited` set (shared across
    //   all target expansions) is the correct guard.
    // -----------------------------------------------------------------------
    private void collectKHopsAway(TreeNode currentNode, TreeNode fromNode,
                                   int remainingHops,
                                   Map<TreeNode, TreeNode> parentMap,
                                   Set<TreeNode> visited,
                                   List<String> result) {
        // Base case: null node or already visited
        if (currentNode == null || visited.contains(currentNode)) return;

        // Mark as visited so no other target's DFS re-processes this node
        visited.add(currentNode);

        // If we have traveled exactly k hops, record this node
        if (remainingHops == 0) {
            result.add(currentNode.val);
            return; // No need to go further — we only want nodes at EXACTLY k hops
        }

        // Explore left child (if it's not the node we came from)
        if (currentNode.left != fromNode) {
            collectKHopsAway(currentNode.left, currentNode, remainingHops - 1,
                             parentMap, visited, result);
        }

        // Explore right child (if it's not the node we came from)
        if (currentNode.right != fromNode) {
            collectKHopsAway(currentNode.right, currentNode, remainingHops - 1,
                             parentMap, visited, result);
        }

        // Explore parent (going upward) — if it's not the node we came from
        TreeNode parentNode = parentMap.get(currentNode);
        if (parentNode != null && parentNode != fromNode) {
            collectKHopsAway(parentNode, currentNode, remainingHops - 1,
                             parentMap, visited, result);
        }
    }

    // -----------------------------------------------------------------------
    // Driver / Test
    // -----------------------------------------------------------------------
    public static void main(String[] args) {
        AllNodesKHopsAway solver = new AllNodesKHopsAway();

        // Build Example 1 tree:
        //        3
        //       / \
        //      5   1
        //     / \ / \
        //    6  2 0  8
        //      / \
        //     7   4
        TreeNode root1 = new TreeNode("3");
        root1.left = new TreeNode("5");
        root1.right = new TreeNode("1");
        root1.left.left = new TreeNode("6");
        root1.left.right = new TreeNode("2");
        root1.right.left = new TreeNode("0");
        root1.right.right = new TreeNode("8");
        root1.left.right.left = new TreeNode("7");
        root1.left.right.right = new TreeNode("4");

        System.out.println("Example 1 (target=5, k=2): " +
            solver.findNodesKHopsAway(root1, "5", 2));
        // Expected: [7, 4, 1] (order may vary)

        // Build Example 2 — duplicate "2" values:
        //        1
        //       / \
        //      2   3
        //     / \
        //    4   2
        TreeNode root2 = new TreeNode("1");
        root2.left = new TreeNode("2");
        root2.right = new TreeNode("3");
        root2.left.left = new TreeNode("4");
        root2.left.right = new TreeNode("2");

        System.out.println("Example 2 (target=2, k=1): " +
            solver.findNodesKHopsAway(root2, "2", 1));
        // Expected: nodes 1 hop from first "2" (left child): 1, 4, 2(right-child)
        //           nodes 1 hop from second "2" (right-child): parent "2" is already visited
        //           So result: [1, 4, 2]

        // Example 3 — k = 0, returns the target node itself
        System.out.println("Example 3 (target=5, k=0): " +
            solver.findNodesKHopsAway(root1, "5", 0));
        // Expected: [5]
    }
}
```

---

## Tree Problem Analysis (Per Rule)

### Why a helper function is required
Three helpers are used with clear separation:
- `buildParentMap` — builds the parent pointer map in one full DFS pass.
- `findTargetNodes` — collects all nodes matching the target value string (handles duplicates).
- `collectKHopsAway` — the core distance-DFS that explores left, right, and parent directions.

Without this separation, the main method would be unreadable and difficult to reason about.

### Why no global variable is required
All state (`parentMap`, `visited`, `result`, `targetNodes`) is passed as parameters. This keeps the solution reusable, testable, and thread-safe. No class-level mutable fields are needed.

### What is calculated at the current node
- In `buildParentMap`: record `parentMap[currentNode] = parentNode`.
- In `findTargetNodes`: check if `currentNode.val == targetValue`, add to list if so.
- In `collectKHopsAway`: if `remainingHops == 0`, record node in result; otherwise, recurse in 3 directions with `remainingHops - 1`.

### What is returned to the parent from the current level
All three helpers are `void` — they mutate shared data structures passed as parameters. No return value propagates up the call stack.

### How to decide if recursive call to children is made before or after current node calculation
In all three helpers, the current node is processed **first** (pre-order), then children are recurrsed into. This is natural because:
- In `buildParentMap`: we must record the parent of `currentNode` before recursing into children (otherwise, children won't find their parent in the map during their own visit — though the map is filled top-down anyway).
- In `findTargetNodes`: we check current before children so we can short-circuit early if desired.
- In `collectKHopsAway`: we check `remainingHops == 0` before recursing; if we've reached the target distance, there's no need to go deeper.

---

## Complexity Analysis

**Time Complexity:** $O(N)$
- `buildParentMap` visits every node once: $O(N)$.
- `findTargetNodes` visits every node once: $O(N)$.
- `collectKHopsAway` — across all target nodes combined, each node is visited at most once (guarded by the `visited` set): $O(N)$.
- **Total:** $O(N)$.

**Space Complexity:** $O(N)$
- `parentMap`: stores one entry per node → $O(N)$.
- `visited` set: at most $N$ entries → $O(N)$.
- `targetNodes` list: at most $N$ entries (degenerate case where all values match) → $O(N)$.
- Recursion call stack: $O(H)$ where $H$ is the height of the tree (worst case $O(N)$ for a skewed tree).
- **Total:** $O(N)$.

---

## Similar Problems

These problems share the **"parent-map + multi-directional DFS/BFS"** or **"distance from node in tree"** pattern:

- [**LeetCode 863 — All Nodes Distance K in Binary Tree**](https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/) — The exact LeetCode version of this problem, where target is given as a `TreeNode` reference instead of a string value.
- [**LeetCode 1740 — Find Distance in a Binary Tree**](https://leetcode.com/problems/find-distance-in-a-binary-tree/) — Find the distance between two nodes in a binary tree; uses LCA + depth counting, closely related pattern.
- [**LeetCode 1026 — Maximum Difference Between Node and Ancestor**](https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/) — Requires tracking values along a root-to-node path; similar DFS-with-state-passing structure.
- [**LeetCode 987 — Vertical Order Traversal of a Binary Tree**](https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/) — Requires tracking coordinates (row, col) of each node; same pre-pass DFS + separate collection pattern.
- [**LeetCode 742 — Closest Leaf in a Binary Tree**](https://leetcode.com/problems/closest-leaf-in-a-binary-tree/) — Find nearest leaf to a target node; requires upward traversal via parent map, directly analogous.

> **Why LeetCode 102 (Level Order Traversal) is NOT similar:** Although both use tree traversal, level-order traversal is always from the root downward with BFS levels. Here, the "level" (distance) is measured from an arbitrary interior node and requires upward movement — a fundamentally different problem structure.
