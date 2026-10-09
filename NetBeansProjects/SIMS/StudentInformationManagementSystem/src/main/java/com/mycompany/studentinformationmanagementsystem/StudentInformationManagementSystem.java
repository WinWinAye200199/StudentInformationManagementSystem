/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.studentinformationmanagementsystem;

import database.DatabaseInitializer;
import java.sql.SQLException;
import database.DatabaseConnection;
//import ui.Menu;

public class StudentInformationManagementSystem {

    public static void main(String[] args) {
        
//        Menu menu = new Menu();
//
//        menu.start();
          try{
              DatabaseInitializer.initialise();
              System.out.println("Derby connected. Tables ready.");
          } catch (SQLException e){
              System.err.println("Database error: " + e.getMessage());
          } finally {
              DatabaseConnection.getInstance().shutdown();
          }
    }
}
