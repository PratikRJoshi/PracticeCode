# Complimentary Pairs

## 1) Problem Description

Two strings form a complementary pair if their concatenation can be rearranged into a palindrome.

For a multiset of characters to form a palindrome:
- at most one character may have odd frequency.

Given `stringData[]`, count pairs `(i, j)` with `i < j` that satisfy this condition.

## 2) Intuition/Main Idea

For each string, track odd/even parity of each letter using a 26-bit mask.

- bit = 1 means that letter appears odd times in string
- bit = 0 means even

For two strings with masks `a` and `b`, concatenation parity is `a XOR b`.

Valid pair requires `a XOR b` to have:
- `0` set bits (all even), or
- exactly `1` set bit (one odd character allowed)

### Why this intuition works

- Parity captures exactly what palindrome rearrangement needs.
- We avoid building concatenations and counting all letters repeatedly.

### How to derive it step by step

1. Convert each string to parity mask.
2. While scanning strings, maintain frequency map of previous masks.
3. For current mask `m`:
   - add pairs with identical mask (`m`)
   - add pairs with masks differing by one bit (`m ^ (1 << b)`)
4. Insert current mask into map.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @PalindromePermutationCondition | use parity mask and odd-count logic via XOR |
| @CountUniquePairsOnly | scan left-to-right and count only prior masks |
| @LowercaseAlphabetOnly | 26-bit integer mask construction |
| @LargeInputTotalLength3e5 | linear scan plus constant 26 toggles per string |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static long countComplementaryPairs(List<String> stringData) {
        Map<Integer, Long> seenMaskFrequency = new HashMap<>();
        long pairCount = 0L;

        for (String word : stringData) {
            int currentMask = buildParityMask(word);

            pairCount += seenMaskFrequency.getOrDefault(currentMask, 0L);

            for (int bit = 0; bit < 26; bit++) {
                int toggledMask = currentMask ^ (1 << bit);
                pairCount += seenMaskFrequency.getOrDefault(toggledMask, 0L);
            }

            seenMaskFrequency.merge(currentMask, 1L, Long::sum);
        }

        return pairCount;
    }

    private static int buildParityMask(String word) {
        int mask = 0;
        for (int index = 0; index < word.length(); index++) {
            int bit = word.charAt(index) - 'a';
            mask ^= (1 << bit);
        }
        return mask;
    }
}
```

Learning Pattern:
- For palindrome-permutation checks, parity masks are often enough.
- Pair counting with constraints like "equal or one-bit-different" maps naturally to hashmap + bit toggles.

## 5) Complexity Analysis

- Let `L` be total length of all strings.
- Time Complexity: $O(L + 26n)$
- Space Complexity: $O(n)$ worst case for distinct masks

## Similar Problems

- [LeetCode 1915: Number of Wonderful Substrings](https://leetcode.com/problems/number-of-wonderful-substrings/) (parity mask + one-bit difference counting)
- [LeetCode 1457: Pseudo-Palindromic Paths in a Binary Tree](https://leetcode.com/problems/pseudo-palindromic-paths-in-a-binary-tree/) (odd-count via bitmask)