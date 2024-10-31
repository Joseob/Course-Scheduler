/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author acv
 */
public class MultiTableQueries {
    private static Connection connection;
    private static PreparedStatement getAllClassDescriptions;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static ResultSet resultSet;
    
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester)
    {
        connection = DBConnection.getConnection();
        
        ArrayList<ClassDescription> description = new ArrayList<ClassDescription>();
        
        try
        {
            getAllClassDescriptions = connection.prepareStatement("select app.class.courseCode, description, seats from app.class, app.course where semester = ? and app.class.courseCode = app.course.courseCode order by app.class.courseCode\n" +
"");
            
            getAllClassDescriptions.setString(1, semester);
            resultSet = getAllClassDescriptions.executeQuery();
            
            while(resultSet.next())
            {
                description.add(new ClassDescription(resultSet.getString("courseCode"),resultSet.getString("description"),resultSet.getInt("seats")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return description;
        
    }
    
        public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode) 
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> scheduledStudents = new ArrayList<StudentEntry>();
        try
        {
            getScheduledStudentsByClass = connection.prepareStatement("SELECT app.schedule.studentId, app.student.lastName, app.student.firstName FROM app.student INNER JOIN app.schedule ON app.student.studentId = app.schedule.studentId WHERE semester = ? and courseCode = ? AND status = ?");
            getScheduledStudentsByClass.setString(1,semester);
            getScheduledStudentsByClass.setString(2,courseCode);
            getScheduledStudentsByClass.setString(3,"S");
            resultSet = getScheduledStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {
                scheduledStudents.add(new StudentEntry(resultSet.getString("studentID"),resultSet.getString("firstName"),resultSet.getString("lastName")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduledStudents;  
    }
        
        public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode) 
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> waitlistedStudents = new ArrayList<StudentEntry>();
        try
        {
            getScheduledStudentsByClass = connection.prepareStatement("SELECT app.schedule.timestamp, app.schedule.studentId, app.student.lastName, app.student.firstName FROM app.student INNER JOIN app.schedule ON app.student.studentId = app.schedule.studentId WHERE semester = ? and courseCode = ? AND status = ? ORDER BY timestamp");
            getScheduledStudentsByClass.setString(1,semester);
            getScheduledStudentsByClass.setString(2,courseCode);
            getScheduledStudentsByClass.setString(3,"W");
            resultSet = getScheduledStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {
                waitlistedStudents.add(new StudentEntry(resultSet.getString("studentID"),resultSet.getString("firstName"),resultSet.getString("lastName")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlistedStudents;
}
}