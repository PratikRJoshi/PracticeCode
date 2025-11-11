### 1046. Last Stone Weight
### Problem Link: [Last Stone Weight](https://leetcode.com/problems/last-stone-weight/)

### Intuition/Main Idea
This problem simulates a game where we repeatedly take the two heaviest stones, smash them together, and potentially get a new stone with a weight equal to the difference between the two original stones. The key insight is that we always need to find and remove the two largest stones, which makes a max heap (priority queue) the perfect data structure for this problem.

The algorithm is straightforward:
1. Add all stones to a max heap
2. While there are at least two stones in the heap:
   - Remove the two heaviest stones
   - If they're not equal, add a new stone with weight equal to their difference
3. Return the remaining stone's weight, or 0 if the heap is empty

This approach efficiently handles the repeated process of finding the two largest stones without having to sort the array multiple times.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Create max heap | `PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);` |
| Add stones to heap | `for (int stone : stones) { maxHeap.offer(stone); }` |
| Remove two heaviest stones | `int stone1 = maxHeap.poll(); int stone2 = maxHeap.poll();` |
| Add difference if not equal | `if (stone1 != stone2) { maxHeap.offer(stone1 - stone2); }` |
| Return remaining stone or 0 | `return maxHeap.isEmpty() ? 0 : maxHeap.peek();` |

### Final Java Code & Learning Pattern

```java
// [Pattern: Priority Queue (Max Heap)]
class Solution {
    public int lastStoneWeight(int[] stones) {
        // Create a max heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        
        // Add all stones to the heap
        for (int stone : stones) {
            maxHeap.offer(stone);
        }
        
        // Process stones until 0 or 1 stone remains
        while (maxHeap.size() > 1) {
            // Remove the two heaviest stones
            int stone1 = maxHeap.poll();
            int stone2 = maxHeap.poll();
            
            // If they're not equal, add the difference back to the heap
            if (stone1 != stone2) {
                maxHeap.offer(stone1 - stone2);
            }
        }
        
        // Return the weight of the last stone, or 0 if no stones remain
        return maxHeap.isEmpty() ? 0 : maxHeap.poll();
    }
}
```

### Alternative Implementation (Using a Sorted Array)

```java
// [Pattern: Sorting and Simulation]
class Solution {
    public int lastStoneWeight(int[] stones) {
        List<Integer> stoneList = new ArrayList<>();
        for (int stone : stones) {
            stoneList.add(stone);
        }
        
        while (stoneList.size() > 1) {
            // Sort in descending order
            Collections.sort(stoneList, Collections.reverseOrder());
            
            // Get the two heaviest stones
            int stone1 = stoneList.remove(0);
            int stone2 = stoneList.remove(0);
            
            // If they're not equal, add the difference back
            if (stone1 != stone2) {
                stoneList.add(stone1 - stone2);
            }
        }
        
        return stoneList.isEmpty() ? 0 : stoneList.get(0);
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(n \log n)$ where n is the number of stones. Adding all stones to the heap takes O(n log n) time, and each subsequent operation takes O(log n) time. In the worst case, we perform O(n) such operations.
- **Space Complexity**: $O(n)$ for storing all stones in the heap.

For the alternative implementation:
- **Time Complexity**: $O(n^2 \log n)$ because we sort the list O(n) times, and each sort takes O(n log n) time.
- **Space Complexity**: $O(n)$ for storing the stones in the list.

### Similar Problems
1. **LeetCode 215: Kth Largest Element in an Array** - Find the kth largest element in an unsorted array.
2. **LeetCode 347: Top K Frequent Elements** - Find the k most frequent elements in an array.
3. **LeetCode 703: Kth Largest Element in a Stream** - Design a class to find the kth largest element in a stream.
4. **LeetCode 973: K Closest Points to Origin** - Find the k closest points to the origin.
5. **LeetCode 692: Top K Frequent Words** - Find the k most frequent words in an array.
6. **LeetCode 451: Sort Characters By Frequency** - Sort characters by decreasing frequency.
7. **LeetCode 23: Merge k Sorted Lists** - Merge k sorted linked lists into one sorted list.
8. **LeetCode 295: Find Median from Data Stream** - Find the median from a data stream.
