package com.example.plantobefit2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener,NickDialogFragment.OnSend {

    private FirebaseAuth auch;
    private FirebaseFirestore db;
    private EditText email, password;
    private Button log;
    private TextView status;
    private FirebaseUser user;

    public LoginFragment(TextView status) {
        this.status=status;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Logowanie");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        log = view.findViewById(R.id.loguj);
        log.setOnClickListener(this);
        email = view.findViewById(R.id.email2);
        password = view.findViewById(R.id.hasło2);
        db = FirebaseFirestore.getInstance();
        auch = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (!User.iflog) {
            login();

        }
    }

    public void login() {

        auch.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                .addOnCompleteListener(getActivity(), new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            user = auch.getCurrentUser();
                            if (user.isEmailVerified()) {

                                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                                DocumentReference docIdRef = rootRef.collection("User").document(user.getUid());
                                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Toast.makeText(getActivity(), "Zalogowano", Toast.LENGTH_SHORT).show();
                                                User.iflog = true;
                                                status.setText((User.iflog == true ? "Zalogowany" : "Wylogowany"));


                                                //TODO:pobieranie z Firestora, wyzerowanie treningu,

//                                                Utils.getInstance(getActivity()).removeTotalStartedExercises();
//                                                System.out.println("####1");
//                                                Utils.getInstance(getActivity()).removeProgressBarValue(); //TODONE: dopiero gdy zostanie dodane nowe cwiczenie
//                                                System.out.println("####2");
//                                                Utils.getInstance(getActivity()).removeSingleDoneExercises();
//                                                System.out.println("####3");
//                                                Utils.getInstance(getActivity()).removeInitStartedExercises();
//                                                System.out.println("####4");
//                                                Utils.getInstance(getActivity()).updateStartedTrainingStatus();

//                                                try {
//                                                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//                                                    firestore.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                            if (task.isSuccessful()) {
//                                                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                                                    Map<String, Object> doc = document.getData();
//
//                                                                    String nick = (String) doc.get("Nick");
//
//                                                                    for(String key : doc.keySet()) {
//                                                                        if (key.startsWith("Trening")) {
//                                                                            System.out.println(key);
//
//                                                                            Map<Integer, Object> rec = (Map<Integer, Object>) doc.get(key);
//                                                                            for(int id: rec.keySet()) {
//                                                                                int eid = id;
//                                                                                Map<String, Object> rec2 = (Map<String, Object>) rec.get(key);
//                                                                                int ereps = Integer.parseInt(String.valueOf((Integer) rec2.get("Reps")));
//                                                                                System.out.println("eid equals : " + eid + "ereps equals: " + ereps);
//
//                                                                                //TODO:
//                                                                                // HistoryEntry, details, dodanie do sharedPreferences,
//                                                                                // updateTrainingDetail wyzeruje historie, manualnie counter na ty ile będzie teraz
//
//                                                                            }
//
//
//                                                                        }
//
//
//                                                                    }
//                                                                }
//                                                                //new UpdateAlco().execute();
//
//                                                            } else {
//
//                                                            }
//                                                        }
//                                                    });
//
//                                                    //return true;
//
//
//                                                }catch (Exception e) {
//                                                    System.out.println("error");
//                                                }



                                            } else {
                                                NickDialogFragment dialog = new NickDialogFragment();
                                                dialog.setTargetFragment(LoginFragment.this, 1);
                                                dialog.show(getFragmentManager(), "Dialog2");
                                            }
                                        }
                                    }
                                });


                            } else {
                                auch.signOut();
                                Toast.makeText(getActivity(), "Potwierdz email", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else{
                            Toast.makeText(getActivity(), "Złe hasło lub email", Toast.LENGTH_SHORT).show();

                        }
                    }

                });

    }

    @Override
    public void respondData(String nick) {
        Map map = new HashMap<String, Object>();
        map.put("Nick", nick);
        map.put("Email", email.getText().toString().trim());
        map.put("password", password.getText().toString().trim());
        map.put("Data", new Timestamp(new Date()));
        db.collection("User").document(user.getUid()).set(map);
        Toast.makeText(getActivity(),"Gratuluje 1 loginu",Toast.LENGTH_SHORT).show();
        User.iflog = true;
        status.setText((User.iflog==true ? "Zalogowany" : "Wylogowany"));
    }
}


