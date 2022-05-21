package com.example.plantobefit2;

import static com.example.plantobefit2.SingleActivity.EXERCISE_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;

public class exerciseRecyclerViewAdapter extends RecyclerView.Adapter<exerciseRecyclerViewAdapter.MyViewHolder>{

    private ArrayList<Exercise> exerciseList = new ArrayList<>();
    //private ArrayList<ArrayList<HistoryEntry>> historyList = new ArrayList<ArrayList<HistoryEntry>>();
    private ArrayList<HistoryEntry> historyList = new ArrayList<HistoryEntry>();
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<ArrayList> datas = new ArrayList<>();
    private Context context;
    private String parentActivity;
    private Listener listener  = null;


////    public exerciseRecyclerViewAdapter(Context context, ArrayList<Exercise> exerciseList) {
////        this.exerciseList = exerciseList;
////        this.context = context;
////    }

    public exerciseRecyclerViewAdapter(Context context, String parentActivity){
        this.context = context;
        this.parentActivity = parentActivity;
    }

    public interface Listener{
        void onItemClick();
    }

    void setListener(Listener listener){
        this.listener = listener;
    }

    //nonull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_exercise1, parent, false);
        return new MyViewHolder(view);
    }

    //nonull
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        System.out.println("exerciseRecyclerViewAdapter position equals: " + position);


        if (!parentActivity.equals("historyExercises")) {
            holder.txtExerciseName1.setText(exerciseList.get(position).getName());
            holder.txtExerciseCategory1.setText(exerciseList.get(position).getCategory());
            Glide.with(context)
                    .asBitmap()
                    .load(exerciseList.get(position).getImageURL())
                    .placeholder(R.drawable.ic_clepsydra)
                    .into(holder.imgExercise1);
            // holder.imgExercise.setImageResource(exerciseList.get(position).getImageResource());


            //holder.txtSetsData.setText(Utils.getInstance(context).getTrainingsData().get(position).get(0));
            //holder.txtRepsData.setText(Utils.getInstance(context).getTrainingsData().get(position).get(1));
            //holder.txtWeightData.setText(Utils.getInstance(context).getTrainingsData().get(position).get(2));

            // holder.txtSetsData.setText((String) Utils.getInstance(context).getTrainingsData().get(position).get(0));

            //holder.txtSetsData.setText(String.valueOf(Utils.getInstance(context).getTrainingsData().get(exerciseList.get(position).getId()).get(0)));
            // holder.txtRepsData.setText(String.valueOf(Utils.getInstance(context).getTrainingsData().get(exerciseList.get(position).getId()).get(1)));
            // holder.txtWeightData.setText(String.valueOf(Utils.getInstance(context).getTrainingsData().get(exerciseList.get(position).getId()).get(2)));

            holder.txtSetsData.setText(Utils.getInstance(context).getTrainingData().get(exerciseList.get(position).getId()).get(0));
            holder.txtRepsData.setText(Utils.getInstance(context).getTrainingData().get(exerciseList.get(position).getId()).get(1));
            holder.txtWeightData.setText(Utils.getInstance(context).getTrainingData().get(exerciseList.get(position).getId()).get(2));
        }
        else {
            // holder.txtRepsData.setText((String)Utils.getInstance(context).getTrainingsData().get(position).get(1));
            // holder.txtWeightData.setText((String)Utils.getInstance(context).getTrainingsData().get(position).get(2));

            Exercise e = Utils.getInstance(context).getExerciseById(historyList.get(position).getId());
            holder.txtExerciseCategory1.setText(e.getCategory());
            holder.txtExerciseName1.setText(e.getName());
            Glide.with(context)
                    .asBitmap()
                    .load(e.getImageURL())
                    .into(holder.imgExercise1);
            // holder.txtSetsData.setText(historyList.get(position).getSets());
            holder.txtSetsData.setText(String.valueOf(historyList.get(position).getSets()));
            holder.txtRepsData.setText(String.valueOf(historyList.get(position).getReps()));
            holder.txtWeightData.setText(String.valueOf(historyList.get(position).getWeight()));
        }

        holder.parent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, exerciseList.get(holder.getAdapterPosition()).getName() + "Clicked", Toast.LENGTH_SHORT).show();
                if (!parentActivity.equals("historyExercises")) {
                    Intent intent = new Intent(context, SingleActivity.class);
                    intent.putExtra(EXERCISE_ID_KEY, exerciseList.get(position).getId());
                    context.startActivity(intent);
                    //intent.putExtra(EXERCISE_ID_KEY, exerciseList.get(holder.getAdapterPosition()).getId()); // "excerciseId" ###
                } else {}

            }
        });

        //ProgressBar pBar; ###
        // pBar = findViewById(R.id.pBar); ###

       if (parentActivity.equals("catalogExercises")) {
           holder.btnExerciseFinished.setVisibility(View.GONE);
           holder.txtExerciseSets1.setVisibility(View.GONE);
           holder.txtExerciseReps1.setVisibility(View.GONE);
           holder.txtExerciseWeight1.setVisibility(View.GONE);
           holder.txtWeightData.setVisibility(View.GONE);
           holder.txtSetsData.setVisibility(View.GONE);
           holder.txtRepsData.setVisibility(View.GONE);

       }
       else if (parentActivity.equals("startExercises")) {
           holder.btnExerciseFinished.setVisibility(View.VISIBLE);
           holder.txtExerciseSets1.setVisibility(View.VISIBLE);
           holder.txtExerciseReps1.setVisibility(View.VISIBLE);
           holder.txtExerciseWeight1.setVisibility(View.VISIBLE);
           holder.txtWeightData.setVisibility(View.VISIBLE);
           holder.txtSetsData.setVisibility(View.VISIBLE);
           holder.txtRepsData.setVisibility(View.VISIBLE);





           holder.btnExerciseFinished.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   // String n = exerciseList.get(position).getName(); ###
                   String name = holder.txtExerciseName1.getText().toString();
                   String name1 = String.valueOf(holder.txtExerciseName1);
                   // int reps = Integer.parseInt(holder.txtRepsData.getText().toString());
                   // int sets = Integer.parseInt(holder.txtSetsData.getText().toString());
                   // int weight = Integer.parseInt(holder.txtWeightData.getText().toString());
                   int id = exerciseList.get(position).getId();

                   // String name = exerciseList.get(position).getName();

                   int reps = Integer.parseInt(holder.txtRepsData.getText().toString()); //TODO: gdy nic nie ma to czy 0 czy null?
                   int sets = Integer.parseInt(holder.txtSetsData.getText().toString());
                   int weight = Integer.parseInt(holder.txtWeightData.getText().toString());

                   System.out.println("Reps + sets + weight equals: " + (reps + sets + weight));

                   if(Utils.getInstance(context).removeFromStartExercises(exerciseList.get(position))) {
                       System.out.println("ExerciseRecyclerViewAdapter pre now");

                       Utils.getInstance(context).addSingleDoneExercises();

                       //if(0 != Utils.getInstance(context).getTrainingsHistory().size()) {
                       //if(!Utils.getInstance(context).getTrainingDetail().get(position).get(5).getClass().getSimpleName().equals("Date")) {
                       if(Utils.getInstance(context).getSingleDoneExercises() == 1) {
                           System.out.println("Inside getSingleDoneExercises() == 1");
                           Date startDate = java.util.Calendar.getInstance().getTime();
                           long date = startDate.getTime();
                           // float date = 1;
                           System.out.println("Start date equals: " + date);
                           ArrayList<TrainingEntry> details = Utils.getInstance(context).getTrainingDetail();
                           if (null != details) {
                               // details.get(position).setDate_start(date); //TODONE: cos innego zamiast position
                               details.get(Utils.getInstance(context).getCounterTrainingDetail()).setDate_start(date);
                               Utils.getInstance(context).updateTrainingDetail(details);
                           }

                       }

                       // HistoryEntry history = new HistoryEntry(name, 1, reps, sets, weight);
                       // HistoryEntry history = new HistoryEntry(name, id, 2, 3, 4); ZZZ
                       HistoryEntry history = new HistoryEntry(name, id, reps, sets, weight); // TODO: dodać ilosc powtorzen itp, 173-176, TODONE?
                       // history.name = exerciseList.get(position).getName();
                       Utils.getInstance(context).addToHistoryExercises(history);

                       boolean preStatus = Utils.getInstance(context).getStartedTrainingStatus();
                       System.out.println("PreStatus equals: " + preStatus);


                       if(listener != null){
                           listener.onItemClick();
                       }

                       boolean postStatus = Utils.getInstance(context).getStartedTrainingStatus();
                       System.out.println("PostStatus equals: " + postStatus);

                       Toast.makeText(context, exerciseList.get(position).getName() + " Skończone", Toast.LENGTH_SHORT).show();

                       // if(!(preStatus == true && postStatus == false)) { // TODO: potencjalny zgrzyt zarzegnany
                           Intent intent = new Intent(context, StartActivity.class);
                           context.startActivity(intent);
                      // }
                       //else {
                           //Toast.makeText(context, exerciseList.get(position).getName() + " Trening skończony", Toast.LENGTH_SHORT).show();
                       //}
                       System.out.println("ExerciseRecyclerViewAdapter post now");

                       notifyDataSetChanged();

                   }
                   //Toast.makeText(context, exerciseList.get(position).getName() + "Clicked", Toast.LENGTH_SHORT).show();
               }
           });
       }
       else if(parentActivity.equals("historyExercises")) {
           holder.btnExerciseFinished.setVisibility(View.GONE);
           holder.txtExerciseSets1.setVisibility(View.VISIBLE);
           holder.txtExerciseReps1.setVisibility(View.VISIBLE);
           holder.txtExerciseWeight1.setVisibility(View.VISIBLE);
           holder.txtWeightData.setVisibility(View.VISIBLE);
           holder.txtSetsData.setVisibility(View.VISIBLE);
           holder.txtRepsData.setVisibility(View.VISIBLE);

       }
       else {
           holder.btnExerciseFinished.setVisibility(View.GONE);
       }

    }

    @Override
    public int getItemCount() {
        if (!parentActivity.equals("historyExercises")) {
            return exerciseList.size();
        }
        else {
            return historyList.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView parent1;
        private ImageView imgExercise1;
        private TextView txtExerciseName1;
        private TextView txtExerciseCategory1;
        private Button btnExerciseFinished;

        private TextView txtExerciseSets1;
        private TextView txtExerciseReps1;
        private TextView txtExerciseWeight1;

        private TextView txtSetsData;
        private TextView txtRepsData;
        private TextView txtWeightData;

        //nonull
        public MyViewHolder( View itemView) {
            super(itemView);
            parent1 = itemView.findViewById(R.id.parent1);
            imgExercise1 = itemView.findViewById(R.id.imgExercise1);
            txtExerciseName1 = itemView.findViewById(R.id.txtTrainingName1);
            txtExerciseCategory1 = itemView.findViewById(R.id.txtExerciseCategory1);
            btnExerciseFinished = itemView.findViewById(R.id.btnExerciseFinished);

            txtExerciseSets1 = itemView.findViewById(R.id.txtTrainingDuration);
            txtExerciseReps1 = itemView.findViewById(R.id.txtTrainingNote);
            txtExerciseWeight1 = itemView.findViewById(R.id.txtTrainingTotal);

            txtSetsData = itemView.findViewById(R.id.txtDurationData);
            txtRepsData = itemView.findViewById(R.id.txtNoteData);
            txtWeightData = itemView.findViewById(R.id.txtTotalData);

        }
    }

    public void setExerciseList(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
        notifyDataSetChanged();

    }


    public void setHistoryList(ArrayList<HistoryEntry> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

}
