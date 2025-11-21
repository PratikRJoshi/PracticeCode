# Basic Calculator II

## Problem Description

**Problem Link:** [Basic Calculator II](https://leetcode.com/problems/basic-calculator-ii/)

Given a string `s` which represents an expression, evaluate this expression and return its value.

The integer division should **truncate toward zero**.

You may assume that the given expression is always valid. All intermediate results will be in the range of `[-2^31, 2^31 - 1]`.

**Note:** You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as `eval()`.

**Example 1:**
```
Input: s = "3+2*2"
Output: 7
```

**Example 2:**
```
Input: s = " 3/2 "
Output: 1
```

**Example 3:**
```
Input: s = " 3+5 / 2 "
Output: 5
```

**Constraints:**
- `1 <= s.length <= 3 * 10^5`
- `s` consists of integers and operators `('+', '-', '*', '/')` separated by some number of spaces.
- `s` represents a valid expression.
- All the integers in the expression are non-negative integers in the range `[0, 2^31 - 1]`.
- The answer is guaranteed to fit in a **32-bit integer**.

## Intuition/Main Idea

We need to handle operator precedence: multiplication and division have higher precedence than addition and subtraction.

**Core Algorithm:**
- Process expression left to right
- Use a stack to store numbers
- When we see `+` or `-`, push number with sign
- When we see `*` or `/`, immediately evaluate with previous number
- Track current number and last operator

**Why stack:** We need to defer addition/subtraction until we've processed all multiplication/division. However, a simpler approach processes immediately: track current number and last operator, evaluate `*` and `/` immediately, and handle `+` and `-` by pushing to stack with sign.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Parse numbers | Digit accumulation - Lines 15-17 |
| Handle operators | Operator processing - Lines 18-30 |
| Evaluate * and / immediately | Immediate evaluation - Lines 22-28 |
| Handle + and - | Push to stack - Lines 19-20, 29-30 |
| Sum all values | Final sum - Lines 32-34 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int calculate(String s) {
        // Remove all spaces for easier processing
        s = s.replaceAll(" ", "");
        
        // Stack to store numbers (with signs for + and -)
        Stack<Integer> stack = new Stack<>();
        
        // Track current number and last operator
        int currentNumber = 0;
        char operator = '+';
        
        // Process each character
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            
            // If digit, build the number
            if (Character.isDigit(ch)) {
                currentNumber = currentNumber * 10 + (ch - '0');
            }
            
            // If operator or last character, process previous number
            if (!Character.isDigit(ch) || i == s.length() - 1) {
                // Handle based on last operator
                if (operator == '+') {
                    // Push positive number
                    stack.push(currentNumber);
                } else if (operator == '-') {
                    // Push negative number (subtraction)
                    stack.push(-currentNumber);
                } else if (operator == '*') {
                    // Multiply with top of stack immediately
                    stack.push(stack.pop() * currentNumber);
                } else if (operator == '/') {
                    // Divide top of stack by current number immediately
                    stack.push(stack.pop() / currentNumber);
                }
                
                // Update operator and reset current number
                operator = ch;
                currentNumber = 0;
            }
        }
        
        // Sum all values in stack
        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        
        return result;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the string. We process each character once.

**Space Complexity:** $O(n)$ for the stack in worst case (all additions/subtractions).

## Similar Problems

- [Basic Calculator](https://leetcode.com/problems/basic-calculator/) - Handles parentheses
- [Decode String](https://leetcode.com/problems/decode-string/) - Similar parsing pattern
- [Evaluate Reverse Polish Notation](https://leetcode.com/problems/evaluate-reverse-polish-notation/) - Stack-based evaluation

