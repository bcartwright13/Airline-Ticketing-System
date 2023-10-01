import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Employee class used for creating employee objects
 */

public class Employee extends Person{
    private String id;

    public Employee(){

    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    /**
     * Method that displays terminal for employees allowing them to view and edit flights
     * @param flights Takes in hashmap that contain flight objects
     */
    public void employeeTerminal(HashMap<String, Flight> flights, HashMap<String, Airport> airports, HashMap<String, Customer> customers){  //terminal for employees
        Log log = Log.getInstance();
        int menuLoop = 0;
        Scanner scan = new Scanner(System.in);
        while(menuLoop == 0){
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.println("                                     Employee Terminal");
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.println("1. Inquire about flight");
            System.out.println("2. Inquire about airport");
            System.out.println("3. Enable auto purchaser");
            System.out.println("4. Create electronic ticket summary");
            System.out.println("5. Exit");
            System.out.println("-------------------------------------------------------------------------------------------------");
            String employeeOption = scan.nextLine();
            if(employeeOption.equals("1")){
                menuLoop = 1;
            }else if(employeeOption.equals("2")){
                menuLoop = 10;

            }else if(employeeOption.equals("3")){
                while(true){
                    System.out.println("Which autopurchaser would you like to run?");
                    System.out.println("1. 10K");
                    System.out.println("2. 100K");
                    System.out.println("3. 400k");
                    scan = new Scanner(System.in);
                    String autoSelector = scan.nextLine();
                    AutoPurchase autoPurchase = new AutoPurchase();
                    if(autoSelector.equals("1")){
                        autoPurchase.runAutoPurchaser(flights, customers, "AutoPurchaser10K.csv");
                        break;
                    }else if(autoSelector.equals("2")){
                        autoPurchase.runAutoPurchaser(flights, customers, "AutoPurchaser100K.csv");
                        break;
                    }else if(autoSelector.equals("3")){
                        autoPurchase.runAutoPurchaser(flights, customers, "AutoPurchaser400K.csv");
                        break;
                    }else{
                        System.out.println("Invalid Option");
                    }
                    
                    
                }
            }else if(employeeOption.equals("4")){
                ElectronicTicket electronicTicket = new ElectronicTicket();
                electronicTicket.writeElectonicTicketSummary(customers.get("47"), "brandoncartwright.txt");
                electronicTicket.writeElectonicTicketSummary(customers.get("2"), "donaldduck.txt");
                electronicTicket.writeElectonicTicketSummary(customers.get("17"), "buzzlightyear.txt");
            }
            else if(employeeOption.equals("5")){
                break;
            }
            else{
                System.out.println("Invalid choice");
            }
            while(menuLoop == 10){
                System.out.println("Enter airport code you wish to inquire about");
                scan = new Scanner(System.in);
                String airportCode = scan.nextLine();
                try{
                    airports.get(airportCode).printAirportInformation();
                    menuLoop = 0;
                }catch(NullPointerException e){
                    System.out.println("Airport does not exist");
                }
            }
        
            while(menuLoop == 1){ 
                scan = new Scanner(System.in);                  //terminal window
                System.out.println("Enter the flight ID of the flight you wish to view or type exit to exit");
                String custFlightID = scan.nextLine();
                
                if(custFlightID.equalsIgnoreCase("exit")){                     //selects flight by flightID
                    menuLoop = 0;
                }
                else if(flights.get(custFlightID) != null){                  
                    menuLoop = 2;     
                    while(menuLoop == 2){
                        System.out.println("-------------------------------------------------------------------------------------------------");
                        System.out.println("What would you like to do?");
                        System.out.println("1. View Flight Information");
                        System.out.println("2. Change Flight Information");
                        System.out.println("3. Cancel Flight");
                        System.out.println("4. Exit");
                        System.out.println("-------------------------------------------------------------------------------------------------");
                        scan = new Scanner(System.in);
                        String option = scan.nextLine();
                        if(option.equals("1")){
                            menuLoop = 3;
                        }else if(option.equals("2")){
                            menuLoop = 4;
                        }else if(option.equals("3")){
                            System.out.println("Flight " + flights.get(custFlightID).getFlightNumber() + " has been cancelled");
                            log.logAction("Flight " + flights.get(custFlightID).getFlightNumber() + " has been cancelled. All tickets have been refunded");
                            flights.get(custFlightID).cancelFlight();
                        }
                        else if(option.equals("4")){
                            menuLoop = 1;
                        }else{
                            System.out.println("Invalid Option");
                        }

                    } 
                    while(menuLoop == 3){
                        System.out.println("-------------------------------------------------------------------------------------------------");
                        System.out.println("1. Print flight information");
                        System.out.println("2. Print customers on flight");
                        System.out.println("3. View flight finances");
                        System.out.println("4. Exit");
                        System.out.println("-------------------------------------------------------------------------------------------------");
                        scan = new Scanner(System.in);
                        String choice = scan.nextLine();
                        if (choice.equals("1")){
                            flights.get(custFlightID).printFlight(); 
                        }
                        else if (choice.equals("2")){
                            flights.get(custFlightID).printCustomers(); 
                        }else if (choice.equals("3")){
                            System.out.println("-------------------------------------------------------------------------------------------------");
                            System.out.println("                                       FLIGHT FINANCES                                           ");
                            System.out.println("-------------------------------------------------------------------------------------------------");
                            System.out.println("Route Cost: $" + flights.get(custFlightID).getRouteCost());
                            System.out.println("Main Cabin Revenue: $" + flights.get(custFlightID).getmCabinRevenue());
                            System.out.println("Business Class Revenue: $" + flights.get(custFlightID).getbClassRevenue());
                            System.out.println("First Class Revenue: $" + flights.get(custFlightID).getfClassRevenue());
                            System.out.println("Current Profit: $" + flights.get(custFlightID).computeCurrentProfit());
                            System.out.println("Expected Profit: $" + flights.get(custFlightID).getExpectedProfit());
                            System.out.println("Total Tax Charged: $" + flights.get(custFlightID).getTotalCollectedTaxes());
                            System.out.println("Total Miner Airlines Fees Collected: $" + flights.get(custFlightID).getTotalMinerAirFee());
                            System.out.println("Total Security Fees Collect: $" + flights.get(custFlightID).getTotalSecurityFee());
                            System.out.println("-------------------------------------------------------------------------------------------------");
                        }else if (choice.equals("4")){
                            menuLoop = 1;
                        }else{
                            System.out.println("Invalid choice");
                        }
                    }                                                     
                    while(menuLoop == 4){
                        System.out.println("-------------------------------------------------------------------------------------------------");
                        System.out.println("What would you like to change (Enter number).");
                        System.out.println("1. Origin Airport");
                        System.out.println("2. Origin Code");
                        System.out.println("3. Destination Airport");
                        System.out.println("4. Destination Code");
                        System.out.println("5. Departure Date");           //select option to change selected attribute
                        System.out.println("6. Departure Time");
                        System.out.println("7. First Class Price");
                        System.out.println("8. Business Class Price");
                        System.out.println("9. Main Cabin Price");
                        System.out.println("10. Exit");
                        System.out.println("-------------------------------------------------------------------------------------------------");
                        String choice = scan.nextLine();
                        Scanner newScan = new Scanner(System.in);
                        if(choice.equals("1")){
                            System.out.println("Enter the new origin airport");
                            String change = newScan.nextLine();
                            flights.get(custFlightID).setOriginAirport(airports.get(change));
                            log.logAction("Flight ID " + custFlightID + " updated origin airport to " + change);
                            
                        }
                        else if(choice.equals("2")){
                            System.out.println("Enter the new origin code");
                            String change = newScan.nextLine();
                            flights.get(custFlightID).setOriginCode(change);
                            log.logAction("Flight ID " + custFlightID + " updated origin code to " + change);
                            
                        }
                        else if(choice.equals("3")){
                            System.out.println("Enter the new destination airport");
                            String change = newScan.nextLine();
                            flights.get(custFlightID).setDestinationAirport(airports.get(change));
                            log.logAction("Flight ID " + custFlightID + " updated destination airport to " + change);
                            
                        }
                        else if(choice.equals("4")){
                            System.out.println("Enter the new destination Code");
                            String change = newScan.nextLine();
                            flights.get(custFlightID).setDestinationCode(change);
                            log.logAction("Flight ID " + custFlightID + " updated destination code to " + change);
                            
                        }
                        else if(choice.equals("5")){
                            System.out.println("Enter the new departure date in valid format MM/dd/yyyy");
                            String change = newScan.nextLine();
                            try{
                                flights.get(custFlightID).setDepartureDate(change);
                                flights.get(custFlightID).setNewArrivalDate();        //sets new arrival date
                                log.logAction("Flight ID " + custFlightID + " updated departure date to " + change);
                            }catch(DateTimeParseException e){
                                System.out.println("Invalid Format");
                            }
                        }
                        else if(choice.equals("6")){
                            System.out.println("Enter the new departure time in valid format hh:mm AM");
                            String change = newScan.nextLine();
                            try{
                                flights.get(custFlightID).setDepartureTime(change);
                                flights.get(custFlightID).setArrivalTime();
                                log.logAction("Flight ID " + custFlightID + " updated departure time to " + change);
                            }
                            catch(DateTimeParseException e){
                                System.out.println("Invalid Format");
                            }
                        }
                        else if(choice.equals("7")){
                            System.out.println("Enter the new first class price");
                            
                            try{
                                float change = newScan.nextFloat();
                                flights.get(custFlightID).setFirstClassPrice(change);
                                log.logAction("Flight ID " + custFlightID + " updated first class price to " + change);
                            }catch(InputMismatchException e){
                                System.out.println("Enter a valid number");
                            }
                        }
                        else if(choice.equals("8")){
                            System.out.println("Enter the new business class price");
                            
                            try{
                                float change = newScan.nextFloat();
                                flights.get(custFlightID).setBusinessClassPrice(change);
                                log.logAction("Flight ID " + custFlightID + " updated business class price to " + change);
                            }catch(InputMismatchException e){
                                System.out.println("Enter a valid number");
                            }
                        }
                        else if(choice.equals("9")){
                            System.out.println("Enter the new main cabin price");
                            try{
                                float change = newScan.nextFloat();
                                flights.get(custFlightID).setMainCabinPrice(change);
                                log.logAction("Flight ID " + custFlightID + " updated main cabin price to " + change);
                            }catch(InputMismatchException e){
                                System.out.println("Enter a valid number");
                            }
                        }
                        else if(choice.equals("10")){
                            menuLoop = 1;
                        }
                        else{
                            System.out.println("Invalid choice try again.");
                        }    
                    }           
                }else{
                    System.out.println("Not a valid flight ID");
                }          
            } 
        }         
    }
}