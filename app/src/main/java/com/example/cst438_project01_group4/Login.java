package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.cst438_project01_group4.ClassObjects.User;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

public class Login extends AppCompatActivity {

    private GradeAppDAO mGradeAppDAO;
    private EditText Username;
    private EditText Password;
    private Button Login;
    private Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getGradeAppDAO();
        wireup();
    }

    public void wireup() {
        Username = (EditText) findViewById(R.id.etUsername);
        Password = (EditText) findViewById(R.id.etPassword);
        Login = (Button) findViewById(R.id.btnLogin);
        Register = (Button) findViewById(R.id.btnRegister);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean pass = true;
                if (checkForUserInDatabase(Username.getText().toString())) {
                    User account = mGradeAppDAO.getUserByUsername(Username.getText().toString());
                    //Toast.makeText(Login.this, "UserID " + account.getUserID() + "User courses" + mGradeAppDAO.getAllCoursesByUserID(account.getUserID()).size(), Toast.LENGTH_LONG).show();
                    if (!Username.getText().toString().equals(account.getUsername())) {
                        Username.setError("Incorrect username!!!");
                        pass = false;
                    }
                    if (!Password.getText().toString().equals(account.getPassword())) {
                        Password.setError("Incorrect password!!!");
                        pass = false;
                    }
                    if (pass) {
                        logInUser(account.getUsername());
                        //no reason to pass userId anymore
                        Intent intent = ManageCourses.getIntent(getApplicationContext(), account.getUserID());
                        //Toast.makeText(Login.this, "UserID " + account.getUserID() + "User courses" + mGradeAppDAO.getAllCoursesByUserID(account.getUserID()).size(), Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                }
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RegisterActivity.getIntent(Login.this);
                startActivity(intent);
            }
        });
    }

    /**
     * logs out all previous users and logs in the current user
     * @param username
     */
    private void logInUser(String username) {
        mGradeAppDAO.logOutAllUsers();
        mGradeAppDAO.setLoggedInUser(username);
    }


    /**
     * function that checks if the given username is in the database
     * @param username
     */
    public boolean checkForUserInDatabase(String username){
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

    /**
     * DAO Factory
     */
    private void getGradeAppDAO(){
        mGradeAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
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
        Intent intent = new Intent(context, Login.class);
        intent.putExtra("EXTRA",value);

        return intent;
    }
}
