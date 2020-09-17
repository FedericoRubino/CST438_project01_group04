package com.example.cst438_project01_group4.ClassObjects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst438_project01_group4.DataBase.AppDatabase;

@Entity(tableName = AppDatabase.COURSE_TABLE)
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;

    private String instructor;
    private String title;
    private String description;
//    private Date startDate;
//    private Date endDate;
    private int userID;
    private double grade;

    public Course(int userID, String instructor, String title, String description) {
        this.instructor = instructor;
        this.title = title;
        this.description = description;
        this.userID = userID;
        grade = 100;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getUserID() {
        return userID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Course: " + title + "\nInstructor: " + instructor + "\nDescription: " + description + "\n" + "Weighted Grade: " + grade + "%";
    }
}
