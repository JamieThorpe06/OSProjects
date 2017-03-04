
//package diningphilosophers1; 

/* * @author webster*/
public class Semaphore extends Object {

    private int count;
    private int max_count = 0;
    private int waiting_count = 0;
    private int num_threads_eating = 0;

    public Semaphore(int initial_count, int maximum_count) {
        if (max_count == 0 && maximum_count >= initial_count) {
            count = initial_count;
            max_count = maximum_count;
        }
    }

    public Semaphore() {
        count = 1;
        max_count = 1;
    }

    public synchronized void P() {
        while (count == 0) {
            waiting_count++;
            try {
                wait();
            } catch (Exception e) {
            }
            num_threads_eating++;
        }

        num_threads_eating++;
        count--;


    }

    public synchronized void V() {
        if (waiting_count > 0) {
            waiting_count--;
            try {
                notify();
            } catch (Exception e) {
            } 
        }
        if (count < max_count) {
            count++;
            num_threads_eating--;
        }

    }
}// end semaphore
