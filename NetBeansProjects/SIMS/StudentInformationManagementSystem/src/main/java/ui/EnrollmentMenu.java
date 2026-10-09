/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.util.ArrayList;
import java.util.Scanner;

import manager.EnrollmentManager;
import manager.StudentManager;
import manager.CourseManager;

import model.Student;
import model.Course;

public class EnrollmentMenu {

    private Scanner input;
    private EnrollmentManager enrollmentManager;
    private StudentManager studentManager;
    private CourseManager courseManager;

    public EnrollmentMenu(
            Scanner input,
            EnrollmentManager enrollmentManager,
            StudentManager studentManager,
            CourseManager courseManager) {

        this.input = input;
        this.enrollmentManager = enrollmentManager;
        this.studentManager = studentManager;
        this.courseManager = courseManager;
    }

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("\n===== ENROLLMENT MANAGEMENT =====");
            System.out.println("1. Enroll Student");
            System.out.println("2. View All Enrollments");
            System.out.println("3. Back to Main Menu");

            System.out.print("Enter your choice: ");
            String choice = input.nextLine();

            switch (choice) {

                case "1":
                    addEnrollment();
                    break;

                case "2":
                    viewEnrollments();
                    break;

                case "3":
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please enter a number from 1 to 3."
                    );
            }
        }
    }

    private void addEnrollment() {

        System.out.println("\n===== ENROLL STUDENT =====");

        System.out.print("Enter Student ID: ");
        String studentId = input.nextLine();

        Student student
                = studentManager.searchStudent(studentId);

        if (student == null) {

            System.out.println("Student not found.");
            return;
        }

        System.out.println("\nStudent Found:");
        System.out.println("----------------------");
        System.out.println("ID: " + student.getId());
        System.out.println("Name: " + student.getName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Programme: " + student.getProgramme());
        System.out.println("Year: " + student.getYear());

        ArrayList<Course> courses
                = courseManager.getCourses();

        if (courses.isEmpty()) {

            System.out.println(
                    "\nNo courses are currently available."
            );

            return;
        }

        System.out.println(
                "\n===== AVAILABLE COURSES ====="
        );

        for (int i = 0; i < courses.size(); i++) {

            Course course = courses.get(i);

            System.out.println(
                    (i + 1)
                    + ". "
                    + course.getCourseCode()
                    + " - "
                    + course.getCourseName()
                    + " ("
                    + course.getCredits()
                    + " credits)"
            );
        }

        int courseChoice;

        while (true) {

            System.out.print(
                    "\nChoose a course: "
            );

            try {

                courseChoice
                        = Integer.parseInt(
                                input.nextLine()
                        );

                if (courseChoice >= 1
                        && courseChoice <= courses.size()) {

                    break;
                }

                System.out.println(
                        "Invalid choice. Please choose a number from 1 to "
                        + courses.size()
                        + "."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }

        Course selectedCourse
                = courses.get(courseChoice - 1);

        System.out.print("Enter Semester: ");
        String semester = input.nextLine();

        System.out.println(
                "\n===== ENROLLMENT SUMMARY ====="
        );

        System.out.println(
                "Student: "
                + student.getName()
                + " (" + student.getId() + ")"
        );

        System.out.println(
                "Course: "
                + selectedCourse.getCourseCode()
                + " - "
                + selectedCourse.getCourseName()
        );

        System.out.println(
                "Semester: " + semester
        );

        System.out.print(
                "Confirm enrollment? (Y/N): "
        );

        String answer = input.nextLine();

        if (!answer.equalsIgnoreCase("Y")) {

            System.out.println(
                    "Enrollment cancelled."
            );

            return;
        }

        if (enrollmentManager.isAlreadyEnrolled(
                studentId,
                selectedCourse.getCourseCode())) {

            System.out.println(
                    "This student is already enrolled in this course."
            );

            return;
        }

        boolean success
                = enrollmentManager.addEnrollment(
                        studentId,
                        selectedCourse.getCourseCode(),
                        semester
                );

        if (success) {

            System.out.println(
                    "Student enrolled successfully."
            );

        } else {

            System.out.println(
                    "Enrollment failed."
            );
        }
    }

    private void viewEnrollments() {

        System.out.println(
                "\n--- All Enrollments ---"
        );

        enrollmentManager.viewEnrollments();
    }
}
