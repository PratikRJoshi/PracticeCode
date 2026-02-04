### 424. Longest Repeating Character Replacement
### Problem Link: [Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement/)

### Problem Description
You are given a string `s` consisting of only uppercase English letters and an integer `k`.

You can choose any character of the string and change it to any other uppercase English character at most `k` times.

Return the length of the longest substring containing the same letter you can get after performing at most `k` replacements.

#### Example 1
- Input: `s = "ABAB", k = 2`
- Output: `4`
- Explanation: Replace the two `A`s with `B`s (or vice versa) to get a substring of length 4 with all the same character.

#### Example 2
- Input: `s = "AABABBA", k = 1`
- Output: `4`
- Explanation: One optimal answer is to replace the one `A` in `"AABA"` to get `"AAAA"` (length 4).

### Intuition/Main Idea
We want the longest window `[windowStart..windowEnd]` such that we can make every character in that window equal using at most `k` replacements.

For a fixed window, the best target character is always the one that already appears most frequently in that window.

- Let `windowSize = windowEnd - windowStart + 1`.
- Let `maxFrequencyInWindow` be the maximum count of any single character in the window.

Then the minimum number of replacements needed to make the whole window the same character is:

`replacementsNeeded = windowSize - maxFrequencyInWindow`

So the window is valid exactly when:

`windowSize - maxFrequencyInWindow <= k`

Sliding window strategy:
- Expand `windowEnd` to include more characters.
- Track frequencies in the current window, and track `maxFrequencyInWindow`.
- If the window becomes invalid (`windowSize - maxFrequencyInWindow > k`), shrink from the left (`windowStart++`) until it is valid again.
- Track the maximum valid window size seen.

Key detail: we do not recompute `maxFrequencyInWindow` when shrinking. It may become “stale” (too large), but that only makes the window look *more* valid than it truly is. The algorithm still returns the correct maximum length because:
- The answer is a maximum over all windows.
- `maxFrequencyInWindow` only ever increases when expanding.
- If staleness allows the window to stay larger temporarily, that larger window will eventually be validated by a future expansion that increases some character frequency (or it will be shrunk later). The recorded maximum length remains correct.

### Code Mapping
| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @Track counts of characters in current substring window | `int[] frequencyByLetter = new int[26];` and updates on expand/shrink |
| @Know how many replacements are needed for current window | `int replacementsNeeded = windowSize - maxFrequencyInWindow;` |
| @Shrink window when replacements exceed k | `if (replacementsNeeded > k) { ... windowStart++; }` |
| @Return maximum valid window size | `maxWindowSize = Math.max(maxWindowSize, windowEnd - windowStart + 1);` |

### Final Java Code & Learning Pattern (Full Content)
```java
class Solution {
    public int characterReplacement(String s, int k) {
        // frequencyByLetter[i] = count of ('A' + i) in the current window
        int[] frequencyByLetter = new int[26];

        int windowStart = 0;
        int maxWindowSize = 0;

        // Track the highest single-character frequency seen in the current window.
        // This lets us compute how many replacements are needed to unify the window.
        int maxFrequencyInWindow = 0;

        int windowEnd = 0;
        while (windowEnd < s.length()) {
            char addedCharacter = s.charAt(windowEnd);
            int addedIndex = addedCharacter - 'A';
            frequencyByLetter[addedIndex]++;

            // Update the best "keep" character frequency in this window.
            maxFrequencyInWindow = Math.max(maxFrequencyInWindow, frequencyByLetter[addedIndex]);

            // If we unify the window to its most frequent character,
            // every other character must be replaced.
            int windowSize = windowEnd - windowStart + 1;
            int replacementsNeeded = windowSize - maxFrequencyInWindow;

            // If we need more than k replacements, the window is too big.
            // Shrink from the left until it becomes valid again.
            if (replacementsNeeded > k) {
                char removedCharacter = s.charAt(windowStart);
                int removedIndex = removedCharacter - 'A';
                frequencyByLetter[removedIndex]--;
                windowStart++;

                // We intentionally do NOT recompute maxFrequencyInWindow here.
                // Keeping it as a possibly-stale upper bound preserves O(n) time.
            }

            // After potential shrinking, the current window is valid.
            maxWindowSize = Math.max(maxWindowSize, windowEnd - windowStart + 1);
            windowEnd++;
        }

        return maxWindowSize;
    }
}
```

**Learning Pattern:**
- For “longest substring under constraint”, try sliding window.
- Express the constraint as a simple formula on window statistics (here: `windowSize - maxFrequencyInWindow <= k`).
- Maintain those statistics incrementally while moving pointers.

### Complexity Analysis
- **Time Complexity**: $O(n)$ because each pointer only moves forward across the string.
- **Space Complexity**: $O(1)$ because we use a fixed-size array of 26 counts.

### Similar Problems
- [76. Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/)
- [340. Longest Substring with At Most K Distinct Characters](https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/)
- [1004. Max Consecutive Ones III](https://leetcode.com/problems/max-consecutive-ones-iii/)