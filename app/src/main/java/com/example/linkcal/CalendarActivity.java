package com.example.linkcal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.example.linkcal.models.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.example.linkcal.helpers.FirebaseHelper;
import android.util.Log;

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

        events = new HashMap<>();
        setupEventList();
        setupFab();
        registerEventCreationLauncher();
        loadEventsFromFirebase();
    }

    private void setupEventList() {
        eventList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(events);
        eventList.setAdapter(adapter);
    }

    private void loadEventsFromFirebase() {
        FirebaseHelper.getInstance().getAllDocuments("events",
                querySnapshot -> {
                    events.clear();
                    List<Event> allEvents = new ArrayList<>();

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        Map<String, Object> data = doc.getData();
                        String date = (String) data.get("date");
                        String day = (String) data.get("day");
                        String month = (String) data.get("month");
                        String title = (String) data.get("title");
                        String owner = (String) data.get("owner");

                        Event event = new Event(date, day, month, title, owner);
                        allEvents.add(event);
                    }


                    Collections.sort(allEvents, (e1, e2) -> {
                        int month1 = getMonthNumber(e1.getMonth());
                        int month2 = getMonthNumber(e2.getMonth());
                        if (month1 != month2) return month1 - month2;
                        return Integer.parseInt(e1.getDate()) - Integer.parseInt(e2.getDate());
                    });


                    for (Event event : allEvents) {
                        addEvent(events, event);
                    }

                    runOnUiThread(() -> {
                        adapter = new EventAdapter(events);
                        eventList.setAdapter(adapter);
                    });
                },
                exception -> Toast.makeText(this,
                        "Error loading events: " + exception.getMessage(),
                        Toast.LENGTH_SHORT).show()
        );
    }

    private int getMonthNumber(String month) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for (int i = 0; i < months.length; i++) {
            if (months[i].equalsIgnoreCase(month)) return i;
        }
        return 0;
    }

    private void registerEventCreationLauncher() {
        eventCreationLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadEventsFromFirebase();
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

    private void addEvent(Map<String, List<Event>> events, Event event) {
        String key = event.getDate() + event.getMonth();
        if (!events.containsKey(key)) {
            events.put(key, new ArrayList<>());
        }
        events.get(key).add(event);
    }

    private class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
        private List<Map.Entry<String, List<Event>>> eventEntries;

        EventAdapter(Map<String, List<Event>> events) {
            this.eventEntries = new ArrayList<>(events.entrySet());

            Collections.sort(eventEntries, (e1, e2) -> {
                Event event1 = e1.getValue().get(0);
                Event event2 = e2.getValue().get(0);

                int month1 = getMonthNumber(event1.getMonth());
                int month2 = getMonthNumber(event2.getMonth());

                if (month1 != month2) {
                    return month1 - month2;
                }

                return Integer.parseInt(event1.getDate()) - Integer.parseInt(event2.getDate());
            });
        }

        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_event_day, parent, false);
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
                dateText.setText(firstEvent.getDate());
                dayText.setText(firstEvent.getDay());
                monthText.setText(firstEvent.getMonth());

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
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_event, parent, false);
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
                titleText.setText(event.getTitle());
                ownerText.setText(event.getOwner());
            }
        }

        private void showEventDetails(int position) {
            Event event = events.get(position);
            EventDetailsDialogFragment dialogFragment = EventDetailsDialogFragment.newInstance(
                    event.getDate(), event.getDay(), event.getMonth(),
                    event.getTitle(), event.getOwner());
            dialogFragment.show(getSupportFragmentManager(), "EventDetails");
        }
    }
}