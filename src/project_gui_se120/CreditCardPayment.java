public class CreditCardPayment implements Payment {

    private String cardNumber;
    
    
    public CreditCardPayment(String cardNumber) {
    
        this.cardNumber = cardNumber;
        
    }
    
    

    
    @Override
    public boolean processPayment(double amount) {
    
        return true;
        
    }
    
}