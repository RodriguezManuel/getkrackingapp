package com.example.getkracking.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;

public class PerfilFragment extends Fragment {

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        if (((HomeActivity) getActivity()).getSupportActionBar() != null)
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(R.string.bottombaricon_perfil);

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_perfil, container, false);
        TextView tvName = vista.findViewById(R.id.name_perfil);

        tvName.setText("FABIO SINAPELLIDO");    //HARDCODEADO

        LinearLayoutCompat editPerfil = vista.findViewById(R.id.EditProfileCompat);
        editPerfil.setOnClickListener(v1 -> {
             PerfilFragmentDirections.ActionPerfilFragmentToEditPerfilFragment action = PerfilFragmentDirections.actionPerfilFragmentToEditPerfilFragment(tvName.getText().toString(),
                    "fabiopisculichi",  //HARDCODEADO
                    "pepepe@lololo.com.uy");    //HARDCODEADO


            //IMAGEN??????

            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(action);
        });

        LinearLayoutCompat logout = vista.findViewById(R.id.LogoutCompat);
        logout.setOnClickListener(v -> {


            ///LOGOUT DE LA API


        });

        return vista;
    }

}