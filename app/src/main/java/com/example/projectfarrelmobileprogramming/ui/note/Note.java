package com.example.projectfarrelmobileprogramming.ui.note;

public class Note {
    private int id;
    private String text;
    private String timestamp;

    public Note(int id, String text, String timestamp) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
