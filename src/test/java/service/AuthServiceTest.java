/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import model.User;
import org.junit.BeforeClass;
import org.junit.Test;
import testutil.TestDatabase;

// Login tests. DatabaseInitializer creates the default admin / admin123 user.
public class AuthServiceTest {

    private final AuthService authService = new AuthService();

    @BeforeClass
    public static void initDatabase() throws SQLException {
        TestDatabase.setUp();
    }

    @Test
    public void correctPasswordLogsIn() throws ServiceException {
        User user = authService.login("admin", "admin123");

        assertEquals("admin", user.getUsername());
        assertTrue(user.isAdmin());
    }

    @Test
    public void wrongPasswordIsRejected() {
        try {
            authService.login("admin", "wrong");
            fail("Expected ServiceException for wrong password");
        } catch (ServiceException e) {
            assertEquals("Incorrect username or password.", e.getMessage());
        }
    }

    @Test
    public void blankUsernameIsRejected() {
        try {
            authService.login("  ", "admin123");
            fail("Expected ServiceException for blank username");
        } catch (ServiceException e) {
            assertEquals("Please enter your username.", e.getMessage());
        }
    }
}