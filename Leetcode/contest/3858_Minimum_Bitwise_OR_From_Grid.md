# Minimum Bitwise OR From Grid

## Problem Description

**Problem Link:** [Minimum Bitwise OR From Grid](https://leetcode.com/problems/minimum-bitwise-or-from-grid/)

You are given an `m x n` integer matrix `grid`.

You must pick exactly one value from each row.

Return the minimum possible bitwise OR of the selected values.

**Example 1:**
```
Input: grid = [[1,5],[2,4]]
Output: 3
Explanation: choose 1 and 2, and 1 | 2 = 3.
```

**Example 2:**
```
Input: grid = [[3,5],[6,4]]
Output: 5
Explanation: choose 5 and 4, and 5 | 4 = 5.
```

**Example 3:**
```
Input: grid = [[7,9,8]]
Output: 7
```

**Constraints:**
- `1 <= m == grid.length <= 10^5`
- `1 <= n == grid[i].length <= 10^5`
- `m * n <= 10^5`
- `1 <= grid[i][j] <= 10^5`

## Intuition/Main Idea

To minimize OR, we want as many bits as possible to be `0`.

A bit can stay `0` in final OR only if **every chosen number** has that bit `0`.

So think in reverse: try to **ban** bits from appearing in chosen numbers.

### Build the intuition step by step

1. Maintain a mask `bannedBits`: bits that we force to be `0` in the final OR.
2. If a value has any banned bit set, we cannot choose that value.
3. A `bannedBits` mask is feasible iff every row still has at least one allowed value.
4. Process bits from high to low:
   - try banning this bit as well,
   - keep it banned only if feasibility still holds.

Why high to low? In integer minimization, making a higher bit `0` is always more valuable than any combination of lower bits.

### Why the intuition works

For any candidate mask of banned bits:
- Feasibility check is exact (row by row, at least one valid pick per row).
- If feasible, there exists a full row selection respecting all banned bits.

Greedy from high to low guarantees lexicographically smallest bit pattern for the answer, therefore minimum numeric OR.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Pick exactly one integer from each row | `canBanBit(...)` checks every row has at least one valid candidate |
| Minimize final bitwise OR | High-to-low greedy bit banning in `for (int bit = ...)` |
| Respect all constraints efficiently (`m*n <= 1e5`) | `O(B * m * n)` check with `B = 17` |
| Final OR value from banned bits | `answer = fullMask ^ bannedBits` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Bitmask Greedy + Feasibility Check]
class Solution {
    public int minimumOr(int[][] grid) {
        // grid[i][j] <= 1e5 < 2^17, so bits 0..16 are enough.
        final int maxBit = 16;
        int bannedBits = 0;

        // Try to force each bit to 0 in the answer, from high bit to low bit.
        for (int bit = maxBit; bit >= 0; bit--) {
            int candidateBannedBits = bannedBits | (1 << bit);
            if (canBanBitSet(grid, candidateBannedBits)) {
                bannedBits = candidateBannedBits;
            }
        }

        int fullMask = (1 << (maxBit + 1)) - 1;
        return fullMask ^ bannedBits;
    }

    private boolean canBanBitSet(int[][] grid, int bannedBits) {
        // For feasibility, each row must have at least one value with no banned bits set.
        for (int[] row : grid) {
            boolean rowHasValidChoice = false;

            for (int value : row) {
                if ((value & bannedBits) == 0) {
                    rowHasValidChoice = true;
                    break;
                }
            }

            if (!rowHasValidChoice) {
                return false;
            }
        }

        return true;
    }
}
```

## Complexity Analysis

Let `m = grid.length`, `n = grid[0].length`, and `B = 17`.

- **Time Complexity:** $O(B \cdot m \cdot n)$
- **Space Complexity:** $O(1)$ extra space

Because `m * n <= 10^5`, this is efficient in practice.

## Similar Problems

1. [2044. Count Number of Maximum Bitwise-OR Subsets](https://leetcode.com/problems/count-number-of-maximum-bitwise-or-subsets/) - Builds intuition around OR behavior and bit contribution.
2. [898. Bitwise ORs of Subarrays](https://leetcode.com/problems/bitwise-ors-of-subarrays/) - Another OR-focused bit-state problem.
3. [1639. Number of Ways to Form a Target String Given a Dictionary](https://leetcode.com/problems/number-of-ways-to-form-a-target-string-given-a-dictionary/) - Not OR-specific, but similar idea of row/position-wise feasibility under constraints.
