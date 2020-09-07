package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.cst438_project01_group4.ClassObjects.Course;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

public class EditCourseActivity extends AppCompatActivity {

    private Course editCourse;
    private GradeAppDAO gradeAppDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        Intent intent = getIntent();
        int courseId = intent.getIntExtra("EXTRA", -1);
    }

    // Intent factory
    public static Intent getIntent(Context context, int value){
        Intent intent = new Intent(context, EditCourseActivity.class);
        intent.putExtra("EXTRA",value);

        return intent;
    }

    // returns the dao
    private void getGradeAppDAO(){
        gradeAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGradeAppDao();
    }

    private String test(){
        return "Hello";
    }
}
