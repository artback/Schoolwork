
import java.sql.Connection;
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
	
    private String[][] dbGetFlights(String where)   {
        String[][] flights = new String[25][8];
        ResultSet rs;
        int count = 0;
            if(where != "") {
           rs= db.query("SELECT * (Flight.Landing-Flight.TakeOff) FROM Flight INNER JOIN Travel on Flight.TravelID=Travel.TravelID" + where);
            } else{
            rs = db.query("SELECT * (Flight.Landing-Flight.TakeOff) AS travel_time FROM Flight INNER JOIN Travel on Flight.TravelID=Travel.TravelID");
                }
        try {
            while(rs.next()) {
                flights[count][0] = Integer.toString(rs.getInt("flight_id"));
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
            "SELECT * FROM flights WHERE flight_id = " + id
        );
    }
	
    public String[][] getAllFlights() {
        String[][] flights = new String[0][];
        flights = this.dbGetFlights(");
        return flights;
    }
    
    public String[][] getFlights(String origin, String destination, String date) {
        String[][] flights = new String[0][];
            flights = this.dbGetFlights(
                "SELECT * FROM flights WHERE origin = '" +
                origin + "' AND destination = '" + destination +
                "' AND departure_date = '" + date + "'"
            );
        return flights;
    }



}
