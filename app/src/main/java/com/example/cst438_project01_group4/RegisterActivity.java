package com.example.cst438_project01_group4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cst438_project01_group4.ClassObjects.User;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

public class RegisterActivity extends AppCompatActivity {

    private GradeAppDAO mGradeAppDAO;
    private EditText Newuser;
    private EditText Newpassword;
    private EditText Firstname;
    private EditText Lastname;
    private Button Sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getGradeAppDAO();
        Newuser = findViewById(R.id.etNewuser);
        Newpassword = findViewById(R.id.etNewpassword);
        Firstname = findViewById(R.id.etEditfirstname);
        Lastname = findViewById(R.id.etLastname);
        Sign = findViewById(R.id.btnSign);

        String username = Newuser.getText().toString();
        String password = Newpassword.getText().toString();
        String firstname = Firstname.getText().toString();
        String lastname = Lastname.getText().toString();

        Sign.setOnClickListener(v -> {
            if(mGradeAppDAO.getAllUsers().isEmpty() || mGradeAppDAO.getUserByUsername(username) == null){
                User newuser = new User(username, password, firstname, lastname);
                mGradeAppDAO.insert(newuser);

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "Username already exists, please try again!", Toast.LENGTH_LONG).show();
                Newuser.setError("Incorrect username!");
            }
        });

    }

    private void getGradeAppDAO(){
        mGradeAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGradeAppDao();
    }
}
