### 215. Kth Largest Element in an Array
### Problem Link: [Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/)
### Intuition
This problem asks us to find the kth largest element in an unsorted array. There are several approaches to solve this problem:

1. Sort the array and return the kth element from the end (O(n log n))
2. Use a min-heap of size k to keep track of the k largest elements (O(n log k))
3. Use the QuickSelect algorithm, which is based on the partitioning scheme of QuickSort (average O(n))

The QuickSelect algorithm is the most efficient approach for this problem. It works by selecting a pivot element and partitioning the array such that elements greater than the pivot are on one side and elements less than the pivot are on the other side. We then recursively apply this process to the appropriate partition until we find the kth largest element.

### Java Reference Implementation (QuickSelect)
```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        if(nums.length == 1){
            return nums[0];
        }

        int left = 0, right = nums.length - 1;

        while(left <= right){
            int pivot = partition(nums, left, right);

            int count = pivot - left + 1;

            if(count > k){
                right = pivot - 1;
            } else if(count < k) {
                k = k - count;
                left = pivot + 1;
            } else {
                return nums[pivot];
            }
        }

        return 0; // for invalid input;
    }

    private int partition(int[] nums, int left, int right){
        // Choose a pivot in the middle
        int pivot = left + (right - left)/2;
        int pVal = nums[pivot];

        // Move pivot temporarily to the end
        swap(nums, pivot, right);

        int l = left, r = right - 1;
        // Partition process:
        // Goal: all elements >= pivot value on the left (since we want kth largest)
        //       all elements < pivot value on the right
        while(l <= r){
            if(nums[l] >= pVal){
                l++;
            } else if(nums[r] < pVal){
                r--;
            } else {
                swap(nums, l, r);
                l++;
                r--;
            }
        }

        // Place pivot in its correct position
        /**
         1. What’s happening just before this line?

         Inside partition, we:

         1. Move the pivot element to the end (swap(nums, pivot, right))
         2. Walk from both ends (l from left, r from right-1), swapping mis-placed numbers
         3. Stop when l > r

         At that point:
         1. All elements left of l are guaranteed to be >= pivot value
         2. All elements right of l are guaranteed to be < pivot value
         …but the pivot itself is still sitting at the end (right index), not in its correct spot.

         2. What does swap(nums, right, l) do?

         We take the pivot value sitting at right and drop it exactly into the gap at l.
         That gap (l) is the first position where all elements to its left are >= pivot,
         and all elements to its right are < pivot.
         So placing pivot at l ensures it’s exactly where it belongs in this "partial order."
         */
        swap(nums, right, l);

        // Return the final position of the pivot
        return l;
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
// T: O(n) avg, O(n2) worst
// S: O(1)
```

### Alternative Implementation (Using a Min-Heap)
```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        // Create a min-heap of size k
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
        
        // Add elements to the heap
        for (int num : nums) {
            minHeap.offer(num);
            
            // If the heap size exceeds k, remove the smallest element
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // The top of the heap is the kth largest element
        return minHeap.peek();
    }
}
```

### Alternative Implementation (Sorting)
```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        // Sort the array in ascending order
        Arrays.sort(nums);
        
        // Return the kth largest element
        return nums[nums.length - k];
    }
}
```

### Understanding the Algorithm and QuickSelect

1. **QuickSelect Algorithm:**
   - QuickSelect is a selection algorithm to find the kth smallest element in an unordered list
   - It's based on the partitioning scheme of QuickSort
   - The average time complexity is O(n), but the worst-case is O(n²)
   - Using a random pivot helps avoid the worst-case scenario

2. **Partitioning Process:**
   - Choose a pivot element
   - Rearrange the array so that elements less than the pivot are on the left, and elements greater than the pivot are on the right
   - The pivot is now in its final sorted position
   - If this position is the target index, we've found our answer
   - Otherwise, recursively apply the process to the appropriate partition

3. **Converting to 0-based Index:**
   - The kth largest element corresponds to the (n-k)th smallest element
   - We convert k to a 0-based index by calculating `targetIndex = nums.length - k`

4. **Min-Heap Approach:**
   - Maintain a min-heap of size k
   - After processing all elements, the heap contains the k largest elements
   - The smallest element in the heap (the root) is the kth largest element

5. **Sorting Approach:**
   - Sort the array in ascending order
   - Return the element at index `nums.length - k`
   - This is simple but less efficient for large arrays

### Requirement → Code Mapping
- **R0 (Convert k to index)**: `int targetIndex = nums.length - k;` - Convert to 0-based index
- **R1 (Apply QuickSelect)**: Call the QuickSelect algorithm
- **R2 (Base case)**: `if (left == right)` - Handle the base case
- **R3 (Choose random pivot)**: Select a random pivot to avoid worst-case scenarios
- **R4 (Partition array)**: Rearrange elements around the pivot
- **R5-R7 (Recursive cases)**: Determine which partition to search next
- **R8-R11 (Partitioning process)**: Implement the partitioning scheme

### Complexity Analysis
- **Time Complexity**:
  - QuickSelect approach: O(n) average case, O(n²) worst case
  - Min-Heap approach: O(n log k)
  - Sorting approach: O(n log n)

- **Space Complexity**:
  - QuickSelect approach: O(log n) average case for the recursion stack
  - Min-Heap approach: O(k) for the heap
  - Sorting approach: O(log n) or O(n) depending on the sorting algorithm

### Related Problems
- **Top K Frequent Elements** (Problem 347): Find the k most frequent elements
- **Kth Smallest Element in a Sorted Matrix** (Problem 378): Similar concept in a 2D matrix
- **Find K Closest Elements** (Problem 658): Find the k closest elements to a given value
