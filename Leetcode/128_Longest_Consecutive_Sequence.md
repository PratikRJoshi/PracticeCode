### 128. Longest Consecutive Sequence

**Problem:** https://leetcode.com/problems/longest-consecutive-sequence/

---

### Problem Description

Given an unsorted array of integers `nums`, return the length of the longest consecutive elements sequence.

You must write an algorithm that runs in `O(n)` time.

**Example 1:**
```
Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
```

**Example 2:**
```
Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
Explanation: The longest consecutive elements sequence is [0,1,2,3,4,5,6,7,8]. The sequence has 9 elements.
```

**Constraints:**
- `0 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`

---

### Main Idea & Intuition

The key insight is to use a **HashSet with greedy expansion and removal**. Instead of sorting (which would be O(n log n)), we can achieve O(n) time by:

1. **Store all numbers in a HashSet** for O(1) lookup
2. **For each number, greedily expand in both directions** (left and right) to find all consecutive numbers
3. **Remove numbers as we process them** - this is the critical optimization that ensures each number is only processed once

**Why this works in O(n) time:**

The brilliant part is the `set.remove()` calls inside the while loops. At first glance, it might seem like O(n²) because we have nested loops, but here's why it's actually O(n):

- Each number is added to the set once: O(n)
- Each number can only be removed from the set once: O(n) total across all iterations
- Once a number is removed, it will never be processed again
- Therefore, the total number of `set.remove()` operations across the entire algorithm is at most n

This is called **amortized analysis** - even though we have nested loops, each element is visited at most twice (once when we encounter it in the outer loop, and once when it gets removed as part of another element's consecutive sequence).

**The Algorithm:**
1. Handle edge cases (null or empty array)
2. Add all numbers to a HashSet
3. For each number n in the original array:
   - **Skip if already processed** (handles duplicates efficiently)
   - **Remove n itself first** (critical: marks it as processed and handles isolated numbers)
   - Expand left: keep removing (n-1), (n-2), (n-3)... until no more consecutive numbers exist
   - Expand right: keep removing (n+1), (n+2), (n+3)... until no more consecutive numbers exist
   - Calculate the length of this consecutive sequence: `right - left - 1`
   - Track the maximum length found
4. Early termination: if the set becomes empty, we can return immediately

---

### Final Java Code & Explanation

```java
class Solution {
    public int longestConsecutive(int[] nums) {
        // Edge case: if array is null or empty, no sequence exists
        if(nums == null || nums.length == 0) {
            return 0;
        }
        
        // Step 1: Add all numbers to a HashSet for O(1) lookup
        // This allows us to quickly check if consecutive numbers exist
        Set<Integer> set = new HashSet<>();
        for(int n : nums) {
            set.add(n);
        }
        
        int max = 0;
        
        // Step 2: For each number, try to build a consecutive sequence
        for(int n : nums) {
            // Skip if this number was already processed as part of another sequence
            // This handles duplicates in the input array efficiently
            if(!set.contains(n)) {
                continue;
            }
            
            // Remove the current number first - crucial for correctness!
            // This ensures isolated numbers and sequence centers are marked as processed
            set.remove(n);
            
            // Initialize pointers for left and right boundaries
            int left = n - 1;   // One less than current number
            int right = n + 1;  // One more than current number
            
            // Expand left: remove all consecutive numbers smaller than n
            // The while loop continues as long as (n-1), (n-2), etc. exist in set
            // Key: set.remove() returns true if element existed (and removes it)
            while(set.remove(left)) {
                left--;  // Move to next smaller number
            }
            // After loop: 'left' points to one position BEFORE the start of sequence
            
            // Expand right: remove all consecutive numbers larger than n
            // The while loop continues as long as (n+1), (n+2), etc. exist in set
            while(set.remove(right)) {
                right++;  // Move to next larger number
            }
            // After loop: 'right' points to one position AFTER the end of sequence
            
            // Calculate sequence length:
            // If sequence is [left+1, left+2, ..., right-1]
            // Length = (right - 1) - (left + 1) + 1 = right - left - 1
            max = Math.max(max, right - left - 1);
            
            // Early termination: if we've processed all numbers, no need to continue
            // This optimization can save iterations when we find a very long sequence early
            if(set.isEmpty()) {
                return max;
            }
        }
        
        return max;
    }
}
```

**Why `right - left - 1` gives the correct length:**

Let's trace through an example: `nums = [100, 4, 200, 1, 3, 2]`

When we process `n = 3`:
- First, we remove 3 itself from the set
- `left = 2, right = 4` initially
- Expand left: `set.remove(2)` succeeds, `left = 1`; then `set.remove(1)` succeeds, `left = 0`; then `set.remove(0)` fails
- Now `left = 0` (one position before the start of our sequence which is 1)
- Expand right: `set.remove(4)` succeeds, `right = 5`; then `set.remove(5)` fails  
- Now `right = 5` (one position after the end of our sequence which is 4)
- Sequence is [1, 2, 3, 4], length = 5 - 0 - 1 = 4 ✓

**Why removing `n` itself is critical:**
- **Isolated numbers**: If a number has no consecutive neighbors (like 100 or 200), it must be removed to avoid reprocessing
- **Duplicates**: If the input has duplicates, the `set.contains(n)` check at the start ensures we skip already-processed numbers
- **Correctness**: Every number in a sequence gets removed exactly once, guaranteeing true O(n) behavior

---

### Complexity Analysis

**Time Complexity:** `O(n)`

- Building the HashSet: O(n)
- The outer loop runs n times (or potentially more if there are duplicates, but duplicates are skipped via the `contains` check)
- **Key insight:** Each element can only be removed from the set **once** (via `set.remove(n)`, `set.remove(left)`, or `set.remove(right)`)
- Total remove operations across all iterations = exactly n (one per unique element)
- Therefore: O(n) for building + O(n) for processing = O(n)

This is a classic example of **amortized time complexity analysis**. Even though we have nested loops, the inner while loops collectively perform at most n remove operations across the entire execution, not n operations per outer loop iteration.

**Space Complexity:** `O(n)`

- HashSet stores all n elements from the input array
- No other significant space is used

---

### Similar Problems

The following problems share similar patterns or techniques:

1. **[#217 Contains Duplicate](https://leetcode.com/problems/contains-duplicate/)** - Basic HashSet usage for O(1) lookup
2. **[#219 Contains Duplicate II](https://leetcode.com/problems/contains-duplicate-ii/)** - HashSet with sliding window
3. **[#1570 Dot Product of Two Sparse Vectors](https://leetcode.com/problems/dot-product-of-two-sparse-vectors/)** - HashSet for efficient lookup
4. **[#2274 Maximum Consecutive Floors Without Special Floors](https://leetcode.com/problems/maximum-consecutive-floors-without-special-floors/)** - Finding gaps in consecutive sequences
5. **[#298 Binary Tree Longest Consecutive Sequence](https://leetcode.com/problems/binary-tree-longest-consecutive-sequence/)** - Consecutive sequence in tree structure
6. **[#549 Binary Tree Longest Consecutive Sequence II](https://leetcode.com/problems/binary-tree-longest-consecutive-sequence-ii/)** - Bidirectional consecutive sequence
7. **[#2274 Maximum Consecutive Floors Without Special Floors](https://leetcode.com/problems/maximum-consecutive-floors-without-special-floors/)** - Finding maximum gaps

**Related Pattern - Union Find:**
- **[#323 Number of Connected Components in an Undirected Graph](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/)** - Alternative approach using Union-Find data structure
- **[#547 Number of Provinces](https://leetcode.com/problems/number-of-provinces/)** - Finding connected components

**Key Technique Shared:** Using HashSet for O(1) membership testing combined with greedy expansion and the "mark as visited" pattern (removing from set) to achieve linear time complexity.
