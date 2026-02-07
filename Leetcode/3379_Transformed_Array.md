# Transformed Array

## Problem Description

**Problem Link:** [Transformed Array](https://leetcode.com/problems/transformed-array/)

You are given an integer array `nums` that represents a circular array. Your task is to create a new array `result` of the same size, following these rules:

For each index `i` (where `0 <= i < nums.length`), perform the following independent actions:

- If `nums[i] > 0`:
  - Start at index `i` and move `nums[i]` steps to the right in the circular array.
  - Set `result[i]` to the value of the index where you land.
- If `nums[i] < 0`:
  - Start at index `i` and move `abs(nums[i])` steps to the left in the circular array.
  - Set `result[i]` to the value of the index where you land.
- If `nums[i] == 0`:
  - Set `result[i] = nums[i]`.

Return the new array `result`.

Since `nums` is circular:

- Moving past the last element wraps to the beginning.
- Moving before the first element wraps to the end.

**Example 1:**
```
Input: nums = [3,-2,1,1]
Output: [1,1,1,3]
Explanation:
- i=0, nums[0]=3: move 3 right from 0 -> land at 3 -> result[0]=nums[3]=1
- i=1, nums[1]=-2: move 2 left from 1 -> land at 3 -> result[1]=nums[3]=1
- i=2, nums[2]=1: move 1 right from 2 -> land at 3 -> result[2]=nums[3]=1
- i=3, nums[3]=1: move 1 right from 3 -> land at 0 -> result[3]=nums[0]=3
```

**Example 2:**
```
Input: nums = [-1,4,-1]
Output: [-1,-1,4]
Explanation:
- i=0, nums[0]=-1: move 1 left from 0 -> land at 2 -> result[0]=nums[2]=-1
- i=1, nums[1]=4: move 4 right from 1 -> (1+4)%3=2 -> result[1]=nums[2]=-1
- i=2, nums[2]=-1: move 1 left from 2 -> land at 1 -> result[2]=nums[1]=4
```

## Intuition/Main Idea

Each index `i` is processed independently, so we can compute the destination index directly.

The only tricky part is circular wrap-around.

### Key idea: modulo to wrap indices

For length `n`:

- Moving right by `k` from `i` lands at:
  - `(i + k) % n`

- Moving left by `k` from `i` lands at:
  - `(i - k) % n` might become negative in Java, so normalize it.

A clean way to normalize any index into `[0, n-1]` is:

- `((rawIndex % n) + n) % n`

So we can unify both directions:

- `rawIndex = i + nums[i]`
- `dest = ((rawIndex % n) + n) % n`
- `result[i] = nums[dest]`

Special case:

- If `nums[i] == 0`, then `rawIndex == i`, and the formula still works.
- But the problem explicitly says `result[i] = nums[i]`, so we can keep it explicit for clarity.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Circular movement right when `nums[i] > 0` | `rawIndex = i + nums[i]` and modulo normalization (lines 12-23) |
| Circular movement left when `nums[i] < 0` | Same formula handles negative via normalization (lines 12-23) |
| `nums[i] == 0` -> `result[i] = 0` | Explicit branch (lines 14-16) |
| Return new array `result` | Allocate and fill result (lines 6-25) |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int[] transformedArray(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            // Requirement: if nums[i] == 0, result[i] is nums[i].
            if (nums[i] == 0) {
                result[i] = 0;
                continue;
            }

            // Core idea: in a circular array, moving k steps right/left is just index arithmetic.
            // rawIndex can be > n-1 or < 0.
            int rawIndex = i + nums[i];

            // Java % can be negative, so normalize into [0, n-1].
            int destIndex = ((rawIndex % n) + n) % n;

            // Set result to the value at the landing index.
            result[i] = nums[destIndex];
        }

        return result;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$

**Space Complexity:** $O(n)$ for the `result` array

## Similar Problems

- [Circular Array Loop](https://leetcode.com/problems/circular-array-loop/) - Circular movement and modulo index handling
- [Rotate Array](https://leetcode.com/problems/rotate-array/) - Modulo-based index mapping in arrays
