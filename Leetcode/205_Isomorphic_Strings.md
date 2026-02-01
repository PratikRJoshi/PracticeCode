# 205. Isomorphic Strings

[LeetCode Link](https://leetcode.com/problems/isomorphic-strings/)

## Problem Description
Given two strings `s` and `t`, determine if they are **isomorphic**.

Two strings are isomorphic if the characters in `s` can be replaced to get `t` with the following rules:

- Each character in `s` must map to exactly one character in `t`.
- No two different characters in `s` may map to the same character in `t`.
- A character may map to itself.

Return `true` if `s` and `t` are isomorphic, otherwise return `false`.

### Examples

#### Example 1
- Input: `s = "egg"`, `t = "add"`
- Output: `true`

#### Example 2
- Input: `s = "foo"`, `t = "bar"`
- Output: `false`

#### Example 3
- Input: `s = "paper"`, `t = "title"`
- Output: `true`

---

## Intuition/Main Idea
The requirements describe a **one-to-one (bijective) mapping** between characters of `s` and characters of `t`.

When scanning left-to-right:

- If we have seen `s[i]` before, it must map to the same `t[i]` as earlier.
- If we have not seen `s[i]` before, we can create a mapping `s[i] -> t[i]` **only if** `t[i]` is not already mapped from a different character.

So we maintain two maps:

- `mapST`: mapping from characters in `s` to characters in `t`
- `mapTS`: mapping from characters in `t` to characters in `s`

The second map enforces the “no two different characters in `s` map to the same character in `t`” rule.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Each character in `s` must map to one character in `t` | `mapST` stores and validates `sourceChar -> targetChar` |
| No two different chars in `s` map to the same char in `t` | `mapTS` enforces reverse mapping uniqueness |
| Mapping must be consistent at every index | On each index, compare existing mappings to current pair |
| Strings must be same length | Early return if `s.length() != t.length()` |

---

## Final Java Code & Learning Pattern (Full Content)
```java
import java.util.*;

class Solution {
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        Map<Character, Character> mapST = new HashMap<>();
        Map<Character, Character> mapTS = new HashMap<>();

        for (int index = 0; index < s.length(); index++) {
            char sourceChar = s.charAt(index);
            char targetChar = t.charAt(index);

            if (mapST.containsKey(sourceChar)) {
                // sourceChar was seen before, it must map to the same targetChar as earlier.
                if (mapST.get(sourceChar) != targetChar) {
                    return false;
                }
            } else {
                // sourceChar is new. It can map to targetChar only if targetChar isn't already used.
                if (mapTS.containsKey(targetChar)) {
                    return false;
                }

                mapST.put(sourceChar, targetChar);
                mapTS.put(targetChar, sourceChar);
            }
        }

        return true;
    }
}
```

### Learning Pattern
- Many “isomorphic / pattern matching” string problems are **bijection checks**.
- Use two maps to enforce 1-to-1 mapping:
  - forward map validates consistency
  - reverse map prevents collisions

---

## Complexity Analysis
- Time Complexity: $O(n)$ where `n = s.length()`
- Space Complexity: $O(\Sigma)$ where `\Sigma` is the number of distinct characters seen (bounded by the character set)

---

## Similar Problems
- [290. Word Pattern](https://leetcode.com/problems/word-pattern/) (bijection between pattern symbols and words)
- [205. Isomorphic Strings](https://leetcode.com/problems/isomorphic-strings/) (this problem)
- [242. Valid Anagram](https://leetcode.com/problems/valid-anagram/) (character frequency comparison)
