/*
 * class to represent an airport for the flights to departure from or land in.
 * uses synchronized blocks the schedule the departures and landings in the airport
 */
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

class Airport {
    private LinkedBlockingQueue<Runway> queue = new LinkedBlockingQueue<>(); //queue implementation to save the flights by order
    private boolean[] runways; // represents the runway states. false = empty , true = occupied
    private String name; // the name of the airport
    private int number_runways; // the number of runways

    // constructor
    Airport(String name,int number_runways){
        this.name = name;
        this.number_runways = number_runways;
        runways = new boolean[number_runways];
    }

    /* method for the flight to depart from the airport */
    synchronized int depart(int flight_number){
        //in order to use the runway, the flight needs to be added to the queue
        queue.add(new Runway(flight_number,1));
        while (getRunway()== -1)
        {
            try{
                wait();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        int freeRunway = getRunway(); // get a runway for departure
        runways[freeRunway] = true; // mark the runway as occupied
        System.out.printf("--- Runway number %d in %s is occupied by the departure of flight number %d ---\n\n", freeRunway + 1, name, queue.remove().getFlightNum());
        return freeRunway; // return the number of the used runway
    }

    /* method for the flight to land at the airport */
    synchronized int land (int flight_number){
        queue.add(new Runway(flight_number,0));
        // if there is no free runway wait for one
        while ( getRunway() == -1 || Objects.requireNonNull(queue.peek()).getStatus()==1) {
            try{
                wait();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        int freeRunway = getRunway();
        runways[freeRunway] = true;
        System.out.printf("--- Runway number %d in %s is occupied by the lading of flight number %d ---\n\n", freeRunway+1, name, flight_number);
        return freeRunway;
    }

    /* clears the given runway and notifies waiting threads */
    synchronized void freeRunway(int run_num, int flight_number){
            runways[run_num] = false; //set the runway to display free
            System.out.printf("--- Runway number %d in %s is cleared from flight %d ---\n\n", run_num+1, name, flight_number);
            notifyAll();
    }

    /* method to return the number of the free runway in their array if there is no free runways return -1 */
    private int getRunway(){
        for (int i = 0 ; i < number_runways ; i++)
            if ( !runways[i])
                return i;
        return -1;
    }
    /*
     * inner class to create a Runway object
     * this way an occupied runway includes the occupying flight number and its status
     */
    class Runway {
        private int flight_number; //the occupying flight number
        private int status; // 1 represents departure and 0 represents arrival

        //constructor
        Runway(int flight_number,  int status) {
            this.flight_number = flight_number;
            this.status = status;
        }
        /* method to return the occupying flight number */
        int getFlightNum() {
            return flight_number;
        }
        /* method to return the status of the flight, departure or land */
        int getStatus() {
            return status;
        }
    }
}