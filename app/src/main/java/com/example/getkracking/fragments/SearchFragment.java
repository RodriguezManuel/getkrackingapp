package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.adapters.RoutinesAdapter;
import com.example.getkracking.app.MyApplication;
import com.example.getkracking.entities.RoutineVO;
import com.example.getkracking.repository.RoutineRepository;
import com.example.getkracking.viewmodels.RepositoryViewModelFactory;
import com.example.getkracking.viewmodels.SearchViewModel;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements RoutinesAdapter.OnRoutineListener {
    RecyclerView recyclerRoutines;
    ArrayList<RoutineVO> routinesList;
    SearchViewModel searchViewModel;
    RoutinesAdapter adapter;

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

        List<RoutineVO> favRoutines = new ArrayList<>();

        adapter = new RoutinesAdapter(routinesList, this, null);
        recyclerRoutines.setAdapter(adapter);
        recyclerRoutines.setNestedScrollingEnabled(false);

        if (!searchViewModel.getRoutines().hasActiveObservers()) {
            searchViewModel.getRoutines().observe(getViewLifecycleOwner(), resource -> {
                switch (resource.status) {
                    case LOADING:
                        Log.d("UI", "awaiting routines");
                        break;
                    case SUCCESS:
                        Log.d("UI", "Éxito recuperando rutinas");

                        routinesList.addAll(resource.data);

                        if (!searchViewModel.getFavouriteRoutines().hasActiveObservers()) {
                            searchViewModel.getFavouriteRoutines().observe(getViewLifecycleOwner(), favresource -> {
                                switch (favresource.status) {
                                    case LOADING:
                                        Log.d("UI", "awaiting favourite routines");
                                        break;
                                    case SUCCESS:
                                        Log.d("UI", "Éxito recuperando rutinas favoritas");

                                        if (favresource.data.size() > 0) {
                                            for (RoutineVO routine : routinesList) {
                                                for (RoutineVO favRoutine : favresource.data) {
                                                    if (routine.getId() == favRoutine.getId()) {
                                                        Log.d("UI", "MATCH!");
                                                        routine.setFavorited(true);
                                                        break;
                                                    }
                                                }
                                            }
                                        }

                                        adapter.notifyDataSetChanged();

                                        break;
                                    case ERROR:
                                        Log.d("UI", "Error recuperando rutinas favoritas - " + favresource.message);
                                        break;
                                }
                            });
                        }
                        break;
                    case ERROR:
                        Log.d("UI", "Error en get routines - " + resource.message);
                        break;
                }
            });
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        recyclerRoutines = vista.findViewById(R.id.recyclerSearchRoutines);
        recyclerRoutines.setLayoutManager(new LinearLayoutManager(getContext()));

        RepositoryViewModelFactory viewModelFactory = new RepositoryViewModelFactory(RoutineRepository.class, ((MyApplication) getActivity().getApplication()).getRoutineRepository());
        searchViewModel = new ViewModelProvider(this, viewModelFactory).get(SearchViewModel.class);

        //lista rutinas
        routinesList = new ArrayList<>();

        fillList();
        return vista;
    }

    @Override
    public void onRoutineClick(int position, String type) {
        SearchFragmentDirections.ActionSearchFragmentToRoutineInfoFragment action = SearchFragmentDirections.actionSearchFragmentToRoutineInfoFragment
                (routinesList.get(position).getId(), routinesList.get(position).getDescription(), routinesList.get(position).getCreator(), routinesList.get(position).getRating(),
                        routinesList.get(position).getCategory(), routinesList.get(position).isFavorited());


        // LE PASO LOS ARGUMENTOS QUE NO TIENEN VALOR DEFAULT
        action.setNameRoutine(routinesList.get(position).getName());
        action.setDifficultyRoutine(routinesList.get(position).getLevelCategory1());
        //falta rating
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(action);
    }

    @Override
    public void onStop() {
        searchViewModel.getFavouriteRoutines().removeObservers(getViewLifecycleOwner());
        searchViewModel.getRoutines().removeObservers(getViewLifecycleOwner());
        super.onStop();
    }
}