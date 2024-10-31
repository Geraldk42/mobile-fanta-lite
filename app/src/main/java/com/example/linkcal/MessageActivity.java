//  CHANTELLE

package com.example.linkcal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MessageActivity extends BaseActivity {

    private Toolbar toolbar;
    private EditText editTextMessage;
    private Button buttonSend;
    private LinearLayout messageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_message);

        setToolbarTitle("Chat with [User Name]");

        // Initialize the Toolbar
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // Set title programmatically
//        getSupportActionBar().setTitle(" Chat with [User Name]");

        // Initialize message input and send button
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        messageContainer = findViewById(R.id.messageContainer); // Layout for messages

        // Set up the button click listener
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    // Method to send a message
    private void sendMessage() {
        String message = editTextMessage.getText().toString();
        if (!message.isEmpty()) {
            // Create a TextView for the message
            TextView textViewMessage = new TextView(this);
            textViewMessage.setText(message);
            textViewMessage.setTextColor(getResources().getColor(android.R.color.black));
            textViewMessage.setBackgroundResource(R.drawable.bubble_background_sent);
            textViewMessage.setPadding(10, 10, 10, 10);
            textViewMessage.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            // Add the message TextView to the message container
            messageContainer.addView(textViewMessage);
            // Clear the input field
            editTextMessage.setText("");
        }
    }
}

