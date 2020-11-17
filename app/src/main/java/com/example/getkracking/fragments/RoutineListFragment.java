package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.getkracking.R;
import com.example.getkracking.adapters.ExercisesAdapter;
import com.example.getkracking.viewmodels.RoutineInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoutineListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoutineListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerExcercise;
    private RoutineInfoViewModel viewModel;

    public RoutineListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Routine_list_format.
     */
    // TODO: Rename and change types and number of parameters
    public static RoutineListFragment newInstance(String param1, String param2) {
        RoutineListFragment fragment = new RoutineListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista =  inflater.inflate(R.layout.fragment_run_routine_list_format, container, false);

        // Inflate the layout for this fragment
        viewModel = new ViewModelProvider(this).get(RoutineInfoViewModel.class);
        recyclerExcercise = vista.findViewById(R.id.ExercisesRecyclerForList);
        recyclerExcercise.setLayoutManager(new LinearLayoutManager(getContext()));
        ExercisesAdapter adapter = new ExercisesAdapter(viewModel.getExcercisesList());
        recyclerExcercise.setAdapter(adapter);
        recyclerExcercise.setNestedScrollingEnabled(false);
        return vista;
    }
}