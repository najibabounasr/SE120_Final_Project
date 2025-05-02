public class Reservation{

    private int reservationId;
    private java.util.Date bookingDate;
    private Passenger passenger;
    
    private Flight flight;
    private Payment payment;
    
    
    public Reservation(int reservationId, java.util.Date bookingDate) {
    
        this.reservationId = reservationId;
        this.bookingDate = bookingDate;
        
    }
    

    
    public String getReservationDetails() {
        
        return "Reservation ID: " + reservationId + "\n" + passenger.getDetails() +"\nBooking Date: " + bookingDate + "\nFlight: " + flight;
    }
    
    

    
    public boolean cancel() {
    
        return false;
    }

}