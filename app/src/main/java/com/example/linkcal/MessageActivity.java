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


//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


//        getSupportActionBar().setTitle(" Chat with [User Name]");

        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        messageContainer = findViewById(R.id.messageContainer);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String message = editTextMessage.getText().toString();
        if (!message.isEmpty()) {
            TextView textViewMessage = new TextView(this);
            textViewMessage.setText(message);
            textViewMessage.setTextColor(getResources().getColor(android.R.color.black));
            textViewMessage.setBackgroundResource(R.drawable.bubble_background_sent);
            textViewMessage.setPadding(10, 10, 10, 10);
            textViewMessage.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            messageContainer.addView(textViewMessage);
            editTextMessage.setText("");
        }
    }
}

