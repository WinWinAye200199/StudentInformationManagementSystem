/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.util.Scanner;
import model.Course;
import manager.CourseManager;

public class CourseMenu {

    private Scanner input;
    private CourseManager courseManager;

    public CourseMenu(Scanner input, CourseManager courseManager) {
        this.input = input;
        this.courseManager = courseManager;
    }

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("\n===== COURSE MANAGEMENT =====");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Search Course");
            System.out.println("4. Delete Course");
            System.out.println("5. Back to Main Menu");

            System.out.print("Enter your choice: ");

            String choice = input.nextLine();

            switch (choice) {

                case "1":
                    addCourse();
                    break;

                case "2":
                    viewCourses();
                    break;

                case "3":
                    searchCourse();
                    break;

                case "4":
                    deleteCourse();
                    break;

                case "5":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }
    }

    private void addCourse() {

        System.out.println("\n--- Add Course ---");

        System.out.print("Course Code: ");
        String courseCode = input.nextLine();

        System.out.print("Course Name: ");
        String courseName = input.nextLine();

        int credits;

        while (true) {

            System.out.print("Credits: ");

            try {

                credits = Integer.parseInt(input.nextLine());

                if (credits > 0) {
                    break;
                }

                System.out.println("Credits must be greater than 0.");

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }

        Course course = new Course(
                courseCode,
                courseName,
                credits
        );

        if (courseManager.addCourse(course)) {
            System.out.println("Course added successfully.");
        } else {
            System.out.println("Course code already exists.");
        }
    }

    private void viewCourses() {

        System.out.println("\n--- All Courses ---");

        courseManager.viewCourses();
    }

    private void searchCourse() {

        System.out.print("Enter Course Code: ");
        String courseCode = input.nextLine();

        Course course = courseManager.searchCourse(courseCode);

        if (course != null) {
            System.out.println("\nCourse found:");
            course.displayCourse();
        } else {
            System.out.println("Course not found.");
        }
    }

    private void deleteCourse() {

        System.out.print("Enter Course Code to delete: ");
        String courseCode = input.nextLine();

        Course course = courseManager.searchCourse(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.print("Are you sure you want to delete this course? (Y/N): ");
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("Y")) {

            if (courseManager.deleteCourse(courseCode)) {
                System.out.println("Course deleted successfully.");
            }

        } else {
            System.out.println("Delete cancelled.");
        }
    }
}