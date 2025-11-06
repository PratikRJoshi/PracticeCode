### 23. Merge k Sorted Lists
### Problem Link: [Merge k Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/)
### Intuition
This problem asks us to merge k sorted linked lists into one sorted linked list. The key insight is to use a min-heap (priority queue) to efficiently find the smallest element among the heads of all lists. By maintaining a heap of the current head nodes of each list, we can always extract the smallest node and add it to our result list.

Another approach is to merge lists one by one, or use a divide-and-conquer strategy to merge pairs of lists recursively. The heap-based approach is generally the most efficient and intuitive.

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
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) { // [R0] Handle edge cases
            return null;
        }
        
        // [R1] Create a min heap ordered by node values
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);
        
        // [R2] Add the head of each list to the heap
        for (ListNode head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }
        
        // [R3] Create a dummy head for the result list
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // [R4] Process nodes from the heap until it's empty
        while (!minHeap.isEmpty()) {
            // [R5] Extract the smallest node
            ListNode node = minHeap.poll();
            current.next = node;
            current = current.next;
            
            // [R6] If the extracted node has a next node, add it to the heap
            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }
        
        return dummy.next; // [R7] Return the merged list (excluding the dummy head)
    }
}
```

### Alternative Implementation (Divide and Conquer)
```java
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        return mergeKListsHelper(lists, 0, lists.length - 1);
    }
    
    private ListNode mergeKListsHelper(ListNode[] lists, int start, int end) {
        if (start == end) {
            return lists[start];
        }
        
        if (start + 1 == end) {
            return mergeTwoLists(lists[start], lists[end]);
        }
        
        int mid = start + (end - start) / 2;
        ListNode left = mergeKListsHelper(lists, start, mid);
        ListNode right = mergeKListsHelper(lists, mid + 1, end);
        
        return mergeTwoLists(left, right);
    }
    
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        
        current.next = (l1 != null) ? l1 : l2;
        
        return dummy.next;
    }
}
```

### Understanding the Algorithm and Data Structure

1. **Priority Queue (Min-Heap) Approach:**
   - We use a min-heap to always have access to the smallest node among all list heads
   - The heap is ordered by the node values, so the smallest node is always at the top
   - We initially add the head of each list to the heap
   - In each iteration, we extract the smallest node, add it to our result list, and add its next node to the heap
   - This process continues until the heap is empty

2. **Divide and Conquer Approach:**
   - We recursively divide the lists into halves until we're merging just two lists
   - We then merge pairs of lists bottom-up
   - This approach has the same time complexity as the heap approach but may be more space-efficient

3. **Key Operations:**
   - Extracting the minimum element: O(log k) using a heap
   - Merging two sorted lists: O(n) where n is the total number of nodes
   - The total time complexity is O(N log k) where N is the total number of nodes across all lists

4. **Edge Cases:**
   - Empty lists array: Return null
   - All empty lists: Return null
   - Single list: Return that list directly

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (lists == null || lists.length == 0) { return null; }` - Return null for empty input
- **R1 (Create min-heap)**: `PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);` - Create a min-heap ordered by node values
- **R2 (Add list heads)**: Add the head of each non-empty list to the heap
- **R3 (Create result list)**: Create a dummy head for the result list
- **R4 (Process heap)**: Continue processing nodes from the heap until it's empty
- **R5 (Extract smallest node)**: `ListNode node = minHeap.poll();` - Extract the smallest node from the heap
- **R6 (Add next node)**: `if (node.next != null) { minHeap.offer(node.next); }` - Add the next node to the heap
- **R7 (Return result)**: `return dummy.next;` - Return the merged list (excluding the dummy head)

### Complexity Analysis
- **Time Complexity**: O(N log k)
  - N is the total number of nodes across all lists
  - k is the number of lists
  - Each insertion and extraction from the heap takes O(log k) time
  - We perform these operations for each of the N nodes

- **Space Complexity**: O(k)
  - The heap contains at most k nodes (one from each list)
  - The result list doesn't count towards extra space as it's part of the output

### Related Problems
- **Merge Two Sorted Lists** (Problem 21): Simpler version with just two lists
- **Merge Sorted Array** (Problem 88): Similar concept but with arrays
- **Kth Smallest Element in a Sorted Matrix** (Problem 378): Uses similar heap-based approach
