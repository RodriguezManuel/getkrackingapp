package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.dialogs.EndedRoutineDialog;
import com.example.getkracking.dialogs.ExitRoutineDialog;
import com.example.getkracking.entities.CycleVO;
import com.example.getkracking.entities.ExerciseVO;
import com.example.getkracking.viewmodels.RunRoutineViewModel;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class RunRoutineFragment extends Fragment {
    //para desactivarlos mientras se ejecuta la rutina
    private BottomAppBar bottomAppBar;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton homeButton;
    private RunRoutineViewModel viewModel;

    private TextView sectionName, exerciseName, exerciseDesc, exerciseType;
    private TextView countDownText;
    private Button countDownButton;

    public RunRoutineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(RunRoutineViewModel.class);

        if (getArguments() != null) {
            RunRoutineFragmentArgs args = RunRoutineFragmentArgs.fromBundle(getArguments());
            viewModel.setCycles(Arrays.asList(args.getCycles()));
            viewModel.setRoutineId(args.getIdRoutine());
        }

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_run_routine, container, false);
        countDownText = vista.findViewById(R.id.timerInRunRoutine);
        countDownButton = vista.findViewById(R.id.buttonInRunRoutine);

        sectionName = vista.findViewById(R.id.section_name_run_routine);
        exerciseName = vista.findViewById(R.id.exercise_name_run_routine);
        exerciseDesc = vista.findViewById(R.id.exercise_desc_run_routine);
        exerciseType = vista.findViewById(R.id.exercise_type_run_routine);

        viewModel.getTimeLeftText().observe(getViewLifecycleOwner(), s -> countDownText.setText(s));
        viewModel.getButtonText().observe(getViewLifecycleOwner(), s -> countDownButton.setText(s));
        viewModel.getSectionName().observe(getViewLifecycleOwner(), s -> sectionName.setText(s));
        viewModel.getExerciseName().observe(getViewLifecycleOwner(), s -> exerciseName.setText(s));
        viewModel.getExerciseDesc().observe(getViewLifecycleOwner(), s -> exerciseDesc.setText(s));
        viewModel.getExerciseType().observe(getViewLifecycleOwner(), s -> exerciseType.setText(s));
        viewModel.getFinishedRoutine().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean)
                openEndDialog();
        });

        countDownButton.setOnClickListener(v -> viewModel.buttonSelected());

        viewModel.runNextExercise();
        return vista;
    }

    public void openExitDialog() {
        ExitRoutineDialog dialog = new ExitRoutineDialog();
        dialog.show(getActivity().getSupportFragmentManager(), "Exit routine execution");
    }

    private void openEndDialog() {
        EndedRoutineDialog dialog = new EndedRoutineDialog(viewModel.getRoutineId());
        dialog.show(getActivity().getSupportFragmentManager(), "Routine ended");
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

        //saco la barra de navegacion
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

        //vuelve a aparecer la interfaz de navegacion
        bottomAppBar.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        homeButton.setVisibility(View.VISIBLE);
    }
}