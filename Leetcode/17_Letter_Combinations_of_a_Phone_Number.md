# Letter Combinations of a Phone Number

## Problem Description

**Problem Link:** [Letter Combinations of a Phone Number](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)

Given a string containing digits from `2-9` inclusive, return all possible letter combinations that the number could represent. Return the answer in **any order**.

A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.

```
2 -> "abc"
3 -> "def"
4 -> "ghi"
5 -> "jkl"
6 -> "mno"
7 -> "pqrs"
8 -> "tuv"
9 -> "wxyz"
```

**Example 1:**
```
Input: digits = "23"
Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

**Example 2:**
```
Input: digits = ""
Output: []
```

**Example 3:**
```
Input: digits = "2"
Output: ["a","b","c"]
```

**Constraints:**
- `0 <= digits.length <= 4`
- `digits[i]` is a digit in the range `['2', '9']`.

## Intuition/Main Idea

This is a classic **backtracking** problem. We need to generate all possible combinations by choosing one letter from each digit's mapping.

**Core Algorithm:**
1. Use backtracking to build combinations character by character.
2. For each digit, try all possible letters it can represent.
3. When we've processed all digits, add the current combination to results.
4. Backtrack by removing the last added character and try the next option.

**Why backtracking works:** We need to explore all possible paths (combinations). Backtracking systematically explores all possibilities by building partial solutions and undoing choices when needed.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Map digits to letters | String array - Lines 5-6 |
| Store current combination | StringBuilder - Line 12 |
| Process each digit | Backtrack method - Lines 14-28 |
| Try all letters for current digit | For loop - Line 20 |
| Add letter and recurse | Append and recursive call - Lines 21-23 |
| Backtrack by removing letter | Delete last character - Line 24 |
| Add complete combination | Result addition - Line 18 |
| Return all combinations | Return statement - Line 11 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    // Mapping of digits to letters
    private static final String[] DIGIT_TO_LETTERS = {
        "",     // 0
        "",     // 1
        "abc",  // 2
        "def",  // 3
        "ghi",  // 4
        "jkl",  // 5
        "mno",  // 6
        "pqrs", // 7
        "tuv",  // 8
        "wxyz"  // 9
    };
    
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        
        // Handle empty input
        if (digits == null || digits.length() == 0) {
            return result;
        }
        
        // Backtrack to build combinations
        backtrack(digits, 0, new StringBuilder(), result);
        return result;
    }
    
    private void backtrack(String digits, int index, StringBuilder current, List<String> result) {
        // Base case: processed all digits
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }
        
        // Get current digit and its corresponding letters
        int digit = digits.charAt(index) - '0';
        String letters = DIGIT_TO_LETTERS[digit];
        
        // Try each letter for current digit
        for (char letter : letters.toCharArray()) {
            // Choose: add current letter
            current.append(letter);
            
            // Explore: recurse for next digit
            backtrack(digits, index + 1, current, result);
            
            // Unchoose: remove last letter (backtrack)
            current.deleteCharAt(current.length() - 1);
        }
    }
}
```

**Explanation of Key Code Sections:**

1. **Digit Mapping (Lines 5-13):** We create a static array mapping each digit (0-9) to its corresponding letters. Digits 0 and 1 map to empty strings.

2. **Base Case Check (Lines 16-19):** Handle the edge case of empty input.

3. **Backtrack Method (Lines 22-38):**
   - **Base Case (Lines 23-26):** When we've processed all digits (`index == digits.length()`), we have a complete combination. Add it to results.
   - **Get Current Letters (Lines 28-29):** Extract the current digit and get its corresponding letters.
   - **Try Each Letter (Lines 31-37):** For each possible letter:
     - **Choose (Line 32):** Add the letter to current combination.
     - **Explore (Line 35):** Recursively process the next digit.
     - **Unchoose (Line 37):** Remove the letter (backtrack) to try the next option.

**Why backtracking works:**
- **Systematic exploration:** We try all letters for digit 1, then all letters for digit 2, etc.
- **Efficient:** We build combinations incrementally, avoiding duplicate work.
- **Complete:** We explore all possible paths, ensuring we find all combinations.

**Example walkthrough for `digits = "23"`:**
- Start: index=0, current=""
- Digit 2: Try 'a' → current="a", recurse with index=1
  - Digit 3: Try 'd' → current="ad", index=2 → add "ad"
  - Backtrack: current="a", try 'e' → current="ae" → add "ae"
  - Backtrack: current="a", try 'f' → current="af" → add "af"
- Backtrack: current="", try 'b' → current="b", recurse...
- Continue for 'c'...

## Complexity Analysis

- **Time Complexity:** $O(4^n \times n)$ where $n$ is the number of digits. In the worst case, each digit has 4 letters (7 and 9), so we have $4^n$ combinations, and each combination takes $O(n)$ to build.

- **Space Complexity:** $O(n)$ for the recursion stack and the current combination string. The result list takes $O(4^n \times n)$ space.

## Similar Problems

Problems that can be solved using similar backtracking patterns:

1. **17. Letter Combinations of a Phone Number** (this problem) - Backtracking combinations
2. **22. Generate Parentheses** - Backtracking with constraints
3. **39. Combination Sum** - Backtracking with repetition
4. **40. Combination Sum II** - Backtracking with duplicates
5. **46. Permutations** - Backtracking permutations
6. **47. Permutations II** - Backtracking with duplicates
7. **78. Subsets** - Backtracking subsets
8. **90. Subsets II** - Backtracking with duplicates
9. **131. Palindrome Partitioning** - Backtracking partitions
10. **93. Restore IP Addresses** - Backtracking with validation

