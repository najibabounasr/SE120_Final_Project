package project_se120;

import java.util.*;


// Note: we have to rewrite all of this.
public class MainApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Flight Booking System!");

        // 1. Create passenger
        System.out.print("Enter your full name: ");
        String name = input.nextLine();

        System.out.print("Enter your email: ");
        String email = input.nextLine();

        System.out.print("Enter your passport number: ");
        String passport = input.nextLine();

        Passenger passenger = new Passenger(name, email, passport);

        // 2. Origin & destination
        System.out.print("Enter origin city: ");
        String origin = input.nextLine();

        System.out.print("Enter destination city: ");
        String destination = input.nextLine();

        try {
            Calendar cal = Calendar.getInstance();
            Date dummyDeparture = cal.getTime();
            cal.add(Calendar.HOUR, 3);
            Date dummyArrival = cal.getTime();

            FlightManager flightManager = new FlightManager();
            Flight baseFlight = new Flight("TEMP", origin, destination, dummyDeparture, dummyArrival, flightManager);

            ArrayList<Flight> flights = flightManager.findTimings(baseFlight);

            if (flights.isEmpty()) {
                System.out.println("No flights found.");
                return;
            }

            // 3. Show flights
            for (int i = 0; i < flights.size(); i++) {
                System.out.println((i + 1) + ". " + flights.get(i));
            }

            // 4. Choose flight
            System.out.print("Select a flight number: ");
            int flightChoice = input.nextInt();
            input.nextLine(); // consume newline
            Flight chosenFlight = flights.get(flightChoice - 1);
            
            // 5. Show available seats
            ArrayList<Seat> seats = chosenFlight.seats;
            ArrayList<Seat> availableSeats = new ArrayList<>();
            for (Seat seat : seats) {
                if (!seat.isBooked()) {
                    availableSeats.add(seat);
                }
            }

            if (availableSeats.isEmpty()) {
                System.out.println("No available seats on this flight.");
                return;
            }

            for (int i = 0; i < availableSeats.size(); i++) {
                System.out.println((i + 1) + ". " + availableSeats.get(i));
            }

            System.out.print("Choose a seat: ");
            int seatChoice = input.nextInt();
            Seat selectedSeat = availableSeats.get(seatChoice - 1);
            selectedSeat.setBookingStatus(true);

            // 6. Payment
            System.out.println("Selected seat: " + selectedSeat.toString());
            if (selectedSeat.toString().contains("Price: $")) {
                double price = Double.parseDouble(selectedSeat.toString().split("Price: \\$")[1].split(" ")[0]);
                if (price > 10000) {
                    System.out.println("Price too high. Booking canceled.");
                    return;
                }
                System.out.print("Enter 16-digit credit card number: ");
                input.nextLine(); // clean leftover
                String cardNumber = input.nextLine();
                CreditCardPayment payment = new CreditCardPayment(cardNumber);
                if (!payment.processPayment(price)) {
                    System.out.println("Payment failed.");
                    return;
                }

                // 7. Reservation
                Reservation reservation = new Reservation((int)(Math.random()*100000), new Date());
                reservation.setPassenger(passenger);
                reservation.setFlight(chosenFlight);
                reservation.setPayment(payment);
                passenger.addReservation(reservation);

                System.out.println("\nReservation confirmed:");
                System.out.println(reservation.getReservationDetails());
            }

        } catch (InvalidCityException e) {
            System.out.println("City Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
