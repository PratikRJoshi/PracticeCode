### 76. Minimum Window Substring
Problem: https://leetcode.com/problems/minimum-window-substring/description/

---

### Main Idea & Intuition

The problem asks us to find the minimum window in a string `s` that contains all the characters of a given string `t`. The key insight is to use a sliding window approach with character frequency tracking.

#### Core Concept: The Sliding Window Algorithm

1. **Expand the window**: Add characters from the right and update frequencies
2. **Check validity**: If the window contains all characters of `t`, try to shrink from the left
3. **Track minimum**: Keep track of the smallest valid window seen

#### Understanding the Frequency Calculation

The frequency map serves two key purposes:
1. It tracks how many of each character we need (positive values)
2. It tracks how many extra characters we have (negative values)

When `map[c] > 0`, it means we still need more of that character.
When `map[c] <= 0`, it means we have enough (or extra) of that character.

The `matches` variable is crucial - it counts how many characters from `t` we've found in our current window. When `matches == t.length()`, we have a valid window.

#### Code Implementation

```java
class Solution {
    public String minWindow(String s, String t) {
        // Character frequency map: positive values indicate characters we need
        // negative values indicate characters we have extra of
        int[] map = new int[128];
        
        // Initialize the map with characters from t
        for(char c : t.toCharArray()){
            map[c]++;
        }

        // Variables to track window state
        int start = 0;          // Left pointer of the window
        int end = 0;            // Right pointer of the window
        int minStart = 0;       // Starting index of the minimum window found
        int minLen = Integer.MAX_VALUE;  // Length of the minimum window found
        int matches = 0;        // Count of characters from t found in current window

        // Expand the window by moving the right pointer
        while(end < s.length()){
            char c1 = s.charAt(end);
            
            // If this character is needed (map[c1] > 0), increment matches
            if(map[c1] > 0){
                matches++; // We've found a required character
            }
            // Decrement frequency to indicate we've processed this character
            map[c1]--; // Update the frequency map

            // When we have all required characters, try to shrink the window
            while(matches == t.length()){
                char c2 = s.charAt(start);
                
                // Increment frequency as we're removing this character from window
                map[c2]++; // Update the frequency map

                // If this character was needed (map[c2] > 0 after incrementing),
                // we've lost a required character, so decrement matches
                if(map[c2] > 0){
                    matches--; // We've lost a required character
                }

                // Update minimum window if current window is smaller
                if(minLen > end - start){
                    minLen = end - start; // Update the minimum window length
                    minStart = start; // Update the starting index of the minimum window
                }
                
                // Shrink the window from the left
                start++; // Move the left pointer to the right
            }
            
            // Continue expanding the window
            end++; // Move the right pointer to the right
        }

        // If no valid window found, return empty string
        // Otherwise, return the minimum window substring
        // Note: +1 is needed because end-start gives the distance between pointers,
        // not the length of the substring (which includes both endpoints)
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen + 1);
    }
}
```

> **Why is the `+1` needed in the return statement?**
>
> The `+1` is crucial because of how we calculate `minLen`. When we find a valid window, we set:
> ```java
> minLen = end - start;
> ```
> 
> This calculation gives us the *distance* between the pointers, not the actual length of the substring. For example:
> - If `start = 2` and `end = 5`, then `end - start = 3`
> - But to include both indices in the substring, we need length `4` (indices 2, 3, 4, 5)
> 
> When using `substring(minStart, minStart + minLen + 1)`:
> - The first parameter `minStart` is inclusive
> - The second parameter `minStart + minLen + 1` is exclusive
> - So we need the `+1` to include the character at position `end`
>
> Without the `+1`, we would be missing the last character of our window.

### Detailed Algorithm Walkthrough

1. **Initialize a frequency map**: We use an array of size 128 to cover all ASCII characters. Each entry `map[c]` represents how many more of character `c` we need.

2. **Track matches**: The `matches` variable counts how many characters from `t` we've successfully matched in our current window.

3. **Expand the window**:
   - Add the current character to our window
   - If `map[c1] > 0`, this character is needed for `t`, so increment `matches`
   - Decrement `map[c1]` to indicate we've processed this character

4. **Shrink the window when valid**:
   - When `matches == t.length()`, we have a valid window
   - Try to shrink from the left by incrementing `map[c2]`
   - If `map[c2]` becomes positive after incrementing, it means we've removed a necessary character, so decrement `matches`
   - Update `minLen` and `minStart` if we found a smaller valid window

5. **Return the result**:
   - If no valid window was found, return an empty string
   - Otherwise, return the substring using `minStart` and `minLen + 1`

### Complexity Analysis

*   **Time Complexity**: `O(n)`, where `n` is the length of the string `s`. We process each character at most twice (once by the `end` pointer and once by the `start` pointer).
*   **Space Complexity**: `O(1)` because we use a fixed-size array of 128 characters, regardless of the input size.

### Example Walkthrough

Let's trace through a simple example: `s = "ADOBECODEBANC"` and `t = "ABC"`

| Step | Window | Action | `map` after action | `matches` | `minLen` |
|------|--------|--------|-------------------|-----------|----------|
| 1 | A | Expand | A:-1, B:1, C:1 | 1 | MAX_VALUE |
| 2 | AD | Expand | A:-1, B:1, C:1, D:-1 | 1 | MAX_VALUE |
| 3 | ADO | Expand | A:-1, B:1, C:1, D:-1, O:-1 | 1 | MAX_VALUE |
| 4 | ADOB | Expand | A:-1, B:0, C:1, D:-1, O:-1 | 2 | MAX_VALUE |
| 5 | ADOBEC | Expand | A:-1, B:0, C:0, D:-1, E:-1, O:-1 | 3 | MAX_VALUE |
| 5a | DOBEC | Shrink | A:0, B:0, C:0, D:-1, E:-1, O:-1 | 2 | 5 |
| 6 | DOBECO | Expand | A:0, B:0, C:0, D:-1, E:-1, O:-2 | 2 | 5 |
| 7 | DOBECODEB | Expand | A:0, B:-1, C:0, D:-1, E:-1, O:-2 | 2 | 5 |
| 8 | DOBECODEBA | Expand | A:-1, B:-1, C:0, D:-1, E:-1, O:-2 | 3 | 5 |
| 9 | DOBECODEBA | Shrink | A:0, B:-1, C:0, D:0, E:-1, O:-2 | 2 | 5 |
| 10 | OBECODEBA | Shrink | A:0, B:-1, C:0, D:0, E:0, O:-1 | 2 | 5 |
| 11 | BECODEBA | Shrink | A:0, B:-1, C:0, D:0, E:0, O:0 | 2 | 5 |
| 12 | ECODEBA | Shrink | A:0, B:0, C:0, D:0, E:0, O:0 | 1 | 5 |
| 13 | ECODEBANC | Expand | A:0, B:0, C:-1, D:0, E:0, N:-1, O:0 | 2 | 5 |
| 14 | CODEBANC | Shrink | A:0, B:0, C:-1, D:0, E:1, N:-1, O:0 | 2 | 5 |
| 15 | ODEBANC | Shrink | A:0, B:0, C:0, D:0, E:1, N:-1, O:0 | 1 | 5 |
| 16 | DEBANC | Shrink | A:0, B:0, C:0, D:0, E:1, N:-1, O:1 | 1 | 5 |
| 17 | EBANC | Shrink | A:0, B:0, C:0, D:1, E:1, N:-1, O:1 | 1 | 5 |
| 18 | BANC | Shrink | A:0, B:0, C:0, D:1, E:2, N:-1, O:1 | 1 | 4 |
| 19 | ANC | Shrink | A:0, B:1, C:0, D:1, E:2, N:-1, O:1 | 0 | 4 |

The minimum window is "BANC" with length 4.