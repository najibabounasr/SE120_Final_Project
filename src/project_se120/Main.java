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
        ArrayList<Flight> flights;
        FlightManager flightmanager = new FlightManager();
        String origin;
        String destination;
        Scanner input = new Scanner(System.in);
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
        
        
        
    }
    
}
