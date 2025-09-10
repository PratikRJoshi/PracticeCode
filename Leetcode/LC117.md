### 117. Populating Next Right Pointers in Each Node II
Problem: https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/description/

## Problem Statement

Given a binary tree, populate each next pointer to point to its next right node. **If there is no next right node, the next pointer should be set to `null`.**

*   The tree is **not** perfect; nodes can have 0, 1 or 2 children.
*   You must connect the pointers **in-place** (O(1) extra space is expected for the core algorithm).
*   Return the root of the tree after wiring all pointers.

This is LeetCode 117 and it is the follow-up to LeetCode 116, which asked the same thing for a *perfect* binary tree.

---

## Mapping Requirements → Code

| Requirement | Where it appears in code |
|-------------|-------------------------|
| Traverse every level from **left → right** | `while (curr != null)` loop (lines **20-32**) |
| Build links for the **next level** | Dummy node `nextLevelDummy` + pointer `tail` (lines **22-29**) |
| **Skip null children** (tree is not perfect) | `if (curr.left != null)` and `if (curr.right != null)` (lines **23-28**) |
| Advance to next sibling in current level | `curr = curr.next;` (line **31**) |
| Move down to start of next level | `curr = nextLevelDummy.next;` (line **33**) |
| Reset dummy for following level | `nextLevelDummy.next = null; tail = nextLevelDummy;` (lines **34-35**) |
| **Return** wired root | final `return root;` (line **37**) |

> The structure is *almost identical* to the in-place O(1) solution for LC116.  The only difference is the two null checks because a node might be missing a left or right child in this more general problem.

---

## Intuition (Same Pattern as LC116)

LC116 works because in a **perfect** tree every parent has two children, so we could confidently link `node.left.next = node.right` and `node.right.next = node.next.left`.

For an *arbitrary* tree that assumption breaks.  The trick is to **walk the current level using already-established `next` pointers while splicing together the nodes of the next level with a temporary dummy head.**

1.  Keep a pointer `curr` that walks the *current* level.
2.  Use a **dummy node** `nextLevelDummy` and a moving pointer `tail` to stitch together the *next* level.
3.  For every child that exists (`left`, `right`) → attach it to `tail.next` and advance `tail`.
4.  When `curr` runs out (reaches `null`), slide down one level: `curr = nextLevelDummy.next`.
5.  Reset `tail` back to `nextLevelDummy` and clear `nextLevelDummy.next` so it can be reused.

Because we only use a handful of pointers, the algorithm stays **O(1) space**, just like LC116.

---

## Java Implementation (LC116 pattern with null checks)

```java
/*
 *  Node definition comes from LeetCode:
 *  class Node {
 *      public int val;
 *      public Node left;
 *      public Node right;
 *      public Node next;
 *      public Node(int _val) { val = _val; }
 *  }
 */
class Solution {
    public Node connect(Node root) {
        if (root == null) return null;

        Node curr = root;                // start node of current level
        Node nextLevelDummy = new Node(0); // dummy head for next level
        Node tail = nextLevelDummy;      // tail pointer to build next level list

        while (curr != null) {
            // 1. Stitch children of `curr` (if they exist) to the next-level list
            if (curr.left != null) {
                tail.next = curr.left;
                tail = tail.next;
            }
            if (curr.right != null) {
                tail.next = curr.right;
                tail = tail.next;
            }

            // 2. Advance horizontally within the current level
            curr = curr.next;

            // 3. If we finished the level, move down one level
            if (curr == null) {
                curr = nextLevelDummy.next; // first node of next level
                nextLevelDummy.next = null; // reset for the following pass
                tail = nextLevelDummy;      // reset tail
            }
        }

        return root;
    }
}
```

---

## Complexity Analysis

* **Time Complexity:** `O(N)` — each node is visited exactly once.
* **Space Complexity:** `O(1)` — the algorithm only uses a few pointers irrespective of tree size (recursion avoided; queue avoided).

---

## Why This Mirrors LC116

| Aspect | LC116 (Perfect Tree) | LC117 (General Tree) |
|--------|----------------------|-----------------------|
| Walk current level with `next` | ✅ | ✅ |
| Use constant-space pointers | ✅ | ✅ |
| Dummy node for next level | ❌ (not needed) | ✅ (handles missing children) |
| Null-checks on children | Minimal | Required |

By adding just **two null checks** and the **dummy head technique**, we upgrade the LC116 pattern to solve the generalized problem without losing the elegant O(1) extra-space guarantee.
