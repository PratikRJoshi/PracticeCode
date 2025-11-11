### 703. Kth Largest Element in a Stream
### Problem Link: [Kth Largest Element in a Stream](https://leetcode.com/problems/kth-largest-element-in-a-stream/)

### Intuition/Main Idea
This problem asks us to design a class that finds the kth largest element in a stream of numbers. The key insight is to maintain a min-heap of size k, which will always have the k largest elements seen so far, with the kth largest at the top of the heap.

When we initialize the class, we add all elements from the input array to the heap. If the heap size exceeds k, we remove the smallest element (the top of the min-heap). This ensures that the heap always contains the k largest elements seen so far.

When we add a new element to the stream, we add it to the heap and then remove the smallest element if the heap size exceeds k. This way, the top of the heap will always be the kth largest element in the stream.

This approach is efficient because:
1. Heap operations (add and remove) are O(log k) time complexity
2. We only need to store k elements, regardless of how many elements we've seen

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Initialize with k and nums | `public KthLargest(int k, int[] nums)` |
| Maintain min-heap of size k | `if (minHeap.size() > k) { minHeap.poll(); }` |
| Add new value to stream | `public int add(int val)` |
| Return kth largest element | `return minHeap.peek();` |

### Final Java Code & Learning Pattern

```java
// [Pattern: Min-Heap for K Largest Elements]
class KthLargest {
    private final int k;
    private final PriorityQueue<Integer> minHeap;
    
    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.minHeap = new PriorityQueue<>();
        
        // Add all elements from nums to the heap
        for (int num : nums) {
            add(num);
        }
    }
    
    public int add(int val) {
        // Add the new value to the heap
        minHeap.offer(val);
        
        // If heap size exceeds k, remove the smallest element
        if (minHeap.size() > k) {
            minHeap.poll();
        }
        
        // The top of the heap is the kth largest element
        return minHeap.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
```

### Alternative Implementation (Using a TreeSet for Duplicates)

```java
// [Pattern: TreeSet for K Largest Elements with Duplicates]
class KthLargest {
    private final int k;
    private final TreeMap<Integer, Integer> valueCount;
    private int size;
    
    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.valueCount = new TreeMap<>();
        this.size = 0;
        
        for (int num : nums) {
            add(num);
        }
    }
    
    public int add(int val) {
        // Add the new value to the TreeMap
        valueCount.put(val, valueCount.getOrDefault(val, 0) + 1);
        size++;
        
        // Remove smallest elements if size exceeds k
        while (size > k) {
            int smallest = valueCount.firstKey();
            int count = valueCount.get(smallest);
            
            if (count > 1) {
                valueCount.put(smallest, count - 1);
            } else {
                valueCount.remove(smallest);
            }
            
            size--;
        }
        
        // Return the kth largest element
        return valueCount.firstKey();
    }
}
```

### Complexity Analysis
- **Time Complexity**: 
  - Initialization: O(n log k) where n is the length of the input array, as we perform n heap operations each taking O(log k) time.
  - Add operation: O(log k) for heap insertion and potential removal.
- **Space Complexity**: O(k) as we only store k elements in the heap.

### Similar Problems
1. **LeetCode 215: Kth Largest Element in an Array** - Find the kth largest element in an unsorted array.
2. **LeetCode 347: Top K Frequent Elements** - Find the k most frequent elements in an array.
3. **LeetCode 973: K Closest Points to Origin** - Find the k closest points to the origin.
4. **LeetCode 295: Find Median from Data Stream** - Find the median from a data stream.
5. **LeetCode 1046: Last Stone Weight** - Simulate a game using a max heap.
6. **LeetCode 692: Top K Frequent Words** - Find the k most frequent words in an array.
7. **LeetCode 451: Sort Characters By Frequency** - Sort characters by decreasing frequency.
8. **LeetCode 239: Sliding Window Maximum** - Find the maximum element in each sliding window.
