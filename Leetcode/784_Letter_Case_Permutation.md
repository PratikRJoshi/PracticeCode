# Letter Case Permutation

## Problem Description

**Problem Link:** [Letter Case Permutation](https://leetcode.com/problems/letter-case-permutation/)

Given a string `s`, you can transform every letter individually to be lowercase or uppercase to create another string.

Return *a list of all possible strings we could create*. Return the output in **any order**.

**Example 1:**

```
Input: s = "a1b2"
Output: ["a1b2","a1B2","A1b2","A1B2"]
```

**Example 2:**

```
Input: s = "3z4"
Output: ["3z4","3Z4"]
```

**Constraints:**
- `1 <= s.length <= 12`
- `s` consists of lowercase letters, uppercase letters, and digits.

## Intuition/Main Idea

This is a **backtracking** problem. We need to generate all permutations by changing letter cases.

**Core Algorithm:**
1. For each character:
   - If digit: keep as is.
   - If letter: try both lowercase and uppercase.
2. Build strings recursively.
3. When all characters processed, add to result.

**Why backtracking works:** We systematically explore all possible case combinations for letters, keeping digits unchanged.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Backtrack function | Backtrack method - Lines 6-20 |
| Base case: all processed | Index check - Lines 8-11 |
| Check if letter | Character check - Line 14 |
| Try lowercase | Lowercase branch - Lines 15-16 |
| Try uppercase | Uppercase branch - Lines 17-18 |
| Handle digit | Digit branch - Line 20 |
| Return result | Return statement - Line 24 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    public List<String> letterCasePermutation(String s) {
        List<String> result = new ArrayList<>();
        backtrack(s, 0, new StringBuilder(), result);
        return result;
    }
    
    private void backtrack(String s, int index, StringBuilder current, List<String> result) {
        // Base case: all characters processed
        if (index == s.length()) {
            result.add(current.toString());
            return;
        }
        
        char ch = s.charAt(index);
        if (Character.isLetter(ch)) {
            // Try lowercase
            current.append(Character.toLowerCase(ch));
            backtrack(s, index + 1, current, result);
            current.deleteCharAt(current.length() - 1);
            
            // Try uppercase
            current.append(Character.toUpperCase(ch));
            backtrack(s, index + 1, current, result);
            current.deleteCharAt(current.length() - 1);
        } else {
            // Digit: keep as is
            current.append(ch);
            backtrack(s, index + 1, current, result);
            current.deleteCharAt(current.length() - 1);
        }
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Lines 8-11):** When all characters processed, add current string to result.

2. **Handle Letter (Lines 14-18):** For letters:
   - **Try Lowercase (Lines 15-16):** Add lowercase version and recurse.
   - **Try Uppercase (Lines 17-18):** Add uppercase version and recurse.
   - **Backtrack:** Remove character after each branch.

3. **Handle Digit (Lines 19-21):** For digits, keep as is and recurse.

**Why backtracking:**
- We need to explore all case combinations.
- After trying one case, we backtrack and try the other.
- This ensures we generate all permutations.

**Example walkthrough for `s = "a1b2"`:**
- Process 'a': try 'a' → process '1' → process 'b' → process '2' → "a1b2" ✓
- Backtrack: try 'A' → "A1b2" ✓
- Backtrack 'b': try 'B' → "a1B2" ✓, "A1B2" ✓
- Result: ["a1b2","a1B2","A1b2","A1B2"] ✓

## Complexity Analysis

- **Time Complexity:** $O(2^l \times n)$ where $l$ is the number of letters and $n$ is the string length. We generate $2^l$ permutations, each taking $O(n)$ time.

- **Space Complexity:** $O(n)$ for recursion stack and current string.

## Similar Problems

Problems that can be solved using similar backtracking patterns:

1. **784. Letter Case Permutation** (this problem) - Backtracking with case changes
2. **46. Permutations** - Generate permutations
3. **47. Permutations II** - With duplicates
4. **78. Subsets** - Generate subsets
5. **90. Subsets II** - With duplicates
6. **77. Combinations** - Generate combinations
7. **17. Letter Combinations of a Phone Number** - Phone keypad
8. **22. Generate Parentheses** - Generate parentheses

