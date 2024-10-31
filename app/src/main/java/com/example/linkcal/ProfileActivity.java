//  MARIETTA

package com.example.linkcal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class ProfileActivity extends BaseActivity {

    // Declare switches and button for the updated layout
    private Switch onlineStatusSwitch, calendarAccessSwitch, accountTypeSwitch, notificationSwitch;
    private Button manageFriendsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_profile); // Ensure this matches the updated XML layout


        setToolbarTitle("Profile");

        // Initialize components (Switches and Button)
        initViews();

        // Setup listeners for switches and button
        setupListeners();
    }

    // Initialize views for switches and manage friends button
    private void initViews() {
        // Initialize the switches for online status, calendar access, and account type
        onlineStatusSwitch = findViewById(R.id.online_status_switch);
        calendarAccessSwitch = findViewById(R.id.calendar_access_switch);
        accountTypeSwitch = findViewById(R.id.account_type_switch);

        // Initialize the switch for notifications (already existed in your code)
        notificationSwitch = findViewById(R.id.notification_switch);

        // Initialize the button for managing friends
        manageFriendsButton = findViewById(R.id.manage_friends_button);
    }

    // Setup listeners for the new switches and button
    private void setupListeners() {
        // Listener for Online Status Switch
        onlineStatusSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(ProfileActivity.this, "Online Status: Visible", Toast.LENGTH_SHORT).show();
                // Add your logic for enabling online status visibility
            } else {
                Toast.makeText(ProfileActivity.this, "Online Status: Hidden", Toast.LENGTH_SHORT).show();
                // Add your logic for disabling online status visibility
            }
        });

        // Listener for Calendar Access Switch
        calendarAccessSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(ProfileActivity.this, "Calendar Access Enabled", Toast.LENGTH_SHORT).show();
                // Add your logic for enabling calendar access
            } else {
                Toast.makeText(ProfileActivity.this, "Calendar Access Disabled", Toast.LENGTH_SHORT).show();
                // Add your logic for disabling calendar access
            }
        });

        // Listener for Account Type Switch
        accountTypeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(ProfileActivity.this, "Account Type: Public", Toast.LENGTH_SHORT).show();
                // Add your logic for setting account type to public
            } else {
                Toast.makeText(ProfileActivity.this, "Account Type: Private", Toast.LENGTH_SHORT).show();
                // Add your logic for setting account type to private
            }
        });

        // Listener for Notifications Switch (existing logic)
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(ProfileActivity.this, "Notifications Enabled", Toast.LENGTH_SHORT).show();
                // Add your code for enabling notifications
            } else {
                Toast.makeText(ProfileActivity.this, "Notifications Disabled", Toast.LENGTH_SHORT).show();
                // Add your code for disabling notifications
            }
        });

        // Listener for "Manage Friends" button

        //Jeff's comment
//        manageFriendsButton.setOnClickListener(v -> {
//            // Navigate to ManageFriendsActivity when button is clicked
//            Intent intent = new Intent(ProfileActivity.this, ManageFriendsActivity.class);
//            startActivity(intent);
//        });
    }
}
