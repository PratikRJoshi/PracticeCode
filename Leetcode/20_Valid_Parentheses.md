# [20. Valid Parentheses](https://leetcode.com/problems/valid-parentheses/)

Given a string `s` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`, determine if the input string is valid.

An input string is valid if:

1. Open brackets must be closed by the same type of brackets.
2. Open brackets must be closed in the correct order.
3. Every close bracket has a corresponding open bracket of the same type.

**Example 1:**

```
Input: s = "()"
Output: true
```

**Example 2:**

```
Input: s = "()[]{}"
Output: true
```

**Example 3:**

```
Input: s = "(]"
Output: false
```

**Constraints:**

- `1 <= s.length <= 10^4`
- `s` consists of parentheses only `'()[]{}'`.

## Intuition/Main Idea:

This is a classic problem that can be efficiently solved using a stack data structure. The key insight is that parentheses must be properly nested and matched in a Last-In-First-Out (LIFO) order, which is exactly what a stack is designed to handle.

The approach is straightforward:
1. Iterate through each character in the string.
2. If the character is an opening bracket (`(`, `{`, or `[`), push it onto the stack.
3. If the character is a closing bracket (`)`, `}`, or `]`), check if the stack is empty or if the top of the stack matches the corresponding opening bracket.
   - If not, the string is invalid.
   - If yes, pop the opening bracket from the stack and continue.
4. After processing all characters, the stack should be empty for a valid string.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Open brackets must be closed by the same type of brackets | `if (stack.isEmpty() || stack.peek() != getMatchingOpenBracket(c)) return false;` |
| Open brackets must be closed in the correct order | Using a stack to ensure LIFO order |
| Every close bracket has a corresponding open bracket | `if (stack.isEmpty()) return true;` at the end |

## Final Java Code & Learning Pattern:

```java
class Solution {
    public boolean isValid(String s) {
        // Create a stack to keep track of opening brackets
        Stack<Character> stack = new Stack<>();
        
        // Iterate through each character in the string
        for (char c : s.toCharArray()) {
            // If it's an opening bracket, push it onto the stack
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } 
            // If it's a closing bracket
            else {
                // If the stack is empty or the top doesn't match the corresponding opening bracket
                if (stack.isEmpty() || stack.peek() != getMatchingOpenBracket(c)) {
                    return false;
                }
                // Pop the matching opening bracket
                stack.pop();
            }
        }
        
        // The string is valid if and only if the stack is empty
        return stack.isEmpty();
    }
    
    // Helper method to get the matching opening bracket for a closing bracket
    private char getMatchingOpenBracket(char closeBracket) {
        switch (closeBracket) {
            case ')': return '(';
            case '}': return '{';
            case ']': return '[';
            default: return ' '; // Should never reach here given the constraints
        }
    }
}
```

This solution uses a stack to track opening brackets and ensure they are properly matched with closing brackets:

1. We iterate through each character in the string.
2. If we encounter an opening bracket, we push it onto the stack.
3. If we encounter a closing bracket, we check if the stack is empty (which means there's no matching opening bracket) or if the top of the stack doesn't match the expected opening bracket. If either condition is true, the string is invalid.
4. If the closing bracket matches, we pop the opening bracket from the stack and continue.
5. After processing all characters, the string is valid if and only if the stack is empty (all opening brackets have been matched).

The helper method `getMatchingOpenBracket` simply returns the corresponding opening bracket for a given closing bracket.

## Alternative Implementation:

We could also use a HashMap to store the mapping between opening and closing brackets, which might make the code more concise:

```java
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> bracketPairs = new HashMap<>();
        bracketPairs.put(')', '(');
        bracketPairs.put('}', '{');
        bracketPairs.put(']', '[');
        
        for (char c : s.toCharArray()) {
            // If it's a closing bracket
            if (bracketPairs.containsKey(c)) {
                // Check if stack is empty or top doesn't match
                if (stack.isEmpty() || stack.peek() != bracketPairs.get(c)) {
                    return false;
                }
                stack.pop();
            } else {
                // It's an opening bracket
                stack.push(c);
            }
        }
        
        return stack.isEmpty();
    }
}
```

## Complexity Analysis:

- **Time Complexity**: $O(n)$ where n is the length of the string. We iterate through each character exactly once.
- **Space Complexity**: $O(n)$ in the worst case (e.g., for a string like "((((", we would push all characters onto the stack).

## Similar Problems:

1. [22. Generate Parentheses](https://leetcode.com/problems/generate-parentheses/)
2. [32. Longest Valid Parentheses](https://leetcode.com/problems/longest-valid-parentheses/)
3. [678. Valid Parenthesis String](https://leetcode.com/problems/valid-parenthesis-string/)
4. [1249. Minimum Remove to Make Valid Parentheses](https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/)
5. [921. Minimum Add to Make Parentheses Valid](https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/)
