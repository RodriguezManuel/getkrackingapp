package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.adapters.RoutinesAdapter;
import com.example.getkracking.app.MyApplication;
import com.example.getkracking.entities.RoutineVO;
import com.example.getkracking.repository.RoutineRepository;
import com.example.getkracking.viewmodels.RepositoryViewModelFactory;
import com.example.getkracking.viewmodels.RoutinesViewModel;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicBoolean;

public class SearchFragment extends Fragment implements RoutinesAdapter.OnRoutineListener, AdapterView.OnItemSelectedListener {
    RecyclerView recyclerRoutines;
    ArrayList<RoutineVO> routinesList;
    RoutinesViewModel routinesViewModel;
    RoutinesAdapter adapter;
    AtomicBoolean zonaCritica = new AtomicBoolean(true);

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        if (((HomeActivity) getActivity()).getSupportActionBar() != null)
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(R.string.bottombaricon_search);

        super.onResume();
    }


    private void fillListSearch(String search) {
        if (search.length() < 3) {
            Toast.makeText(getContext(), "mínimo 3 carácteres", Toast.LENGTH_SHORT);
            fillList();
            return;
        }
        routinesList.clear();
        adapter.notifyDataSetChanged();
        if (!routinesViewModel.getRoutines().hasActiveObservers()) {
            routinesViewModel.searchRoutines(search).observe(getViewLifecycleOwner(), resource -> {
                switch (resource.status) {
                    case LOADING:
                        Log.d("UI", "awaiting routines de search");
                        break;
                    case SUCCESS:
                        Log.d("UI", "Éxito recuperando rutinas de search");

                        if (resource.data != null)
                            routinesList.addAll(resource.data);

                        if (!routinesViewModel.getFavouriteRoutines().hasActiveObservers()) {
                            routinesViewModel.getFavouriteRoutines().observe(getViewLifecycleOwner(), favresource -> {
                                switch (favresource.status) {
                                    case LOADING:
                                        Log.d("UI", "awaiting favourite routines de search");
                                        break;
                                    case SUCCESS:
                                        Log.d("UI", "Éxito recuperando rutinas favoritas de search");

                                        if (favresource.data != null && favresource.data.size() > 0) {
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
                                        Log.d("UI", "Error recuperando rutinas favoritas de search - " + favresource.message);
                                        break;
                                }
                            });
                        }

                        adapter.notifyDataSetChanged();

                        break;
                    case ERROR:
                        Log.d("UI", "Error en get routines de search - " + resource.message);
                        break;
                }
            });
        }
    }

    private void fillList() {
        if (!routinesViewModel.getRoutines().hasActiveObservers()) {
            routinesViewModel.getRoutines().observe(getViewLifecycleOwner(), resource -> {
                switch (resource.status) {
                    case LOADING:
                        Log.d("UI", "awaiting routines");
                        break;
                    case SUCCESS:
                        Log.d("UI", "Éxito recuperando rutinas");
                        if (resource.data != null)
                            routinesList.addAll(resource.data);

                        if (!routinesViewModel.getFavouriteRoutines().hasActiveObservers()) {
                            routinesViewModel.getFavouriteRoutines().observe(getViewLifecycleOwner(), favresource -> {
                                switch (favresource.status) {
                                    case LOADING:
                                        Log.d("UI", "awaiting favourite routines");
                                        break;
                                    case SUCCESS:
                                        Log.d("UI", "Éxito recuperando rutinas favoritas");

                                        if (favresource.data != null && favresource.data.size() > 0) {
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

    private enum Order {
        ASCENDING, DESCENDING;
    }

    private enum Field {
        DIFFICULTY, DATECREATED, RATING;
    }

    private void orderList(Field field, Order order) {
        switch (field) {
            case DIFFICULTY:
                switch (order) {
                    case ASCENDING:
                        Log.d("UI", "Entré ascendente");
                        Collections.sort(routinesList, Comparator.comparing(RoutineVO::getDifficulty));
                        adapter.notifyDataSetChanged();
                        break;
                    case DESCENDING:
                        Log.d("UI", "Entré descendente");
                        Collections.sort(routinesList, Comparator.comparing(RoutineVO::getDifficulty).reversed());
                        Log.d("UI", routinesList.toString());
                        adapter.notifyDataSetChanged();
                        break;
                }
                break;
            case DATECREATED:
                switch (order) {
                    case ASCENDING:
                        Log.d("UI", "Entré ascendente");
                        Collections.sort(routinesList, Comparator.comparing(RoutineVO::getDateCreated));
                        Log.d("UI", routinesList.toString());
                        adapter.notifyDataSetChanged();
                        break;
                    case DESCENDING:
                        Log.d("UI", "Entré descendente");
                        Collections.sort(routinesList, Comparator.comparing(RoutineVO::getDateCreated).reversed());
                        Log.d("UI", routinesList.toString());
                        adapter.notifyDataSetChanged();
                        break;
                }
                break;
            case RATING:
                switch (order) {
                    case ASCENDING:
                        Log.d("UI", "Entré ascendente");
                        Collections.sort(routinesList, Comparator.comparing(RoutineVO::getRating));
                        Log.d("UI", routinesList.toString());
                        adapter.notifyDataSetChanged();
                        break;
                    case DESCENDING:
                        Log.d("UI", "Entré descendente");
                        Collections.sort(routinesList, Comparator.comparing(RoutineVO::getRating).reversed());
                        Log.d("UI", routinesList.toString());
                        adapter.notifyDataSetChanged();
                        break;
                }
                break;
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

        EditText etSearch = vista.findViewById(R.id.etSearchRoutines);
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (event.getKeyCode()) {
                            case KeyEvent.KEYCODE_DPAD_CENTER:
                            case KeyEvent.KEYCODE_ENTER:
                                fillListSearch(etSearch.getText().toString());
                                return true;
                            default:
                                break;
                        }
                    }
                    return false;
                }
        );
        fillListSearch(etSearch.getText().toString());
        EditText etSearchIcon = vista.findViewById(R.id.search_toggle);
        etSearchIcon.setOnClickListener(v -> fillListSearch(etSearch.getText().toString()));

        //chip de filtros
        ChipGroup filters = vista.findViewById(R.id.chipgroup_filterSearch);
        filters.setOnCheckedChangeListener((group, id) -> {
            ArrayList<RoutineVO> aux = new ArrayList<>();
            if (id == R.id.filterchip_favourites) {
                aux.addAll(routinesList.stream().filter(RoutineVO::isFavorited).collect(Collectors.toList()));
            } else if (id == R.id.filterchip_highdifficulty) {
                aux.addAll(routinesList.stream().filter(routine -> routine.getDifficulty() < 1).collect(Collectors.toList()));
            } else if (id == R.id.filterchip_mediumdifficulty) {
                aux.addAll(routinesList.stream().filter(routine -> routine.getDifficulty() < 3).collect(Collectors.toList()));
            } else if (id == R.id.filterchip_lowdifficulty) {
                aux.addAll(routinesList.stream().filter(routine -> routine.getDifficulty() < 6).collect(Collectors.toList()));
            } else
                aux = routinesList;

            adapter = new RoutinesAdapter(aux, this, null);
            recyclerRoutines.setAdapter(adapter);
            recyclerRoutines.setNestedScrollingEnabled(false);
        });

        ArrayAdapter<CharSequence> difAdapter = ArrayAdapter.createFromResource(getContext(), R.array.difficult_array, android.R.layout.simple_spinner_item);

        Spinner difficultySpinner = vista.findViewById(R.id.difficulty_order_spinner);
        difAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difAdapter);
        difficultySpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> dateAdapter = ArrayAdapter.createFromResource(getContext(), R.array.date_array, android.R.layout.simple_spinner_item);
        Spinner dateCreatedSpinner = vista.findViewById(R.id.datecreated_order_spinner);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateCreatedSpinner.setAdapter(dateAdapter);
        dateCreatedSpinner.setOnItemSelectedListener(this);

        recyclerRoutines = vista.findViewById(R.id.recyclerSearchRoutines);
        recyclerRoutines.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayAdapter<CharSequence> ratingAdapter = ArrayAdapter.createFromResource(getContext(), R.array.rating_array, android.R.layout.simple_spinner_item);
        Spinner ratingSpinner = vista.findViewById(R.id.rating_order_spinner);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingSpinner.setAdapter(ratingAdapter);
        ratingSpinner.setOnItemSelectedListener(this);

        recyclerRoutines = vista.findViewById(R.id.recyclerSearchRoutines);
        recyclerRoutines.setLayoutManager(new LinearLayoutManager(getContext()));

        RepositoryViewModelFactory viewModelFactory = new RepositoryViewModelFactory(RoutineRepository.class, ((MyApplication) getActivity().getApplication()).getRoutineRepository());
        routinesViewModel = new ViewModelProvider(this, viewModelFactory).get(RoutinesViewModel.class);

        //lista rutinas
        routinesList = new ArrayList<>();
        adapter = new RoutinesAdapter(routinesList, this, null);
        recyclerRoutines.setAdapter(adapter);
        recyclerRoutines.setNestedScrollingEnabled(false);

        fillList();
        return vista;
    }

    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        String option = parentView.getItemAtPosition(position).toString();
        if (option.equals(getResources().getString(R.string.spinner_diff_ascend))) {
            orderList(Field.DIFFICULTY, Order.ASCENDING);
        } else if (option.equals(getResources().getString(R.string.spinner_diff_descend))) {
            orderList(Field.DIFFICULTY, Order.DESCENDING);
        } else if (option.equals(getResources().getString(R.string.spinner_date_ascend))) {
            orderList(Field.DATECREATED, Order.ASCENDING);
        } else if (option.equals(getResources().getString(R.string.spinner_date_descend))) {
            orderList(Field.DATECREATED, Order.DESCENDING);
        } else if (option.equals(getResources().getString(R.string.spinner_rating_ascend))) {
            orderList(Field.RATING, Order.ASCENDING);
        } else if (option.equals(getResources().getString(R.string.spinner_rating_descend))) {
            orderList(Field.RATING, Order.DESCENDING);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parentView) {
        Log.d("UI", "nothing selected!");
    }

    @Override
    public void onRoutineClick(int position, String type) {
        SearchFragmentDirections.ActionSearchFragmentToRoutineInfoFragment action = SearchFragmentDirections.actionSearchFragmentToRoutineInfoFragment
                (routinesList.get(position).getId());

        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(action);
    }

    @Override
    public void onStop() {
        routinesViewModel.getFavouriteRoutines().removeObservers(getViewLifecycleOwner());
        routinesViewModel.getRoutines().removeObservers(getViewLifecycleOwner());
        super.onStop();
    }
}