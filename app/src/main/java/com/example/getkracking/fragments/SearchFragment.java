package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.adapters.RoutinesAdapter;
import com.example.getkracking.entities.RoutineVO;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements RoutinesAdapter.OnRoutineListener {
    RecyclerView recyclerRoutines;
    ArrayList<RoutineVO> routinesList;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        if (((HomeActivity) getActivity()).getSupportActionBar() != null)
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(R.string.bottombaricon_search);

        super.onResume();
    }

    private void fillList() {
        //HARDCODEADO ADAPTAR A API
        routinesList.add(new RoutineVO("IRONMAN", "HMHMHMHMHMHMHMHMHMHMHMHMHMHMHMHMHMMHMH", "Octa1", "Piernas", 5, 5, 18, 1, true));
        routinesList.add(new RoutineVO("CAPTAIN AMERICA", "VALCHARRRR SACA LA MANO DE AHI CARAJO", "Octa2", "Brazos", 1, 0, 180, 2, true));
        routinesList.add(new RoutineVO("THOR NOT AGUSTIN", "wasaaaaaaaaaaaaaaaaaaaaaaaaaa", "Octa3", "Piernas", 2, 0, 11, 3, true));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_search, container, false);
        //chip de filtros
        ChipGroup filters = vista.findViewById(R.id.chipgroup_filterSearch);
        filters.setOnCheckedChangeListener((group, id) -> {
            if (id == R.id.filterchip_favourites) {
                //completar
            } else if (id == R.id.filterchip_highdifficulty) {
                //completar
            } else if (id == R.id.filterchip_mediumdifficulty) {
                //completar
            } else if (id == R.id.filterchip_lowdifficulty) {
                //completar
            }
        });

        //lista rutinas
        routinesList = new ArrayList<>();
        recyclerRoutines = vista.findViewById(R.id.recyclerSearchRoutines);
        recyclerRoutines.setLayoutManager(new LinearLayoutManager(getContext()));

        fillList();

        RoutinesAdapter adapter = new RoutinesAdapter(routinesList, this, null);
        recyclerRoutines.setAdapter(adapter);
        recyclerRoutines.setNestedScrollingEnabled(false);

        return vista;
    }

    @Override
    public void onRoutineClick(int position, String type) {
        SearchFragmentDirections.ActionSearchFragmentToRoutineInfoFragment action = SearchFragmentDirections.actionSearchFragmentToRoutineInfoFragment
                (routinesList.get(position).getId(), routinesList.get(position).getDescription(), routinesList.get(position).getCreator(), routinesList.get(position).getCategory());
        // LE PASO LOS ARGUMENTOS QUE NO TIENEN VALOR DEFAULT
        action.setNameRoutine(routinesList.get(position).getName());
        action.setDifficultyRoutine(routinesList.get(position).getLevelCategory1());
        //falta rating
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(action);
    }
}