package com.example.linkcal;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private List<Message> messagesList;

    public MessagesAdapter(List<Message> messagesList) {
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messagesList.get(position);
        holder.textViewMessage.setText(message.getText());

        if (message.isSentByUser()) {
            holder.textViewMessage.setBackgroundResource(R.drawable.bubble_background_sent);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.textViewMessage.getLayoutParams();
            params.gravity = Gravity.END;
            holder.textViewMessage.setLayoutParams(params);
        } else {
            holder.textViewMessage.setBackgroundResource(R.drawable.bubble_background_received);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.textViewMessage.getLayoutParams();
            params.gravity = Gravity.START;
            holder.textViewMessage.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage;
        LinearLayout messageContainer;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
            messageContainer = itemView.findViewById(R.id.messageContainer);
        }
    }
}
