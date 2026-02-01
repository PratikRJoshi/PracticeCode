# 451. Sort Characters By Frequency

[LeetCode Link](https://leetcode.com/problems/sort-characters-by-frequency/)

## Problem Description
Given a string `s`, sort it in decreasing order based on the frequency of the characters.

Return the sorted string. If multiple answers are possible, return any of them.

### Examples

#### Example 1
- Input: `s = "tree"`
- Output: `"eert"`
- Explanation: `e` appears twice, `r` and `t` appear once.

#### Example 2
- Input: `s = "cccaaa"`
- Output: `"cccaaa"`
- Explanation: Both `c` and `a` appear three times, so either `"cccaaa"` or `"aaaccc"` is valid.

#### Example 3
- Input: `s = "Aabb"`
- Output: `"bbAa"`
- Explanation: `b` appears twice, `A` and `a` appear once.

---

## Intuition/Main Idea
The problem is asking for a **reordering by frequency**.

So we do two steps:

1. Count how many times each character appears.
2. Output characters in descending order of their counts.

A clean way to do step (2) is:

- Convert the frequency map into a list of “(character, frequency)” pairs.
- Sort that list by frequency descending.
- Build the output string by repeating each character its frequency number of times.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Count frequency of each character in `s` | Build `frequencyByChar` via a loop over `s.toCharArray()` |
| Sort characters by decreasing frequency | Sort `entries` using comparator on frequency descending |
| Construct the output string with repeated characters | Append `currentChar` exactly `frequency` times using `StringBuilder` |

---

## Final Java Code & Learning Pattern (Full Content)
```java
import java.util.*;

class Solution {
    public String frequencySort(String s) {
        Map<Character, Integer> frequencyByChar = new HashMap<>();
        for (char currentChar : s.toCharArray()) {
            frequencyByChar.put(currentChar, frequencyByChar.getOrDefault(currentChar, 0) + 1);
        }

        List<Map.Entry<Character, Integer>> entries = new ArrayList<>(frequencyByChar.entrySet());
        entries.sort((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));

        StringBuilder result = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : entries) {
            char currentChar = entry.getKey();
            int frequency = entry.getValue();

            for (int count = 0; count < frequency; count++) {
                result.append(currentChar);
            }
        }

        return result.toString();
    }
}
```

### Learning Pattern
- This is a “count then sort by key” pattern.
- Steps:
  - build frequency map
  - sort `(char, frequency)` pairs by frequency descending
  - rebuild the string by repeating each char `frequency` times

---

## Complexity Analysis
- Time Complexity: $O(n + k \log k)$
  - counting frequencies: $O(n)$
  - sorting `k` distinct characters: $O(k \log k)$
- Space Complexity: $O(k)$ for the frequency map and list

---

## Similar Problems
- [347. Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/) (frequency map + ordering)
- [692. Top K Frequent Words](https://leetcode.com/problems/top-k-frequent-words/) (frequency map + ordering)
- [1657. Determine if Two Strings Are Close](https://leetcode.com/problems/determine-if-two-strings-are-close/) (frequency analysis)
