/**
 * Basic implementation of reader writer problem.
 */
public class MyRWProblem {
    static RWDriver rwDriver;
    public static void main(String[] args) {
        rwDriver = new RWDriver();
        Thread t1 = new Thread(new Readers(rwDriver));
        t1.start();
    }
}

class RWDriver {
    private int readers = 0;
    private int writers = 0;
    private int writeRequest = 0;

    public synchronized void lockRead() throws InterruptedException {
        while (this.writers > 0 || this.writeRequest > 0) {
            Thread.currentThread().wait();
        }
        readers++;
    }

    public synchronized void unlockRead() {
        readers--;
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException {
        writeRequest++;

        while (readers > 0 || writers > 0) {
            Thread.currentThread().wait();
        }
        writeRequest--;
        writers++;
    }

    public synchronized void unlockWrite() {
        writers--;
        notifyAll();
    }
}

class Readers implements Runnable {
    private RWDriver rwDriver;

    public Readers(RWDriver rwd){
        this.rwDriver = rwd;
    }


    public void run() {
        try {
            rwDriver.lockRead();
            /* Do some read operation */

            /* Finish the read operation */
            rwDriver.unlockRead();
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
            rwDriver.lockRead();
            /* Do some read operation */

            /* Finish the read operation */
            rwDriver.unlockRead();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}