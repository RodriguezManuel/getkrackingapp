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
                showSelectedFragment(socialFragment, "Social");
            } else if (item.getItemId() == R.id.menu_search) {
                showSelectedFragment(searchFragment, "Search");
            } else if (item.getItemId() == R.id.menu_stats) {
                showSelectedFragment(statsFragment, "Stats");
            } else if (item.getItemId() == R.id.menu_perfil) {
                showSelectedFragment(perfilFragment, "Perfil");
            }
            return true;
        });

        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        switchFabHomeColor(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, new HomeFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack("Home");
        transaction.commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment())
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit(); // no uso showSelectedFragment por que quiero evitar el addToBackStack
//        showSelectedFragment(new HomeFragment(), "Home");

    }

    public void goToHome(View view) {
        showSelectedFragment(homeFragment, "Home");
        bottomNavigationView.getMenu().getItem(2).setChecked(true);//desactivo todos los otros activando el placeholder
        switchFabHomeColor(true);
    }

    private void showSelectedFragment(Fragment fragment, String name) {
        if(getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() -1).getName().equals(name))
            return;

        boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate(name, 0);

        if (!fragmentPopped) {
            //fragment no en stack, se debe crear
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(name);
            transaction.commit();
        }

        int newIconIndex;
        switch (name) {
            case "Social":
                newIconIndex = 0;
                break;
            case "Search":
                newIconIndex = 1;
                break;
            case "Stats":
                newIconIndex = 3;
                break;
            case "Perfil":
                newIconIndex = 4;
                break;
            default:
                newIconIndex = 2;
        }
        //no es el mismo que se encontraba prendido antes, saco si se encontraba antes y lo pusheo
        if (iconSelectedStack.contains(newIconIndex)) {
            iconSelectedStack.remove((Object) newIconIndex);
        }

        //Busco cual es el icono que estaba prendido antes del cambio para ver si es necesario guardarlo
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            if (bottomNavigationView.getMenu().getItem(i).isChecked()) {
                if (i != newIconIndex) {
                    iconSelectedStack.push(i);
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1)
            finish();
        else {
            super.onBackPressed();

            //agrego este codigo para que la bottomNavBar mantenga su comportamiento
            if (!iconSelectedStack.isEmpty()) {
                //Toast.makeText(getBaseContext(), String.valueOf(iconSelectedStack.size()), Toast.LENGTH_SHORT).show();
                int selectedIcon = iconSelectedStack.pop();
                bottomNavigationView.getMenu().getItem(selectedIcon).setChecked(true);
                switchFabHomeColor(selectedIcon == 2);
            }
        }
    }

    private void switchFabHomeColor(boolean flag) {
        if (flag)
            homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.orange));
        else
            homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.gray));
    }
}