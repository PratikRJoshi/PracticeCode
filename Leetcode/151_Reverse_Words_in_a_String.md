### 151. Reverse Words in a String
### Problem Link: [Reverse Words in a String](https://leetcode.com/problems/reverse-words-in-a-string/)
### Intuition
This problem asks us to reverse the order of words in a string while handling multiple spaces and trimming leading/trailing spaces. The key insight is to split the string into words, reverse their order, and then join them back with a single space between each word.

### Java Reference Implementation
```java
class Solution {
    public String reverseWords(String s) {
        // Handle edge cases
        if (s == null || s.isEmpty()) {
            return "";
        }
        
        // Split the string by one or more spaces
        String[] words = s.trim().split("\\s+");
        
        // Reverse the array of words
        StringBuilder result = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            result.append(words[i]);
            if (i > 0) {
                result.append(" ");
            }
        }
        
        return result.toString();
    }
}
```

### Alternative Implementation (Using a Stack)
```java
import java.util.Stack;

class Solution {
    public String reverseWords(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        
        // Trim and split the string
        String[] words = s.trim().split("\\s+");
        
        // Use a stack to reverse the order
        Stack<String> stack = new Stack<>();
        for (String word : words) {
            stack.push(word);
        }
        
        // Build the result string
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
            if (!stack.isEmpty()) {
                result.append(" ");
            }
        }
        
        return result.toString();
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (s == null || s.isEmpty()) { return ""; }`
- **R1 (Trim and split)**: `String[] words = s.trim().split("\\s+");` - Remove leading/trailing spaces and split by one or more spaces
- **R2 (Reverse words)**: Either iterate from the end of the array or use a stack
- **R3 (Join with single spaces)**: Append each word followed by a space, except for the last word

### Complexity
- **Time Complexity**: O(n) - We process each character in the string once
- **Space Complexity**: O(n) - We need space to store the split words and the result string
