/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.StudentDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;
import model.DomesticStudent;
import model.InternationalStudent;
import model.Student;
 
public class StudentService {
 
    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final Pattern PHONE = Pattern.compile("^[0-9+ -]{7,15}$");
    private static final int MIN_YEAR = 1;
    private static final int MAX_YEAR = 5;
 
    private final StudentDAO studentDAO;
 
    public StudentService() {
        this(new StudentDAO());
    }
 
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }
 
    public void addStudent(Student student) throws ServiceException {
        validateStudent(student);
        try {
            if (studentDAO.getStudentById(student.getId()) != null) {
                throw new ServiceException("Student ID " + student.getId() + " already exists.");
            }
            studentDAO.addStudent(student);
        } catch (SQLException e) {
            throw new ServiceException("Could not save the student. Please try again.", e);
        }
    }
 
    public void updateStudent(Student student) throws ServiceException {
        validateStudent(student);
        try {
            if (!studentDAO.updateStudent(student)) {
                throw new ServiceException("Student ID " + student.getId() + " was not found.");
            }
        } catch (SQLException e) {
            throw new ServiceException("Could not update the student. Please try again.", e);
        }
    }
 
    public void deleteStudent(String id) throws ServiceException {
        requireText(id, "Student ID");
        try {
            if (!studentDAO.deleteStudent(id.trim())) {
                throw new ServiceException("Student ID " + id + " was not found.");
            }
        } catch (SQLException e) {
            throw new ServiceException("Could not delete the student. Please try again.", e);
        }
    }
 
    public Student getStudentById(String id) throws ServiceException {
        requireText(id, "Student ID");
        try {
            return studentDAO.getStudentById(id.trim());
        } catch (SQLException e) {
            throw new ServiceException("Could not load the student. Please try again.", e);
        }
    }
 
    public List<Student> getAllStudents() throws ServiceException {
        try {
            return studentDAO.getAllStudents();
        } catch (SQLException e) {
            throw new ServiceException("Could not load students. Please try again.", e);
        }
    }
 
    public List<Student> searchStudents(String keyword) throws ServiceException {
        try {
            if (keyword == null || keyword.isBlank()) {
                return studentDAO.getAllStudents();
            }
            return studentDAO.searchStudents(keyword.trim());
        } catch (SQLException e) {
            throw new ServiceException("Could not search students. Please try again.", e);
        }
    }
 
    public void validateStudent(Student s) throws ServiceException {
        if (s == null) {
            throw new ServiceException("Student details are missing.");
        }
        requireText(s.getId(), "Student ID");
        requireText(s.getName(), "Name");
        requireText(s.getProgramme(), "Programme");
 
        if (s.getEmail() != null && !s.getEmail().isBlank()
                && !EMAIL.matcher(s.getEmail().trim()).matches()) {
            throw new ServiceException("Please enter a valid email address (e.g. name@email.com).");
        }
        if (s.getPhone() != null && !s.getPhone().isBlank()
                && !PHONE.matcher(s.getPhone().trim()).matches()) {
            throw new ServiceException("Please enter a valid phone number (7-15 digits).");
        }
        if (s.getYear() < MIN_YEAR || s.getYear() > MAX_YEAR) {
            throw new ServiceException("Year must be between " + MIN_YEAR + " and " + MAX_YEAR + ".");
        }
 
        if (s instanceof InternationalStudent i) {
            requireText(i.getCountry(), "Country");
            requireText(i.getVisaExpiry(), "Visa expiry date");
            try {
                LocalDate.parse(i.getVisaExpiry().trim());
            } catch (DateTimeParseException e) {
                throw new ServiceException("Visa expiry date must be in the format YYYY-MM-DD.");
            }
        } else if (!(s instanceof DomesticStudent)) {
            throw new ServiceException("Please choose Domestic or International student type.");
        }
    }
 
    private static void requireText(String value, String fieldName) throws ServiceException {
        if (value == null || value.isBlank()) {
            throw new ServiceException(fieldName + " is required.");
        }
    }
}

