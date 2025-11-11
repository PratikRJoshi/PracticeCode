# Russian Doll Envelopes

## Problem Description

**Problem Link:** [Russian Doll Envelopes](https://leetcode.com/problems/russian-doll-envelopes/)

You are given a 2D array of integers `envelopes` where `envelopes[i] = [wi, hi]` represents the width and the height of an envelope.

One envelope can fit into another if and only if both the width and height of one envelope are greater than the width and height of the other envelope.

Return *the maximum number of envelopes you can Russian doll (i.e., put one inside the other)*.

**Note:** You cannot rotate an envelope.

**Example 1:**
```
Input: envelopes = [[5,4],[6,4],[6,7],[2,3]]
Output: 3
Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
```

**Example 2:**
```
Input: envelopes = [[1,1],[1,1],[1,1]]
Output: 1
```

**Constraints:**
- `1 <= envelopes.length <= 10^5`
- `envelopes[i].length == 2`
- `1 <= wi, hi <= 10^5`

## Intuition/Main Idea

This is a **2D Longest Increasing Subsequence** problem. We need to find the longest chain of envelopes where each envelope can fit inside the next.

**Core Algorithm:**
1. Sort envelopes by width (ascending), and if widths are equal, sort by height (descending).
2. Find the Longest Increasing Subsequence based on height.
3. The descending height for equal widths ensures we don't incorrectly chain envelopes with the same width.

**Why this works:** After sorting by width, the problem reduces to finding LIS on heights. Sorting heights in descending order for equal widths prevents chaining envelopes with the same width (which is invalid).

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Sort envelopes | Custom comparator - Lines 7-12 |
| Binary search for LIS | Binary search - Lines 15-25 |
| Maintain LIS array | LIS array - Line 14 |
| Return maximum length | Return statement - Line 26 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        int n = envelopes.length;
        
        // Sort by width ascending, and if widths are equal, sort by height descending
        // This ensures we can use LIS on heights after sorting
        Arrays.sort(envelopes, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];  // Descending height for equal width
            }
            return a[0] - b[0];  // Ascending width
        });
        
        // Find LIS on heights using binary search
        List<Integer> lis = new ArrayList<>();
        
        for (int[] envelope : envelopes) {
            int height = envelope[1];
            
            // Binary search for the position to insert/replace
            int left = 0, right = lis.size();
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (lis.get(mid) < height) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            
            // If height is larger than all, append; otherwise replace
            if (left == lis.size()) {
                lis.add(height);
            } else {
                lis.set(left, height);
            }
        }
        
        return lis.size();
    }
}
```

**Explanation of Key Code Sections:**

1. **Sorting (Lines 7-12):** 
   - Sort by width ascending: ensures envelopes are ordered by width.
   - For equal widths, sort height descending: prevents chaining envelopes with same width (invalid).

2. **LIS with Binary Search (Lines 14-25):** 
   - Maintain a list `lis` where `lis[i]` is the smallest height ending a subsequence of length `i+1`.
   - For each envelope height, binary search to find where to insert/replace.
   - This gives O(n log n) time instead of O(n²).

3. **Binary Search Logic (Lines 18-24):** 
   - Find the leftmost position where we can place the current height.
   - If it's larger than all, append (new longest subsequence).
   - Otherwise, replace to maintain the smallest ending height.

**Why descending height for equal widths:**
- If we have `[5,4]` and `[5,6]`, we can't chain them (same width).
- By sorting heights descending for equal widths, `[5,6]` comes before `[5,4]`.
- When processing `[5,4]`, it won't extend a chain ending with `[5,6]` because 4 < 6.

**Example walkthrough for `envelopes = [[5,4],[6,4],[6,7],[2,3]]`:**
- After sort: `[[2,3],[5,4],[6,7],[6,4]]` (note [6,7] before [6,4])
- Process heights: [3,4,7,4]
- LIS: [3] → [3,4] → [3,4,7] → [3,4,4] (replace 7 with 4)
- Final length: 3 ✓

## Complexity Analysis

- **Time Complexity:** $O(n \log n)$ where $n$ is the number of envelopes. Sorting takes O(n log n) and LIS with binary search takes O(n log n).

- **Space Complexity:** $O(n)$ for the LIS list.

## Similar Problems

Problems that can be solved using similar LIS patterns:

1. **354. Russian Doll Envelopes** (this problem) - 2D LIS
2. **300. Longest Increasing Subsequence** - Classic LIS
3. **368. Largest Divisible Subset** - LIS variant
4. **673. Number of Longest Increasing Subsequence** - Count LIS
5. **646. Maximum Length of Pair Chain** - Interval LIS
6. **1048. Longest String Chain** - String chain LIS
7. **1027. Longest Arithmetic Sequence** - Arithmetic progression
8. **1218. Longest Arithmetic Subsequence of Given Difference** - Fixed difference

