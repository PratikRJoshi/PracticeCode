# Valid Parenthesis String

## Problem Description

**Problem Link:** [Valid Parenthesis String](https://leetcode.com/problems/valid-parenthesis-string/)

Given a string `s` containing only three types of characters: `'('`, `')'` and `'*'`, return `true` *if `s` is **valid***.

The following rules define a **valid** string:

- Any left parenthesis `'('` must have a corresponding right parenthesis `')'`.
- Any right parenthesis `')'` must have a corresponding left parenthesis `'('`.
- Left parenthesis `'('` must go before the corresponding right parenthesis `')'`.
- `'*'` could be treated as a single right parenthesis `')'`, a single left parenthesis `'('`, or an empty string.

**Example 1:**
```
Input: s = "()"
Output: true
```

**Example 2:**
```
Input: s = "(*)"
Output: true
```

**Example 3:**
```
Input: s = "(*))"
Output: true
```

**Constraints:**
- `1 <= s.length <= 100`
- `s[i]` is `'('`, `')'`, or `'*'`.

## Intuition/Main Idea

This problem extends valid parentheses by allowing `'*'` to be treated as `'('`, `')'`, or empty. We can use a **greedy approach** with two counters.

**Core Algorithm:**
1. Track the range of possible open parentheses count `[minOpen, maxOpen]`.
2. `minOpen`: minimum possible open parentheses (treat `'*'` as `')'`).
3. `maxOpen`: maximum possible open parentheses (treat `'*'` as `'('`).
4. For each character:
   - `'('`: increment both counters.
   - `')'`: decrement both counters.
   - `'*'`: decrement min, increment max (can be `'('`, `')'`, or empty).
5. If `maxOpen < 0`, invalid. If `minOpen < 0`, reset to 0 (can't be negative).
6. At end, check if `minOpen == 0`.

**Why greedy works:** We maintain the range of possible open parentheses. As long as the range is valid (min <= 0 <= max), the string can be valid.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Track minimum open count | `minOpen` variable - Line 5 |
| Track maximum open count | `maxOpen` variable - Line 6 |
| Handle '(' character | Increment both - Lines 9-10 |
| Handle ')' character | Decrement both - Lines 11-12 |
| Handle '*' character | Decrement min, increment max - Lines 13-14 |
| Check validity | Range checks - Lines 15-18 |
| Return result | Return statement - Line 21 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public boolean checkValidString(String s) {
        int minOpen = 0;  // Minimum possible open parentheses (treat '*' as ')')
        int maxOpen = 0;  // Maximum possible open parentheses (treat '*' as '(')
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                minOpen++;
                maxOpen++;
            } else if (c == ')') {
                minOpen--;
                maxOpen--;
            } else {  // c == '*'
                minOpen--;  // Can be ')'
                maxOpen++;  // Can be '('
            }
            
            // If maxOpen < 0, impossible to balance
            if (maxOpen < 0) {
                return false;
            }
            
            // minOpen can't be negative (we can choose '*' as empty)
            minOpen = Math.max(minOpen, 0);
        }
        
        // Valid if minOpen == 0 (can balance with some choice of '*')
        return minOpen == 0;
    }
}
```

**Explanation of Key Code Sections:**

1. **Counters Initialization (Lines 5-6):** 
   - `minOpen`: Treat all `'*'` as `')'` (minimum open count).
   - `maxOpen`: Treat all `'*'` as `'('` (maximum open count).

2. **Process Each Character (Lines 8-19):**
   - **'(' (Lines 9-11):** Increment both counters (definitely opens a parenthesis).
   - **')' (Lines 12-14):** Decrement both counters (definitely closes a parenthesis).
   - **'*' (Lines 15-17):** 
     - Decrement `minOpen` (can be `')'`).
     - Increment `maxOpen` (can be `'('`).

3. **Validity Checks (Lines 19-23):**
   - **Max Check (Lines 19-21):** If `maxOpen < 0`, we have more `')'` than possible `'('`, so invalid.
   - **Min Reset (Line 23):** If `minOpen < 0`, we can treat some `'*'` as empty to reset it to 0.

4. **Final Check (Line 26):** If `minOpen == 0`, we can balance the string with some choice of `'*'`.

**Why this works:**
- **Range tracking:** `[minOpen, maxOpen]` represents all possible open counts.
- **Validity:** As long as `0` is in the range `[minOpen, maxOpen]`, the string can be valid.
- **Greedy:** We make the most conservative choices (min) and most liberal choices (max) and check if they overlap.

**Example walkthrough for `s = "(*))"`:**
- `'('`: minOpen=1, maxOpen=1
- `'*'`: minOpen=0, maxOpen=2 (can be '(', ')', or empty)
- `')'`: minOpen=-1→0, maxOpen=1 (reset minOpen to 0)
- `')'`: minOpen=-1→0, maxOpen=0
- Final: minOpen=0 ✓ (valid)

**Alternative Approach (Two-Pass):**

```java
class Solution {
    public boolean checkValidString(String s) {
        // Left to right: treat '*' as '('
        int balance = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '*') {
                balance++;
            } else {
                balance--;
            }
            if (balance < 0) return false;
        }
        
        // Right to left: treat '*' as ')'
        balance = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ')' || s.charAt(i) == '*') {
                balance++;
            } else {
                balance--;
            }
            if (balance < 0) return false;
        }
        
        return true;
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the length of the string. We process each character once.

- **Space Complexity:** $O(1)$ as we only use a constant amount of extra space.

## Similar Problems

Problems that can be solved using similar greedy or stack patterns:

1. **678. Valid Parenthesis String** (this problem) - Greedy with wildcards
2. **20. Valid Parentheses** - Stack-based validation
3. **22. Generate Parentheses** - Backtracking generation
4. **32. Longest Valid Parentheses** - DP or stack
5. **301. Remove Invalid Parentheses** - Backtracking removal
6. **921. Minimum Add to Make Parentheses Valid** - Greedy counting
7. **1249. Minimum Remove to Make Valid Parentheses** - Stack removal
8. **1021. Remove Outermost Parentheses** - Stack parsing
9. **856. Score of Parentheses** - Stack calculation
10. **1190. Reverse Substrings Between Each Pair of Parentheses** - Stack reversal

