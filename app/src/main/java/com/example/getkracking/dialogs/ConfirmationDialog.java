package com.example.getkracking.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.getkracking.R;
import com.example.getkracking.app.MyApplication;
import com.example.getkracking.repository.UserRepository;
import com.example.getkracking.viewmodels.RepositoryViewModelFactory;
import com.example.getkracking.viewmodels.UserViewModel;

import org.w3c.dom.Text;

public class ConfirmationDialog extends AppCompatDialogFragment {
    private EditText etEmail, etCode;
    private CharSequence mail;
    UserViewModel repository;

    public ConfirmationDialog(CharSequence mail){
        super();
        this.mail = mail;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_emailconfirmation, null);
        Button bt = view.findViewById(R.id.buttonResendVerification);
        RepositoryViewModelFactory viewModelFactory = new RepositoryViewModelFactory(UserRepository.class, ((MyApplication) getActivity().getApplication()).getUserRepository());
        repository = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);

        bt.setOnClickListener(v -> {
            repository.resendVerification(etEmail.getText().toString()).observe(this, resource -> {
                switch (resource.status) {
                    case LOADING:
                        Log.d("UI", "awaiting for verification");
                        break;
                    case SUCCESS:
                        dismiss();
                        break;
                    case ERROR:
                        dismiss();
                        break;
                }
            });;
            bt.setClickable(false);
            bt.setVisibility(View.INVISIBLE);   //solo se puede llamar una vez a la API
        });

        builder.setView(view).setTitle(R.string.email_confirmation).setNegativeButton(R.string.confirmation_close_dialog, (dialog, which) -> {
            // ROUTINE ID ESTA EN LA VARIABLE DE LA CLASE
            //no pasa nada
        }).setPositiveButton(R.string.confirmation_done_dialog, (dialog, which) -> {
            //nada por que despues se sobreescribe
        });
        etEmail = view.findViewById(R.id.etEmail_dialog);
        if ( mail != null) {
            etEmail.setText(mail);
        }
        etCode = view.findViewById(R.id.etCode_dialog);
        TextView errorMsg = view.findViewById(R.id.wrong_code);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (etEmail.getText().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches()) {
                Toast.makeText(getContext(), R.string.invalid_email_format, Toast.LENGTH_LONG).show();
                return;
            }
            if (etCode.getText().length() != 6) {
                Toast.makeText(getContext(), R.string.invalid_confirmationcode_format, Toast.LENGTH_LONG).show();
                return;
            }

            //validar codigo y mail


            repository.verifyEmail(etEmail.getText().toString(), etCode.getText().toString()).observe(this, resource -> {
                switch (resource.status) {
                    case LOADING:
                        Log.d("UI", "awaiting for verification");
                        break;
                    case SUCCESS:
                        dismiss();
                        break;
                    case ERROR:
                        errorMsg.setVisibility(View.VISIBLE);
                        break;
                }
            });

    });
        return dialog;
    }


}
