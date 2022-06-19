package com.example.plantobefit2;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.spec.DESedeKeySpec;

public class Utils {


    private static final String CATALOG_EXERCISE_KEY = "catalog_exercises";
    private static final String START_EXERCISE_KEY = "start_exercises";
    private static final String HISTORY_EXERCISE_KEY = "history_exercises";
    private static final String TRAINING_DATA_KEY = "training_data";
    private static final String TRAININGS_HISTORY_KEY = "trainings_history";
    private static final String PROGRESSBAR_VALUE_KEY = "progressbar_value";
    private static final String TOTAL_STARTED_KEY = "total_started_number";
    private static final String INIT_STARTED_KEY = "init_started_number";
    private static final String SINGLE_DONE_KEY = "single_done_number";
    private static final String TRAINING_DETAIL_KEY = "training_detail_value";
    private static final String COUNTER_TRAINING_DETAIL = "counter_detail_value";
    private static final String STARTED_STATUS_KEY = "started_status_value";
    private static final String __STARTED_STATUS_KEY = "__started_status_value";



    private static Utils instance;
    private SharedPreferences sharedPreferences;
    // private int totalStartedExercises = -1; ###
    private int totalStartedExercises;
    // private int progressBarValue = -1; ###
    private int progressBarValue;
    // private int singleDoneExercises = -1; ###
    private int singleDoneExercises;
    // private int initStartedExercises = -1; ###
    private int initStartedExercises;
    // private boolean isStartedTrainingStatusSet = false; ###
    private boolean isStartedTrainingStatusSet;
    // private boolean startedTrainingStatus = false; ###
    private boolean startedTrainingStatus;
    private int counterTrainingDetail;

    private Utils(Context context) {
        sharedPreferences = context.getSharedPreferences("my_db4", Context.MODE_PRIVATE);

        if (null == getCatalogExercises()) {
            initData();
        }

        // System.out.println("after null == getCatalogExercises()"); ###

        if (null == getTrainingData()) {
            System.out.println("Inside null == getTrainingData()");
            // editor.putString(TRAINING_DATA_KEY, gson.toJson(new ArrayList<Exercise>())); ###
            // editor.commit(); ###
            initData2();
        }
        else {
            System.out.println("Inside null != getTrainingData()");
        }

        if(null == getTrainingDetail()) {
            System.out.println("Inside null == getTrainingDetail()");
            updateTrainingDetail(true);
            // System.out.println("getTrainingDetail() equals: " + getTrainingDetail()); ###
        } else {
            System.out.println("Inside null != getTrainingDetail()");
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getStartExercises()) {
            System.out.println("Inside null == getStartExercises()");
            editor.putString(START_EXERCISE_KEY, gson.toJson(new ArrayList<Exercise>()));
            editor.commit();
        }

        if (null == getHistoryExercises()) {
            System.out.println("Inside null == getHistoryExercises()");
            editor.putString(HISTORY_EXERCISE_KEY, gson.toJson(new ArrayList<HistoryEntry>()));
            editor.commit();
        }
        else {
            System.out.println("Inside null != getHistoryExercises()");
        }

        // if (progressBarValue == -1) { ###
        if (null == sharedPreferences.getString(PROGRESSBAR_VALUE_KEY, null)) {
            // System.out.println("Inside progressBarValue == -1"); ###
            System.out.println("Inside progressBarValue == none");
            progressBarValue = 0;
            editor.putString(PROGRESSBAR_VALUE_KEY, gson.toJson(String.valueOf(progressBarValue)));
            editor.commit();
        } else {
            progressBarValue = getProgressBarValue();
            System.out.println("Inside progressBarValue != none: " + progressBarValue);
        }

        // if (totalStartedExercises == -1) { ###
        if (null == sharedPreferences.getString(TOTAL_STARTED_KEY, null)) {
            System.out.println("Inside totalStartedExercises == none");
            totalStartedExercises = 0;
            editor.putString(TOTAL_STARTED_KEY, gson.toJson(String.valueOf(totalStartedExercises)));
            editor.commit();
        } else {
            totalStartedExercises = getTotalStartedExercises();
            System.out.println("Inside totalStartedExercises != none: " + totalStartedExercises);
        }

        // if (initStartedExercises == -1) { ###
        if (null == sharedPreferences.getString(INIT_STARTED_KEY, null)) {
            System.out.println("Inside initStartedExercises == none");
            initStartedExercises = 0;
            editor.putString(INIT_STARTED_KEY, gson.toJson(String.valueOf(initStartedExercises)));
            editor.commit();
        } else {
            initStartedExercises = getInitStartedExercises();
            System.out.println("Inside null != initStartedExercises: " + initStartedExercises);
        }

        //if (singleDoneExercises == -1) { ###
        if (null == sharedPreferences.getString(SINGLE_DONE_KEY, null)) {
            singleDoneExercises = 0;
            editor.putString(SINGLE_DONE_KEY, gson.toJson(String.valueOf(singleDoneExercises)));
            editor.commit();
        } else {
            singleDoneExercises = getSingleDoneExercises();
        }

        if (null == sharedPreferences.getString(COUNTER_TRAINING_DETAIL, null)) {
            counterTrainingDetail = 0;
            editor.putString(COUNTER_TRAINING_DETAIL, gson.toJson(String.valueOf(counterTrainingDetail)));
            editor.commit();
        } else {
            counterTrainingDetail = getCounterTrainingDetail();
        }

        if(null == sharedPreferences.getString(__STARTED_STATUS_KEY, null)) {
            System.out.println("Inside isStartedTrainingStatusSet wasn't set");
            isStartedTrainingStatusSet = true;
            startedTrainingStatus = false;
            // editor.putString(__STARTED_STATUS_KEY,  gson.toJson(isStartedTrainingStatusSet));
            // editor.putString(STARTED_STATUS_KEY,  gson.toJson(startedTrainingStatus));
            editor.putString(__STARTED_STATUS_KEY,  gson.toJson(String.valueOf(isStartedTrainingStatusSet)));
            editor.putString(STARTED_STATUS_KEY,  gson.toJson(String.valueOf(startedTrainingStatus)));
            editor.commit();
            //}

        } else {
            System.out.println("Inside isStartedTrainingStatusSet was set");
            Type type = new TypeToken<String>(){}.getType();
            // isStartedTrainingStatusSet = Boolean.getBoolean(gson.fromJson(sharedPreferences.getString(__STARTED_STATUS_KEY, null), type));
            // startedTrainingStatus = Boolean.getBoolean(gson.fromJson(sharedPreferences.getString(STARTED_STATUS_KEY, null), type));
            isStartedTrainingStatusSet = Boolean.parseBoolean(gson.fromJson(sharedPreferences.getString(__STARTED_STATUS_KEY, null), type));
            startedTrainingStatus = Boolean.parseBoolean(gson.fromJson(sharedPreferences.getString(STARTED_STATUS_KEY, null), type));
            System.out.println("startedTrainingStatus equals: " + startedTrainingStatus);

        }
    }

    private void initData2() {

        System.out.println("inside initData2()");

        // ArrayList<ArrayList> datas = new ArrayList<>(); ###
        ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();


        for (int i = 0; i < getCatalogExercises().size(); i++) {
            ArrayList<String> data = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                // data.add(""); ###
                data.add("abc");
            }
            datas.add(data);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(TRAINING_DATA_KEY,  gson.toJson(datas));
        editor.commit();
    }

    private void initData() {

        ArrayList<Exercise> exercises = new ArrayList<>();
        System.out.println("inside initData()");



        exercises.add(new Exercise(0, "Wiosłowanie w podporze jednorącz", "Cwiczenie wykonywane na mięśnie pleców.\n" +
                "Ręka podpierająca wyprostowana w łokciu.\n" +
                "Wzrok skierowany delikatnie przed siebie.", "plecy", "https://www.bodybuilding.com/images/2020/xdb/originals/xdb-13e-single-arm-bench-dumbbell-row-m2-16x9.jpg", "https://thumbs.gfycat.com/DecisiveDirtyAfghanhound-max-1mb.gif"));

        exercises.add(new Exercise(1, "Wyciskanie na ławce płaskiej", "Cwiczenie wykonywane na mięśnie klatki piersiowej.\n" +
                "Głowa utrzymuje stały kontakt z powierzchnią.\n" +
                "Kolana skierowane delikatnie do zewnątrz.\n" +
                "Stopy ułożone płasko na ziemii.\n" +
                "", "klatka", "https://cdn.prod.openfit.com/uploads/2016/09/19195609/dumbbell-bench-press-man-1024x512.jpg", "https://hips.hearstapps.com/ame-prod-menshealth-assets.s3.amazonaws.com/main/assets/bench-press-dumbell.gif?"));

        exercises.add(new Exercise(2, "Przysiad bułgarski", "Cwiczenie wykonywane na mięśnie pleców.\n" +
                "" +
                "Obciągniete palce u stopy położonej na powierzchni. \n" +
                "Kolano nie dotyka podłoża.", "plecy", "https://experiencelife.lifetime.life/wp-content/uploads/2021/03/Bulgarian-Split-Squat.jpg", "https://www.getstrong.fit/images/dumbbellbulgariangobletsplitsquat.gif"));

        exercises.add(new Exercise(3, "Rozpiętki", "Cwiczenie wykonywane na mięśnie rąk. \n" +
                "Głowa utrzymuje stały kontakt z powierzchnią.\n" +
                "Kolana skierowane delikatnie do zewnątrz\n" +
                "Stopy ułożone płasko na ziemii.\n", "rece", "https://api.army.mil/e2/c/images/2018/12/13/538956/original.jpg", "https://hips.hearstapps.com/ame-prod-menshealth-assets.s3.amazonaws.com/main/assets/fly-dumbbell-incline.gif"));

        exercises.add(new Exercise(4, "Wznosy bokiem", "Cwiczenie wykonywane na mięśnie pleców.\n" +
                "Ręce podnoszone ponad obręcz barkową.\n" +
                "Głowa pozostaje nieruchoma.\n", "plecy", "https://bodybuilding-wizard.com/wp-content/uploads/2014/05/standing-dumbbell-lateral-raise-exercise.jpg", "https://newlevelsport.pl/wp-content/uploads/2021/02/boczne-unoszenie-barkow.gif"));



        //Toast.makeText(CatalogActivity.context, "aaa", Toast.LENGTH_SHORT); ###

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(CATALOG_EXERCISE_KEY,  gson.toJson(exercises));
        editor.commit();
    }

    public static Utils getInstance(Context context) {
        if(null != instance) {
            return instance;
        }
        else {
            instance = new Utils(context);
            return instance;
        }
    }

    public ArrayList<Exercise> getCatalogExercises() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Exercise>>(){}.getType();
        ArrayList<Exercise> exercises = gson.fromJson(sharedPreferences.getString(CATALOG_EXERCISE_KEY, null), type);
        return exercises;
    }

    public ArrayList<Exercise> getStartExercises() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Exercise>>(){}.getType();
        ArrayList<Exercise> exercises = gson.fromJson(sharedPreferences.getString(START_EXERCISE_KEY, null), type);
        return exercises;
    }

    public ArrayList<TrainingEntry> getTrainingDetail() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<TrainingEntry>>(){}.getType();
        ArrayList<TrainingEntry> details = gson.fromJson(sharedPreferences.getString(TRAINING_DETAIL_KEY, null), type);
        // System.out.println("getTrainingDetail() details equals: " + details);
        return details;
    }

    public ArrayList<HistoryEntry> getHistoryExercises() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<HistoryEntry>>(){}.getType();
        ArrayList<HistoryEntry> histories = gson.fromJson(sharedPreferences.getString(HISTORY_EXERCISE_KEY, null), type);
        return histories;
    }

    public ArrayList<ArrayList<HistoryEntry>> getTrainingsHistory() {
        Gson gson = new Gson();
        // Type type = new TypeToken<ArrayList<ArrayList>>(){}.getType(); ĄĄĄ
        Type type = new TypeToken<ArrayList<ArrayList<HistoryEntry>>>(){}.getType(); // ĄĄĄ
        ArrayList<ArrayList<HistoryEntry>>  full_histories;
        if (null != sharedPreferences.getString(TRAININGS_HISTORY_KEY, null)) {
            // String a = gson.fromJson(sharedPreferences.getString(TRAININGS_HISTORY_KEY, null), type); bb
            // System.out.println("trainings_history equals: " + a); bb
            System.out.println("Inside getTrainingsHistory() nth time");
            full_histories = gson.fromJson(sharedPreferences.getString(TRAININGS_HISTORY_KEY, null), type);
        }
        else {
            System.out.println("Inside getTrainingsHistory() 1st time");
            full_histories = new ArrayList<ArrayList<HistoryEntry>>();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(TRAININGS_HISTORY_KEY,  gson.toJson(full_histories));
            editor.commit();
        }
        return full_histories;
    }

    public ArrayList<ArrayList<String>> getTrainingData() {
        Gson gson = new Gson();
        // Type type = new TypeToken<ArrayList<String>>(){}.getType(); ###
        Type type = new TypeToken<ArrayList<ArrayList<String>>>(){}.getType(); //TODO: TypeToken<ArrayList<ArrayList<String>>>
        // ArrayList<ArrayList> training_datas = gson.fromJson(sharedPreferences.getString(TRAINING_DATA_KEY, null), type); ###
        ArrayList<ArrayList<String>> training_datas = gson.fromJson(sharedPreferences.getString(TRAINING_DATA_KEY, null), type);
        return training_datas;
    }

    public int getCounterTrainingDetail() {
        Gson gson = new Gson();
        Type type = new TypeToken<String>(){}.getType();
        counterTrainingDetail = Integer.parseInt(gson.fromJson(sharedPreferences.getString(COUNTER_TRAINING_DETAIL, null), type));
        return counterTrainingDetail;
    }

    public int getTotalStartedExercises() {
        Gson gson = new Gson();
        Type type = new TypeToken<String>(){}.getType();
        // int total = Integer.parseInt(gson.fromJson(sharedPreferences.getString(TOTAL_STARTED_KEY, null), type)); ###
        totalStartedExercises = Integer.parseInt(gson.fromJson(sharedPreferences.getString(TOTAL_STARTED_KEY, null), type));
        //return total; ###
        return totalStartedExercises;
    }

    public int getInitStartedExercises() {
        Gson gson = new Gson();
        Type type = new TypeToken<String>(){}.getType();
        initStartedExercises = Integer.parseInt(gson.fromJson(sharedPreferences.getString(INIT_STARTED_KEY, null), type));
        return initStartedExercises;
    }

    public int getSingleDoneExercises() {
        Gson gson = new Gson();
        Type type = new TypeToken<String>(){}.getType();
        singleDoneExercises = Integer.parseInt(gson.fromJson(sharedPreferences.getString(SINGLE_DONE_KEY, null), type));
        return singleDoneExercises;
    }

    public int getProgressBarValue() {
        Gson gson = new Gson();
        Type type = new TypeToken<String>(){}.getType();
        // int progress = Integer.parseInt(gson.fromJson(sharedPreferences.getString(PROGRESSBAR_VALUE_KEY, null), type)); ###
        progressBarValue = Integer.parseInt(gson.fromJson(sharedPreferences.getString(PROGRESSBAR_VALUE_KEY, null), type));
        // progressBarValue = gson.fromJson(sharedPreferences.getString(PROGRESSBAR_VALUE_KEY, null), type); ###

        // return progress; ###
        return progressBarValue;
    }

    public boolean getStartedTrainingStatus() {
        Gson gson = new Gson();
        Type type = new TypeToken<String>(){}.getType();
        // startedTrainingStatus = Boolean.getBoolean(gson.fromJson(sharedPreferences.getString(STARTED_STATUS_KEY, null), type));
        startedTrainingStatus = Boolean.parseBoolean(gson.fromJson(sharedPreferences.getString(STARTED_STATUS_KEY, null), type));
        return startedTrainingStatus;
    }

    private boolean getIsStartedTrainingStatusSet() { //TODO: a to co
        Gson gson = new Gson();
        Type type = new TypeToken<String>(){}.getType();
        // isStartedTrainingStatusSet = Boolean.getBoolean(gson.fromJson(sharedPreferences.getString(__STARTED_STATUS_KEY, null), type));
        //return isStartedTrainingStatusSet;
        //boolean temp = Boolean.getBoolean(gson.fromJson(sharedPreferences.getString(__STARTED_STATUS_KEY, null), type));
        boolean temp = Boolean.parseBoolean(gson.fromJson(sharedPreferences.getString(STARTED_STATUS_KEY, null), type));
        System.out.println("temp equals: " + temp);
        return temp;
    }

    public Exercise getExerciseById(int id) {
        ArrayList<Exercise> exercises = getCatalogExercises();
        if (null != exercises) {
            for (Exercise e: exercises) {
                if (e.getId() == id) {
                    return e;
                }
            }
        }
        return null;
    }

    public boolean addToStartExercises(Exercise exercise) {
        ArrayList<Exercise> exercises = getStartExercises();
        if (null != exercises) {
            if (exercises.add(exercise)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(START_EXERCISE_KEY);
                editor.putString(START_EXERCISE_KEY, gson.toJson(exercises));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToHistoryExercises(HistoryEntry history) {
        ArrayList<HistoryEntry> histories = getHistoryExercises();
        if (null != histories) {
            System.out.println("Inside successful addtoHistoryExercises()");
            if (histories.add(history)) {
                //histories.add(history);
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(HISTORY_EXERCISE_KEY);
                editor.putString(HISTORY_EXERCISE_KEY, gson.toJson(histories));
                editor.commit();
                return true;
            }
            // }
        }
        return false;
    }



    public void updateProgress() {

        progressBarValue = (int) (((float) singleDoneExercises / initStartedExercises) * (initStartedExercises / (float) totalStartedExercises) * 100);
        addProgressBarValue(progressBarValue);

    }


    public void updateStartedTrainingStatus() {
        System.out.println("pre Status in Utils equals: " + startedTrainingStatus);
        startedTrainingStatus = !startedTrainingStatus;
        System.out.println("post Status in Utils equals: " + startedTrainingStatus);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(STARTED_STATUS_KEY);
        editor.putString(STARTED_STATUS_KEY, gson.toJson(String.valueOf(startedTrainingStatus)));
        editor.commit();
    }


    public void updateTrainingDetail(ArrayList<TrainingEntry> entry) { //TODONE: trzeba jakos zrobic updateTotalWeight
        ArrayList<TrainingEntry> details = entry;
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TRAINING_DETAIL_KEY);
        editor.putString(TRAINING_DETAIL_KEY, gson.toJson(details));
        editor.commit();

    }

    public void updateTrainingDetail(boolean initial) {

        int id;
        ArrayList<TrainingEntry> details;
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (initial) {
            details = new ArrayList<TrainingEntry>();
            id = 0;
        } else {
            details = getTrainingDetail();
            editor.remove(TRAINING_DETAIL_KEY);
            id = getCounterTrainingDetail();
        }
        TrainingEntry init = new TrainingEntry(id, 0, 0, 0, 0, 0, 0, 0, 0, "", "");
        //TODO: tu nie powinien być increment countera?
        details.add(init);
        editor.putString(TRAINING_DETAIL_KEY, gson.toJson(details));
        editor.commit();
    }


    public boolean addToTrainingsHistory() {

        ArrayList <HistoryEntry> histories =  getHistoryExercises();

        ArrayList<ArrayList<HistoryEntry>> full_histories =  getTrainingsHistory();
        if(null != full_histories) {
            if (null != histories) {
                if(full_histories.add(histories)) {
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(TRAININGS_HISTORY_KEY);
                    editor.putString(TRAININGS_HISTORY_KEY, gson.toJson(full_histories));
                    editor.commit();
                    return true;
                }
            }
        }
        return false;

    }


    public boolean addToTrainingData(int pos, ArrayList<String> data) {

        ArrayList<ArrayList<String>> datas = getTrainingData();
        if (null != datas) {
                // datas.remove(pos);
                // if (datas.add(pos, data)) {
                // datas.add(pos, data);
                datas.set(pos, data);
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(TRAINING_DATA_KEY);
                editor.putString(TRAINING_DATA_KEY, gson.toJson(datas));
                editor.commit();
                return true;
            }
            // }
        // }
        return false;
    }


    public void addCounterTrainingDetail() {
        counterTrainingDetail++;
        System.out.println("Inside addCounterTrainingDetail: " + counterTrainingDetail);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(COUNTER_TRAINING_DETAIL); //hotfix
        editor.putString(COUNTER_TRAINING_DETAIL, gson.toJson(String.valueOf(counterTrainingDetail)));
        editor.commit();
    }

    public void addTotalStartedExercises() {
        totalStartedExercises++;
        System.out.println("Inside addTotalStartedExercises(): " + totalStartedExercises);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOTAL_STARTED_KEY);
        editor.putString(TOTAL_STARTED_KEY, gson.toJson(String.valueOf(totalStartedExercises)));
        editor.commit();
    }

    public void addSingleDoneExercises() {
        singleDoneExercises++;
        System.out.println("Inside addSingleDoneExercises: " + singleDoneExercises);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SINGLE_DONE_KEY);
        editor.putString(SINGLE_DONE_KEY, gson.toJson(String.valueOf(singleDoneExercises))); //TODO: sprawdz bez String.valueOf()
        editor.commit();
    }

    public void addInitStartedExercises(int val) {
        initStartedExercises = val;
        System.out.println("Inside initStartedExercises " + initStartedExercises);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(INIT_STARTED_KEY);
        editor.putString(INIT_STARTED_KEY, gson.toJson(String.valueOf(initStartedExercises)));
        editor.commit();
    }

    public void addProgressBarValue(int progress) {
        progressBarValue = progress;
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PROGRESSBAR_VALUE_KEY);
        editor.putString(PROGRESSBAR_VALUE_KEY, gson.toJson(String.valueOf(progressBarValue)));
        editor.commit();

    }

    public boolean removeFromStartExercises(Exercise exercise) {
        ArrayList<Exercise> exercises= getStartExercises();
        if (null != exercises) {
            for (Exercise e: exercises) {
                if (e.getId() == exercise.getId()) {
                    if(exercises.remove(e)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(START_EXERCISE_KEY);
                        editor.putString(START_EXERCISE_KEY, gson.toJson(exercises));
                        editor.commit();
                        return true;
                    }

                }
            }
        }
        return false;
    }


    public boolean removeFromHistoryExercises(HistoryEntry history) { // raczej redundant
        ArrayList<HistoryEntry> histories = getHistoryExercises();
        if (null != histories) {
            // int i = 0;
            for (HistoryEntry hs: histories) {
                if (hs.getId() == history.getId()) {
                    if(histories.remove(hs)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(HISTORY_EXERCISE_KEY);
                        editor.putString(HISTORY_EXERCISE_KEY, gson.toJson(histories));
                        editor.commit();
                        return true;
                    }

                }
                // i++;
            }
        }
        return false;
    }

    public void removeHistoryExercises() {

        ArrayList<HistoryEntry> histories = new ArrayList<HistoryEntry>();
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(HISTORY_EXERCISE_KEY);
        editor.putString(HISTORY_EXERCISE_KEY, gson.toJson(histories));
        editor.commit();
    }



    public void removeTotalStartedExercises() {

        totalStartedExercises = 0;
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOTAL_STARTED_KEY);
        editor.putString(TOTAL_STARTED_KEY, gson.toJson(String.valueOf(totalStartedExercises)));
        editor.commit();
    }

    public void removeInitStartedExercises() {

        initStartedExercises = 0;
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(INIT_STARTED_KEY);
        editor.putString(INIT_STARTED_KEY, gson.toJson(String.valueOf(initStartedExercises)));
        editor.commit();
    }

    public void removeSingleDoneExercises() {

        singleDoneExercises = 0;
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SINGLE_DONE_KEY);
        editor.putString(SINGLE_DONE_KEY, gson.toJson(String.valueOf(singleDoneExercises)));
        editor.commit();
    }

    public void removeProgressBarValue() {
        progressBarValue = 0;
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PROGRESSBAR_VALUE_KEY);
        editor.putString(PROGRESSBAR_VALUE_KEY, gson.toJson(String.valueOf(progressBarValue)));
        editor.commit();
    }

    public boolean removeFromTrainingData(int pos) { //redundant
        ArrayList<ArrayList<String>> datas = getTrainingData();
        if (null != datas) {
            ArrayList<String> data = new ArrayList();
            data.add("cdf");
            data.add("cdf");
            data.add("cdf");
            // datas.add(pos, data);
            datas.set(pos, data);
            // if(datas.remove(pos)) {
            Gson gson = new Gson();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(TRAINING_DATA_KEY);
            editor.putString(TRAINING_DATA_KEY, gson.toJson(datas));
            editor.commit();
            return true;
           // }
        }
        return false;
    }
}
