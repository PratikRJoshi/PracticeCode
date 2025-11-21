# [2336. Smallest Number in Infinite Set](https://leetcode.com/problems/smallest-number-in-infinite-set/)

You have a set which contains all positive integers `[1, 2, 3, 4, 5, ...]`.

Implement the `SmallestInfiniteSet` class:

- `SmallestInfiniteSet()` Initializes the **SmallestInfiniteSet** object to contain all positive integers.
- `int popSmallest()` Removes and returns the smallest integer contained in the infinite set.
- `void addBack(int num)` Adds a positive integer `num` back into the infinite set, if it is not already in the infinite set.

**Example 1:**

```
Input
["SmallestInfiniteSet", "addBack", "popSmallest", "popSmallest", "popSmallest", "addBack", "popSmallest", "popSmallest", "popSmallest"]
[[], [2], [], [], [], [1], [], [], []]
Output
[null, null, 1, 2, 3, null, 1, 4, 5]

Explanation
SmallestInfiniteSet smallestInfiniteSet = new SmallestInfiniteSet();
smallestInfiniteSet.addBack(2);    // 2 is already in the set, so no change is made.
smallestInfiniteSet.popSmallest(); // return 1, since 1 is the smallest number, and remove it from the set.
smallestInfiniteSet.popSmallest(); // return 2, and remove it from the set.
smallestInfiniteSet.popSmallest(); // return 3, and remove it from the set.
smallestInfiniteSet.addBack(1);    // 1 is added back to the set.
smallestInfiniteSet.popSmallest(); // return 1, since 1 was added back to the set and is the smallest number, and remove it from the set.
smallestInfiniteSet.popSmallest(); // return 4, and remove it from the set.
smallestInfiniteSet.popSmallest(); // return 5, and remove it from the set.
```

**Constraints:**

- `1 <= num <= 1000`
- At most `1000` calls will be made **in total** to `popSmallest` and `addBack`.

## Intuition/Main Idea:

This problem requires us to efficiently track the smallest number in an infinite set of positive integers, with the ability to remove numbers and add them back. 

A key insight is that we don't need to explicitly store all positive integers. Instead, we can keep track of:
1. The smallest number that hasn't been popped yet (which starts at 1)
2. The numbers that have been popped but then added back

For the second part, we need a data structure that allows us to efficiently find the smallest number. A min-heap (priority queue) is perfect for this.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Initialize set with all positive integers | `currentSmallest = 1;` |
| Remove and return smallest integer | `if (!addedBack.isEmpty() && addedBack.peek() < currentSmallest) { return addedBack.poll(); } else { return currentSmallest++; }` |
| Add a number back if not in set | `if (num < currentSmallest && !addedBack.contains(num)) { addedBack.add(num); }` |

## Final Java Code & Learning Pattern:

```java
class SmallestInfiniteSet {
    private int currentSmallest; // Tracks the smallest number that hasn't been popped
    private PriorityQueue<Integer> addedBack; // Min-heap for numbers that have been added back
    private HashSet<Integer> presentInHeap; // Set to check if a number is already in the heap
    
    public SmallestInfiniteSet() {
        currentSmallest = 1; // Start with 1 as the smallest
        addedBack = new PriorityQueue<>();
        presentInHeap = new HashSet<>();
    }
    
    public int popSmallest() {
        int result;
        
        // If there are numbers that have been added back, check if the smallest one
        // is smaller than currentSmallest
        if (!addedBack.isEmpty() && addedBack.peek() < currentSmallest) {
            result = addedBack.poll();
            presentInHeap.remove(result);
        } else {
            // Otherwise, return and increment currentSmallest
            result = currentSmallest++;
        }
        
        return result;
    }
    
    public void addBack(int num) {
        // Only add back if:
        // 1. The number is less than currentSmallest (meaning it's been popped)
        // 2. It's not already in the heap
        if (num < currentSmallest && !presentInHeap.contains(num)) {
            addedBack.add(num);
            presentInHeap.add(num);
        }
    }
}

/**
 * Your SmallestInfiniteSet object will be instantiated and called as such:
 * SmallestInfiniteSet obj = new SmallestInfiniteSet();
 * int param_1 = obj.popSmallest();
 * obj.addBack(num);
 */
```

This solution uses a combination of a counter, a min-heap, and a hash set:

1. `currentSmallest`: Keeps track of the smallest number that hasn't been popped yet. Initially set to 1.
2. `addedBack`: A min-heap (priority queue) that stores numbers that have been popped and then added back. This allows us to efficiently find the smallest number among those added back.
3. `presentInHeap`: A hash set to quickly check if a number is already in the heap, avoiding duplicates.

The operations work as follows:

- **popSmallest()**:
  - If there are numbers in the heap and the smallest one is less than `currentSmallest`, we return and remove that number from both the heap and the set.
  - Otherwise, we return `currentSmallest` and increment it.

- **addBack(num)**:
  - We only add a number back if it's less than `currentSmallest` (meaning it's been popped) and it's not already in the heap.
  - If these conditions are met, we add it to both the heap and the set.

The use of a hash set alongside the priority queue is an optimization to quickly check if a number is already in the heap, which would otherwise be an O(n) operation.

## Alternative Implementation:

We could also use a TreeSet instead of a PriorityQueue and HashSet combination, as TreeSet maintains elements in sorted order and provides O(log n) operations for add, remove, and contains:

```java
class SmallestInfiniteSet {
    private int currentSmallest;
    private TreeSet<Integer> addedBack;
    
    public SmallestInfiniteSet() {
        currentSmallest = 1;
        addedBack = new TreeSet<>();
    }
    
    public int popSmallest() {
        if (!addedBack.isEmpty() && addedBack.first() < currentSmallest) {
            return addedBack.pollFirst();
        } else {
            return currentSmallest++;
        }
    }
    
    public void addBack(int num) {
        if (num < currentSmallest) {
            addedBack.add(num);
        }
    }
}
```

## Complexity Analysis:

- **Time Complexity**:
  - Constructor: O(1)
  - popSmallest(): O(log n) where n is the number of elements in the heap
  - addBack(): O(log n) for heap operations, O(1) for set operations

- **Space Complexity**: O(k) where k is the number of unique elements added back to the set. In the worst case, this could be up to 1000 given the constraints.

## Similar Problems:

1. [295. Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/)
2. [703. Kth Largest Element in a Stream](https://leetcode.com/problems/kth-largest-element-in-a-stream/)
3. [1046. Last Stone Weight](https://leetcode.com/problems/last-stone-weight/)
4. [1642. Furthest Building You Can Reach](https://leetcode.com/problems/furthest-building-you-can-reach/)
5. [1845. Seat Reservation Manager](https://leetcode.com/problems/seat-reservation-manager/)
