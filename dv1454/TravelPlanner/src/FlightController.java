
import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightController {
    public FlightController() {
    }

    private static Integer[] dbGet(String SQL)  {
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
	
    private static String[][] dbGetFlights(String SQL)   {
        String[][] flights = new String[25][8];
        ResultSet rs;
        int count = 0;
           rs= db.query(SQL);
        try {
            while(rs.next()) {
                flights[count][0] = Integer.toString(rs.getInt("FlightID"));
                flights[count][1] = rs.getString("Departure");
                flights[count][2] = rs.getString("Destination");
                flights[count][3] = rs.getString("takeOff");
                flights[count][4] = rs.getString("travel_time");
                flights[count][5] = Integer.toString(rs.getInt("StandardPriceBusiness"));
                flights[count][6] = Integer.toString(rs.getInt("StandardPriceCoach"));
                flights[count][7] = Integer.toString(rs.getInt("StandardPriceFirstClass"));
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }
	
    public static boolean bookFlight(int id) {
        Integer[] flight = FlightController.dbGet(
            "SELECT * FROM Flights WHERE FlightID = " + id
        );

        return true;
    }
	
    public static String[][] getAllFlights() {
        String[][] flights = new String[0][];
        flights = FlightController.dbGetFlights("SELECT *,CONVERT(VARCHAR(8),Flight.Landing-Flight.TakeOff,108) AS travel_time FROM Flight INNER JOIN Travel on Flight.TravelID=Travel.TravelID");
        return flights;
    }
    
    public static String[][] getFlights(String origin, String destination, String date) {
        String[][] flights;
            flights = FlightController.dbGetFlights(
                "SELECT * ," +
                        "CONVERT(VARCHAR(8),Flight.Landing-Flight.TakeOff,108)" +
                        " AS travel_time FROM Flight " +
                        "INNER JOIN Travel on Flight.TravelID=Travel.TravelID WHERE Travel.Departure = '" +
                origin + "' AND Destination = '" + destination +
                "' AND CONVERT(VARCHAR(8),Flight.TakeOff,1)  = '" + date + "'"
            );
        return flights;
    }


}
