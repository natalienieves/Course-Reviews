package edu.virginia.cs;

import javax.persistence.*;


@Entity
@Table(name = "Reviews")
public class Reviews {
    // Natalie built this class
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.AUTO) //
    @Column (name = "ID_NUMBER") // column name
    private int ID_NUMBER;

    @ManyToOne
    @JoinColumn(name="Student_ID", referencedColumnName = "USER_NAME")
    private Student student;


    @ManyToOne
    @JoinColumn(name="Course_ID", referencedColumnName = "COURSE_CATALOG_NUMBER")
    private Course course;

    @Column(name="Text_message" /*,unique = true*/, nullable = false, length = 64)
    private String Text_message;
    @Column(name="Rating" /*,unique = true*/, nullable = false, length = 1)
    private int Rating;

    public Reviews() {

    }
    public int getID_NUMBER() {
        return ID_NUMBER;
    }
    public void setID_NUMBER(int ID_NUMBER){
        this.ID_NUMBER = ID_NUMBER;
    }
    public int getStudent_ID() {
        return student.getID_NUMBER();
    }
    public Student setStudent_ID(Student Student_ID){
        this.student = Student_ID;
        return student;
    }
    public int getCourse_ID() {
        return course.getID_NUMBER();
    }
    public Course setCourse_ID(Course Course_ID){
        this.course = Course_ID;
        return course;
    }
    public String getText_message() {
        return Text_message;
    }
    public void setText_message(String Text_message){
        this.Text_message = Text_message;
    }
    public int getRating() {
        return Rating;
    }
    public void setRating(int Rating){
        this.Rating = Rating;
    }
    @Override
    public String toString() {
        return "Review {" +
                "ID_NUMBER =" + ID_NUMBER +
                ", Student_ID= " + student.getID_NUMBER() + '\'' +
                ", Course_ID = " + course.getID_NUMBER() + '\'' +
                ",Text_message = " + Text_message + '\'' +
                ",Rating = " + Rating +
                '}'+"\n";
    }
}


