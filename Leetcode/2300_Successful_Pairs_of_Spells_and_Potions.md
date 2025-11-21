# Successful Pairs of Spells and Potions

## Problem Description

**Problem Link:** [Successful Pairs of Spells and Potions](https://leetcode.com/problems/successful-pairs-of-spells-and-potions/)

You are given two positive integer arrays `spells` and `potions`, of length `n` and `m` respectively, where `spells[i]` represents the strength of the `i`th spell and `potions[j]` represents the strength of the `j`th potion.

You are also given an integer `success`. A spell and potion pair is **successful** if the **product** of their strengths is **at least** `success`.

Return *an integer array* `pairs` *of length* `n` *where* `pairs[i]` *is the number of potions that will form a successful pair with the* `i`th *spell*.

**Example 1:**
```
Input: spells = [5,1,3], potions = [1,2,3,4,5], success = 7
Output: [4,0,3]
Explanation:
- 0th spell: 5 * [1,2,3,4,5] = [5,10,15,20,25]. 4 pairs are successful.
- 1st spell: 1 * [1,2,3,4,5] = [1,2,3,4,5]. 0 pairs are successful.
- 3rd spell: 3 * [1,2,3,4,5] = [3,6,9,12,15]. 3 pairs are successful.
```

**Example 2:**
```
Input: spells = [3,1,2], potions = [8,5,8], success = 16
Output: [2,0,2]
```

**Constraints:**
- `n == spells.length`
- `m == potions.length`
- `1 <= n, m <= 10^5`
- `1 <= spells[i], potions[i] <= 10^5`
- `1 <= success <= 10^10`

## Intuition/Main Idea

For each spell, we need to count how many potions satisfy `spell * potion >= success`, which means `potion >= success / spell`.

**Core Algorithm:**
- Sort potions array
- For each spell, binary search for the first potion where `potion >= ceil(success / spell)`
- Count = number of potions from that position to end

**Why binary search:** After sorting potions, we can use binary search to find the threshold position in $O(\log m)$ time instead of linear search $O(m)$.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Sort potions | Arrays.sort - Line 6 |
| Binary search threshold | Binary search - Lines 10-20 |
| Count successful pairs | Calculate count - Line 21 |
| Handle integer division | Ceiling calculation - Line 12 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        // Sort potions to enable binary search
        Arrays.sort(potions);
        
        int n = spells.length;
        int[] result = new int[n];
        
        // For each spell, find how many potions form successful pairs
        for (int i = 0; i < n; i++) {
            // We need potion >= ceil(success / spell)
            // To avoid floating point, we check: spell * potion >= success
            // Which is equivalent to: potion >= success / spell
            // We use (success + spell - 1) / spell to get ceiling
            
            long minPotion = (success + spells[i] - 1) / spells[i];
            
            // Binary search for first potion >= minPotion
            int left = 0;
            int right = potions.length;
            
            // Use < instead of <= to find insertion position
            while (left < right) {
                int mid = left + (right - left) / 2;
                
                // If potions[mid] >= minPotion, it's valid, search left
                // We want first position where potion >= minPotion
                if (potions[mid] >= minPotion) {
                    right = mid;
                }
                // If potions[mid] < minPotion, search right
                else {
                    left = mid + 1;
                }
            }
            
            // left points to first potion >= minPotion
            // Count = number of potions from left to end
            result[i] = potions.length - left;
        }
        
        return result;
    }
}
```

## Binary Search Decision Guide

**How to decide whether to use < or <= in the main loop condition:**
- Use `<` to find insertion position (first position where condition is true)
- When `left == right`, we've found the boundary

**How to decide if pointers should be set to mid + 1 or mid - 1 or mid:**
- When `potions[mid] >= minPotion`, mid could be answer, but we want first occurrence, so `right = mid`
- When `potions[mid] < minPotion`, mid is not answer, so `left = mid + 1`

**How to decide what would be the return value:**
- `left` points to first potion >= minPotion
- Count = `potions.length - left` (all potions from left to end)

## Complexity Analysis

**Time Complexity:** $O(m \log m + n \log m)$ where $m$ is length of potions and $n$ is length of spells. Sorting takes $O(m \log m)$, and each binary search takes $O(\log m)$.

**Space Complexity:** $O(1)$ excluding output array. Sorting may use $O(\log m)$ space.

## Similar Problems

- [Search Insert Position](https://leetcode.com/problems/search-insert-position/) - Similar binary search pattern
- [Two Sum II](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) - Two pointers on sorted array
- [Koko Eating Bananas](https://leetcode.com/problems/koko-eating-bananas/) - Binary search on answer space

