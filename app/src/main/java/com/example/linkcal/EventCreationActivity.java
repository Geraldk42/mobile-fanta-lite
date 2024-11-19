package com.example.linkcal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.linkcal.helpers.FirebaseHelper;
import com.example.linkcal.models.Event;
import com.example.linkcal.BaseActivity;

public class EventCreationActivity extends BaseActivity {

    private EditText titleEditText, ownerEditText;
    private TextView dateTextView;
    private Calendar selectedDate;
    private Button createEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_event_creation);

        titleEditText = findViewById(R.id.titleEditText);
        ownerEditText = findViewById(R.id.ownerEditText);
        dateTextView = findViewById(R.id.dateTextView);
        Button datePickerButton = findViewById(R.id.datePickerButton);
        createEventButton = findViewById(R.id.createEventButton);

        selectedDate = Calendar.getInstance();

        datePickerButton.setOnClickListener(v -> showDatePickerDialog());
        createEventButton.setOnClickListener(v -> createEvent());

        updateDateDisplay();
    }

    private void showDatePickerDialog() {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, month);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateDisplay();
        }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDateDisplay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        dateTextView.setText(sdf.format(selectedDate.getTime()));
    }

    private void createEvent() {
        String title = titleEditText.getText().toString().trim();
        String owner = ownerEditText.getText().toString().trim();

        if (title.isEmpty() || owner.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());
        String day = dayFormat.format(selectedDate.getTime());
        String month = monthFormat.format(selectedDate.getTime());
        String date = String.valueOf(selectedDate.get(Calendar.DAY_OF_MONTH));

        Event event = new Event(date, day, month, title, owner);

        createEventButton.setEnabled(false);

        FirebaseHelper.getInstance().addDocument("events", event.toMap(),
                documentId -> {
                    Toast.makeText(this, "Event created successfully", Toast.LENGTH_SHORT).show();
                    // Return the new event data to CalendarActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("eventId", documentId);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                },
                exception -> {
                    createEventButton.setEnabled(true);
                    Toast.makeText(this, "Error creating event: " + exception.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
        );
    }
}