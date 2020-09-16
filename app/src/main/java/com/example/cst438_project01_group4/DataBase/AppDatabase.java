package com.example.cst438_project01_group4.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.cst438_project01_group4.ClassObjects.Assignment;
import com.example.cst438_project01_group4.ClassObjects.Course;
import com.example.cst438_project01_group4.ClassObjects.Enrollment;
import com.example.cst438_project01_group4.ClassObjects.Grade;
import com.example.cst438_project01_group4.ClassObjects.GradeCategory;
import com.example.cst438_project01_group4.ClassObjects.User;
import com.example.cst438_project01_group4.DataBase.TypeConverters.DateTypeConverter;

@Database(entities = {Assignment.class, Course.class, Enrollment.class, Grade.class, GradeCategory.class, User.class},version = 10)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {


    public static final String DB_NAME = "gradeAppDAO";
    // The names of the tables
    public static final String ASSIGNMENT_TABLE = "assignment";
    public static final String COURSE_TABLE = "course";
    public static final String ENROLLMENT_TABLE = "enrollment";
    public static final String GRADE_TABLE = "grade";
    public static final String GRADE_CATEGORY_TABLE = "gradeCategory";
    public static final String USER_TABLE = "user";

    // abstract getter methods for the different DAOs
    public abstract GradeAppDAO getGradeAppDao();
}