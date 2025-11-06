### 143. Reorder List
### Problem Link: [Reorder List](https://leetcode.com/problems/reorder-list/)
### Intuition
This problem asks us to reorder a linked list such that the new order is: L₀ → Lₙ → L₁ → Lₙ₋₁ → L₂ → Lₙ₋₂ → ... where the original list is L₀ → L₁ → ... → Lₙ.

The key insight is to break this problem into three steps:
1. Find the middle of the linked list
2. Reverse the second half of the linked list
3. Merge the two halves by alternating nodes

This approach allows us to solve the problem in O(n) time and O(1) space without using any extra data structures.

### Java Reference Implementation
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
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) { // [R0] Handle edge cases
            return;
        }
        
        // [R1] Step 1: Find the middle of the linked list
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // [R2] Step 2: Reverse the second half of the linked list
        ListNode secondHalf = reverseList(slow.next);
        slow.next = null; // Break the list into two halves
        
        // [R3] Step 3: Merge the two halves by alternating nodes
        mergeAlternating(head, secondHalf);
    }
    
    // [R4] Helper method to reverse a linked list
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        
        return prev;
    }
    
    // [R5] Helper method to merge two lists by alternating nodes
    private void mergeAlternating(ListNode list1, ListNode list2) {
        while (list1 != null && list2 != null) {
            ListNode list1Next = list1.next;
            ListNode list2Next = list2.next;
            
            list1.next = list2;
            list2.next = list1Next;
            
            list1 = list1Next;
            list2 = list2Next;
        }
    }
}
```

### Alternative Implementation (Using a Stack)
```java
class Solution {
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        
        // Use a stack to store all nodes
        Stack<ListNode> stack = new Stack<>();
        ListNode curr = head;
        while (curr != null) {
            stack.push(curr);
            curr = curr.next;
        }
        
        int count = stack.size();
        curr = head;
        
        // Reorder the list
        for (int i = 0; i < count / 2; i++) {
            ListNode nextNode = curr.next;
            ListNode lastNode = stack.pop();
            
            curr.next = lastNode;
            lastNode.next = nextNode;
            
            curr = nextNode;
        }
        
        // Set the last node's next to null to avoid cycles
        curr.next = null;
    }
}
```

### Understanding the Algorithm and Linked List Operations

1. **Finding the Middle of the List:**
   - We use the slow and fast pointer technique (also known as the tortoise and hare algorithm)
   - The slow pointer moves one step at a time, while the fast pointer moves two steps
   - When the fast pointer reaches the end, the slow pointer is at the middle
   - This is an efficient way to find the middle in a single pass

2. **Reversing the Second Half:**
   - We reverse the second half of the list using the standard linked list reversal algorithm
   - This involves changing the next pointers to point to the previous node
   - After reversal, the second half starts from the end of the original list

3. **Merging the Two Halves:**
   - We merge the first half and the reversed second half by alternating nodes
   - For each node in the first half, we insert a node from the second half after it
   - This creates the desired L₀ → Lₙ → L₁ → Lₙ₋₁ → ... pattern

4. **Edge Cases:**
   - Empty list: Nothing to reorder
   - Single node: Nothing to reorder
   - Two nodes: Simply swap the next pointers

5. **Alternative Approach:**
   - Using a stack allows us to access the nodes from the end easily
   - However, it requires O(n) extra space
   - The three-step approach is more space-efficient

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (head == null || head.next == null) { return; }` - Nothing to reorder for empty or single-node lists
- **R1 (Find middle)**: Use slow and fast pointers to find the middle of the list
- **R2 (Reverse second half)**: Reverse the second half of the list and break the original list
- **R3 (Merge halves)**: Merge the two halves by alternating nodes
- **R4 (Reverse list helper)**: Helper method to reverse a linked list
- **R5 (Merge lists helper)**: Helper method to merge two lists by alternating nodes

### Complexity Analysis
- **Time Complexity**: O(n)
  - Finding the middle: O(n/2) = O(n)
  - Reversing the second half: O(n/2) = O(n)
  - Merging the two halves: O(n/2) = O(n)
  - Overall: O(n)

- **Space Complexity**: O(1)
  - We only use a constant amount of extra space for pointers
  - The alternative stack-based approach uses O(n) space

### Related Problems
- **Reverse Linked List** (Problem 206): Used as a subroutine in this problem
- **Palindrome Linked List** (Problem 234): Also uses the find middle + reverse technique
- **Rotate List** (Problem 61): Another problem involving rearrangement of linked lists
