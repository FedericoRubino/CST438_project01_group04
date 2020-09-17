package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.cst438_project01_group4.ClassObjects.Assignment;
import com.example.cst438_project01_group4.ClassObjects.Course;
import com.example.cst438_project01_group4.ClassObjects.User;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;
import com.example.cst438_project01_group4.RecyclerView.GradeAppAdapter;
import com.example.cst438_project01_group4.RecyclerView.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ManageCourses extends AppCompatActivity implements ItemClickListener {

    private static RecyclerView recyclerView;
    private static GradeAppAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private GradeAppDAO gradeAppDAO;
    private static List<Course> courses;
    private Course clickedCourse;
    private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courses);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getGradeAppDAO();

        loggedInUser = gradeAppDAO.getLoggedInUser();
        TextView mTitle = (TextView) findViewById(R.id.manageCourses);
        mTitle.setText("Courses for " + loggedInUser);
//        loggedInUser = gradeAppDAO.getUserByUserID(getIntent().getIntExtra("EXTRA", -1));
//        Toast.makeText(ManageCourses.this, "User Assignments" + gradeAppDAO.getAllCoursesByUserID(loggedInUser.getUserID()).size() +  "\nUserID " + loggedInUser.getUserID(), Toast.LENGTH_LONG).show();
        courses = gradeAppDAO.getAllCoursesByUserID(loggedInUser.getUserID());
        List<Assignment> assignments = new ArrayList<>();
        for(Course c: courses){
            assignments = gradeAppDAO.getAssignmentsByCourseID(c.getCourseID());
            c.setGrade(getCourseGrade(assignments));
        }
        recyclerView = findViewById(R.id.rvCourses);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new GradeAppAdapter(courses);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(ManageCourses.this);
        if(gradeAppDAO.getAllCoursesByUserID(loggedInUser.getUserID()).size() == 0) {
            Toast.makeText(ManageCourses.this, "No courses added", Toast.LENGTH_LONG).show();
        }

        findViewById(R.id.addCourseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddCourseActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        clickedCourse = courses.get(position);
        showAlertDialogButtonClicked(view);
    }


    /**
     * Alert screen that shows up when clicking on a specific element
     * @param view
     */
    public void showAlertDialogButtonClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Course Details");
        builder.setMessage(clickedCourse.toString());
        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    Intent intent = EditCourseActivity.getIntent(getApplicationContext(), clickedCourse.getCourseID());
                    startActivity(intent);
                }
        });
        builder.setNeutralButton("ASSIGNMENTS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    Intent intent = ManageAssignments.getIntent(getApplicationContext(), clickedCourse.getCourseID());
                    startActivity(intent);
            }

        });
        builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                gradeAppDAO.delete(clickedCourse);
                dialog.cancel();
                showAlertDelete(view);
            }

        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    /**
     * Alert screen that shows up when clicking on a specific element
     * @param view
     */
    public void showAlertDelete(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Course");
        builder.setMessage(clickedCourse.toString());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gradeAppDAO.delete(clickedCourse);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    /**
     * DAO Factory
     */
    private void getGradeAppDAO(){
        gradeAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGradeAppDao();
    }

    /**
     * Intent Factory
     * @param context
     * @param value
     * @return
     */
    public static Intent getIntent(Context context, int value){
        Intent intent = new Intent(context, ManageCourses.class);
        intent.putExtra("EXTRA",value);

        return intent;
    }

    /**
     * Used to logout all users and return to the login page
     * @param view
     */
    public void logout(View view){
        gradeAppDAO.logOutAllUsers();
        finish();
    }

    public double getCourseGrade(List<Assignment> assignmentList){
        double grades = 0;
        double weights = 0;
        for(Assignment a: assignmentList){
           grades += (gradeAppDAO.getGradeCategoryById(a.getCategoryID()).getWeight() * a.getUnweightedGrade());
           weights += gradeAppDAO.getGradeCategoryById(a.getCategoryID()).getWeight();
        }
        if(grades == 0){
            return 100.0;
        }
        return grades/weights;
    }
}
