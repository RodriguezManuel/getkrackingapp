package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.adapters.RoutinesAdapter;
import com.example.getkracking.entities.RoutineVO;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    RecyclerView recyclerRoutines;
    ArrayList<RoutineVO> routinesList;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (((HomeActivity) getActivity()).getSupportActionBar() != null)
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(R.string.bottombaricon_search_string);

        super.onCreate(savedInstanceState);
    }

    private void fillList() {
        //HARDCODEADO ADAPTAR A API
        routinesList.add(new RoutineVO("IRONMAN","HMHMHMHMHMHMHMHMHMHMHMHMHMHMHMHMHMMHMH", 5, 5, 18, true));
        routinesList.add(new RoutineVO("CAPTAIN AMERICA","VALCHARRRR SACA LA MANO DE AHI CARAJO", 1, 0, 180, false));
        routinesList.add(new RoutineVO("THOR NOT AGUSTIN","wasaaaaaaaaaaaaaaaaaaaaaaaaaa", 2.5f, 0.5f, 11, true));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_search, container, false);
        routinesList = new ArrayList<>();
        recyclerRoutines = vista.findViewById(R.id.recyclerSearchRoutines);
        recyclerRoutines.setLayoutManager(new LinearLayoutManager(getContext()));

        fillList();

        RoutinesAdapter adapter = new RoutinesAdapter(routinesList);
        recyclerRoutines.setAdapter(adapter);
        recyclerRoutines.setNestedScrollingEnabled(false);

        return vista;
    }
}