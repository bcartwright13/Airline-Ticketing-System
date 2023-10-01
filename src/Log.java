import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Log class used for logging to a txt file
 */

public class Log {
    private boolean newFile;

    private static Log obj;

    private Log(){
        
    }

    public static Log getInstance(){
        if(obj == null){
            obj = new Log();
        }
        return obj;
    }
    
    public void setNewFile(boolean newFile){
        this.newFile = newFile;
    }
    /**
     * Writes to a txt file every action taken in the program
     * @param s
     */
    public void logAction(String s){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("flightLog.txt", !this.newFile));  // checks if the file needs to be overwritten or not
            writer.write(s);
            writer.newLine();  // created new line
            writer.close();
        }
        catch (IOException e){ //checks if file name exists
            System.out.println("Error when writing to file");
            e.printStackTrace();
        }
    }
}
