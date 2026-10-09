/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import model.Course;
import java.util.ArrayList;
import java.util.HashMap;

public class CourseManager {

    private ArrayList<Course> courses;
    private HashMap<String, Course> courseMap;

    public CourseManager() {
        courses = new ArrayList<>();
        courseMap = new HashMap<>();
    }

    public boolean addCourse(Course course) {

        if (courseMap.containsKey(course.getCourseCode())) {
            return false;
        }

        courses.add(course);
        courseMap.put(course.getCourseCode(), course);

        return true;
    }

    public void viewCourses() {

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        for (Course course : courses) {
            course.displayCourse();
            System.out.println("----------------------");
        }
    }

    public Course searchCourse(String courseCode) {
        return courseMap.get(courseCode);
    }

    public boolean deleteCourse(String courseCode) {

        Course course = courseMap.get(courseCode);

        if (course == null) {
            return false;
        }

        courses.remove(course);
        courseMap.remove(courseCode);

        return true;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}