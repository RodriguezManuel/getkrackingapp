package com.example.getkracking.fragments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.adapters.ExcercisesAdapter;
import com.example.getkracking.viewmodels.RoutineInfoViewModel;

public class RoutineInfoFragment extends Fragment {

    RecyclerView recyclerExcercise;
    RoutineInfoViewModel viewModel;

    @Override
    public void onResume() {
        super.onResume();setHasOptionsMenu(true);
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
//
//    @Override
//    public void onResume() {
//        if (((HomeActivity) getActivity()).getSupportActionBar() != null) {
//        }
//
//        super.onResume();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(RoutineInfoViewModel.class);
        View vista = inflater.inflate(R.layout.routine_info_fragment, container, false);
        recyclerExcercise = vista.findViewById(R.id.ExercisesRecycler);
        recyclerExcercise.setLayoutManager(new LinearLayoutManager(getContext()));
        ExcercisesAdapter adapter = new ExcercisesAdapter(viewModel.getExcercisesList());
        recyclerExcercise.setAdapter(adapter);
        recyclerExcercise.setNestedScrollingEnabled(false);
        return vista;
    }
}