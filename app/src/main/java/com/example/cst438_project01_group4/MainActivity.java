package com.example.cst438_project01_group4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Room;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cst438_project01_group4.ClassObjects.User;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private GradeAppDAO mGradeAppDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getGradeAppDAO();

        if(mGradeAppDAO.getAllUsers().isEmpty()){
            mGradeAppDAO.insert(new User("username", "password", "firstName", "lastName"));
        }
        checkForUserInDatabase("username");

    }

    private boolean checkForUserInDatabase(String username){
        User mUser;
        mUser = mGradeAppDAO.getUserByUsername(username);
        if(mUser == null){
            Toast.makeText(this, "no user " + "username" + " found", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            Toast.makeText(this, mUser.getUsername() + " found", Toast.LENGTH_LONG).show();
            return true;
        }
    }

    private void getGradeAppDAO(){
        mGradeAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGradeAppDAO();
    }

    public GradeAppDAO returnDatabase(){
        getGradeAppDAO();
        return mGradeAppDAO;
    }
}
