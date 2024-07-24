package edu.virginia.cs;

import javax.persistence.*;

@Entity
@Table(name = "Courses")
public class Course { // Raheem built this class
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.AUTO) //
    @Column (name = "ID_NUMBER") // column name
    private int ID_NUMBER;
    @Column(name="DEPARTMENT", unique = true, nullable = false /*length = 32*/)
    private String DEPARTMENT;
    @Column(name="COURSE_CATALOG_NUMBER", unique = true, nullable = false /*, length = 32*/)
    private int COURSE_CATALOG_NUMBER;

    public Course(int id_number, String DEPARTMENT, int COURSE_CATALOG_NUMBER){
        this.ID_NUMBER = id_number;
        this.DEPARTMENT = DEPARTMENT;
        this.COURSE_CATALOG_NUMBER = COURSE_CATALOG_NUMBER;
    }

    public Course() {

    }
    public int getID_NUMBER() {
        return ID_NUMBER;
    }
    public void setID_NUMBER(int ID_NUMBER){
        this.ID_NUMBER = ID_NUMBER;
    }
    public String getDEPARTMENT() {
        return DEPARTMENT;
    }
    public void setDEPARTMENT(String DEPARTMENT){
        this.DEPARTMENT = DEPARTMENT;
    }
    public int getCOURSE_CATALOG_NUMBER() {
        return COURSE_CATALOG_NUMBER;
    }
    public void setCOURSE_CATALOG_NUMBER(int COURSE_CATALOG_NUMBER){
        this.COURSE_CATALOG_NUMBER = COURSE_CATALOG_NUMBER;
    }
    @Override
    public String toString() {
        return "Courses{" +
                "ID_NUMBER =" + ID_NUMBER +
                ", DEPARTMENT='" + DEPARTMENT + '\'' +
                ", COURSE_CATALOG_NUMBER =" + COURSE_CATALOG_NUMBER +
                '}';
    }

}
