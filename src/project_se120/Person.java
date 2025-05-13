/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_se120;
public class Person {

    private String name;
    private String email;

    public Person() {
    }

    public Person(String name, String email) {

        this.name = name;
        this.email = email;
    }

    public String getName() {

        return name;
    }

    public String getEmail() {

        return email;
    }

    public String getDetails() {

        return "Name: " + name + "\nEmail: " + email;
    }
    
    public String getDetailsForFile() {
    
        return name + "~" + email;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setEmail(String email) {

        this.email = email;
    }

}
