package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.cst438_project01_group4.ClassObjects.User;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

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
    private User mUser;
    String username;
    String password;
    String firstname;
    String lastname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        Intent intent = getIntent();


        getGradeAppDAO();
        String username = mGradeAppDAO.getUserByUserID(intent.getIntExtra("EXTRA", -1)).getUsername();;

        editUser = findViewById(R.id.tvUseredit);
        editUsername = findViewById(R.id.etEditusername);
        editPassword = findViewById(R.id.etEditpassword);
        editFirstname = findViewById(R.id.etEditfirstname);
        editLastname = findViewById(R.id.etEditlastname);

        mUser = mGradeAppDAO.getUserByUsername(username);

        editUser.setText("Hello " + username + "!" + "\nEdit your information here");

        if(mUser != null){
            preUsername = mUser.getUsername();
            prePassword = mUser.getPassword();
            preFirstname = mUser.getFirstName();
            preLastname = mUser.getLastName();

            editUsername.setHint(preUsername);
            editPassword.setHint(prePassword);
            editFirstname.setHint(preFirstname);
            editLastname.setHint(preLastname);
        }

        findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditUserInfo();
            }
        });
        findViewById(R.id.exitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewIntent = ManageCourses.getIntent(getApplicationContext(), intent.getIntExtra("EXTRA", -1));
                startActivity(startNewIntent);
            }
        });
    }

    public void confirm(User user){
        //This is where we check input
        //Then we update the database
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please confirm changes");
        User editUser = user;
        builder.setMessage(editUser.toString());
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateUser();
                mGradeAppDAO.update(mUser);
                Intent intent = ManageCourses.getIntent(getApplicationContext(), mUser.getUserID());
                startActivity(intent);
            }

        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = ManageCourses.getIntent(getApplicationContext(), 0);
                startActivity(intent);
            }

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getEditUserInfo(){
            username = editUsername.getText().toString();
            password = editPassword.getText().toString();
            firstname = editFirstname.getText().toString();
            lastname = editLastname.getText().toString();
        if(!username.equals("")) {
            User checkUser = mGradeAppDAO.getUserByUsername(username);
            if(checkUser != null){
                Toast.makeText(getApplicationContext(), "A user with that username already exists", Toast.LENGTH_LONG).show();
                return;
            }
        }
        checkValues();

        confirm(new User(username, password, firstname, lastname));

    }

    private void checkValues(){
        if(username.equals("")) {
            username = preUsername;
        }

        if(password.equals("")){
            password = prePassword;
        }

        if(firstname.equals("")){
            firstname = preFirstname;
        }

        if(lastname.equals("")){
           lastname = preLastname;
        }
    }

    private void updateUser(){
        mUser.setUsername(username);
        mUser.setPassword(password);
        mUser.setFirstName(firstname);
        mUser.setLastName(lastname);
    }

    private void getGradeAppDAO(){
        mGradeAppDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGradeAppDao();
    }

    /**
     * Intent factory
     */
    public static Intent getIntent(Context context, int value){
        Intent intent = new Intent(context, EditUserActivity.class);
        intent.putExtra("EXTRA",value);

        return intent;
    }
}
