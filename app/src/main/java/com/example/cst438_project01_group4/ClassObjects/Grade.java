package com.example.cst438_project01_group4.ClassObjects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst438_project01_group4.DataBase.AppDatabase;

@Entity(tableName = AppDatabase.GRADE_TABLE)
public class Grade {
    @PrimaryKey(autoGenerate = true)
    private int gradeID;

    private double score;
    private int courseID;

    public Grade(double score, int courseID) {
        this.score = score;
        this.courseID = courseID;
    }


    public int getGradeID() {
        return gradeID;
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

//    public Date getDateEarned() {
//        return dateEarned;
//    }
//
//    public void setDateEarned(Date dateEarned) {
//        this.dateEarned = dateEarned;
//    }
}
