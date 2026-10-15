/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import model.Course;
import model.DomesticStudent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testutil.TestDatabase;


public class EnrollmentServiceTest {

    private EnrollmentService enrollmentService;

    @BeforeClass
    public static void initDatabase() throws SQLException {
        TestDatabase.setUp();
    }

    @Before
    public void setUp() throws SQLException, ServiceException {
        TestDatabase.clearTables();
        new StudentService().addStudent(new DomesticStudent("S001", "Win Win Aye",
                "win@email.com", "0211234567", "Computer Science", 2, true));
        new CourseService().addCourse(new Course("COMP603", "Software Development", 15));
        enrollmentService = new EnrollmentService();
    }

    @Test
    public void validEnrollmentIsSaved() throws ServiceException {
        enrollmentService.enrollStudent("S001", "COMP603", "2026 S2");

        assertEquals(1, enrollmentService.getAllEnrollments().size());
        assertEquals("Software Development",
                enrollmentService.getAllEnrollments().get(0).getCourse().getCourseName());
    }

    @Test
    public void duplicateEnrollmentIsRejected() throws ServiceException {
        enrollmentService.enrollStudent("S001", "COMP603", "2026 S2");
        try {
            enrollmentService.enrollStudent("S001", "COMP603", "2027 S1");
            fail("Expected ServiceException for duplicate enrollment");
        } catch (ServiceException e) {
            assertEquals("Student S001 is already enrolled in COMP603.", e.getMessage());
        }
    }

    @Test
    public void unknownStudentIsRejected() {
        try {
            enrollmentService.enrollStudent("S999", "COMP603", "2026 S2");
            fail("Expected ServiceException for unknown student");
        } catch (ServiceException e) {
            assertEquals("Student ID S999 does not exist.", e.getMessage());
        }
    }
}
