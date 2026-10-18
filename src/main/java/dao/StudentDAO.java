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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.DomesticStudent;
import model.InternationalStudent;
import model.Student;

public class StudentDAO {
    private static final String TYPE_DOMESTIC = "DOMESTIC";
    private static final String TYPE_INTERNATIONAL = "INTERNATIONAL";

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    // CREATE
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO STUDENTS (name, email, phone, programme, study_year,"
                   + " student_type, scholarship, country, visa_expiry, student_id)"
                   + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            fillStatement(ps, student);
            ps.executeUpdate();
        }
    }

    // READ (one)
    public Student getStudentById(String id) throws SQLException {
        String sql = "SELECT * FROM STUDENTS WHERE student_id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    // READ (all)
    public List<Student> getAllStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM STUDENTS ORDER BY student_id";
        try (Statement st = getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    // SEARCH 
    public List<Student> searchStudents(String keyword) throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM STUDENTS WHERE LOWER(student_id) LIKE ?"
                   + " OR LOWER(name) LIKE ? OR LOWER(programme) LIKE ?"
                   + " ORDER BY student_id";
        String pattern = "%" + keyword.toLowerCase() + "%";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    // Next ID in the format S001, S002, ... (finds the highest existing number and adds 1)
    public String getNextStudentId() throws SQLException {
        String sql = "SELECT student_id FROM STUDENTS WHERE student_id LIKE 'S%'";
        int max = 0;
        try (Statement st = getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String digits = rs.getString(1).substring(1);
                if (digits.matches("\\d+")) {
                    max = Math.max(max, Integer.parseInt(digits));
                }
            }
        }
        return String.format("S%03d", max + 1);
    }
    
    // UPDATE
    public boolean updateStudent(Student student) throws SQLException {
        String sql = "UPDATE STUDENTS SET name = ?, email = ?, phone = ?, programme = ?,"
                   + " study_year = ?, student_type = ?, scholarship = ?, country = ?,"
                   + " visa_expiry = ? WHERE student_id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            fillStatement(ps, student);
            return ps.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean deleteStudent(String id) throws SQLException {
        String sql = "DELETE FROM STUDENTS WHERE student_id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    
    private void fillStatement(PreparedStatement ps, Student s) throws SQLException {
        ps.setString(1, s.getName());
        ps.setString(2, s.getEmail());
        ps.setString(3, s.getPhone());
        ps.setString(4, s.getProgramme());
        ps.setInt(5, s.getYear());

        if (s instanceof DomesticStudent d) {
            ps.setString(6, TYPE_DOMESTIC);
            ps.setBoolean(7, d.isScholarship());
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        } else if (s instanceof InternationalStudent i) {
            ps.setString(6, TYPE_INTERNATIONAL);
            ps.setNull(7, Types.BOOLEAN);
            ps.setString(8, i.getCountry());
            ps.setString(9, i.getVisaExpiry());
        } else {
            throw new IllegalArgumentException("Student must be Domestic or International.");
        }

        ps.setString(10, s.getId());
    }

    
    private Student mapRow(ResultSet rs) throws SQLException {
        Student s;
        if (TYPE_DOMESTIC.equals(rs.getString("student_type"))) {
            DomesticStudent d = new DomesticStudent();
            d.setScholarship(rs.getBoolean("scholarship"));
            s = d;
        } else {
            InternationalStudent i = new InternationalStudent();
            i.setCountry(rs.getString("country"));
            i.setVisaExpiry(rs.getString("visa_expiry"));
            s = i;
        }
        s.setId(rs.getString("student_id"));
        s.setName(rs.getString("name"));
        s.setEmail(rs.getString("email"));
        s.setPhone(rs.getString("phone"));
        s.setProgramme(rs.getString("programme"));
        s.setYear(rs.getInt("study_year"));
        return s;
    }
}
