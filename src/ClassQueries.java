/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author acv
 */
public class ClassQueries {
    private static Connection connection;
    private static PreparedStatement addClass;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getClassSeats;
    private static PreparedStatement dropClass;
    private static ResultSet resultSet;
    
    public static void addClass(ClassEntry classes)
    {
        connection = DBConnection.getConnection();
        try
        {
            addClass = connection.prepareStatement("insert into app.class (semester, courseCode, seats) values (?,?,?)");
            addClass.setString(1, classes.getSemester());
            addClass.setString(2, classes.getCourseCode());
            int seats = classes.getSeats();
            addClass.setInt(3, seats);
            addClass.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester)
    {
        connection = DBConnection.getConnection();
        
        ArrayList<String> courseCodes = new ArrayList<String>();
        
        try
        {
            getAllCourseCodes = connection.prepareStatement("select courseCode from app.class where semester = ? order by courseCode");
            
            getAllCourseCodes.setString(1, semester);
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                courseCodes.add(resultSet.getString("courseCode"));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
        
    }
        
    public static int getClassSeats (String semester, String courseCode)
            
    {
        connection = DBConnection.getConnection();
        
        int seats = 0;
        try
        {
            
            getClassSeats = connection.prepareStatement("select seats from app.class where semester = ? and coursecode = ? ");
            getClassSeats.setString(1, semester);
            getClassSeats.setString(2, courseCode);
            resultSet = getClassSeats.executeQuery();
            while(resultSet.next()){
            seats =  resultSet.getInt("seats");   
            }
          
            
        }
        catch(SQLException sqlException)
        {
            
            sqlException.printStackTrace();
        }
        return seats;
           
        
    }
    public static void dropClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropClass = connection.prepareStatement("delete from app.class where semester = ? and coursecode = ?");
            dropClass.setString(1, semester);
            dropClass.setString(2, courseCode);
            dropClass.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}