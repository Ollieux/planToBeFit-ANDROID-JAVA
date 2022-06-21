package com.example.plantobefit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EntryActivity extends AppCompatActivity {

    //exerciseRecyclerViewAdapter exercise_adapter;
    historyEntryRecyclerViewAdapter history_adapter;
    TextView txtSummary;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        txtSummary = findViewById(R.id.txtSummary);
        buildHistoryEntryRecycleView();

        history_adapter.setListener(new historyEntryRecyclerViewAdapter.historyExerciseListener() {
            @Override
            public void onItemClick() {
                ArrayList<Object> fshelper = Utils.getInstance(EntryActivity.this).firestorehelper;
                long date_start = (long) fshelper.get(0);
                float exhaust = (float) fshelper.get(1);
                float focus = (float) fshelper.get(2);
                float motivation = (float) fshelper.get(3);
                float satisfaction = (float) fshelper.get(4);
                long duration = (long) fshelper.get(5);
               String time_formated = (String) fshelper.get(6);
                float weight_total = (float) fshelper.get(7);
                String weight_formated = (String) fshelper.get(8);
                ArrayList<HistoryEntry> arr = (ArrayList<HistoryEntry>) fshelper.get(9);
                //for(HistoryEntry item: arr) {

                //}

                if(User.iflog) {
                        auth = FirebaseAuth.getInstance();
                        user = auth.getCurrentUser();
                        db = FirebaseFirestore.getInstance();

                        try {
                            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                            DocumentReference docIdRef = rootRef.collection("User").document(user.getUid());
                            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Map<String, Object> count = document.getData();
                                            System.out.println("count size equals" + count.size());
                                            Map map = new HashMap<String, Object>();
                                            ArrayList<HistoryEntry> data = Utils.getInstance(EntryActivity.this).getHistoryExercises();
                                            System.out.println("Data size equals" + data.size());

                                            if (document.get("Trening" + count.size()) != null) {

                                                Map map2 = new HashMap<String, Object>();
                                                map2.put("DateStart", date_start);
                                                map2.put("Exhaust", exhaust);
                                                map2.put("Focus", focus);
                                                map2.put("Motivation", motivation);
                                                map2.put("Satisfaction", satisfaction);
                                                map2.put("Duration", duration);
                                                map2.put("TimeFormated", time_formated);
                                                map2.put("WeightTotal", weight_total);
                                                map2.put("WeightFormated", weight_formated);

                                                for(HistoryEntry e: arr) {
                                                    Map map3 = new HashMap<String, Object>();
                                                    map3.put("Sets", e.getSets());
                                                    map3.put("Reps", e.getReps());
                                                    map3.put("Weight", e.getWeight());
                                                    map2.put(String.valueOf(e.getId()), map3);
                                                    //System.out.println("fb2 petla");
                                                }
                                                map.put("Trening" + count.size(), map2);

                                                db.collection("User").document(user.getUid())
                                                        .update(map);

                                            } else {
                                                //Map map2 = new HashMap<Integer, Object>();
                                                Map map2 = new HashMap<String, Object>();

                                                //System.out.println("fb2 pre petla");

                                                map2.put("DateStart", date_start);
                                                map2.put("Exhaust", exhaust);
                                                map2.put("Focus", focus);
                                                map2.put("Motivation", motivation);
                                                map2.put("Satisfaction", satisfaction);
                                                map2.put("Duration", duration);
                                                map2.put("TimeFormated", time_formated);
                                                map2.put("WeightTotal", weight_total);
                                                map2.put("WeightFormated", weight_formated);

                                                for(HistoryEntry e: arr) {
                                                    Map map3 = new HashMap<String, Object>();
                                                    map3.put("Sets", e.getSets());
                                                    map3.put("Reps", e.getReps());
                                                    map3.put("Weight", e.getWeight());
                                                    map2.put(String.valueOf(e.getId()), map3);
                                                    //System.out.println("fb2 petla");
                                                }
                                                map.put("Trening" + count.size(), map2);
                                                //Toast.makeText(StartActivity.this, "Tamto", Toast.LENGTH_SHORT).show();

                                                db.collection("User").document(user.getUid())
                                                        .set(map, SetOptions.merge());

                                            }
                                        }
                                    }
                                }
                        });
                    }catch (SQLiteException e) {
                            System.out.println("bład");
                        }
                }





            }
        });

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