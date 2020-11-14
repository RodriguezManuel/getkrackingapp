package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.adapters.ExcercisesAdapter;
import com.example.getkracking.entities.ExcerciseVO;
import com.example.getkracking.entities.RoutineVO;

import java.util.ArrayList;

public class StatsFragment extends Fragment {

    RecyclerView recyclerExcercise;
    ArrayList<ExcerciseVO> excercisesList;

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        if (((HomeActivity) getActivity()).getSupportActionBar() != null)
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(R.string.bottombaricon_stats);

        super.onResume();
    }

    private void fillList(){
        excercisesList.add(new ExcerciseVO("Sentadillas", "40 repeticiones"));
        excercisesList.add(new ExcerciseVO("Abominables", "50 repeticiones"));
        excercisesList.add(new ExcerciseVO("Los de culo", "20 minutos"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_stats, container, false);
        excercisesList = new ArrayList<>();
        recyclerExcercise = vista.findViewById(R.id.RoutineRecycler);
        recyclerExcercise.setLayoutManager(new LinearLayoutManager(getContext()));

        fillList();

        ExcercisesAdapter adapter = new ExcercisesAdapter(excercisesList);
        recyclerExcercise.setAdapter(adapter);
        recyclerExcercise.setNestedScrollingEnabled(false);
        return vista;
    }
}