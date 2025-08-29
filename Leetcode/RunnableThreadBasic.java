import java.util.Scanner;

/**
 * Created by pjoshi on 4/4/16.
 * Creates and reaps 'n' joinable peer threads, where n is a given by user.
 */
public class RunnableThreadBasic implements Runnable {
    public void run() {
        System.out.println("Starting...");
        System.out.println("Current thread name: "+Thread.currentThread().getName());
        System.out.println("Ending");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Enter the number of threads: ");
        Scanner sc = new Scanner(System.in);
        int numberOfThreads = sc.nextInt();
        for (int i = 0; i < numberOfThreads; i++) {
            Thread t = new Thread(new RunnableThreadBasic(), "I am thread "+(i+1));
            t.start();
            t.join();
        }

        System.out.println("Done!");
    }
}
