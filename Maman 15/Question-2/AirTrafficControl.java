/*
 * the main class to run the entire program
 * everything is according to the directions in the booklet
 */
import java.util.Random;

public class AirTrafficControl
{
    public static void main (String[] args){

        final int FLIGHTS = 10; //the total number of flights
        final int RUNWAYS = 3; //number of runways in each airport
        Random random = new Random(); //to randomly pick the flight numbers
        Flight flight; //declare a Flight object to initialize all of the flights in the loop
        Airport first = new Airport("First Airport", RUNWAYS);
        Airport second = new Airport("Second Airport", RUNWAYS);

        /* initialize all the different flights between the airport */
        for (int i = 0 ; i < FLIGHTS ; i++) {
            if ( random.nextInt(20) > 10)
                flight = new Flight(i,first,second);
            else
                flight = new Flight(i,second,first);
            flight.start();
        }
    }
}