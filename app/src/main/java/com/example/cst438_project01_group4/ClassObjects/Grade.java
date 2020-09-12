package com.example.cst438_project01_group4.ClassObjects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst438_project01_group4.DataBase.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.GRADE_TABLE)
public class Grade {
    @PrimaryKey(autoGenerate = true)
    private int gradeID;

    private double score;
    private int assignmentID;
//    private int courseID;
    private int userID;
    private Date dateEarned;

    public Grade(double score, int assignmentID, Date dateEarned, int userID) {
        this.score = score;
        this.assignmentID = assignmentID;
        this.dateEarned = dateEarned;
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    public Date getDateEarned() {
        return dateEarned;
    }

    public void setDateEarned(Date dateEarned) {
        this.dateEarned = dateEarned;
    }
}
