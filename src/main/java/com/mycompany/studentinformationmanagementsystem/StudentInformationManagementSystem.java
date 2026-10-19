/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.studentinformationmanagementsystem;

import database.DatabaseInitializer;
import java.sql.SQLException;
import database.DatabaseConnection;
import gui.LoginFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class StudentInformationManagementSystem {

    public static void main(String[] args) {

 // 1. Connect to Derby and create tables if they do not exist yet
        try {
            DatabaseInitializer.initialise();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Could not start the database.\n" + e.getMessage()
                    + "\n\nIf the database is open in NetBeans (Services tab), disconnect it and try again.",
                    "Database error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Shut Derby down cleanly when the application closes
        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> DatabaseConnection.getInstance().shutdown()));

        // 3. Start the GUI on the Swing event thread
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
