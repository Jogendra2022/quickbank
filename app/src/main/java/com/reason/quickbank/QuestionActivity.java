package com.reason.quickbank;

import android.content.res.ColorStateList;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    // Existing Views
    private RelativeLayout rootLayout;
    private TextView questionTextView, categoryTitleTextView;
    private ImageView categoryIcon;
    private Button choiceA, choiceB, choiceC, choiceD;
    private Button nextButton;
    private List<Button> choiceButtons;

    // New Timer Views
    private TextView timerTextView;
    private ProgressBar timerProgressBar;
    private CountDownTimer countDownTimer;
    private static final long QUESTION_TIME_LIMIT = 30000; // 30 seconds
    private long timeLeftInMillis;
    private int primaryColor; // Store primary color for the timer

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private LinearLayout scoreView;
    private TextView finalScoreText, scoreValueText;
    private Button goHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Initialize Views
        rootLayout = findViewById(R.id.question_layout_root);
        questionTextView = findViewById(R.id.question_text);
        categoryTitleTextView = findViewById(R.id.category_title);
        categoryIcon = findViewById(R.id.category_icon);
        choiceA = findViewById(R.id.choice_a);
        choiceB = findViewById(R.id.choice_b);
        choiceC = findViewById(R.id.choice_c);
        choiceD = findViewById(R.id.choice_d);
        nextButton = findViewById(R.id.next_button);



        // Initialize Timer Views
        timerTextView = findViewById(R.id.timer_text);
        timerProgressBar = findViewById(R.id.timer_progress);

        scoreView = findViewById(R.id.score_view);
        finalScoreText = findViewById(R.id.final_score_text);
        scoreValueText = findViewById(R.id.score_value_text);
        goHomeButton = findViewById(R.id.go_home_button);

        choiceButtons = new ArrayList<>();
        choiceButtons.add(choiceA);
        choiceButtons.add(choiceB);
        choiceButtons.add(choiceC);
        choiceButtons.add(choiceD);

        for (Button btn : choiceButtons) {
            btn.setOnClickListener(this);
        }

        nextButton.setOnClickListener(v -> {
            currentQuestionIndex++;
            displayQuestion();
        });

        // --- START: SET ONCLICK FOR HOME BUTTON ---
        goHomeButton.setOnClickListener(v -> finish());
        // --- END: SET ONCLICK FOR HOME BUTTON ---

        String category = getIntent().getStringExtra("CATEGORY");
        setThemeForCategory(category);
        loadQuestions(category);
        displayQuestion();
    }

    private void setThemeForCategory(String category) {
        int backgroundResId;
        int iconResId;
        categoryTitleTextView.setText(category);

        switch (category) {
            case "Math":
                primaryColor = ContextCompat.getColor(this, R.color.math_primary);
                backgroundResId = R.drawable.background_math;
                iconResId = R.drawable.ic_math;
                break;
            case "English":
                primaryColor = ContextCompat.getColor(this, R.color.english_primary);
                backgroundResId = R.drawable.background_english;
                iconResId = R.drawable.ic_english;
                break;
            case "Computer":
                primaryColor = ContextCompat.getColor(this, R.color.computer_primary);
                backgroundResId = R.drawable.background_computer;
                iconResId = R.drawable.ic_computer;
                break;
            case "Reasoning":
                primaryColor = ContextCompat.getColor(this, R.color.reasoning_primary);
                backgroundResId = R.drawable.background_reasoning;
                iconResId = R.drawable.ic_reasoning;
                break;
            case "Science":
                primaryColor = ContextCompat.getColor(this, R.color.science_primary);
                backgroundResId = R.drawable.background_science;
                iconResId = R.drawable.ic_science;
                break;
            case "Web Development":
                primaryColor = ContextCompat.getColor(this, R.color.web_primary);
                backgroundResId = R.drawable.background_web;
                iconResId = R.drawable.ic_web;
                break;
            case "Data Analyst":
                primaryColor = ContextCompat.getColor(this, R.color.data_analyst_primary);
                backgroundResId = R.drawable.background_data_analyst;
                iconResId = R.drawable.ic_data_analyst;
                break;
            case "Data Science":
                primaryColor = ContextCompat.getColor(this, R.color.data_science_primary);
                backgroundResId = R.drawable.background_data_science;
                iconResId = R.drawable.ic_data_science;
                break;
            default:
                primaryColor = ContextCompat.getColor(this, R.color.dark_gray);
                backgroundResId = R.color.light_gray;
                iconResId = 0;
                break;
        }

        rootLayout.setBackgroundResource(backgroundResId);
        categoryTitleTextView.setTextColor(primaryColor);
        timerTextView.setTextColor(primaryColor); // Set timer text color
        nextButton.setBackgroundColor(primaryColor);

        if (iconResId != 0) {
            categoryIcon.setImageResource(iconResId);
            categoryIcon.setImageTintList(ColorStateList.valueOf(primaryColor));
        }
        goHomeButton.setBackgroundColor(primaryColor);
        scoreValueText.setTextColor(primaryColor);
        // --- END: SET COLOR F
    }

    private void loadQuestions(String category) {
        questionList = new ArrayList<>();
        switch (category) {
            case "Math":
                questionList.add(new Question("What is 12 * 8?", new String[]{"A. 92", "B. 96", "C. 104", "D. 88"}, 1));
                questionList.add(new Question("What is the square root of 144?", new String[]{"A. 10", "B. 11", "C. 12", "D. 13"}, 2));
                questionList.add(new Question("Solve for x: 3x - 7 = 14", new String[]{"A. x = 5", "B. x = 6", "C. x = 7", "D. x = 8"}, 2));
                questionList.add(new Question("What is 25% of 200?", new String[]{"A. 40", "B. 50", "C. 60", "D. 75"}, 1));
                questionList.add(new Question("What is the area of a circle with a radius of 5?", new String[]{"A. 25π", "B. 10π", "C. 5π", "D. 100π"}, 0));
                questionList.add(new Question("What is 7 factorial (7!)?", new String[]{"A. 720", "B. 49", "C. 5040", "D. 1"}, 2));
                questionList.add(new Question("What is the next prime number after 13?", new String[]{"A. 15", "B. 16", "C. 17", "D. 19"}, 2));
                questionList.add(new Question("How many degrees are in a right angle?", new String[]{"A. 45", "B. 90", "C. 180", "D. 360"}, 1));
                questionList.add(new Question("What is the value of Pi (π) to two decimal places?", new String[]{"A. 3.12", "B. 3.14", "C. 3.16", "D. 3.18"}, 1));
                questionList.add(new Question("If a triangle has angles 50° and 70°, what is the third angle?", new String[]{"A. 50°", "B. 60°", "C. 70°", "D. 80°"}, 1));
                break;
            case "English":
                questionList.add(new Question("Which word is a synonym for 'ephemeral'?", new String[]{"A. Permanent", "B. Fleeting", "C. Strong", "D. Beautiful"}, 1));
                questionList.add(new Question("Identify the verb in: 'She quickly ran to the store.'", new String[]{"A. quickly", "B. ran", "C. store", "D. She"}, 1));
                questionList.add(new Question("What is the plural of 'cactus'?", new String[]{"A. Cactuses", "B. Cacti", "C. Cactus", "D. Cactii"}, 1));
                questionList.add(new Question("Choose the correct spelling:", new String[]{"A. Accomodate", "B. Acommodate", "C. Accommodate", "D. Acomodate"}, 2));
                questionList.add(new Question("What does the idiom 'bite the bullet' mean?", new String[]{"A. To eat something hard", "B. To get shot", "C. To stop talking", "D. To endure a difficult situation"}, 3));
                questionList.add(new Question("Which of the following is a preposition?", new String[]{"A. and", "B. jump", "C. under", "D. happy"}, 2));
                questionList.add(new Question("A story's main character is called the...", new String[]{"A. Antagonist", "B. Narrator", "C. Protagonist", "D. Author"}, 2));
                questionList.add(new Question("'Their', 'there', and 'they're' are examples of...", new String[]{"A. Synonyms", "B. Homophones", "C. Antonyms", "D. Acronyms"}, 1));
                questionList.add(new Question("What is the past tense of 'begin'?", new String[]{"A. Begun", "B. Began", "C. Beginned", "D. Begint"}, 1));
                questionList.add(new Question("Which punctuation mark is used to show possession?", new String[]{"A. Comma", "B. Period", "C. Apostrophe", "D. Semicolon"}, 2));
                break;
            case "Science":
                questionList.add(new Question("What is the chemical symbol for water?", new String[]{"A. H2O", "B. CO2", "C. O2", "D. NaCl"}, 0));
                questionList.add(new Question("What planet is known as the Red Planet?", new String[]{"A. Venus", "B. Jupiter", "C. Mars", "D. Saturn"}, 2));
                questionList.add(new Question("What is the powerhouse of the cell?", new String[]{"A. Nucleus", "B. Ribosome", "C. Mitochondrion", "D. Cell Wall"}, 2));
                questionList.add(new Question("What is the hardest natural substance on Earth?", new String[]{"A. Gold", "B. Iron", "C. Diamond", "D. Quartz"}, 2));
                questionList.add(new Question("How many bones are in the adult human body?", new String[]{"A. 206", "B. 210", "C. 195", "D. 300"}, 0));
                questionList.add(new Question("What force pulls objects toward the center of the Earth?", new String[]{"A. Magnetism", "B. Friction", "C. Gravity", "D. Inertia"}, 2));
                questionList.add(new Question("Which gas do plants absorb from the atmosphere?", new String[]{"A. Oxygen", "B. Nitrogen", "C. Carbon Dioxide", "D. Hydrogen"}, 2));
                questionList.add(new Question("What is the boiling point of water in Celsius?", new String[]{"A. 90°C", "B. 100°C", "C. 110°C", "D. 212°C"}, 1));
                questionList.add(new Question("What type of galaxy is the Milky Way?", new String[]{"A. Elliptical", "B. Irregular", "C. Spiral", "D. Lenticular"}, 2));
                questionList.add(new Question("Who developed the theory of relativity?", new String[]{"A. Isaac Newton", "B. Galileo Galilei", "C. Albert Einstein", "D. Stephen Hawking"}, 2));
                break;
            case "Web Development":
                questionList.add(new Question("What does HTML stand for?", new String[]{"A. HyperText Markup Language", "B. HighText Machine Language", "C. Hyperlink and Text Markup", "D. Home Tool Markup Language"}, 0));
                questionList.add(new Question("Which language is used for styling web pages?", new String[]{"A. HTML", "B. jQuery", "C. CSS", "D. XML"}, 2));
                questionList.add(new Question("What is the correct HTML for creating a hyperlink?", new String[]{"A. <link>http://a.com</link>", "B. <a url='...'>", "C. <a href='...'>", "D. <a>http://a.com</a>"}, 2));
                questionList.add(new Question("Which property is used to change the background color in CSS?", new String[]{"A. color", "B. bgcolor", "C. background-color", "D. background"}, 2));
                questionList.add(new Question("What does 'JS' stand for?", new String[]{"A. Java Source", "B. JavaScript", "C. Just-in-time Script", "D. JSON Script"}, 1));
                questionList.add(new Question("Which company developed JavaScript?", new String[]{"A. Microsoft", "B. Apple", "C. Netscape", "D. Sun Microsystems"}, 2));
                questionList.add(new Question("What is the most popular front-end framework in 2023?", new String[]{"A. Angular", "B. Vue", "C. React", "D. Svelte"}, 2));
                questionList.add(new Question("What does API stand for?", new String[]{"A. Application Programming Interface", "B. Advanced Programming Input", "C. Application Process Integration", "D. All Purpose Internet"}, 0));
                questionList.add(new Question("Which is a server-side JavaScript runtime environment?", new String[]{"A. React", "B. Node.js", "C. TypeScript", "D. Express"}, 1));
                questionList.add(new Question("What HTTP status code means 'Not Found'?", new String[]{"A. 200", "B. 404", "C. 500", "D. 301"}, 1));
                break;
            case "Data Analyst":
                questionList.add(new Question("Which tool is most commonly used for data manipulation and analysis in Python?", new String[]{"A. NumPy", "B. Pandas", "C. Scikit-learn", "D. Matplotlib"}, 1));
                questionList.add(new Question("What is the primary language for querying relational databases?", new String[]{"A. Python", "B. R", "C. SQL", "D. Java"}, 2));
                questionList.add(new Question("Which chart is best for showing the relationship between two continuous variables?", new String[]{"A. Bar Chart", "B. Pie Chart", "C. Scatter Plot", "D. Histogram"}, 2));
                questionList.add(new Question("What does 'ETL' stand for in data warehousing?", new String[]{"A. Extract, Transform, Load", "B. Execute, Test, Link", "C. Estimate, Transmit, Log", "D. Encode, Transfer, Launch"}, 0));
                questionList.add(new Question("Which function in SQL is used to count the number of rows?", new String[]{"A. SUM()", "B. COUNT()", "C. TOTAL()", "D. NUMBER()"}, 1));
                questionList.add(new Question("Missing data is often referred to as:", new String[]{"A. Null values", "B. Zero values", "C. Empty strings", "D. Bad data"}, 0));
                questionList.add(new Question("Which of these is a popular data visualization tool?", new String[]{"A. Excel", "B. Tableau", "C. PowerPoint", "D. Word"}, 1));
                questionList.add(new Question("What is the 'mean' of a dataset?", new String[]{"A. The middle value", "B. The most frequent value", "C. The average value", "D. The highest value"}, 2));
                questionList.add(new Question("What is 'data cleaning'?", new String[]{"A. Deleting all data", "B. Archiving old data", "C. Visualizing data", "D. Fixing or removing incorrect data"}, 3));
                questionList.add(new Question("Which SQL clause is used to filter query results?", new String[]{"A. FROM", "B. GROUP BY", "C. ORDER BY", "D. WHERE"}, 3));
                break;
            case "Data Science":
                questionList.add(new Question("Which of these is a supervised machine learning algorithm?", new String[]{"A. K-Means Clustering", "B. Linear Regression", "C. PCA", "D. Apriori"}, 1));
                questionList.add(new Question("What is 'overfitting' in machine learning?", new String[]{"A. Model is too simple", "B. Model performs well on new data", "C. Model performs well on training data but poorly on new data", "D. Using too little data"}, 2));
                questionList.add(new Question("Which Python library is primarily used for machine learning?", new String[]{"A. Pandas", "B. NumPy", "C. Scikit-learn", "D. TensorFlow"}, 2));
                questionList.add(new Question("What does 'NLP' stand for?", new String[]{"A. Natural Language Processing", "B. Neural Link Protocol", "C. Non-linear Programming", "D. New Logic Paradigm"}, 0));
                questionList.add(new Question("'Classification' and 'Regression' are types of:", new String[]{"A. Unsupervised Learning", "B. Supervised Learning", "C. Reinforcement Learning", "D. Data Cleaning"}, 1));
                questionList.add(new Question("What is the purpose of a 'training set'?", new String[]{"A. To test the final model", "B. To build/train the model", "C. To validate model parameters", "D. To pre-process data"}, 1));
                questionList.add(new Question("A 'neural network' is inspired by the:", new String[]{"A. Internet", "B. Human brain", "C. A computer's CPU", "D. A social network"}, 1));
                questionList.add(new Question("Which of these is used for creating deep learning models?", new String[]{"A. Scikit-learn", "B. Pandas", "C. TensorFlow", "D. SQL"}, 2));
                questionList.add(new Question("What is 'feature engineering'?", new String[]{"A. Selecting a model", "B. Creating new input variables from existing ones", "C. Tuning model hyperparameters", "D. Visualizing the output"}, 1));
                questionList.add(new Question("The 'accuracy' of a model is a measure of:", new String[]{"A. How fast it trains", "B. How many predictions it got right", "C. How complex it is", "D. How much memory it uses"}, 1));
                break;
            default:
                questionList.add(new Question("No questions found for this category.", new String[]{"A.", "B.", "C.", "D."}, 0));
                break;
        }
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            resetButtonsUI();
            nextButton.setVisibility(View.INVISIBLE);
            Question currentQuestion = questionList.get(currentQuestionIndex);
            String questionTextWithNumber = "Q. " + (currentQuestionIndex + 1) + ": " + currentQuestion.getQuestionText();
            questionTextView.setText(questionTextWithNumber);
            // --- END

            String[] choices = currentQuestion.getChoices();
            for (int i = 0; i < choiceButtons.size(); i++) {
                choiceButtons.get(i).setText(choices[i]);
                choiceButtons.get(i).setEnabled(true);
            }
            startTimer(); // Start the timer for the new question
        } else {
            showFinalScore();
        }
    }
    private void showFinalScore() {
        cancelTimer(); // Ensure timer is stopped


        // --- START: ADD THIS CODE TO SAVE THE SCORE ---

        // 1. Get the category name (you already have this from the Intent)
        String category = getIntent().getStringExtra("CATEGORY");

        // 2. Get access to SharedPreferences. We'll use one file named "user_scores".
        SharedPreferences prefs = getSharedPreferences("user_scores", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // 3. Save the score. The category name (e.g., "Math") is the key.
        //    This will overwrite any previous score for this category.
        editor.putInt(category, score);

        // 4. Apply the changes to save the data.
        editor.apply();

        // Hide all question-related views
        findViewById(R.id.header_section).setVisibility(View.GONE);
        findViewById(R.id.choices_layout).setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);

        // Get user's first name
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = "User"; // Default name
        if (user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
            String email = user.getEmail();
            userName = email.split("@")[0]; // Get text before "@"
            // Capitalize the first letter
            userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);
        }

        // Set the score texts
        finalScoreText.setText(userName + ", your score is");
        scoreValueText.setText(score + " / " + questionList.size());

        // Show the score view
        scoreView.setVisibility(View.VISIBLE);
    }

    private void startTimer() {
        timeLeftInMillis = QUESTION_TIME_LIMIT;
        timerProgressBar.setMax((int) (QUESTION_TIME_LIMIT / 100)); // max for progress bar

        countDownTimer = new CountDownTimer(timeLeftInMillis, 100) { // Update every 100ms
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int seconds = (int) (timeLeftInMillis / 1000);
                timerTextView.setText(String.format(Locale.getDefault(), "%02d", seconds));
                timerProgressBar.setProgress((int) (timeLeftInMillis / 100));
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                timerTextView.setText("00");
                timerProgressBar.setProgress(0);
                handleTimeUp();
            }
        }.start();
    }

    private void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void handleTimeUp() {
        // Disable all choices
        for (Button btn : choiceButtons) {
            btn.setEnabled(false);
        }

        // Mark the correct answer
        int correctChoiceIndex = questionList.get(currentQuestionIndex).getCorrectAnswerIndex();
        Button correctButton = choiceButtons.get(correctChoiceIndex);
        updateButtonUI(correctButton, R.color.correct_green, false);

        // Show Next button
        nextButton.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Time's up!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_button) return;

        cancelTimer(); // Stop the timer as soon as an answer is clicked

        Button clickedButton = (Button) v;
        int clickedChoiceIndex = choiceButtons.indexOf(clickedButton);
        checkAnswer(clickedButton, clickedChoiceIndex);
    }

    private void checkAnswer(Button selectedButton, int selectedChoiceIndex) {
        for (Button btn : choiceButtons) {
            btn.setEnabled(false);
        }

        Question currentQuestion = questionList.get(currentQuestionIndex);
        int correctChoiceIndex = currentQuestion.getCorrectAnswerIndex();

        if (selectedChoiceIndex == correctChoiceIndex) {
            score++;
            updateButtonUI(selectedButton, R.color.correct_green, true);
            // Go to next question after a short delay
            new Handler().postDelayed(() -> {
                currentQuestionIndex++;
                displayQuestion();
            }, 1000);
        } else {
            updateButtonUI(selectedButton, R.color.incorrect_red, true);
            Button correctButton = choiceButtons.get(correctChoiceIndex);
            updateButtonUI(correctButton, R.color.correct_green, false);
            nextButton.setVisibility(View.VISIBLE); // Show next button to proceed
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer(); // Prevent memory leaks
    }

    private void updateButtonUI(Button button, int colorResId, boolean isSelected) {
        GradientDrawable drawable = (GradientDrawable) button.getBackground().mutate();
        int color = ContextCompat.getColor(this, colorResId);

        if (isSelected) {
            drawable.setColor(color);
            drawable.setStroke(0, 0);
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            drawable.setStroke(4, color);
        }
    }

    private void resetButtonsUI() {
        for (Button btn : choiceButtons) {
            GradientDrawable drawable = (GradientDrawable) btn.getBackground().mutate();
            drawable.setColor(ContextCompat.getColor(this, R.color.white));
            drawable.setStroke(2, ContextCompat.getColor(this, R.color.medium_gray));
            btn.setTextColor(ContextCompat.getColor(this, R.color.dark_gray));
        }
    }
}
