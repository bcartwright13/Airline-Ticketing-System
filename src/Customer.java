import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;
import java.text.DecimalFormat;
/**
 * customer class for creating customer objects extending from the abstract person class
 */

public class Customer extends Person{
    private String userName;
    private String password;
    private Double moneyAvailable;
    private boolean minerAirlinesMembership;
    private int flightsPurchased = 0;
    private String id;
    private Ticket ticket;
    private HashMap<String, Ticket> tickets = new HashMap<>();
    private float minerAirSavings;
    
    DecimalFormat df = new DecimalFormat("#.00");

    public Customer(){
        
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getMinerAirSavings(){
        return this.minerAirSavings;
    }
    public void setMinerAirSavings(float savings){
        this.minerAirSavings = savings;
    }
    public void addMinerAirSavings(float savings){
        this.minerAirSavings += savings;
    }

    public HashMap<String,Ticket> getTickets() {
        return this.tickets;
    }

    public void setTickets(String flightID, Ticket ticket) {
        this.tickets.put(flightID, ticket);
    }
    public Ticket getTicket(){
        return ticket;
    }
    public void setTicket(Ticket ticket){
        this.ticket = ticket;
    }
    public String getid(){
        return this.id;
    }
    public void setid(String id){
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getMoneyAvailable() {
        return this.moneyAvailable;
    }

    public void setMoneyAvailable(Double moneyAvailable) {
        this.moneyAvailable = moneyAvailable;
    }

    public boolean getMinerAirlinesMembership() {
        return this.minerAirlinesMembership;
    }

    public void setMinersAirlineMembership(boolean ticketMinerMembership) {
        this.minerAirlinesMembership = ticketMinerMembership;
    }

    public int getFlightsPurchased() {
        return this.flightsPurchased;
    }

    public void setFlightsPurchased(int flightsPurchased) {
        this.flightsPurchased = flightsPurchased;
    }

    public void printCustomerInformation(){
        System.out.println(this.getFirstName());
        System.out.println(this.getLastName());
        System.out.println(this.getMoneyAvailable());
        System.out.println(this.getFlightsPurchased());
        System.out.println(this.getMinerAirlinesMembership());
        System.out.println(this.getUserName());
        System.out.println(this.getPassword());
        try{
            this.getTicket().printTicket();
        }catch(NullPointerException e){
            System.out.println("Customer has no ticket");
        }    
    
    }
    /**
     * Displays terminal for customers allowing them to purchase tickets
     * @param flights
     */
    public void customerTerminal(HashMap<String, Flight> flights){  //method that opens customer terminal
        Log log = Log.getInstance();
        Scanner scan = new Scanner(System.in); 
        String flightID = "0";
        float minerAirlinesFee = 9.15f;
        float securityFee = 5.60f;

        
        while(true){            //customer chooses which flight they want to purchase tickets for
            System.out.println("Select an option below");
            System.out.println("1. Purchase flight by flight ID");
            System.out.println("2. Purchase flight by Airport search");
            String choice = scan.nextLine();
            if(choice.equals("1")){
                System.out.println("Which flight would you like to purchase? (Enter flight ID number 1 - " + flights.size() + ")");
                flightID = scan.nextLine();
                if(flights.get(flightID) == null){  //checks if flight id exists in list of flights
                    System.out.println("Please enter a valid number");
                }else{
                    if(flights.get(flightID).getFlightType().equals("International")){
                        System.out.println("This is an international flight and a $" + flights.get(flightID).getSurcharge() + " surcharge will be added per seat");
                    }
                    break;
                }
            }else if(choice.equals("2")){
                scan = new Scanner(System.in);                             //gets customer input on origin and destination airport
                System.out.println("Enter the origin airport code");
                String originAirport = scan.nextLine();
                scan = new Scanner(System.in);
                System.out.println("Enter the destination airport code");
                int counter = 0;
                String destinationAirport = scan.nextLine();
                scan = new Scanner(System.in);
                System.out.println("Enter the departure date ex M/DD/YYYY");
                String departureDate = scan.nextLine();
                for(int j = 1; j < flights.size() + 1; j++){      //loops through flights checking for the correct criteria
                    String i = String.valueOf(j); 
                    if(flights.get(i).getOriginAirport().getCode().equalsIgnoreCase(originAirport)){
                        if(flights.get(i).getDestinationAirport().getCode().equalsIgnoreCase(destinationAirport)){
                            if(flights.get(i).getDepartureDate().equals(departureDate)){
                                counter++;
                                System.out.println(flights.get(i).getFlightID() + "." + " " + flights.get(i).getOriginAirport().getAirportName() + " to " + flights.get(i).getDestinationAirport().getAirportName() + " departing: " + flights.get(i).getDepartureDate() + " at " + flights.get(i).getDepartureTime());
                            } 
                        }
                    }
                }
                if(counter != 0){
                    System.out.println("Select from the IDs above");
                    scan = new Scanner(System.in);
                    flightID = scan.nextLine();
                    break;
                }else{
                    System.out.println("No flights available");
                }
            }else{
                System.out.println("Invalid choice try again");
            }
        }
        if(!flights.get(flightID).isCancelled()){
        //displays list of seat options and price
            System.out.println("Prices for seats");
            System.out.println("1. Main Cabin: $" + flights.get(flightID).getMainCabinPrice());
            System.out.println("2. First Class: $" + flights.get(flightID).getFirstClassPrice());
            System.out.println("3. Business Class: $" + flights.get(flightID).getBusinessClassPrice());
            System.out.println("What type of seat would you like to purchase? Enter 1-3");
            int seatChoice = 0;
            String seatType = "";   //used to set seat type for ticket
            float employeeDiscount = 1;
            while(true){
                try{    //user chooses which seat option
                    scan = new Scanner(System.in);
                    seatChoice = scan.nextInt();
                    if(seatChoice > 0 && seatChoice < 4){
                        if(seatChoice == 1){
                            seatType = "Main Cabin Seats";
                            employeeDiscount = 0.75f;
                        }else if(seatChoice == 2){
                            seatType = "First Class Seats";
                            employeeDiscount = 0.5f;
                        }else if(seatChoice == 3){
                            seatType = "Business Class Seats";
                            employeeDiscount = 0.75f;
                        }
                        break;
                    }else{
                        System.out.println("Enter valid number");
                    }
                }catch(InputMismatchException e){
                    System.out.println("Please enter valid option");
                }
            }
            int numberOfSeats = 0;
            float totalPrice = 0;
            float subtotalBeforeFees = 0;
            float subtotal = 0;
            
            System.out.println("You have $" + this.getMoneyAvailable() + " available");
            System.out.println("How many seats would you like to purchase? (Max 8)");
            while(true){
                scan = new Scanner(System.in);
                try{
                    //user enters amount of seats they would like to purchase
                    numberOfSeats = scan.nextInt();
                    if(numberOfSeats > 8 || numberOfSeats < 1){   //only lets customer purchase 1-8 seats
                        System.out.println("Not a valid seat amount only can purchase 1-8");
                    }else{
                        float surchargeTotal = 0;
                        float savings = 0;
                        if(seatChoice == 1){
                            //check if there's enough seats
                            if(flights.get(flightID).getMainCabinSeats() - numberOfSeats >= 0){  // checks flight object if enough seats are available 
                                if(flights.get(flightID).getFlightType().equals("International")){                 //calculates surcharge total if flight is international
                                    surchargeTotal = flights.get(flightID).getSurcharge() * numberOfSeats;
                                    System.out.println("A total surcharge of $" + surchargeTotal + " will be added to the total.");
                                }
                                
                                //calculates subtotal buy multiplying the number of seats with the seat price
                                subtotalBeforeFees = numberOfSeats * flights.get(flightID).getMainCabinPrice() + surchargeTotal;
                                System.out.println("Your subtotal before fees is: $" + subtotalBeforeFees);
                                if(this.getRole().contains("Employee")){
                                    System.out.println("A 75% discount has been add because you are an employee");
                                    subtotalBeforeFees = subtotalBeforeFees - (subtotalBeforeFees * employeeDiscount);
                                    System.out.println("You new subtotal is: $" + subtotalBeforeFees);
                                }
                                 
                                subtotal = subtotalBeforeFees + minerAirlinesFee + securityFee + flights.get(flightID).getOriginAirport().getFee(); //adds security and miner airline fees
                                System.out.println("A Miners Airlines fee of $" + minerAirlinesFee + " has been added");
                                System.out.println("A Security fee of $" + securityFee + " has been added");
                                System.out.println("A airport fee of $" + flights.get(flightID).getOriginAirport().getFee() + " has been added");
                                
                                System.out.println("The subtotal price for this ticket is $" + subtotal);

                                //gives a 5% discount to members
                                if(this.minerAirlinesMembership){
                                    System.out.println("You are a Miner Airlines Member. 5% has been taken off your subtotal");
                                    savings = subtotalBeforeFees * 0.05f;
                                    
                                    subtotal = subtotal - savings;
                                    System.out.println("Your new subtotal is $" + subtotal);
                                }
                                float tax = calculateTax(subtotal);
                                totalPrice = tax + subtotal;
                                System.out.println("Taxes: $" + tax);
                                System.out.println("The total price after tax is $" + totalPrice);
                                //check if customer has enough money
                                if(this.moneyAvailable - totalPrice > 0){
                                    System.out.println("Confirm that you would like to purchase this ticket (Enter y/n)");
                                    scan = new Scanner(System.in);
                                    String confirmation = scan.nextLine();
                                    if(confirmation.equalsIgnoreCase("y")){
                                        flights.get(flightID).setMainCabinSeats(flights.get(flightID).getMainCabinSeats() - numberOfSeats);   //Takes away seats from flight object
                                        this.setTicket(ticketGenerator(numberOfSeats, totalPrice, flights.get(flightID)));  //Creats a ticket for customer
                                        this.getTicket().setSeatType(seatType);  //sets ticket seat type
                                        this.getTicket().setCustomer(this);  //gives ticket object the customer object
                                        this.setTickets(this.getTicket().getConfirmationNumber(), this.getTicket());   //adds to hashmap of tickets for customer
                                        this.moneyAvailable = (this.moneyAvailable - totalPrice);
                                        log.logAction(this.getFirstName() + " " + this.getLastName() + " purchased ticket for Flight " + flights.get(flightID).getFlightNumber() + " from " + flights.get(flightID).getOriginCode() + " to " + flights.get(flightID).getDestinationCode() + " with " + numberOfSeats + " Main Cabin seats.");
                                        this.setFlightsPurchased(this.getFlightsPurchased() + 1);
                                        this.minerAirSavings += savings;
                                        flights.get(flightID).addTotalDiscounter(savings);
                                        flights.get(flightID).addTotalCollectedTaxes(tax);
                                        flights.get(flightID).getOriginAirport().addtofees(flights.get(flightID).getOriginAirport().getFee());
                                        System.out.println("Ticket Purchased");
                                        break;
                                    }else if(confirmation.equalsIgnoreCase("n")){
                                        System.out.println("Ticket Cancelled");  //cancels ticket purchase if customer presses n
                                        break;
                                    }else{
                                        System.out.println("Invalid entry. Transaction cancelled");
                                        break;
                                    }
                                }else{
                                    System.out.println("Not enough funds available");
                                    break;
                                }
                            }else{
                                System.out.println("Seats are not available");
                                break;
                            }
                        }else if(seatChoice == 2){
                            if(flights.get(flightID).getFirstClassSeats() - numberOfSeats >= 0){
                                if(flights.get(flightID).getFlightType().equals("International")){
                                    surchargeTotal = flights.get(flightID).getSurcharge() * numberOfSeats;
                                    System.out.println("A total surcharge of $" + surchargeTotal + " will be added to the total.");
                                }
                                subtotalBeforeFees = numberOfSeats * flights.get(flightID).getFirstClassPrice() + surchargeTotal;
                                System.out.println("Your subtotal before fees is: $" + subtotalBeforeFees);
                                if(this.getRole().contains("Employee")){
                                    System.out.println("A 75% discount has been add because you are an employee");
                                    subtotalBeforeFees = subtotalBeforeFees - (subtotalBeforeFees * employeeDiscount);
                                    System.out.println("You new subtotal is: $" + subtotalBeforeFees);
                                } 
                                subtotal = subtotalBeforeFees + minerAirlinesFee + securityFee + flights.get(flightID).getOriginAirport().getFee();
                                System.out.println("A Miners Airlines fee of $" + minerAirlinesFee + " has been added");
                                System.out.println("A Security fee of $" + securityFee + " has been added");
                                System.out.println("A airport fee of $" + flights.get(flightID).getOriginAirport().getFee() + " has been added");
                                System.out.println("The subtotal price for this ticket is $" + subtotal);
                                if(this.minerAirlinesMembership){
                                    System.out.println("You are a Miner Airlines Member. 5% has been taken off your subtotal");
                                    savings = subtotalBeforeFees * 0.05f;
                                    subtotal = subtotal - savings;
                                    System.out.println("Your new subtotal is $" + subtotal);
                                }
                                float tax = calculateTax(subtotal);
                                totalPrice = tax + subtotal;
                                System.out.println("Taxes: $" + tax);
                                System.out.println("The total price after tax is $" + totalPrice);
                                
                                if(this.getMoneyAvailable() - totalPrice > 0){
                                    System.out.println("Confirm that you would like to purchase this ticket (Enter y/n)");
                                    scan = new Scanner(System.in);
                                    String confirmation = scan.nextLine();
                                    if(confirmation.equalsIgnoreCase("y")){
                                        flights.get(flightID).setFirstClassSeats(flights.get(flightID).getFirstClassSeats() - numberOfSeats);
                                        this.setTicket(ticketGenerator(numberOfSeats, totalPrice, flights.get(flightID)));
                                        this.getTicket().setSeatType(seatType);
                                        this.getTicket().setCustomer(this);
                                        this.setTickets(this.getTicket().getConfirmationNumber(), this.getTicket());
                                        this.setMoneyAvailable(this.getMoneyAvailable() - totalPrice);
                                        log.logAction(this.getFirstName() + " " + this.getLastName() + " purchased ticket for Flight " + flightID + " from " + flights.get(flightID).getOriginCode() + " to " + flights.get(flightID).getDestinationCode() + " with " + numberOfSeats + " First Class seats.");
                                        this.setFlightsPurchased(this.getFlightsPurchased() + 1);
                                        this.minerAirSavings += savings;
                                        flights.get(flightID).addTotalDiscounter(savings);
                                        flights.get(flightID).addTotalCollectedTaxes(tax);
                                        flights.get(flightID).getOriginAirport().addtofees(flights.get(flightID).getOriginAirport().getFee());
                                        System.out.println("Ticket Purchased");
                                        break;
                                    }else if(confirmation.equalsIgnoreCase("n")){
                                        System.out.println("Ticket Cancelled");
                                        break;
                                    }else{
                                        System.out.println("Invalid entry. Transaction cancelled");
                                        break;
                                    }
                                }else{
                                    System.out.println("Not enough funds available");
                                    break;
                                }
                            }else{
                                System.out.println("Seats are not available");
                                break;
                            }
                
                        }else if(seatChoice ==3){
                            if(flights.get(flightID).getBusinessClassSeats() - numberOfSeats >= 0){
                                if(flights.get(flightID).getFlightType().equals("International")){
                                    surchargeTotal = flights.get(flightID).getSurcharge() * numberOfSeats;
                                    System.out.println("A total surcharge of $" + surchargeTotal + " will be added to the total.");
                                }
                                subtotalBeforeFees = numberOfSeats * flights.get(flightID).getBusinessClassPrice() + surchargeTotal;
                                System.out.println("Your subtotal before fees is: $" + subtotalBeforeFees);
                                if(this.getRole().contains("Employee")){
                                    System.out.println("A 75% discount has been add because you are an employee");
                                    subtotalBeforeFees = subtotalBeforeFees - (subtotalBeforeFees * employeeDiscount);
                                    System.out.println("You new subtotal is: $" + subtotalBeforeFees);
                                } 
                                subtotal = subtotalBeforeFees + minerAirlinesFee + securityFee + flights.get(flightID).getOriginAirport().getFee();
                                System.out.println("A Miners Airlines fee of $" + minerAirlinesFee + " has been added");
                                System.out.println("A Security fee of $" + securityFee + " has been added");
                                System.out.println("A airport fee of $" + flights.get(flightID).getOriginAirport().getFee() + " has been added");
                                System.out.println("The subtotal price for this ticket is $" + subtotal);
                                if(this.minerAirlinesMembership){
                                    System.out.println("You are a Miner Airlines Member. 5% has been taken off your subtotal");
                                    savings = subtotalBeforeFees * 0.05f;
                                    
                                    subtotal = subtotal - savings;
                                    System.out.println("Your new subtotal is $" + subtotal);
                                }
                                float tax = calculateTax(subtotal);
                                totalPrice = tax + subtotal;
                                System.out.println("Taxes: $" + tax);
                                System.out.println("The total price after tax is $" + totalPrice);
                                if(this.getMoneyAvailable() - totalPrice > 0){
                                    System.out.println("Confirm that you would like to purchase this ticket (Enter y/n)");
                                    scan = new Scanner(System.in);
                                    String confirmation = scan.nextLine();
                                    if(confirmation.equalsIgnoreCase("y")){
                                        flights.get(flightID).setBusinessClassSeats(flights.get(flightID).getBusinessClassSeats() - numberOfSeats); 
                                        this.setTicket(ticketGenerator(numberOfSeats, totalPrice, flights.get(flightID)));
                                        this.getTicket().setSeatType(seatType);
                                        this.getTicket().setCustomer(this);
                                        this.setTickets(this.getTicket().getConfirmationNumber(), this.getTicket());
                                        this.setMoneyAvailable(this.getMoneyAvailable() - totalPrice);
                                        log.logAction(this.getFirstName() + " " + this.getLastName() + " purchased ticket for Flight " + flightID + " from " + flights.get(flightID).getOriginCode() + " to " + flights.get(flightID).getDestinationCode() + " with " + numberOfSeats + " Business Class seats.");
                                        this.setFlightsPurchased(this.getFlightsPurchased() + 1);
                                        this.minerAirSavings += savings;
                                        flights.get(flightID).addTotalDiscounter(savings);
                                        flights.get(flightID).addTotalCollectedTaxes(tax);
                                        flights.get(flightID).getOriginAirport().addtofees(flights.get(flightID).getOriginAirport().getFee());
                                        System.out.println("Ticket Purchased");
                                        break;
                                    }else if(confirmation.equalsIgnoreCase("n")){
                                        System.out.println("Ticket Cancelled");
                                        break;
                                    }else{
                                        System.out.println("Invalid entry. Transaction cancelled");
                                        break;
                                    }
                                }else{
                                    System.out.println("Not enough funds available");
                                    break;
                                }
                            }else{
                                System.out.println("Seats are not available");
                                break;
                            }
                        }
                    }
                }catch(InputMismatchException e){
                    System.out.println("Please enter a valid number of seats.");
                }
            
            }
        }else{
            System.out.println("This flight has been cancelled");
        }
    }
    /**
     * Calculates tax
     * @param subtotal takes in subtotal
     * @return returns tax amount
     */
    public float calculateTax(float subtotal){
        return subtotal * 0.0825f;
    }
    /**
     * Generates a ticket for customer
     * @param seats takes in seats
     * @param price takes in the prices of the ticket
     * @param flight takes in which flight the ticket is purchased for
     * @return returns the ticket object
     */
    public Ticket ticketGenerator(int seats, float price, Flight flight){  //generates ticker for customer
        Random random = new Random();  
        int confirmationNumber = 100000 + random.nextInt(900000); //uses random number generator for ticket confirmation number.
        String number = Integer.toString(confirmationNumber);
        Ticket ticket = new Ticket(seats, price, number,flight);
        flight.addTicketToFlight(ticket); //adds ticket to flight
        
        return ticket; 
    }
    public void printTickets(){
        if(this.tickets.isEmpty()){
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.println("You have not purchased any tickets");
            System.out.println("-------------------------------------------------------------------------------------------------");
        }else{
            for(String key : this.tickets.keySet()){
                System.out.println("-------------------------------------------------------------------------------------------------");
                
                this.tickets.get(key).printTicket();
                System.out.println("-------------------------------------------------------------------------------------------------");

            }
        }
    }
    public void printReceipt(){

    }
}
