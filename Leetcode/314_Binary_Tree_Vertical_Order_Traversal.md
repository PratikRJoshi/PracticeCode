# Binary Tree Vertical Order Traversal

## Problem Description

**Problem Link:** [Binary Tree Vertical Order Traversal](https://leetcode.com/problems/binary-tree-vertical-order-traversal/)

Given the `root` of a binary tree, return the **vertical order traversal** of its nodes' values (from top to bottom, column by column).

If two nodes are in the **same row and same column**, the order should be from **left to right**.

**Example 1:**
```
Input: root = [3,9,20,null,null,15,7]
Tree:
    3
   / \
  9  20
    /  \
   15   7
Output: [[9],[3,15],[20],[7]]
```

**Example 2:**
```
Input: root = [3,9,8,4,0,1,7]
Tree:
      3
     / \
    9   8
   / \ / \
  4  0 1  7
Output: [[4],[9],[3,0,1],[8],[7]]
```

**Example 3:**
```
Input: root = [3,9,8,4,0,1,7,null,null,null,2,5]
(0's right child is 2 and 1's left child is 5)
Tree:
      3
     / \
    9   8
   / \ / \
  4  0 1  7
      \ /
      2 5
Output: [[4],[9,5],[3,0,1],[8,2],[7]]
```

**Constraints:**
- The number of nodes in the tree is in the range `[0, 100]`.
- `-100 <= Node.val <= 100`

---

## Intuition/Main Idea

### Building the Intuition Incrementally

**Step 1 — What does "vertical column" mean?**
Assign each node a column index. Root is at column `0`. Every left child shifts column by `-1`; every right child shifts by `+1`. All nodes sharing the same column index belong to the same vertical group.

**Step 2 — What order do we need within a column?**
Top to bottom (by depth/row). For ties (same row AND same column), left to right — meaning whichever node was visited from the left side of the tree first.

**Step 3 — Why BFS and NOT DFS or the sort-based approach from problem 987?**
This is the key distinction between 314 and 987:
- In 987, ties (same row + same column) are broken by **node value** (smallest first).
- In 314, ties are broken by **left-to-right order** in the original tree — i.e., whichever node appears earlier in a level-order traversal wins.

BFS (level-order traversal) naturally visits nodes from left to right within each level. If we track the column during BFS, nodes within the same column are added to their column group in exactly the correct order — **no sorting is needed at all**. This is why BFS is the natural fit here, whereas DFS + sort is the fit for 987.

**Step 4 — How to track the column range?**
Use a `HashMap<Integer, List<Integer>>` keyed by column index. Also track `minColumn` and `maxColumn` as we go so we can iterate columns left to right at the end without sorting the keys.

**Step 5 — Algorithm:**
1. BFS with a queue of `(node, column)` pairs.
2. For each dequeued node, append its value to `columnMap.get(column)`.
3. Enqueue left child with `column - 1`, right child with `column + 1`.
4. After BFS, iterate from `minColumn` to `maxColumn` and collect each list.

### Helper Function Analysis (Tree Problem Section)

**Why a helper function is NOT required:**
The entire logic fits cleanly in one iterative BFS loop. There is no recursive decomposition needed. The BFS queue replaces the call stack.

**Why a global variable is NOT required:**
All state (queue, map, min/max column) lives locally inside `verticalOrder`. No shared mutable state is needed across calls.

**What is calculated at the current node:**
Its column index (passed via the queue pair), and its value is appended to the correct column bucket.

**What is returned to the parent:**
Nothing — BFS is iterative. The column index of the current node determines the column indices passed to children.

**Whether to recurse into children before or after current node calculation:**
In BFS, we process (record) the current node's value first, then enqueue its children. This ensures top-to-bottom ordering within each column is preserved naturally.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|--------------------------|-------------------------------------|
| Assign column index to every node | Queue stores `int[]` pairs `{nodeRef-index, column}` |
| Top-to-bottom order within column | BFS naturally dequeues level by level (top first) |
| Left-to-right tie-breaking (same row, same col) | BFS visits left child before right child; left goes to queue first |
| Group nodes by column | `columnMap.put(column, list)` keyed by column index |
| Iterate columns left to right | Loop from `minColumn` to `maxColumn` inclusive |
| Handle empty tree | Early return `new ArrayList<>()` when `root == null` |

---

## Final Java Code & Learning Pattern

### Simpler Solution — Single Queue + TreeMap

The cleanest way to express this: bundle each node with its column index in one queue entry (`int[]` pair), and use a `TreeMap` so the keys stay sorted automatically — no min/max tracking needed.

```java
class Solution {

    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        // TreeMap keeps column keys sorted left-to-right automatically
        Map<Integer, List<Integer>> columnMap = new TreeMap<>();

        // Single queue: each int[] entry is {node-value-placeholder, column}
        // We pair the TreeNode and its column together using a queue of Object[]
        Queue<Object[]> queue = new LinkedList<>();
        queue.offer(new Object[]{root, 0}); // {TreeNode, columnIndex}

        while (!queue.isEmpty()) {
            Object[] entry = queue.poll();
            TreeNode currentNode = (TreeNode) entry[0];
            int currentColumn = (int) entry[1];

            // Add this node's value to the correct column bucket
            columnMap.computeIfAbsent(currentColumn, k -> new ArrayList<>())
                     .add(currentNode.val);

            // Enqueue left child first → preserves left-to-right order within a column
            if (currentNode.left != null) {
                queue.offer(new Object[]{currentNode.left, currentColumn - 1});
            }

            // Enqueue right child second
            if (currentNode.right != null) {
                queue.offer(new Object[]{currentNode.right, currentColumn + 1});
            }
        }

        // TreeMap already sorted by column key — just collect values in order
        result.addAll(columnMap.values());

        return result;
    }
}
```

**Why this is simpler:**
- **One queue, one entry per node** — no parallel queues; the `Object[]` pair `{node, column}` keeps everything together, making the code easier to read.
- **`TreeMap` auto-sorts** — `columnMap.values()` is already in column order (left to right). No min/max tracking, no manual loop from `minColumn` to `maxColumn`.
- **`computeIfAbsent`** — creates the list on first access and adds in one line, replacing the two-step `putIfAbsent` + `get`.

**Trade-off vs. the optimised solution below:**
- `TreeMap` costs $O(\log N)$ per insertion instead of $O(1)$, making overall time $O(N \log N)$ vs. $O(N)$.
- For the given constraint of ≤ 100 nodes, this is completely negligible in practice.

---

### Optimised Solution — Parallel Queues + HashMap + Min/Max Tracking

```java
class Solution {

    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        // Maps each column index to the list of node values in that column
        // (in top-to-bottom, left-to-right order — guaranteed by BFS)
        Map<Integer, List<Integer>> columnMap = new HashMap<>();

        // Track the range of columns seen so we can iterate without sorting keys
        int minColumn = 0;
        int maxColumn = 0;

        // BFS queue: each entry is a pair [node, columnIndex]
        // Using a Queue of int[] where index 0 = node identity, index 1 = column
        // We store node references separately to avoid boxing overhead
        Queue<int[]> columnQueue = new LinkedList<>(); // stores column index per node
        Queue<TreeNode> nodeQueue = new LinkedList<>(); // stores node reference

        nodeQueue.offer(root);
        columnQueue.offer(new int[]{0}); // root is at column 0

        while (!nodeQueue.isEmpty()) {
            TreeNode currentNode = nodeQueue.poll();
            int currentColumn = columnQueue.poll()[0];

            // Record the current node in its column bucket
            columnMap.putIfAbsent(currentColumn, new ArrayList<>());
            columnMap.get(currentColumn).add(currentNode.val);

            // Update the column range
            minColumn = Math.min(minColumn, currentColumn);
            maxColumn = Math.max(maxColumn, currentColumn);

            // Enqueue left child first — left-to-right tie-breaking is implicit
            if (currentNode.left != null) {
                nodeQueue.offer(currentNode.left);
                columnQueue.offer(new int[]{currentColumn - 1});
            }

            // Enqueue right child second
            if (currentNode.right != null) {
                nodeQueue.offer(currentNode.right);
                columnQueue.offer(new int[]{currentColumn + 1});
            }
        }

        // Collect columns in left-to-right order (minColumn → maxColumn)
        // No sorting needed because we tracked the range explicitly
        for (int column = minColumn; column <= maxColumn; column++) {
            result.add(columnMap.get(column));
        }

        return result;
    }
}
```

**Explanation of Key Code Sections:**

1. **Two parallel queues (`nodeQueue` + `columnQueue`):** We pair each `TreeNode` with its column index. Keeping them in two separate queues is equivalent to a queue of `(TreeNode, int)` pairs and avoids creating wrapper objects.

2. **`putIfAbsent` before adding:** We ensure the column bucket exists before calling `.add()`. This is simpler than a `computeIfAbsent` but equally correct.

3. **Enqueue left before right:** This is the critical line for tie-breaking. In BFS, nodes are dequeued in FIFO order. Since left is enqueued first, it is always dequeued (and recorded) before right at any given level. This gives us the left-to-right tie-breaking for free.

4. **`minColumn` / `maxColumn` tracking:** Avoids the need to call `Collections.sort(columnMap.keySet())`. A simple linear loop from `minColumn` to `maxColumn` replaces a sort, keeping the overall algorithm $O(N)$ instead of $O(N \log N)$.

5. **Why not `TreeMap`:** A `TreeMap` would automatically sort keys, giving us columns in order. However, that would cost $O(\log N)$ per insertion. The explicit min/max tracking with a `HashMap` achieves the same result in $O(1)$ per insertion.

### Why BFS beats the DFS+Sort approach here (contrast with 987)

| | **314 (this problem)** | **987** |
|---|---|---|
| Tie-breaking rule | Left-to-right (tree position) | Smaller value first |
| Why BFS is correct | Left-to-right order is naturally preserved by BFS queue order | BFS order is NOT sufficient — value-based sort is required |
| Why sort alone doesn't work | Sorting by value would violate left-to-right ordering | Sorting by (col, row, value) is exactly what's needed |
| Time complexity | $O(N)$ | $O(N \log N)$ |

---

## Complexity Analysis

- **Time Complexity:** $O(N)$ — every node is enqueued and dequeued once. Map insertions and the final column iteration are all $O(N)$.

- **Space Complexity:** $O(N)$ — the BFS queues hold at most one full level at a time (at most $N/2$ nodes for the last level of a perfect tree); the `columnMap` stores all $N$ values.

---

## Similar Problems

Problems using BFS column-tracking on a binary tree:

1. [987. Vertical Order Traversal of a Binary Tree](https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/) — Harder variant: ties broken by value, not position. Requires DFS + sort instead of pure BFS.
2. [102. Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) — Groups nodes by row (depth) using the same BFS-with-snapshot technique.
3. [103. Binary Tree Zigzag Level Order Traversal](https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/) — Level order with alternating direction; same BFS grouping with a direction flag.
4. [116. Populating Next Right Pointers in Each Node](https://leetcode.com/problems/populating-next-right-pointers-in-each-node/) — Links nodes at the same level; level-order structure is identical, implemented with $O(1)$ extra space.

**Why seemingly similar problems differ:**
- **987** looks identical but the tie-breaking rule changes the entire algorithm from BFS to DFS+sort. Using BFS for 987 would produce a wrong answer for inputs like `[0, 2, 1, 3, null, null, null, 4, 5, null, 7, null, null, 6]` where two nodes share the exact same `(row, col)` position.
