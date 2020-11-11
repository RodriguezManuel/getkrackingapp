package com.example.getkracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.getkracking.fragments.HomeFragment;
import com.example.getkracking.fragments.PerfilFragment;
import com.example.getkracking.fragments.SearchFragment;
import com.example.getkracking.fragments.StatsFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        homeButton = findViewById(R.id.fabBottomAppBar);
        homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.gray));

        bottomNavigationView.setBackground(null);   //saca la sombra que se previsualiza en la bottom bar
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);    //desactiva el boton placeholder del medio que ayuda en la alineacion

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.gray)); //por si estaba prendido
            if (item.getItemId() == R.id.menu_social) {
                showSelectedFragment(new PerfilFragment());
            } else if (item.getItemId() == R.id.menu_search) {
                showSelectedFragment(new SearchFragment());
            } else if (item.getItemId() == R.id.menu_stats) {
                showSelectedFragment(new StatsFragment());
            } else if (item.getItemId() == R.id.menu_perfil) {
                showSelectedFragment(new PerfilFragment());
            }
            getSupportFragmentManager().beginTransaction().addToBackStack(null);
            return true;
        });

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new HomeFragment()).commit();
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.orange));
    }

    public void goToHome(View view) {
        bottomNavigationView.getMenu().getItem(2).setChecked(true);//desactivo todos los otros activando el placeholder
        homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.orange));
        showSelectedFragment(new HomeFragment());
    }

    private void showSelectedFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        getSupportFragmentManager().beginTransaction().addToBackStack(null);
    }
}