
import java.sql.ResultSet;

public class AccountController {



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