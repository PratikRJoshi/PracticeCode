# Reverse Letters Then Special Characters in a String

## Problem Description

**Problem Link:** [Reverse Letters Then Special Characters in a String](https://leetcode.com/problems/reverse-letters-then-special-characters-in-a-string/)

You are given a string `s` consisting of lowercase English letters and special characters.

Perform these operations **in order**:

1. Reverse the lowercase letters and place them back into the positions originally occupied by letters.
2. Reverse the special characters and place them back into the positions originally occupied by special characters.

Return the resulting string.

**Example 1:**
```
Input: s = ")ebc#da@f("
Output: "(fad@cb#e)"
Explanation:
Letters: ['e','b','c','d','a','f'] -> reversed -> ['f','a','d','c','b','e']
After placing letters back: ")fad#cb@e("
Specials: [')','#','@','('] -> reversed -> ['(','@','#',')']
After placing specials back: "(fad@cb#e)"
```

**Example 2:**
```
Input: s = "z"
Output: "z"
```

**Example 3:**
```
Input: s = "!@#$%^&*()"
Output: ")(*&^%$#@!"
```

**Constraints:**
- `1 <= s.length <= 100`
- `s` consists only of lowercase English letters and the special characters in `"!@#$%^&*()"`.

## Intuition/Main Idea

### A simple way to think about the problem

Every index in the string is one of two types:

- a **letter position** (where `s[i]` is in `'a'..'z'`)
- a **special-character position** (everything else)

The problem says:

- reverse the sequence of letters, but keep them only in the original letter slots
- reverse the sequence of specials, but keep them only in the original special slots

So the simplest solution is:

1. Scan the string once and collect:
   - all letters in a list
   - all special characters in a list
2. Reverse both lists.
3. Scan the string again:
   - if this index was a letter originally, take the next item from the reversed letters list
   - otherwise, take the next item from the reversed specials list

### Why your two-pointer code fails

In your first loop (“reverse all alphabets”), this condition is wrong:

```java
} else if(reverse[start] < 'a' || reverse[start] < 'z'){
    start++;
}
```

- `reverse[start] < 'z'` is true for **almost every lowercase letter** (`'a'..'y'`) and also for many special characters.
- So the `start++` branch runs when it shouldn’t, causing you to skip valid letters and never swap correctly.

What you intended was:

- “start is NOT a letter” which should be:
  - `reverse[start] < 'a' || reverse[start] > 'z'`

Also, for an empty string, returning `null` is usually not desired in LeetCode string problems; returning `""` (or just handling constraints) is safer.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Reverse letters and put them back only in letter positions | Collect letters, reverse, and fill back on letter slots (lines 14-33) |
| Reverse special characters and put them back only in special positions | Collect specials, reverse, and fill back on special slots (lines 18-38) |
| Handle circular-free string positions (fixed indices) | Second pass reconstructs result based on original character type (lines 27-41) |
| Constraints up to length 100 | Uses O(n) scans and O(n) extra storage (overall) |

## Final Java Code & Learning Pattern (Full Content)

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public String reverseByType(String s) {
        // Constraints guarantee length >= 1, but this keeps the method safe.
        if (s == null) {
            return null;
        }

        int n = s.length();

        // Step 1: collect letters and special characters separately.
        List<Character> letters = new ArrayList<>();
        List<Character> specials = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            char currentChar = s.charAt(i);
            if (currentChar >= 'a' && currentChar <= 'z') {
                letters.add(currentChar);
            } else {
                specials.add(currentChar);
            }
        }

        // Step 2: we want the reversed order, so we'll read from the end.
        int letterIndex = letters.size() - 1;
        int specialIndex = specials.size() - 1;

        // Step 3: rebuild the answer.
        // If the original position was a letter slot, place the next reversed letter.
        // Otherwise, place the next reversed special character.
        StringBuilder result = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            char originalChar = s.charAt(i);

            if (originalChar >= 'a' && originalChar <= 'z') {
                result.append(letters.get(letterIndex));
                letterIndex--;
            } else {
                result.append(specials.get(specialIndex));
                specialIndex--;
            }
        }

        return result.toString();
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ (two passes through the string)

**Space Complexity:** $O(n)$ (store letters + specials, plus output)

## Similar Problems

- [Reverse Vowels of a String](https://leetcode.com/problems/reverse-vowels-of-a-string/) - reverse only certain character positions
- [Reverse Only Letters](https://leetcode.com/problems/reverse-only-letters/) - reverse letters while leaving non-letters in place
- [Valid Palindrome](https://leetcode.com/problems/valid-palindrome/) - character classification during scanning
