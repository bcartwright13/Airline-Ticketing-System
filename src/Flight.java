import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * flight class for creating flight objects
 */

public class Flight{
    private String flightID;
    private Airport originAirport;
    private String originCode;
    private Airport destinationAirport;
    private String destinationCode;
    private String departureDate;
    private String departureTime;
    private float firstClassPrice;
    private float businessClassPrice;
    private float mainCabinPrice;
    private String arrivalTime;
    private String arrivalDate;
    private int duration;
    private float distance;
    private int timezoneDifference;
    private int firstClassSeats;
    private int businessClassSeats;
    private int mainCabinSeats;
    private int totalSeats;
    private String flightNumber;
    private ArrayList<Ticket> ticketList = new ArrayList<>();
    private String flightType;
    private float surcharge = 0;
    private International international;
    private float expectedProfit;
    private float routeCost;
    private boolean foodServed;
    private int minerPoints;
    private float totalDiscounted = 0;
    private float totalCollectedTaxes = 0;
    private float minerAirFee = 9.15f;
    private float securityFee = 5.60f;
    private boolean isCancelled = false;
    
    


    
    //formatts time
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
    //formatts date
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");


    public Flight(){
        
    }
    public String getFlightID(){
        return this.flightID;
    }
    public void setFlightID(String flightID){
        this.flightID = flightID;
    }
    public boolean isCancelled(){
        return this.isCancelled;
    }
    public float getTotalCollectedTaxes(){
        return this.totalCollectedTaxes;
    }
    public void addTotalCollectedTaxes(float taxes){
        this.totalCollectedTaxes += taxes;
    }
    public float getTotalDiscounted(){
        return this.totalDiscounted;
    }
    public void addTotalDiscounter(float discount){
        this.totalDiscounted += discount;
    }
    public String getFlightNumber() {
        return this.flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Airport getOriginAirport() {
        return this.originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }

    public String getOriginCode() {
        return this.originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public Airport getDestinationAirport() {
        return this.destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getDestinationCode() {
        return this.destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getDepartureDate() {
        return this.departureDate;
    }

    public void setDepartureDate(String departureDate) { 
        LocalDate departure = LocalDate.parse(departureDate, dateFormatter);
        String format = departure.format(dateFormatter); 
        this.departureDate = format;
    }

    public String getDepartureTime() {
        return this.departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public float getFirstClassPrice() {
        return this.firstClassPrice;
    }

    public void setFirstClassPrice(float firstClassPrice) {
        this.firstClassPrice = firstClassPrice;
    }

    public float getBusinessClassPrice() {
        return this.businessClassPrice;
    }

    public void setBusinessClassPrice(float businessClassPrice) {
        this.businessClassPrice = businessClassPrice;
    }

    public float getMainCabinPrice() {
        return this.mainCabinPrice;
    }

    public void setMainCabinPrice(float mainCabinPrice) {
        this.mainCabinPrice = mainCabinPrice;
    }

    public String getArrivalTime() {
        return this.arrivalTime;
    }

    /**
     * sets arrival time based off departure time and changes date dynamically
     */
    public void setArrivalTime(){
        LocalTime departure = LocalTime.parse(this.departureTime, formatter);
        int totalTravelTime = this.duration + (this.timezoneDifference * 60);
        LocalTime arrivalTime = departure.plusMinutes(totalTravelTime);          //sets arrival time depending on duration and time zone diff
        String formattedTime = arrivalTime.format(formatter);
        this.arrivalTime = formattedTime;
        if(arrivalTime.isBefore(departure)){
            add1ToArrivalDate();                   //if duration moves to next day then change arrival date by 1
        }

    }
    public void add1ToArrivalDate(){
        LocalDate arrival = LocalDate.parse(this.arrivalDate, dateFormatter);
        LocalDate newDate =arrival.plusDays(1);                 //changes arrival date by one if arrival time goes past 12AM
        String format = newDate.format(dateFormatter);
        this.arrivalDate = format;
    }

    public String getArrivalDate() {

        return this.arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        LocalDate arrival = LocalDate.parse(arrivalDate, dateFormatter);
        String format = arrival.format(dateFormatter);
        this.arrivalDate = format;
    }
    public void setNewArrivalDate(){
        this.arrivalDate = this.departureDate;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getTimezoneDifference() {
        return this.timezoneDifference;
    }

    public void setTimezoneDifference(int timezoneDifference) {
        this.timezoneDifference = timezoneDifference;
    }

    public int getFirstClassSeats() {
        return this.firstClassSeats;
    }

    public void setFirstClassSeats(int firstClassSeats) {
        this.firstClassSeats = firstClassSeats;
    }

    public int getBusinessClassSeats() {
        return this.businessClassSeats;
    }

    public void setBusinessClassSeats(int businessClassSeats) {
        this.businessClassSeats = businessClassSeats;
    }

    public int getMainCabinSeats() {
        return this.mainCabinSeats;
    }

    public void setMainCabinSeats(int mainCabinSeats) {
        this.mainCabinSeats = mainCabinSeats;
    }

    public int getTotalSeats() {
        return this.mainCabinSeats + this.businessClassSeats + this.firstClassSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
    public void addTicketToFlight(Ticket ticket){
        this.ticketList.add(ticket);          //adds any ticket that is purchased for the flight
    }
    public ArrayList<Ticket> getTickets(){
        return this.ticketList;
    }
    public void setFlightType(String flightType){
        this.flightType = flightType;
    }
    public String getFlightType(){
        return this.flightType;
    }
    public void setSurcharge(float surcharge){
        this.surcharge = surcharge;
    }
    public float getSurcharge(){
        return this.surcharge;
    }
    public void setInternational(float surcharge){
        International international = new International(surcharge);
        this.international = international;
    }
    public International getInternational(){
        return this.international;
    }
    public void setRouteCost(float routeCost){
        this.routeCost = routeCost;
    }
    public float getRouteCost(){
        return this.routeCost;
    }

    public boolean getFoodServed() {
        return this.foodServed;
    }

    public void setFoodServed(boolean foodServed) {
        this.foodServed = foodServed;
    }

    public int getMinerPoints() {
        return this.minerPoints;
    }

    public void setMinerPoints(int minerPoints) {
        this.minerPoints = minerPoints;
    }
 /**
  * Prints flight information
  */
    public void printFlight(){ //prints out flight information
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("                                    FLIGHT INFORMATION                                           ");
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("Origin Airport: " + this.originAirport.getAirportName());
        System.out.println("Origin Code: " + this.originCode);
        System.out.println("Destination Airport: " + this.destinationAirport.getAirportName());
        System.out.println("Destination Code: " + this.destinationCode);
        System.out.println("Departure Date: " + this.departureDate);
        System.out.println("Departure Time: " + this.departureTime);
        System.out.println("Arrival Time: " + this.arrivalTime);
        System.out.println("Arrival Date: " + this.arrivalDate);
        System.out.println("Duration: " + this.duration);
        System.out.println("Distance: " + this.distance);
        System.out.println("Time Zone Difference: " + this.timezoneDifference);
        System.out.println("First Class Price: $" + this.firstClassPrice);
        System.out.println("Business Class Price: $" + this.businessClassPrice);
        System.out.println("Main Cabin Price: $" + this.mainCabinPrice);
        System.out.println("First Class Seats Remaining: " + this.firstClassSeats);
        System.out.println("Business Class Seats Remaining: " + this.businessClassSeats);
        System.out.println("Main Cabin Seats Remaining: " + this.mainCabinSeats);
        System.out.println("Total Seats Remaining: " + this.totalSeats);

        System.out.println("-------------------------------------------------------------------------------------------------");
        
    }
    /**
     * Prints all customers that have purchased a ticket for flight
     */
    public void printCustomers(){ 
        //detects if any customer has purchased a ticket for flight
        if(ticketList.isEmpty()){
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.println("No Customers have purchased a ticket for this flight");
            System.out.println("-------------------------------------------------------------------------------------------------");
        }else{
            for(int i = 0; i < ticketList.size();i++){
                System.out.println("-------------------------------------------------------------------------------------------------");
                System.out.println("Customer: " + ticketList.get(i).getCustomer().getFirstName() + " " + ticketList.get(i).getCustomer().getLastName());
                System.out.println(ticketList.get(i).getSeatType() + ": " + ticketList.get(i).getHowManyTickets() + " seats");
                System.out.println("Total price: $" + ticketList.get(i).getTotalPrice());
                if(ticketList.get(i).getCancelled()){
                    System.out.println("Ticket has been cancelled");
                }
                System.out.println("-------------------------------------------------------------------------------------------------");
            }
        }
    }

    /**
     * Computes the current profit of flight
     * @return
     */
    public float computeCurrentProfit(){
        //gets current profit from current seats sold. Includes surcharges
        float revenue = 0;
        for(int i = 0; i < ticketList.size(); i++){
            
            revenue += ticketList.get(i).getTotalPrice() + (ticketList.get(i).getHowManyTickets() * this.surcharge);
        }
        //subtracts fees and taxes from profit
        float profit = revenue - this.getRouteCost() - this.totalCollectedTaxes - this.getTotalSecurityFee() - this.getTotalMinerAirFee();
        return profit;
    }
    /**
     * Sets expected profit based off seats
     */
    public void setExpectedProfit(){
        //sets expected profit when object is created
        float totalSurcharge = this.surcharge * totalSeats;
        float revenue = (this.mainCabinPrice * this.mainCabinSeats) + (this.businessClassPrice * this.businessClassSeats) + (this.firstClassPrice * this.firstClassSeats) + totalSurcharge;
        this.expectedProfit = revenue - this.routeCost;
    } 
    public float getExpectedProfit(){
        return this.expectedProfit;
    }  
    /**
     * Gets revenue from first class seats
     * @return
     */
    public float getfClassRevenue(){
        //gets revenue of first class seats by checking tickets stored in flight
        float revenue = 0;
        for(int i = 0; i < ticketList.size(); i++){
            if(ticketList.get(i).getSeatType().equals("First Class Seats")){
                revenue += ticketList.get(i).getTotalPrice() + (ticketList.get(i).getHowManyTickets() * this.surcharge) - this.totalCollectedTaxes - this.getTotalMinerAirFee() - this.getTotalSecurityFee();
            }
        }
        return revenue;
    } 
    //returns total fees
    public float getTotalMinerAirFee(){
        return ticketList.size() * this.minerAirFee;
    }
    public float getTotalSecurityFee(){
        return ticketList.size() * this.securityFee;
    }

    public float getbClassRevenue(){
        //gets revenue of business class seats by checking tickets stored in flight
        float revenue = 0;
        for(int i = 0; i < ticketList.size(); i++){
            if(ticketList.get(i).getSeatType().equals("Business Class Seats")){
                revenue += ticketList.get(i).getTotalPrice() + (ticketList.get(i).getHowManyTickets() * this.surcharge) - this.totalCollectedTaxes - this.getTotalMinerAirFee() - this.getTotalSecurityFee();
            }
        }
        return revenue;
    } 
    public float getmCabinRevenue(){
        //gets revenue of main cabin seats by checking tickets stored in flight
        float revenue = 0;
        for(int i = 0; i < ticketList.size(); i++){
            if(ticketList.get(i).getSeatType().equals("Main Cabin Seats")){
                revenue += ticketList.get(i).getTotalPrice() + (ticketList.get(i).getHowManyTickets() * this.surcharge) - this.totalCollectedTaxes - this.getTotalMinerAirFee() - this.getTotalSecurityFee();
            }
        }
        return revenue;
    } 
    public int getfClassSeatsSold(){
        int revenue = 0;
        for(int i = 0; i < ticketList.size(); i++){
            if(ticketList.get(i).getSeatType().equals("First Class Seats")){
                revenue += 1;
            }
        }
        return revenue;
    } 
    /**
     * Returns the number of seats sold
     * @return
     */
    public int getbClassSeatsSold(){
        int revenue = 0;
        for(int i = 0; i < ticketList.size(); i++){
            if(ticketList.get(i).getSeatType().equals("Business Class Seats")){
                revenue += 1;
            }
        }
        return revenue;
    } 
    public int getmCabinSeatsSold(){
        int revenue = 0;
        for(int i = 0; i < ticketList.size(); i++){
            if(ticketList.get(i).getSeatType().equals("Main Cabin Seats")){
                revenue += 1;
            }
        }
        return revenue;
    }
    public void cancelFlight(){
        Log log = Log.getInstance();
        log.logAction("Flight " + this.flightNumber + " has been cancelled");
        for(int i = 0; i < ticketList.size(); i++){
            ticketList.get(i).cancelTicket(false);
        }
        this.isCancelled = true;
    }
}