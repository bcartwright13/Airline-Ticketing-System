import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ElectronicTicket {
    public void writeElectonicTicketSummary(Customer customer, String txtfile){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(txtfile,false));  // checks if the file needs to be overwritten or not
            for(String key: customer.getTickets().keySet()){
                Ticket ticket = customer.getTickets().get(key);
                writer.write("-------------------------------------------------------------------------------------------------");
                writer.newLine();
                writer.write("                                  ELECTRONIC TICKET SUMMARY");
                writer.newLine();
                writer.write("-------------------------------------------------------------------------------------------------");
                writer.newLine();
                writer.write("Confirmation Number: " + ticket.getConfirmationNumber());
                writer.newLine();
                writer.write("Flight Origin Airport Code: " + ticket.getFlight().getOriginAirport().getCode());
                writer.newLine();
                writer.write("Flight Origin Airport Name: " + ticket.getFlight().getOriginAirport().getAirportName());
                writer.newLine();
                writer.write("Flight Destination Airport Code: " + ticket.getFlight().getDestinationAirport().getCode());
                writer.newLine();
                writer.write("Flight Destination Airport Name: " + ticket.getFlight().getDestinationAirport().getAirportName());
                writer.newLine();
                writer.write("Departure Date: " + ticket.getFlight().getDepartureDate());
                writer.newLine();
                writer.write("Departure Time: " + ticket.getFlight().getDepartureTime());
                writer.newLine();
                writer.write("Arrival Date: " + ticket.getFlight().getArrivalDate());
                writer.newLine();
                writer.write("Arrival Time: " + ticket.getFlight().getArrivalTime());
                writer.newLine();
                writer.write("Ticket Type: " + ticket.getSeatType());
                writer.newLine();
                writer.write("Ticket Quantity: " + ticket.getHowManyTickets());
                writer.newLine();
                writer.write("Total Cost: " + ticket.getTotalPrice());
                writer.newLine();
                
                

            }
            writer.close();
        }
        catch (IOException e){ //checks if file name exists
            System.out.println("Error when writing to file");
            e.printStackTrace();
        }
    }
}
