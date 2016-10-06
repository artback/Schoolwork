import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class db{
     static void connect() throws SQLException, ClassNotFoundException {
        String userName = "username";
        String password = "password";
        String url = "jdbc:sqlserver://MYPC\\SQLEXPRESS;databaseName=MYDB";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection(url, userName, password);
    }
};

