# 907. Sum of Subarray Minimums

[LeetCode Link](https://leetcode.com/problems/sum-of-subarray-minimums/)

## Problem Description
Given an array of integers `arr`, return the sum of `min(subarray)` over all contiguous subarrays of `arr`.

Since the answer may be large, return it modulo `1_000_000_007`.

### Examples

#### Example 1
- Input: `arr = [3,1,2,4]`
- Output: `17`

#### Example 2
- Input: `arr = [11,81,94,43,3]`
- Output: `444`

---

## Intuition/Main Idea
Instead of enumerating all subarrays, count each element’s **contribution**.

If `arr[i]` is the minimum for `k` different subarrays, then it contributes `arr[i] * k` to the final sum.

So the job is to compute how many subarrays have `arr[i]` as their minimum.

We do this by finding, for each index `i`:

- `left[i]`: how many choices of the left boundary produce a subarray where `arr[i]` is the minimum
- `right[i]`: how many choices of the right boundary produce a subarray where `arr[i]` is the minimum

Then `arr[i]` is the minimum for exactly `left[i] * right[i]` subarrays.

To avoid double counting when there are equal values, we use asymmetric comparisons:

- left pass uses `>` (previous strictly smaller-or-equal stops us)
- right pass uses `>=` (next strictly smaller stops us)

Both spans can be computed in `O(n)` using a monotonic increasing stack of indices.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Compute each element’s contribution as a minimum | `contrib = arr[i] * left[i] * right[i]` |
| Avoid double counting equal values | left uses `>` and right uses `>=` |
| Need modulo arithmetic | Keep sum in `long` and apply `% MOD` |
| Must be efficient for large `n` | Monotonic stack makes both passes linear |

---

## Final Java Code & Learning Pattern (Full Content)
```java
import java.util.*;

class Solution {
    public int sumSubarrayMins(int[] arr) {
        final int MOD = 1_000_000_007;
        int n = arr.length;

        int[] left = new int[n];
        int[] right = new int[n];

        Deque<Integer> stack = new ArrayDeque<>();

        // PASS 1 (Left span): compute left[i]
        // left[i] = how many ways we can extend to the left (choose a left boundary)
        // so that arr[i] is still the minimum.
        //
        // Keep a stack of indices with increasing values.
        // Pop bigger values (arr[i] < arr[top]) because they can't be the "previous smaller/equal".
        // After popping, the stack top (if any) is the closest index on the left with value <= arr[i].
        //
        // Use '<' (not '<=') so equal values are handled on the RIGHT pass (prevents double counting).
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[i] < arr[stack.peek()]) {
                stack.pop();
            }

            // Count of valid left boundaries:
            // - none on left => i + 1
            // - otherwise => distance to previous <= element
            left[i] = stack.isEmpty() ? (i + 1) : (i - stack.peek());
            stack.push(i);
        }

        stack.clear();

        // PASS 2 (Right span): compute right[i]
        // right[i] = how many ways we can extend to the right (choose a right boundary)
        // so that arr[i] is still the minimum.
        //
        // Scan from right to left.
        // Pop values where arr[i] <= arr[top] so the remaining top (if any) is the closest STRICTLY smaller value.
        //
        // Use '<=' here to pair with the left pass ('<') so duplicates are counted exactly once.
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[i] <= arr[stack.peek()]) {
                stack.pop();
            }

            // Count of valid right boundaries:
            // - none smaller on right => n - i
            // - otherwise => distance to next strictly smaller element
            right[i] = stack.isEmpty() ? (n - i) : (stack.peek() - i);
            stack.push(i);
        }

        long answer = 0;
        for (int i = 0; i < n; i++) {
            long contribution = ((long) arr[i] * left[i] % MOD) * right[i] % MOD;
            answer = (answer + contribution) % MOD;
        }

        return (int) answer;
    }
}
```

### Learning Pattern
- Convert “sum over all subarrays” into “sum over all indices” using contribution counting.
- Use a monotonic increasing stack to find span counts.
- Break ties consistently (`>` on one side and `>=` on the other) so equal values are counted exactly once.

---

## Complexity Analysis
- Time Complexity: $O(n)$
  - each index is pushed/popped at most once per pass
- Space Complexity: $O(n)$
  - `left`, `right`, and the stack

---

## Similar Problems
- [84. Largest Rectangle in Histogram](https://leetcode.com/problems/largest-rectangle-in-histogram/) (monotonic stack spans)
- [739. Daily Temperatures](https://leetcode.com/problems/daily-temperatures/) (monotonic stack)
- [2104. Sum of Subarray Ranges](https://leetcode.com/problems/sum-of-subarray-ranges/) (max/min contributions with stacks)