
import java.sql.ResultSet;

public class AccountController {



    private void addUserToDB(String firstName, String lastName, String Adress,
                                String Email,String PhoneNr) {

            ResultSet rs = db.query("INSERT INTO Customer VALUES(" + null + ", " +
                            "'" + firstName + "', " +
                            "'" + lastName + "', " +
                            "'" + firstName + "', " +
                            "'" + lastName + "' )");
;

    }
    
}