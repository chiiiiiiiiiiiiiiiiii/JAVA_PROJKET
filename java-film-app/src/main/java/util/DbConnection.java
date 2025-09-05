package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Filmovi;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "Supersifra98";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}