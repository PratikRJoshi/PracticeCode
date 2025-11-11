# Partition to K Equal Sum Subsets

## Problem Description

**Problem Link:** [Partition to K Equal Sum Subsets](https://leetcode.com/problems/partition-to-k-equal-sum-subsets/)

Given an integer array `nums` and an integer `k`, return `true` *if it is possible to divide this array into `k` non-empty subsets whose sums are all equal*.

**Example 1:**
```
Input: nums = [4,3,2,3,5,2,1], k = 4
Output: true
Explanation: It is possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
```

**Example 2:**
```
Input: nums = [1,2,3,4], k = 3
Output: false
```

**Constraints:**
- `1 <= k <= nums.length <= 16`
- `1 <= nums[i] <= 10^4`
- The frequency of each element is in the range `[1, 4]`.

## Intuition/Main Idea

This is a **backtracking** problem with **bitmasking** optimization. We need to partition the array into `k` subsets with equal sums.

**Core Algorithm:**
1. Calculate target sum: `totalSum / k`.
2. Use backtracking to try placing each number into one of `k` buckets.
3. Use memoization with bitmask to avoid recomputing the same state.
4. Prune early if a bucket exceeds target sum.

**Why backtracking works:** We need to explore all possible ways to partition. Backtracking systematically tries all possibilities and prunes invalid ones early.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Calculate target sum | Target calculation - Lines 7-10 |
| Sort for optimization | Arrays.sort - Line 12 |
| Backtrack with memoization | Backtrack method - Lines 15-35 |
| Try each bucket | For loop - Lines 22-30 |
| Check if bucket full | Target check - Line 24 |
| Memoize state | Memo map - Lines 18, 32 |
| Return result | Return statement - Line 14 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        
        // Check if total sum is divisible by k
        if (totalSum % k != 0) {
            return false;
        }
        
        int target = totalSum / k;
        
        // Sort in descending order for better pruning
        Arrays.sort(nums);
        reverse(nums);
        
        // Memoization: key = bitmask, value = can partition
        Map<Integer, Boolean> memo = new HashMap<>();
        
        return backtrack(nums, 0, 0, target, k, 0, memo);
    }
    
    private boolean backtrack(int[] nums, int index, int currentSum, int target, 
                             int remainingBuckets, int used, Map<Integer, Boolean> memo) {
        // Base case: all buckets filled
        if (remainingBuckets == 0) {
            return true;
        }
        
        // Check memoization
        if (memo.containsKey(used)) {
            return memo.get(used);
        }
        
        // If current bucket is full, move to next bucket
        if (currentSum == target) {
            boolean result = backtrack(nums, 0, 0, target, remainingBuckets - 1, used, memo);
            memo.put(used, result);
            return result;
        }
        
        // Try placing each unused number in current bucket
        for (int i = index; i < nums.length; i++) {
            // Skip if number already used or would exceed target
            if ((used & (1 << i)) != 0 || currentSum + nums[i] > target) {
                continue;
            }
            
            // Use number i
            used |= (1 << i);
            
            // Recurse
            if (backtrack(nums, i + 1, currentSum + nums[i], target, remainingBuckets, used, memo)) {
                return true;
            }
            
            // Backtrack: unuse number i
            used ^= (1 << i);
            
            // Skip duplicates
            while (i + 1 < nums.length && nums[i] == nums[i + 1]) {
                i++;
            }
        }
        
        memo.put(used, false);
        return false;
    }
    
    private void reverse(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
```

**Explanation of Key Code Sections:**

1. **Validation (Lines 7-10):** Check if total sum is divisible by `k`. If not, impossible to partition.

2. **Sort Descending (Line 12):** Sort in descending order. Larger numbers are tried first, leading to better pruning.

3. **Backtracking (Lines 15-35):**
   - **Base Case (Lines 16-18):** If all buckets are filled, return true.
   - **Memoization (Lines 20-23):** Use bitmask `used` to represent which numbers are used. Check memo to avoid recomputation.
   - **Bucket Full (Lines 25-29):** If current bucket is full, move to next bucket.
   - **Try Numbers (Lines 31-44):** For each unused number:
     - Skip if already used or would exceed target.
     - Mark as used and recurse.
     - Backtrack if recursion fails.
     - Skip duplicates to avoid redundant work.

4. **Bitmasking:** Use integer bits to represent which numbers are used. `used & (1 << i)` checks if number `i` is used.

**Why bitmasking works:**
- **State representation:** `used` is an integer where bit `i` represents whether `nums[i]` is used.
- **Efficient:** O(1) to check/set bits.
- **Memoization:** Can use `used` as key for memoization.

**Example walkthrough for `nums = [4,3,2,3,5,2,1], k = 4`:**
- Total sum = 20, target = 5
- After sort: [5,4,3,3,2,2,1]
- Bucket 1: [5] → full
- Bucket 2: [4,1] → full
- Bucket 3: [3,2] → full
- Bucket 4: [3,2] → full
- Result: true ✓

## Complexity Analysis

- **Time Complexity:** $O(k \times 2^n)$ in worst case, but memoization and pruning significantly reduce this.

- **Space Complexity:** $O(2^n)$ for memoization in worst case.

## Similar Problems

Problems that can be solved using similar backtracking and bitmasking patterns:

1. **698. Partition to K Equal Sum Subsets** (this problem) - Backtracking with bitmasking
2. **416. Partition Equal Subset Sum** - 2-way partition (k=2)
3. **473. Matchsticks to Square** - 4-way partition (k=4)
4. **2305. Fair Distribution of Cookies** - Partition with constraints
5. **2035. Partition Array Into Two Arrays to Minimize Sum Difference** - 2-way partition
6. **1049. Last Stone Weight II** - Partition to minimize difference
7. **39. Combination Sum** - Backtracking combinations
8. **40. Combination Sum II** - With duplicates

