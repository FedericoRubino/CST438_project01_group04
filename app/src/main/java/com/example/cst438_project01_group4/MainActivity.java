package com.example.cst438_project01_group4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Room;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.UserDAO;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDatabase();
    }

    private void getDatabase() {
            userDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.USER_TABLE)
                    .allowMainThreadQueries()
                    .build()
                    .getUserDAO();
    }
}
