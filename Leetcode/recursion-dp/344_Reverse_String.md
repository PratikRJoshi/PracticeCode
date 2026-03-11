# Reverse String

## Problem Description

**Problem Link:** [Reverse String](https://leetcode.com/problems/reverse-string/)

Write a function that reverses a character array in-place.

Example:
- Input: `['h','e','l','l','o']`
- Output: `['o','l','l','e','h']`

## Intuition/Main Idea

Use recursion with two pointers: `left` and `right`.

- Base case: if `left >= right`, we are done.
- Work: swap `s[left]` and `s[right]`.
- Recurse inward: `(left + 1, right - 1)`.

Why this works: every recursive call fixes one mirrored pair; when stack unwinds, all pairs are already fixed.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Reverse in-place | swap inside helper, no extra array |
| Stop recursion correctly | `if (left >= right) return;` |
| Process mirrored positions | `reverse(s, left + 1, right - 1)` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Pure Recursion - Two Pointers]
class Solution {
    public void reverseString(char[] s) {
        reverse(s, 0, s.length - 1);
    }

    private void reverse(char[] s, int left, int right) {
        if (left >= right) {
            return;
        }

        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;

        reverse(s, left + 1, right - 1);
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n)$
- **Space Complexity:** $O(n)$ recursion stack

## Similar Problems

1. [206. Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) - same recurse-then-shrink idea.
2. [24. Swap Nodes in Pairs](https://leetcode.com/problems/swap-nodes-in-pairs/) - recursive local swap pattern.
