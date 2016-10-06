import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class db{
    String userName = "username";
    String password = "password";
    String url = "jdbc:sqlserver://MYPC\\SQLEXPRESS;databaseName=MYDB";
    void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection(url, userName, password);
    }
};

