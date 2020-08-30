package com.example.cst438_project01_group4.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst438_project01_group4.ClassObjects.GradeCategory;

import java.util.List;

@Dao
public interface GradeCategoryDAO {
    @Insert
    public void insert(GradeCategory gradeCategory);

    @Delete
    public void delete(GradeCategory gradeCategory);

    @Update
    public void update(GradeCategory gradeCategory);

    @Query("SELECT * FROM " + AppDatabase.GRADE_CATEGORY_TABLE)
    public List<GradeCategory> getAllGradeCategorys();
}
