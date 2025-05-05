/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_se120;

/**
 *
 * @author najibabounasr
 */
import java.util.*;
public class Seats {
        
    // Constructor paramaters for Seat:
    private String seatNumber;
    private String classType;
    private Flight flight;
    
    public ArrayList<Seat> createSeats(Flight flight) {
        ArrayList<Seat> seats_list = new ArrayList<>();
        for (int row = 1; row <= 30; ++row) {
            // We first set the class type dynamically based on the row number
            if (row <=3) {
                classType = "First";   
            }
            else if (row <= 10) {
                classType = "Business";
            }
            else {
                classType = "Economy";
            }
            for (char column = 'A';  column <=  'F'; ++column) {
                String seatNumber = row + String.valueOf(column);
                seats_list.add(new Seat(seatNumber,classType,flight));   
            }
        }
        return (seats_list);
    }
    
    
    public void occupySeats(ArrayList<Seat> seats,Date departureTime,double demand  ) {
        // pass-by-reference so we do not need to use a return 
        // value
        Date current_date = new Date();
        long time_until_departure = (departureTime.getTime() - current_date.getTime());
        double milliseconds_in_week = 6.048e8;
        double time_coefficient = time_until_departure / milliseconds_in_week;
        
        for (Seat seat :  seats) {
            if (Math.random() > (time_coefficient * demand *0.25)) {
                seat.setBookingStatus(true);
            }
        }
    }
    public int getAvailableSeats(ArrayList<Seat> seats){
        int seats_available = 180;
        for (Seat seat: seats) {
            if (seat.isBooked()) {
                seat.toString();
                --seats_available;
        }
    }    return (seats_available);
    }
    
}
