
package edu.virginia.cs;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    static Session session;


    private static Course course = new Course();
    private static Student student = new Student();
    private static Reviews reviews = new Reviews();
    private static Login login = new Login();
    public static MainMenu MainMenuRunner (){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        try {
            System.out.println("Would you like to submit a review, or see reviews for a course?\n\n" + "\tIf you want to submit a review type -- submit review\n\tIf you want to see reviews for a course type --course reviews\n\tIf you would like to log out type --logout");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            if (input.equalsIgnoreCase("submit review")) {
                try {

                    //Get Course for Review from User
                    System.out.println("Please type in the subject and catalog number ex -- CS 3140");
                    new ArrayList<String>().toArray(new String[0]);
                    String[] values;
                    values = (in.nextLine().split(" "));
                    String UserCourse = values[0];
                    UserCourse= UserCourse.toUpperCase();
                    String UserNumber = values[1];
                    int CourseNumber = Integer.parseInt(UserNumber);
                    //Check Course Name
                    if(UserCourse.length() > 4 || UserCourse.length() <= 0){
                        System.out.println("This is NOT a valid Course Name\n");
                        return MainMenuRunner();
                    }

                    //Check Course Number
                    if(UserNumber.length() != 4){
                        System.out.println("This is NOT a valid Course Catalog Number\n");
                        return MainMenuRunner();
                    }
                    if(!containsCourse(CourseNumber)) {
                        session.getTransaction().commit();
                        session.beginTransaction();
                        course.setDEPARTMENT(UserCourse);
                        course.setCOURSE_CATALOG_NUMBER(CourseNumber);
                        session.persist(course);
                        session.getTransaction().commit();
                    }
                    // Get review and rating for review
                    System.out.println("Please type in your review for " + UserCourse + CourseNumber + ".");
                    String userReply = in.nextLine();
                    if(userReply.isBlank()){
                        while(userReply.isBlank()){
                            System.out.println("Please type in your review for " + UserCourse + CourseNumber + ".");
                            String UserReply = in.nextLine();
                            userReply = UserReply;
                        }
                    }

                    System.out.println("Please type in your Rating (1-5) for " + UserCourse + CourseNumber + ".");
                    int UserRating = in.nextInt();

                    //If UserRating is insufficient, keeps prompting
                    while(UserRating > 5 || UserRating <= 0){
                        if(UserRating > 5){
                            System.out.println("WOW you must really like the course, but please read the instructions, leave an amazing message instead!");
                        }
                        if(UserRating <= 0){
                            System.out.println("WOW you must REALLY hate this course, maybe if you read instructions you may have liked the class better,\nprobably why you didn't do well (oop)\n");
                        }
                        System.out.println("Please type in your Rating (1-5) for " + UserCourse + CourseNumber + ".");
                        int UserRatingAtemptX = in.nextInt();
                        UserRating = UserRatingAtemptX;
                    }
                    session.getTransaction().commit();
                    //Construct Review
                    //reviews.setStudent_ID(Login.getUser_name(Login.UserName).getID_NUMBER());
                    Course courseID =getCourseID(UserCourse,CourseNumber);
                  //  int courseIdNumber = courseID.getID_NUMBER();
                    //Add Review to Database
                    session.beginTransaction();
                    String student_username = Login.UserName;
                    Student student1 = Login.getUser_name(student_username);
                    session.getTransaction().commit();
                    session.beginTransaction();
                    reviews.setStudent_ID(student1);
                    reviews.setCourse_ID(courseID);
                    //String student_username = Login.getUser_name(USER_NAME);
                    reviews.setText_message(userReply);
                    reviews.setRating(UserRating);
                    session.persist(reviews);
                    session.getTransaction().commit();
                    session.close();
                    return MainMenu.MainMenuRunner();

                }catch (HibernateException e){
                    System.out.println("Review already submitted for this course");
                    return MainMenuRunner();

                }
            }




            if (input.equalsIgnoreCase("course reviews")) {
                try {
                    System.out.println("Please type in the subject and catalog number ex -- CS 3140");
                    new ArrayList<String>().toArray(new String[0]);
                    String[] values;
                    values = (in.nextLine().split(" "));
                    String UserCourse = values[0];
                    UserCourse = UserCourse.toUpperCase();
                    int CourseNumber = Integer.parseInt(values[1]);
                    String UserNumber = values[1];

                    //Check Course Name
                    if(UserCourse.length() > 4 || UserCourse.length() <= 0){
                        System.out.println("This is NOT a valid Course Name\n");
                        return MainMenuRunner();
                    }

                    //Check Course Number
                    if(UserNumber.length() != 4){
                        System.out.println("This is NOT a valid Course Catalog Number\n");
                        return MainMenuRunner();
                    }
                    Course course1 = getCourseID(UserCourse,CourseNumber);
                    try{


                        if(getReviews(course1).isEmpty()){
                            System.out.println("Course does not have any reviews available\n");
                            return MainMenuRunner();
                        }
                        else{

                            System.out.println(getReviews(course1));
                            return MainMenuRunner();
                        }

                    }catch(PersistenceException e){
                        System.out.println("Course does not have any reviews available\n");
                        return MainMenuRunner();
                    }
                    //System.exit(0);
                }catch (PersistenceException e){
                    System.out.println("Not a valid course bruv  ");
                    //return userInput;
                }
            }
            if(input.equalsIgnoreCase("logout")){
                System.out.println("Logging out");
                Logout.LogoutRunner();
            }

            else{
                throw new IllegalArgumentException();
            }
        }catch(IllegalArgumentException e){
            System.out.println("Not a valid response");
            //return userInput.login();
        }
        session.close();

        return null;
    }
    public static boolean containsCourse(int COURSE_CATALOG_NUMBER) {
        String hql = "SELECT e FROM Course e WHERE e.COURSE_CATALOG_NUMBER =: COURSE_CATALOG_NUMBER";
        TypedQuery<Course> courseQuery = MainMenu.session.createQuery(hql, Course.class);
        courseQuery.setParameter("COURSE_CATALOG_NUMBER", COURSE_CATALOG_NUMBER);
        List list = courseQuery.getResultList();
        if(list.size() > 0){
            return true;
        }
        else {
            return false;
        }
    }
    private static Course getCourseID(String DEPARTMENT, int COURSE_CATALOG_NUMBER) {
        String hql = "SELECT e FROM Course e WHERE e.DEPARTMENT =: DEPARTMENT AND e.COURSE_CATALOG_NUMBER =: COURSE_CATALOG_NUMBER";
        TypedQuery<Course> courseQuery = MainMenu.session.createQuery(hql, Course.class);
        courseQuery.setParameter("DEPARTMENT", DEPARTMENT);
        courseQuery.setParameter("COURSE_CATALOG_NUMBER", COURSE_CATALOG_NUMBER);
        List list = courseQuery.getResultList();
        Course course1 = courseQuery.getSingleResult();
        return course1;

    }
    private static List<Reviews> getReviews(Course course) {
        String hql = "SELECT e FROM Reviews e WHERE e.course = :ID_NUMBER";
        TypedQuery<Reviews> reviewsQuery = MainMenu.session.createQuery(hql, Reviews.class);
        reviewsQuery.setParameter("ID_NUMBER", course);
        return reviewsQuery.getResultList();
    }



}
