package com.example.getkracking.fragments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.adapters.CyclesAdapter;
import com.example.getkracking.app.MyApplication;
import com.example.getkracking.entities.CycleVO;
import com.example.getkracking.entities.ExerciseVO;
import com.example.getkracking.repository.CycleRepository;
import com.example.getkracking.repository.ExerciseRepository;
import com.example.getkracking.viewmodels.RoutineInfoViewModel;

import java.net.URL;
import java.util.ArrayList;

public class RoutineInfoFragment extends Fragment {

    RecyclerView cyclesRoutine;
    ArrayList<CycleVO> cyclesList;
    RoutineInfoViewModel viewModel;
    boolean favorited = false;  //HARDCODEADO OBTENIDO DE API
    int idRoutine;
    CycleRepository cycleRepository;
    ExerciseRepository exerciseRepository;
    CyclesAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
        Toolbar mToolBar = ((HomeActivity) getActivity()).findViewById(R.id.homeTopBar);
        ActionBar actionBar = ((HomeActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.routine);

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
        if (id == R.id.topbar_share) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.routine_info_fragment, container, false);
        if (getArguments() != null) {
            RoutineInfoFragmentArgs args = RoutineInfoFragmentArgs.fromBundle(getArguments());
            //una vez q consegui los argumentos los seteo en la vista
            ((TextView) vista.findViewById(R.id.RoutineNameInRoutine)).setText(args.getNameRoutine());
            ((TextView) vista.findViewById(R.id.CreatorNameInRoutine)).setText(args.getCreatorRoutine());
            ((TextView) vista.findViewById(R.id.RoutineDescriptionInRoutine)).setText(args.getDescRoutine());
            ((RatingBar) vista.findViewById(R.id.rbCategory1InRoutine)).setRating(args.getDifficultyRoutine());
            ((TextView) vista.findViewById(R.id.RoutineNameInRoutine)).setText(args.getNameRoutine());
            favorited = args.getFavoritedRoutine();
            idRoutine = args.getIdRoutine();    //PARA HACER EL REQUEST DE CICLOS
            //category?? donde va????

            //seteo de funcionalidades de botones
            ((Button) vista.findViewById(R.id.ButtonEmpezarInRoutine)).setOnClickListener(v1 -> {

                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.runRoutineListFragment);
//                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.runRoutineFragment);
            });

            ImageView favIcon = vista.findViewById(R.id.favoriteIconInfoRoutine);
            favIcon.setOnClickListener((View.OnClickListener) v -> {
                if (favorited) {
                    favIcon.setBackgroundResource(R.drawable.ic_favorite_border);
                    //SACAR DE FAVORITOS CON LA API
                } else {
                    favIcon.setBackgroundResource(R.drawable.ic_favorite);
                    //AGREGAR A FAVORITOS CON LA API
                }
                favorited = !favorited;
            });

            TextView puntuacion = vista.findViewById(R.id.rating_value_info_routine);
            puntuacion.setText(String.valueOf(args.getRatingRoutine()));
        }

        viewModel = new ViewModelProvider(this).get(RoutineInfoViewModel.class);
        cyclesRoutine = vista.findViewById(R.id.CyclesRoutine);
        cyclesList = new ArrayList<>();

        MyApplication application = (MyApplication) getActivity().getApplication();
        cycleRepository = application.getCycleRepository();
        exerciseRepository = application.getExerciseRepository();

        adapter = new CyclesAdapter(getActivity(), cyclesList);
        cyclesRoutine.setLayoutManager(new LinearLayoutManager(getContext()));
        cyclesRoutine.setAdapter(adapter);
        cyclesRoutine.setNestedScrollingEnabled(false);
        fillCycles();
        return vista;
    }

    private void fillExercises(int cycleId, CycleVO cycle) {
        exerciseRepository.getExercises(idRoutine, cycleId).observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    Log.d("UI", "awaiting routines");
                    break;
                case SUCCESS:
                    Log.d("UI", "Éxito recuperando rutinas");

                    cycle.getExercises().addAll(resource.data);
                    adapter.notifyDataSetChanged();
                    break;
                case ERROR:
                    Log.d("UI", "Error en get routines - " + resource.message);
                    break;
            }
        });
    }

    private void fillCycles() {
        cycleRepository.getCycles(idRoutine).observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    Log.d("UI", "awaiting routines");
                    break;
                case SUCCESS:
                    Log.d("UI", "Éxito recuperando rutinas");

                    for (CycleVO cycle : resource.data) {
                        cyclesList.add(cycle);
                        fillExercises(cycle.getId(), cycle);
                        adapter.notifyDataSetChanged();
                    }

                    break;
                case ERROR:
                    Log.d("UI", "Error en get routines - " + resource.message);
                    break;
            }
        });
    }
}