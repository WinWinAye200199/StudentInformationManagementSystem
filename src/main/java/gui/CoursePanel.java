/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import model.Course;
import service.CourseService;
import service.ServiceException;


public class CoursePanel extends JPanel {

    private final CourseService courseService;

    // Table
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[] {"Course Code", "Course Name", "Credits"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;   // the table is read-only; edits go through the form
        }
    };
    private final JTable table = new JTable(tableModel);
    private List<Course> currentCourses;

    // Form fields
    private final JTextField codeField = new JTextField(15);
    private final JTextField nameField = new JTextField(15);
    private final JSpinner creditsSpinner = new JSpinner(new SpinnerNumberModel(15, 0, 120, 1));

    public CoursePanel() {
        this(new CourseService());
    }

    public CoursePanel(CourseService courseService) {
        this.courseService = courseService;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildTable(), BorderLayout.CENTER);
        add(buildFormScroll(), BorderLayout.EAST);

        loadCourses();

        // Reload every time the tab is opened
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadCourses();
            }
        });
    }

    // ---------- Layout ----------

    private JScrollPane buildTable() {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        // Horizontal scroll: keep each column at a readable width
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int[] widths = {120, 320, 80};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFormFromSelectedRow();
            }
        });
        return new JScrollPane(table);
    }

    // Vertical scroll for the form, so the buttons can be reached on a small screen
    private JScrollPane buildFormScroll() {
        JScrollPane scroll = new JScrollPane(buildForm(),
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Course details"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        row = addRow(form, c, row, "Course code:", codeField);
        row = addRow(form, c, row, "Course name:", nameField);
        row = addRow(form, c, row, "Credits:", creditsSpinner);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");
        addButton.addActionListener(e -> addCourse());
        updateButton.addActionListener(e -> updateCourse());
        deleteButton.addActionListener(e -> deleteCourse());
        clearButton.addActionListener(e -> clearForm());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);
        buttons.add(clearButton);

        c.gridx = 0; c.gridy = row; c.gridwidth = 2;
        c.insets = new Insets(12, 6, 4, 6);
        form.add(buttons, c);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(form, BorderLayout.NORTH);   // keeps the form at the top
        return wrapper;
    }

    private int addRow(JPanel form, GridBagConstraints c, int row, String label, JComponent field) {
        c.gridx = 0; c.gridy = row; c.gridwidth = 1;
        form.add(new JLabel(label), c);
        c.gridx = 1;
        form.add(field, c);
        return row + 1;
    }

    // ---------- Actions ----------

    private void loadCourses() {
        try {
            currentCourses = courseService.getAllCourses();
            tableModel.setRowCount(0);
            for (Course course : currentCourses) {
                tableModel.addRow(new Object[] {
                    course.getCourseCode(), course.getCourseName(), course.getCredits()
                });
            }
        } catch (ServiceException ex) {
            showError(ex.getMessage());
        }
    }

    private void addCourse() {
        try {
            courseService.addCourse(buildCourseFromForm());
            showInfo("Course added successfully.");
            clearForm();
            loadCourses();
        } catch (ServiceException ex) {
            showError(ex.getMessage());
        }
    }

    private void updateCourse() {
        if (codeField.isEditable()) {
            showError("Please select a course in the table first.");
            return;
        }
        try {
            courseService.updateCourse(buildCourseFromForm());
            showInfo("Course updated successfully.");
            clearForm();
            loadCourses();
        } catch (ServiceException ex) {
            showError(ex.getMessage());
        }
    }

    private void deleteCourse() {
        if (codeField.isEditable()) {
            showError("Please select a course in the table first.");
            return;
        }
        String code = codeField.getText();
        int choice = JOptionPane.showConfirmDialog(this,
                "Delete course " + code + "?\nAll enrollments in this course will also be removed.",
                "Confirm delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            courseService.deleteCourse(code);
            showInfo("Course deleted.");
            clearForm();
            loadCourses();
        } catch (ServiceException ex) {
            showError(ex.getMessage());
        }
    }

    // ---------- Form helpers ----------

    private Course buildCourseFromForm() {
        return new Course(
                codeField.getText().trim().toUpperCase(),   // e.g. "comp603" -> "COMP603"
                nameField.getText().trim(),
                (Integer) creditsSpinner.getValue());
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0 || currentCourses == null || row >= currentCourses.size()) {
            return;
        }
        Course course = currentCourses.get(row);
        codeField.setText(course.getCourseCode());
        codeField.setEditable(false);   // the course code is the primary key, so it cannot be changed
        nameField.setText(course.getCourseName());
        creditsSpinner.setValue(course.getCredits());
    }

    private void clearForm() {
        table.clearSelection();
        codeField.setText("");
        codeField.setEditable(true);
        nameField.setText("");
        creditsSpinner.setValue(15);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
