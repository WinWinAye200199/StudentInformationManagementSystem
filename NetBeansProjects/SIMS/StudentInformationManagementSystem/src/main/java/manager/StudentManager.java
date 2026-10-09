/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import java.util.ArrayList;
import java.util.HashMap;
import model.DomesticStudent;
import model.InternationalStudent;
import model.Student;

public class StudentManager {

    private ArrayList<Student> students;

    private HashMap<String, Student> studentMap;

    private HashMap<String, DomesticStudent> domesticStuMap;

    private HashMap<String, InternationalStudent> internationalStuMap;

    public StudentManager() {

        students = new ArrayList<>();
        studentMap = new HashMap<>();
        domesticStuMap = new HashMap<>();
        internationalStuMap = new HashMap<>();

    }

    public boolean addStudent(Student student) {

        if (studentMap.containsKey(student.getId())) {
            return false;
        }

        students.add(student);

        studentMap.put(
                student.getId(),
                student
        );

        if (student instanceof DomesticStudent) {
            DomesticStudent domesticStudent
                    = (DomesticStudent) student;

            domesticStuMap.put(
                    student.getId(),
                    domesticStudent
            );
        }

        if (student instanceof InternationalStudent) {
            InternationalStudent internationalStudent
                    = (InternationalStudent) student;

            internationalStuMap.put(
                    student.getId(),
                    internationalStudent
            );
        }

        return true;
    }

    public void viewStudents() {

        if (students.isEmpty()) {

            System.out.println("No students found.");

            return;
        }

        for (Student student : students) {

            student.displayInfo();

            System.out.println("******************");

        }

    }
    
    public Student searchStudent(String id) {

        return studentMap.get(id);

    }

    public boolean deleteStudent(String id) {

        Student student = studentMap.get(id);

        if (student == null) {
            return false;
        }

        students.remove(student);
        studentMap.remove(id);

        if (student instanceof DomesticStudent) {
            domesticStuMap.remove(id);
        }

        if (student instanceof InternationalStudent) {
            internationalStuMap.remove(id);
        }

        return true;
    }

    public boolean updateName(String id, String newName) {

        Student student = studentMap.get(id);

        if (student == null) {

            return false;

        }

        student.setName(newName);

        return true;
    }

    public boolean updateEmail(String id, String newEmail) {

        Student student = studentMap.get(id);

        if (student == null) {

            return false;

        }

        student.setEmail(newEmail);

        return true;

    }

    public boolean updatePhone(String id, String newPhone) {

        Student student = studentMap.get(id);

        if (student == null) {

            return false;

        }

        student.setPhone(newPhone);

        return true;

    }

    public boolean updateScholarship(String id, boolean newScholarship) {

        DomesticStudent domesticStu = domesticStuMap.get(id);

        if (domesticStu == null) {

            return false;

        }

        domesticStu.setScholarship(newScholarship);

        return true;
    }

    public boolean updateCountry(String id, String newCountry) {

        InternationalStudent internationalStu = internationalStuMap.get(id);

        if (internationalStu == null) {

            return false;

        }

        internationalStu.setCountry(newCountry);

        return true;
    }

    public boolean updateVisaExpiry(String id, String newVisaExpiry) {

        InternationalStudent internationalStu = internationalStuMap.get(id);

        if (internationalStu == null) {

            return false;

        }

        internationalStu.setVisaExpiry(newVisaExpiry);

        return true;
    }

    public ArrayList<Student> getStudents() {

        return students;

    }

}
