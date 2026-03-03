### 973. K Closest Points to Origin
### Problem Link: [K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin/)

### Problem Description
Given an array of `points` where `points[i] = [xi, yi]` represents a point on the X-Y plane and an integer `k`, return the `k` closest points to the origin `(0, 0)`.

The distance between two points on the X-Y plane is the Euclidean distance (i.e., $\sqrt{(x_1 - x_2)^2 + (y_1 - y_2)^2}$).

You may return the answer in **any order**. The answer is guaranteed to be unique (except for the order that it is in).

**Example 1:**
```
Input: points = [[1,3],[-2,2]], k = 1
Output: [[-2,2]]
Explanation:
The distance between (1, 3) and the origin is sqrt(10).
The distance between (-2, 2) and the origin is sqrt(8).
Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
We only want the closest k = 1 points from the origin, so the answer is just [[-2,2]].
```

**Example 2:**
```
Input: points = [[3,3],[5,-1],[-2,4]], k = 2
Output: [[3,3],[-2,4]]
Explanation: The answer [[-2,4],[3,3]] would also be accepted.
```

**Constraints:**
- `1 <= k <= points.length <= 10^4`
- `-10^4 <= xi, yi <= 10^4`

---

### Intuition / Main Idea

The problem asks us to find the `k` points that have the smallest Euclidean distance to the origin `(0,0)`. 
The Euclidean distance formula from the origin is $\sqrt{x^2 + y^2}$. 
*Crucial optimization:* Since we only care about relative ordering, we can ignore the expensive square root operation and just compare $x^2 + y^2$.

#### Why the Intuition Works (Max Heap Approach)
Whenever a problem asks for the "top K", "K closest", or "K smallest" elements, a Heap (Priority Queue) is the most reliable and intuitive data structure.

Wait, if we want the *closest* (smallest) distances, shouldn't we use a Min Heap?
Actually, a **Max Heap of size K** is much more efficient! 

Here is why:
If we use a Min Heap, we have to insert all $N$ elements into the heap, taking $O(N \log N)$ time, and then pop $K$ times. This is essentially just sorting the array.
Instead, we use a Max Heap and restrict its size to exactly $K$. The Max Heap will keep the **farthest** point (among the $K$ points we've seen so far) at the very top. 
As we iterate through the remaining points:
- If we find a new point that is *closer* than the farthest point in our heap (i.e., smaller than the root), we throw away the root and insert the new closer point.
- If it's farther, we just ignore it.
By the end of the iteration, the Max Heap will hold exactly the $K$ points with the smallest distances.

#### How to Derive It Step by Step
1. **Define the Comparator:** Create a Max Heap that stores `int[]` arrays (the coordinates). The comparator should calculate $x^2 + y^2$ for both points and sort them in descending order (largest distance at the top).
2. **Iterate and Add:** Loop through every point in the `points` array and add it to the Max Heap.
3. **Evict the Farthest:** Immediately after adding a point, check if the heap size exceeds `k`. If it does, `poll()` the heap. Because it's a Max Heap, this removes the point with the largest distance.
4. **Extract Results:** Once the loop finishes, the heap contains exactly the `k` closest points. Pop them out into a result array.

---

### Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| :--- | :--- |
| Calculate distance (squared) to origin | `(b[0] * b[0] + b[1] * b[1]) - (a[0] * a[0] + a[1] * a[1])` |
| Maintain Max Heap based on distance | `PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> ...);` |
| Process all points | `for (int[] point : points) { maxHeap.add(point); }` |
| Keep heap size to at most K | `if (maxHeap.size() > k) { maxHeap.poll(); }` |
| Return K closest points | `int[][] result = new int[k][2]; ... result[i] = maxHeap.poll();` |

---

### Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        // Create a Max Heap. 
        // We want the point with the MAXIMUM distance to be at the top of the heap.
        // Distance is x^2 + y^2. We compare point 'b' to point 'a' to get descending order.
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> {
            int distanceA = (a[0] * a[0]) + (a[1] * a[1]);
            int distanceB = (b[0] * b[0]) + (b[1] * b[1]);
            return Integer.compare(distanceB, distanceA);
        });
        
        // Iterate through all given points
        for (int[] point : points) {
            maxHeap.add(point);
            
            // If our heap grows larger than k, we remove the top element.
            // Since it's a Max Heap, the top element is the FARTHEST point we've seen so far.
            // By constantly throwing away the farthest points, we are left with the k closest.
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        
        // Create the result array to store the k closest points
        int[][] kClosestPoints = new int[k][2];
        
        // Extract the remaining k points from the heap
        for (int i = 0; i < k; i++) {
            kClosestPoints[i] = maxHeap.poll();
        }
        
        return kClosestPoints;
    }
}
```

---

### Alternative Approach: QuickSelect

While the Max Heap approach is $O(N \log K)$, we can optimize the average time complexity to $O(N)$ using the **QuickSelect** algorithm. 

#### Intuition for QuickSelect
QuickSelect is based on the QuickSort algorithm. Instead of sorting the entire array, we only care about partitioning the array such that the `k` smallest elements are on the left side of a pivot. We don't care if the `k` elements themselves are perfectly sorted—we just need them to be the smallest `k`.

1. **Choose a Pivot:** Pick an element (e.g., the last element in the current range) and calculate its distance.
2. **Partition:** Rearrange the array so that all points closer to the origin than the pivot are moved to the left, and all points farther are moved to the right. 
3. **Check the Pivot's Position:** 
   - If the pivot ends up exactly at index `k - 1`, we are done! The `k` elements to the left (including the pivot) are the `k` closest.
   - If the pivot is at an index greater than `k - 1`, the target elements are in the left partition. We recursively QuickSelect on the left side.
   - If the pivot is at an index less than `k - 1`, we recursively QuickSelect on the right side.

#### QuickSelect Java Code (Iterative)

```java
import java.util.Arrays;

class Solution {
    public int[][] kClosest(int[][] points, int k) {
        int left = 0;
        int right = points.length - 1;
        
        // Iterative QuickSelect
        while (left <= right) {
            // Partition the array and get the final sorted index of the pivot
            int pivotIndex = partition(points, left, right);
            
            // We want the k-th element, which is at index k - 1
            if (pivotIndex == k - 1) {
                // The k-th element is exactly where it should be.
                // Everything to its left is smaller, so the first k elements are our answer.
                break;
            } else if (pivotIndex < k - 1) {
                // We need more elements. The target is in the right partition.
                left = pivotIndex + 1;
            } else {
                // We have too many elements. The target is in the left partition.
                right = pivotIndex - 1;
            }
        }
        
        // Return a copy of the first k elements
        return Arrays.copyOfRange(points, 0, k);
    }
    
    private int partition(int[][] points, int left, int right) {
        // Use the rightmost element as the pivot
        int[] pivotPoint = points[right];
        int pivotDistance = getDistance(pivotPoint);
        
        int storeIndex = left;
        
        // Move all points strictly closer than the pivot to the left side
        for (int i = left; i < right; i++) {
            if (getDistance(points[i]) <= pivotDistance) {
                swap(points, storeIndex, i);
                storeIndex++;
            }
        }
        
        // Move the pivot to its final sorted position
        swap(points, storeIndex, right);
        
        return storeIndex;
    }
    
    private int getDistance(int[] point) {
        // Calculate squared Euclidean distance
        return (point[0] * point[0]) + (point[1] * point[1]);
    }
    
    private void swap(int[][] points, int i, int j) {
        int[] temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }
}
```

#### Complexity Analysis (QuickSelect)
- **Time Complexity:** $O(N)$ on average. In the best/average case, the partition step continually halves the search space ($N + N/2 + N/4 + ... \approx 2N$). However, in the absolute worst case (e.g., already sorted array and picking the worst pivot), it degrades to $O(N^2)$.
- **Space Complexity:** $O(1)$. Because this is an iterative implementation, there is no recursive call stack. We sort the array strictly in-place, requiring only constant extra memory for pointer variables.

---

### Complexity Analysis

- **Time Complexity:** $O(N \log K)$ where $N$ is the total number of points. We iterate through all $N$ points. For each point, we push to the heap (and potentially pop). Since we strictly maintain the heap size at $K$, the insertion and extraction operations take $O(\log K)$ time. 
- **Space Complexity:** $O(K)$ because the Max Heap stores at most $K$ points at any given time. The result array also takes $O(K)$ space.

---

### Similar Problems

- [215. Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/) - The foundational problem for learning how to use a Min Heap of size K to find the Kth largest element.
- [347. Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/) - Uses the exact same pattern of maintaining a size K heap, but the comparator is based on frequency instead of distance.
- [692. Top K Frequent Words](https://leetcode.com/problems/top-k-frequent-words/) - Similar to Top K Frequent Elements, but requires a custom comparator that handles both frequency and lexicographical string sorting.
- [703. Kth Largest Element in a Stream](https://leetcode.com/problems/kth-largest-element-in-a-stream/) - Teaches how to maintain a Min Heap of size K dynamically as a continuous stream of data arrives.
