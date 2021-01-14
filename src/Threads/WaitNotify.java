package Threads;

public class WaitNotify {
    private final int MAX_COUNT = 5;
    private final Object mon = new Object();
    private volatile char aChar = 'A';

    public static void main(String[] args) {
        WaitNotify w = new WaitNotify();
        Thread t1 = new Thread(w::printA);
        Thread t2 = new Thread(w::printB);
        Thread t3 = new Thread(w::printC);
        t1.start();
        t2.start();
        t3.start();
    }
    
    public void printA() {
        synchronized (mon) {
            try {
                for (int i = 0; i < MAX_COUNT; i++) {
                    while (aChar != 'A') {
                        mon.wait();
                    }
                    System.out.print("A");
                    aChar = 'B';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (mon) {
            try {
                for (int i = 0; i < MAX_COUNT; i++) {
                    while (aChar != 'B') {
                        mon.wait();
                    }
                    System.out.print("B");
                    aChar = 'C';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printC() {
        synchronized (mon) {
            try {
                for (int i = 0; i < MAX_COUNT; i++) {
                    while (aChar != 'C') {
                        mon.wait();
                    }
                    System.out.print("C");
                    aChar = 'A';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
