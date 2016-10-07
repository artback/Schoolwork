
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
	
    private String[][] dbGetFlights(String SQL)   {
        Connection connection = null;
        String[][] flights = new String[25][8];
        

                int count = 0;
                ResultSet rs = db.query(SQL);
        try {
            while(rs.next()) {
                flights[count][0] = Integer.toString(rs.getInt("flight_id"));
                flights[count][1] = rs.getString("");
                flights[count][2] = rs.getString("Landing");
                flights[count][3] = rs.getString("departure_date");
                flights[count][4] = rs.getString("departure_time");
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
        int nrOfSeats = flight[1] - nrOfPassengers;

    }
	
    public String[][] getAllFlights() {
        String[][] flights = new String[0][];
        flights = this.dbGetFlights("SELECT * FROM Flight INNER JOIN Travel on Flight.TravelID=Travel.TravelID");
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
