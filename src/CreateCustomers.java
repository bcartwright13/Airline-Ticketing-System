import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class that creates customer objects stored in hashmaps
 */
public class CreateCustomers {
    public CreateCustomers(){
        
    }
    public HashMap<String, Customer> createCustomers(){      //Create customer objects and store by id
        HashMap<String, Customer> customers = new HashMap<>();
        HashMap<Integer,String> attributeStorage = new HashMap<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("CustomerListPA4.csv"));
            String line;
            int row = 0;
            while((line = reader.readLine())!= null){                   //while loop to read each line in csv
                String[] customerAttributes = line.split(",");            //split string in csv file by comma
                if(row == 0){
                    for(int i = 0; i < customerAttributes.length; i++){
                        attributeStorage.put(i, customerAttributes[i]);
                    }
                }
                else{
                    Customer customer = new Customer();
                    for(int i = 0; i < customerAttributes.length; i++){           //loop to go through the cvs file and add all attributes to flight class
                        if(attributeStorage.get(i).equals("ID")){     
                            customers.put(customerAttributes[i], customer);
                        }
                        else if(attributeStorage.get(i).equals("First Name")){
                            customer.setFirstName(customerAttributes[i]);
                        }
                        else if(attributeStorage.get(i).equals("Last Name")){
                            customer.setLastName(customerAttributes[i]);
                        }
                        else if(attributeStorage.get(i).equals("Money Available")){
                            double money = Double.parseDouble(customerAttributes[i]);
                            customer.setMoneyAvailable(money);
                        }
                        else if(attributeStorage.get(i).equals("Flights Purchased")){
                            int purchasedFlights = Integer.parseInt(customerAttributes[i]);
                            customer.setFlightsPurchased(purchasedFlights);
                        }
                        else if(attributeStorage.get(i).equals("MinerAirlines Membership")){
                            boolean membership = Boolean.parseBoolean(customerAttributes[i]);
                            customer.setMinersAirlineMembership(membership);
                        }
                        else if(attributeStorage.get(i).equals("Username")){
                            customer.setUserName(customerAttributes[i]);
                        }
                        else if(attributeStorage.get(i).equals("Password")){
                            customer.setPassword(customerAttributes[i]);
                        }
                        else if(attributeStorage.get(i).equals("DOB")){
                            customer.setDob(customerAttributes[i]);
                        } else if(attributeStorage.get(i).equals("Role")){
                            customer.setRole(customerAttributes[i]);
                        }
                        
                    }
                }
                row += 1;
            }
            reader.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return customers;
    }
}
