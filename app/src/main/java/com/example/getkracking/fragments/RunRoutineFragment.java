package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.getkracking.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RunRoutineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RunRoutineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView countDownText;
    private Button countDownButton;

    //Variables para el timer
    private CountDownTimer countDownTimer;
    private long timeLeftInMiliseconds = 600000; //10 mins
    private boolean timerRunning;

    public RunRoutineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RunRoutineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RunRoutineFragment newInstance(String param1, String param2) {
        RunRoutineFragment fragment = new RunRoutineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_run_routine, container, false);
        countDownText = vista.findViewById(R.id.timerInRunRoutine);
        countDownButton= vista.findViewById(R.id.buttonInRunRoutine);

        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        updateTimer();

        return vista;
    }






    //METODOS PARA EL TIMER

    public void startStop(){
        if(timerRunning){
            stopTimer();
        }
        else{
            startTimer();
        }
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMiliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMiliseconds = millisUntilFinished; //actualizo el tiempo restante
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start(); //lo empieza de inmediato
        countDownButton.setText("Pausar");
        timerRunning = true;
    }

    public void stopTimer(){
        countDownTimer.cancel();
        countDownButton.setText("Continuar");
        timerRunning = false;
    }

    public void updateTimer(){
        int minutes = (int) timeLeftInMiliseconds / 60000;
        int seconds = (int) timeLeftInMiliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if(seconds < 10){
            timeLeftText += "0";
        }
        timeLeftText += ""+seconds;
        countDownText.setText(timeLeftText);
    }
}