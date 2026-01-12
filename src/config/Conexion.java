package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String SERVER = "127.0.0.1";
    private static final String DATABASE = "ticketsys_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL =
            "jdbc:mysql://" + SERVER + "/" + DATABASE + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
