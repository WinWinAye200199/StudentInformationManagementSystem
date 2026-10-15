/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package testutil;

import database.DatabaseConnection;
import database.DatabaseInitializer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class TestDatabase {

    private static final String TEST_DB_URL = "jdbc:derby:memory:SIMS_TEST;create=true";

    private TestDatabase() { }

    // Call from @BeforeClass, before any DAO is used
    public static void setUp() throws SQLException {
        System.setProperty("sims.db.url", TEST_DB_URL);
        DatabaseInitializer.initialise();
    }

    // Call from @Before so every test starts with empty tables
    public static void clearTables() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("DELETE FROM ENROLLMENTS");
            st.executeUpdate("DELETE FROM STUDENTS");
            st.executeUpdate("DELETE FROM COURSES");
        }
    }
}