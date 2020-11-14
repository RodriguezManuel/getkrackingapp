package com.example.getkracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;
import java.util.Stack;

public class HomeActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton homeButton;
    Stack<Integer> iconSelectedStack;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.topbarmenu, menu);
        return true;
    }

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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        homeButton = findViewById(R.id.fabBottomAppBar);
        homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.primary));
        iconSelectedStack = new Stack<>();

        bottomNavigationView.setBackground(null);   //saca la sombra que se previsualiza en la bottom bar
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);    //desactiva el boton placeholder del medio que ayuda en la alineacion

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switchFabHomeColor(false); //por si estaba prendido

            // no uso switch por un tema de retrocompatibilidad
            if (item.getItemId() == R.id.menu_social) {
                showSelectedFragment(R.id.socialFragment, 0);
            } else if (item.getItemId() == R.id.menu_search) {
                showSelectedFragment(R.id.searchFragment, 1);
            } else if (item.getItemId() == R.id.menu_stats) {
                showSelectedFragment(R.id.statsFragment, 3);
            } else if (item.getItemId() == R.id.menu_perfil) {
                showSelectedFragment(R.id.perfilFragment, 4);
            }
            return true;
        });

        goToHome( findViewById(android.R.id.content).getRootView() );
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.homeFragment);
    }

    public void goToHome(View view) {
        showSelectedFragment(R.id.homeFragment, 2);
        bottomNavigationView.getMenu().getItem(2).setChecked(true); //desactivo todos los otros activando el placeholder
        switchFabHomeColor(true);
    }

    private void showSelectedFragment(int id, int newIconIndex) {
        NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        if (!Objects.isNull(controller.getCurrentDestination()) && controller.getCurrentDestination().getId() == id)
            return;

        //Busco cual es el icono que estaba prendido antes del cambio para ver si es necesario guardarlo
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            if (bottomNavigationView.getMenu().getItem(i).isChecked()) {
                iconSelectedStack.push(i);
                break;
            }
        }
        boolean poppedFragment = controller.popBackStack(id, true);

        if (!poppedFragment) {
            controller.navigate(id);    //lo creo por que no existe
        } else {
            int i = 0;
            for (; i < iconSelectedStack.size(); i++)
                if (iconSelectedStack.get(i) == newIconIndex)
                    break;

            iconSelectedStack.subList(i, iconSelectedStack.size()).clear();
            Toast.makeText(getBaseContext(),iconSelectedStack.toString(),Toast.LENGTH_SHORT).show();
        }

            /*
            VER SI SE PUEDE ARREGLAR LA SITUACION EN LA QUE TENGO
            A-> B-> C-> D
            voy a A y me queda
            A
            estaria bueno que quede B-> C-> D-> A
             */

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //agrego este codigo para que la bottomNavBar mantenga su comportamiento
        if (!iconSelectedStack.isEmpty()) {
            int selectedIcon = iconSelectedStack.pop();
            bottomNavigationView.getMenu().getItem(selectedIcon).setChecked(true);
            switchFabHomeColor(selectedIcon == 2);
        }

    }

    private void switchFabHomeColor(boolean flag) {
        if (flag)
            homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.orange));
        else
            homeButton.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.primary));
    }
}