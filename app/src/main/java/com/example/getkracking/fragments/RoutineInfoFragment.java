package com.example.getkracking.fragments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.adapters.CyclesAdapter;
import com.example.getkracking.entities.CycleVO;
import com.example.getkracking.entities.ExerciseVO;
import com.example.getkracking.viewmodels.RoutineInfoViewModel;

import java.util.ArrayList;

public class RoutineInfoFragment extends Fragment {

    RecyclerView cyclesRoutine;
    ArrayList<CycleVO> cycles;
    RoutineInfoViewModel viewModel;
    int idRoutine;

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
        Toolbar mToolBar =  ((HomeActivity) getActivity()).findViewById(R.id.homeTopBar);
        ActionBar actionBar = ((HomeActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Rutina");

        //CUSTOMIZAR BACK BUTTON
        ((HomeActivity) getActivity()).setSupportActionBar(mToolBar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationIcon(R.drawable.ic_chevron_left);
        mToolBar.setNavigationOnClickListener(v -> Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.topbarmenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handle options selected
        int id = item.getItemId();

        //if (id == R.id.topbar_fav)
        //AGREGAR O DESAGREGAR RUTINA A FAVORITOS

        return super.onOptionsItemSelected(item);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.routine_info_fragment, container, false);
        if(getArguments() != null) {
            RoutineInfoFragmentArgs args = RoutineInfoFragmentArgs.fromBundle(getArguments());
            //una vez q consegui los argumentos los seteo en la vista
            ((TextView)vista.findViewById(R.id.RoutineNameInRoutine)).setText(args.getNameRoutine());
            ((TextView)vista.findViewById(R.id.CreatorNameInRoutine)).setText(args.getCreatorRoutine());
            ((TextView)vista.findViewById(R.id.RoutineDescriptionInRoutine)).setText(args.getDescRoutine());
            ((RatingBar)vista.findViewById(R.id.rbCategory1InRoutine)).setRating(args.getDifficultyRoutine());
            ((TextView)vista.findViewById(R.id.RoutineNameInRoutine)).setText(args.getNameRoutine());
            ((Button) vista.findViewById(R.id.ButtonEmpezarInRoutine)).setOnClickListener(v1 -> Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.runRoutineFragment));
            idRoutine = args.getIdRoutine();    //PARA HACER EL REQUEST DE CICLOS
            //category?? donde va????
        }

        viewModel = new ViewModelProvider(this).get(RoutineInfoViewModel.class);
        cyclesRoutine = vista.findViewById(R.id.CyclesRoutine);
        cycles = new ArrayList<>();

        fillCycles();

        CyclesAdapter adapter = new CyclesAdapter(getActivity(), cycles);
        cyclesRoutine.setLayoutManager(new LinearLayoutManager(getContext()));
        cyclesRoutine.setAdapter(adapter);


//        cyclesRoutine = vista.findViewById(R.id.CyclesRoutine);
//        cyclesRoutine.setLayoutManager(new LinearLayoutManager(getContext()));
//        ExercisesAdapter adapter = new ExercisesAdapter(viewModel.getExcercisesList());
//        cyclesRoutine.setAdapter(adapter);
//        cyclesRoutine.setNestedScrollingEnabled(false);
        return vista;
    }

    public void fillCycles() {
        //HARDCODEADO ARREGLAR CON API PADREEEEEEEEEEEEEEEEEE Y MADREEEEEEEEE
        ArrayList<ExerciseVO> exercises = new ArrayList<>();
        exercises.add(new ExerciseVO("SALtos", "16 minutos"));
        exercises.add(new ExerciseVO("PATADAS", "12 minutos"));
        exercises.add(new ExerciseVO("nada", "1 minutos"));
        cycles.add(new CycleVO("CALENTAMIENTO", exercises));
        ArrayList<ExerciseVO> exercises2 = new ArrayList<>();
        exercises2.add(new ExerciseVO("beto", "16 repe"));
        exercises2.add(new ExerciseVO("mbhertDAS", "12 pasamela"));
        exercises2.add(new ExerciseVO("betaismo", "1 guido"));
        cycles.add(new CycleVO("ENFRIAMIENTO", exercises2));
    }
}