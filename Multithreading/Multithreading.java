class MyThread extends Thread {
    private String threadName;
    MyThread(String name) {
        threadName = name;
    }

    public void run() {
        System.out.println(threadName + " is starting.");
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(threadName + " is running: " + i);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + " interrupted.");
        }
        System.out.println(threadName + " is exiting.");
    }
} 
public class Multithreading {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread("Thread-1");
        MyThread thread2 = new MyThread("Thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        System.out.println("Main thread exiting.");
    }
}