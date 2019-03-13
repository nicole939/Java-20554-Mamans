/*
 * controls the threads to identify the prime numbers.
 * uses synchronized blocks the schedule the threaded processes
 */

class SyncProcess {

    private int max_threads, running, passed_nums;
    // as suggested in the forum, a boolean array marks each number as a prime or not (true or false)
    boolean[] prime_array;

    SyncProcess(int n, int m) {
        max_threads = n; //maximum possible threaded processes to run simultaneously
        running = 0; //to track the currently active processes
        passed_nums = 0; //counter to go over all of the numbers
        prime_array = new boolean[m + 1];
        //initialize all the array cells to false
        for (int i = 0; i < prime_array.length; i++)
            prime_array[i] = false;

    }

    /*
     * method to make sure only n threaded processes operate simultaneously
     * if there are already n processes operating, wait for an opening
     */
    synchronized void waitForThread() {
        while (running == max_threads) {
            //handle the exception
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /* when a threaded process finishes handling a number, update the other threads to continue */
    synchronized void finished() {
        running--; //update the number of currently running threads
        passed_nums++; //move on to the next number
        notifyAll(); //alert the other threads
    }

    /* wait until all the threaded processes are finished */
    synchronized void waitForAll() {
        while (passed_nums < prime_array.length - 2) {
            //handle the exception
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * method to print the prime numbers
     * there's a loop to iterate through the array and add to the returned string all the key cells with the value 'true'
     */
    StringBuilder printPrimes() {
        StringBuilder str = new StringBuilder();
        for (int i = 2; i < prime_array.length; i++)
            // print only the prime numbers in the array (the ones marked as true)
            if (prime_array[i]) {
                str.append(i);
                str.append("\n");
            }
        return str;
    }
}