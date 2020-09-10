package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.cst438_project01_group4.ClassObjects.Course;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;
import com.example.cst438_project01_group4.RecyclerView.GradeAppAdapter;
import com.example.cst438_project01_group4.RecyclerView.ItemClickListener;

import java.util.List;

public class ManageCourses extends AppCompatActivity implements ItemClickListener {

    private static RecyclerView recyclerView;
    private static GradeAppAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private GradeAppDAO gradeAppDAO;
    private static List<Course> courses;
    private Course clickedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courses);

        getGradeAppDAO();
        courses = gradeAppDAO.getAllCourses();

        // wiring up the recycler view
        recyclerView = findViewById(R.id.rvCourses);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new GradeAppAdapter(courses);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(ManageCourses.this);


    }

    @Override
    public void onClick(View view, int position) {
        clickedCourse = courses.get(position);
        showAlertDialogButtonClicked(view);
    }

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
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // returns the dao
    private void getGradeAppDAO(){
        gradeAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGradeAppDao();
    }



    // Intent factory
    public static Intent getIntent(Context context, String value){
        Intent intent = new Intent(context, ManageCourses.class);
        intent.putExtra("EXTRA",value);

        return intent;
    }

}
