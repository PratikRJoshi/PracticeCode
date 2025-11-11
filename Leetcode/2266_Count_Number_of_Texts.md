# Count Number of Texts

## Problem Description

**Problem Link:** [Count Number of Texts](https://leetcode.com/problems/count-number-of-texts/)

Alice is texting to Bob using a phone. The **mapping** of digits to letters is shown in the figure below. In order to add a letter, Alice has to press the key of the corresponding digit `i` times, where `i` is the position of the letter in the digit.

For example, to add the letter `'s'`, Alice has to press `'7'` four times. Similarly, to add the letter `'k'`, Alice has to press `'5'` two times. Note that the digits `'0'` and `'1'` do not map to any letters, so they are not used for texting.

Alice wants to know the **total number of ways** she can produce the text `pressedKeys`.

Given a string `pressedKeys` representing the pressed keys, return *the total number of ways Alice could have produced the text*.

Since the answer may be very large, return it **modulo** $10^9 + 7$.

**Example 1:**

```
Input: pressedKeys = "22233"
Output: 8
Explanation:
The possible text messages Alice could have typed are:
"aaadd", "abdd", "badd", "cdd", "aaae", "abe", "bae", and "ce".
Since there are 8 possible messages, we return 8.
```

**Example 2:**

```
Input: pressedKeys = "222222222222222222222222222222222222"
Output: 82876089
Explanation: There are 2082876103 ways to type the message. Since we need to return it modulo 10^9 + 7, the answer is 2082876103 % (10^9 + 7) = 82876089.
```

**Constraints:**
- `1 <= pressedKeys.length <= 10^5`
- `pressedKeys` only consists of digits from `'2'` - `'9'`.

## Intuition/Main Idea

This is a **dynamic programming** problem. We need to count ways to decode a sequence of key presses.

**Core Algorithm:**
1. Use DP where `dp[i]` = number of ways to decode `pressedKeys[0..i-1]`.
2. For each position, try decoding 1, 2, 3, or 4 consecutive same digits (depending on digit).
3. Digits 7 and 9 have 4 letters, others have 3.
4. `dp[i] = sum(dp[i-k])` for valid k (1 to max letters for that digit).

**Why DP works:** The problem has overlapping subproblems - counting ways to decode prefixes is needed multiple times. We can build the solution bottom-up.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| DP for ways | DP array - Line 6 |
| Base case | dp[0] = 1 - Line 7 |
| Process each position | For loop - Line 9 |
| Get digit | Digit extraction - Line 10 |
| Determine max letters | Max letters - Line 11 |
| Try all valid lengths | Length loop - Lines 13-17 |
| Update ways | DP update - Line 15 |
| Return result | Return statement - Line 19 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    private static final int MOD = 1000000007;
    
    public int countTexts(String pressedKeys) {
        int n = pressedKeys.length();
        Integer[] memo = new Integer[n];
        return countWays(pressedKeys, 0, memo);
    }
    
    private int countWays(String pressedKeys, int index, Integer[] memo) {
        if (index == pressedKeys.length()) {
            return 1;
        }
        
        if (memo[index] != null) {
            return memo[index];
        }
        
        char digit = pressedKeys.charAt(index);
        int maxLetters = (digit == '7' || digit == '9') ? 4 : 3;
        
        long ways = 0;
        for (int len = 1; len <= maxLetters && index + len <= pressedKeys.length(); len++) {
            if (pressedKeys.charAt(index + len - 1) == digit) {
                ways = (ways + countWays(pressedKeys, index + len, memo)) % MOD;
            } else {
                break;
            }
        }
        
        memo[index] = (int) ways;
        return (int) ways;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    private static final int MOD = 1000000007;
    
    public int countTexts(String pressedKeys) {
        int n = pressedKeys.length();
        long[] dp = new long[n + 1];
        dp[0] = 1; // Base case: 1 way to decode empty string
        
        for (int i = 1; i <= n; i++) {
            char digit = pressedKeys.charAt(i - 1);
            int maxLetters = (digit == '7' || digit == '9') ? 4 : 3;
            
            for (int len = 1; len <= maxLetters && i - len >= 0; len++) {
                if (pressedKeys.charAt(i - len) == digit) {
                    dp[i] = (dp[i] + dp[i - len]) % MOD;
                } else {
                    break;
                }
            }
        }
        
        return (int) dp[n];
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Line 7):** `dp[0] = 1` - 1 way to decode empty string.

2. **Process Positions (Lines 9-18):** For each position `i`:
   - **Get Digit (Line 10):** Current digit.
   - **Max Letters (Line 11):** Digits 7 and 9 have 4 letters, others have 3.
   - **Try Lengths (Lines 13-17):** Try decoding 1 to `maxLetters` consecutive same digits.

3. **Update Ways (Line 15):** Add ways from previous position.

**Intuition behind generating subproblems:**
- **Subproblem:** "How many ways to decode `pressedKeys[0..i-1]`?"
- **Why this works:** To decode up to position `i`, we can decode the last 1, 2, 3, or 4 consecutive same digits (depending on digit) and add ways from the remaining prefix.
- **Overlapping subproblems:** Multiple positions may share the same optimal subproblems.

**Example walkthrough for `pressedKeys = "22233"`:**
- dp[0]=1
- dp[1]=dp[0]=1 (decode "2" as "a")
- dp[2]=dp[1]+dp[0]=2 (decode "22" as "aa" or "b")
- dp[3]=dp[2]+dp[1]+dp[0]=4 (decode "222" as "aaa", "ab", "ba", or "c")
- dp[4]=dp[3]=4 (decode "3" as "d")
- dp[5]=dp[4]+dp[3]=8 (decode "33" as "dd" or "e")
- Result: 8 ✓

## Complexity Analysis

- **Time Complexity:** $O(n \times 4)$ = $O(n)$ where $n$ is the string length. We process each position and try up to 4 lengths.

- **Space Complexity:** $O(n)$ for the DP array.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **2266. Count Number of Texts** (this problem) - DP with variable length decoding
2. **91. Decode Ways** - DP decoding
3. **639. Decode Ways II** - DP with wildcards
4. **17. Letter Combinations of a Phone Number** - Phone keypad
5. **70. Climbing Stairs** - Similar DP pattern
6. **746. Min Cost Climbing Stairs** - DP with costs
7. **377. Combination Sum IV** - Count ways DP
8. **518. Coin Change 2** - Count ways DP

