package com.reason.quickbank;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Map;

public class ScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        // This is the LinearLayout we added to activity_scores.xml
        LinearLayout scoresContainer = findViewById(R.id.scores_container);

        // Get access to the same SharedPreferences file
        SharedPreferences prefs = getSharedPreferences("user_scores", MODE_PRIVATE);

        // Get all saved scores. The .getAll() method returns a Map of all key-value pairs.
        Map<String, ?> allScores = prefs.getAll();

        // Check if there are no scores saved yet
        if (allScores.isEmpty()) {
            displayNoScoresMessage(scoresContainer);
        } else {
            // Loop through each score and display it
            for (Map.Entry<String, ?> entry : allScores.entrySet()) {
                String category = entry.getKey();
                // Ensure the value is an Integer before casting
                if (entry.getValue() instanceof Integer) {
                    int score = (Integer) entry.getValue();

                    // Create a new TextView to display the score
                    TextView scoreTextView = createScoreTextView(category, score);

                    // Add the TextView to our container
                    scoresContainer.addView(scoreTextView);
                }
            }
        }
    }

    /**
     * Creates and styles a TextView to display a category and its score.
     */
    private TextView createScoreTextView(String category, int score) {
        TextView textView = new TextView(this);
        textView.setText(String.format("%s: %d", category, score));
        textView.setTextSize(18); // Set text size to 18sp
        textView.setTextColor(ContextCompat.getColor(this, R.color.dark_gray));

        // Set some layout parameters for spacing
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 16); // Add 16dp margin to the bottom
        textView.setLayoutParams(params);
        textView.setPadding(8, 8, 8, 8);

        return textView;
    }

    /**
     * Displays a message when no scores have been saved.
     */
    private void displayNoScoresMessage(LinearLayout container) {
        TextView noScoresText = new TextView(this);
        noScoresText.setText("You haven't completed any quizzes yet.");
        noScoresText.setTextSize(16);
        noScoresText.setGravity(Gravity.CENTER);
        container.addView(noScoresText);
    }
}
