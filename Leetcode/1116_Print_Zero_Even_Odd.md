### 1116. Print Zero Even Odd
### Problem Link: [Print Zero Even Odd](https://leetcode.com/problems/print-zero-even-odd/)
### Intuition
This problem asks us to coordinate three threads to print numbers in a specific order: one thread prints zeros, another prints even numbers, and the third prints odd numbers. The output should be "010203..." for n=3. This is another thread synchronization problem that requires careful coordination between the threads.

The Reader-Writer pattern is a related concurrency pattern that can be adapted for this kind of thread coordination problem.

### Java Reference Implementation (Using Semaphores)
```java
import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private int n;
    private Semaphore zeroSem;
    private Semaphore evenSem;
    private Semaphore oddSem;
    
    public ZeroEvenOdd(int n) {
        this.n = n;
        this.zeroSem = new Semaphore(1);
        this.evenSem = new Semaphore(0);
        this.oddSem = new Semaphore(0);
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            zeroSem.acquire();
            printNumber.accept(0);
            
            if (i % 2 == 0) {
                evenSem.release();
            } else {
                oddSem.release();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            evenSem.acquire();
            printNumber.accept(i);
            zeroSem.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            oddSem.acquire();
            printNumber.accept(i);
            zeroSem.release();
        }
    }
}
```

### Alternative Implementation (Using synchronized and wait/notify)
```java
import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private int n;
    private int state = 0; // 0: print zero, 1: print odd, 2: print even
    private int current = 1;
    
    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this) {
                while (state != 0) {
                    wait();
                }
                printNumber.accept(0);
                state = (current % 2 == 0) ? 2 : 1;
                notifyAll();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            synchronized (this) {
                while (state != 2 || current != i) {
                    wait();
                }
                printNumber.accept(i);
                current++;
                state = 0;
                notifyAll();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            synchronized (this) {
                while (state != 1 || current != i) {
                    wait();
                }
                printNumber.accept(i);
                current++;
                state = 0;
                notifyAll();
            }
        }
    }
}
```

### Reader-Writer Implementation (Using synchronized)
```java
class RWLock {
    private int readers = 0;
    private int writers = 0;
    private int maxReaders = Integer.MAX_VALUE; // Can be configured to limit readers
    
    public synchronized void lockRead() throws InterruptedException {
        while (writers > 0 || readers >= maxReaders) {
            wait();
        }
        readers++;
    }
    
    public synchronized void unlockRead() {
        readers--;
        notifyAll();
    }
    
    public synchronized void lockWrite() throws InterruptedException {
        while (readers > 0 || writers > 0) {
            wait();
        }
        writers++;
    }
    
    public synchronized void unlockWrite() {
        writers--;
        notifyAll();
    }
}

class Reader implements Runnable {
    private RWLock rwLock;
    private String name;
    
    public Reader(RWLock rwLock, String name) {
        this.rwLock = rwLock;
        this.name = name;
    }
    
    @Override
    public void run() {
        try {
            System.out.println(name + " is trying to read");
            rwLock.lockRead();
            System.out.println(name + " is reading");
            Thread.sleep(100); // Simulate reading
            rwLock.unlockRead();
            System.out.println(name + " has finished reading");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Writer implements Runnable {
    private RWLock rwLock;
    private String name;
    
    public Writer(RWLock rwLock, String name) {
        this.rwLock = rwLock;
        this.name = name;
    }
    
    @Override
    public void run() {
        try {
            System.out.println(name + " is trying to write");
            rwLock.lockWrite();
            System.out.println(name + " is writing");
            Thread.sleep(200); // Simulate writing
            rwLock.unlockWrite();
            System.out.println(name + " has finished writing");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

### Requirement → Code Mapping
- **R0 (Thread coordination)**: Use semaphores or synchronized blocks to coordinate thread execution
- **R1 (Zero thread)**: Print 0 before each number and then signal the appropriate thread (even or odd)
- **R2 (Even thread)**: Wait for signal from zero thread, print even number, then signal zero thread
- **R3 (Odd thread)**: Wait for signal from zero thread, print odd number, then signal zero thread
- **R4 (Reader-Writer pattern)**: Allow multiple readers but only one writer at a time

### Complexity Analysis
- **Time Complexity**: O(n) - Each thread performs operations proportional to n
- **Space Complexity**: O(1) - We use a constant amount of extra space regardless of the input size
