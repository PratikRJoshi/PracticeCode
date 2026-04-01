# Binary Operations

## 1) Problem Description

Given positive integer `n`, in one operation you may do:

```text
n = n + 2^i  OR  n = n - 2^i   (i >= 0)
```

Find minimum operations to make `n = 0`.

Example:

```text
n = 7
7 + 1 = 8
8 - 8 = 0
answer = 2
```

Constraints:
- `1 <= n < 2^60`

## 2) Intuition/Main Idea

Each operation picks one signed power of two (`+2^i` or `-2^i`).
So we want to represent `n` as a sum of signed powers of two using minimum non-zero terms.

This is exactly the Non-Adjacent Form (NAF) idea.

### Why this intuition works

- If `n` is even, no operation is forced at bit 0; divide by 2 and continue.
- If `n` is odd, we must consume one signed unit at current bit:
  - choose `+1` or `-1` at this bit so remaining value becomes divisible by 2.
- Greedy rule for odd `n`:
  - if `n == 1` or `n % 4 == 1`, use `-1`
  - else use `+1`
- This avoids consecutive non-zero signed bits and minimizes count.

### How to derive it step by step

1. Work bit by bit from least significant side.
2. Even `n`: shift right (`n /= 2`).
3. Odd `n`: choose better sign (`+1` or `-1`) via `n % 4`.
4. Count each odd-step choice as one operation.
5. Continue until `n == 0`.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @OperationIsPlusOrMinusPowerOfTwo | odd-case chooses signed unit at current bit |
| @MinimumOperationCount | greedy odd handling by `value % 4` |
| @HandleLargeNUpTo2Pow60 | use `long` for computations |
| @StopAtZero | while loop runs until `value == 0` |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static int getMinOperations(long n) {
        int operations = 0;
        long value = n;

        while (value > 0) {
            if ((value & 1L) == 0L) {
                value >>= 1;
            } else {
                operations++;

                if (value == 1 || (value & 3L) == 1L) {
                    value -= 1;
                } else {
                    value += 1;
                }

                value >>= 1;
            }
        }

        return operations;
    }
}
```

Learning Pattern:
- When operations are powers of two with signs, think in binary and signed-binary representation.
- Modulo pattern (`n % 4`) often drives optimal odd-step decisions.

## 5) Complexity Analysis

- Time Complexity: $O(\log n)$
- Space Complexity: $O(1)$

## Similar Problems

- [LeetCode 397: Integer Replacement](https://leetcode.com/problems/integer-replacement/) (same odd-choice greedy principle)
- [LeetCode 1404: Number of Steps to Reduce a Number in Binary Representation to One](https://leetcode.com/problems/number-of-steps-to-reduce-a-number-in-binary-representation-to-one/)