package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.cst438_project01_group4.ClassObjects.Assignment;
import com.example.cst438_project01_group4.ClassObjects.GradeCategory;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

import java.util.ArrayList;
import java.util.List;

public class AddAssignment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Assignment assignment;
    private EditText mDetails;
    private EditText mMaxScore;
    private EditText mEarnedScore;
    private List<GradeCategory> categories;
    private List<String> categoriesNames;
    Spinner spinner;
    private GradeAppDAO mGradeAppDAO;
    private int courseId;
    private Button mSubmitBtn;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        getDatabase();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        wireup();
    }

    private void wireup() {
        courseId = getIntent().getIntExtra("EXTRA", -1);
        mDetails = findViewById(R.id.etDetails);
        mMaxScore = findViewById(R.id.etMaxScore);
        mEarnedScore = findViewById(R.id.etEarnedScore);
        mSubmitBtn = findViewById(R.id.addButton);
        categories = mGradeAppDAO.getAllGradeCategoriesByCourseID(courseId);
        categoriesNames = gradeCategories(categories);

        setSpinner();

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Assignment newAssignment = getNewAssignment();
                if(mGradeAppDAO.getAssignmentByName(newAssignment.getDetails()) != null){
                    badAssignment(v);
                }
                else{
                    goodAssignment(v);
                }

            }
        });
        findViewById(R.id.exitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ManageAssignments.getIntent(getApplicationContext(), mGradeAppDAO.getLoggedInUser().getUserID());
                startActivity(intent);
            }
        });
    }

    private void setSpinner(){
        categories = mGradeAppDAO.getAllGradeCategoriesByCourseID(courseId);
        categoriesNames = gradeCategories(categories);
        spinner = (Spinner) findViewById(R.id.addCategories_spinner);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, categoriesNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private Assignment getNewAssignment() {
                String details = "TBD";
                int maxScore = 100;
                double earnedScore = 0;

                details = mDetails.getText().toString();
                try {
                    maxScore = Integer.parseInt(mMaxScore.getText().toString());

                } catch (NumberFormatException e) {
                    Log.d("ASSIGNMENTLOG", "Couldn't convert score");
                }
                try {
                    earnedScore = Double.parseDouble(mEarnedScore.getText().toString());

                } catch (NumberFormatException e) {
                    Log.d("ASSIGNMENTLOG", "Couldn't convert score");
                }

                assignment = new Assignment(details, maxScore, earnedScore, getGradeCategory(), courseId);
                assignment.setUnweightedGrade(Math.round((assignment.getEarnedScore()/assignment.getMaxScore()) * 100));
                return assignment;
    }
    /**
     * Used for the spinner
     * @param parent
     * @param view
     * @param pos
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        category =  (String) parent.getItemAtPosition(pos);
    }

    /**
     * Used for the spinner
     * @param parent
     */
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public int getGradeCategory(){
        return mGradeAppDAO.getGradeCategoryByName(category).getCategoryID();
    }

    /**
     * @param categories a list of GradeCategories for a Course
     * @return all of the categories as a string to display
     */
    private List<String> gradeCategories(List<GradeCategory> categories){
        List<String> stringCategories= new ArrayList<String>();
        for(GradeCategory c: categories){
            stringCategories.add(c.getTitle());
        }
        return stringCategories;
    }

    private void getDatabase() {
        mGradeAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGradeAppDao();
    }

    public void badAssignment(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invalid Assignment");
        // set the custom layout
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = ManageAssignments.getIntent(getApplicationContext(), mGradeAppDAO.getLoggedInUser().getUserID());
                startActivity(intent);
            }

        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void goodAssignment(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Assignment");
        builder.setMessage(assignment.toString());
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mGradeAppDAO.insert(assignment);
                Intent intent = ManageAssignments.getIntent(getApplicationContext(), courseId);
                startActivity(intent);
            }

        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = ManageAssignments.getIntent(getApplicationContext(), mGradeAppDAO.getLoggedInUser().getUserID());
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
    public static Intent getIntent(Context context, int value){
        Intent intent = new Intent(context, AddAssignment.class);
        intent.putExtra("EXTRA",value);

        return intent;
    }
}