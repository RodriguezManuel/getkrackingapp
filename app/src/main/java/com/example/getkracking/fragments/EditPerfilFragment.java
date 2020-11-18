package com.example.getkracking.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.adapters.RoutinesAdapter;
import com.example.getkracking.app.MyApplication;
import com.example.getkracking.repository.UserRepository;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EditPerfilFragment extends Fragment {
    EditText name, image;

    private MyApplication application;
    private UserRepository userRepository;

    public EditPerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        setHasOptionsMenu(true);
        Toolbar mToolBar = ((HomeActivity) getActivity()).findViewById(R.id.homeTopBar);
        ActionBar actionBar = ((HomeActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Editar Perfil");

        //CUSTOMIZAR BACK BUTTON
        ((HomeActivity) getActivity()).setSupportActionBar(mToolBar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationIcon(R.drawable.ic_chevron_left);
        mToolBar.setNavigationOnClickListener(v -> Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack());

        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_edit_perfil, container, false);

        application = (MyApplication)getActivity().getApplication();
        userRepository = application.getUserRepository();

        if (getArguments() != null) {
            EditPerfilFragmentArgs args = EditPerfilFragmentArgs.fromBundle(getArguments());
            //una vez q consegui los argumentos los seteo en la vista
            ((TextView) vista.findViewById(R.id.name_edit_perfil)).setText(args.getName());
            ((TextView) vista.findViewById(R.id.username_edit_perfil)).setText(args.getUsername());
            ((TextView) vista.findViewById(R.id.email_edit_perfil)).setText(args.getEmail());

            //TODO: fetch a la api
            userRepository.getCurrent().observe(getViewLifecycleOwner(), resource -> {
                switch (resource.status) {
                    case LOADING:
                        Log.d("UI", "awaiting user data");
                        break;
                    case SUCCESS:
                        Log.d("UI", "Éxito recuperando datos");
                        name.setText(resource.data.getFullName());
                        image.setText(resource.data.getAvatarUrl());

                        //TODO: faltan campos para mail y username
                        break;
                    case ERROR:
                        Log.d("UI", "Error en get edición de perfil - " + resource.message);
                        break;
                }
            });
        }
        vista.findViewById(R.id.returnButton_edit_perfil).setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();
        });
        vista.findViewById(R.id.saveButton_edit_perfil).setOnClickListener(v -> {

            //TODO: post a la api


        });

        name = vista.findViewById(R.id.name_edit_perfil);
        image = vista.findViewById(R.id.image_edit_perfil);

        TextView nameEdit = vista.findViewById(R.id.name_edit_perfil_icon);
        name.setInputType(InputType.TYPE_NULL);
        name.setTextColor(ContextCompat.getColor(getContext(),R.color.primary));    //arranca disabled
        nameEdit.setOnClickListener(v12 -> {
            if (name.getInputType() == InputType.TYPE_NULL) {
                name.setInputType(InputType.TYPE_CLASS_TEXT);
                name.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            } else {
                name.setInputType(InputType.TYPE_NULL);
                name.setTextColor(ContextCompat.getColor(getContext(),R.color.primary));
            }
        });

        TextView imageEdit = vista.findViewById(R.id.image_edit_perfil_icon);
        image.setInputType(InputType.TYPE_NULL);
        name.setTextColor(ContextCompat.getColor(getContext(),R.color.primary));
        imageEdit.setOnClickListener(v13 -> {
            if (image.getInputType() == InputType.TYPE_NULL){
                image.setInputType(InputType.TYPE_CLASS_TEXT);
                name.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            }
            else{
                image.setInputType(InputType.TYPE_NULL);
                name.setTextColor(ContextCompat.getColor(getContext(),R.color.primary));
            }
        });
        return vista;
    }
}
