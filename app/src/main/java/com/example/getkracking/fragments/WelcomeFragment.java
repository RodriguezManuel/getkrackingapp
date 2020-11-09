package com.example.getkracking.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.getkracking.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment extends Fragment {
    private static final String ARG_PARAM = "param";
    private String type;
    private Button bt;
    private EditText passwordET;
    private TextView passwordToggle;
    private boolean passwordFlag = false;

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
        passwordET = v.findViewById(R.id.etPassword);
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
        return v;
    }
}