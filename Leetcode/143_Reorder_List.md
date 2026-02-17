# Reorder List

## Problem Description

**Problem Link:** [Reorder List](https://leetcode.com/problems/reorder-list/)

You are given the head of a singly linked list `L0 -> L1 -> ... -> Ln`.

Reorder the list to become:

`L0 -> Ln -> L1 -> Ln-1 -> L2 -> Ln-2 -> ...`

You **must do this in-place** (i.e., modify the list nodes’ pointers). You may not change the values in the nodes.

**Example 1:**
```
Input:  1 -> 2 -> 3 -> 4
Output: 1 -> 4 -> 2 -> 3
```

**Example 2:**
```
Input:  1 -> 2 -> 3 -> 4 -> 5
Output: 1 -> 5 -> 2 -> 4 -> 3
```

## Intuition/Main Idea

### Build the intuition (mentor-style)

The target ordering is “take one from the front, then one from the back, repeat”.

In an array, grabbing the back is easy (`nums[n - 1]`), but in a singly linked list, we cannot walk backward.

So we need a trick to access the “back half” in forward direction.

The standard linked-list pattern here is:

1. **Find the middle** of the list.
2. **Reverse the second half**.
3. **Merge the two halves alternatingly**.

After reversal, the original “back” nodes become the “front” of the reversed second half, so we can interleave nodes in one forward pass.

### Why the intuition works

Let the list be split as:

- First half: `L0 -> L1 -> ... -> Lk`
- Second half: `Lk+1 -> ... -> Ln`

If we reverse the second half, it becomes:

- Reversed second: `Ln -> Ln-1 -> ... -> Lk+1`

Now, if we alternate nodes:

- Take one from first half (`L0`), then one from reversed second (`Ln`), then `L1`, then `Ln-1`, ...

This matches exactly the required ordering.

### LinkedList pointers & loop conditions (what to pay attention to)

#### Pointer initializations (and why each exists)

- `slow` and `fast`: used to find the middle in **one pass**.
- `secondHalfHead`: the head of the reversed second half, so we can merge from the back-forward order.
- `firstPointer` and `secondPointer`: two runners during merge (one on each half).
- Temporary `nextFirst` / `nextSecond`: because we are about to rewire `next` pointers, we must save where to continue.

#### Loop condition choices

- Middle finding loop: `while (fast.next != null && fast.next.next != null)`
  - This stops `slow` at the “end of the first half”.
  - It also avoids null pointer access when jumping `fast` by 2.
- Merge loop: `while (secondPointer != null)`
  - The second half is the shorter or equal half.
  - Once second half is fully merged, the remaining first-half node (only possible when odd length) is already in correct place.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Reorder in-place (no extra nodes) | Only pointer rewiring; no new nodes allocated (entire code) |
| Must follow `L0, Ln, L1, Ln-1, ...` pattern | Reverse second half then alternate merge (lines 18-63) |
| Handle small lists safely | Early return when `head == null` or `head.next == null` (lines 10-13) |
| Avoid cycles / preserve list termination | Cut at middle (`slow.next = null`) and careful merge wiring (lines 29-63) |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public void reorderList(ListNode head) {
        // Edge cases:
        // - empty list: nothing to reorder
        // - single node: already in required order
        if (head == null || head.next == null) {
            return;
        }

        // ------------------------------------------------------------
        // Step 1) Find the middle of the list using slow/fast pointers.
        // ------------------------------------------------------------
        // Why we need two pointers:
        // - slow moves 1 step, fast moves 2 steps
        // - when fast reaches the end, slow is at the middle
        ListNode slow = head;
        ListNode fast = head;

        // Loop condition reasoning:
        // - We check fast.next and fast.next.next because fast moves by 2.
        // - This places slow at the end of the first half for both even/odd lengths.
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // ------------------------------------------------------------
        // Step 2) Reverse the second half.
        // ------------------------------------------------------------
        // secondHalfStart is the first node of the second half.
        // We will reverse it so the last node becomes accessible in forward direction.
        ListNode secondHalfStart = slow.next;

        // Important to cut the list here:
        // If we don't set slow.next = null, merging can accidentally create cycles.
        slow.next = null;

        ListNode secondHalfHead = reverse(secondHalfStart);

        // ------------------------------------------------------------
        // Step 3) Merge alternating nodes: first half, reversed second half.
        // ------------------------------------------------------------
        ListNode firstPointer = head;
        ListNode secondPointer = secondHalfHead;

        // Merge loop condition reasoning:
        // - second half is shorter or equal.
        // - once second half is exhausted, the remaining first node (only in odd length)
        //   is already in correct final position.
        while (secondPointer != null) {
            // Save next pointers before rewiring.
            ListNode nextFirst = firstPointer.next;
            ListNode nextSecond = secondPointer.next;

            // Place one node from second half after one node from first half.
            firstPointer.next = secondPointer;
            secondPointer.next = nextFirst;

            // Advance both pointers.
            firstPointer = nextFirst;
            secondPointer = nextSecond;
        }
    }

    private ListNode reverse(ListNode head) {
        // Standard iterative reversal.
        // Pointer initialization reasoning:
        // - prev starts as null because the new tail must point to null.
        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            // Save where we would go next before changing current.next.
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        // prev becomes the new head of the reversed list.
        return prev;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$

- Find middle: $O(n)$
- Reverse second half: $O(n)$
- Merge: $O(n)$

**Space Complexity:** $O(1)$

- We only use a constant number of pointers.

## Similar Problems

- [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/)
- [Palindrome Linked List](https://leetcode.com/problems/palindrome-linked-list/)
- [Middle of the Linked List](https://leetcode.com/problems/middle-of-the-linked-list/)
