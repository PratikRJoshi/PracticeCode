# Reverse String

## Problem Description

**Problem Link:** [Reverse String](https://leetcode.com/problems/reverse-string/)

Write a function that reverses a string. The input string is given as an array of characters `s`.

You must do this by modifying the input array **in-place** with $O(1)$ extra memory.

**Example 1:**
```
Input: s = ["h","e","l","l","o"]
Output: ["o","l","l","e","h"]
```

**Example 2:**
```
Input: s = ["a","b","b","a"]
Output: ["a","b","b","a"]
```

**Constraints:**
- `1 <= s.length <= 10^5`
- `s[i]` is a printable ascii character.

## Intuition/Main Idea

This is a classic two-pointer problem. We swap characters from both ends, moving towards the center.

**Core Algorithm:**
- Use two pointers: one at start, one at end
- Swap characters at these positions
- Move pointers towards center
- Stop when pointers meet or cross

**Why two pointers:** This is the most efficient approach - $O(n)$ time with $O(1)$ space. We swap pairs of characters, so we only need $n/2$ swaps.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Reverse string in-place | Two-pointer swap - Lines 6-12 |
| Swap characters | Swap operation - Lines 9-11 |
| Move pointers | Pointer updates - Lines 12-13 |
| Stop when done | Loop condition - Line 6 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public void reverseString(char[] s) {
        // Two-pointer approach
        // left: starts at beginning of array
        // right: starts at end of array
        int left = 0;
        int right = s.length - 1;
        
        // Continue swapping until pointers meet or cross
        // When left >= right, we've processed all pairs
        while (left < right) {
            // Swap characters at left and right positions
            // This places left character at right position and vice versa
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            
            // Move pointers towards center
            left++;
            right--;
        }
        // After loop, all characters have been swapped
        // Array is now reversed
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the string. We perform $n/2$ swaps.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space for the temporary variable and pointers.

## Similar Problems

- [Reverse Words in a String](https://leetcode.com/problems/reverse-words-in-a-string/) - Reverse words, then reverse entire string
- [Valid Palindrome](https://leetcode.com/problems/valid-palindrome/) - Uses similar two-pointer approach
- [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) - Similar reversal concept

