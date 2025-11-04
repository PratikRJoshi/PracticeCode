### 300. Longest Increasing Subsequence
### Problem Link: [Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/)
### Intuition
This problem asks us to find the length of the longest subsequence of an array where all elements are in strictly increasing order. A subsequence is a sequence that can be derived from another sequence by deleting some or no elements without changing the order of the remaining elements.

The key insight is to use dynamic programming. For each element, we consider all previous elements and extend the longest increasing subsequence if possible. We maintain an array `dp` where `dp[i]` represents the length of the longest increasing subsequence ending at index `i`.

### Java Reference Implementation
```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) { // [R0] Handle edge cases
            return 0;
        }
        
        int n = nums.length;
        int[] dp = new int[n]; // [R1] Initialize DP array
        Arrays.fill(dp, 1); // [R2] Each element by itself forms a subsequence of length 1
        
        int maxLength = 1; // [R3] Initialize maximum length
        
        for (int i = 1; i < n; i++) { // [R4] Iterate through the array
            for (int j = 0; j < i; j++) { // [R5] Check all previous elements
                if (nums[i] > nums[j]) { // [R6] If current element is greater than previous element
                    dp[i] = Math.max(dp[i], dp[j] + 1); // [R7] Update LIS length at current position
                }
            }
            maxLength = Math.max(maxLength, dp[i]); // [R8] Update maximum LIS length
        }
        
        return maxLength; // [R9] Return the maximum length
    }
}
```

### Alternative Implementation (Binary Search - O(n log n))
The binary search approach is much more efficient but less intuitive than the dynamic programming solution. Here's how to understand it:

1. **What the `tails` array represents**:
    - `tails[i]` is the smallest value that can appear at the end of an increasing subsequence of length `i+1`.
    - For example, if `tails[2] = 7`, it means the smallest possible value at the end of any increasing subsequence of length 3 is 7.

2. **Why this works**:
    - If we have increasing subsequences of lengths 1 to k, and their tails (last elements) are strictly increasing, then:
        - For any new element `num`, if it's larger than all tails, we can extend the longest subsequence.
        - If it's smaller than some tails, we can potentially make some existing subsequences more optimal by having a smaller tail value.

3. **The algorithm step by step**:
    - For each number in the array:
        - Use binary search to find the position where this number should be placed in the `tails` array.
        - If the number is larger than all tails, append it (creating a longer subsequence).
        - Otherwise, replace the smallest tail that is greater than or equal to this number.

4. **Example walkthrough**:
    - For the array `[10, 9, 2, 5, 3, 7, 101, 18]`:
        - Start with empty `tails` array, `size = 0`
        - Process 10: `tails = [10]`, `size = 1`
        - Process 9: Replace 10 with 9, `tails = [9]`, `size = 1`
        - Process 2: Replace 9 with 2, `tails = [2]`, `size = 1`
        - Process 5: 5 > 2, so append, `tails = [2, 5]`, `size = 2`
        - Process 3: 3 > 2 but < 5, so replace 5, `tails = [2, 3]`, `size = 2`
        - Process 7: 7 > 3, so append, `tails = [2, 3, 7]`, `size = 3`
        - Process 101: 101 > 7, so append, `tails = [2, 3, 7, 101]`, `size = 4`
        - Process 18: 18 < 101, so replace 101, `tails = [2, 3, 7, 18]`, `size = 4`
        - Final answer: `size = 4`

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        List<Integer> result = new ArrayList<>();  // [BS0] Store the tails of increasing subsequences

        for(int n : nums) {  // [BS1] Process each number in the array
            // [BS2] Find the position where this number should be inserted or replaced
            int pos = binarySearch(n, result);
            
            if(pos < result.size()) {  // [BS3] If position is within current result size
                result.set(pos, n);    // [BS4] Replace the existing element (optimize the subsequence)
            } else {
                result.add(n);         // [BS5] Append to create a longer subsequence
            }
        }

        return result.size();  // [BS6] The size of result is the length of LIS
    }

    /**
     * Binary search to find the position where num should be inserted
     * Returns the index of the first element >= num, or result.size() if all elements < num
     */
    private int binarySearch(int num, List<Integer> result) {
        int left = 0, right = result.size() - 1;  // [BS7] Initialize search boundaries

        while(left <= right) {  // [BS8] Standard binary search loop
            int mid = left + (right - left) / 2;  // [BS9] Calculate middle index safely
            
            if(result.get(mid) == num) {  // [BS10] Found exact match
                return mid;
            } else if(result.get(mid) < num) {  // [BS11] Target is in the right half
                left = mid + 1;
            } else {  // [BS12] Target is in the left half
                right = mid - 1;
            }
        }

        return left;  // [BS13] Return insertion position (first element >= num)
    }
}
```

/** 
Time Complexity: O(nlogn) - We process n elements, each requiring O(log n) time for binary search
Space Complexity: O(n) - In worst case, the result list can grow to size n
*/
