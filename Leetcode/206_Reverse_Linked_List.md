### 206. Reverse Linked List
### Problem Link: [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/)
### Intuition
This problem asks us to reverse a singly linked list. The key insight is to iterate through the list and change the direction of each pointer. We need to keep track of the previous node so that we can update the next pointer of the current node to point to it.

This is a fundamental linked list operation that is often used as a building block for more complex algorithms. It can be implemented both iteratively and recursively.

### Java Reference Implementation (Iterative)
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
    public ListNode reverseList(ListNode head) {
        ListNode prev = null; // [R0] Initialize previous node as null
        ListNode curr = head; // [R1] Start with the head node
        
        // [R2] Iterate through the list
        while (curr != null) {
            // [R3] Save the next node before changing the pointer
            ListNode nextTemp = curr.next;
            
            // [R4] Reverse the pointer to point to the previous node
            curr.next = prev;
            
            // [R5] Move prev and curr pointers one step forward
            prev = curr;
            curr = nextTemp;
        }
        
        return prev; // [R6] prev is the new head of the reversed list
    }
}
```

### Alternative Implementation (Recursive)
```java
class Solution {
    public ListNode reverseList(ListNode head) {
        // Base case: empty list or single node
        if (head == null || head.next == null) {
            return head;
        }
        
        // Recursively reverse the rest of the list
        ListNode reversedListHead = reverseList(head.next);
        
        // Reverse the pointer between head and head.next
        head.next.next = head;
        head.next = null;
        
        return reversedListHead;
    }
}
```

### Understanding the Algorithm and Linked List Operations

1. **Iterative Approach:**
   - We maintain three pointers: prev, curr, and nextTemp
   - For each node, we:
     - Save the next node (nextTemp = curr.next)
     - Reverse the current node's pointer (curr.next = prev)
     - Move prev and curr pointers one step forward
   - When curr becomes null, prev points to the new head of the reversed list

2. **Recursive Approach:**
   - Base case: If the list is empty or has only one node, return the head
   - Recursive step: Reverse the rest of the list starting from head.next
   - After the recursion returns, head.next is the last node of the reversed list
   - We make head.next.next point to head, effectively reversing the pointer
   - We set head.next to null to avoid cycles
   - The new head of the reversed list is returned up the recursion chain

3. **Visualization of the Iterative Process:**
   - Initial state: null <- 1 -> 2 -> 3 -> 4 -> 5
   - After first iteration: null <- 1    2 -> 3 -> 4 -> 5
   - After second iteration: null <- 1 <- 2    3 -> 4 -> 5
   - And so on until: null <- 1 <- 2 <- 3 <- 4 <- 5

4. **Edge Cases:**
   - Empty list: Return null
   - Single node: Return the node itself (no change needed)

### Requirement → Code Mapping
- **R0 (Initialize prev)**: `ListNode prev = null;` - Start with prev pointing to null
- **R1 (Initialize curr)**: `ListNode curr = head;` - Start with curr pointing to the head
- **R2 (Iterate through list)**: `while (curr != null)` - Process each node in the list
- **R3 (Save next node)**: `ListNode nextTemp = curr.next;` - Save the next node before changing pointers
- **R4 (Reverse pointer)**: `curr.next = prev;` - Make current node point to the previous node
- **R5 (Move pointers)**: `prev = curr; curr = nextTemp;` - Move both pointers one step forward
- **R6 (Return new head)**: `return prev;` - Return the new head of the reversed list

### Complexity Analysis
- **Time Complexity**: O(n)
  - We visit each node exactly once
  - Each operation inside the loop takes constant time
  - Overall: O(n)

- **Space Complexity**: 
  - Iterative approach: O(1) - We use only a constant amount of extra space
  - Recursive approach: O(n) - The recursion stack can go as deep as the number of nodes

### Related Problems
- **Reverse Linked List II** (Problem 92): Reverse a specific portion of a linked list
- **Palindrome Linked List** (Problem 234): Check if a linked list is a palindrome
- **Reorder List** (Problem 143): Reorder a list in a specific pattern
