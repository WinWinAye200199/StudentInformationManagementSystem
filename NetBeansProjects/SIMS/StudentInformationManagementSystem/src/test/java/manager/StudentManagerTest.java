/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentManagerTest {

    @Test
    public void testAddStudent() {

        StudentManager manager = new StudentManager();

        Student student = new Student(
                "S001",
                "Win",
                "win@gmail.com",
                "0212345678",
                "Computing",
                2
        );

        boolean result = manager.addStudent(student);

        assertTrue(result);
        assertNotNull(manager.searchStudent("S001"));
    }


    @Test
    public void testDuplicateStudentId() {

        StudentManager manager = new StudentManager();

        Student student1 = new Student(
                "S001",
                "Win",
                "win@gmail.com",
                "0212345678",
                "Computing",
                2
        );

        Student student2 = new Student(
                "S001",
                "John",
                "john@gmail.com",
                "0215555555",
                "Business",
                1
        );

        assertTrue(manager.addStudent(student1));

        assertFalse(manager.addStudent(student2));
    }


    @Test
    public void testSearchStudent() {

        StudentManager manager = new StudentManager();

        Student student = new Student(
                "S001",
                "Win",
                "win@gmail.com",
                "0212345678",
                "Computing",
                2
        );

        manager.addStudent(student);

        Student result =
                manager.searchStudent("S001");

        assertNotNull(result);
        assertEquals("Win", result.getName());
    }


    @Test
    public void testDeleteStudent() {

        StudentManager manager = new StudentManager();

        Student student = new Student(
                "S001",
                "Win",
                "win@gmail.com",
                "0212345678",
                "Computing",
                2
        );

        manager.addStudent(student);

        assertTrue(manager.deleteStudent("S001"));

        assertNull(manager.searchStudent("S001"));
    }
}