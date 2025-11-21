# [27. Remove Element](https://leetcode.com/problems/remove-element/)

Given an integer array `nums` and an integer `val`, remove all occurrences of `val` in `nums` **in-place**. The relative order of the elements may be changed.

Since it is impossible to change the length of the array in some languages, you must instead have the result be placed in the **first part** of the array `nums`. More formally, if there are `k` elements after removing the duplicates, then the first `k` elements of `nums` should hold the final result. It does not matter what you leave beyond the first `k` elements.

Return `k` after placing the final result in the first `k` slots of `nums`.

Do **not** allocate extra space for another array. You must do this by **modifying the input array in-place** with O(1) extra memory.

**Custom Judge:**

The judge will test your solution with the following code:

```
int[] nums = [...]; // Input array
int val = ...; // Value to remove
int[] expectedNums = [...]; // The expected answer with correct length.
                            // It is sorted with no values equaling val.

int k = removeElement(nums, val); // Calls your implementation

assert k == expectedNums.length;
sort(nums, 0, k); // Sort the first k elements of nums
for (int i = 0; i < actualLength; i++) {
    assert nums[i] == expectedNums[i];
}
```

If all assertions pass, then your solution will be **accepted**.

**Example 1:**

```
Input: nums = [3,2,2,3], val = 3
Output: 2, nums = [2,2,_,_]
Explanation: Your function should return k = 2, with the first two elements of nums being 2.
It does not matter what you leave beyond the returned k (hence they are underscores).
```

**Example 2:**

```
Input: nums = [0,1,2,2,3,0,4,2], val = 2
Output: 5, nums = [0,1,4,0,3,_,_,_]
Explanation: Your function should return k = 5, with the first five elements of nums containing 0, 0, 1, 3, and 4.
Note that the five elements can be returned in any order.
It does not matter what you leave beyond the returned k (hence they are underscores).
```

**Constraints:**

- `0 <= nums.length <= 100`
- `0 <= nums[i] <= 50`
- `0 <= val <= 100`

## Intuition/Main Idea:

This problem asks us to remove all occurrences of a specific value from an array in-place, meaning we can't use additional arrays. The key insight is that we don't actually need to "remove" elements; we just need to ensure that all non-target elements are moved to the front of the array.

A simple and efficient approach is to use the two-pointer technique:
1. Use one pointer (`k`) to keep track of where the next non-target element should be placed.
2. Use another pointer (`i`) to scan through the array.
3. When we find a non-target element, we place it at position `k` and increment `k`.

This way, all non-target elements will be moved to the front of the array, and `k` will represent the length of the array after "removing" all occurrences of the target value.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Remove all occurrences of val in-place | `if (nums[i] != val) { nums[k] = nums[i]; k++; }` |
| Return k after placing final result | `return k;` |
| Do not allocate extra space | The solution uses only two pointers, no extra arrays |

## Final Java Code & Learning Pattern:

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        // Edge case: empty array
        if (nums.length == 0) {
            return 0;
        }
        
        // Initialize pointer for the position where non-target elements should be placed
        int k = 0;
        
        // Scan through the array
        for (int i = 0; i < nums.length; i++) {
            // If the current element is not the target value
            if (nums[i] != val) {
                // Place it at position k and increment k
                nums[k] = nums[i];
                k++;
            }
            // If the current element is the target value, skip it
        }
        
        // k now represents the length of the array after removing all occurrences of val
        return k;
    }
}
```

This solution uses the two-pointer technique:

1. We initialize a pointer `k` to 0, which represents the position where the next non-target element should be placed.
2. We iterate through the array with pointer `i`.
3. For each element:
   - If it's not the target value, we place it at position `k` and increment `k`.
   - If it's the target value, we simply skip it.
4. After the iteration, `k` represents the length of the array after "removing" all occurrences of the target value.

The key insight is that we're effectively overwriting the elements we want to "remove" with the elements we want to keep, and `k` keeps track of where the next element should be placed.

## Alternative Implementation:

Another approach is to swap elements from the end of the array:

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int i = 0;
        int n = nums.length;
        
        while (i < n) {
            if (nums[i] == val) {
                // Replace the current element with the last element
                nums[i] = nums[n - 1];
                // Reduce the array size by 1
                n--;
            } else {
                // Move to the next element
                i++;
            }
        }
        
        return n;
    }
}
```

This approach is more efficient when the elements to remove are rare, as it avoids unnecessary copies. It works by swapping elements to remove with elements from the end of the array, effectively "shrinking" the array.

## Complexity Analysis:

- **Time Complexity**: O(n) where n is the length of the array. We need to iterate through the array once.
- **Space Complexity**: O(1) as we only use a constant amount of extra space regardless of the input size.

## Similar Problems:

1. [26. Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)
2. [80. Remove Duplicates from Sorted Array II](https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/)
3. [283. Move Zeroes](https://leetcode.com/problems/move-zeroes/)
4. [844. Backspace String Compare](https://leetcode.com/problems/backspace-string-compare/)
5. [977. Squares of a Sorted Array](https://leetcode.com/problems/squares-of-a-sorted-array/)
