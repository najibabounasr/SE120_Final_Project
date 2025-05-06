/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package project_se120;

/**
 *
 * @author najibabounasr
 */
import java.util.*;
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
        
        String origin;
        String destination;
        
        // 1. First take user input and create Passenger object 
        System.out.print("Enter your full name: ");
        String name = input.nextLine();

        System.out.print("Enter your email: ");
        String email = input.nextLine();

        System.out.print("Enter your passport number: ");
        String passport = input.nextLine();

        Passenger passenger = new Passenger(name, email, passport);
        
        // 2. Then you should take origin and destination and call FlightManage's findTimings(Flight flight) method
        // populate with some dummy values
        System.out.println("Enter the origin city: ");
        origin = input.nextLine();
        System.out.println("Enter the destination city: ");
        destination = input.nextLine();
        
        Flight flight = new Flight("DUMMY", origin, destination, new Date(), new Date(), flight_manager);
        
        flights = flightmanager.findTimings(flight);
        int i =0;
        System.out.println("||Please select a flight number from those listed on the left||\n");
        for (Flight f : flights) {
            System.out.print(++i);
            System.out.println(".Flight: " + flight.getFlightNumber() + "|| From:  " + flight.getOriginCity() + "|| To: " + flight.getDestination() +"|| Arrival Time: " + flight.getArrivalTime() + "|| Destination Time: " + flight.getDepartureTime());
        }
        int flightIndex;
        double demand = flightmanager.getDemand(flight.getDepartureTime());
        
        do {
        System.out.print("Enter Flight Number: ");
        flightIndex = input.nextInt();
        seats = seat_loader.createSeats(flights.get(flightIndex+1), flights.get(flightIndex+1).getDepartureTime(), demand);
        seat_loader.occupySeats(seats,flights.get(flightIndex+1).getDepartureTime(), demand);
        
        if (seat_loader.getAvailableSeats(seats) == 0) {
            System.out.println("Sorry, the flight you selected has no available seats.\n");
        }
        }
        while ((seat_loader.getAvailableSeats(seats)) == 0) ;
        System.out.println("Sorry, the flight you selected has no available seats.\n");
        
    
     
        
        
        
    }
    
}
