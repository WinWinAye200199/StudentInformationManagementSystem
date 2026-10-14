/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.studentinformationmanagementsystem;

import dao.StudentDAO;
import database.DatabaseInitializer;
import java.sql.SQLException;
import database.DatabaseConnection;
import model.DomesticStudent;
import model.InternationalStudent;
import model.Student;
//import ui.Menu;

public class StudentInformationManagementSystem {

    public static void main(String[] args) {
        
//        Menu menu = new Menu();
//
//        menu.start();
          try{
              DatabaseInitializer.initialise();
              StudentDAO dao = new StudentDAO();
              
              dao.deleteStudent("S001");
              dao.deleteStudent("S002");
              
              dao.addStudent(new DomesticStudent("S001", "Win Win Aye", "win@email.com",
              "0211234567", "Computer Science", 2, true));
              dao.addStudent(new InternationalStudent("S002", "John Lee", "john@email.com",
              "0217654321", "IT", 1, "Myanmar", "2027-12-31"));
              
              for (Student s : dao.getAllStudents()){
                  s.displayInfo();
                  System.out.println("------");
              }
              System.out.println("Derby connected. Tables ready.");
          } catch (SQLException e){
              System.err.println("Database error: " + e.getMessage());
          } finally {
              DatabaseConnection.getInstance().shutdown();
          }
    }
}
