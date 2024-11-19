package com.example.linkcal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    protected FrameLayout contentContainer;
    protected BottomNavigationView bottomNavigation;
    protected ImageButton backButton;
    protected TextView toolbarTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!PreferenceManager.isLoggedIn(this)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }


        setContentView(R.layout.activity_base);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });


        contentContainer = findViewById(R.id.content_container);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        toolbarTitle = findViewById(R.id.toolbarTitle);

        setupBackButton();
        setupBottomNavigation();
    }

    protected void logout() {
        PreferenceManager.clearSession(this);
        startActivity(new Intent(this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    private void setupBackButton() {
        backButton.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        if (!(this instanceof MainActivity)) {
            backButton.setVisibility(View.VISIBLE);
        }
    }

    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();


            if (itemId == R.id.navigation_home && !(this instanceof MainActivity)) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (itemId == R.id.navigation_profile) {
                if (!(this instanceof ProfileActivity)) {
                    startActivity(new Intent(this, ProfileActivity.class));
                    finish();
                }
                return true;
            } else if (itemId == R.id.navigation_chats) {
                if (!(this instanceof MessageActivity)) {
                    startActivity(new Intent(this, MessageActivity.class));
                    finish();
                }
                return true;
            }


//            else if (itemId == R.id.navigation_calendar && !(this instanceof CalendarActivity)) {
//                startActivity(new Intent(this, CalendarActivity.class));
//                return true;
//                }
//            } else if (itemId == R.id.nav_friends) {
//               startActivity(new Intent(this, FriendsActivity.class));
//                return true;
//            } else if (itemId == R.id.nav_profile) {
//             startActivity(new Intent(this, ProfileActivity.class));
//                return true;
//            }
            return false;
        });

        setSelectedNavigationItem();
    }


    protected void setSelectedNavigationItem() {
        if (this instanceof MainActivity) {
            bottomNavigation.setSelectedItemId(R.id.navigation_home);
        } else if (this instanceof ProfileActivity) {
            bottomNavigation.setSelectedItemId(R.id.navigation_profile);
        } else if (this instanceof MessageActivity) {
            bottomNavigation.setSelectedItemId(R.id.navigation_chats);
        }

    }

    protected void setToolbarTitle(String title) {
        toolbarTitle.setText(title);
    }

    protected void setContent(int layoutResID) {
        View.inflate(this, layoutResID, contentContainer);
    }
}