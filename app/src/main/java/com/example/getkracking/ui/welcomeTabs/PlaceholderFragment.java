package com.example.getkracking.ui.welcomeTabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.getkracking.R;
import com.example.getkracking.fragments.WelcomeLoginFragment;
import com.example.getkracking.fragments.WelcomeRegisterFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PlaceholderFragment extends Fragment {

    public static Fragment newInstance(int index) {
        Fragment fragment = null;
        switch (index){
            case 1: fragment = new WelcomeLoginFragment(); break;
            case 2: fragment = new WelcomeRegisterFragment(); break;
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome_login, container, false);
    }
}