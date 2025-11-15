# Add Two Numbers II

## Problem Description

**Problem Link:** [Add Two Numbers II](https://leetcode.com/problems/add-two-numbers-ii/)

You are given two **non-empty** linked lists representing two non-negative integers. The most significant digit comes first and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

**Example 1:**
```
Input: l1 = [7,2,4,3], l2 = [5,6,4]
Output: [7,8,0,7]
Explanation: 7243 + 564 = 7807.
```

**Example 2:**
```
Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [8,0,7]
Explanation: 243 + 564 = 807.
```

**Example 3:**
```
Input: l1 = [0], l2 = [0]
Output: [0]
```

**Constraints:**
- The number of nodes in each linked list is in the range `[1, 100]`.
- `0 <= Node.val <= 9`
- It is guaranteed that the list represents a number that does not have leading zeros.

**Follow up:** Could you solve it without reversing the input lists?

## Intuition/Main Idea

This problem is about adding two numbers represented as linked lists. Unlike the original "Add Two Numbers" problem (LC 2), here the most significant digit comes first (left to right). This presents a challenge because addition is performed from right to left.

There are two main approaches:
1. **Reverse the lists**, add them like in LC 2, then reverse the result.
2. **Use stacks** to process nodes from right to left without modifying the original lists.

I'll implement the stack-based approach as it's more elegant and avoids modifying the input lists. The key insight is that stacks naturally give us the digits in reverse order, which is exactly what we need for addition.

**Core Algorithm:**
1. Push all nodes from both lists onto separate stacks.
2. Pop from both stacks and add digits with carry, building the result list from right to left.
3. Handle any remaining carry at the end.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Process digits from right to left | Using stacks to reverse order - Lines 9-15 |
| Add corresponding digits with carry | Addition logic with carry - Lines 18-24 |
| Handle different list lengths | While loops continue until both stacks empty - Lines 18-24 |
| Create result list in correct order | Insert at head for each new sum - Lines 25-26 |
| Handle final carry if needed | Check for remaining carry - Lines 28-31 |

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
        // Use stacks to reverse the order of digits
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        
        // Push all digits from first list to stack1
        while (l1 != null) {
            stack1.push(l1.val);
            l1 = l1.next;
        }
        
        // Push all digits from second list to stack2
        while (l2 != null) {
            stack2.push(l2.val);
            l2 = l2.next;
        }
        
        // Initialize result as null (we'll build it from right to left)
        ListNode result = null;
        int carry = 0;
        
        // Process digits from right to left (by popping from stacks)
        while (!stack1.isEmpty() || !stack2.isEmpty() || carry > 0) {
            // Get digits from stacks (or 0 if stack is empty)
            int digit1 = stack1.isEmpty() ? 0 : stack1.pop();
            int digit2 = stack2.isEmpty() ? 0 : stack2.pop();
            
            // Calculate sum with carry
            int sum = digit1 + digit2 + carry;
            carry = sum / 10;  // Calculate new carry
            
            // Create new node with current digit and insert at head
            ListNode newNode = new ListNode(sum % 10);
            newNode.next = result;
            result = newNode;
        }
        
        return result;
    }
}
```

### Explanation:

1. **Stack Creation (Lines 9-10)**: We create two stacks to store the digits from both lists. Stacks follow LIFO (Last In, First Out) order, which naturally gives us the digits in reverse order when we pop.

2. **Filling the Stacks (Lines 13-21)**: We traverse both linked lists and push each digit onto its respective stack. After this step, the least significant digits will be at the top of the stacks.

3. **Addition Process (Lines 24-34)**:
   - We process digits until both stacks are empty and there's no remaining carry.
   - For each iteration, we pop digits from both stacks (or use 0 if a stack is empty).
   - We add the digits along with any carry from the previous step.
   - The new carry is calculated as `sum / 10`.
   - The current digit of the result is `sum % 10`.

4. **Building the Result List (Lines 31-33)**:
   - We create a new node for each calculated digit.
   - We insert each new node at the head of our result list.
   - This effectively builds our result list from right to left, which gives us the correct order.

5. **Final Result (Line 36)**: After processing all digits, we return the head of our result list.

This approach elegantly handles different list lengths and any carry that might propagate to create an additional digit.

## Complexity Analysis

- **Time Complexity**: $O(m + n)$ where $m$ and $n$ are the lengths of the two input lists. We traverse each list once to build the stacks, and then process each digit once during addition.
  
- **Space Complexity**: $O(m + n)$ for the two stacks that store all digits from both lists. The output list doesn't count toward space complexity as it's part of the required output.

## Similar Problems

1. [2. Add Two Numbers](https://leetcode.com/problems/add-two-numbers/) - Similar problem but with least significant digit first
2. [66. Plus One](https://leetcode.com/problems/plus-one/) - Simpler version with array representation
3. [67. Add Binary](https://leetcode.com/problems/add-binary/) - Addition with binary numbers
4. [369. Plus One Linked List](https://leetcode.com/problems/plus-one-linked-list/) - Add one to a number represented as linked list
5. [415. Add Strings](https://leetcode.com/problems/add-strings/) - Addition with string representation
6. [989. Add to Array-Form of Integer](https://leetcode.com/problems/add-to-array-form-of-integer/) - Add number to array representation
7. [1634. Add Two Polynomials Represented as Linked Lists](https://leetcode.com/problems/add-two-polynomials-represented-as-linked-lists/) - Similar concept with polynomials
8. [43. Multiply Strings](https://leetcode.com/problems/multiply-strings/) - More complex arithmetic operation
9. [306. Additive Number](https://leetcode.com/problems/additive-number/) - Check if string forms an additive sequence
10. [371. Sum of Two Integers](https://leetcode.com/problems/sum-of-two-integers/) - Addition without using + operator
