package com.example;
import java.sql.*;

public class Main {

    static String url = "jdbc:postgresql://localhost:5432/A3";
    static String user = "postgres";
    static String password = "admin";

    //connect function
    public static Connection Connect() throws Exception{
        return DriverManager.getConnection(url, user, password);
    }

    //disconnect function
    public static void Disconnect(Connection connection){
        try{
            if (connection != null)
                connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    //function to print all students
    public static void getAllStudents(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        //connect and execute query
        try{
            connection = Connect();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM students");

            while (resultSet.next()){
                System.out.println(resultSet.getInt("student_id") + "\t" + resultSet.getString("first_name") + "\t" +resultSet.getString("last_name") + "\t" +resultSet.getString("email") + "\t" +resultSet.getDate("enrollment_date"));
            }
        } catch (Exception e){
            System.out.println(e);
        }

        //close and disconnect
        try {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            Disconnect(connection);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static void addStudent(String first_name, String last_name, String email, Date enrollment_date){
        Connection connection = null;
        Statement statement = null;

        //connect and execute query
        try{
            connection = Connect();
            statement = connection.createStatement();
            statement.executeQuery("INSERT INTO students (first_name, last_name, email, enrollment_date) " +"VALUES ('" + first_name + "', '" + last_name + "', '" + email + "', '" + enrollment_date + "')");
        } catch (Exception e){
            System.out.println(e);
        }

        //close and disconnect
        try{
            if (statement != null)
                statement.close();
            Disconnect(connection);
        } catch (Exception e){
            System.out.println(e);
        }

    }

    public static void updateStudentEmail (int student_id, String new_email){
        Connection connection = null;
        PreparedStatement preStatement = null;

        //connect and execute query
        try{
            connection = Connect();
            preStatement = connection.prepareStatement("UPDATE students SET email = ? WHERE student_id = ?");
            preStatement.setString(1, new_email);
            preStatement.setInt(2, student_id);
            preStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

        //close and disconnect
        try{
            if (preStatement != null)
                preStatement.close();
            Disconnect(connection);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static void deleteStudent(int student_id){
        Connection connection = null;
        PreparedStatement preStatement = null;

        //connect and execute query
        try{
            connection = Connect();
            preStatement = connection.prepareStatement("DELETE FROM students WHERE student_id = ?");
            preStatement.setInt(1, student_id);
            preStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

        //close and disconnect
        try{
            if (preStatement != null)
                preStatement.close();
            Disconnect(connection);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        getAllStudents();
        addStudent("Aidan", "Chow", "aidan.chow@example.com", Date.valueOf("2024-03-18"));
        System.out.println("After addStudent():");
        getAllStudents();
        updateStudentEmail(1, "update.email@example.com");
        System.out.println("After updateStudentEmail()");
        getAllStudents();
        deleteStudent(4);
        System.out.println("After deleteStudent()");
        getAllStudents();
    }
}
