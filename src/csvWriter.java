
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class to write updated csv files
 */

public class csvWriter {

    /**
     * updates flight csv file
     * @param flights
     */
    public void updateFlightCSV(HashMap<String, Flight> flights){  //creates a new csv document with updated information
        String[] header = {"ID","Flight Number","Origin Airport","Origin Code","Destination Airport","Destination Code","Departing Date","Departing Time","Duration","Distance","Time Zone Difference","Arrival Date","Arrival Time","Flight Type", "Surcharge","Food Served","Route Cost","Miner Points","Total Seats","First Class Seats","Business Class Seats","Main Cabin Seats","First Class Price","Business Class Price","Main Cabin Price","First Class Seats Sold","Business Class Seats Sold", "Main Cabin Seats Sold", "Total First Class Revenue","Total Business Class Revenue", "Total Main Cabin Revenue"};
        try (FileWriter writer = new FileWriter("updatedFlightSchedule.csv")) {
            writer.write(String.join(",", header));
            writer.write("\n");
            for(String key : flights.keySet()){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              //"First Class Seats Sold","Business Class Seats Sold", "Main Cabin Seats Sold", "Total First Class Revenue","Total Business Class Revenue", "Total Main Cabin Revenue"                                                           
                String[] row = {key, flights.get(key).getFlightNumber(),flights.get(key).getOriginAirport().getAirportName(),flights.get(key).getOriginCode(),flights.get(key).getDestinationAirport().getAirportName(),flights.get(key).getDestinationCode(),flights.get(key).getDepartureDate(),flights.get(key).getDepartureTime(),String.valueOf(flights.get(key).getDuration()), String.valueOf(flights.get(key).getDistance()), String.valueOf(flights.get(key).getTimezoneDifference()), flights.get(key).getArrivalDate(), flights.get(key).getArrivalTime(), flights.get(key).getFlightType(), String.valueOf(flights.get(key).getSurcharge()),String.valueOf(flights.get(key).getFoodServed()), String.valueOf(flights.get(key).getRouteCost()), String.valueOf(flights.get(key).getMinerPoints()), String.valueOf(flights.get(key).getTotalSeats()), String.valueOf(flights.get(key).getFirstClassSeats()), String.valueOf(flights.get(key).getBusinessClassSeats()), String.valueOf(flights.get(key).getMainCabinSeats()), String.valueOf(flights.get(key).getFirstClassPrice()),String.valueOf(flights.get(key).getBusinessClassPrice()), String.valueOf(flights.get(key).getMainCabinPrice()),String.valueOf(flights.get(key).getfClassSeatsSold()),String.valueOf(flights.get(key).getbClassSeatsSold()),String.valueOf(flights.get(key).getmCabinSeatsSold()),String.valueOf(flights.get(key).getfClassRevenue()),String.valueOf(flights.get(key).getbClassRevenue()),String.valueOf(flights.get(key).getmCabinRevenue())};
                writer.write(String.join(",", row));
                writer.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }

    }
    /**
     * updates customer csv file
     * @param customers
     */
    public void updateCustomerCSV(HashMap<String, Customer> customers){
        String[] header = {"ID","First Name","Last Name","DOB","Role","Money Available","Flights Purchased","MinerAir Membership","Username","Password"};
        try (FileWriter writer = new FileWriter("updatedCustomerList.csv")){
            writer.write(String.join(",", header));
            writer.write("\n");
            for(String key : customers.keySet()){        
                String[] row = {key, customers.get(key).getFirstName(),customers.get(key).getLastName(),customers.get(key).getDob(),customers.get(key).getRole(),String.valueOf(customers.get(key).getMoneyAvailable()),String.valueOf(customers.get(key).getFlightsPurchased()),String.valueOf(customers.get(key).getMinerAirlinesMembership()),customers.get(key).getUserName(),customers.get(key).getPassword()};
                writer.write(String.join(",", row));
                writer.write("\n");
            }
        }catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }

    }
}
