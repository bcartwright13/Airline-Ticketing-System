import java.util.HashMap;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
public class AutoPurchase{

    public AutoPurchase() {
    }
    public void runAutoPurchaser(HashMap<String, Flight> flights, HashMap<String, Customer> customers,String file){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;  //stores each line in file
                   
            HashMap<Integer,String> attributeStorage = new HashMap<>();  //hashmap to store column associated with attribute in csv file
            int row = 0;
            String firstName = "";
            String lastName = "";
            String ticketType = "";
            int ticketQuantity = 0;
            Customer customer = new Customer();
            String flightID = "0";
            while((line = reader.readLine())!= null){                   //while loop to read each line in csv
                String[] flightAttributes = line.split(",");            //split string in csv file by comma
                if(row == 0){
                    for(int i = 0; i < flightAttributes.length; i++){
                        attributeStorage.put(i, flightAttributes[i]);
                    }
                }else{
                
                    for(int i = 0; i < flightAttributes.length; i++){
                        if(attributeStorage.get(i).equals("First Name")){
                            firstName = flightAttributes[i];
                        }else if(attributeStorage.get(i).equals("Last Name")){
                            lastName = flightAttributes[i];
                        }else if(attributeStorage.get(i).equals("Ticket Quantity")){
                            ticketQuantity = Integer.parseInt(flightAttributes[i]);
                        }else if (attributeStorage.get(i).equals("Ticket Type")){
                            ticketType = flightAttributes[i];
                        }else if (attributeStorage.get(i).equals("Flight ID")){
                            flightID = flightAttributes[i];
                        }
                    }
                    for(int j = 1; j < customers.size() + 1; j++){
                        String i = String.valueOf(j);
                        if(customers.get(i).getFirstName().equals(firstName) && customers.get(i).getLastName().equals(lastName)){
                            customer = customers.get(i);
                        }
                    }
                    purchase(flights, customer, ticketQuantity, ticketType, flightID);
                }
                row++;
            }
        }catch(IOException e){
            System.out.println("Can't find file");
        }
    }
    public void purchase(HashMap<String,Flight> flights, Customer customer ,int numberOfSeats, String seatType, String flightID){
        int seat = 0;
        float surchargeTotal = 0;
        float subtotalBeforeFees = 0;
        float subtotal = 0;
        float employeeDiscount = 0;
        if(flights.get(flightID).getFlightType().equals("International")){                 //calculates surcharge total if flight is international
            surchargeTotal = flights.get(flightID).getSurcharge() * numberOfSeats;
        }
        if(seatType.equals("First Class")){
            seat = flights.get(flightID).getFirstClassSeats();
            subtotalBeforeFees = numberOfSeats * flights.get(flightID).getFirstClassPrice() + surchargeTotal; 
            employeeDiscount = 0.5f;   
        }else if(seatType.equals("Business Class")){
            seat = flights.get(flightID).getBusinessClassSeats();
            employeeDiscount = 0.75f;
            subtotalBeforeFees = numberOfSeats * flights.get(flightID).getBusinessClassPrice() + surchargeTotal;
        }else if(seatType.equals("Main Cabin")){
            seat = flights.get(flightID).getMainCabinSeats();
            subtotalBeforeFees = numberOfSeats * flights.get(flightID).getMainCabinPrice() + surchargeTotal;
            employeeDiscount = 0.75f;
        }
        if(customer.getRole().contains("Employee")){
            subtotalBeforeFees = subtotalBeforeFees - (subtotalBeforeFees * employeeDiscount);
        }
        float savings = 0;
        if(customer.getMinerAirlinesMembership()){
            savings = subtotalBeforeFees * 0.05f;
            
            subtotal = subtotal - savings;
        }
        float minerAirlinesFee = 9.15f;
        float securityFee = 5.60f;
        float totalPrice = 0;
        subtotal = subtotalBeforeFees + minerAirlinesFee + securityFee + flights.get(flightID).getOriginAirport().getFee();
        float tax = customer.calculateTax(subtotal);
        totalPrice = tax + subtotal;
        Log log = Log.getInstance();
     
        if(customer.getMoneyAvailable() - totalPrice > 0 && seat - numberOfSeats >=0){
            if(seatType.equals("First Class")){
                flights.get(flightID).setFirstClassSeats(flights.get(flightID).getFirstClassSeats() - numberOfSeats);
            }else if(seatType.equals("Business Class")){
                flights.get(flightID).setBusinessClassSeats(flights.get(flightID).getBusinessClassSeats() - numberOfSeats);
            }else if(seatType.equals("Main Cabin")){
                flights.get(flightID).setMainCabinSeats(flights.get(flightID).getMainCabinSeats() - numberOfSeats);
            }
            customer.setTicket(customer.ticketGenerator(numberOfSeats, totalPrice, flights.get(flightID)));
            customer.getTicket().setSeatType(seatType);
            customer.getTicket().setCustomer(customer);
            customer.setTickets(customer.getTicket().getConfirmationNumber(), customer.getTicket());
            customer.setMoneyAvailable(customer.getMoneyAvailable() - totalPrice);
            log.logAction(customer.getFirstName() + " " + customer.getLastName() + " purchased ticket for Flight " + flightID + " from " + flights.get(flightID).getOriginCode() + " to " + flights.get(flightID).getDestinationCode() + " with " + numberOfSeats + " " + seatType);
            customer.setFlightsPurchased(customer.getFlightsPurchased() + 1);
            customer.addMinerAirSavings(savings);
            flights.get(flightID).addTotalDiscounter(savings);
            flights.get(flightID).addTotalCollectedTaxes(tax);
            flights.get(flightID).getOriginAirport().addtofees(flights.get(flightID).getOriginAirport().getFee());
        }
    }
}
