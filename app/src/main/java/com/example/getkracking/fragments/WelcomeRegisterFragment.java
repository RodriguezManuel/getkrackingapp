package com.example.getkracking.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.getkracking.app.MyApplication;
import com.example.getkracking.dialogs.ConfirmationDialog;
import com.example.getkracking.repository.UserRepository;
import com.example.getkracking.viewmodels.RepositoryViewModelFactory;
import com.example.getkracking.viewmodels.UserViewModel;

public class WelcomeRegisterFragment extends Fragment {
    EditText username, email, password;
    private TextView passwordToggle;
    private boolean passwordFlag = false;
    private UserViewModel userViewModel;

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

        RepositoryViewModelFactory viewModelFactory = new RepositoryViewModelFactory(UserRepository.class, ((MyApplication) getActivity().getApplication()).getUserRepository());
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);

        username = v.findViewById(R.id.etUsername_register);
        email = v.findViewById(R.id.etEmail_register);
        password = v.findViewById(R.id.etPassword_register);
        ((Button) v.findViewById(R.id.welcomeButton_register)).setOnClickListener(v1 -> {
            if (username.getText().length() == 0) {
                Toast.makeText(getContext(), R.string.username_missing, Toast.LENGTH_LONG).show();
                return;
            }
            if (email.getText().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
                Toast.makeText(getContext(), R.string.invalid_email_format, Toast.LENGTH_LONG).show();
                return;
            }
            if (password.getText().length() < 5) {
                Toast.makeText(getContext(), R.string.wrong_password_format, Toast.LENGTH_LONG).show();
                return;
            }

            userViewModel.register(username.getText().toString(), email.getText().toString(), password.getText().toString())
                    .observe(getViewLifecycleOwner(), r -> {
                        switch (r.status) {
                            case LOADING:
                                Log.d("UI", "awaiting favourite routines");
                                break;
                            case SUCCESS:
                                Log.d("UI", "id: " + r.data.getId() + "name: " + r.data.getFullName());
                                Intent homeIntent = new Intent(getActivity(), HomeActivity.class);


                                break;
                            case ERROR:
                                Log.d("UI", "error");
                                break;
                        }
                    });
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

        Button confirmationToggle = v.findViewById(R.id.openDialog_register);
        confirmationToggle.setOnClickListener(v12 -> openConfirmationDialog(email.getText()));
        return v;
    }

    public void openConfirmationDialog(CharSequence email) {
        ConfirmationDialog dialog = new ConfirmationDialog(email);
        dialog.show(getActivity().getSupportFragmentManager(), "Confirmation popup");
    }
}