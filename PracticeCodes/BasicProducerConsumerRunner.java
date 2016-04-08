import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by pjoshi on 4/8/16.
 */
class ProducerConsumer {
    LinkedList<Integer> list = new LinkedList<>();
    int totalItems = 10;
    Object lock = new Object();
    Random item = new Random();

    public void produce() throws InterruptedException {

//        while (true) {
        synchronized (lock) {
            while (list.size() == totalItems) {
                lock.wait();
                System.out.println("Producer thread " + Thread.currentThread().getName() + " waiting");
            }

            int value = item.nextInt(100);
            list.add(value);
            System.out.println("Producer has put item: " + value + "; Current list size: " + list.size());
            lock.notifyAll();
            Thread.sleep(300);
//            }
        }
    }

    public synchronized void consume() throws InterruptedException {
//        while (true) {
        synchronized (lock) {
            while (list.size() == 0) {
                lock.wait();
                System.out.println("Consumer thread " + Thread.currentThread().getName() + " waiting");
            }
            int value = list.removeFirst();
            System.out.println("Retrieved item: " + value + "; Current list size: " + list.size());
            lock.notifyAll();
        }
        Thread.sleep(100);
//        }
    }
}

class Producer implements Runnable {
    ProducerConsumer pc;

    public Producer(ProducerConsumer pc) {
        this.pc = pc;
    }

    public void run() {
        try {
            pc.produce();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    ProducerConsumer pc;

    public Consumer(ProducerConsumer pc) {
        this.pc = pc;
    }

    public void run() {
        try {
            pc.consume();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class BasicProducerConsumerRunner {
    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer();

        ExecutorService producerPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            producerPool.submit(new Producer(pc));
        }

        ExecutorService consumerPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 4; i++) {
            consumerPool.submit(new Consumer(pc));
        }

        producerPool.shutdown();
        consumerPool.shutdown();

        producerPool.awaitTermination(1, TimeUnit.MINUTES);
        consumerPool.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Size of list at the end: "+pc.list.size());
        System.out.println("Program completed.");
/*
        Thread t1 = new Thread(new Producer(pc), "Producer thread");
        Thread t2 = new Thread(new Consumer(pc), "Consumer thread1");
        Thread t3 = new Thread(new Consumer(pc), "Consumer thread2");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
*/
    }
}
