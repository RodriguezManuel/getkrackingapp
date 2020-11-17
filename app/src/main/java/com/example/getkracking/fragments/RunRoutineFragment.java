package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.dialogs.ExitRoutineDialog;
import com.example.getkracking.entities.CycleVO;
import com.example.getkracking.entities.ExerciseVO;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RunRoutineFragment extends Fragment {
    //para desactivarlos mientras se ejecuta la rutina
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton homeButton;
    ArrayList<CycleVO> cycles = new ArrayList<>();
    int actualCycle = 0, actualExercise = 0;

    TextView sectionName, exerciseName, exerciseDesc;
    private TextView countDownText;
    private Button countDownButton;

    //Variables para el timer
    private CountDownTimer countDownTimer;
    private long timeLeftInMiliseconds; //en caso de que se corra con tiempo
    private boolean timerRunning, timedExercise;

    public RunRoutineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(false);
        Toolbar mToolBar = ((HomeActivity) getActivity()).findViewById(R.id.homeTopBar);
        ActionBar actionBar = ((HomeActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.routine_in_ejecution);

        //CUSTOMIZAR BACK BUTTON
        ((HomeActivity) getActivity()).setSupportActionBar(mToolBar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationIcon(R.drawable.ic_chevron_left);
        mToolBar.setNavigationOnClickListener(v -> openExitDialog());


        bottomAppBar = getActivity().findViewById(R.id.bottomAppBar);
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        homeButton = getActivity().findViewById(R.id.fabBottomAppBar);

        bottomAppBar.setVisibility(View.GONE);
        bottomNavigationView.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        bottomAppBar.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        homeButton.setVisibility(View.VISIBLE);
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
        countDownButton = vista.findViewById(R.id.buttonInRunRoutine);

        countDownButton.setOnClickListener(v -> {
            if (timedExercise)
                startStop();
            else
                runNextExercise();
        });

//        View listChanger = vista.findViewById(R.id.ListToggleInRoutine);
//        listChanger.setOnClickListener(v1 -> {
//
//            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.listRoutineFragment);
//        });

        if (getArguments() != null) {
        }
        //CONSEGUIR LISTA DESDE INFOFRAGMENT??

        ArrayList<ExerciseVO> exercises = new ArrayList<>();
        exercises.add(new ExerciseVO("SALtos", 16, 0));
        exercises.add(new ExerciseVO("PATADAS", 5, 0));
        exercises.add(new ExerciseVO("nada", 0, 5));
        cycles.add(new CycleVO("Calentamiento", exercises));
        ArrayList<ExerciseVO> exercises2 = new ArrayList<>();
        exercises2.add(new ExerciseVO("beto", 0, 3));
        exercises2.add(new ExerciseVO("mbhertDAS", 7, 0));
        exercises2.add(new ExerciseVO("betaismo", 0, 12));
        cycles.add(new CycleVO("ENFRIAMIENTO", exercises2));

        sectionName = vista.findViewById(R.id.section_name_run_routine);
        exerciseName = vista.findViewById(R.id.exercise_name_run_routine);
        exerciseDesc = vista.findViewById(R.id.exercise_desc_run_routine);

        runNextExercise();
        return vista;
    }

    private void runNextExercise() {
        if (actualCycle == cycles.size()) {
            Toast.makeText(getContext(), "FINALIZADA RUTINA", Toast.LENGTH_LONG).show();
            return;
        }
        sectionName.setText(cycles.get(actualCycle).getName());
        exerciseName.setText(cycles.get(actualCycle).getExercises().get(actualExercise).getName());
        exerciseDesc.setText("LOS EJERCICIOS TIENEN DESCRIPCION???");
        if (cycles.get(actualCycle).getExercises().get(actualExercise).getDuration() > 0) {
            setCountDownTimer(cycles.get(actualCycle).getExercises().get(actualExercise).getDuration() * 1000);
            updateTimer();
            timedExercise = true;
            countDownButton.setText("Empezar");
        } else {
            countDownText.setText(String.valueOf(cycles.get(actualCycle).getExercises().get(actualExercise).getQuantity()));
            timedExercise = false;
            countDownButton.setText("Continuar");
        }

        actualExercise++;
        if (actualExercise == cycles.get(actualCycle).getExercises().size()) {
            actualExercise = 0;
            actualCycle++;
        }
    }

    public void openExitDialog() {
        ExitRoutineDialog dialog = new ExitRoutineDialog();
        dialog.show(getActivity().getSupportFragmentManager(), "Exit routine execution");
    }

    //METODOS PARA EL TIMER
    public void startStop() {
        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void setCountDownTimer(long exerciseDurationInMiliseconds) {
        timeLeftInMiliseconds = exerciseDurationInMiliseconds;
        countDownTimer = new CountDownTimer(exerciseDurationInMiliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMiliseconds = millisUntilFinished; //actualizo el tiempo restante
                updateTimer();
            }

            @Override
            public void onFinish() {
                stopTimer();
                runNextExercise();
            }
        };
    }

    public void startTimer() {
        countDownTimer.start(); //lo empieza de inmediato
        countDownButton.setText("Pausar");
        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        countDownButton.setText("Continuar");
        timerRunning = false;
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMiliseconds / 60000;
        int seconds = (int) timeLeftInMiliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) {
            timeLeftText += "0";
        }
        timeLeftText += "" + seconds;
        countDownText.setText(timeLeftText);
    }
}