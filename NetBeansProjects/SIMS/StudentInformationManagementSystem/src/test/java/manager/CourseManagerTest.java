/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import model.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseManagerTest {

    @Test
    public void testAddCourse() {

        CourseManager manager = new CourseManager();

        Course course = new Course(
                "COMP503",
                "Programming",
                15
        );

        assertTrue(manager.addCourse(course));

        assertNotNull(
                manager.searchCourse("COMP503")
        );
    }


    @Test
    public void testDuplicateCourseCode() {

        CourseManager manager = new CourseManager();

        Course course1 = new Course(
                "COMP503",
                "Programming",
                15
        );

        Course course2 = new Course(
                "COMP503",
                "Database",
                15
        );

        assertTrue(manager.addCourse(course1));

        assertFalse(manager.addCourse(course2));
    }


    @Test
    public void testSearchCourse() {

        CourseManager manager = new CourseManager();

        Course course = new Course(
                "COMP503",
                "Programming",
                15
        );

        manager.addCourse(course);

        Course result =
                manager.searchCourse("COMP503");

        assertNotNull(result);

        assertEquals(
                "Programming",
                result.getCourseName()
        );
    }


    @Test
    public void testDeleteCourse() {

        CourseManager manager = new CourseManager();

        Course course = new Course(
                "COMP503",
                "Programming",
                15
        );

        manager.addCourse(course);

        assertTrue(
                manager.deleteCourse("COMP503")
        );

        assertNull(
                manager.searchCourse("COMP503")
        );
    }
}