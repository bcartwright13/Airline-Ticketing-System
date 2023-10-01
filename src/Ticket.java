/**
 * Ticket class that creates ticket objects for customers
 */
public class Ticket {
    private String seatType;
    private int totalSeats;
    private float totalPrice;
    private String confirmationNumber;
    private Flight flight;
    private Customer customer;
    private Boolean cancelled = false;
    

    public Ticket(int howManyTickets, float totalPrice, String confirmationNumber, Flight flight){
        this.totalSeats = howManyTickets;
        this.totalPrice = totalPrice;
        this.confirmationNumber = confirmationNumber;
        this.flight = flight;
    }
    public boolean getCancelled(){
        return this.cancelled;
    }
    public void setCustomer(Customer customer){
        this.customer = customer;
    }
    public Customer getCustomer(){
        return this.customer;
    }
    public void setSeatType(String seatType){
        this.seatType = seatType;
    }
    public String getSeatType(){
        return this.seatType;
    }
    public Flight getFlight() {
        return this.flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Ticket(){
        
    }

    public int getHowManyTickets() {
        return this.totalSeats;
    }

    public void setHowManyTickets(int howManySeats) {
        this.totalSeats = howManySeats;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getConfirmationNumber() {
        return this.confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }
    /**
     * method to print out ticket information
     */
    public void printTicket(){
        System.out.println("Flight Number: " + this.flight.getFlightNumber());
        System.out.println("Flying from " + this.flight.getOriginAirport().getAirportName() + " to " + this.flight.getDestinationAirport().getAirportName());
        System.out.println("Seat Type: " + this.seatType);
        System.out.println("Number of Seats: " + getHowManyTickets());
        System.out.println("Total Price: $" + getTotalPrice());
        System.out.println("Confirmation Number: " + getConfirmationNumber());
        if(this.cancelled){
            System.out.println("Ticket has been cancelled");
        }
    }
    /**
     * Method that cancels ticket and returns funds to customer
     * @param isCustomer
     */
    public void cancelTicket(boolean isCustomer){ //parameter checks if customer is cancelling or if the flight is being cancelled by an employee
        Log log = Log.getInstance();  //gets instance of log object
        int seats = this.totalSeats;  //seat holder
        float refund = 0;
        if(isCustomer){
            refund = this.totalPrice - 9.15f;
            customer.setMoneyAvailable(customer.getMoneyAvailable() + refund);
        }  //refunds money to customer but not airline fee
        else{
            refund = this.totalPrice;
            customer.setMoneyAvailable(customer.getMoneyAvailable() + this.totalPrice);
        }
        if(this.getSeatType().equals("Main Cabin Seats")){   //returns seats to flights depending on seat type
            flight.setMainCabinSeats(flight.getMainCabinSeats() + this.totalSeats);
        }else if(this.getSeatType().equals("Business Class Seats")){
            flight.setBusinessClassSeats(flight.getBusinessClassSeats() + this.totalSeats);
        }else if(this.getSeatType().equals("First Class Seats")){
            flight.setFirstClassSeats(flight.getFirstClassSeats() + this.totalSeats);
        }
        
        this.totalSeats = 0;  //sets ticket seat to 0
        log.logAction(this.customer.getFirstName() + " " + this.customer.getLastName() + "'s' ticket for flight " + this.flight.getFlightNumber()
        + " from " + this.flight.getOriginCode() + " to " + this.flight.getDestinationCode() + " has been cancelled. " + seats + " " + this.getSeatType() +
        " added back to flight and $" + refund + " has been refunded.");
        this.cancelled = true;
        this.totalPrice = 0;

    }
}                   
