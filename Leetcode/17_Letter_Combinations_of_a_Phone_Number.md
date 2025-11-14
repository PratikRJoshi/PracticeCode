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
| Map digits to letters | 2D char array - Lines 3-14 |
| Store current combination | StringBuilder - Line 22 |
| Process each digit | DFS method - Lines 25-38 |
| Try all letters for current digit | For loop - Line 33 |
| Add letter and recurse | Append and recursive call - Lines 34-35 |
| Backtrack by removing letter | Delete last character - Line 36 |
| Add complete combination | Result addition - Line 27 |
| Return all combinations | Return statement - Line 23 |

## Final Java Code & Learning Pattern

```java
class Solution {
    private String[] map = {
            "",
            "",
            "abc",
            "def",
            "ghi",
            "jkl",
            "mno",
            "pqrs",
            "tuv",
            "wxyz"
    };

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if(digits == null || digits.length() == 0){
            return result;
        }

        generate(digits, map, new StringBuilder(), result);
        return result;
    }

    private void generate(String digits, String[] map, StringBuilder sb, List<String> result){
        if(sb.length() == digits.length()){
            result.add(sb.toString());
            return;
        }

        int digit = digits.charAt(sb.length()) - '0';
        for(char letter : map[digit].toCharArray()){
            sb.append(letter);
            generate(digits, map, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
```

**Explanation of Key Code Sections:**

1. **Digit Mapping (Lines 3-14):** We create a 2D char array mapping each digit (0-9) to its corresponding letters. Digits 0 and 1 have empty arrays since they don't map to any letters.

2. **Base Case Check (Lines 16-19):** Handle the edge case of empty input.

3. **DFS Method (Lines 25-38):**
   - **Base Case (Lines 26-29):** When the StringBuilder length equals the digits length, we have a complete combination. Add it to results.
   - **Get Current Digit (Line 31):** Determine which digit to process based on the current StringBuilder length.
   - **Try Each Letter (Lines 32-37):** For each possible letter of the current digit:
     - **Choose (Line 34):** Add the letter to current combination.
     - **Explore (Line 35):** Recursively process the next digit.
     - **Unchoose (Line 36):** Remove the letter (backtrack) to try the next option.

**Key Differences in This Implementation:**

1. **2D Char Array vs String Array:**
   - Uses a 2D char array instead of an array of strings
   - Direct access to characters without converting from strings

2. **DFS vs Backtrack Naming:**
   - Uses `dfs()` method name instead of `backtrack()`
   - Same algorithm, different terminology

3. **Position Tracking:**
   - Uses `sb.length()` to track position instead of a separate index parameter
   - This elegantly ties the current position to the length of the partial solution

**Example walkthrough for `digits = "23"`:**
- Start: sb=""
- Digit 2: Try 'a' → sb="a", recurse
  - Digit 3: Try 'd' → sb="ad" → add "ad"
  - Backtrack: sb="a", try 'e' → sb="ae" → add "ae"
  - Backtrack: sb="a", try 'f' → sb="af" → add "af"
- Backtrack: sb="", try 'b' → sb="b", recurse...
- Continue for 'c'...

## Complexity Analysis

- **Time Complexity:** $O(4^n \times n)$ where $n$ is the number of digits. In the worst case, each digit has 4 letters (7), so we have $4^n$ combinations, and each combination takes $O(n)$ to build.

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
