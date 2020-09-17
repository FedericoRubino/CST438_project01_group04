package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.cst438_project01_group4.ClassObjects.User;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

public class RegisterActivity extends AppCompatActivity {

    private GradeAppDAO mGradeAppDAO;
    private EditText mNewUser;
    private EditText mNewpassword;
    private EditText mFirstname;
    private EditText mLastname;

    String username;
    String password;
    String firstname;
    String lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getGradeAppDAO();

        mNewUser = findViewById(R.id.etNewuser);
        mNewpassword = findViewById(R.id.etNewpassword);
        mFirstname = findViewById(R.id.etEditfirstname);
        mLastname = findViewById(R.id.etLastname);
    }

    /**
     * Signs up a new user
     * @param view
     */
    public void signUpUser(View view){
        username = mNewUser.getText().toString();
        password = mNewpassword.getText().toString();
        firstname = mFirstname.getText().toString();
        lastname = mLastname.getText().toString();
        if(username.equals("") || password.equals("") || firstname.equals("") || lastname.equals("")){
            Toast.makeText(getApplicationContext(), "Please fill out all of the fields", Toast.LENGTH_LONG).show();
            return;
        }
        User newUser = mGradeAppDAO.getUserByUsername(username);
        if(newUser == null){
            newUser = new User(username,password,firstname,lastname);
            Toast.makeText(getApplicationContext(), "Successfully added " + username, Toast.LENGTH_LONG).show();
            mGradeAppDAO.insert(newUser);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "A user with that username already exists", Toast.LENGTH_LONG).show();
            return;
        }
    }

    /**
     *  DAO Factory
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
     * @return
     */
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);

        return intent;
    }
}
