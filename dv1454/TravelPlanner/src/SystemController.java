
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class SystemController {
    public static class db{
        String userName = "username";
        String password = "password";
        String url = "jdbc:sqlserver://MYPC\\SQLEXPRESS;databaseName=MYDB";
         
    };


    public String user;
    public String userName;
    private FlightController flight;
    private AccountController account;
    private PaymentController pay;
    
    SystemController() {
        DB
    }
    
    public boolean login(String username, String password) throws NoSuchAlgorithmException {
        byte[] bytes;
        String[] loginReturn = new String[2];

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        bytes = md.digest();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        String hashedPassword = sb.toString();
        
        this.account = new AccountController(username, hashedPassword);
        loginReturn = account.login();
        this.user = loginReturn[0];
        this.userName = loginReturn[1];
        
        boolean ret = true;
        
        if (this.user.equals("")) {
            ret = false;
        } else if (this.user.equals("ACTIVATE")) {
            this.user = null;
            this.userName = null;
            ret = false;
        }
        
        return ret;
    }
    
    public boolean register(String username, String password, String firstName,
        String lastName) throws NoSuchAlgorithmException {
        byte[] bytes;
        boolean ret = false;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        bytes = md.digest();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        String hashedPassword = sb.toString();
        this.account = new AccountController(username, hashedPassword);
        boolean result = account.register(firstName, lastName);

        if (result) {
            //Send activation email

            ret = true;
        } else {
            user = null;
        }
        
        return ret;
    }
    
    public boolean bookFlight(int id, int nrOfPassangers, String cardNr, int price) {
    	boolean success = false;
    	
            this.pay = new PaymentController(cardNr, price, this.user);

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
    
    
    public boolean addFlight(String origin, String destination, String deptDate,
        String deptTime, String travelTime,int price, int nrOfSeats) {
        this.flight = new FlightController();
        
        return this.flight.addFlight(origin, destination, deptDate, deptTime,
            travelTime, price, nrOfSeats);
    }
    
    public boolean removeFlight(int id) {
        this.flight = new FlightController();
        
        return this.flight.removeFlight(id);
    }
    
    public boolean updateFlight(int id, String origin, String destination,
        String deptDate, String deptTime, String travelTime, int price,
        int nrOfSeats) {
        this.flight = new FlightController();
        
        return this.flight.updateFlight(id, origin, destination, deptDate,
            deptTime, travelTime, price, nrOfSeats);
    }

}
