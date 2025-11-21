# Simplify Path

## Problem Description

**Problem Link:** [Simplify Path](https://leetcode.com/problems/simplify-path/)

Given a string `path`, which is an absolute path (starting with a slash `'/'`) to a file or directory in a Unix-style file system, convert it to the simplified **canonical path**.

In a Unix-style file system, a period `'.'` refers to the current directory, a double period `'..'` refers to the directory up a level, and any multiple consecutive slashes (i.e. `'//'`) are treated as a single slash `'/'`. For this problem, any other format of periods such as `'...'` are treated as file/directory names.

The **canonical path** should have the following format:

- The path starts with a single slash `'/'`.
- Any two directories are separated by a single slash `'/'`.
- The path does not end with a trailing `'/'` unless the path is the root directory.
- The path only contains the directories on the path from the root directory to the target file or directory (i.e., no period `'.'` or double period `'..'`)

Return *the simplified **canonical path***.

**Example 1:**
```
Input: path = "/home/"
Output: "/home"
Explanation: Note that there is no trailing slash after the last directory name.
```

**Example 2:**
```
Input: path = "/../"
Output: "/"
Explanation: Going one level up from the root directory is a no-op, as the root level is the highest level you can go.
```

**Example 3:**
```
Input: path = "/home//foo/"
Output: "/home/foo"
Explanation: In the canonical path, multiple consecutive slashes are replaced by a single one.
```

**Constraints:**
- `1 <= path.length <= 3000`
- `path` consists of English letters, digits, period `'.'`, slash `'/'` or `'_'`.
- `path` is a valid absolute Unix path.

## Intuition/Main Idea

This is a stack problem. We process the path components and use a stack to handle directory navigation.

**Core Algorithm:**
- Split path by `/`
- Process each component:
  - If `".."`, pop from stack (go up one level)
  - If `"."` or empty, ignore (current directory or multiple slashes)
  - Otherwise, push to stack (valid directory)
- Reconstruct path from stack

**Why stack:** When we encounter `".."`, we need to remove the last directory added. A stack naturally handles this LIFO behavior for directory navigation.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Split path by slashes | split() method - Line 6 |
| Handle ".." (go up) | Stack pop - Line 12 |
| Handle "." (current) | Ignore - Line 14 |
| Handle valid directories | Stack push - Line 16 |
| Reconstruct canonical path | Stack to string - Lines 19-25 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public String simplifyPath(String path) {
        // Split path by slashes
        // This separates directory names and special symbols
        String[] components = path.split("/");
        
        // Stack to store valid directory names
        // We use stack to handle ".." by popping last directory
        Stack<String> stack = new Stack<>();
        
        // Process each component
        for (String component : components) {
            // Skip empty strings (from multiple slashes) and current directory "."
            if (component.isEmpty() || component.equals(".")) {
                continue;
            }
            
            // Handle ".." - go up one directory level
            // Pop from stack if stack is not empty
            if (component.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                // Valid directory name, push to stack
                stack.push(component);
            }
        }
        
        // Reconstruct canonical path from stack
        // Start with root "/"
        StringBuilder result = new StringBuilder("/");
        
        // Join directory names with "/"
        for (int i = 0; i < stack.size(); i++) {
            result.append(stack.get(i));
            // Add "/" between directories, but not after last one
            if (i < stack.size() - 1) {
                result.append("/");
            }
        }
        
        return result.toString();
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the path. We split the path and process each component once.

**Space Complexity:** $O(n)$ for the stack and string array.

## Similar Problems

- [Basic Calculator](https://leetcode.com/problems/basic-calculator/) - Similar stack-based parsing
- [Decode String](https://leetcode.com/problems/decode-string/) - Stack for nested structures
- [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) - Stack for matching

