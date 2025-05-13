/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_se120;
import java.util.*;
import java.io.*;

public class Passenger extends Person {

    private String passportNumber;
    private ArrayList<Reservation> reservation = new ArrayList<>();

    public Passenger() {
    }

    public Passenger(String name, String email, String passportNumber) {

        super(name, email);
        this.passportNumber = passportNumber;
    }

    @Override
    public String toString() {
        
        return "Name: " + super.getName() + ", Email: " + super.getEmail() + ", Passport Number: " + passportNumber;
    }
    
    public String toStringForFile() {
    
        return super.getName() + "~" + super.getEmail() + "~" + passportNumber;
    }
    

    @Override
    public String getDetails() {

        return super.getDetails();
    }
    
    public String getDetailsForFile() {
    
        return super.getDetailsForFile();
    }

    public boolean bookFlight(int flightId) {

        System.out.println("Booking flight " + flightId);
        return true;
    }

    public void addReservation(Reservation reservation) {

        this.reservation.add(reservation);
    }
    
    
    
    public void reservationToFile() {
    
        try (
                
            FileWriter output = new FileWriter(System.getProperty("user.dir") + "/src/reservationFile.txt", true);
        ) {
            
            for (Reservation r: this.reservation) {
            
                output.write(r.getReservationDetailsForFile() + "\n");
            }
            
        }
        
        
        
        catch (FileNotFoundException e) {
        
            System.out.println(e.getMessage());
        }
        
        catch (Exception e) {
        
            System.out.println(e.getMessage());
        }
        
    }
    
    
    public String checkReservationInFile(String reservationId) {
        
        String reservationDetail = null;
    
        try (
                Scanner input = new Scanner(new File(System.getProperty("user.dir") + "/src/reservationFile.txt"));
               
        )   {
            
            while(input.hasNextLine()) {
                
                String data = input.nextLine();
                String dataId = (data.split("~"))[0];
                if (dataId.equals(reservationId) ) {
                
                    reservationDetail = data;
                }
                
            }
        }
        
        
        
        catch (Exception e) {
        
            System.out.println(e.getMessage());
        }
        
        
        return reservationDetail;
    }

}
