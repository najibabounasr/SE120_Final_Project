/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_se120;

/**
 *
 * @author najibabounasr
 */
public class City {
    String name;
    String capital_status;
    double longitude;
    double latitude;
    
        
    
    public City(String name, double latitude, double longitude, String capital_status) {
        this.name = name;
        // This makes calculations later much simpler. We need longitude and latitude in radians anyways. 
        // This makes it so that we do not need to convert later !
        this.longitude = Math.toRadians(longitude);
        this.latitude = Math.toRadians(latitude);
        this.capital_status = capital_status;
    }

    public String getCapital_status() {
        return capital_status;
    }

    public void setCapital_status(String capital_status) {
        this.capital_status = capital_status;
    }

    @Override
    public String toString() {
        return "City{" + "name=" + name + ", longitude=" + longitude + ", latitude=" + latitude + ", capital status=" + capital_status + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    
}
