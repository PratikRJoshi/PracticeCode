/**
 * Basic implementation of reader writer problem.
 */
public class MyRWProblem {
    static RWDriver rwDriver;
    public static void main(String[] args) throws InterruptedException {
        rwDriver = new RWDriver();
        Thread rt1 = new Thread(new Readers(rwDriver), "First Reader thread");
        Thread rt2 = new Thread(new Readers(rwDriver), "First Reader thread");
        Thread wt1 = new Thread(new Writers(rwDriver), "First Writer thread");
        Thread wt2 = new Thread(new Writers(rwDriver), "First Writer thread");
        rt1.start();
        rt2.start();
        wt1.start();
        wt2.start();

        rt1.join();
        rt2.join();
        wt1.join();

    }
}

class RWDriver {
    private static int readers = 0;
    private static int writers = 0;
    private static int writeRequest = 0;

    public synchronized void lockRead() throws InterruptedException {
        while (writers > 0 || writeRequest > 0) {
            System.out.println(Thread.currentThread().getName() + " is waiting to get read lock.");
            Thread.currentThread().wait();
        }
        System.out.println(Thread.currentThread().getName() + " has acquired lock for reading");
        readers++;

    }

    public synchronized void unlockRead() {
        readers--;
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " has released lock for reading.");
    }

    public synchronized void lockWrite() throws InterruptedException {
        writeRequest++;

        while (readers > 0 || writers > 0) {
            System.out.println(Thread.currentThread().getName() + " is waiting to get write lock.");
            Thread.currentThread().wait();
        }
        writeRequest--;
        System.out.println(Thread.currentThread().getName() + " has acquired lock for writing.");
        writers++;
    }

    public synchronized void unlockWrite() {
        writers--;
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " has released lock for writing.");
    }
}

class Readers implements Runnable {
    private RWDriver rwDriver;

    public Readers(RWDriver rwd){
        this.rwDriver = rwd;
    }


    public void run() {
        try {
            System.out.println("Starting reader "+Thread.currentThread().getName());
            rwDriver.lockRead();
            /* Do some read operation */
            System.out.println(Thread.currentThread().getName() + " has started reading.");
            /* Finish the read operation */
            rwDriver.unlockRead();
            System.out.println(Thread.currentThread().getName() + " has finished reading.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Writers implements Runnable {

    RWDriver rwDriver;

    public Writers(RWDriver rwd){
        this.rwDriver = rwd;
    }

    public void run() {
        try {
            System.out.println("Starting writer "+Thread.currentThread().getName());
            rwDriver.lockWrite();
            /* Do some write operation */
            System.out.println(Thread.currentThread().getName() + " has started writing.");
            /* Finish the write operation */
            rwDriver.unlockWrite();
            System.out.println(Thread.currentThread().getName() + " has finished writing.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}