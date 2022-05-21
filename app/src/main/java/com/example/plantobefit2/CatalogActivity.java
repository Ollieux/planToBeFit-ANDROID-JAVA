package com.example.plantobefit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class CatalogActivity extends AppCompatActivity {

    Menu menu;
    exerciseRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        // setButtons();
        ////fillExerciseList();
        buildRecycleView();

    }

    private void buildRecycleView() {

        RecyclerView exerciseRecView = findViewById(R.id.rvCatalog);

        adapter = new exerciseRecyclerViewAdapter(this, "catalogExercises");
        exerciseRecView.setAdapter(adapter);
        exerciseRecView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setExerciseList(Utils.getInstance(this).getCatalogExercises());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        ArrayList<Exercise> list = Utils.getInstance(this).getCatalogExercises();

        // Toast.makeText(CatalogActivity.this, "Id = " + String.valueOf(item.getItemId()), Toast.LENGTH_SHORT);
        // Toast.makeText(CatalogActivity.this, "Menu klik", Toast.LENGTH_SHORT);
         switch (item.getItemId()) {
            case R.id.filter_none:
                Toast.makeText(CatalogActivity.this, "Wszystkie", Toast.LENGTH_SHORT).show();
                adapter.setExerciseList(Utils.getInstance(this).getCatalogExercises());
                return true;

            case R.id.filter_arms:
                ArrayList<Exercise> tempList1 = new ArrayList();
                Toast.makeText(CatalogActivity.this, "Ręce", Toast.LENGTH_SHORT).show();
                for(Exercise e: list) {
                    if (e.getCategory().equals("rece")) {
                        tempList1.add(e);
                    }
                }
                adapter.setExerciseList(tempList1);
                return true;

            case R.id.filter_chest:
                ArrayList<Exercise> tempList2 = new ArrayList();
                Toast.makeText(CatalogActivity.this, "Klatka piersiowa", Toast.LENGTH_SHORT).show();
                for(Exercise e: list) {
                    if (e.getCategory().equals("klatka")) {
                        tempList2.add(e);
                    }
                }
                adapter.setExerciseList(tempList2);
                return true;

            case R.id.filter_back:
                ArrayList<Exercise> tempList3 = new ArrayList();
                Toast.makeText(CatalogActivity.this, "plecy", Toast.LENGTH_SHORT).show();
                for(Exercise e: list) {
                    if (e.getCategory().equals("plecy")) {
                        tempList3.add(e);
                    }
                }
                adapter.setExerciseList(tempList3);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}