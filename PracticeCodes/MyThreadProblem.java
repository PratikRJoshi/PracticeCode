/**
 * Basic implementation of reader writer problem.
 */
public class MyThreadProblem {
    static RWDriver rwDriver;
    public static void main(String[] args) throws InterruptedException {

        int numberOfReaders = 3;
        int numberOfWriters = 2;

        rwDriver = new RWDriver();
        Thread rt[] = new Thread[numberOfReaders];
        for (int i = 0 ; i < numberOfReaders; i++){
            rt[i] = new Thread(new Readers(rwDriver), "Reader "+(i+1)+" thread");
            rt[i].start();
        }

        Thread wt[] = new Thread[numberOfReaders];
        for (int i = 0; i < numberOfReaders; i++){
            wt[i] = new Thread(new Writers(rwDriver), "Writer "+(i+1)+" thread");
            wt[i].start();
        }

        for (int i = 0; i < numberOfReaders; i++){
            rt[i].join();
        }

        for (int i = 0; i < numberOfReaders; i++){
            wt[i].join();
        }
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
        System.out.println(Thread.currentThread().getName() + " has acquired lock for reading.");
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
            System.out.println("Starting reader - "+Thread.currentThread().getName());
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
            System.out.println("Starting writer - "+Thread.currentThread().getName());
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