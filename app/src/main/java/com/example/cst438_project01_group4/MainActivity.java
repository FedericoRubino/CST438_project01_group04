package com.example.cst438_project01_group4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.cst438_project01_group4.ClassObjects.Assignment;
import com.example.cst438_project01_group4.ClassObjects.Course;
import com.example.cst438_project01_group4.ClassObjects.Grade;
import com.example.cst438_project01_group4.ClassObjects.GradeCategory;
import com.example.cst438_project01_group4.ClassObjects.User;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private GradeAppDAO mGradeAppDAO;
    private EditText Username;
    private EditText Password;
    private Button Login;
    private Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getGradeAppDAO();

        User one = new User("username", "password", "firstName", "lastName");
        if(mGradeAppDAO.getAllUsers().isEmpty()){
            mGradeAppDAO.insert(one);
        }
        checkForUserInDatabase("username");
//

        if(mGradeAppDAO.getAllCourses().isEmpty()){
            Course a = new Course(111,"Dr.C", "cst538","The best there ever was", new Date(01,01,2018),new Date(01,01,2018));
            mGradeAppDAO.insert(a);
            GradeCategory g = new GradeCategory("Homework", 0.4, a.getCourseID());
            mGradeAppDAO.insert(g);
            mGradeAppDAO.insert(new GradeCategory("Homework2", 0.5, a.getCourseID()));
            mGradeAppDAO.insert(new GradeCategory("Homework3", 0.6, a.getCourseID()));
            Assignment as = new Assignment("SuperDifficult", 100, 0.5, new Date(01,01,2018), new Date(01,01,2018), g.getCategoryID(), g.getCourseID(), one.getUserID());
            mGradeAppDAO.insert(as);
            Grade grade = new Grade(0.5, as.getAssignmentID(), a.getCourseID(), new Date(01,01,2018));
            as.setGradeID(grade.getGradeID());
            mGradeAppDAO.insert(new Course(111,"Dr.C", "cst338","The best there ever was", new Date(01,01,2018),new Date(01,01,2018)));
            mGradeAppDAO.insert(new Course(111,"Dr.C", "cst438","The best there ever was", new Date(01,01,2018),new Date(01,01,2018)));
//            mGradeAppDAO.insert(new Course("Dr.C", "cst438","The best there ever was", new Date(01/01/2018),null));
//            mGradeAppDAO.insert(new Course("Dr.C", "cst201","The best there ever was", new Date(01/01/2018),null));
            Toast.makeText(this, "" + mGradeAppDAO.getAllGradeCategoriesByCourseID(as.getCourseID()).size(), Toast.LENGTH_LONG).show();
            Intent intent = EditAssignmentActivity.getIntent(getApplicationContext(), as.getAssignmentID());
            startActivity(intent);
        }

        Username = (EditText)findViewById(R.id.etUsername);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btnLogin);
        Register = (Button)findViewById(R.id.btnRegister);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean pass = true;
                if(checkForUserInDatabase(Username.getText().toString())){
                    User account = mGradeAppDAO.getUserByUsername(Username.getText().toString());
                    if(!Username.getText().toString().equals(account.getUsername())){
                        Username.setError("Incorrect username!!!");
                        pass = false;
                    }
                    if(!Password.getText().toString().equals(account.getPassword())){
                        Password.setError("Incorrect password!!!");
                        pass = false;
                    }
                    if(pass){
                        Intent intent = new Intent(MainActivity.this, LandingActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean checkForUserInDatabase(String username){
        User mUser;
        mUser = mGradeAppDAO.getUserByUsername(username);
        if(mUser == null){
            Toast.makeText(this, "no user " + "username" + " found, you need to register an account", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            //Toast.makeText(this, mUser.getUsername() + " found", Toast.LENGTH_LONG).show();
            return true;
        }
    }

    private void getGradeAppDAO(){
        mGradeAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGradeAppDao();
    }

    public GradeAppDAO returnDatabase(){
        getGradeAppDAO();
        return mGradeAppDAO;
    }
}
