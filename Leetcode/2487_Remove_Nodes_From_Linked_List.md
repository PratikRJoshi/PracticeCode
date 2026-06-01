# 2487. Remove Nodes From Linked List

## Problem Description

[Remove Nodes From Linked List](https://leetcode.com/problems/remove-nodes-from-linked-list/)

You are given the `head` of a linked list. Remove every node which has a node with a **greater value** anywhere to the **right** side of it. Return the `head` of the modified linked list.

### Example 1

Input: `head = [5,2,13,3,8]`

Output: `[13,8]`

Explanation: Nodes `5`, `2`, and `3` are removed because `13` (or `8`) sits to their right with a greater value.

### Example 2

Input: `head = [1,1,1,1]`

Output: `[1,1,1,1]`

Explanation: Every node has value `1`, so none has a strictly greater node to its right.

### Constraints

- The number of nodes is in the range `[1, 10^5]`.
- `1 <= Node.val <= 10^5`.

## Intuition / Main Idea

A node survives **iff no node to its right is greater**. That means the surviving nodes, read left to right, form a **non-increasing** sequence (each kept node is ≥ everything after it).

### Build the intuition step by step

1. Imagine scanning left to right and keeping a stack of "candidates that might survive".
2. When a new node arrives with value `v`, any candidate currently on the stack whose value is `< v` now has a greater node to its right (namely the new node) — so it must be removed. Pop all such candidates.
3. Push the new node. The stack stays **non-increasing** from bottom to top.
4. After the scan, the stack holds exactly the survivors, in order. Rebuild the list from it.

### Why this works

The monotonic-stack invariant (non-increasing bottom→top) is exactly the survivor condition. The crucial detail is the **pop condition**: pop only while `stack.peek().val < v`, not the whole stack — popping everything would wrongly discard larger earlier nodes.

A second subtlety: **never rewire `next` pointers during the scan**, because a node you linked may get popped later, leaving a stale link. Build the result list only after the scan completes.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Keep nodes with no greater node to the right | Monotonic non-increasing stack |
| Remove a smaller node when a greater one appears | `while (!stack.isEmpty() && stack.peek().val < head.val) stack.pop();` |
| Produce a valid linked list | Rebuild from stack after the scan, terminating the tail with `null` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Monotonic Stack over a Linked List]
class Solution {
    public ListNode removeNodes(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        java.util.Deque<ListNode> stack = new java.util.ArrayDeque<>();

        while (head != null) {
            // Pop every candidate that now has a greater node to its right.
            while (!stack.isEmpty() && stack.peek().val < head.val) {
                stack.pop();
            }
            stack.push(head);
            head = head.next;
        }

        // Rebuild the list from the stack. Top of stack is the tail.
        // Start from null so the last real node terminates the list.
        ListNode result = null;
        while (!stack.isEmpty()) {
            ListNode node = stack.pop();
            node.next = result;
            result = node;
        }
        return result;
    }
}
```

### Why each part exists

- **`while (... peek().val < head.val) pop()`** — the heart of the algorithm; maintains the non-increasing invariant. Popping the entire stack is the classic bug.
- **Single `head = head.next` after the branches** — every node is visited once.
- **Rebuild starting from `result = null`** — initializing to a `new ListNode()` instead would leave a trailing `0`; `null` makes the last popped node a proper tail.

## Complexity Analysis

- **Time Complexity:** $O(n)$ — each node is pushed and popped at most once, so the nested `while` is amortized $O(1)$ per node (it does **not** make this $O(n^2)$).
- **Space Complexity:** $O(n)$ for the stack.

## LinkedList Notes

- **`result` pointer**: accumulates the rebuilt list tail-first; each popped node points to the previously built suffix.
- **Loop condition `while (head != null)`**: a single forward pass; the inner pop loop is bounded by total pushes, keeping it linear.
- **O(1)-space alternative**: reverse the list, sweep keeping a running max (drop anything below it), then reverse back. Or recurse, returning the max seen to the right.

## Similar Problems

1. [LeetCode 237. Delete Node in a Linked List](https://leetcode.com/problems/delete-node-in-a-linked-list/) — pointer manipulation without the head.
2. [LeetCode 739. Daily Temperatures](https://leetcode.com/problems/daily-temperatures/) — canonical monotonic stack on an array.
3. [LeetCode 1019. Next Greater Node In Linked List](https://leetcode.com/problems/next-greater-node-in-linked-list/) — monotonic stack directly on a linked list.
