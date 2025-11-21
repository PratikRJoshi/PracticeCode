# [88. Merge Sorted Array](https://leetcode.com/problems/merge-sorted-array/)

You are given two integer arrays `nums1` and `nums2`, sorted in **non-decreasing order**, and two integers `m` and `n`, representing the number of elements in `nums1` and `nums2` respectively.

**Merge** `nums1` and `nums2` into a single array sorted in **non-decreasing order**.

The final sorted array should not be returned by the function, but instead be stored inside the array `nums1`. To accommodate this, `nums1` has a length of `m + n`, where the first `m` elements denote the elements that should be merged, and the last `n` elements are set to `0` and should be ignored. `nums2` has a length of `n`.

**Example 1:**

```
Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
Output: [1,2,2,3,5,6]
Explanation: The arrays we are merging are [1,2,3] and [2,5,6].
The result of the merge is [1,2,2,3,5,6] with the underlined elements coming from nums1.
```

**Example 2:**

```
Input: nums1 = [1], m = 1, nums2 = [], n = 0
Output: [1]
Explanation: The arrays we are merging are [1] and [].
The result of the merge is [1].
```

**Example 3:**

```
Input: nums1 = [0], m = 0, nums2 = [1], n = 1
Output: [1]
Explanation: The arrays we are merging are [] and [1].
The result of the merge is [1].
Note that because m = 0, there are no elements in nums1. The 0 is only there to ensure the merge result can fit in nums1.
```

**Constraints:**

- `nums1.length == m + n`
- `nums2.length == n`
- `0 <= m, n <= 200`
- `1 <= m + n <= 200`
- `-10^9 <= nums1[i], nums2[j] <= 10^9`

**Follow up:** Can you come up with an algorithm that runs in `O(m + n)` time?

## Intuition/Main Idea:

This problem asks us to merge two sorted arrays into one, with the result stored in the first array. The key insight is that the first array has enough space to accommodate all elements from both arrays.

There are several approaches to solve this problem:

1. **Merge and Sort**: Copy all elements from `nums2` to the end of `nums1`, then sort the entire array. This is simple but not optimal.
2. **Merge from Front**: Use a temporary array to store the merged result, then copy it back to `nums1`. This requires extra space.
3. **Merge from Back**: Start filling `nums1` from the end, comparing elements from both arrays and placing the larger one at the end of `nums1`. This is optimal as it doesn't require extra space.

The third approach (merge from back) is the most efficient and elegant solution, as it avoids the need for extra space and takes advantage of the fact that `nums1` has enough space at the end.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Merge nums1 and nums2 into nums1 | The entire solution implements this |
| Result should be sorted in non-decreasing order | `if (nums1[p1] > nums2[p2]) { ... } else { ... }` ensures this |
| nums1 has length m + n with last n elements set to 0 | The solution utilizes this space for merging |

## Final Java Code & Learning Pattern:

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // Initialize pointers to the last elements of nums1 and nums2
        int p1 = m - 1;  // Pointer for nums1 (points to the last valid element)
        int p2 = n - 1;  // Pointer for nums2
        int p = m + n - 1;  // Pointer for the merged array (points to the last position)
        
        // Merge from back to front
        while (p1 >= 0 && p2 >= 0) {
            // Compare elements and place the larger one at the end of nums1
            if (nums1[p1] > nums2[p2]) {
                nums1[p] = nums1[p1];
                p1--;
            } else {
                nums1[p] = nums2[p2];
                p2--;
            }
            p--;
        }
        
        // If there are remaining elements in nums2, copy them to nums1
        // (No need to handle remaining elements in nums1 as they are already in place)
        while (p2 >= 0) {
            nums1[p] = nums2[p2];
            p2--;
            p--;
        }
    }
}
```

This solution uses the "merge from back" approach:

1. We initialize three pointers:
   - `p1`: Points to the last valid element in `nums1`.
   - `p2`: Points to the last element in `nums2`.
   - `p`: Points to the last position in the merged array (which is the last position in `nums1`).

2. We compare elements from both arrays and place the larger one at position `p` in `nums1`, then decrement the corresponding pointer.

3. We continue this process until we've processed all elements from one of the arrays.

4. If there are remaining elements in `nums2`, we copy them to the beginning of `nums1`. (Note: If there are remaining elements in `nums1`, they are already in their correct positions, so we don't need to do anything.)

The key insight is that by merging from back to front, we avoid overwriting elements in `nums1` that we still need to compare.

## Alternative Implementations:

### Merge and Sort Approach:

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // Copy elements from nums2 to the end of nums1
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        
        // Sort the entire array
        Arrays.sort(nums1);
    }
}
```

This approach is simple but not optimal, as it doesn't take advantage of the fact that the arrays are already sorted. Its time complexity is O((m+n)log(m+n)) due to the sorting step.

### Merge from Front with Extra Space:

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // Create a copy of the valid part of nums1
        int[] nums1Copy = new int[m];
        for (int i = 0; i < m; i++) {
            nums1Copy[i] = nums1[i];
        }
        
        // Merge the arrays into nums1
        int p1 = 0;  // Pointer for nums1Copy
        int p2 = 0;  // Pointer for nums2
        int p = 0;   // Pointer for nums1
        
        while (p1 < m && p2 < n) {
            if (nums1Copy[p1] <= nums2[p2]) {
                nums1[p] = nums1Copy[p1];
                p1++;
            } else {
                nums1[p] = nums2[p2];
                p2++;
            }
            p++;
        }
        
        // Copy remaining elements from nums1Copy
        while (p1 < m) {
            nums1[p] = nums1Copy[p1];
            p1++;
            p++;
        }
        
        // Copy remaining elements from nums2
        while (p2 < n) {
            nums1[p] = nums2[p2];
            p2++;
            p++;
        }
    }
}
```

This approach uses extra space to store a copy of the valid part of `nums1`, then merges the arrays from front to back. Its time complexity is O(m+n), but its space complexity is O(m).

## Complexity Analysis:

- **Time Complexity**: O(m+n) where m and n are the lengths of the valid parts of `nums1` and `nums2`. We process each element exactly once.
- **Space Complexity**: O(1) as we only use a constant amount of extra space regardless of the input size.

## Similar Problems:

1. [21. Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/)
2. [23. Merge k Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/)
3. [977. Squares of a Sorted Array](https://leetcode.com/problems/squares-of-a-sorted-array/)
4. [986. Interval List Intersections](https://leetcode.com/problems/interval-list-intersections/)
5. [1213. Intersection of Three Sorted Arrays](https://leetcode.com/problems/intersection-of-three-sorted-arrays/)
