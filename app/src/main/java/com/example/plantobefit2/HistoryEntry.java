package com.example.plantobefit2;

import androidx.annotation.NonNull;

public class HistoryEntry {
/*
    private String name;
    private int id;
    private int reps;
    private int sets;
    private int weight;
    private float exhaust;
    private float focus;
    private float motivation;
    private float satisfaction;
    private float date_start;
    private float note;
    private float duration;
    private float weight_total;

    public HistoryEntry(
            String name,
            int id,
            int reps,
            int sets,
            int weight,
            float exhaust,
            float focus,
            float motivation,
            float satisfaction,
            float date_start,
            float note,
            float duration,
            float weight_total) {

        this.name = name;
        this.id = id;
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
        this.exhaust = exhaust;
        this.focus = focus;
        this.motivation = motivation;
        this.satisfaction = satisfaction;
        this.date_start = date_start;
        this.note = note;
        this.duration = duration;
        this.weight_total = weight_total;
    } */


    private String name;
    private int id;
    private int reps;
    private int sets;
    private int weight;
    // private int duration;

    public HistoryEntry(String name, int id, int reps, int sets, int weight) {
        this.name = name;
        this.id = id;
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public int getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "HistoryEntry{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", reps=" + reps +
                ", sets=" + sets +
                ", weight= " + weight +
                '}';
    }
}
