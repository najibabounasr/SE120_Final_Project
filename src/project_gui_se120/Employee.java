public class Employee extends Person {

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
    
        return super.getDetails() + "\nEmployee ID: " + employeeId + "\nRole: " + role;
    }
    
    
    
}