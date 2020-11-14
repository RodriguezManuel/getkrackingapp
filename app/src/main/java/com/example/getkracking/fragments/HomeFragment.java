package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.adapters.RoutinesAdapter;
import com.example.getkracking.adapters.SmallRoutinesAdapter;
import com.example.getkracking.entities.RoutineVO;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerFavouriteRoutines;
    ArrayList<RoutineVO> favouriteRoutinesList;
    RecyclerView recyclerRecentRoutines;
    ArrayList<RoutineVO> recentRoutinesList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        if (((HomeActivity) getActivity()).getSupportActionBar() != null)
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(R.string.home_button);

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_home, container, false);
        //favourites routines
        favouriteRoutinesList = new ArrayList<>();
        recyclerFavouriteRoutines = vista.findViewById(R.id.recyclerHomeFavouritesRoutines);
        recyclerFavouriteRoutines.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //recent routines
        recentRoutinesList = new ArrayList<>();
        recyclerRecentRoutines = vista.findViewById(R.id.recyclerHomeRecentRoutines);
        recyclerRecentRoutines.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        fillFavouriteList();
        fillRecentList();

        SmallRoutinesAdapter adapterFavourites = new SmallRoutinesAdapter(favouriteRoutinesList);
        recyclerFavouriteRoutines.setAdapter(adapterFavourites);
        recyclerFavouriteRoutines.setNestedScrollingEnabled(false);

        SmallRoutinesAdapter adapterRecents = new SmallRoutinesAdapter(recentRoutinesList);
        recyclerRecentRoutines.setAdapter(adapterRecents);
        recyclerRecentRoutines.setNestedScrollingEnabled(false);

        View aux1 = vista.findViewById(R.id.include_highlighted_routine);
        View aux2 = vista.findViewById(R.id.include_first_category_routine);
        View aux3 = vista.findViewById(R.id.include_second_category_routine);
        View aux4 = vista.findViewById(R.id.include_third_category_routine);

        fillBigRoutines(aux1);
        fillBigRoutines(aux2);
        fillBigRoutines(aux3);
        fillBigRoutines(aux4);

        return vista;
    }

    private void fillFavouriteList() {
        //HARDCODEADO ADAPTAR A API
        favouriteRoutinesList.add(new RoutineVO("IRONMAN", "HMHMHMHMHMHMHMHMHMHMHMHMHMHMHMHMHMMHMH", 5, 5, 18, true));
        favouriteRoutinesList.add(new RoutineVO("CAPTAIN AMERICA", "VALCHARRRR SACA LA MANO DE AHI CARAJO", 1, 0, 180, true));
        favouriteRoutinesList.add(new RoutineVO("THOR NOT AGUSTIN", "wasaaaaaaaaaaaaaaaaaaaaaaaaaa", 2.5f, 0.5f, 11, true));
    }

    private void fillRecentList() {
        //HARDCODEADO ADAPTAR A API
        recentRoutinesList.add(new RoutineVO("IRONMAN", "HMHMHMHMHMHMHMHMHMHMHMHMHMHMHMHMHMMHMH", 5, 5, 18, true));
        recentRoutinesList.add(new RoutineVO("CAPTAIN AMERICA", "VALCHARRRR SACA LA MANO DE AHI CARAJO", 1, 0, 180, true));
        recentRoutinesList.add(new RoutineVO("THOR NOT AGUSTIN", "wasaaaaaaaaaaaaaaaaaaaaaaaaaa", 2.5f, 0.5f, 11, true));
    }

    private void fillBigRoutines(View view) {
        //HARDCODEADO ADAPTAR A API
        RoutinesAdapter.RoutineViewHolder holder = new RoutinesAdapter.RoutineViewHolder(view);
        holder.setName("PEPE");
        holder.setDescription("Arroz con leche me quiero casar, con una seniorita de RADA TILLY");
        holder.setDuration(String.valueOf(69420));
        holder.setFavourite(true);
        holder.setCategory1(3);
        holder.setCategory2(1);
    }

}