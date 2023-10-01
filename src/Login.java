import java.util.HashMap;
import java.util.Scanner;

/**
 * Login class used for login terminal
 */

public class Login {
    private String username;
    private String password;

    public Login(){

    }
    /**
     * Login method that gives the user the oppurtunity to log in.
     * @param customers Takes in customer hashmap to check username and password
     * @return
     */
    public String login(HashMap<String, Customer> customers){      //function to log customer in 
        Log log = Log.getInstance();
        Scanner scan = new Scanner(System.in);
        
        
        while(true){
            System.out.println("Enter your first and last name");
            String name = scan.nextLine();
            try{
                String[] split = name.split(" ");
                String firstName = split[0];
                String lastName = split[1];
                scan = new Scanner(System.in);
                System.out.println("Enter your username");
                this.username = scan.nextLine();                      
                scan = new Scanner(System.in);                     // User enters username and password
                System.out.println("Enter Password");
                this.password = scan.nextLine();
                for(int i = 1; i<customers.size() + 1; i++){
                    String j = Integer.toString(i);
                    if(customers.get(j).getUserName().equals(username) && customers.get(j).getPassword().equals(password)){    //checks username in password by going through all customers and returns customer ID
                        log.logAction(customers.get(j).getFirstName() + " " + customers.get(j).getLastName() + " has logged in");
                        return j; //j is customer ID
                    }
                }
                return "-1";
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println("You did not enter a first and last name.");
            }
        }
    }
    
}
