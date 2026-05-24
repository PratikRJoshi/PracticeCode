# 3941. Password Strength

## Problem Description

Given a string `password`, compute its strength using the following rules. Each character contributes **at most once** even if it appears multiple times.

- 1 point per distinct lowercase letter (`a–z`)
- 2 points per distinct uppercase letter (`A–Z`)
- 3 points per distinct digit (`0–9`)
- 5 points per distinct special character from `"!@#$"`

Return the total strength.

### Example 1

Input: `password = "aA1!"`

Output: `11`

Explanation: Distinct chars `{'a', 'A', '1', '!'}` → `1 + 2 + 3 + 5 = 11`.

### Example 2

Input: `password = "bbB11#"`

Output: `11`

Explanation: Distinct chars `{'b', 'B', '1', '#'}` → `1 + 2 + 3 + 5 = 11`.

### Constraints

- `1 <= password.length <= 10^5`
- Characters are lowercase / uppercase letters, digits, and `!@#$`.

## Intuition / Main Idea

"At most once" = **distinct**. A `Set<Character>` collapses duplicates in one pass, after which we categorize each unique character.

### Build the intuition step by step

1. Walk the password once and dump every character into a `HashSet<Character>`.
2. For each unique character, look up its category and add its point value.
3. Categories are mutually exclusive — use `Character.isDigit`, `Character.isUpperCase`, `Character.isLowerCase`, and a small set for `!@#$`.

### Why this works

Sets deduplicate in `O(1)` per insertion, so the total cost is `O(n)`. After deduplication, the set has at most `26 + 26 + 10 + 4 = 66` elements, so the scoring loop is effectively `O(1)`.

## Code Mapping

| Problem Requirement | Java Code Section |
|---------------------|-------------------|
| "At most once" per character | `unique.add(c)` in a `HashSet<Character>` |
| Score lowercase / uppercase / digit | `Character.isLowerCase`, `Character.isUpperCase`, `Character.isDigit` |
| Score special chars `!@#$` | `Set.of('!','@','#','$').contains(c)` |
| Single-pass tally | One loop over `unique`, accumulator `result` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Hash Set deduplication + categorized scoring]
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int passwordStrength(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }

        Set<Character> special = Set.of('!', '@', '#', '$');

        Set<Character> unique = new HashSet<>();
        for (char c : password.toCharArray()) {
            unique.add(c);
        }

        int result = 0;
        for (char c : unique) {
            if (Character.isDigit(c)) {
                result += 3;
            } else if (Character.isUpperCase(c)) {
                result += 2;
            } else if (Character.isLowerCase(c)) {
                result += 1;
            } else if (special.contains(c)) {
                result += 5;
            }
        }

        return result;
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n)$ — single pass to deduplicate; the scoring loop is bounded by 66 unique chars.
- **Space Complexity:** $O(1)$ — the `HashSet` holds at most 66 distinct characters regardless of input size.

## Common Pitfalls

- **Scoring on the raw string** instead of the deduplicated set — duplicates get counted multiple times.
- **Forgetting `!@#$` as a separate category** — these aren't matched by `isDigit` / `isLetter`, so they'd silently score 0 if you skip the `contains` check.
- **Using `String.contains`** with a literal like `"!@#$".contains(...)` only works on `CharSequence` — you'd need `indexOf(c) >= 0`. A small `Set<Character>` is clearer.

## Similar Problems

1. [LeetCode 387. First Unique Character in a String](https://leetcode.com/problems/first-unique-character-in-a-string/) — same character-frequency idiom.
2. [LeetCode 771. Jewels and Stones](https://leetcode.com/problems/jewels-and-stones/) — set membership over characters.
3. [LeetCode 1832. Check if the Sentence Is Pangram](https://leetcode.com/problems/check-if-the-sentence-is-pangram/) — distinct-character tracking with a `Set<Character>`.
