package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.cst438_project01_group4.ClassObjects.Course;
import com.example.cst438_project01_group4.ClassObjects.GradeCategory;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

public class AddCourseActivity extends AppCompatActivity {

    private EditText instructorEditText;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText homeworkEditText;
    private EditText assignmentEditText;
    private EditText quizEditText;
    private EditText testEditText;
    private Button submitBtn;
    private Course course;
    private double homework;
    private double assignment;
    private double test;
    private double quiz;

    GradeAppDAO mGradeAppDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        getDatabase();
        wireup();
    }

    private void wireup() {
        instructorEditText = findViewById(R.id.etInstructor);
        titleEditText = findViewById(R.id.etTitle);
        descriptionEditText = findViewById(R.id.etDescription);
        homeworkEditText = findViewById(R.id.etHomeworkWeight);
        assignmentEditText = findViewById(R.id.etAssignmentWeight);
        quizEditText = findViewById(R.id.etQuizWeight);
        testEditText = findViewById(R.id.etTestWeight);

        submitBtn = findViewById(R.id.button);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String instructor = "TBD";
                String title = "TBD";
                String description = "TBD";

                instructor = instructorEditText.getText().toString();
                title = titleEditText.getText().toString();
                description = descriptionEditText.getText().toString();


                course = new Course(mGradeAppDAO.getLoggedInUser().getUserID(), instructor, title, description);



                if(mGradeAppDAO.getCourseByName(title) != null){
                    badCourse(v);
                }
                else{
                    goodCourse(v);
                }
            }
        });
    }

    private void getDatabase() {
        mGradeAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGradeAppDao();
    }

    public void badCourse(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invalid Course");
        // set the custom layout
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = ManageCourses.getIntent(getApplicationContext(), mGradeAppDAO.getLoggedInUser().getUserID());
                startActivity(intent);
            }

        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void goodCourse(View view){
        double assignmentWeight = Double.parseDouble(assignmentEditText.getText().toString());
        double homeworkWeight = Double.parseDouble(homeworkEditText.getText().toString());
        double quizWeight = Double.parseDouble(quizEditText.getText().toString());
        double testWeight = Double.parseDouble(testEditText.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Course");
        builder.setMessage(course.toString() + "\nHomework Weight: " + homeworkWeight + "%" + "\nAssignment Weight: " + assignmentWeight + "%" + "\nQuiz Weight: " + quizWeight + "%" + "\nTest Weight: " + testWeight + "%");
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mGradeAppDAO.insert(course);
                GradeCategory assignmentCategory = new GradeCategory("Assignment", Double.parseDouble(homeworkEditText.getText().toString())/100, mGradeAppDAO.getCourseByName(course.getTitle()).getCourseID());
                GradeCategory homeworkCategory = new GradeCategory("Homework", Double.parseDouble(homeworkEditText.getText().toString())/100, mGradeAppDAO.getCourseByName(course.getTitle()).getCourseID());
                GradeCategory quizCategory = new GradeCategory("Quiz", Double.parseDouble(homeworkEditText.getText().toString())/100, mGradeAppDAO.getCourseByName(course.getTitle()).getCourseID());
                GradeCategory testCategory = new GradeCategory("Test", Double.parseDouble(homeworkEditText.getText().toString())/100, mGradeAppDAO.getCourseByName(course.getTitle()).getCourseID());
                mGradeAppDAO.insert(assignmentCategory);
                mGradeAppDAO.insert(homeworkCategory);
                mGradeAppDAO.insert(quizCategory);
                mGradeAppDAO.insert(testCategory);
                Intent intent = ManageCourses.getIntent(getApplicationContext(), mGradeAppDAO.getLoggedInUser().getUserID());
                startActivity(intent);
            }

        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = ManageCourses.getIntent(getApplicationContext(), mGradeAppDAO.getLoggedInUser().getUserID());
                startActivity(intent);
            }

        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Intent Factory
     * @param context
     * @return
     */
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, AddCourseActivity.class);

        return intent;
    }
}
