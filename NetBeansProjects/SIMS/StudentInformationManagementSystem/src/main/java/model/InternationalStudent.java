/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class InternationalStudent extends Student{
    private String country;
    private String visaExpiry;

    public InternationalStudent() {
    }

    public InternationalStudent(String id, String name,
                                String email,
                                String phone,
                                String programme,
                                int year,
                                String country,
                                String visaExpiry) {
        super(id, name, email, phone, programme, year);
        
        this.country = country;
        this.visaExpiry = visaExpiry;
        
    }

    public String getCountry() {
        return country;
    }

    public String getVisaExpiry() {
        return visaExpiry;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setVisaExpiry(String visaExpiry) {
        this.visaExpiry = visaExpiry;
    }
    
    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Student Type: International");
        System.out.println("Country: " + country +
            "\nVisaExpiry: " + visaExpiry);
    }
    
}
