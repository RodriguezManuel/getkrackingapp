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
import com.example.getkracking.WelcomeActivity;

public class WelcomeFragment extends Fragment {
    private static final String ARG_PARAM = "param";
    private  String type;
    private Button bt;
    private EditText passwordET , usernameET;
    private TextView passwordToggle;
    private boolean passwordFlag = false;
    private TextView link1TV, link2TV;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    public static WelcomeFragment newInstance(String param) {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        bt = v.findViewById(R.id.welcomeButton);
        if (getArguments() != null)
            type = getArguments().getString(ARG_PARAM);

        bt.setText(type);
        bt.setOnClickListener(v12 -> {
            if(type.equals("Ingresar")){
                ApiUserService userService = ApiClient.create(ApiUserService.class);
                userService.login(new Credentials( usernameET.getText().toString() , passwordET.getText().toString())).observe(getViewLifecycleOwner(), r-> {
                    if ( r.getError() == null){
                        Log.d("UI" , "Token: " + r.getData().getToken() );
                        Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(homeIntent);
                        getActivity().finish();
                    }else {
                        Log.d("UI" , "error" );
                    }
                });
            }
        });
        passwordET = v.findViewById(R.id.etPassword);
        usernameET = v.findViewById(R.id.etEmail);
        passwordToggle = v.findViewById(R.id.password_toggle);

        passwordToggle.setOnClickListener(v1 -> {
            if(passwordFlag){
                // SE DEBE DEJAR DE VER
                passwordET.setTransformationMethod(new PasswordTransformationMethod());
                passwordET.setSelection(passwordET.getText().length()); // Para que siga escribiendo desde el final y no vuelva al inicio de la linea
                passwordToggle.setBackgroundResource(R.drawable.ic_eye);
            }else {
                passwordET.setTransformationMethod(null);
                passwordET.setSelection(passwordET.getText().length());
                passwordToggle.setBackgroundResource(R.drawable.ic_eye_off);
            }
            passwordFlag = !passwordFlag;
        });

        link1TV = v.findViewById(R.id.welcomeLink);
        link1TV.setText(R.string.welcome_link);
        link2TV = v.findViewById(R.id.welcomeLoginLink);
        if(type.equals("Ingresar")){
            link2TV.setText(R.string.welcome_login_link);
        }

        return v;
    }
}