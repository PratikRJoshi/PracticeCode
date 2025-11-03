### 1115. Print FooBar Alternately
### Problem Link: [Print FooBar Alternately](https://leetcode.com/problems/print-foobar-alternately/)
### Intuition
This problem asks us to implement a class that prints "foo" and "bar" alternately using two threads. This is a classic thread synchronization problem where we need to ensure that the two threads execute in a specific order. There are several ways to achieve this, including using semaphores, locks, or other synchronization primitives.

The basic concept of thread creation and management is a prerequisite for solving this problem.

### Java Reference Implementation (Using Semaphores)
```java
import java.util.concurrent.Semaphore;

class FooBar {
    private int n;
    private Semaphore fooSem;
    private Semaphore barSem;
    
    public FooBar(int n) {
        this.n = n;
        this.fooSem = new Semaphore(1);
        this.barSem = new Semaphore(0);
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            fooSem.acquire();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            barSem.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            barSem.acquire();
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            fooSem.release();
        }
    }
}
```

### Alternative Implementation (Using synchronized and wait/notify)
```java
class FooBar {
    private int n;
    private boolean fooTurn = true;
    
    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this) {
                while (!fooTurn) {
                    wait();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                fooTurn = false;
                notifyAll();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this) {
                while (fooTurn) {
                    wait();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                fooTurn = true;
                notifyAll();
            }
        }
    }
}
```

### Basic Thread Creation Example
```java
class RunnableExample implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread started: " + Thread.currentThread().getName());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Thread ended: " + Thread.currentThread().getName());
    }
    
    public static void main(String[] args) throws InterruptedException {
        int numThreads = 5;
        Thread[] threads = new Thread[numThreads];
        
        // Create and start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new RunnableExample(), "Thread-" + i);
            threads[i].start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("All threads have completed execution");
    }
}
```

### Requirement → Code Mapping
- **R0 (Thread creation)**: `Thread t = new Thread(new Runnable() { ... })` - Create a new thread with a Runnable implementation
- **R1 (Thread start)**: `t.start()` - Start the thread's execution
- **R2 (Thread join)**: `t.join()` - Wait for the thread to complete
- **R3 (Alternating execution)**: Use semaphores or wait/notify to ensure alternating execution
- **R4 (Synchronization)**: Use synchronized blocks or other synchronization primitives to prevent race conditions

### Complexity Analysis
- **Time Complexity**: O(n) - Each thread performs n iterations
- **Space Complexity**: O(1) - We use a constant amount of extra space regardless of the input size
