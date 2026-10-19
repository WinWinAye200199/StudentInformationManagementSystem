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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import model.Course;
import model.DomesticStudent;
import model.Enrollment;
import model.Student;
import service.CourseService;
import service.EnrollmentService;
import service.ServiceException;
import service.StudentService;

public class EnrollmentPanel extends JPanel {

    private static final String[] SEMESTERS = {"2026 S1", "2026 S2", "2027 S1", "2027 S2"};

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    private final JComboBox<Student> studentBox = new JComboBox<>();
    private final JComboBox<Course> courseBox = new JComboBox<>();
    private final JComboBox<String> semesterBox = new JComboBox<>(SEMESTERS);

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"Student ID", "Student Name", "Course Code", "Course Name", "Credits", "Semester"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;   
        }
    };
    private final JTable table = new JTable(tableModel);
    private List<Enrollment> currentEnrollments;

    public EnrollmentPanel() {
        this(new EnrollmentService(), new StudentService(), new CourseService());
    }

    public EnrollmentPanel(EnrollmentService enrollmentService,
            StudentService studentService,
            CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buildTable(), BorderLayout.CENTER);
        add(buildFormScroll(), BorderLayout.EAST);

        refreshAll();

        // Reload every time the tab is opened, so new students/courses appear in the dropdowns
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refreshAll();
            }
        });
    }

    // ---------- Layout ----------
    private JScrollPane buildTable() {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        // Horizontal scroll: keep each column at a readable width
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int[] widths = {90, 160, 110, 260, 70, 100};
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

        studentBox.setPrototypeDisplayValue(
                new DomesticStudent("S0000", "Long Student Name", "", "", "", 1, false));
        courseBox.setPrototypeDisplayValue(new Course("COMP0000", "Long Course Name Here", 0));

        // Semester: the user can type any value, or pick one of the suggestions
        semesterBox.setEditable(true);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Enrollment details"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        row = addRow(form, c, row, "Student:", studentBox);
        row = addRow(form, c, row, "Course:", courseBox);
        row = addRow(form, c, row, "Semester:", semesterBox);

        JButton enrolButton = new JButton("Enrol");
        JButton removeButton = new JButton("Remove");
        JButton clearButton = new JButton("Clear");
        enrolButton.addActionListener(e -> enrolStudent());
        removeButton.addActionListener(e -> removeSelected());
        clearButton.addActionListener(e -> clearForm());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        buttons.add(enrolButton);
        buttons.add(removeButton);
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
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 1;
        form.add(new JLabel(label), c);
        c.gridx = 1;
        form.add(field, c);
        return row + 1;
    }

    // ---------- Loading data ----------
    private void refreshAll() {
        loadStudents();
        loadCourses();
        loadEnrollments();
        clearForm();
    }

    // Load all students from the database into the dropdown
    private void loadStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            studentBox.setModel(new DefaultComboBoxModel<>(students.toArray(new Student[0])));
        } catch (ServiceException ex) {
            showError(ex.getMessage());
        }
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

    private void loadEnrollments() {
        try {
            currentEnrollments = enrollmentService.getAllEnrollments();
            tableModel.setRowCount(0);
            for (Enrollment en : currentEnrollments) {
                tableModel.addRow(new Object[]{
                    en.getStudent().getId(), en.getStudent().getName(),
                    en.getCourse().getCourseCode(), en.getCourse().getCourseName(),
                    en.getCourse().getCredits(), en.getSemester()
                });
            }
        } catch (ServiceException ex) {
            showError(ex.getMessage());
        }
    }

    // ---------- Actions ----------
    private void enrolStudent() {
        Student student = (Student) studentBox.getSelectedItem();
        if (student == null) {
            showError("Please choose a student.");
            return;
        }

        Course selected = (Course) courseBox.getSelectedItem();
        if (selected == null) {
            showError("Please choose a course.");
            return;
        }
        String courseCode = selected.getCourseCode();   // e.g. "COMP603"

        // Read what is typed in the box (works for both typed and chosen values)
        Object typed = semesterBox.getEditor().getItem();
        String semester = (typed == null) ? "" : typed.toString().trim();
        if (semester.isEmpty()) {
            showError("Please enter a semester (e.g. 2026 S2).");
            return;
        }

        try {
            enrollmentService.enrollStudent(student.getId(), courseCode, semester);
            showInfo(student.getName() + " has been enrolled in " + courseCode + ".");
            loadEnrollments();
        } catch (ServiceException ex) {
            showError(ex.getMessage());   // e.g. "Student S001 is already enrolled in COMP603."
        }
    }

    private void removeSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showError("Please select an enrollment in the table first.");
            return;
        }
        Enrollment en = currentEnrollments.get(row);
        String studentId = en.getStudent().getId();
        String courseCode = en.getCourse().getCourseCode();

        int choice = JOptionPane.showConfirmDialog(this,
                "Remove " + studentId + " from " + courseCode + "?",
                "Confirm remove", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            enrollmentService.removeEnrollment(studentId, courseCode);
            showInfo("Enrollment removed.");
            clearForm();
            loadEnrollments();
        } catch (ServiceException ex) {
            showError(ex.getMessage());
        }
    }

    // ---------- Form helpers ----------
    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0 || currentEnrollments == null || row >= currentEnrollments.size()) {
            return;
        }
        Enrollment en = currentEnrollments.get(row);

        for (int i = 0; i < studentBox.getItemCount(); i++) {
            if (studentBox.getItemAt(i).getId().equals(en.getStudent().getId())) {
                studentBox.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < courseBox.getItemCount(); i++) {
            if (courseBox.getItemAt(i).getCourseCode().equals(en.getCourse().getCourseCode())) {
                courseBox.setSelectedIndex(i);
                break;
            }
        }
        semesterBox.setSelectedItem(en.getSemester());
    }

    private void clearForm() {
        table.clearSelection();
        studentBox.setSelectedIndex(-1);
        courseBox.setSelectedIndex(-1);
        semesterBox.setSelectedIndex(-1);
        semesterBox.getEditor().setItem("");   // also clear any typed text
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

}
