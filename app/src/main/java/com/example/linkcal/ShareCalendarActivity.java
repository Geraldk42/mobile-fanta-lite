package com.example.linkcal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ShareCalendarActivity extends BaseActivity {
    private TextView userIdText;
    private EditText friendIdInput;
    private Button shareButton, linkButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_share_calendar);
        setToolbarTitle("Share Calendar");

        initializeFirebase();
        initViews();
        displayUserId();
        setupListeners();
    }

    private void initializeFirebase() {
        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users");
    }

    private void initViews() {
        userIdText = findViewById(R.id.userIdText);
        friendIdInput = findViewById(R.id.friendIdInput);
        shareButton = findViewById(R.id.shareButton);
        linkButton = findViewById(R.id.linkButton);


    }

    private void displayUserId() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail =currentUser.getUid();
            userIdText.setText(userEmail);
        }
    }

    private void setupListeners() {
        shareButton.setOnClickListener(v -> shareUserId());
        linkButton.setOnClickListener(v -> linkWithFriend());
    }

    private void shareUserId() {
        String userEmail = userIdText.getText().toString();
        String shareMessage = "Hey, link your calendar with me using my ID: " + userEmail;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Link Calendars on LinkCal");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    private void linkWithFriend() {
        String friendEmail = friendIdInput.getText().toString().trim();
        if (friendEmail.isEmpty()) {
            Toast.makeText(this, "Please enter friend's email", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) return;

        String currentUserEmail = currentUser.getEmail();
        HashMap<String, Object> linkData = new HashMap<>();
        linkData.put("linkedWith", friendEmail);

        databaseRef.child(currentUser.getUid()).updateChildren(linkData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(ShareCalendarActivity.this,
                            "Calendar shared successfully!", Toast.LENGTH_SHORT).show();
                    friendIdInput.setText("");
                })
                .addOnFailureListener(e -> Toast.makeText(ShareCalendarActivity.this,
                        "Failed to share calendar", Toast.LENGTH_SHORT).show());
    }
}