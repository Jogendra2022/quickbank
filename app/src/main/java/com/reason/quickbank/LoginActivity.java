package com.reason.quickbank;

import android.content.Intent;
import android.content.SharedPreferences; // 1. ADD THIS IMPORT
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.login_email);
        passwordField = findViewById(R.id.login_password);
        Button loginButton = findViewById(R.id.login_button);
        TextView gotoSignup = findViewById(R.id.goto_signup);

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim(); // Use trim() for safety
            String password = passwordField.getText().toString().trim();
            if (!email.isEmpty() && !password.isEmpty()) {
                loginUser(email, password);
            } else {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        gotoSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        // 2. START: SAVE THE LOGIN STATE
                        // Get SharedPreferences editor to save session data
                        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        // Save a flag indicating the user is logged in
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply(); // Use apply() to save asynchronously
                        // END: SAVE THE LOGIN STATE

                        // Sign in success, update UI and go to the next activity
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, CategoryActivity.class));
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
