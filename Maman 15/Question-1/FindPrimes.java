/*
 * the main program to run the classes.
 * it finds all the prime numbers between 1 and 100 and prints all of them in the end
 */
public class FindPrimes {
    public static void main(String[] args)
    {
        final int N = 10, M = 1000;
        SyncProcess c = new SyncProcess(N, M);
        //0 and 1 are not prime numbers so the process must start with 2
        for(int i=2; i < c.prime_array.length; i++) {
            c.waitForThread();
            (new PrimeThread(i, c)).start();
        }
        c.waitForAll();
        System.out.println(c.printPrimes());
    }
}
