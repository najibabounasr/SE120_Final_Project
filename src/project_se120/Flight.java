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
    private java.util.Date departureTime;
    private java.util.Date arrivalTime;
    private java.util.ArrayList <Seat> seat = new java.util.ArrayList <> ();
    
    // We load an Array List of all the cities, and their latitude and longitude:
    private ArrayList<City> cities_list = cityloader.readCSV();
    
    // This constant helps us simulate realistic flight booking. we set all flights to a maximum occupancy of 180
    private static final int TOTAL_SEATS = 180; // There are 180 seats max for each flight. This works with demand. 
    private double demand;
    private boolean originValid;
    private boolean destinationValid;
    
    
    public Flight(String flightNumber, String origin, String destination) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        
        // Validation so we see that the City, for either origin or arrival actually exist in our 
        // csv mini-database of City names. 
        // Note: we understand not all cities have airports but this we decided this was the most interest way to validate. 
        // The user input for city names. 
        for (City city : cities_list) {
            // We use .toLowerCase so cities are not case-sensitive (better for user and tricky city names).
            if (city.getName().toLowerCase().equals(origin.toLowerCase())){
                this.origin = origin;
                this.originCity = new City(city.getName(),city.getLatitude(),city.getLongitude(),city.getCapital_status());
            }
            if (city.getName().toLowerCase().equals(destination.toLowerCase())){ 
                this.destination = destination;
                this.destinationCity = new City(city.getName(), city.getLatitude(),city.getLongitude(),city.getCapital_status());
            }
        }
            
            // We throw a 'InvalifCityException' which extends a RunTimeError which is unchecked. 
            // Because it is unchecked it is easier to throw, (no need for try catch blocks). 
            // But also allows us to make sure an object is not created if the user inputs invalid cities. 
            if (originValid) {
                throw (new InvalidCityException("We apologize but we have no available flights from " + origin));
            }
            if (destinationValid) {
                throw (new InvalidCityException("We apologize but we have no available flights to " + destination));
            }
            
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
        
        return (R * c);
    }

    
    public void getTimings() {
        double distance = getDistance();

        
    }

    
    public boolean checkAvailability() {     
    
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
        calendar.setTime(departureTime);
        // we set coefficients. based on travel time within the day.
        int day_of_week = calendar.get(Calendar.HOUR_OF_DAY);
        if (day_of_week < 3) {
            hour_coefficient = 0.4;
        }
        else if (day_of_week < 6) {
        hour_coefficient = 0.3;
    }
        else if (day_of_week < 9) {
            hour_coefficient = 0.9;
        }
        else if (day_of_week < 12) {
            hour_coefficient = 0.7;
        }
        else if (day_of_week < 15) {
            hour_coefficient = 1.0;
        }
        else if (day_of_week < 18) {
            hour_coefficient = 1.2; }
        else if (day_of_week < 21) {
            hour_coefficient = 1.5; }
        else if (day_of_week < 24) {
            hour_coefficient = 1.5; 
        }
        // We also set coefficients for day of week:
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                day_coefficient = 0.8;
                break;
            case Calendar.MONDAY:
                day_coefficient = 0.6;
                break;
            case Calendar.TUESDAY:
                day_coefficient = 0.7;
                break;
            case Calendar.WEDNESDAY:
                day_coefficient = 0.9;
                break;
            case Calendar.THURSDAY:
                day_coefficient = 1.2;
                break;
            case Calendar.FRIDAY:
                day_coefficient = 1.5;
                break;
            case Calendar.SATURDAY:
                day_coefficient = 1.1;
                break;
            default:
                break;
        }
        
        return (hour_coefficient * day_coefficient * Math.random());
    }
        
    
    public String flightOptions() {
        
        if (checkAvailability()) {
        
            for (int i = 0; i <= (int)((Math.random() * 2) + 3); i++) {};
            
        }
        
        return "";
    }
    
}