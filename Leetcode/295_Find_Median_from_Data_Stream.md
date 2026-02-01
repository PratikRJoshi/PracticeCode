### 295. Find Median from Data Stream
### Problem Link: [Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/)
### Intuition
This problem asks us to design a data structure that can efficiently insert numbers and find the median of all numbers inserted so far. The key challenge is maintaining the ability to quickly find the median as new numbers are added.

The key insight is to use two heaps:
1. A max-heap for the smaller half of the numbers
2. A min-heap for the larger half of the numbers

By maintaining these two heaps with a size difference of at most 1, we can efficiently find the median:
- If both heaps have the same size, the median is the average of the top elements from both heaps
- If one heap has one more element than the other, the median is the top element of the heap with more elements

### Java Reference Implementation
```java
class MedianFinder {
    private PriorityQueue<Integer> maxHeap; // [R0] Max heap for the smaller half
    private PriorityQueue<Integer> minHeap; // [R1] Min heap for the larger half
    
    public MedianFinder() {
        // [R2] Initialize the heaps
        maxHeap = new PriorityQueue<>((a, b) -> b - a); // Max heap
        minHeap = new PriorityQueue<>(); // Min heap
    }
    
    public void addNum(int num) {
        // [R3] Add the number to the appropriate heap
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num); // [R4] Add to the max heap (smaller half)
        } else {
            minHeap.offer(num); // [R5] Add to the min heap (larger half)
        }
        
        // [R6] Balance the heaps to maintain the size property
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll()); // [R7] Move the largest element from max heap to min heap
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll()); // [R8] Move the smallest element from min heap to max heap
        }
    }
    
    public double findMedian() {
        // [R9] If the heaps have the same size, the median is the average of the two middle elements
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else {
            // [R10] Otherwise, the median is the top element of the heap with more elements
            return maxHeap.peek();
        }
    }
}
```

### Alternative Implementation (Using a Single List with Binary Search)
```java
class MedianFinder {
    private List<Integer> nums;
    
    public MedianFinder() {
        nums = new ArrayList<>();
    }
    
    public void addNum(int num) {
        // Use binary search to find the insertion position
        int pos = Collections.binarySearch(nums, num);
        if (pos < 0) {
            pos = -(pos + 1);
        }
        nums.add(pos, num);
    }
    
    public double findMedian() {
        int size = nums.size();
        if (size % 2 == 1) {
            return nums.get(size / 2);
        } else {
            return (nums.get(size / 2 - 1) + nums.get(size / 2)) / 2.0;
        }
    }
}
```

### Understanding the Algorithm and Data Structure

1. **Two-Heap Approach:**
   - We maintain two heaps: a max-heap for the smaller half of the numbers and a min-heap for the larger half
   - The max-heap contains all numbers less than or equal to the median
   - The min-heap contains all numbers greater than the median
   - We ensure that the size difference between the two heaps is at most 1

2. **Insertion Logic:**
   - If the number is less than or equal to the max-heap's top element, add it to the max-heap
   - Otherwise, add it to the min-heap
   - After insertion, balance the heaps if necessary to maintain the size property

3. **Balancing the Heaps:**
   - If the max-heap has more than one element than the min-heap, move the largest element from the max-heap to the min-heap
   - If the min-heap has more elements than the max-heap, move the smallest element from the min-heap to the max-heap
   - This ensures that the max-heap has either the same number of elements as the min-heap or one more

### Common Bug: Why an `else` in balancing can break (NPE)
If balancing is written like this:

```java
if (maxHeap.size() - minHeap.size() > 1) {
    minHeap.offer(maxHeap.poll());
} else {
    maxHeap.offer(minHeap.poll());
}
```

The `else` runs even when the heaps are already balanced (for example right after inserting the first number):

- After `addNum(1)`: `maxHeap = [1]`, `minHeap = []`
- `maxHeap.size() - minHeap.size()` is `1` (not `> 1`)
- so the `else` runs and calls `minHeap.poll()`
- but `minHeap` is empty, so `poll()` returns `null`
- `maxHeap.offer(null)` throws a `NullPointerException`

Correct balancing must move elements only when one heap is actually too large:

```java
if (maxHeap.size() > minHeap.size() + 1) {
    minHeap.offer(maxHeap.poll());
} else if (minHeap.size() > maxHeap.size()) {
    maxHeap.offer(minHeap.poll());
}
```

### Minor improvement: safer max-heap comparator
Instead of `(a, b) -> b - a` (can overflow), prefer:

```java
maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
```

4. **Finding the Median:**
   - If both heaps have the same size, the median is the average of the top elements from both heaps
   - If the max-heap has one more element than the min-heap, the median is the top element of the max-heap

5. **Alternative Approach:**
   - We can also use a sorted list with binary search for insertion
   - This approach is simpler but less efficient for large datasets (O(n) insertion vs O(log n) for heaps)

### Requirement → Code Mapping
- **R0 (Max heap)**: `PriorityQueue<Integer> maxHeap` - Store the smaller half of the numbers
- **R1 (Min heap)**: `PriorityQueue<Integer> minHeap` - Store the larger half of the numbers
- **R2 (Initialize heaps)**: Create max heap and min heap with appropriate comparators
- **R3 (Add number)**: Decide which heap to add the number to
- **R4 (Add to max heap)**: `maxHeap.offer(num)` - Add to the smaller half
- **R5 (Add to min heap)**: `minHeap.offer(num)` - Add to the larger half
- **R6 (Balance heaps)**: Ensure the size difference between heaps is at most 1
- **R7 (Move from max to min)**: `minHeap.offer(maxHeap.poll())` - Balance when max heap is too large
- **R8 (Move from min to max)**: `maxHeap.offer(minHeap.poll())` - Balance when min heap is too large
- **R9 (Find median - even)**: Return average of top elements when heaps have equal size
- **R10 (Find median - odd)**: Return top element of max heap when it has one more element

### Complexity Analysis
- **Time Complexity**:
  - `addNum`: O(log n) - Heap insertion takes logarithmic time
  - `findMedian`: O(1) - Accessing the top elements of the heaps takes constant time
  
- **Space Complexity**: O(n)
  - We store all n elements across the two heaps
  - Each heap can contain up to n/2 elements in the worst case

### Related Problems
- **Sliding Window Median** (Problem 480): Find the median in a sliding window of a stream
- **Kth Largest Element in a Stream** (Problem 703): Similar concept but only tracking the kth largest
- **Data Stream as Disjoint Intervals** (Problem 352): Another data stream problem with different requirements
