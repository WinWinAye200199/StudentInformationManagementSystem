/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import model.Enrollment;
import model.Student;
import model.Course;

import java.util.ArrayList;

public class EnrollmentManager {

    private ArrayList<Enrollment> enrollments;

    private StudentManager studentManager;
    private CourseManager courseManager;

    public EnrollmentManager(StudentManager studentManager,
            CourseManager courseManager) {

        this.studentManager = studentManager;
        this.courseManager = courseManager;

        enrollments = new ArrayList<>();
    }

    public boolean addEnrollment(
            String studentId,
            String courseCode,
            String semester) {

        Student student
                = studentManager.searchStudent(studentId);

        Course course
                = courseManager.searchCourse(courseCode);

        if (student == null || course == null) {
            return false;
        }

        for (Enrollment enrollment : enrollments) {

            boolean sameStudent
                    = enrollment.getStudent().getId()
                            .equalsIgnoreCase(studentId);

            boolean sameCourse
                    = enrollment.getCourse().getCourseCode()
                            .equalsIgnoreCase(courseCode);

            if (sameStudent && sameCourse) {
                return false;
            }
        }

        Enrollment enrollment
                = new Enrollment(
                        student,
                        course,
                        semester
                );

        enrollments.add(enrollment);

        return true;
    }
    
    public boolean isAlreadyEnrolled(
            String studentId,
            String courseCode) {

        for (Enrollment enrollment : enrollments) {

            if (enrollment.getStudent().getId()
                    .equalsIgnoreCase(studentId)
                    && enrollment.getCourse().getCourseCode()
                    .equalsIgnoreCase(courseCode)) {

                return true;
            }
        }

        return false;
    }

    public void viewEnrollments() {

        if (enrollments.isEmpty()) {
            System.out.println("No enrollments available.");
            return;
        }

        for (Enrollment enrollment : enrollments) {

            System.out.println(
                    "Student: "
                    + enrollment.getStudent().getId()
                    + " - "
                    + enrollment.getStudent().getName()
            );

            System.out.println(
                    "Course: "
                    + enrollment.getCourse().getCourseCode()
                    + " - "
                    + enrollment.getCourse().getCourseName()
            );

            System.out.println(
                    "Semester: "
                    + enrollment.getSemester()
            );

            System.out.println("----------------------");
        }
    }

    public ArrayList<Enrollment> getEnrollments() {
        return enrollments;
    }
}
