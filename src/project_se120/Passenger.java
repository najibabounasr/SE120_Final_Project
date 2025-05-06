package project_se120;

import java.util.*;
public class Passenger extends Person {

    private String passportNumber;
    private ArrayList<Reservation> reservation =  new ArrayList<>();
    
    
    public Passenger() {
    }
    
    public Passenger(String name, String email, String passportNumber) {
    
        super(name, email);
        this.passportNumber = passportNumber;   
    }

    @Override
    public String toString() {
        return "Name: " + super.getName() + ", Email: " + super.getEmail() + ", Passport Number: " + passportNumber;
    }
    
    

        
    @Override
    public String getDetails() {
    
        return super.getDetails();
    }

    
    public boolean bookFlight(int flightId) {
        
        System.out.println("Booking flight " + flightId);
        return true;     
    }
    
    
    public void addReservation(Reservation reservation) {
    
        this.reservation.add(reservation);
    }
    
}