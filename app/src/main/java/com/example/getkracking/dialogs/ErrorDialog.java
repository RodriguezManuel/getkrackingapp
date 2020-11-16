package com.example.getkracking.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.getkracking.R;

public class ErrorDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_errorlogin, null);
        builder.setView(view).setTitle("Error en el inicio de sesión").setPositiveButton(R.string.confirmation_done_dialog, (dialog, which) -> {
            //se cierra el dialog
        });
        return builder.create();
    }
}
