package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.cst438_project01_group4.ClassObjects.Course;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

import java.util.Date;

public class EditCourseActivity extends AppCompatActivity {

    private Course editCourse;
    private GradeAppDAO gradeAppDAO;
    private EditText mTitle;
    private EditText mInstructor;
    private EditText mDescription;
    private EditText mStart;
    private EditText mEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        Intent intent = getIntent();
        int courseId = intent.getIntExtra("EXTRA", -1);
        // this is what Federico is adding
        mTitle = findViewById(R.id.courseNameEditText);
        mInstructor = findViewById(R.id.InstructorNameEditText);;
        mDescription = findViewById(R.id.CourseDescriptionEditText);;
        mStart = findViewById(R.id.CourseStartTimeEditText);;
        mEnd = findViewById(R.id.CourseEndEditText);;

        getGradeAppDAO();
        editCourse = gradeAppDAO.getCourseById(courseId);
        if(editCourse != null){
            String courseName = editCourse.getTitle();
            String courseInstructor = editCourse.getInstructor();
            String courseDescription = editCourse.getDescription();
            Date courseStartTime = editCourse.getStartDate();
            Date courseEndTime = editCourse.getEndDate();

            mTitle.setHint(courseName);
            mInstructor.setHint(courseInstructor);
            mDescription.setHint(courseDescription);
            mStart.setHint(courseStartTime.toString());
            mEnd.setHint(courseEndTime.toString());
        }
    }

    // confirms input
    public void confirm(View view){
        //This is where we check input
        //Then we update the database

        finish();
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
