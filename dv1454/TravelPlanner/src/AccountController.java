package TravelPlanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ThreadLocalRandom;

public class AccountController {
    private final String user;
    private final String password;

    public AccountController(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String[] login() {
        String[] ret = new String[2];
        ret[0] = "";
        ret[1] = "";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" +
                SystemController.dbPath);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(
                "SELECT first_name, last_name, email, user_password_hash," +
                "account_active, admin  FROM users WHERE email = '" +
                this.user + "'"
            );

            String pwdHash = rs.getString("user_password_hash");
            //LOGIN
            if (pwdHash.equals(password)) {
            	ret[0] = this.user;
                ret[1] = rs.getString("first_name") + " " + rs.getString("last_name");
        		
            	if (rs.getString("admin").equals("1")) {
                    ret[0] = "ADMIN";
                    ret[1] = "Admin";
            	}
            	
                if (rs.getString("account_active").equals("0")) {
                    ret[0] = "ACTIVATE";
            	}
            } else {
                ret[0] = "";   //password incorrect
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            ret[0] = "";
            return ret; // return if user isn't found
        } finally {
            try {
                if(connection != null)
                connection.close();
            } catch(SQLException e) {
                System.err.println(e);
            }
        }

        return ret;
    }

    public boolean register(String firstName, String lastName) {
        boolean success = addUserToDB(firstName, lastName);
        return success;
    }

    private boolean addUserToDB(String firstName, String lastName) {
        boolean ret = false;
        String activationHash = this.makeActivationHash();

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" +
                SystemController.dbPath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //CHECK IF A USER WITH THE EMAIL ALREADY EXISTS
            ResultSet rs = statement.executeQuery(
                "SELECT email FROM users WHERE email = '" + this.user + "'"
            );	

            //IF NO SUCH USER EXISTS ADD THE USER
            if( !rs.isBeforeFirst() ) {
                statement.executeUpdate(
                    "INSERT INTO users VALUES(" + null + ", " +
                    "'" + this.user + "', " +
                    "'" + this.password + "', " +
                    "'" + activationHash + "', " +
                    "'" + 1 + "', " +
                    "'" + 0 + "', " +
                    "'" + firstName + "', " +
                    "'" + lastName + "' )"
                ); 

                /*// The code above is for demoing, the acount is instantly activated. Code below is original
                statement.executeUpdate(
                    "INSERT INTO users VALUES(" + null + ", " +
                    "'" + this.user + "', " +
                    "'" + this.password + "', " +
                    "'" + activationHash + "', " +
                    "'" + 0 + "', " +
                    "'" + 0 + "', " +
                    "'" + firstName + "', " +
                    "'" + lastName + "' )"
                );                
                 */
                
                ret = true;
            }
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

        return ret;
    }
    
    private String makeActivationHash(){
    	int ret = ThreadLocalRandom.current().nextInt(10000000, 49999999);
    	return Integer.toString(ret);
    	
    }
}