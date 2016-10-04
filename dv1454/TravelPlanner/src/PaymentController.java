package travelplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class PaymentController{
    private final BankMock bank;
    private final String user;
    private final String cardNr;
    private final int price;
    
    public PaymentController(String cardNr, int price, String user){
        this.bank = new BankMock();
        this.cardNr = cardNr;
        this.price = price;
        this.user = user;
    }
    public Boolean makePayment() {
        boolean payed = false;
        
        if (!this.cardNr.equals("0")) {
           payed = this.bank.makePayment(this.cardNr, this.price);
        }
        
        if (payed) {
            logTranscation();
        }
        
        return payed;
    }
    
    private boolean logTranscation(){
        Connection connection = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" +
                SystemController.dbPath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate(
                "INSERT INTO transactions VALUES (" + null + ", " +
                "'" + this.user + "', " +
                "'" + this.price + "', " +
                "'" + Calendar.getInstance().getTime() + "' )"
            );
            
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                System.err.println(e);
            }
        }
        return true;
    }
}


