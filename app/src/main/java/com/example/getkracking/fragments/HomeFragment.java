package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.adapters.CategoriesAdapter;
import com.example.getkracking.adapters.RoutinesAdapter;
import com.example.getkracking.adapters.SmallRoutinesAdapter;
import com.example.getkracking.app.MyApplication;
import com.example.getkracking.entities.CategoryVO;
import com.example.getkracking.entities.RoutineVO;
import com.example.getkracking.repository.RoutineRepository;
import com.example.getkracking.viewmodels.RepositoryViewModelFactory;
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
    ArrayList<Pair<CategoryVO, ArrayList<RoutineVO>>> categoriesList;
    CategoriesAdapter adapterCategories;
    RecyclerView recyclerCategories;

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

        //categories
        categoriesList = new ArrayList<>();
        recyclerCategories = vista.findViewById(R.id.recyclerHomeCategories);
        recyclerCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCategories = new CategoriesAdapter(getActivity(), categoriesList, this);
        recyclerCategories.setAdapter(adapterCategories);
        recyclerRecentRoutines.setNestedScrollingEnabled(false);

        fillFavouriteList();
        fillCategoriesList();

        return vista;
    }

    private void fillCategoriesList() {
        if (!viewModel.getRoutines().hasActiveObservers()) {
            viewModel.getRoutines().observe(getViewLifecycleOwner(), resource -> {
                switch (resource.status) {
                    case LOADING:
                        Log.d("UI", "awaiting routines");
                        break;
                    case SUCCESS:
                        Log.d("UI", "Éxito recuperando rutinas");

                        if (!viewModel.getCategories().hasActiveObservers()) {
                            viewModel.getCategories().observe(getViewLifecycleOwner(), resourceCat -> {
                                switch (resourceCat.status) {
                                    case LOADING:
                                        Log.d("UI", "awaiting categories");
                                        break;
                                    case SUCCESS:
                                        Log.d("UI", "exito recuperando categorias");
                                        ArrayList<RoutineVO> aux;
                                        if (resourceCat.data != null)
                                            for (CategoryVO cat : resourceCat.data) {
                                                aux = new ArrayList<>();
                                                //busco rutinas que matchean con la categoria actual
                                                if (resource.data != null)
                                                    for (RoutineVO rout : resource.data) {
                                                        if (rout.getCategory() == cat.getId())
                                                            aux.add(rout);
                                                    }
                                                if (aux.size() != 0) {
                                                    categoriesList.add(new Pair<>(new CategoryVO(cat.getId(), cat.getName(), cat.getDetail()), aux));
                                                }
                                                //no se muestran categorias vacias
                                                fillHighlightedList();
                                            }
                                        adapterCategories.notifyDataSetChanged();
                                        break;
                                    case ERROR:
                                        Log.d("UI", "Error en get categories - " + resourceCat.message);
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

    private void fillHighlightedList() {
        //solo un item necesitamos, tomamos el primero disponible
        if (highlightedRoutinesList.size() == 0 && categoriesList.size() != 0 && categoriesList.get(0).second.size() != 0) {
            highlightedRoutinesList.add(categoriesList.get(0).second.get(0));
            adapterHighlighted.notifyDataSetChanged();
        }

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
                                if (favresource.data != null)
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