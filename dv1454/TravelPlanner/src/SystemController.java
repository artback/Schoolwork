
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class SystemController {


    private FlightController flight;
    private PaymentController pay;
    
    public SystemController() {
       flight = new FlightController();
    }

    public boolean bookFlight(int id, int nrOfPassangers, String cardNr, int price) {
    	boolean success = false;
    	
            this.pay = new PaymentController(cardNr, price, "");

            this.flight = new FlightController();

            pay.makePayment();
            success = this.flight.bookFlight(id, nrOfPassangers);

    	return success;
    }
    public String[][] getAllFlights(){
    	this.flight = new FlightController();
    	
    	return this.flight.getAllFlights();
    }
    
    public String[][] getFlights(String origin, String destination, String date) {
        this.flight = new FlightController();
        
        return this.flight.getFlights(origin, destination, date);
    }
    
    


    public boolean updateFlight(int id, String origin, String destination,
        String deptDate, String deptTime, String travelTime, int price,
        int nrOfSeats) {
        this.flight = new FlightController();
        return true;
    }

}
