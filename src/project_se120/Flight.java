package project_se120;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Flight {

    private String flightNumber;
    private String origin;
    private String destination;

    // Extra utility instances, used and created just for Flight
    // There is a composition relationship between City, Cities and Flight
    // Because City and Cities were created just to be used in the Flight class. 
    private City originCity;
    private City destinationCity;
    private Cities cityloader = new Cities();
    private Seats seat_loader = new Seats();
    private java.util.Date departureTime;
    private java.util.Date arrivalTime;
    private FlightManager flight_manager;
    // ArrayLists:
    // 1. ArrayList to store Seat objects:
    public java.util.ArrayList<Seat> seats = new java.util.ArrayList<>();

    // 2. ArrayList to store City objects:
    // We load an Array List of all the cities, and their latitude and longitude:
    private ArrayList<City> cities_list = cityloader.readCSV();

    // 3. ArrayList to store Flight Objects:
//    private ArrayList<Flight> flights_list = new ArrayList<>(); // Moved to 'Airline'
    // This constant helps us simulate realistic flight booking. we set all flights to a maximum occupancy of 180
    private static final int TOTAL_SEATS = 180; // There are 180 seats max for each flight. This works with demand. 
    private double demand;
    private double distance;
    private boolean originValid;
    private boolean destinationValid;

    public Flight(String flightNumber, String origin, String destination, Date departureTime, Date arrivalTime, FlightManager flight_manager) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flight_manager = flight_manager;
        // Validation so we see that the City, for either origin or arrival actually exist in our 
        // csv mini-database of City names. 
        // Note: we understand not all cities have airports but this we decided this was the most interest way to validate. 
        // The user input for city names. 
        // NOTE: WE CAN DO BINARY SEARCH HERE:
        for (City city : cities_list) {
            // We use .toLowerCase so cities are not case-sensitive (better for user and tricky city names).
            if (city.getName().trim().replace("\"", "").equalsIgnoreCase(origin.trim().replace("\"", ""))) {
                this.origin = origin;
                this.originCity = new City(city.getName(), city.getLatitude(), city.getLongitude(), city.getCapitalStatus());
                originValid = true;
            }
            if (city.getName().trim().replace("\"", "").equalsIgnoreCase(destination.trim().replace("\"", ""))) {
                this.destination = destination;
                this.destinationCity = new City(city.getName(), city.getLatitude(), city.getLongitude(), city.getCapitalStatus());
                destinationValid = true;
            }
        }
        // We throw a 'InvalifCityException' which extends a RunTimeError which is unchecked. 
        // Because it is unchecked it is easier to throw, (no need for try catch blocks). 
        // But also allows us to make sure an object is not created if the user inputs invalid cities. 
        if (!originValid) {
            throw (new InvalidCityException("We apologize but we have no available flights from " + origin));
        } else if (!destinationValid) {
            throw (new InvalidCityException("We apologize but we have no available flights to " + destination));
        }

        this.seats = seat_loader.createSeats(this);
        seat_loader.occupySeats(this.seats, this.departureTime, flight_manager.getDemand(this.departureTime));

        int available = seat_loader.getAvailableSeats(this.seats);
        for (Seat seat : this.seats) {
            seat.calculateSeatPrice(available, seat.getClassType());
        }

    }

    @Override
    public String toString() {
        return (". Flight: " + this.flightNumber
                + " | Departure: " + this.departureTime
                + " | Arrival: " + this.arrivalTime
                + " | From: " + this.origin
                + " | To: " + this.destination);
    }
    
    public String toStringForFile() {
    
        return this.flightNumber + "~" + this.departureTime + "~" + this.arrivalTime + "~" + this.origin + "~" + this.destination;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public City getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(City destinationCity) {
        this.destinationCity = destinationCity;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getDistance() {
        // We have to perform the 'Haversine formula' to complete the 
        // calculation using latitude and longitude.
        final int R = 6371;

        // Save change in lat and longitude. 
        double change_lat = this.destinationCity.getLatitude() - this.originCity.getLatitude();
        double change_long = this.destinationCity.getLongitude() - this.originCity.getLongitude();

        // Applying haversine formula to calculate distance:
        double a = Math.sin(change_lat / 2) * Math.sin(change_lat / 2)
                + Math.cos(this.originCity.getLatitude()) * Math.cos(this.destinationCity.getLatitude())
                * Math.sin(change_long / 2) * Math.sin(change_long / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        this.distance = R * c;
        return (R * c);
    }

    public void getTimings(ArrayList<Flight> flights_list) {
        int counter = 0;
        for (Flight flight : flights_list) {
            // Output:
            System.out.println((counter + 1) + flight.toString());
            ++counter;
        }
    }

    public boolean checkAvailability() {
        // Tell user if flight is available (has seats)
        return true;

    }

    public City getOriginCity() {
        return originCity;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setOriginCity(City originCity) {
        this.originCity = originCity;
    }

    public String flightOptions() {

        if (checkAvailability()) {

            for (int i = 0; i <= (int) ((Math.random() * 2) + 3); i++) {
            };
            return "Flight " + flightNumber + " is available.";

        } else {
            return "Flight not available.";
        }
    }

}
