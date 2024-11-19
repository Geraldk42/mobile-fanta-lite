// SignUpActivity.java
package com.example.linkcal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.linkcal.helpers.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput, confirmPasswordInput, nameInput;
    private Button signupButton;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (PreferenceManager.isLoggedIn(this)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        initViews();
        setupListeners();
    }

    private void initViews() {
        emailInput = findViewById(R.id.signupEmailInput);
        passwordInput = findViewById(R.id.signupPasswordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        nameInput = findViewById(R.id.nameInput);
        signupButton = findViewById(R.id.signupButton);
        loginLink = findViewById(R.id.loginLink);
    }

    private void setupListeners() {
        signupButton.setOnClickListener(v -> attemptSignUp());
        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void attemptSignUp() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();
        String name = nameInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.contains("@") || password.length() < 6) {
            Toast.makeText(this, "Invalid email or password too short", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        signupButton.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseHelper.getInstance().setDocument(
                            "users",
                            authResult.getUser().getUid(),
                            Map.of(
                                    "email", email,
                                    "name", name
                            ),
                            aVoid -> {
                                PreferenceManager.saveUserSession(this,
                                        authResult.getUser().getUid(),
                                        email);
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                            },
                            e -> Toast.makeText(this, "Error saving user data",
                                    Toast.LENGTH_SHORT).show()
                    );
                })
                .addOnFailureListener(e -> {
                    signupButton.setEnabled(true);
                    Toast.makeText(this, "Sign up failed: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });

//
//        PreferenceManager.saveUserSession(this, "link_calToken", email);
//        startActivity(new Intent(this, MainActivity.class)
//                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
//        finish();
    }


}