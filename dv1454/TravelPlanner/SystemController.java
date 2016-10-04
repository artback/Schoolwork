package travelplanner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SystemController {

    public static String dbPath = "E:/massa goa grejor/Skola/PA1415/"
            + "TravelPlanner_netbeans.project/travelplanner/src/"
            + "TravelPlanner/pa1415_group.e2_travelplanner.db";

    public String user;
    public String userName;
    private FlightController flight;
    private AccountController account;
    private PaymentController pay;
    
    SystemController() {
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
            EmailController ec = new EmailController(userName);
            ec.sendActivate();
        	
            ret = true;
        } else {
            user = null;
        }
        
        return ret;
    }
    
    public boolean bookFlight(int id, int nrOfPassangers, String cardNr, int price) {
    	boolean success = false;
    	
    	if (this.luhn(cardNr)) {
            this.pay = new PaymentController(cardNr, price, this.user);

            EmailController ec = new EmailController(this.user);
            this.flight = new FlightController();

            pay.makePayment();
            ec.sendRecipt();
            success = this.flight.bookFlight(id, nrOfPassangers);    	
    	}
    	
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
    
    private boolean luhn(String ccNumber) {
        int sum = 0;
        boolean alternate = false;

        for (int i = ccNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));

            if (alternate) {
                n *= 2;

                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }
}
