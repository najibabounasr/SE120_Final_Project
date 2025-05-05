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
    
    // ArrayLists:
    // 1. ArrayList to store Seat objects:
    private java.util.ArrayList <Seat> seats = new java.util.ArrayList <> ();
    
    // 2. ArrayList to store City objects:
    // We load an Array List of all the cities, and their latitude and longitude:
    private ArrayList<City> cities_list = cityloader.readCSV();
    
    // 3. ArrayList to store Flight Objects:
    private ArrayList<Flight> flights_list = new ArrayList<>();
    // This constant helps us simulate realistic flight booking. we set all flights to a maximum occupancy of 180
    private static final int TOTAL_SEATS = 180; // There are 180 seats max for each flight. This works with demand. 
    private double demand;
    private double distance;
    private boolean originValid;
    private boolean destinationValid;
    
    
    public Flight(String flightNumber, String origin, String destination, Date departureTime, Date arrivalTime) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;  
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        // Validation so we see that the City, for either origin or arrival actually exist in our 
        // csv mini-database of City names. 
        // Note: we understand not all cities have airports but this we decided this was the most interest way to validate. 
        // The user input for city names. 
        // NOTE: WE CAN DO BINARY SEARCH HERE:
        for (City city : cities_list) {
            // We use .toLowerCase so cities are not case-sensitive (better for user and tricky city names).
            if (city.getName().trim().replace("\"", "").equalsIgnoreCase(origin.trim().replace("\"", ""))){
                this.origin = origin;
                this.originCity = new City(city.getName(),city.getLatitude(),city.getLongitude(),city.getCapitalStatus());
                originValid = true;
            }
            if (city.getName().trim().replace("\"", "").equalsIgnoreCase(destination.trim().replace("\"", ""))){ 
                this.destination = destination;
                this.destinationCity = new City(city.getName(), city.getLatitude(),city.getLongitude(),city.getCapitalStatus());
                 destinationValid = true;
            }
        }
            // We throw a 'InvalifCityException' which extends a RunTimeError which is unchecked. 
            // Because it is unchecked it is easier to throw, (no need for try catch blocks). 
            // But also allows us to make sure an object is not created if the user inputs invalid cities. 
            if (!originValid) {
                throw (new InvalidCityException("We apologize but we have no available flights from " + origin));
            }
            if (!destinationValid) {
                throw (new InvalidCityException("We apologize but we have no available flights to " + destination));
            }
            
            this.seats = seat_loader.createSeats(this);
            seat_loader.occupySeats(this.seats, this.departureTime, getDemand(this.departureTime));

            
        }

    @Override
    public String toString() {
        return (". Flight: " + this.flightNumber +
                    " | Departure: " + this.departureTime +
                    " | Arrival: " + this.arrivalTime +
                    " | From: " + this.origin +
                    " | To: " + this.destination);
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
    
    
        
        
    
    
    public double getDistance() {
        // We have to perform the 'Haversine formula' to complete the 
        // calculation using latitude and longitude.
        final int R = 6371;
        
        // Save change in lat and longitude. 
        double change_lat = this.destinationCity.getLatitude() - this.originCity.getLatitude();
        double change_long = this.destinationCity.getLongitude() - this.originCity.getLongitude();
        
        // Applying haversine formula to calculate distance:
        double a = Math.sin(change_lat / 2) * Math.sin(change_lat / 2) +
               Math.cos(this.originCity.getLatitude()) * Math.cos(this.destinationCity.getLatitude()) *
               Math.sin(change_long / 2) * Math.sin(change_long / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        this.distance = R *c;
        return (R * c);
    }
    
    private double getCapitalCoefficient(String status) {
        if (status.toLowerCase().equals("primary")) {
            return 1.5; }
        else if (status.toLowerCase().equals("admin")) {
            return (1.2);
                    }
        else if (status.toLowerCase().equals("minor")) {
            return (0.8);
                    
            }
        else {
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
    
    public void getTimings(ArrayList<Flight> flights_list) {
        int counter = 0;
        for (Flight flight : flights_list) {
            // Output:
            System.out.println((counter +1) + flight.toString());
            ++counter; }
    }
    
    public void getTimings() {
        int counter = 0;
        for (Flight flight : this.flights_list) {
            // Output:
            System.out.println((counter +1) + this.flights_list.toString());
            ++counter; }
    }
    
    
    public Flight chooseTiming() {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the flight number: ");
        int index = input.nextInt() - 1;
        return (this.flights_list.get(index - 1));
    }
    
    public Flight chooseTiming(ArrayList<Flight> flights_list) {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the flight number: ");
        int index = input.nextInt() - 1;
        return flights_list.get(index);
    }
    
    public void displayAvailableSeats(ArrayList<Seat> seats,Date departureTime,double demand , Flight flight) {
        System.out.println("\nSeats available on flight" + flight );
        for (Seat seat: seats) {
            if (seat.isBooked()) {
                seat.toString();
                
        }
        }
    }
    
    
    
    public ArrayList<Flight> findTimings() {
        // Give time-to-arrival
        this.distance = getDistance();
        
       // We call the getFlightsCoefficient method to get the coefficient based on distance from location. 
       double distanceBoost = this.getFlightsCoefficient(distance);
    
       // We initialize a capital boost variable to store the boost given based on city status.
       double capitalBoost = 1.0;
       
       // We use getter methods to calculate the capital coefficient. 
       double capitalBoostOrigin = this.getCapitalCoefficient(originCity.getCapitalStatus());
       double capitalBoostDestination = this.getCapitalCoefficient(destinationCity.getCapitalStatus());
       
       // We combine both coefficients together to create a single capitalBoost
       capitalBoost = capitalBoostOrigin * capitalBoostDestination;
       
       
       int flightsPerDay = (int) (capitalBoost * distanceBoost) ;
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
       System.out.println("Note: All flight timings are in Greenwich Standard Time (GMT)");
       System.out.println("Please select flight number from the left.");
       for (int day = 0; day < 7; ++day) {
         for (int i = 0; i <flightsPerDay; ++i) {
             // Create a fresh character object. 
             // We understand this takes some memory but it is also the easiest solution
             // We standardize the time-zone because it will be too difficult to adapt to time-zones. 
             calendar = (Calendar) base_calendar.clone();
             // 
             hour = (int) (Math.random() * 24);
             int[] fifteens = {0,15, 30, 45};
             minute = fifteens[(int)(Math.random() * 4)];
             // We add to the time to simulate the schedule filling up. 
             // We safely move across the days
            calendar.add(Calendar.DAY_OF_YEAR, day); 
            // we simulate random hours
            calendar.set(Calendar.HOUR_OF_DAY, (int) (Math.random() * 24));
            // We even simulate random minutes. 
            calendar.set(Calendar.MINUTE, new int[]{0, 15, 30, 45}[(int) (Math.random() * 4)]);
            calendar.set(Calendar.SECOND, 0);

             
             departureTime = calendar.getTime();
             calendar.add(Calendar.HOUR_OF_DAY, getFlightDurationHours(this.getDistance()));
             arrivalTime = calendar.getTime();
             demand = getDemand(departureTime);
             
             if (Math.random() < (demand )) {
                 // There may be duplicate flight numbers, though this is unlikely and 
                 // is therefore not handled. 
                 flightNumber = generateFlightNumber();
                 flights_list.add(new Flight(flightNumber,origin,destination,departureTime,arrivalTime));
             }
             
             
             

             
     } }return (flights_list); }
    
    private int getFlightDurationHours(double distance) {
        // Average speed of a plane our size in km / h :
            // formula returns duration in hours.
            return (int) Math.round(distance / 900.0);
    }
    
    private static String generateFlightNumber() {
               // Initialize
                String[] airlines = new String[]{"SA","BA", "GF", "AA", "BD", "TA", "RJ", "BC"};
                int num;
               // We need to generate realistic flight numbers:
               airlines = new String[]{"SA","BA", "GF", "AA", "BD", "TA", "RJ", "BC"};
              
               num = (int) (Math.random() * 1000);
               // We return a concatenated string with the Airline name and Number generated randomly
              return (airlines[(int) (Math.random() * airlines.length)] + num);
             }
    
    

    
    public boolean checkAvailability() {     
        // Tell user if flight is available (has seats)
        return true;
        
    }
    
   

    
    // Simulates real-life demand using Math.random() and flight timing.  
    private double getDemand(java.util.Date date) {
        // we have hour and day coefficients, to adjust number of seats based on time of week 
        // and time of day. This simulates demand. Because certain times of the week have more demand than others. 
        double hour_coefficient = 0, day_coefficient = 0;
        // We use calendar to easily access the date object's contents and use it 
        // to simulate demand for flights. 
        Calendar calendar = Calendar.getInstance();
        departureTime = date;
        calendar.setTime(departureTime);
        // we set coefficients. based on travel time within the day.
        int hour_of_day = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour_of_day < 3) {
            hour_coefficient = 0.4;
        }
        else if (hour_of_day < 6) {
        hour_coefficient = 0.3;
    }
        else if (hour_of_day < 9) {
            hour_coefficient = 0.9;
        }
        else if (hour_of_day < 12) {
            hour_coefficient = 0.7;
        }
        else if (hour_of_day < 15) {
            hour_coefficient = 1.0;
        }
        else if (hour_of_day < 18) {
            hour_coefficient = 1.2; }
        else if (hour_of_day < 21) {
            hour_coefficient = 1.5; }
        else if (hour_of_day < 24) {
            hour_coefficient = 1.5; 
        }
        // We also set coefficients for day of week:
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        
        if (dayOfWeek == Calendar.SATURDAY) {
            day_coefficient = 1.1; }
        else if (dayOfWeek == Calendar.SUNDAY) {
            day_coefficient = 0.8;
    } 
        else if (dayOfWeek == Calendar.MONDAY) {
            day_coefficient = 0.6;
    } 
        else if (dayOfWeek == Calendar.TUESDAY) {
            day_coefficient = 0.7;
    }
        else if (dayOfWeek == Calendar.WEDNESDAY) {
            day_coefficient = 0.9;
    }
        else if (dayOfWeek == Calendar.THURSDAY) {
            day_coefficient = 1.2; }
        else if (dayOfWeek == Calendar.FRIDAY) {
                day_coefficient = 1.5;
        }
        // We return 
        return (hour_coefficient * day_coefficient * Math.random());
    }
        
    
    public String flightOptions() {
        
        if (checkAvailability()) {
        
            for (int i = 0; i <= (int)((Math.random() * 2) + 3); i++) {};
            return "Flight " + flightNumber + " is available.";
            
        }
        else 
        
        return "Flight not available.";
    }
    
}
