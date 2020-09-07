package com.example.cst438_project01_group4.ClassObjects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

import com.example.cst438_project01_group4.DataBase.AppDatabase;

@Entity(tableName = AppDatabase.ASSIGNMENT_TABLE)
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    private int assignmentID;

    private String details;
    private int maxScore;
    private double earnedScore;
    private Date assignedDate;
    private Date dueDate;
    private int categroyID;
    private int courseID;

    // added this after
    private int gradeID;
    private int userID; // TODO: finish the set up

    public Assignment(int gradeId, String details, int maxScore, double earnedScore, Date assignedDate, Date dueDate, int categroyID, int courseID) {
        this.details = details;
        this.maxScore = maxScore;
        this.earnedScore = earnedScore;
        this.assignedDate = assignedDate;
        this.dueDate = dueDate;
        this.categroyID = categroyID;
        this.courseID = courseID;
        this.gradeID = gradeId;
    }

    public int getGradeID() {
        return gradeID;
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public double getEarnedScore() {
        return earnedScore;
    }

    public void setEarnedScore(double earnedScore) {
        this.earnedScore = earnedScore;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getCategroyID() {
        return categroyID;
    }

    public void setCategroyID(int categroyID) {
        this.categroyID = categroyID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
