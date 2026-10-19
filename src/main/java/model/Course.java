/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Course {
    private String courseCode;
    private String courseName;
    private int credits;
    
    public Course(){}

    public Course(String courseCode,
                  String courseName,
                  int credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
    
    public void displayCourse(){
        System.out.println(
            courseCode + " - "
            + courseName
            + " (" + credits + " credits)"
        );
    };
    
   @Override
    public String toString() {
        return courseCode + " - " + courseName;   
    }
}
