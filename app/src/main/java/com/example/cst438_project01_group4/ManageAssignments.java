package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.cst438_project01_group4.ClassObjects.Assignment;
import com.example.cst438_project01_group4.ClassObjects.GradeCategory;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;
import com.example.cst438_project01_group4.RecyclerView.AssignmentAdapter;
import com.example.cst438_project01_group4.RecyclerView.ItemClickListener;

import java.util.List;

public class ManageAssignments extends AppCompatActivity implements ItemClickListener {

    private static RecyclerView recyclerView;
    private static AssignmentAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private GradeAppDAO gradeAppDAO;
    private static List<Assignment> assignments;
    private Assignment clickedAssignment;
    private GradeCategory category;
    private int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_assignments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getGradeAppDAO();

        // in the case that this activity is called without an intent the value will be -1
        // and we will keep the original courseID
        int temp = getIntent().getIntExtra("EXTRA", -1);
        if(temp != -1) {
            courseID = temp;
        }
        assignments = gradeAppDAO.getAssignmentsByCourseID(courseID);
        recyclerView = findViewById(R.id.rvAssignments);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AssignmentAdapter(assignments);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(ManageAssignments.this);

        if(assignments.size() == 0) {
            Toast.makeText(ManageAssignments.this, "No Assignments added", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     * @param view
     */
    public void addAssignment(View view){
        Intent intent = AddAssignment.getIntent(getApplicationContext(), getIntent().getIntExtra("EXTRA", -1));
        startActivity(intent);
    }

    @Override
    public void onClick(View view, int position) {
        clickedAssignment = assignments.get(position);
        category = gradeAppDAO.getGradeCategoryById(clickedAssignment.getCategoryID());
        showAlertDialogButtonClicked(view);
    }

    public void showAlertDialogButtonClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Assignment Details");
        builder.setMessage(clickedAssignment.toString() + category.toString());
        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = EditAssignmentActivity.getIntent(getApplicationContext(), clickedAssignment.getAssignmentID());
                startActivity(intent);

            }
        });
        builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
        builder.setMessage(clickedAssignment.toString());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gradeAppDAO.delete(clickedAssignment);
                assignments = gradeAppDAO.getAssignmentsByCourseID(courseID);
                mAdapter.setData(assignments);
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
        Intent intent = new Intent(context, ManageAssignments.class);
        intent.putExtra("EXTRA",value);

        return intent;
    }
}
