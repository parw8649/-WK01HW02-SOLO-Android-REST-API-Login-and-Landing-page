package com.example.wk01hw02solo;

import android.content.Context;
import android.content.Intent;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.wk01hw02solo.activity.LandingPageActivity;
import com.example.wk01hw02solo.activity.MainActivity;
import com.example.wk01hw02solo.db.AppDatabase;
import com.example.wk01hw02solo.db.Wk01hw02soloDao;
import com.example.wk01hw02solo.model.User;
import com.example.wk01hw02solo.utility.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class WK01HW02SOLOTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Wk01hw02soloDao wk01hw02soloDao = Room.databaseBuilder(appContext, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getWk01hw02soloDao();


        // Username verification Test
        User user = new User("Kamren", "kamren123");

        wk01hw02soloDao.insert(user);

        boolean isValid = Utils.verifyUsername("Kamren", user.getUsername());

        assertTrue(isValid);

        user.setUsername("Antonette");

        wk01hw02soloDao.update(user);

        isValid = Utils.verifyUsername("Kamren", user.getUsername());

        assertFalse(isValid);

        // Password Verification Test
        isValid = Utils.verifyPassword("kamren123", user.getPassword());

        assertTrue(isValid);

        user.setPassword("Antonette123");

        wk01hw02soloDao.update(user);

        isValid = Utils.verifyPassword("kamren123", user.getPassword());

        assertFalse(isValid);

        assertEquals("com.example.wk01hw02solo", appContext.getPackageName());
    }

    @Test
    public void verifyIntent() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Intent intent = MainActivity.getIntent(appContext);
        intent.putExtra("Test", "intentWithData");

        assertEquals("intentWithData", intent.getStringExtra("Test"));

        intent = new Intent();
        intent.putExtra("Test1", "intentWithData2");

        assertEquals("intentWithData2", intent.getStringExtra("Test1"));
    }
}
