package com.example.cst438_project01_group4.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst438_project01_group4.ClassObjects.Enrollment;

import java.util.List;

@Dao
public interface EnrollmentDao {
    @Insert
    public void insert(Enrollment enrollment);

    @Delete
    public void delete(Enrollment enrollment);

    @Update
    public void update(Enrollment enrollment);

    @Query("SELECT * FROM " + AppDatabase.ENROLLMENT_TABLE)
    public List<Enrollment> getAllEnrollments();
}
