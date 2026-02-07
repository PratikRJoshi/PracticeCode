# Count Monobit Integers

## Problem Description

**Problem Link:** [Count Monobit Integers](https://leetcode.com/problems/count-monobit-integers/)

You are given an integer `n`.

An integer is called **Monobit** if all bits in its binary representation are the same.

Return the count of Monobit integers in the range `[0, n]` (inclusive).

**Example 1:**
```
Input: n = 1
Output: 2
Explanation:
The integers in the range [0, 1] have binary representations "0" and "1".
Each representation consists of identical bits. Thus, the answer is 2.
```

**Example 2:**
```
Input: n = 4
Output: 3
Explanation:
The integers in the range [0, 4] include binaries "0", "1", "10", "11", and "100".
Only 0, 1 and 3 satisfy the Monobit condition. Thus, the answer is 3.
```

**Constraints:**
- `0 <= n <= 1000`

## Intuition/Main Idea

A number’s binary representation has **all bits equal** only in these cases:

- The number is `0`.
  - Binary representation is just `"0"`.
- The number is of the form `111...111` in binary (all ones).
  - These numbers are exactly:
    - `1` (`1`)
    - `3` (`11`)
    - `7` (`111`)
    - `15` (`1111`)
    - ...
  - In math form: `2^k - 1` for `k >= 1`.

So we just need to count:

- `0` (always included if `n >= 0`)
- plus how many values of the form `2^k - 1` are `<= n`.

We can generate these numbers iteratively:

- Start with `value = 1`.
- Next “all ones” number is made by shifting left and adding a `1`:
  - `1` -> `(1 << 1) | 1 = 3`
  - `3` -> `(3 << 1) | 1 = 7`
  - ...

This is fast because we generate only `O(log n)` candidates.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Return count of Monobit integers in `[0, n]` | Count `0` + count generated `(2^k - 1)` values `<= n` (lines 8-25) |
| `0 <= n <= 1000` | Simple iterative generation; loop runs `O(log n)` times (lines 11-22) |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int countMonobitIntegers(int n) {
        // 0 is always monobit because its binary representation is "0".
        int count = 1;

        // All other monobit numbers must look like 1, 11, 111, 1111, ... in binary.
        // These are exactly: 2^k - 1.
        int value = 1; // binary: 1

        // Keep generating: 1, 3, 7, 15, ... until we exceed n.
        while (value <= n) {
            count++;

            // Build the next "all ones" number:
            // Example: 3 (11) -> 7 (111)
            value = (value << 1) | 1;
        }

        // We added 0 (count starts at 1) and each valid (2^k - 1) value.
        return count;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(\log n)$ (we generate one number per bit-length)

**Space Complexity:** $O(1)$

## Similar Problems

- [Number of 1 Bits](https://leetcode.com/problems/number-of-1-bits/) - Bit manipulation practice
- [Power of Two](https://leetcode.com/problems/power-of-two/) - Recognizing special forms in binary
- [Power of Four](https://leetcode.com/problems/power-of-four/) - More binary pattern recognition
