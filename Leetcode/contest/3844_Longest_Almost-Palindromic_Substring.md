# Longest Almost-Palindromic Substring

## Problem Description

**Problem Link:** [Longest Almost-Palindromic Substring](https://leetcode.com/problems/longest-almost-palindromic-substring/)

You are given a string `s` consisting of lowercase English letters.

A substring is **almost-palindromic** if it becomes a palindrome after removing **exactly one character** from it.

Return the length of the longest almost-palindromic substring in `s`.

**Example 1:**
```
Input: s = "abca"
Output: 4
Explanation:
Substring "abca" is almost-palindromic because removing 'c' gives "aba" (a palindrome).
```

**Example 2:**
```
Input: s = "abba"
Output: 4
Explanation:
Substring "abba" is almost-palindromic because removing one of the middle 'b' characters gives "aba" (a palindrome).
```

**Example 3:**
```
Input: s = "zzabba"
Output: 5
Explanation:
Substring "zzabb" -> remove one 'z' gives "zabb" (not palindrome), but substring "zabba" (length 5) removing 'z' gives "abba".
```

**Constraints:**

- `2 <= s.length <= 2500`
- `s` consists only of lowercase English letters

## Intuition/Main Idea

### Build the intuition (mentor-style)

For a normal palindrome-substring problem, we often build a DP table:

- `pal[left][right] = true` if `s[left..right]` is a palindrome.

Here we need “almost-palindromic”:

- A substring is valid if it can become a palindrome after removing **exactly one** character.

So we’ll build **two** DP tables:

1. `pal[left][right]`: `s[left..right]` is a palindrome (0 deletions)
2. `almost[left][right]`: `s[left..right]` can become a palindrome after removing **exactly one** character

### How to derive the transitions (step-by-step)

#### 1) Palindrome DP (`pal`)

- If `s[left] == s[right]`, then `pal[left][right] = pal[left + 1][right - 1]`.
- Base cases:
  - length 1 is always palindrome
  - length 2 is palindrome if both chars equal

#### 2) Exactly-one-deletion DP (`almost`)

There are two cases for substring `s[left..right]`:

1. **Ends match**: `s[left] == s[right]`
   - Then we must still use exactly one deletion inside:
   - `almost[left][right] = almost[left + 1][right - 1]`

2. **Ends mismatch**: `s[left] != s[right]`
   - The only way to fix it with exactly one deletion is to delete one of these two ends:
     - delete `s[left]` and the rest `s[left+1..right]` must be a palindrome
     - delete `s[right]` and the rest `s[left..right-1]` must be a palindrome
   - So:
   - `almost[left][right] = pal[left + 1][right] || pal[left][right - 1]`

#### Important note: substrings that are already palindromes

If `s[left..right]` is already a palindrome and its length is at least 2, then it is also almost-palindromic:

- Remove one of the middle characters and the remaining string is still a palindrome.

So:

- if `pal[left][right] == true` and `(right - left + 1) >= 2`, then `almost[left][right] = true`.

### Why the intuition works

The “exactly one deletion” requirement only ever interacts with the palindrome condition in two ways:

- If ends match, the decision moves inward unchanged.
- If ends mismatch, deleting anything other than one of the two ends cannot fix the first mismatch.

So the transitions above are complete.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Consider all substrings up to length 2500 | Build `pal` and `almost` DP tables in $O(n^2)$ (lines 16-70) |
| Remove exactly one character | `almost` transitions and palindrome-shortcut for palindromes (lines 38-63) |
| Return maximum length | Track global maximum over all `almost[left][right]` (lines 65-70) |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int longestAlmostPalindromeSubstring(String s) {
        int n = s.length();
        int bestLength = 0;

        // pal[left][right] = s[left..right] is a palindrome.
        // We allocate n x n because we want O(1) palindrome checks for any substring.
        boolean[][] pal = new boolean[n][n];

        // almost[left][right] = s[left..right] can become palindrome after removing exactly one char.
        // Same size n x n because it is defined for each substring.
        boolean[][] almost = new boolean[n][n];

        // Fill tables by increasing substring length.
        for (int len = 1; len <= n; len++) {
            for (int left = 0; left + len - 1 < n; left++) {
                int right = left + len - 1;

                // Build pal DP.
                if (len == 1) {
                    pal[left][right] = true;
                } else if (len == 2) {
                    pal[left][right] = (s.charAt(left) == s.charAt(right));
                } else {
                    pal[left][right] = (s.charAt(left) == s.charAt(right)) && pal[left + 1][right - 1];
                }

                // Build almost DP (exactly one deletion).
                if (len >= 2) {
                    if (pal[left][right]) {
                        // If it's already a palindrome, we can delete a middle character
                        // and still have a palindrome.
                        almost[left][right] = true;
                    } else {
                        if (s.charAt(left) == s.charAt(right)) {
                            // Ends match, deletion must happen inside.
                            almost[left][right] = almost[left + 1][right - 1];
                        } else {
                            // Ends mismatch: the only way with one deletion is to delete one end
                            // and require the remaining substring to be a perfect palindrome.
                            almost[left][right] = pal[left + 1][right] || pal[left][right - 1];
                        }
                    }
                }

                if (almost[left][right]) {
                    bestLength = Math.max(bestLength, len);
                }
            }
        }

        return bestLength;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n^2)$

- We fill `pal` and `almost` for all substrings.
- There are $O(n^2)$ substrings, and each DP transition is $O(1)$.

**Space Complexity:** $O(n^2)$

- Two `n x n` boolean DP tables.

## Alternative Solution 1: DP with Single `pal` Table

The solution above uses two DP tables (`pal` and `almost`). We can also solve this by **reusing the palindrome-checking logic from LC 5** and extending it to handle one deletion.

**Key insight:** Build a `pal` DP table (as in LC 5) to answer "is s[i..j] a palindrome?" in O(1). Then, for each substring, check if it is almost-palindromic by trying to delete each character and checking if the result is a palindrome.

```java
class Solution {
    public int longestAlmostPalindromeSubstring(String s) {
        int n = s.length();
        
        // Build pal DP table (from LC 5 approach).
        // pal[i][j] = true if s[i..j] is a palindrome.
        boolean[][] pal = new boolean[n][n];
        
        // Base case: single characters are palindromes.
        for (int i = 0; i < n; i++) {
            pal[i][i] = true;
        }
        
        // Check length 2 substrings.
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                pal[i][i + 1] = true;
            }
        }
        
        // Fill DP table for length 3 and above.
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && pal[i + 1][j - 1]) {
                    pal[i][j] = true;
                }
            }
        }
        
        int bestLength = 0;
        
        // Check for almost-palindromic substrings.
        // A substring s[i..j] is almost-palindromic if removing exactly one character makes it a palindrome.
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int len = j - i + 1;
                
                if (len < 2) continue;
                
                // Case 1: Already a palindrome with length >= 2.
                if (pal[i][j]) {
                    bestLength = Math.max(bestLength, len);
                }
                // Case 2: Try deleting each character and check if result is palindrome.
                else {
                    for (int k = i; k <= j; k++) {
                        // Check if removing s[k] makes s[i..j] a palindrome.
                        if (isPalindromeAfterDelete(s, i, j, k, pal)) {
                            bestLength = Math.max(bestLength, len);
                            break;
                        }
                    }
                }
            }
        }
        
        return bestLength;
    }
    
    private boolean isPalindromeAfterDelete(String s, int left, int right, int deleteIdx, boolean[][] pal) {
        int l = left, r = right;
        while (l < r) {
            if (l == deleteIdx) l++;
            if (r == deleteIdx) r--;
            if (l < r && s.charAt(l) != s.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }
}
```

**Why this approach works:**

- We reuse the exact same `pal` DP table construction from LC 5.
- For each substring s[i..j]:
  1. If it's already a palindrome (length >= 2), it's almost-palindromic.
  2. Otherwise, try deleting each character k in [i, j] and check if the remaining string is a palindrome.
- This correctly handles deletions from anywhere in the substring, not just the ends.

**Comparison to the main solution:**

| Aspect | Main Solution | DP with Single Table |
|--------|---------------|----------------------|
| DP tables | Two (`pal` and `almost`) | One (`pal` only) |
| Space | $O(n^2)$ for two tables | $O(n^2)$ for one table |
| Time | $O(n^2)$ | $O(n^4)$ worst case (try delete each char) |
| Clarity | Explicit state transitions | Reuses LC 5 logic directly |
| Reusability | Self-contained | Extends LC 5 approach |

The main solution is more efficient; this alternative is simpler to understand.

## Alternative Solution 2: Clever Expand Around Center (LC 5 Extension)

We can extend the **expand-around-center approach** from LC 5 to handle almost-palindromic substrings by **continuing to expand after hitting a mismatch**. The key insight is to skip one character at the mismatch point and keep expanding to find longer almost-palindromic substrings.

```java
class Solution {
    public int longestAlmostPalindromeSubstring(String s) {
        int n = s.length();
        int bestLength = 0; // longest substring that becomes a palindrome after deleting exactly one char

        // Try all possible centers and delegate the actual expansion work
        // to helper methods for odd-length and even-length palindromes.
        for (int center = 0; center < n; center++) {
            bestLength = Math.max(bestLength, expandFromOddCenter(s, center));
            bestLength = Math.max(bestLength, expandFromEvenCenter(s, center));
        }

        // We never want to return more than the whole string length.
        return Math.min(bestLength, n);
    }

    // Expand around an odd-length center (like "aba" or "abcba")
    // and return the best almost-palindromic length contributed by this center.
    private int expandFromOddCenter(String s, int center) {
        int n = s.length();
        int bestLength = 0;

        int left = center;
        int right = center;

        // Step 1: expand as a normal palindrome while characters match.
        // For any perfect palindrome [left..right], we can delete any
        // one character inside it and still have a palindrome.
        while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
            // +2 because we are allowed to delete exactly one character
            // from this perfectly palindromic window.
            bestLength = Math.max(bestLength, right - left + 2);
            left--;
            right++;
        }

        // At this point, (left, right) is the first mismatching pair or out of bounds.
        // Try skipping the character on the LEFT side of the mismatch
        // and continue expanding as long as it remains a palindrome.
        int skipLeft = left - 1;
        int keepRight = right;
        while (skipLeft >= 0 && keepRight < n && s.charAt(skipLeft) == s.charAt(keepRight)) {
            bestLength = Math.max(bestLength, keepRight - skipLeft + 1);
            skipLeft--;
            keepRight++;
        }

        // Now try skipping the character on the RIGHT side of the mismatch
        // and again continue expanding.
        int keepLeft = left;
        int skipRight = right + 1;
        while (keepLeft >= 0 && skipRight < n && s.charAt(keepLeft) == s.charAt(skipRight)) {
            bestLength = Math.max(bestLength, skipRight - keepLeft + 1);
            keepLeft--;
            skipRight++;
        }

        return bestLength;
    }

    // Expand around an even-length center (like "abba")
    // and return the best almost-palindromic length contributed by this center.
    private int expandFromEvenCenter(String s, int center) {
        int n = s.length();
        int bestLength = 0;

        int left = center;
        int right = center + 1;

        // Step 1: expand as a normal even-length palindrome.
        while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
            bestLength = Math.max(bestLength, right - left + 2);
            left--;
            right++;
        }

        // Mismatch at (left, right): skip LEFT character and keep expanding.
        int skipLeft = left - 1;
        int keepRight = right;
        while (skipLeft >= 0 && keepRight < n && s.charAt(skipLeft) == s.charAt(keepRight)) {
            bestLength = Math.max(bestLength, keepRight - skipLeft + 1);
            skipLeft--;
            keepRight++;
        }

        // Mismatch at (left, right): skip RIGHT character and keep expanding.
        int keepLeft = left;
        int skipRight = right + 1;
        while (keepLeft >= 0 && skipRight < n && s.charAt(keepLeft) == s.charAt(skipRight)) {
            bestLength = Math.max(bestLength, skipRight - keepLeft + 1);
            keepLeft--;
            skipRight++;
        }

        return bestLength;
    }
}
```

**How this approach works:**

For each center (odd and even):

1. **Expand while matching:** Expand outward while `s[l] == s[r]`. Record `r - l + 2` (includes one extra character that will be deleted).

2. **Skip left, continue expanding:** When mismatch at `(l, r)`, skip `s[l]` (set `sl = l - 1`) and continue expanding. This finds almost-palindromes where deleting the left character fixes it.

3. **Skip right, continue expanding:** Skip `s[r]` (set `sr = r + 1`) and continue expanding. This finds almost-palindromes where deleting the right character fixes it.

**Why this works:**

- **Continues after mismatch:** Unlike a naive approach that stops at the first mismatch, this continues expanding after skipping one character, finding longer almost-palindromic substrings.
- **Handles middle deletions:** For "abca" centered at 'b', it expands to find the full "abca" substring, then when it hits the mismatch between 'a' and 'a' (actually 'c'), it skips 'c' and continues, capturing the almost-palindrome.
- **Reuses LC 5 structure:** Directly extends the expand-around-center approach from LC 5.

**Complexity:**

- **Time:** $O(n^2)$ – for each of $O(n)$ centers, we expand $O(n)$ in the worst case.
- **Space:** $O(1)$ – only pointers, no DP table needed.

**Comparison of all three approaches:**

| Approach | Time | Space | Correctness | Reuses LC 5 |
|----------|------|-------|-------------|------------|
| Main (Two DP tables) | $O(n^2)$ | $O(n^2)$ | ✓ Correct | Partially |
| DP with Single Table | $O(n^4)$ worst | $O(n^2)$ | ✓ Correct | Yes |
| Clever Expand Around Center | $O(n^2)$ | $O(1)$ | ✓ Correct | Yes (most directly) |

**Recommendation:** This clever expand-around-center approach is the **most space-efficient and most directly extends the LC 5 code** while remaining correct. Use this for interviews when space optimization is valued.

## Alternative Solution 3: Top-Down Memoized DP (LC 5 Style)

We can also write a **top-down, memoized version** of the DP logic. Instead of filling `pal` and `almost` tables bottom-up, we define two recursive helpers:

- `isPalindrome(i, j)`: whether `s[i..j]` is a perfect palindrome (LC 5 recurrence), memoized.
- `isAlmostPalindrome(i, j)`: whether `s[i..j]` becomes a palindrome after deleting **exactly one** character, using the same recurrence as the main DP solution.

```java
class Solution {
    private String s;
    private Boolean[][] palMemo;   // memo for perfect palindromes
    private Boolean[][] almostMemo; // memo for almost-palindromes

    public int longestAlmostPalindromeSubstring(String s) {
        int n = s.length();
        this.s = s;
        this.palMemo = new Boolean[n][n];
        this.almostMemo = new Boolean[n][n];

        int bestLength = 0;

        // Try all substrings s[i..j] and ask: is it almost-palindromic?
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) { // length >= 2
                if (isAlmostPalindrome(i, j)) {
                    bestLength = Math.max(bestLength, j - i + 1);
                }
            }
        }

        return bestLength;
    }

    // Standard LC 5-style palindrome check with memoization.
    private boolean isPalindrome(int left, int right) {
        if (left >= right) {
            return true; // empty or single char is palindrome
        }
        if (palMemo[left][right] != null) {
            return palMemo[left][right];
        }

        boolean result = (s.charAt(left) == s.charAt(right))
                && isPalindrome(left + 1, right - 1);

        palMemo[left][right] = result;
        return result;
    }

    // Top-down version of the "almost" DP recurrence.
    // Returns true if s[left..right] can become a palindrome after deleting exactly one character.
    private boolean isAlmostPalindrome(int left, int right) {
        int len = right - left + 1;
        if (len < 2) {
            return false; // need at least 2 chars to delete one and still have a palindrome
        }
        if (almostMemo[left][right] != null) {
            return almostMemo[left][right];
        }

        boolean result;

        if (s.charAt(left) == s.charAt(right)) {
            // If the ends match, we can always delete some character in the middle
            // (or one of the ends) and still form a palindrome for len >= 2.
            result = true;
        } else {
            // Ends do not match: try all three ways to use our single deletion.
            // 1) Delete one of the mismatched ends and require the rest to be a palindrome.
            // 2) Delete a middle character so that s[left+1..right-1] becomes palindrome.
            result = isPalindrome(left + 1, right - 1)
                    || isPalindrome(left + 1, right)
                    || isPalindrome(left, right - 1);
        }

        almostMemo[left][right] = result;
        return result;
    }
}
```

**Intuition for the memoized version:**

- `isPalindrome(left, right)` is exactly the LC 5 top-down recurrence:
  - Base: length 0 or 1 is a palindrome.
  - Recurrence: ends must match, and the inner substring must be palindrome.
- `isAlmostPalindrome(left, right)` mirrors the bottom-up DP logic:
  - If `s[left] == s[right]` and `len >= 2`, we can always delete **some** character inside and keep a palindrome.
  - If `s[left] != s[right]`, we spend our one deletion on:
    - Deleting one of the ends and requiring the rest to be a perfect palindrome, or
    - Deleting a middle character so that `s[left+1..right-1]` becomes a palindrome.
- The outer double loop just scans all substrings and uses these memoized checks.

**Complexity:**

- `palMemo` and `almostMemo` each store $O(n^2)$ states.
- Each state is computed once, and each transition is $O(1)$.
- Total time: $O(n^2)`, space: $O(n^2)$, same as the main DP solution but in a top-down style.

## Similar Problems

- [Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/) - classic DP approach (LC 5)
- [Valid Palindrome II](https://leetcode.com/problems/valid-palindrome-ii/) - delete at most one character to make palindrome
