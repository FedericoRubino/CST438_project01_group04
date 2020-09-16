package com.example.cst438_project01_group4;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cst438_project01_group4.ClassObjects.Assignment;
import com.example.cst438_project01_group4.ClassObjects.Course;
import com.example.cst438_project01_group4.ClassObjects.Grade;
import com.example.cst438_project01_group4.ClassObjects.GradeCategory;
import com.example.cst438_project01_group4.ClassObjects.User;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.GradeAppDAO;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    //checks to see if the dao is functional
    @Test
    public void testUsername() {
        User newUser = new User("username", "password","foo", "bar");
        gradeAppDAO.insert(newUser);
        User retrieveUser = gradeAppDAO.getUserByUsername("username");
        assertEquals(newUser.getUsername(), retrieveUser.getUsername());
        assertNotEquals("abc", newUser.getUsername());
    }

    //checks to see if the dao is functional
    @Test
    public void testPassword() {
        User newUser = new User("username", "password","foo", "bar");
        gradeAppDAO.insert(newUser);
        User retrieveUser = gradeAppDAO.getUserByUsername("username");
        assertEquals(newUser.getPassword(), retrieveUser.getPassword());
        assertNotEquals("abc", newUser.getPassword());
    }

    /**
     * ------------------------------------------------------
     * Tests for the different classes
     * ------------------------------------------------------
     */

    /**
     * test Assignment class
     */
    @Test
    public void testAssignment(){
        Assignment assignment = null;
        assertNull(assignment);
        assignment = new Assignment("detail",100,80,new Date(),new Date(),0,0);
        assertNotNull(assignment);
        assertEquals("detail", assignment.getDetails());
        assertEquals(100, assignment.getMaxScore());
        assertEquals(80, assignment.getEarnedScore(), 0.0);
        assertNotNull(assignment.getAssignedDate());
        assertNotNull(assignment.getDueDate());
        assertEquals(0, assignment.getCategoryID());
        assertEquals(0, assignment.getCourseID());
    }

 /**
     * test User class
     */
    @Test
    public void testUser(){
        User user = null;
        assertNull(user);
        user = new User("username", "password","foo", "bar");
        assertNotNull(user);
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("foo", user.getFirstName());
        assertEquals("bar", user.getLastName());
    }

    /**
     * test Course class
     */
    @Test
    public void testCourse(){
        Course course = null;
        assertNull(course);
        course = new Course(0,"instructor","title", "foobar", new Date(),new Date());
        assertNotNull(course);
        assertEquals("instructor", course.getInstructor());
        assertEquals("title", course.getTitle());
        assertEquals("foobar", course.getDescription());
        assertNotNull(course.getStartDate());
        assertNotNull(course.getEndDate());
        assertEquals(0, course.getUserID());
        assertEquals(0, course.getCourseID());
    }

    /**
     * test Grade class
     */
    @Test
    public void testGrade(){
        Grade grade = null;
        assertNull(grade);
        grade = new Grade(100,0, new Date(),0);
        assertNotNull(grade);
        assertEquals(100, grade.getScore(), 0.0);
        assertNotNull(grade.getDateEarned());
        assertEquals(0, grade.getUserID());
        assertEquals(0, grade.getAssignmentID());
    }

    /**
     * test GradeCategory class
     */
    @Test
    public void testGradeCategory(){
        GradeCategory gradeCategory = null;
        assertNull(gradeCategory);
        gradeCategory = new GradeCategory("title", 50,0);
        assertNotNull(gradeCategory);
        assertEquals(gradeCategory.getTitle(),"title");
//        assertEquals(gradeCategory.getCourseID(), 0);
        assertEquals(50, gradeCategory.getWeight(), 0.0);
    }

    /**
     * Tests if the checkForUserInDatabase functions
     * This function can be found in Mainactivity
     */
    @Test
    public void testCheckForUserInDatabase() {
        User user = new User("username", "password","foo", "bar");
        gradeAppDAO.insert(user);
        User mUser = null;
        assertNull(mUser);
        mUser = gradeAppDAO.getUserByUsername("username");
        assertNotNull(mUser);
    }

    /**
     * Tests if the checkForUserInDatabase functions
     * This function can be found in Mainactivity
     */
    @Test
    public void testgradeCategories() {
        GradeCategory gradeCategory00 = new GradeCategory("title", 50,0);
        assertNotNull(gradeCategory00);
        GradeCategory gradeCategory01 = new GradeCategory("title01", 50,0);
        assertNotNull(gradeCategory01);
        GradeCategory gradeCategory02 = new GradeCategory("title02", 50,0);
        assertNotNull(gradeCategory02);
        List<GradeCategory> categories = new ArrayList<>();
        assertNotNull(categories);
        assertTrue(categories.isEmpty());
        categories.add(gradeCategory00);
        categories.add(gradeCategory01);
        categories.add(gradeCategory02);
        assertFalse(categories.isEmpty());
        // set up has been complete
        List<String> stringCategories = new ArrayList<String>();
        assertTrue(stringCategories.isEmpty());
        for(GradeCategory c: categories){
            stringCategories.add(c.getTitle());
        }
        assertFalse(stringCategories.isEmpty());
    }

    // testing the logging status of the user
    @Test
    public void testLogInStatus(){
        User user = null;
        assertNull(user);
        user = new User("username", "password", "firstname", "lastname");
        gradeAppDAO.insert(user);
        assertNotNull(gradeAppDAO.getUserByUsername(user.getUsername()));
        //established that user is in the database!
        assertNull(gradeAppDAO.getLoggedInUser());
        gradeAppDAO.setLoggedInUser(user.getUsername());
        assertNotNull(gradeAppDAO.getLoggedInUser());
        assertEquals(user.getUsername(), gradeAppDAO.getLoggedInUser().getUsername());
        gradeAppDAO.logOutAllUsers();
        assertNull(gradeAppDAO.getLoggedInUser());
    }

    // tests if the IntentFactory for the EditCourseActivity is working alright
    @Test
    public void getIntentEditCourseActivityTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = EditCourseActivity.getIntent(appContext,100);
        assertNotNull(intent);
        assertNotNull(intent.getExtras());
    }

    // tests if the IntentFactory for the EditCourseActivity is working alright
    @Test
    public void getIntentEditAssignmentActivityTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = EditAssignmentActivity.getIntent(appContext,100);
        assertNotNull(intent);
        assertNotNull(intent.getExtras());
    }

    // tests if the IntentFactory for the EditCourseActivity is working alright
    @Test
    public void getIntentManageCoursesActivityTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = ManageCourses.getIntent(appContext,100);
        assertNotNull(intent);
        assertNotNull(intent.getExtras());
    }

    // generic pre constructed test
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.cst438_project01_group4", appContext.getPackageName());
    }
}
