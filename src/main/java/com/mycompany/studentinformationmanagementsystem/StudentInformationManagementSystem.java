/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.studentinformationmanagementsystem;

import dao.CourseDAO;
import dao.EnrollmentDAO;
import dao.StudentDAO;
import database.DatabaseInitializer;
import java.sql.SQLException;
import database.DatabaseConnection;
import gui.LoginFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.Course;
import model.DomesticStudent;
import model.Enrollment;
import model.InternationalStudent;
import model.Student;
import service.CourseService;
import service.ServiceException;
//import ui.Menu;

public class StudentInformationManagementSystem {

    public static void main(String[] args) {
//
//        try {
//            DatabaseInitializer.initialise();
//            StudentDAO studentDAO = new StudentDAO();
//            CourseDAO courseDAO = new CourseDAO();
//            EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
//
//            courseDAO.deleteCourse("COMP603");
//            studentDAO.deleteStudent("S001");
//            studentDAO.deleteStudent("S002");
//
//            studentDAO.addStudent(new DomesticStudent("S001", "Win Win Aye", "win@email.com",
//                    "0211234567", "Computer Science", 2, true));
//            studentDAO.addStudent(new InternationalStudent("S002", "John Lee", "john@email.com",
//                    "0217654321", "IT", 1, "Myanmar", "2027-12-31"));
//            courseDAO.addCourse(new Course("COMP603", "Software Development", 15));
//            enrollmentDAO.addEnrollment("S001", "COMP603", "2026 S2");
//
//            System.out.println("=== Students ===");
//            for (Student s : studentDAO.getAllStudents()) {
//                s.displayInfo();
//                System.out.println("------");
//            }
//
//            System.out.println("=== Courses ===");
//            for (Course c : courseDAO.getAllCourses()) {
//                c.displayCourse();
//            }
//
//            System.out.println("=== Enrollments ===");
//            System.out.println("Already enrolled? "
//                    + enrollmentDAO.isAlreadyEnrolled("S001", "COMP603"));
//            for (Enrollment e : enrollmentDAO.getAllEnrollments()) {
//                System.out.println(e.getStudent().getName() + " -> "
//                        + e.getCourse().getCourseName() + " (" + e.getSemester() + ")");
//            }
//            CourseService courseService = new CourseService();
//            try {
//                courseService.addCourse(new Course("COMP999", "Test", 0));
//            } catch (ServiceException ex) {
//                System.out.println("Validation works: " + ex.getMessage());
//            }
//        } catch (SQLException e) {
//            System.err.println("Database error: " + e.getMessage());
//        } finally {
//            DatabaseConnection.getInstance().shutdown();
//        }
//    }
 // 1. Connect to Derby and create tables if they do not exist yet
        try {
            DatabaseInitializer.initialise();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Could not start the database.\n" + e.getMessage()
                    + "\n\nIf the database is open in NetBeans (Services tab), disconnect it and try again.",
                    "Database error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Shut Derby down cleanly when the application closes
        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> DatabaseConnection.getInstance().shutdown()));

        // 3. Start the GUI on the Swing event thread
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
