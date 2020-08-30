package com.example.cst438_project01_group4.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst438_project01_group4.ClassObjects.Grade;

import java.util.List;

@Dao
public interface GradeDAO {
    @Insert
    public void insert(Grade grade);

    @Delete
    public void delete(Grade grade);

    @Update
    public void update(Grade grade);

    @Query("SELECT * FROM " + AppDatabase.GRADE_TABLE)
    public List<Grade> getAllGrades();
}
