package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
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
    private String courseName;
    private String courseInstructor;
    private String courseDescription;
    private Date courseStartTime;
    private Date courseEndTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        Intent intent = getIntent();
        int courseId = intent.getIntExtra("EXTRA", -1);
        // this is what Federico is adding
        mTitle = findViewById(R.id.courseNameEditText);
        mInstructor = findViewById(R.id.InstructorNameEditText);
        mDescription = findViewById(R.id.CourseDescriptionEditText);

        getGradeAppDAO();
        editCourse = gradeAppDAO.getCourseById(courseId);
        if(editCourse != null){
            courseName = editCourse.getTitle();
            courseInstructor = editCourse.getInstructor();
            courseDescription = editCourse.getDescription();
            courseStartTime = editCourse.getStartDate();
            courseEndTime = editCourse.getEndDate();

            mTitle.setHint(courseName);
            mInstructor.setHint(courseInstructor);
            mDescription.setHint(courseDescription);
        }
    }

    // confirms input
    public void confirm(View view){
        //This is where we check input
        //Then we update the database
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please confirm changes");
        Course course = getNewCourseValues();
        builder.setMessage(course.toString());
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateCourse();
                gradeAppDAO.update(editCourse);
                Intent intent = ManageCourses.getIntent(getApplicationContext(), "");
                startActivity(intent);
            }

        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = ManageCourses.getIntent(getApplicationContext(), "");
                startActivity(intent);
            }

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private Course getNewCourseValues(){
       String instructor;
       String title;
       String description;
       Date startDate;
       Date endDate;

        if(!mTitle.getText().toString().isEmpty()){
            title = mTitle.getText().toString();
        }
        else {
            title = courseName;
        }

        if(!mInstructor.getText().toString().isEmpty()){
            instructor = mInstructor.getText().toString();
        }
        else {
            instructor = courseInstructor;
        }

        if(!mDescription.getText().toString().isEmpty()){
            description = mDescription.getText().toString();
        }
        else {
            description = courseDescription;
        }

        return new Course(editCourse.getUserID(), instructor, title, description, courseStartTime, courseEndTime);
    }

    private void updateCourse(){

        if(!mTitle.getText().toString().isEmpty()){
            editCourse.setTitle(mTitle.getText().toString());
        }

        if(!mInstructor.getText().toString().isEmpty()){
            editCourse.setInstructor(mInstructor.getText().toString());
        }

        if(!mDescription.getText().toString().isEmpty()){
            editCourse.setDescription(mDescription.getText().toString());
        }
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
}
