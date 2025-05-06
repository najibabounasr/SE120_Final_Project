/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package project_se120;

/**
 *
 * @author najibabounasr
 */
// import dependencies
import java.util.*;
// For boarding pass
import java.io.*;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Seats seat_loader = new Seats();
        ArrayList<Seat> seats = new ArrayList<>();
        ArrayList<Flight> flights;
        FlightManager flightmanager = new FlightManager();
        Scanner input = new Scanner(System.in);
        CreditCardPayment credit_card = null;
        Reservation reservation;
        Employee employee = new Employee("Jackie Brown","jackie_brown@caboair.org",1997,"Flight Attendant");
        
        String origin;
        String destination;
        
        // for triggering logic that repeats the program, like reselecting seats etc. 
        boolean continue_choice = true;
        String choice = "";
        int seatIndex;
        int reservationId;
        
        // 1. First take user input and create Passenger object 
        // we save many characters that are not allowed for a first name
        String invalidInputName = ".*[0-9!\\{/:@#$%\\^&*\\(\\)_\\-\\+\\=\\;].*";
        System.out.print("Enter your first name: ");
        String first_name = input.nextLine();
        while (first_name.matches(invalidInputName)) {
            System.out.print("Invalid name used, please re-enter without invalid characters: ");
            first_name = input.nextLine();
        }
        System.out.print("Enter your last name: ");
        String last_name = input.nextLine();
        while (last_name.matches(invalidInputName)) {
            System.out.print("Invalid name used, please re-enter without invalid characters: ");
            last_name = input.nextLine();
        }
        String name = first_name + " " + last_name; 
        // This regex pattern should catch all invalidEmails
        
        String validEmail = ".*[A-Za-z0-9._]+@[A-Za-z]+\\.(com|org|net|gov|sa|co)$";
        System.out.print("Enter your email: ");
        String email = input.nextLine();
        while (!email.matches(validEmail)) {
            System.out.print("Incorrect email. Please enter a valid email: ");
            email = input.nextLine();
        }
            
        String validPassport = "^[A-Z][0-9]";
        System.out.print("Enter your passport number: ");
        // Must be 6 digits +letters and start with letters
        String passport = input.nextLine();
        while ((passport.matches((validPassport)) && (passport.length() == 6))) {
                    System.out.print("Invalid passport number. Enter your passport number: ");
                    passport = input.nextLine();
        }
        
        Passenger passenger = new Passenger(name, email, passport);
        System.out.println();
        try {
        // 2. Then you should take origin and destination and call FlightManage's findTimings(Flight flight) method
        // populate with some dummy values
        
        System.out.print("Enter the origin city: ");
        origin = input.nextLine();
        System.out.print("Enter the destination city: ");
        destination = input.nextLine();
        System.out.println();
        
        Flight flight = new Flight("DUMMY", origin, destination, new Date(), new Date(), flightmanager);
        
        flights = flightmanager.findTimings(flight);
        
        int i =0;
        for (Flight f : flights) {
            System.out.print(++i);
            System.out.println(f.toString());
        }
        
        int flightIndex;
        double demand = flightmanager.getDemand(flight.getDepartureTime());
        
        
        
        // We repeat the whole logic if seat is too expensive/ above the limit : 
        // This can be used if we want to add more detail to the payment function. For example allowing user to 
        // choose multiple seats or flights. Although this is out of scope for this project. 
        do {
            System.out.println();
        do {
        System.out.print("Enter Flight Number: ");
        flightIndex = input.nextInt();
        seats = seat_loader.createSeats(flights.get(flightIndex-1));
        seat_loader.occupySeats(seats,flights.get(flightIndex-1).getDepartureTime(), demand);
        System.out.println("Available seats: "+ seat_loader.getAvailableSeats(seats));
        
        if (seat_loader.getAvailableSeats(seats) <= 0) {
            System.out.println("Sorry, the flight you selected has no available seats.\n");
        }
        
        else {
            System.out.println("Would you like to proceed with flight " + flights.get(flightIndex-1).getFlightNumber()+ "? [Press any key to continue, or 'C' to cancel]");
            input.nextLine();
            choice = input.nextLine().toLowerCase();
            continue_choice = (choice.equalsIgnoreCase("C")) ? true: false;
        }
        }
        while ((seat_loader.getAvailableSeats(seats)) <= 0 || (continue_choice)) ;
        
        // Note: the design choice to take seat number andf flight number from the left rather than the 
        // actual flightNumber o seatNumber Strings, is because using ArrayList indexes is much simpler,
        // more effective and takes less code than using the seat number or flight number Strings. 
        // This was a design choice, and was chosen for simplicity. We didn't think the user would mind
        // using the numbered list to indicate which flight he/she wanted. 
        
        i =0;
        System.out.println("\n||Please select a seat number from those listed on the left||");
        for (Seat s : seats) {
            ++i;
            if (!s.isBooked()) {
            // the calculateSeatPrice will take into consideration total number of seats aswell as classType 
            // to adjust the seat pricing
                s.calculateSeatPrice(seat_loader.getAvailableSeats(seats), s.getClassType());
                System.out.print(i +". ");
                System.out.println(s.toString()); }
        }
        
        
        do {
        System.out.print("Enter seat number: ");
        seatIndex = input.nextInt();
        // we check if user selected an empty seat. 
        if (!seats.get(seatIndex-1).isBooked()) {
        System.out.println("Selected seat: " + seats.get(seatIndex-1).toString());
        
        System.out.println("Would you like to proceed with your seat? [Press any key to continue, or 'C' to cancel]");
        input.nextLine();
        choice = input.nextLine().toLowerCase(); 
        continue_choice = (choice.equalsIgnoreCase("C")) ? true: false; }
        else {
            System.out.println("Invalid choice: Seat number " + (seatIndex) + " is booked\n");
        }
        } while(continue_choice || seats.get(seatIndex -1).isBooked());
        
        
        
        // We simulate purchase of the seat using credit-card
        String credit_card_number;
        System.out.println("Please enter a valid 16 digit credit-card number:" );
        while (credit_card == null) {

        credit_card_number = input.nextLine(); 
        System.out.println();
        credit_card = new CreditCardPayment(credit_card_number);
            }
        
        if ((credit_card.processPayment(seats.get(seatIndex-1).getPrice()))) {
            System.out.println("\nWould you like to check for other flights and seats? [Press any key to continue, or 'C' to look for other flights or seats]");
            choice = input.nextLine();
            continue_choice = (choice.equalsIgnoreCase("C")) ? true: false;
        }
        else {
            continue_choice = true;
        }
        
        // This allows user to completely rechoose flight and seat }
        } while((continue_choice));
        Flight chosenFlight = flights.get(flightIndex-1);
        Seat chosenSeat = seats.get(seatIndex-1);
        // We finally create the reservation object
        reservationId = Reservation.generateReservationId();
        // A reservation holds references to the flight obect, seat object, and passenger object. 
        reservation = new Reservation(reservationId, chosenFlight.getDepartureTime(), chosenFlight, passenger, chosenSeat);
        System.out.println("\nReservation succesfully completed. Full Reservation Details: ");    
        System.out.println(reservation.getReservationDetails());
        // We continue with final logic, we show the boarding pass
        // we do the final thing: Employee checks for boarding pass:
        
        
        System.out.println("\nE-booking available, an employee will be able to assist you in creating a boarding pass.\nResponsible Employee: \n" + employee.getDetails() + "\n\nPlease press any key to continue with employee #" + employee.getEmployeeId() + " or press 'C' to end the program");
        choice = input.nextLine();
        if (choice.equals("C")) {
            System.out.println("\nThank you for booking with us!");
        }
        else {
            employee.varsToPDF(reservation);
            employee.checkForBoarding();
        }
        }
      
        // Excpetion handling for invalid credit cards and invalid cities:
        catch (InvalidCityException e) {
            System.out.println("\nCity Error: " + e.getMessage()); }
        catch (InvalidCreditCardException e){
            System.out.println(e.getMessage());
        } 
        catch (Exception e) {
            System.out.println("\nUnexpected error: " + e.getMessage());
        }
       
        
        
        
        
    

    
     
        
        
        
    }
    
}
