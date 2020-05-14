import java.sql.ResultSet;
import java.sql.SQLException;

public final class AccountController {

    public static String[][] searchUsers(String lastName, String phoneNr){
        ResultSet rs;
        if(!lastName.equals("") && !phoneNr.equals("")) {
            rs = db.query("SELECT * FROM Customer WHERE LastName= '" + lastName + "' AND PhoneNr= '" + phoneNr + "'");
        }
        else if(lastName.equals("") && !phoneNr.equals("")) {
            rs = db.query("SELECT * FROM Customer WHERE PhoneNr= '" + phoneNr + "'");
        }
        else if (!lastName.equals("") && phoneNr.equals("") ){
            rs = db.query("SELECT * FROM Customer WHERE LastName= '" + lastName + "'" );
        }
        else {
            rs = db.query("SELECT * FROM Customer" );
        }
        int total=25;

        String[][] users = new String[total][6];
        int count = 0;
        try {
            while(rs.next() && count < total) {
                users[count][0] = Integer.toString(rs.getInt("CustomerID"));
                users[count][1] = rs.getString("FirstName");
                users[count][2] = rs.getString("LastName");
                users[count][3] = rs.getString("Address");
                users[count][4] = rs.getString("Email");
                users[count][5] = rs.getString("PhoneNr");
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    public static boolean addUserToDB(String firstName, String lastName, String Adress,
                                String Email,String phoneNr) {

           return db.insert("INSERT INTO Customer (FirstName,LastName,Address,Email,PhoneNr)  VALUES("+
                            "'" + firstName + "', " +
                            "'" + lastName + "', " +
                            "'" + Adress + "', " +
                            "'" + Email + "', '" + phoneNr + "' )");

    }
    
}