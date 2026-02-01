# 1657. Determine if Two Strings Are Close

[LeetCode Link](https://leetcode.com/problems/determine-if-two-strings-are-close/)

## Problem Description
You are given two strings `word1` and `word2`.

Two strings are considered **close** if you can transform one into the other using the following operations any number of times:

1. Swap any two existing characters (i.e., you can reorder the string arbitrarily).
2. Transform every occurrence of one existing character into another existing character, and do the same with the other character (i.e., swap the identities/frequencies of two characters already present in the string).

Return `true` if `word1` and `word2` are close, otherwise return `false`.

### Examples

#### Example 1
- Input: `word1 = "abc"`, `word2 = "bca"`
- Output: `true`
- Explanation: We can reorder `"abc"` to get `"bca"`.

#### Example 2
- Input: `word1 = "a"`, `word2 = "aa"`
- Output: `false`
- Explanation: The lengths differ, so they can’t be made equal.

#### Example 3
- Input: `word1 = "cabbba"`, `word2 = "abbccc"`
- Output: `true`
- Explanation:
  - Both strings use the same set of characters `{a, b, c}`.
  - The frequency multiset is the same: `{1,2,3}`.

---

## Intuition/Main Idea
Operation (1) means **order doesn’t matter**: only character counts matter.

Operation (2) is the key constraint: you are allowed to **swap the labels of two existing characters**. That means:

- You cannot introduce a brand new character.
  - So `word1` and `word2` must contain the **same set of distinct characters**.
- You can permute which character has which count.
  - So the **multiset of character frequencies** must match between the two strings.

So the problem becomes two checks:

1. Same distinct letters used.
2. Same multiset of frequencies.

If both are true, you can reassign frequencies via operation (2), then reorder via operation (1).

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Reordering is allowed (swap any two characters) | Reduce strings to frequency counts; ignore ordering |
| Can only transform between existing characters | Ensure both strings use the same set of letters (`frequency > 0` at same indices) |
| Can swap identities of existing characters | Sort frequency arrays and compare them as multisets |

---

## Final Java Code & Learning Pattern (Full Content)
```java
import java.util.*;

class Solution {
    public boolean closeStrings(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }

        int[] freqWord1 = new int[26];
        int[] freqWord2 = new int[26];

        for (int index = 0; index < word1.length(); index++) {
            freqWord1[word1.charAt(index) - 'a']++;
            freqWord2[word2.charAt(index) - 'a']++;
        }

        // Constraint from operation (2): you can only swap identities of characters that already exist.
        // So both strings must use the exact same set of distinct characters.
        for (int letter = 0; letter < 26; letter++) {
            boolean existsInWord1 = freqWord1[letter] > 0;
            boolean existsInWord2 = freqWord2[letter] > 0;
            if (existsInWord1 != existsInWord2) {
                return false;
            }
        }

        // Operation (2) allows swapping the counts among existing letters.
        // That means the multiset of frequencies must match.
        Arrays.sort(freqWord1);
        Arrays.sort(freqWord2);

        return Arrays.equals(freqWord1, freqWord2);
    }
}
```

### Learning Pattern
- Convert “allowed operations” into invariants:
  - Operation (1) => ordering irrelevant => compare counts
  - Operation (2) => cannot create new letters + can permute counts among existing letters
- Therefore check:
  - same set of letters
  - same multiset of counts (sort frequencies)

---

## Complexity Analysis
- Time Complexity: $O(n + 26 \log 26)$ which is effectively $O(n)$
  - counting characters: $O(n)$
  - sorting two arrays of size 26: constant time
- Space Complexity: $O(1)$
  - two arrays of size 26

---

## Similar Problems
- [242. Valid Anagram](https://leetcode.com/problems/valid-anagram/) (compare character counts)
- [451. Sort Characters By Frequency](https://leetcode.com/problems/sort-characters-by-frequency/) (frequency counting)
- [49. Group Anagrams](https://leetcode.com/problems/group-anagrams/) (character-count signatures)
