package com.example.wk01hw02solo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wk01hw02solo.R;
import com.example.wk01hw02solo.db.AppDatabase;
import com.example.wk01hw02solo.db.Wk01hw02soloDao;
import com.example.wk01hw02solo.model.User;
import com.example.wk01hw02solo.utility.Utils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.wk01hw02solo.userIdKey";
    private static final String PREFS = "com.example.wk01hw02solo.prefs";

    private EditText etLoginUsername;
    private EditText etLoginPassword;

    private Wk01hw02soloDao wk01hw02soloDao;
    private String mUsername, mPassword;
    private User mUser;

    private int mUserId = -1;

    private SharedPreferences mPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase();
        checkForUser();
        wireUpDisplay();
    }

    private void getDatabase() {

        wk01hw02soloDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getWk01hw02soloDao();
    }

    private void checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(mUserId != -1) {
            switchActivityToLandingPageActivity(mUserId);
        }

        if(mPreferences == null) {
            getPrefs();
        }
        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        if(mUserId != -1) {
            switchActivityToLandingPageActivity(mUserId);
        }

        List<User> users = wk01hw02soloDao.getAllUsers();
        if(users.size() <= 0) {
            User user1 = new User("Bret", "Bret123");
            User user2 = new User("Antonette", "Antonette123");
            User user3 = new User("Samantha", "Samantha123");
            User user4 = new User("Karianne", "Karianne123");
            User user5 = new User("Kamren", "Kamren123");
            User user6 = new User("Leopoldo_Corkery", "Leopoldo_Corkery123");
            User user7 = new User("Elwyn.Skiles", "Elwyn.Skiles123");
            User user8 = new User("Maxime_Nienow", "Maxime_Nienow123");
            User user9 = new User("Delphine", "Delphine123");
            User user10 = new User("Moriah.Stanton", "Moriah.Stanton123");
            wk01hw02soloDao.insert(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10);
        }
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    private void switchActivityToLandingPageActivity(int mUserId) {
        Intent goToLandingPageActivity = LandingPageActivity.getIntent(MainActivity.this, mUserId);
        startActivity(goToLandingPageActivity);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    private void wireUpDisplay() {

        etLoginUsername = findViewById(R.id.et_login_username);
        etLoginPassword = findViewById(R.id.et_login_password);
        Button loginButton = findViewById(R.id.button_signIn);

        loginButton.setOnClickListener(v -> {

            getValuesFromDisplay();
            if (checkForUserInDatabase()) {
                etLoginUsername.setBackgroundColor(Color.WHITE);
                if (!validatePassword()) {
                    etLoginPassword.setBackgroundColor(Color.RED);
                    Toast.makeText(this,"Invalid Password", Toast.LENGTH_LONG).show();
                } else {
                    etLoginPassword.setBackgroundColor(Color.WHITE);
                    Intent intent = LandingPageActivity.getIntent(MainActivity.this, mUser.getUid());
                    startActivity(intent);
                }
            }
        });
    }

    private void getValuesFromDisplay() {

        mUsername = etLoginUsername.getText().toString();
        mPassword = etLoginPassword.getText().toString();
    }

    private boolean checkForUserInDatabase() {

        mUser = wk01hw02soloDao.getUserByUsername(mUsername);
        if (mUser == null) {
            etLoginUsername.setBackgroundColor(Color.RED);
            Toast.makeText(this,"Invalid Username", Toast.LENGTH_LONG).show();
            return false;
        }

        return Utils.verifyUsername(mUsername, mUser.getUsername());
    }

    private boolean validatePassword() {

        return Utils.verifyPassword(mPassword, mUser.getPassword());
    }

    private static void highlightString(TextView input) {

        String inputText = input.getText().toString();

        //Get the text from text view and create a spannable string
        SpannableString spannableString = new SpannableString(inputText);

        //Get the previous spans and remove them
        BackgroundColorSpan[] backgroundSpans = spannableString.getSpans(0, spannableString.length(), BackgroundColorSpan.class);

        for (BackgroundColorSpan span: backgroundSpans) {
            spannableString.removeSpan(span);
        }

        spannableString.setSpan(new BackgroundColorSpan(Color.RED), inputText.indexOf(inputText.charAt(0)), inputText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //Set the final text on TextView
        input.setText(spannableString);
    }
}