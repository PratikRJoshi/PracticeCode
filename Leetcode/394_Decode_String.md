# Decode String

## Problem Description

**Problem Link:** [Decode String](https://leetcode.com/problems/decode-string/)

Given an encoded string, return its decoded string.

The encoding rule is: `k[encoded_string]`, where the `encoded_string` inside the square brackets is being repeated exactly `k` times. Note that `k` is guaranteed to be a positive integer.

You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, `k`. For example, there will not be input like `3a` or `2[4]`.

**Example 1:**
```
Input: s = "3[a]2[bc]"
Output: "aaabcbc"
```

**Example 2:**
```
Input: s = "3[a2[c]]"
Output: "accaccacc"
```

**Example 3:**
```
Input: s = "2[abc]3[cd]ef"
Output: "abcabccdcdcdef"
```

**Constraints:**
- `1 <= s.length <= 30`
- `s` consists of lowercase English letters, digits, and square brackets `'[]'`.
- `s` is a valid input.

## Intuition/Main Idea

This is a stack problem for handling nested structures. We need to process the string and handle nested brackets.

**Core Algorithm:**
- Use two stacks: one for counts, one for strings
- When we see a digit, build the number
- When we see `[`, push current count and string to stacks
- When we see `]`, pop count and string, repeat current string count times, append to previous string
- For letters, append to current string

**Why stack:** The nested brackets require LIFO processing. When we encounter `]`, we need to process the innermost bracket first, which matches stack's LIFO behavior.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Handle nested brackets | Two stacks - Lines 5-6 |
| Parse numbers | Digit accumulation - Lines 12-15 |
| Handle '[' | Push to stacks - Lines 17-20 |
| Handle ']' | Pop and repeat - Lines 21-26 |
| Handle letters | Append to current string - Line 28 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public String decodeString(String s) {
        // Two stacks: one for repeat counts, one for strings
        // We need stacks to handle nested brackets
        Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();
        
        // Current string being built
        StringBuilder currentString = new StringBuilder();
        // Current number being parsed
        int currentCount = 0;
        
        // Process each character
        for (char ch : s.toCharArray()) {
            // If digit, build the number
            // Numbers can be multi-digit, so we accumulate
            if (Character.isDigit(ch)) {
                currentCount = currentCount * 10 + (ch - '0');
            }
            // If '[', push current count and string to stacks
            // Start fresh for nested bracket
            else if (ch == '[') {
                countStack.push(currentCount);
                stringStack.push(currentString);
                // Reset for nested content
                currentCount = 0;
                currentString = new StringBuilder();
            }
            // If ']', pop count and previous string
            // Repeat current string and append to previous string
            else if (ch == ']') {
                int repeatCount = countStack.pop();
                StringBuilder previousString = stringStack.pop();
                
                // Repeat current string 'repeatCount' times
                for (int i = 0; i < repeatCount; i++) {
                    previousString.append(currentString);
                }
                
                // Current string becomes the expanded string
                currentString = previousString;
            }
            // If letter, append to current string
            else {
                currentString.append(ch);
            }
        }
        
        return currentString.toString();
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n \cdot m)$ where $n$ is the length of the string and $m$ is the maximum repeat count. In worst case, we might repeat strings multiple times.

**Space Complexity:** $O(n)$ for the stacks and string builders.

## Similar Problems

- [Basic Calculator](https://leetcode.com/problems/basic-calculator/) - Similar stack-based parsing
- [Simplify Path](https://leetcode.com/problems/simplify-path/) - Stack for path processing
- [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) - Stack for matching brackets

