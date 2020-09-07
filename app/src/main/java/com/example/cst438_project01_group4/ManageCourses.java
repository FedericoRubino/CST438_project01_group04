package com.example.cst438_project01_group4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.cst438_project01_group4.ClassObjects.Course;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;
import com.example.cst438_project01_group4.RecyclerView.GradeAppAdapter;

import java.util.List;

public class ManageCourses extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private GradeAppDAO gradeAppDAO;
    private List<Course> courses;

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
//        mAdapter.setClickListener()


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
