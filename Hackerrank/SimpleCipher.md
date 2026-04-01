# Simple Cipher

## 1) Problem Description

You are given an uppercase string `encrypted` and an integer `k`.

Decrypt by shifting every letter `k` positions backward on the cyclic alphabet (`A-Z`).

Example:

```text
encrypted = "VTAOG", k = 2
output = "TRYME"
```

Constraints:
- `1 <= encrypted.length <= 10^5`
- `1 <= k <= 10^5`

## 2) Intuition/Main Idea

Each character is independent, so we transform one by one.

Since alphabet size is 26, shifting by `k` is same as shifting by `k % 26`.

### Why this intuition works

- Alphabet shift is modular arithmetic on `[0..25]`.
- Backward shift by `s` from index `x` is `(x - s + 26) % 26`.

### How to derive it step by step

1. Convert char to index: `x = c - 'A'`.
2. Compute normalized shift `s = k % 26`.
3. New index: `(x - s + 26) % 26`.
4. Convert back to char.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @CounterClockwiseShift | `(alphabetIndex - normalizedShift + 26) % 26` |
| @AlphabetWrapAround | modular `% 26` arithmetic |
| @UppercaseOnly | index conversion relative to `'A'` |
| @LargeInputEfficiency | single pass with `StringBuilder` |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static String simpleCipher(String encrypted, int k) {
        int normalizedShift = k % 26;
        StringBuilder decrypted = new StringBuilder(encrypted.length());

        for (int index = 0; index < encrypted.length(); index++) {
            char current = encrypted.charAt(index);
            int alphabetIndex = current - 'A';
            int decryptedIndex = (alphabetIndex - normalizedShift + 26) % 26;
            decrypted.append((char) ('A' + decryptedIndex));
        }

        return decrypted.toString();
    }
}
```

Learning Pattern:
- Any cyclic character transform can be modeled as modular arithmetic on index space.
- Normalize big shifts (`k % cycleLength`) early.

## 5) Complexity Analysis

- Time Complexity: $O(n)$
- Space Complexity: $O(n)$ for output

## Similar Problems

- [LeetCode 848: Shifting Letters](https://leetcode.com/problems/shifting-letters/)
- [LeetCode 2381: Shifting Letters II](https://leetcode.com/problems/shifting-letters-ii/)