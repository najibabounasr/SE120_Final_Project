package project_se120;
import java.util.*;

public class FlightManager {

    // Creating instance
    private Flight flight;

    // objcts and object lists
    private ArrayList<Flight> flights_list = new ArrayList<>();
    private Cities cityloader;
    private Seats seat_loader;
    ArrayList<City> cities_list;

    //primitive types
    double demand;
    double distance;

    // datetime
    Date departureTime;
    Date arrivalTime;

    // Strings
    String flightNumber;

    public ArrayList<Flight> findTimings(Flight flight) {
        // Give time-to-arrival
        this.distance = flight.getDistance();

        // We call the getFlightsCoefficient method to get the coefficient based on distance from location. 
        double distanceBoost = this.getFlightsCoefficient(distance);

        // We initialize a capital boost variable to store the boost given based on city status.
        double capitalBoost = 1.0;

        // We use getter methods to calculate the capital coefficient. 
        double capitalBoostOrigin = this.getCapitalCoefficient(flight.getOriginCity().getCapitalStatus());
        double capitalBoostDestination = this.getCapitalCoefficient(flight.getDestinationCity().getCapitalStatus());

        // We combine both coefficients together to create a single capitalBoost
        capitalBoost = capitalBoostOrigin * capitalBoostDestination;

        int flightsPerDay = (int) (capitalBoost * distanceBoost);
        // We use Calendar objects to simulate real-life timings. 
        Calendar calendar;
        int hour, minute;
        double demand;

        // We need to return:
        // 1. Seat number(s)
        // 2. Flight number(s)
        // 3. Arrival and destination times
        // 4. Class Available
        // This will becomne the populateFlights method:  
        // We initialize departureTime;
        int counter = 0;
        Calendar base_calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        base_calendar.add(Calendar.HOUR_OF_DAY, 0);
        base_calendar.add(Calendar.MINUTE, 0);
        base_calendar.add(Calendar.SECOND, 0);
        base_calendar.set(Calendar.MILLISECOND, 0);
        System.out.println("\nNote: All flight timings are in Greenwich Standard Time (GMT)");
        System.out.println("||Please select a flight number from those listed on the left||");
        for (int day = 0; day < 7; ++day) {
            for (int i = 0; i < flightsPerDay; ++i) {
                // Create a fresh character object. 
                // We understand this takes some memory but it is also the easiest solution
                // We standardize the time-zone because it will be too difficult to adapt to time-zones. 
                calendar = (Calendar) base_calendar.clone();
                // 
                hour = (int) (Math.random() * 24);
                int[] fifteens = {0, 15, 30, 45};
                minute = fifteens[(int) (Math.random() * 4)];
                // We add to the time to simulate the schedule filling up. 
                // We safely move across the days
                calendar.add(Calendar.DAY_OF_YEAR, day);
                // we simulate random hours
                calendar.set(Calendar.HOUR_OF_DAY, (int) (Math.random() * 24));
                // We even simulate random minutes. 
                calendar.set(Calendar.MINUTE, new int[]{0, 15, 30, 45}[(int) (Math.random() * 4)]);
                calendar.set(Calendar.SECOND, 0);

                departureTime = calendar.getTime();
                calendar.add(Calendar.HOUR_OF_DAY, getFlightDurationHours(flight.getDistance()));
                arrivalTime = calendar.getTime();
                demand = getDemand(departureTime);

                if (Math.random() < (demand)) {
                    // There may be duplicate flight numbers, though this is unlikely and 
                    // is therefore not handled. 
                    flightNumber = generateFlightNumber();
                    flights_list.add(new Flight(flightNumber, flight.getOrigin(), flight.getDestination(), departureTime, arrivalTime, this));
                }

            }
        }
        return (flights_list);
    }

    public void getTimings() {
        int counter = 0;
        for (Flight flight : this.flights_list) {
            // Output:
            System.out.println((counter + 1) + this.flights_list.toString());
            ++counter;
        }
    }

    public void getTimings(ArrayList<Flight> flights_list) {
        int counter = 0;
        for (Flight flight : flights_list) {
            // Output:
            System.out.println((counter + 1) + flight.toString());
            ++counter;
        }
    }

    public Flight chooseTiming() {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the flight number: ");
        int index = input.nextInt() - 1;
        return (this.flights_list.get(index));
    }

    private int getFlightDurationHours(double distance) {
        // Average speed of a plane our size in km / h :
        // formula returns duration in hours.
        return (int) Math.round(distance / 900.0);
    }

    private static String generateFlightNumber() {
        // Initialize
        String[] airlines = new String[]{"SA", "BA", "GF", "AA", "BD", "TA", "RJ", "BC"};
        int num;
        // We need to generate realistic flight numbers:
        airlines = new String[]{"SA", "BA", "GF", "AA", "BD", "TA", "RJ", "BC"};

        num = (int) (Math.random() * 1000);
        // We return a concatenated string with the Airline name and Number generated randomly
        return (airlines[(int) (Math.random() * airlines.length)] + num);
    }

    public Flight chooseTiming(ArrayList<Flight> flights_list) {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the flight number: ");
        int index = input.nextInt() - 1;
        return flights_list.get(index);
    }

    // Simulates real-life demand using Math.random() and flight timing.  
    double getDemand(java.util.Date date) {
        // we have hour and day coefficients, to adjust number of seats based on time of week 
        // and time of day. This simulates demand. Because certain times of the week have more demand than others. 
        double hour_coefficient = 0, day_coefficient = 0;
        // We use calendar to easily access the date object's contents and use it 
        // to simulate demand for flights. 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // we set coefficients. based on travel time within the day.
        int hour_of_day = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour_of_day < 4) {
            hour_coefficient = 0.2;
        } else if (hour_of_day < 5) {
            hour_coefficient = 0.3;
        } else if (hour_of_day < 7) {
            hour_coefficient = 0.7;
        } else if (hour_of_day < 9) {
            hour_coefficient = 1.3;
        } else if (hour_of_day < 11) {
            hour_coefficient = 1.0;
        } else if (hour_of_day < 14) {
            hour_coefficient = 0.8;
        } else if (hour_of_day < 16) {
            hour_coefficient = 1.1;
        } else if (hour_of_day < 19) {
            hour_coefficient = 1.4;
        } else if (hour_of_day < 21) {
            hour_coefficient = 1.0;}
        else {
            hour_coefficient = 0.5;}
        
        // We also set coefficients for day of week:
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == Calendar.SATURDAY) {
            day_coefficient = 1.1;
        } else if (dayOfWeek == Calendar.SUNDAY) {
            day_coefficient = 0.8;
        } else if (dayOfWeek == Calendar.MONDAY) {
            day_coefficient = 0.6;
        } else if (dayOfWeek == Calendar.TUESDAY) {
            day_coefficient = 0.7;
        } else if (dayOfWeek == Calendar.WEDNESDAY) {
            day_coefficient = 0.9;
        } else if (dayOfWeek == Calendar.THURSDAY) {
            day_coefficient = 1.2;
        } else if (dayOfWeek == Calendar.FRIDAY) {
            day_coefficient = 1.4;
        }
        // We return 
        return (hour_coefficient * day_coefficient * Math.random());
    }

    private double getCapitalCoefficient(String status) {
        if (status.toLowerCase().equals("primary")) {
            return 1.5;
        } else if (status.toLowerCase().equals("admin")) {
            return (1.2);
        } else if (status.toLowerCase().equals("minor")) {
            return (0.8);

        } else {
            return (1.0);
        }

    }

    private double getFlightsCoefficient(double distance) {
        // We take flight distance into consideration
        // for number of flights per-day
        if (distance < 800.0) {
            return 8.0;
        } else if (distance < 3000.0) {
            return 5.0;
        } else if (distance < 6000.0) {
            return 2.0;
        } else {
            return 1.0;
        }
    }

    public void displayAvailableSeats(ArrayList<Seat> seats, Date departureTime, double demand, Flight flight) {
        System.out.println("\nSeats available on flight" + flight);
        for (Seat seat : seats) {
            if (seat.isBooked()) {
                System.out.println(seat.toString());

            }
        }
    }

}
