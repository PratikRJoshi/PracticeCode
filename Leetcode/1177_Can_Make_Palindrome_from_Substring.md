# 1177. Can Make Palindrome from Substring

[LeetCode Link](https://leetcode.com/problems/can-make-palindrome-from-substring/)

## Problem Description
You are given a string `s` and an array of queries `queries`.

Each query is of the form `[left, right, k]` and asks:

- Consider the substring `s[left..right]`.
- You may rearrange the substring.
- You may replace at most `k` characters (change one character into another).

Return a boolean array where each entry answers whether it is possible to make that substring a palindrome under these rules.

### Examples

#### Example 1
- Input: `s = "abcda"`, `queries = [[3,3,0],[1,2,0],[0,3,1],[0,3,2],[0,4,1]]`
- Output: `[true,false,false,true,true]`

#### Example 2
- Input: `s = "lyb"`, `queries = [[0,1,0],[2,2,1]]`
- Output: `[false,true]`

---

## Intuition/Main Idea
Because we are allowed to **rearrange** the substring, the only thing that matters is the **frequency parity** (odd/even) of each letter.

A string can be rearranged into a palindrome iff:

- At most **one** character has an odd count (for odd-length palindromes).
- For even length, **zero** odd counts.

If the substring has `oddCount` letters with odd frequencies, then the minimum number of replacements needed to make it palindromic is:

- `oddCount / 2`

Why?
- Each replacement can “fix” two odd counts by converting one character into another, turning both odd counts into even.

So a query `[left, right, k]` is true iff:

- `oddCount / 2 <= k`

### Fast oddCount per query
We need `oddCount` for many ranges, so we precompute prefix parity.

Use a 26-bit bitmask:

- Bit `i` is 1 if the count of letter `('a' + i)` is odd.

Let `prefixMask[index]` represent the parity mask for `s[0..index-1]` (first `index` characters). Then for substring `[left, right]`:

- `rangeMask = prefixMask[right + 1] XOR prefixMask[left]`

The number of odd-frequency letters is:

- `oddCount = bitCount(rangeMask)`

This makes each query O(1).

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Substring can be rearranged | Use parity-only logic (bitmask), not exact order |
| Allowed to replace at most `k` chars | Check `oddCount / 2 <= k` |
| Must answer many range queries efficiently | Build `prefixMask[]` and compute `rangeMask` via XOR |
| Determine odd counts quickly | `Integer.bitCount(rangeMask)` |

---

## Final Java Code & Learning Pattern (Full Content)
```java
import java.util.*;

class Solution {
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        int n = s.length();

        // prefixMask[i] stores parity mask of s[0..i-1]
        // Size n+1 so prefixMask[0] = 0 represents empty prefix.
        int[] prefixMask = new int[n + 1];
        prefixMask[0] = 0;

        for (int index = 0; index < n; index++) {
            int letterBit = 1 << (s.charAt(index) - 'a');
            prefixMask[index + 1] = prefixMask[index] ^ letterBit;
        }

        List<Boolean> answers = new ArrayList<>(queries.length);

        for (int[] query : queries) {
            int left = query[0];
            int right = query[1];
            int maxReplacements = query[2];

            int rangeMask = prefixMask[right + 1] ^ prefixMask[left];
            int oddCount = Integer.bitCount(rangeMask);

            int minReplacementsNeeded = oddCount / 2;
            answers.add(minReplacementsNeeded <= maxReplacements);
        }

        return answers;
    }
}
```

### Learning Pattern
- If a substring can be rearranged into a palindrome, only **odd/even counts** matter.
- Use a prefix XOR bitmask to answer parity questions on any substring:
  - `rangeMask = prefixMask[r+1] XOR prefixMask[l]`
  - `oddCount = bitCount(rangeMask)`
- Replacements needed is `oddCount / 2`.

---

## Complexity Analysis
- Time Complexity: $O(n + q)$
  - Build `prefixMask`: $O(n)$
  - Answer each query in O(1): total $O(q)$
- Space Complexity: $O(n)$ for `prefixMask`

---

## Similar Problems
- [1915. Number of Wonderful Substrings](https://leetcode.com/problems/number-of-wonderful-substrings/) (parity bitmask)
- [1371. Find the Longest Substring Containing Vowels in Even Counts](https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/) (prefix parity)
- [516. Longest Palindromic Subsequence](https://leetcode.com/problems/longest-palindromic-subsequence/) (palindrome reasoning)
