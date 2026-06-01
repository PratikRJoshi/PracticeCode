# 564. Find the Closest Palindrome

## Problem Description

[Find the Closest Palindrome](https://leetcode.com/problems/find-the-closest-palindrome/)

Given a string `n` representing an integer, return the closest integer (as a string) that is a **palindrome**, not including `n` itself. If there is a tie, return the **smaller** one.

### Example 1

Input: `n = "123"`

Output: `"121"`

### Example 2

Input: `n = "1"`

Output: `"0"`

Explanation: `0` and `2` are both distance `1`; return the smaller, `0`.

### Constraints

- `1 <= n.length <= 18`.
- `n` has no leading zeros and represents a value in `[1, 10^18 - 1]`.

## Intuition / Main Idea

You cannot search outward number-by-number (the range is astronomically large). Instead, the closest palindrome is always among a **tiny fixed set of candidates** built by manipulating the **left half** of `n` plus two digit-count boundary values.

### Build the intuition step by step

1. A palindrome is determined by its left half (the right half mirrors it). To stay close to `n`, keep `n`'s left half — or nudge it by `±1`.
2. That gives three candidates: `mirror(left)`, `mirror(left - 1)`, `mirror(left + 1)`.
3. Two **boundary** candidates handle digit-count transitions:
   - `10^(len-1) - 1` — the all-nines value one digit shorter (e.g., `1000 → 999`).
   - `10^len + 1` — the smallest palindrome one digit longer (e.g., `99 → 101`).
4. Collect all five, drop `n` itself, and pick the closest (ties → smaller).

### Why this works

Any palindrome closer than these candidates would have to share `n`'s left half (covered by the three mirror candidates) or arise from a length change (covered by the two boundaries). So five candidates provably suffice.

### Pitfalls

- **`Math.pow` precision:** doubles lose precision past ~`10^15`, breaking 18-digit inputs. Use **`long`** arithmetic for the powers of 10.
- **Odd vs even length:** the left half includes the middle digit for odd lengths; mirroring must not duplicate that middle digit.
- **Tie-break:** equal distances → return the smaller value.
- **Exclude `n` itself** from the candidate set.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Mirror left half and `±1` neighbors | `for (long p = prefix - 1; p <= prefix + 1; p++) candidates.add(mirror(p, even));` |
| Digit-count boundary candidates | `candidates.add(pow10(len-1) - 1); candidates.add(pow10(len) + 1);` |
| Exclude `n` | `candidates.remove(num);` |
| Closest, ties → smaller | distance compare with `c < best` tie-break |
| Avoid float precision | `pow10` via `long` loop |

## Final Java Code & Learning Pattern

```java
// [Pattern: Candidate enumeration via palindrome construction]
import java.util.HashSet;
import java.util.Set;

class Solution {
    public String nearestPalindromic(String n) {
        int len = n.length();
        long num = Long.parseLong(n);

        Set<Long> candidates = new HashSet<>();
        candidates.add(pow10(len - 1) - 1);   // e.g., 1000 -> 999
        candidates.add(pow10(len) + 1);       // e.g., 99 -> 101

        long prefix = Long.parseLong(n.substring(0, (len + 1) / 2));
        for (long p = prefix - 1; p <= prefix + 1; p++) {
            candidates.add(mirror(p, len % 2 == 0));
        }

        candidates.remove(num);               // never return n itself

        long best = -1;
        for (long c : candidates) {
            if (c < 0) continue;
            if (best == -1
                || Math.abs(c - num) < Math.abs(best - num)
                || (Math.abs(c - num) == Math.abs(best - num) && c < best)) {
                best = c;
            }
        }
        return Long.toString(best);
    }

    private long pow10(int e) {
        long r = 1;
        for (int i = 0; i < e; i++) r *= 10;  // exact; avoids Math.pow double error
        return r;
    }

    private long mirror(long prefix, boolean evenLength) {
        String left = Long.toString(prefix);
        // For odd length, the middle digit is not duplicated when mirroring.
        String toReverse = evenLength ? left : left.substring(0, left.length() - 1);
        StringBuilder sb = new StringBuilder(left);
        sb.append(new StringBuilder(toReverse).reverse());
        return Long.parseLong(sb.toString());
    }
}
```

### Why each part exists

- **`pow10` via `long`** — exact powers of ten; `Math.pow` would corrupt 18-digit inputs.
- **`prefix = n.substring(0, (len+1)/2)`** — left half, including the middle digit for odd lengths.
- **`mirror(p, even)`** — rebuilds a palindrome from a (possibly nudged) left half; for odd lengths it skips re-appending the middle digit.
- **Boundary candidates** — cover the cases where the answer has a different digit count than `n`.
- **Tie-break `c < best`** — enforces "smaller on a tie".

## Complexity Analysis

- **Time Complexity:** $O(k)$ where `k` = number of digits (constant-size candidate set; each built in `O(k)`).
- **Space Complexity:** $O(k)$.

## Similar Problems

1. [LeetCode 9. Palindrome Number](https://leetcode.com/problems/palindrome-number/) — palindrome test fundamentals.
2. [LeetCode 479. Largest Palindrome Product](https://leetcode.com/problems/largest-palindrome-product/) — constructing palindromes from halves.
3. [LeetCode 906. Super Palindromes](https://leetcode.com/problems/super-palindromes/) — enumerate palindromes by building from a half.
