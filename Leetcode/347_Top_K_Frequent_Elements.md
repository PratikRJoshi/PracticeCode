### 347. Top K Frequent Elements
### Problem Link: [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/)
### Intuition
This problem asks us to find the k most frequent elements in an array. The key insight is to count the frequency of each element and then select the k elements with the highest frequencies.

There are several approaches to solve this problem:
1. Use a HashMap to count frequencies, then use a PriorityQueue (min-heap) to keep track of the k most frequent elements
2. Use a HashMap to count frequencies, then use a bucket sort approach
3. Use a HashMap to count frequencies, then sort the elements by frequency

The heap approach is efficient and intuitive, while the bucket sort approach can achieve linear time complexity.

### Java Reference Implementation (Heap Approach)
```java
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) { // [R0] Handle edge cases
            return new int[0];
        }
        
        // [R1] Count the frequency of each element
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // [R2] Use a min-heap to keep track of the k most frequent elements
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
            (a, b) -> frequencyMap.get(a) - frequencyMap.get(b)
        );
        
        // [R3] Add elements to the heap
        for (int num : frequencyMap.keySet()) {
            minHeap.add(num);
            
            // [R4] If heap size exceeds k, remove the element with the lowest frequency
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // [R5] Build the result array
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = minHeap.poll(); // [R6] Extract elements from the heap
        }
        
        return result; // [R7] Return the k most frequent elements
    }
}
```

### Alternative Implementation (Bucket Sort Approach)
```java
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }
        
        // Count the frequency of each element
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // Create buckets where bucket[i] contains elements that appear i times
        List<Integer>[] buckets = new ArrayList[nums.length + 1];
        for (int num : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(num);
            if (buckets[frequency] == null) {
                buckets[frequency] = new ArrayList<>();
            }
            buckets[frequency].add(num);
        }
        
        // Build the result array by taking elements from the highest frequency buckets
        int[] result = new int[k];
        int index = 0;
        
        for (int i = buckets.length - 1; i >= 0 && index < k; i--) {
            if (buckets[i] != null) {
                for (int num : buckets[i]) {
                    result[index++] = num;
                    if (index == k) {
                        break;
                    }
                }
            }
        }
        
        return result;
    }
}
```

### Understanding the Algorithm and Boundary Checks

1. **Heap Approach:**
   - We use a HashMap to count the frequency of each element in O(n) time
   - We use a min-heap of size k to keep track of the k most frequent elements
   - When the heap size exceeds k, we remove the element with the lowest frequency
   - This ensures that the heap always contains the k most frequent elements
   - Time complexity: O(n log k), which is efficient when k is small compared to n

2. **Min-Heap vs. Max-Heap:**
   - We use a min-heap (not a max-heap) because we want to remove the element with the lowest frequency when the heap size exceeds k
   - The heap will eventually contain the k elements with the highest frequencies
   - We extract elements from the heap in reverse order to get them in descending order of frequency

3. **Bucket Sort Approach:**
   - After counting frequencies, we create buckets where bucket[i] contains elements that appear i times
   - We then iterate through the buckets from highest frequency to lowest
   - This approach has O(n) time complexity, which is better than the heap approach
   - However, it requires more space (O(n) for the buckets)

4. **Edge Cases:**
   - Empty array: Return an empty array
   - k <= 0: Return an empty array (invalid input)
   - k > distinct elements: Return all distinct elements (sorted by frequency)

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (nums == null || nums.length == 0 || k <= 0) { return new int[0]; }` - Return empty array for invalid inputs
- **R1 (Count frequencies)**: Use HashMap to count the frequency of each element
- **R2 (Create min-heap)**: Create a min-heap ordered by frequency
- **R3 (Add elements to heap)**: Add each element to the heap
- **R4 (Maintain heap size)**: Remove the element with the lowest frequency when heap size exceeds k
- **R5 (Build result array)**: Create an array to store the k most frequent elements
- **R6 (Extract elements)**: Extract elements from the heap in reverse order
- **R7 (Return result)**: Return the array containing the k most frequent elements

### Complexity Analysis
- **Time Complexity**: 
  - Heap approach: O(n log k)
    - Building the frequency map: O(n)
    - Adding elements to the heap: O(n log k) - we perform at most log k operations for each of the n elements
    - Extracting elements from the heap: O(k log k)
    - Overall: O(n log k) since n > k
  
  - Bucket sort approach: O(n)
    - Building the frequency map: O(n)
    - Creating the buckets: O(n)
    - Building the result array: O(n) in the worst case
    - Overall: O(n)

- **Space Complexity**: O(n)
  - Frequency map: O(n) in the worst case (all distinct elements)
  - Heap approach: O(k) for the heap
  - Bucket sort approach: O(n) for the buckets
  - Overall: O(n)

### Related Problems
- **Top K Frequent Words** (Problem 692): Similar but with strings and additional sorting requirements
- **Sort Characters By Frequency** (Problem 451): Sort characters in a string by decreasing frequency
- **Kth Largest Element in an Array** (Problem 215): Find the kth largest element in an unsorted array
