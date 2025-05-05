package project_se120;

import java.util.*;
public class TestProgram {
    public static void main(String[] args) {
        try {
            // 1. Create base flight (null values because we’ll auto-generate timings)
            Flight baseFlight = new Flight("null", "Riyadh", "Dubai", null, null);

            // 2. Generate list of possible flights
            ArrayList<Flight> options = baseFlight.findTimings();

            // 3. Display them
            baseFlight.getTimings(options); // just prints

            // 4. Let user choose one
            Flight selected = baseFlight.chooseTiming(options);

            // 5. Populate seats for chosen flight
            Seats seatLoader = new Seats();
            ArrayList<Seat> populatedSeats = seatLoader.populateSeats(selected);

            // 6. Confirm
            System.out.println("\nYou selected: " + selected.toString());
            System.out.println("Total seats loaded: " + populatedSeats.size());

        } catch (InvalidCityException e) {
            System.out.println("Invalid route: " + e.getMessage());
        }
    }
}
