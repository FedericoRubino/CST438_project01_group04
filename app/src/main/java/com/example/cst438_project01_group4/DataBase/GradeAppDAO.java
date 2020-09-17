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

    //Assignment Queries

    @Insert
    public void insert(Assignment assignment);

    @Delete
    public void delete(Assignment assignment);

    @Update
    public void update(Assignment assignment);

    @Query("SELECT * FROM " + AppDatabase.ASSIGNMENT_TABLE)
    public List<Assignment> getAllAssignments();

    @Query("Select * FROM " + AppDatabase.ASSIGNMENT_TABLE + " WHERE assignmentID = :assignmentId")
    public Assignment getAssignmentById(int assignmentId);

    @Query("Select * FROM " + AppDatabase.ASSIGNMENT_TABLE + " WHERE details = :details")
    public Assignment getAssignmentByName(String details);

    //Course Queries

    @Insert
    public void insert(Course course);

    @Delete
    public void delete(Course course);

    @Update
    public void update(Course course);

    @Query("SELECT * FROM " + AppDatabase.COURSE_TABLE)
    public List<Course> getAllCourses();

    @Query("SELECT * FROM " + AppDatabase.COURSE_TABLE  + " WHERE title = :title")
    Course getCourseByName(String title);

    @Query("DELETE FROM " + AppDatabase.COURSE_TABLE )
    public void deleteCourseTable();

    @Query("Select * FROM " + AppDatabase.COURSE_TABLE + " WHERE courseID = :courseId")
    public Course getCourseById(int courseId);

    //Enrollment Queries

    @Insert
    public void insert(Enrollment enrollment);

    @Delete
    public void delete(Enrollment enrollment);

    @Update
    public void update(Enrollment enrollment);

    @Query("SELECT * FROM " + AppDatabase.ENROLLMENT_TABLE)
    public List<Enrollment> getAllEnrollments();

    //GradeCategory Queries

    @Insert
    public void insert(GradeCategory gradeCategory);

    @Delete
    public void delete(GradeCategory gradeCategory);

    @Update
    public void update(GradeCategory gradeCategory);

    @Query("Select * FROM " + AppDatabase.GRADE_CATEGORY_TABLE + " WHERE categoryID = :categoryId")
    public GradeCategory getGradeCategoryById(int categoryId);

    @Query("SELECT * FROM " + AppDatabase.GRADE_CATEGORY_TABLE)
    public List<GradeCategory> getAllGradeCategories();

    @Query("Select * from " + AppDatabase.GRADE_CATEGORY_TABLE + " WHERE title = :categoryName")
    public GradeCategory getGradeCategoryByName(String categoryName);

    //Grade Queries

    @Insert
    public void insert(Grade grade);

    @Delete
    public void delete(Grade grade);

    @Update
    public void update(Grade grade);

    @Query("SELECT * FROM " + AppDatabase.GRADE_TABLE)
    public List<Grade> getAllGrades();

    //User Queries

    @Insert
    public void insert(User user);

    @Delete
    public void delete(User user);

    @Update
    public void update(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE username= :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE userID = :userID")
    User getUserByUserID(int userID);

    // Queries that handle logged in statuses in the database

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE loggedIn = 1")
    User getLoggedInUser();

    @Query("UPDATE " + AppDatabase.USER_TABLE + " SET loggedIn = 1 WHERE username = :userName")
    void setLoggedInUser(String userName);

    @Query("UPDATE " + AppDatabase.USER_TABLE + " SET loggedIn = 0")
    void logOutAllUsers();


    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();


    // get a list of assignments based on courseID
    @Query("Select * from " + AppDatabase.ASSIGNMENT_TABLE + " WHERE courseID = :courseID")
    List<Assignment> getAssignmentsByCourseID(int courseID);

    // get a greade category based on assignment
    @Query("Select * from " + AppDatabase.GRADE_CATEGORY_TABLE + " WHERE courseID = :courseID")
    GradeCategory getGradeCategoryByCourseID(int courseID);

    // get a grade category based on assignment
    @Query("Select * from " + AppDatabase.GRADE_CATEGORY_TABLE + " WHERE courseID = :courseID")
    List<GradeCategory> getAllGradeCategoriesByCourseID(int courseID);

    // get a list of courses based on userID
    @Query("Select * from " + AppDatabase.COURSE_TABLE + " WHERE userID = :userID")
    List<Course> getAllCoursesByUserID(int userID);

    // get a list of courses based on userID
    @Query("Select * from " + AppDatabase.COURSE_TABLE + " WHERE userID = :userID")
    Course getCourseByUserID(int userID);

}
