//  MARIETTA

package com.example.linkcal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends BaseActivity {

    private Switch onlineStatusSwitch, calendarAccessSwitch, accountTypeSwitch, notificationSwitch;
    private Button manageFriendsButton;

    private TextView profileUsername, profileEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_profile); // Ensure this matches the updated XML layout


        setToolbarTitle("Profile");

        initViews();

        setupListeners();
        loadUserData();
    }



    private void initViews() {
        onlineStatusSwitch = findViewById(R.id.online_status_switch);
        calendarAccessSwitch = findViewById(R.id.calendar_access_switch);
        accountTypeSwitch = findViewById(R.id.account_type_switch);

        notificationSwitch = findViewById(R.id.notification_switch);

        manageFriendsButton = findViewById(R.id.manage_friends_button);


        profileUsername = findViewById(R.id.profile_username);
        profileEmail = findViewById(R.id.profile_email);

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> logout());
    }

    private void loadUserData() {
        String userEmail = PreferenceManager.getUserEmail(this);
        if (userEmail != null) {
            TextView emailTextView = findViewById(R.id.profile_email);
            TextView nameTextView = findViewById(R.id.profile_name);

            emailTextView.setText(userEmail);
            nameTextView.setText(userEmail.split("@")[0]);
        }
    }


    private void setupListeners() {
        onlineStatusSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(ProfileActivity.this, "Online Status: Visible", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Online Status: Hidden", Toast.LENGTH_SHORT).show();
            }
        });

        calendarAccessSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(ProfileActivity.this, "Calendar Access Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Calendar Access Disabled", Toast.LENGTH_SHORT).show();
            }
        });

        accountTypeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(ProfileActivity.this, "Account Type: Public", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Account Type: Private", Toast.LENGTH_SHORT).show();
            }
        });

        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(ProfileActivity.this, "Notifications Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Notifications Disabled", Toast.LENGTH_SHORT).show();
            }
        });


        //Jeff's comment
//        manageFriendsButton.setOnClickListener(v -> {
//            // Navigate to ManageFriendsActivity when button is clicked
//            Intent intent = new Intent(ProfileActivity.this, ManageFriendsActivity.class);
//            startActivity(intent);
//        });
    }
}
