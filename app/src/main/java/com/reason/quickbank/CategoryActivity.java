package com.reason.quickbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        // --- START: ADD THESE LINES ---
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Choose a Category");
        }



        // --- Keep your existing card setups ---
        setupCategoryCard(R.id.card_math, R.drawable.new_math_icon, "Math", R.color.math_primary);
        setupCategoryCard(R.id.card_english, R.drawable.new_english_icon, "English", R.color.english_primary);
        setupCategoryCard(R.id.card_computer, R.drawable.new_computer_icon, "Computer", R.color.computer_primary);
        setupCategoryCard(R.id.card_reasoning, R.drawable.new_reasoning_icon, "Reasoning", R.color.reasoning_primary);

        // --- ADD THESE NEW LINES FOR THE NEW CATEGORIES ---
        setupCategoryCard(R.id.card_science, R.drawable.new_science_icon, "Science", R.color.science_primary);
        setupCategoryCard(R.id.card_web, R.drawable.new_web_developing_icon, "Web Development", R.color.web_primary);
        setupCategoryCard(R.id.card_data_analyst, R.drawable.new_data_analyst_icon, "Data Analyst", R.color.data_analyst_primary);
        setupCategoryCard(R.id.card_data_science, R.drawable.nwe_data_science_icon, "Data Science", R.color.data_science_primary);

        Button seeScoresButton = findViewById(R.id.see_scores_button);

        // Set a click listener
        seeScoresButton.setOnClickListener(v -> {
            // Create an Intent to open the ScoresActivity
            Intent intent = new Intent(CategoryActivity.this, ScoresActivity.class);
            startActivity(intent);
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout_button) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(CategoryActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        // 1. Sign out the user from Firebase Authentication
        com.google.firebase.auth.FirebaseAuth.getInstance().signOut();

        // 2. Clear the saved login state from SharedPreferences
        android.content.SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.clear(); // This removes all data (including the "isLoggedIn" flag)
        editor.apply();

        // 3. Create an Intent to go back to the LoginActivity
        Intent intent = new Intent(CategoryActivity.this, LoginActivity.class);

        // These flags clear the activity stack so the user cannot press "back" to return here
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Start the login activity
        startActivity(intent);

        // Close the current activity
        finish();
    }


    private void setupCategoryCard(int cardId, int iconId, String categoryName, int colorId) {
        // Find the included layout view
        View cardLayout = findViewById(cardId);

        // Safety check in case the view is not found
        if (cardLayout == null) {
            return;
        }

        CardView cardView = (CardView) cardLayout;

        // Find the elements inside the card
        ImageView icon = cardLayout.findViewById(R.id.card_icon);
        TextView title = cardLayout.findViewById(R.id.card_title);

        // Set the data and styles
        icon.setImageResource(iconId);
        title.setText(categoryName);
        cardView.setCardBackgroundColor(ContextCompat.getColor(this, colorId));

        // Set the click listener
        cardLayout.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryActivity.this, QuestionActivity.class);
            intent.putExtra("CATEGORY", categoryName);
            startActivity(intent);
        });
    }
}
