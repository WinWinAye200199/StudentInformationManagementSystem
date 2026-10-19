/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Student extends Person {

    private String programme;
    private int year;

    public Student() {

    }

    public Student(String id, String name, String email, String phone,
            String programme, int year) {
        super(id, name, email, phone);
        this.programme = programme;
        this.year = year;
    }

    public String getProgramme() {
        return programme;
    }

    public int getYear() {
        return year;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public void displayInfo() {
        System.out.println(
                "ID: " + getId()
                + "\nName: " + getName()
                + "\nEmail: " + getEmail()
                + "\nPhone: " + getPhone()
                + "\nProgramme: " + programme
                + "\nYear: " + year
        );
    }

    @Override
    public String toString() {
        return getId() + " - " + getName();   // shown in dropdowns, e.g. "S001 - Win Win Aye"
    }
}
