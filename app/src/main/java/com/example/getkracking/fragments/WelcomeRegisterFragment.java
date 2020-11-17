package com.example.getkracking.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getkracking.API.ApiClient;
import com.example.getkracking.API.ApiUserService;
import com.example.getkracking.API.model.RegisterCredentialsModel;
import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.dialogs.ConfirmationDialog;

public class WelcomeRegisterFragment extends Fragment {
    EditText username, email, password;
    private TextView passwordToggle;
    private boolean passwordFlag = false;

    public WelcomeRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_welcome_register, container, false);
        username = v.findViewById(R.id.etUsername_register);
        email = v.findViewById(R.id.etEmail_register);
        password = v.findViewById(R.id.etPassword_register);
        ((Button) v.findViewById(R.id.welcomeButton_register)).setOnClickListener(v1 -> {
            if(username.getText().length() == 0){
                Toast.makeText(getContext(), R.string.username_missing, Toast.LENGTH_LONG).show();
                return;
            }
            if(email.getText().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
                Toast.makeText(getContext(), R.string.invalid_email_format, Toast.LENGTH_LONG).show();
                return;
            }
            if(password.getText().length() < 5){
                Toast.makeText(getContext(), R.string.wrong_password_format, Toast.LENGTH_LONG).show();
                return;
            }
            ApiUserService userService = ApiClient.create(getActivity().getApplication(),ApiUserService.class);
            userService.register(new RegisterCredentialsModel(
                username.getText().toString(), //username
                password.getText().toString(), //password
                username.getText().toString(), //Full name
                "other",
                28400760,
                email.getText().toString(),
                "6969696969",
                "https://pm1.narvii.com/6707/15da369aa5d200756c41d4de43683b0c3886c507_hq.jpg"
            )).observe(getViewLifecycleOwner(), r -> {
                if (r.getError() == null) {
                    Log.d("UI", "id: " + r.getData().getId() + "name: " + r.getData().getFullName());
                    Intent homeIntent = new Intent(getActivity(), HomeActivity.class);

                } else {
                    Log.d("UI", "error");
                }
            });
            //AGREGAR LO Q HACE AL REGISTRARSE
            openConfirmationDialog(email.getText().toString());
        });

        passwordToggle = v.findViewById(R.id.password_toggle_register);

        passwordToggle.setOnClickListener(v1 -> {
            if (passwordFlag) {
                // SE DEBE DEJAR DE VER
                password.setTransformationMethod(new PasswordTransformationMethod());
                password.setSelection(password.getText().length()); // Para que siga escribiendo desde el final y no vuelva al inicio de la linea
                passwordToggle.setBackgroundResource(R.drawable.ic_eye);
            } else {
                password.setTransformationMethod(null);
                password.setSelection(password.getText().length());
                passwordToggle.setBackgroundResource(R.drawable.ic_eye_off);
            }
            passwordFlag = !passwordFlag;
        });
        return v;
    }

    public void openConfirmationDialog( CharSequence email) {
        ConfirmationDialog dialog = new ConfirmationDialog( email);
        dialog.show(getActivity().getSupportFragmentManager(), "Confirmation popup");
    }
}