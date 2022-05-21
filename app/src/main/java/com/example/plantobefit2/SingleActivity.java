package com.example.plantobefit2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SingleActivity extends AppCompatActivity {

    public static final String EXERCISE_ID_KEY = "exerciseId";
    Button btnExerciseAdd;
    TextView txtExerciseName2, txtExerciseDesc2;
    EditText edtExerciseWeight, edtExerciseReps, edtExerciseSets;
    ImageView imgExercise2;
    int exerciseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        setButtons();

        Intent intent = getIntent();
        if (null != intent) {
            exerciseId = intent.getIntExtra(EXERCISE_ID_KEY, -1);
            if (exerciseId != -1) {
                Exercise exercise = Utils.getInstance(this).getExerciseById(exerciseId);
                if (null != exercise) {
                    setData(exercise);
                    handleStartExercises(exercise);
                }
            }
        }
    }

    public void setButtons() {

        btnExerciseAdd = findViewById(R.id.btnExerciseAdd);
        txtExerciseName2 = findViewById(R.id.txtExerciseName2);
        txtExerciseDesc2 = findViewById(R.id.txtExerciseDesc2);
        edtExerciseWeight = findViewById(R.id.edtExerciseWeight);
        edtExerciseReps = findViewById(R.id.edtExerciseReps);
        edtExerciseSets = findViewById(R.id.edtExerciseSets);
        imgExercise2 = findViewById(R.id.imgExercise2);
    }

    public void setData(Exercise e) {
        //imgExercise2.setText(e.getImageURL());
        txtExerciseName2.setText(e.getName());
        txtExerciseDesc2.setText(e.getDescription());
        /* Glide.with(this)
                .asBitmap()
                .load(e.getImageURL())
                .into(imgExercise2); */
        Glide.with(this)
                .asGif()
                .load(e.getGifURL())
                .placeholder(R.drawable.ic_clepsydra)
                .into(imgExercise2);
    }

    private void handleStartExercises (final Exercise exercise) {
        ArrayList<Exercise> startExercises = Utils.getInstance(this).getStartExercises();

        boolean existInStartExercises = false;

        for (Exercise e : startExercises) {
            if (e.getId() == exercise.getId()) {
                existInStartExercises = true;
            }
        }

        if (existInStartExercises) {
            btnExerciseAdd.setEnabled(false);
        } else {
            btnExerciseAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ////Intent intent = new Intent(SingleActivity.this, StartActivity.class);
                    ////startActivity(intent);
                    // if (Utils.getInstance(SingleActivity.this).addToStartExercises(exercise)){

                        String  exerciseWeight = edtExerciseWeight.getText().toString();
                        String exerciseReps = edtExerciseReps.getText().toString();
                        String exerciseSets = edtExerciseSets.getText().toString();

                        if(exerciseSets.equals("")) {
                            edtExerciseSets.setError("Wartość pola Liczba serii nie może być pusta");
                        }

                        if(exerciseReps.equals("")) {
                            edtExerciseReps.setError("Wartość pola Ilość powtórzeń nie może być pusta");
                        }

                        if(exerciseWeight.equals("")) {
                            edtExerciseWeight.setError("Wartość pola Obciążenie nie może być pusta");
                        }

                        if(!exerciseReps.equals("") && !exerciseSets.equals("") && !exerciseWeight.equals("")) {
                            Intent intent = new Intent(SingleActivity.this, StartActivity.class);

                            if(!exerciseReps.equals(null)) {
                                intent.putExtra("exerciseReps", exerciseReps);
                            } else{
                                intent.putExtra("1", exerciseReps);
                            }
                            if(!exerciseSets.equals(null)) {
                                intent.putExtra("exerciseSets", exerciseSets);
                            } else {
                                intent.putExtra("1", exerciseSets);
                            }
                            if(!exerciseWeight.equals(null)) {
                                intent.putExtra("exerciseWeight", exerciseWeight);
                            } else {
                                intent.putExtra("1", exerciseWeight);
                            }

                            if(-1 != exerciseId) {
                                intent.putExtra("exerciseId2", exerciseId);
                            }

                            if (Utils.getInstance(SingleActivity.this).addToStartExercises(exercise)) {
                                Utils.getInstance(SingleActivity.this).addTotalStartedExercises();
                            }


                            Toast.makeText(SingleActivity.this, "Dodano ćwiczenie", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }


                }
            });
        }

    }
}
