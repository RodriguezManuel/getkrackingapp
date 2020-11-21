package com.example.getkracking.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.getkracking.R;
import com.example.getkracking.app.MyApplication;
import com.example.getkracking.repository.RoutineRepository;
import com.example.getkracking.viewmodels.RepositoryViewModelFactory;
import com.example.getkracking.viewmodels.RoutinesViewModel;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EndedRoutineDialog extends AppCompatDialogFragment {
    float rating = -1;
    int routineId;
    RoutinesViewModel viewModel;

    public EndedRoutineDialog(int routineId) {
        super();
        this.routineId = routineId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_endedroutine, null);

        RepositoryViewModelFactory viewModelFactory = new RepositoryViewModelFactory(RoutineRepository.class, ((MyApplication) getActivity().getApplication()).getRoutineRepository());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RoutinesViewModel.class);

        ((RatingBar) view.findViewById(R.id.rating_routine_end)).setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            this.rating = rating;
        });
        builder.setView(view).setPositiveButton(R.string.confirm, (dialog, which) -> {
            if (rating != -1) {
                viewModel.postReview(routineId, (int) rating).observe(this, resource -> {
                    switch (resource.status) {
                        case LOADING:
                            Log.d("UI", "awaiting for rating post");
                            break;
                        case SUCCESS:
                            Log.d("UI", "Éxito enviando puntuacion");
                            break;
                        case ERROR:
                            Log.d("UI", "Error en puntuacion de rutina - " + resource.message);
                            break;
                    }
                });
            }

            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.homeFragment); //sale de la rutina
        });
        AlertDialog dialog = builder.create();

        //para que no se cierre ante un toque fuera del dialogo
        dialog.setCancelable(false);
        return dialog;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //saco la barra de navegacion
        BottomAppBar bottomAppBar = getActivity().findViewById(R.id.bottomAppBar);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        FloatingActionButton homeButton = getActivity().findViewById(R.id.fabBottomAppBar);

        //vuelve a aparecer la interfaz de navegacion
        bottomAppBar.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        homeButton.setVisibility(View.VISIBLE);
    }
}
