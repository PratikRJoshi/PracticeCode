import java.util.LinkedList;
import java.util.Random;

/**
 * Created by pjoshi on 4/8/16.
 */
class ProducerConsumer {
    LinkedList<Integer> list = new LinkedList<>();
    int totalItems = 10;
    Object lock = new Object();
    Random item = new Random();

    public void produce() throws InterruptedException {

        while (true) {
            synchronized (lock) {
                while (list.size() == totalItems) {
                    lock.wait();
                    System.out.println("Producer thread "+ Thread.currentThread().getName()+" waiting");
                }

                list.add(item.nextInt(100));
                lock.notifyAll();
                Thread.sleep(100);
            }
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (lock) {
                while (list.size() == 0){
                    lock.wait();
                    System.out.println("Consumer thread "+ Thread.currentThread().getName()+" waiting");
                }
                int value = list.removeFirst();
                System.out.println("Retrieved item: " + value + "; Current list size: " + list.size());
                lock.notifyAll();
            }
            Thread.sleep(1000);
        }
    }
}

public class BasicProducerConsumerRunner {
    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Producer thread");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Consumer thread1");


        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
