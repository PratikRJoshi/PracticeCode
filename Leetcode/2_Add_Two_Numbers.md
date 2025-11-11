# Add Two Numbers

## Problem Description

**Problem Link:** [Add Two Numbers](https://leetcode.com/problems/add-two-numbers/)

You are given two **non-empty** linked lists representing two non-negative integers. The digits are stored in **reverse order**, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

**Example 1:**

```
Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [7,0,8]
Explanation: 342 + 465 = 807.
```

**Example 2:**

```
Input: l1 = [0], l2 = [0]
Output: [0]
```

**Example 3:**

```
Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
Output: [8,9,9,9,0,0,0,1]
```

**Constraints:**
- The number of nodes in each linked list is in the range `[1, 100]`.
- `0 <= Node.val <= 9`
- It is guaranteed that the list represents a number that does not have leading zeros.

## Intuition/Main Idea

This problem simulates manual addition digit by digit. Since the digits are stored in reverse order, we can process them from left to right, which is natural for addition.

**Core Algorithm:**
1. Traverse both linked lists simultaneously.
2. Add corresponding digits along with any carry from the previous addition.
3. Create a new node with the ones digit of the sum.
4. Carry over the tens digit to the next addition.
5. Handle cases where one list is longer than the other.
6. Don't forget to handle the final carry if it exists.

**Why this works:** Reverse order makes addition straightforward - we add digits at the same position, handle carry, and move to the next position. This is exactly how we add numbers manually.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Initialize dummy head | Dummy node creation - Line 6 |
| Track carry | Carry variable - Line 7 |
| Process both lists | While loop - Line 9 |
| Add digits with carry | Sum calculation - Lines 11-13 |
| Create result node | Node creation - Lines 15-17 |
| Update carry | Carry calculation - Line 18 |
| Handle final carry | Final carry check - Lines 23-26 |
| Return result | Return statement - Line 28 |

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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Dummy head to simplify result construction
        ListNode dummyHead = new ListNode(0);
        ListNode current = dummyHead;
        int carry = 0;
        
        // Process both lists while they exist or carry remains
        while (l1 != null || l2 != null || carry != 0) {
            // Get values from current nodes (0 if null)
            int val1 = (l1 != null) ? l1.val : 0;
            int val2 = (l2 != null) ? l2.val : 0;
            
            // Calculate sum: digits + carry
            int sum = val1 + val2 + carry;
            
            // Create new node with ones digit
            current.next = new ListNode(sum % 10);
            current = current.next;
            
            // Calculate carry for next iteration
            carry = sum / 10;
            
            // Move to next nodes
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        
        // Return the result (skip dummy head)
        return dummyHead.next;
    }
}
```

**Explanation of Key Code Sections:**

1. **Dummy Head (Line 6):** We create a dummy head node to simplify the code. This avoids special cases when creating the first node of the result list.

2. **Carry Variable (Line 7):** We track the carry from each addition. Initially, it's 0.

3. **Main Loop (Line 9):** Continue while either list has nodes or there's a carry remaining. This handles:
   - Lists of different lengths
   - Final carry after processing all digits

4. **Get Values (Lines 11-12):** Extract values from current nodes. If a list is exhausted, use 0.

5. **Calculate Sum (Line 15):** Add the two digits and the carry from the previous step.

6. **Create Result Node (Lines 17-18):** Create a new node with the ones digit (`sum % 10`) and move the current pointer.

7. **Update Carry (Line 21):** Calculate the carry for the next iteration (`sum / 10`).

8. **Move Pointers (Lines 23-24):** Advance both list pointers if they're not null.

9. **Return Result (Line 28):** Return `dummyHead.next` to skip the dummy node.

**Why dummy head simplifies the code:**
- Without dummy head: We need to check if the result list is empty before creating the first node.
- With dummy head: We always append to `current.next`, making the code uniform.

**Example walkthrough for `l1 = [2,4,3], l2 = [5,6,4]`:**
- Iteration 1: val1=2, val2=5, sum=7, carry=0 → node(7)
- Iteration 2: val1=4, val2=6, sum=10, carry=1 → node(0)
- Iteration 3: val1=3, val2=4, sum=8, carry=0 → node(8)
- Result: [7,0,8] representing 807 ✓

## Complexity Analysis

- **Time Complexity:** $O(\max(m, n))$ where $m$ and $n$ are the lengths of the two linked lists. We process each node at most once.

- **Space Complexity:** $O(\max(m, n))$ for the result linked list. The space used does not count the input lists.

## Similar Problems

Problems that can be solved using similar linked list manipulation patterns:

1. **2. Add Two Numbers** (this problem) - Linked list addition
2. **445. Add Two Numbers II** - Addition with digits in forward order
3. **21. Merge Two Sorted Lists** - Merge linked lists
4. **23. Merge k Sorted Lists** - Merge multiple lists
5. **206. Reverse Linked List** - Reverse a linked list
6. **92. Reverse Linked List II** - Reverse part of a list
7. **25. Reverse Nodes in k-Group** - Reverse in groups
8. **19. Remove Nth Node From End of List** - Remove node
9. **83. Remove Duplicates from Sorted List** - Remove duplicates
10. **82. Remove Duplicates from Sorted List II** - Remove all duplicates

