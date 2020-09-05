package com.example.cst438_project01_group4.ClassObjects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst438_project01_group4.DataBase.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.COURSE_TABLE)
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;

    private String instructor;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;

    public Course(String instructor, String title, String description, Date startDate, Date endDate) {
        this.instructor = instructor;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Course{" +
                "instructor='" + instructor + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
