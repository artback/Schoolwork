
import java.sql.ResultSet;
import java.sql.SQLException;

public final class FlightController {
    public FlightController() {
    }


    private static String[][] dbGetFlights(String SQL)   {
        String[][] flights = new String[5][4];
        ResultSet rs;
        int count = 0;
           rs= db.query(SQL);
        try {
            while(rs.next() && count < 5 ) {
                flights[count][0] = Integer.toString(rs.getInt(1));
                flights[count][1] = rs.getString("TravelID");
                flights[count][2] = rs.getString("Departure");
                flights[count][3] = rs.getString("Destination");
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }
    private static String[][] dbget(String SQL)   {
        String[][] flights = new String[5][4];
        ResultSet rs;
        int count = 0;
        rs= db.query(SQL);
        try {
            while(rs.next() && count < 5 ) {
                flights[count][0] = rs.getString(1);
                flights[count][1] = rs.getString(2);
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    public static String[][] getBest(int by){
        String[][] best;
        if(by == 1){
            best= FlightController.dbget("select TOP 5 DATENAME(dw,datepart(dw,cast(TakeOff as Date))) as Date, count(datepart(dw,convert(date,Flight.TakeOff))) as dept from Ticket\n" +
                    "inner join Flight on Ticket.FlightID=Flight.FlightID\n" +
                    "group by datepart(dw,cast(TakeOff as Date)) order by dept desc");
        }
        else if(by == 2){
            best= FlightController.dbget("select TOP 5 datepart(wk, cast(TakeOff as Date)) as Week, count(datepart(wk,convert(date,Flight.TakeOff))) as dept from Ticket\n" +
                    "inner join Flight on Ticket.FlightID=Flight.FlightID\n" +
                    "group by datepart(wk, cast(TakeOff as Date)) order by dept desc");
        }
        else{
            best= FlightController.dbget("select TOP 5 datepart(mm, cast(TakeOff as Date)) as Month, count(convert(date,Flight.TakeOff)) as dept from Ticket\n" +
                    "inner join Flight on Ticket.FlightID=Flight.FlightID\n" +
                    "group by datepart(mm, cast(TakeOff as Date)) order by dept desc");
        }
        return best;
    }

    public  static String[][] getFlights(Boolean sort) {
        String[][] flights;
        if(sort) {
            flights = FlightController.dbGetFlights(
                    "SELECT TOP 5 SUM(z.nrOF) AS 'Total2', Flight.TravelID,Travel.Departure,Travel.Destination " +
                            "FROM( SELECT COUNT(Ticket.FlightID) AS 'nrOF',FlightID " +
                            "FROM Ticket GROUP BY Ticket.FlightID ) z " +
                            "JOIN Flight ON z.FlightID=Flight.FlightID " +
                            "JOIN Travel ON Flight.TravelID=Travel.TravelID " +
                            "GROUP BY Flight.TravelID,Travel.Departure,Travel.Destination ORDER BY Total2 DESC"
            );
        }
        else{
            flights= FlightController.dbGetFlights("SELECT SUM(z.Total)as Total, Flight.TravelID, Travel.Departure, Travel.Destination\n" +
                    "FROM( SELECT COUNT(Ticket.FlightID)*Price AS 'Total',FlightID\n" +
                            "FROM Ticket GROUP BY  Ticket.FlightID,Price\n" +
                            ") z JOIN Flight ON z.FlightID=Flight.FlightID\n" +
                            "JOIN Travel ON Flight.TravelID=Travel.TravelID\n" +
                            "GROUP BY Flight.TravelID,Travel.Departure,Travel.Destination ORDER BY Total DESC");
        }

        return flights;
    }


}
