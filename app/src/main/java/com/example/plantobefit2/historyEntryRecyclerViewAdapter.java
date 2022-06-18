package com.example.plantobefit2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class historyEntryRecyclerViewAdapter extends RecyclerView.Adapter<historyEntryRecyclerViewAdapter.historyEntryMyViewHolder>{
    private ArrayList<ArrayList<HistoryEntry>> historyList = new ArrayList<ArrayList<HistoryEntry>>();
    private Context context;


    public historyEntryRecyclerViewAdapter(Context context){
        this.context = context;
    }

    public interface historyExerciseListener{
        void onItemClick();
    }

//    void setListener(Listener listener){
//        this.listener = listener;
//    }

    //nonull
    @Override
    public historyEntryMyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_entry, parent, false);
        return new historyEntryMyViewHolder(view);
    }

    //nonull
    @Override
    public void onBindViewHolder(historyEntryMyViewHolder holder, final int position) {

        System.out.println("historyEntryRecyclerViewAdapter position equals: " + position);

        //ArrayList<HistoryEntry> temp = new ArrayList<HistoryEntry>(); //
        //temp = historyList.get(position);
        //System.out.println("Inside ArrayList<HistoryEntry> temp = historyList.get(position): " +temp.get(0).getName());


        holder.txtNoteData.setText(String.valueOf( Utils.getInstance(context).getTrainingDetail().get(position).getNote()));
        // holder.txtDurationData.setText(String.valueOf(Utils.getInstance(context).getTrainingDetail().get(position).getDuration())); ZZZ
        holder.txtDurationData.setText(Utils.getInstance(context).getTrainingDetail().get(position).getTime_formated());

        //ArrayList<ArrayList<HistoryEntry>> histories = Utils.getInstance(context).getTrainingsHistory(); XXX
        //ArrayList<HistoryEntry> history = Utils.getInstance(context).getTrainingsHistory().get(position);//TODO: możliwa implementacja w setTotalWeight
        // ArrayList<HistoryEntry> history = histories.get(position);
        //HistoryEntry history_entry = history.get(0);

        System.out.println("historyList weight equals: " + historyList.get(position).get(0).getWeight());
        int total = 0;
        for (int i = 0; i < historyList.get(position).size(); i++) {
            total += historyList.get(position).get(i).getReps() * historyList.get(position).get(i).getSets() * historyList.get(position).get(i).getWeight();
        }
        ArrayList<TrainingEntry> details = Utils.getInstance(context).getTrainingDetail();
        if(null != details) {
            details.get(position).setWeight_total(total);
            details.get(position).setWeight_total_formated();
            Utils.getInstance(context).updateTrainingDetail(details);

            // holder.txtTotalData.setText(String.valueOf(total));
            holder.txtTotalData.setText(details.get(position).getWeight_total_formated());
        }

        holder.imgArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Inside imgArrowDown onClick()");
                holder.buildExerciseRecycleView();
                holder.exerciseAdapter.setHistoryList(historyList.get(position));
                //holder.txtNoteData.setText("abc");
                holder.extended.setVisibility(View.VISIBLE);
                holder.imgArrowDown.setVisibility(View.GONE);

                 holder.rBarFocus.setRating(Utils.getInstance(context).getTrainingDetail().get(position).getFocus());
                 holder.rBarSatisfaction.setRating(Utils.getInstance(context).getTrainingDetail().get(position).getSatisfaction());
                 holder.rBarExhaust.setRating(Utils.getInstance(context).getTrainingDetail().get(position).getExhaust());
                 holder.rBarMotivation.setRating(Utils.getInstance(context).getTrainingDetail().get(position).getMotivation());



                /*
                if(holder.exhaust_listener != null){
                    holder.exhaust_listener.onItemClick();
                }
                if(holder.motivation_listener != null){
                    holder.motivation_listener.onItemClick();
                }

                if(holder.focus_listener != null){
                    holder.focus_listener.onItemClick();
                }

                if(holder.satisfaction_listener != null){
                    holder.satisfaction_listener.onItemClick();
                } */




                /*
                holder.setExhaustListener(new Listener() {
                    @Override
                    public void onItemClick() {
                        System.out.println("Inside exhaust");
                    }
                });

                holder.setFocusListener(new Listener() {
                    @Override
                    public void onItemClick() {
                        System.out.println("Inside focus");
                    }
                });

                holder.setMotivationListener(new Listener() {
                    @Override
                    public void onItemClick() {
                        System.out.println("Inside motivation");
                    }
                });

                holder.setSatisfactionListener(new Listener() {
                    @Override
                    public void onItemClick() {
                        System.out.println("Inside satisfaction");
                    }
                });
                */

                //notifyDataSetChanged();


            }
        });

        /*holder.rBarSatisfaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Inside satisfaction");
            }
        });
        holder.rBarExhaust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Inside exhaust");
            }
        });

        holder.rBarFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Inside focus");
            }
        });

        holder.rBarMotivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Inside motivation");
            }
        }); */

        holder.imgArrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // holder.txtNoteData.setText("abc");
                System.out.println("Inside imgArrowUp onClick()");
                holder.extended.setVisibility(View.GONE);
                holder.imgArrowDown.setVisibility(View.VISIBLE);

                ArrayList<TrainingEntry> details = Utils.getInstance(context).getTrainingDetail();
                if(null != details) {
                    details.get(position).setMotivation(holder.rBarMotivation.getRating());
                    details.get(position).setSatisfaction(holder.rBarSatisfaction.getRating());
                    details.get(position).setExhaust(holder.rBarExhaust.getRating());
                    details.get(position).setFocus(holder.rBarFocus.getRating());
                    details.get(position).setNote();
                    Utils.getInstance(context).updateTrainingDetail(details);
                }

                //TODO: intent activity_entry

            }
        });

    }



    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class historyEntryMyViewHolder extends RecyclerView.ViewHolder {

        private CardView parent_entry;
        private TextView txtTrainingName1;
        private TextView txtTrainingDuration;
        private TextView txtTrainingTotal;
        private TextView txtTrainingNote;
        private TextView txtTotalData;
        private TextView txtDurationData;
        private TextView txtNoteData;

        private ImageView imgArrowDown;


        private RatingBar rBarExhaust;
        private RatingBar rBarFocus;
        private RatingBar rBarMotivation;
        private RatingBar rBarSatisfaction;
        private TextView txtExhaustLvl;
        private TextView txtFocusLvl;
        private TextView txtMotivationLvl;
        private TextView txtSatisfactionLvl;
        // private RecyclerView rvHistory;
        private ImageView imgArrowUp;

        exerciseRecyclerViewAdapter exerciseAdapter ;

        private ConstraintLayout extended;
        /*
        private historyExerciseListener focus_listener = null;
        private historyExerciseListener exhaust_listener = null;
        private historyExerciseListener motivation_listener = null;
        private historyExerciseListener satisfaction_listener = null; */
        //getTxtTrainingName

        //nonull
        public historyEntryMyViewHolder(View itemView) {
            super(itemView);
            parent_entry = itemView.findViewById(R.id.parent_entry);
            txtTrainingName1 = itemView.findViewById(R.id.txtTrainingName1);
            txtTrainingDuration = itemView.findViewById(R.id.txtTrainingDuration);
            txtTrainingNote = itemView.findViewById(R.id.txtTrainingNote);
            txtTrainingTotal = itemView.findViewById(R.id.txtTrainingTotal);
            txtTotalData = itemView.findViewById(R.id.txtTotalData);
            txtNoteData = itemView.findViewById(R.id.txtNoteData);
            txtDurationData = itemView.findViewById(R.id.txtDurationData);
            imgArrowDown = itemView.findViewById(R.id.imgArrowDown);

            txtExhaustLvl = itemView.findViewById(R.id.txtExhaustLvl);
            txtFocusLvl = itemView.findViewById(R.id.txtFocusLvl);
            txtMotivationLvl = itemView.findViewById(R.id.txtMotivationLvl);
            txtSatisfactionLvl = itemView.findViewById(R.id.txtSatisfactionLvl);
            rBarExhaust = itemView.findViewById(R.id.rBarExhaust);
            rBarFocus = itemView.findViewById(R.id.rBarFocus);
            rBarMotivation = itemView.findViewById(R.id.rBarMotivation);
            rBarSatisfaction = itemView.findViewById(R.id.rBarSatisfaction);
            // rvHistory = itemView.findViewById(R.id.rvHistory);
            imgArrowUp = itemView.findViewById(R.id.imgArrowUp);

            extended = itemView.findViewById(R.id.extended);
        }

//        public interface EntryListener{
//            void onItemClick();
//        }

        /*
        void setExhaustListener(historyExerciseListener listener) {
            this.exhaust_listener = listener;
        }

        void setMotivationListener(historyExerciseListener listener) {
            this.motivation_listener = listener;
        }
        void setSatisfactionListener(historyExerciseListener listener) {
            this.satisfaction_listener = listener;
        }
        void setFocusListener(historyExerciseListener listener) {
            this.focus_listener = listener;
        } */

        public void buildExerciseRecycleView() {

            RecyclerView exerciseRecView = itemView.findViewById(R.id.rvHistory);
            exerciseAdapter = new exerciseRecyclerViewAdapter(context, "historyExercises");
            exerciseRecView.setAdapter(exerciseAdapter);
            exerciseRecView.setLayoutManager(new LinearLayoutManager(context));
        }

    }

    public void setHistoryList(ArrayList<ArrayList<HistoryEntry>> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

}



