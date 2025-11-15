# 7. Reverse Integer

## [Problem Link](https://leetcode.com/problems/reverse-integer/)

### Problem Description

Given a signed 32-bit integer `x`, return `x` with its digits reversed. If reversing `x` causes the value to go outside the signed 32-bit integer range `[-2^31, 2^31 - 1]`, then return `0`.

**Assume the environment does not allow you to store 64-bit integers (signed or unsigned).**

**Example 1:**
```
Input: x = 123
Output: 321
```

**Example 2:**
```
Input: x = -123
Output: -321
```

**Example 3:**
```
Input: x = 120
Output: 21
```

**Constraints:**
- `-2^31 <= x <= 2^31 - 1`

## Intuition/Main Idea

The core idea is to extract digits from the input number one by one from right to left and build the reversed number. The key challenge is handling potential overflow when reversing large numbers. Since we can't use 64-bit integers, we need to check for overflow before adding each digit to our result.

We can detect potential overflow by checking if the current result is approaching the integer limit. For a positive number, if `result > Integer.MAX_VALUE/10` or if `result == Integer.MAX_VALUE/10` and the next digit is greater than 7 (since MAX_VALUE ends with 7), then adding the next digit would cause overflow. Similar logic applies to negative numbers.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Reverse the digits of the input integer | `while (x != 0) { digit = x % 10; x /= 10; result = result * 10 + digit; }` |
| Handle 32-bit integer overflow | `if (result > Integer.MAX_VALUE/10 || (result == Integer.MAX_VALUE/10 && digit > 7)) return 0;` <br> `if (result < Integer.MIN_VALUE/10 || (result == Integer.MIN_VALUE/10 && digit < -8)) return 0;` |
| Return the reversed integer or 0 if overflow | `return result;` |

## Final Java Code & Learning Pattern

```java
class Solution {
    public int reverse(int x) {
        int result = 0;
        
        while (x != 0) {
            int digit = x % 10;  // Extract the last digit
            x /= 10;             // Remove the last digit
            
            // Check for potential overflow before adding the next digit
            if (result > Integer.MAX_VALUE/10 || 
                (result == Integer.MAX_VALUE/10 && digit > 7)) {
                return 0;  // Overflow would occur for positive numbers
            }
            
            // Check for potential underflow before adding the next digit
            if (result < Integer.MIN_VALUE/10 || 
                (result == Integer.MIN_VALUE/10 && digit < -8)) {
                return 0;  // Overflow would occur for negative numbers
            }
            
            // Build the reversed number by adding digits from right to left
            result = result * 10 + digit;
        }
        
        return result;
    }
}
```

### Explanation:

1. **Digit Extraction**: We extract the last digit of the number using the modulo operator (`x % 10`), then remove it from the original number by integer division (`x /= 10`).

2. **Overflow Check**: Before adding each digit to our result, we check if doing so would cause an overflow:
   - For positive numbers, overflow occurs if `result > Integer.MAX_VALUE/10` or if `result == Integer.MAX_VALUE/10` and the next digit is greater than 7 (since `Integer.MAX_VALUE` is 2147483647).
   - For negative numbers, overflow occurs if `result < Integer.MIN_VALUE/10` or if `result == Integer.MIN_VALUE/10` and the next digit is less than -8 (since `Integer.MIN_VALUE` is -2147483648).

3. **Building the Reversed Number**: We build the reversed number by multiplying the current result by 10 (shifting it left) and adding the extracted digit.

4. **Handling Zero**: The loop continues until the entire number has been processed (when `x` becomes 0).

## Complexity Analysis

- **Time Complexity**: $O(\log_{10}(x))$ - We process each digit of the input number, and the number of digits in an integer is approximately $\log_{10}(x)$.
  
- **Space Complexity**: $O(1)$ - We only use a constant amount of space regardless of the input size.

## Similar Problems

1. [9. Palindrome Number](https://leetcode.com/problems/palindrome-number/)
2. [190. Reverse Bits](https://leetcode.com/problems/reverse-bits/)
3. [191. Number of 1 Bits](https://leetcode.com/problems/number-of-1-bits/)
4. [8. String to Integer (atoi)](https://leetcode.com/problems/string-to-integer-atoi/)
5. [66. Plus One](https://leetcode.com/problems/plus-one/)
