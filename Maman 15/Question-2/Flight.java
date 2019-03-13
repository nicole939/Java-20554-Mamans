/*
 * class to represent a flight
 * this class is set to be a thread in order to implements the different methods
 */
import java.util.Random;

public class Flight extends Thread {
    private Random rand = new Random();
    private int flight_number; // the flight number
    private Airport departure; // departing airport
    private Airport land; // landing airport

    /* constructor */
    Flight(int flight_number, Airport departure, Airport land) {
        this.flight_number = flight_number;
        this.departure = departure;
        this.land = land;
    }

    @Override
    public void run(){
        final int LAND = 3, DEPART = 3, FLY = 5;
        //depart from airport using depart method in Airport class
        int departRunway = departure.depart(flight_number);
        //simulate the departing duration
        simulateFlight(DEPART);
        //clear the runway after the departure
        departure.freeRunway(departRunway, flight_number);
        //simulate the flight duration
        simulateFlight(FLY);
        //land in airport using the land method in Airport class
        int landRunway = land.land(flight_number);
        //simulate the landing duration
        simulateFlight(LAND);
        //clear the runway after landing
        land.freeRunway(landRunway, flight_number);
    }

    /* method to delay the thread to simulate departure, flight and landing duration */
    private void simulateFlight(int time){
        try {
            sleep((rand.nextInt(time) + 2)*1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
