import java.sql.*;

public final class db {
    static Connection conn;

    static private void connect() {
        String userName = "dv1454_ht16_8";
        String password = "7PJEoNn&";
        String url = "jdbc:sqlserver://194.47.129.139:1433;databaseName=DV1454_AirPort";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver" );
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
        if (conn == null) {
            connect();
        }
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
    static Boolean insert(String insert){
        if (conn == null) {
            connect();
        }
        boolean success = true;
        Statement statement = null;
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            success=false;
            e.printStackTrace();
        }
        try {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
        } catch (SQLException e) {
            e.printStackTrace();
            success=false;
        }
        if(!insert.equals("")){
                try {
                    statement.executeUpdate(insert);
                } catch (SQLException e) {
                    e.printStackTrace();
                    success=false;
                }
            }

        return success;
    }
};

