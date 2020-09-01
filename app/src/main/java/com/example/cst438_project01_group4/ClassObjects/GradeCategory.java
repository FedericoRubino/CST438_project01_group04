package com.example.cst438_project01_group4.ClassObjects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import com.example.cst438_project01_group4.DataBase.AppDatabase;

@Entity(tableName = AppDatabase.GRADE_CATEGORY_TABLE)
public class GradeCategory {
    @PrimaryKey(autoGenerate = true)
    private int categoryID;

    private String title;
    private double weight;
    private int gradeID;
    private Date assignmentDate;

    public GradeCategory(String title, double weight, int gradeID, Date assignmentDate) {
        this.title = title;
        this.weight = weight;
        this.gradeID = gradeID;
        this.assignmentDate = assignmentDate;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getGradeID() {
        return gradeID;
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }

    public Date getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(Date assignmentDate) {
        this.assignmentDate = assignmentDate;
    }
}
