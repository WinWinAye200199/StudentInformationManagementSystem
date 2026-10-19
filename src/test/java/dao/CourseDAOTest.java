/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import model.Course;
import model.DomesticStudent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testutil.TestDatabase;

// Database tests for courses (in-memory Derby)
public class CourseDAOTest {

    private CourseDAO courseDAO;

    @BeforeClass
    public static void initDatabase() throws SQLException {
        TestDatabase.setUp();
    }

    @Before
    public void setUp() throws SQLException {
        TestDatabase.clearTables();
        courseDAO = new CourseDAO();
    }

    @Test
    public void updatedCourseIsSaved() throws SQLException {
        courseDAO.addCourse(new Course("COMP603", "Software Development", 15));

        assertTrue(courseDAO.updateCourse(new Course("COMP603", "Program Design", 30)));

        Course loaded = courseDAO.getCourseByCode("COMP603");
        assertEquals("Program Design", loaded.getCourseName());
        assertEquals(30, loaded.getCredits());
    }

    @Test
    public void deletingCourseAlsoRemovesItsEnrollments() throws SQLException {
        new StudentDAO().addStudent(new DomesticStudent("S001", "Win Win Aye", "win@email.com",
                "0211234567", "Computer Science", 2, true));
        courseDAO.addCourse(new Course("COMP603", "Software Development", 15));
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        enrollmentDAO.addEnrollment("S001", "COMP603", "2026 S2");

        courseDAO.deleteCourse("COMP603");

        assertNull(courseDAO.getCourseByCode("COMP603"));
        assertEquals(0, enrollmentDAO.getAllEnrollments().size());   // ON DELETE CASCADE
    }
}