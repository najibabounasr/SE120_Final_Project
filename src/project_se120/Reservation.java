package project_se120;

import java.util.Date;

public class Reservation{

    private int reservationId;
    private java.util.Date bookingDate;
    private Passenger passenger;

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    
    private Flight flight;
    private Payment payment;
    
    
    public Reservation(int reservationId, java.util.Date bookingDate) {
    
        this.reservationId = reservationId;
        this.bookingDate = bookingDate;
        
    }
    

    
    public String getReservationDetails() {
        
        return "Reservation ID: " + reservationId + "\n" + passenger.getDetails() +"\nBooking Date: " + bookingDate + "\n " + flight;
    }
    
    

    
    public boolean cancel() {
    
        return false;
    }

}