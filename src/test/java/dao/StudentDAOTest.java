/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import model.DomesticStudent;
import model.InternationalStudent;
import model.Student;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testutil.TestDatabase;


public class StudentDAOTest {

    private StudentDAO dao;

    @BeforeClass
    public static void initDatabase() throws SQLException {
        TestDatabase.setUp();
    }

    @Before
    public void setUp() throws SQLException {
        TestDatabase.clearTables();
        dao = new StudentDAO();
    }

    @Test
    public void domesticStudentIsSavedAndRetrieved() throws SQLException {
        dao.addStudent(new DomesticStudent("S001", "Win Win Aye", "win@email.com",
                "0211234567", "Computer Science", 2, true));

        Student loaded = dao.getStudentById("S001");

        assertTrue(loaded instanceof DomesticStudent);   // polymorphism: correct subclass returned
        assertEquals("Win Win Aye", loaded.getName());
        assertTrue(((DomesticStudent) loaded).isScholarship());
    }

    @Test
    public void internationalStudentFieldsAreSaved() throws SQLException {
        dao.addStudent(new InternationalStudent("S002", "John Lee", "john@email.com",
                "0217654321", "IT", 1, "Myanmar", "2027-12-31"));

        InternationalStudent loaded = (InternationalStudent) dao.getStudentById("S002");

        assertEquals("Myanmar", loaded.getCountry());
        assertEquals("2027-12-31", loaded.getVisaExpiry());
    }

    @Test
    public void deletedStudentCannotBeFound() throws SQLException {
        dao.addStudent(new DomesticStudent("S003", "Aye Aye", "aye@email.com",
                "0211111111", "IT", 1, false));

        assertTrue(dao.deleteStudent("S003"));
        assertNull(dao.getStudentById("S003"));
        assertFalse(dao.deleteStudent("S003"));   // second delete finds nothing
    }
    
    @Test
    public void nextStudentIdFollowsTheHighestExistingId() throws SQLException {
        dao.addStudent(new DomesticStudent("S001", "Win Win Aye", "win@email.com",
                "0211234567", "Computer Science", 2, true));
        dao.addStudent(new DomesticStudent("S007", "Aye Aye", "aye@email.com",
                "0211111111", "IT", 1, false));

        assertEquals("S008", dao.getNextStudentId());
    }
}
