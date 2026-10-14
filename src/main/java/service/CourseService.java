/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import dao.CourseDAO;
import java.sql.SQLException;
import java.util.List;
import model.Course;
 

public class CourseService {
 
    private final CourseDAO courseDAO;
 
    public CourseService() {
        this(new CourseDAO());
    }
 
    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }
 
    public void addCourse(Course course) throws ServiceException {
        validateCourse(course);
        try {
            if (courseDAO.getCourseByCode(course.getCourseCode()) != null) {
                throw new ServiceException("Course code " + course.getCourseCode() + " already exists.");
            }
            courseDAO.addCourse(course);
        } catch (SQLException e) {
            throw new ServiceException("Could not save the course. Please try again.", e);
        }
    }
 
    public void updateCourse(Course course) throws ServiceException {
        validateCourse(course);
        try {
            if (!courseDAO.updateCourse(course)) {
                throw new ServiceException("Course code " + course.getCourseCode() + " was not found.");
            }
        } catch (SQLException e) {
            throw new ServiceException("Could not update the course. Please try again.", e);
        }
    }
 
    public void deleteCourse(String courseCode) throws ServiceException {
        if (courseCode == null || courseCode.isBlank()) {
            throw new ServiceException("Course code is required.");
        }
        try {
            if (!courseDAO.deleteCourse(courseCode.trim())) {
                throw new ServiceException("Course code " + courseCode + " was not found.");
            }
        } catch (SQLException e) {
            throw new ServiceException("Could not delete the course. Please try again.", e);
        }
    }
 
    public Course getCourseByCode(String courseCode) throws ServiceException {
        try {
            return courseDAO.getCourseByCode(courseCode.trim());
        } catch (SQLException e) {
            throw new ServiceException("Could not load the course. Please try again.", e);
        }
    }
 
    public List<Course> getAllCourses() throws ServiceException {
        try {
            return courseDAO.getAllCourses();
        } catch (SQLException e) {
            throw new ServiceException("Could not load courses. Please try again.", e);
        }
    }
 
    public void validateCourse(Course c) throws ServiceException {
        if (c == null) {
            throw new ServiceException("Course details are missing.");
        }
        if (c.getCourseCode() == null || c.getCourseCode().isBlank()) {
            throw new ServiceException("Course code is required.");
        }
        if (c.getCourseName() == null || c.getCourseName().isBlank()) {
            throw new ServiceException("Course name is required.");
        }
        if (c.getCredits() <= 0) {
            throw new ServiceException("Credits must be a positive number.");
        }
    }
}
 
