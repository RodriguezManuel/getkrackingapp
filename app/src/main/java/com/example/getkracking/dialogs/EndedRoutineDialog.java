package com.example.getkracking.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.Navigation;

import com.example.getkracking.R;

public class EndedRoutineDialog extends AppCompatDialogFragment {
    float rating = -1;
    int routineId;

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
        ((RatingBar) view.findViewById(R.id.rating_routine_end)).setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            this.rating = rating;
        });
        builder.setView(view).setPositiveButton(R.string.confirm, (dialog, which) -> {
            if (rating != -1) {
                //POST A API CON VALOR DE RESENIA QUE ESTA EN this.rating
            }

            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack(); //sale de la rutina
        });
        AlertDialog dialog = builder.create();

        //para que no se cierre ante un toque fuera del dialogo
        dialog.setCancelable(false);
        return dialog;
    }
}
