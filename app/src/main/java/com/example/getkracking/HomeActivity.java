package com.example.getkracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.SurfaceControl;
import android.view.View;

import com.example.getkracking.fragments.HomeFragment;
import com.example.getkracking.fragments.PerfilFragment;
import com.example.getkracking.fragments.SearchFragment;
import com.example.getkracking.fragments.SocialFragment;
import com.example.getkracking.fragments.StatsFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Stack;

public class HomeActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton homeButton;
    Fragment homeFragment, socialFragment, searchFragment, statsFragment, perfilFragment;
    Stack<Integer> iconSelectedStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        homeButton = findViewById(R.id.fabBottomAppBar);
        homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.gray));
        iconSelectedStack = new Stack<>();

        bottomNavigationView.setBackground(null);   //saca la sombra que se previsualiza en la bottom bar
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);    //desactiva el boton placeholder del medio que ayuda en la alineacion

        homeFragment = new HomeFragment();
        socialFragment = new SocialFragment();
        searchFragment = new SearchFragment();
        statsFragment = new StatsFragment();
        perfilFragment = new PerfilFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switchFabHomeColor(false); //por si estaba prendido
            // no uso switch por un tema de retrocompatibilidad
            if (item.getItemId() == R.id.menu_social) {
                showSelectedFragment(socialFragment);
            } else if (item.getItemId() == R.id.menu_search) {
                showSelectedFragment(searchFragment);
            } else if (item.getItemId() == R.id.menu_stats) {
                showSelectedFragment(statsFragment);
            } else if (item.getItemId() == R.id.menu_perfil) {
                showSelectedFragment(perfilFragment);
            }
            return true;
        });

        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        switchFabHomeColor(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit(); // no uso showSelectedFragment por que quiero evitar el addToBackStack
    }

    public void goToHome(View view) {
        showSelectedFragment(homeFragment);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);//desactivo todos los otros activando el placeholder
        switchFabHomeColor(true);
    }

    private void showSelectedFragment(Fragment fragment) {
        for (int index = 0; index < bottomNavigationView.getMenu().size(); index++) {
            if (bottomNavigationView.getMenu().getItem(index).isChecked()) {
                iconSelectedStack.push(index);
            }
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //agrego este codigo para que la bottomNavBar mantenga su comportamiento
        if (!iconSelectedStack.isEmpty()) {
            int selectedIcon = iconSelectedStack.pop();
            bottomNavigationView.getMenu().getItem(selectedIcon).setChecked(true);
            if (selectedIcon == 2)
                switchFabHomeColor(true);
        }
    }

    private void switchFabHomeColor(boolean flag) {
        if (flag)
            homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.orange));
        else
            homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.gray));
    }
}