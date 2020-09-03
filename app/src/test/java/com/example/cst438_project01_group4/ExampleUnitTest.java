package com.example.cst438_project01_group4;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.cst438_project01_group4.ClassObjects.User;
import com.example.cst438_project01_group4.DataBase.AppDatabase;
import com.example.cst438_project01_group4.DataBase.UserDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;


public class ExampleUnitTest {

//    private UserDAO userDAO;
//
//    @Before
//    public void getDatabase() {
//        MainActivity m = new MainActivity();
//        userDAO = m.getDatabase();
//    }
//
//    @Test
//    public void add_user() {
//        assertNull(userDAO);
//        User newUser = new User("test", "password", "testName", "lastName");
//        userDAO.insert(newUser);
//        User retrieveUser = userDAO.getUserByUsername("testName");
//        assertEquals(newUser, retrieveUser);
//    }
//
//    @Test
//    public void delete_user() {
//        User newUser = new User("test", "password", "testName", "lastName");
//        userDAO.insert(newUser);
//        userDAO.delete(newUser);
//        assertNull(newUser);
//    }
}