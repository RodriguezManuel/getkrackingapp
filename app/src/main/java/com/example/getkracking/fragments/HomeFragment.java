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

import java.util.ArrayList;

public class HomeFragment extends Fragment implements RoutinesAdapter.OnRoutineListener {
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

        SmallRoutinesAdapter adapterFavourites = new SmallRoutinesAdapter(favouriteRoutinesList, this, "Favourites");
        recyclerFavouriteRoutines.setAdapter(adapterFavourites);
        recyclerFavouriteRoutines.setNestedScrollingEnabled(false);

        SmallRoutinesAdapter adapterRecents = new SmallRoutinesAdapter(recentRoutinesList, this, "Recents");
        recyclerRecentRoutines.setAdapter(adapterRecents);
        recyclerRecentRoutines.setNestedScrollingEnabled(false);

//        View aux1 = vista.findViewById(R.id.include_highlighted_routine);
//        View aux2 = vista.findViewById(R.id.include_first_category_routine);
//        View aux3 = vista.findViewById(R.id.include_second_category_routine);
//        View aux4 = vista.findViewById(R.id.include_third_category_routine);
//
//        fillBigRoutines(aux1);
//        fillBigRoutines(aux2);
//        fillBigRoutines(aux3);
//        fillBigRoutines(aux4);

        return vista;
    }

    private void fillFavouriteList() {
        //HARDCODEADO ADAPTAR A API
        favouriteRoutinesList.add(new RoutineVO("IRONMAN", "HMHMHMHMHMHMHMHMHMHMHMHMHMHMHMHMHMMHMH", "Octa1", "Piernas", 5, 5, 18, 1, true));
        favouriteRoutinesList.add(new RoutineVO("CAPTAIN AMERICA", "VALCHARRRR SACA LA MANO DE AHI CARAJO", "Octa2","Brazos",1, 0, 180, 2, true));
        favouriteRoutinesList.add(new RoutineVO("THOR NOT AGUSTIN", "wasaaaaaaaaaaaaaaaaaaaaaaaaaa", "Octa3","Piernas", 2, 0, 11,3, true));
    }

    private void fillRecentList() {
        //HARDCODEADO ADAPTAR A API
        recentRoutinesList.add(new RoutineVO("OCTA", "horacio", "Octa4", "Piernas", 5, 5, 18, 1, true));
        recentRoutinesList.add(new RoutineVO("CAPTAIN AMERICA", "VALCHARRRR SACA LA MANO DE AHI CARAJO", "Octa2","Brazos",1, 0, 180, 2, true));
        recentRoutinesList.add(new RoutineVO("THOR NOT AGUSTIN", "wasaaaaaaaaaaaaaaaaaaaaaaaaaa", "Octa3","Piernas", 2, 0, 11,3, true));
    }
//
//    private void fillBigRoutines(View view) {
//        //HARDCODEADO ADAPTAR A API
//        RoutinesAdapter.RoutineViewHolder holder = new RoutinesAdapter.RoutineViewHolder(view);
//        holder.setName("PEPE");
//        holder.setDescription("Arroz con leche me quiero casar, con una seniorita de RADA TILLY");
//        holder.setDuration(String.valueOf(69420));
//        holder.setFavourite(true);
//        holder.setCategory1(3);
//        holder.setCategory2(1);
//    }

    @Override
    public void onRoutineClick(int position, String type) {
        ArrayList<RoutineVO> array;

        if(type.equals("Favourites"))
            array = favouriteRoutinesList;
        else if(type.equals("Recents"))
            array = recentRoutinesList;
        else return;

        HomeFragmentDirections.ActionHomeFragmentToRoutineInfoFragment action = HomeFragmentDirections.actionHomeFragmentToRoutineInfoFragment
                (array.get(position).getId(), array.get(position).getDescription(), array.get(position).getCreator(), array.get(position).getCategory());
        // LE PASO LOS ARGUMENTOS QUE NO TIENEN VALOR DEFAULT
        action.setNameRoutine(array.get(position).getName());
        action.setDifficultyRoutine(array.get(position).getLevelCategory1());
        //falta rating
        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(action);
    }
}