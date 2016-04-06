// Unrolled and not inlined version with 2 readers and 2 writers
// No data structure used for readers and writers to work with
// No priorities given or policies used in this example

public class ReaderWriterBasic {
    static Controller ctl;

    public static void main(String argv[]) {
        ctl = new Controller();

        new Reader1(ctl).start();
        new Reader2(ctl).start();
        new Writer1(ctl).start();
        new Writer2(ctl).start();
    }
}


final class Reader1 extends Thread {
    protected Controller ctl;

    public Reader1(Controller c) {
        ctl = c;
    }

    public void run() {
        while (true) {
            ctl.startRead();
            System.out.println("reader reading:");
            System.out.println("done reading");
            ctl.stopRead();
        }

    } // end public void run()
}

final class Reader2 extends Thread {
    protected Controller ctl;

    public Reader2(Controller c) {
        ctl = c;
    }

    public void run() {
        while (true) {
            ctl.startRead();
            System.out.println("reader reading:");
            System.out.println("done reading");
            ctl.stopRead();
        }
    } // end public void run()
}

final class Writer1 extends Thread {
    protected Controller ctl;

    public Writer1(Controller c) {
        ctl = c;
    }

    public void run() {
        while (true) {
            ctl.startWrite();
            System.out.println("writer writing:");
            System.out.println("done writing");
            ctl.stopWrite();
        }
    } // end public void run()
}

final class Writer2 extends Thread {
    protected Controller ctl;

    public Writer2(Controller c) {
        ctl = c;
    }

    public void run() {
        while (true) {
            ctl.startWrite();
            System.out.println("writer writing:");
            System.out.println("done writing");
            ctl.stopWrite();
        }
    } // end public void run()
}

final class Controller {
    protected int activeReaders = 0;
    protected boolean writerPresent = false;

    protected boolean writeCondition() {
        return activeReaders == 0 && !writerPresent;
    }

    protected boolean readCondition() {
        return !writerPresent;
    }

    protected synchronized void startRead() {
        while (!readCondition())
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        ++activeReaders;
    }

    protected synchronized void stopRead() {
        --activeReaders;
        notifyAll();
    }

    protected synchronized void startWrite() {
        while (!writeCondition())
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        writerPresent = true;
    }

    protected synchronized void stopWrite() {
        writerPresent = false;
        notifyAll();
    }
}

