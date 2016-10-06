import java.sql.*;

public final class db{
    static Connection conn;
     static void connect() {
        String userName = "username";
        String password = "password";
        String url = "jdbc:sqlserver://MYPC\\SQLEXPRESS;databaseName=MYDB";
         try {
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         }
         try {
             conn = DriverManager.getConnection(url, userName, password);
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
    static ResultSet query(String query) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

};

