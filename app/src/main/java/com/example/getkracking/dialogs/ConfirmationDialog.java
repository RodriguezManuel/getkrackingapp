package com.example.getkracking.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.getkracking.R;

import org.w3c.dom.Text;

public class ConfirmationDialog extends AppCompatDialogFragment {
    private EditText etEmail, etCode;
    private CharSequence mail;
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

        view.findViewById(R.id.buttonResendVerification).setOnClickListener(v -> {
            //TODO: IMPLEMENTAR LLAMADO A RESEND DE API
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
            if(etEmail.getText().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches()) {
                Toast.makeText(getContext(), R.string.invalid_email_format, Toast.LENGTH_LONG).show();
                return;
            }
            if(etCode.getText().length() != 10) {
                Toast.makeText(getContext(), R.string.invalid_confirmationcode_format, Toast.LENGTH_LONG).show();
                return;
            }

            //validar codigo y mail

            if(false)
                //se valido el codigo
                dismiss();
            else
                errorMsg.setVisibility(View.VISIBLE);
        });

        return dialog;
    }
}
