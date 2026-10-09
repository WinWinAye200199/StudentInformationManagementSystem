/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.util.Scanner;
import manager.StudentManager;
import model.DomesticStudent;
import model.InternationalStudent;
import model.Student;

public class StudentMenu {

    private Scanner input;

    StudentManager studentManager;

    public StudentMenu(Scanner input, StudentManager studentManager) {

        this.input = input;
        this.studentManager = studentManager;
    }

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("================================");
            System.out.println("       STUDENT MANAGEMENT");
            System.out.println("================================");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Back to Main Menu");
            System.out.println("================================");

            System.out.print("Enter your choice: ");

            String choice = input.nextLine();

            switch (choice) {

                case "1":
                    addStudent();
                    break;

                case "2":
                    viewStudents();
                    break;

                case "3":
                    searchStudent();
                    break;

                case "4":
                    updateStudent();
                    break;

                case "5":
                    deleteStudent();
                    break;

                case "6":
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please enter a number from 1 to 6."
                    );
            }
        }
    }

    private void addStudent() {

        System.out.println("\n--- Add Student ---");

        System.out.print("Student ID: ");
        String id = input.nextLine();

        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Phone: ");
        String phone = input.nextLine();

        System.out.print("Programme: ");
        String programme = input.nextLine();

        int year;

        while (true) {
            System.out.print("Year of Study (1-4): ");

            try {
                year = Integer.parseInt(input.nextLine());

                if (year >= 1 && year <= 4) {
                    break;
                }

                System.out.println(
                        "Year must be between 1 and 4."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }

        System.out.println("\nStudent Type:");
        System.out.println("1. Domestic Student");
        System.out.println("2. International Student");

        String type;

        while (true) {

            System.out.print("Enter student type: ");
            try {
                type = input.nextLine();

                if (type.equals("1") || type.equals("2")) {
                    break;
                }

                System.out.println(
                        "Invalid choice. Please enter 1 or 2."
                );
            }catch (NumberFormatException e) {
                System.out.println(
                        "Please enter a valid number."
                );
            }

        }

        Student student;

        if (type.equals("1")) {

            System.out.print("Scholarship (Y/N): ");
            String scholarshipInput = input.nextLine();

            boolean scholarship
                    = scholarshipInput.equalsIgnoreCase("Y");

            student = new DomesticStudent(
                    id,
                    name,
                    email,
                    phone,
                    programme,
                    year,
                    scholarship
            );

        } else {

            System.out.print("Country: ");
            String country = input.nextLine();

            System.out.print("Visa Expiry Date: ");
            String visaExpire = input.nextLine();

            student = new InternationalStudent(
                    id,
                    name,
                    email,
                    phone,
                    programme,
                    year,
                    country,
                    visaExpire
            );
        }

        if (studentManager.addStudent(student)) {

            System.out.println(
                    "Student added successfully."
            );

        } else {

            System.out.println(
                    "Student ID already exists."
            );
        }
    }

    private void viewStudents() {

        System.out.println();
        System.out.println("---------- All Students ----------");

        studentManager.viewStudents();
    }

    private void searchStudent() {

        System.out.println();
        System.out.print("Enter Student ID: ");

        String id = input.nextLine();

        Student student = studentManager.searchStudent(id);

        if (student == null) {

            System.out.println("Student not found.");

        } else {

            student.displayInfo();
        }
    }

    private void updateStudent() {

        System.out.println();
        System.out.print("Enter Student ID: ");

        String id = input.nextLine();

        Student student = studentManager.searchStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        if (student instanceof DomesticStudent) {
            System.out.println("Student Type: Domestic Student");
        } else if (student instanceof InternationalStudent) {
            System.out.println("Student Type: International Student");
        }

        System.out.println("\nChoose number you would like to update:");
        System.out.println("1. Name");
        System.out.println("2. Email");
        System.out.println("3. Phone");
        System.out.println("4. Programme");
        System.out.println("5. Year of Study (1-4)");

        if (student instanceof DomesticStudent) {
            System.out.println("6. Scholarship");
        } else if (student instanceof InternationalStudent) {
            System.out.println("6. Country");
            System.out.println("7. Visa Expiry Date");
        }

        System.out.println("0. Cancel");
        System.out.print("Enter your choice: ");

        String choice = input.nextLine();

        switch (choice) {

            case "1":
                System.out.print("Enter new name: ");
                String newName = input.nextLine();

                if (studentManager.updateName(id, newName)) {
                    System.out.println("Name updated successfully.");
                } else {
                    System.out.println("Unable to update name.");
                }
                break;

            case "2":
                System.out.print("Enter new email: ");
                String newEmail = input.nextLine();

                if (studentManager.updateEmail(id, newEmail)) {
                    System.out.println("Email updated successfully.");
                } else {
                    System.out.println("Unable to update email.");
                }
                break;

            case "3":
                System.out.print("Enter new phone: ");
                String newPhone = input.nextLine();

                if (studentManager.updatePhone(id, newPhone)) {
                    System.out.println("Phone updated successfully.");
                } else {
                    System.out.println("Unable to update phone.");
                }
                break;

            case "4":
                System.out.print("Enter new programme: ");
                String newProgramme = input.nextLine();

                student.setProgramme(newProgramme);
                System.out.println("Programme updated successfully.");
                break;

            case "5":
                System.out.print("Enter new year of study (1-4): ");

                try {
                    int newYear = Integer.parseInt(input.nextLine());

                    if (newYear < 1 || newYear > 4) {
                        System.out.println("Invalid year. Please enter 1-4.");
                        break;
                    }

                    student.setYear(newYear);
                    System.out.println("Year updated successfully.");

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
                break;

            case "6":

                if (student instanceof DomesticStudent) {

                    System.out.print("Scholarship (Y/N): ");
                    String scholarshipInput = input.nextLine();

                    if (scholarshipInput.equalsIgnoreCase("Y")) {

                        if (studentManager.updateScholarship(id, true)) {
                            System.out.println(
                                    "Scholarship updated successfully."
                            );
                        } else {
                            System.out.println(
                                    "Unable to update scholarship."
                            );
                        }

                    } else if (scholarshipInput.equalsIgnoreCase("N")) {

                        if (studentManager.updateScholarship(id, false)) {
                            System.out.println(
                                    "Scholarship updated successfully."
                            );
                        } else {
                            System.out.println(
                                    "Unable to update scholarship."
                            );
                        }

                    } else {
                        System.out.println("Please enter Y or N.");
                    }

                } else if (student instanceof InternationalStudent) {

                    System.out.print("Enter new country: ");
                    String newCountry = input.nextLine();

                    if (studentManager.updateCountry(id, newCountry)) {
                        System.out.println(
                                "Country updated successfully."
                        );
                    } else {
                        System.out.println(
                                "Unable to update country."
                        );
                    }

                } else {
                    System.out.println("Invalid option.");
                }

                break;

            case "7":

                if (student instanceof InternationalStudent) {

                    System.out.print("Enter new visa expiry date: ");
                    String newVisaExpiry = input.nextLine();

                    if (studentManager.updateVisaExpiry(
                            id, newVisaExpiry)) {

                        System.out.println(
                                "Visa expiry date updated successfully."
                        );

                    } else {
                        System.out.println(
                                "Unable to update visa expiry date."
                        );
                    }

                } else {
                    System.out.println("Invalid option.");
                }

                break;

            case "0":
                System.out.println("Update cancelled.");
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void deleteStudent() {

        System.out.println();
        System.out.print("Enter Student ID: ");

        String id = input.nextLine();

        Student student = studentManager.searchStudent(id);

        if (student == null) {

            System.out.println("Student not found.");
            return;
        }

        System.out.print(
                "Are you sure you want to delete this student? (Y/N): "
        );

        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("Y")) {

            boolean deleted
                    = studentManager.deleteStudent(id);

            if (deleted) {

                System.out.println(
                        "Student deleted successfully."
                );

            }

        } else {

            System.out.println("Delete cancelled.");
        }
    }
}
