### 4. Median of Two Sorted Arrays
### Problem Link: [Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/)
### Intuition
This problem asks us to find the median of two sorted arrays. The key constraint is that the solution should have a time complexity of O(log(m+n)), which suggests a binary search approach.

The naive approach would be to merge the two arrays and find the median, but that would take O(m+n) time. To achieve O(log(m+n)), we need to use binary search.

The key insight is to reformulate the problem: finding the median is equivalent to finding the (m+n)/2 th element in the merged array. We can use binary search to find the correct partition of the two arrays such that:
1. The left half of the partition has exactly (m+n)/2 elements
2. Every element in the left half is less than or equal to every element in the right half

### Java Reference Implementation
```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // [R0] Ensure nums1 is the smaller array for simplicity
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int x = nums1.length;
        int y = nums2.length;
        
        // [R1] Define the search range for the partition in nums1
        int low = 0;
        int high = x;
        
        while (low <= high) {
            // [R2] Calculate partition positions
            int partitionX = (low + high) / 2;
            int partitionY = (x + y + 1) / 2 - partitionX;
            
            // [R3] Get the elements around the partitions
            int maxX = (partitionX == 0) ? Integer.MIN_VALUE : nums1[partitionX - 1];
            int maxY = (partitionY == 0) ? Integer.MIN_VALUE : nums2[partitionY - 1];
            
            int minX = (partitionX == x) ? Integer.MAX_VALUE : nums1[partitionX];
            int minY = (partitionY == y) ? Integer.MAX_VALUE : nums2[partitionY];
            
            // [R4] Check if we've found the correct partition
            if (maxX <= minY && maxY <= minX) {
                // [R5] Calculate the median based on odd or even total length
                if ((x + y) % 2 == 0) {
                    return (Math.max(maxX, maxY) + Math.min(minX, minY)) / 2.0;
                } else {
                    return Math.max(maxX, maxY);
                }
            } 
            // [R6] Adjust the search range
            else if (maxX > minY) {
                high = partitionX - 1;
            } else {
                low = partitionX + 1;
            }
        }
        
        // [R7] If we reach here, the input arrays are not sorted
        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}
```

### Alternative Implementation (Merge and Find)
```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] merged = new int[m + n];
        
        // Merge the arrays
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) {
            if (nums1[i] <= nums2[j]) {
                merged[k++] = nums1[i++];
            } else {
                merged[k++] = nums2[j++];
            }
        }
        
        // Add remaining elements
        while (i < m) {
            merged[k++] = nums1[i++];
        }
        while (j < n) {
            merged[k++] = nums2[j++];
        }
        
        // Find the median
        int mid = (m + n) / 2;
        if ((m + n) % 2 == 0) {
            return (merged[mid - 1] + merged[mid]) / 2.0;
        } else {
            return merged[mid];
        }
    }
}
```

### Understanding the Algorithm and Binary Search

1. **Problem Reformulation:**
   - Finding the median is equivalent to finding the correct partition of the two arrays
   - The partition should divide the combined array into two equal halves
   - Every element in the left half should be less than or equal to every element in the right half

2. **Binary Search Approach:**
   - We perform binary search on the smaller array (nums1) to find the correct partition
   - For each partition in nums1, we calculate the corresponding partition in nums2
   - We check if the partition is correct by comparing the boundary elements
   - If not, we adjust the search range and continue

3. **Partition Correctness Conditions:**
   - Let maxX be the last element in the left partition of nums1
   - Let maxY be the last element in the left partition of nums2
   - Let minX be the first element in the right partition of nums1
   - Let minY be the first element in the right partition of nums2
   - The partition is correct if maxX ≤ minY and maxY ≤ minX

4. **Median Calculation:**
   - If the total length is odd, the median is the maximum of maxX and maxY
   - If the total length is even, the median is the average of max(maxX, maxY) and min(minX, minY)

5. **Edge Cases:**
   - Empty arrays: Handle by using MIN_VALUE and MAX_VALUE as placeholders
   - Arrays of different sizes: Always perform binary search on the smaller array
   - Single element arrays: Correctly handled by the partition logic

### Requirement → Code Mapping
- **R0 (Ensure smaller array)**: Swap arrays if nums1 is larger than nums2
- **R1 (Define search range)**: Set the binary search range for the partition in nums1
- **R2 (Calculate partitions)**: Determine the partition positions in both arrays
- **R3 (Get boundary elements)**: Find the elements around the partitions
- **R4 (Check partition)**: Verify if the current partition is correct
- **R5 (Calculate median)**: Compute the median based on the total length
- **R6 (Adjust search)**: Update the search range based on the comparison
- **R7 (Handle errors)**: Throw an exception if the input is invalid

### Complexity Analysis
- **Time Complexity**: O(log(min(m, n)))
  - We perform binary search on the smaller array
  - Each step of binary search takes O(1) time
  - Overall: O(log(min(m, n)))

- **Space Complexity**: O(1)
  - We use only a constant amount of extra space regardless of input size

### Related Problems
- **Kth Smallest Element in a Sorted Matrix** (Problem 378): Similar binary search approach
- **Find K-th Smallest Pair Distance** (Problem 719): Uses binary search on the result space
- **Median of a Row Wise Sorted Matrix** (Not a LeetCode problem, but related concept)
