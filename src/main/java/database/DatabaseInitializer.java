package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialise() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        createTable(conn,
            "CREATE TABLE USERS ("
          + " username VARCHAR(50) PRIMARY KEY,"
          + " password VARCHAR(100) NOT NULL,"
          + " role VARCHAR(20) NOT NULL)");

        
        createTable(conn,
            "CREATE TABLE STUDENTS ("
          + " student_id VARCHAR(20) PRIMARY KEY,"      
          + " name VARCHAR(100) NOT NULL,"              
          + " email VARCHAR(100),"                      
          + " phone VARCHAR(20),"                       
          + " programme VARCHAR(100),"                 
          + " study_year INT,"                          
          + " student_type VARCHAR(20) NOT NULL"
          + "   CHECK (student_type IN ('DOMESTIC', 'INTERNATIONAL')),"
          + " scholarship BOOLEAN,"                     
          + " country VARCHAR(60),"                     
          + " visa_expiry VARCHAR(20))");               

        createTable(conn,
            "CREATE TABLE COURSES ("
          + " course_code VARCHAR(20) PRIMARY KEY,"
          + " course_name VARCHAR(100) NOT NULL,"
          + " credits INT NOT NULL CHECK (credits > 0))");

        
        createTable(conn,
            "CREATE TABLE ENROLLMENTS ("
          + " enrollment_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
          + " student_id VARCHAR(20) NOT NULL REFERENCES STUDENTS(student_id) ON DELETE CASCADE,"
          + " course_code VARCHAR(20) NOT NULL REFERENCES COURSES(course_code) ON DELETE CASCADE,"
          + " semester VARCHAR(30) NOT NULL,"
          + " CONSTRAINT uq_enrollment UNIQUE (student_id, course_code, semester))");

        seedDefaultAdmin(conn);
    }

    private static void createTable(Connection conn, String sql) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            if (!"X0Y32".equals(e.getSQLState())) {   
                throw e;
            }
        }
    }

    private static void seedDefaultAdmin(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM USERS")) {
            rs.next();
            if (rs.getInt(1) == 0) {
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO USERS (username, password, role) VALUES (?, ?, ?)")) {
                    ps.setString(1, "admin");
                    ps.setString(2, "admin123");
                    ps.setString(3, "ADMIN");
                    ps.executeUpdate();
                }
            }
        }
    }
}
