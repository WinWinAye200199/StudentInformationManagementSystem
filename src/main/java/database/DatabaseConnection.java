package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Real database used by the application (embedded mode, stored in the project folder)
    private static final String DEFAULT_DB_URL = "jdbc:derby:database/SIMS_DB;create=true";

    // JUnit tests set the "sims.db.url" system property to an in-memory database,
    // so running the tests never changes the real data in database/SIMS_DB
    private static String getDbUrl() {
        return System.getProperty("sims.db.url", DEFAULT_DB_URL);
    }

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() { }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(getDbUrl());
        }
        return connection;
    }

    public void shutdown() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            if (!"XJ015".equals(e.getSQLState())) {
                System.err.println("Database shutdown error: " + e.getMessage());
            }
        }
    }
}