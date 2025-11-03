### 22. Generate Parentheses
### Problem Link: [Generate Parentheses](https://leetcode.com/problems/generate-parentheses/)
### Intuition
This problem is a classic backtracking problem. The key insight is to track the number of open and closed parentheses as we build the string. We can only add an open parenthesis if we have remaining open parentheses available, and we can only add a closing parenthesis if there are unclosed open parentheses.

### Java Reference Implementation
```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }
    
    private void backtrack(List<String> result, StringBuilder current, int open, int close, int max) {
        // Base case: if the current string has 2*n characters, it's complete
        if (current.length() == max * 2) {
            result.add(current.toString());
            return;
        }
        
        // Add an open parenthesis if we still have some available
        if (open < max) {
            current.append("(");
            backtrack(result, current, open + 1, close, max);
            current.deleteCharAt(current.length() - 1);
        }
        
        // Add a closing parenthesis if it's valid (more open than closed)
        if (close < open) {
            current.append(")");
            backtrack(result, current, open, close + 1, max);
            current.deleteCharAt(current.length() - 1);
        }
    }
}
```

### Requirement → Code Mapping
- **R0 (Initialize result container)**: `List<String> result = new ArrayList<>();`
- **R1 (Backtracking function)**: `backtrack(result, new StringBuilder(), 0, 0, n);`
- **R2 (Base case - complete valid string)**: `if (current.length() == max * 2) { result.add(current.toString()); return; }`
- **R3 (Add open parenthesis)**: `if (open < max) { ... }` - We can add an open parenthesis if we haven't used all n.
- **R4 (Add close parenthesis)**: `if (close < open) { ... }` - We can add a closing parenthesis if there are unclosed open parentheses.
- **R5 (Backtracking)**: After exploring each option, we remove the last character to backtrack.

### Complexity
- **Time Complexity**: O(4^n / √n) - This is the nth Catalan number, which represents the number of valid parenthesis combinations.
- **Space Complexity**: O(n) - The recursion stack can go up to a depth of 2n, and each recursive call uses constant space.
