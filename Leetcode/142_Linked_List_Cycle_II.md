# [142. Linked List Cycle II](https://leetcode.com/problems/linked-list-cycle-ii/)

Given the `head` of a linked list, return the node where the cycle begins. If there is no cycle, return `null`.

There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the `next` pointer. Internally, `pos` is used to denote the index of the node that tail's `next` pointer is connected to (0-indexed). It is `-1` if there is no cycle. **Note that** `pos` **is not passed as a parameter**.

**Do not modify** the linked list.

**Example 1:**

![Example 1](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist.png)

```
Input: head = [3,2,0,-4], pos = 1
Output: tail connects to node index 1
Explanation: There is a cycle in the linked list, where tail connects to the second node.
```

**Example 2:**

![Example 2](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test2.png)

```
Input: head = [1,2], pos = 0
Output: tail connects to node index 0
Explanation: There is a cycle in the linked list, where tail connects to the first node.
```

**Example 3:**

![Example 3](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test3.png)

```
Input: head = [1], pos = -1
Output: no cycle
Explanation: There is no cycle in the linked list.
```

**Constraints:**

- The number of the nodes in the list is in the range `[0, 10^4]`.
- `-10^5 <= Node.val <= 10^5`
- `pos` is `-1` or a valid index in the linked-list.

**Follow up:** Can you solve it using `O(1)` extra space?

## Intuition/Main Idea:

This problem can be solved using Floyd's Tortoise and Hare algorithm (also known as the cycle detection algorithm). The algorithm uses two pointers moving at different speeds to detect a cycle:

1. **Cycle Detection**: Use a slow pointer (tortoise) that moves one step at a time and a fast pointer (hare) that moves two steps at a time. If there's a cycle, the fast pointer will eventually catch up to the slow pointer.

2. **Finding the Cycle Start**: Once we detect a cycle, we need to find where it begins. This is done by:
   - Resetting one pointer to the head of the list
   - Moving both pointers at the same speed (one step at a time)
   - The point where they meet is the start of the cycle

The mathematical proof for why this works is based on the fact that when the slow and fast pointers meet, the fast pointer has traveled exactly twice the distance of the slow pointer. Using this property, we can derive that resetting one pointer to the head and moving both at the same speed will make them meet at the cycle's starting point.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Detect if there is a cycle | `while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; if (slow == fast) break; }` |
| Return null if no cycle | `if (fast == null || fast.next == null) return null;` |
| Find the start of the cycle | `while (slow != fast) { slow = slow.next; fast = fast.next; }` |
| Do not modify the linked list | The solution only uses pointers without modifying the list structure |
| O(1) extra space | Only using two pointers regardless of list size |

## Final Java Code & Learning Pattern:

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
    public ListNode detectCycle(ListNode head) {
        // Edge case: empty list or single node
        if (head == null || head.next == null) {
            return null;
        }
        
        // Step 1: Detect if there is a cycle using Floyd's algorithm
        ListNode slow = head;
        ListNode fast = head;
        
        // Move slow by 1 step and fast by 2 steps
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            // If they meet, there is a cycle
            if (slow == fast) {
                break;
            }
        }
        
        // If fast reached the end, there is no cycle
        if (fast == null || fast.next == null) {
            return null;
        }
        
        // Step 2: Find the start of the cycle
        // Reset slow pointer to the head
        slow = head;
        
        // Move both pointers at the same speed until they meet
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        // The meeting point is the start of the cycle
        return slow;
    }
}
```

This solution implements Floyd's Tortoise and Hare algorithm:

1. **Cycle Detection**:
   - We use two pointers, `slow` and `fast`, both starting at the head.
   - `slow` moves one step at a time, while `fast` moves two steps.
   - If there's a cycle, they will eventually meet. If `fast` reaches the end of the list, there's no cycle.

2. **Finding the Cycle Start**:
   - After detecting a cycle, we reset the `slow` pointer to the head.
   - We keep the `fast` pointer at the meeting point.
   - We move both pointers one step at a time.
   - The point where they meet again is the start of the cycle.

The mathematical intuition behind this is:
- Let's say the distance from the head to the cycle start is `a`.
- The distance from the cycle start to the meeting point is `b`.
- The remaining distance of the cycle (from meeting point back to cycle start) is `c`.
- When they meet, the slow pointer has traveled `a + b` distance.
- The fast pointer has traveled `a + b + n*(b + c)` distance, where `n` is the number of times it has gone around the cycle.
- Since the fast pointer travels twice as fast: `a + b + n*(b + c) = 2*(a + b)`
- Simplifying: `n*(b + c) = a + b`
- For the smallest positive `n`, which is 1: `b + c = a + b`
- Therefore: `c = a`

This means that the distance from the head to the cycle start (`a`) is equal to the distance from the meeting point to the cycle start going around the cycle (`c`). This is why moving both pointers at the same speed from the head and the meeting point will make them meet at the cycle start.

## Complexity Analysis:

- **Time Complexity**: $O(n)$ where n is the number of nodes in the linked list. In the worst case, we might need to traverse the entire list once to detect the cycle and once more to find the cycle start.
- **Space Complexity**: $O(1)$ as we only use two pointers regardless of the list size.

## Similar Problems:

1. [141. Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/)
2. [287. Find the Duplicate Number](https://leetcode.com/problems/find-the-duplicate-number/)
3. [160. Intersection of Two Linked Lists](https://leetcode.com/problems/intersection-of-two-linked-lists/)
4. [202. Happy Number](https://leetcode.com/problems/happy-number/)
5. [876. Middle of the Linked List](https://leetcode.com/problems/middle-of-the-linked-list/)
