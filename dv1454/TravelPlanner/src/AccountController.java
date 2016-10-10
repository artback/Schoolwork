
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

    public String[] login() throws SQLException {
        String[] ret = new String[2];
        ret[0] = "";
        ret[1] = "";
            ResultSet rs = db.query();

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
        return ret;
    }

    public boolean register(String firstName, String lastName) {
        boolean success = addUserToDB(firstName, lastName);
        return success;
    }

    private void addUserToDB(String firstName, String lastName, String Adress,
                                String Email,String PhoneNr) {

            ResultSet rs = db.query("SELECT ");

            //IF NO SUCH USER EXISTS ADD THE USER
            if( !rs.isBeforeFirst() ) {
                statement.executeUpdate(
                    "INSERT INTO Customer VALUES(" + null + ", " +
                    "'" + this.user + "', " +
                    "'" + this.password + "', " +
                    "'" + firstName + "', " +
                    "'" + lastName + "' )"
                ); 
            }
    }
    
}