package com.example.linkcal.models;

import com.google.firebase.firestore.DocumentId;
import java.util.HashMap;
import java.util.Map;

public class Event {
    @DocumentId
    private String id;
    private String date;
    private String day;
    private String month;
    private String title;
    private String owner;
    private long timestamp; // for sorting events

    public Event() {}

    public Event(String date, String day, String month, String title, String owner) {
        this.date = date;
        this.day = day;
        this.month = month;
        this.title = title;
        this.owner = owner;
        this.timestamp = System.currentTimeMillis();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("date", date);
        map.put("day", day);
        map.put("month", month);
        map.put("title", title);
        map.put("owner", owner);
        map.put("timestamp", timestamp);
        return map;
    }

    public static Event fromMap(Map<String, Object> map) {
        Event event = new Event();
        event.date = (String) map.get("date");
        event.day = (String) map.get("day");
        event.month = (String) map.get("month");
        event.title = (String) map.get("title");
        event.owner = (String) map.get("owner");
        event.timestamp = (long) map.get("timestamp");
        return event;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}