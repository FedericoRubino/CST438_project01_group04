package com.example.cst438_project01_group4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cst438_project01_group4.ClassObjects.Course;
import com.example.cst438_project01_group4.ClassObjects.User;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

import java.util.Date;

public class EditUserActivity extends AppCompatActivity {

    private TextView editUser;
    private EditText editUsername;
    private EditText editPassword;
    private EditText editFirstname;
    private EditText editLastname;
    private GradeAppDAO mGradeAppDAO;
    private String preUsername;
    private String prePassword;
    private String preFirstname;
    private String preLastname;
    private User muser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        Intent intent = getIntent();
        String username = intent.getStringExtra("");

        getGradeAppDAO();

        editUser = findViewById(R.id.tvUseredit);
        editUsername = findViewById(R.id.etEditusername);
        editPassword = findViewById(R.id.etEditpassword);
        editFirstname = findViewById(R.id.etEditfirstname);
        editLastname = findViewById(R.id.etEditlastname);

        muser = mGradeAppDAO.getUserByUsername(username);

        editUser.setText("Hello " + username + "/nWelcome to edit your information");

        if(muser != null){
            preUsername = muser.getUsername();
            prePassword = muser.getPassword();
            preFirstname = muser.getFirstName();
            preLastname = muser.getLastName();

            editUsername.setHint(preUsername);
            editPassword.setHint(prePassword);
            editFirstname.setHint(preFirstname);
            editLastname.setHint(preLastname);
        }
    }

    public void confirm(View view){
        //This is where we check input
        //Then we update the database
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please confirm changes");
        User editUser = getEditUserInfo();
        builder.setMessage(editUser.toString());
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateUser();
                mGradeAppDAO.update(muser);
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

    private User getEditUserInfo(){
        String username;
        String password;
        String firstname;
        String lastname;

        if(!editUsername.getText().toString().isEmpty()){
            username = editUsername.getText().toString();
        }
        else {
            username = preUsername;
        }

        if(!editPassword.getText().toString().isEmpty()){
            password = editPassword.getText().toString();
        }
        else {
            password = prePassword;
        }

        if(!editFirstname.getText().toString().isEmpty()){
            firstname = editFirstname.getText().toString();
        }
        else {
            firstname = preFirstname;
        }
        if(!editLastname.getText().toString().isEmpty()){
            lastname = editLastname.getText().toString();
        }
        else {
            lastname = preLastname;
        }

        return new User(username, password, firstname, lastname);
    }

    private void updateUser(){

        if(!editUsername.getText().toString().isEmpty()){
            muser.setUsername(editUsername.getText().toString());
        }

        if(!editPassword.getText().toString().isEmpty()){
            muser.setPassword(editPassword.getText().toString());
        }

        if(!editFirstname.getText().toString().isEmpty()){
            muser.setFirstName(editFirstname.getText().toString());
        }

        if(!editLastname.getText().toString().isEmpty()){
            muser.setLastName(editLastname.getText().toString());
        }
    }

    private void getGradeAppDAO(){
        mGradeAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGradeAppDao();
    }
}
