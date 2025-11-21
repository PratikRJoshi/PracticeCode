# Remove Linked List Elements

## Problem Description

**Problem Link:** [Remove Linked List Elements](https://leetcode.com/problems/remove-linked-list-elements/)

Given the `head` of a linked list and an integer `val`, remove all the nodes of the linked list that have `Node.val == val`, and return *the new head*.

**Example 1:**
```
Input: head = [1,2,6,3,4,5,6], val = 6
Output: [1,2,3,4,5]
```

**Example 2:**
```
Input: head = [], val = 1
Output: []
```

**Example 3:**
```
Input: head = [7,7,7,7], val = 7
Output: []
```

**Constraints:**
- The number of nodes in the list is in the range `[0, 10^4]`.
- `1 <= Node.val <= 50`
- `0 <= val <= 50`

## Intuition/Main Idea

We need to remove all nodes with a given value. The challenge is handling the head node if it needs to be removed.

**Core Algorithm:**
- Use a dummy node to simplify head removal
- Traverse the list, removing nodes with target value
- Update pointers to skip removed nodes

**Why dummy node:** It simplifies the code by ensuring we always have a previous node, even when the head needs to be removed.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Handle head removal | Dummy node - Lines 5-6 |
| Traverse list | While loop - Line 9 |
| Remove matching nodes | Pointer update - Line 12 |
| Skip non-matching nodes | Pointer advance - Line 15 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        // Create a dummy node to simplify head removal
        // Dummy's next points to head
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Use current pointer starting from dummy
        ListNode current = dummy;
        
        // Traverse the list
        while (current.next != null) {
            // If next node has target value, remove it
            if (current.next.val == val) {
                // Skip the next node by pointing to next.next
                current.next = current.next.next;
            } else {
                // Move to next node
                current = current.next;
            }
        }
        
        // Return the new head (dummy.next)
        return dummy.next;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the number of nodes. We traverse the list once.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Delete Node in a Linked List](https://leetcode.com/problems/delete-node-in-a-linked-list/) - Delete specific node
- [Remove Duplicates from Sorted List](https://leetcode.com/problems/remove-duplicates-from-sorted-list/) - Remove duplicates
- [Remove Nth Node From End](https://leetcode.com/problems/remove-nth-node-from-end-of-list/) - Remove at specific position

