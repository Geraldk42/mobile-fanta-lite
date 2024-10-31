// BILL

package com.example.linkcal;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventCreationActivity extends BaseActivity {

    private EditText titleEditText, ownerEditText;
    private TextView dateTextView;
    private Calendar selectedDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_event_creation);

        titleEditText = findViewById(R.id.titleEditText);
        ownerEditText = findViewById(R.id.ownerEditText);
        dateTextView = findViewById(R.id.dateTextView);
        Button datePickerButton = findViewById(R.id.datePickerButton);
        Button createEventButton = findViewById(R.id.createEventButton);

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
        }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH)).show();
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

        Intent resultIntent = new Intent();
        resultIntent.putExtra("title", title);
        resultIntent.putExtra("owner", owner);
        resultIntent.putExtra("date", selectedDate.get(Calendar.DAY_OF_MONTH));
        resultIntent.putExtra("month", new SimpleDateFormat("MMM", Locale.getDefault()).format(selectedDate.getTime()));
        resultIntent.putExtra("day", new SimpleDateFormat("EEEE", Locale.getDefault()).format(selectedDate.getTime()));

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}