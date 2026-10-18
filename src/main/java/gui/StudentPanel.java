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
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import model.DomesticStudent;
import model.InternationalStudent;
import model.Student;
import service.CourseService;
import service.ServiceException;
import service.StudentService;

public class StudentPanel extends JPanel {

    private static final String DOMESTIC = "Domestic";
    private static final String INTERNATIONAL = "International";

    private final CourseService courseService = new CourseService();
    private final JComboBox<Course> courseBox = new JComboBox<>();

    private final StudentService studentService;
    private String selectedId = null;   // null = adding a new student, otherwise = editing this student

    // Table
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Name", "Email", "Phone", "Programme", "Year", "Type", "Details"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;   // the table is read-only; edits go through the form
        }
    };
    private final JTable table = new JTable(tableModel);
    private List<Student> currentStudents;

    // Search
    private final JTextField searchField = new JTextField(20);

    // Form fields
    private final JTextField idField = new JTextField(15);
    private final JTextField nameField = new JTextField(15);
    private final JTextField emailField = new JTextField(15);
    private final JTextField phoneField = new JTextField(15);
    private final JTextField programmeField = new JTextField(15);
    private final JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
    private final JComboBox<String> typeBox = new JComboBox<>(new String[]{DOMESTIC, INTERNATIONAL});
    private final JCheckBox scholarshipBox = new JCheckBox("Has scholarship");
    private final JTextField countryField = new JTextField(15);
    private final JTextField visaField = new JTextField(15);

    // Labels that are shown or hidden depending on the student type
    private final JLabel scholarshipLabel = new JLabel("Scholarship:");
    private final JLabel countryLabel = new JLabel("Country:");
    private final JLabel visaLabel = new JLabel("Visa expiry:");
    private final JLabel visaHint = new JLabel("Format: YYYY-MM-DD");

    public StudentPanel() {
        this(new StudentService());
    }

    public StudentPanel(StudentService studentService) {
        this.studentService = studentService;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildSearchBar(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
        add(buildFormScroll(), BorderLayout.EAST);

        typeBox.addActionListener(e -> updateTypeFields());
        updateTypeFields();
        loadStudents(null);
        loadCourses();

        idField.setEditable(false);   // the ID is generated automatically
        showNextId();

    }

    // Load all courses from the database into the dropdown
    private void loadCourses() {
        try {
            List<Course> courses = courseService.getAllCourses();
            courseBox.setModel(new DefaultComboBoxModel<>(courses.toArray(new Course[0])));
        } catch (ServiceException ex) {
            showError(ex.getMessage());
        }
    }

    // Show the next free ID (e.g. S003) in the ID field
    private void showNextId() {
        try {
            idField.setText(studentService.generateNextId());
        } catch (ServiceException ex) {
            showError(ex.getMessage());
        }
    }

    // ---------- Layout ----------
    private JPanel buildSearchBar() {
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");
        searchButton.addActionListener(e -> loadStudents(searchField.getText()));
        showAllButton.addActionListener(e -> {
            searchField.setText("");
            loadStudents(null);
        });

        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bar.add(new JLabel("Search (ID, name or programme):"));
        bar.add(searchField);
        bar.add(searchButton);
        bar.add(showAllButton);
        return bar;
    }

    private JScrollPane buildTable() {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        // Horizontal scroll: keep each column at a readable width instead of squeezing them
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int[] widths = {70, 140, 180, 110, 160, 50, 100, 220};
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

    // Vertical scroll for the form, so all fields and buttons can be reached on a small screen
    private JScrollPane buildFormScroll() {
        JScrollPane scroll = new JScrollPane(buildForm(),
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);   // smoother mouse-wheel scrolling
        return scroll;
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Student details"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        row = addRow(form, c, row, "Student ID:", idField);
        row = addRow(form, c, row, "Name:", nameField);
        row = addRow(form, c, row, "Email:", emailField);
        row = addRow(form, c, row, "Phone:", phoneField);
        row = addRow(form, c, row, "Programme:", programmeField);
        row = addRow(form, c, row, "Year (1-5):", yearSpinner);
        row = addRow(form, c, row, "Student type:", typeBox);
        row = addRow(form, c, row, scholarshipLabel, scholarshipBox);
        row = addRow(form, c, row, countryLabel, countryField);
        row = addRow(form, c, row, visaLabel, visaField);

        c.gridx = 1;
        c.gridy = row++;
        visaHint.setFont(visaHint.getFont().deriveFont(11f));
        form.add(visaHint, c);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearForm());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);
        buttons.add(clearButton);

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        c.insets = new Insets(12, 6, 4, 6);
        form.add(buttons, c);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(form, BorderLayout.NORTH);   // keeps the form at the top
        return wrapper;
    }

    private int addRow(JPanel form, GridBagConstraints c, int row, String label, JComponent field) {
        return addRow(form, c, row, new JLabel(label), field);
    }

    private int addRow(JPanel form, GridBagConstraints c, int row, JLabel label, JComponent field) {
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 1;
        form.add(label, c);
        c.gridx = 1;
        form.add(field, c);
        return row + 1;
    }

    // Show only the fields that belong to the selected student type
    private void updateTypeFields() {
        boolean domestic = DOMESTIC.equals(typeBox.getSelectedItem());

        scholarshipLabel.setVisible(domestic);
        scholarshipBox.setVisible(domestic);

        countryLabel.setVisible(!domestic);
        countryField.setVisible(!domestic);
        visaLabel.setVisible(!domestic);
        visaField.setVisible(!domestic);
        visaHint.setVisible(!domestic);

        revalidate();   // recalculate the layout after showing/hiding fields
        repaint();
    }

    // ---------- Actions ----------
    private void loadStudents(String keyword) {
        try {
            currentStudents = studentService.searchStudents(keyword);
            System.out.println("Students loaded from DB: " + currentStudents.size());
            tableModel.setRowCount(0);
            for (Student s : currentStudents) {
                tableModel.addRow(new Object[]{
                    s.getId(), s.getName(), s.getEmail(), s.getPhone(),
                    s.getProgramme(), s.getYear(), typeOf(s), detailsOf(s)
                });
            }
        } catch (ServiceException ex) {
            showError(ex.getMessage());
        }
    }

    private void addStudent() {
        try {
            Student student = buildStudentFromForm();
            studentService.addStudent(student);
            showInfo("Student " + student.getId() + " added successfully.");
            clearForm();
            loadStudents(null);
        } catch (ServiceException ex) {
            showError(ex.getMessage());
        }
    }

    private void updateStudent() {
        if (selectedId == null) {
            showError("Please select a student in the table first.");
            return;
        }
        try {
            studentService.updateStudent(buildStudentFromForm());
            showInfo("Student updated successfully.");
            clearForm();
            loadStudents(null);
        } catch (ServiceException ex) {
            showError(ex.getMessage());
        }
    }

    private void deleteStudent() {
        if (selectedId == null) {
            showError("Please select a student in the table first.");
            return;
        }
        String id = idField.getText();
        int choice = JOptionPane.showConfirmDialog(this,
                "Delete student " + id + "?\nTheir enrollments will also be removed.",
                "Confirm delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            studentService.deleteStudent(id);
            showInfo("Student deleted.");
            clearForm();
            loadStudents(null);
        } catch (ServiceException ex) {
            showError(ex.getMessage());
        }
    }

    // ---------- Form helpers ----------
    // Polymorphism: the form creates the correct subclass, but the service only sees "Student"
    private Student buildStudentFromForm() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String programme = programmeField.getText().trim();
        int year = (Integer) yearSpinner.getValue();

        if (DOMESTIC.equals(typeBox.getSelectedItem())) {
            return new DomesticStudent(id, name, email, phone, programme, year,
                    scholarshipBox.isSelected());
        }
        return new InternationalStudent(id, name, email, phone, programme, year,
                countryField.getText().trim(), visaField.getText().trim());
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0 || currentStudents == null || row >= currentStudents.size()) {
            return;
        }
        Student s = currentStudents.get(row);
        idField.setText(s.getId());
        selectedId = s.getId();
        nameField.setText(s.getName());
        emailField.setText(s.getEmail());
        phoneField.setText(s.getPhone());
        programmeField.setText(s.getProgramme());
        yearSpinner.setValue(Math.max(1, Math.min(5, s.getYear())));

        if (s instanceof DomesticStudent d) {
            typeBox.setSelectedItem(DOMESTIC);
            scholarshipBox.setSelected(d.isScholarship());
            countryField.setText("");
            visaField.setText("");
        } else if (s instanceof InternationalStudent i) {
            typeBox.setSelectedItem(INTERNATIONAL);
            scholarshipBox.setSelected(false);
            countryField.setText(i.getCountry());
            visaField.setText(i.getVisaExpiry());
        }
        updateTypeFields();
    }

    private void clearForm() {
        table.clearSelection();
        selectedId = null;
        showNextId();
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        programmeField.setText("");
        yearSpinner.setValue(1);
        typeBox.setSelectedItem(DOMESTIC);
        scholarshipBox.setSelected(false);
        countryField.setText("");
        visaField.setText("");
        updateTypeFields();
    }

    private String typeOf(Student s) {
        return (s instanceof InternationalStudent) ? INTERNATIONAL : DOMESTIC;
    }

    private String detailsOf(Student s) {
        if (s instanceof DomesticStudent d) {
            return d.isScholarship() ? "Scholarship" : "No scholarship";
        }
        InternationalStudent i = (InternationalStudent) s;
        return i.getCountry() + ", visa until " + i.getVisaExpiry();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
