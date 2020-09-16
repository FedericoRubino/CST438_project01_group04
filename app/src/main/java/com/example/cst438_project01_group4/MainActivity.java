package com.example.cst438_project01_group4;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.cst438_project01_group4.ClassObjects.Assignment;
import com.example.cst438_project01_group4.ClassObjects.Course;
import com.example.cst438_project01_group4.ClassObjects.GradeCategory;
import com.example.cst438_project01_group4.ClassObjects.User;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private GradeAppDAO mGradeAppDAO;
    private GradeCategory g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getGradeAppDAO();



        if (mGradeAppDAO.getAllCourses().isEmpty() && mGradeAppDAO.getAllUsers().isEmpty()) {
            User one = new User("username", "password", "firstName", "lastName");
            mGradeAppDAO.insert(one);

            Course a = new Course(mGradeAppDAO.getUserByUsername("username").getUserID(), "Dr.C", "cst538", "The best there ever was", new Date(01, 01, 2018), new Date(01, 01, 2018));
            mGradeAppDAO.insert(a);
            g = new GradeCategory("Homework", 0.4, mGradeAppDAO.getCourseByUserID(mGradeAppDAO.getUserByUsername("username").getUserID()).getCourseID());
            mGradeAppDAO.insert(g);
            Assignment as = new Assignment("SuperDifficult", 100, 0.5, new Date(01, 01, 2018), new Date(01, 01, 2018), mGradeAppDAO.getGradeCategoryByName("Homework").getCategoryID(), mGradeAppDAO.getCourseByUserID(mGradeAppDAO.getUserByUsername("username").getUserID()).getCourseID());
            mGradeAppDAO.insert(as);
            mGradeAppDAO.insert(new GradeCategory("Homework2", 0.5, mGradeAppDAO.getCourseByUserID(mGradeAppDAO.getUserByUsername("username").getUserID()).getCourseID()));
            mGradeAppDAO.insert(new GradeCategory("Homework3", 0.6, mGradeAppDAO.getCourseByUserID(mGradeAppDAO.getUserByUsername("username").getUserID()).getCourseID()));

            //Grade grade = new Grade(0.5, as.getAssignmentID(), new Date(01, 01, 2018), a.getUserID());
            //mGradeAppDAO.insert(new Course(mGradeAppDAO.getUserByUsername("username").getUserID(), "Dr.C", "cst338", "The best there ever was", new Date(01, 01, 2018), new Date(01, 01, 2018)));
            //mGradeAppDAO.insert(new Course(mGradeAppDAO.getUserByUsername("username").getUserID(), "Dr.C", "cst438", "The best there ever was", new Date(01, 01, 2018), new Date(01, 01, 2018)));
//            Toast.makeText(MainActivity.this, "UserID " + one.getUserID() + "User courses" + mGradeAppDAO.getAllCoursesByUserID(one.getUserID()).size(), Toast.LENGTH_LONG).show();
//            mGradeAppDAO.insert(new Course("Dr.C", "cst438","The best there ever was", new Date(01/01/2018),null));
//            mGradeAppDAO.insert(new Course("Dr.C", "cst201","The best there ever was", new Date(01/01/2018),null));
        }
        wireup();
    }

    public void wireup(){
        Intent intent = Login.getIntent(getApplicationContext(), -1);
//      Toast.makeText(MainActivity.this, "UserID " + account.getUserID() + "User courses" + mGradeAppDAO.getAllCoursesByUserID(account.getUserID()).size(), Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    /**
     * DAO Factory
     */
    private void getGradeAppDAO(){
        mGradeAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGradeAppDao();
    }

}