///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package manager;
//
//import model.Course;
//import model.Student;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class EnrollmentManagerTest {
//
//    @Test
//    public void testAddEnrollment() {
//
//        StudentManager studentManager
//                = new StudentManager();
//
//        CourseManager courseManager
//                = new CourseManager();
//
//        EnrollmentManager enrollmentManager
//                = new EnrollmentManager(
//                        studentManager,
//                        courseManager
//                );
//
//        Student student = new Student(
//                "S001",
//                "Win",
//                "win@gmail.com",
//                "0212345678",
//                "Computing",
//                2
//        );
//
//        Course course = new Course(
//                "COMP503",
//                "Programming",
//                15
//        );
//
//        studentManager.addStudent(student);
//        courseManager.addCourse(course);
//
//        boolean result
//                = enrollmentManager.addEnrollment(
//                        "S001",
//                        "COMP503",
//                        "Semester 2 2026"
//                );
//
//        assertTrue(result);
//
//        assertEquals(
//                1,
//                enrollmentManager
//                        .getEnrollments()
//                        .size()
//        );
//    }
//
//    @Test
//    public void testEnrollmentWithInvalidStudent() {
//
//        StudentManager studentManager
//                = new StudentManager();
//
//        CourseManager courseManager
//                = new CourseManager();
//
//        EnrollmentManager enrollmentManager
//                = new EnrollmentManager(
//                        studentManager,
//                        courseManager
//                );
//
//        Course course = new Course(
//                "COMP503",
//                "Programming",
//                15
//        );
//
//        courseManager.addCourse(course);
//
//        boolean result
//                = enrollmentManager.addEnrollment(
//                        "INVALID",
//                        "COMP503",
//                        "Semester 2 2026"
//                );
//
//        assertFalse(result);
//    }
//
//    @Test
//    public void testDuplicateEnrollment() {
//
//        StudentManager studentManager
//                = new StudentManager();
//
//        CourseManager courseManager
//                = new CourseManager();
//
//        EnrollmentManager enrollmentManager
//                = new EnrollmentManager(
//                        studentManager,
//                        courseManager
//                );
//
//        Student student = new Student(
//                "S001",
//                "Win",
//                "win@gmail.com",
//                "0212345678",
//                "Computing",
//                2
//        );
//
//        Course course = new Course(
//                "COMP503",
//                "Programming",
//                15
//        );
//
//        studentManager.addStudent(student);
//        courseManager.addCourse(course);
//
//        assertTrue(
//                enrollmentManager.addEnrollment(
//                        "S001",
//                        "COMP503",
//                        "Semester 2 2026"
//                )
//        );
//
//        assertFalse(
//                enrollmentManager.addEnrollment(
//                        "S001",
//                        "COMP503",
//                        "Semester 2 2026"
//                )
//        );
//    }
//}
