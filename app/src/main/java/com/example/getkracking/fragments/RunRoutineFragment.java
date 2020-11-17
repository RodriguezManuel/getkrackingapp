package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.dialogs.ErrorDialog;
import com.example.getkracking.dialogs.ExitRoutineDialog;

public class RunRoutineFragment extends Fragment {
    private TextView countDownText;
    private Button countDownButton;

    //Variables para el timer
    private CountDownTimer countDownTimer;
    private long timeLeftInMiliseconds = 6000; //10 mins
    private boolean timerRunning;

    public RunRoutineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(false);
        Toolbar mToolBar =  ((HomeActivity) getActivity()).findViewById(R.id.homeTopBar);
        ActionBar actionBar = ((HomeActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.routine_in_ejecution);

        //CUSTOMIZAR BACK BUTTON
        ((HomeActivity) getActivity()).setSupportActionBar(mToolBar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationIcon(R.drawable.ic_chevron_left);
        mToolBar.setNavigationOnClickListener(v -> openExitDialog());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        View listChanger = vista.findViewById(R.id.ListToggleInRoutine);
        listChanger.setOnClickListener(v1 -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.listRoutineFragment);
        });

        return vista;
    }

    public void openExitDialog() {
        ExitRoutineDialog dialog = new ExitRoutineDialog();
        dialog.show(getActivity().getSupportFragmentManager(), "Exit routine execution");
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
                Toast.makeText(getContext(), "MBEHH",Toast.LENGTH_LONG).show();
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