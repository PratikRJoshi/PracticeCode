# Evaluate Reverse Polish Notation

## Problem Description

**Problem Link:** [Evaluate Reverse Polish Notation](https://leetcode.com/problems/evaluate-reverse-polish-notation/)

You are given an array of strings `tokens` that represents an arithmetic expression in a **Reverse Polish Notation**.

Evaluate the expression and return *an integer that represents the value of the expression*.

**Note that:**
- The valid operators are `'+'`, `'-'`, `'*'`, and `'/'`.
- Each operand may be an integer or another expression.
- The division between two integers always **truncates toward zero**.
- There will not be any division by zero.
- The input represents a valid arithmetic expression in a reverse polish notation.
- The answer and all the intermediate calculations can be represented in a **32-bit integer**.

**Example 1:**
```
Input: tokens = ["2","1","+","3","*"]
Output: 9
Explanation: ((2 + 1) * 3) = 9
```

**Example 2:**
```
Input: tokens = ["4","13","5","/","+"]
Output: 6
Explanation: (4 + (13 / 5)) = 4 + 2 = 6
```

**Example 3:**
```
Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
Output: 22
Explanation: ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
= ((10 * (6 / (12 * -11))) + 17) + 5
= ((10 * (6 / -132)) + 17) + 5
= ((10 * 0) + 17) + 5
= (0 + 17) + 5
= 22
```

**Constraints:**
- `1 <= tokens.length <= 10^4`
- `tokens[i]` is either an operator: `"+"`, `"-"`, `"*"`, or `"/"`, or an integer in the range `[-200, 200]`.

## Intuition/Main Idea

Reverse Polish Notation (RPN), also known as postfix notation, is a mathematical notation where operators follow their operands. The key advantage is that it doesn't require parentheses to indicate operation precedence.

**Core Algorithm:**
1. Use a **stack** to store operands.
2. When we encounter a number, push it onto the stack.
3. When we encounter an operator, pop the top two operands, perform the operation, and push the result back.
4. After processing all tokens, the stack will contain a single value - the result.

**Why stack works:** RPN processes operations from left to right, and the most recent operands are needed first. A stack's LIFO (Last In First Out) property perfectly matches this requirement.

**Order matters:** For subtraction and division, the order of operands matters. The second popped element is the left operand, and the first popped is the right operand.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Store operands for operations | Stack data structure - Line 5 |
| Process each token | For loop - Line 8 |
| Handle numeric operands | Number parsing and push - Lines 10-12 |
| Handle operators | Operator detection and calculation - Lines 13-30 |
| Perform arithmetic operations | Switch statement - Lines 16-29 |
| Return final result | Stack pop - Line 33 |

## Final Java Code & Learning Pattern

```java
import java.util.Stack;

class Solution {
    public int evalRPN(String[] tokens) {
        // Stack to store operands
        Stack<Integer> stack = new Stack<>();
        
        // Process each token
        for (String token : tokens) {
            // Check if token is a number
            if (!isOperator(token)) {
                // Parse and push the number onto stack
                stack.push(Integer.parseInt(token));
            } else {
                // Token is an operator, pop two operands
                // Note: second popped is left operand, first popped is right operand
                int right = stack.pop();
                int left = stack.pop();
                int result = 0;
                
                // Perform the operation based on operator
                switch (token) {
                    case "+":
                        result = left + right;
                        break;
                    case "-":
                        result = left - right;
                        break;
                    case "*":
                        result = left * right;
                        break;
                    case "/":
                        // Division truncates toward zero (Java integer division does this)
                        result = left / right;
                        break;
                }
                
                // Push the result back onto stack
                stack.push(result);
            }
        }
        
        // Final result is the only element left in stack
        return stack.pop();
    }
    
    // Helper method to check if token is an operator
    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || 
               token.equals("*") || token.equals("/");
    }
}
```

**Explanation of Key Code Sections:**

1. **Stack Initialization (Line 5):** We use a stack to store integer operands. The stack allows us to access the most recent operands first, which is exactly what RPN requires.

2. **Token Processing (Line 8):** We iterate through each token in the array. Each token is either a number or an operator.

3. **Number Handling (Lines 10-12):** If the token is not an operator, it's a number. We parse it as an integer and push it onto the stack. Numbers are stored for later use when we encounter operators.

4. **Operator Handling (Lines 13-30):** When we encounter an operator:
   - **Pop order (Lines 14-15):** We pop two operands. The first popped (`right`) is the right operand, and the second popped (`left`) is the left operand. This order is crucial for subtraction and division.
   - **Operation execution (Lines 16-29):** We use a switch statement to perform the appropriate operation. Java's integer division already truncates toward zero, so no special handling is needed.
   - **Result storage (Line 31):** We push the result back onto the stack, making it available for subsequent operations.

5. **Final Result (Line 33):** After processing all tokens, the stack contains exactly one element - the final result of the expression.

**Why pop order matters:**
- For `["4","13","5","/","+"]`: When we see `/`, stack has `[4, 13, 5]` (top to bottom)
- We pop `5` (right), then `13` (left), so we calculate `13 / 5 = 2`
- Stack becomes `[4, 2]`, then `+` gives `4 + 2 = 6`

**Edge cases handled:**
- Single number: `["42"]` → returns `42`
- Negative numbers: Handled by `Integer.parseInt()`
- Division truncation: Java integer division already truncates toward zero

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the number of tokens. We process each token exactly once.

- **Space Complexity:** $O(n)$ in the worst case when all tokens are numbers (stack stores all operands). In practice, it's $O(n)$ due to the nature of RPN expressions.

## Similar Problems

Problems that can be solved using similar stack-based evaluation techniques:

1. **150. Evaluate Reverse Polish Notation** (this problem) - Stack-based expression evaluation
2. **224. Basic Calculator** - More complex expression evaluation with parentheses
3. **227. Basic Calculator II** - Expression evaluation with +, -, *, /
4. **772. Basic Calculator III** - Expression evaluation with parentheses and operators
5. **394. Decode String** - Stack for nested structure processing
6. **20. Valid Parentheses** - Stack for matching brackets
7. **71. Simplify Path** - Stack for path processing
8. **385. Mini Parser** - Stack for nested structure parsing
9. **726. Number of Atoms** - Stack for chemical formula parsing
10. **439. Ternary Expression Parser** - Stack for ternary operator evaluation

