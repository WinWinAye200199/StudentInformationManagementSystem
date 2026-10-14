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
import model.Course;
import model.DomesticStudent;
import model.Enrollment;
import model.InternationalStudent;
import model.Student;
//import ui.Menu;

public class StudentInformationManagementSystem {

    public static void main(String[] args) {
        
//        Menu menu = new Menu();
//
//        menu.start();
//          try{
//              DatabaseInitializer.initialise();
//              StudentDAO dao = new StudentDAO();
//              
//              dao.deleteStudent("S001");
//              dao.deleteStudent("S002");
//              
//              dao.addStudent(new DomesticStudent("S001", "Win Win Aye", "win@email.com",
//              "0211234567", "Computer Science", 2, true));
//              dao.addStudent(new InternationalStudent("S002", "John Lee", "john@email.com",
//              "0217654321", "IT", 1, "Myanmar", "2027-12-31"));
//              
//              for (Student s : dao.getAllStudents()){
//                  s.displayInfo();
//                  System.out.println("------");
//              }
//              System.out.println("Derby connected. Tables ready.");
//          } catch (SQLException e){
//              System.err.println("Database error: " + e.getMessage());
//          } finally {
//              DatabaseConnection.getInstance().shutdown();
//          }
            try {
            DatabaseInitializer.initialise();
            StudentDAO studentDAO = new StudentDAO();
            CourseDAO courseDAO = new CourseDAO();
            EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

            // Clean old test data so the test can run again
            courseDAO.deleteCourse("COMP603");
            studentDAO.deleteStudent("S001");
            studentDAO.deleteStudent("S002");

            studentDAO.addStudent(new DomesticStudent("S001", "Win Win Aye", "win@email.com",
                    "0211234567", "Computer Science", 2, true));
            studentDAO.addStudent(new InternationalStudent("S002", "John Lee", "john@email.com",
                    "0217654321", "IT", 1, "Myanmar", "2027-12-31"));
            courseDAO.addCourse(new Course("COMP603", "Software Development", 15));
            enrollmentDAO.addEnrollment("S001", "COMP603", "2026 S2");

            System.out.println("=== Students ===");
            for (Student s : studentDAO.getAllStudents()) {
                s.displayInfo();
                System.out.println("------");
            }

            System.out.println("=== Courses ===");
            for (Course c : courseDAO.getAllCourses()) {
                c.displayCourse();
            }

            System.out.println("=== Enrollments ===");
            System.out.println("Already enrolled? "
                    + enrollmentDAO.isAlreadyEnrolled("S001", "COMP603"));
            for (Enrollment e : enrollmentDAO.getAllEnrollments()) {
                System.out.println(e.getStudent().getName() + " -> "
                        + e.getCourse().getCourseName() + " (" + e.getSemester() + ")");
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } finally {
            DatabaseConnection.getInstance().shutdown();
        }
    }
}
