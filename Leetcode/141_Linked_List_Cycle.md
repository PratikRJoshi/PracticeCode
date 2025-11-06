### 141. Linked List Cycle
### Problem Link: [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/)
### Intuition
This problem asks us to determine if a linked list has a cycle in it. A cycle occurs when a node in the list points to a previously visited node, creating a loop.

The key insight is to use the "tortoise and hare" algorithm (also known as Floyd's Cycle-Finding Algorithm). We use two pointers that move at different speeds: a slow pointer (tortoise) that moves one step at a time and a fast pointer (hare) that moves two steps at a time. If there's a cycle, the fast pointer will eventually catch up to the slow pointer. If there's no cycle, the fast pointer will reach the end of the list.

### Java Reference Implementation
```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) { // [R0] Handle edge cases
            return false; // Empty list or single node cannot have a cycle
        }
        
        // [R1] Initialize slow and fast pointers
        ListNode slow = head;
        ListNode fast = head;
        
        // [R2] Move pointers until they meet or fast reaches the end
        while (fast != null && fast.next != null) {
            slow = slow.next;       // [R3] Move slow pointer one step
            fast = fast.next.next;  // [R4] Move fast pointer two steps
            
            // [R5] If pointers meet, there's a cycle
            if (slow == fast) {
                return true;
            }
        }
        
        // [R6] If fast pointer reaches the end, there's no cycle
        return false;
    }
}
```

### Alternative Implementation (Using HashSet)
```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        Set<ListNode> visited = new HashSet<>();
        
        ListNode current = head;
        while (current != null) {
            if (visited.contains(current)) {
                return true; // Found a cycle
            }
            
            visited.add(current);
            current = current.next;
        }
        
        return false; // No cycle found
    }
}
```

### Understanding the Algorithm and Pointer Movement

1. **Floyd's Cycle-Finding Algorithm:**
   - We use two pointers: slow (moves one step) and fast (moves two steps)
   - If there's no cycle, the fast pointer will reach the end of the list
   - If there's a cycle, the fast pointer will eventually catch up to the slow pointer
   - This works because if both pointers are in a cycle, the fast pointer will gain on the slow pointer by one step in each iteration

2. **Mathematical Proof:**
   - Suppose the slow pointer has moved s steps and the fast pointer has moved 2s steps
   - If there's a cycle of length c, and the distance from the start to the cycle entrance is d
   - When the slow pointer enters the cycle, it has moved d steps
   - The fast pointer would have moved 2d steps, so it's already d steps into the cycle
   - The relative distance between them in the cycle is d % c
   - In each subsequent step, this distance decreases by 1
   - Therefore, they will meet after d % c steps in the cycle

3. **Edge Cases:**
   - Empty list: Return false (no cycle)
   - Single node: Return false (unless it points to itself)
   - Two nodes: Can have a cycle if they point to each other

4. **Alternative Approach:**
   - Using a HashSet to track visited nodes is more intuitive but uses O(n) extra space
   - The two-pointer approach is more space-efficient (O(1))

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (head == null || head.next == null) { return false; }` - Empty list or single node cannot have a cycle
- **R1 (Initialize pointers)**: Set up slow and fast pointers, both starting at the head
- **R2 (Move pointers)**: Continue moving pointers until they meet or fast reaches the end
- **R3 (Move slow)**: `slow = slow.next;` - Move slow pointer one step at a time
- **R4 (Move fast)**: `fast = fast.next.next;` - Move fast pointer two steps at a time
- **R5 (Check for meeting)**: `if (slow == fast) { return true; }` - If pointers meet, there's a cycle
- **R6 (No cycle)**: Return false if fast pointer reaches the end

### Complexity Analysis
- **Time Complexity**: O(n)
  - In the worst case, we might need to traverse the entire list once
  - If there's a cycle, the fast pointer will catch up to the slow pointer in at most n steps
  - If there's no cycle, the fast pointer will reach the end in at most n/2 steps

- **Space Complexity**: O(1)
  - We only use two pointers regardless of the list size
  - The alternative HashSet approach uses O(n) space

### Related Problems
- **Linked List Cycle II** (Problem 142): Find the node where the cycle begins
- **Happy Number** (Problem 202): Uses the same cycle detection algorithm
- **Find the Duplicate Number** (Problem 287): Can be solved using cycle detection
