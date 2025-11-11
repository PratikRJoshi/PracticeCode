# Single Number

## Problem Description

**Problem Link:** [Single Number](https://leetcode.com/problems/single-number/)

Given a **non-empty** array of integers `nums`, every element appears **twice** except for one. Find that single one.

You must implement a solution with a linear runtime complexity and use only constant extra space.

**Example 1:**
```
Input: nums = [2,2,1]
Output: 1
```

**Example 2:**
```
Input: nums = [4,1,2,1,2]
Output: 4
```

**Example 3:**
```
Input: nums = [1]
Output: 1
```

**Constraints:**
- `1 <= nums.length <= 3 * 10^4`
- `-3 * 10^4 <= nums[i] <= 3 * 10^4`
- Each element in the array appears twice except for one element which appears only once.

## Intuition/Main Idea

The key insight is to use the **XOR (exclusive OR) bitwise operation**. XOR has two important properties:
1. `a XOR a = 0` - Any number XORed with itself equals zero
2. `a XOR 0 = a` - Any number XORed with zero equals itself
3. XOR is commutative and associative: `a XOR b XOR a = (a XOR a) XOR b = 0 XOR b = b`

**Core Algorithm:**
Since every element appears twice except one, if we XOR all numbers together:
- Pairs of identical numbers will cancel out (result in 0)
- The single number will remain because `x XOR 0 = x`

This approach achieves O(1) space complexity (just one variable) and O(n) time complexity (single pass).

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Process all elements in array | For loop - Line 7 |
| XOR operation to cancel pairs | XOR assignment - Line 8 |
| Return single number | Return statement - Line 10 |
| Constant space complexity | Single variable `result` - Line 5 |
| Linear time complexity | Single pass through array - Line 7 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public int singleNumber(int[] nums) {
        // Initialize result to 0 (identity element for XOR)
        int result = 0;
        
        // XOR all numbers together
        // Pairs will cancel out, leaving only the single number
        for (int num : nums) {
            result ^= num;
        }
        
        return result;
    }
}
```

**Explanation of Key Code Sections:**

1. **Initialization (Line 5):** We initialize `result` to 0, which is the identity element for XOR operations. This means `x XOR 0 = x`.

2. **XOR Accumulation (Lines 7-9):** We iterate through all numbers and XOR each with the result:
   - When we encounter a number for the first time: `result = 0 XOR num = num`
   - When we encounter the same number again: `result = num XOR num = 0`
   - Since all numbers appear twice except one, all pairs cancel to 0
   - The single number remains: `result = 0 XOR singleNum = singleNum`

3. **Return (Line 11):** After processing all numbers, `result` contains the single number that appeared only once.

**Why XOR works:**
- XOR is commutative: `a XOR b = b XOR a`
- XOR is associative: `(a XOR b) XOR c = a XOR (b XOR c)`
- This means the order doesn't matter: `[2,2,1]` → `2 XOR 2 XOR 1 = (2 XOR 2) XOR 1 = 0 XOR 1 = 1`

**Alternative approaches (for learning):**
- **Hash Set:** Add numbers, remove when seen twice. O(n) time, O(n) space.
- **Math:** `2 * (sum of unique elements) - sum of all elements = single number`. O(n) time, O(n) space for set.
- **Sorting:** Sort and check adjacent pairs. O(n log n) time, O(1) space.

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the length of the array. We iterate through the array once.

- **Space Complexity:** $O(1)$ as we only use a single variable to store the result.

## Similar Problems

Problems that can be solved using similar bit manipulation or XOR techniques:

1. **136. Single Number** (this problem) - XOR to find single element
2. **137. Single Number II** - Extension where one number appears once, others three times
3. **260. Single Number III** - Two numbers appear once, rest twice
4. **268. Missing Number** - XOR to find missing number in range
5. **389. Find the Difference** - XOR to find added character
6. **421. Maximum XOR of Two Numbers in an Array** - Trie + XOR for maximum XOR pair
7. **461. Hamming Distance** - XOR to find differing bits
8. **693. Binary Number with Alternating Bits** - XOR patterns
9. **1442. Count Triplets That Can Form Two Arrays of Equal XOR** - XOR prefix sums
10. **1720. Decode XORed Array** - XOR properties for decoding

