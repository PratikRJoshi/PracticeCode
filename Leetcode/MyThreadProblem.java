/**
 * Basic implementation of reader writer problem.
 */
public class MyThreadProblem {
    static RWDriver rwDriver;

    public static void main(String[] args) throws InterruptedException {

/*        int numberOfReaders = 3;
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
*/
        rwDriver = new RWDriver();
        Thread rt1 = new Thread(new Readers(rwDriver), "Reader 1");
        rt1.start();
        Thread rt2 = new Thread(new Readers(rwDriver), "Reader 2");
        rt2.start();
        Thread rt3 = new Thread(new Readers(rwDriver), "Reader 3");
        rt3.start();

        Thread wt1 = new Thread(new Writers(rwDriver), "Writer 1");
        wt1.start();
        Thread wt2 = new Thread(new Writers(rwDriver), "Writer 2");
        wt2.start();

        rt1.join();
        rt2.join();
        rt3.join();

        wt1.join();
        wt2.join();

    }
}

class RWDriver {
    private static int readers = 0;
    private static int writers = 0;
    private static int maxReaders = 1;

    //public synchronized void lockRead() throws InterruptedException {
    public void lockRead() throws InterruptedException {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " has entered lockRead");
            while (writers > 0 || readers >= maxReaders) {
                System.out.println(Thread.currentThread().getName() + " is waiting to get read lock.");
//            Thread.currentThread().wait();
                wait();
            }
            readers++;
            System.out.println(Thread.currentThread().getName() + " has acquired lock for reading. Number of threads " +
                    "at this point is " + readers);
        }

    }

    public synchronized void unlockRead() {
        System.out.println(Thread.currentThread().getName() + " has entered unlockRead");
        readers--;
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " has released lock for reading.");
    }

    public synchronized void lockWrite() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " has entered lockWrite");
        while (writers > 0) {
            System.out.println(Thread.currentThread().getName() + " is waiting to get write lock.");
//            Thread.currentThread().wait();
            wait();
        }
        writers++;
        System.out.println(Thread.currentThread().getName() + " has acquired lock for writing. The number of writers " +
                "at this point is " + writers);
    }

    public synchronized void unlockWrite() {
        System.out.println(Thread.currentThread().getName() + " has entered unlockWrite");
        writers--;
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " has released lock for writing.");
    }
}

class Readers implements Runnable {
    private RWDriver rwDriver;

    public Readers(RWDriver rwd) {
        this.rwDriver = rwd;
    }


    public void run() {
        try {
            System.out.println("Starting reader - " + Thread.currentThread().getName());
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

    public Writers(RWDriver rwd) {
        this.rwDriver = rwd;
    }

    public void run() {
        try {
            System.out.println("Starting writer - " + Thread.currentThread().getName());
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