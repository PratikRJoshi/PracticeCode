# [705. Design HashSet](https://leetcode.com/problems/design-hashset/)

Design a HashSet without using any built-in hash table libraries.

Implement `MyHashSet` class:

- `void add(key)` Inserts the value `key` into the HashSet.
- `bool contains(key)` Returns whether the value `key` exists in the HashSet or not.
- `void remove(key)` Removes the value `key` in the HashSet. If `key` does not exist in the HashSet, do nothing.

**Example 1:**

```
Input
["MyHashSet", "add", "add", "contains", "contains", "add", "contains", "remove", "contains"]
[[], [1], [2], [1], [3], [2], [2], [2], [2]]
Output
[null, null, null, true, false, null, true, null, false]

Explanation
MyHashSet myHashSet = new MyHashSet();
myHashSet.add(1);      // set = [1]
myHashSet.add(2);      // set = [1, 2]
myHashSet.contains(1); // return True
myHashSet.contains(3); // return False, (not found)
myHashSet.add(2);      // set = [1, 2]
myHashSet.contains(2); // return True
myHashSet.remove(2);   // set = [1]
myHashSet.contains(2); // return False, (already removed)
```

**Constraints:**

- `0 <= key <= 10^6`
- At most `10^4` calls will be made to `add`, `remove`, and `contains`.

## Intuition/Main Idea:

A HashSet is a data structure that stores unique elements and provides constant-time complexity for basic operations like add, remove, and contains. To implement a HashSet without using built-in hash table libraries, we need to:

1. Design a hash function to map keys to indices in our storage array.
2. Handle collisions (when different keys map to the same index).

There are several ways to handle collisions:
- **Separate Chaining**: Use a linked list or another data structure at each index to store multiple elements.
- **Open Addressing**: Find another slot if a collision occurs (e.g., linear probing, quadratic probing).

For this problem, we'll use separate chaining with a simple array of linked lists (or buckets). The key steps are:
1. Create an array of a fixed size (the number of buckets).
2. Define a hash function to map a key to a bucket index.
3. Use a linked list at each bucket to handle collisions.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| void add(key) | `public void add(int key) { ... }` |
| bool contains(key) | `public boolean contains(int key) { ... }` |
| void remove(key) | `public void remove(int key) { ... }` |

## Final Java Code & Learning Pattern:

```java
class MyHashSet {
    private static final int NUM_BUCKETS = 769; // A prime number for better distribution
    private LinkedList<Integer>[] buckets;
    
    /** Initialize your data structure here. */
    public MyHashSet() {
        buckets = new LinkedList[NUM_BUCKETS];
        for (int i = 0; i < NUM_BUCKETS; i++) {
            buckets[i] = new LinkedList<>();
        }
    }
    
    /** Hash function to map a key to a bucket index */
    private int hash(int key) {
        return key % NUM_BUCKETS;
    }
    
    /** Add a key to the HashSet */
    public void add(int key) {
        int index = hash(key);
        if (!buckets[index].contains(key)) {
            buckets[index].add(key);
        }
    }
    
    /** Remove a key from the HashSet */
    public void remove(int key) {
        int index = hash(key);
        buckets[index].remove(Integer.valueOf(key)); // Use Integer.valueOf to remove by value, not index
    }
    
    /** Check if the HashSet contains a specific key */
    public boolean contains(int key) {
        int index = hash(key);
        return buckets[index].contains(key);
    }
}

/**
 * Your MyHashSet object will be instantiated and called as such:
 * MyHashSet obj = new MyHashSet();
 * obj.add(key);
 * obj.remove(key);
 * boolean param_3 = obj.contains(key);
 */
```

This solution implements a HashSet using separate chaining:

1. We create an array of LinkedLists (buckets) to store our elements.
2. We use a simple hash function (`key % NUM_BUCKETS`) to map keys to bucket indices.
3. For the `add` operation:
   - We compute the hash of the key to find the appropriate bucket.
   - If the key is not already in the bucket, we add it.
4. For the `remove` operation:
   - We compute the hash of the key to find the appropriate bucket.
   - We remove the key from the bucket if it exists.
5. For the `contains` operation:
   - We compute the hash of the key to find the appropriate bucket.
   - We check if the key exists in the bucket.

The choice of `NUM_BUCKETS` as a prime number (769) helps distribute the keys more evenly across the buckets, reducing collisions.

## Alternative Implementations:

### Using a Boolean Array (for the given constraints):

Since the problem states that keys are between 0 and 10^6, we could use a simple boolean array:

```java
class MyHashSet {
    private boolean[] set;
    
    public MyHashSet() {
        set = new boolean[1000001]; // 0 to 10^6
    }
    
    public void add(int key) {
        set[key] = true;
    }
    
    public void remove(int key) {
        set[key] = false;
    }
    
    public boolean contains(int key) {
        return set[key];
    }
}
```

This approach is very efficient for the given constraints but would be impractical for a larger range of keys or non-integer keys.

### Using Open Addressing (Linear Probing):

```java
class MyHashSet {
    private static final int SIZE = 1000001;
    private static final int EMPTY = -1;
    private int[] set;
    
    public MyHashSet() {
        set = new int[SIZE];
        Arrays.fill(set, EMPTY);
    }
    
    public void add(int key) {
        int index = hash(key);
        while (set[index] != EMPTY && set[index] != key) {
            index = (index + 1) % SIZE; // Linear probing
        }
        set[index] = key;
    }
    
    public void remove(int key) {
        int index = hash(key);
        while (set[index] != EMPTY) {
            if (set[index] == key) {
                set[index] = EMPTY;
                return;
            }
            index = (index + 1) % SIZE;
        }
    }
    
    public boolean contains(int key) {
        int index = hash(key);
        while (set[index] != EMPTY) {
            if (set[index] == key) {
                return true;
            }
            index = (index + 1) % SIZE;
        }
        return false;
    }
    
    private int hash(int key) {
        return key % SIZE;
    }
}
```

This approach uses open addressing with linear probing to handle collisions. When a collision occurs, we simply move to the next slot.

## Complexity Analysis:

For the separate chaining approach:

- **Time Complexity**:
  - Average case: O(1) for add, remove, and contains operations.
  - Worst case: O(n) if all keys hash to the same bucket.
  
- **Space Complexity**: O(n + m) where n is the number of elements and m is the number of buckets.

For the boolean array approach:

- **Time Complexity**: O(1) for all operations.
- **Space Complexity**: O(10^6) = O(1) since it's a fixed size regardless of the number of elements.

## Similar Problems:

1. [706. Design HashMap](https://leetcode.com/problems/design-hashmap/)
2. [146. LRU Cache](https://leetcode.com/problems/lru-cache/)
3. [460. LFU Cache](https://leetcode.com/problems/lfu-cache/)
4. [380. Insert Delete GetRandom O(1)](https://leetcode.com/problems/insert-delete-getrandom-o1/)
5. [432. All O`one Data Structure](https://leetcode.com/problems/all-oone-data-structure/)
