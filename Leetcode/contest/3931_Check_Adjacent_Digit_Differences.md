# 3931. Check Adjacent Digit Differences

> Weekly Contest 502, Q1. The statement below is reconstructed from the accepted solution (official contest text is login-gated).

## Problem Description

[Check Adjacent Digit Differences](https://leetcode.com/problems/check-adjacent-digit-differences/)

You are given a string `s` of digits. Return `true` if the absolute difference between **every** pair of adjacent characters is at most `2`, and `false` otherwise.

### Example 1

Input: `s = "13"`

Output: `true`

Explanation: `|1 - 3| = 2 <= 2`.

### Example 2

Input: `s = "14"`

Output: `false`

Explanation: `|1 - 4| = 3 > 2`.

### Constraints

- `1 <= s.length`.
- `s` consists of digit characters.

## Intuition / Main Idea

A single linear scan over adjacent pairs. There is no global structure to track — each adjacent pair is checked independently, and a single violation makes the answer `false`.

### Build the intuition step by step

1. Compare each character with the one before it.
2. The difference of two digit characters equals the difference of their numeric values (`'7' - '5' == 2`), so comparing `char`s directly is fine.
3. On the first pair exceeding the threshold, short-circuit and return `false`.
4. If the scan completes with no violation, return `true`.

### Why this works

The condition is purely local (every adjacent pair), so verifying each consecutive pair once is both necessary and sufficient. A single index from `1` to `n-1` is the cleanest form; the dual-pointer version below works identically.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Examine every adjacent pair | `while (right < s.length())` over `(left, right)` |
| Difference at most 2 | `if (Math.abs(l - r) > 2) return false;` |
| Whole string valid | `return true;` after the scan |

## Final Java Code & Learning Pattern

```java
// [Pattern: Linear scan over adjacent pairs]
class Solution {
    public boolean isAdjacentDiffAtMostTwo(String s) {
        int left = 0, right = 1;

        while (right < s.length()) {
            char l = s.charAt(left);
            char r = s.charAt(right);
            if (Math.abs(l - r) > 2) {
                return false;
            }
            left++;
            right++;
        }
        return true;
    }
}
```

### Why each part exists

- **`Math.abs(l - r)`** — char subtraction yields the numeric digit gap directly.
- **Early `return false`** — no need to scan further once any pair fails.
- A single index `for (int i = 1; i < n; i++)` comparing `s.charAt(i-1)` and `s.charAt(i)` is an equivalent, slightly tidier form.

## Complexity Analysis

- **Time Complexity:** $O(n)$ — one pass over the string.
- **Space Complexity:** $O(1)$.

## Similar Problems

1. [LeetCode 392. Is Subsequence](https://leetcode.com/problems/is-subsequence/) — linear two-pointer scan.
2. [LeetCode 657. Robot Return to Origin](https://leetcode.com/problems/robot-return-to-origin/) — single linear validation pass.
