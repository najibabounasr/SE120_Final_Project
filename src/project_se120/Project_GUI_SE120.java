package project_se120;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

public class Project_GUI_SE120 extends Application {

    private final FlightManager flightManager = new FlightManager();
    private Flight selectedFlight;
    private ArrayList<Flight> flightOptions;
    private Passenger passenger;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) { showPassengerForm(primaryStage); }

    /* =====================  Passenger Form  ===================== */
    private void showPassengerForm(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        TextField nameField     = new TextField(); nameField.setPromptText("Full Name");
        TextField emailField    = new TextField(); emailField.setPromptText("Email");
        TextField passportField = new TextField(); passportField.setPromptText("Passport Number");
        TextField originField   = new TextField(); originField.setPromptText("Origin City");
        TextField destField     = new TextField(); destField.setPromptText("Destination City");

        Label  status      = new Label();
        Button findFlights = new Button("Find Flights");

        findFlights.setOnAction(e -> {
            try {
                passenger = new Passenger(nameField.getText(), emailField.getText(), passportField.getText());

                Calendar cal = Calendar.getInstance();
                Date dep = cal.getTime();
                cal.add(Calendar.HOUR, 3);
                Date arr = cal.getTime();

                Flight base = new Flight("TEMP", originField.getText(), destField.getText(), dep, arr, flightManager);
                flightOptions = flightManager.findTimings(base);
                showFlightSelection(stage);
            } catch (InvalidCityException ex) {
                status.setText("Invalid city: " + ex.getMessage());
            } catch (Exception ex) {
                status.setText("Unexpected error: " + ex.getMessage());
            }
        });

        root.getChildren().addAll(nameField, emailField, passportField, originField, destField, findFlights, status);
        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Flight Booking - Passenger Info");
        stage.show();
    }

    /* =====================  Flight Selection  ===================== */
    private void showFlightSelection(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label label = new Label("Select a Flight:");
        ComboBox<Flight> flightBox = new ComboBox<>();
        flightBox.getItems().addAll(flightOptions);

        Button choose = new Button("Choose Flight");
        choose.setOnAction(e -> {
            selectedFlight = flightBox.getValue();
            if (selectedFlight != null) showSeatSelection(stage);
        });

        root.getChildren().addAll(label, flightBox, choose);
        stage.setScene(new Scene(root, 500, 300));
        stage.setTitle("Select Flight");
    }

    /* =====================  Seat Selection  ===================== */
    private void showSeatSelection(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label info = new Label("Available Seats");
        ListView<Seat> seatList = new ListView<>();
        ArrayList<Seat> available = new ArrayList<>();
        for (Seat s : selectedFlight.seats) if (!s.isBooked()) available.add(s);
        seatList.getItems().addAll(available);

        Label   result = new Label();
        Button bookBtn = new Button("Book Selected Seat");

        bookBtn.setOnAction(e -> {
            Seat seat = seatList.getSelectionModel().getSelectedItem();
            if (seat == null) return;

            double price = seat.getPrice();
            if (price > 10000) {
                result.setText("Seat price exceeds $10,000");
                return;
            }

            seat.setBookingStatus(true);

            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Enter 16‑digit Credit Card Number:");
            Optional<String> input = dialog.showAndWait();

            input.ifPresent(card -> {
                try {
                    CreditCardPayment pay = new CreditCardPayment(card);
                    if (pay.processPayment(price)) {
                        Reservation res = new Reservation((int)(Math.random()*100000), new Date());
                        res.setPassenger(passenger);
                        res.setFlight(selectedFlight);
                        res.setPayment(pay);
                        passenger.addReservation(res);
                        result.setText("Reservation confirmed!");

                        showConfirmation(stage, res);
                    }
                } catch (IllegalArgumentException ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                }
            });
        });

        root.getChildren().addAll(info, seatList, bookBtn, result);
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Choose Your Seat");
    }

    /* =====================  Confirmation Scene  ===================== */
    private void showConfirmation(Stage stage, Reservation res) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label header = new Label("Reservation Details");
        header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextArea details = new TextArea(res.getReservationDetails());
        details.setEditable(false);
        details.setPrefRowCount(10);

        Button finish = new Button("Finish");
        finish.setOnAction(ev -> stage.close());

        root.getChildren().addAll(header, details, finish);
        stage.setScene(new Scene(root, 500, 350));
        stage.setTitle("Booking Confirmation");
    }
}
