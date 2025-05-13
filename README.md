# ✈️ Airline Reservation System (SE 120 Project)

This Java-based airline reservation system simulates real-world booking, seat selection, flight demand, and boarding pass generation. Designed for educational purposes, it blends core Java OOP concepts with realistic aviation logic.

---

## 📦 Features

- **Flight Generation**  
  Dynamically generates flights between cities using real-world data including capital status, lat/lng, and distance.

- **Dynamic Seat Allocation**  
  Seats are generated with First, Business, and Economy classes and are booked based on simulated demand.

- **Demand Simulation**  
  Demand is calculated using:
  - Time of day (hour coefficient)
  - Day of week (day coefficient)
  - Random variation
  - Realistic coefficients for peak travel hours

- **Reservation System**  
  Users input passenger information and choose a flight and seat. The system handles payments (simulated with basic validation).

- **PDF Boarding Pass**  
  A boarding pass is auto-generated and saved using a template (`iTextPDF`) and personalized with passenger/flight data.

- **Employee Module**  
  Employees handle final boarding checks and generate boarding passes.

---

## 🧠 Logic Highlights

- **FlightManager.java**  
  Handles flight creation, demand modeling, and coefficient adjustments based on:
  - Distance between cities
  - Capital status (Primary/Admin/Minor)
  - Number of flights per day/week

- **Seats.java**  
  Allocates seats to each flight. Implements a demand and time-based algorithm to simulate real-world seat bookings.

- **Test.java**  
  Acts as the main CLI interface. Guides the user through:
  - Booking flow
  - Checking reservation
  - Input validation for names, emails, and passport numbers

- **Employee.java**  
  Generates and opens personalized boarding passes using form fields and writes them to PDF.

---

## 📂 File Structure

src/
├── Test.java # Main CLI logic
├── Flight.java # Flight object
├── FlightManager.java # Demand, flight generation logic
├── Seats.java # Seat creation & booking simulation
├── Employee.java # Boarding logic and PDF generation
├── Passenger.java # Passenger object
├── Seat.java # Seat object
└── Reservation.java # Reservation details

---

## 🔧 Requirements

- Java 8+
- iTextPDF library (for PDF generation)
- A template file `BoardingPassTemplateReal.pdf` located in `src/resources/`

---

## 📌 Notes

- Cities and capital status are loaded from a CSV (`worldcities.csv`)
- Boarding passes are saved in `/src/output/`
- This system is modular: easily extendable for GUI, databases, or web APIs

---

## 🛫 Sample Improvements (Future Ideas)

- Add hub-based coefficient logic (e.g., Dubai, Heathrow get special boost)
- Save flight history and bookings to files or database
- Add GUI interface or web layer
- Simulate round trips and baggage options

---

> Developed as part of SE 120 final project at Alfaisal University. 🎓

