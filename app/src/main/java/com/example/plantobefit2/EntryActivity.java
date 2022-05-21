package com.example.plantobefit2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class EntryActivity extends AppCompatActivity {

    //exerciseRecyclerViewAdapter exercise_adapter;
    historyEntryRecyclerViewAdapter history_adapter;
    TextView txtSummary;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        txtSummary = findViewById(R.id.txtSummary);
        buildHistoryEntryRecycleView();

    }

//    private void buildExerciseRecycleView() {
//
//        ArrayList<ArrayList<HistoryEntry>> temp = new ArrayList<ArrayList<HistoryEntry>>();
//        RecyclerView exerciseRecView = findViewById(R.id.rvHistory);
//        exercise_adapter = new exerciseRecyclerViewAdapter(this, "historyExercises");
//        exerciseRecView.setAdapter(exercise_adapter);
//        exerciseRecView.setLayoutManager(new LinearLayoutManager(this));
//
//        //exercise_adapter.setHistoryList(Utils.getInstance(this).getTrainingsHistory(), 0);
//
//    }

    private void buildHistoryEntryRecycleView() {

        // ArrayList<ArrayList<HistoryEntry>> temp = new ArrayList<ArrayList<HistoryEntry>>();
        RecyclerView historyEntryRecView = findViewById(R.id.rvEntry);
        history_adapter = new historyEntryRecyclerViewAdapter(this);
        historyEntryRecView.setAdapter(history_adapter);
        historyEntryRecView.setLayoutManager(new LinearLayoutManager(this));

        history_adapter.setHistoryList(Utils.getInstance(this).getTrainingsHistory());

    }


}