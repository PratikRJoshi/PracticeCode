### 239. Sliding Window Maximum
### Problem Link: [Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/)

### Intuition/Main Idea
This problem asks us to find the maximum element in each sliding window of size k as it moves from left to right through an array. The key insight is that we need an efficient way to:
1. Track the maximum element in the current window
2. Remove elements that are no longer in the window
3. Add new elements as the window slides

A naive approach would be to find the maximum in each window, but that would be O(nk) time complexity. Instead, we can use a **deque** (double-ended queue) to maintain a decreasing order of elements (their indices) within the current window. The front of the deque will always contain the index of the maximum element in the current window.

The algorithm works as follows:
1. For each element, remove indices from the back of the deque if they represent smaller elements (as they can't be the maximum)
2. Remove indices from the front of the deque if they're outside the current window
3. Add the current element's index to the deque
4. Once we've processed k elements, the front of the deque will contain the index of the maximum element in the window

This approach gives us an O(n) solution because each element is processed exactly once.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Maintain decreasing deque | `while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) { deque.pollLast(); }` |
| Remove elements outside window | `if (!deque.isEmpty() && deque.peekFirst() <= i - k) { deque.pollFirst(); }` |
| Add current element | `deque.offerLast(i);` |
| Get maximum for current window | `result[i - k + 1] = nums[deque.peekFirst()];` |

### Final Java Code & Learning Pattern

```java
// [Pattern: Monotonic Deque for Sliding Window]
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }
        
        int n = nums.length;
        int[] result = new int[n - k + 1]; // Number of sliding windows
        
        // Deque to store indices of elements in decreasing order of values
        Deque<Integer> deque = new ArrayDeque<>();
        
        for (int i = 0; i < n; i++) {
            // Remove elements smaller than current element from the back
            // They can't be the maximum in any future window
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            
            // Remove elements that are outside the current window from the front
            if (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            
            // Add current element's index to the deque
            deque.offerLast(i);
            
            // If we've processed at least k elements, add the maximum to result
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        
        return result;
    }
}
```

### Alternative Implementation (Using a Max Heap)

```java
// [Pattern: Priority Queue for Sliding Window]
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        
        // Max heap to store pairs of (value, index)
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        
        for (int i = 0; i < n; i++) {
            // Add current element to the heap
            maxHeap.offer(new int[]{nums[i], i});
            
            // If we've processed at least k elements
            if (i >= k - 1) {
                // Remove elements that are outside the current window
                while (!maxHeap.isEmpty() && maxHeap.peek()[1] < i - k + 1) {
                    maxHeap.poll();
                }
                
                // The top of the heap is the maximum in the current window
                result[i - k + 1] = maxHeap.peek()[0];
            }
        }
        
        return result;
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(n)$ for the deque approach, where n is the length of the array. Each element is processed exactly once, and all deque operations are O(1).
- **Space Complexity**: $O(k)$ for the deque, which stores at most k elements (indices).

For the max heap approach:
- **Time Complexity**: $O(n \log n)$ because heap operations take O(log n) time, and in the worst case, we might need to remove multiple elements for each window.
- **Space Complexity**: $O(n)$ for the heap, which might store all elements in the worst case.

### Similar Problems
1. **LeetCode 1438: Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit** - Uses a similar deque approach.
2. **LeetCode 1425: Constrained Subsequence Sum** - Find the maximum sum of a subsequence with constraints.
3. **LeetCode 862: Shortest Subarray with Sum at Least K** - Find the shortest subarray with sum at least K.
4. **LeetCode 1696: Jump Game VI** - Maximum score by jumping through an array with constraints.
5. **LeetCode 1499: Max Value of Equation** - Find the maximum value of an equation with constraints.
6. **LeetCode 1562: Find Latest Group of Size M** - Track the latest group of consecutive 1's.
7. **LeetCode 1074: Number of Submatrices That Sum to Target** - Count submatrices with a given sum.
8. **LeetCode 480: Sliding Window Median** - Find the median in each sliding window.
