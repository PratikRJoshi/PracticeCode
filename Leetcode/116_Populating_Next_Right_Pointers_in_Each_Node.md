# Populating Next Right Pointers in Each Node

## Problem Description

**Problem Link:** [Populating Next Right Pointers in Each Node](https://leetcode.com/problems/populating-next-right-pointers-in-each-node/)

You are given a **perfect binary tree** where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:

```
struct Node {
    int val;
    Node *left;
    Node *right;
    Node *next;
}
```

Populate each `next` pointer to point to its **next right node**. If there is no next right node, the `next` pointer should be set to `NULL`.

Initially, all `next` pointers are set to `NULL`.

**Follow up:** You may only use **constant extra space**. Recursive approach is fine — implicit stack space does not count.

**Example 1:**
```
Input:  root = [1,2,3,4,5,6,7]

Before:          After:
      1               1 → NULL
     / \             / \
    2   3           2 → 3 → NULL
   / \ / \         / \ / \
  4  5 6  7       4→5→6→7→NULL

Output: [1,#,2,3,#,4,5,6,7,#]
Explanation: '#' signifies the end of each level (next == NULL).
```

**Constraints:**
- The number of nodes in the tree is less than `4096`.
- `-1000 <= Node.val <= 1000`
- The tree is a **perfect binary tree** (every parent has exactly two children; all leaves at the same depth).

---

## Intuition/Main Idea

### Building the Intuition Incrementally

**Step 1 — What does the `next` pointer mean?**
It is a horizontal link connecting siblings and cousins at the same depth. After the algorithm runs, every node's `next` points to the node immediately to its right on the same level.

**Step 2 — Approach 1: BFS (Level-Order Traversal) — intuitive but $O(N)$ space.**
Process nodes level by level. For every node at position `i` within a level, set `node.next = queue.peek()` (the next node to be dequeued from the same level). The last node in each level gets `next = null` naturally.

This is correct and simple, but uses a queue that can hold up to $N/2$ nodes — $O(N)$ space.

**Step 3 — Approach 2: Using previously established `next` pointers — $O(1)$ space.**
The key observation: **once we wire the `next` pointers of a level, we can use them to traverse that level without a queue**, and simultaneously wire the `next` pointers of the level below.

Think of each level as a linked list after we finish wiring it. The next level's nodes are all children of this linked list. We can traverse the current level's linked list using `head = head.next` and wire the children as we go.

**Why this works — the perfect binary tree guarantee:**
- Every node has exactly two children (until the leaf level).
- So we always have both `head.left` and `head.right` available to wire.
- `head.left.next = head.right` connects the two children of the same parent.
- `head.right.next = head.next.left` connects the right child of one parent to the left child of the next parent (the "across-parent" link). This is only possible because `head.next` is already wired.

**Step 4 — Outer loop: move down levels.**
`leftmost` always points to the leftmost node of the current level. We go to the next level by setting `leftmost = leftmost.left`. We stop when `leftmost.left == null` (we've reached the leaf level — no children to wire).

**Step 5 — Inner loop: traverse current level and wire children.**
`head` starts at `leftmost` and walks right via `head = head.next` until `head == null` (end of level).

### Helper Function Analysis (Tree Problem Section)

**Why a helper function is NOT required:**
Both approaches are fully iterative. The $O(1)$-space approach uses two nested while loops. No recursive decomposition is needed.

**Why a global variable is NOT required:**
All state (`leftmost`, `head`) is tracked with local pointer variables that move across the already-wired level.

**What is calculated at the current node (`head`):**
Two `next` pointer assignments:
1. `head.left.next = head.right` — intra-parent link.
2. `head.right.next = head.next.left` — inter-parent link (only if `head.next != null`).

**What is returned to the parent:**
Nothing. The wiring is done in-place. The return value of `connect` is just `root`.

**Whether to recurse into children before or after current node calculation:**
Not applicable (iterative). The **outer loop descends** only after the inner loop has fully wired the current level. We process the current level first, then descend — analogous to post-processing the current level before moving to the next.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|--------------------------|-------------------------------------|
| Wire next pointers level by level | Outer `while (leftmost.left != null)` loop |
| Move `head` across current level | `head = head.next` in inner while loop |
| Connect two children of same parent | `head.left.next = head.right` |
| Connect right child to left child of next parent | `head.right.next = head.next.left` (guarded by `head.next != null`) |
| Descend to next level | `leftmost = leftmost.left` after inner loop |
| Handle null root | `if (root == null) return root` guard |
| BFS approach (O(N) space alternative) | `connect_BFS` method using queue |

---

## Final Java Code & Learning Pattern

### Approach 1: BFS — $O(N)$ Space

```java
class Solution {

    public Node connect(Node root) {
        if (root == null) {
            return root;
        }

        // Queue holds nodes level by level
        Queue<Node> bfsQueue = new LinkedList<>();
        bfsQueue.add(root);

        while (!bfsQueue.isEmpty()) {
            // Snapshot the number of nodes at the current level
            int levelSize = bfsQueue.size();

            for (int positionInLevel = 0; positionInLevel < levelSize; positionInLevel++) {
                Node currentNode = bfsQueue.poll();

                // Wire next to the node immediately ahead in the queue
                // (which is the next node on the same level)
                // The last node in the level keeps next == null
                if (positionInLevel < levelSize - 1) {
                    currentNode.next = bfsQueue.peek();
                }

                // Enqueue children for the next level
                if (currentNode.left != null) {
                    bfsQueue.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    bfsQueue.add(currentNode.right);
                }
            }
        }

        return root;
    }
}
```

**Key points for BFS approach:**
- `levelSize` is captured before the inner loop so we know exactly how many nodes belong to the current level — this is the standard BFS level-snapshot pattern.
- `bfsQueue.peek()` after a `poll()` gives us the next node in the same level without removing it, allowing the `next` pointer assignment.
- The last node in each level (`positionInLevel == levelSize - 1`) does not get its `next` set, so it stays `null` as required.

---

### Approach 2: Using Previously Established `next` Pointers — $O(1)$ Space

```java
class Solution {

    public Node connect(Node root) {
        if (root == null) {
            return root;
        }

        // leftmost always points to the first (leftmost) node of the current level
        // We use it to descend one level after fully wiring the current level
        Node leftmost = root;

        // Continue as long as the current level has children (not leaf level)
        while (leftmost.left != null) {

            // head traverses the current level using already-wired next pointers
            Node head = leftmost;

            while (head != null) {

                // Link 1: Connect the two children of the same parent
                // (intra-parent link — always valid since this is a perfect binary tree)
                head.left.next = head.right;

                // Link 2: Connect the right child of this parent to the left child
                // of the NEXT parent on the same level
                // (inter-parent link — only possible because head.next is already wired)
                if (head.next != null) {
                    head.right.next = head.next.left;
                }

                // Move to the next parent on this level using the wired next pointer
                head = head.next;
            }

            // Descend to the leftmost node of the next level
            leftmost = leftmost.left;
        }

        return root;
    }
}
```

**Explanation of Key Code Sections:**

1. **Why `leftmost.left != null` as outer loop condition:**
   We need to wire the children of the current level. When `leftmost.left == null`, we are at the leaf level — there are no children to wire, so we stop. The loop runs for every non-leaf level.

2. **Why `head != null` as inner loop condition:**
   `head` walks rightward across the current level using `next` pointers. When `head == null`, we've processed every parent on this level (the last node's `next` is `null`).

3. **Why `head.left.next = head.right` is always safe:**
   The perfect binary tree guarantee ensures every non-leaf node has both `left` and `right`. No null checks needed.

4. **Why `head.right.next = head.next.left` requires a null guard:**
   The rightmost node on each level has `head.next == null` (no parent to the right). Accessing `head.next.left` without the guard would throw `NullPointerException`.

5. **Why `leftmost = leftmost.left` descends correctly:**
   After the inner loop, all `next` pointers at the next level are wired. `leftmost.left` is the leftmost node of that next level. We can now use `head.next` traversal on it in the next outer loop iteration.

6. **Why $O(1)$ space:** No queue, no recursion stack beyond the constant number of pointer variables (`leftmost`, `head`). The `next` pointers of the tree itself serve as the traversal structure.

### When to use BFS vs. $O(1)$ approach

| | **BFS Approach** | **$O(1)$ Space Approach** |
|---|---|---|
| Space | $O(N)$ — queue | $O(1)$ — pointer variables only |
| Applicability | Any binary tree (perfect or not) | **Only** perfect binary trees (relies on every node having two children) |
| Code complexity | Simpler, easier to reason about | More elegant, but requires careful pointer management |
| Use when | Asked about general trees (e.g., Problem 117) | Follow-up asks for constant space with a perfect tree |

---

## Complexity Analysis

**BFS Approach:**
- **Time Complexity:** $O(N)$ — each node is enqueued and dequeued exactly once.
- **Space Complexity:** $O(N)$ — the queue holds up to $N/2$ nodes (the last level of a perfect binary tree).

**$O(1)$ Space Approach:**
- **Time Complexity:** $O(N)$ — each node is visited once during the inner loop traversal across its level.
- **Space Complexity:** $O(1)$ — only two pointer variables (`leftmost`, `head`). Implicit recursion stack space is excluded per the problem's follow-up note.

---

## Similar Problems

1. [117. Populating Next Right Pointers in Each Node II](https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/) — Harder generalization: the tree is **not** perfect (nodes may have zero or one child). The $O(1)$ two-pointer approach must be adapted because `head.left` and `head.right` may be `null`. The BFS approach works unchanged.
2. [102. Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) — Same BFS level-snapshot pattern; instead of wiring `next` pointers, it collects values into lists. The `levelSize` snapshot trick is identical.
3. [314. Binary Tree Vertical Order Traversal](https://leetcode.com/problems/binary-tree-vertical-order-traversal/) — BFS with column tracking; same level-order foundation.
4. [199. Binary Tree Right Side View](https://leetcode.com/problems/binary-tree-right-side-view/) — Level-order BFS; records only the last node per level. Same BFS level-snapshot structure.

**Why seemingly similar problems differ:**
- **[117. Populating Next Right Pointers II](https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/)** breaks the $O(1)$ approach used here because nodes may have one or zero children. The `head.left.next = head.right` line assumes both children always exist — an assumption that only holds for perfect binary trees.
