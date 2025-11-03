### 1188. Design Bounded Blocking Queue
### Problem Link: [Design Bounded Blocking Queue](https://leetcode.com/problems/design-bounded-blocking-queue/)
### Intuition
This problem asks us to implement a thread-safe bounded blocking queue, which is a data structure that supports concurrent operations. A bounded blocking queue has a fixed capacity and blocks when trying to enqueue an item when the queue is full or dequeue when the queue is empty.

The Reader-Writer pattern is a related concurrency pattern that allows multiple readers to access a shared resource simultaneously, but writers must have exclusive access. This pattern is useful when read operations are more frequent than write operations.

### Java Reference Implementation (Bounded Blocking Queue)
```java
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBlockingQueue {
    private Queue<Integer> queue;
    private int capacity;
    private ReentrantLock lock;
    private Condition notFull;
    private Condition notEmpty;
    
    public BoundedBlockingQueue(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
        this.lock = new ReentrantLock();
        this.notFull = lock.newCondition();
        this.notEmpty = lock.newCondition();
    }
    
    public void enqueue(int element) throws InterruptedException {
        lock.lock();
        try {
            // Wait if the queue is full
            while (queue.size() == capacity) {
                notFull.await();
            }
            
            // Add the element and signal that the queue is not empty
            queue.offer(element);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
    
    public int dequeue() throws InterruptedException {
        lock.lock();
        try {
            // Wait if the queue is empty
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            
            // Remove the element and signal that the queue is not full
            int element = queue.poll();
            notFull.signal();
            return element;
        } finally {
            lock.unlock();
        }
    }
    
    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}
```

### Reader-Writer Implementation (Using ReentrantReadWriteLock)
```java
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ReaderWriter {
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
    private int sharedResource = 0;
    
    public int read() {
        readLock.lock();
        try {
            System.out.println("Reading: " + sharedResource);
            return sharedResource;
        } finally {
            readLock.unlock();
        }
    }
    
    public void write(int value) {
        writeLock.lock();
        try {
            System.out.println("Writing: " + value);
            sharedResource = value;
        } finally {
            writeLock.unlock();
        }
    }
}

// Usage example
class ReaderWriterExample {
    public static void main(String[] args) {
        ReaderWriter rw = new ReaderWriter();
        
        // Create reader threads
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                while (true) {
                    rw.read();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        }
        
        // Create writer threads
        for (int i = 0; i < 2; i++) {
            final int id = i;
            new Thread(() -> {
                int value = 0;
                while (true) {
                    rw.write(value++);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        }
    }
}
```

### Reader-Writer Implementation (Using Semaphores)
```java
import java.util.concurrent.Semaphore;

class Database {
    private int readerCount;  // the number of active readers
    private Semaphore mutex;  // controls access to readerCount
    private Semaphore db;     // controls access to the database
    
    public Database() {
        readerCount = 0;
        mutex = new Semaphore(1);
        db = new Semaphore(1);
    }
    
    public void acquireReadLock(int readerNum) {
        try {
            // Mutual exclusion for readerCount
            mutex.acquire();
            
            ++readerCount;
            
            // If this is the first reader, acquire the database lock
            if (readerCount == 1) {
                db.acquire();
            }
            
            System.out.println("Reader " + readerNum + " is reading. Reader count = " + readerCount);
            
            // Release mutex
            mutex.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void releaseReadLock(int readerNum) {
        try {
            // Mutual exclusion for readerCount
            mutex.acquire();
            
            --readerCount;
            
            // If this is the last reader, release the database lock
            if (readerCount == 0) {
                db.release();
            }
            
            System.out.println("Reader " + readerNum + " is done reading. Reader count = " + readerCount);
            
            // Release mutex
            mutex.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void acquireWriteLock(int writerNum) {
        try {
            db.acquire();
            System.out.println("Writer " + writerNum + " is writing.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void releaseWriteLock(int writerNum) {
        System.out.println("Writer " + writerNum + " is done writing.");
        db.release();
    }
}
```

### Basic Reader-Writer Implementation (Using synchronized)
```java
class Controller {
    private int activeReaders = 0;
    private boolean writerPresent = false;
    
    private boolean writeCondition() {
        return activeReaders == 0 && !writerPresent;
    }
    
    private boolean readCondition() {
        return !writerPresent;
    }
    
    public synchronized void startRead() throws InterruptedException {
        while (!readCondition()) {
            wait();
        }
        activeReaders++;
    }
    
    public synchronized void stopRead() {
        activeReaders--;
        notifyAll();
    }
    
    public synchronized void startWrite() throws InterruptedException {
        while (!writeCondition()) {
            wait();
        }
        writerPresent = true;
    }
    
    public synchronized void stopWrite() {
        writerPresent = false;
        notifyAll();
    }
}
```

### Requirement → Code Mapping
- **R0 (Thread safety)**: Use locks, semaphores, or synchronized blocks to ensure thread safety
- **R1 (Blocking operations)**: Block when trying to enqueue to a full queue or dequeue from an empty queue
- **R2 (Signaling)**: Use conditions, semaphores, or wait/notify to signal when the state changes
- **R3 (Reader-Writer pattern)**: Allow multiple readers but only one writer at a time
- **R4 (Reader preference)**: In the semaphore implementation, readers are given preference over writers

### Complexity Analysis
- **Time Complexity**: O(1) for each operation
- **Space Complexity**: O(n) for the queue with capacity n, O(1) for the reader-writer implementations
