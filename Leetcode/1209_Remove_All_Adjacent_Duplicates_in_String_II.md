# Remove All Adjacent Duplicates in String II

## Problem Description

**Problem Link:** [Remove All Adjacent Duplicates in String II](https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/)

You are given a string `s` and an integer `k`, a `k` **duplicate removal** consists of choosing `k` adjacent and equal letters from `s` and removing them, causing the left and the right side of the deleted substring to concatenate together.

We repeatedly make `k` **duplicate removals** on `s` until we no longer can.

Return *the final string after all such duplicate removals have been made*. It is guaranteed that the answer is unique.

**Example 1:**
```
Input: s = "abcd", k = 2
Output: "abcd"
Explanation: There's nothing to delete.
```

**Example 2:**
```
Input: s = "deeedbbcccbdaa", k = 3
Output: "aa"
Explanation: 
First delete "eee" and "ccc", get "ddbbbdaa"
Then delete "bbb", get "dddaa"
Finally delete "ddd", get "aa"
```

**Example 3:**
```
Input: s = "pbbcggttciiippooaais", k = 2
Output: "ps"
```

**Constraints:**
- `1 <= s.length <= 10^5`
- `2 <= k <= 10^4`
- `s` only contains lowercase English letters.

## Intuition/Main Idea

This is similar to the previous problem, but we need to track counts of consecutive characters. We use a stack that stores pairs of (character, count).

**Core Algorithm:**
- Use a stack of pairs: (character, count)
- For each character:
  - If stack top has same character, increment count
  - If count reaches k, pop from stack
  - Otherwise, push new entry or update count
- Reconstruct string from stack

**Why stack with counts:** We need to track how many consecutive occurrences of each character we've seen. When count reaches k, we remove them. The stack naturally handles the cascading removals.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Track character counts | Stack of pairs - Lines 5-6, 15-16 |
| Remove k duplicates | Count check and pop - Lines 17-19 |
| Increment count | Count update - Line 14 |
| Reconstruct result | Stack to string - Lines 22-27 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public String removeDuplicates(String s, int k) {
        // Stack to store pairs of (character, count)
        // We need to track consecutive occurrences
        Stack<int[]> stack = new Stack<>();
        
        // Process each character
        for (char ch : s.toCharArray()) {
            // Check if stack is not empty and top character matches current
            if (!stack.isEmpty() && stack.peek()[0] == ch) {
                // Increment count of consecutive occurrences
                stack.peek()[1]++;
                
                // If count reaches k, remove this entry (pop k duplicates)
                if (stack.peek()[1] == k) {
                    stack.pop();
                }
            } else {
                // New character or different character, push with count 1
                stack.push(new int[]{ch, 1});
            }
        }
        
        // Reconstruct string from stack
        StringBuilder result = new StringBuilder();
        for (int[] entry : stack) {
            char ch = (char) entry[0];
            int count = entry[1];
            // Append character 'count' times
            for (int i = 0; i < count; i++) {
                result.append(ch);
            }
        }
        
        return result.toString();
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the string. We process each character once.

**Space Complexity:** $O(n)$ for the stack in worst case.

## Similar Problems

- [Remove All Adjacent Duplicates In String](https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/) - k=2 variant
- [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) - Similar stack-based matching
- [Decode String](https://leetcode.com/problems/decode-string/) - Stack for nested structures

