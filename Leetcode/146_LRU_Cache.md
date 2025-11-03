### 146. LRU Cache
### Problem Link: [LRU Cache](https://leetcode.com/problems/lru-cache/)
### Intuition
An LRU (Least Recently Used) cache is a data structure that maintains a fixed-size collection of items, discarding the least recently used item when the cache reaches its capacity. This problem asks us to implement an LRU cache with O(1) time complexity for both get and put operations.

The key insight is to use a combination of a hash map and a doubly linked list:
- The hash map provides O(1) lookup by key
- The doubly linked list allows for O(1) removal and insertion at any position, which is needed to maintain the order of elements based on their recency of use

When an element is accessed or added, it's moved to the front of the linked list (most recently used). When the cache is full and a new element is added, the element at the end of the linked list (least recently used) is removed.

### Java Reference Implementation
```java
import java.util.HashMap;
import java.util.Map;

class LRUCache {
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Map<Integer, Node> cache;
    private int capacity;
    private Node head; // Most recently used
    private Node tail; // Least recently used
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node(0, 0); // Dummy head
        this.tail = new Node(0, 0); // Dummy tail
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        
        // Move the accessed node to the front (most recently used)
        Node node = cache.get(key);
        moveToHead(node);
        
        return node.value;
    }
    
    public void put(int key, int value) {
        // If key exists, update its value and move it to the front
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            moveToHead(node);
            return;
        }
        
        // If capacity is reached, remove the least recently used item (tail)
        if (cache.size() == capacity) {
            Node lru = tail.prev;
            removeNode(lru);
            cache.remove(lru.key);
        }
        
        // Add the new node to the front
        Node newNode = new Node(key, value);
        cache.put(key, newNode);
        addToHead(newNode);
    }
    
    private void addToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
    
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }
}
```

### Alternative Implementation (Without Dummy Nodes)
```java
import java.util.HashMap;
import java.util.Map;

class LRUCache {
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Map<Integer, Node> cache;
    private int capacity;
    private Node head; // Most recently used
    private Node tail; // Least recently used
    private int size;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.size = 0;
    }
    
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        
        Node node = cache.get(key);
        removeNode(node);
        addToHead(node);
        
        return node.value;
    }
    
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            removeNode(node);
            addToHead(node);
            return;
        }
        
        Node newNode = new Node(key, value);
        
        if (size == capacity) {
            cache.remove(tail.key);
            removeNode(tail);
            size--;
        }
        
        addToHead(newNode);
        cache.put(key, newNode);
        size++;
    }
    
    private void addToHead(Node node) {
        if (head == null) {
            head = node;
            tail = node;
            return;
        }
        
        node.next = head;
        head.prev = node;
        head = node;
    }
    
    private void removeNode(Node node) {
        if (node == head && node == tail) {
            head = null;
            tail = null;
            return;
        }
        
        if (node == head) {
            head = head.next;
            head.prev = null;
            return;
        }
        
        if (node == tail) {
            tail = tail.prev;
            tail.next = null;
            return;
        }
        
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}
```

### Requirement → Code Mapping
- **R0 (O(1) time complexity)**: Use a hash map for lookups and a doubly linked list for ordering
- **R1 (Get operation)**: `get(int key)` - Return the value if the key exists, otherwise return -1
- **R2 (Put operation)**: `put(int key, int value)` - Update or insert the key-value pair
- **R3 (Update recency)**: Move accessed or updated nodes to the front (most recently used)
- **R4 (Eviction policy)**: Remove the least recently used item when capacity is reached

### Complexity Analysis
- **Time Complexity**: O(1) for both get and put operations
- **Space Complexity**: O(capacity) for storing up to capacity key-value pairs
