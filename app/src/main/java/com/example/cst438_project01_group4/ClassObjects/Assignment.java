package com.example.cst438_project01_group4.ClassObjects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst438_project01_group4.DataBase.AppDatabase;


/**
 * Assignment class
 * Used to store assignment information
 */
@Entity(tableName = AppDatabase.ASSIGNMENT_TABLE)
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    private int assignmentID;

    private String details;
    private int maxScore;
    private double earnedScore;
//    private Date assignedDate; Can be added later - there were formatting issues
//    private Date dueDate;
    private int categoryID;
    private int courseID;
    private double unweightedGrade;


    public Assignment(String details, int maxScore, double earnedScore, int categoryID, int courseID) {
        this.details = details;
        this.maxScore = maxScore;
        this.earnedScore = earnedScore;
//        this.assignedDate = assignedDate;
//        this.dueDate = dueDate;
        this.categoryID = categoryID;
        this.courseID = courseID;
        unweightedGrade = Math.round((earnedScore/maxScore) * 100);
    }

    public double getUnweightedGrade() {
        return unweightedGrade;
    }

    public void setUnweightedGrade(double unweightedGrade) {
        this.unweightedGrade = unweightedGrade;
    }

    //    public int getUserID() {
//        return userID;
//    }
//
//    public void setUserID(int userID) {
//        this.userID = userID;
//    }
//
//    public int getGradeID() {
//        return gradeID;
//    }
//
//    public void setGradeID(int gradeID) {
//        this.gradeID = gradeID;
//    }

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

//    public Date getAssignedDate() {
//        return assignedDate;
//    }
//
//    public void setAssignedDate(Date assignedDate) {
//        this.assignedDate = assignedDate;
//    }
//
//    public Date getDueDate() {
//        return dueDate;
//    }
//
//    public void setDueDate(Date dueDate) {
//        this.dueDate = dueDate;
//    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "Assignment: " + details + "\nMax Score: " + maxScore +
                "\nEarned Score: " + earnedScore + "\nGrade: " + unweightedGrade + "%\n";
    }
}
