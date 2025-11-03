### 439. Ternary Expression Parser
### Problem Link: [Ternary Expression Parser](https://leetcode.com/problems/ternary-expression-parser/)
### Intuition
This problem asks us to evaluate a ternary expression string. A ternary expression is in the format `A?B:C`, where A, B, and C can be either a single character or another ternary expression. If A is true (represented as '1' or 'T'), then the result is B; otherwise, the result is C.

One approach is to parse the expression into a ternary expression tree and then evaluate it. Another approach is to use a stack or recursion to directly evaluate the expression without explicitly building a tree.

### Java Reference Implementation (Using Stack)
```java
import java.util.Stack;

class Solution {
    public String parseTernary(String expression) {
        if (expression == null || expression.length() == 0) {
            return "";
        }
        
        Stack<Character> stack = new Stack<>();
        
        // Process the expression from right to left
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);
            
            if (c == ':') {
                // Skip the colon
                continue;
            } else if (c == '?') {
                // When we encounter a '?', we need to evaluate the condition
                // The next character (to the left) is the condition
                char condition = expression.charAt(i - 1);
                i--; // Skip the condition character
                
                char trueResult = stack.pop();
                char falseResult = stack.pop();
                
                if (condition == 'T' || condition == '1') {
                    stack.push(trueResult);
                } else {
                    stack.push(falseResult);
                }
            } else {
                // Push the operand onto the stack
                stack.push(c);
            }
        }
        
        return String.valueOf(stack.pop());
    }
}
```

### Alternative Implementation (Using Recursion)
```java
class Solution {
    private int index;
    
    public String parseTernary(String expression) {
        index = 0;
        return String.valueOf(parseExpression(expression));
    }
    
    private char parseExpression(String expression) {
        // Base case: single character
        if (index >= expression.length() || expression.charAt(index) == ':') {
            return ' ';
        }
        
        // Get the current character
        char c = expression.charAt(index++);
        
        // If it's not a condition (T or F), return it
        if (index >= expression.length() || expression.charAt(index) != '?') {
            return c;
        }
        
        // Skip the '?'
        index++;
        
        // Parse the 'true' expression
        char trueResult = parseExpression(expression);
        
        // Skip the ':'
        index++;
        
        // Parse the 'false' expression
        char falseResult = parseExpression(expression);
        
        // Evaluate the condition
        return (c == 'T' || c == '1') ? trueResult : falseResult;
    }
}
```

### Ternary Expression Tree Implementation
```java
class Node {
    char value;
    Node left;  // True branch
    Node right; // False branch
    
    public Node(char value) {
        this.value = value;
    }
}

class TernaryExpressionTree {
    public static Node buildTree(String expression, int[] index) {
        // Create a node with the current character
        Node root = new Node(expression.charAt(index[0]));
        index[0]++;
        
        // If we've reached the end or the next character is not '?', return the node
        if (index[0] >= expression.length() || expression.charAt(index[0]) != '?') {
            return root;
        }
        
        // Skip the '?'
        index[0]++;
        
        // Build the true branch
        root.left = buildTree(expression, index);
        
        // Skip the ':'
        index[0]++;
        
        // Build the false branch
        root.right = buildTree(expression, index);
        
        return root;
    }
    
    public static char evaluateTree(Node root) {
        if (root.left == null && root.right == null) {
            return root.value;
        }
        
        if (root.value == 'T' || root.value == '1') {
            return evaluateTree(root.left);
        } else {
            return evaluateTree(root.right);
        }
    }
    
    public static String parseTernary(String expression) {
        if (expression == null || expression.isEmpty()) {
            return "";
        }
        
        int[] index = {0};
        Node root = buildTree(expression, index);
        return String.valueOf(evaluateTree(root));
    }
}
```

### Requirement → Code Mapping
- **R0 (Parse expression)**: Build a tree or use a stack/recursion to parse the ternary expression
- **R1 (Evaluate condition)**: Check if the condition is 'T' or '1' to determine which branch to take
- **R2 (Handle nested expressions)**: Recursively parse nested ternary expressions
- **R3 (Return result)**: Return the final evaluated result

### Complexity Analysis
- **Time Complexity**: O(n) - We process each character in the expression once
- **Space Complexity**: O(n) - In the worst case, we might need to store all characters in the stack or recursion stack
