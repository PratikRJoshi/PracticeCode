### LC378: Kth Smallest Element in a Sorted Matrix
#### Problem Statement: [Kth Smallest Element in a Sorted Matrix](https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/)
#### Difficulty: Medium
#### Topic: Array, Binary Search, Heap (Priority Queue), Matrix

### Intuition
The problem asks for the k-th smallest element in a matrix where both rows and columns are sorted. This is different from finding the k-th distinct element; we need the k-th element if all matrix values were flattened into a sorted list.

Two main approaches work well:
1.  **Min-Heap (Similar to LC373)**: This is a direct way to keep track of the smallest elements. We can start with the first element of each row. Every time we extract the smallest element `matrix[r][c]`, we add the next element in its row, `matrix[r][c+1]`, to the heap. After `k-1` extractions, the top of the heap is our answer. This is correct but not the most optimal solution.

2.  **Binary Search on the Value Range (Optimal)**: The answer must lie between the smallest element (`matrix[0][0]`) and the largest (`matrix[n-1][n-1]`). We can binary search for the *value* of the answer.
    - For any chosen `mid` value, we can efficiently count how many elements in the matrix are less than or equal to `mid`. Let's call this `count`.
    - If `count < k`, it means the k-th element must be larger than `mid`, so we search in the range `[mid + 1, high]`. 
    - If `count >= k`, it means `mid` *could* be our answer (or the answer is smaller), so we search in `[low, mid]`. 
    - This process narrows down the range until `low` equals `high`, which will be our answer.

    The key is to count elements `<= mid` in `O(n)` time. We can do this by starting at the bottom-left corner (`row = n-1, col = 0`):
    - If `matrix[row][col] <= mid`, all elements in that column above the current row are also `<= mid`. We add `row + 1` to our count and move to the next column (`col++`).
    - If `matrix[row][col] > mid`, the current element is too large, so we move to the row above (`row--`).

### Java Reference Implementation (Binary Search)
```java
import java.util.*;

class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int low = matrix[0][0]; // [R0]
        int high = matrix[n - 1][n - 1]; // [R0]

        while (low < high) { // [R1]
            int mid = low + (high - low) / 2;
            int count = countLessOrEqual(matrix, mid); // [R2]

            if (count < k) {
                low = mid + 1; // [R3]
            } else {
                high = mid; // [R4]
            }
        }

        return low; // [R5]
    }

    // Counts elements in the matrix less than or equal to 'value'.
    private int countLessOrEqual(int[][] matrix, int value) {
        int count = 0;
        int n = matrix.length;
        int row = n - 1; // Start from bottom-left
        int col = 0;

        while (row >= 0 && col < n) {
            if (matrix[row][col] <= value) {
                count += row + 1; // All elements in this column up to 'row' are <= value
                col++; // Move to the next column
            } else {
                row--; // Current element is too large, move up
            }
        }
        return count;
    }
}
```

### Requirement → Code Mapping (Binary Search)
- **R0 (Define Search Range)**: `low = matrix[0][0]; high = matrix[n - 1][n - 1];` sets the initial boundaries for the binary search to the min and max values in the matrix.
- **R1 (Binary Search Loop)**: `while (low < high)` performs the search until the range collapses to a single value.
- **R2 (Count Elements)**: `int count = countLessOrEqual(matrix, mid);` calls the helper to find how many elements are less than or equal to the current `mid` value.
- **R3 (Adjust Lower Bound)**: `if (count < k) { low = mid + 1; }` means the target must be in the upper half of the value range.
- **R4 (Adjust Upper Bound)**: `else { high = mid; }` means `mid` is a potential answer, so we try to find an even smaller value in the lower half.
- **R5 (Return Result)**: `return low;` is the final value where the search range converges.

### Complexity (Binary Search)
- **Time Complexity**: `O(n log(max - min))`, where `max` and `min` are the largest and smallest values in the matrix. The binary search performs `log(max - min)` iterations, and in each iteration, the `countLessOrEqual` function takes `O(n)` time.
- **Space Complexity**: `O(1)`, as the algorithm uses only a few variables and does not require any extra data structures.

---

### Java Reference Implementation (Min-Heap)
```java
import java.util.*;

class SolutionHeap {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        // Min-heap stores {value, row, col}
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // Seed the heap with the first element of each row
        for (int r = 0; r < Math.min(n, k); r++) {
            minHeap.offer(new int[]{matrix[r][0], r, 0});
        }

        int result = -1;
        for (int i = 0; i < k; i++) {
            int[] top = minHeap.poll();
            int r = top[1];
            int c = top[2];
            result = top[0];

            if (c + 1 < n) {
                minHeap.offer(new int[]{matrix[r][c + 1], r, c + 1});
            }
        }

        return result;
    }
}
```

### Complexity (Min-Heap)
- **Time Complexity**: `O(k log n)`. The heap size can go up to `n`. We perform `k` pop/push operations.
- **Space Complexity**: `O(n)` for the heap.
