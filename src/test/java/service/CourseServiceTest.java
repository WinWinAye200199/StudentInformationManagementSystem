/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import model.Course;
import org.junit.Test;


public class CourseServiceTest {

    private final CourseService service = new CourseService();

    @Test
    public void zeroCreditsIsRejected() {
        try {
            service.validateCourse(new Course("COMP603", "Software Development", 0));
            fail("Expected ServiceException for 0 credits");
        } catch (ServiceException e) {
            assertEquals("Credits must be a positive number.", e.getMessage());
        }
    }

    @Test
    public void blankCourseNameIsRejected() {
        try {
            service.validateCourse(new Course("COMP603", "  ", 15));
            fail("Expected ServiceException for blank course name");
        } catch (ServiceException e) {
            assertEquals("Course name is required.", e.getMessage());
        }
    }
}

