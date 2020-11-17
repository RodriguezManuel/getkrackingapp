package com.example.getkracking.fragments;

import android.os.Bundle;
import android.text.InputType;
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

public class EditPerfilFragment extends Fragment {
    EditText name, image;

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
        if (getArguments() != null) {
            EditPerfilFragmentArgs args = EditPerfilFragmentArgs.fromBundle(getArguments());
            //una vez q consegui los argumentos los seteo en la vista
            ((TextView) vista.findViewById(R.id.name_edit_perfil)).setText(args.getName());
            ((TextView) vista.findViewById(R.id.username_edit_perfil)).setText(args.getUsername());
            ((TextView) vista.findViewById(R.id.email_edit_perfil)).setText(args.getEmail());

            //IMAGEN???

        }
        vista.findViewById(R.id.returnButton_edit_perfil).setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();
        });
        vista.findViewById(R.id.saveButton_edit_perfil).setOnClickListener(v -> {
            //POST A API CON NUEVA INFORMACION
        });

        name = vista.findViewById(R.id.name_edit_perfil);
        image = vista.findViewById(R.id.image_edit_perfil);

        TextView nameEdit = vista.findViewById(R.id.name_edit_perfil_icon);
        nameEdit.setInputType(InputType.TYPE_NULL);
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
        TextView emailEdit = vista.findViewById(R.id.image_edit_perfil_icon);
        emailEdit.setInputType(InputType.TYPE_NULL);
        name.setTextColor(ContextCompat.getColor(getContext(),R.color.primary));
        emailEdit.setOnClickListener(v13 -> {
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
