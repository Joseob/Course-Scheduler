/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author joseob
 */
public class ClassEntry {
    private String semester;
    private String courseCode;
    private Integer seats;

    public ClassEntry(String semester, String courseCode, Integer seats) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.seats = seats;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public Integer getSeats() {
        return seats;
    }
   
}
