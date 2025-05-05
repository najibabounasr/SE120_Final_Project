/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_se120;

/**
 *
 * @author najibabounasr
 */
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Cities {
    private ArrayList<City> cityList =  new ArrayList<>();
    String line;
    StringBuilder output = new StringBuilder();
    String [] indexes;
    
    // These are the constructor parameters of 'City' class:
    String name;
    String capital_status;
    double latitude;
    double longitude;
    
            
    
    
    
    
    
    public ArrayList<City> readCSV(){
        
        
        
        ArrayList<City> cities_list = new ArrayList<>();
    // BufferedReader throws a checked exception if we don't use a try block. it will read 
    // 'Unreported exception FileNotFoundException, must be caught or declared to be thrown'
    try {
        BufferedReader br = new BufferedReader(new FileReader ("/Users/najibabounasr/Desktop/Al Faisal/SE120_Final_Project/SE120_Final_Project/src/project_se120/worldcities.csv"));
        // 48057 is the length of the full csv file (rows). We choose 48000 as a clean number of Cities, still a very 
        // large number of real cities are included in this simulator. 
        br.readLine();
        for (int i = 1; i < 48000; ++i) {
            
            // We first use a String to read the line (we need to use .split method which only String had)
            line = br.readLine();
            
            // We now switch to a String list, and split the csv based on the commas to get a csv format
            indexes = line.split(",");
            
            // Because the strings in the file were causing a 'NumberFormatException', we must trim and remove extra commas around 
            // any of the strings. 
            // We know also know that index 0 stores the name in ascii. 
            if (indexes.length > 3) {
            name = indexes[0].trim();
            latitude = Double.parseDouble(indexes[2].replace("\"", "").trim());
            longitude = Double.parseDouble(indexes[3].replace("\"", "").trim()); // Using wrapper classes are the easiest way to convert String--> primitive type. 
            capital_status = indexes[8];
            cities_list.add(new City(name,latitude, longitude,capital_status));
            }
        } return (cities_list);
        
        
        } 
    catch (FileNotFoundException e) {
        System.out.println("Error: File was not found" +  e.getMessage());
    } catch (IOException e) {
        System.out.println("Error: Input/Output Exception" +  e.getMessage());
    } 
    return (cities_list);
    
    } 
    // small test program
    public static void main(String[] args) {
    Cities cities = new Cities();
    ArrayList<City> cities_list = cities.readCSV();
    for (int i = 0; i<4000; ++i) {
    System.out.println(cities_list.get(i));  // This works
   
    }
    }
       
    
    

    } 




    
    

