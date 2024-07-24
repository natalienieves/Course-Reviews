package edu.virginia.cs;

import javax.persistence.*;

@Entity
@Table( name = "Student")
public class Student {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.AUTO) //
    @Column (name = "ID_NUMBER") // column name// Raheem built this class
    private int ID_NUMBER;
    @Column(name="USER_NAME", /*unique = true,*/ nullable = false, length = 32)
    private String USER_NAME;
//    @Column(name="LAST_NAME", /*unique = true,*/ nullable = false, length = 32)
//    private String LAST_NAME;
    @Column(name="PASSWORD", /*unique = true,*/ nullable = false, length = 32)
    private String PASSWORD;

    public Student(int id_number, String user_name, String password){
        this.ID_NUMBER = id_number;
        this.USER_NAME = user_name;
//        this.FIRST_NAME = first_name;
//        this.LAST_NAME = last_name;
        this.PASSWORD = password;

    }
    public Student(){

    }

    public int getID_NUMBER() {
        return ID_NUMBER;
    }
    public String getUSER_NAME() {
        return USER_NAME;
    }
    public void setUSER_NAME(String USER_NAME){
        this.USER_NAME = USER_NAME;
    }

//    public String getLAST_NAME() {
//        return LAST_NAME;
//    }
//    public void setLAST_NAME(String LAST_NAME){
//        this.LAST_NAME = LAST_NAME;
//    }

    public String getPASSWORD() {
        return PASSWORD;
    }
    public void setPASSWORD(String PASSWORD){
        this.PASSWORD = PASSWORD;
    }
    @Override
    public String toString() {
        return "Student{" +
                "ID_NUMBER =" + ID_NUMBER +
                ", USER_NAME ='" + USER_NAME + '\'' +
                "," + PASSWORD + "=" +
                '}';
    }
}
