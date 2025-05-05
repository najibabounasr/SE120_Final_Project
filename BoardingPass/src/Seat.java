package project_se120;

public class Seat {
    
    private String seatNumber;
    private String classType;
    
    private Flight flight;
    
    private boolean bookingStatus;
    

    public Seat(String seatNumber, String classType,Flight flight) {
        this.seatNumber = seatNumber;
        this.classType = classType;
        this.flight = flight;
    }
    
    public void assignSeat(String passenger) {
    
        System.out.println(seatNumber + " " + classType);
        
    }
    
    public String getRow() {
        //  Used regex to take out the alphabetic part
        return this.seatNumber.replaceAll("[^0-9]", "");
    }
    
    public String getColumn() {
        return this.seatNumber.replaceAll("[0-9]"," ");
    }
    

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public boolean isBooked() {
        return bookingStatus;
    }

    public void setBookingStatus(boolean bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String toString() {
        return "Seat number: "+ seatNumber + ", Class: " + classType ;
    }
    
    
    
}