# Fruit Into Baskets

## Problem Description

**Problem Link:** [Fruit Into Baskets](https://leetcode.com/problems/fruit-into-baskets/)

You are visiting a farm that has a single row of fruit trees arranged from left to right. The trees are represented by an integer array `fruits` where `fruits[i]` is the **type** of fruit the `i`th tree produces.

You want to collect as much fruit as possible. However, the owner has some strict rules that you must follow:

- You only have **two baskets**, and each basket can only hold a **single type** of fruit. There is no limit on the amount of fruit each basket can hold.
- Starting from any tree of your choice, you must pick **exactly one fruit** from **every** tree (including the start tree) while moving to the right. The picked fruits must fit in one of your baskets.
- Once you reach a tree with fruit that cannot fit in your baskets, you must stop.

Given the integer array `fruits`, return *the **maximum** number of fruits you can pick*.

**Example 1:**
```
Input: fruits = [1,2,1]
Output: 3
Explanation: We can pick from all 3 trees.
```

**Example 2:**
```
Input: fruits = [0,1,2,2]
Output: 3
Explanation: We can pick from trees [1,2,2].
If we had started at the first tree, we would only pick from trees [0,1].
```

**Example 3:**
```
Input: fruits = [1,2,3,2,2]
Output: 4
Explanation: We can pick from trees [2,3,2,2].
If we had started at the first tree, we would only pick from trees [1,2].
```

**Constraints:**
- `1 <= fruits.length <= 10^5`
- `0 <= fruits[i] < fruits.length`

## Intuition/Main Idea

This is essentially finding the longest contiguous subarray with at most 2 distinct elements. It's a sliding window problem.

**Core Algorithm:**
- Use sliding window with HashMap to track fruit types and counts
- Expand window by adding fruits
- When we have more than 2 types, shrink window from left
- Track maximum window size

**Why sliding window:** We need to find the longest contiguous subarray with at most 2 distinct values. The sliding window efficiently maintains this constraint while exploring all possibilities.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| At most 2 fruit types | HashMap size check - Line 15 |
| Maximum fruits picked | Max window tracking - Lines 5, 19 |
| Expand window | Right pointer - Line 9 |
| Shrink window | Left pointer - Line 16 |
| Track fruit counts | HashMap - Lines 6, 11, 17 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int totalFruit(int[] fruits) {
        int n = fruits.length;
        int maxFruits = 0;
        
        // HashMap to track fruit types and their counts in current window
        Map<Integer, Integer> basket = new HashMap<>();
        
        // Sliding window: left pointer
        int left = 0;
        
        // Expand window by moving right pointer
        for (int right = 0; right < n; right++) {
            // Add current fruit to basket
            basket.put(fruits[right], basket.getOrDefault(fruits[right], 0) + 1);
            
            // If we have more than 2 types of fruits, shrink window from left
            // We need to remove fruits until we have at most 2 types
            while (basket.size() > 2) {
                // Remove leftmost fruit
                basket.put(fruits[left], basket.get(fruits[left]) - 1);
                
                // If count becomes 0, remove the type from basket
                if (basket.get(fruits[left]) == 0) {
                    basket.remove(fruits[left]);
                }
                
                // Move left pointer forward
                left++;
            }
            
            // Update maximum fruits picked
            // Current window size is (right - left + 1)
            maxFruits = Math.max(maxFruits, right - left + 1);
        }
        
        return maxFruits;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the array. Each element is visited at most twice.

**Space Complexity:** $O(1)$. The HashMap stores at most 3 entries (when we have 2 types and are removing one).

## Similar Problems

- [Longest Substring with At Most K Distinct Characters](https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/) - Generalization with k distinct
- [Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/) - Similar sliding window pattern
- [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/) - Sliding window variant

