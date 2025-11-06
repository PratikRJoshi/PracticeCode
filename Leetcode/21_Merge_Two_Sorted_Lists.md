### 21. Merge Two Sorted Lists
### Problem Link: [Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/)
### Intuition
This problem asks us to merge two sorted linked lists into one sorted linked list. The key insight is to use a dummy head node to simplify the code and avoid edge cases. We iterate through both lists simultaneously, always choosing the smaller of the two current nodes to add to our result list.

This is a fundamental linked list operation that forms the basis for more complex algorithms like merge sort for linked lists.

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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // [R0] Create a dummy head to simplify edge cases
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        
        // [R1] Iterate through both lists
        while (list1 != null && list2 != null) {
            // [R2] Compare the current nodes and choose the smaller one
            if (list1.val <= list2.val) {
                current.next = list1; // [R3] Add list1's node to the result
                list1 = list1.next;   // [R4] Move to the next node in list1
            } else {
                current.next = list2; // [R5] Add list2's node to the result
                list2 = list2.next;   // [R6] Move to the next node in list2
            }
            current = current.next;   // [R7] Move the current pointer forward
        }
        
        // [R8] Attach the remaining nodes from either list
        current.next = (list1 != null) ? list1 : list2;
        
        return dummy.next; // [R9] Return the merged list (excluding the dummy head)
    }
}
```

### Alternative Implementation (Recursive)
```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Base cases
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        
        // Choose the smaller node and recursively merge the rest
        if (list1.val <= list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }
}
```

### Understanding the Algorithm and Linked List Operations

1. **Iterative Approach:**
   - We use a dummy head node to simplify the code and avoid edge cases
   - We maintain a `current` pointer to keep track of the last node in our merged list
   - We iterate through both lists simultaneously, always choosing the smaller of the two current nodes
   - When one list is exhausted, we attach the remaining nodes from the other list

2. **Recursive Approach:**
   - Base cases: If one list is empty, return the other list
   - If list1's head is smaller, it becomes the head of the merged list, and we recursively merge list1.next with list2
   - If list2's head is smaller, it becomes the head of the merged list, and we recursively merge list1 with list2.next

3. **Key Operations:**
   - Comparing node values: `list1.val <= list2.val`
   - Linking nodes: `current.next = list1` or `current.next = list2`
   - Moving pointers: `list1 = list1.next`, `list2 = list2.next`, `current = current.next`
   - Attaching remaining nodes: `current.next = (list1 != null) ? list1 : list2`

4. **Edge Cases:**
   - One or both lists are empty: Handled by the dummy head approach or base cases in recursion
   - Lists of different lengths: Handled by attaching the remaining nodes

### Requirement → Code Mapping
- **R0 (Create dummy head)**: `ListNode dummy = new ListNode(-1);` - Create a dummy head to simplify edge cases
- **R1 (Iterate through lists)**: `while (list1 != null && list2 != null)` - Process both lists until one is exhausted
- **R2 (Compare nodes)**: `if (list1.val <= list2.val)` - Choose the smaller node
- **R3-R6 (Add node to result)**: Add the chosen node to the result and move to the next node in that list
- **R7 (Move current pointer)**: `current = current.next;` - Move the current pointer forward
- **R8 (Attach remaining nodes)**: `current.next = (list1 != null) ? list1 : list2;` - Attach any remaining nodes
- **R9 (Return merged list)**: `return dummy.next;` - Return the merged list (excluding the dummy head)

### Complexity Analysis
- **Time Complexity**: O(n + m)
  - n is the length of list1
  - m is the length of list2
  - We visit each node exactly once

- **Space Complexity**: 
  - Iterative approach: O(1) - We use only a constant amount of extra space
  - Recursive approach: O(n + m) - The recursion stack can go as deep as the total number of nodes

### Related Problems
- **Merge k Sorted Lists** (Problem 23): Extension to k lists instead of just two
- **Sort List** (Problem 148): Uses merge sort with this merging technique
- **Intersection of Two Linked Lists** (Problem 160): Another problem involving two linked lists
