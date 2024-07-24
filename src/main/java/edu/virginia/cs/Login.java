package edu.virginia.cs;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class Login {
    private static Session session;
    static Scanner in = new Scanner(System.in);

    private static Course course = new Course();
    private static Student student = new Student();
    private static Reviews reviews = new Reviews();
    protected static String UserName;
    //private static MainMenu mainMenu = new MainMenu();

    public static Login LoginRunner (){
        Scanner in = new Scanner(System.in);
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        System.out.println("Hello! Welcome to Course Reviews!!!\nIf you already have an account type --login\nIf you don't type --create account");
        String userReply = in.nextLine();
        try{
            if(userReply.equalsIgnoreCase("login")){
                try {
                    System.out.println("Please enter your username: ");
                    UserName = in.nextLine();
                    if(!containsUsername(UserName)){
                        throw new PersistenceException("User name does not exist");

                    }
                }catch(IllegalArgumentException|PersistenceException e){
                    System.out.println("User name does not exist");
                    return LoginRunner();
                }
                try{
                    System.out.println("Please enter your password: ");
                    String UserPassword = in.nextLine();
                    if (!containsPassword(UserPassword)){
                        throw new PersistenceException("Incorrect password");
                    }
                }catch (PersistenceException | IllegalArgumentException e){
                    System.out.println("Incorrect Password");
                    return LoginRunner();

                }
                session.close();
                MainMenu.MainMenuRunner();




            }
            if(userReply.equalsIgnoreCase("create account")){
                try {
                    System.out.println("Enter desired user name: ");
                    String UserName = in.nextLine();
                    try{
                    if (containsUsername(UserName)) {
                        throw new PersistenceException("This username already exists, please login");

                    }}
                    catch (PersistenceException e){
                        System.out.println("This username already exists, please login");
                        return LoginRunner();
                    }
                    if (!containsUsername(UserName)) {
                        System.out.println("Please enter your desired password (PLEASE MAKE IT GOOD): ");
                        String UserPassword = in.nextLine();
                        System.out.println("Please confirm password ");
                        String confirmPassword = in.nextLine();
                        if (!UserPassword.equals(confirmPassword)) {
                            throw new PersistenceException("Passwords do not match");
                        }
                        session.getTransaction().commit();
                        if (UserPassword.equals(confirmPassword)) {
                            session.beginTransaction();
                            System.out.println("Login Successful!");
                            student.setUSER_NAME(UserName);
                            student.setPASSWORD(confirmPassword);
                            session.persist(student);
                            session.getTransaction().commit();
                            session.close();
                            MainMenu.MainMenuRunner();
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                }
                catch(PersistenceException | IllegalArgumentException e){
                    System.out.println("Passwords do not match");
                    return LoginRunner();
                }

            }
        }catch (IllegalArgumentException e){
            System.out.println("Not a valid response");
            System.exit(0);
        }
        session.close();
        return null;//System.exit(0);
    }


    public static Student getUser_name(String USER_NAME){
        String hql = "SELECT e FROM Student e WHERE e.USER_NAME =:USER_NAME ";
        TypedQuery<Student> studentQuery = MainMenu.session.createQuery(hql, Student.class);
        studentQuery.setParameter("USER_NAME", USER_NAME);
        Student student = studentQuery.getSingleResult();
        return student;
    }
    public static boolean containsUsername(String USER_NAME) {
        String hql = "SELECT e FROM Student e WHERE e.USER_NAME =:USER_NAME ";
        TypedQuery<Student> studentQuery = Login.session.createQuery(hql, Student.class);
        studentQuery.setParameter("USER_NAME", USER_NAME);
        List list = studentQuery.getResultList();
        if(list.size() > 0){
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean containsPassword(String PASSWORD){
        String hql = "SELECT e FROM Student e WHERE e.PASSWORD =:PASSWORD ";
        TypedQuery<Student> studentQuery = Login.session.createQuery(hql, Student.class);
        studentQuery.setParameter("PASSWORD", PASSWORD);
        //Student student = studentQuery.getSingleResult();
        List list = studentQuery.getResultList();
        if(list.size() > 0){
            return true;
        }
        else{
            return false;
        }


    }
}





