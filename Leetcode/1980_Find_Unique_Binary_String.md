# Find Unique Binary String

## Problem Description

**Problem Link:** [Find Unique Binary String](https://leetcode.com/problems/find-unique-binary-string/)

Given an array of strings `nums` containing `n` unique binary strings each of length `n`, return *a binary string of length `n` that **does not appear** in `nums`*. If there are multiple answers, you may return **any** of them.

**Example 1:**

```
Input: nums = ["01","10"]
Output: "11"
Explanation: "11" does not appear in nums. "00" would also be correct.
```

**Example 2:**

```
Input: nums = ["00","01"]
Output: "11"
Explanation: "11" does not appear in nums. "10" would also be correct.
```

**Example 3:**

```
Input: nums = ["111","011","001"]
Output: "101"
Explanation: "101" does not appear in nums. "000", "010", "100", and "110" would also be correct.
```

**Constraints:**
- `n == nums.length`
- `1 <= n <= 16`
- `nums[i].length() == n`
- `nums[i]` is either `'0'` or `'1'`.
- All the strings of `nums` are **unique**.

## Intuition/Main Idea

This is a **backtracking** or **Cantor's diagonal argument** problem. We need to find a binary string not in the given set.

**Core Algorithm (Cantor's Diagonal):**
1. For position `i`, choose the opposite of `nums[i][i]`.
2. This guarantees the result differs from each string in `nums` at position `i`.

**Alternative (Backtracking):**
1. Generate binary strings of length `n`.
2. Check if string is in `nums`.
3. Return first string not in `nums`.

**Why Cantor's diagonal works:** By choosing the opposite character at position `i` from `nums[i]`, we ensure our string differs from `nums[i]` at position `i`, so it can't be equal to any string in `nums`.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Cantor's diagonal | For loop - Lines 5-7 |
| Choose opposite | Character selection - Line 6 |
| Return result | Return statement - Line 8 |

## Final Java Code & Learning Pattern

### Cantor's Diagonal Approach (Optimal)

```java
class Solution {
    public String findDifferentBinaryString(String[] nums) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            // Choose opposite of nums[i][i]
            result.append(nums[i].charAt(i) == '0' ? '1' : '0');
        }
        return result.toString();
    }
}
```

### Backtracking Approach

```java
import java.util.*;

class Solution {
    public String findDifferentBinaryString(String[] nums) {
        Set<String> set = new HashSet<>(Arrays.asList(nums));
        return backtrack(nums[0].length(), "", set);
    }
    
    private String backtrack(int n, String current, Set<String> set) {
        if (current.length() == n) {
            if (!set.contains(current)) {
                return current;
            }
            return null;
        }
        
        // Try '0'
        String result = backtrack(n, current + "0", set);
        if (result != null) {
            return result;
        }
        
        // Try '1'
        return backtrack(n, current + "1", set);
    }
}
```

**Explanation of Key Code Sections:**

**Cantor's Diagonal:**

1. **Choose Opposite (Line 6):** For position `i`, choose the opposite character of `nums[i][i]`.
2. **Guarantee:** This ensures the result differs from `nums[i]` at position `i`, so it can't be in `nums`.

**Backtracking:**

1. **Base Case (Lines 7-11):** When string length is `n`, check if it's not in set.
2. **Try Characters (Lines 14-20):** Try '0' and '1', return first valid string.

**Why Cantor's diagonal is better:**
- **Time:** O(n) vs O(2^n) for backtracking.
- **Space:** O(n) vs O(n) for backtracking.
- **Guaranteed:** Always finds a solution in one pass.

**Example walkthrough for `nums = ["01","10"]`:**
- i=0: nums[0][0]='0' → choose '1' → result="1"
- i=1: nums[1][1]='0' → choose '1' → result="11" ✓

## Complexity Analysis

- **Time Complexity:** 
  - Cantor's diagonal: $O(n)$.
  - Backtracking: $O(2^n)$ in worst case.

- **Space Complexity:** 
  - Cantor's diagonal: $O(n)$.
  - Backtracking: $O(n)$ for recursion stack.

## Similar Problems

Problems that can be solved using similar patterns:

1. **1980. Find Unique Binary String** (this problem) - Cantor's diagonal or backtracking
2. **784. Letter Case Permutation** - Backtracking with transformations
3. **22. Generate Parentheses** - Generate strings
4. **17. Letter Combinations of a Phone Number** - Generate combinations
5. **1238. Circular Permutation in Binary Representation** - Binary generation
6. **1415. The k-th Lexicographical String of All Happy Strings of Length n** - String generation
7. **46. Permutations** - Generate permutations
8. **77. Combinations** - Generate combinations

