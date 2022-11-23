package com.example.profru.Resume;

public class WorkItem {

    private float time;
    private String place;
    private String profession;

    public float getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getProfession() {
        return profession;
    }

    public WorkItem(String _place, String _profession, float _time) {
        time = _time;
        place = _place;
        profession = _profession;
    }

    public WorkItem(String compressed) {
        this(compressed.split("|")[1], compressed.split("|")[0], Float.parseFloat(compressed.split("|")[2]));
    }
}
