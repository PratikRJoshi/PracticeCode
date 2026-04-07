# Closest Leaf in a Binary Tree

## Problem Description

**Problem Link:** [Closest Leaf in a Binary Tree](https://leetcode.com/problems/closest-leaf-in-a-binary-tree/)

Given the `root` of a binary tree where every node has a **unique value**, and a target integer `k`, return the value of the **nearest leaf node** to the node with value `k` in the tree.

**Nearest leaf** means the fewest number of edges travelled on the binary tree to reach any leaf. A node is a leaf if it has **no children**.

**Example 1:**
```
Input: root = [1, 3, 2], k = 1
Tree:
    1
   / \
  3   2
Output: 2 (or 3)
Explanation: Either 2 or 3 is the nearest leaf node to the target of 1. Both are 1 edge away.
```

**Example 2:**
```
Input: root = [1], k = 1
Output: 1
Explanation: The nearest leaf node is the root node itself (it is a leaf).
```

**Example 3:**
```
Input: root = [1,2,3,4,null,null,null,5,null,6], k = 2
Tree:
        1
       / \
      2   3
     /
    4
   /
  5
 /
6
Output: 3
Explanation: The leaf node with value 3 (not 6) is nearest to the node with value 2 (2 edges: 2→1→3).
```

**Constraints:**
- The number of nodes in the tree is in the range `[1, 1000]`.
- `1 <= Node.val <= 1000`
- All `Node.val` are unique.
- There exists a node in the tree with `Node.val == k`.

---

## Intuition/Main Idea

### Building the Intuition Incrementally

**Step 1 — Why this problem is harder than it looks.**
Finding the closest leaf **below** the target node `k` is easy: BFS/DFS downward from `k`. But the closest leaf might be reached by going **upward** from `k` (through its parent or ancestors) and then back down. Example 3 illustrates this: the leaf `3` is above-and-across from node `2`.

**Step 2 — Observation: "upward travel" is hard in a standard binary tree.**
In a standard `TreeNode`, there are no parent pointers. Going upward requires us to reconstruct the path or convert the tree structure.

**Step 3 — Key idea: convert the tree to a graph.**
If we model the binary tree as an **undirected graph** (adjacency list), then every edge becomes bidirectional. "Going up to a parent" becomes just another neighbour traversal. The problem then reduces to: **find the shortest path from node `k` to any leaf in this undirected graph** — which is exactly BFS from `k`.

**Why graph + BFS works:**
- BFS on an unweighted graph always finds the shortest path.
- Treating the binary tree as an undirected graph removes the directional constraint (parent → child only) that makes upward traversal hard.
- A node is a leaf in the original tree if it had no children; we can record all leaves while building the graph.

**Step 4 — Algorithm summary:**
1. DFS to build the adjacency list and record all leaf nodes.
2. BFS starting from the node with value `k`.
3. Return the value of the first leaf node reached.

### Helper Function Analysis (Tree Problem Section)

**Why a helper function is required:**
The `buildGraph` DFS helper traverses the entire tree to populate the adjacency list and leaf set. This is separate from the BFS phase and cleanly encapsulates tree-to-graph conversion.

**Why a global variable IS used (adjacency map + leaf set):**
Both the BFS phase and the DFS phase need access to the same adjacency map and leaf set. Storing them as instance fields avoids threading them through every recursive call.

**What is calculated at the current node:**
- Whether this node is a leaf (no children) — if so, its value is added to `leafNodes`.
- Bidirectional edges between `currentNode` and each of its children are added to the adjacency list.

**What is returned to the parent:**
Nothing (`void`). The DFS is purely for side-effects (populating the graph).

**Whether to recurse into children before or after current node calculation:**
Current node processing happens **before** recursing into children (pre-order). We add the current-to-child edges first, then recurse. However, the order does not affect correctness since we are populating a complete data structure that BFS uses after DFS is fully done.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|--------------------------|-------------------------------------|
| Convert tree to undirected graph | `buildGraph` method — adds bidirectional edges |
| Record all leaf nodes | `leafNodes.add(currentNode.val)` when node has no children |
| BFS from node k | `Queue<Integer>` initialized with `k`; `visited` set to avoid cycles |
| Return first leaf reached by BFS | Check `leafNodes.contains(current)` inside BFS loop |
| Handle `k` itself being a leaf | BFS starts at `k`; first iteration checks if `k` is in `leafNodes` |

---

## Final Java Code & Learning Pattern

```java
class Solution {

    // Adjacency list representing the binary tree as an undirected graph
    private Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

    // Set of values that are leaf nodes in the original binary tree
    private Set<Integer> leafNodes = new HashSet<>();

    public int findClosestLeaf(TreeNode root, int k) {
        // Step 1: Build undirected graph from the binary tree
        buildGraph(root, -1);

        // Step 2: BFS from node k — first leaf reached is the closest
        Queue<Integer> bfsQueue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        bfsQueue.offer(k);
        visited.add(k);

        while (!bfsQueue.isEmpty()) {
            int currentNodeValue = bfsQueue.poll();

            // If this node is a leaf in the original tree, return it immediately
            if (leafNodes.contains(currentNodeValue)) {
                return currentNodeValue;
            }

            // Expand to all neighbours (both children and parent in original tree)
            for (int neighbourValue : adjacencyList.getOrDefault(currentNodeValue, new ArrayList<>())) {
                if (!visited.contains(neighbourValue)) {
                    visited.add(neighbourValue);
                    bfsQueue.offer(neighbourValue);
                }
            }
        }

        // Should never reach here given valid input
        return -1;
    }

    // Pre-order DFS: adds bidirectional edges and records leaves
    private void buildGraph(TreeNode currentNode, int parentValue) {
        if (currentNode == null) {
            return;
        }

        // Initialize adjacency list entry for this node
        adjacencyList.putIfAbsent(currentNode.val, new ArrayList<>());

        // Add bidirectional edge between this node and its parent
        if (parentValue != -1) {
            adjacencyList.get(currentNode.val).add(parentValue);
            adjacencyList.get(parentValue).add(currentNode.val);
        }

        // If this node has no children, it is a leaf
        if (currentNode.left == null && currentNode.right == null) {
            leafNodes.add(currentNode.val);
        }

        // Recurse into children, passing the current node's value as their parent
        buildGraph(currentNode.left, currentNode.val);
        buildGraph(currentNode.right, currentNode.val);
    }
}
```

**Explanation of Key Code Sections:**

1. **`parentValue == -1` for root:** The root has no parent. We use `-1` as a sentinel and skip adding an edge for the root. Since all values are in `[1, 1000]`, `-1` never collides with a real node value.

2. **Bidirectional edge insertion:** Both `adjacencyList.get(currentNode.val).add(parentValue)` and `adjacencyList.get(parentValue).add(currentNode.val)` are needed. Without the parent→child direction being reversible, the BFS could not travel upward through the tree.

3. **`visited` set in BFS:** The undirected graph has cycles (parent ↔ child). Without the `visited` set, BFS would loop back and forth between a node and its parent infinitely.

4. **`adjacencyList.putIfAbsent` before accessing:** We ensure every node has an entry in the map before adding edges. This prevents `NullPointerException` when `adjacencyList.get(currentNode.val).add(...)` is called.

5. **Why BFS and not DFS:** BFS expands nodes in order of increasing distance from the source. The first leaf it reaches is guaranteed to be at the minimum distance. DFS would find A leaf, but not necessarily the closest one.

6. **The `visited.add(k)` before BFS loop:** Prevents BFS from re-enqueueing `k` if it appears as a neighbour of one of its neighbours (which happens since edges are bidirectional).

---

## Complexity Analysis

- **Time Complexity:** $O(N)$ — building the graph visits every node once; BFS visits each node at most once.

- **Space Complexity:** $O(N)$ — adjacency list stores $O(N)$ edges (one per tree edge, doubled for bidirectionality); `leafNodes` and `visited` store at most $O(N)$ entries; BFS queue holds at most $O(N)$ elements.

---

## Similar Problems

Problems that use graph conversion of a tree or multi-directional BFS:

1. [863. All Nodes Distance K in Binary Tree](https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/) — Identical approach: convert tree to undirected graph, then BFS from a source node to find all nodes at exactly distance `K`. The closest cousin of this problem.
2. [1740. Find Distance in a Binary Tree](https://leetcode.com/problems/find-distance-in-a-binary-tree/) — Distance between two specific nodes; solvable with LCA or the same graph+BFS approach.
3. [994. Rotting Oranges](https://leetcode.com/problems/rotting-oranges/) — Multi-source BFS on a grid to find the minimum time to reach all cells; same BFS-for-shortest-distance principle.
4. [1162. As Far from Land as Possible](https://leetcode.com/problems/as-far-from-land-as-possible/) — Multi-source BFS to find maximum distance; inverse of "closest" but same BFS strategy.

**Why seemingly similar problems differ:**
- **[543. Diameter of Binary Tree](https://leetcode.com/problems/diameter-of-binary-tree/)** finds the longest path between any two nodes; it does **not** require graph conversion because it computes depth from each subtree root, which is purely downward. This problem requires upward travel, making graph conversion necessary.
