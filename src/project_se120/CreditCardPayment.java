package project_se120;
public class CreditCardPayment implements Payment {

    private String cardNumber;
    // Spending limit for credit card (we set it very high)
    private final int SPENDING_LIMIT = 10000;
    private int amount_spent;
    
    
    // we assume user has no spending limit for sake of simulation. 
    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
        boolean isTrue = true;
        
        while (isTrue) {                       
            if (!this.cardNumber.matches("\\d{16}")) {
                
                throw new InvalidCreditCardException("Invalid card number. Please re-enter a valid credit card number: ");
            } 
            
            else {
                
                System.out.println("Credit card information saved succesfuly. ");
                isTrue = false;
            }
        }
    }
    
    @Override
    public boolean processPayment(double amount) {
        amount_spent += amount;
        if (amount_spent <= this.SPENDING_LIMIT) {
            System.out.println("Payment Accepted.\nAmount: " + amount + " $" + "\nFor card ending with: " + cardNumber.substring(12));
                    
            return (true);
                
        } else {
            System.out.println("Payment cannot be accepted. Amount of $" + amount_spent + " is above your current limit of $" + SPENDING_LIMIT);
            return (false);
        }
        
    }
    
}
