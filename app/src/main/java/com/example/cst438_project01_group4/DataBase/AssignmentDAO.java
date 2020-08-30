package com.example.cst438_project01_group4.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst438_project01_group4.ClassObjects.Assignment;

import java.util.List;

@Dao
public interface AssignmentDAO {

    @Insert
    public void insert(Assignment assignment);

    @Delete
    public void delete(Assignment assignment);

    @Update
    public void update(Assignment assignment);

    @Query("SELECT * FROM " + AppDatabase.ASSIGNMENT_TABLE)
    public List<Assignment> getAllAssignments();
}
