/**
 * Airport class for creating airport objects
 */

public class Airport {
    //all flights will have a src and destination airport objects
    private String airportName;
    private boolean lounge;
    private String state;
    private String country;
    private float fee;
    private String city;
    private String code;
    private float totalFeesCollected = 0;


    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Airport(){

    }

    public void addtofees(float fee){
        this.totalFeesCollected += fee;
    }
    public float getTotalFeesCollected(){
        return this.totalFeesCollected;
    }

    /**
     * @return String return the airportName
     */
    public String getAirportName() {
        return airportName;
    }

    /**
     * @param airportName the airportName to set
     */
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    /**
     * @return boolean return the lounge
     */
    public boolean isLounge() {
        return lounge;
    }

    /**
     * @param lounge the lounge to set
     */
    public void setLounge(boolean lounge) {
        this.lounge = lounge;
    }

    /**
     * @return String return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return String return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return float return the fee
     */
    public float getFee() {
        return fee;
    }

    /**
     * @param fee the fee to set
     */
    public void setFee(float fee) {
        this.fee = fee;
    }

    /**
     * @return String return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }
    public void printAirportInformation(){
        System.out.println("Airport Code: " + this.code);
        System.out.println("Airport Name: " + this.airportName);
        System.out.println("Airport City: " + this.city);
        System.out.println("Airport State: " + this.state);
        System.out.println("Airport Country: " + this.country);
        System.out.println("Airport Fee: $" + this.fee);
        System.out.println("Lounge Status: " + this.lounge);
        System.out.println("Total Fees Collected: " + this.totalFeesCollected);
    }
}
