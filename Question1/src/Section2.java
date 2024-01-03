import java.util.Random;

public class Section2 {
    public static void main(String[] args) {
        A threadA = new A();
        C C1 = new C(1, threadA);
        C C2 = new C(2, threadA);
        C C3 = new C(3, threadA);

        threadA.start();
        C1.start();
        C3.start();
        C2.start();
    }
}


class A extends Thread {

    private int cCounter;

    public synchronized void increaseCount(int amount) {
        cCounter += amount;
        notifyAll();
    }

    public void run() {
        Random rand = new Random();

        synchronized(this) {
            while(cCounter < 3) {
                try { wait(); } catch (InterruptedException e) {}
            }
        }
        // Wait until 3 C threads have finished
        
        // Critical code here
        System.out.println("A thread running");
        try { Thread.sleep(rand.nextInt(1500)); } catch (InterruptedException e) {}
        System.out.println("A thread ended");

    }
}


class C extends Thread {

    private int id;
    private A threadA;

    public C(int id, A threadA) {
        this.id = id;
        this.threadA = threadA;
    }

    public void run() {
        Random rand = new Random();
        System.out.printf("C%d thread running\n", id);
        
        // C thread code here
        try { Thread.sleep(rand.nextInt(1500)); } catch (InterruptedException e) {}
        // C thread critical code ends

        System.out.printf("C%d ended \n", id);
        threadA.increaseCount(1); // Notify thread A that a C thread has finished
    }
}
