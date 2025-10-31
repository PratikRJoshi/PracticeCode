### Problem Statement
[373. Find K Pairs with Smallest Sums](https://leetcode.com/problems/find-k-pairs-with-smallest-sums/)

### Intuition
- **Problem as a Matrix**: Imagine a conceptual matrix where `matrix[i][j] = nums1[i] + nums2[j]`. Since `nums1` and `nums2` are sorted, each row and column of this matrix is also sorted. The problem is to find the `k` smallest values in this matrix.
- **Inefficiency of Brute Force**: Generating all `N*M` pairs and sorting them is too slow (`O(N*M log(N*M))`) and memory-intensive.
- **Heap-Based Search**: A min-heap is perfect for this. We can explore the pairs in increasing order of their sums. A clean strategy is to initially add pairs `(nums1[i], nums2[0])` for the first `k` elements of `nums1`. When we extract a pair `(nums1[i], nums2[j])`, we only need to add the next element in the second array, `(nums1[i], nums2[j+1])`, if it exists. This naturally explores the matrix without adding duplicate pairs.

### Java Reference Implementation
```java
import java.util.*;

class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>(); // [R0]
        if (nums1.length == 0 || nums2.length == 0 || k == 0) {
            return result;
        }

        // Min-heap to store pairs, ordered by sum. Stores [num1, num2, index_in_nums2]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> (a[0] + a[1]) - (b[0] + b[1])); // [R1]

        // Initially, add the first k pairs with the first element of nums2.
        for (int i = 0; i < nums1.length && i < k; i++) { // [R2]
            minHeap.offer(new int[]{nums1[i], nums2[0], 0});
        }

        while (k-- > 0 && !minHeap.isEmpty()) { // [R3]
            int[] current = minHeap.poll(); // [R4]
            int num1 = current[0];
            int num2 = current[1];
            int nums2Idx = current[2];

            result.add(Arrays.asList(num1, num2)); // [R5]

            // If there's a next element in nums2 for the current num1, add the new pair to the heap.
            if (nums2Idx + 1 < nums2.length) { // [R6]
                minHeap.offer(new int[]{num1, nums2[nums2Idx + 1], nums2Idx + 1});
            }
        }

        return result;
    }
}
```

### Requirement → Code Mapping
- **R0 (Initialize result container)**: `List<List<Integer>> result = new ArrayList<>();`
- **R1 (Create min-heap for pairs)**: `PriorityQueue<int[]> min Heap = new PriorityQueue<>(...);` The lambda sorts pairs by their sum.
- **R2 (Seed the heap)**: The initial `for` loop adds starting pairs `(nums1[i], nums2[0])`.
- **R3 (Extract k smallest pairs)**: `while (k-- > 0 && !minHeap.isEmpty())` ensures we collect `k` pairs.
- **R4 (Get the current smallest pair)**: `minHeap.poll()` retrieves the pair with the globally smallest sum.
- **R5 (Add pair to results)**: `result.add(Arrays.asList(num1, num2));` stores the found pair.
- **R6 (Add next candidate to heap)**: `if (nums2Idx + 1 < nums2.length)` adds the next pair from the same `nums1` row.

### Complexity
- **Time Complexity**: `O(k log k)`. We perform `k` extractions and at most `k` insertions into a heap of size up to `k`.
- **Space Complexity**: `O(k)`. The heap stores at most `k` elements.