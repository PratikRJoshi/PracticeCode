# Final Element After Subarray Deletions

## Problem Description

**Problem Link:** [Final Element After Subarray Deletions](https://leetcode.com/problems/final-element-after-subarray-deletions/)

You are given an integer array `nums`.

Two players, Alice and Bob, play a game in turns, with Alice playing first.

In each turn, the current player chooses any `nums[l..r]` such that `r - l + 1 < m`, where `m` is the current length of the array.

The selected subarray is removed, and the remaining elements are concatenated to form the new array.

The game continues until only one element remains.

- Alice aims to **maximize** the final element.
- Bob aims to **minimize** the final element.

Assuming both play optimally, return the value of the final remaining element.

**Example 1:**
```
Input: nums = [1,5,2]
Output: 2
Explanation:
One valid optimal strategy:
Alice removes [1], array becomes [5, 2].
Bob removes [5], array becomes [2]. Thus, the answer is 2.
```

**Example 2:**
```
Input: nums = [3,7]
Output: 7
Explanation:
Alice removes [3], leaving the array [7]. Since Bob cannot play a turn now, the answer is 7.
```

**Constraints:**
- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^5`

## Intuition/Main Idea

The key is the move constraint:

- Current array length is `m`.
- In one move you can remove any subarray with length `< m`.

That means you are allowed to remove a subarray of length **exactly `m - 1`**.

If you remove `m - 1` contiguous elements from the current array, exactly **one element remains**, and the game ends immediately.

From an array of length `m`, removing `m - 1` contiguous elements can only leave:

- The **first element** (remove the suffix `nums[1..m-1]`)
- The **last element** (remove the prefix `nums[0..m-2]`)

So Alice (who plays first) can end the game on her first move, choosing whichever end value is better for her.

- Alice wants to maximize the final element.
- Therefore the optimal result is simply:

- If `nums.length == 1`, answer is `nums[0]`.
- Otherwise, answer is `max(nums[0], nums[n - 1])`.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Remove any subarray with length `< m` | This allows deleting `m - 1` elements in one move (intuition; implemented via picking ends) (lines 10-21) |
| Alice maximizes, Bob minimizes (optimal play) | Alice ends game immediately by choosing best end element (lines 14-21) |
| Return final remaining element | Return `nums[0]` if size 1 else `max(nums[0], nums[n-1])` (lines 6-21) |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int finalElementAfterSubarrayDeletions(int[] nums) {
        int n = nums.length;

        // If there's only one element, the game is already over.
        if (n == 1) {
            return nums[0];
        }

        // Because a player can remove a subarray of length n - 1 (< n),
        // Alice can end the game on her first move.
        // She can only leave either the first or the last element.
        // Since she wants to maximize the final remaining element:
        return Math.max(nums[0], nums[n - 1]);
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(1)$

**Space Complexity:** $O(1)$

## Similar Problems

- No close classic equivalents needed here; the main pattern is spotting a game where the first player can finish immediately due to a strong allowed move.
