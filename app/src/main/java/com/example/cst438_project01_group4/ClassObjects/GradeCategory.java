package com.example.cst438_project01_group4.ClassObjects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst438_project01_group4.DataBase.AppDatabase;

/**
 * GradeCategory class, helps calculate the weights of grades
 */
@Entity(tableName = AppDatabase.GRADE_CATEGORY_TABLE)
public class GradeCategory {
    @PrimaryKey(autoGenerate = true)
    private int categoryID;

    private String title;
    private double weight;
    private int courseID;

    public GradeCategory(String title, double weight, int courseID) {
        this.title = title;
        this.weight = weight;
        this.courseID = courseID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
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

    public int getcategoryID() {
        return categoryID;
    }

    public void setcategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return title + "\n";
    }
}
