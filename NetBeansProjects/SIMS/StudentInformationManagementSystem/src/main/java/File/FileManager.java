/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package File;

import model.Student;
import model.Course;
import model.Enrollment;

import manager.StudentManager;
import manager.CourseManager;
import manager.EnrollmentManager;

import java.io.*;
import java.util.ArrayList;
import model.DomesticStudent;
import model.InternationalStudent;

public class FileManager {

    private final String studentFile = "src/main/java/resources/students.txt";
    private final String courseFile = "src/main/java/resources/courses.txt";
    private final String enrollmentFile = "src/main/java/resources/enrollments.txt";

    public void saveStudents(ArrayList<Student> students) {

        try (BufferedWriter writer
                = new BufferedWriter(new FileWriter(studentFile))) {

            for (Student student : students) {

                if (student instanceof DomesticStudent) {

                    DomesticStudent domestic
                            = (DomesticStudent) student;

                    writer.write(
                            "DOMESTIC,"
                            + domestic.getId() + ","
                            + domestic.getName() + ","
                            + domestic.getEmail() + ","
                            + domestic.getPhone() + ","
                            + domestic.getProgramme() + ","
                            + domestic.getYear() + ","
                            + domestic.isScholarship()
                    );

                } else if (student instanceof InternationalStudent) {

                    InternationalStudent international
                            = (InternationalStudent) student;

                    writer.write(
                            "INTERNATIONAL,"
                            + international.getId() + ","
                            + international.getName() + ","
                            + international.getEmail() + ","
                            + international.getPhone() + ","
                            + international.getProgramme() + ","
                            + international.getYear() + ","
                            + international.getCountry() + ","
                            + international.getVisaExpiry()
                    );
                }

                writer.newLine();
            }

            System.out.println("Students saved successfully.");

        } catch (IOException e) {

            System.out.println(
                    "Error saving students: "
                    + e.getMessage()
            );
        }
    }

    public ArrayList<Student> loadStudents() {

        ArrayList<Student> students = new ArrayList<>();

        File file = new File(studentFile);

        if (!file.exists()) {
            return students;
        }

        try (BufferedReader reader
                = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",", -1);

                if (data.length == 8
                        && data[0].equalsIgnoreCase("DOMESTIC")) {

                    String id = data[1];
                    String name = data[2];
                    String email = data[3];
                    String phone = data[4];
                    String programme = data[5];
                    int year = Integer.parseInt(data[6]);
                    boolean scholarship
                            = Boolean.parseBoolean(data[7]);

                    DomesticStudent student
                            = new DomesticStudent(
                                    id,
                                    name,
                                    email,
                                    phone,
                                    programme,
                                    year,
                                    scholarship
                            );

                    students.add(student);

                } else if (data.length == 9
                        && data[0].equalsIgnoreCase("INTERNATIONAL")) {

                    String id = data[1];
                    String name = data[2];
                    String email = data[3];
                    String phone = data[4];
                    String programme = data[5];
                    int year = Integer.parseInt(data[6]);
                    String country = data[7];
                    String visaExpiry = data[8];

                    InternationalStudent student
                            = new InternationalStudent(
                                    id,
                                    name,
                                    email,
                                    phone,
                                    programme,
                                    year,
                                    country,
                                    visaExpiry
                            );

                    students.add(student);
                }
            }

            System.out.println(
                    "Students loaded successfully."
            );

        } catch (IOException | NumberFormatException e) {

            System.out.println(
                    "Error loading students: "
                    + e.getMessage()
            );
        }

        return students;
    }

    public void saveCourses(ArrayList<Course> courses) {

        try (BufferedWriter writer
                = new BufferedWriter(new FileWriter(courseFile))) {

            for (Course course : courses) {

                writer.write(
                        course.getCourseCode() + ","
                        + course.getCourseName() + ","
                        + course.getCredits()
                );

                writer.newLine();
            }

            System.out.println("Courses saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving courses: " + e.getMessage());
        }
    }

    public ArrayList<Course> loadCourses() {

        ArrayList<Course> courses = new ArrayList<>();

        File file = new File(courseFile);

        if (!file.exists()) {
            return courses;
        }

        try (BufferedReader reader
                = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",", -1);

                if (data.length == 3) {

                    String courseCode = data[0];
                    String courseName = data[1];
                    int credits = Integer.parseInt(data[2]);

                    Course course = new Course(
                            courseCode,
                            courseName,
                            credits
                    );

                    courses.add(course);
                }
            }

            System.out.println("Courses loaded successfully.");

        } catch (IOException | NumberFormatException e) {

            System.out.println(
                    "Error loading courses: " + e.getMessage()
            );
        }

        return courses;
    }

    public void saveEnrollments(
            ArrayList<Enrollment> enrollments) {

        try (BufferedWriter writer
                = new BufferedWriter(new FileWriter(enrollmentFile))) {

            for (Enrollment enrollment : enrollments) {

                writer.write(
                        enrollment.getStudent().getId() + ","
                        + enrollment.getCourse().getCourseCode() + ","
                        + enrollment.getSemester()
                );

                writer.newLine();
            }

            System.out.println("Enrollments saved successfully.");

        } catch (IOException e) {

            System.out.println(
                    "Error saving enrollments: "
                    + e.getMessage()
            );
        }
    }

    public void loadEnrollments(
            StudentManager studentManager,
            CourseManager courseManager,
            EnrollmentManager enrollmentManager) {

        File file = new File(enrollmentFile);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader
                = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",", -1);

                if (data.length == 3) {

                    String studentId = data[0];
                    String courseCode = data[1];
                    String semester = data[2];

                    enrollmentManager.addEnrollment(
                            studentId,
                            courseCode,
                            semester
                    );
                }
            }

            System.out.println(
                    "Enrollments loaded successfully."
            );

        } catch (IOException e) {

            System.out.println(
                    "Error loading enrollments: "
                    + e.getMessage()
            );
        }
    }
}
