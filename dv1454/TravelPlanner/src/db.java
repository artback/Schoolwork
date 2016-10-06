import java.sql.*;

public final class db{
    static Connection conn;
     static void connect() throws SQLException, ClassNotFoundException {
        String userName = "username";
        String password = "password";
        String url = "jdbc:sqlserver://MYPC\\SQLEXPRESS;databaseName=MYDB";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(url, userName, password);
    }
    static ResultSet query(String query) throws SQLException {
        Statement statement = conn.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }

};

