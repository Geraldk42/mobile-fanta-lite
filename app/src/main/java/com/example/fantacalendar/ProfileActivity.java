package com.example.fantacalendar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;  // Ensure AppCompatActivity is imported

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout onlineStatusDropdown, calendarAccessDropdown, accountTypeDropdown;
    private ImageView onlineStatusDropdownArrow, calendarAccessDropdownArrow, accountTypeDropdownArrow;
    private Switch notificationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Replace with your actual layout

        // Initialize components
        initViews();

        // Setup listeners
        setupListeners();
    }

    private void initViews() {
        // Initialize dropdown layouts
        onlineStatusDropdown = findViewById(R.id.online_status_dropdown);
        calendarAccessDropdown = findViewById(R.id.calendar_access_dropdown);
        accountTypeDropdown = findViewById(R.id.account_type_dropdown);

        // Initialize dropdown arrows
        onlineStatusDropdownArrow = findViewById(R.id.online_status_dropdown_arrow);
        calendarAccessDropdownArrow = findViewById(R.id.calendar_access_dropdown_arrow);
        accountTypeDropdownArrow = findViewById(R.id.account_type_dropdown_arrow);

        // Initialize the switch for notifications
        notificationSwitch = findViewById(R.id.notification_switch);
    }

    private void setupListeners() {
        // Online Status Dropdown Toggle
        findViewById(R.id.online_status_container).setOnClickListener(view -> toggleDropdown(onlineStatusDropdown, onlineStatusDropdownArrow));

        // Calendar Access Dropdown Toggle
        findViewById(R.id.calendar_access_container).setOnClickListener(view -> toggleDropdown(calendarAccessDropdown, calendarAccessDropdownArrow));

        // Account Type Dropdown Toggle
        findViewById(R.id.account_type_container).setOnClickListener(view -> toggleDropdown(accountTypeDropdown, accountTypeDropdownArrow));

        // Notification Switch Listener
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(ProfileActivity.this, "Notifications Enabled", Toast.LENGTH_SHORT).show();
                // Add your code for enabling notifications
            } else {
                Toast.makeText(ProfileActivity.this, "Notifications Disabled", Toast.LENGTH_SHORT).show();
                // Add your code for disabling notifications
            }
        });
    }

    // Function to toggle dropdown visibility and change arrow icon
    private void toggleDropdown(LinearLayout dropdownLayout, ImageView dropdownArrow) {
        if (dropdownLayout.getVisibility() == View.GONE) {
            dropdownLayout.setVisibility(View.VISIBLE);
        } else {
            dropdownLayout.setVisibility(View.GONE);
            dropdownArrow.setImageResource(R.drawable.dropdown_arrow); // Replace with arrow-down drawable
        }
    }
}
