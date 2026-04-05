# 3880. Minimum Absolute Difference Between Two Values

**Link:** [https://leetcode.com/problems/minimum-absolute-difference-between-two-values/](https://leetcode.com/problems/minimum-absolute-difference-between-two-values/)

**Difficulty:** Easy

---

## Problem Description

You are given an integer array `nums` containing only `0`, `1`, and `2`.

A pair of indices `(i, j)` is valid if:
- `nums[i] == 1`
- `nums[j] == 2`

Return the minimum absolute difference `abs(i - j)` among all valid pairs.

If no valid pair exists, return `-1`.

### Example 1

```text
Input: nums = [1,0,0,2,0,1]
Output: 2
```

Explanation:
- Valid pairs are `(0,3)` and `(5,3)`.
- Distances are `3` and `2`.
- Minimum is `2`.

### Example 2

```text
Input: nums = [1,0,1,0]
Output: -1
```

Explanation:
- There is no `2`, so no valid pair exists.

### Constraints

- `1 <= nums.length <= 100`
- `0 <= nums[i] <= 2`

---

## Intuition/Main Idea

### Core thought

For every `1` we want the nearest `2`, and for every `2` we want the nearest `1`.  
Brute force checks all pairs in `O(n^2)`, but we can do better in one pass.

### Build intuition step by step

1. While scanning left to right, keep the latest index where we saw each value.
2. If current value is `1`, the closest seen `2` on the left is exactly the last index of `2`.
3. If current value is `2`, the closest seen `1` on the left is exactly the last index of `1`.
4. Update answer with this new candidate distance.

Why is this enough?
- Any pair `(i, j)` gets evaluated when the later index is processed.
- At that moment, the earlier partner can be retrieved from the "last seen" array.
- So every meaningful candidate is considered exactly when needed.

### Data structure choice

Use an integer array `lastSeenIndex` of size 3:
- `lastSeenIndex[0]` -> last position of `0` (not needed for answer but harmless)
- `lastSeenIndex[1]` -> last position of `1`
- `lastSeenIndex[2]` -> last position of `2`

Initialize with a very negative value so accidental early subtraction does not create a valid minimum.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| `@Find minimum abs(i-j)` for `1` and `2` pairs | `minimumDistance = Math.min(minimumDistance, index - lastSeenIndex[3 - value])` |
| `@Single pass` style optimization | `for (int index = 0; index < arrayLength; index++)` |
| `@No valid pair -> -1` | `return minimumDistance > arrayLength ? -1 : minimumDistance;` |
| Track latest `1` / `2` positions | `lastSeenIndex[value] = index;` |

---

## Final Java Code & Learning Pattern (Full Content)

```java
import java.util.Arrays;

class Solution {
    public int minAbsoluteDifference(int[] nums) {
        int arrayLength = nums.length;
        int minimumDistance = arrayLength + 1;

        // lastSeenIndex[x] stores the latest index where value x appeared.
        // Initialize with a large negative sentinel so early differences are ignored naturally.
        int[] lastSeenIndex = new int[3];
        Arrays.fill(lastSeenIndex, -(arrayLength + 1));

        for (int index = 0; index < arrayLength; index++) {
            int value = nums[index];

            // Only 1 and 2 can form valid pairs.
            if (value != 0) {
                // If value is 1, partner is 2 (3 - 1 = 2).
                // If value is 2, partner is 1 (3 - 2 = 1).
                int partnerValue = 3 - value;
                minimumDistance = Math.min(minimumDistance, index - lastSeenIndex[partnerValue]);
            }

            // Update latest index for current value.
            lastSeenIndex[value] = index;
        }

        return minimumDistance > arrayLength ? -1 : minimumDistance;
    }
}
```

### Learning Pattern

This is a **last-seen index + one-pass scan** pattern:
- Keep the latest position of needed counterpart values.
- Compute candidate answer in constant time per index.
- Ideal for nearest/shortest distance problems over small categorical domains.

---

## Complexity Analysis

- **Time Complexity:** `O(n)` because each index is processed once.
- **Space Complexity:** `O(1)` because we use a fixed-size array (`size = 3`).

---

## Similar Problems

- [LC 821 - Shortest Distance to a Character](https://leetcode.com/problems/shortest-distance-to-a-character/)  
  Uses nearest-position reasoning for minimum distance.
- [LC 1182 - Shortest Distance to Target Color](https://leetcode.com/problems/shortest-distance-to-target-color/)  
  Distance-to-category lookup with preprocessed nearest indices.
- [LC 2903 - Find Indices With Index and Value Difference I](https://leetcode.com/problems/find-indices-with-index-and-value-difference-i/)  
  Index-difference constraints and distance-based checking pattern.
