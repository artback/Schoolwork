import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketController{
    public static String[][] getTickets(String lastName,String phoneNr){
        ResultSet rs;
        if(!lastName.equals("") && !phoneNr.equals("")) {
            rs = db.query("SELECT * FROM Customer INNER JOIN Ticket ON " +
                    "Customer.CustomerID=Ticket.CustomerID " +
                    "INNER JOIN Flight ON Ticket.FlightID=Flight.FlightID " +
                    "INNER JOIN Travel ON Travel.TravelID=Flight.TravelID " +
                    "WHERE LastName= '" + lastName + "' AND PhoneNr= '" + phoneNr + "'");
        }
        else if(lastName.equals("") && !phoneNr.equals("")) {
            rs = db.query("SELECT * FROM Customer INNER JOIN Ticket ON " +
                    "Customer.CustomerID=Ticket.CustomerID " +
                    "INNER JOIN Flight ON Ticket.FlightID=Flight.FlightID " +
                    "INNER JOIN Travel ON Travel.TravelID=Flight.TravelID " +
                    "WHERE PhoneNr= '" + phoneNr + "'");
        }
        else if (!lastName.equals("") && phoneNr.equals("") ){
            rs = db.query("SELECT * FROM Customer INNER JOIN Ticket ON " +
                    "Customer.CustomerID=Ticket.CustomerID " +
                    "INNER JOIN Flight ON Ticket.FlightID=Flight.FlightID " +
                    "INNER JOIN Travel ON Travel.TravelID=Flight.TravelID " +
                    "WHERE LastName= '" + lastName + "'" );
        }
        else {
                rs = db.query("SELECT * FROM Customer INNER JOIN Ticket ON Customer.CustomerID=Ticket.CustomerID " +
                        "INNER JOIN Flight ON Ticket.FlightID=Flight.FlightID " +
                        "INNER JOIN Travel ON Travel.TravelID=Flight.TravelID " );
        }
        int top = 25;
        String[][]  tickets = new String[top][9];
        int count = 0;
        try {
            while(rs.next() && count < top) {

                tickets[count][0] = Integer.toString(rs.getInt("CustomerID"));
                tickets[count][1] = Integer.toString(rs.getInt("TicketID"));
                tickets[count][2] = rs.getString("FirstName");
                tickets[count][3] = rs.getString("LastName");
                tickets[count][4] = rs.getString("Price");
                tickets[count][5] = rs.getString("TakeOff");
                tickets[count][6] = rs.getString("Departure");
                tickets[count][7] = rs.getString("Destination");
                tickets[count][8] = rs.getString("Email");
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public static boolean makePayment(int cID,int seatType,int flightID,int price) {
        return db.insert("EXEC BookTicket "+ cID + "," +  flightID + "," + price + ",0," + seatType);
    }
    public static void removeTicket(int ticketID){
        db.insert("EXEC cancelTicket " + ticketID );
    }
}


