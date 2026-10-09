/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class DomesticStudent extends Student{
    private boolean scholarship;
    
    public DomesticStudent(){
        
    }
    
    public DomesticStudent(String id, String name,
                           String email,
                           String phone,
                           String programme,
                           int year,
                           boolean scholarship) {

        super(id, name, email, phone, programme, year);

        this.scholarship = scholarship;
    }

    public boolean isScholarship() {
        return scholarship;
    }
    
    public void setScholarship(boolean scholarship){
        this.scholarship = scholarship;
    }

    @Override
    public void displayInfo() {
        
       super.displayInfo();
       System.out.println("Student Type: Domestic");
       System.out.println(
            "Scholarship: "+ scholarship
        );
    }
    
}
