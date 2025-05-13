/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_se120;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import java.util.Map;
import java.io.*;

public class Employee extends Person implements BoardingPassGenerator {

    protected int employeeId;
    private String role;

    public Employee(String name, String email, int employeeId, String role) {

        super(name, email);
        this.employeeId = employeeId;
        this.role = role;

    }

    public String getRole() {

        return "Role: " + role;
    }

    @Override
    public String getDetails() {

        return super.getDetails() + "\nName: " + super.getName() + "\nEmployee ID: " + employeeId + "\nRole: " + role;
    }

    public boolean checkForBoarding(Passenger passenger) {
        try {
            String fileName = "boarding_" + passenger.getName().replaceAll(" ", "_") + ".pdf";
            File file = new File(System.getProperty("user.dir") + "/src/output/" + fileName);
            java.awt.Desktop.getDesktop().open(file);
            System.out.println("\nBoarding Pass located! Please enjoy your flight\n\n|PROGRAM ENDED|");
            return (true);
        } catch (IOException e) {
            System.out.println("\nBoarding Pass could not be located. Please try again or try at the airport in-person.: " + e.getMessage());
            return (false);
        }

    }

    public int getEmployeeId() {
        return employeeId;
    }

    @Override
    public void varsToPDF(Reservation reservation) {
            String templatePath = System.getProperty("user.dir") + "/src/resources/BoardingPassTemplateReal.pdf";
            String fileName = "boarding_" + reservation.getPassenger().getName().replaceAll(" ", "_") + ".pdf";
            String outputPath = System.getProperty("user.dir") + "/src/output/" + fileName;

        try {
            PdfDocument pdfDoc = new PdfDocument(
                    new PdfReader(templatePath),
                    new PdfWriter(outputPath)
            );

            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

            String name = reservation.getPassenger().getName().toUpperCase();
            String flightNumber = String.valueOf(reservation.getFlight().getFlightNumber()).toUpperCase();
            String date = String.valueOf(reservation.getBookingDate()).toUpperCase();
            String theClass = reservation.getSeat().getClassType().toUpperCase();
            String origin = reservation.getFlight().getOrigin().toUpperCase();
            String seatNumber = reservation.getSeat().getSeatNumber().toUpperCase();
            // The user would have booked too early to actually see the gate number:
            String departureGate = "TBD";
            String destination = reservation.getFlight().getDestination().toUpperCase();
            String zone = "TBD";
            String none = "hi";

            Map<String, String> data = Map.ofEntries(
                    Map.entry("text_4vxzj", name),
                    Map.entry("text_4vxzj.1", name),
                    Map.entry("text_5nbo", flightNumber),
                    Map.entry("text_5nbo.1", flightNumber),
                    Map.entry("text_6qgvo", date),
                    Map.entry("text_6qgvo.1", date),
                    Map.entry("text_7dazc", theClass),
                    Map.entry("text_7dazc.1", theClass),
                    Map.entry("text_8eszi", origin),
                    Map.entry("text_8eszi.1", origin),
                    Map.entry("text_9qdyg", seatNumber),
                    Map.entry("text_9qdyg.1", seatNumber),
                    Map.entry("text_10ondk", departureGate),
                    Map.entry("text_10ondk.1", departureGate),
                    Map.entry("text_11hdcb", destination),
                    Map.entry("text_11hdcb.1", destination),
                    Map.entry("text_12jchh", flightNumber),
                    Map.entry("text_12jchh.1", flightNumber),
                    Map.entry("text_13mjob", date),
                    Map.entry("text_13mjob.1", date),
                    Map.entry("text_14okzr", origin),
                    Map.entry("text_14okzr.1", origin),
                    Map.entry("text_15ivow", destination),
                    Map.entry("text_15ivow.1", destination),
                    Map.entry("text_16nraj", seatNumber),
                    Map.entry("text_16nraj.1", seatNumber),
                    Map.entry("text_17zmrj", zone),
                    Map.entry("text_17zmrj.1", zone),
                    Map.entry("text_18gsyt", zone),
                    Map.entry("text_18gsyt.1", zone)
            );
            
            System.out.println("Available form fields:");
            for (Map.Entry<String, PdfFormField> entry : form.getFormFields().entrySet()) {
                System.out.println(" - " + entry.getKey());
            }

            for (Map.Entry<String, String> entry : data.entrySet()) {
                String fieldName = entry.getKey();
                String fieldValue = entry.getValue();

                PdfFormField field = form.getField(fieldName);
                field.setValue(fieldValue);
                field.setBorderWidth(0);
                field.setBackgroundColor(null);

                field.regenerateField();
            }

            form.flattenFields();

            pdfDoc.close();

            System.out.println("Boarding pass succesfully generated!\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error " + e.getMessage());
        }
    }
}
