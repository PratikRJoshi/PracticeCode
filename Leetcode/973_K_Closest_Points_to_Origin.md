### 973. K Closest Points to Origin
### Problem Link: [K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin/)

### Intuition/Main Idea
This problem asks us to find the k closest points to the origin (0, 0) in a 2D plane. The distance of a point (x, y) from the origin is calculated using the Euclidean distance formula: sqrt(x² + y²).

There are several approaches to solve this problem:

1. **Sort all points** by their distance to the origin and return the first k points. This is simple but takes O(n log n) time.

2. **Use a max heap** of size k to keep track of the k closest points seen so far. This takes O(n log k) time.

3. **Use QuickSelect algorithm** to find the kth closest point in O(n) average time.

The max heap approach offers a good balance between simplicity and efficiency, especially when k is much smaller than n. We maintain a max heap of size k, where the point with the largest distance is at the top. When we encounter a new point, if its distance is smaller than the top of the heap, we remove the top and add the new point.

For simplicity, we can avoid calculating the square root since comparing x² + y² gives the same ordering as comparing sqrt(x² + y²).

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Calculate distance from origin | `int distance = points[i][0] * points[i][0] + points[i][1] * points[i][1];` |
| Maintain max heap of size k | `if (maxHeap.size() > k) { maxHeap.poll(); }` |
| Return k closest points | `int[][] result = new int[k][2]; for (int i = 0; i < k; i++) { result[i] = maxHeap.poll()[0]; }` |

### Final Java Code & Learning Pattern

```java
// [Pattern: Max Heap for K Smallest Elements]
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        // Max heap to store points based on distance (largest distance at top)
        PriorityQueue<int[][]> maxHeap = new PriorityQueue<>((a, b) -> {
            // Compare distances (without square root for efficiency)
            int distA = a[1][0];
            int distB = b[1][0];
            return distB - distA;
        });
        
        // Process each point
        for (int[] point : points) {
            // Calculate distance squared
            int distance = point[0] * point[0] + point[1] * point[1];
            
            // Add point and its distance to the heap
            maxHeap.offer(new int[][]{{point[0], point[1]}, {distance}});
            
            // If heap size exceeds k, remove the point with largest distance
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        
        // Extract the k closest points from the heap
        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll()[0];
        }
        
        return result;
    }
}
```

### Alternative Implementation (Using QuickSelect)

```java
// [Pattern: QuickSelect for Kth Element]
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        // Calculate distances
        int n = points.length;
        int[] distances = new int[n];
        for (int i = 0; i < n; i++) {
            distances[i] = points[i][0] * points[i][0] + points[i][1] * points[i][1];
        }
        
        // Find the kth smallest distance using QuickSelect
        int targetDistance = quickSelect(distances, 0, n - 1, k);
        
        // Collect points with distance <= targetDistance
        int[][] result = new int[k][2];
        int index = 0;
        for (int i = 0; i < n; i++) {
            int distance = points[i][0] * points[i][0] + points[i][1] * points[i][1];
            if (distance <= targetDistance && index < k) {
                result[index++] = points[i];
            }
        }
        
        return result;
    }
    
    private int quickSelect(int[] distances, int start, int end, int k) {
        if (start == end) {
            return distances[start];
        }
        
        // Choose a random pivot
        int pivotIndex = start + (int)(Math.random() * (end - start + 1));
        pivotIndex = partition(distances, start, end, pivotIndex);
        
        // The pivot is in its final sorted position
        if (pivotIndex == k - 1) {
            return distances[pivotIndex];
        } else if (pivotIndex > k - 1) {
            // Target is in the left part
            return quickSelect(distances, start, pivotIndex - 1, k);
        } else {
            // Target is in the right part
            return quickSelect(distances, pivotIndex + 1, end, k);
        }
    }
    
    private int partition(int[] distances, int start, int end, int pivotIndex) {
        int pivotValue = distances[pivotIndex];
        
        // Move pivot to the end
        swap(distances, pivotIndex, end);
        
        // Move all elements smaller than pivot to the left
        int storeIndex = start;
        for (int i = start; i < end; i++) {
            if (distances[i] < pivotValue) {
                swap(distances, i, storeIndex);
                storeIndex++;
            }
        }
        
        // Move pivot to its final place
        swap(distances, storeIndex, end);
        
        return storeIndex;
    }
    
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```

### Complexity Analysis
- **Time Complexity**: 
  - Max Heap approach: $O(n \log k)$ where n is the number of points and k is the number of closest points to return.
  - QuickSelect approach: $O(n)$ average case, $O(n^2)$ worst case.
- **Space Complexity**: 
  - Max Heap approach: $O(k)$ for storing k points in the heap.
  - QuickSelect approach: $O(n)$ for storing distances.

### Similar Problems
1. **LeetCode 215: Kth Largest Element in an Array** - Find the kth largest element in an unsorted array.
2. **LeetCode 347: Top K Frequent Elements** - Find the k most frequent elements in an array.
3. **LeetCode 703: Kth Largest Element in a Stream** - Design a class to find the kth largest element in a stream.
4. **LeetCode 1046: Last Stone Weight** - Simulate a game using a max heap.
5. **LeetCode 692: Top K Frequent Words** - Find the k most frequent words in an array.
6. **LeetCode 451: Sort Characters By Frequency** - Sort characters by decreasing frequency.
7. **LeetCode 23: Merge k Sorted Lists** - Merge k sorted linked lists into one sorted list.
8. **LeetCode 378: Kth Smallest Element in a Sorted Matrix** - Find the kth smallest element in a sorted matrix.
