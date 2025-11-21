# Maximum Subsequence Score

## Problem Description

**Problem Link:** [Maximum Subsequence Score](https://leetcode.com/problems/maximum-subsequence-score/)

You are given two **0-indexed** integer arrays `nums1` and `nums2` of equal length `n` and a positive integer `k`. You must choose a **subsequence** of indices from `nums1` of length `k`.

For chosen indices `i0, i1, ..., ik-1`, your **score** is defined as:

- The sum of the selected elements from `nums1` multiplied by the **minimum** of the selected elements from `nums2`.

Return *the **maximum** possible score*.

A **subsequence** of indices of an array is a set that can be derived from the array by deleting some or no elements without changing the order of the remaining elements.

**Example 1:**
```
Input: nums1 = [1,3,3,2], nums2 = [2,1,3,4], k = 3
Output: 12
Explanation: The four possible subsequence scores are:
- We choose the indices 0, 1, 2 with score = (1+3+3) * min(2,1,3) = 7 * 1 = 7.
- We choose the indices 0, 1, 3 with score = (1+3+2) * min(2,1,4) = 6 * 1 = 6.
- We choose the indices 0, 2, 3 with score = (1+3+2) * min(2,3,4) = 6 * 2 = 12.
- We choose the indices 1, 2, 3 with score = (3+3+2) * min(1,3,4) = 8 * 1 = 8.
Therefore we return 12.
```

**Example 2:**
```
Input: nums1 = [4,2,3,1,1], nums2 = [7,5,10,9,6], k = 1
Output: 30
Explanation: Choosing index 2 is optimal: nums1[2] * nums2[2] = 3 * 10 = 30 is the maximum possible score.
```

**Constraints:**
- `n == nums1.length == nums2.length`
- `1 <= n <= 10^5`
- `1 <= nums1[i], nums2[j] <= 10^5`
- `1 <= k <= n`

## Intuition/Main Idea

We need to maximize `(sum of nums1) * (min of nums2)` for k chosen indices.

**Core Algorithm:**
- Sort pairs by nums2 in descending order
- Use a min-heap to maintain k largest nums1 values
- For each position, calculate score with current min nums2
- Track maximum score

**Why sorting by nums2:** If we fix nums2[i] as the minimum, we want to maximize sum of nums1 from indices with nums2 >= nums2[i]. Sorting helps us process candidates efficiently.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Choose k indices | Heap to maintain k largest - Lines 10-25 |
| Maximize score | Score calculation - Lines 20-22 |
| Handle minimum nums2 | Sort by nums2 descending - Line 8 |
| Track maximum | Max tracking - Line 22 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public long maxScore(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        
        // Create pairs and sort by nums2 in descending order
        // This allows us to fix nums2[i] as minimum and maximize nums1 sum
        int[][] pairs = new int[n][2];
        for (int i = 0; i < n; i++) {
            pairs[i] = new int[]{nums1[i], nums2[i]};
        }
        Arrays.sort(pairs, (a, b) -> b[1] - a[1]); // Sort by nums2 descending
        
        // Min-heap to maintain k largest nums1 values
        // When we have k elements, smallest is at top
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        long sum = 0;
        long maxScore = 0;
        
        // Process each pair
        for (int[] pair : pairs) {
            int num1 = pair[0];
            int num2 = pair[1];
            
            // Add current nums1 to heap and sum
            minHeap.offer(num1);
            sum += num1;
            
            // If we have more than k elements, remove smallest
            // This maintains k largest elements
            if (minHeap.size() > k) {
                sum -= minHeap.poll();
            }
            
            // If we have exactly k elements, calculate score
            // Current num2 is the minimum (since sorted descending)
            if (minHeap.size() == k) {
                maxScore = Math.max(maxScore, sum * num2);
            }
        }
        
        return maxScore;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n \log n)$ where $n$ is the array length. Sorting takes $O(n \log n)$, and heap operations take $O(n \log k)$.

**Space Complexity:** $O(n)$ for pairs array and $O(k)$ for heap, total $O(n)$.

## Similar Problems

- [IPO](https://leetcode.com/problems/ipo/) - Similar heap + sorting pattern
- [Maximum Performance of a Team](https://leetcode.com/problems/maximum-performance-of-a-team/) - Similar optimization
- [K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin/) - Heap for top k

