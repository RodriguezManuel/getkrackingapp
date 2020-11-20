package com.example.getkracking.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.getkracking.HomeActivity;
import com.example.getkracking.R;
import com.example.getkracking.adapters.RoutinesAdapter;
import com.example.getkracking.app.MyApplication;
import com.example.getkracking.repository.UserRepository;
import com.example.getkracking.viewmodels.RepositoryViewModelFactory;
import com.example.getkracking.viewmodels.UserViewModel;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditPerfilFragment extends Fragment {
    private EditText name, image;

    private UserViewModel userViewModel;

    public EditPerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        setHasOptionsMenu(true);
        Toolbar mToolBar = ((HomeActivity) getActivity()).findViewById(R.id.homeTopBar);
        ActionBar actionBar = ((HomeActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.edit_perfil);

        //CUSTOMIZAR BACK BUTTON
        ((HomeActivity) getActivity()).setSupportActionBar(mToolBar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationIcon(R.drawable.ic_chevron_left);
        mToolBar.setNavigationOnClickListener(v -> Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack());

        super.onResume();
    }

    private void updateImage(View vista) {
        Thread loadImage = new Thread(() -> {
            try {
                URL newurl = new URL(image.getText().toString());
                Bitmap bm = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                ((Activity) getContext()).runOnUiThread(() -> {
                    ((CircleImageView) vista.findViewById(R.id.picture_edit_perfil)).setImageBitmap(bm);
                    userViewModel.setImageBM(bm);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        loadImage.start();
    }

    private void setEditing(EditText text, boolean bool, boolean isName) {
        if (bool) {
            text.setInputType(InputType.TYPE_CLASS_TEXT);
            text.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        } else {
            text.setInputType(InputType.TYPE_NULL);
            text.setTextColor(ContextCompat.getColor(getContext(), R.color.primary));
        }

        if(isName)
            userViewModel.setEditingName(bool);
        else userViewModel.setEditingEmail(bool);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_edit_perfil, container, false);

        RepositoryViewModelFactory viewModelFactory = new RepositoryViewModelFactory(UserRepository.class, ((MyApplication) getActivity().getApplication()).getUserRepository());
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);

        name = vista.findViewById(R.id.name_edit_perfil);
        image = vista.findViewById(R.id.image_edit_perfil);

        if (getArguments() != null) {
            EditPerfilFragmentArgs args = EditPerfilFragmentArgs.fromBundle(getArguments());
            userViewModel.setUsername(args.getUsername());
            userViewModel.setEmail(args.getEmail());
            userViewModel.setIdUser(args.getIdUser());
            //una vez q consegui los argumentos los seteo en la vista
            name.setText(args.getName());
            image.setText(args.getImage());
            ((TextView) vista.findViewById(R.id.email_edit_perfil)).setText(args.getEmail());
            ((TextView) vista.findViewById(R.id.username_edit_perfil)).setText(args.getUsername());

            //cargo la imagen
            updateImage(vista);
        }
        vista.findViewById(R.id.returnButton_edit_perfil).setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();
        });
        vista.findViewById(R.id.saveButton_edit_perfil).setOnClickListener(v -> {

            //TODO: post a la api
            postUserData();

            reloadUserData(vista);
        });

        //veo el estado previo
        setEditing(name, userViewModel.isEditingName(), true);
        setEditing(image, userViewModel.isEditingEmail(), false);

        // configuracion de campo para cambiar nombre
        TextView nameEdit = vista.findViewById(R.id.name_edit_perfil_icon);
        nameEdit.setOnClickListener(v12 -> setEditing(name, name.getInputType() == InputType.TYPE_NULL, true));

        // configuracion de campo para cambiar imagen
        TextView imageEdit = vista.findViewById(R.id.image_edit_perfil_icon);
        imageEdit.setOnClickListener(v13 -> setEditing(image, image.getInputType() == InputType.TYPE_NULL, false));
        return vista;
    }

    //Cuando guardo cambios hago este post
    private void postUserData() {
        userViewModel.updateUser(name.getText().toString(), image.getText().toString()).observe(getViewLifecycleOwner(),
                resource -> {
                    switch (resource.status) {
                        case LOADING:
                            Log.d("UI", "awaiting user data");
                            break;
                        case SUCCESS:
                            Log.d("UI", "Éxito actualizando datos");
                            break;
                        case ERROR:
                            Log.d("UI", "Error en get edición de perfil - " + resource.message);
                            break;
                    }
                }
        );
    }

    //refresca los datos en pantalla. Lo ejecuto luego de hacer post
    private void reloadUserData(View vista) {
        //Pido los datos a la API
        userViewModel.getCurrent().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    Log.d("UI", "awaiting user data");
                    break;
                case SUCCESS:
                    Log.d("UI", "Éxito recuperando datos");
                    name.setText(resource.data.getFullName());
                    image.setText(resource.data.getAvatarUrl());
                    userViewModel.setUsername(resource.data.getUsername());
                    userViewModel.setEmail(resource.data.getEmail());
                    userViewModel.setIdUser(resource.data.getId());

                    break;
                case ERROR:
                    Log.d("UI", "Error en get edición de perfil - " + resource.message);
                    break;
            }
        });
        //refresco la imágen
        updateImage(vista);
    }
}



