package com.example.wk01hw02solo.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.wk01hw02solo.R;
import com.example.wk01hw02solo.db.AppDatabase;
import com.example.wk01hw02solo.db.Wk01hw02soloDao;
import com.example.wk01hw02solo.external.IExternalAPI;
import com.example.wk01hw02solo.model.User;
import com.example.wk01hw02solo.model.UserPostData;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingPageActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.wk01hw02solo.userIdKey";
    private static final String PREFS = "com.example.wk01hw02solo.prefs";

    private TextView welcomeView, userDetailsView;
    private SharedPreferences mPreferences = null;
    private Wk01hw02soloDao wk01hw02soloDao;
    private int mUserId = -1;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        getDatabase();
        wireUpDisplay();
        checkForUser();
        getUserDetails(mUserId);
        addUserToPreferences(mUserId);
        setWelcomeView();
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, LandingPageActivity.class);
    }

    public static Intent getIntent(Context context, int mUserId) {
        Intent intent = new Intent(context, LandingPageActivity.class);
        intent.putExtra(USER_ID_KEY, mUserId);
        return intent;
    }

    private void getUserDetails(int mUserId) {
        mUser = wk01hw02soloDao.getUserById(mUserId);
    }

    private void wireUpDisplay() {
        welcomeView = findViewById(R.id.textView_welcome);
        userDetailsView = findViewById(R.id.textView_user_details);
    }

    private void setWelcomeView() {


        String loggedInUsername = mUser.getUsername();
        String welcomeText = getString(R.string.tv_editable_welcome) + " " + loggedInUsername;
        welcomeView.setText(welcomeText);

        userDetailsView.setMovementMethod(new ScrollingMovementMethod());
        fetchUserPostDataFromExternal(mUser.getUid());
    }

    private void getDatabase() {

        wk01hw02soloDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getWk01hw02soloDao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.userMenuLogout) {
            processLogout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(mUser != null) {
            MenuItem item = menu.findItem(R.id.userMenuLogout);
            item.setTitle(mUser.getUsername());
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private void checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(mUserId != -1) {
            return;
        }

        if(mPreferences == null) {
            getPrefs();
        }
        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        if(mUserId != -1) {
            return;
        }

        Intent goToMainActivity = MainActivity.getIntent(LandingPageActivity.this);
        startActivity(goToMainActivity);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    private void processLogout() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setMessage(R.string.logout);

        dialogBuilder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            clearUserFromIntent();
            clearUserFromPrefs();
            mUserId = -1;
            checkForUser();
        });

        dialogBuilder.setNegativeButton(R.string.no, (dialogInterface, i) -> {

        });

        dialogBuilder.setOnCancelListener(dialog -> {

        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void addUserToPreferences(int mUserId) {

        if(mPreferences == null) {
            getPrefs();
        }

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, mUserId);
        editor.apply();
    }

    private void clearUserFromPrefs() {
        addUserToPreferences(-1);
    }

    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void fetchUserPostDataFromExternal(int userId) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IExternalAPI externalAPI = retrofit.create(IExternalAPI.class);
        Call<List<UserPostData>> userPostDataCall = externalAPI.getAllUserPostData();

        userPostDataCall.enqueue(new Callback<List<UserPostData>>() {
            @Override
            public void onResponse(Call<List<UserPostData>> call, Response<List<UserPostData>> response) {
                if (!response.isSuccessful()) {
                    userDetailsView.setText("Code: " + response.code());
                    return;
                }

                List<UserPostData> userPostDataResponseList = response.body();
                if(userPostDataResponseList != null && !userPostDataResponseList.isEmpty()) {
                    userDetailsView.setText(processUserPostDataByUserId(userId, userPostDataResponseList));
                }
            }
            @Override
            public void onFailure(Call<List<UserPostData>> call, Throwable t) {
                userDetailsView.setText(t.getMessage());
            }
        });

    }

    private String processUserPostDataByUserId(int userId, List<UserPostData> userPostDataList) {

        StringBuilder stringBuilder = new StringBuilder();
        if(!userPostDataList.isEmpty()) {

            for (UserPostData data : userPostDataList) {
                if(data.getUserId() == userId) {
                    stringBuilder.append(data.toString()).append("\n\n");
                }
            }
        }

        return stringBuilder.toString();
    }
}