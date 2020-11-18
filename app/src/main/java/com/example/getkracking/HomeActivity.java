package com.example.getkracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton homeButton;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Toolbar toolbar = findViewById(R.id.homeTopBar);
        setSupportActionBar(toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.socialFragment, R.id.searchFragment,
                R.id.homeFragment, R.id.statsFragment, R.id.perfilFragment)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        homeButton = findViewById(R.id.fabBottomAppBar);
        bottomNavigationView.setBackground(null);   //saca la sombra que se previsualiza en la bottom bar
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);    //desactiva el boton placeholder del medio que ayuda en la alineacion
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switchFabHomeColor(false); //por si estaba prendido

            // no uso switch por un tema de retrocompatibilidad
            if (id == R.id.menu_social) {
                showSelectedFragment(R.id.socialFragment);
            } else if (id == R.id.menu_search) {
                showSelectedFragment(R.id.searchFragment);
            } else if (id == R.id.menu_stats) {
                showSelectedFragment(R.id.statsFragment);
            } else if (id == R.id.menu_perfil) {
                showSelectedFragment(R.id.perfilFragment);
            } else if (id == R.id.menu_placeholder)
                switchFabHomeColor(true);
            return true;
        });

        if (savedInstanceState == null) {
            switchFabHomeColor(true);
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
        } else if (navController.getCurrentBackStackEntry() != null && navController.getCurrentBackStackEntry().getDestination().getId() == R.id.homeFragment)
            switchFabHomeColor(true);
        else switchFabHomeColor(false);
    }

    public void goToHome(View view) {
        if (!showSelectedFragment(R.id.homeFragment)) {
            bottomNavigationView.getMenu().getItem(2).setChecked(true); //desactivo todos los otros activando el placeholder
            switchFabHomeColor(true);
        }
    }

    private boolean showSelectedFragment(int id) {
        if (navController.getCurrentBackStackEntry() != null &&
                navController.getCurrentBackStackEntry().getDestination().getId() == id)
            return false;

        NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        boolean poppedFragment = controller.popBackStack(id, false);
        if (!poppedFragment) {
            controller.navigate(id);    //lo creo por que no existe
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (navController.getCurrentBackStackEntry() != null) {
            int actualId = navController.getCurrentBackStackEntry().getDestination().getId();
            if (!checkOptionBottomBar(actualId) && navController.getPreviousBackStackEntry() != null) {
                int previousId = navController.getPreviousBackStackEntry().getDestination().getId();
                checkOptionBottomBar(previousId);
            }
        }
    }

    public boolean checkOptionBottomBar(int id) {
        if (id == R.id.homeFragment) {
            switchFabHomeColor(true);
            bottomNavigationView.getMenu().getItem(2).setChecked(true); //desactivo todos los otros activando el placeholder
        } else if (id == R.id.socialFragment)
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
        else if (id == R.id.searchFragment)
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
        else if (id == R.id.statsFragment)
            bottomNavigationView.getMenu().getItem(3).setChecked(true);
        else if (id == R.id.perfilFragment)
            bottomNavigationView.getMenu().getItem(4).setChecked(true);
        else
            return false;

        return true;
    }

    private void switchFabHomeColor(boolean flag) {
        if (flag)
            homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.orange));
        else
            homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.primary));
    }
}