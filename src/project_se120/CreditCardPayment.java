package project_se120;

public class CreditCardPayment implements Payment {

    private String cardNumber;
    // Spending limit for credit card (we set it very high)
    private final int SPENDING_LIMIT = 10000;
    
    // we assume user has no spending limit for sake of simulation. 
    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
        boolean isTrue = true;
        while(isTrue) {
        if ((!(this.cardNumber.length() == 16)) || (!this.cardNumber.matches("\\d+"))) {
            System.out.println("Invalid card number! Please enter a valid cred-card number: ");
        }
        else {
                System.out.println("Credit card information saved succesfuly. ");
               isTrue = false;
                } }
    }
    
    

    
    @Override
    public boolean processPayment(double amount) {
            if (amount <= this.SPENDING_LIMIT){
                System.out.println("Payment Accepted.\nAmmount: " + amount + "\nFor card ending with: " + cardNumber.substring(12));
                return (true);
            }
            else {
               System.out.println("Payment cannot be accepted. Ammount of " + amount + " is above your current limit of $" + SPENDING_LIMIT);
                return (false);
            }
        
    }
    
}