package com.example.getkracking.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.getkracking.API.ApiClient;
import com.example.getkracking.API.ApiUserService;
import com.example.getkracking.API.model.Credentials;
import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;

public class WelcomeLoginFragment extends Fragment {
    EditText username, password;
    private TextView passwordToggle;
    private boolean passwordFlag = false;

    public WelcomeLoginFragment() {
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
        View v = inflater.inflate(R.layout.fragment_welcome_login, container, false);

        username = v.findViewById(R.id.etEmail_login);  //cambiar id a username xd
        password = v.findViewById(R.id.etPassword_login);
        ((Button) v.findViewById(R.id.welcomeButton_login)).setOnClickListener(v1 -> {
            ApiUserService userService = ApiClient.create(getActivity().getApplication(),ApiUserService.class);
            userService.login(new Credentials(username.getText().toString(), password.getText().toString())).observe(getViewLifecycleOwner(), r -> {
                if (r.getError() == null) {
                    Log.d("UI", "Token: " + r.getData().getToken());
                    Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(homeIntent);
                    getActivity().finish();
                } else {
                    Log.d("UI", "error");
                }
            });
        });

        passwordToggle = v.findViewById(R.id.password_toggle_login);

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
}