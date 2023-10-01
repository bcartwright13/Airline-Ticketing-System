/*This work was done individually and completely on my own. I did not share, reproduce, or alter any part of this assignment 
for any purpose. I did not share code, upload this assignment online in any form, or view/received/modified code written
from anyone else. All deliverables were produced entirely on my own. This assignment is part of an academic course
at The University of Texas at El Paso and a grade will be assigned for the work I produced. */
//Brandon Cartwright

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Date;

/**
 * Main class generates the flights and customer from csv
 * @version 1.0
 */

public class RunFlight{

        /**
     * Main method that creates the the hashmaps for the customers and flights. 
     * Opens login menu and gives customer options
     * @param args  argument taken into the main
     */
    public static void main(String[] args) {
        Log log = Log.getInstance();     //resets log file
        log.setNewFile(true);
        Date date = new Date();
        log.logAction("Flight Log for " + date);       
        log.setNewFile(false);      //Sets newFile to false so it is not constantly resetting the file
        CreateCustomers generateCustomers = new CreateCustomers();
        HashMap<String, Airport> airports = createAirports();
        HashMap<String, Flight> flights = createFlights(airports);          //create flights objects stored by ID
        HashMap<String, Customer> customers = generateCustomers.createCustomers();    // create customer objects stored by ID  
        
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("                                 Welcome to Miner Airlines");
        System.out.println("-------------------------------------------------------------------------------------------------");
        Scanner scan = new Scanner(System.in);
        while(true){   //menu     
            String customerID = "-1";
            System.out.println("Would you like to login or exit. (Type login or exit)");
            String choice = scan.nextLine();
            if(choice.equalsIgnoreCase("login")){
                Login login = new Login();
                customerID = login.login(customers);
                if(customerID.equals("-1")){           //check login success                 
                    System.out.println("Wrong username or password Try again");
                }else{          
                    System.out.println("Login Successful!");
                    while(true){
                        System.out.println("-------------------------------------------------------------------------------------------------");
                        System.out.println("Welcome " + customers.get(customerID).getFirstName() + "! Select from the list of options below. You have and available balance of $" + customers.get(customerID).getMoneyAvailable());
                        System.out.println("1. Purchase Ticket");
                        System.out.println("2. Look up purchased tickets");
                        System.out.println("3. Cancel a Ticket");
                        System.out.println("4. Exit");
                        if(customers.get(customerID).getRole().equals("Employee")){
                            System.out.println("5. Employee Terminal");
                        }
                        System.out.println("-------------------------------------------------------------------------------------------------");
                        scan = new Scanner(System.in);
                        String purchaseTicket = scan.nextLine();
                        if(purchaseTicket.equals("1")){    
                            customers.get(customerID).customerTerminal(flights);      //runs a terminal for customers to purchase Ticket for flight. Takes in ID from login
                        }else if(purchaseTicket.equals("2")){                          
                            customers.get(customerID).printTickets();      //checks if customer has a ticket                         
                        }else if(purchaseTicket.equals("3")){
                            customers.get(customerID).printTickets();
                            if(customers.get(customerID).getTicket() == null){
                                System.out.println("Going back to menu");
                            }else{
                                System.out.println("Enter the confirmation number of the ticket you wish to cancel or enter 0 to exit");
                                scan = new Scanner(System.in);
                                String confirmationNumber = scan.nextLine();
                                if(confirmationNumber.equals("0")){
                                    System.out.println("");
                                }else{
                                    try{
                                        customers.get(customerID).getTickets().get(confirmationNumber).cancelTicket(true);  //cancels ticket by confirmation number. Takes in true parameter to show a customer is cancelling
                                        System.out.println("Ticket has been cancelled");
                                        customers.get(customerID).getTickets().remove(confirmationNumber);
                                        
                                    }catch(NullPointerException e){
                                        System.out.println("Invalid confirmation number");
                                    }
                                }
                            }
                        }else if(purchaseTicket.equals("5")){
                            Employee runTerminal = new Employee();
                            runTerminal.employeeTerminal(flights, airports,customers); //runs employee terminal
                        }
                        else if(purchaseTicket.equals("4")){
                            break;
                        }else{
                            System.out.println("Invalid input try again");
                        }
                    }
                }
            }else if(choice.equalsIgnoreCase("exit")){
                csvWriter writer = new csvWriter();
                writer.updateFlightCSV(flights);
                writer.updateCustomerCSV(customers);
                break;
            }else{
                System.out.println("Invalid input try again");
            }

            
        }

    }  

    public static HashMap<String, Airport> createAirports(){
        HashMap<String, Airport> airports = new HashMap<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("FlightSchedule.csv"));

            String line;  //stores each line in file
                   
            HashMap<Integer,String> attributeStorage = new HashMap<>();  //hashmap to store column associated with attribute in csv file
            int row = 0;
            while((line = reader.readLine())!= null){                   //while loop to read each line in csv
                String[] flightAttributes = line.split(",");            //split string in csv file by comma
                if(row == 0){
                    for(int i = 0; i < flightAttributes.length; i++){
                        attributeStorage.put(i, flightAttributes[i]);
                    }
                }else{
                    Airport airport = new Airport();
                    for(int i = 0; i < flightAttributes.length; i++){
                        if(attributeStorage.get(i).equals("Origin Code")){
                            airports.put(flightAttributes[i], airport);
                            airport.setCode(flightAttributes[i]);
                        }else if(attributeStorage.get(i).equals("Origin Airport")){
                            airport.setAirportName(flightAttributes[i]);
                        }else if(attributeStorage.get(i).equals("Origin Airport Lounge")){
                            Boolean lounge = Boolean.parseBoolean(flightAttributes[i]);
                            airport.setLounge(lounge);
                        }else if(attributeStorage.get(i).equals("Origin Airport Country")){
                            airport.setCountry(flightAttributes[i]);
                        }else if(attributeStorage.get(i).equals("Origin Airport Fee")){
                            Float fee = Float.parseFloat(flightAttributes[i]);
                            airport.setFee(fee);
                        }else if(attributeStorage.get(i).equals("Origin Airport State")){
                            airport.setState(flightAttributes[i]);
                        }else if(attributeStorage.get(i).equals("Origin Airport City")){
                            airport.setCity(flightAttributes[i]);
                        }
                        
                    }

                }
                row++;
                
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return airports;
    }
    /**
     * Created flight hashmaps
     * @return
     */
    public static HashMap<String, Flight> createFlights(HashMap<String, Airport> airports){
        HashMap<String,Flight> flights = new HashMap<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("FlightSchedule.csv"));

            String line;  //stores each line in file
                   
            HashMap<Integer,String> attributeStorage = new HashMap<>();  //hashmap to store column associated with attribute in csv file
            int row = 0;
            while((line = reader.readLine())!= null){                   //while loop to read each line in csv
                String[] flightAttributes = line.split(",");            //split string in csv file by comma
                if(row == 0){
                    for(int i = 0; i < flightAttributes.length; i++){
                        attributeStorage.put(i, flightAttributes[i]);
                    }
                }
                else{
                    Flight flight = new Flight();
                    for(int i = 0; i < flightAttributes.length; i++){           //loop to go through the cvs file and add all attributes to flight class
                        if(attributeStorage.get(i).equals("ID")){     
                            flights.put(flightAttributes[i], flight);
                            flight.setFlightID(flightAttributes[i]);
                        }
                        else if(attributeStorage.get(i).equals("Flight Number")){
                            flight.setFlightNumber(flightAttributes[i]);
                        }
                       
                        else if(attributeStorage.get(i).equals("Origin Code")){
                            flight.setOriginCode(flightAttributes[i]);
                            flight.setOriginAirport(airports.get(flightAttributes[i]));
                            
                        }
                        
                        else if(attributeStorage.get(i).equals("Destination Code")){
                            flight.setDestinationCode(flightAttributes[i]);
                            flight.setDestinationAirport(airports.get(flightAttributes[i]));
                        }
                        else if(attributeStorage.get(i).equals("Departing Date")){
                            flight.setDepartureDate(flightAttributes[i]);
                        }
                        else if(attributeStorage.get(i).equals("Departing Time")){
                            flight.setDepartureTime(flightAttributes[i]);
                        }
                        else if(attributeStorage.get(i).equals("Duration")){
                            int dur = Integer.parseInt(flightAttributes[i]);
                            flight.setDuration(dur);
                        }
                        else if(attributeStorage.get(i).equals("Distance")){
                            int dist = Integer.parseInt(flightAttributes[i]);
                            flight.setDistance(dist);
                        }
                        else if(attributeStorage.get(i).equals("Time Zone Difference")){
                            int tzDif = Integer.parseInt(flightAttributes[i]);
                            flight.setTimezoneDifference(tzDif);
                        }
                        else if(attributeStorage.get(i).equals("Arrival Date")){
                            flight.setArrivalDate(flightAttributes[i]);
                        }
                        else if(attributeStorage.get(i).equals("Arrival Time")){
                            flight.setArrivalTime();
                        }
                        else if(attributeStorage.get(i).equals("Total Seats")){
                            int tseats = Integer.parseInt(flightAttributes[i]);
                            flight.setTotalSeats(tseats);
                        }
                        else if(attributeStorage.get(i).equals("First Class Seats")){
                            int fcSeats = Integer.parseInt(flightAttributes[i]);
                            flight.setFirstClassSeats(fcSeats);
                        }
                        else if(attributeStorage.get(i).equals("Business Class Seats")){
                            int bcSeats = Integer.parseInt(flightAttributes[i]);
                            flight.setBusinessClassSeats(bcSeats);
                        }
                        else if(attributeStorage.get(i).equals("Main Cabin Seats")){
                            int mcSeats = Integer.parseInt(flightAttributes[i]);
                            flight.setMainCabinSeats(mcSeats);
                        }
                        else if(attributeStorage.get(i).equals("First Class Price")){
                            int fcPrice = Integer.parseInt(flightAttributes[i]);
                            flight.setFirstClassPrice(fcPrice);        
                        }
                        else if(attributeStorage.get(i).equals("Business Class Price")){
                            int bcPrice = Integer.parseInt(flightAttributes[i]);
                            flight.setBusinessClassPrice(bcPrice);
                        }
                        else if(attributeStorage.get(i).equals("Main Cabin Price")){
                            int mcPrice = Integer.parseInt(flightAttributes[i]);
                            flight.setMainCabinPrice(mcPrice);
                        }
                        else if(attributeStorage.get(i).contains("Type")){
                            flight.setFlightType(flightAttributes[i]);
                        }
                        else if(attributeStorage.get(i).contains("Surcharge")){
                            float surcharge = Float.parseFloat(flightAttributes[i]);
                            flight.setSurcharge(surcharge);
                        }else if(attributeStorage.get(i).contains("Route Cost")){
                            float routeCost = Float.parseFloat(flightAttributes[i]);
                            flight.setRouteCost(routeCost);
                        }else if(attributeStorage.get(i).contains("Food Served")){
                            Boolean foodServed = Boolean.parseBoolean(flightAttributes[i]);
                            flight.setFoodServed(foodServed);
                        }else if(attributeStorage.get(i).contains("Miner Points")){
                            Integer minerPoints = Integer.parseInt(flightAttributes[i]);
                            flight.setMinerPoints(minerPoints);
                        }
                    }
                    flight.setExpectedProfit();  //sets expected profit after all attributes have been added
                }
                row += 1;
            }
            reader.close();
    

        }catch(IOException e){
            e.printStackTrace();
        }
        return flights;

    }
}