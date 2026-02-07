# Excel Sheet Column Number

## Problem Description

**Problem Link:** [Excel Sheet Column Number](https://leetcode.com/problems/excel-sheet-column-number/)

Given a string `columnTitle` that represents the column title as it appears in an Excel sheet, return its corresponding column number.

Mapping is like:

- `A -> 1`
- `B -> 2`
- ...
- `Z -> 26`
- `AA -> 27`
- `AB -> 28`
- ...

**Example 1:**
```
Input: columnTitle = "A"
Output: 1
```

**Example 2:**
```
Input: columnTitle = "AB"
Output: 28
```

**Example 3:**
```
Input: columnTitle = "ZY"
Output: 701
```

**Constraints:**
- `1 <= columnTitle.length <= 7`
- `columnTitle` consists only of uppercase English letters.
- `columnTitle` is in the range `["A", "FXSHRXW"]`.

## Intuition/Main Idea

This is exactly like converting a number from a base system into decimal — except Excel columns are a **1-indexed base-26 system**.

### Why it’s “base-26 but 1-indexed”

In normal base-26, digits would be `0..25`.

But Excel uses:

- `A..Z` to represent `1..26` (not `0..25`).

So each character contributes a value:

- `A -> 1`, `B -> 2`, ..., `Z -> 26`

### Building the number left to right

When you read from left to right, you do the same thing as parsing base-10:

- Start with `result = 0`
- For each character `ch`:
  - Convert it to a digit value `digit = ch - 'A' + 1`
  - Shift the previous value one “place” to the left: `result = result * 26`
  - Add the new digit: `result += digit`

Example: `"AB"`

- Start `result = 0`
- `'A'` -> 1: `result = 0*26 + 1 = 1`
- `'B'` -> 2: `result = 1*26 + 2 = 28`

This works because each new letter is the next base-26 digit.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Map Excel title (A..Z, AA..) to column number | Base-26 accumulation `result = result * 26 + digit` (lines 10-20) |
| Uppercase letters only | Digit computation via `ch - 'A' + 1` (lines 14-16) |
| `length <= 7` ensures fits in int | Use `int` accumulation (lines 9-20) |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int titleToNumber(String columnTitle) {
        int result = 0;

        // Parse like a base-26 number, except A..Z represent 1..26.
        for (int i = 0; i < columnTitle.length(); i++) {
            char ch = columnTitle.charAt(i);

            // Convert character to its 1-based digit value.
            // 'A' -> 1, 'B' -> 2, ..., 'Z' -> 26
            int digit = ch - 'A' + 1;

            // Shift previous digits left (multiply by base) and add new digit.
            result = result * 26 + digit;
        }

        return result;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(L)$ where `L = columnTitle.length()`

**Space Complexity:** $O(1)$

## Similar Problems

- [Excel Sheet Column Title](https://leetcode.com/problems/excel-sheet-column-title/) - Reverse mapping (number -> title)
- [Base 7](https://leetcode.com/problems/base-7/) - Base conversion pattern
- [Convert a Number to Hexadecimal](https://leetcode.com/problems/convert-a-number-to-hexadecimal/) - Base conversion pattern
