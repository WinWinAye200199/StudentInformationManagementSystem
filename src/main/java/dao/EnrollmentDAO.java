/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.Enrollment;
import model.Student;

public class EnrollmentDAO {

    private final StudentDAO studentDAO = new StudentDAO();
    private final CourseDAO courseDAO = new CourseDAO();

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    // CREATE
    public void addEnrollment(String studentId, String courseCode, String semester) throws SQLException {
        String sql = "INSERT INTO ENROLLMENTS (student_id, course_code, semester) VALUES (?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, courseCode);
            ps.setString(3, semester);
            ps.executeUpdate();
        }
    }

    public boolean isAlreadyEnrolled(String studentId, String courseCode) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ENROLLMENTS"
                   + " WHERE UPPER(student_id) = UPPER(?) AND UPPER(course_code) = UPPER(?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, courseCode);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public List<Enrollment> getAllEnrollments() throws SQLException {
        String sql = "SELECT student_id, course_code, semester FROM ENROLLMENTS"
                   + " ORDER BY student_id, course_code";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            return readList(ps);
        }
    }

    public List<Enrollment> getEnrollmentsByStudent(String studentId) throws SQLException {
        String sql = "SELECT student_id, course_code, semester FROM ENROLLMENTS"
                   + " WHERE student_id = ? ORDER BY course_code";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, studentId);
            return readList(ps);
        }
    }

    // DELETE
    public boolean deleteEnrollment(String studentId, String courseCode) throws SQLException {
        String sql = "DELETE FROM ENROLLMENTS WHERE student_id = ? AND course_code = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, courseCode);
            return ps.executeUpdate() > 0;
        }
    }

    private List<Enrollment> readList(PreparedStatement ps) throws SQLException {
        
        List<String[]> rows = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rows.add(new String[] {
                    rs.getString("student_id"),
                    rs.getString("course_code"),
                    rs.getString("semester")
                });
            }
        }

        List<Enrollment> list = new ArrayList<>();
        for (String[] row : rows) {
            Student student = studentDAO.getStudentById(row[0]);
            Course course = courseDAO.getCourseByCode(row[1]);
            list.add(new Enrollment(student, course, row[2]));
        }
        return list;
    }
}