package com.example.linkcal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class CalendarActivity extends BaseActivity {

    private RecyclerView eventList;
    private FloatingActionButton fabAddEvent;
    private Map<String, List<Event>> events;
    private EventAdapter adapter;

    private ActivityResultLauncher<Intent> eventCreationLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_calendar);
        setToolbarTitle("Calendar");

        eventList = findViewById(R.id.eventList);
        fabAddEvent = findViewById(R.id.fabAddEvent);

        events = getDummyEvents();
        setupEventList();
        setupFab();

        registerEventCreationLauncher();
    }

    private void setupEventList() {
        eventList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(events);
        eventList.setAdapter(adapter);
    }

    private void registerEventCreationLauncher() {
        eventCreationLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String title = data.getStringExtra("title");
                        String owner = data.getStringExtra("owner");
                        int date = data.getIntExtra("date", 1);
                        String month = data.getStringExtra("month");
                        String day = data.getStringExtra("day");

                        Event newEvent = new Event(String.valueOf(date), day, month, title, owner);
                        addEvent(events, newEvent);
                        adapter.notifyDataSetChanged();
                    }
                }
        );
    }

    private void setupFab() {
        fabAddEvent.setOnClickListener(v -> {
            Intent intent = new Intent(this, EventCreationActivity.class);
            eventCreationLauncher.launch(intent);
        });
    }



    private Map<String, List<Event>> getDummyEvents() {
        Map<String, List<Event>> events = new HashMap<>();
        addEvent(events, new Event("15", "Tuesday", "Oct", "Team Meeting", "John"));
        addEvent(events, new Event("15", "Tuesday", "Oct", "Lunch with Client", "Sarah"));
        addEvent(events, new Event("17", "Thursday", "Oct", "Movie Night", "Mike"));
        addEvent(events, new Event("20", "Sunday", "Oct", "Birthday Party", "Emma"));
        addEvent(events, new Event("22", "Tuesday", "Oct", "Dentist Appointment", "John"));
        addEvent(events, new Event("25", "Friday", "Oct", "Conference Call", "Sarah"));
        return events;
    }

    private void addEvent(Map<String, List<Event>> events, Event event) {
        String key = event.date + event.month;
        if (!events.containsKey(key)) {
            events.put(key, new ArrayList<>());
        }
        events.get(key).add(event);
    }

    private static class Event {
        String date, day, month, title, owner;

        Event(String date, String day, String month, String title, String owner) {
            this.date = date;
            this.day = day;
            this.month = month;
            this.title = title;
            this.owner = owner;
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

        private List<Map.Entry<String, List<Event>>> eventEntries;

        EventAdapter(Map<String, List<Event>> events) {
            this.eventEntries = new ArrayList<>(events.entrySet());
        }

        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_day, parent, false);
            return new EventViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            Map.Entry<String, List<Event>> entry = eventEntries.get(position);
            holder.bind(entry.getValue());
        }

        @Override
        public int getItemCount() {
            return eventEntries.size();
        }

        class EventViewHolder extends RecyclerView.ViewHolder {
            TextView dateText, dayText, monthText;
            RecyclerView eventItemsRecyclerView;

            EventViewHolder(@NonNull View itemView) {
                super(itemView);
                dateText = itemView.findViewById(R.id.dateText);
                dayText = itemView.findViewById(R.id.dayText);
                monthText = itemView.findViewById(R.id.monthText);
                eventItemsRecyclerView = itemView.findViewById(R.id.eventItemsRecyclerView);
            }

            void bind(List<Event> events) {
                Event firstEvent = events.get(0);
                dateText.setText(firstEvent.date);
                dayText.setText(firstEvent.day);
                monthText.setText(firstEvent.month);

                EventItemAdapter eventItemAdapter = new EventItemAdapter(events);
                eventItemsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
                eventItemsRecyclerView.setAdapter(eventItemAdapter);
            }
        }
    }

    private class EventItemAdapter extends RecyclerView.Adapter<EventItemAdapter.EventItemViewHolder> {

        private List<Event> events;

        EventItemAdapter(List<Event> events) {
            this.events = events;
        }

        @NonNull
        @Override
        public EventItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
            return new EventItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventItemViewHolder holder, int position) {
            Event event = events.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        class EventItemViewHolder extends RecyclerView.ViewHolder {
            TextView titleText, ownerText;

            EventItemViewHolder(@NonNull View itemView) {
                super(itemView);
                titleText = itemView.findViewById(R.id.titleText);
                ownerText = itemView.findViewById(R.id.ownerText);

                itemView.setOnClickListener(v -> showEventDetails(getAdapterPosition()));
            }

            void bind(Event event) {
                titleText.setText(event.title);
                ownerText.setText(event.owner);
            }
        }

        private void showEventDetails(int position) {
            Event event = events.get(position);
            EventDetailsDialogFragment dialogFragment = EventDetailsDialogFragment.newInstance(
                    event.date, event.day, event.month, event.title, event.owner);
            dialogFragment.show(getSupportFragmentManager(), "EventDetails");
        }
    }
}