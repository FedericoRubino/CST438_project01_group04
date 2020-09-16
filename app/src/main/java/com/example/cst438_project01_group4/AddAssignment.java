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
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

public class AddAssignment extends AppCompatActivity {

    private EditText instructorEditText;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button submitBtn;
    private Course course;

    GradeAppDAO mGradeAppDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        getDatabase();
        wireup();
    }

    private void wireup() {
        instructorEditText = findViewById(R.id.etInstructor);
        titleEditText = findViewById(R.id.etTitle);
        descriptionEditText = findViewById(R.id.etDescription);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Course");
        builder.setMessage(course.toString());
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mGradeAppDAO.insert(course);
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