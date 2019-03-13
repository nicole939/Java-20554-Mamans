/*
 * this class takes a threaded process and uses it to check the current number if its a prime number or not
 * the result affects the numbers array and not the process.
 * uses the SyncProcess class to control the processes flow
 */
public class PrimeThread extends Thread {
    private SyncProcess process;
    private int limit_num;

    //Constructor receives the upper limit of the numbers to be checked and the SyncProcess object to control the different processes timing
    PrimeThread(int limit_num, SyncProcess process) {
        this.limit_num = limit_num;
        this.process = process;
    }

    @Override
    public void run() {
        int i;
        for(i = 2; i<limit_num; i++)
            // if the result of the division is a natural number, finish the process without changing the array
            if(limit_num % i == 0)
                break;
        // iterated through all the number from 2 to the current one and no division is possible
        if(i == limit_num)
            process.prime_array[i] = true;
        process.finished(); //finish the current threaded process and move on
    }
}
