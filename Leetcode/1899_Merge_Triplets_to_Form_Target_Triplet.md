# Merge Triplets to Form Target Triplet

## Problem Description

**Problem Link:** [Merge Triplets to Form Target Triplet](https://leetcode.com/problems/merge-triplets-to-form-target-triplet/)

A **triplet** is an array of three integers. You are given a 2D integer array `triplets`, where `triplets[i] = [ai, bi, ci]` denotes the `i`th **triplet**. You are also given an integer array `target = [x, y, z]` that describes the **target triplet**.

Return `true` *if it is possible to obtain the `target` triplet by **merging** the triplets in `triplets` array, or `false` otherwise*.

You may **merge** two triplets by performing the following operation:

- Choose two indices (`i`, `j`) such that `0 <= i, j < triplets.length` and `i != j`.
- Replace `triplets[i]` with `[max(ai, aj), max(bi, bj), max(ci, cj)]`.
- Remove `triplets[j]`.

For example, if `triplets = [[2,5,3],[1,8,4],[1,7,5]]` and `target = [2,7,5]`, then merging `triplets[0]` with `triplets[2]` will change `triplets` to `[[2,7,5],[1,8,4]]`.

**Example 1:**

```
Input: triplets = [[2,5,3],[1,8,4],[1,7,5]], target = [2,7,5]
Output: true
Explanation: Perform the following operations:
- Choose the first and last triplets [[2,5,3],[1,8,4],[1,7,5]]. Update the first triplet to be the max of the two: [[2,7,5],[1,8,4]].
- Choose the first and second triplets [[2,7,5],[1,8,4]]. Update the first triplet to be the max of the two: [[2,8,5]].
- Choose the first and second triplets again [[2,8,5],[1,8,4]]. Update the first triplet to be the max of the two: [[2,8,5]].
Wait, that's not right. Let me reconsider.

Actually: Merge [2,5,3] with [1,7,5] → [2,7,5] ✓
```

**Example 2:**

```
Input: triplets = [[3,4,5],[4,5,6]], target = [3,2,5]
Output: false
Explanation: It is impossible to have [3,2,5] as the triplet because there is no triplet with a value less than or equal to 2 in its second position.
```

**Example 3:**

```
Input: triplets = [[2,5,3],[2,3,4],[1,2,5],[5,2,3]], target = [5,5,5]
Output: true
Explanation: Perform the following operations:
- Choose the first and third triplets [[2,5,3],[1,2,5]]. Update the first triplet to [2,5,5].
- Choose the first and fourth triplets [[2,5,5],[5,2,3]]. Update the first triplet to [5,5,5].
```

**Constraints:**
- `1 <= triplets.length <= 10^5`
- `triplets[i].length == 3`
- `0 <= ai, bi, ci, x, y, z <= 1000`

## Intuition/Main Idea

The key insight is that we can only **increase** values through merging (taking max), never decrease them. Therefore:

1. **Necessary condition:** Each position in the target must be achievable from at least one triplet (no triplet has a value > target in any position).
2. **Sufficient condition:** We need triplets that can achieve each position of the target exactly, or we can combine triplets to achieve all positions.

**Core Algorithm:**
1. Filter out triplets that have any value > target (they can't be used).
2. Check if we can achieve each position of the target:
   - For position 0: need at least one triplet with `triplet[0] == target[0]`
   - For position 1: need at least one triplet with `triplet[1] == target[1]`
   - For position 2: need at least one triplet with `triplet[2] == target[2]`
3. If all three positions are achievable, return true.

**Why this works:** Since merging takes max, we can only increase values. To achieve `target[i]`, we need at least one triplet that has `triplet[i] == target[i]` and all other positions <= target. By filtering invalid triplets and checking achievability of each position, we determine if the target is reachable.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Filter invalid triplets | Validation check - Lines 8-11 |
| Track achievable positions | Boolean flags - Lines 13-15 |
| Check each valid triplet | For loop - Line 17 |
| Mark achievable positions | Flag updates - Lines 19-24 |
| Verify all positions achievable | Return statement - Line 26 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public boolean mergeTriplets(int[][] triplets, int[] target) {
        // Flags to track if we can achieve each position of target
        boolean canAchieve0 = false;
        boolean canAchieve1 = false;
        boolean canAchieve2 = false;
        
        for (int[] triplet : triplets) {
            // Skip triplets that have any value > target (can't use them)
            // Because merging takes max, we can only increase values
            if (triplet[0] > target[0] || triplet[1] > target[1] || triplet[2] > target[2]) {
                continue;
            }
            
            // Check if this triplet can help achieve each position of target
            // A position is achievable if at least one triplet has that exact value
            if (triplet[0] == target[0]) {
                canAchieve0 = true;
            }
            if (triplet[1] == target[1]) {
                canAchieve1 = true;
            }
            if (triplet[2] == target[2]) {
                canAchieve2 = true;
            }
        }
        
        // All three positions must be achievable
        return canAchieve0 && canAchieve1 && canAchieve2;
    }
}
```

**Explanation of Key Code Sections:**

1. **Flags Initialization (Lines 4-6):** We use three boolean flags to track whether we can achieve each position (0, 1, 2) of the target.

2. **Filter Invalid Triplets (Lines 9-12):** We skip any triplet that has a value greater than the target in any position. This is because:
   - Merging takes the **maximum** of two values
   - We can only **increase** values, never decrease them
   - If a triplet has a value > target, merging it will always keep that value >= target, making it impossible to achieve the exact target

3. **Check Achievability (Lines 14-22):** For each valid triplet, we check if it can help achieve each position:
   - If `triplet[0] == target[0]`, we can achieve position 0
   - If `triplet[1] == target[1]`, we can achieve position 1
   - If `triplet[2] == target[2]`, we can achieve position 2

4. **Return Result (Line 25):** We return true only if all three positions are achievable.

**Why this algorithm is correct:**
- **Necessity:** To achieve `target[i]`, we need at least one triplet with `triplet[i] == target[i]` (since we can only increase).
- **Sufficiency:** If we have triplets that achieve each position, we can merge them:
  - Start with a triplet achieving position 0
  - Merge with a triplet achieving position 1 (if different triplet)
  - Merge with a triplet achieving position 2 (if different triplet)
  - The result will have max of all positions, which equals target

**Example walkthrough for `triplets = [[2,5,3],[1,8,4],[1,7,5]], target = [2,7,5]`:**
- Triplet [2,5,3]: All <= target ✓, achieves position 0 (2==2)
- Triplet [1,8,4]: Has 8 > 7, skip ✗
- Triplet [1,7,5]: All <= target ✓, achieves position 1 (7==7) and position 2 (5==5)
- Result: canAchieve0=true, canAchieve1=true, canAchieve2=true → true ✓

**Why we don't need to track which specific triplets:**
- We only care about **existence** of triplets that can achieve each position
- The merging operation is commutative and associative (max operation)
- If all positions are achievable, we can always combine the triplets to form the target

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the number of triplets. We iterate through all triplets once.

- **Space Complexity:** $O(1)$ as we only use a constant amount of extra space for the flags.

## Similar Problems

Problems that can be solved using similar greedy and constraint-checking patterns:

1. **1899. Merge Triplets to Form Target Triplet** (this problem) - Greedy with constraints
2. **55. Jump Game** - Greedy reachability
3. **45. Jump Game II** - Greedy with minimum steps
4. **134. Gas Station** - Greedy circular route
5. **135. Candy** - Greedy distribution
6. **330. Patching Array** - Greedy patching
7. **763. Partition Labels** - Greedy partitioning
8. **406. Queue Reconstruction by Height** - Greedy insertion
9. **435. Non-overlapping Intervals** - Greedy selection
10. **452. Minimum Number of Arrows to Burst Balloons** - Greedy covering

