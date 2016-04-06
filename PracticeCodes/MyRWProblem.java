/**
 * Created by pjoshi on 4/5/16.
 */
public class MyRWProblem implements Runnable {
    public void run(){

    }

    public static void main(String[] args) {

    }
}

class ReaderWriter {
    private int readers = 0;
    private int writers = 0;
    private int writeRequest = 0;

    public synchronized void lockRead() throws InterruptedException {
       while (this.writers > 0 || this.writeRequest > 0){
           Thread.currentThread().wait();
       }
        readers++;
    }

    public synchronized void unlockRead(){
        readers--;
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException {
        writeRequest++;

        while (readers > 0 || writers > 0){
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