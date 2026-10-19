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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Course;


public class CourseDAO {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    // CREATE
    public void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO COURSES (course_code, course_name, credits) VALUES (?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, course.getCourseCode());
            ps.setString(2, course.getCourseName());
            ps.setInt(3, course.getCredits());
            ps.executeUpdate();
        }
    }

    // READ (one)
    public Course getCourseByCode(String courseCode) throws SQLException {
        String sql = "SELECT * FROM COURSES WHERE course_code = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, courseCode);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    // READ (all)
    public List<Course> getAllCourses() throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM COURSES ORDER BY course_code";
        try (Statement st = getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    // UPDATE
    public boolean updateCourse(Course course) throws SQLException {
        String sql = "UPDATE COURSES SET course_name = ?, credits = ? WHERE course_code = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, course.getCourseName());
            ps.setInt(2, course.getCredits());
            ps.setString(3, course.getCourseCode());
            return ps.executeUpdate() > 0;
        }
    }

    // DELETE (related ENROLLMENTS rows are removed automatically by ON DELETE CASCADE)
    public boolean deleteCourse(String courseCode) throws SQLException {
        String sql = "DELETE FROM COURSES WHERE course_code = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, courseCode);
            return ps.executeUpdate() > 0;
        }
    }

    private Course mapRow(ResultSet rs) throws SQLException {
        return new Course(
                rs.getString("course_code"),
                rs.getString("course_name"),
                rs.getInt("credits"));
    }
}
