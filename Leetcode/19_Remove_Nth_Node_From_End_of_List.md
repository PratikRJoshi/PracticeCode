### 19. Remove Nth Node From End of List
### [Problem Link](https://leetcode.com/problems/remove-nth-node-from-end-of-list/)

```java
import java.util.*;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head); // [R0]
        ListNode slow = dummy; // [R1]
        ListNode fast = dummy; // [R1]

        // Move fast pointer n+1 steps ahead.
        // This creates a gap of n nodes between slow and fast.
        for (int i = 0; i <= n; i++) { // [R2]
            fast = fast.next;
        }

        // Move both pointers until fast reaches the end of the list.
        // When fast is null, slow will be at the node just before the one to be removed.
        while (fast != null) { // [R3]
            slow = slow.next;
            fast = fast.next;
        }

        // Remove the nth node from the end.
        slow.next = slow.next.next; // [R4]

        return dummy.next; // [R5]
    }
}
```
### Intuition
The problem can be solved in a single pass using two pointers, `slow` and `fast`. The key is to create a gap of `n` nodes between them.

1.  **Dummy Node**: We start by creating a `dummy` node that points to the `head`. This simplifies edge cases, such as removing the first node of the list.
2.  **Pointer Initialization**: Both `slow` and `fast` pointers start at the `dummy` node.
3.  **Create the Gap**: We first advance the `fast` pointer `n + 1` steps. The `+1` is crucial because it ensures that when `fast` reaches the end, `slow` will be positioned right *before* the node we want to delete.
4.  **Move in Tandem**: After establishing the gap, we move both `slow` and `fast` one step at a time until `fast` becomes `null`.
5.  **Deletion**: At this point, `slow` is pointing to the node just before the target node. We can then remove the target by updating `slow.next` to `slow.next.next`.
6.  **Return**: Finally, we return `dummy.next`, which is the head of the potentially modified list.

### Requirement → Code Mapping
- **R0 (Create Dummy Node)**: `ListNode dummy = new ListNode(0, head);` handles edge cases like removing the head.
- **R1 (Initialize Pointers)**: `ListNode slow = dummy; ListNode fast = dummy;` sets both pointers to the same starting position.
- **R2 (Create Gap)**: The `for` loop advances `fast` by `n+1` nodes to create the necessary distance from `slow`.
- **R3 (Move Pointers Together)**: `while (fast != null)` advances both pointers until `fast` reaches the end, positioning `slow` correctly.
- **R4 (Perform Deletion)**: `slow.next = slow.next.next;` bypasses the target node, effectively removing it.
- **R5 (Return Head)**: `return dummy.next;` returns the head of the modified list.

### Complexity
- **Time Complexity**: `O(L)`, where `L` is the number of nodes in the list. We traverse the list at most twice (once to create the gap, once to move in tandem), but it simplifies to a single pass.
- **Space Complexity**: `O(1)`, as we only use a few extra pointers.