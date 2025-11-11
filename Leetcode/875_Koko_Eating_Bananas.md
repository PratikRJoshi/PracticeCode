# Koko Eating Bananas

## Problem Description

**Problem Link:** [Koko Eating Bananas](https://leetcode.com/problems/koko-eating-bananas/)

Koko loves to eat bananas. There are `n` piles of bananas, the `i`th pile has `piles[i]` bananas. The guards have gone and will come back in `h` hours.

Koko can decide her bananas-per-hour eating speed of `k`. Each hour, she chooses some pile of bananas and eats `k` bananas from that pile. If the pile has less than `k` bananas, she eats all of them instead and will not eat any more bananas during this hour.

Koko wants to finish eating all the bananas before the guards come back.

Return *the minimum integer `k` such that she can eat all the bananas within `h` hours*.

**Example 1:**
```
Input: piles = [3,6,7,11], h = 8
Output: 4
Explanation: With k=4, Koko can finish in 8 hours:
- Hour 1: Eat 4 from pile 0 (3 left)
- Hour 2: Eat 3 from pile 0 (done), then 1 from pile 1 (5 left)
- Hour 3: Eat 4 from pile 1 (1 left)
- Hour 4: Eat 1 from pile 1 (done), then 3 from pile 2 (4 left)
- Hour 5: Eat 4 from pile 2 (done), then 0 from pile 3 (11 left)
- Hour 6: Eat 4 from pile 3 (7 left)
- Hour 7: Eat 4 from pile 3 (3 left)
- Hour 8: Eat 3 from pile 3 (done)
```

**Example 2:**
```
Input: piles = [30,11,23,4,20], h = 5
Output: 30
```

**Example 3:**
```
Input: piles = [30,11,23,4,20], h = 6
Output: 23
```

**Constraints:**
- `1 <= piles.length <= 10^4`
- `piles.length <= h <= 10^9`
- `1 <= piles[i] <= 10^9`

## Intuition/Main Idea

This is a **binary search on answer space** problem. Instead of searching through the array, we're searching for the minimum valid eating speed `k`.

**Key Insights:**
1. If Koko can finish with speed `k`, she can finish with any speed greater than `k`.
2. If Koko cannot finish with speed `k`, she cannot finish with any speed less than `k`.
3. The answer space is `[1, max(piles)]` - minimum speed is 1, maximum needed is the largest pile.

**Core Algorithm:**
1. Binary search on the possible eating speeds from 1 to max(piles).
2. For each speed `k`, calculate the total hours needed.
3. If hours <= h, try a smaller speed (search left).
4. If hours > h, try a larger speed (search right).
5. Return the minimum valid speed.

**Why binary search works:** The problem has a monotonic property - if speed `k` works, all speeds > `k` work. If speed `k` doesn't work, all speeds < `k` don't work. This makes binary search applicable.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Find maximum pile size | Max calculation - Line 6 |
| Binary search boundaries | `left` and `right` pointers - Lines 8-9 |
| Check if speed is valid | `canFinish` method - Lines 20-29 |
| Calculate hours for given speed | Hours calculation - Lines 23-27 |
| Narrow search space | Binary search logic - Lines 12-18 |
| Return minimum valid speed | Return statement - Line 19 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        // Find the maximum pile size (upper bound for eating speed)
        int maxPile = 0;
        for (int pile : piles) {
            maxPile = Math.max(maxPile, pile);
        }
        
        // Binary search on eating speed: [1, maxPile]
        int left = 1;
        int right = maxPile;
        int result = maxPile;  // Worst case: need to eat max pile per hour
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Check if we can finish with speed = mid
            if (canFinish(piles, h, mid)) {
                // If yes, try smaller speed (search left)
                result = mid;
                right = mid - 1;
            } else {
                // If no, need larger speed (search right)
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    // Check if Koko can finish all bananas within h hours with speed k
    private boolean canFinish(int[] piles, int h, int k) {
        int totalHours = 0;
        
        for (int pile : piles) {
            // Calculate hours needed for this pile
            // If pile has 7 bananas and k=3, need ceil(7/3) = 3 hours
            // Using (pile + k - 1) / k to get ceiling division
            totalHours += (pile + k - 1) / k;
            
            // Early exit if already exceeded h hours
            if (totalHours > h) {
                return false;
            }
        }
        
        return totalHours <= h;
    }
}
```

**Explanation of Key Code Sections:**

1. **Find Maximum Pile (Lines 5-8):** We find the maximum pile size, which serves as the upper bound for our binary search. If Koko eats at this speed, she can definitely finish (one hour per pile maximum).

2. **Binary Search Setup (Lines 10-13):** We initialize:
   - `left = 1`: Minimum possible speed
   - `right = maxPile`: Maximum needed speed
   - `result = maxPile`: Initialize to worst case

3. **Binary Search Loop (Lines 15-24):** 
   - **Mid Calculation (Line 16):** Calculate the middle speed to test.
   - **Check Feasibility (Line 19):** Check if we can finish with speed `mid`.
   - **Search Left (Lines 20-22):** If `mid` works, it's a candidate. Try smaller speeds to find the minimum.
   - **Search Right (Line 24):** If `mid` doesn't work, we need a larger speed.

4. **Feasibility Check (Lines 27-38):** 
   - **Hours Calculation (Line 31):** For each pile, calculate hours needed using ceiling division: `(pile + k - 1) / k`. This is equivalent to `Math.ceil(pile / k)` but avoids floating point.
   - **Early Exit (Lines 33-35):** If we've already exceeded `h` hours, we can return false immediately.
   - **Return (Line 37):** Return whether total hours is within the limit.

**Why ceiling division works:**
- If `pile = 7` and `k = 3`: `(7 + 3 - 1) / 3 = 9 / 3 = 3` hours ✓
- If `pile = 6` and `k = 3`: `(6 + 3 - 1) / 3 = 8 / 3 = 2` hours ✓
- Formula: `ceil(a/b) = (a + b - 1) / b` for positive integers

## Binary Search Decision Guide

### How to decide whether to use `<` or `<=` in the main loop condition:

**Use `left <= right` when:**
- You want to check every valid value in the search space
- You're looking for the minimum/maximum valid value
- This is the standard pattern for "find minimum valid" problems

**For this problem:** We use `left <= right` to ensure we check all possible speeds and find the minimum valid one.

### How to decide if the pointers should be set to `mid + 1` or `mid - 1` or `mid`:

**Standard pattern (this problem):**
- When `canFinish(mid)` is true: `right = mid - 1` (try smaller, but keep `mid` as candidate)
- When `canFinish(mid)` is false: `left = mid + 1` (need larger)
- We store `result = mid` when we find a valid speed

**Why `mid + 1` and `mid - 1`:**
- We've already tested `mid`, so we can exclude it from the next search
- This ensures the search space shrinks at each step
- Prevents infinite loops

### How to decide what would be the return value:

**For "find minimum valid" problems:**
- Keep track of the best valid value found so far (`result = mid` when valid)
- Return `result` after the loop
- This ensures we return the minimum valid value even if the loop ends with `left > right`

**Alternative:** Return `left` if the problem guarantees a solution exists, as `left` will point to the minimum valid value.

## Complexity Analysis

- **Time Complexity:** $O(n \log m)$ where $n$ is the number of piles and $m$ is the maximum pile size. We do $O(\log m)$ binary search iterations, each taking $O(n)$ time to check feasibility.

- **Space Complexity:** $O(1)$ as we only use a constant amount of extra space.

## Similar Problems

Problems that can be solved using similar binary search on answer space patterns:

1. **875. Koko Eating Bananas** (this problem) - Binary search on eating speed
2. **1011. Capacity To Ship Packages Within D Days** - Binary search on ship capacity
3. **410. Split Array Largest Sum** - Binary search on maximum sum
4. **1482. Minimum Number of Days to Make m Bouquets** - Binary search on days
5. **1283. Find the Smallest Divisor Given a Threshold** - Binary search on divisor
6. **774. Minimize Max Distance to Gas Station** - Binary search on distance
7. **719. Find K-th Smallest Pair Distance** - Binary search on distance
8. **378. Kth Smallest Element in a Sorted Matrix** - Binary search on value
9. **668. Kth Smallest Number in Multiplication Table** - Binary search on value
10. **1201. Ugly Number III** - Binary search on count

