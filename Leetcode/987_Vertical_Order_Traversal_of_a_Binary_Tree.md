# Vertical Order Traversal of a Binary Tree

## Problem Description

**Problem Link:** [Vertical Order Traversal of a Binary Tree](https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/)

Given the `root` of a binary tree, calculate the **vertical order traversal** of the binary tree.

For each node at position `(row, col)`:
- Its **left child** will be at position `(row + 1, col - 1)`.
- Its **right child** will be at position `(row + 1, col + 1)`.

The root of the tree is at `(0, 0)`.

The vertical order traversal is a list of non-empty reports for each unique column from left to right. Each report is a list of all node values in that column, ordered from **top to bottom**. If two nodes are in the same position (same row and same column), they are sorted by their **value**.

**Example 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: [[9],[3,15],[20],[7]]

Tree:
    3          (row=0, col=0)
   / \
  9  20        (row=1, col=-1) and (row=1, col=1)
    /  \
   15   7      (row=2, col=0) and (row=2, col=2)

Column -1: [9]
Column  0: [3, 15]   (row 0 then row 2)
Column  1: [20]
Column  2: [7]
```

**Example 2:**
```
Input: root = [1,2,3,4,5,6,7]
Output: [[4],[2],[1,5,6],[3],[7]]

Tree:
        1         (row=0, col=0)
       / \
      2   3       (row=1, col=-1) and (row=1, col=1)
     / \ / \
    4  5 6  7     (row=2, col=-2), (row=2, col=0), (row=2, col=0), (row=2, col=2)

Column -2: [4]
Column -1: [2]
Column  0: [1, 5, 6]   (5 and 6 share same position → sorted by value)
Column  1: [3]
Column  2: [7]
```

**Constraints:**
- The number of nodes in the tree is in the range `[1, 1000]`.
- `0 <= Node.val <= 1000`

---

## Intuition/Main Idea

### Building the Intuition Incrementally

**Step 1 — Assign coordinates to every node.**
Imagine overlaying a grid on the tree. The root starts at `(row=0, col=0)`. Each left child shifts the column left by 1 (`col - 1`) and increases the row by 1; each right child shifts right by 1 (`col + 1`) and also increases the row by 1.

With coordinates in hand, the problem becomes: **group nodes by column, and within each column sort by row, then by value for ties.**

**Step 2 — How to collect coordinates efficiently?**
A simple DFS (pre-order or any order) can visit every node and record its `(col, row, value)` triple. We store all triples in a list.

**Step 3 — How to produce the final answer?**
Sort the collected triples by `(col, row, value)` — this gives us the exact order required. Then group consecutive triples with the same `col` into one list.

**Why this sorting works:**
- Primary sort by `col` → groups the vertical lines left to right.
- Secondary sort by `row` → within a column, top-to-bottom ordering.
- Tertiary sort by `value` → tie-breaking rule for nodes at the exact same position.

**Step 4 — Why not BFS?**
BFS (level-order) naturally gives us top-to-bottom ordering within a column, but handling the column coordinate still requires the same bookkeeping. The sorting approach is cleaner and more direct.

### Helper Function Analysis (Tree Problem Section)

**Why a helper function is required:**
We need a recursive DFS that carries `(row, col)` context downward. Wrapping this in a `dfs` helper keeps the main method clean; the helper only exists to propagate coordinates.

**Why a global variable IS used (the `nodeList`):**
Each recursive call needs to append to a shared collection of `(col, row, value)` triples. Passing the list as a parameter is equivalent, but using an instance-level list is idiomatic and avoids threading the list through every call signature.

**What is calculated at the current node:**
The `(row, col, value)` triple for this node is recorded. The node does not need any information back from its children.

**What is returned to the parent:**
Nothing (`void`). The DFS is purely for side-effects (populating the shared list). The result is assembled after the DFS completes.

**Whether to recurse into children before or after current node calculation:**
The current node is recorded **before** recursing into children (pre-order). The order does not affect correctness here because we sort everything afterward, but recording the current node first is natural.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|--------------------------|-------------------------------------|
| Assign `(row, col)` to every node | `dfs` method, parameters `currentRow` and `currentCol` |
| Collect all triples | `nodeList.add(new int[]{currentCol, currentRow, currentNode.val})` |
| Sort by col, then row, then value | `Collections.sort(nodeList, ...)` comparator |
| Group by column | Loop over sorted list; detect column change |
| Build result list per column | `currentColumnValues` list flushed on column boundary |

---

## Final Java Code & Learning Pattern

```java
class Solution {

    // Shared list of [col, row, value] triples collected during DFS
    private List<int[]> nodeList = new ArrayList<>();

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        // Step 1: Traverse the tree and record (col, row, value) for every node
        dfs(root, 0, 0);

        // Step 2: Sort triples — primary: column, secondary: row, tertiary: value
        // This single sort encodes all three ordering requirements simultaneously
        Collections.sort(nodeList, (firstNode, secondNode) -> {
            if (firstNode[0] != secondNode[0]) return firstNode[0] - secondNode[0]; // sort by col
            if (firstNode[1] != secondNode[1]) return firstNode[1] - secondNode[1]; // sort by row
            return firstNode[2] - secondNode[2];                                    // sort by value
        });

        List<List<Integer>> result = new ArrayList<>();

        // Step 3: Group consecutive entries by column
        int previousColumn = Integer.MIN_VALUE;
        List<Integer> currentColumnValues = null;

        for (int[] triple : nodeList) {
            int column = triple[0];
            int nodeValue = triple[2];

            // When the column changes, start a new group
            if (column != previousColumn) {
                currentColumnValues = new ArrayList<>();
                result.add(currentColumnValues);
                previousColumn = column;
            }
            currentColumnValues.add(nodeValue);
        }

        return result;
    }

    // Pre-order DFS: record current node, then recurse into children
    private void dfs(TreeNode currentNode, int currentRow, int currentCol) {
        if (currentNode == null) {
            return;
        }

        // Record this node's position and value
        nodeList.add(new int[]{currentCol, currentRow, currentNode.val});

        // Left child: same row depth + 1, column shifts left by 1
        dfs(currentNode.left, currentRow + 1, currentCol - 1);

        // Right child: same row depth + 1, column shifts right by 1
        dfs(currentNode.right, currentRow + 1, currentCol + 1);
    }
}
```

**Explanation of Key Code Sections:**

1. **`nodeList` stores `[col, row, value]`:** We put `col` first so the comparator can use a simple three-field sort without reordering the array fields.

2. **Three-key comparator:** Sorting by `(col, row, value)` in one pass is more efficient than grouping first and sorting within groups. It fully determines the output order.

3. **Column-change detection in the grouping loop:** We track the `previousColumn`. When `column != previousColumn`, we create a new inner list and add it to `result`. This linear pass is sufficient after sorting.

4. **Why `Integer.MIN_VALUE` as initial `previousColumn`:** The minimum possible integer ensures the first column is always treated as a "new" column, so a new inner list is created on the very first triple.

5. **Pre-order vs. post-order does not matter:** Because all triples are collected into one list and then sorted, the DFS traversal order does not affect correctness.

---

## Complexity Analysis

- **Time Complexity:** $O(N \log N)$ — DFS visits each of the $N$ nodes once in $O(N)$; sorting $N$ triples takes $O(N \log N)$; grouping is $O(N)$. Sorting dominates.

- **Space Complexity:** $O(N)$ — the `nodeList` stores one triple per node; the recursion stack is $O(H)$ where $H$ is the tree height.

---

## Similar Problems

Problems that use coordinate assignment + sorting on a tree or grid:

1. [314. Binary Tree Vertical Order Traversal](https://leetcode.com/problems/binary-tree-vertical-order-traversal/) — Simpler variant (no tie-breaking by value; BFS suffices).
2. [102. Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) — Groups nodes by row (depth) instead of column.
3. [103. Binary Tree Zigzag Level Order Traversal](https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/) — Level order with alternating direction; same grouping idea.
4. [116. Populating Next Right Pointers in Each Node](https://leetcode.com/problems/populating-next-right-pointers-in-each-node/) — Level-order traversal used to connect nodes in the same row.

**Why seemingly similar problems differ:**
- **[314. Binary Tree Vertical Order Traversal](https://leetcode.com/problems/binary-tree-vertical-order-traversal/)** uses BFS and **does not sort by value** when two nodes share the same position — making it strictly simpler than 987. A BFS solution for 987 would fail the tie-breaking requirement; sorting is mandatory here.
