package com.example.getkracking.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getkracking.API.ApiClient;
import com.example.getkracking.API.ApiUserService;
import com.example.getkracking.API.model.CredentialsModel;
import com.example.getkracking.HomeActivity;
import com.example.getkracking.MainActivity;
import com.example.getkracking.R;
import com.example.getkracking.WelcomeActivity;
import com.example.getkracking.app.MyApplication;
import com.example.getkracking.app.MyPreferences;
import com.example.getkracking.dialogs.ErrorDialog;
import com.example.getkracking.repository.UserRepository;
import com.example.getkracking.viewmodels.RepositoryViewModelFactory;
import com.example.getkracking.viewmodels.UserViewModel;
import com.example.getkracking.viewmodels.WelcomeViewModel;

public class WelcomeLoginFragment extends Fragment {
    private EditText username, password;
    private TextView passwordToggle;
    private boolean passwordFlag = false;
    private MyApplication application;
    private UserRepository userRepository;
    private String argument;
    private UserViewModel userViewModel;

    public WelcomeLoginFragment() {
        //constructor vacio necesario
    }

    public WelcomeLoginFragment(String argument) {
        this.argument = argument;
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
        WelcomeViewModel argumentViewModel = new ViewModelProvider(this).get(WelcomeViewModel.class);

        RepositoryViewModelFactory viewModelFactory = new RepositoryViewModelFactory(UserRepository.class, ((MyApplication) getActivity().getApplication()).getUserRepository());
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);

        if(argument != null)
            argumentViewModel.setArgument(argument);

        username = v.findViewById(R.id.etUsername_login);  //cambiar id a username xd
        password = v.findViewById(R.id.etPassword_login);
        ((Button) v.findViewById(R.id.welcomeButton_login)).setOnClickListener(v1 -> {
            if (username.getText().length() == 0) {
                Toast.makeText(getContext(), R.string.username_missing, Toast.LENGTH_LONG).show();
                return;
            }
            if (password.getText().length() < 5) {
                Toast.makeText(getContext(), R.string.wrong_password_format, Toast.LENGTH_LONG).show();
                return;
            }
            ApiUserService userService = ApiClient.create(getActivity().getApplication(), ApiUserService.class);
            application = (MyApplication) getActivity().getApplication();
            userRepository = application.getUserRepository();

            String username_string = username.getText().toString();
            String password_string = password.getText().toString();
            userRepository.login(username_string, password_string)
                .observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            Log.d("UI", "awaiting");
                            break;
                        case SUCCESS:
                            application.getPreferences().setLogged(true);
                            application.getPreferences().setAuthToken(resource.data);
                            application.getPreferences().setUsername(username_string);
                            Log.d("UI", "User + " + username_string);
                            Log.d("UI", "PASS = " + password_string);
                            Log.d("UI", "ALL GOOD :) -- token = " + application.getPreferences().getAuthToken());
                            Intent homeIntent = new Intent(getActivity(), HomeActivity.class);

                            homeIntent.putExtra("routineId", argumentViewModel.getArgument());
                            application.getPreferences().setPassword(password_string);
                            startActivity(homeIntent);
                            getActivity().finish();

                            break;
                        case ERROR:
                            Log.d("UI", "Error");
                            openErrorDialog();
                            break;
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

    public void openErrorDialog() {
        ErrorDialog dialog = new ErrorDialog();
        dialog.show(getActivity().getSupportFragmentManager(), "Error in login popup");
    }
}