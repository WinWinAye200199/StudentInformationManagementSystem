/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.CourseDAO;
import dao.EnrollmentDAO;
import dao.StudentDAO;
import java.sql.SQLException;
import java.util.List;
import model.Enrollment;
 
public class EnrollmentService {
 
    private final EnrollmentDAO enrollmentDAO;
    private final StudentDAO studentDAO;
    private final CourseDAO courseDAO;
 
    public EnrollmentService() {
        this(new EnrollmentDAO(), new StudentDAO(), new CourseDAO());
    }
 
    public EnrollmentService(EnrollmentDAO enrollmentDAO, StudentDAO studentDAO, CourseDAO courseDAO) {
        this.enrollmentDAO = enrollmentDAO;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
    }
 
    public void enrollStudent(String studentId, String courseCode, String semester) throws ServiceException {
        if (studentId == null || studentId.isBlank()) {
            throw new ServiceException("Student ID is required.");
        }
        if (courseCode == null || courseCode.isBlank()) {
            throw new ServiceException("Course code is required.");
        }
        if (semester == null || semester.isBlank()) {
            throw new ServiceException("Semester is required.");
        }
        String id = studentId.trim();
        String code = courseCode.trim();
        try {
            if (studentDAO.getStudentById(id) == null) {
                throw new ServiceException("Student ID " + id + " does not exist.");
            }
            if (courseDAO.getCourseByCode(code) == null) {
                throw new ServiceException("Course code " + code + " does not exist.");
            }
            if (enrollmentDAO.isAlreadyEnrolled(id, code)) {
                throw new ServiceException("Student " + id + " is already enrolled in " + code + ".");
            }
            enrollmentDAO.addEnrollment(id, code, semester.trim());
        } catch (SQLException e) {
            throw new ServiceException("Could not save the enrollment. Please try again.", e);
        }
    }
 
    public void removeEnrollment(String studentId, String courseCode) throws ServiceException {
        try {
            if (!enrollmentDAO.deleteEnrollment(studentId, courseCode)) {
                throw new ServiceException("Enrollment was not found.");
            }
        } catch (SQLException e) {
            throw new ServiceException("Could not remove the enrollment. Please try again.", e);
        }
    }
 
    public List<Enrollment> getAllEnrollments() throws ServiceException {
        try {
            return enrollmentDAO.getAllEnrollments();
        } catch (SQLException e) {
            throw new ServiceException("Could not load enrollments. Please try again.", e);
        }
    }
 
    public List<Enrollment> getEnrollmentsByStudent(String studentId) throws ServiceException {
        try {
            return enrollmentDAO.getEnrollmentsByStudent(studentId);
        } catch (SQLException e) {
            throw new ServiceException("Could not load enrollments. Please try again.", e);
        }
    }
}
