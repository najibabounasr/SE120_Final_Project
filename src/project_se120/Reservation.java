/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_se120;
import java.util.Date;

public class Reservation {

    private int reservationId;
    private java.util.Date bookingDate;
    private Passenger passenger;
    private Seat seat;

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

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
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

    // We use this constructor to avoid redundant calls to other set methods.
    public Reservation(int reservationId, java.util.Date bookingDate, Flight flight, Passenger passenger, Seat seat) {

        this.reservationId = reservationId;
        this.bookingDate = bookingDate;
        this.flight = flight;
        this.passenger = passenger;
        this.seat = seat;

    }

    // This was the original constructor Dr. Sarra showed in the UML (still exists):
    public Reservation(int reservationId, java.util.Date bookingDate) {

        this.reservationId = reservationId;
        this.bookingDate = bookingDate;

    }

    public String getReservationDetails() {

        return "|Reservation Details|\nReservation ID: " + reservationId + "\n" + passenger.getDetails() + "\nBooking Date: " + bookingDate + "\n Passenger" + passenger.toString() + "\n\nFlight: " + flight.toString() + "\nSeat: " + seat.toString();
    }
    
    public String getReservationDetailsForFile() {
    
        return reservationId + "~" + passenger.getDetailsForFile() + "~" + bookingDate + "~" + passenger.toStringForFile() + "~" + flight.toStringForFile() + "~" + seat.toStringForFile();
    }

    public static int generateReservationId() {
        return (int) (100000 - (Math.random() * 10000));
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + "\nBooking Date: " + bookingDate + "\n" + passenger + "\n" + seat + "\n" + flight;
    }

    public boolean cancel() {

        return false;
    }

}
