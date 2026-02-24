### 19. Remove Nth Node From End of List
### Problem Link: [Remove Nth Node From End of List](https://leetcode.com/problems/remove-nth-node-from-end-of-list/)

### Problem Description
Given the `head` of a linked list, remove the `n`th node from the end of the list and return its head.

**Example 1:**
```
Input: head = [1,2,3,4,5], n = 2
Output: [1,2,3,5]
```

**Example 2:**
```
Input: head = [1], n = 1
Output: []
```

**Example 3:**
```
Input: head = [1,2], n = 1
Output: [1]
```

**Constraints:**
- The number of nodes in the list is `sz`.
- `1 <= sz <= 30`
- `0 <= Node.val <= 100`
- `1 <= n <= sz`

---

### Intuition / Main Idea

The problem asks us to remove the `n`th node from the *end* of a singly linked list. Since we can only traverse forward, a naive approach would be to do two passes: one to count the total length `L` of the list, and a second pass to find and remove the `(L - n)`th node from the beginning. 

However, we can do this in a **single pass** using the **Two Pointers (Fast & Slow)** technique.

#### Why the Intuition Works
Imagine two runners, Fast and Slow. If we want Slow to stop exactly `n` steps behind Fast when Fast reaches the finish line, we simply give Fast an `n`-step head start. 

By the time Fast reaches the very end of the list (meaning `fast == null`), Slow will be positioned exactly `n` nodes behind the end. However, to *remove* a node in a singly linked list, we need access to the node *immediately preceding* it. Therefore, we actually want Slow to stop exactly `n + 1` steps behind Fast. We achieve this by giving Fast an `n + 1` step head start.

#### How to Derive It Step by Step

1.  **Handling Edge Cases (The Dummy Node):** What if the list has 1 node and we want to remove the 1st node from the end? Or what if we need to remove the head of a longer list? Removing the head is always a tricky edge case because there is no "previous" node to reconnect.
    *   **Solution:** We introduce a `dummy` node that points to the actual `head`. Now, the head is just a regular node with a predecessor (`dummy`), completely eliminating the edge case. We will return `dummy.next` at the end.

2.  **Initializing Pointers:** We start both the `fast` and `slow` pointers at the `dummy` node.

3.  **Creating the Gap:** We need `slow` to end up pointing to the node *right before* the target node. So, we advance the `fast` pointer `n + 1` times. This establishes a gap of exactly `n` nodes between `slow` and `fast`.

4.  **Moving in Tandem:** We move both `fast` and `slow` pointers forward one step at a time until `fast` falls off the end of the list (`fast == null`). 

5.  **Removing the Node:** Because of the `n + 1` gap, `slow` is now sitting on the node immediately preceding the node to be deleted. We delete the target node by skipping it: `slow.next = slow.next.next`.

---

### Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| :--- | :--- |
| Handle edge cases (e.g., removing the head node) | `ListNode dummyNode = new ListNode(0); dummyNode.next = head;` |
| Initialize two pointers | `ListNode slowPointer = dummyNode; ListNode fastPointer = dummyNode;` |
| Give fast pointer an `n + 1` head start | `for (int i = 0; i <= n; i++) { fastPointer = fastPointer.next; }` |
| Traverse until fast reaches the end | `while (fastPointer != null) { ... }` |
| Move both pointers at the same speed | `slowPointer = slowPointer.next; fastPointer = fastPointer.next;` |
| Remove the `n`th node from the end | `slowPointer.next = slowPointer.next.next;` |
| Return the new head | `return dummyNode.next;` |

---

### LinkedList Problems - Pointer & Loop Explanations

*   **Reason behind `dummyNode` initialization:** Initialized to point to the `head` to handle cases where the node to be removed is the `head` itself. It guarantees that every node in the original list has a predecessor, simplifying the deletion logic.
*   **Reason behind `slowPointer` and `fastPointer` initialization:** Both start at `dummyNode`. They act as a measuring stick. `fastPointer` goes ahead to find the end, and `slowPointer` trails behind at a fixed distance to find the target.
*   **Loop Condition `for (int i = 0; i <= n; i++)`:** This creates the exact gap needed. We loop `n + 1` times because we want `slowPointer` to stop exactly one node *before* the target node to perform the deletion.
*   **Loop Condition `while (fastPointer != null)`:** This correctly identifies when `fastPointer` has traversed the entire list and fallen off the end. Since the gap is strictly maintained, when this condition is met, `slowPointer` is guaranteed to be in the correct position.

---

### Final Java Code & Learning Pattern (Full Content)

```java
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
        // Create a dummy node that points to the head. 
        // This handles edge cases like deleting the only node in the list or the head node.
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;
        
        // Initialize two pointers, both starting at the dummy node.
        ListNode slowPointer = dummyNode;
        ListNode fastPointer = dummyNode;
        
        // Advance the fastPointer n + 1 steps ahead.
        // We do n + 1 so that the slowPointer ends up one node BEFORE the node we want to delete.
        for (int i = 0; i <= n; i++) {
            fastPointer = fastPointer.next;
        }
        
        // Move both pointers forward at the same speed.
        // When fastPointer reaches null (the end of the list), slowPointer will be right before the target.
        while (fastPointer != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next;
        }
        
        // Skip the nth node from the end by changing the next pointer of the slowPointer.
        slowPointer.next = slowPointer.next.next;
        
        // Return the actual head of the modified list, which is the node after dummyNode.
        return dummyNode.next;
    }
}
```

---

### Complexity Analysis

- **Time Complexity:** $O(L)$ where $L$ is the number of nodes in the linked list. We make exactly one pass through the list using the fast pointer.
- **Space Complexity:** $O(1)$ because we only use a constant amount of extra space (the `dummyNode`, `slowPointer`, and `fastPointer`).

---

### Similar Problems

- [61. Rotate List](https://leetcode.com/problems/rotate-list/) - Uses a similar approach of finding the length and navigating to a specific node from the end to break and reconnect the list.
- [876. Middle of the Linked List](https://leetcode.com/problems/middle-of-the-linked-list/) - Also uses the fast and slow pointer technique to find a specific relative position (the middle) in a single pass.
- [141. Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/) - Uses fast and slow pointers, though moving at different speeds, which is a variation of the pointer-gap concept.
- [160. Intersection of Two Linked Lists](https://leetcode.com/problems/intersection-of-two-linked-lists/) - Uses two pointers to compensate for length differences between two lists, similar to how we create a gap here.