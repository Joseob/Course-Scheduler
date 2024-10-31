/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
/**
 *
 * @author Joseob
 */
public class ScheduleQueries {
    
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester,coursecode,studentid,status,timestamp) values (?,?,?,?,?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentId());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
   public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleByStudent = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select * from app.schedule where semester = ? and studentid = ?");
            getScheduleByStudent.setString(1,semester);
            getScheduleByStudent.setString(2,studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
                String courseCode = resultSet.getString("coursecode");
                String status = resultSet.getString("status");
                Timestamp timeStamp = resultSet.getTimestamp("timestamp");
                
                ScheduleEntry schedule = new ScheduleEntry(semester, courseCode, studentID, status, timeStamp);
                scheduleByStudent.add(schedule); 
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduleByStudent;
        
    }
    public static int getScheduledStudentCount(String semester, String courseCode)
    {
        int studentCount = 0;
        connection = DBConnection.getConnection();
        
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
            getScheduledStudentCount.setString(1, semester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            while(resultSet.next()){
            studentCount = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentCount;
        
    }
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode) 
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse = new ArrayList<ScheduleEntry>();
        try
        {
            getWaitlistedStudentsByClass = connection.prepareStatement("select * from app.schedule where semester = ? and coursecode = ? and status = ? ORDER BY timestamp");
            getWaitlistedStudentsByClass.setString(1,semester);
            getWaitlistedStudentsByClass.setString(2,courseCode);
            getWaitlistedStudentsByClass.setString(3,"W");
            resultSet = getWaitlistedStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {
                String studentID = resultSet.getString("studentId");
                String status = resultSet.getString("status");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                
                ScheduleEntry schedule = new ScheduleEntry(semester, courseCode, studentID, status, timestamp);
                getWaitlistedStudentsByCourse.add(schedule); 
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return getWaitlistedStudentsByCourse;  
    }
    
        public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and studentid = ? and coursecode = ?");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
        
        public static void updateScheduleEntry(ScheduleEntry entry) 
    {
        connection = DBConnection.getConnection();
        try
        {
            updateScheduleEntry = connection.prepareStatement("update app.schedule set semester = ?, coursecode = ?, studentid = ?, status = ?, timestamp = ? where semester = ? and coursecode = ? and studentid = ?");
            updateScheduleEntry.setString(1, entry.getSemester());
            updateScheduleEntry.setString(2, entry.getCourseCode());
            updateScheduleEntry.setString(3, entry.getStudentId());
            updateScheduleEntry.setString(4, "S");
            updateScheduleEntry.setTimestamp(5, entry.getTimestamp());
            updateScheduleEntry.setString(6, entry.getSemester());
            updateScheduleEntry.setString(7, entry.getCourseCode());
            updateScheduleEntry.setString(8, entry.getStudentId());
            updateScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    public static void dropScheduleByCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and studentid = ? and coursecode = ?");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(3, courseCode);
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }  
    }
}