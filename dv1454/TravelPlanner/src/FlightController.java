
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FlightController {
    public FlightController() {
    }
    

    private Integer[] dbGet(String SQL) throws SQLException {
        Connection connection = null;
        Integer flight[] = new Integer[2];

            ResultSet rs = db.query(SQL);
                while(rs.next()) {
                    flight[0] = rs.getInt("flight_id");
                    flight[1] = rs.getInt("nr_of_seats");
                }
        return flight;
    }
	
    private String[][] dbGetFlights(String SQL) throws SQLException {
        Connection connection = null;
        String[][] flights = new String[25][8];
        

                int count = 0;
                ResultSet rs = db.query(SQL);
                while(rs.next()) {
                    flights[count][0] = Integer.toString(rs.getInt("flight_id"));
                    flights[count][1] = rs.getString("origin");
                    flights[count][2] = rs.getString("destination");
                    flights[count][3] = rs.getString("departure_date");
                    flights[count][4] = rs.getString("departure_time");
                    flights[count][5] = rs.getString("travel_time");
                    flights[count][6] = Integer.toString(rs.getInt("price"));
                    flights[count][7] = Integer.toString(rs.getInt("nr_of_seats"));
                    count++;
                }

        return flights;
    }
	
    public boolean bookFlight(int id, int nrOfPassengers) {
        Integer[] flight = this.dbGet(
            "SELECT * FROM flights WHERE flight_id = " + id
        );
        int nrOfSeats = flight[1] - nrOfPassengers;

        return this.dbInsert(
            "UPDATE FLIGHTS SET nr_of_seats = " +
            nrOfSeats + " WHERE flight_id = " + flight[0]
        );
    }
	
    public String[][] getAllFlights() {
    	String[][] flights = this.dbGetFlights("SELECT * FROM flights");
    	
    	return flights;
    }
    
    public String[][] getFlights(String origin, String destination, String date) {
        String[][] flights = this.dbGetFlights(
            "SELECT * FROM flights WHERE origin = '" +
            origin + "' AND destination = '" + destination +
            "' AND departure_date = '" + date + "'" 
        );
        
        return flights;
    }

    public boolean addFlight(String origin, String destination, String deptDate,
        String deptTime, String travelTime, int price, int seats) {
        return this.addToDatabase(
            origin, destination,deptDate, deptTime, travelTime, price, seats
        );
    }

    public boolean removeFlight(int id){
        return this.deleteFromDatabase(id);
    }

    public boolean updateFlight(int id,String origin, String destination,
        String deptDate, String deptTime, String travelTime, int price, int seats) {
        return this.updateDatabase(
            id,origin, destination,deptDate, deptTime, travelTime, price, seats
        );
    }

    private boolean addToDatabase(String origin, String destination,
        String deptDate, String deptTime, String travelTime, int price, int seats) {
        return this.dbInsert(
            "INSERT INTO flights VALUES(" + null + ",'" +
            origin + "', '" +
            destination + "', '" +
            deptDate + "', '" +
            deptTime + "', '" +
            travelTime + "', " +
            price +", " +
            seats + ")"
        );
    }
	
    private boolean deleteFromDatabase(int id){
        return this.dbDelete("DELETE FROM flights WHERE flight_id = "+ id);
    }

    private boolean updateDatabase(int id, String origin, String destination,
        String deptDate, String deptTime, String travelTime, int price, int seats){
        return this.dbInsert(
            "UPDATE flights SET " +
            "origin = '" + origin + 
            "', destination = '" + destination + 
            "', departure_date = '" + deptDate +
            "', departure_time = '" + deptTime +
            "', travel_time = '" + travelTime +
            "', price = " + price +
            ",  nr_of_seats = " + seats + 
            " WHERE flight_id = " + id
        );
    }
}
