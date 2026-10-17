/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.UserDAO;
import java.sql.SQLException;
import model.User;

public class AuthService {

    private final UserDAO userDAO;

    public AuthService() {
        this(new UserDAO());
    }

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User login(String username, String password) throws ServiceException {
        if (username == null || username.isBlank()) {
            throw new ServiceException("Please enter your username.");
        }
        if (password == null || password.isEmpty()) {
            throw new ServiceException("Please enter your password.");
        }
        try {
            User user = userDAO.findByCredentials(username.trim(), password);
            if (user == null) {
                throw new ServiceException("Incorrect username or password.");
            }
            return user;
        } catch (SQLException e) {
            throw new ServiceException("Could not connect to the database. Please try again.", e);
        }
    }
}
