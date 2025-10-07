package com.reason.quickbank;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check the user's login status from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        Intent intent;
        if (isLoggedIn) {
            // If the user is logged in, go directly to the dashboard
            intent = new Intent(MainActivity.this, CategoryActivity.class);
        } else {
            // If the user is not logged in, go to the Login screen
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }

        // Start the determined activity
        startActivity(intent);

        // Finish this MainActivity so the user cannot press "back" to return to it
        finish();
    }
}
