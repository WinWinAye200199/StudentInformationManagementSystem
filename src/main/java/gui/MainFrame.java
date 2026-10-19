/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import model.User;

// View: main window shown after login, with one tab per feature
public class MainFrame extends JFrame {

    public MainFrame(User user) {
        super("SIMS - Student Information Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JLabel welcome = new JLabel("Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
        welcome.setFont(welcome.getFont().deriveFont(Font.BOLD, 14f));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        header.add(welcome, BorderLayout.WEST);
        header.add(logoutButton, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Students", new StudentPanel());
        tabs.addTab("Courses", new CoursePanel());
        tabs.addTab("Enrollments", new EnrollmentPanel());

        add(header, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel placeholder(String name) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(name + " screen - coming next", SwingConstants.CENTER), BorderLayout.CENTER);
        return panel;
    }

    private void logout() {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?",
                "Logout", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}
