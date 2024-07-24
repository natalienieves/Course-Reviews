package edu.virginia.cs;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;




public class UserInput {
    public static final String STATE_DATABASE_PATH = "Reviews.sqlite3";
    private Connection connection;


    public void connect() {
        try {
            if (connected()) {
                throw new IllegalStateException("already connected");
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" +
                    STATE_DATABASE_PATH);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean connected() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return true;
        }
        return false;
    }

    public void createTables() {
        try {
            if (connection.isClosed()) {
                throw new IllegalStateException("Connection is closed right.");
            }

            Statement statement = connection.createStatement();
            String table1 = "CREATE TABLE Courses (\n" +
                    "ID_NUMBER             INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                    "NOT NULL,\n" +
                    "DEPARTMENT            TEXT    NOT NULL,\n" +
                    "COURSE_CATALOG_NUMBER INTEGER NOT NULL\n" +
                    ");";


            statement.executeUpdate(table1);
            statement.close();

            Statement statement2 = connection.createStatement();
            String table2 =
                    "CREATE TABLE Student(\n" +
                            "ID_NUMBER             INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                            "NOT NULL,\n" +
                            "USER_NAME            TEXT    NOT NULL,\n" +
                            "UNIQUE,\n" +
                            "PASSWORD TEXT NOT NULL\n" +
                            ");";
            statement2.executeUpdate(table2);
            statement2.close();

            Statement statement3 = connection.createStatement();
            String table3 =
                    "CREATE TABLE Reviews (\n" +
                            "ID_NUMBER    INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                            "NOT NULL,\n" +
                            "Student_ID   REFERENCES Student (ID_NUMBER) ON DELETE CASCADE\n" +
                            "NOT NULL,\n" +
                            "Course_ID    REFERENCES Courses (ID_NUMBER) ON DELETE CASCADE\n" +
                            "NOT NULL,\n" +
                            "Text_message TEXT    NOT NULL,\n" +
                            "Rating       INTEGER NOT NULL,\n" +
                            "CONSTRAINT [check] CHECK ( (Rating <= 5 & Rating >= 0) )" +
                            ")";

            statement3.executeUpdate(table3);
            statement3.close();


        } catch (SQLException e) {

        }
    }

    public boolean login() {
        try {
            if(connection.isClosed()){
                throw new IllegalStateException("Connection is Closed");
            }
            Statement statement = connection.createStatement();
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter your username: ");
            String username = scan.nextLine();
            System.out.println("Enter your password: ");
            String password = scan.nextLine();
            String sql = "select count(*) from Student where USER_NAME=" + username + "and where PASSWORD=" + password;
            ResultSet rs = statement.executeQuery(sql);
            if (rs.getInt(1) == 1) {
                return true;
            }
            else {
                Statement statement2 = connection.createStatement();
                System.out.println("Create and Enter new username: ");
                String new_username = scan.nextLine();
                System.out.println("Create and Enter new password: ");
                String new_password = scan.nextLine();
                System.out.println("Confirm Password: ");
                String confirm_password = scan.nextLine();
                if (new_password == confirm_password) {
                    String insertquery = String.format("""
                       insert into Student (USER_NAME, PASSWORD) 
                       values ("%s","%s")
                       """, new_username,confirm_password);
                    statement2.executeUpdate(insertquery);
                    statement2.close();
                    return true;
                } else if (new_password != confirm_password) {
                    login();
                }

            }
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
        return false;
    }


}


