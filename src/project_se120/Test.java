// import dependencies
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_se120;
import java.util.*;

// For boarding pass
import java.io.*;

public class Test {

    public static void main(String[] args) {

        Seats seat_loader = new Seats();
        ArrayList<Seat> seats = new ArrayList<>();
        
        ArrayList<Flight> flights;
        FlightManager flightmanager = new FlightManager();
        
        Scanner input = new Scanner(System.in);
        
        CreditCardPayment credit_card = null;
        Reservation reservation = null;
        
        ArrayList<Passenger> passengers = new ArrayList<>();
        int passenger_index = 0;
        
        ArrayList<Reservation> reservations = new ArrayList<>();
        int reservation_index = 0;
        Employee employee = new Employee("Jackie Brown", "jackie_brown@caboair.org", 1997, "Flight Attendant");

        String origin;
        String destination;
        
        // We give a choice to the user wheather to reserve a flight or check areservation.
        System.out.print("Hello and Welcome!\n\nPlease select any one of the service:\n  1.Book a flight\n  2.Check your reservation\n\nEnter your choice: ");
        String serviceChoice = input.nextLine();
        
        if (serviceChoice.equals("2")) {
        
            System.out.println();
            System.out.print("Please enter your reservation id: ");
            int reservationId = input.nextInt();
            
            String details = new Passenger().checkReservationInFile("" + reservationId);
            
            if (details == null) System.out.println("Sorry, no reservation with this id is found.");
            
            else {          
            
                String[] detailsList = details.split("~");
                String[] departure = detailsList[8].split(" ");
                String departureDetails =  departure[2] + " " +departure[1] + " "  + departure[3] + " " + departure[5];
                
                String[] arrival = detailsList[9].split(" ");
                String arrivalDetails = (Integer.parseInt(departure[2])) + 1 + " " + departure[1] + " "  + departure[3] + " " + departure[5];
                
                System.out.println();
                System.out.println("Reservation ID: " + detailsList[0] + "\nName: " + detailsList[1] + "\nEmail: " + detailsList[2] + "\nPassport Number: " + detailsList[6] + "\nFlight Number: " + detailsList[7]);
                System.out.println("Departure Date: " + departureDetails + "\nArrival Date: " + arrivalDetails + "\nOrigin: " + detailsList[10] + "\nDestination: " + detailsList[11] + "\nSeat: " + detailsList[12] + "\nClass: " + detailsList[13]);
            }
            
            System.exit(0);
        }
        
        else if (!(serviceChoice.equals("1")) & !(serviceChoice.equals("2"))) {
        
            System.out.println("Invalid choice");
            System.exit(0);
        }
        
        System.out.println("\n");

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
        

        String validPassport = "[A-Z]{2}[A-Z\\d]{7}";   ///////////////////////!!!!!!!
        System.out.print("Enter your passport number: ");
        // Must be 6 digits + letters and start with letters
        String passport = input.nextLine();
        
        while (!(passport.matches(validPassport))) {
            
            System.out.print("Invalid passport number. Enter your passport number: ");
            passport = input.nextLine();
        }
        
        
        passengers.add(new Passenger(name, email, passport));
        System.out.println();
        
        Flight chosenFlight;
        Seat chosenSeat;
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
            

            int i = 0;
            for (Flight f : flights) {
                
                System.out.print(++i);
                System.out.println(f.toString());
            }

            
            int flightIndex;
            double demand = flightmanager.getDemand(flight.getDepartureTime());

            // We repeat the whole logic if seat is too expensive/ above the limit : 
            // This can be used if we want to add more detail to the payment function. For example allowing user to 
            // choose multiple seats or flights. Although this is out of scope for this project. 
            boolean createSeats = true;
            do {
                
                System.out.println();
                
                do {
                    
                    System.out.print("Enter Flight Number: ");
                    flightIndex = input.nextInt();
                    if (createSeats) {
                    seats = seat_loader.createSeats(flights.get(flightIndex - 1));
                    seat_loader.occupySeats(seats, flights.get(flightIndex - 1).getDepartureTime(), demand);
                    System.out.println("Available seats: " + seat_loader.getAvailableSeats(seats)); 
                    createSeats = false;
                    }

                    if (seat_loader.getAvailableSeats(seats) <= 0) {
                        
                        System.out.println("Sorry, the flight you selected has no available seats.\n");
                    } 
                    
                    
                    else {
                        
                        System.out.println("Would you like to proceed with flight " + flights.get(flightIndex - 1).getFlightNumber() + "? [Press any key to continue, or 'C' to cancel]");
                        input.nextLine();
                        choice = input.nextLine().toLowerCase();
                        continue_choice = (choice.equalsIgnoreCase("C")) ? true : false;
                    }
                    
                    
                } while ((seat_loader.getAvailableSeats(seats)) <= 0 || (continue_choice));

                
                
                // Note: the design choice to take seat number andf flight number from the left rather than the 
                // actual flightNumber o seatNumber Strings, is because using ArrayList indexes is much simpler,
                // more effective and takes less code than using the seat number or flight number Strings. 
                // This was a design choice, and was chosen for simplicity. We didn't think the user would mind
                // using the numbered list to indicate which flight he/she wanted. 

                i = 0;
                System.out.println("\n||Please select a seat number from those listed on the left||");
                
                for (Seat s : seats) {
                    
                    ++i;
                    
                    if (!s.isBooked()) {
                        
                        // the calculateSeatPrice will take into consideration total number of seats aswell as classType 
                        // to adjust the seat pricing
                        
                        s.calculateSeatPrice(seat_loader.getAvailableSeats(seats), s.getClassType());
                        System.out.print(i + ". ");
                        System.out.println(s.toString());
                    }
                }

                
                do {
                    
                    System.out.print("Enter seat number: ");
                    seatIndex = input.nextInt();
                    
                    // we check if user selected an empty seat. 
                    if (!seats.get(seatIndex - 1).isBooked()) {
                        
                        System.out.println("Selected seat: " + seats.get(seatIndex - 1).toString());

                        System.out.println("Would you like to proceed with your seat? [Press any key to continue, or 'C' to cancel]");
                        input.nextLine();
                        
                        choice = input.nextLine().toLowerCase();
                        
                        continue_choice = (choice.equalsIgnoreCase("C")) ? true : false;
                    } 
                    
                    else {
                        
                        System.out.println("Invalid choice: Seat number " + (seatIndex) + " is booked\n");
                    }
                    
                } while (continue_choice || seats.get(seatIndex - 1).isBooked());
                

                // We simulate purchase of the seat using credit-card
                String credit_card_number;
                
                
                if (credit_card == null) {

                    System.out.println("Please enter a valid 16 digit credit-card number:");
                    credit_card_number = input.nextLine();
                    System.out.println();
                    
                    try {
                        
                        credit_card = new CreditCardPayment(credit_card_number);
                    }
                    
                    catch (InvalidCreditCardException e) {
            
                        System.out.println(e.getMessage());
                        credit_card_number = input.nextLine();
                        
                        while(!credit_card_number.matches("\\d{16}")) {
                        
                            System.out.println(e.getMessage());
                            credit_card_number = input.nextLine();
                        }
                        
                        credit_card = new CreditCardPayment(credit_card_number);
                        
                    }  
                    
                }

                if ((credit_card.processPayment(seats.get(seatIndex - 1).getPrice()))) {
                    chosenFlight = flights.get(flightIndex - 1);
                    chosenSeat = seats.get(seatIndex - 1);
                    chosenSeat.setBookingStatus(true);

                    // We finally create the reservation object
                    reservationId = Reservation.generateReservationId();

                    // A reservation holds references to the flight obect, seat object, and passenger object. 
                    reservations.add(new Reservation(reservationId, chosenFlight.getDepartureTime(), chosenFlight, passengers.get(passenger_index), chosenSeat));

                    System.out.println("\nReservation succesfully completed. Full Reservation Details: ");
                    System.out.println(reservations.get(reservation_index).getReservationDetails());
                    
                    // we mainly work with ArrayLists because it allows greatest flexibility long-term, especially
                    // If we want to allow for multiple reservations, flight options, seat options, and allow
                    // the user to re-book with another passenger. They are easy to work with and you only need 
                    // to keep track of which index you are accessing. Saves previous data seemlessly. 
                    passengers.get(passenger_index).addReservation(reservation);

                    passengers.get(passenger_index).reservationToFile();
                    
                    System.out.println("\nWould you like to check for other flights and seats? [Press any key to continue, or 'C' to look for other flights or seats]");
                    choice = input.nextLine();
                    continue_choice = (choice.equalsIgnoreCase("C")) ? true : false;
                    // We check again, to get new passenger info.
                    if (choice.equalsIgnoreCase("C")) {
                        passenger_index +=1;
                        reservation_index +=1;
                        
                        System.out.print("\nEnter first name for the next passenger: ");

                        first_name = input.nextLine();

                        while (first_name.matches(invalidInputName)) {
                            System.out.print("Invalid name, try again: ");
                            first_name = input.nextLine();
                        }

                        System.out.print("Enter last name for the next passenger: ");
                        last_name = input.nextLine();

                        while (last_name.matches(invalidInputName)) {
                            System.out.print("Invalid name, try again: ");
                            last_name = input.nextLine();
                        }

                        name = first_name + " " + last_name;

                        System.out.print("Enter email for the next passenger: ");
                        email = input.nextLine();
                        while (!email.matches(validEmail)) {
                            System.out.print("Invalid email, try again: ");
                            email = input.nextLine();
                        }

                        System.out.print("Enter passport number for the next passenger: ");
                        passport = input.nextLine();

                        while (!passport.matches(validPassport)) {

                            System.out.print("Invalid passport, try again: ");
                            passport = input.nextLine();
                        }

                        passengers.add( new Passenger(name, email, passport));
                                                }
                } 
                
                else {
                    
                    continue_choice = true;
                }

                // This allows user to completely rechoose flight and seat }
            } while ((continue_choice));
            
            

            
            // We continue with final logic, we show the boarding pass
            // we do the final thing: Employee checks for boarding pass:

            System.out.println("\nE-booking available, an employee will be able to assist you in creating a boarding pass.\nResponsible Employee: \n" + employee.getDetails() + "\n\nPlease press any key to continue with employee #" + employee.getEmployeeId() + " or press 'C' to end the program");
            choice = input.nextLine();
            
            if (choice.equals("C")) {
                
                System.out.println("\nThank you for booking with us!");
            } 
            
            else {
                for (Reservation r : reservations) {
                employee.varsToPDF(r);
                employee.checkForBoarding(r.getPassenger());
            }
        } 

        
        
        
        // Excpetion handling for invalid cities and general expections:
        }
        catch (InvalidCityException e) {
            
            System.out.println("\nCity Error: " + e.getMessage());
        }
        
        catch (Exception e) {
            
            System.out.println("\nUnexpected error: " + e.getMessage());
        }
        
        catch (Throwable e) {
            
            System.out.println("\nUnexpected system error: " + e.getMessage());
        }
 
    }

}
