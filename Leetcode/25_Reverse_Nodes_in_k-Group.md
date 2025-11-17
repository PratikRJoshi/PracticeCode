# Reverse Nodes in k-Group

## Problem Description

**Problem Link:** [Reverse Nodes in k-Group](https://leetcode.com/problems/reverse-nodes-in-k-group/)

Given the `head` of a linked list, reverse the nodes of the list `k` at a time, and return *the modified list*.

`k` is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of `k` then left-out nodes, in the end, should remain as it is.

You may not alter the values in the list's nodes, only nodes themselves may be changed.

**Example 1:**

```
Input: head = [1,2,3,4,5], k = 2
Output: [2,1,4,3,5]
```

**Example 2:**

```
Input: head = [1,2,3,4,5], k = 3
Output: [3,2,1,4,5]
```

**Constraints:**
- The number of nodes in the list is `n`.
- `1 <= k <= n <= 5000`
- `0 <= Node.val <= 1000`

## Intuition/Main Idea

This problem requires reversing linked list nodes in groups of `k`. We need to:
1. Check if there are at least `k` nodes remaining.
2. Reverse the next `k` nodes.
3. Recursively process the remaining list.
4. Connect the reversed group to the rest.

**Core Algorithm:**
1. Check if there are `k` nodes ahead.
2. If yes, reverse the next `k` nodes.
3. Recursively process the remaining list.
4. Connect the reversed group to the previous part.

**Why recursion works:** After reversing a group of `k` nodes, we need to process the remaining list in the same way. Recursion naturally handles this pattern.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Check if k nodes exist | Count check - Lines 9-15 |
| Reverse k nodes | Reverse function - Lines 17-28 |
| Recursively process rest | Recursive call - Line 29 |
| Connect reversed group | Return statement - Line 30 |
| Handle base case | Base case check - Line 8 |

## Final Java Code & Learning Pattern

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
    public ListNode reverseKGroup(ListNode head, int k) {
        // Base case: if head is null, return null
        if (head == null) {
            return null;
        }
        
        // Check if there are at least k nodes ahead
        ListNode current = head;
        int count = 0;
        while (current != null && count < k) {
            current = current.next;
            count++;
        }
        
        // If we have k nodes, reverse them
        if (count == k) {
            // Reverse the next k nodes
            // We process the rest of the list first because:
            // 1. We need the new head of the remaining list to connect our current group to
            // 2. This builds the solution from the end to the beginning, ensuring proper connections
            // 3. When we reverse the current group, we'll connect its tail to this already-processed part
            current = reverseKGroup(current, k);  // Process rest first
            
            // Reverse current group of k nodes
            while (count > 0) {
                ListNode next = head.next;
                head.next = current;
                current = head;
                head = next;
                count--;
            }
            head = current;
        }
        
        // If less than k nodes, return as is
        return head;
    }
}
```

**Alternative Iterative Approach (More Intuitive):**

```java
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        // Dummy head to simplify edge cases
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode groupPrev = dummy;  // Previous node of the current group
        
        while (true) {
            // Check if there are k nodes remaining
            ListNode kth = getKthNode(groupPrev, k);
            if (kth == null) {
                break;  // Less than k nodes remaining
            }
            
            ListNode groupNext = kth.next;  // First node of next group
            
            // Reverse the current group
            ListNode prev = groupNext;
            ListNode curr = groupPrev.next;
            
            while (curr != groupNext) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            
            // Connect the reversed group
            ListNode temp = groupPrev.next;  // Save the new tail of reversed group
            groupPrev.next = kth;  // Connect to the new head
            groupPrev = temp;  // Move to next group's previous node
        }
        
        return dummy.next;
    }
    
    // Helper function to get the k-th node from a starting point
    private ListNode getKthNode(ListNode node, int k) {
        while (node != null && k > 0) {
            node = node.next;
            k--;
        }
        return node;
    }
}
```

**Explanation of Key Code Sections:**

**Recursive Approach:**

1. **Base Case (Lines 8-10):** If head is null, return null.

2. **Count Check (Lines 12-17):** Count if there are at least `k` nodes ahead. This determines if we should reverse or leave nodes as is.

3. **Reverse Group (Lines 19-28):** If we have `k` nodes:
   - **Process Rest First (Line 20):** Recursively reverse the remaining list.
   - **Reverse Current Group (Lines 22-27):** Reverse the current `k` nodes using standard reversal technique.
   - **Update Head (Line 28):** Set head to the new head of reversed group.

**Iterative Approach:**

1. **Dummy Head (Line 6):** Use dummy head to handle edge cases.

2. **Group Processing (Lines 10-30):** For each group:
   - **Check k Nodes (Lines 12-15):** Verify there are `k` nodes remaining.
   - **Reverse Group (Lines 19-25):** Reverse the `k` nodes.
   - **Connect Groups (Lines 27-29):** Connect the reversed group to the previous part.

3. **Get Kth Node Helper (Lines 33-39):** Helper function to find the k-th node from a starting point.

**Why both approaches work:**
- **Recursive:** Naturally handles the pattern of processing groups and connecting them.
- **Iterative:** More explicit control flow, easier to understand step-by-step.

**Example walkthrough for `head = [1,2,3,4,5], k = 2`:**
- Group 1: [1,2] → reverse → [2,1]
- Group 2: [3,4] → reverse → [4,3]
- Group 3: [5] → less than k, keep as is
- Result: [2,1,4,3,5]

**Example for `head = [1,2,3,4,5], k = 3`:**
- Group 1: [1,2,3] → reverse → [3,2,1]
- Group 2: [4,5] → less than k, keep as is
- Result: [3,2,1,4,5]

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the number of nodes. We visit each node exactly once.

- **Space Complexity:** 
  - Recursive: $O(n/k)$ for the recursion stack (depth of recursion).
  - Iterative: $O(1)$ extra space.

## Similar Problems

Problems that can be solved using similar linked list reversal patterns:

1. **25. Reverse Nodes in k-Group** (this problem) - Reverse in groups
2. **206. Reverse Linked List** - Reverse entire list
3. **92. Reverse Linked List II** - Reverse part of list
4. **24. Swap Nodes in Pairs** - Special case with k=2
5. **61. Rotate List** - Rotate linked list
6. **143. Reorder List** - Reorder linked list
7. **234. Palindrome Linked List** - Check palindrome
8. **19. Remove Nth Node From End of List** - Remove node
9. **82. Remove Duplicates from Sorted List II** - Remove duplicates
10. **86. Partition List** - Partition linked list
