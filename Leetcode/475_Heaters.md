# 475. Heaters

[LeetCode Link](https://leetcode.com/problems/heaters/)

## Problem Description
You are given positions of houses and heaters on a number line.

- `houses[i]` is the position of the `i`-th house.
- `heaters[j]` is the position of the `j`-th heater.

A heater at position `x` with radius `r` warms all houses within distance `r`, i.e. for a house at position `h`, it is covered if `|h - x| <= r`.

Return the minimum radius `r` such that **every house is covered by at least one heater**.

### Examples

#### Example 1
- Input: `houses = [1,2,3]`, `heaters = [2]`
- Output: `1`

#### Example 2
- Input: `houses = [1,2,3,4]`, `heaters = [1,4]`
- Output: `1`

---

## Intuition/Main Idea
Think of the heater radius as a single global setting `r` applied to **every** heater. A house is covered if it is within `r` of **some** heater.

### Step 1: Turn the statement into a “max of local needs”
For each house at position `housePos`, define:

`need(housePos) = distance from housePos to its nearest heater`

If we choose a radius `r`, then this house is covered iff:

`need(housePos) <= r`

To cover **all** houses with the **same** `r`, we must have:

`r >= need(housePos)` for every house

So the smallest valid radius is:

`answer = max over houses ( need(housePos) )`

That translates directly to code as:

- compute `nearestDist` for each `housePos`
- update `minRadius = max(minRadius, nearestDist)`

### Step 2: Compute nearest heater distance fast (sorting + lower bound)
Once `heaters` is sorted, for a given `housePos`:

- `insertionIdx = lowerBound(heaters, housePos)` gives the first heater on the **right** (position `>= housePos`)
- the heater just to the **left** is at index `insertionIdx - 1`

The nearest heater must be one of these two neighbors in the sorted list, so:

`nearestDist = min(|housePos - heaters[insertionIdx]|, |housePos - heaters[insertionIdx - 1]|)`

(checking bounds when `insertionIdx` is `0` or `heaters.length`).

This is the same “closest element in a sorted array” pattern as `lowerBound` use-cases like LC 1818.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Find minimum radius covering all houses | Track `minRadius = max(minRadius, nearestDist)` |
| House covered if within radius of some heater | For each house compute `min(distToLeftHeater, distToRightHeater)` |
| Efficient search for nearest heater | Sort `heaters`, use `lowerBound` per house |

---

## Final Java Code & Learning Pattern (Full Content)
```java
import java.util.*;

class Solution {
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(heaters);

        int minRadius = 0;

        for (int housePos : houses) {
            int insertionIdx = lowerBound(heaters, housePos);

            int distToRightHeater = Integer.MAX_VALUE;
            if (insertionIdx < heaters.length) {
                distToRightHeater = Math.abs(heaters[insertionIdx] - housePos);
            }

            int distToLeftHeater = Integer.MAX_VALUE;
            if (insertionIdx - 1 >= 0) {
                distToLeftHeater = Math.abs(housePos - heaters[insertionIdx - 1]);
            }

            int nearestDist = Math.min(distToLeftHeater, distToRightHeater);
            minRadius = Math.max(minRadius, nearestDist);
        }

        return minRadius;
    }

    // First index i such that arr[i] >= target.
    // If all elements are < target, returns arr.length.
    private int lowerBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}
```

### Learning Pattern
- Convert “minimum radius to cover all” into “maximum over houses of nearest-heater distance”.
- Use the **sorted array + lower bound** pattern to find closest candidates in `O(log m)`.
- Implementation memory hook: for each `housePos`, find `insertionIdx` in heaters, compute `nearestDist`, and update `minRadius`.

---

## Complexity Analysis
- Time Complexity: $O(m \log m + n \log m)$
  - sort heaters: $O(m \log m)$
  - for each of `n` houses, binary search heaters: $O(\log m)$
- Space Complexity: $O(1)$ extra (ignoring sort implementation stack)

---

## Similar Problems
- [34. Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/) (lower bound pattern)
- [1818. Minimum Absolute Sum Difference](https://leetcode.com/problems/minimum-absolute-sum-difference/) (closest value via lower bound)
- [658. Find K Closest Elements](https://leetcode.com/problems/find-k-closest-elements/) (nearest elements in sorted array)
