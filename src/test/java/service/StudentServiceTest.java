/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import model.DomesticStudent;
import model.InternationalStudent;
import org.junit.Before;
import org.junit.Test;


public class StudentServiceTest {

    private StudentService service;

    @Before
    public void setUp() {
        service = new StudentService();
    }

    @Test
    public void validDomesticStudentPassesValidation() throws ServiceException {
        DomesticStudent s = new DomesticStudent("S100", "Win Win Aye", "win@email.com",
                "0211234567", "Computer Science", 2, true);
        service.validateStudent(s);   // no exception = test passes
    }

    @Test
    public void invalidEmailIsRejected() {
        DomesticStudent s = new DomesticStudent("S101", "Win Win Aye", "win-email.com",
                "0211234567", "Computer Science", 2, false);
        try {
            service.validateStudent(s);
            fail("Expected ServiceException for invalid email");
        } catch (ServiceException e) {
            assertEquals("Please enter a valid email address (e.g. name@email.com).", e.getMessage());
        }
    }

    @Test
    public void yearOutsideRangeIsRejected() {
        DomesticStudent s = new DomesticStudent("S102", "Win Win Aye", "win@email.com",
                "0211234567", "Computer Science", 9, false);
        try {
            service.validateStudent(s);
            fail("Expected ServiceException for year 9");
        } catch (ServiceException e) {
            assertEquals("Year must be between 1 and 5.", e.getMessage());
        }
    }

    @Test
    public void wrongVisaDateFormatIsRejected() {
        InternationalStudent s = new InternationalStudent("S103", "John Lee", "john@email.com",
                "0217654321", "IT", 1, "Myanmar", "31/12/2027");
        try {
            service.validateStudent(s);
            fail("Expected ServiceException for visa date format");
        } catch (ServiceException e) {
            assertEquals("Visa expiry date must be in the format YYYY-MM-DD.", e.getMessage());
        }
    }
}
