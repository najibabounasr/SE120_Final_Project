package project_se120;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import java.io.IOException;
import java.util.Map;

public class VarsToPDF {
    public static void varsToPDF(Passenger passenger, Flight flight, Seat seat) {
        String templatePath = "src/resources/BoardingPassTemplateReal.pdf";
        String outputPath = "src/output/outputPLACEHOLDERS.pdf";
        
        try {
            PdfDocument pdfDoc = new PdfDocument(
                    new PdfReader(templatePath),
                    new PdfWriter(outputPath)
            );
            
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            
            String name = passenger.getName().toUpperCase();
            String flightNumber = String.valueOf(flight.getFlightNumber());
            String date = String.valueOf(flight.getDepartureTime());
            String theClass = seat.getClassType();
            String origin = flight.getOrigin();
            String seatNumber = seat.getSeatNumber();
            String departureGate = "B27";
            String destination = "LOS VEGAS";
            String zone = "ZONE 3";
            String none = "hi";
            
            Map<String, String> data = Map.ofEntries(
                    Map.entry("text_4vxzj", name),
                    Map.entry("text_4vxzj.1", name),
                    Map.entry("text_5nbo", flight),
                    Map.entry("text_5nbo.1", flight),  
                    Map.entry("text_6qgvo", date),
                    Map.entry("text_6qgvo.1", date),   
                    Map.entry("text_7dazc", theClass), 
                    Map.entry("text_7dazc.1", theClass),
                    Map.entry("text_8eszi", origin),   
                    Map.entry("text_8eszi.1", origin),
                    Map.entry("text_9qdyg", seat),    
                    Map.entry("text_9qdyg.1", seat),
                    Map.entry("text_10ondk", departureGate), 
                    Map.entry("text_10ondk.1", departureGate),
                    Map.entry("text_11hdcb", destination),   
                    Map.entry("text_11hdcb.1", destination),
                    Map.entry("text_12jchh", flight),        
                    Map.entry("text_12jchh.1", flight),
                    Map.entry("text_13mjob", date),       
                    Map.entry("text_13mjob.1", date),
                    Map.entry("text_14okzr", origin),       
                    Map.entry("text_14okzr.1", origin),
                    Map.entry("text_15ivow", destination),  
                    Map.entry("text_15ivow.1", destination),
                    Map.entry("text_16nraj", seat),         
                    Map.entry("text_16nraj.1", seat),
                    Map.entry("text_17zmrj", zone),
                    Map.entry("text_17zmrj.1", zone),
                    Map.entry("text_18gsyt", zone),
                    Map.entry("text_18gsyt.1", zone)
            );
            
            System.out.println("🔍 Available form fields:");
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
            
            System.out.println("PDF generated!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error " + e.getMessage());
        }
    }
}