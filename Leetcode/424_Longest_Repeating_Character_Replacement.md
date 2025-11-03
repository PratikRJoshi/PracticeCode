### 424. Longest Repeating Character Replacement

#### Problem Statement
[Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement/)

---

### Main Idea & Intuition

This problem asks us to find the length of the longest substring containing the same letter after performing at most `k` character replacements. The key insight is to use a sliding window approach with character frequency tracking.

#### Core Concept: The Replacement Formula

For any window of characters, we can make all characters the same if:
- The number of replacements needed ≤ `k`
- The number of replacements needed = `windowLength - count of the most frequent character`

For example, in the window "AAABBC" with `k = 2`:
- Most frequent character: 'A' (count = 3)
- Window length: 6
- Replacements needed: 6 - 3 = 3
- Since 3 > k (which is 2), this window is invalid

#### The Sliding Window Algorithm

1. **Expand the window**: Add characters from the right and update frequencies
2. **Check validity**: If `(window length - most frequent count) > k`, the window has too many characters to replace
3. **Contract the window**: If invalid, shrink from the left until valid again
4. **Track maximum**: Keep track of the longest valid window seen

### Understanding the Frequency Calculation

The frequency map serves two critical purposes:
1. It tells us how many of each character we have in the current window
2. It helps us quickly find the most frequent character count

The formula `windowLength - maxFrequency` gives us exactly how many characters need to be replaced to make the entire window the same character. If this number exceeds `k`, we know our window is too large.

### Code Implementation

```java
class Solution {
    public int characterReplacement(String s, int k) {
        int[] freq = new int[26]; // Frequency map for uppercase English letters
        int maxLength = 0;
        int maxFrequency = 0; // Track the highest frequency in the current window
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            // Expand the window and update frequencies
            char rightChar = s.charAt(right);
            freq[rightChar - 'A']++;
            
            // Update the max frequency if we found a new max
            maxFrequency = Math.max(maxFrequency, freq[rightChar - 'A']);
            
            // Calculate how many characters we need to replace
            int replacementsNeeded = (right - left + 1) - maxFrequency;
            
            // If we need more replacements than allowed, shrink the window
            if (replacementsNeeded > k) {
                // Remove the leftmost character from our frequency map
                char leftChar = s.charAt(left);
                freq[leftChar - 'A']--;
                left++;
                
                // Note: We don't need to update maxFrequency when the window shrinks.
                // This is a key optimization that keeps our algorithm O(n).
            }
            
            // Update the max length (current window size)
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}
```

> **Why don't we update `maxFrequency` when the window shrinks?**
>
> This is a subtle but important optimization. When we shrink the window, the maximum frequency might decrease, but:
> 1. We're only interested in finding the longest valid window
> 2. If a smaller window with the same `maxFrequency` becomes valid, it won't be longer than our previous valid window
> 3. If a new character later increases the frequency beyond our current `maxFrequency`, we'll catch it in the expansion phase
>
> This keeps our algorithm O(n) instead of potentially O(n²) if we had to recalculate the max frequency every time.

### Complexity Analysis

*   **Time Complexity**: `O(n)`, where `n` is the length of the string. We process each character exactly once in the worst case.
*   **Space Complexity**: `O(1)` because we use a fixed-size array of 26 characters, regardless of the input size.

### Example Walkthrough

Let's trace through a simple example: `s = "AABABBA"` with `k = 1`

| Step | Window | Frequencies | maxFreq | Replacements | Action |
|------|--------|-------------|---------|-------------|--------|
| 1 | A | A:1 | 1 | 0 | Expand |
| 2 | AA | A:2 | 2 | 0 | Expand |
| 3 | AAB | A:2, B:1 | 2 | 1 | Expand |
| 4 | AABA | A:3, B:1 | 3 | 1 | Expand |
| 5 | AABAB | A:3, B:2 | 3 | 2 > k | Shrink |
| 6 | ABAB | A:2, B:2 | 3 | 1 | Expand |
| 7 | ABABB | A:2, B:3 | 3 | 2 > k | Shrink |
| 8 | BABB | A:1, B:3 | 3 | 1 | Expand |
| 9 | BABBA | A:2, B:3 | 3 | 2 > k | Shrink |

The longest valid window has length 4 (either "AABA" or "BABB").