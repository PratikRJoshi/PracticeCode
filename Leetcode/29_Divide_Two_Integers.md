# 29. Divide Two Integers

[LeetCode Link](https://leetcode.com/problems/divide-two-integers/)

## Problem Description
Given two integers `dividend` and `divisor`, divide two integers **without using multiplication, division, and mod operator**.

Return the quotient after dividing `dividend` by `divisor`.

The integer division should truncate toward zero.

**Constraint/Edge Case:** If the quotient is overflow (i.e., it exceeds the 32-bit signed integer range `[-2^31, 2^31 - 1]`), return `2^31 - 1`.

### Examples

#### Example 1
- Input: `dividend = 10`, `divisor = 3`
- Output: `3`
- Explanation: `10 / 3 = 3.333...` truncated toward zero is `3`.

#### Example 2
- Input: `dividend = 7`, `divisor = -3`
- Output: `-2`
- Explanation: `7 / -3 = -2.333...` truncated toward zero is `-2`.

#### Example 3 (Overflow)
- Input: `dividend = -2147483648` (`Integer.MIN_VALUE`), `divisor = -1`
- Output: `2147483647`
- Explanation: True quotient is `2147483648`, which overflows 32-bit int, so clamp to `Integer.MAX_VALUE`.

---

## Intuition/Main Idea
We need to compute integer division using only addition/subtraction and bit operations.

### Core idea: subtract the largest doubled divisor each time
Instead of subtracting `divisor` one-by-one (too slow), we subtract **powers of two multiples**:

- `divisor * 1`, `divisor * 2`, `divisor * 4`, `divisor * 8`, ...

In binary, division is basically asking: **which powers of two fit into the dividend**.

### Avoid overflow by working in negatives
In Java, `Integer.MIN_VALUE` cannot be converted to positive (`abs(Integer.MIN_VALUE)` overflows).

So we do this:

- Convert both numbers to **negative**.
  - Negative range is larger: `[-2^31, -1]`.
- Keep subtracting negative values safely.

### How we build the quotient
For each step:

- Find the largest `value = divisor * 2^k` (via doubling) such that `value` still fits into the current `dividend`.
- Subtract it: `dividend -= value`.
- Add `2^k` to the answer.

### Note on binary-search rules
This solution uses **bit shifting/doubling**, not a classic binary search loop. The “search” is greedy doubling per iteration.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| No `*`, `/`, `%` operators | Build quotient using doubling (`value += value`) and subtraction (`dividend -= value`) |
| Truncate toward zero | Determine sign separately, compute magnitude, then apply sign |
| Overflow case `MIN_VALUE / -1` | Early return `Integer.MAX_VALUE` |
| Must handle `Integer.MIN_VALUE` safely | Convert to negatives and stay in negative domain |

---

## Final Java Code & Learning Pattern (Full Content)
```java
class Solution {
    public int divide(int dividend, int divisor) {
        // Overflow case: only occurs when dividend is MIN and divisor is -1.
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // Determine sign of result.
        // If exactly one is negative => result negative.
        boolean negative = (dividend < 0) ^ (divisor < 0);

        // Convert both to negative to avoid overflow with abs(MIN_VALUE).
        int dvd = dividend;
        int dvs = divisor;
        if (dvd > 0) dvd = -dvd;
        if (dvs > 0) dvs = -dvs;

        int quotient = 0;

        // While divisor still fits into dividend (both negative).
        while (dvd <= dvs) {
            int value = dvs;
            int powerOfTwo = 1;

            // Try to double value until doubling would exceed dvd or overflow.
            // Since numbers are negative:
            // - More negative means smaller.
            // - Doubling value makes it more negative.
            // Condition value >= MIN/2 ensures value+value doesn't overflow.
            while (value >= Integer.MIN_VALUE / 2 && dvd <= value + value) {
                value += value;           // value *= 2
                powerOfTwo += powerOfTwo; // powerOfTwo *= 2
            }

            // Subtract the largest doubled divisor from dividend.
            dvd -= value;

            // Add the corresponding power of two to quotient.
            quotient += powerOfTwo;
        }

        return negative ? -quotient : quotient;
    }
}
```

### Learning Pattern
- This is a **bit-doubling / exponential search** pattern:
  - Each outer loop reduces the remaining dividend by the largest chunk possible.
  - Inner loop finds that chunk in `O(log dividend)` by repeated doubling.
- Critical safety trick:
  - Work in the **negative** domain to avoid `abs(Integer.MIN_VALUE)` overflow.

---

## Complexity Analysis
- Time Complexity: $O(\log |dividend|)$ (each outer iteration removes a large power-of-two chunk)
- Space Complexity: $O(1)$

---

## Similar Problems
- [50. Pow(x, n)](https://leetcode.com/problems/powx-n/) (doubling/exponentiation by squaring mindset)
- [29. Divide Two Integers](https://leetcode.com/problems/divide-two-integers/) (this exact pattern repeats in variants)
