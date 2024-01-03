public class Section1 extends Thread {

    private static int shared; // Shared attribute

    public Section1() {} // Empty constructor

    public void run() {
        // non-critical section
        synchronized(this.getClass()) { // acquire lock
            // critical-section in here
            shared++; // for example
        } // release lock
        // non-critical section
    }
}