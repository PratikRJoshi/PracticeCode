### 33. Search in Rotated Sorted Array
### Problem Link: [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/)
### Intuition
This problem asks us to search for a target value in a rotated sorted array. A rotated sorted array is a sorted array that has been rotated at some pivot point. For example, `[0,1,2,4,5,6,7]` might become `[4,5,6,7,0,1,2]` after rotation.

The key insight is to use a modified binary search. In a regular binary search, we compare the middle element with the target and decide which half to search next. In a rotated sorted array, we need an additional step to determine which half is sorted and then check if the target lies within that sorted half.

### Java Reference Implementation
```java
class Solution {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) { // [R0] Handle edge cases
            return -1;
        }
        
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) { // [R1] Standard binary search loop
            int mid = left + (right - left) / 2; // [R2] Calculate middle index
            
            if (nums[mid] == target) { // [R3] Check if middle element is the target
                return mid;
            }
            
            // [R4] Check which half is sorted
            if (nums[left] <= nums[mid]) { // Left half is sorted
                // [R5] Check if target is in the sorted left half
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1; // Search in the left half
                } else {
                    left = mid + 1; // Search in the right half
                }
            } else { // Right half is sorted
                // [R6] Check if target is in the sorted right half
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1; // Search in the right half
                } else {
                    right = mid - 1; // Search in the left half
                }
            }
        }
        
        return -1; // [R7] Target not found
    }
}
```

### Understanding the Loop Checks and Boundary Conditions

The binary search implementation for rotated sorted arrays has several important nuances that are worth understanding:

1. **Loop Condition (`left <= right` vs `left < right`):**
   - We use `while (left <= right)` instead of `while (left < right)` because:
     - We're searching for a specific target value that might be at any position
     - The `<=` ensures we check the case when `left == right` (a single element)
     - This is consistent with standard binary search when looking for a specific value

2. **Determining Which Half is Sorted (`nums[left] <= nums[mid]` vs `nums[left] < nums[mid]`):**
   - The condition `nums[left] <= nums[mid]` checks if the left half is sorted
   - The `<=` (instead of just `<`) is crucial to handle the case when `left` and `mid` point to the same element
   - This happens when the array size is 2 and `left = 0, mid = 0, right = 1`

3. **Target Range Checks:**
   - For the sorted left half: `nums[left] <= target && target < nums[mid]`
     - We use `<=` for the lower bound to include the case when the target equals the leftmost element
     - We use `<` for the upper bound because if `target == nums[mid]`, we would have already returned
   - For the sorted right half: `nums[mid] < target && target <= nums[right]`
     - We use `<` for the lower bound because if `target == nums[mid]`, we would have already returned
     - We use `<=` for the upper bound to include the case when the target equals the rightmost element

4. **Boundary Updates:**
   - When searching in the left half: `right = mid - 1`
     - We exclude `mid` because we've already checked if `nums[mid] == target`
   - When searching in the right half: `left = mid + 1`
     - We exclude `mid` for the same reason

5. **Special Case - When Array Contains Duplicates:**
   - This implementation assumes all elements are distinct
   - If duplicates are allowed (as in "Search in Rotated Sorted Array II"), additional checks are needed
   - With duplicates, the condition `nums[left] <= nums[mid]` might not reliably tell which half is sorted

Understanding these subtleties is key to correctly implementing binary search for rotated arrays and avoiding off-by-one errors or infinite loops.

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (nums == null || nums.length == 0) { return -1; }` - Return -1 for empty arrays
- **R1 (Binary search loop)**: `while (left <= right)` - Standard binary search loop condition
- **R2 (Calculate middle)**: `int mid = left + (right - left) / 2;` - Calculate middle index safely
- **R3 (Check target)**: `if (nums[mid] == target) { return mid; }` - Return index if target found
- **R4 (Determine sorted half)**: Check which half of the array is sorted
- **R5 (Search left half)**: If left half is sorted and target is in range, search there
- **R6 (Search right half)**: If right half is sorted and target is in range, search there
- **R7 (Not found)**: `return -1;` - Return -1 if target is not found

### Complexity Analysis
- **Time Complexity**: O(log n) - We perform a binary search
- **Space Complexity**: O(1) - We use a constant amount of extra space

### Related Problems
- **Search in Rotated Sorted Array II** (Problem 81): Similar but with duplicates allowed
- **Find Minimum in Rotated Sorted Array** (Problem 153): Find the minimum element in a rotated sorted array
- **Find Minimum in Rotated Sorted Array II** (Problem 154): Find the minimum element with duplicates allowed
