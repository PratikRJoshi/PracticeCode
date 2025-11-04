### 153. Find Minimum in Rotated Sorted Array
### Problem Link: [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)
### Intuition
This problem asks us to find the minimum element in a rotated sorted array. A rotated sorted array is a sorted array that has been rotated at some pivot point. For example, `[0,1,2,4,5,6,7]` might become `[4,5,6,7,0,1,2]` after rotation.

The key insight is to use binary search to find the pivot point (where the minimum element is located). In a rotated sorted array, the minimum element is the only element that is smaller than both its previous and next elements. We can use the fact that elements to the left of the minimum are greater than the rightmost element, and elements to the right of the minimum are less than the rightmost element.

### Java Reference Implementation
```java
class Solution {
    public int findMin(int[] nums) {
        if (nums.length == 1) { // [R0] Handle single element case
            return nums[0];
        }
        
        int left = 0;
        int right = nums.length - 1;
        
        if (nums[left] < nums[right]) { // [R1] If array is not rotated (already sorted)
            return nums[left];
        }

        // [R2] Binary search to find the minimum element
        while(left < right){ // [R3] Note: using < instead of <= because we narrow down to a single element
            int mid = left + (right - left) / 2; // [R4] Calculate middle index safely
            
            if(nums[mid] < nums[right]){ // [R5] If middle element < rightmost element
                right = mid; // [R6] Right half is sorted, minimum must be in left half (including mid)
            } else {
                left = mid + 1; // [R7] Right half is not sorted, minimum must be in right half (excluding mid)
            }
        }

        // [R8] At this point, left == right and points to the minimum element
        return nums[left];
    }
}
```

### Understanding the Boundary Conditions and Loop Checks

The binary search implementation in this problem has some subtle but important differences from a standard binary search:

1. **Loop Condition (`left < right` vs `left <= right`):**
   - We use `while(left < right)` instead of `while(left <= right)` because:
     - We're not searching for a specific value but narrowing down to a single element
     - The loop terminates when `left == right`, which means we've found our answer
     - Using `<=` would require an additional check inside the loop to avoid infinite loops

2. **Right Boundary Update (`right = mid` vs `right = mid - 1`):**
   - When `nums[mid] < nums[right]`, we know the minimum is in the left half (including mid)
   - We set `right = mid` (not `mid - 1`) because the middle element could be the minimum
   - This is different from standard binary search where we typically exclude the middle element

3. **Left Boundary Update (`left = mid + 1` vs `left = mid`):**
   - When `nums[mid] >= nums[right]`, we know the minimum is in the right half
   - We set `left = mid + 1` because we can prove that `mid` itself cannot be the minimum:
     - If `nums[mid] > nums[right]`, then `mid` is greater than at least one element
     - If `nums[mid] == nums[right]` (not possible in this problem as all elements are distinct)

4. **Comparison with Rightmost Element:**
   - We compare `nums[mid]` with `nums[right]` (not `nums[left]`) because:
     - It gives us a reliable way to determine which half is sorted
     - In a rotated array, the minimum element is the only point where the "sorted property" breaks

This approach ensures that we always narrow down our search space correctly and eventually converge to the minimum element.

### Requirement → Code Mapping
- **R0 (Handle single element)**: `if (nums.length == 1) { return nums[0]; }` - Return the only element if array has size 1
- **R1 (Check if not rotated)**: `if (nums[left] < nums[right]) { return nums[left]; }` - Return first element if array is not rotated
- **R2 (Binary search)**: The main algorithm to find the minimum element
- **R3 (Loop condition)**: `while(left < right)` - Continue until we narrow down to a single element
- **R4 (Calculate middle)**: `int mid = left + (right - left) / 2;` - Calculate middle index safely
- **R5 (Check sorted half)**: `if(nums[mid] < nums[right])` - Determine which half contains the minimum
- **R6 (Update right boundary)**: `right = mid;` - Narrow search to left half including mid
- **R7 (Update left boundary)**: `left = mid + 1;` - Narrow search to right half excluding mid
- **R8 (Return result)**: `return nums[left];` - Return the minimum element found

### Complexity Analysis
- **Time Complexity**: O(log n)
  - The algorithm uses binary search, which divides the search space in half at each step
  - In the worst case, we need log₂(n) steps to find the minimum element
  - Each step involves constant-time operations (comparisons and pointer updates)
  - Even with the initial checks (single element, not rotated), the overall complexity remains O(log n)
  - This is much more efficient than a linear search which would be O(n)

- **Space Complexity**: O(1)
  - We use only a constant amount of extra space regardless of input size
  - Only a few variables are needed: `left`, `right`, and `mid` pointers
  - No additional data structures are created that scale with input size
  - The algorithm works in-place without modifying the input array

### Related Problems
- **Find Minimum in Rotated Sorted Array II** (Problem 154): Similar but with duplicates allowed
- **Search in Rotated Sorted Array** (Problem 33): Search for a target value in a rotated sorted array
- **Search in Rotated Sorted Array II** (Problem 81): Search for a target value with duplicates allowed
