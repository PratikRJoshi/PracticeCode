# Permutation in String

## Problem Description

**Problem Link:** [Permutation in String](https://leetcode.com/problems/permutation-in-string/)

Given two strings `s1` and `s2`, return `true` *if `s2` contains a permutation of `s1`, or `false` otherwise*.

In other words, return `true` if one of `s1`'s permutations is the substring of `s2`.

**Example 1:**
```
Input: s1 = "ab", s2 = "eidbaooo"
Output: true
Explanation: s2 contains one permutation of s1 ("ba").
```

**Example 2:**
```
Input: s1 = "ab", s2 = "eidboaoo"
Output: false
```

**Constraints:**
- `1 <= s1.length, s2.length <= 10^4`
- `s1` and `s2` consist of lowercase English letters.

## Intuition/Main Idea

This is a **sliding window** problem with **frequency counting**. We need to check if any window in `s2` has the same character frequency as `s1`.

**Core Algorithm:**
1. Count frequency of each character in `s1` using a HashMap.
2. Use a sliding window approach to check substrings in `s2`.
3. Track the number of character matches between the current window and `s1`.
4. If all characters match in frequency, return true.
5. Slide the window by adding a new character and removing an old one.

**Why HashMap and matches counter works:** Instead of comparing entire frequency arrays each time, we track how many characters have the correct frequency. When this count equals the number of unique characters in `s1`, we have a match.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count frequency of s1 | HashMap initialization - Lines 7-10 |
| Track character matches | `matches` variable - Line 12 |
| Process window characters | While loop - Lines 13-37 |
| Add new character | Frequency decrement - Lines 15-20 |
| Check if all characters match | Matches check - Lines 22-24 |
| Remove old character | Frequency increment - Lines 27-33 |
| Return result | Return statement - Line 39 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if(s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()){
            return false;
        }

        Map<Character, Integer> map = new HashMap<>();
        for(char c : s1.toCharArray()){
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int start = 0, end = 0, matches = 0;
        while(end < s2.length()){
            char c = s2.charAt(end);
            if(map.containsKey(c)){
                map.put(c, map.get(c) - 1);
                if(map.get(c) == 0){
                    matches++;
                }
            }

            if(matches == map.size()){
                return true;
            }

            if(end >= s1.length() - 1){
                char c2 = s2.charAt(start);
                if(map.containsKey(c2)){
                    if(map.get(c2) == 0){
                        matches--;
                    }
                    map.put(c2, map.get(c2) + 1);
                }
                start++;
            }
            end++;
        }

        return false;
    }
}
```

**Explanation of Key Code Sections:**

1. **Edge Cases (Lines 3-5):** Check for null or empty strings.

2. **Count s1 Frequency (Lines 7-10):** Create a HashMap to store the frequency of each character in `s1`. This is our target pattern.

3. **Initialize Variables (Line 12):**
   - `start`: Left boundary of sliding window
   - `end`: Right boundary of sliding window
   - `matches`: Number of characters that have the correct frequency

4. **Process Window (Lines 13-37):** For each character in `s2`:
   - **Process Current Character (Lines 14-20):** If the character is in our map, decrement its count. If its count becomes 0, increment `matches`.
   - **Check for Match (Lines 22-24):** If `matches` equals the size of the map (all characters have correct frequency), return true.
   - **Slide Window (Lines 26-35):** If window size reaches `s1.length()`, remove the leftmost character and slide the window.
   - **Update Window (Lines 27-33):** When removing a character, if it's in our map, increment its count. If its count was 0, decrement `matches`.

5. **Final Result (Line 39):** If no match is found, return false.

**Why this approach is efficient:**
- **Targeted counting:** We only track characters that appear in `s1`.
- **Optimized comparison:** Instead of comparing all characters each time, we track the number of matching characters.
- **Dynamic window:** The window expands until it reaches the size of `s1`, then slides by maintaining its size.

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the length of `s2`. We process each character at most twice (once when added, once when removed).

- **Space Complexity:** $O(k)$ where $k$ is the number of unique characters in `s1`. In the worst case, this is $O(26)$ for lowercase English letters, which simplifies to $O(1)$.

## Similar Problems

Problems that can be solved using similar sliding window patterns:

1. **567. Permutation in String** (this problem) - Sliding window with frequency
2. **438. Find All Anagrams in a String** - Same pattern, find all occurrences
3. **76. Minimum Window Substring** - Sliding window with frequency
4. **3. Longest Substring Without Repeating Characters** - Sliding window
5. **424. Longest Repeating Character Replacement** - Sliding window with replacement
6. **209. Minimum Size Subarray Sum** - Sliding window with sum
7. **239. Sliding Window Maximum** - Sliding window maximum
8. **30. Substring with Concatenation of All Words** - Sliding window variant
9. **159. Longest Substring with At Most Two Distinct Characters** - Sliding window
10. **340. Longest Substring with At Most K Distinct Characters** - Sliding window
