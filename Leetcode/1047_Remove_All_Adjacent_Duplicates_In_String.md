# Remove All Adjacent Duplicates In String

## Problem Description

**Problem Link:** [Remove All Adjacent Duplicates In String](https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/)

You are given a string `s` consisting of lowercase English letters. A **duplicate removal** consists of choosing two **adjacent** and **equal** letters and removing them.

We repeatedly make **duplicate removals** on `s` until we no longer can.

Return *the final string after all such duplicate removals have been made*. It can be proven that the answer is **unique**.

**Example 1:**
```
Input: s = "abbaca"
Output: "ca"
Explanation: 
For example, in "abbaca", we can remove "bb" since the letters are adjacent and equal, and this is the only possible move.  The result of this move is that the string is "aaca", of which only "aa" is possible, so we remove "aa" and get "ca".
```

**Example 2:**
```
Input: s = "azxxzy"
Output: "ay"
```

**Constraints:**
- `1 <= s.length <= 10^5`
- `s` consists of lowercase English letters.

## Intuition/Main Idea

This is a stack problem. We use a stack to track characters, and when we see a duplicate adjacent character, we remove it.

**Core Algorithm:**
- Use a stack (or StringBuilder as stack)
- For each character:
  - If stack top equals current character, pop (remove duplicate)
  - Otherwise, push current character
- Return stack contents as string

**Why stack:** When we remove adjacent duplicates, new adjacent pairs might form. A stack naturally handles this by allowing us to check the last added character and remove pairs as they form.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Remove adjacent duplicates | Stack-based approach - Lines 5-13 |
| Check for duplicates | Compare with top - Line 8 |
| Remove duplicates | Pop from stack - Line 9 |
| Add non-duplicates | Push to stack - Line 11 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public String removeDuplicates(String s) {
        // Use StringBuilder as a stack
        // This is more efficient than using Stack<Character>
        StringBuilder stack = new StringBuilder();
        
        // Process each character
        for (char ch : s.toCharArray()) {
            // Check if current character matches top of stack
            // If stack is not empty and top equals current, remove duplicate
            if (stack.length() > 0 && stack.charAt(stack.length() - 1) == ch) {
                // Remove the duplicate (pop from stack)
                stack.deleteCharAt(stack.length() - 1);
            } else {
                // No duplicate, add to stack
                stack.append(ch);
            }
        }
        
        // Return the remaining characters
        return stack.toString();
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the string. We process each character once.

**Space Complexity:** $O(n)$ for the StringBuilder in worst case (no duplicates).

## Similar Problems

- [Remove All Adjacent Duplicates in String II](https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/) - Allow k duplicates
- [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) - Similar stack-based matching
- [Backspace String Compare](https://leetcode.com/problems/backspace-string-compare/) - Similar character removal pattern

