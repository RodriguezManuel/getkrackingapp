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
import com.example.getkracking.adapters.SmallRoutinesAdapter;
import com.example.getkracking.entities.RoutineVO;
import com.example.getkracking.viewmodels.RoutineInfoViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements RoutinesAdapter.OnRoutineListener {
    RecyclerView recyclerHighlightedRoutines;   //en teoria de un unico elemento, necesito hacerlo para que comparta el comportamiento de inforutina de los otros
    ArrayList<RoutineVO> highlightedRoutinesList;
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
        //highlighted routines
        highlightedRoutinesList = new ArrayList<>();
        recyclerHighlightedRoutines = vista.findViewById(R.id.recyclerHighlightedRoutines);
        recyclerHighlightedRoutines.setLayoutManager(new LinearLayoutManager(getContext()));
        //favourites routines
        favouriteRoutinesList = new ArrayList<>();
        recyclerFavouriteRoutines = vista.findViewById(R.id.recyclerHomeFavouritesRoutines);
        recyclerFavouriteRoutines.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //recent routines
        recentRoutinesList = new ArrayList<>();
        recyclerRecentRoutines = vista.findViewById(R.id.recyclerHomeRecentRoutines);
        recyclerRecentRoutines.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        fillHighlightedList();
        fillFavouriteList();
        fillRecentList();

        RoutinesAdapter adapterHighlighted = new RoutinesAdapter(highlightedRoutinesList, this, "Highlighted");
        recyclerHighlightedRoutines.setAdapter(adapterHighlighted);
        recyclerHighlightedRoutines.setNestedScrollingEnabled(false);

        SmallRoutinesAdapter adapterFavourites = new SmallRoutinesAdapter(favouriteRoutinesList, this, "Favourites");
        recyclerFavouriteRoutines.setAdapter(adapterFavourites);
        recyclerFavouriteRoutines.setNestedScrollingEnabled(false);

        SmallRoutinesAdapter adapterRecents = new SmallRoutinesAdapter(recentRoutinesList, this, "Recents");
        recyclerRecentRoutines.setAdapter(adapterRecents);
        recyclerRecentRoutines.setNestedScrollingEnabled(false);

        return vista;
    }

    private void fillHighlightedList() {
        //solo un item necesitamos
        highlightedRoutinesList.add(new RoutineVO("LA MEJOR RUTINA DE TODAS", "NDEAHHHHHHHHHHHH LA VECINDAD PADRE", "Roosevelt", "Pecho", 0, 1, 0, 0, false, 1));
    }

    private void fillFavouriteList() {
        //HARDCODEADO ADAPTAR A API
        favouriteRoutinesList.add(new RoutineVO("IRONMAN", "HMHMHMHMHMHMHMHMHMHMHMHMHMHMHMHMHMMHMH", "Octa1", "Piernas", 5, 5, 18, 1, true, 2));
        favouriteRoutinesList.add(new RoutineVO("CAPTAIN AMERICA", "VALCHARRRR SACA LA MANO DE AHI CARAJO", "Octa2", "Brazos", 1, 0, 180, 2, true, 3));
        favouriteRoutinesList.add(new RoutineVO("THOR NOT AGUSTIN", "wasaaaaaaaaaaaaaaaaaaaaaaaaaa", "Octa3", "Piernas", 2, 0, 11, 3, true, 3.3f));
    }

    private void fillRecentList() {
        //HARDCODEADO ADAPTAR A API
        recentRoutinesList.add(new RoutineVO("OCTA", "horacio", "Octa4", "Piernas", 5, 5, 18, 1, true,1.5f));
        recentRoutinesList.add(new RoutineVO("CAPTAIN AMERICA", "VALCHARRRR SACA LA MANO DE AHI CARAJO", "Octa2", "Brazos", 1, 0, 180, 2, true, 0));
        recentRoutinesList.add(new RoutineVO("THOR NOT AGUSTIN", "wasaaaaaaaaaaaaaaaaaaaaaaaaaa", "Octa3", "Piernas", 2, 0, 11, 3, true, 1));
    }

    @Override
    public void onRoutineClick(int position, String type) {
        ArrayList<RoutineVO> array;

        if (type.equals("Highlighted"))
            array = highlightedRoutinesList;
        else if (type.equals("Favourites"))
            array = favouriteRoutinesList;
        else if (type.equals("Recents"))
            array = recentRoutinesList;
        else return;

        HomeFragmentDirections.ActionHomeFragmentToRoutineInfoFragment action = HomeFragmentDirections.actionHomeFragmentToRoutineInfoFragment
                (array.get(position).getId());
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(action);
    }
}