# Time Based Key-Value Store

## Problem Description

**Problem Link:** [Time Based Key-Value Store](https://leetcode.com/problems/time-based-key-value-store/)

Design a time-based key-value data structure that can store multiple values for the same key at different time stamps and retrieve the key's value at a certain timestamp.

Implement the `TimeMap` class:

- `TimeMap()` Initializes the object of the data structure.
- `void set(String key, String value, int timestamp)` Stores the key `key` with the value `value` at the given time `timestamp`.
- `String get(String key, int timestamp)` Returns a value such that `set` was called previously, with `timestamp_prev <= timestamp`. If there are multiple such values, it returns the value associated with the largest `timestamp_prev`. If there are no values, it returns `""`.

**Example 1:**
```
Input
["TimeMap", "set", "get", "get", "set", "get", "get"]
[[], ["foo", "bar", 1], ["foo", 1], ["foo", 3], ["foo", "bar2", 4], ["foo", 4], ["foo", 5]]
Output
[null, null, "bar", "bar", null, "bar2", "bar2"]

Explanation
TimeMap timeMap = new TimeMap();
timeMap.set("foo", "bar", 1);  // store the key "foo" and value "bar" along with timestamp = 1.
timeMap.get("foo", 1);         // return "bar"
timeMap.get("foo", 3);         // return "bar", since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 is "bar".
timeMap.set("foo", "bar2", 4); // store the key "foo" and value "bar2" along with timestamp = 4.
timeMap.get("foo", 4);         // return "bar2"
timeMap.get("foo", 5);         // return "bar2"
```

**Constraints:**
- `1 <= key.length, value.length <= 100`
- `key` and `value` consist of lowercase English letters and digits.
- `1 <= timestamp <= 10^7`
- All the timestamps `timestamp` of `set` are strictly increasing.
- At most `2 * 10^5` calls will be made to `set` and `get`.

## Intuition/Main Idea

This is a **design problem** that requires efficient storage and retrieval of time-stamped values. We need to find the value with the largest timestamp that is <= the query timestamp.

**Core Algorithm:**
1. Use a `HashMap` to map keys to lists of `(timestamp, value)` pairs.
2. Since timestamps are strictly increasing, we can use **binary search** to find the largest timestamp <= query timestamp.
3. For `set`: Append to the list (timestamps are increasing).
4. For `get`: Binary search the list to find the right value.

**Why binary search works:** Since timestamps are strictly increasing, the list is sorted. We can use binary search to find the largest timestamp <= query timestamp in O(log n) time.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Store key-value pairs | HashMap with lists - Line 5 |
| Set operation | Append to list - Lines 9-12 |
| Get operation | Binary search - Lines 14-30 |
| Binary search for timestamp | Binary search implementation - Lines 20-29 |
| Return value or empty string | Return statement - Lines 31-32 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class TimeMap {
    // Map: key -> list of (timestamp, value) pairs
    private Map<String, List<Pair<Integer, String>>> map;
    
    public TimeMap() {
        map = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        // If key doesn't exist, create new list
        map.putIfAbsent(key, new ArrayList<>());
        // Append (timestamp, value) pair
        // Since timestamps are strictly increasing, list remains sorted
        map.get(key).add(new Pair<>(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        // If key doesn't exist, return empty string
        if (!map.containsKey(key)) {
            return "";
        }
        
        List<Pair<Integer, String>> list = map.get(key);
        
        // Binary search for the largest timestamp <= query timestamp
        int left = 0;
        int right = list.size() - 1;
        String result = "";
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midTimestamp = list.get(mid).getKey();
            
            if (midTimestamp <= timestamp) {
                // This timestamp is valid, update result and search right
                result = list.get(mid).getValue();
                left = mid + 1;
            } else {
                // This timestamp is too large, search left
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    // Helper class for Pair (or use Map.Entry)
    static class Pair<K, V> {
        K key;
        V value;
        
        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        K getKey() { return key; }
        V getValue() { return value; }
    }
}
```

**Alternative Using TreeMap:**

```java
import java.util.*;

class TimeMap {
    // Map: key -> TreeMap<timestamp, value>
    private Map<String, TreeMap<Integer, String>> map;
    
    public TimeMap() {
        map = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        map.putIfAbsent(key, new TreeMap<>());
        map.get(key).put(timestamp, value);
    }
    
    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) {
            return "";
        }
        
        TreeMap<Integer, String> treeMap = map.get(key);
        // Find the largest key <= timestamp
        Integer floorKey = treeMap.floorKey(timestamp);
        
        return floorKey == null ? "" : treeMap.get(floorKey);
    }
}
```

### Understanding TreeMap's floorKey vs ceilingKey Methods

TreeMap in Java provides several navigation methods that are particularly useful for this problem:

1. **floorKey(K key)** - Returns the greatest key less than or equal to the given key, or null if there is no such key.
   - In our problem: `floorKey(timestamp)` gives us the largest timestamp that is ≤ the query timestamp.
   - This is exactly what we need for the `get` operation, as we want the value with the largest timestamp not exceeding our query timestamp.
   - Example: If we have timestamps [1, 4, 8] and query for timestamp 5, `floorKey(5)` returns 4.

2. **ceilingKey(K key)** - Returns the least key greater than or equal to the given key, or null if there is no such key.
   - This would give us the smallest timestamp that is ≥ the query timestamp.
   - Not suitable for our problem as stated, but would be useful if we wanted the "next" value after a timestamp.
   - Example: If we have timestamps [1, 4, 8] and query for timestamp 5, `ceilingKey(5)` returns 8.

3. **Why we use floorKey and not ceilingKey:**
   - The problem asks for "a value such that set was called previously, with timestamp_prev <= timestamp"
   - `floorKey` directly implements this "less than or equal to" requirement
   - `ceilingKey` would give us the "future" value, which doesn't match our requirements

4. **Edge cases handled by floorKey:**
   - If the query timestamp is smaller than all stored timestamps, `floorKey` returns null (handled by our code to return "")
   - If the query timestamp exactly matches a stored timestamp, `floorKey` returns that exact timestamp

Using `floorKey` makes our code more concise and directly expresses the problem's requirements without having to implement custom binary search logic.

### Practical Memory Aid: Direction of Search
- Floor: Looking for the past or present (≤)
  - Use when you need the most recent/largest value not exceeding a threshold
  - Example: "What's the latest timestamp before or at 5:00 PM?"

- Ceiling: Looking for the present or future (≥)
  - Use when you need the next/smallest value not below a threshold
  - Example: "What's the next available appointment at or after 5:00 PM?"

**Example walkthrough:**
- set("foo", "bar", 1) → map["foo"] = [(1, "bar")]
- get("foo", 1) → binary search finds timestamp 1 → "bar"
- get("foo", 3) → binary search finds timestamp 1 (largest <= 3) → "bar"
- set("foo", "bar2", 4) → map["foo"] = [(1, "bar"), (4, "bar2")]
- get("foo", 4) → binary search finds timestamp 4 → "bar2"
- get("foo", 5) → binary search finds timestamp 4 (largest <= 5) → "bar2"

## Complexity Analysis

- **Time Complexity:** 
  - `set`: $O(1)$ amortized (appending to list).
  - `get`: $O(\log n)$ where $n$ is the number of values for the key (binary search).

- **Space Complexity:** $O(n)$ where $n$ is the total number of `set` operations.

## Similar Problems

Problems that can be solved using similar data structure patterns:

1. **981. Time Based Key-Value Store** (this problem) - HashMap + Binary search
2. **146. LRU Cache** - Design with HashMap + Doubly linked list
3. **355. Design Twitter** - Design with multiple data structures
4. **380. Insert Delete GetRandom O(1)** - HashMap + ArrayList
5. **381. Insert Delete GetRandom O(1) - Duplicates allowed** - HashMap + ArrayList
6. **432. All O(1) Data Structure** - Design with HashMap + Doubly linked list
7. **460. LFU Cache** - Design with HashMap + Frequency buckets
8. **588. Design In-Memory File System** - Design with Trie/HashMap
9. **642. Design Search Autocomplete System** - Design with Trie
10. **716. Max Stack** - Design with stack + heap
