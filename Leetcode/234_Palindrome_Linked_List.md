# Palindrome Linked List

## Problem Description

**Problem Link:** [Palindrome Linked List](https://leetcode.com/problems/palindrome-linked-list/)

Given the `head` of a singly linked list, return `true` *if it is a palindrome or* `false` *otherwise*.

**Example 1:**
```
Input: head = [1,2,2,1]
Output: true
```

**Example 2:**
```
Input: head = [1,2]
Output: false
```

**Constraints:**
- The number of nodes in the list is in the range `[1, 10^5]`.
- `0 <= Node.val <= 9`

## Intuition/Main Idea

We need to check if a linked list is a palindrome. The challenge is we can't easily access elements from the end.

**Core Algorithm:**
- Find the middle of the list using slow/fast pointers
- Reverse the second half
- Compare first half with reversed second half
- Restore the list (optional)

**Why two pointers:** Finding the middle and reversing allows us to compare the list from both ends without using extra space for a copy.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find middle | Slow/fast pointers - Lines 8-13 |
| Reverse second half | Reverse function - Lines 15-16, 30-40 |
| Compare halves | Comparison loop - Lines 17-22 |
| Restore list | Reverse again - Lines 24-25 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        // Find the middle of the list using slow and fast pointers
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Reverse the second half
        // slow.next is the start of second half
        ListNode secondHalf = reverse(slow.next);
        ListNode firstHalf = head;
        
        // Compare first half with reversed second half
        boolean isPalindrome = true;
        ListNode secondHalfCopy = secondHalf; // Save for restoration
        while (secondHalf != null) {
            if (firstHalf.val != secondHalf.val) {
                isPalindrome = false;
                break;
            }
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }
        
        // Restore the list (optional but good practice)
        slow.next = reverse(secondHalfCopy);
        
        return isPalindrome;
    }
    
    // Helper function to reverse a linked list
    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        return prev;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the number of nodes. We traverse the list a few times.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) - Reverse operation
- [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/) - Two pointer technique
- [Valid Palindrome](https://leetcode.com/problems/valid-palindrome/) - Palindrome check on string

