# [3. Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)

Given a string `s`, find the length of the **longest substring** without repeating characters.

**Example 1:**

```
Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.
```

**Example 2:**

```
Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.
```

**Example 3:**

```
Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
```

**Constraints:**

- `0 <= s.length <= 5 * 10^4`
- `s` consists of English letters, digits, symbols and spaces.

## Intuition/Main Idea:

To find the longest substring without repeating characters, we can use the sliding window technique. The basic idea is to maintain a window that contains only unique characters:

1. Use two pointers, `left` and `right`, to define the current window.
2. Expand the window by moving the `right` pointer to include more characters.
3. When we encounter a character that's already in our window, we need to shrink the window from the left until we remove the duplicate.
4. Keep track of the maximum window size we've seen.

To efficiently check if a character is already in our window, we'll use a HashMap to store each character and its most recent position. This allows us to quickly move the `left` pointer to the right position when we encounter a duplicate.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Find longest substring without repeating characters | `for (int right = 0; right < s.length(); right++) { ... }` |
| Track character positions | `Map<Character, Integer> charIndexMap = new HashMap<>();` |
| Update window when duplicate found | `left = Math.max(left, charIndexMap.get(c) + 1);` |
| Calculate max length | `maxLength = Math.max(maxLength, right - left + 1);` |

## Final Java Code & Learning Pattern:

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        // Edge case: empty string
        if (s.length() == 0) return 0;
        
        // Map to store the most recent index of each character
        Map<Character, Integer> charIndexMap = new HashMap<>();
        
        int maxLength = 0;  // To store the length of the longest substring
        int left = 0;       // Left pointer of the sliding window
        
        // Iterate through the string with the right pointer
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            
            // If the character is already in our window, move the left pointer
            // to the position after the last occurrence of the character
            if (charIndexMap.containsKey(c)) {
                // We use Math.max to ensure left pointer only moves forward
                left = Math.max(left, charIndexMap.get(c) + 1);
            }
            
            // Update the most recent position of the character
            charIndexMap.put(c, right);
            
            // Update the maximum length if current window is larger
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}
```

This solution uses the sliding window technique with a HashMap to efficiently track character positions:

1. We initialize a HashMap to store each character and its most recent position.
2. We use two pointers, `left` and `right`, to define our current window.
3. As we iterate through the string with the `right` pointer:
   - If the current character is already in our window (i.e., its most recent position is at or after the `left` pointer), we move the `left` pointer to the position right after the last occurrence of this character.
   - We update the character's position in our HashMap.
   - We calculate the current window size (`right - left + 1`) and update our maximum length if necessary.
4. Finally, we return the maximum length we found.

The key insight is using the HashMap to efficiently jump the `left` pointer when we encounter a duplicate, rather than incrementing it one by one.

## Complexity Analysis:

- **Time Complexity**: $O(n)$ where n is the length of the string. We process each character exactly once.
- **Space Complexity**: $O(min(m, n))$ where m is the size of the character set and n is the length of the string. In the worst case, we might need to store all unique characters in the HashMap.

## Similar Problems:

1. [159. Longest Substring with At Most Two Distinct Characters](https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/)
2. [340. Longest Substring with At Most K Distinct Characters](https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/)
3. [424. Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement/)
4. [76. Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/)
5. [992. Subarrays with K Different Integers](https://leetcode.com/problems/subarrays-with-k-different-integers/)
