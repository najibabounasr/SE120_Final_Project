import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
public class Flight {

    private String flightNumber;
    private String origin;
    private String destination;
    private java.util.Date departureTime;
    private java.util.Date arrivalTime;
    private java.util.ArrayList <Seat> seat = new java.util.ArrayList <> ();
    
    // This constant helps us simulate realistic flight booking. we set all flights to a maximum occupancy of 180
    private static final int TOTAL_SEATS = 180; // There are 180 seats max for each flight. This works with demand. 
    private double demand;
    
    
    public Flight(String flightNumber, String origin, String destination) {
    
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        
    }
    
    public loadValidCities() {
        String file_path = "data.csv";
        String line;
        
        
    }
    
    public boolean getTimings(Date departureTime, Date arrivalTime) {
        
        
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