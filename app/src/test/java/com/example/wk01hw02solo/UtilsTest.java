package com.example.wk01hw02solo;

import android.content.Context;
import android.content.Intent;

import com.example.wk01hw02solo.activity.MainActivity;
import com.example.wk01hw02solo.model.User;
import com.example.wk01hw02solo.utility.Utils;

import org.junit.Test;

import java.util.regex.Matcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class UtilsTest {

    @Test
    public void checkUsername() {

        User user = new User("Kamren", "kamren123");

        boolean isValid = Utils.verifyUsername("Kamren", user.getUsername());

        assertTrue(isValid);

        user.setUsername("Antonette");

        isValid = Utils.verifyUsername("Kamren", user.getUsername());

        assertFalse(isValid);
    }

    @Test
    public void checkPassword() {

        User user = new User("Kamren", "kamren123");

        boolean isValid = Utils.verifyPassword("kamren123", user.getPassword());

        assertTrue(isValid);

        user.setPassword("Antonette123");

        isValid = Utils.verifyPassword("kamren123", user.getPassword());

        assertFalse(isValid);
    }
}
