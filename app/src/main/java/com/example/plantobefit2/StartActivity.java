package com.example.plantobefit2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SignalThresholdInfo;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//import nl.dionsegijn.konfetti.core.Position;
//import nl.dionsegijn.konfetti.xml.KonfettiView;
//import nl.dionsegijn.konfetti.core.Angle;
//import nl.dionsegijn.konfetti.core.Party;
//import nl.dionsegijn.konfetti.core.PartyFactory;
//import nl.dionsegijn.konfetti.core.Spread;
//import nl.dionsegijn.konfetti.core.emitter.Emitter;
//import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
//import nl.dionsegijn.konfetti.core.models.Shape;
//import nl.dionsegijn.konfetti.core.models.Size;


public class StartActivity extends AppCompatActivity {

    private ProgressBar pBar;
    private ConstraintLayout startEmpty;
    private ConstraintLayout startNonEmpty;
    // private KonfettiView konfetti;

    ArrayList<String> data = new ArrayList();
    exerciseRecyclerViewAdapter adapter;
    // int progress;
    // int i = 0;
    // int initTotal, newTotal;
    // boolean clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_2);

        // getActivity().setTitle("Trening");

        Intent intent = getIntent();
        String sets, reps, weight;
        int id;

        if(null != intent) {
            sets = intent.getStringExtra("exerciseSets");
            reps = intent.getStringExtra("exerciseReps");
            weight = intent.getStringExtra("exerciseWeight");
            id = intent.getIntExtra("exerciseId2", -1);

            if (id != -1) {

                data.add(sets);
                data.add(reps);
                data.add(weight);

                if (Utils.getInstance(this).addToTrainingData(id, data)) {

                    System.out.println("Inside Utils.getInstance(this).addToTrainingsData(id, data)");
                    //String a = Utils.getInstance(this).getTrainingsData().get(0).get(0); ###


                }
            }

        } else {
            sets = "ghi";
            reps = "ghi";
            weight = "ghi";
            id = -1;
        }

        pBar = findViewById(R.id.pBar);
        startEmpty = findViewById(R.id.startEmpty);
        startNonEmpty = findViewById(R.id.startNonEmpty);
        // konfetti = findViewById(R.id.konfetti);

        //Utils.getInstance(StartActivity.this).updateProgress(); //XXX
        System.out.println("getTotalStartedExercises() in StartActivity equals: " + Utils.getInstance(StartActivity.this).getTotalStartedExercises());
        if(Utils.getInstance(StartActivity.this).getTotalStartedExercises() > 0) {
            startEmpty.setVisibility(View.GONE);
            startNonEmpty.setVisibility(View.VISIBLE);
            // System.out.println("Inside getTotalStartedExercises() > 0 pre progress equals: " + pBar.getProgress());
            Utils.getInstance(StartActivity.this).updateProgress();
            pBar.setProgress(Utils.getInstance(StartActivity.this).getProgressBarValue()); //YYY

        }
        else {
            startNonEmpty.setVisibility(View.GONE);
            startEmpty.setVisibility(View.VISIBLE);
        }


        buildRecycleView();

        adapter.setListener(new exerciseRecyclerViewAdapter.Listener() {
            @Override
            public void onItemClick() {

                boolean started = Utils.getInstance(StartActivity.this).getStartedTrainingStatus();

                if (started == false) {

                    Utils.getInstance(StartActivity.this).addInitStartedExercises(Utils.getInstance(StartActivity.this).getStartExercises().size() + 1);
                    System.out.println("pre Status in StartActivity equals: " + Utils.getInstance(StartActivity.this).getStartedTrainingStatus());
                    Utils.getInstance(StartActivity.this).updateStartedTrainingStatus();
                    System.out.println("post Status in StartActivity equals: " + Utils.getInstance(StartActivity.this).getStartedTrainingStatus());

                }

                // Jeżeli dodany zostanie przycisk usun to
                int initTotal = Utils.getInstance(StartActivity.this).getInitStartedExercises();
                int total = Utils.getInstance(StartActivity.this).getTotalStartedExercises();
                int i = Utils.getInstance(StartActivity.this).getSingleDoneExercises();

                Utils.getInstance(StartActivity.this).updateProgress();

                pBar.setProgress(Utils.getInstance(StartActivity.this).getProgressBarValue());

                int progress = pBar.getProgress();

                System.out.println("Progress equals: " + progress);
                System.out.println("i equals: " + i);
                System.out.println("total equals: " + total);
                System.out.println("initTotal equals: " + initTotal);

                if (progress == 100) {
                    System.out.println("Inside progress == 100");

                    Date endDate = java.util.Calendar.getInstance().getTime();

                    long stop = endDate.getTime();
                    System.out.println("Stop equals: " + stop);

                    ArrayList<TrainingEntry> details = Utils.getInstance(StartActivity.this).getTrainingDetail();

                    if(null != details) {

                        long start = details.get(details.size() - 1).getDate_start();

                        long duration = (stop - start) / 1000;
                        // duration %= 60; ###
                        System.out.println("duration equals:  " + duration);
                        details.get(details.size() - 1).setDuration(duration);
                        details.get(details.size() - 1).setTime_formated();

                        Utils.getInstance(StartActivity.this).updateTrainingDetail(details);
                    }


                    System.out.println("StartActivity pre now");
                    Utils.getInstance(StartActivity.this).removeTotalStartedExercises();
                    System.out.println("###1");
                    Utils.getInstance(StartActivity.this).removeProgressBarValue(); //TODONE: dopiero gdy zostanie dodane nowe cwiczenie
                    System.out.println("###2");
                    Utils.getInstance(StartActivity.this).removeSingleDoneExercises();
                    System.out.println("###3");
                    Utils.getInstance(StartActivity.this).removeInitStartedExercises();
                    System.out.println("###4");
                    Utils.getInstance(StartActivity.this).updateStartedTrainingStatus();
                    System.out.println("###5");
                    Utils.getInstance(StartActivity.this).addToTrainingsHistory();
                    System.out.println("###6");
                    Utils.getInstance(StartActivity.this).removeHistoryExercises(); //TODO: też dopiero gdy zostanie dodane nowe cwiczenie, czemu?
                    System.out.println("###7");

                    //konfetti.setOnClickListener() {
                    // konfetti.build()
                           // .setSpeed(1f, 5f);

                    //double duration = (endDate.getTime() - (((Date) Utils.getInstance(StartActivity.this).getTrainingDetail().get(Utils.getInstance(StartActivity.this).getCounterTrainingDetail()).get(5)).getTime())) / 60000.0; //60000f // % 60
                    // System.out.println("duration equals:  " + duration); YYY
                    //Utils.getInstance(StartActivity.this).addTrainingDetail(duration, Utils.getInstance(StartActivity.this).getTrainingsHistory().size() - 1, 8);
                    ////Utils.getInstance(StartActivity.this).addTrainingDetail(duration, Utils.getInstance(StartActivity.this).getCounterTrainingDetail(), 6); TODO: pos


                    Utils.getInstance(StartActivity.this).addCounterTrainingDetail();
                    Utils.getInstance(StartActivity.this).updateTrainingDetail(false);
                    // Utils.getInstance(StartActivity.this).removeProgressBarValue(); //co to?
                    System.out.println("StartActivity post now");

                    //TODO: confetti
                }

            }
        }) ;


        }


    private void buildRecycleView() {

        RecyclerView exerciseRecView = findViewById(R.id.rvStart);
        adapter = new exerciseRecyclerViewAdapter(this, "startExercises");
        exerciseRecView.setAdapter(adapter);
        exerciseRecView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setExerciseList(Utils.getInstance(this).getStartExercises());
    }

    @Override
    public void onBackPressed() {
        System.out.println("Inside StartActivity onBackPressed()");
        /*if (pBar.getProgress() == 100) {
         Utils.getInstance(StartActivity.this).removeProgressBarValue();
        } */
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

}