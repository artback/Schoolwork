
import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightController {
    public FlightController() {
    }
    

    private Integer[] dbGet(String SQL)  {
        Integer flight[] = new Integer[2];

            ResultSet rs = db.query(SQL);
        try {
            while(rs.next()) {
                flight[0] = rs.getInt("flight_id");
                flight[1] = rs.getInt("nr_of_seats");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flight;
    }
	
    private String[][] dbGetFlights(String SQL)   {
        String[][] flights = new String[25][8];
        ResultSet rs;
        int count = 0;
           rs= db.query(SQL);
        try {
            while(rs.next()) {
                flights[count][0] = Integer.toString(rs.getInt("FlightID"));
                flights[count][1] = rs.getString("Departure");
                flights[count][2] = rs.getString("Destination");
                flights[count][3] = rs.getString("Departure");
                flights[count][4] = rs.getString("TakeOff");
                flights[count][5] = rs.getString("travel_time");
                flights[count][6] = Integer.toString(rs.getInt("price"));
                flights[count][7] = Integer.toString(rs.getInt("nr_of_seats"));
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }
	
    public boolean bookFlight(int id, int nrOfPassengers) {
        Integer[] flight = this.dbGet(
            "SELECT * FROM Flights WHERE flight_id = " + id
        );

    }
	
    public String[][] getAllFlights() {
        String[][] flights = new String[0][];
        flights = this.dbGetFlights("SELECT * (Flight.Landing-Flight.TakeOff) AS travel_time FROM Flight INNER JOIN Travel on Flight.TravelID=Travel.TravelID");
        return flights;
    }
    
    public String[][] getFlights(String origin, String destination, String date) {
        String[][] flights = new String[0][];
            flights = this.dbGetFlights(
                "SELECT * FROM Flight WHERE Departure = '" +
                origin + "' AND Destination = '" + destination +
                "' AND takeOff  = '" + date + "'"
            );
        return flights;
    }


}
