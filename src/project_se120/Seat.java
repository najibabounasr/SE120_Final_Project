package project_se120;

public class Seat {
    
    private String seatNumber;
    private String classType;
    
    private Flight flight;
    
    private boolean bookingStatus;
    private double seatPrice;
    private double price;
    
    public Seat(String seatNumber, String classType,Flight flight) {
        this.seatNumber = seatNumber;
        this.classType = classType;
        this.flight = flight;
        // price still needs to be figured out by the system.
        // we assign price after constructor in the calculateSeatPrice method. 
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
        return "Seat number: "+ seatNumber + ", Class: " + classType + ", Price: $" + price ;
    }
    
    public void calculateSeatPrice(int seats_available, String classType) {
        double base_coefficient, availability; 
        if (classType.equals("First")) {
            base_coefficient = 1500;
        } 
        else if (classType.equals("Business")) {
            base_coefficient = 900;
        }
        else {
            base_coefficient = 400;
        }
        
        availability = (double)((1.5-(Math.random()/4)) - seats_available / 180);
        this.price = Math.round(base_coefficient * availability);
    }
    
}