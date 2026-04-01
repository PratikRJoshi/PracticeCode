# Arranging Numbers ([LeetCode 526: Beautiful Arrangement](https://leetcode.com/problems/beautiful-arrangement/))

## 1) Problem Description

You are given an integer `n`. Consider numbers from `1` to `n`.

An arrangement (permutation) is called **beautiful** if for every 1-based position `i`, at least one condition is true:
- `arr[i] % i == 0`
- `i % arr[i] == 0`

Return the total number of beautiful arrangements.

Example:

```text
n = 3
Output = 3

Valid arrangements:
[1, 2, 3]
[2, 1, 3]
[3, 2, 1]
```

Constraints:
- `1 < n < 20`

## 2) Intuition/Main Idea

If we generate every permutation first, complexity is `O(n!)`, and that is too slow as `n` grows.

We need two ideas:

1. **Build arrangement position by position**
   - At position `pos`, try only unused numbers.
   - Skip numbers that fail divisibility condition at that position.
   - This is backtracking with pruning.

2. **Memoize by used-set of numbers (bitmask DP)**
   - Suppose we already used a set of numbers represented by `mask`.
   - The next position is fixed by `Integer.bitCount(mask) + 1`.
   - Therefore, number of valid completions from this state depends only on `mask`.
   - So cache it: `memo[mask]`.

### Why this intuition works

- Every full beautiful permutation corresponds to one unique DFS path.
- Every partial state is fully described by used numbers (`mask`), because position is derived.
- If we revisit same `mask` through different orderings, remaining answer is identical, so memoization is valid.

### How to derive it step by step

- Start from plain backtracking.
- Observe repeated subproblems: same set of used numbers reappears.
- Replace `boolean[] used` state with bitmask integer.
- Add memo so each `mask` is solved once.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @DivisibilityCondition | `if (number % position == 0 || position % number == 0)` |
| @UseEachNumberExactlyOnce | `if ((usedMask & bit) != 0) continue;` prevents reusing number |
| @CountAllValidArrangements | recursive sum `count += countArrangements(...)` over all valid choices |
| @EfficientForNLessThan20 | `Integer[] memo = new Integer[1 << n]` stores subproblem by mask |
| @ReturnTotalCount | `arrangements(int n)` returns result from initial state mask `0` |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static int arrangements(int n) {
        // memo[mask] = number of beautiful completions from this used-mask state
        // Size is 2^n because each number from 1..n can be used or unused.
        Integer[] memo = new Integer[1 << n];
        return countArrangements(n, 0, memo);
    }

    private static int countArrangements(int n, int usedMask, Integer[] memo) {
        if (memo[usedMask] != null) {
            return memo[usedMask];
        }

        int position = Integer.bitCount(usedMask) + 1;
        if (position > n) {
            return 1;
        }

        int count = 0;

        for (int number = 1; number <= n; number++) {
            int bit = 1 << (number - 1);

            if ((usedMask & bit) != 0) {
                continue;
            }

            if (number % position == 0 || position % number == 0) {
                int nextMask = usedMask | bit;
                count += countArrangements(n, nextMask, memo);
            }
        }

        memo[usedMask] = count;
        return count;
    }
}
```

Learning Pattern:
- When choices are permutations/subsets and constraints depend on current index, try bitmask state compression.
- If next index can be derived from mask size, memo key can be only `mask`.
- Backtracking + memo converts many factorial-style explorations into manageable DP over subsets.

## 5) Complexity Analysis

Time Complexity: $O(n \cdot 2^n)$
- There are `2^n` mask states.
- For each state, we try up to `n` numbers.

Space Complexity: $O(2^n)$
- Memo table stores one value per mask.
- Recursion depth is `O(n)` (stack), dominated by memo for large `n`.

## Similar Problems

- [LeetCode 526: Beautiful Arrangement](https://leetcode.com/problems/beautiful-arrangement/) (same pattern)
- [LeetCode 698: Partition to K Equal Sum Subsets](https://leetcode.com/problems/partition-to-k-equal-sum-subsets/) (subset state + pruning)
- [LeetCode 473: Matchsticks to Square](https://leetcode.com/problems/matchsticks-to-square/) (backtracking with strong pruning)
- [LeetCode 464: Can I Win](https://leetcode.com/problems/can-i-win/) (bitmask + memo on game states)
