package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.Date;
import java.util.List;

public class EditAssignmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Assignment editAssignment;
    private GradeAppDAO gradeAppDAO;
    private EditText mDetails;
    private EditText mMaxScore;
    private EditText mEarnedScore;
    private EditText mCategoryID;
    private EditText mCourseID;
    private String details;
    private int maxScore;
    private double earnedScore;
    private int categoryID;
    private int courseID;
    private String category;
    private List<GradeCategory> categories;
    private List<String> categoriesNames;
    Date assignedDate;
    Date dueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);
        getGradeAppDAO();

        Intent intent = getIntent();
        int assignmentId = intent.getIntExtra("EXTRA", -1);
        int courseId = gradeAppDAO.getAssignmentById(assignmentId).getCourseID();
        mDetails = findViewById(R.id.AssignmentDetailsEditText);
        mMaxScore = findViewById(R.id.MaxScoreEditText);
        mEarnedScore = findViewById(R.id.EarnedScoreEditText);

        editAssignment = gradeAppDAO.getAssignmentById(assignmentId);
        if(editAssignment != null){
            details = editAssignment.getDetails();
            maxScore = editAssignment.getMaxScore();
            earnedScore = editAssignment.getEarnedScore();
            assignedDate = editAssignment.getAssignedDate();
            dueDate = editAssignment.getDueDate();

            mDetails.setHint("Details: " + details);
            mMaxScore.setHint("Max Score: " + maxScore);
            mEarnedScore.setHint("Earned Score: " + earnedScore);
        }

        categories = gradeAppDAO.getAllGradeCategoriesByCourseID(courseId);
        categoriesNames = gradeCategories(categories);

        Spinner spinner = (Spinner) findViewById(R.id.categories_spinner);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, categoriesNames );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private List<String> gradeCategories(List<GradeCategory> categories){
        List<String> stringCategories= new ArrayList<String>();
        for(GradeCategory c: categories){
            stringCategories.add(c.getTitle());
        }
        return stringCategories;
    }


    // confirms input
    public void confirm(View view){
        //This is where we check input
        //Then we update the database
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please confirm changes");
        Assignment assignment = getNewAssignmentValues();
        builder.setMessage(assignment.toString() + category);
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateAssignment();
                gradeAppDAO.update(editAssignment);
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

    private Assignment getNewAssignmentValues(){
        String assignmentDetails;
        int assignmentMaxScore;
        double assignmentEarnedScore;

        if(!mDetails.getText().toString().isEmpty()){
            assignmentDetails = mDetails.getText().toString();
        }
        else {
            assignmentDetails = details;
        }

        try{
            assignmentMaxScore = Integer.parseInt(mMaxScore.getText().toString());

        }catch(NumberFormatException e){
            Log.d("ASSIGNMENTLOG", "Couldn't convert score");
            assignmentMaxScore = maxScore;
        }

        try{
            assignmentEarnedScore = Double.parseDouble(mEarnedScore.getText().toString());

        }catch(NumberFormatException e){
            Log.d("ASSIGNMENTLOG", "Couldn't convert score");
            assignmentEarnedScore = earnedScore;
        }

        return new Assignment(assignmentDetails, assignmentMaxScore, assignmentEarnedScore, assignedDate, dueDate,  gradeAppDAO.getGradeCategoryByName(category).getCategoryID(), editAssignment.getCourseID(), editAssignment.getUserID());
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
         category =  (String) parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        category = gradeAppDAO.getGradeCategoryById(categoryID).toString();
    }

    private void updateAssignment(){

        if(!mDetails.getText().toString().isEmpty()){
            editAssignment.setDetails(mDetails.getText().toString());
        }

        if(!mMaxScore.getText().toString().isEmpty()){
            editAssignment.setMaxScore(Integer.parseInt(mMaxScore.getText().toString()));
        }

        if(!mEarnedScore.getText().toString().isEmpty()){
            editAssignment.setEarnedScore(Double.parseDouble(mEarnedScore.getText().toString()));
        }
    }

    // Intent factory
    public static Intent getIntent(Context context, int value){
        Intent intent = new Intent(context, EditAssignmentActivity.class);
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
