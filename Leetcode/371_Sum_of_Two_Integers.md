# Sum of Two Integers

## Problem Description

**Problem Link:** [Sum of Two Integers](https://leetcode.com/problems/sum-of-two-integers/)

Given two integers `a` and `b`, return the sum of the two integers **without using** the operators `+` and `-`.

**Example 1:**
```
Input: a = 1, b = 2
Output: 3
```

**Example 2:**
```
Input: a = 2, b = 3
Output: 5
```

## Intuition/Main Idea

### Build the intuition (mentor-style)

Think about how you add numbers on paper:

- At each digit (or bit), you write down the result **without carry**.
- Separately, you compute the **carry** and add it into the next position.

Binary addition is the same, just simpler because every bit is only `0` or `1`.

So we want to compute:

1. **Sum without carry** (bit-by-bit addition ignoring carry)
2. **Carry** (where both bits are `1`, a carry is generated)

Then repeat until carry becomes zero.

### How to derive the bitwise operations

Consider adding two bits `x` and `y`:

- If `x=0, y=0` => sum bit `0`, carry `0`
- If `x=0, y=1` => sum bit `1`, carry `0`
- If `x=1, y=0` => sum bit `1`, carry `0`
- If `x=1, y=1` => sum bit `0`, carry `1`

From this truth table:

- The **sum bit without carry** matches `x XOR y`.
- The **carry bit** matches `x AND y`.

The carry must be added to the **next** higher bit position, so we shift it left:

- `carry = (a & b) << 1`

Then we re-run the same process:

- new `a` becomes the partial sum (without carry)
- new `b` becomes the carry

### Why the intuition works

At every iteration, we are doing exactly what grade-school addition does:

- `a ^ b` computes all bit sums **as if carry didn’t exist**.
- `(a & b) << 1` computes exactly the positions where carry is created, moved into the next bit.

When there is no carry left (`b == 0`), the partial sum is the final answer.

### Why the loop terminates (even for negatives)

Java `int` is fixed 32-bit two’s-complement.

Every iteration moves carry bits **leftward** (towards more significant bits). In a 32-bit integer, carry cannot move left forever, so after at most 32 iterations, the carry becomes zero.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| No `+` or `-` operators | Use only bitwise operations `^`, `&`, `<<` (lines 12-29) |
| Correctly handle carry | Compute `carry = (a & b) << 1` and iterate until `b == 0` (lines 16-26) |
| Work for negative numbers too | Relies on two’s-complement behavior of bitwise ops on `int` (lines 12-29) |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int getSum(int a, int b) {
        // We repeat until there's no carry left.
        // Loop condition reasoning:
        // - b represents the "carry" to be added.
        // - when carry becomes 0, a already holds the full sum.
        while (b != 0) {
            // carry has 1s exactly where both a and b have 1s (because 1 + 1 creates a carry).
            // We shift left because the carry affects the next higher bit.
            int carry = (a & b) << 1;

            // XOR gives the sum without carry (bit-by-bit addition mod 2).
            a = a ^ b;

            // Now add the carry in the next iteration.
            b = carry;
        }

        return a;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(1)$ for Java `int`

- At most 32 iterations (one per bit position).

**Space Complexity:** $O(1)$

- Only a constant number of variables.

## Similar Problems

- [Add Binary](https://leetcode.com/problems/add-binary/) - same carry idea but on strings
- [Bitwise AND of Numbers Range](https://leetcode.com/problems/bitwise-and-of-numbers-range/) - bit manipulation reasoning
