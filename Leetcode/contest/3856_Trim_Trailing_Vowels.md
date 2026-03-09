# 3856. Trim Trailing Vowels

## Problem Description

Given a string `s`, remove all vowels that appear at the **end** of the string and return the remaining string.

A vowel is one of: `a, e, i, o, u` (case-insensitive).

### Example 1

Input: `s = "leetcodeae"`

Output: `"leetcod"`

Explanation: The trailing `a` and `e` are vowels and are removed until we hit `d`.

### Example 2

Input: `s = "AEIOU"`

Output: `""`

Explanation: Every character is a vowel, so the full string is trimmed.

## Intuition/Main Idea

If we only care about **trailing** vowels, we do not need to touch the beginning or middle part of the string.

### Build the intuition step by step

1. The characters that may be removed are only at the right end.
2. So start from the last index and keep moving left.
3. Stop as soon as we find the first non-vowel character.
4. The answer is the prefix from index `0` to that position.

### Why this intuition works

This is correct because trailing vowels form one contiguous suffix at the end. Once a non-vowel is found, everything to its left must stay unchanged. So a single reverse scan is enough.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Handle empty/null input safely | `if (input == null || input.isEmpty()) return "";` |
| Remove only trailing vowels | `while (rightIndex >= 0 && isVowel(input.charAt(rightIndex)))` |
| Keep non-trailing portion unchanged | `return input.substring(0, rightIndex + 1);` |
| Treat uppercase and lowercase vowels the same | `switch (Character.toLowerCase(character))` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Two Pointers / Reverse Scan]
class Solution {
    public String trimTrailingVowels(String input) {
        // Defensive check: no characters means nothing to trim.
        if (input == null || input.isEmpty()) {
            return "";
        }

        // Start from the last character because only trailing vowels can be removed.
        int rightIndex = input.length() - 1;

        // Keep moving left while we are still on trailing vowels.
        while (rightIndex >= 0 && isVowel(input.charAt(rightIndex))) {
            rightIndex--;
        }

        // If all characters were vowels, rightIndex becomes -1 and substring(0, 0) returns "".
        return input.substring(0, rightIndex + 1);
    }

    private boolean isVowel(char character) {
        // Use lowercase conversion so this works for both uppercase and lowercase input.
        switch (Character.toLowerCase(character)) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            default:
                return false;
        }
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n)$ in the worst case (when the whole string is vowels).
- **Space Complexity:** $O(1)$ extra space.

## Similar Problems

1. [LeetCode 345. Reverse Vowels of a String](https://leetcode.com/problems/reverse-vowels-of-a-string/) - Also revolves around identifying vowels with pointer-based processing.
2. [LeetCode 917. Reverse Only Letters](https://leetcode.com/problems/reverse-only-letters/) - Similar pointer movement idea with selective character handling.
