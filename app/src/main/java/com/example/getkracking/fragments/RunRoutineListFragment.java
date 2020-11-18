package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.adapters.ExercisesAdapter;
import com.example.getkracking.adapters.RoutinesAdapter;
import com.example.getkracking.dialogs.EndedRoutineDialog;
import com.example.getkracking.dialogs.ExitRoutineDialog;
import com.example.getkracking.entities.CycleVO;
import com.example.getkracking.entities.ExerciseVO;
import com.example.getkracking.viewmodels.RunRoutineViewModel;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RunRoutineListFragment extends Fragment {
    private BottomAppBar bottomAppBar;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton homeButton;
    private RunRoutineViewModel viewModel;

    private TextView sectionName, exerciseName;
    private TextView countDownText;
    private Button countDownButton;

    private ArrayList<ExerciseVO> remainingExercises = new ArrayList<>();
    private RecyclerView recyclerExercises;
    private ExercisesAdapter adapter;

    public RunRoutineListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(RunRoutineViewModel.class);

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_run_routine_list, container, false);
        countDownText = vista.findViewById(R.id.timerInRunListRoutine);
        countDownButton = vista.findViewById(R.id.buttonInRunListRoutine);

        sectionName = vista.findViewById(R.id.section_name_run_listroutine);
        exerciseName = vista.findViewById(R.id.exercise_name_run_listroutine);
        recyclerExercises = vista.findViewById(R.id.recycler_exercises_run_listroutine);
        recyclerExercises.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getTimeLeftText().observe(getViewLifecycleOwner(), s -> countDownText.setText(s));
        viewModel.getButtonText().observe(getViewLifecycleOwner(), s -> countDownButton.setText(s));
        viewModel.getSectionName().observe(getViewLifecycleOwner(), s -> sectionName.setText(s));
        viewModel.getExerciseName().observe(getViewLifecycleOwner(), s -> exerciseName.setText(s));
        viewModel.getFinishedRoutine().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean)
                openEndDialog();
        });
        viewModel.getFinishedExercise().observe(getViewLifecycleOwner(), bool -> {
            if (remainingExercises.isEmpty()) {    //nuevo ciclo
                if(viewModel.getRemainingExercises() != null) {
                    remainingExercises = new ArrayList<>(viewModel.getRemainingExercises());
                    adapter = new ExercisesAdapter(remainingExercises);
                    recyclerExercises.setAdapter(adapter);
                    recyclerExercises.setNestedScrollingEnabled(false);
                }
            } else if(!bool){
                remainingExercises.remove(0);
                adapter.notifyItemRemoved(0);
            }
        });

        countDownButton.setOnClickListener(v -> viewModel.buttonSelected());

        if (getArguments() != null) {
        }
        //CONSEGUIR LISTA DESDE INFOFRAGMENT??

        ArrayList<CycleVO> cycles = new ArrayList<>();
        ArrayList<ExerciseVO> exercises = new ArrayList<>();
        exercises.add(new ExerciseVO("SALtos", "PEREPREPREPRperperpeprepr", 16, 0,"A"));
        exercises.add(new ExerciseVO("PATADAS", "pasdoeokqokpapsdapwda", 5, 0,"A"));
        exercises.add(new ExerciseVO("nada", "awoelko129ekos;la,dam wadmaw amp   ", 0, 5,"A"));
        cycles.add(new CycleVO("Calentamiento", exercises));
        ArrayList<ExerciseVO> exercises2 = new ArrayList<>();
        exercises2.add(new ExerciseVO("beto", "aowdlqowdkpqsl,apld,pasl dpaw ", 0, 3,"A"));
        exercises2.add(new ExerciseVO("mbhertDAS", "dopwk,ospakdmapskdqw", 7, 0,"A"));
        exercises2.add(new ExerciseVO("betaismo", "dawokdpqwkdpo,asld,aw", 0, 12,"A"));
        cycles.add(new CycleVO("ENFRIAMIENTO", exercises2));

        viewModel.setCycles(cycles);
        return vista;
    }

    public void openExitDialog() {
        ExitRoutineDialog dialog = new ExitRoutineDialog();
        dialog.show(getActivity().getSupportFragmentManager(), "Exit routine execution");
    }

    private void openEndDialog() {
        EndedRoutineDialog dialog = new EndedRoutineDialog();
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

        //seteo recyclerview
        remainingExercises = new ArrayList<>(viewModel.getRemainingExercises());
        adapter = new ExercisesAdapter(remainingExercises);
        recyclerExercises.setAdapter(adapter);
        recyclerExercises.setNestedScrollingEnabled(false);

        //arranca rutina
        viewModel.runNextExercise();
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