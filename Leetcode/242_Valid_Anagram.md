# [242. Valid Anagram](https://leetcode.com/problems/valid-anagram/)

Given two strings `s` and `t`, return `true` if `t` is an anagram of `s`, and `false` otherwise.

An **Anagram** is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

**Example 1:**

```
Input: s = "anagram", t = "nagaram"
Output: true
```

**Example 2:**

```
Input: s = "rat", t = "car"
Output: false
```

**Constraints:**

- `1 <= s.length, t.length <= 5 * 10^4`
- `s` and `t` consist of lowercase English letters.

**Follow up:** What if the inputs contain Unicode characters? How would you adapt your solution to such a case?

## Intuition/Main Idea:

An anagram is a word formed by rearranging the letters of another word, using all the original letters exactly once. To check if two strings are anagrams, we need to verify that they have the same characters with the same frequencies.

There are several approaches to solve this problem:

1. **Character Frequency Count**: Count the frequency of each character in both strings and compare the counts.
2. **Sorting**: Sort both strings and check if they are equal.
3. **Hash Table**: Use a hash table to track character counts.

The character frequency count approach is efficient and straightforward for this problem, especially since we're dealing with lowercase English letters only.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Check if t is an anagram of s | The entire solution checks this by comparing character frequencies |
| Return true if t is an anagram of s | `return true;` (after verifying all character counts match) |
| Return false otherwise | `return false;` (if lengths differ or character counts don't match) |

## Final Java Code & Learning Pattern:

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        // If the lengths are different, they can't be anagrams
        if (s.length() != t.length()) {
            return false;
        }
        
        // Create an array to count character frequencies (26 lowercase letters)
        int[] charCount = new int[26];
        
        // Count characters in string s
        for (char c : s.toCharArray()) {
            charCount[c - 'a']++;
        }
        
        // Decrement counts for characters in string t
        for (char c : t.toCharArray()) {
            charCount[c - 'a']--;
            // If the count becomes negative, t has more of this character than s
            if (charCount[c - 'a'] < 0) {
                return false;
            }
        }
        
        // At this point, if all counts are zero, the strings are anagrams
        // We don't need to check explicitly since we've already verified:
        // 1. The lengths are the same
        // 2. No character in t appears more times than in s
        return true;
    }
}
```

This solution uses a character frequency counting approach:

1. First, we check if the lengths of the two strings are equal. If not, they can't be anagrams.
2. We create an array of size 26 (for 26 lowercase English letters) to count the frequency of each character.
3. We iterate through string `s` and increment the count for each character.
4. We iterate through string `t` and decrement the count for each character. If at any point a count becomes negative, it means `t` has more of that character than `s`, so they can't be anagrams.
5. If we complete both iterations without returning false, the strings are anagrams.

The key insight is that two strings are anagrams if and only if they have the same characters with the same frequencies.

## Alternative Implementations:

### Sorting Approach:

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        
        Arrays.sort(sChars);
        Arrays.sort(tChars);
        
        return Arrays.equals(sChars, tChars);
    }
}
```

This approach sorts both strings and then compares them. If they are anagrams, the sorted strings will be identical.

### HashMap Approach:

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        Map<Character, Integer> charCount = new HashMap<>();
        
        // Count characters in string s
        for (char c : s.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        
        // Decrement counts for characters in string t
        for (char c : t.toCharArray()) {
            int count = charCount.getOrDefault(c, 0) - 1;
            if (count < 0) {
                return false;
            }
            charCount.put(c, count);
        }
        
        return true;
    }
}
```

This approach uses a HashMap instead of an array to count character frequencies. It's more versatile and can handle Unicode characters (addressing the follow-up question).

## Complexity Analysis:

- **Time Complexity**: 
  - Array Counting Approach: O(n) where n is the length of the strings.
  - Sorting Approach: O(n log n) due to the sorting step.
  - HashMap Approach: O(n).
  
- **Space Complexity**: 
  - Array Counting Approach: O(1) since we use a fixed-size array of 26 characters.
  - Sorting Approach: O(n) for storing the sorted arrays.
  - HashMap Approach: O(k) where k is the number of unique characters in the strings.

## Follow-up: Handling Unicode Characters

If the inputs contain Unicode characters, we can't use a fixed-size array of 26 elements. Instead, we would need to use a HashMap to count character frequencies, as shown in the HashMap approach above. This would allow us to handle any character set, not just lowercase English letters.

## Similar Problems:

1. [49. Group Anagrams](https://leetcode.com/problems/group-anagrams/)
2. [438. Find All Anagrams in a String](https://leetcode.com/problems/find-all-anagrams-in-a-string/)
3. [567. Permutation in String](https://leetcode.com/problems/permutation-in-string/)
4. [1347. Minimum Number of Steps to Make Two Strings Anagram](https://leetcode.com/problems/minimum-number-of-steps-to-make-two-strings-anagram/)
5. [383. Ransom Note](https://leetcode.com/problems/ransom-note/)
