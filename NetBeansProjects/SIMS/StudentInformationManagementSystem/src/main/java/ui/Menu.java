/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import File.FileManager;
import java.util.ArrayList;
import java.util.Scanner;
import manager.CourseManager;
import manager.EnrollmentManager;
import manager.StudentManager;
import model.Course;
import model.Student;

public class Menu {

    private Scanner input;

    private FileManager fileManager;

    private StudentMenu studentMenu;
    private CourseMenu courseMenu;
    private EnrollmentMenu enrollmentMenu;

    private StudentManager studentManager;
    private CourseManager courseManager;
    private EnrollmentManager enrollmentManager;

    public Menu() {

        input = new Scanner(System.in);

        fileManager = new FileManager();

        courseManager = new CourseManager();
        studentManager = new StudentManager();
        enrollmentManager = new EnrollmentManager(studentManager, courseManager);

        studentMenu = new StudentMenu(input, studentManager);

        courseMenu = new CourseMenu(input, courseManager);

        enrollmentMenu = new EnrollmentMenu(
                input,
                enrollmentManager,
                studentManager,
                courseManager
        );

        loadData();
    }

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("   STUDENT INFORMATION MANAGEMENT SYSTEM");
            System.out.println("==========================================");
            System.out.println("1. Student Management");
            System.out.println("2. Course Management");
            System.out.println("3. Enrollment Management");
            System.out.println("4. Save Data");
            System.out.println("5. Exit");
            System.out.println("==========================================");

            System.out.print("Enter your choice: ");

            String choice = input.nextLine();

            switch (choice) {

                case "1":
                    studentMenu.start();
                    break;

                case "2":
                    courseMenu.start();
                    break;

                case "3":
                    enrollmentMenu.start();
                    break;

                case "4":
                    saveData();
                    break;

                case "5":
                    running = false;
                    System.out.println("Thank you for using the system.");
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please enter a number from 1 to 5."
                    );
            }
        }
    }

    private void saveData() {

        System.out.println("\n===== SAVING DATA =====");

        fileManager.saveStudents(
                studentManager.getStudents()
        );

        fileManager.saveCourses(
                courseManager.getCourses()
        );

        fileManager.saveEnrollments(
                enrollmentManager.getEnrollments()
        );

        System.out.println("All data saved successfully.");
    }

    private void loadData() {

        ArrayList<Student> students
                = fileManager.loadStudents();

        for (Student student : students) {
            studentManager.addStudent(student);
        }

        ArrayList<Course> courses
                = fileManager.loadCourses();

        for (Course course : courses) {
            courseManager.addCourse(course);
        }

        fileManager.loadEnrollments(
                studentManager,
                courseManager,
                enrollmentManager
        );
    }
}
