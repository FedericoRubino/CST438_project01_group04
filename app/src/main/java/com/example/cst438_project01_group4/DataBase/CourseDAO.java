package com.example.cst438_project01_group4.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst438_project01_group4.ClassObjects.Course;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert
    public void insert(Course course);

    @Delete
    public void delete(Course course);

    @Update
    public void update(Course course);

    @Query("SELECT * FROM " + AppDatabase.COURSE_TABLE)
    public List<Course> getAllCourses();
}
