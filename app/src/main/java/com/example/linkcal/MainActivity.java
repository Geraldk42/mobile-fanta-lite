package com.example.linkcal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_main);

        ImageButton calendarButton = findViewById(R.id.calendarButton);
        ImageButton schoolButtom = findViewById(R.id.eventCreationButton);
        ImageButton friendsButton = findViewById(R.id.friendsBtn);

        friendsButton.setOnClickListener(v -> startActivity(new Intent(this, CalendarActivity.class)));
        calendarButton.setOnClickListener(v -> startActivity(new Intent(this, CalendarActivity.class)));
        schoolButtom.setOnClickListener(v -> startActivity(new Intent(this, CalendarActivity.class)));
    }


}