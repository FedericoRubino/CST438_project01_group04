package com.example.cst438_project01_group4.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst438_project01_group4.ClassObjects.Assignment;
import com.example.cst438_project01_group4.ClassObjects.Course;
import com.example.cst438_project01_group4.ClassObjects.Enrollment;
import com.example.cst438_project01_group4.ClassObjects.Grade;
import com.example.cst438_project01_group4.ClassObjects.GradeCategory;
import com.example.cst438_project01_group4.ClassObjects.User;

import java.util.List;

@Dao
public interface GradeAppDAO {
    @Insert
    public void insert(Assignment assignment);

    @Delete
    public void delete(Assignment assignment);

    @Update
    public void update(Assignment assignment);

    @Query("SELECT * FROM " + AppDatabase.ASSIGNMENT_TABLE)
    public List<Assignment> getAllAssignments();

    @Insert
    public void insert(Course course);

    @Delete
    public void delete(Course course);

    @Update
    public void update(Course course);

    @Query("SELECT * FROM " + AppDatabase.COURSE_TABLE)
    public List<Course> getAllCourses();

    @Query("DELETE FROM " + AppDatabase.COURSE_TABLE)
    public void deleteCourseTable();

    @Query("Select * FROM " + AppDatabase.COURSE_TABLE + " WHERE courseID = :courseId")
    public Course getCourseById(int courseId);


    @Insert
    public void insert(Enrollment enrollment);

    @Delete
    public void delete(Enrollment enrollment);

    @Update
    public void update(Enrollment enrollment);

    @Query("SELECT * FROM " + AppDatabase.ENROLLMENT_TABLE)
    public List<Enrollment> getAllEnrollments();

    @Insert
    public void insert(GradeCategory gradeCategory);

    @Delete
    public void delete(GradeCategory gradeCategory);

    @Update
    public void update(GradeCategory gradeCategory);

    @Query("SELECT * FROM " + AppDatabase.GRADE_CATEGORY_TABLE)
    public List<GradeCategory> getAllGradeCategorys();

    @Insert
    public void insert(Grade grade);

    @Delete
    public void delete(Grade grade);

    @Update
    public void update(Grade grade);

    @Query("SELECT * FROM " + AppDatabase.GRADE_TABLE)
    public List<Grade> getAllGrades();

    @Insert
    public void insert(User user);

    @Delete
    public void delete(User user);

    @Update
    public void update(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE username = :usernameField")
    User getUserByUsername(String usernameField);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    public List<User> getAllUsers();


    // get a list of assignments based on userID, courseID
    @Query("Select * from " + AppDatabase.ASSIGNMENT_TABLE + " WHERE courseID = :courseID and userID = :userID")
    public List<Assignment> getAssignmentsByCourseID(int courseID, int userID);

    // get a grade based on assignmentID
    @Query("Select * from " + AppDatabase.GRADE_TABLE + " WHERE courseID = :assignmentID")
    public Grade getGradeByAssignmentID(int assignmentID);

}
