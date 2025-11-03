### 1114. Print in Order
### Problem Link: [Print in Order](https://leetcode.com/problems/print-in-order/)
### Intuition
This problem is about thread synchronization. We need to ensure that three functions are always executed in a specific order (first, second, third) regardless of the order in which their corresponding threads are started. This is a classic example of using synchronization mechanisms in concurrent programming.

The Producer-Consumer pattern is a related concurrency pattern where producers add items to a shared buffer and consumers remove them. The key challenge is to ensure that producers don't add items when the buffer is full and consumers don't remove items when the buffer is empty, which requires proper synchronization.

### Java Reference Implementation (Using Semaphores)
```java
import java.util.concurrent.Semaphore;

class Foo {
    private Semaphore sem1;
    private Semaphore sem2;
    
    public Foo() {
        sem1 = new Semaphore(0);
        sem2 = new Semaphore(0);
    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        sem1.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        sem1.acquire();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        sem2.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        sem2.acquire();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
```

### Alternative Implementation (Using CountDownLatch)
```java
import java.util.concurrent.CountDownLatch;

class Foo {
    private CountDownLatch latch1;
    private CountDownLatch latch2;
    
    public Foo() {
        latch1 = new CountDownLatch(1);
        latch2 = new CountDownLatch(1);
    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        latch1.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        latch1.await();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        latch2.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        latch2.await();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
```

### Producer-Consumer Implementation
```java
import java.util.LinkedList;
import java.util.Queue;

class ProducerConsumer {
    private Queue<Integer> buffer;
    private int capacity;
    private Object lock;
    
    public ProducerConsumer(int capacity) {
        this.buffer = new LinkedList<>();
        this.capacity = capacity;
        this.lock = new Object();
    }
    
    public void produce(int item) throws InterruptedException {
        synchronized (lock) {
            // Wait if buffer is full
            while (buffer.size() == capacity) {
                lock.wait();
            }
            
            // Add item to buffer
            buffer.add(item);
            System.out.println("Produced: " + item);
            
            // Notify consumers
            lock.notifyAll();
        }
    }
    
    public int consume() throws InterruptedException {
        int item;
        synchronized (lock) {
            // Wait if buffer is empty
            while (buffer.isEmpty()) {
                lock.wait();
            }
            
            // Remove item from buffer
            item = buffer.poll();
            System.out.println("Consumed: " + item);
            
            // Notify producers
            lock.notifyAll();
        }
        return item;
    }
}

class Producer implements Runnable {
    private ProducerConsumer pc;
    private int id;
    
    public Producer(ProducerConsumer pc, int id) {
        this.pc = pc;
        this.id = id;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                pc.produce(i + id * 100);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private ProducerConsumer pc;
    
    public Consumer(ProducerConsumer pc) {
        this.pc = pc;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                pc.consume();
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

### Requirement → Code Mapping
- **R0 (Synchronization)**: Use semaphores, CountDownLatch, or synchronized blocks for thread coordination
- **R1 (Order guarantee)**: Ensure that functions execute in the correct order
- **R2 (Producer-Consumer)**: Implement a shared buffer with proper synchronization
- **R3 (Wait mechanism)**: Use `wait()` when the buffer is full (producer) or empty (consumer)
- **R4 (Notification)**: Use `notifyAll()` to wake up waiting threads when conditions change

### Complexity Analysis
- **Time Complexity**: O(1) for each operation
- **Space Complexity**: O(1) for synchronization primitives, O(n) for the buffer in the Producer-Consumer pattern
