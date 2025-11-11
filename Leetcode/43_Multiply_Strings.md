# Multiply Strings

## Problem Description

**Problem Link:** [Multiply Strings](https://leetcode.com/problems/multiply-strings/)

Given two non-negative integers `num1` and `num2` represented as strings, return the product of `num1` and `num2`, also represented as a string.

**Note:** You must not use any built-in BigInteger library or convert the inputs to integers directly.

**Example 1:**
```
Input: num1 = "2", num2 = "3"
Output: "6"
```

**Example 2:**
```
Input: num1 = "123", num2 = "456"
Output: "56088"
```

**Constraints:**
- `1 <= num1.length, num2.length <= 200`
- `num1` and `num2` consist of digits only.
- Both `num1` and `num2` do not contain any leading zero, except the number 0 itself.

## Intuition/Main Idea

This problem simulates manual multiplication. The key insight is that when we multiply two digits, the result goes into specific positions in the result array.

**Core Algorithm:**
1. Create a result array of size `len1 + len2` (maximum possible length).
2. Multiply each digit of `num1` with each digit of `num2`.
3. Store the result at position `i + j` (tens) and `i + j + 1` (ones).
4. Handle carry propagation.
5. Convert result array to string, removing leading zeros.

**Why this works:** This mimics how we multiply numbers by hand - we multiply each digit pair and add the results at the correct positions, handling carries.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Create result array | Array initialization - Line 6 |
| Multiply digits | Nested loops - Lines 8-15 |
| Calculate product | Product calculation - Line 11 |
| Store result positions | Array indexing - Lines 12-13 |
| Handle carry | Carry propagation - Lines 17-20 |
| Convert to string | StringBuilder - Lines 22-28 |
| Remove leading zeros | Leading zero check - Line 24 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public String multiply(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();
        
        // Result array: maximum length is len1 + len2
        int[] result = new int[len1 + len2];
        
        // Multiply each digit of num1 with each digit of num2
        for (int i = len1 - 1; i >= 0; i--) {
            for (int j = len2 - 1; j >= 0; j--) {
                // Get digits
                int digit1 = num1.charAt(i) - '0';
                int digit2 = num2.charAt(j) - '0';
                
                // Calculate product
                int product = digit1 * digit2;
                
                // Add to result at positions i+j (tens) and i+j+1 (ones)
                int sum = product + result[i + j + 1];
                result[i + j + 1] = sum % 10;  // Ones place
                result[i + j] += sum / 10;      // Tens place (carry)
            }
        }
        
        // Convert result array to string
        StringBuilder sb = new StringBuilder();
        for (int digit : result) {
            // Skip leading zeros
            if (sb.length() != 0 || digit != 0) {
                sb.append(digit);
            }
        }
        
        // Handle case where result is zero
        return sb.length() == 0 ? "0" : sb.toString();
    }
}
```

**Explanation of Key Code Sections:**

1. **Result Array (Line 6):** We create an array of size `len1 + len2` to store intermediate results. This is the maximum possible length of the product.

2. **Nested Loops (Lines 8-15):** We iterate through each digit of `num1` and `num2`:
   - **Process from right to left:** This matches how we do manual multiplication.
   - **Get Digits (Lines 10-11):** Convert character to integer.
   - **Calculate Product (Line 14):** Multiply the two digits.

3. **Store Result (Lines 16-18):** 
   - **Position Logic:** When multiplying digits at positions `i` and `j`, the result affects positions `i+j` (tens) and `i+j+1` (ones).
   - **Add to Existing (Line 16):** Add product to existing value at `i+j+1` (there might be a carry from previous operations).
   - **Store Ones (Line 17):** Store ones digit at `i+j+1`.
   - **Add Carry (Line 18):** Add tens digit (carry) to position `i+j`.

4. **Convert to String (Lines 21-28):**
   - **Skip Leading Zeros (Line 24):** Don't append zeros until we've seen a non-zero digit.
   - **Handle Zero Result (Line 29):** If all digits are zero, return "0".

**Why position `i+j` and `i+j+1`:**
- When multiplying numbers, digit at position `i` in `num1` and position `j` in `num2`:
  - Their product affects the result at position `i+j` (for the tens place) and `i+j+1` (for the ones place).
- Example: `123 × 456`:
  - `3 × 6 = 18` → affects positions 2+2=4 and 2+2+1=5
  - `2 × 6 = 12` → affects positions 1+2=3 and 1+2+1=4
  - And so on...

**Example walkthrough for `num1 = "123", num2 = "456"`:**
- Initialize: result = [0,0,0,0,0,0]
- i=2, j=2: 3×6=18 → result[5]=8, result[4]=1
- i=2, j=1: 3×5=15 → result[4]=1+5=6, result[3]=1
- i=2, j=0: 3×4=12 → result[3]=1+2=3, result[2]=1
- Continue for i=1 and i=0...
- Final: result = [0,5,6,0,8,8] → "56088"

## Complexity Analysis

- **Time Complexity:** $O(m \times n)$ where $m$ and $n$ are the lengths of `num1` and `num2`. We multiply each digit pair.

- **Space Complexity:** $O(m + n)$ for the result array.

## Similar Problems

Problems that can be solved using similar string manipulation patterns:

1. **43. Multiply Strings** (this problem) - String multiplication
2. **415. Add Strings** - String addition
3. **67. Add Binary** - Binary string addition
4. **2. Add Two Numbers** - Linked list addition
5. **445. Add Two Numbers II** - Linked list addition (reversed)
6. **66. Plus One** - Array addition
7. **989. Add to Array-Form of Integer** - Array addition
8. **8. String to Integer (atoi)** - String to integer conversion
9. **12. Integer to Roman** - Integer to string conversion
10. **13. Roman to Integer** - String to integer conversion

