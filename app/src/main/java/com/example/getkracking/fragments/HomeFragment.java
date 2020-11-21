package com.example.getkracking.fragments;

import android.os.Bundle;

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
import com.example.getkracking.adapters.SmallRoutinesAdapter;
import com.example.getkracking.app.MyApplication;
import com.example.getkracking.entities.RoutineVO;
import com.example.getkracking.repository.RoutineRepository;
import com.example.getkracking.viewmodels.RepositoryViewModelFactory;
import com.example.getkracking.viewmodels.RoutineInfoViewModel;
import com.example.getkracking.viewmodels.RoutinesViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements RoutinesAdapter.OnRoutineListener {
    RecyclerView recyclerHighlightedRoutines;   //en teoria de un unico elemento, necesito hacerlo para que comparta el comportamiento de inforutina de los otros
    ArrayList<RoutineVO> highlightedRoutinesList;
    RecyclerView recyclerFavouriteRoutines;
    ArrayList<RoutineVO> favouriteRoutinesList;
    RecyclerView recyclerRecentRoutines;
    ArrayList<RoutineVO> recentRoutinesList;
    RoutinesViewModel viewModel;
    RoutinesAdapter adapterHighlighted;
    SmallRoutinesAdapter adapterFavourites;
    SmallRoutinesAdapter adapterRecents;

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

        RepositoryViewModelFactory viewModelFactory = new RepositoryViewModelFactory(RoutineRepository.class, ((MyApplication) getActivity().getApplication()).getRoutineRepository());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RoutinesViewModel.class);

        //highlighted routines
        highlightedRoutinesList = new ArrayList<>();
        recyclerHighlightedRoutines = vista.findViewById(R.id.recyclerHighlightedRoutines);
        recyclerHighlightedRoutines.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterHighlighted = new RoutinesAdapter(highlightedRoutinesList, this, "Highlighted");
        recyclerHighlightedRoutines.setAdapter(adapterHighlighted);
        recyclerHighlightedRoutines.setNestedScrollingEnabled(false);

        //favourites routines
        favouriteRoutinesList = new ArrayList<>();
        recyclerFavouriteRoutines = vista.findViewById(R.id.recyclerHomeFavouritesRoutines);
        recyclerFavouriteRoutines.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterFavourites = new SmallRoutinesAdapter(favouriteRoutinesList, this, "Favourites");
        recyclerFavouriteRoutines.setAdapter(adapterFavourites);
        recyclerFavouriteRoutines.setNestedScrollingEnabled(false);

        //recent routines
        recentRoutinesList = new ArrayList<>();
        recyclerRecentRoutines = vista.findViewById(R.id.recyclerHomeRecentRoutines);
        recyclerRecentRoutines.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterRecents = new SmallRoutinesAdapter(recentRoutinesList, this, "Recents");
        recyclerRecentRoutines.setAdapter(adapterRecents);
        recyclerRecentRoutines.setNestedScrollingEnabled(false);

        fillHighlightedList();
        fillFavouriteList();

        return vista;
    }

    private void fillHighlightedList() {
        //solo un item necesitamos
        highlightedRoutinesList.add(new RoutineVO("LA MEJOR RUTINA DE TODAS", "NDEAHHHHHHHHHHHH LA VECINDAD PADRE", "Roosevelt", "Pecho", 0, 1, 0, 0, false, (float) 4.5,(long) 2434));
    }

    private void fillFavouriteList() {
        if (!viewModel.getFavouriteRoutines().hasActiveObservers()) {
            viewModel.getFavouriteRoutines().observe(getViewLifecycleOwner(), favresource -> {
                        switch (favresource.status) {
                            case LOADING:
                                Log.d("UI", "awaiting favourite routines");
                                break;
                            case SUCCESS:
                                Log.d("UI", "Éxito recuperando rutinas favoritas");
                                favouriteRoutinesList.addAll(favresource.data);
                                adapterFavourites.notifyDataSetChanged();

                                //-----------------------------------------------------
                                //CAMBIO TEMPORAL POR CAPACIDADES DE LA API
                                recentRoutinesList.addAll(favresource.data);
                                adapterRecents.notifyDataSetChanged();
                                //CAMBIO TEMPORAL POR CAPACIDADES DE LA API
                                //-----------------------------------------------------


                                break;
                            case ERROR:
                                Log.d("UI", "Error recuperando rutinas favoritas - " + favresource.message);
                                break;
                        }
                    }
            );
        }
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


    @Override
    public void onStop() {
        viewModel.getFavouriteRoutines().removeObservers(getViewLifecycleOwner());
        viewModel.getRoutines().removeObservers(getViewLifecycleOwner());
        super.onStop();
    }
}