# [706. Design HashMap](https://leetcode.com/problems/design-hashmap/)

Design a HashMap without using any built-in hash table libraries.

Implement the `MyHashMap` class:

- `MyHashMap()` initializes the object with an empty map.
- `void put(int key, int value)` inserts a `(key, value)` pair into the HashMap. If the `key` already exists in the map, update the corresponding `value`.
- `int get(int key)` returns the `value` to which the specified `key` is mapped, or `-1` if this map contains no mapping for the `key`.
- `void remove(key)` removes the `key` and its corresponding `value` if the map contains the mapping for the `key`.

**Example 1:**

```
Input
["MyHashMap", "put", "put", "get", "get", "put", "get", "remove", "get"]
[[], [1, 1], [2, 2], [1], [3], [2, 1], [2], [2], [2]]
Output
[null, null, null, 1, -1, null, 1, null, -1]

Explanation
MyHashMap myHashMap = new MyHashMap();
myHashMap.put(1, 1); // The map is now [[1,1]]
myHashMap.put(2, 2); // The map is now [[1,1], [2,2]]
myHashMap.get(1);    // return 1, The map is now [[1,1], [2,2]]
myHashMap.get(3);    // return -1 (i.e., not found), The map is now [[1,1], [2,2]]
myHashMap.put(2, 1); // The map is now [[1,1], [2,1]] (i.e., update the existing value)
myHashMap.get(2);    // return 1, The map is now [[1,1], [2,1]]
myHashMap.remove(2); // remove the mapping for 2, The map is now [[1,1]]
myHashMap.get(2);    // return -1 (i.e., not found), The map is now [[1,1]]
```

**Constraints:**

- `0 <= key, value <= 10^6`
- At most `10^4` calls will be made to `put`, `get`, and `remove`.

## Intuition/Main Idea:

Similar to the HashSet problem, we need to design a HashMap without using built-in hash table libraries. The key difference is that a HashMap stores key-value pairs, not just keys.

To implement a HashMap, we need:
1. A hash function to map keys to indices in our storage array.
2. A way to handle collisions.
3. A data structure to store key-value pairs.

We'll use the separate chaining approach with a linked list at each bucket to handle collisions. Each node in the linked list will store both a key and a value.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| void put(int key, int value) | `public void put(int key, int value) { ... }` |
| int get(int key) | `public int get(int key) { ... }` |
| void remove(key) | `public void remove(int key) { ... }` |

## Final Java Code & Learning Pattern:

```java
class MyHashMap {
    private static final int NUM_BUCKETS = 769; // A prime number for better distribution
    private LinkedList<Pair<Integer, Integer>>[] buckets;
    
    /** Initialize your data structure here. */
    public MyHashMap() {
        buckets = new LinkedList[NUM_BUCKETS];
        for (int i = 0; i < NUM_BUCKETS; i++) {
            buckets[i] = new LinkedList<>();
        }
    }
    
    /** Hash function to map a key to a bucket index */
    private int hash(int key) {
        return key % NUM_BUCKETS;
    }
    
    /** Returns the index of the key in the bucket, or -1 if not found */
    private int getIndex(int key, int bucketIndex) {
        LinkedList<Pair<Integer, Integer>> bucket = buckets[bucketIndex];
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).getKey() == key) {
                return i;
            }
        }
        return -1;
    }
    
    /** Value will always be non-negative. */
    public void put(int key, int value) {
        int bucketIndex = hash(key);
        int keyIndex = getIndex(key, bucketIndex);
        
        if (keyIndex != -1) {
            // Key exists, update the value
            buckets[bucketIndex].get(keyIndex).setValue(value);
        } else {
            // Key doesn't exist, add a new pair
            buckets[bucketIndex].add(new Pair<>(key, value));
        }
    }
    
    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        int bucketIndex = hash(key);
        int keyIndex = getIndex(key, bucketIndex);
        
        if (keyIndex != -1) {
            return buckets[bucketIndex].get(keyIndex).getValue();
        }
        return -1;
    }
    
    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        int bucketIndex = hash(key);
        int keyIndex = getIndex(key, bucketIndex);
        
        if (keyIndex != -1) {
            buckets[bucketIndex].remove(keyIndex);
        }
    }
    
    /** Simple Pair class to store key-value pairs */
    private static class Pair<K, V> {
        private K key;
        private V value;
        
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public K getKey() {
            return key;
        }
        
        public V getValue() {
            return value;
        }
        
        public void setValue(V value) {
            this.value = value;
        }
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
```

This solution implements a HashMap using separate chaining:

1. We create an array of LinkedLists (buckets) to store our key-value pairs.
2. We use a simple hash function (`key % NUM_BUCKETS`) to map keys to bucket indices.
3. We define a custom `Pair` class to store key-value pairs.
4. For the `put` operation:
   - We compute the hash of the key to find the appropriate bucket.
   - We check if the key already exists in the bucket.
   - If it does, we update its value.
   - If not, we add a new key-value pair to the bucket.
5. For the `get` operation:
   - We compute the hash of the key to find the appropriate bucket.
   - We search for the key in the bucket.
   - If found, we return its value.
   - If not found, we return -1.
6. For the `remove` operation:
   - We compute the hash of the key to find the appropriate bucket.
   - We search for the key in the bucket.
   - If found, we remove the key-value pair from the bucket.

The choice of `NUM_BUCKETS` as a prime number (769) helps distribute the keys more evenly across the buckets, reducing collisions.

## Alternative Implementations:

### Using an Array (for the given constraints):

Since the problem states that keys are between 0 and 10^6, we could use a simple array:

```java
class MyHashMap {
    private static final int SIZE = 1000001;
    private int[] map;
    
    public MyHashMap() {
        map = new int[SIZE];
        Arrays.fill(map, -1); // Initialize all values to -1 (indicating not present)
    }
    
    public void put(int key, int value) {
        map[key] = value;
    }
    
    public int get(int key) {
        return map[key];
    }
    
    public void remove(int key) {
        map[key] = -1;
    }
}
```

This approach is very efficient for the given constraints but would be impractical for a larger range of keys or non-integer keys.

### Using Open Addressing (Linear Probing):

```java
class MyHashMap {
    private static final int SIZE = 1000001;
    private static final int EMPTY = -1;
    private static final int DELETED = -2;
    private int[] keys;
    private int[] values;
    
    public MyHashMap() {
        keys = new int[SIZE];
        values = new int[SIZE];
        Arrays.fill(keys, EMPTY);
    }
    
    public void put(int key, int value) {
        int index = hash(key);
        while (keys[index] != EMPTY && keys[index] != DELETED && keys[index] != key) {
            index = (index + 1) % SIZE; // Linear probing
        }
        keys[index] = key;
        values[index] = value;
    }
    
    public int get(int key) {
        int index = hash(key);
        while (keys[index] != EMPTY) {
            if (keys[index] == key) {
                return values[index];
            }
            index = (index + 1) % SIZE;
        }
        return -1;
    }
    
    public void remove(int key) {
        int index = hash(key);
        while (keys[index] != EMPTY) {
            if (keys[index] == key) {
                keys[index] = DELETED; // Mark as deleted
                return;
            }
            index = (index + 1) % SIZE;
        }
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
  - Average case: O(1) for put, get, and remove operations.
  - Worst case: O(n) if all keys hash to the same bucket.
  
- **Space Complexity**: O(n + m) where n is the number of elements and m is the number of buckets.

For the array approach:

- **Time Complexity**: O(1) for all operations.
- **Space Complexity**: O(10^6) = O(1) since it's a fixed size regardless of the number of elements.

## Similar Problems:

1. [705. Design HashSet](https://leetcode.com/problems/design-hashset/)
2. [146. LRU Cache](https://leetcode.com/problems/lru-cache/)
3. [460. LFU Cache](https://leetcode.com/problems/lfu-cache/)
4. [380. Insert Delete GetRandom O(1)](https://leetcode.com/problems/insert-delete-getrandom-o1/)
5. [981. Time Based Key-Value Store](https://leetcode.com/problems/time-based-key-value-store/)
