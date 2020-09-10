package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.Intent;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cst438_project01_group4.ClassObjects.User;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private GradeAppDAO gradeAppDAO;

    // set up the dao object before running tests
    @Before
    public void setUp(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        gradeAppDAO = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getGradeAppDao();
    }

    //checks to see if the user class is functional
    @Test
    public void testUsername() {
        User newUser = new User("username", "password","foo", "bar");
        gradeAppDAO.insert(newUser);
        User retrieveUser = gradeAppDAO.getUserByUsername("username");
        assertEquals(newUser.getUsername(), retrieveUser.getUsername());
        assertNotEquals("abc", newUser.getUsername());
    }

    // TODO: check every class functionality

    // TODO: supposed to check if the checkforuserindatabase function -- not working currently
    // TODO: go to office hours for help
    @Test
    public void testCheckForUserInDatabase() {
        User newUser = new User("username", "password","foo", "bar");
        gradeAppDAO.insert(newUser);
        MainActivity mainActivity = new MainActivity();
        assertTrue(mainActivity.testCheckForUserInDatabase("username"));
    }

    // tests if the IntentFactory for the EditCourseActivity is working alright
    @Test
    public void getIntentTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = EditCourseActivity.getIntent(appContext,100);
        assertNotNull(intent);
        assertNotNull(intent.getExtras());
    }

    //TODO: add intent tests for all of the activities


    //checks to see if the user class is functional
    @Test
    public void testPassword() {
        User newUser = new User("username", "password","foo", "bar");
        gradeAppDAO.insert(newUser);
        User retrieveUser = gradeAppDAO.getUserByUsername("username");
        assertEquals(newUser.getPassword(), retrieveUser.getPassword());
        assertNotEquals("abc", newUser.getPassword());
    }

    // generic preconstucted test
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.cst438_project01_group4", appContext.getPackageName());
    }
}
