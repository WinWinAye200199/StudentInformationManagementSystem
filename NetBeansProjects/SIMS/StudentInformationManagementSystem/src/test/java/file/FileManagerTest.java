///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package file;
//
//import manager.CourseManager;
//import manager.EnrollmentManager;
//import manager.StudentManager;
//import model.Course;
//import model.Student;
//
//import File.FileManager;
//
//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class FileManagerTest {
//
//    @Test
//    public void testSaveAndLoadStudents() {
//
//        FileManager fileManager = new FileManager();
//
//        ArrayList<Student> students = new ArrayList<>();
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
//        students.add(student);
//
//        fileManager.saveStudents(students);
//
//        File file = new File("students.txt");
//
//        assertTrue(file.exists());
//
//        ArrayList<Student> loadedStudents =
//                fileManager.loadStudents();
//
//        assertEquals(1, loadedStudents.size());
//
//        assertEquals(
//                "S001",
//                loadedStudents.get(0).getId()
//        );
//
//        assertEquals(
//                "Win",
//                loadedStudents.get(0).getName()
//        );
//
//        assertEquals(
//                "Computing",
//                loadedStudents.get(0).getProgramme()
//        );
//
//        assertEquals(
//                2,
//                loadedStudents.get(0).getYear()
//        );
//    }
//
//
//    @Test
//    public void testSaveAndLoadCourses() {
//
//        FileManager fileManager = new FileManager();
//
//        ArrayList<Course> courses = new ArrayList<>();
//
//        Course course = new Course(
//                "COMP503",
//                "Programming",
//                15
//        );
//
//        courses.add(course);
//
//        fileManager.saveCourses(courses);
//
//        File file = new File("courses.txt");
//
//        assertTrue(file.exists());
//
//        ArrayList<Course> loadedCourses =
//                fileManager.loadCourses();
//
//        assertEquals(1, loadedCourses.size());
//
//        assertEquals(
//                "COMP503",
//                loadedCourses.get(0).getCourseCode()
//        );
//
//        assertEquals(
//                "Programming",
//                loadedCourses.get(0).getCourseName()
//        );
//
//        assertEquals(
//                15,
//                loadedCourses.get(0).getCredits()
//        );
//    }
//
//
//    @Test
//    public void testSaveAndLoadEnrollments() {
//
//        FileManager fileManager = new FileManager();
//
//        StudentManager studentManager =
//                new StudentManager();
//
//        CourseManager courseManager =
//                new CourseManager();
//
//        EnrollmentManager enrollmentManager =
//                new EnrollmentManager(
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
//        enrollmentManager.addEnrollment(
//                "S001",
//                "COMP503",
//                "Semester 2 2026"
//        );
//
//        fileManager.saveEnrollments(
//                enrollmentManager.getEnrollments()
//        );
//
//        File file = new File("enrollments.txt");
//
//        assertTrue(file.exists());
//
//        StudentManager newStudentManager =
//                new StudentManager();
//
//        CourseManager newCourseManager =
//                new CourseManager();
//
//        EnrollmentManager newEnrollmentManager =
//                new EnrollmentManager(
//                        newStudentManager,
//                        newCourseManager
//                );
//
//        newStudentManager.addStudent(student);
//        newCourseManager.addCourse(course);
//
//        fileManager.loadEnrollments(
//                newStudentManager,
//                newCourseManager,
//                newEnrollmentManager
//        );
//
//        assertEquals(
//                1,
//                newEnrollmentManager
//                        .getEnrollments()
//                        .size()
//        );
//
//        assertEquals(
//                "S001",
//                newEnrollmentManager
//                        .getEnrollments()
//                        .get(0)
//                        .getStudent()
//                        .getId()
//        );
//
//        assertEquals(
//                "COMP503",
//                newEnrollmentManager
//                        .getEnrollments()
//                        .get(0)
//                        .getCourse()
//                        .getCourseCode()
//        );
//
//        assertEquals(
//                "Semester 2 2026",
//                newEnrollmentManager
//                        .getEnrollments()
//                        .get(0)
//                        .getSemester()
//        );
//    }
//}